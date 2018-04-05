/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprCode;
import com.lgcns.ikep4.approval.admin.model.ApprDefLine;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.admin.model.ApprDoc.CreateApprDoc;
import com.lgcns.ikep4.approval.admin.model.ApprDoc.UpdateApprDoc;
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprAdminFormService;
import com.lgcns.ikep4.approval.admin.service.ApprCodeService;
import com.lgcns.ikep4.approval.admin.service.ApprDefLineService;
import com.lgcns.ikep4.approval.work.model.ApprDocAuth;
import com.lgcns.ikep4.approval.work.model.ApprLine;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprDisplayService;
import com.lgcns.ikep4.approval.work.service.ApprEntrustService;
import com.lgcns.ikep4.approval.work.service.ApprExamService;
import com.lgcns.ikep4.approval.work.service.ApprLineService;
import com.lgcns.ikep4.approval.work.service.ApprReferenceService;
import com.lgcns.ikep4.approval.work.service.ApprWorkDocService;
import com.lgcns.ikep4.approval.work.util.HttpProxy;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;

/**
 * ApprWorkDoc 컨트롤러
 * 
 * @author wonchu
 * @version $Id: FormController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/approval/work/apprWorkDoc")
@SessionAttributes("apprWorkDoc")
public class ApprWorkDocController extends BaseController {

	@Autowired
	private ApprWorkDocService apprWorkDocService;
	
	@Autowired
	private ApprAdminFormService apprAdminFormService;
	
	@Autowired
	private ApprCodeService apprCodeService;
	
	@Autowired
	private	ApprLineService	apprLineService;
	
	@Autowired
	private	ApprDefLineService	apprDefLineService;

	@Autowired
	private	GroupService	groupService;
	
	@Autowired
	private ApprDisplayService apprDisplayService;

	@Autowired
	private ApprExamService apprExamService;
	
	@Autowired
	ApprAdminConfigService apprAdminConfigService;
	
	@Autowired
	private ApprEntrustService	apprEntrustService;
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private ApprReferenceService apprReferenceService;
	
	/**
	 * 로그인 사용자가 전자결재의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Approval", user.getUserId());
	}
	
	/**
	 * 환경 설정에 정의된 값을 조회한다
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId){
		
		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		if(apprAdminConfig.getIsReadAll().equals("1")) {
			//IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}
	
	/**
	 * 기안문 작성폼
	 * 
	 * @param	ApprFormSearchCondition
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/createApprDocForm.do")
	public ModelAndView createApprDocForm(
			@RequestParam(value = "formId", required = true) String formId,
			@RequestParam(value = "formParentId", required = false) String formParentId,
			@RequestParam(value = "linkType", defaultValue = "", required = false) String linkType,
			ApprFormSearchCondition apprFormSearchCondition) 
			throws JsonGenerationException, JsonMappingException, IOException {
		
		// tiles
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/createApprDocForm" + linkType);
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// 겸직
		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("userId", user.getUserId());
		List <Group> groupList = groupService.selectUserGroup(groupMap);	
		int groupSize = groupList.size();
		
		//apprDoc data
		ApprDoc apprDoc = new ApprDoc();
		apprDoc.setLinkType(linkType);
		
		// apprForm data
		ApprForm apprForm = apprAdminFormService.readApprForm(formId);
		
		// 사용중인 양식만 서비스
		if(apprForm.getUsage()!=1){
			throw new IKEP4AuthorizedException();
		}
		
		// 수신참조자
		List <User> apprReferenceList   =  apprAdminFormService.getApprReferenceList(formId, user.getLocaleCode());
		
		// 결재선
		List<ApprLine> apprLineList = null;
		
		// 결재선 - 수신처
		List<ApprLine> apprReceiveLineList = null;
		
		if(apprForm.getDefLineUse()==1 && apprForm.getDefLineId()!=null){
			Map<String, String> map = new HashMap<String, String>();
			map.put("defLineId", apprForm.getDefLineId());
			map.put("apprType", "0");	
			map.put("groupId", user.getGroupId());
			
			apprLineList = apprLineService.listApprLineDef(map);
			
			// apprType : 3 인경우 결재 수신정보만 
			map.put("apprType", "3");			
			apprReceiveLineList = apprLineService.listApprLineDef(map);	
			
			ApprDefLine apprDefLine = new ApprDefLine();
			apprDefLine = apprDefLineService.read(apprForm.getDefLineId());
			
			// 합의유형 0:순차,1:병렬
			apprDoc.setApprLineType(apprDefLine.getDefLineWay());

		}
		
		// 연계여부
		if(apprForm.getIsLinkUrl()==1){
			HttpProxy p = new HttpProxy();
			groupMap.put("formId", apprForm.getFormId());
			apprDoc.setFormLinkedData(p.get(apprForm.getLinkUrl(), groupMap));
		}
				
		// 파일 목록을 JSON으로 변환한다.
		ObjectMapper mapper = new ObjectMapper();
		
		String fileDataListJson = null;
		if (apprForm.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(apprForm.getFileDataList());
		}
		
		// 보존연한 목록
		List<ApprCode> apprPeriodList 	= this.apprCodeService.listGroupApprCodeByValue(CommonCode.APPR_PERIOD, user.getLocaleCode());

		ApprAdminConfig apprConfig= new ApprAdminConfig();
		apprConfig = apprAdminConfigService.adminConfigDetail(user.getPortalId());	
		
		//set return info
		mav.addObject("today", 					DateUtil.getToday("yyyy.MM.dd"));
		mav.addObject("groupSize", 				groupSize);
		mav.addObject("groupList", 				groupList);
		mav.addObject("apprDoc", 				apprDoc);
		mav.addObject("apprForm", 				apprForm);
		mav.addObject("apprLineList", 			apprLineList);
		mav.addObject("apprReceiveLineList", 	apprReceiveLineList);
		mav.addObject("apprReferenceList", 		apprReferenceList);
		mav.addObject("fileDataListJson", 		fileDataListJson);
		mav.addObject("apprPeriodList", 		apprPeriodList);
		mav.addObject("searchCondition", 		apprFormSearchCondition);
		mav.addObject("isSystemAdmin", 			this.isSystemAdmin(user));
		mav.addObject("isReadAll", 				this.isReadAll(user.getPortalId()));
		mav.addObject("apprConfig", apprConfig);	// 문서결재 기본 설정 정보
		return mav;
	}
	
	/**
	 * 기안문 저장
	 * @param	ApprDoc
	 * @return 	리다이렉트
	 */
	@RequestMapping(value = "/createApprDoc.do", method = RequestMethod.POST)
	public String createApprDoc(
			@ValidEx(groups = { CreateApprDoc.class }) ApprDoc apprDoc, BindingResult result, SessionStatus status, Model model, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}
		
		String linkType = apprDoc.getLinkType()==null || "".equals(apprDoc.getLinkType())?"":apprDoc.getLinkType();
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// userInfo settion
		apprDoc.setPortalId(user.getPortalId());
		apprDoc.setRegisterId(user.getUserId());
		apprDoc.setRegisterName(user.getUserName());
		apprDoc.setUpdaterId(user.getUserId());
		apprDoc.setUpdaterName(user.getUserName());

		// apprDoc 생성
		apprWorkDocService.createApprDoc(apprDoc, user,request);
		
		status.setComplete();
		
		String listType = apprDoc.getApprDocStatus()==0?"tempList":"myRequestList"; 
		return "redirect:/approval/work/apprWorkDoc/viewApprDoc.do?apprId=" + apprDoc.getApprId() + "&listType=" + listType + "&linkType=" + linkType;
	}

	/**
	 * 기안문 상세
	 * 
	 * @param 	apprId
	 * @param 	listType
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/viewApprDoc.do")
	public ModelAndView viewApprDoc(@RequestParam(value = "apprId", required = true) String apprId,
									@RequestParam(value = "listType", required = true) String listType,
									@RequestParam(value = "entrustUserId", required = false) String entrustUserId,
									@RequestParam(value = "relApprId", required = false) String relApprId,
									@RequestParam(value = "linkType", required = false, defaultValue = "") String linkType,
									@RequestParam(value = "popupYn", required = false, defaultValue = "false") Boolean popupYn,
									ApprListSearchCondition apprListSearchCondition) 
			throws JsonGenerationException, JsonMappingException, IOException{
		Long past = System.currentTimeMillis();	 
		// tiles
		String link = "";
		if(popupYn){
			link = "Popup";
		}else if(!"".equals(linkType)){
			link = linkType;
		}
		
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/viewApprDoc"+ link);
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		boolean isSystemAdmin = this.isSystemAdmin(user);
		boolean isReadAll = this.isReadAll(user.getPortalId());
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());
		
		// apprDoc
		ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
		
		apprDoc.setListType(listType);
		apprDoc.setLinkType(linkType);
		apprDoc.setUserId(user.getUserId());
		apprDoc.setPopupYn(popupYn);
		apprDoc.setFolderId(apprListSearchCondition.getFolderId());
		
		// 위임/수임
		if(entrustUserId!=null && !"".equals(entrustUserId)){
			if(listType.equals("listApprEntrust")) {
				boolean eId = apprEntrustService.isSignUser(apprListSearchCondition.getEntrustId());
				if(!eId){
					throw new IKEP4AuthorizedException();
				}
				apprDoc.setEntrustUserId(entrustUserId);
				apprDoc.setEntrustType(apprListSearchCondition.getEntrustType());
			}else {
				if(!apprEntrustService.isEntrust(entrustUserId, user.getUserId())){
					throw new IKEP4AuthorizedException();
				}
				apprDoc.setEntrustUserId(entrustUserId);
			}
		}
		
		// parent apprDoc
		ApprDoc 		parentApprDoc	=	new ApprDoc();
		List<ApprLine>	parentApprLineList	=	new ArrayList<ApprLine>();
		if(apprDoc.getParentApprId()!=null){
			Map<String, String> parentMap = new HashMap<String, String>();
			parentMap.put("apprId", 	apprDoc.getParentApprId());
			parentMap.put("locale",   user.getLocaleCode());	
			
			parentApprDoc = apprWorkDocService.readApprDoc(parentMap);
			
			// 결재선
			parentMap.put("apprType", 	"0");		
			parentApprLineList = apprLineService.listApprLine(parentMap);
		
		}
		
		// apprForm data
		ApprForm apprForm = apprAdminFormService.readApprForm(apprDoc.getFormId());
		
		// 공람확인여부
		if(listType.equals("listApprDisplayWaiting")) {
			String displayId = apprDisplayService.getApprDisplayId(user.getUserId(), apprId);
			if(displayId != null && !displayId.equals("")) {
				apprDisplayService.updateApprDisplaySub(displayId, user.getUserId());
			}
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
		
		// 위임 정보 세팅
		if(apprLine!=null && entrustUserId!=null && !entrustUserId.equals("")){
			apprLine.setEntrustUserId(entrustUserId);
		}
		
		if(apprLine !=null && apprLine.getViewDate()==null && apprLine.getApprStatus()==1){			
			apprLineService.updateRead(apprLine);
		}
		
		// 읽기, 버튼권한 
		ApprDocAuth apprDocAuth = null;
		
		//  기결제 문서
		if("viewRelationDoc".equals(listType) && relApprId!=null && !relApprId.equals("")){
			List <ApprDoc> relApprRelationsList =  apprWorkDocService.getApprRelationsList(relApprId);
			// 첨부된 기결제 문서 체크
			boolean hasRel = false;
			for(int i=0;i<relApprRelationsList.size();i++){
				if(apprId.equals(relApprRelationsList.get(i).getApprId())){
					hasRel = true;
					break;
				}
			}
			
			if(hasRel)
				apprDocAuth = apprWorkDocService.setApprDocAuth(relApprId, listType, user, entrustUserId, isSystemAdmin, isReadAll);
			else
				throw new IKEP4AuthorizedException();
		}else{
			apprDocAuth = apprWorkDocService.setApprDocAuth(apprDoc, apprLine, user, isSystemAdmin, isReadAll);
		}
		
		// 읽기권한이 없을시
		if(!apprDocAuth.isHasReadAuth()){
			throw new IKEP4AuthorizedException();
		}

		
		// 참조자 문서 조회 및 참조의견 입력여부
		map.put("userId", 	user.getUserId());
		map.put("isMessage", 	"1");
		boolean hasReference=apprReferenceService.exists(map);
		if(hasReference){
			apprReferenceService.updateRead(map);
			apprDocAuth.setHasReference(hasReference);
		}
		
		
		// 결재선
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("apprId", 		apprId);
		map2.put("apprType", 	"0");		
		List<ApprLine> apprLineList = apprLineService.listApprLine(map2);
		
		// apprType : 3 인경우 결재 수신정보만 
		map2.put("apprType", "3");			
		List<ApprLine> apprReceiveLineList = apprLineService.listApprLine(map2);
		
		// 열람권한 사용자
		List <User> apprReadUserList   =  apprWorkDocService.getApprReadUserList(map);

		// 열람권한 그룹
		List <Group> apprReadGroupList =  apprWorkDocService.getApprReadGroupList(map);
		
		// 참조자
		List <User> apprReferenceList =  apprWorkDocService.getApprReferenceList(map);
		
		// 기결재
		List <ApprDoc> apprRelationsList =  apprWorkDocService.getApprRelationsList(apprId);
		
		// 파일 목록을 JSON으로 변환한다.
		ObjectMapper mapper = new ObjectMapper();
		
		String fileDataListJson = null;
		if (apprDoc.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(apprDoc.getFileDataList());
		}
	
		
		// 문서결재 기본 설정 정보 ( 결재취소,결재선/문서/참조,열람권 수정 체크 )
		ApprAdminConfig apprConfig= new ApprAdminConfig();
		apprConfig = apprAdminConfigService.adminConfigDetail(user.getPortalId());	
		
		mav.addObject("apprDoc", 				apprDoc);
		mav.addObject("apprForm", 				apprForm);
		mav.addObject("apprDocAuth", 			apprDocAuth);
		mav.addObject("apprLineList", 			apprLineList);
		mav.addObject("apprReceiveLineList", 	apprReceiveLineList);
		mav.addObject("apprReadUserList", 		apprReadUserList);
		mav.addObject("apprReadGroupList", 		apprReadGroupList);
		mav.addObject("apprReferenceList", 		apprReferenceList);
		mav.addObject("apprRelationsList", 		apprRelationsList);
		mav.addObject("fileDataListJson", 		fileDataListJson);
		mav.addObject("parentApprDoc", 			parentApprDoc);
		mav.addObject("parentApprLineList", 	parentApprLineList);
		mav.addObject("isSystemAdmin", 			isSystemAdmin);
		mav.addObject("isReadAll", 				isReadAll);
		mav.addObject("apprListSearchCondition",apprListSearchCondition);
		mav.addObject("apprConfig", apprConfig);	// 문서결재 기본 설정 정보
		
		Long present = System.currentTimeMillis();

		this.log.info("조회 시간 소모시간 : " + (present - past));

		
		return mav;
	}
	
	/**
	 * 기안문 이력 목록
	 * 
	 * @param 	apprId
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/listApprDocHistory.do", method = RequestMethod.GET)
	public ModelAndView listApprDocHistory(@RequestParam(value = "apprId", required = true) String apprId){
				
		// tiles
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/listApprDocHistory");

		// session
		//User user = (User) getRequestAttribute("ikep.user");
		
		// 수신자
		List <ApprDoc> apprDocHistoryList =  apprWorkDocService.getApprDocHistoryList(apprId);
		
		List<ApprLine> apprLineVersionList = apprLineService.listVersion(apprId);
		List<ApprLine> versionList = apprLineService.groupByVersion(apprId);
		
		ApprDoc apprDoc = new ApprDoc();
		apprDoc.setApprId(apprId);
		
		mav.addObject("apprDoc", 				apprDoc);
		mav.addObject("apprDocHistoryList", 	apprDocHistoryList);
		mav.addObject("apprLineVersionList", 	apprLineVersionList);
		mav.addObject("versionList", 	versionList);
		return mav;
	}
	
	/**
	 * 기안문 수정폼
	 * 
	 * @param 	apprId
	 * @param 	listType
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/updateApprDocForm.do", method = RequestMethod.GET)
	public ModelAndView updateApprDocForm(	@RequestParam(value = "apprId", required = true) String apprId,
											@RequestParam(value = "listType", required = true) String listType,
											@RequestParam(value = "linkType", required = false, defaultValue = "") String linkType,
											@RequestParam(value = "entrustUserId", required = false) String entrustUserId) 
			throws JsonGenerationException, JsonMappingException, IOException{
				
		// tiles
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/updateApprDocForm" + linkType);

		// session
		User user = (User) getRequestAttribute("ikep.user");
		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("userId", user.getUserId());
		List <Group> groupList = groupService.selectUserGroup(groupMap);	
		int groupSize = groupList.size();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());
		
		// apprDoc
		ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
		apprDoc.setListType(listType);
		apprDoc.setLinkType(linkType);
		
		// 위임
		if(entrustUserId!=null && !"".equals(entrustUserId)){
			
			if(!apprEntrustService.isEntrust(entrustUserId, user.getUserId())){
				throw new IKEP4AuthorizedException();
			}
			
			apprDoc.setEntrustUserId(entrustUserId);
		}
		
		// 임시저장 - 문서전체수정
		if("tempList".equals(listType)){
			if(apprDoc.getApprDocStatus()==0 && user.getUserId().equals(apprDoc.getRegisterId())){
				apprDoc.setMode("all");
			}else{
				throw new IKEP4AuthorizedException();
			}
		
		} else if("rejectList".equals(listType)){
			// 회수 - 재기안(수정)
			if(apprDoc.getApprDocStatus()==4 && user.getUserId().equals(apprDoc.getRegisterId())){
				apprDoc.setMode("all");
			// 반려 - 재기안(신규)
			} else if(apprDoc.getApprDocStatus()==3 && user.getUserId().equals(apprDoc.getRegisterId())){
				apprDoc.setMode("copy");
			}else{
				throw new IKEP4AuthorizedException();
			}
		
		// 결재해야할 문서 - 문서내용수정
		} else if("listApprTodo".equals(listType)){
			
			ApprLine	apprLine =	new ApprLine();
			apprLine.setApprId(apprId);
			
			if(entrustUserId!=null && !entrustUserId.equals("")){
				apprLine.setApproverId(entrustUserId);
			} else {
				apprLine.setApproverId(user.getUserId());
			}

			apprLine = apprLineService.readLine(apprLine);
			
			if(apprLine.getDocModifyAuth()==1 && apprDoc.getApprDocStatus()==1){
				apprDoc.setMode("content");
			}else{
				throw new IKEP4AuthorizedException();
			}
			
		// 내가 기안한 문서, 전체문서, 부서결재, 기안한문서 - 재기안(신규)
		} else if("myRequestList".equals(listType) || "listApprIntegrate".equals(listType) || "listApprDeptIntegrate".equals(listType) || "myRequestListComplete".equals(listType)){
			if(apprDoc.getApprDocStatus()==2 && user.getUserId().equals(apprDoc.getRegisterId())){
				apprDoc.setMode("copy");
			}else{
				throw new IKEP4AuthorizedException();
			}	
		} else {
			throw new IKEP4AuthorizedException();
		}
		
		// apprForm data
		ApprForm apprForm = apprAdminFormService.readApprForm(apprDoc.getFormId());
		
		// 결재선
		List<ApprLine> apprLineList = null;
		
		// 결재선 - 수신처
		List<ApprLine> apprReceiveLineList = null;
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("apprId", 		apprId);
		map2.put("apprType", 	"0");		
		apprLineList = apprLineService.listApprLine(map2);
		
		// apprType : 3 인경우 결재 수신정보만 
		map2.put("apprType", "3");			
		apprReceiveLineList = apprLineService.listApprLine(map2);	
		
		// 열람권한 사용자
		List <User> apprReadUserList   =  apprWorkDocService.getApprReadUserList(map);

		// 열람권한 그룹
		List <Group> apprReadGroupList =  apprWorkDocService.getApprReadGroupList(map);
		
		// 참조자
		List <User> apprReferenceList =  apprWorkDocService.getApprReferenceList(map);
		
		// 기결재
		List <ApprDoc> apprRelationsList =  apprWorkDocService.getApprRelationsList(apprId);
		
		// 파일 목록을 JSON으로 변환한다.
		ObjectMapper mapper = new ObjectMapper();
		
		//- form 첨부파일
		String formFileDataListJson = null;
		if (apprForm.getFileDataList() != null) {
			formFileDataListJson = mapper.writeValueAsString(apprForm.getFileDataList());
		}
		
		//- doc 첨부파일
		String docFileDataListJson = null;
		if (apprDoc.getFileDataList() != null) {
			docFileDataListJson = mapper.writeValueAsString(apprDoc.getFileDataList());
		}
		
		// 보존연한 목록
		List<ApprCode> apprPeriodList 	= this.apprCodeService.listGroupApprCodeByValue(CommonCode.APPR_PERIOD, user.getLocaleCode());
		
		ApprAdminConfig apprConfig= new ApprAdminConfig();
		apprConfig = apprAdminConfigService.adminConfigDetail(user.getPortalId());
		
		mav.addObject("today", 					DateUtil.getToday("yyyy.MM.dd"));
		mav.addObject("apprDoc", 				apprDoc);
		mav.addObject("apprForm", 				apprForm);
		mav.addObject("groupSize", 				groupSize);
		mav.addObject("groupList", 				groupList);
		mav.addObject("apprLineList", 			apprLineList);
		mav.addObject("apprReceiveLineList", 	apprReceiveLineList);
		mav.addObject("apprReadUserList", 		apprReadUserList);
		mav.addObject("apprReadGroupList", 		apprReadGroupList);
		mav.addObject("apprReferenceList", 		apprReferenceList);
		mav.addObject("apprRelationsList", 		apprRelationsList);
		mav.addObject("formFileDataListJson", 	formFileDataListJson);
		mav.addObject("docFileDataListJson", 	docFileDataListJson);
		mav.addObject("apprPeriodList", 		apprPeriodList);
		mav.addObject("isSystemAdmin", 			this.isSystemAdmin(user));
		mav.addObject("isReadAll", 				this.isReadAll(user.getPortalId()));
		mav.addObject("apprConfig", 			apprConfig);
		return mav;
	}
	
	/**
	 * 기안문 수정
	 * @param	ApprDoc
	 * @return 	리다이렉트
	 */
	@RequestMapping(value = "/updateApprDoc", method = RequestMethod.POST)
	public String updateApprDoc(
			@ValidEx(groups = { UpdateApprDoc.class }) ApprDoc apprDoc, BindingResult result, SessionStatus status, Model model, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		/* to do 위임자 체크 */
		String entrustUserId = "";
		if(apprDoc.getEntrustUserId()!=null && !"".equals(apprDoc.getEntrustUserId())){
			
			if(!apprEntrustService.isEntrust(apprDoc.getEntrustUserId(), user.getUserId())){
				throw new IKEP4AuthorizedException();
			}
			
			entrustUserId = apprDoc.getEntrustUserId();
		}
		
		// userInfo settion
		apprDoc.setRegisterId(user.getUserId());
		apprDoc.setRegisterName(user.getUserName());
		apprDoc.setUpdaterId(user.getUserId());
		apprDoc.setUpdaterName(user.getUserName());
		
		// doc info
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprDoc.getApprId());
		map.put("locale",   user.getLocaleCode());
		ApprDoc tmpApprDoc = apprWorkDocService.readApprDoc(map);
		
		// 수정범위
		if(tmpApprDoc.getApprDocStatus()==0 || tmpApprDoc.getApprDocStatus()==4){ // 임시, 회수
			if(tmpApprDoc.getRegisterId().equals(apprDoc.getRegisterId())){ // 본인여부
				// apprDoc 수정
				apprWorkDocService.updateApprDoc(apprDoc, user, request);
				
				if(apprDoc.getApprDocStatus()==1) apprDoc.setListType("myRequestList");
			}else{
				throw new IKEP4AuthorizedException();
			}
		}else{
			ApprLine	apprLine =	new ApprLine();
			apprLine.setApprId(apprDoc.getApprId());
			
			if(!"".equals(entrustUserId)){
				apprLine.setApproverId(entrustUserId);
			} else {
				apprLine.setApproverId(user.getUserId());
			}
			
			apprLine = apprLineService.readLine(apprLine);
			
			if(apprLine.getDocModifyAuth()!=1){
				throw new IKEP4AuthorizedException();
			}else{
				// apprDoc Meta 생성
				apprWorkDocService.updateApprDocContent(apprDoc, user);
			}
		}
		
		String linkType = apprDoc.getLinkType()==null || "".equals(apprDoc.getLinkType())?"":apprDoc.getLinkType();
		
		status.setComplete();
		
		return "redirect:/approval/work/apprWorkDoc/viewApprDoc.do?apprId=" + apprDoc.getApprId() + "&listType=" + apprDoc.getListType() + "&entrustUserId=" + entrustUserId + "&linkType=" + linkType;
	}

	/**
	 * 부서합의 / 수신처 문서 결재 요청
	 * @param	ApprDoc
	 * @return 	리다이렉트
	 */
	@RequestMapping(value = "/updateChildApprDoc", method = RequestMethod.POST)
	public String updateChildApprDoc(ApprDoc apprDoc, SessionStatus status, Model model, HttpServletRequest request) {
		
 		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// userInfo settion
		apprDoc.setRegisterId(user.getUserId());
		apprDoc.setRegisterName(user.getUserName());
		apprDoc.setUpdaterId(user.getUserId());
		apprDoc.setUpdaterName(user.getUserName());
		apprDoc.setRegisterJobTitle(user.getJobTitleName());
		// doc info

		apprWorkDocService.updateChildApprDoc(apprDoc,user,request);
		
 
		
		status.setComplete();
		
		String listType = apprDoc.getApprDocStatus()==1?"myRequestList":apprDoc.getListType(); 
		return "redirect:/approval/work/apprWorkDoc/viewApprDoc.do?apprId=" + apprDoc.getApprId() + "&listType=" + listType;
	}	
	
	
	/**
	 * 기안문 삭제
	 * 
	 * @param 	apprId
	 * @return	결과
	 */
	@RequestMapping(value = "/deleteApprDoc.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String deleteApprDoc(String apprId,
			SessionStatus status) {
		
		String returnValue = "";
		try{
			// session
    		User user = (User) getRequestAttribute("ikep.user");
    		
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("apprId", 	apprId);
    		map.put("locale",   user.getLocaleCode());
    		
    		// apprDoc
    		ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
    		apprDoc.setUpdaterId(user.getUserId());
    		
    		// 삭제 가능 문서상태 임시(0), 반려(3), 회수(4)
    		boolean b = apprDoc.getApprDocStatus()==0 || apprDoc.getApprDocStatus()==3  || apprDoc.getApprDocStatus()==4;
    		
    		// 관리자 또는 문서상태&기안자
    		if(this.isSystemAdmin(user) || (b && user.getUserId().equals(apprDoc.getRegisterId()))){
        		// apprDoc 삭제
        		apprWorkDocService.deleteApprDoc(apprDoc.getApprId());
        		returnValue = "true";
    		}else{
    			returnValue = "false";
    		}
    		
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteApprDoc", ex);
		}
		
		return returnValue;
	}	
	
	/**
	 * 기안의견
	 * 
	 * @param 	nothing
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/viewApprDocRegisterMessage.do", method = RequestMethod.GET)
	public ModelAndView viewApprDocRegisterMessage() {
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/viewApprDocRegisterMessage");
		return mav;
	}
	
	/**
	 * 기안문 인쇄
	 * 
	 * @param 	nothing
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/viewApprDocPrint.do")
	public ModelAndView viewApprDocPrint() {
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/viewApprDocPrint");
		return mav;
	}
	
	/**
	 * 기안문 PC 저장
	 * 
	 * @param 	nothing
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/createApprDocPC.do")
	public ModelAndView createApprDocPC() {
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/createApprDocPC");
		return mav;
	}
	
	/**
	 * 기안문 변경사유
	 * 
	 * @param 	nothing
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/viewApprDocChangeReason.do", method = RequestMethod.GET)
	public ModelAndView viewApprDocChangeReason() {
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/viewApprDocChangeReason");
		return mav;
	}
	
	/**
	 * 참조자 수정
	 * 
	 * @param 
	 * @param apprId
	 * @param referenceId
	 * @return
	 */
	@RequestMapping(value = "/ajaxSetReference.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String ajaxSetReference(String apprId, String referenceId, String entrustUserId,
			SessionStatus status) {
		
		String returnValue = "";
		try{
			
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			map.put("locale",   user.getLocaleCode());
			
			// apprDoc
			ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
			
			String userId = "";
			String approvalId = "";
			boolean process = false;
			
			//위임시
			if(entrustUserId!=null && !entrustUserId.equals("")){
				if(apprEntrustService.isEntrust(entrustUserId, user.getUserId())) {
					process = true;
					approvalId = entrustUserId;
					userId = entrustUserId;
				}
				
			}else{
				process = true;
				approvalId = user.getUserId();
				userId = user.getUserId();
			}
			
			if(apprDoc.getRegisterId().equals(userId)){
				// 기안자, 수임자
			}else if(process){
				ApprLine apprLine =	new ApprLine();
				apprLine.setApprId(apprId);
				apprLine.setApproverId(approvalId);
				
				apprLine = apprLineService.readLine(apprLine);
				
				if(apprLine!=null && apprLine.getReadModifyAuth()==1){
					// 권한 있음
				}else{
					process = false;
				}
			}
			
			if(process){
				apprWorkDocService.tranApprReference("edit", apprId, referenceId);
				returnValue = "true";
			}else{
				returnValue = "false";
			}
			
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxSetReference", ex);
		}
		
		return returnValue;
	}	
	
	/**
	 * 열람권한 수정
	 * @param 
	 * @param 	apprId
	 * @param 	readId
	 * @return
	 */
	@RequestMapping(value = "/ajaxSetRead.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String ajaxSetRead(String apprId, String readId, String entrustUserId,
			SessionStatus status) {
		
		String returnValue = "";
		try{
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			map.put("locale",   user.getLocaleCode());
			
			// apprDoc
			ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
			
			String userId = "";
			String approvalId = "";
			boolean process = false;
			
			//위임시
			if(entrustUserId!=null && !entrustUserId.equals("")){
				if(apprEntrustService.isEntrust(entrustUserId, user.getUserId())) {
					process = true;
					approvalId = entrustUserId;
					userId = entrustUserId;
				}
				
			}else{
				process = true;
				approvalId = user.getUserId();
				userId = user.getUserId();
			}
			
			if(apprDoc.getRegisterId().equals(userId)){
				// 기안자, 수임자
			}else if(process){
				ApprLine apprLine =	new ApprLine();
				apprLine.setApprId(apprId);
				apprLine.setApproverId(approvalId);
				
				apprLine = apprLineService.readLine(apprLine);
				
				if(apprLine!=null && apprLine.getReadModifyAuth()==1){
					// 권한 있음
				}else{
					process = false;
				}
			}
			
			if(process){
				apprWorkDocService.tranApprRead("edit", apprId, readId, user.getUserId(), user.getUserName());
				returnValue = "true";
			}else{
				returnValue = "false";
			}
			
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxSetRead", ex);
		}
		
		return returnValue;
	}	
	
	/**
	 * 결재 회수
	 * 
	 * @param 
	 * @param 	apprId
	 * @param 	String
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateApprDocInit.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String ajaxUpdateApprDocInit(String apprId,
			SessionStatus status,
			@RequestParam(value = "entrustUserId", required = false) String entrustUserId) {
		
		String returnValue = "";
		
		try{
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// 기결재된건 체크
			boolean beforeAppr = apprLineService.beforeAppr(apprId);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			map.put("locale",   user.getLocaleCode());
			
			// apprDoc
			ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
			
			//문서상태 (0:임시저장, 1:진행중, 2:완료, 3:반려, 4:회수, 5:오류,6:접수대기 ..)
			if(apprDoc.getApprDocStatus()!=1) {// 
				returnValue = "DOC"+apprDoc.getApprDocStatus();
			} else {
			
			
				// 결제된 건이 없고 등록자 만 변경 할수 있음
				if((beforeAppr==false && user.getUserId().equals(apprDoc.getRegisterId()))){
					ApprDoc apprDoc2 = new ApprDoc();
					apprDoc2.setApprId(apprId);
					if(apprDoc.getParentApprId()!=null && !apprDoc.getParentApprId().equals(""))
						apprDoc2.setApprDocStatus(6); // Child Doc 인경우 대기상태로 변경
					else
						apprDoc2.setApprDocStatus(4); // 회수
					apprDoc2.setUpdaterId(user.getUserId());
					apprDoc2.setUpdaterName(user.getUserName());
					
					apprLineService.updateRecoveryAppr(apprId,user.getLocaleCode());
					
					apprWorkDocService.updateApprDocStatus(apprDoc2);
					status.setComplete();
					returnValue= "OK";
				}else{
					status.setComplete();
					returnValue = "false";
				}
			}
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxUpdateApprDocInit", ex);
		}
		return returnValue;
	}	
	/**
	 * 관리자>결재문서관리>문서조회>결재복원> 결재 회수클릭
	 * 
	 * @param 
	 * @param 	apprId
	 * @param 	String
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateApprDocInitAdmin.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String ajaxUpdateApprDocInitAdmin(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "ApprovalMgnt", operationName = { "MANAGE" }, resourceName = "ApprovalMgnt")) AdminSearchCondition searchCondition,
			AccessingResult accessResult,String apprId,	SessionStatus status) {
		

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		try{
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			
			ApprDoc apprDoc = new ApprDoc();
			apprDoc.setApprId(apprId);
			apprDoc.setApprDocStatus(4); // 결재회수처리
			apprDoc.setUpdaterId(user.getUserId());
			apprDoc.setUpdaterName(user.getUserName());
			
			// 결재선 정보 초기화
			apprLineService.updateRecoveryAppr(apprId,user.getLocaleCode());
			
			// 해당문서의 검토내용 삭제처리
			apprExamService.remove(map);
			
			// 문서 결재회수 처리
			apprWorkDocService.updateApprDocStatus(apprDoc);
			
			status.setComplete();
			return "OK";

		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxUpdateApprDocInit", ex);
		}
	}
	
	/**
	 * 문서 정보 검색
	 * 
	 * @param 	apprId
	 * @return	apprDoc
	 */
	@RequestMapping("/ajaxReadApprDoc.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ApprDoc ajaxReadApprDoc(String apprId) {
		ApprDoc apprDoc = null;
		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			map.put("locale",   user.getLocaleCode());
			
			apprDoc = apprWorkDocService.readApprDoc(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxReadApprDoc", ex);
		}
		return apprDoc;
	}
}