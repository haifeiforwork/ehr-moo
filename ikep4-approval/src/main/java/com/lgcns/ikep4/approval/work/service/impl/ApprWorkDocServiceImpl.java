/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.work.dao.ApprLineDao;
import com.lgcns.ikep4.approval.work.dao.ApprWorkDocDao;
import com.lgcns.ikep4.approval.work.model.ApprDocAuth;
import com.lgcns.ikep4.approval.work.model.ApprLine;
import com.lgcns.ikep4.approval.work.service.ApprDisplayService;
import com.lgcns.ikep4.approval.work.service.ApprEntrustService;
import com.lgcns.ikep4.approval.work.service.ApprExamService;
import com.lgcns.ikep4.approval.work.service.ApprLineService;
import com.lgcns.ikep4.approval.work.service.ApprWorkDocService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.group.dao.GroupDao;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * WorkPlace List Impl 
 * 
 * @author wonchu
 * @version $Id: ApprWorkDocServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("apprWorkDocServiceImpl")
@SuppressWarnings("unchecked")
public class ApprWorkDocServiceImpl extends GenericServiceImpl<ApprDoc, String> implements ApprWorkDocService{
	
	@Autowired
	private ApprWorkDocDao apprWorkDocDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private	ApprLineService	apprLineService;
	
	@Autowired
	private ApprDisplayService apprDisplayService;
	
	@Autowired
	private ApprExamService		apprExamService;
	
	@Autowired
	private ApprEntrustService		apprEntrustService;
	
