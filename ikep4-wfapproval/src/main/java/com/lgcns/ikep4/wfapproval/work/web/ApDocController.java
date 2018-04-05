/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.service.ApCodeService;
import com.lgcns.ikep4.wfapproval.admin.service.ApFormService;
import com.lgcns.ikep4.wfapproval.common.model.CommonCode;
import com.lgcns.ikep4.wfapproval.work.model.ApDoc;
import com.lgcns.ikep4.wfapproval.work.service.ApDocService;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;
import com.lgcns.ikep4.workflow.workplace.search.WorkplaceSearchCondition;
import com.lgcns.ikep4.workflow.workplace.service.WorkplaceListService;


/**
 * 품위서 작성<br/>
 * <br/>
 * 다음 사항 포함
 * 
 * <pre>
 * <li>품위서 기본 양식 조회</li>
 * <li>품위서 작성</li>
 * </pre>
 * 
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApDocController.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Controller
@RequestMapping(value = "/wfapproval/work/apdoc")
@SessionAttributes("apdoc")
public class ApDocController extends BaseController {

	@Autowired
	private ApDocService apDocService;

	@Autowired
	private ApFormService apFormService;
	
	@Autowired
	private ApCodeService apCodeService;
	
	@Autowired
	private WorkplaceListService workplaceListService;
	
	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;

	//품위서 양식번호를 받아 기본 세팅
	@RequestMapping(value = "/createApDoc.do")
	public ModelAndView createApDoc(@RequestParam(value = "formId", required = true) String formId,@RequestParam(value = "linkType", required = false) String linkType) {

		ModelAndView mav = new ModelAndView("/wfapproval/work/apdoc/createApDoc");
		if(linkType != null && linkType.equals("mywork")){
			mav.setViewName("/wfapproval/work/apdoc/createMyWorkApDoc");
		}

		if (formId != null) {
			ApForm apForm = apFormService.readApForm(formId);

			mav.addObject("apForm", apForm);
			mav.addObject("apFormTpl", apForm.getApFormTpl());
			mav.addObject("today", DateUtil.getToday(""));
			
			//사용자 세션정보
			User user = (User)this.getRequestAttribute("ikep.user");
			mav.addObject("user", user); 
			
			// 공통코드 목록정보 설정하기
			this.setApFormModelByApCodeList(mav);
						
		}
		return mav;
	}
	
	//품위서 번호를 받아 상세조회
	@RequestMapping(value = "/readApDoc.do", method = RequestMethod.GET)
	public ModelAndView readApDoc(HttpServletRequest request,
			@RequestParam(value = "apprId", required = true) String apprId,
			@RequestParam(value = "instanceId", required = false) String instanceId,
			@RequestParam(value = "insLogId", required = false) String insLogId,
			@RequestParam(value = "activityId", required = false) String activityId,
			@RequestParam(value = "linkType", required = false) String linkType) 
	{
		ModelAndView mav = new ModelAndView("/wfapproval/work/apdoc/readApDoc");
		
		
		if(linkType == null) linkType="";
		if(linkType.equals("mywork")){
			mav.setViewName("/wfapproval/work/apdoc/readMyWorkApDoc");
		}
		
		if (apprId == null) apprId="";
		
		if ( !apprId.equals("") ) {
			
			ApDoc apdoc = new ApDoc();
			
			if ( instanceId == null ) { instanceId =""; }
			if ( insLogId == null ) { insLogId =""; }
			if ( activityId == null ) { activityId =""; }
			
			
			apdoc.setApprId(apprId);
			
			
			////품위서 확인하는
			User user = (User)this.getRequestAttribute("ikep.user");
			apdoc.setRegistUserId(user.getUserId());
			
			List<ApDoc> listApProcessUserId = apDocService.listApProcessUserId(apdoc);
			//사용자 결재라인 조회
			mav.addObject("apProcessUserId", listApProcessUserId);
			////
			
			
			apdoc = apDocService.readApDoc(apdoc);
			
			ApForm apForm = apFormService.readApForm(apdoc.getFormId());
			
			//임시저장이면 수정페이지로   appr_doc_state 0 임시저장
			if ( apdoc.getApprDocState().equals("0"))
			{
				mav = new ModelAndView("/wfapproval/work/apdoc/updateApDoc");
				//임시저장일때 양식정보 RELOAD
				//apForm = apFormService.readApForm(apdoc.getFormId());
				//mav.addObject("apFormTpl", apForm.getApFormTpl());
//			}else{
				////사용자 세션정보
				//User user = (User)this.getRequestAttribute("ikep.user");
				//apdoc.setRegistUserId(user.getUserId());
				
				////List<ApDoc> listApProcessUserId = apDocService.listApProcessUserId(apdoc);
				////사용자 결재라인 조회
				//mav.addObject("apProcessUserId", listApProcessUserId);
				
			}
						
			//insLogId 전달
			apdoc.setInsLogId(insLogId);
			apdoc.setInstanceId(instanceId);
			apdoc.setActivityId(activityId);
			apdoc.setLinkType(linkType);
			
			mav.addObject("apDoc", apdoc);
			
			//결재선리스트 조회
			List<ApDoc> listApProcess = apDocService.listApProcess(apdoc);
			mav.addObject("apProcess", listApProcess);
			
			//수신자리스트 조회
			List<ApDoc> listApReceive = apDocService.listApReceive(apdoc);
			mav.addObject("apReceive", listApReceive);
			
			//참조자리스트 조회
			List<ApDoc> listApAuthUser = apDocService.listApAuthUser(apdoc);
			mav.addObject("apAuthUser", listApAuthUser);
			
			//기결재참조첨부리스트 조회
			List<ApDoc> listApRelations = apDocService.listApRelations(apdoc);
			mav.addObject("apRelations", listApRelations);
			
			
			
			mav.addObject("apFormTpl", apForm.getApFormTpl());
			mav.addObject("today", DateUtil.getToday(""));
			
			//사용자 세션정보
			//User user = (User)this.getRequestAttribute("ikep.user");
			mav.addObject("user", user); 
			
			// 공통코드 목록정보 설정하기
			this.setApFormModelByApCodeList(mav);
						
		}
		return mav;
	}	

	
	//품위서 번호를 받아 인쇄
	@RequestMapping(value = "/printApDoc.do", method = RequestMethod.GET)
	public ModelAndView printApDoc(
			@RequestParam(value = "apprId", required = false) String apprId,
			@RequestParam(value = "instanceId", required = false) String instanceId) 
	{
		ModelAndView mav = new ModelAndView("/wfapproval/work/apdoc/printApDoc");
		
		if (apprId != null || instanceId != null) {
			
			//ApForm apForm = new ApForm();
				
		
			ApDoc apdoc = new ApDoc();
			if ( apprId == null ) { apprId =""; }
			if ( instanceId == null ) { instanceId =""; }
			
			
			apdoc.setApprId(apprId);
			apdoc.setInstanceId(instanceId);
			
			apdoc = apDocService.readApDoc(apdoc);
			
			
			mav.addObject("apDoc", apdoc);

			List<ApDoc> listApProcess = apDocService.listApProcess(apdoc);
			mav.addObject("apProcess", listApProcess);
						
			
			//mav.addObject("apFormTpl", apForm.getApFormTpl());
			mav.addObject("today", DateUtil.getToday(""));
			
			//사용자 세션정보
			User user = (User)this.getRequestAttribute("ikep.user");
			mav.addObject("user", user); 
			
			// 공통코드 목록정보 설정하기
			this.setApFormModelByApCodeList(mav);
						
		}
		return mav;
	}
	
	//품위서 작성 후 DB Insert        INSERT APDOC
	@RequestMapping(value = "/createApDoc.do", method = RequestMethod.POST)
	//@Override
	public String onSubmit(@Valid ApDoc apdoc, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			return "board/form";
		}
		
		String appr_id = apdoc.getApprId();
		
		//수신처등록을 위한 목록 세팅
		String etcName = apdoc.getEtcName();
		if (etcName == null) etcName="";
		String[] tmp = etcName.split(",");
		List<String> workerList = new ArrayList<String>(Arrays.asList(tmp));
		if (etcName.equals("")){
			apdoc.setWorkerList(null);
		}else{
			apdoc.setWorkerList(workerList);
		}
		
		//열람권한지정을 위한 목록 세팅
		etcName = apdoc.getEtcName1();
		if (etcName == null) etcName="";
		tmp = etcName.split(",");
		List<String> workerList1 = new ArrayList<String>(Arrays.asList(tmp));
		if (etcName.equals("")){
			apdoc.setWorkerList1(null);
		}else{
			apdoc.setWorkerList1(workerList1);
		}
		
		//기결제참조첨부를 위한 목록 세팅
		etcName = apdoc.getEtcName3();
		if (etcName == null) etcName="";
		tmp = etcName.split(",");
		List<String> workerList3 = new ArrayList<String>(Arrays.asList(tmp));
		if (etcName.equals("")){
			apdoc.setWorkerList3(null);
		}else{
			apdoc.setWorkerList3(workerList3);
		}
		
		//결재선지정을 위한 목록 세팅
		etcName = apdoc.getEtcName2();
		tmp = etcName.split(",");
		List<String> workerList2 = new ArrayList<String>(Arrays.asList(tmp));
		if (etcName.equals("")){
			apdoc.setWorkerList2(null);
		}else{
			apdoc.setWorkerList2(workerList2);		
		}
		
		
		if (appr_id != null)
		{
			// UPDATE 임시저장 후 수정 시 
			//apdoc.setIsAprReceive("N");
			apDocService.update(apdoc);
			
			
			
		}
		else
		{
			//apdoc 등록
			apDocService.create(apdoc);
			
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return "redirect:/wfapproval/work/aplist/listApMyRequest.do" ;
	}
	
	//품위서 결재합의 처리
	@RequestMapping(value = "/confirmApDoc.do", method = RequestMethod.POST)
	//@Override
	public ModelAndView confirmApDoc(
			@RequestParam(value = "apprId", required = false) String apprId,
			@RequestParam(value = "instanceId", required = false) String instanceId) 
	{
		
		ModelAndView mav = new ModelAndView("/wfapproval/work/apdoc/confirmApDoc");

			//instanceService.startProcess(processId, title, userId, paramHsh);
			
			//instanceService.completeWorkItem("","DRAFT","", userId, paramHsh);
			
			
			//instanceService.startProcess(processId, title, userId, paramHsh);
			
			//instanceService.completeWorkItem("","DRAFT","", userId, paramHsh);
			
		return mav;
	}
	
	/**
	 * 품위서 결재 합의 반려
	 * @param apDoc
	 * @param result
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajaxConfirmApDoc", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ApDoc ajaxConfirmApDoc(@Valid ApDoc apdoc, BindingResult result, SessionStatus status) {
		
		try{
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", new Exception("Code Update Valid-Error"));
			}
			
			String userId = apdoc.getRegistUserId();
			String apprType = apdoc.getApprType();
			String activityId= apdoc.getActivityId();
			String apprLineType = apdoc.getApprLineType();
			String approvalYn = apdoc.getApprovalYn();
			
			String decisionYn = apdoc.getDecisionYn();
			
			String processId = apdoc.getProcessId();
			
			if (apprType==null) apprType="";
			if (decisionYn==null) decisionYn="";
			
			
			if ( processId.equals("APPROVAL_PROCESS"))
			{
			
				int apprOrder = Integer.parseInt(apdoc.getApprOrder()); //apprOrder +1해서 결재처리
						
				HashMap paramHsh	= new HashMap();
			
				if ( apprLineType.equals("S") )
				{
					paramHsh.put("draftType","APP");
					if (decisionYn.equals("Y"))
					{
						paramHsh.put("decisionYN",decisionYn);
					}else{
						
						//합의 의사표시 불가일때는  agreeYN = Y로만   종료없이 계속 진행
						if ( apprType.equals("1") )
						{
							if ( apdoc.getDiscussCd().equals("100000012252"))
							{
								paramHsh.put("approvalYN","Y");
							}else{
								paramHsh.put("approvalYN",approvalYn);
							}
						}else{
							paramHsh.put("approvalYN",approvalYn);
						}
					}
					//paramHsh.put("agreeYN","X");
					paramHsh.put("approvalList_Key",apprOrder+1);// apprOrder에 +1 해서 결재처리
					activityId="APPROVAL";
			
				}else{
					if ( apprType.equals("0") )
					{
						paramHsh.put("draftType","APP");
						if (decisionYn.equals("Y"))
						{
							paramHsh.put("decisionYN",decisionYn);
						}else{
							paramHsh.put("approvalYN",approvalYn);
						}
						
						paramHsh.put("approvalList_Key",apprOrder+1);// apprOrder에 +1 해서 결재처리
						activityId="APPROVAL";
					}else
					{
						paramHsh.put("draftType","AGR");
						
						
						//합의 의사표시 불가일때는  agreeYN = Y로만   종료없이 계속 진행
						if ( apdoc.getDiscussCd().equals("100000012252"))
						{
							paramHsh.put("agreeYN","Y");
						}else{
							paramHsh.put("agreeYN",approvalYn);
						}
						paramHsh.put("agreeList_Key",apprOrder);// apprOrder에 +1 해서 결재처리
						activityId="AGREE";
					}
				}
			
			
			
			
				try {
					// 결재는 APPROVAL,  합의는 AGREE
				
					instanceService.completeWorkItem(apdoc.getInstanceId(),activityId,apdoc.getInsLogId(), userId, paramHsh);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			
				//IKEP4_AP_PROCESS 상태 업데이트 하는 기능 추가 필요.
				if (approvalYn.equals("Y") || decisionYn.equals("Y"))
				{
					apdoc.setApprState("COMPLETE");
				}else{
					apdoc.setApprState("REJECT");
				}
			
				apdoc.setApprUserId(userId);
				apDocService.updateApProcess(apdoc);
			
			}else{

				HashMap paramHsh	= new HashMap();
				paramHsh.put("approvalYN",approvalYn);
				
				try {
					// 결재는 APPROVAL,  합의는 AGREE
					instanceService.completeWorkItem(apdoc.getInstanceId(),activityId,apdoc.getInsLogId(), userId, paramHsh);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
			

			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ConfirmApDoc", ex);
		}

		return apdoc;
	}
	
	/**
	 * 사용자정의 저장
	 * @param apDoc
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxApplyApUser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ApDoc ajaxApplyApUser(@Valid ApDoc apdoc, BindingResult result, SessionStatus status) {
		
		try{
			if (result.hasErrors()) {
				throw new IKEP4AjaxException("", new Exception("Code Update Valid-Error"));
			}
			
			
			
			//리스트 등록		
			String etcName = apdoc.getEtcName();
			String[] tmp = etcName.split(",");
			List<String> workerList = new ArrayList<String>(Arrays.asList(tmp));
			apdoc.setWorkerList(workerList);
	
			apDocService.createApUser(apdoc);
			
			//if (StringUtil.isEmpty(apCode.getCodeId()))
			//	this.apCodeService.createApCode(apCode);
			//else
			//	this.apCodeService.updateApCode(apCode);
	
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("applyApUser", ex);
		}

		return apdoc;
	}
	
	/**
	 * 사용자정의 결재선 리스트
	 * @param processId  사용자정의결재선ID
	 * @param response
	 */
	@RequestMapping("/ajaxRequestApUserList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> requestApUserList(@RequestParam(value="processId", required=false) String processId
									, HttpServletResponse response) {
		if(processId == null || processId.equals("")) {
			processId = "00000";
		}
		
		
		Map<String, Object> item = new HashMap<String, Object>();
		//List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	    try{
	    	
	    	ApDoc apdoc = new ApDoc();
			apdoc.setProcessId(processId);	
			List<ApDoc> listApdoc = apDocService.listApUserList(apdoc);
			
			
			
			/*for(ApDoc apdoc1 : listApdoc) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				map.put("name", apdoc1.getUserName());
				map.put("code", "");
				map.put("parent", "");
				map.put("id", apdoc1.getApprId());
				map.put("empNo", "");
				map.put("email", apdoc1.getMail());
				map.put("mobile", "");
				map.put("jobTitle", apdoc1.getJobPositionName());
				map.put("teamName", apdoc1.getTeamName() );
				list.add(map);
			}*/
			
			item.put("items", listApdoc);
			
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }
	    
	    return item;
	}
	
	/**
	 * 기결재 참조첨부 선택 화면
	 * 
	 * @param apprId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchApDoc", method = RequestMethod.GET)
	public ModelAndView readApFormPreview(Model model) {
		return new ModelAndView("/wfapproval/work/apdoc/searchApDoc");
	}
	
	/**
	 * 기결재 참조첨부 선택화면 - 품위서 목록 수정필요.
	 */
	@RequestMapping(value = "/ajaxListApDoc")
	public ModelAndView workPlaceStartWorkList(WorkplaceSearchCondition workplaceSearchCondition, BindingResult result,
			SessionStatus status) {

		ModelAndView mav = new ModelAndView("/wfapproval/admin/apform/listApFormProcess");

		workplaceSearchCondition.setQueryId("startProcList");

		SearchResult<ProcessBean> searchResult = this.workplaceListService
				.workplaceProcessList(workplaceSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("workplaceSearchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode", new WorkplaceCode());

		return mav;
	}


	//결재선지정 팝업
	@RequestMapping(value = "/listUserApLine.do")
	public ModelAndView approvalLinePopup(@RequestParam(value="selectType", required=true, defaultValue="GROUP" ) String selectType
			, @RequestParam(value="controlTabType", required=true, defaultValue="0:0:0:0")  String controlTabType
			, @RequestParam(value="controlType", required=true, defaultValue="ORG" ) String controlType
			, @RequestParam(value="selectMaxCnt", required=true, defaultValue="500") String selectMaxCnt
			, @RequestParam(value="selectApprLineCnt", required=true, defaultValue="15") String selectApprLineCnt
			, @RequestParam(value="selectDiscussLineCnt", required=true, defaultValue="15") String selectDiscussLineCnt
			, @RequestParam(value="isAppr", required=true, defaultValue="N") String isAppr
			, @RequestParam(value="isDiscuss", required=true, defaultValue="N") String isDiscuss
			, @RequestParam(value="searchSubFlag", required=true, defaultValue="false" ) String searchSubFlag
			, @RequestParam(value="apprLineType", required=true, defaultValue="S" ) String apprLineType
		) {
		
		ModelAndView mav = new ModelAndView("/wfapproval/work/apdoc/popSelectList");

		//workplaceSearchCondition.setQueryId("startProcList");
		
		//SearchResult<ProcessBean> searchResult = this.workplaceListService.workplaceProcessList(workplaceSearchCondition);
		
		//mav.addObject("searchResult", searchResult);
		//mav.addObject("workplaceSearchCondition", searchResult.getSearchCondition());
		//mav.addObject("workplaceCode", new WorkplaceCode());
		//사용자 세션정보
		User user = (User)this.getRequestAttribute("ikep.user");
		mav.addObject("user", user); 
		
		ApDoc apdoc = new ApDoc();
		apdoc.setRegistUserId(user.getUserId());	
		List<ApDoc> listApUser = apDocService.listApUser(apdoc);
		
		//사용자정의 리스트
		
		
		
		
		// 첫 팝업 호출시 ORG 타입으로 호출
		ObjectMapper mapper = new ObjectMapper(); 
		List<Map<String, Object>> list = getOrgGroupAndUser(null,selectType);
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("items", list);
		List<String> controlTabTypeList = StringUtil.getTokens(controlTabType, ":");
		
		try{
			mav.addObject("deptItems", mapper.writeValueAsString(items));
			
			int i = 0;
			if( !(selectType.equals("GROUP")) ){
				for(String tabType : controlTabTypeList) {
	
					//List<Map<String, Object>> subTab = new ArrayList<Map<String, Object>>();
					//Map<String, Object> subTabItems = new HashMap<String, Object>();
					
					if( i == 0 ){
						if( tabType.equals("1") ){
							//subTab = getPublicGroupAndUser(null,selectType);
							//subTabItems.put("items", subTab);
							//mav.addObject("pubGroupItems", mapper.writeValueAsString(subTabItems));
							mav.addObject("dispPubTabFlag", "true");
						}else{
							//mav.addObject("pubGroupItems", "");
							mav.addObject("dispPubTabFlag", "false");
						}
					}else if( i == 1 ){
						if( tabType.equals("1") ){
							//subTab = getPrivateGroupAndUser(null,selectType);
							//subTabItems.put("items", subTab);
							//mav.addObject("priGroupItems", mapper.writeValueAsString(subTabItems));
							mav.addObject("dispPriTabFlag", "true");
						}else{
							//mav.addObject("priGroupItems", "");
							mav.addObject("dispPriTabFlag", "false");
						}
					}else if(  i == 2 ){
						if( tabType.equals("1") ){
							//subTab = getCollaborationGroupAndUser(null,selectType);
							//subTabItems.put("items", subTab);
							//mav.addObject("colGroupItems", mapper.writeValueAsString(subTabItems));
							mav.addObject("dispColTabFlag", "true");
						}else{
							//mav.addObject("colGroupItems", "");
							mav.addObject("dispColTabFlag", "false");
						}
					}else if(  i == 3 ){
						if( tabType.equals("1") ){
							//subTab = getSnsGroupAndUser(null,selectType);
							//subTabItems.put("items", subTab);
							//mav.addObject("snsGroupItems", mapper.writeValueAsString(subTabItems));
							mav.addObject("dispSnsTabFlag", "true");
						}else{
							//mav.addObject("snsGroupItems", "");
							mav.addObject("dispSnsTabFlag", "false");
						}
					}
					i++;
				}
			}else{
				mav.addObject("dispPubTabFlag", "false");
				mav.addObject("dispPriTabFlag", "false");
				mav.addObject("dispColTabFlag", "false");
				mav.addObject("dispSnsTabFlag", "false");
			}
			
		} catch(Exception ex) {
		    throw new IKEP4AjaxException("code", ex);
		}
		
		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("selectMaxCnt", selectMaxCnt);
		mav.addObject("selectApprLineCnt", selectApprLineCnt);
		mav.addObject("selectDiscussLineCnt", selectDiscussLineCnt);
		mav.addObject("searchSubFlag", searchSubFlag);
		mav.addObject("isAppr", isAppr);
		mav.addObject("isDiscuss", isDiscuss);
		mav.addObject("apprLineType", apprLineType);

		
		
		
		
		
		
		
		
		
		
		
		mav.addObject("apdocApUserList", listApUser);
		
		return mav;
	}

	/**
	 * 조직도와 사용자 조회를 하기 위한 메서드
	 * @param groupId
	 * @param selectType
	 * @return
	 */
	private List<Map<String, Object>> getOrgGroupAndUser(String groupId, String selectType) {
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Group searchgroup = new Group();
		searchgroup.setGroupId(groupId);
		searchgroup.setRegisterId(sessionuser.getUserId());
		List<Group> groupList = groupService.selectOrgGroup(searchgroup);
		for(Group group : groupList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "group");
			map.put("name", group.getGroupName());
			map.put("code", group.getGroupId());
			map.put("groupTypeId", group.getGroupTypeId());
			map.put("parent", group.getParentGroupId());
			map.put("hasChild", group.getChildGroupCount());
			list.add(map);
		}
		
		if( !(selectType.equals("GROUP"))){
			
			List<User> userList = userService.selectAllForTree(sessionuser.getLocaleCode(), groupId,sessionuser.getUserId());
			for(User user : userList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				map.put("name", user.getUserName());
				map.put("code", "");
				map.put("parent", user.getGroupId());
				map.put("id", user.getUserId());
				map.put("empNo", user.getEmpNo());
				map.put("email", user.getMail());
				map.put("mobile", user.getMobile());
				map.put("jobTitle", user.getJobTitleName());
				map.put("teamName", user.getTeamName() );
				list.add(map);
			}
		}
		
		return list;
	}
	//기결재참조첨부팝업
	@RequestMapping(value = "/listApComplete.do")
	public ModelAndView getApCompleteList( WorkplaceSearchCondition workplaceSearchCondition,
			                           	   HttpServletRequest request,
			                               HttpServletResponse response) throws ServletException, IOException { 

		ModelAndView mav = new ModelAndView("/wfapproval/work/apdoc/readApList");

		//String userId    = request.getParameter("userId") == null ? "test01" : request.getParameter("userId").toString();
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();
		
		String listType  = "completeList";
        
		workplaceSearchCondition.setQueryId(listType);
		workplaceSearchCondition.setUserId(userId);

		SearchResult<WorkItemBean> searchResult = this.workplaceListService.workplaceWorkList(workplaceSearchCondition);		
		
		// 양식유형 목록
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);
		
		// 문서상태 목록
		List<ApCode> listApprDocState = this.apCodeService.listGroupApCodeByValue(CommonCode.APPR_DOC_STATE, localeCode);

		mav.addObject("apList",          searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("pUserName",       (request.getParameter("userName")==null)?"":request.getParameter("userName"));
		mav.addObject("workplaceCode",   new WorkplaceCode());
		mav.addObject("userId",          userId);
		mav.addObject("listType",        listType);
		//mav.addObject("pageTitle",       "기결함");
		mav.addObject("size",            "10");	
		mav.addObject("totalcount",      searchResult.getRecordCount());
		mav.addObject("commonCode", new CommonCode());
		mav.addObject("listFormTypeCd", listFormTypeCd);
		mav.addObject("listApprDocState", listApprDocState);
		
		return mav;
	}
	
	/**
	 * 문서내 공통코드 목록 설정하기
	 * 
	 * @param model
	 */
	private void setApFormModelByApCodeList(ModelAndView mav) {

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);

		String localeCode = user.getLocaleCode();

		// 양식유형 목록
		List<ApCode> listFormClassCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_CLASS_CD, localeCode);
		// 양식구분 목록
		List<ApCode> listFormTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.FORM_TYPE_CD, localeCode);
		// 결재요청시 통보대상
		List<ApCode> listMailReqCd = this.apCodeService.listGroupApCodeByValue(CommonCode.MAIL_REQ_CD, localeCode);
		// 결재완료시 통보대상
		List<ApCode> listMailEndCd = this.apCodeService.listGroupApCodeByValue(CommonCode.MAIL_END_CD, localeCode);
		// 결재요청시 통보방법
		List<ApCode> listMailReqWayCd = this.apCodeService.listGroupApCodeByValue(CommonCode.MAIL_REQ_WAY_CD, localeCode);
		// 결재완료시 통보방법
		List<ApCode> listMailEndWayCd = this.apCodeService.listGroupApCodeByValue(CommonCode.MAIL_END_WAY_CD, localeCode);
		// 합의
		List<ApCode> listDiscussCd = this.apCodeService.listGroupApCodeByValue(CommonCode.DISCUSS_CD, localeCode);
		// 보존연한
		List<ApCode> listApprPeriodCd = this.apCodeService
				.listGroupApCodeByValue(CommonCode.APPR_PERIOD_CD, localeCode);
		// 결재구분
		List<ApCode> listApprTypeCd = this.apCodeService.listGroupApCodeByValue(CommonCode.APPR_TYPE_CD, localeCode);
		// 결재문서종류
		List<ApCode> listApprDocCd = this.apCodeService.listGroupApCodeByValue(CommonCode.APPR_DOC_CD, localeCode);

		mav.addObject("listFormClassCd", listFormClassCd);
		mav.addObject("listFormTypeCd", listFormTypeCd);
		mav.addObject("listMailReqCd", listMailReqCd);
		mav.addObject("listMailEndCd", listMailEndCd);
		mav.addObject("listMailReqWayCd", listMailReqWayCd);
		mav.addObject("listMailEndWayCd", listMailEndWayCd);
		mav.addObject("listDiscussCd", listDiscussCd);
		mav.addObject("listApprPeriodCd", listApprPeriodCd);
		mav.addObject("listApprTypeCd", listApprTypeCd);
		mav.addObject("listApprDocCd", listApprDocCd);

		mav.addObject("commonCode", new CommonCode());
	}
}
