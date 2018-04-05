/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.work.dao.ApprLineDao;
import com.lgcns.ikep4.approval.work.dao.ApprWorkDocDao;
import com.lgcns.ikep4.approval.work.model.ApprEntrust;
import com.lgcns.ikep4.approval.work.model.ApprLine;
import com.lgcns.ikep4.approval.work.model.ApprSign;
import com.lgcns.ikep4.approval.work.service.ApprEntrustService;
import com.lgcns.ikep4.approval.work.service.ApprExamService;
import com.lgcns.ikep4.approval.work.service.ApprLineService;
import com.lgcns.ikep4.approval.work.service.ApprSignService;
import com.lgcns.ikep4.approval.work.service.ApprWorkDocService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * 결재선 관리 Service 구현
 *
 * @author 
 * @version $Id$
 */
@Service("apprLineService")
public class ApprLineServiceImpl extends GenericServiceImpl<ApprLine, String> implements ApprLineService {

	@Autowired
	private ApprLineDao		apprLineDao;	
	
	@Autowired
	private ApprWorkDocDao apprWorkDocDao;

	@Autowired
	private ApprWorkDocService apprWorkDocService;
	
	@Autowired
	private ApprExamService apprExamService;
	
	@Autowired
	private IdgenService idgenService;	
	
	@Autowired
	private ApprSignService apprSignService;
	
	@Autowired
	private ApprEntrustService apprEntrustService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ApprAdminConfigService apprAdminConfigService;
	