	@Autowired
	private	GroupService	groupService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ApprLineDao	apprLineDao;

	
	/**
	 * 기안문 생성
	 * @param 	ApprDoc, user
	 * @return 	void
	 */
	public void createApprDoc(ApprDoc apprDoc, User user, HttpServletRequest request) {
		
		// apprId 생성
		apprDoc.setApprId(idgenService.getNextId());
		
		//첨부파일 업데이트
		if(apprDoc.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(apprDoc.getFileLinkList(), apprDoc.getApprId(),  CommonCode.ITEM_TYPE, user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(apprDoc.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(apprDoc.getEditorFileLinkList(), apprDoc.getApprId(), CommonCode.ITEM_TYPE, user);
		}

		// 참조자가 있을시 
		this.tranApprReference("add", apprDoc.getApprId(), apprDoc.getReferenceId());
			
		// 열람권한이  있을시
		this.tranApprRead("add", apprDoc.getApprId(), apprDoc.getReadId(), user.getUserId(), user.getUserName());
		
		// 기결재문서 첨부
		this.tranApprRelations("add", apprDoc.getApprId(), apprDoc.getApprRefId());
		
		
		// 문서메타
		apprWorkDocDao.create(apprDoc);
		
		// 문서내용
		apprWorkDocDao.createApprDocContent(apprDoc);
		
		
		ApprLine apprLine = new ApprLine();
		apprLine.setApprId(apprDoc.getApprId());
		
		//Default 결재선 등록 & 결재선 Flow 정보 등록
		if(!"".equals(apprDoc.getApprLine())){	
			apprLine.setApprLineList(getApprLine(apprDoc.getApprLine(), apprDoc.getApprId()));
		}
		
		// 수신처
		if(!"".equals(apprDoc.getApprReceiveLine())){	
			apprLine.setApprReceiveLineList(getApprLine(apprDoc.getApprReceiveLine(), apprDoc.getApprId()));
		}
		
		//결재선 지정, 수신처 지정 저장
		if(!"".equals(apprDoc.getApprLine()) || !"".equals(apprDoc.getApprReceiveLine())){
			apprLineService.create(apprLine,user);
		}
		
		// 결재요청시 결재선의 첫번째 결재자의 APPR_STATUS =1 진행으로 수정
		if(apprDoc.getApprDocStatus()==1) {
			
			/**
			 * 첫 결재자가 부서 합의인 경우 조회
			 */
			Map<String, String> nextUserMap = new HashMap<String, String>();
			nextUserMap.put("apprId", 	apprLine.getApprId());
			nextUserMap.put("apprOrder", 1+"");	
			List<ApprLine> nextApprLine=  apprLineDao.getNextApprLine(nextUserMap);

			/**
			 * 부서합의가 존재하면 문서 복제하기
			 */
			if(nextApprLine!=null && nextApprLine.size()>0){
				for(ApprLine apprLine1 : nextApprLine){
					duplicateApprDoc(apprLine1.getApprId(),apprLine1.getApproverType(),apprLine1.getApproverId(),apprLine1.getApproverGroupName(),apprLine1.getApprType());
				}
			}			
			
			// 첫번째 결재자 또는 결재 부서합의 결재선 진행으로 수정
			apprLine.setApprStatus(1);// 진행
			apprLine.setApprOrder(1);// 최초 결재자 진행처리
			apprLineService.update(apprLine);
			
			// 기안자와 첫번째 결재자가 동일한 경우 자동 결재처리
			ApprLine procApprLine	= new ApprLine();
			
			procApprLine.setApprId(apprDoc.getApprId());
			procApprLine.setApproverId(user.getUserId());
			procApprLine.setApprOrder(1);
			procApprLine.setApprType(0);// 수신제외
			procApprLine = apprLineService.readLine(procApprLine);
			
			if(procApprLine!=null && !procApprLine.equals("")) {
				if(procApprLine.getApprType()==0) { // 결재
					procApprLine.setApprMessage("승인합니다.");
					procApprLine.setApprStatus(2);
				} else {
					procApprLine.setApprMessage("합의합니다.");
					procApprLine.setApprStatus(4);
				}
				apprLineService.updateApproval(procApprLine,user,request);
				
				// 동일 apprOrder 의 결재자 존재유무
				Map<String, String> map = new HashMap<String, String>();
				map.put("apprId",		procApprLine.getApprId());
				map.put("apprOrder",	procApprLine.getApprOrder()+"");
				boolean	exists	=	apprLineService.exists(map);

				if(exists) {
					// 첫번째 결재자에게 알람 발송처리
					apprLineService.sendAlarm(user,apprDoc,request);					
				}
			} else {
			
				// 첫번째 결재자에게 알람 발송처리
				apprLineService.sendAlarm(user,apprDoc,request);
			}
		}		
		
	}
	
	/**
	 * 기안문 복사 ( 부서합의 ,수신처로 지정된 사용자/부서)
	 * @param 	폼DTO
	 * @return 	String
	 */
	public String duplicateApprDoc(String apprId,int approverType,String approverId,String approverGroupName,int apprType) {
		
		// apprId 생성
		String newApprId = idgenService.getNextId();
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 		apprId);
		map.put("newApprId", 	newApprId);
		
		
		ApprLine apprLine = new ApprLine();
		apprLine.setApprId(apprId);			
		apprLine.setApproverId(approverId);
		if(apprType==3) {
			apprLine.setApprType(apprType);
		} else {
			apprLine.setApprType(0);
		}
		apprLine = apprLineDao.readLine(apprLine);
		if(apprLine!=null){
			if( apprLine.getApprType() != 0) {
				map.put("apprType", 	apprLine.getApprType()+"" );
			}
		}
		
		// 부서합의/수신처의 결재유형이 그룹인경우
		if(approverType==1){
			map.put("apprGroupId", 	approverId);	// 부서 합의/수신 부서 코드
			map.put("apprGroupName", approverGroupName);
			map.put("approverId", approverId);		// 복사본의 그룹/사용자 결재선
			
		} else { //수신처가 사용자인 경우 
			User user = userDao.get(approverId);
			map.put("apprGroupId", 	user.getGroupId());	// 수신처 사용자의 부서코드
			map.put("apprGroupName", user.getTeamName());
			map.put("registerJobTitle", user.getJobTitleName());	// 수신처 사용자의 직위명
			map.put("approverId", approverId);			// 복사본의 그룹/사용자 결재선
			map.put("registerId", user.getUserId());
			map.put("registerName", user.getUserName());
			
		}
		// 문서메타
		apprWorkDocDao.duplicateApprDoc(map);
		
		// 문서내용
		apprWorkDocDao.duplicateApprDocContent(map);
		
		return newApprId;
	}
	
	/**
	 * 기안문 수정 ( 임시/ 회수 문서만 수정가능 )
	 * @param 	ApprDoc, User
	 * @return 	void
	 */
	public void updateApprDoc(ApprDoc apprDoc, User user, HttpServletRequest request) {
		
		//첨부파일 업데이트
		if(apprDoc.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(apprDoc.getFileLinkList(), apprDoc.getApprId(),  CommonCode.ITEM_TYPE, user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(apprDoc.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(apprDoc.getEditorFileLinkList(), apprDoc.getApprId(), CommonCode.ITEM_TYPE, user);
		}

		
		// 참조자가 있을시
		this.tranApprReference("edit", apprDoc.getApprId(), apprDoc.getReferenceId());
			
		// 열람권한이  있을시
		this.tranApprRead("edit", apprDoc.getApprId(), apprDoc.getReadId(), user.getUserId(), user.getUserName());
		
		// 기결재문서 첨부
		this.tranApprRelations("edit", apprDoc.getApprId(), apprDoc.getApprRefId());
		
		// 매타정보 갱신
		apprWorkDocDao.update(apprDoc);
		
		// 문서내용 버전 추가
		apprWorkDocDao.updateApprDocContent(apprDoc);
		
		
		ApprLine apprLine = new ApprLine();
		apprLine.setApprId(apprDoc.getApprId());
		
		//Default 결재선 등록 & 결재선 Flow 정보 등록
		if(!"".equals(apprDoc.getApprLine())){	
			apprLine.setApprLineList(getApprLine(apprDoc.getApprLine(), apprDoc.getApprId()));
		}
		
		// 수신처
		if(!"".equals(apprDoc.getApprReceiveLine())){	
			apprLine.setApprReceiveLineList(getApprLine(apprDoc.getApprReceiveLine(), apprDoc.getApprId()));
		}
		
		//결재선 지정, 수신처 지정 저장
		if(!"".equals(apprDoc.getApprLine()) || !"".equals(apprDoc.getApprReceiveLine())){
			apprLineService.create(apprLine,user);
		}
		
		// 결재요청시 결재선의 첫번째 결재자의 APPR_STATUS =1 진행으로 수정
		if(apprDoc.getApprDocStatus()==1) {
			
			/**
			 * 첫 결재자가 부서 합의인 경우 조회
			 */
			Map<String, String> nextUserMap = new HashMap<String, String>();
			nextUserMap.put("apprId", 	apprLine.getApprId());
			nextUserMap.put("apprOrder", 1+"");	
			List<ApprLine> nextApprLine=  apprLineDao.getNextApprLine(nextUserMap);

			/**
			 * 부서합의가 존재하면 문서 복제하기
			 */
			if(nextApprLine!=null && nextApprLine.size()>0){
				for(ApprLine apprLine1 : nextApprLine){
					duplicateApprDoc(apprLine1.getApprId(),apprLine1.getApproverType(),apprLine1.getApproverId(),apprLine1.getApproverGroupName(),apprLine1.getApprType());
				}
			}			
			
			// 첫번째 결재자 또는 결재 부서합의 결재선 진행으로 수정
			apprLine.setApprStatus(1);// 진행
			apprLine.setApprOrder(1);// 최초 결재자 진행처리
			apprLineService.update(apprLine);	
			
			
			// 기안자와 첫번째 결재자가 동일한 경우 자동 결재처리
			ApprLine procApprLine	= new ApprLine();
			
			procApprLine.setApprId(apprDoc.getApprId());
			procApprLine.setApproverId(user.getUserId());
			procApprLine.setApprOrder(1);
			procApprLine.setApprType(0);// 수신제외
			procApprLine = apprLineService.readLine(procApprLine);
			
			if(procApprLine!=null && !procApprLine.equals("")) {
				if(procApprLine.getApprType()==0) { // 결재
					procApprLine.setApprMessage("승인합니다.");
					procApprLine.setApprStatus(2);
				} else {
					procApprLine.setApprMessage("합의합니다.");
					procApprLine.setApprStatus(4);
				}
				apprLineService.updateApproval(procApprLine,user,request);
				
				// 동일 apprOrder 의 결재자 존재유무
				Map<String, String> map = new HashMap<String, String>();
				map.put("apprId",		procApprLine.getApprId());
				map.put("apprOrder",	procApprLine.getApprOrder()+"");
				boolean	exists	=	apprLineService.exists(map);

				if(exists) {
					// 첫번째 결재자에게 알람 발송처리
					apprLineService.sendAlarm(user,apprDoc,request);					
				}
			} else {
			
				// 첫번째 결재자에게 알람 발송처리
				apprLineService.sendAlarm(user,apprDoc,request);
			}
		}		
	}
	
	/**
	 * 기안문 수정
	 * @param 	ApprDoc, User
	 * @return 	void
	 */
	public void updateApprDocContent(ApprDoc apprDoc, User user) {
		
		//첨부파일 업데이트
		if(apprDoc.getFileLinkList() != null) {
			//파일 링크 업데이트를 한다.
			this.fileService.saveFileLink(apprDoc.getFileLinkList(), apprDoc.getApprId(),  CommonCode.ITEM_TYPE, user);
		}

		//CKEDITOR내에 첨부된 이미지 파일의 링크 정보를 생성한다.
		if(apprDoc.getEditorFileLinkList() != null) {
			this.fileService.saveFileLinkForEditor(apprDoc.getEditorFileLinkList(), apprDoc.getApprId(), CommonCode.ITEM_TYPE, user);
		}
		
		// 문서내용 버전 추가
		apprWorkDocDao.createApprDocContent(apprDoc);
	}
	
	/**
	 * 부서합의 / 수신처 문서 결재 요청
	 * @param 	ApprDoc, User
	 * @return 	void
	 */
	public void updateChildApprDoc(ApprDoc apprDoc, User user, HttpServletRequest request) {
		
		// 부서합의/수신처 문서는 결재만 존재함
		
		// 문서내용 버전 추가
		apprWorkDocDao.updateReceiverApprDocStatus(apprDoc);
		
		ApprLine apprLine = new ApprLine();
		apprLine.setApprId(apprDoc.getApprId());
		
		//Default 결재선 등록 & 결재선 Flow 정보 등록
		if(!"".equals(apprDoc.getApprLine())){	
			apprLine.setApprLineList(getApprLine(apprDoc.getApprLine(), apprDoc.getApprId()));
		}
		
		// 수신처
		if(!"".equals(apprDoc.getApprReceiveLine())){	
			apprLine.setApprReceiveLineList(getApprLine(apprDoc.getApprReceiveLine(), apprDoc.getApprId()));
		}
		
		//결재선 지정, 수신처 지정 저장
		if(!"".equals(apprDoc.getApprLine()) || !"".equals(apprDoc.getApprReceiveLine())){
			apprLineService.create(apprLine,user);
		}
		
		// 결재요청시 결재선의 첫번째 결재자의 APPR_STATUS =1 진행으로 수정
		if(apprDoc.getApprDocStatus()==1) {
			apprLine.setApprStatus(1);// 진행
			apprLine.setApprOrder(1);// 최초 결재자 진행처리
			apprLineService.update(apprLine);	
		}	
		
		// 기안자와 첫번째 결재자가 동일한 경우 자동 결재처리
		ApprLine procApprLine	= new ApprLine();
		
		procApprLine.setApprId(apprDoc.getApprId());
		procApprLine.setApproverId(user.getUserId());
		procApprLine.setApprOrder(1);
		procApprLine.setApprType(0);// 수신제외
		procApprLine = apprLineService.readLine(procApprLine);
		if(procApprLine!=null && !procApprLine.equals("")) {
			if(procApprLine.getApprType()==0) { // 결재
				procApprLine.setApprMessage("승인합니다.");
				procApprLine.setApprStatus(2);
			} else {
				procApprLine.setApprMessage("합의합니다.");
				procApprLine.setApprStatus(4);
			}
			apprLineService.updateApproval(procApprLine,user,request);
		}		
	}	
	/**
	 * 기안문 삭제
	 * @param 	apprId
	 * @return 	void
	 */
	public void deleteApprDoc(String apprId) {
		
		//첨부파일 삭제
		fileService.removeItemFile(apprId);
		
		// 결제라인, 수신처
		apprLineService.delete(apprId);
		
		// 참조자
		apprWorkDocDao.deleteApprReference(apprId);
		
		// 열람권한
		apprWorkDocDao.deleteApprRead(apprId);
		
		// 문서내용
		apprWorkDocDao.remove(apprId);
		
		// 매타정보 
		apprWorkDocDao.deleteApprDocContent(apprId);
		
	}
	
	/**
	 * apprDoc 기안문 상세
	 * @param 	Map
	 * @return 	ApprDoc
	 */
	public ApprDoc readApprDoc(Map<String, String> map) {
		
		ApprDoc apprDoc = apprWorkDocDao.readApprDoc(map);
		
		if(apprDoc!=null) {
			//첨부 파일 리스트를 가져와 게시물에 넣는다
			List<FileData> fileDataList = this.fileService.getItemFile(map.get("apprId").toString(), "N");
			apprDoc.setFileDataList(fileDataList);
		} 
		return apprDoc;
	}
	
	
	/**
	 * apprDoc Sub 기안문 상세
	 * @param 	Map
	 * @return 	ApprDoc
	 */
	public ApprDoc readApprDocSub(Map<String, String> map) {
		
		ApprDoc apprDoc = apprWorkDocDao.readApprDocSub(map);
		
		if(apprDoc!=null) {
			//첨부 파일 리스트를 가져와 게시물에 넣는다
			List<FileData> fileDataList = this.fileService.getItemFile(apprDoc.getApprId(), "N");
			apprDoc.setFileDataList(fileDataList);
		}
		return apprDoc;
	}	
	/**
	 * apprForm 열람권한 조회
	 * @param 	apprId
	 * @return 	List<ApprDoc>
	 */
	public List<ApprDoc> getApprDocHistoryList(String apprId) {

		return apprWorkDocDao.getApprDocHistoryList(apprId);
		
	}
	
	/**
	 * apprForm 열람권한 조회
	 * @param 	Map
	 * @return 	List<Group>
	 */
	public List<Group> getApprReadGroupList(Map<String, String> map) {
		
		map.put("readType",     "1");
		
		List <ApprDoc> readList = apprWorkDocDao.getApprReadList(map);
		List groupList = new ArrayList();
		
		for(int i=0;i<readList.size();i++){
			Group group = groupDao.get(readList.get(i).getReadId());
			if (!(map.get("locale").toString()).equals("ko")) {
				group.setGroupName(group.getGroupEnglishName());
				group.setGroupTypeName(group.getGroupTypeEnglishName());
			}
			groupList.add(group);
		}
		
		return groupList;
		
	}
	
	/**
	 * apprForm 열람권한 조회
	 * @param 	Map
	 * @return 	List<User>
	 */
	public List<User> getApprReadUserList(Map<String, String> map) {
		
		map.put("readType",     "0");
		
		List <ApprDoc> readList = apprWorkDocDao.getApprReadList(map);
		List userList = new ArrayList();
		
		Map<String, String> map2 = new HashMap<String, String>();
		for(int i=0;i<readList.size();i++){
			User user = new User();
			if(groupDao.exists(readList.get(i).getGroupId())){
				map2.put("userId", 		readList.get(i).getReadId());
				map2.put("groupId", 	readList.get(i).getGroupId());
				
				user = userDao.getUserByGroupId(map2);
			}else{
				user = userDao.get(readList.get(i).getReadId());
			}
			
			if (!(map.get("locale").toString()).equals("ko")) {
				user.setUserName(user.getUserEnglishName());
				user.setTeamName(user.getTeamEnglishName());
				user.setJobTitleName(user.getJobTitleEnglishName());
			}
			userList.add(user);
		}
		
		return userList;
		
	}
	
	/**
	 * apprForm 참조자 조회
	 * @param 	Map
	 * @return 	List<User>
	 */
	public List<User> getApprReferenceList(Map<String, String> map) {
		
		List <ApprDoc> referenceList = apprWorkDocDao.getApprReferenceList(map);
		List userList = new ArrayList();
		
		Map<String, String> map2 = new HashMap<String, String>();
		for(int i=0;i<referenceList.size();i++){
			User user = new User();
			if(groupDao.exists(referenceList.get(i).getGroupId())){
				map2.put("userId", 		referenceList.get(i).getUserId());
				map2.put("groupId", 	referenceList.get(i).getGroupId());
				
				user = userDao.getUserByGroupId(map2);
			}else{
				user = userDao.get(referenceList.get(i).getUserId());
			}
			
			if (!(map.get("locale").toString()).equals("ko")) {
				user.setUserName(user.getUserEnglishName());
				user.setTeamName(user.getTeamEnglishName());
				user.setJobTitleName(user.getJobTitleEnglishName());
			}
			userList.add(user);
		}
		
		return userList;
	}
	
	/**
	 * apprDoc 기결재 조회
	 * @param 	String
	 * @return 	List<ApprDoc>
	 */
	public List<ApprDoc> getApprRelationsList(String apprId){
		return apprWorkDocDao.getApprRelationsList(apprId);
	}
	
	/**
	 * 참조자 수정
	 * @param 	referenceId
	 * @return 	void
	 */
	public void tranApprReference(String mode, String apprId, String referenceId) {
		
		if("edit".equals(mode)){
			// 참조자 삭제
			apprWorkDocDao.deleteApprReference(apprId);
		}
		
		if(!"".equals(referenceId)){
			String [] referenceSet = StringUtils.split(referenceId, ";");
			for(int i=0; i<referenceSet.length; i++){
				Map<String, String> map = new HashMap<String, String>();
				String [] cols = StringUtils.split(referenceSet[i], ",");
				
				map.put("apprId", 		apprId);
				map.put("userId", 		cols[0]);
				map.put("groupId", 		cols[1]);
				
				apprWorkDocDao.createApprReference(map);
			}
		}
	}
	
	/**
	 * 열람권한 수정
	 * @param 	readId
	 * @return 	void
	 */
	public void tranApprRead(String mode, String apprId, String readId, String registerId, String registerName) {
		
		if("edit".equals(mode)){
			// 열람권한삭제
			apprWorkDocDao.deleteApprRead(apprId);
		}
		
		if(!"".equals(readId)){
			// 열람권한 삭제
			String [] readSet = StringUtils.split(readId, ";");
			for(int i=0; i<readSet.length; i++){
				
				log.debug(readSet[i]);
				String [] read = StringUtils.split(readSet[i], ",");
				Map<String, String> map = new HashMap<String, String>();
				map.put("apprId", 		apprId);
				map.put("readType", 	read[0]);
				map.put("readId", 		read[1]);
				map.put("groupId", 		read[2]);
				map.put("registerId", 	registerId);
				map.put("registerName", registerName);
				
				apprWorkDocDao.createApprRead(map);
			}
		}
	}
	
	/**
	 * 기결재 수정
	 * @param 	referenceId
	 * @return 	void
	 */
	public void tranApprRelations(String mode, String apprId, String apprRefId) {
		
		if("edit".equals(mode)){
			// 기결재 삭제
			apprWorkDocDao.deleteApprRelations(apprId);
		}
		
		if(!"".equals(apprRefId)){
			String [] apprRefSet = StringUtils.split(apprRefId, ",");
			for(int i=0; i<apprRefSet.length; i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("apprId", 		apprId);
				map.put("apprRefId", 	apprRefSet[i]);;
				
				apprWorkDocDao.createApprRelations(map);
			}
		}
	}
	
	
	/**
	 * 상태값 변경
	 * @param 	ApprDoc
	 * @return 	void
	 */
	public void updateApprDocStatus(ApprDoc apprDoc) {
		apprWorkDocDao.updateApprDocStatus(apprDoc);
	}
	
	/**
	 * 부서 수신 문서 접수자 처리(전결,내부결재진행)
	 * @param apprDoc
	 * @return 	void
	 */
	public void updateReceiverApprDocStatus(ApprDoc apprDoc) {
		apprWorkDocDao.updateReceiverApprDocStatus(apprDoc);
	}	
	
	public List<ApprLine> getApprLine(String json, String apprId){
		
		ObjectMapper mapper = new ObjectMapper();

		List<ApprLine>	apprLineList = new ArrayList<ApprLine>();
		
		DateFormat formatter ; 
        //Date date ;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

				JsonNode root = mapper.readTree(json);
	
				for(int i=0;i<root.size();i++){
					
					ApprLine item = new ApprLine();
					
					item.setApprId(apprId);
					if(root.get(i).has("id")) item.setApproverId(root.get(i).get("id").getTextValue());
					if(root.get(i).has("userName")) item.setApproverName(root.get(i).get("userName").getTextValue());
					if(root.get(i).has("teamName")) item.setApproverGroupName(root.get(i).get("teamName").getTextValue());
					if(root.get(i).has("jobTitleName")) item.setApproverJobTitle(root.get(i).get("jobTitleName").getTextValue());
					if(root.get(i).has("apprType")) item.setApprType(root.get(i).get("apprType").getIntValue());
					if(root.get(i).has("approverType")) item.setApproverType(root.get(i).get("approverType").getIntValue());
					if(root.get(i).has("apprOrder")) item.setApprOrder(root.get(i).get("apprOrder").getIntValue());
					if(root.get(i).has("apprStatus")) item.setApprStatus(root.get(i).get("apprStatus").getIntValue());
					if(root.get(i).has("lineModifyAuth")) item.setLineModifyAuth(root.get(i).get("lineModifyAuth").getIntValue());
					if(root.get(i).has("docModifyAuth")) item.setDocModifyAuth(root.get(i).get("docModifyAuth").getIntValue());
					if(root.get(i).has("readModifyAuth")) item.setReadModifyAuth(root.get(i).get("readModifyAuth").getIntValue());
					if(root.get(i).has("apprDate") && !"".equals(root.get(i).get("apprDate").getTextValue())) item.setApprDate((Date)formatter.parse(root.get(i).get("apprDate").getTextValue()));
					apprLineList.add(item);
				}
		
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return apprLineList;
	}

	public List<ApprDoc> subList(Map<String, String> map) {
		return apprWorkDocDao.subList(map);
	}
	
	/**
	 * 기안문 원본 + Sub 문서 목록
	 * @param map
	 * @return
	 */
	public List<ApprDoc> listApprDoc(Map<String, String> map) {

		return apprWorkDocDao.listApprDoc(map);
		
	}	
	
	/**
	 * 겹직시 해당 그룹아이디를 가지고 있는지 여부
	 * @param String, String
	 * @return boolean
	 */
	public boolean hasGroupId(String userId, String groupId) {

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("userId", userId);
		List <Group> groupList = groupService.selectUserGroup(groupMap);
		boolean result = false;
		
		for(int i=0;i<groupList.size();i++){
			if(groupId.equals(groupList.get(i).getGroupId())){
				result = true;
				break;
			}
		}
		
		return result;
		
	}	
	
	/**
	 * apprDoc  문서조회 가능여부
	 * @param 	ApprDoc
	 * @return 	ApprDocAuth
	 */
	public ApprDocAuth setApprDocAuth(String apprId, String listType, User user, String entrustUserId, boolean isSystemAdmin, boolean isReadAll) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());
		
		// apprDoc
		ApprDoc apprDoc = this.readApprDoc(map);
		apprDoc.setListType(listType);
		apprDoc.setUserId(user.getUserId());
		
		/* to do 위임자 체크 */
		if(entrustUserId!=null && !"".equals(entrustUserId)){
			apprDoc.setEntrustUserId(entrustUserId);
		}
		
		// 결재자의 결재 문서 Read 정보 Update
		ApprLine	apprLine =	new ApprLine();
			
		apprLine.setApprId(apprId);
		if(entrustUserId!=null && !entrustUserId.equals("")){
			apprLine.setApproverId(entrustUserId);
		} else {
			apprLine.setApproverId(user.getUserId());
		}
		apprLine = apprLineService.readLine(apprLine);
		
		return this.setApprDocAuth(apprDoc, apprLine, user, isSystemAdmin, isReadAll);
	}
	
	
	/**
	 * apprDoc  문서권한
	 * @param 	ApprDoc
	 * @return 	ApprDocAuth
	 */
	public ApprDocAuth setApprDocAuth(ApprDoc apprDoc, ApprLine apprLine, User user, boolean isSystemAdmin, boolean isReadAll) {
		
		ApprDocAuth apprDocAuth = new ApprDocAuth(apprDoc);
		
		String returnUrl = "";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprDoc.getApprId());
		map.put("userId", 	user.getUserId());
		
		/*
		 * 기안문서 > 내가 기안한 문서
		 */
		if("myRequestList".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprMyRequest.do";
			
			// 기안자 여부
			if(apprDocAuth.isRegister()){
				apprDocAuth.setHasReadAuth(true);
				
				// 결재 회수 가능여부
				boolean canInitApprDocStatus = false;
				
				// 진행중 상태에서만 결재 회수 가능
				if(apprDoc.getApprDocStatus()==1){
					canInitApprDocStatus = !apprLineService.beforeAppr(apprDoc.getApprId());
				}
				apprDocAuth.setCanInitApprDocStatus(canInitApprDocStatus);
				
				// 공람지정 버튼
				boolean useDisplayButton = false;
				if(apprDocAuth.isRegister()){
					int userStatusCnt = apprDisplayService.countByDisplayUserStatus(apprDoc.getApprId());
					if(userStatusCnt == 0) useDisplayButton = true;
				}
				apprDocAuth.setUseDisplayButton(useDisplayButton);
			}
		/*
		 * 기안문서 > 임시저장함
		 */
		}else if("tempList".equals(apprDocAuth.getListType())){           
			returnUrl = "work/apprlist/listApprTemp.do";
			
			// 기안자 여부
			apprDocAuth.setHasReadAuth(apprDocAuth.isRegister());
		
		/*
		 * 기안문서 > 회수/반려함
		 */
		}else if("rejectList".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprReject.do";
			
			// 기안자 여부
			apprDocAuth.setHasReadAuth(apprDocAuth.isRegister());

		/*
		 * 결재문서 > 결재해야할 문서
		 */
		}else if("listApprTodo".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprTodo.do";
			
			// 결재자 
			if(apprLine !=null && apprLine.getApprType()==0){
				apprDocAuth.setApprover(true);
				apprDocAuth.setHasReadAuth(true);
				
			// 합의자
			} else if(apprLine !=null && ( apprLine.getApprType()==1 || apprLine.getApprType()==2) ){
				apprDocAuth.setAgreer(true);
				apprDocAuth.setHasReadAuth(true);

			} else{
				return apprDocAuth;
			}
			
			/** 복사된 문서가 부서합의/수신처 확인 **/
			if(apprDocAuth.getParentApprId()!=null) {
				ApprLine	apprLine1 =	new ApprLine();
				
				apprLine1.setApprId(apprDoc.getParentApprId());
				apprLine1.setApproverId(apprDoc.getApprGroupId());
				apprLine1 = apprLineService.readLine(apprLine1);
				
				if(apprLine1!=null) {
					apprDocAuth.setParentApprType(apprLine1.getApprType()==3?3:1);
				}
			}
			
			if(apprLine !=null){
				// 현재 사용자의 결재여부
				apprDocAuth.setApprStatus(apprLine.getApprStatus());
				// 결재선 변경권한
				apprDocAuth.setLineModifyAuth(apprLine.getLineModifyAuth());
				// 문서 변경권한
				apprDocAuth.setDocModifyAuth(apprLine.getDocModifyAuth());
				// 참조자,열람권자 변경 권한
				apprDocAuth.setReadModifyAuth(apprLine.getReadModifyAuth());
			}else{
				apprDocAuth.setLineModifyAuth(0);
				apprDocAuth.setDocModifyAuth(0);
				apprDocAuth.setReadModifyAuth(0);
			}
		
		/*
		 * 결재문서 > 결재한 문서
		 */
		}else if("listApprComplete".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprComplete.do";

			// 결재자 / 합의자 Setting 
			if(apprLine !=null && apprLine.getApprType()==0){
				apprDocAuth.setApprover(true);
				apprDocAuth.setHasReadAuth(true);
			} else if(apprLine !=null && ( apprLine.getApprType()==1 || apprLine.getApprType()==2) ){
				apprDocAuth.setAgreer(true);
				apprDocAuth.setHasReadAuth(true);
			} else{
				return apprDocAuth;
			}
			
			if(apprLine !=null){
				apprDocAuth.setApprStatus(apprLine.getApprStatus());
			}
			
			// 결재 회수 가능여부
			boolean canInitApprDocStatus = false;
			if(apprDocAuth.isRegister() && apprDoc.getApprDocStatus()==1){
				canInitApprDocStatus = !apprLineService.beforeAppr(apprDoc.getApprId());
			}
			apprDocAuth.setCanInitApprDocStatus(canInitApprDocStatus);
			
			if(apprDoc.getEntrustUserId()!=null && !"".equals(apprDoc.getEntrustUserId())) {
				map.put("approverId", 	apprDoc.getEntrustUserId());
			} else {
				map.put("approverId", 	user.getUserId());
			}
			apprDocAuth.setCanCancelApprDocStatus(!apprLineService.existsNextApprProgress(map));

			/** 복사된 문서가 부서합의/수신처 확인 **/
			if(apprDocAuth.getParentApprId()!=null) {
				ApprLine	apprLine1 =	new ApprLine();
				
				apprLine1.setApprId(apprDoc.getParentApprId());
				apprLine1.setApproverId(apprDoc.getApprGroupId());
				apprLine1 = apprLineService.readLine(apprLine1);
				
				if(apprLine1!=null) {
					apprDocAuth.setParentApprType(apprLine1.getApprType()==3?3:1);
				}
			}			
			
		/*
		 * 결재문서 > 검토요청
		 */
		}else if("listApprRequestExam".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprRequestExam.do";
			
			// 검토자 여부
			if(apprExamService.existExamUser(map)){
				
				apprDocAuth.setHasReadAuth(true);
				
				//검토자여부
				apprDocAuth.setExamUser(true);
				
				// 진행중인 경우만 검토의견 입력가능
				if(apprDoc.getApprDocStatus()==1) {
					//검토요청 권한 부여 여부
					apprDocAuth.setExamIsRequest(apprExamService.existExamIsRequest(map));
				}else {
					apprDocAuth.setExamIsRequest(false);
				}
			}

		/*
		 * 수신문서 > 부서수신문서
		 */
		}else if("listApprDeptRec".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprDeptRec.do";
			
			if(apprDocAuth.getParentApprId()!=null && this.hasGroupId(user.getUserId(), apprDoc.getApprGroupId())){
				apprDocAuth.setHasReadAuth(true);
			}else{
				return apprDocAuth;
			}
			
			// 결재 회수 가능여부
			boolean canInitApprDocStatus = false;
			if(apprDocAuth.isRegister() && apprDoc.getApprDocStatus()==1){
				canInitApprDocStatus = !apprLineService.beforeAppr(apprDoc.getApprId());
			}
			apprDocAuth.setCanInitApprDocStatus(canInitApprDocStatus);
			
			/** 복사된 문서가 부서합의/수신처 확인 **/
			if(apprDocAuth.getParentApprId()!=null) {
				ApprLine	apprLine1 =	new ApprLine();
				
				apprLine1.setApprId(apprDoc.getParentApprId());
				apprLine1.setApproverId(apprDoc.getApprGroupId());
				apprLine1 = apprLineService.readLine(apprLine1);
				
				if(apprLine1!=null) {
					apprDocAuth.setParentApprType(apprLine1.getApprType()==3?3:1);
				}
			}
			if(apprLine !=null){
				// 현재 사용자의 결재여부
				apprDocAuth.setApprStatus(apprLine.getApprStatus());
				// 결재선 변경권한
				apprDocAuth.setLineModifyAuth(apprLine.getLineModifyAuth());
				// 문서 변경권한
				apprDocAuth.setDocModifyAuth(apprLine.getDocModifyAuth());
				// 참조자,열람권자 변경 권한
				apprDocAuth.setReadModifyAuth(apprLine.getReadModifyAuth());
			}else{
				apprDocAuth.setLineModifyAuth(0);
				apprDocAuth.setDocModifyAuth(0);
				apprDocAuth.setReadModifyAuth(0);
			}			
			
		/*
		 * 수신문서 > 개인수신문서	
		 */
		}else if("listApprUserRec".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprUserRec.do";
			
			if(apprDocAuth.getParentApprId()!=null && apprLine !=null && apprLine.getApprType()==0){
				apprDocAuth.setHasReadAuth(true);
			}else{
				return apprDocAuth;
			}
			
			/** 복사된 문서가 부서합의/수신처 확인 **/
			if(apprDocAuth.getParentApprId()!=null) {
				ApprLine	apprLine1 =	new ApprLine();
				
				apprLine1.setApprId(apprDoc.getParentApprId());
				apprLine1.setApproverId(apprDoc.getApprGroupId());
				apprLine1 = apprLineService.readLine(apprLine1);
				
				if(apprLine1!=null) {
					apprDocAuth.setParentApprType(apprLine1.getApprType()==3?3:1);
				}
			}	
			if(apprLine !=null){
				// 현재 사용자의 결재여부
				apprDocAuth.setApprStatus(apprLine.getApprStatus());
				// 결재선 변경권한
				apprDocAuth.setLineModifyAuth(apprLine.getLineModifyAuth());
				// 문서 변경권한
				apprDocAuth.setDocModifyAuth(apprLine.getDocModifyAuth());
				// 참조자,열람권자 변경 권한
				apprDocAuth.setReadModifyAuth(apprLine.getReadModifyAuth());
			}else{
				apprDocAuth.setLineModifyAuth(0);
				apprDocAuth.setDocModifyAuth(0);
				apprDocAuth.setReadModifyAuth(0);
			}			
		
		// 공람문서 - 공람대기
		}else if("listApprDisplayWaiting".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprDisplay/listApprDisplayWaiting.do";
			
			// 공람 대상자 여부
			if(apprDisplayService.existDisplayUser(map)){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setDisplayUser(true);
				
				// 완료된 문서만 공람가능 
				if(apprDoc.getApprDocStatus()==2) {
					// 공람지정 문서여부
					apprDocAuth.setUseDisplayDoc(apprDisplayService.existDisplayDoc(map));
				}else {
					// 공람지정 문서여부
					apprDocAuth.setUseDisplayDoc(false);
				}
			}
		
		// 공람문서 - 공람완료
		}else if("listApprDisplayComplete".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprDisplay/listApprDisplayComplete.do";
			
			// 공람 대상자 여부
			if(apprDisplayService.existDisplayUser(map)){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setDisplayUser(true);
				
				// 완료된 문서만 공람가능 
				if(apprDoc.getApprDocStatus()==2) {
					// 공람지정 문서여부
					apprDocAuth.setUseDisplayDoc(apprDisplayService.existDisplayDoc(map));
				}else {
					// 공람지정 문서여부
					apprDocAuth.setUseDisplayDoc(false);
				}
			}
		
		/*
		 * 참조문서 - 참조문서
		 */
		}else if("listApprReference".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprReference.do";
			
			// 참조자 여부
			if(apprWorkDocDao.getApprReferenceCount(map)){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setReferencer(true);
			}
		
		/*
		 * 참조문서 - 열람획득문서
		 */
		}else if("listApprReading".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprReading.do";
			
			// 열람권자 여부
			if(apprWorkDocDao.getApprReadCount(map)){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setReader(true);
			}
		
		/*
		 * 참조문서 > 열람부여문서
		 */
		}else if("listApprReadingAssign".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprReadingAssign.do";
			
			// 기안자 여부
			apprDocAuth.setHasReadAuth(apprDocAuth.isRegister());
		
		/*
		 * 참조문서 - 위임문서
		 */
		}else if("listApprDelegate".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprDelegate.do";
			
			if(apprLine !=null && (apprLine.getApprType()==0 || apprLine.getApprType()==1 || apprLine.getApprType()==2)){
				apprDocAuth.setHasReadAuth(true);
			} else{
				return apprDocAuth;
			}
		
		/*
		 * 완료문서 > 전체문서, 기결제 첨부
		 */
		}else if("listApprIntegrate".equals(apprDocAuth.getListType()) || "listApprUserDoc".equals(apprDocAuth.getListType()) || "viewRelationDoc".equals(apprDocAuth.getListType())){
			
			returnUrl = "listApprIntegrate".equals(apprDocAuth.getListType())?"work/apprlist/readApprAllList.do":"work/userdoc/listApprUserDoc.do?folderId="+apprDoc.getFolderId();
			
			if(	// 기안자 여부	
				apprDocAuth.isRegister() ||
				
				// 결재자, 합의자 여부 체크
				(apprLine !=null && (apprLine.getApprType()==0 || apprLine.getApprType()==1 || apprLine.getApprType()==2)) ||
				
				// 부서 수신, 합의
				//(apprDocAuth.getParentApprId()!=null && user.getGroupId().equals(apprDoc.getApprGroupId())) ||
				// 동일 부서 문서 
				this.hasGroupId(user.getUserId(), apprDoc.getApprGroupId()) ||
				
				// 참조자 여부
				apprWorkDocDao.getApprReferenceCount(map) ||
				
				// 열람권자
				apprWorkDocDao.getApprReadCount(map) ||
				
				// 검토한 문서
				apprExamService.existExamStatus(map)
			){
				apprDocAuth.setHasReadAuth(true);
				
				// 기결제 첨부 문서는 버튼이 없음
				if("viewRelationDoc".equals(apprDocAuth.getListType())) return apprDocAuth;
				
				// 공람지정 버튼
				boolean useDisplayButton = false;
				if(apprDocAuth.isRegister()){
					int userStatusCnt = apprDisplayService.countByDisplayUserStatus(apprDoc.getApprId());
					if(userStatusCnt == 0) useDisplayButton = true;
				}
				apprDocAuth.setUseDisplayButton(useDisplayButton);
				
				/** 복사된 문서가 부서합의/수신처 확인 **/
				if(apprDocAuth.getParentApprId()!=null) {
					ApprLine	apprLine1 =	new ApprLine();
					
					apprLine1.setApprId(apprDoc.getParentApprId());
					apprLine1.setApproverId(apprDoc.getApprGroupId());
					apprLine1 = apprLineService.readLine(apprLine1);
					
					if(apprLine1!=null) {
						apprDocAuth.setParentApprType(apprLine1.getApprType()==3?3:1);
					}
				}
				
			}
		/*
		 * 완료문서 > 부서결재 문서
		 */
		}else if("listApprDeptIntegrate".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprDeptIntegrate.do";
			if(this.hasGroupId(user.getUserId(), apprDoc.getApprGroupId())){
				apprDocAuth.setHasReadAuth(true);
			}else{
				return apprDocAuth;
			}
			
			// 공람지정 버튼
			boolean useDisplayButton = false;
			if(apprDocAuth.isRegister()){
				int userStatusCnt = apprDisplayService.countByDisplayUserStatus(apprDoc.getApprId());
				if(userStatusCnt == 0) useDisplayButton = true;
			}
			apprDocAuth.setUseDisplayButton(useDisplayButton);
			
			/** 복사된 문서가 부서합의/수신처 확인 **/
			if(apprDocAuth.getParentApprId()!=null) {
				ApprLine	apprLine1 =	new ApprLine();
				
				apprLine1.setApprId(apprDoc.getParentApprId());
				apprLine1.setApproverId(apprDoc.getApprGroupId());
				apprLine1 = apprLineService.readLine(apprLine1);
				
				if(apprLine1!=null) {
					apprDocAuth.setParentApprType(apprLine1.getApprType()==3?3:1);
				}
			}
		
		/*
		 * 완료문서 > 기안한문서
		 */
		}else if("myRequestListComplete".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprMyRequestComplete.do";
			
			// 기안자 여부
			if(apprDocAuth.isRegister()){
				apprDocAuth.setHasReadAuth(true);
				
				// 공람지정 버튼
				boolean useDisplayButton = false;
				if(apprDocAuth.isRegister()){
					int userStatusCnt = apprDisplayService.countByDisplayUserStatus(apprDoc.getApprId());
					if(userStatusCnt == 0) useDisplayButton = true;
				}
				apprDocAuth.setUseDisplayButton(useDisplayButton);
			}
		
		/*
		 * 완료문서 > 결재, 합의, 참조 문서
		 */
		}else if("listCompleteAppr".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listCompleteAppr.do";
			
			// 결재자 
			if(apprLine !=null && apprLine.getApprType()==0){
				apprDocAuth.setApprover(true);
				apprDocAuth.setHasReadAuth(true);
			}else if(apprLine !=null && ( apprLine.getApprType()==1 || apprLine.getApprType()==2) ){
				apprDocAuth.setAgreer(true);
				apprDocAuth.setHasReadAuth(true);
			}else if(apprWorkDocDao.getApprReferenceCount(map)){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setReferencer(true);
			}

		/*
		 * 완료문서 > 합의한문서
		 */
		}else if("listApprAgreement".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprAgreement.do";
			
			if(apprLine !=null && ( apprLine.getApprType()==1 || apprLine.getApprType()==2) ){
				apprDocAuth.setAgreer(true);
				apprDocAuth.setHasReadAuth(true);
			}
			
		/*
		 * 완료문서 > 참조한 문서
		 */
		}else if("listApprCompleteRef".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprCompleteRef.do";
			
			// 참조자 여부
			if(apprWorkDocDao.getApprReferenceCount(map)){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setReferencer(true);
			}
		
		/*
		 * 완료문서 > 검토한문서
		 */
		}else if("listApprCompleteExam".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprCompleteExam.do";
			
			//검토한 문서인지 확인
			if(apprDoc.getApprDocStatus()==2) {
				if(apprExamService.existExamStatus(map)){
					apprDocAuth.setHasReadAuth(true);
					apprDocAuth.setExamStatus(true);
				}
			}
		/*
		 * 완료문서 > 전체결재문서
		 */
		}else if("listApprDocAllUser".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprDocAllUser.do";
			
			if(isReadAll || isSystemAdmin){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setViwer(true);
			}
			
		/*
		 * ADMIN > 결재문서 보안관리
		 */
		}else if("listApprDocSecurity".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprDocSecurity.do";	
			
			if(isSystemAdmin){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setAuther(true);
			}
			
		/*
		 * ADMIN > 결재문서관리
		 */
		}else if("listApprDocAllAdmin".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprDocAllAdmin.do";
			
			if(isSystemAdmin){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setAuther(true);
			}
		
		/*
		 * 환경설정 > 수임현황
		 */	
		}else if("listApprEntrust".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprEntrust.do";
			
			if(apprDoc.getEntrustUserId()!=null && !"".equals(apprDoc.getEntrustUserId())) {
				if(apprDoc.getEntrustType().equals("E")) {
					map.put("userId", 			apprDoc.getEntrustUserId());
					map.put("entrustUserId", 	user.getUserId());
				}else {
					map.put("userId", 			user.getUserId());
					map.put("entrustUserId", 	apprDoc.getEntrustUserId());
				}
				if(apprEntrustService.hasSignUser(map)){
					apprDocAuth.setHasSignUser(true);
					apprDocAuth.setHasReadAuth(true);
				}
			}
		/*
		 * 결재 > 검토한 문서
		 */	
		}else if("listApprExam".equals(apprDocAuth.getListType())){
			returnUrl = "work/apprlist/listApprExam.do";
			
			//검토한 문서인지 확인
			if(apprExamService.existExamStatus(map)){
				apprDocAuth.setHasReadAuth(true);
				apprDocAuth.setExamStatus(true);
			}
		}
		
		apprDocAuth.setReturnUrl(returnUrl);
		
		return apprDocAuth;
	}

}
