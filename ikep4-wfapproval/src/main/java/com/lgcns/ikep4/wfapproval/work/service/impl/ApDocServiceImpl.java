/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.wfapproval.work.dao.ApDocDao;
import com.lgcns.ikep4.wfapproval.work.dao.ApReceiveDao;
import com.lgcns.ikep4.wfapproval.work.model.ApDoc;
import com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet;
import com.lgcns.ikep4.wfapproval.work.model.ApReceive;
import com.lgcns.ikep4.wfapproval.work.service.ApDocService;
import com.lgcns.ikep4.wfapproval.work.service.ApFormPortletService;
import com.lgcns.ikep4.workflow.engine.model.InstanceBean;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;


/**
 * 품위서작성관리  Service 구현
 * 
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApDocServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apdocService")
public class ApDocServiceImpl extends GenericServiceImpl<ApDoc, Integer> implements ApDocService {

	@Autowired
	private ApDocDao apDocDao;
	
	@Autowired
	private ApReceiveDao apReceiveDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private ApFormPortletService apformportletService;

	/*
	 * 품위서 등록 (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.work.service.ApDocService#create()
	 */
	public Integer create(ApDoc object) {
		
		ApFormPortlet apFormPortlet = new ApFormPortlet();
		apFormPortlet.setFormId(object.getFormId());
		apFormPortlet.setUserId(object.getRegistUserId());
		
		//결재양식 포틀렛 관리
		apformportletService.addApFormPortlet(apFormPortlet);
		
		
		String apprId = idgenService.getNextId();
		//양식ID
		
		object.setApprId(apprId);
		//양식ID 설정
				
		ApReceive apReceive = new ApReceive();
		apReceive.setApprId(apprId);
		
		//전체 paramHsh
		Map<String, Object> paramHsh	= new HashMap<String, Object>();
		//배열 paramHsh
		Map<String, Object> arrayHsh	= new HashMap<String, Object>();
		Map<String, Object> arrayAgreeHsh	= new HashMap<String, Object>();
		
		//수신자 리스트				
		List<String> workerList = object.getWorkerList();
		if ( workerList != null)
		{	
			for(int i=0; i<workerList.size(); i++) {
				//INSERT IKEP_AP_RECEIVE
				apReceive.setReceiveId(workerList.get(i));
				apReceiveDao.create(apReceive);
				arrayHsh.put(Integer.toString(i), workerList.get(i));
			}
			//수신자 List
			paramHsh.put("receiveList",arrayHsh);
			object.setIsApprReceive("Y");
			
		}else{
			object.setIsApprReceive("N");
		}
		
		arrayHsh	= new HashMap<String, Object>();
		//열람권한자
		List<String> workerList1 = object.getWorkerList1();
		if ( workerList1 != null)
		{
			for(int i=0; i<workerList1.size(); i++) {
				//INSERT IKEP_AP_AUTH_USER
				apReceive.setReceiveId(workerList1.get(i));
				apReceiveDao.createAuthUser(apReceive);
				arrayHsh.put(Integer.toString(i), workerList1.get(i));
			}
			paramHsh.put("referenceList",arrayHsh);
		}

		//기결제참조첨부
		List<String> workerList3 = object.getWorkerList3();
		if ( workerList3 != null)
		{
			for(int i=0; i<workerList3.size(); i++) {
				//INSERT IKEP_AP_RELATIONS
				apReceive.setApprRefId(workerList3.get(i));
				apReceiveDao.createRelations(apReceive);
			}
			
		}
				
		//첨부파일 업데이트
		if(object.getFileLinkList() != null) {	
			this.saveFileLink(object.getFileLinkList(), apprId, "Approval");
		} 
				
		arrayHsh	= new HashMap<String, Object>();
		
		//결재선 저장
		List<String> workerList2 = object.getWorkerList2();
		String tempList="";
		String agreeType = object.getApprLineType();
		int approvalcnt=0; int agreecnt=0;
		if ( workerList2 != null)
		{	
			for(int i=0; i<workerList2.size(); i++) {
				//INSERT IKEP_AP_PROCESS
				tempList =workerList2.get(i);
				
				if (i == 0){
					object.setApprUserId(object.getRegistUserId());
					object.setApprType("0");
					object.setApprOrder(Integer.toString(i));
					if ( object.getApprDocState().equals("0")){
						object.setApprState("TEMP");
					}else{
						object.setApprState("COMPLETE");
					}
					arrayHsh.put(Integer.toString(i), object.getRegistUserId());
					approvalcnt++;
					
					apDocDao.createApProcess(object);

					object.setApprUserId(tempList.substring(2));
					object.setApprType(tempList.substring(0,1));
					//object.setApprOrder(Integer.toString(i+1));
					if ( object.getApprDocState().equals("0")){
						object.setApprState("TEMP");
					}else{
						object.setApprState("ASSIGN");
					}
					object.setApprMessage("");					
					
				}else{
					object.setApprUserId(tempList.substring(2));
					object.setApprType(tempList.substring(0,1));
					//object.setApprOrder(Integer.toString(i+1));
					if ( object.getApprDocState().equals("0")){
						object.setApprState("TEMP");
					}else{
						object.setApprState("ASSIGN");
					}
					object.setApprMessage("");

				}
				
				
				if (agreeType.equals("S"))//직렬
				{
					if ( tempList.substring(0,1).equals("0")) //결재
					{	
						arrayHsh.put(Integer.toString(approvalcnt), tempList.substring(2));
						approvalcnt++;
					}else{
						//합의
						arrayHsh.put(Integer.toString(approvalcnt), tempList.substring(2));
						approvalcnt++;
					}
					object.setApprOrder(Integer.toString(i+1));
				}else{//병렬일때
					if ( tempList.substring(0,1).equals("0")) //결재
					{	object.setApprOrder(Integer.toString(approvalcnt));
						arrayHsh.put(Integer.toString(approvalcnt), tempList.substring(2));
						approvalcnt++;
					}else{
						//합의
						object.setApprOrder(Integer.toString(agreecnt+1));
						arrayAgreeHsh.put(Integer.toString(agreecnt), tempList.substring(2));
						agreecnt++;
					}						
				}
				
				apDocDao.createApProcess(object);
			}
			paramHsh.put("approvalList",arrayHsh);
			paramHsh.put("agreeList",arrayAgreeHsh);
		}
		
		
		String processId=object.getProcessId();
		String title=object.getApprTitle();
		
		String userId = object.getRegistUserId();

		paramHsh.put("apprId",apprId);
		
		//문서종류
		if (object.getApprTypeCd().equals("100000012264") )   //문서종류  품의
			paramHsh.put("docType","TYPE01");
		else if (object.getApprTypeCd().equals("100000012265")) //문서종류 보고		
			paramHsh.put("docType","TYPE02");
		else if (object.getApprTypeCd().equals("100000678725")) //문서종류 통신
			paramHsh.put("docType","TYPE03");
		
		
		//보안등급
		if (object.getApprSecurityCd().equals("100000012271") )   //보안등급이 기밀
			paramHsh.put("securityLevel","SECURITY");
		else if (object.getApprSecurityCd().equals("100000012267")) //보안등급이 일반
			paramHsh.put("securityLevel","NORMAL");
		
		//보존기간
		if (object.getApprPeriodCd().equals("100000012253") )   //보존기간  1년
			paramHsh.put("keepDeadLine","1");
		else if (object.getApprPeriodCd().equals("100000012256")) //보존기간 2년
			paramHsh.put("keepDeadLine","2");
		else if (object.getApprPeriodCd().equals("100000012260")) //보존기간 3년
			paramHsh.put("keepDeadLine","3");
		else if (object.getApprPeriodCd().equals("100000012257")) //보존기간 5년
			paramHsh.put("keepDeadLine","5");
		
		
		//결재요청시 통보
		if (object.getMailReqCd().equals("100000728672") )   //전체 결재자 미통보
			paramHsh.put("stepNotiType","NO");
		else if (object.getMailReqCd().equals("100000728674")) //기안자 통보
			paramHsh.put("stepNotiType","DRAFT");
		else if (object.getMailReqCd().equals("100000729076")) //기안자 / 다음 결재자 통보
			paramHsh.put("stepNotiType","STEP");
		else if (object.getMailReqCd().equals("100000729078")) //전체 결재자 통보
			paramHsh.put("stepNotiType","ALL");
		
		
		//결재완료시 통보
		if (object.getMailEndCd().equals("100000729178") )   //전체 결재자 미통보
			paramHsh.put("EndNotiType","NO");
		else if (object.getMailEndCd().equals("100000729179")) //기안자 통보
			paramHsh.put("EndNotiType","DRAFT");
		else if (object.getMailEndCd().equals("100000012231")) //전체 결재자 통보
			paramHsh.put("EndNotiType","ALL");	
		

		//결재요청시 통보방법
		if (object.getMailReqWayCd().equals("100000012232") )   //메일발송
			paramHsh.put("stepNotiMethod","EMAIL");
		else if (object.getMailReqWayCd().equals("100000012234")) //문자발송
			paramHsh.put("stepNotiMethod","SMS");
		else if (object.getMailReqWayCd().equals("100000012235")) //문자/메일 발송
			paramHsh.put("stepNotiMethod","ALL");

		//결재완료시 통보방법
		if (object.getMailEndWayCd().equals("100000012241") )   //메일발송
			paramHsh.put("EndNotiMethod","EMAIL");
		else if (object.getMailEndWayCd().equals("100000012243")) //문자발송
			paramHsh.put("EndNotiMethod","SMS");
		else if (object.getMailEndWayCd().equals("100000729177")) //문자/메일 발송
			paramHsh.put("EndNotiMethod","ALL");

		
		
		if (agreeType.equals("S")) {
			paramHsh.put("draftType","APP");
		}else{
			paramHsh.put("draftType","ALL");
		}
				
		if ( object.getApprDocState().equals("1"))//임시저장일때는 호출하지 않음.
		{
			InstanceBean instanceBean = new InstanceBean();
			instanceBean = instanceService.startProcess(processId, title, userId, paramHsh);
		
			paramHsh.put("approvalYN","Y");
			paramHsh.put("approvalList_Key","1");
			if ( agreecnt > 0){
				paramHsh.put("agreeList_Key","0");
			}
			
			instanceService.completeWorkItem(instanceBean.getInstanceId(),"DRAFT",instanceBean.getInstanceLogId(), userId, paramHsh);
			object.setInstanceId(instanceBean.getInstanceId());
		}
				
		return apDocDao.create(object);
	}

	/*
	 * 사용자정의 등록 (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.work.service.ApDocService#create()
	 */
	public Integer createApUser(ApDoc object) {
		String processId = idgenService.getNextId();
		//양식ID
		
		object.setProcessId(processId);
		//양식ID 설정
		String tempList="";
		List<String> workerList = object.getWorkerList();
		if ( workerList != null)
		{
			for(int i=0; i<workerList.size(); i++) {
				//INSERT IKEP4_AP_USER_ACTIVITY		
				tempList =workerList.get(i);
				object.setProcessId(processId);
				object.setApprUserId(tempList.substring(2));
				object.setApprType(tempList.substring(0,1));
				object.setApprOrder(Integer.toString(i+1));
				
				apDocDao.createApUserActivity(object);
			}
		}

				
		return apDocDao.createApUser(object);
	}
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#get(java.io.
	 * Serializable)
	 */
	@Transactional(readOnly = true)
	public ApDoc readApDoc(ApDoc object) {

		ApDoc apdoc = this.apDocDao.getApDoc(object);

		/*if(apForm != null){
			// get Form Template.
			if (this.apFormTplDao.exists(formId)) {
				apForm.setApFormTpl(this.apFormTplDao.get(formId));
			} else {
				ApFormTpl apFormTpl = new ApFormTpl();
				
				apFormTpl.setFormId(apForm.getFormId());
				
				apForm.setApFormTpl(apFormTpl);
			}
		}*/

		return apdoc;
	}

	
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.
	 * Serializable)
	 */
	public boolean exists(Integer id) {
		return apDocDao.exists(id);
	}

	/*
	 * (non-Javadoc)
	 * @seecom.lgcns.ikep4.framework.core.service.GenericService#get(java.io.
	 * Serializable)
	 */
	public ApDoc get(Integer id) {
		ApDoc apdoc = apDocDao.get(id);
		if (apdoc != null) {
			//role.setHit(board.getHit() + 1);
			apDocDao.update(apdoc);
		}
		
		return apdoc;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.
	 * Serializable)
	 */
	public void remove(Integer id) {
		apDocDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void update(ApDoc object) {
		
		ApReceive apReceive = new ApReceive();
		apReceive.setApprId(object.getApprId());
		
		deleteApDocData(object.getApprId());
		
		String apprId = object.getApprId();
		
		//전체 paramHsh
		Map<String, Object> paramHsh	= new HashMap<String, Object>();
		//배열 paramHsh
		Map<String, Object> arrayHsh	= new HashMap<String, Object>();
		Map<String, Object> arrayAgreeHsh	= new HashMap<String, Object>();
		
		//수신자 리스트				
		List<String> workerList = object.getWorkerList();
		if ( workerList != null)
		{	
			for(int i=0; i<workerList.size(); i++) {
				//INSERT IKEP_AP_RECEIVE
				apReceive.setReceiveId(workerList.get(i));
				apReceiveDao.create(apReceive);
				arrayHsh.put(Integer.toString(i), workerList.get(i));
			}
			//수신자 List
			paramHsh.put("receiveList",arrayHsh);
			object.setIsApprReceive("Y");
			
		}else{
			object.setIsApprReceive("N");
		}
		
		arrayHsh	= new HashMap<String, Object>();
		//열람권한자
		List<String> workerList1 = object.getWorkerList1();
		if ( workerList1 != null)
		{
			for(int i=0; i<workerList1.size(); i++) {
				//INSERT IKEP_AP_AUTH_USER
				apReceive.setReceiveId(workerList1.get(i));
				apReceiveDao.createAuthUser(apReceive);
				arrayHsh.put(Integer.toString(i), workerList1.get(i));
			}
			paramHsh.put("referenceList",arrayHsh);
		}

		//기결제참조첨부
		List<String> workerList3 = object.getWorkerList3();
		if ( workerList3 != null)
		{
			for(int i=0; i<workerList3.size(); i++) {
				//INSERT IKEP_AP_RELATIONS
				apReceive.setApprRefId(workerList3.get(i));
				apReceiveDao.createRelations(apReceive);
			}
			
		}
		
		
		//첨부파일 업데이트
		if(object.getFileLinkList() != null) {	
			this.saveFileLink(object.getFileLinkList(), apprId, "Approval");
		} 
		arrayHsh	= new HashMap<String, Object>();
		
		//결재선 저장
		List<String> workerList2 = object.getWorkerList2();
		String tempList="";
		String agreeType = object.getApprLineType();
		int approvalcnt=0; int agreecnt=0;
		if ( workerList2 != null)
		{	
			for(int i=0; i<workerList2.size(); i++) {
				//INSERT IKEP_AP_PROCESS
				tempList =workerList2.get(i);
				
				if (i == 0){
					object.setApprUserId(object.getRegistUserId());
					object.setApprType("0");
					object.setApprOrder(Integer.toString(i));
					if ( object.getApprDocState().equals("0")){
						object.setApprState("TEMP");
					}else{
						object.setApprState("COMPLETE");
					}
					arrayHsh.put(Integer.toString(i), object.getRegistUserId());
					approvalcnt++;
					
					apDocDao.createApProcess(object);

					object.setApprUserId(tempList.substring(2));
					object.setApprType(tempList.substring(0,1));
					//object.setApprOrder(Integer.toString(i+1));
					if ( object.getApprDocState().equals("0")){
						object.setApprState("TEMP");
					}else{
						object.setApprState("ASSIGN");
					}
					object.setApprMessage("");					
					
				}else{
					object.setApprUserId(tempList.substring(2));
					object.setApprType(tempList.substring(0,1));
					//object.setApprOrder(Integer.toString(i+1));
					if ( object.getApprDocState().equals("0")){
						object.setApprState("TEMP");
					}else{
						object.setApprState("ASSIGN");
					}
					object.setApprMessage("");

				}
				
				
				if (agreeType.equals("S"))//직렬
				{
					if ( tempList.substring(0,1).equals("0")) //결재
					{	
						arrayHsh.put(Integer.toString(approvalcnt), tempList.substring(2));
						approvalcnt++;
					}else{
						//합의
						arrayHsh.put(Integer.toString(approvalcnt), tempList.substring(2));
						approvalcnt++;
					}
					object.setApprOrder(Integer.toString(i+1));
				}else{//병렬일때
					if ( tempList.substring(0,1).equals("0")) //결재
					{	object.setApprOrder(Integer.toString(approvalcnt));
						arrayHsh.put(Integer.toString(approvalcnt), tempList.substring(2));
						approvalcnt++;
					}else{
						//합의
						object.setApprOrder(Integer.toString(agreecnt+1));
						arrayAgreeHsh.put(Integer.toString(agreecnt), tempList.substring(2));
						agreecnt++;
					}						
				}
				
				apDocDao.createApProcess(object);
			}
			paramHsh.put("approvalList",arrayHsh);
			paramHsh.put("agreeList",arrayAgreeHsh);
		}
		
		
		String processId=object.getProcessId();
		String title=object.getApprTitle();
		
		String userId = object.getRegistUserId();

		paramHsh.put("apprId",apprId);
		
		//문서종류
		if (object.getApprTypeCd().equals("100000012264") )   //문서종류  품의
			paramHsh.put("docType","TYPE01");
		else if (object.getApprTypeCd().equals("100000012265")) //문서종류 보고		
			paramHsh.put("docType","TYPE02");
		else if (object.getApprTypeCd().equals("100000678725")) //문서종류 통신
			paramHsh.put("docType","TYPE03");
		
		
		//보안등급
		if (object.getApprSecurityCd().equals("100000012271") )   //보안등급이 기밀
			paramHsh.put("securityLevel","SECURITY");
		else if (object.getApprSecurityCd().equals("100000012267")) //보안등급이 일반
			paramHsh.put("securityLevel","NORMAL");
		
		//보존기간
		if (object.getApprPeriodCd().equals("100000012253") )   //보존기간  1년
			paramHsh.put("keepDeadLine","1");
		else if (object.getApprPeriodCd().equals("100000012256")) //보존기간 2년
			paramHsh.put("keepDeadLine","2");
		else if (object.getApprPeriodCd().equals("100000012260")) //보존기간 3년
			paramHsh.put("keepDeadLine","3");
		else if (object.getApprPeriodCd().equals("100000012257")) //보존기간 5년
			paramHsh.put("keepDeadLine","5");
		
		
		//결재요청시 통보
		if (object.getMailReqCd().equals("100000728672") )   //전체 결재자 미통보
			paramHsh.put("stepNotiType","NO");
		else if (object.getMailReqCd().equals("100000728674")) //기안자 통보
			paramHsh.put("stepNotiType","DRAFT");
		else if (object.getMailReqCd().equals("100000729076")) //기안자 / 다음 결재자 통보
			paramHsh.put("stepNotiType","STEP");
		else if (object.getMailReqCd().equals("100000729078")) //전체 결재자 통보
			paramHsh.put("stepNotiType","ALL");
		
		//결재완료시 통보
		if (object.getMailEndCd().equals("100000729178") )   //전체 결재자 미통보
			paramHsh.put("EndNotiType","NO");
		else if (object.getMailEndCd().equals("100000729179")) //기안자 통보
			paramHsh.put("EndNotiType","DRAFT");
		else if (object.getMailEndCd().equals("100000012231")) //전체 결재자 통보
			paramHsh.put("EndNotiType","ALL");	
		

		//결재요청시 통보방법
		if (object.getMailReqWayCd().equals("100000012232") )   //메일발송
			paramHsh.put("stepNotiMethod","EMAIL");
		else if (object.getMailReqWayCd().equals("100000012234")) //문자발송
			paramHsh.put("stepNotiMethod","SMS");
		else if (object.getMailReqWayCd().equals("100000012235")) //문자/메일 발송
			paramHsh.put("stepNotiMethod","ALL");

		//결재완료시 통보방법
		if (object.getMailEndWayCd().equals("100000012241") )   //메일발송
			paramHsh.put("EndNotiMethod","EMAIL");
		else if (object.getMailEndWayCd().equals("100000012243")) //문자발송
			paramHsh.put("EndNotiMethod","SMS");
		else if (object.getMailEndWayCd().equals("100000729177")) //문자/메일 발송
			paramHsh.put("EndNotiMethod","ALL");

		if (agreeType.equals("S")) {
			paramHsh.put("draftType","APP");
		}else{
			paramHsh.put("draftType","ALL");
		}
				
		if ( object.getApprDocState().equals("1"))//임시저장일때는 호출하지 않음.
		{
			InstanceBean instanceBean = new InstanceBean();
			instanceBean = instanceService.startProcess(processId, title, userId, paramHsh);
		
			paramHsh.put("approvalYN","Y");
			paramHsh.put("approvalList_Key","1");
			if ( agreecnt > 0){
				paramHsh.put("agreeList_Key","0");
			}
			
			instanceService.completeWorkItem(instanceBean.getInstanceId(),"DRAFT",instanceBean.getInstanceLogId(), userId, paramHsh);
			object.setInstanceId(instanceBean.getInstanceId());
		}

		apDocDao.update(object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang
	 * .Object)
	 */
	public void updateApProcess(ApDoc object) {
		apDocDao.updateApProcess(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.service.BoardService#list()
	 */
	public List<ApDoc> list(ApDoc object) {
		return apDocDao.selectAll(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.service.BoardService#list()
	 */
	public List<ApDoc> listApUser(ApDoc object) {
		return apDocDao.selectAllApUser(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.service.BoardService#list()
	 */
	public List<ApDoc> listApUserList(ApDoc object) {
		return apDocDao.selectAllApUserList(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.service.BoardService#list()
	 */
	public List<ApDoc> listApProcess(ApDoc object) {
		return apDocDao.selectAllApProcess(object);
	}
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.service.BoardService#list()
	 */
	public List<ApDoc> listApProcessUserId(ApDoc object) {
		return apDocDao.selectApProcessUserId(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.service.BoardService#list()
	 */
	public List<ApDoc> listApReceive(ApDoc object) {
		return apDocDao.selectApReceive(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.service.BoardService#list()
	 */
	public List<ApDoc> listApAuthUser(ApDoc object) {
		return apDocDao.selectApAuthUser(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.service.BoardService#list()
	 */
	public List<ApDoc> listApRelations(ApDoc object) {
		return apDocDao.selectApRelations(object);
	}
	
	public int listCount(ApDoc object) {
		return apDocDao.selectCount(object);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#remove(java.io.
	 * Serializable)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteApDocData(String apprId) {
		
		//결재선리스트 초기화
		this.apDocDao.removeApProcess(apprId);
		
		//열람자권한리스트 초기화
		this.apDocDao.removeApAuthUser(apprId);

		//수신자리스트 초기화
		this.apDocDao.removeApReceive(apprId);
	}
	
	
	//첨부파일 저장을 위한  Function
	public void saveFileLink(List<FileLink> fileLinkList, String itemId, String itemTypeCode) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		//List<String> deleteIdList = new ArrayList<String>();
		//DM_FILE 을 조회해서  FILE_NAME,FILE_SIZE 등등 조회 필요
		// 내일 완료 하자
		
		for (FileLink fileLink : fileLinkList) {

			if (fileLink.getFlag().equals("add")) {

				String fileLinkId = idgenService.getNextId();
				fileLink.setItemId(itemId);
				fileLink.setItemTypeCode(itemTypeCode);
				fileLink.setFileLinkId(fileLinkId);
				fileLink.setRegisterId(user.getUserId());
				fileLink.setRegisterName(user.getUserName());
				fileLink.setUpdaterId(user.getUserId());
				fileLink.setUpdaterName(user.getUserName());

				apDocDao.create(fileLink);
				
//			} else {
				//deleteIdList.add(fileLink.getFileId());
			}

		}
		
		//if(deleteIdList.size() > 0 )
		//	removeFile(deleteIdList);

	}
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
}