	@Autowired
	private MailSendService mailSendService;	
	
	
	/*****************************************
	 * 1. 결재선 및 결재선 버전정보 등록/수정/변경
	 * 2. 결재선 정보조회
	 * 3. 결재처리(결재선 상태변경,문서정보 상태변경)
	 * 4. 문서복사(부서합의/수신처)
	 * 5. 문서반려처리(부모문서반려,Child문서반려)
	 * 6. 문서상태변경,결재취소,결재복원,
	 * 7. 알람발송(메일/SMS/쪽지,메신저등)
	 * 8. 기타 Set
	 ****************************************/	
	
	
	/*****************************************
	 * 결재선 및 결재선 버전정보 등록/수정
	 ****************************************/	
	/**
	 * 결재 & 합의/ 수신자 정보 등록/수정
	 */
	public String create(ApprLine object, User user) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	object.getApprId());
		map.put("locale",   user.getLocaleCode());
	
		ApprDoc apprDoc = apprWorkDocDao.readApprDoc(map);			

		// 미진행 결재선정보 삭제
		apprLineDao.removeNotProgress(object.getApprId());
				
		// 결재선 저장
		for(ApprLine apprLine : object.getApprLineList()){
			// 결재처리 & 진행중 결재선 미처리
			if( apprLine.getApprStatus()>0 )
				continue;

			apprLine.setApprId(object.getApprId());
			apprLineDao.create(apprLine);	
		}		
		
		// 수신처 정보 삭제
		apprLineDao.removeApprReceiveLine(object.getApprId());
		
		// 결재선 Count
		int rcvApprOrder = object.getApprLineList()!=null ? object.getApprLineList().size()+1 : 1;
		
		// 수신처 정보 저장
		if(object.getApprReceiveLineList()!=null) {
			for(ApprLine apprLine : object.getApprReceiveLineList()){				
				apprLine.setApprOrder(rcvApprOrder);
				apprLine.setApprId(object.getApprId());
				apprLineDao.create(apprLine);	
			}			
		}
				
		// 결재선 버전정보 등록
		if(apprDoc.getApprDocStatus()== 1 || apprDoc.getApprDocStatus()==3 ||	apprDoc.getApprDocStatus()==4 ){

			String message = null;

			if(object.getApprMessage()!=null && !object.getApprMessage().equals("")) {
				message=object.getApprMessage();
			} else {
				// 결재의견이 없는경우 (등록/재기안) 이전 결재선 버전 정보 삭제
				message = "기안";
				apprLineDao.removeVersion(object.getApprId());
			}			
			createVersion(object.getApprId(),message, user);
		}				
		return object.getApprId();
	}
	
	/**
	 *  결재선 버전정보 등록
	 * @param apprId
	 * @param message
	 * @param user
	 */
	public void createVersion(String apprId,String message, User user) {
		
		Map<String, String> versionMap = new HashMap<String, String>();
		versionMap.put("apprId",			apprId);
		versionMap.put("registerId",		user.getUserId());
		versionMap.put("registerName",		user.getUserName());
		versionMap.put("registerGroupId",	user.getGroupId());
		versionMap.put("registerGroupName",	user.getTeamName());
		versionMap.put("registerJobTitle",	user.getJobTitleName());
		versionMap.put("modifyReason",		message);

		apprLineDao.createLineVersion(versionMap);			
	}
	
	/*
	 * 결재선 변경 apprType : 0 , 수신처 변경 apprType :3
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create(java.lang.Object)
	 */
	public String updateApprLine(ApprLine object,  User user) {
		
		// 결재선 버전 확인 및 등록
		ApprLine apprLineVer = new ApprLine();
		apprLineVer.setApprId(object.getApprId());

		/**
		 * 현재 문서 정보 조회
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	object.getApprId());
		map.put("locale",   user.getLocaleCode());
		ApprDoc apprDoc = apprWorkDocDao.readApprDoc(map);	

		// 미진행 결재선 삭제
		apprLineDao.removeNotProgress(object.getApprId());
				
		// 변경된 결재선 정보 저장
		for(ApprLine apprLine : object.getApprLineList()){
			// 결재처리 , 현재 진행자 제외
			if( apprLine.getApprStatus()>0 )
				continue;			
			
			apprLine.setApprId(object.getApprId());
			apprLineDao.create(apprLine);			
		}
		
		// 결재선 버전정보 등록
		if(apprDoc.getApprDocStatus()==1 ){
			createVersion(object.getApprId(),object.getModifyReason(), user);
		}
		
		// 수신처 apprOrder 값 갱신
		// 결재선 갯수 조회
		ApprLine apprLine = new ApprLine();
		apprLine.setApprId(object.getApprId());
		apprLine.setApprType(0);	// 결재선만 
		apprLine.setApprOrder(9);	// apprOrder 항목 제외 검색(결재선만 전체)
		apprLine.setApprStatus(9);	// apprStatus 항목 제외 검색(결재선만 전체)		
		int countLine = apprLineDao.countApprLine(apprLine)+1;
		
		map.put("apprOrder", 	countLine+"");
		apprLineDao.updateReceiveOrder(map);
		
		return object.getApprId();
	}

	/*
	 * 수신처 변경
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#updateReceiverApprLine(com.lgcns.ikep4.approval.work.model.ApprLine, com.lgcns.ikep4.support.user.member.model.User)
	 */
	public String updateReceiverApprLine(ApprLine object , User user) {

		// 수신처 변경
		// 결재선 버전 확인 및 등록
		ApprLine countApprLine = new ApprLine();
		countApprLine.setApprId(object.getApprId());
		countApprLine.setApprType(0);	// 결재선만 
		countApprLine.setApprOrder(9);	// apprOrder 항목 제외 검색(결재선만 전체)
		countApprLine.setApprStatus(9);	// apprStatus 항목 제외 검색(결재선만 전체)
		
		// 결재선 max apprOrder
		int countLine = apprLineDao.countApprLine(countApprLine)+1;
		
		// 문서 결재 수신처 지정 정보 삭제
		apprLineDao.removeApprReceiveLine(object.getApprId());
		
		// 문서 결재중 수신처 지정 정보 저장		
		if(object.getApprReceiveLineList()!=null) {
			for(ApprLine apprLine : object.getApprReceiveLineList()){				
				// 결재자의 위임정보 확인 및 위임자 정보 입력
				apprLine.setApprOrder(countLine);
				apprLine.setApprId(object.getApprId());
				apprLineDao.create(apprLine);	
			}			
		}		
		return object.getApprId();
	}
	
	
	
	/*****************************************
	 * 결재선 정보조회
	 ****************************************/		
	/**
	 * Doc 문서의 결재선 정보 조회 (apprId, apprType : 3 수신만, apprType != 3 결재/합의만) 
	 */
	public List<ApprLine> listApprLine(Map<String, String> map) {
		List<ApprLine> list = (List<ApprLine>)this.apprLineDao.listApprLine(map);
		
		for(ApprLine apprLine : list) {
			if(apprLine.getApproverType()==1) {
				
				Map<String, String> subMap = new HashMap<String, String>();
				subMap.put("apprId", 	apprLine.getApprId());
				subMap.put("locale",    "");
				subMap.put("groupId",   apprLine.getApproverId());
				
				ApprDoc apprDoc = apprWorkDocService.readApprDocSub(subMap);
				if(apprDoc!=null) {
					List<ApprLine> apprLineList = this.lastApprLine(apprDoc.getApprId());
					if(apprLineList!=null && apprLineList.size()>0) {
						apprLine.setApprLineList(apprLineList);
					}
				}
			}
		}
		return list;
	}


	/*****************************************
	 * 결재처리
	 ****************************************/		
	/*
	 * 결재 처리
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#updateApproval(com.lgcns.ikep4.approval.work.model.ApprLine, com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateApproval(ApprLine apprLine,User user, HttpServletRequest request) { 
		
		// 결재자의 결재 처리정보 갱신
		updateApprovalLine(apprLine,user);
	
		/**
		 * 다음결재자 처리
		 * 문서결재처리
		 * 부모문서 처리
		 * Child 문서처리
		 */
		updateDocAppr(apprLine,user,request);

	}
	
	/**
	 * 결재선 정보 결재처리 
	 * @param apprLine
	 * @param user
	 */
	public void updateApprovalLine(ApprLine apprLine,User user){
		
		ApprSign apprSign = new ApprSign();
		
		// 결재자의 결재처리 내용 저장 
		apprLine.setApprStatus(apprLine.getApprStatus());// 결재 2 /합의 4 /반려 3	
		// 위임자정보
		if(apprLine.getEntrustUserId()!=null && !apprLine.getEntrustUserId().equals("")){

			// 결재 사인 위임자 정보 설정
			apprSign.setUserId(apprLine.getEntrustUserId());
			
			ApprEntrust apprEntrust = new ApprEntrust();
			apprEntrust.setPortalId(user.getPortalId());
			apprEntrust.setUsage("1");
			apprEntrust.setUserId(apprLine.getEntrustUserId());	// 위임자
			apprEntrust.setSignUserId(user.getUserId());		// 수임자
			apprEntrust.setIsValidDate("1");					// 위임기간 Valid check
			apprEntrust = apprEntrustService.entrustDetail(apprEntrust);
			
			apprLine.setApproverId(apprLine.getEntrustUserId());
			apprLine.setEntrustUserId(user.getUserId());
			apprLine.setEntrustUserName(user.getUserName());
			apprLine.setEntrustGroupName(user.getTeamName());
			apprLine.setEntrustJobTitle(user.getJobTitleName());
			
			apprLine.setEntrustId(apprEntrust.getEntrustId());
		} else if(apprLine.getApproverId()==null || apprLine.getApproverId().equals("")) {
			// 결재 사인 정보 설정
			apprSign.setUserId(user.getUserId());
			
			apprLine.setApproverId(user.getUserId());
		} else {
			// 결재 사인 정보 설정
			apprSign.setUserId(apprLine.getApproverId());
		}
		
		
		// 문서결재 기본 설정 정보 ( 결재 사인 사용유무 )
		ApprAdminConfig apprConfig= new ApprAdminConfig();
		apprConfig = apprAdminConfigService.adminConfigDetail(user.getPortalId());
		
		if(apprConfig!=null && apprConfig.getIsUseSign().equals("1")) {
			// 결재 사인 정보 조회(위임 또는 결재자)
			apprSign = apprSignService.selectUserSign(apprSign.getUserId());
		
			if(apprSign!=null && (apprLine.getApprStatus()==2 || apprLine.getApprStatus()==4)){
				apprLine.setSignFileId(apprSign.getSignFileId());
			}
		}
		
		this.apprLineDao.updateApproval(apprLine);			
	}
	
	/**
	 * 다음결재자 처리/문서결재처리/부모문서 처리/Child 문서처리
	 * @param apprLine
	 * @param user
	 */
	public void updateDocAppr(ApprLine apprLine,User user, HttpServletRequest request){
		/**
		 * 현재 문서 정보 조회
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprLine.getApprId());
		map.put("locale",   user.getLocaleCode());
		ApprDoc apprDoc = apprWorkDocDao.readApprDoc(map);		

		/**
		 * 현재 결재자 정보 조회
		 */
		apprLine = apprLineDao.readLine(apprLine);	
		
		if( apprLine.getApprStatus()==3 && ( apprLine.getApprType()==0 || apprLine.getApprType()==1 ) ){			
			/**
			*	결재자/합의(필수) 반려처리
			*		- 문서가 Child 문서인경우 : 부모문서 반려처리
			*		- 문서가 부모문서인 경우  : Child 문서 반려처리
			*/			

			// 결재 문서(Doc) 반려처리
			updateDocStatus(apprDoc,user,apprLine.getApprStatus()+"");
			
			// 알람 발송 (메일/SMS)		
			sendAlarm(user,apprDoc, request);
					
			if(apprDoc.getParentApprId()!=null && !apprDoc.getParentApprId().equals("")) {	
				// 현재 결재문서가 Child 문서인 경우 : 부모 반려처리 및 동일 레벨의 child 문서 반려처리
				updateParentRejectDoc(apprDoc,apprLine,user,request);
			} else {
				// 현재 문서가 부모 문서인 경우 : child 문서 반려처리
				updateChildRejectDoc(apprDoc,apprLine,user,request);
			}				
		} else {
			/**
			*	결재자 결재/ 합의자 합의/ 합의(선택) 반려시 
			*/
			
			// 동일 apprOrder 의 결재자 존재유무
			map.put("apprOrder", 	apprLine.getApprOrder()+"");
			boolean	exists	=	apprLineDao.exists(map);

			if(!exists) {

				// 다음 결재자 유무
				Map<String, String> nextUserMap = new HashMap<String, String>();
				nextUserMap.put("apprId", 	apprLine.getApprId());
				nextUserMap.put("apprOrder", apprLine.getApprOrder()+1+"");			
				
				// 다음 결재자 유무 확인 (결재/합의자 , 수신처는 제외)
				boolean nextApprUser	=apprLineDao.getNext(nextUserMap);
				
				// 다음 결재 진행
				if(nextApprUser) {	

					// 다음 결재자가 부서 합의 인경우 조회
					List<ApprLine> nextApprLine=  apprLineDao.getNextApprLine(nextUserMap);

					// 부서합의가 존재하면 원본 문서 복사
					if(nextApprLine!=null && nextApprLine.size()>0){
						for(ApprLine apprLine1 : nextApprLine){
							apprWorkDocService.duplicateApprDoc(apprLine1.getApprId(),apprLine1.getApproverType(),apprLine1.getApproverId(),apprLine1.getApproverGroupName(),apprLine1.getApprType());
						}
					}

					// 다음 결재자 결재 진행
					apprLine.setApprStatus(1);// 진행
					apprLine.setApprOrder(apprLine.getApprOrder()+1);
					apprLineDao.update(apprLine);

					// 알람 발송 (메일/SMS)		-- 다음 진행자에게 알람 발송			
					sendAlarm(user,apprDoc, request);
				} else {	// 결재 완료 처리
	
					updateDocStatus(apprDoc,user,apprLine.getApprStatus()+"");
				
					// 알람 발송 (메일/SMS)	-- 기안자 완료 알림 발송				
					sendAlarm(user,apprDoc, request);

					// 현재 결재 문서가 부모 문서인 경우
					if(apprDoc.getParentApprId()==null || apprDoc.getParentApprId().equals("")){						
						// 협조 공문이면서 결재 완료된 문서인 경우
						if( apprDoc.getApprDocStatus()==2 && apprDoc.getApprDocType()==1) {	
							// 수신처 존재 유무 확인 및 수신처 문서 복사
							duplicateRecvDoc(apprLine);
						}
					} else {
						// 현재 문서가 Child 문서인경우
						parentApprDocUpdate(apprDoc,apprLine,user,request);
					}
				}
			}			
		}	

	}
	
	/**
	* 부모 문서 및 결재선 정보 갱신
	*
	*/
	public void	parentApprDocUpdate(ApprDoc apprDoc,ApprLine apprLine,User user, HttpServletRequest request) {

		// 문서가 Child 문서인 경우
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprDoc.getParentApprId());
		map.put("locale",   user.getLocaleCode());		
		ApprDoc parentApprDoc = apprWorkDocDao.readApprDoc(map);

		// 부서 합의 문서 처리( 부모문서 미완료)
		if(parentApprDoc.getApprDocStatus()!=2) {	
			
			// 부모 결재선 결재처리 내용 저장 (부서합의)
			ApprLine parentApprLine = new ApprLine();
			parentApprLine.setApprMessage(apprLine.getApprMessage());
			parentApprLine.setApprId(parentApprDoc.getApprId());
			parentApprLine.setApprStatus(apprLine.getApprStatus());// 결재/합의/반려		
			parentApprLine.setApproverId(apprDoc.getApprGroupId()); // 부서합의 부서코드		
			updateApprovalLine(parentApprLine,user);
			
			parentApprLine.setApprId(parentApprDoc.getApprId());
			parentApprLine.setApproverId(apprDoc.getApprGroupId()); // 부서 합의 		
			parentApprLine = apprLineDao.readLine(parentApprLine);
			
			// 동일한 apprOrder 의 진행중인 결재/합의자 존재유무
			Map<String, String> existsMap = new HashMap<String, String>();
			existsMap.put("apprId", 	parentApprLine.getApprId());				
			existsMap.put("apprOrder",	parentApprLine.getApprOrder()+"");							
										
			// 동일한 apprOrder 의 진행중인 결재/합의자가 없으면 결재선 정보 수정 또는 완료 처리
			boolean	exists	=	apprLineDao.exists(existsMap);
			
			if(!exists) {
				// 다음 결재자 유무
				Map<String, String> nextUserMap = new HashMap<String, String>();
				nextUserMap.put("apprId", 	parentApprLine.getApprId());
				nextUserMap.put("apprOrder", parentApprLine.getApprOrder()+1+"");	
				boolean nextAppr	=apprLineDao.getNext(nextUserMap);
				
				// 다음 결재자 처리
				if(nextAppr) {						
					// 다음 결재자가 부서 합의 인경우 조회
					List<ApprLine> nextApprLine=  apprLineDao.getNextApprLine(nextUserMap);

					// 부서합의가 존재하면 원본 문서 복사
					if(nextApprLine!=null && nextApprLine.size()>0){
						for(ApprLine apprLine1 : nextApprLine){
							apprWorkDocService.duplicateApprDoc(apprLine1.getApprId(),apprLine1.getApproverType(),apprLine1.getApproverId(),
									apprLine1.getApproverGroupName(),apprLine1.getApprType());
						}
					}

					// 다음 결재자 결재 진행
					parentApprLine.setApprStatus(1);// 진행
					parentApprLine.setApprOrder(parentApprLine.getApprOrder()+1);
					apprLineDao.update(parentApprLine);

					// 알람 발송 (메일/SMS)		-- 다음 진행자에게 알람 발송			
					sendAlarm(user,parentApprDoc, request);						
				}
			}
		} else {
			// 수신처 문서처리
			// 수신처 문서 결재 완료	

			ApprLine parentApprLine = new ApprLine();
			parentApprLine.setApprId(parentApprDoc.getApprId());			
			parentApprLine.setApproverId(user.getUserId());
			parentApprLine = apprLineDao.readLine(parentApprLine);
			
			// 부모 결재선 결재처리 내용 저장 (수신처 부서)
			if(parentApprLine==null) {
				ApprLine parentApprLine1 = new ApprLine();
				parentApprLine1.setApprMessage(apprLine.getApprMessage());
				parentApprLine1.setApprStatus(apprLine.getApprStatus());// 결재/반려	
				parentApprLine1.setApproverId(apprDoc.getApprGroupId()); // 수신처 부서코드
				parentApprLine1.setApprId(parentApprDoc.getApprId());							
				updateApprovalLine(parentApprLine1,user);
			} else {
				parentApprLine.setApprMessage(apprLine.getApprMessage());
				parentApprLine.setApprStatus(apprLine.getApprStatus());// 결재/반려
				updateApprovalLine(parentApprLine,user);
			}						
		}
	}
	
	/**
	 * Doc 문서 정보 결재 상태 정보 수정 (0:임시저장, 1:진행중, 2:완료, 3:반려, 4:회수, 5:오류,6:접수대기 ..)
	 * @param apprDoc
	 * @param user
	 * @param status
	 */
	public void	updateDocStatus(ApprDoc apprDoc,User user,String status){
		//try{
			// 결재 상태 변경
			apprDoc.setUpdaterId(user.getUserId());
			apprDoc.setUpdaterName(user.getUserName());
			if(apprDoc.getParentApprId()!=null && status.equals("4")) { // 자식문서를 회수처리할때 접수대기 상태로 변경
				apprDoc.setApprDocStatus(6);
			} else {
				apprDoc.setApprDocStatus(Integer.parseInt(status));
			}
			if(status.equals("2")){
				
				ApprAdminConfig apprConfig= new ApprAdminConfig();
				apprConfig = apprAdminConfigService.adminConfigDetail(user.getPortalId());	
				
				String curDay	= DateUtil.getToday("yyyyMMdd");	// 날짜
				String deptNo	="";	// 부서코드 , 부서명
				String formNo	="";	// 양식코드 , 양식명
				String sequence ="";
				
				if(apprConfig.getDocNoMethod().equals("0")){//전체
					//apprDoc.setApprDocNo(idgenService.getNextId()); // 문서 번호

					sequence= String.format("%04d",apprWorkDocDao.getApprDocMaxNo(curDay+"-")+1);
					apprDoc.setApprDocNo(curDay+"-"+sequence); // 문서 번호
				}
				else if(apprConfig.getDocNoMethod().equals("1")){//부서

					deptNo=apprDoc.getApprGroupId();
					
					sequence= String.format("%04d",apprWorkDocDao.getApprDocMaxNo(curDay+"-"+deptNo+"-")+1);

					apprDoc.setApprDocNo(curDay+"-"+deptNo+"-"+sequence); // 문서 번호
				}
				else if(apprConfig.getDocNoMethod().equals("2")){//양식별

					formNo = apprDoc.getFormId();

					sequence= String.format("%04d",apprWorkDocDao.getApprDocMaxNo(curDay+"-"+formNo+"-")+1);
					apprDoc.setApprDocNo(curDay+"-"+formNo+"-"+sequence); // 문서 번호
				}
				
			}
			apprWorkDocDao.updateApprDocStatus(apprDoc);					

		/*} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\n ApprDoc 상태변경 실패...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
		}	*/	
	}
	
 
	
	
	/*****************************************
	 * 문서 복사(부서합의문서,수신처문서)
	 ****************************************/	
	/**
	 * 수신처 문서 복사및 결재선 처리 ( 사용자/부서 포함 )
	 */
	public void	duplicateRecvDoc(ApprLine apprLine){
		// 수신처 존재 유무 확인
		List<ApprLine> nextReceiveApprLine=  apprLineDao.getReceiveApprLine(apprLine.getApprId());

		for(ApprLine receiveApprLine : nextReceiveApprLine){
			
			String newApprId =apprWorkDocService.duplicateApprDoc(receiveApprLine.getApprId(),receiveApprLine.getApproverType(),receiveApprLine.getApproverId(),
					receiveApprLine.getApproverGroupName(),receiveApprLine.getApprType());

			if(receiveApprLine.getApprType()==3 && receiveApprLine.getApproverType()==0) {

				// 현재 문서 정보에 수신처로 사용자 정보 세팅
				ApprDoc apprDoc = new ApprDoc();
				
				apprDoc.setApprId(newApprId);
				apprDoc.setUpdaterId(receiveApprLine.getApproverId());
				apprDoc.setUpdaterName(receiveApprLine.getApproverName());
				apprDoc.setRegisterJobTitle(receiveApprLine.getApproverJobTitle());	
				apprDoc.setRegisterMessage("");				
				apprWorkDocService.updateReceiverApprDocStatus(apprDoc);				
				
				// 수신처가 사용자인 경우 결재라인에 등록
				ApprLine newApprLine = new ApprLine();
				newApprLine.setApprId(newApprId);
				newApprLine.setApprType(0);
				newApprLine.setApproverId(receiveApprLine.getApproverId());	
				newApprLine.setApproverName(receiveApprLine.getApproverName());
				newApprLine.setApproverGroupName(receiveApprLine.getApproverGroupName());
				newApprLine.setApproverJobTitle(receiveApprLine.getApproverJobTitle());
				newApprLine.setApproverType(0);
				newApprLine.setApprOrder(1);
				newApprLine.setApprStatus(0);
				apprLineDao.create(newApprLine);			
			}
		}		
	}
		
	

	/*****************************************
	 * 문서반려처리(부모문서반려,Child문서반려)
	 ****************************************/	
	/**
	* 부모문서 반려처리
	**/
	public void updateParentRejectDoc(ApprDoc apprDoc,ApprLine apprLine,User user, HttpServletRequest request) {
		
		// 부모 결재 문서 조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("locale",   user.getLocaleCode());			
		map.put("apprId", 	apprDoc.getParentApprId());
		
		ApprDoc parentApprDoc = apprWorkDocDao.readApprDoc(map);		
		
		// 부모 결재 문서가 미완료 ( 부서 합의 ) 문서 인경우
		if(parentApprDoc.getApprDocStatus()!=2) {				
			
			// 부서합의 문서의 현재 결재선이 합의(필수/선택) 확인
			ApprLine parentApprLine1 = new ApprLine();
			parentApprLine1.setApprId(parentApprDoc.getApprId());
			parentApprLine1.setApproverId(apprDoc.getApprGroupId()); // 부서 합의 		
			parentApprLine1 = apprLineDao.readLine(parentApprLine1);	
			
			// 부서합의(선택)
			if(parentApprLine1.getApprType()==2) {
				
				/** 부모결재선 정보 반려처리 **/
				ApprLine parentApprLine = new ApprLine();
				parentApprLine.setApprId(parentApprDoc.getApprId());
				parentApprLine.setApproverId(apprDoc.getApprGroupId()); // 부서 합의 		
				parentApprLine.setApprMessage(apprLine.getApprMessage());
				parentApprLine.setApprStatus(apprLine.getApprStatus());// 부모 부서 합의 결재선 반려처리
				updateApprovalLine(parentApprLine,user);	
				
				map.put("apprOrder", 	parentApprLine1.getApprOrder()+"");
				
				// 부모 문서의 동일한 결재순서의 결재선 존재유무
				boolean	exists	=	apprLineDao.exists(map);
				
				if(!exists) {
					// 부모결재선 다음 결재자 유무
					Map<String, String> nextUserMap = new HashMap<String, String>();
					nextUserMap.put("apprId", 	parentApprDoc.getApprId());
					nextUserMap.put("apprOrder", parentApprLine1.getApprOrder()+1+"");			
					
					// 다음 결재자 유무 확인 (결재/합의자 , 수신처는 제외)
					boolean nextApprUser	=apprLineDao.getNext(nextUserMap);
					
					if(nextApprUser) {				

						// 다음 결재자가 부서 합의 인경우 조회
						List<ApprLine> nextApprLine=  apprLineDao.getNextApprLine(nextUserMap);

						// 부서합의가 존재하면 원본 문서 복사
						if(nextApprLine!=null && nextApprLine.size()>0){
							for(ApprLine apprLine1 : nextApprLine){
								apprWorkDocService.duplicateApprDoc(apprLine1.getApprId(),apprLine1.getApproverType(),apprLine1.getApproverId(),
										apprLine1.getApproverGroupName(),apprLine1.getApprType());
							}
						}

						// 다음 결재자 결재 진행
						parentApprLine1.setApprStatus(1);// 진행
						parentApprLine1.setApprOrder(parentApprLine1.getApprOrder()+1);
						apprLineDao.update(parentApprLine1);

						// 알람 발송 (메일/SMS)		-- 다음 진행자에게 알람 발송			
						sendAlarm(user,parentApprDoc, request);	
					}					
				}
			} else { 
				// 부서합의(필수) 인경우
				// 부모 문서 반려 처리
				updateDocStatus(parentApprDoc,user,apprLine.getApprStatus()+"");
				
				// 알람 발송 (메일/SMS)					
				sendAlarm(user,parentApprDoc, request);
				
				/** 부모결재선 정보 반려처리 **/
				ApprLine parentApprLine = new ApprLine();
				parentApprLine.setApprId(parentApprDoc.getApprId());
				parentApprLine.setApproverId(apprDoc.getApprGroupId()); // 부서 합의 		
				parentApprLine.setApprMessage(apprLine.getApprMessage());
				parentApprLine.setApprStatus(apprLine.getApprStatus());// 부모 부서 합의 결재선 반려처리
				updateApprovalLine(parentApprLine,user);
				
				// 동일 부모 결재문서를 가진  나머지 진행중 문서 반려처리
				map.put("apprId", 	apprDoc.getParentApprId());
				map.put("locale",   user.getLocaleCode());
				// 부서 합의 목록
				map.put("apprType",   "0");
				List<ApprDoc> subList = apprWorkDocService.subList(map);

				if(subList!=null) {
					for(ApprDoc apprDoc1 : subList) {
						// 진행중, 접수 대기
						if(apprDoc1.getApprDocStatus()==1 || apprDoc1.getApprDocStatus()==6) {							
							updateDocStatus(apprDoc1,user,apprLine.getApprStatus()+"");
							
							ApprLine apprLine1 = new ApprLine();
							apprLine1.setApprId(apprDoc1.getApprId());
							apprLine1.setApproverType(0);//사용자
							int apprOrder = apprLineDao.ApprOrderInProgress(apprDoc1.getApprId());
							if(apprOrder>0) {
								apprLine1.setApprOrder(apprOrder);						
								updateRejectApprLine(apprLine1);
							}								
		
							/** 부모결재선 정보 반려처리 **/
							parentApprLine = new ApprLine();
							parentApprLine.setApprId(apprDoc1.getParentApprId());
							parentApprLine.setApproverId(apprDoc1.getApprGroupId()); // 부서 합의 			
							parentApprLine.setApprMessage("원본 결재문서가 반려되어 반려처리함");
							parentApprLine.setApprStatus(apprLine.getApprStatus());// 부모 부서 합의 결재선 반려처리							
							updateApprovalLine(parentApprLine,user);			
							// 알람 발송 (메일/SMS)					
							sendAlarm(user,apprDoc1, request);
						}
					}	
				}
				parentApprLine = new ApprLine();
				parentApprLine.setApprId(parentApprDoc.getApprId());
				parentApprLine.setApproverId(apprDoc.getApprGroupId()); // 부서 합의 				
				parentApprLine = apprLineDao.readLine(parentApprLine);
				
				// 부모 결재선 동일한 apprOrder 진행중인 결재선 반려처리 
				updateRejectApprLine(parentApprLine);
			
			}
		}		
	}

	/**
	* Child 문서 반려처리
	*/
	public void updateChildRejectDoc(ApprDoc apprDoc,ApprLine apprLine,User user, HttpServletRequest request) {
		// 현재 결재문서가 원본 문서의 경우 (Child 문서 조회  반려처리)
		Map<String, String> map = new HashMap<String, String>();	
		map.put("apprId", 	apprDoc.getApprId());
		map.put("locale",   user.getLocaleCode());
		// 부서 합의 목록
		map.put("apprType",   "0");
		List<ApprDoc> subList = apprWorkDocService.subList(map);

		if(subList != null) {
			for(ApprDoc apprDoc1 : subList) {
				// 진행중, 부서문서 접수대기
				if(apprDoc1.getApprDocStatus()==1 || apprDoc1.getApprDocStatus()==6) {
					// 대기중 Doc 반려처리
					updateDocStatus(apprDoc1,user,apprLine.getApprStatus()+"");
					
					ApprLine apprLine1 = new ApprLine();
					apprLine1.setApprId(apprDoc1.getApprId());
					apprLine1.setApproverType(0);//사용자
					int apprOrder = apprLineDao.ApprOrderInProgress(apprDoc1.getApprId());
					if(apprOrder>0) {
						apprLine1.setApprOrder(apprOrder);						
						updateRejectApprLine(apprLine1);
					}
					/** 부모결재선 정보 반려처리 **/
					ApprLine parentApprLine = new ApprLine();
					parentApprLine.setApprId(apprDoc1.getParentApprId());
					parentApprLine.setApproverId(apprDoc1.getApprGroupId()); // 부서 합의 			
					parentApprLine.setApprMessage("원본 결재문서가 반려되어 반려처리함");
					parentApprLine.setApprStatus(apprLine.getApprStatus());// 부모 부서 합의 결재선 반려처리
					updateApprovalLine(parentApprLine,user);
					// 알람 발송 (메일/SMS)					
					sendAlarm(user,apprDoc1, request);
				} 
			}	
		}
		// 부모 결재선 동일한 apprOrder 진행중인 결재선 반려처리 
		updateRejectApprLine(apprLine);	
	}	

	/**
	 * 반려처리 ( 병렬합의시 동일 apprOrder 중 진행중인 결재선)
	 * @param apprLine
	 */
	public void	updateRejectApprLine(ApprLine apprLine){
		ApprLine countApprLine = new ApprLine();
		countApprLine.setApprId(apprLine.getApprId());
		countApprLine.setApprOrder(apprLine.getApprOrder());// 동일 결재선 apprOrder
		countApprLine.setApprStatus(1);// 진행중

		// 동일한 결재순서중 진행중 결재라인 갯수
		int countLine = apprLineDao.countApprLine(countApprLine);

		if(countLine>0){
			// 동일한 결재순서의 반려처리
			apprLineDao.updateRejectApprLine(countApprLine);
		}		
	}

		

	/*****************************************
	 * 결재회수,접수자전결,결재취소,결재복원
	 ****************************************/	
	
	/*
	 * 현황관리 > 문서복원 > 결재 회수 처리 ( 기안문의 결재선 정보 초기화, Child 기안문 삭제, Child 기안문 결재선 삭제 )
	 * 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#updateRecoveryAppr(java.lang.String)
	 */
	public void updateRecoveryAppr(String apprId,String userLocale) {
		// 기안문  결재선 정보 초기화
		apprLineDao.updateRecoveryAppr(apprId);		
		
		// child 기안문 삭제 및 결재선 삭제 
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   userLocale);
		map.put("apprType", "0");
		List<ApprDoc> subList = apprWorkDocService.subList(map);
		
		for(ApprDoc apprDoc : subList){
			// child 기안문 삭제
			apprWorkDocService.deleteApprDoc(apprDoc.getApprId());
		}
	}
	

	/*
	 * 부서수신함/개인수신함 접수자 전결 처리
	 * 합의 부서 문서 , 수신처 부서 문서, 수신처 수신자 문서
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#updateReceiverApproval(com.lgcns.ikep4.approval.work.model.ApprLine, com.lgcns.ikep4.support.user.member.model.User)
	 */
	public void updateApprovalRecEnd(ApprLine apprLine,User user, HttpServletRequest request) { 
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprLine.getApprId());		
		map.put("locale",   user.getLocaleCode());
		
		boolean exists = apprLineDao.exists(map);
		// 결재 대상자가 User 인경우(수신처로 사용자인경우)
		if(exists) {
			// 복제 문서중 Appr_line 에 사용자 추가되는 부분은 수신처에 User 로 등록된 경우임.
			apprLine.setApprId(apprLine.getApprId());
			apprLine.setApprStatus(apprLine.getApprStatus());
			apprLine.setApproverId(user.getUserId());

			updateApprovalLine(apprLine,user);
		} else {
			// 복제 문서중 부서수신함 ( 합의 부서 문서, 수신처 부서 문서) 인경우
			// Appr_line 신규 추가 현재 접수자 전결 처리자
			
			apprLine.setApprId(apprLine.getApprId());
			apprLine.setApprType(0);
			apprLine.setApproverId(user.getUserId());	
			apprLine.setApproverName(user.getUserName());
			apprLine.setApproverGroupName(user.getTeamName());
			apprLine.setApproverJobTitle(user.getJobTitleName());
			apprLine.setApproverType(0);
			apprLine.setApprOrder(1);

			// 문서결재 기본 설정 정보 ( 결재 사인 사용유무 )
			ApprAdminConfig apprConfig= new ApprAdminConfig();
			apprConfig = apprAdminConfigService.adminConfigDetail(user.getPortalId());
			
			ApprSign apprSign = new ApprSign();
			apprSign.setUserId(user.getUserId());
			
			if(apprConfig!=null && apprConfig.getIsUseSign().equals("1")) {
				apprSign = apprSignService.selectUserSign(apprSign.getUserId());
			
				if(apprSign!=null && (apprLine.getApprStatus()==2 || apprLine.getApprStatus()==4)){
					apprLine.setSignFileId(apprSign.getSignFileId());
				}
			}

			apprLineDao.createRecEnd(apprLine);			
		}

		/**
		 * 문서 정보 및 합의부서,수신처 정보 처리
		 */
		updateDocAppr(apprLine,user,request);

		/**
		 * 부모 문서의 조회일을 Update
		 */
		ApprDoc apprDoc = apprWorkDocDao.readApprDoc(map);	
		apprLine.setApprId(apprDoc.getParentApprId());
		apprLine.setApproverId(apprDoc.getApprGroupId());
		apprLineDao.updateRead(apprLine);

		// 현재 문서 정보에 전결자 정보 갱신
		apprDoc.setApprDocStatus(apprLine.getApprStatus());
		apprDoc.setUpdaterId(user.getUserId());
		apprDoc.setUpdaterName(user.getUserName());
		apprDoc.setRegisterJobTitle(user.getJobTitleName());
		
		if(apprLine.getApprStatus()==2){
			apprDoc.setApprDocNo(idgenService.getNextId()); // 문서 번호
		}		
		apprWorkDocService.updateReceiverApprDocStatus(apprDoc);
	}




	/*
	 * 결재취소 처리 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#updateApprovalLineCancel(java.util.Map)
	 */
	public void updateApprovalLineCancel(ApprLine apprLine,  User user) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 		apprLine.getApprId());
		if(apprLine.getEntrustUserId()!=null && !apprLine.getEntrustUserId().equals("")){
			map.put("approverId",   apprLine.getEntrustUserId());
		} else {
			map.put("approverId",   user.getUserId());
		}
		// 결재자의 결재취소 처리
		apprLineDao.updateApprovalLineCancel(map);

		// 다음 결재자의 대기상태 처리
		apprLineDao.updateInitNextApprLine(map);
		
		// 복사본 문서 및 결재 정보 삭제
		// child 기안문 삭제 및 결재선 삭제 
		map.put("locale",   user.getLocaleCode());
		map.put("apprType",   "0");
		List<ApprDoc> subList = apprWorkDocService.subList(map);
		
		for(ApprDoc apprDoc : subList){
			// child 기안문 관련 정보 삭제
			apprWorkDocService.deleteApprDoc(apprDoc.getApprId());
		}		
	}	

	/*
	 * 관리자>결재문서관리>문서조회>결재복원>결재 복원
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#updateApprovalLineCancel(java.util.Map)
	 */
	public void updateApprovalLineRestore(String	apprId,String[] approverIds,  User user) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());
		
		ApprLine apprLine = new ApprLine();
		apprLine.setApprId(apprId);
		
		ApprDoc	apprDoc = new ApprDoc();
		
		// 결재 복원 순서 변경 apprOrder 가 큰것부터 처리를 위한..
		String[] tmpApproverIds = new String[approverIds.length];
		int j=0;
		for(int i=approverIds.length-1 ;i>=0; i--) {
			tmpApproverIds[j]=approverIds[i];
			j++;
		}
		approverIds=tmpApproverIds;
		for(String approverId : approverIds) {
			
			apprLine.setApproverId(approverId);
			apprLine = apprLineDao.readLine(apprLine);

			// 결재자의 결재취소 처리
			map.put("approverId", 	approverId);
			apprLineDao.updateApprovalLineCancel(map);

			// 다음 결재자의 진행초기화 처리
			apprLineDao.updateInitNextApprLine(map);
			
			// 해당 결재자가 요청한 검토내용 삭제처리
			map.put("examReqId", 	approverId);
			apprExamService.remove(map);
			
			// 부서합의의 경우 하위 부서 삭제
			if(apprLine.getApproverType()==1){
				map.put("groupId",   apprLine.getApproverId());
				
				apprDoc = apprWorkDocService.readApprDocSub(map);
				if(apprDoc!=null) {
					// 해당 부서합의 검토요청내용 삭제
					map.put("approverId", 	apprDoc.getApprId());
					map.put("examReqId", 	"");
					apprExamService.remove(map);
					
					// 해당 부서합의 문서 삭제
					apprWorkDocService.deleteApprDoc(apprDoc.getApprId());
				}
			}
		}		
	}
	

	
	
	/*****************************************
	 * 알람발송(메일/SMS/쪽지,메신저등)
	 ****************************************/		
	public void	sendAlarm(User user,ApprDoc apprDoc, HttpServletRequest request){
		
		// 알람 발송 대상자 조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprDoc.getApprId());

		
		// 결재문서 도착 알림여부 확인 및 알림	
		if(apprDoc.getApprDocStatus()==1){
			
			map.put("noticeArrival",   "1");
			
			map.put("sendTarget",   "mail");
			List<Map<String, String>> targetMailList = apprLineDao.targetListAlarm(map);
			if(targetMailList!=null && targetMailList.size()>0){
				sendMail(apprDoc,user,targetMailList,request);
			}
			
			map.put("sendTarget",   "sms");
			List<Map<String, String>> targetSmsList = apprLineDao.targetListAlarm(map);
			if(targetSmsList!=null&& targetSmsList.size()>0){
				sendSms(apprDoc,user,targetSmsList);
			}			
			
			map.put("sendTarget",   "notice");
			List<Map<String, String>> targetNoticeList = apprLineDao.targetListAlarm(map);
			if(targetNoticeList!=null&& targetNoticeList.size()>0){
				sendNotice(apprDoc,user,targetNoticeList);
			}
		
		} 
		// 현재 문서 반려 알림여부 및 알림
		else if(apprDoc.getApprDocStatus()==3) {
			
			map.put("sendTarget",   "mail");
			List<Map<String, String>> targetMailList = apprLineDao.targetListAlarmReject(map);
			if(targetMailList!=null && targetMailList.size()>0){
				sendMail(apprDoc,user,targetMailList,request);
			}
			
			map.put("sendTarget",   "sms");
			List<Map<String, String>> targetSmsList = apprLineDao.targetListAlarmReject(map);
			if(targetSmsList!=null&& targetSmsList.size()>0){
				sendSms(apprDoc,user,targetSmsList);
			}			
			
			map.put("sendTarget",   "notice");
			List<Map<String, String>> targetNoticeList = apprLineDao.targetListAlarmReject(map);
			if(targetNoticeList!=null&& targetNoticeList.size()>0){
				sendNotice(apprDoc,user,targetNoticeList);
			}		
		}
		// 결재문서 완료 알림여부 및 알림
		else if (apprDoc.getApprDocStatus()==2) {
			map.put("noticeFinish",   "1");	
			
			map.put("sendTarget",   "mail");
			List<Map<String, String>> targetMailList = apprLineDao.targetListAlarm(map);
			if(targetMailList!=null && targetMailList.size()>0){
				sendMail(apprDoc,user,targetMailList,request);
			}
			
			map.put("sendTarget",   "sms");
			List<Map<String, String>> targetSmsList = apprLineDao.targetListAlarm(map);
			if(targetSmsList!=null&& targetSmsList.size()>0){
				sendSms(apprDoc,user,targetSmsList);
			}			
			
			map.put("sendTarget",   "notice");
			List<Map<String, String>> targetNoticeList = apprLineDao.targetListAlarm(map);
			if(targetNoticeList!=null&& targetNoticeList.size()>0){
				sendNotice(apprDoc,user,targetNoticeList);
			}
		}
	}
	
	/**
	 * 메일발송
	 * 
	 * @param member
	 * @param user
	 * @param memberLevel
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendMail(ApprDoc apprDoc, User user, List<Map<String, String>> targetList, HttpServletRequest request) {

		Mail mail = new Mail();
		
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		
		if (user.getLocaleCode().toUpperCase().equals("KO")) {
			mail.setMailTemplatePath("approvalTemplate.vm");
		} else {
			mail.setMailTemplatePath("approvalEnglishTemplate.vm");
		}

		// 발신자
		User sender = new User();
		sender = user;

		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		String mainFrameUrl="";
		if (apprDoc.getApprDocStatus()==1) { // 다음 결재자에게 알림
			
			recip = new HashMap<String, String>();
			
			for(Map<String, String> alarmUserInfo : targetList){
				
				recip.put("email", alarmUserInfo.get("mail"));
				
				if (user.getLocaleCode().toUpperCase().equals("KO")) {
					recip.put("name", alarmUserInfo.get("userName"));
					// 메일 제목
					mail.setTitle(apprDoc.getApprTitle() + " 결재가 요청되었습니다.");
				} else {
					recip.put("name", alarmUserInfo.get("userEnglishName"));
					// 메일 제목
					mail.setTitle(apprDoc.getApprTitle() + " 결재가 요청되었습니다.");
				}			
			}
			recipients.add(recip);
			mail.setToEmailList(recipients);
			
			mainFrameUrl="/approval/work/apprWorkDoc/viewApprDoc.do?apprId="+apprDoc.getApprId()+"&listType=listApprTodo&linkType=";

		} else if (apprDoc.getApprDocStatus()==2) { // 기안자,참조자,결재자 에게 완료알림
			
			// 기안자는 To
			recip = new HashMap<String, String>();
			User receiveUser = new User();
			receiveUser=userService.read(apprDoc.getRegisterId());
			
			recip.put("email", receiveUser.getMail());
			
			if (user.getLocaleCode().toUpperCase().equals("KO")) {
				recip.put("name", receiveUser.getUserName());
				// 메일 제목
				mail.setTitle(apprDoc.getApprTitle() + " 결재가 완료되었습니다.");
			} else {
				recip.put("name", receiveUser.getUserEnglishName());
				// 메일 제목
				mail.setTitle(apprDoc.getApprTitle() + " 결재가 완료되었습니다."+user.getUserName());
			}
			recipients.add(recip);
			mail.setToEmailList(recipients);
						
			// 참조자,결재자는 cc			
			List<HashMap> ccEmailList = new ArrayList<HashMap>();
			for(Map<String, String> alarmUserInfo : targetList){				
				// 최종 결재자는 제외
				if(user.getUserId().equals(alarmUserInfo.get("userId")))
					continue;
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", alarmUserInfo.get("mail"));
				if (user.getLocaleCode().toUpperCase().equals("KO")) {
					map.put("name", alarmUserInfo.get("userName"));
				} else {
					map.put("name", alarmUserInfo.get("userEnglishName"));
				}	
				ccEmailList.add(map);
			}			
			
			mail.setCcEmailList(ccEmailList);					
			mainFrameUrl="/approval/work/apprWorkDoc/viewApprDoc.do?apprId="+apprDoc.getApprId()+"&listType=listApprIntegrate&linkType=";
		} else if (apprDoc.getApprDocStatus()==3) {// 기안자에게 반려알림
			recip = new HashMap<String, String>();

			for(Map<String, String> alarmUserInfo : targetList){
				
				recip.put("email", alarmUserInfo.get("mail"));
								
				if (user.getLocaleCode().toUpperCase().equals("KO")) {
					recip.put("name", alarmUserInfo.get("userName"));
					// 메일 제목
					mail.setTitle(apprDoc.getApprTitle() + " 결재가 반려되었습니다.");
				} else {
					recip.put("name", alarmUserInfo.get("userEnglishName"));
					// 메일 제목
					mail.setTitle(apprDoc.getApprTitle() + " 결재가 반려되었습니다.");
				}
			
			}	
			recipients.add(recip);
			mail.setToEmailList(recipients);	
			mainFrameUrl="/approval/work/apprWorkDoc/viewApprDoc.do?apprId="+apprDoc.getApprId()+"&listType=rejectList&linkType=";
		} else if (apprDoc.getApprDocStatus()==4) {// 기안자에게 반려알림
			mainFrameUrl="/approval/work/apprWorkDoc/viewApprDoc.do?apprId="+apprDoc.getApprId()+"&listType=listApprDeptRec&linkType=";
		}

		Map dataMap = new HashMap();
		dataMap.put("apprDoc", apprDoc);
		dataMap.put("user", user);
		dataMap.put("apprDocStatus", apprDoc.getApprDocStatus());
		dataMap.put("registDate", DateUtil.getToday(""));
		dataMap.put("sender", sender);
		dataMap.put("url",serverURL(request));
		dataMap.put("mainFrameUrl",URLEncoder.encode(mainFrameUrl));
		/** 메일발송 임시 미사용, 메일 발송 오류로인한 **/
		mailSendService.sendMail(mail, dataMap, sender);
		

		StringBuffer buffer = new StringBuffer();
		buffer.append("\r\n 메일발송.. ");
		this.log.debug(buffer.toString());
		buffer.delete(0, buffer.length());

	}	

	public String serverURL(HttpServletRequest request){
		String protocol = request.getProtocol();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		
		StringBuffer url = new StringBuffer();
		
		if( protocol.toLowerCase().indexOf("https") >=0 ){
			url.append("https://");
		}else{
			url.append("http://");
		}
		
		url.append(serverName);
		
		if( serverPort != 80){
			url.append(":").append(serverPort);
		}
		
		url.append(contextPath+"/portal/main/portalMain.do?mainFrameUrl=");
		
		return url.toString();
	}
	/**
	 * SMS 발송
	 * @param apprDoc
	 * @param user
	 * @param targetList
	 */
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendSms(ApprDoc apprDoc, User user, List<Map<String, String>> targetList) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("\r\n SMS 발송.. ");
		this.log.debug(buffer.toString());
		buffer.delete(0, buffer.length());

	}
	/**
	 * 메신저/쪽지 발송
	 * @param apprDoc
	 * @param user
	 * @param targetList
	 */
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendNotice(ApprDoc apprDoc, User user, List<Map<String, String>> targetList) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("\r\n 메신저/쪽지 발송.. ");
		this.log.debug(buffer.toString());
		buffer.delete(0, buffer.length());

	}	

	
	/**
	 * child 결재선 정보 ( 부서합의/수신처 )
	 * @param map
	 * @return
	 */
	public List<ApprLine> childListApprLine(Map<String, String> map) {
		List<ApprLine> list = (List<ApprLine>)this.apprLineDao.childListApprLine(map);  
		return list;
	}	
	
	/*
	 * 결재선 정보의 결재상태 수정 ( 다음결재자 결재진행 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update(java.lang.Object)
	 */
	public void update(ApprLine apprLine) {
		// 결재선 정보 수정
		apprLineDao.update(apprLine);
	}
	
	/*
	 * 결재/합의 자의 결재문서 Read 시간 수정
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#updateRead(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public void updateRead(ApprLine apprLine) {
		// 결재자의 결재문서 Read 정보 수정
		apprLineDao.updateRead(apprLine);
	}	
	
	/* 
	 * Doc의 결재선 정보 전체 삭제(결재/합의/수신처정보)
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	public void delete(String apprId) {
		apprLineDao.remove(apprId);
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#delete(java.util.List)
	 */
	public void delete(List<String> apprIds) {
		for (int i = 0; i < apprIds.size(); i++) {
			apprLineDao.remove(apprIds.get(i));	
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(Map<String, String> map) {
		return apprLineDao.exists(map);	
	}
	
	/*
	 * 결재자의 다음 결재/합의 대상자 존재유무 확인
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#getNext(java.lang.String)
	 */
	public boolean getNext(Map<String, String> map) {
		return apprLineDao.getNext(map);

	}

	/*
	 * 결재회수를 위한 최초 결재자 결재 상태 체크
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#beforeAppr(java.lang.String)
	 */
	public boolean beforeAppr(String apprId) {
		return apprLineDao.beforeAppr(apprId);

	}
	/*
	 * 결재가 진행중인지 확인 ( APPR_STATUS >1 )
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#countProgress(java.lang.String)
	 */
	public boolean countProgress(String apprId) {
		return apprLineDao.countProgress(apprId);

	}	
	/* 
	 * 결재/합의자의 결재선 정보 조회
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#readLine(com.lgcns.ikep4.approval.work.model.ApprLine)
	 */
	public ApprLine readLine(ApprLine apprLine) {
		apprLine = apprLineDao.readLine(apprLine);
		//intUserLine.setApprUserLineSub(apprUserLineSubdao.getUserLineSubList(defLineId));
		return apprLine;
	}
	
	public List<ApprLine> lastApprLine(String apprId) {
		List<ApprLine> list = (List<ApprLine>)this.apprLineDao.lastApprLine(apprId);  
		return list;
	}
 	
	/*
	 * Doc 문서의 FormId로 Default 결재선의 결재선 정보 조회 (defLineId, apprType : 3 수신만, apprType != 3 결재/합의만) 
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#listApprLineDef(java.util.Map)
	 */
	public List<ApprLine> listApprLineDef(Map<String, String> map) {
		List<ApprLine> list = (List<ApprLine>)this.apprLineDao.listApprLineDef(map);  
		return list;
	}

	/*
	 * 결재취소 가능여부 확인
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.work.service.ApprLineService#existsNextApprProgress(java.util.Map)
	 */
	public boolean existsNextApprProgress(Map<String, String> map) {
		return apprLineDao.existsNextApprProgress(map);
	}
	
	public List<ApprLine> listVersion(String apprId) {
		List<ApprLine> list = (List<ApprLine>)this.apprLineDao.listVersion(apprId);  
		return list;
	}
	
	public List<ApprLine> groupByVersion(String apprId) {
		List<ApprLine> list = (List<ApprLine>)this.apprLineDao.groupByVersion(apprId);  
		return list;
	}

	
	
}
