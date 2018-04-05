/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.model.ApprForm.CreateApprForm;
import com.lgcns.ikep4.approval.admin.model.ApprForm.UpdateApprContentForm;
import com.lgcns.ikep4.approval.admin.model.ApprForm.UpdateApprInfoForm;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprAdminFormService;
import com.lgcns.ikep4.approval.admin.service.ApprCodeService;
import com.lgcns.ikep4.approval.admin.service.ApprDefLineService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.web.TreeMaker;

/**
 * ApprAdminForm 컨트롤러
 * 
 * @author wonchu
 * @version $Id: FormController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value="/approval/admin/apprAdminForm")
@SessionAttributes("apprAdminForm")
public class ApprAdminFormController extends BaseController {

	@Autowired
	private ApprAdminFormService apprAdminFormService;
	
	@Autowired
	private ApprCodeService apprCodeService;
	
	@Autowired
	private	ApprDefLineService	apprDefLineService;
	
	@Autowired
	private ApprAdminConfigService apprAdminConfigService;
	
	@Autowired
	private ACLService aclService;
	
	
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
	 * apprForm 카테고리 트리 및 목록 템플릿 - 메뉴클릭
	 * 
	 * @param 	nothing
	 * @return 	ModelAndView
	 */
	@RequestMapping(value = "/listApprForm.do", method = RequestMethod.GET)
	public ModelAndView listApprForm() {
	
		// tiles
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/listApprForm");
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// 관리자 여부 체크
		if(!this.isSystemAdmin(user)){
			throw new IKEP4AuthorizedException();
		}
		
		// category root 코드 아이디를 구함
		String formParentId = StringUtil.nvl(apprCodeService.getCodeIdByCodeValue(CommonCode.APPR_CATEGORY, user.getPortalId()), "");
		
		// category tree json 생성
		String categoryTreeJson = "{}";		
		if(!"".equals(formParentId)){
			List <ApprCode> apprCodeList = apprCodeService.getApprCodeList(formParentId, user.getLocaleCode(), "9");
			categoryTreeJson = TreeMaker.init(apprCodeList, "codeId", "parentCodeId", "childCount").create().toJsonString();
		}
		
		// 디폴트 검색 조건 셋팅
		ApprFormSearchCondition apprFormSearchCondition = new ApprFormSearchCondition();
		apprFormSearchCondition.setSortColumn("FORM_PARENT_ID");
		apprFormSearchCondition.setSortType("ASC");
		apprFormSearchCondition.setFormParentId(formParentId);
		apprFormSearchCondition.setTopFormParentId(formParentId);
		apprFormSearchCondition.setUsage(9);
		
		// 카테고리명 조회
		ApprCode apprCode = apprCodeService.readApprCode(formParentId);
		if(apprCode!=null){
			apprFormSearchCondition.setFormParentName("ko".equals(user.getLocaleCode())?apprCode.getCodeKrName():apprCode.getCodeEnName());
		}
		
		// set return info
		mav.addObject("searchCondition", 	apprFormSearchCondition);
		mav.addObject("categoryTreeJson", 	categoryTreeJson);
		mav.addObject("isSystemAdmin", 		this.isSystemAdmin(user));
		mav.addObject("isReadAll", 			this.isReadAll(user.getPortalId()));
		
		return mav;
	}
	
	@RequestMapping(value = "/listApprForm.do", method = RequestMethod.POST)
	public ModelAndView listApprForm(
			ApprFormSearchCondition apprFormSearchCondition) {
	
		// tiles
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/listApprForm");
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// 관리자 여부 체크
		if(!this.isSystemAdmin(user)){
			throw new IKEP4AuthorizedException();
		}
		
		// category tree json 생성
		String categoryTreeJson = "{}";
		List <ApprCode> apprCodeList = apprCodeService.getApprCodeList(apprFormSearchCondition.getTopFormParentId(), user.getLocaleCode(), String.valueOf(apprFormSearchCondition.getUsage()));
		categoryTreeJson = TreeMaker.init(apprCodeList, "codeId", "parentCodeId", "childCount").create().toJsonString();
		
		// 카테고리명 조회
		ApprCode apprCode = apprCodeService.readApprCode(apprFormSearchCondition.getFormParentId());
		if(apprCode!=null){
			apprFormSearchCondition.setFormParentName("ko".equals(user.getLocaleCode())?apprCode.getCodeKrName():apprCode.getCodeEnName());
		}
		
		// set return info
		mav.addObject("searchCondition", 	apprFormSearchCondition);
		mav.addObject("categoryTreeJson", 	categoryTreeJson);
		mav.addObject("isSystemAdmin", 		this.isSystemAdmin(user));
		mav.addObject("isReadAll", 			this.isReadAll(user.getPortalId()));
		
		return mav;
	}
	
	/**
	 * apprForm AJAX 목록
	 * 
	 * @param 	ApprFormSearchCondition
	 * @return 	ModelAndView
	 */
	@RequestMapping(value = "/ajaxListApprForm.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView ajaxListApprForm(
			ApprFormSearchCondition apprFormSearchCondition) {
		
		// jsp 페이지에 include는 html을 생성하기 때문에 tiles를 사용 안함
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/ajaxListApprForm");
		
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// 기본정보 셋팅
			apprFormSearchCondition.setPortalId(user.getPortalId());
			apprFormSearchCondition.setLocaleCode(user.getLocaleCode());
			apprFormSearchCondition.setMode("A");
			
			// 폼 목록
			SearchResult<ApprForm> searchResult = apprAdminFormService.listBySearchCondition(apprFormSearchCondition);
			
			// 페이지 넘버
			CommonCode commonCode = new CommonCode();
			
			//set return info
			mav.addObject("searchResult", 		searchResult);
			mav.addObject("numList", 			commonCode.getPageNumList());
			mav.addObject("searchCondition", 	searchResult.getSearchCondition());
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxListApprForm", ex);
		}
		return mav;
	}
	
	/**
	 * apprForm 등록  화면 이동 
	 * 
	 * @param 	formParentId
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/createApprFormForm.do", method = RequestMethod.GET)
	public ModelAndView createApprFormForm(
			@RequestParam(value = "formParentId", required = true) String formParentId) {
		
		// tiles
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/createApprFormForm");
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// 관리자 여부 체크
		if(!this.isSystemAdmin(user)){
			throw new IKEP4AuthorizedException();
		}
		
		// system
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", user.getPortalId());
		map.put("localeCode", user.getLocaleCode());		
		List<ApprForm> apprSystemList = apprAdminFormService.getApprSystem(map);
		
		// 양식 DTO
		ApprForm apprForm = new ApprForm();
		String formParentName ="";
		if(!"".equals(formParentId)){ // root 양식 유형을 제외하고는 formParentId 값이 넘어옴
			ApprCode apprCode = apprCodeService.readApprCode(formParentId);
			formParentName = "ko".equals(user.getLocaleCode())?apprCode.getCodeKrName():apprCode.getCodeEnName();
		}
		
		// 디폴트 셋팅
		apprForm.setApprPeriodCd(StringUtil.nvl(apprCodeService.getCodeIdByCodeValue(CommonCode.APPR_PERIOD_DEFAULT_CODE, user.getPortalId()), ""));
		apprForm.setIsDefLineUpdate(CommonCode.APPR_IS_DEF_LINE_UPDATE);
		apprForm.setFormParentId(formParentId);
		apprForm.setFormParentName(formParentName);
		
		// 보존연한 목록
		List<ApprCode> apprPeriodList 	= this.apprCodeService.listGroupApprCodeByValue(CommonCode.APPR_PERIOD, user.getLocaleCode());

		//set return info
		mav.addObject("apprForm", 			apprForm);
		mav.addObject("apprSystemList", 	apprSystemList);
		mav.addObject("apprPeriodList", 	apprPeriodList);
		mav.addObject("isSystemAdmin", 		this.isSystemAdmin(user));
		mav.addObject("isReadAll", 			this.isReadAll(user.getPortalId()));
		
		return mav;
	}
	
	/**
	 * apprForm 등록
	 * @param	ApprForm
	 * @return 	리다이렉트
	 */
	@RequestMapping(value = "/createApprForm.do", method = RequestMethod.POST)
	public String createApprForm(
			@ValidEx(groups = { CreateApprForm.class }) ApprForm apprForm, BindingResult result, SessionStatus status, Model model) {
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// 관리자 여부 체크
		if(!this.isSystemAdmin(user)){
			throw new IKEP4AuthorizedException();
		}
		
		// userInfo settion
		apprForm.setPortalId(user.getPortalId());
		apprForm.setRegisterId(user.getUserId());
		apprForm.setRegisterName(user.getUserName());
		apprForm.setUpdaterId(user.getUserId());
		apprForm.setUpdaterName(user.getUserName());

		// apprForm 생성
		apprAdminFormService.createApprForm(apprForm);
		
		// category root 코드 아이디를 구함
		String formParentId = StringUtil.nvl(apprCodeService.getCodeIdByCodeValue(CommonCode.APPR_CATEGORY, user.getPortalId()), "");
		
		status.setComplete();
		String params = "?formId=" + apprForm.getFormId() + "&formParentId=" + formParentId + "&topFormParentId=" + formParentId + "&usage=9";
		return "redirect:/approval/admin/apprAdminForm/updateApprFormForm.do" + params;
	}
	
	/**
	 * apprForm 수정 화면 이동 
	 * 
	 * @param	ApprFormSearchCondition
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/updateApprFormForm.do")
	public ModelAndView updateApprFormForm(
			@RequestParam(value = "formId", required = true) String formId,
			@RequestParam(value = "formParentId", required = false) String formParentId,
			ApprFormSearchCondition apprFormSearchCondition) 
			throws JsonGenerationException, JsonMappingException, IOException {
		
		// tiles
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/updateApprFormForm");
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// 관리자 여부 체크
		if(!this.isSystemAdmin(user)){
			throw new IKEP4AuthorizedException();
		}
		
		// system
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", user.getPortalId());
		map.put("localeCode", user.getLocaleCode());	
		List<ApprForm> apprSystemList = apprAdminFormService.getApprSystem(map);
		
		// apprForm data
		ApprForm apprForm = apprAdminFormService.readApprForm(formId);
		
		// 수신참조자
		List <User> apprReferenceList   =  apprAdminFormService.getApprReferenceList(formId, user.getLocaleCode());
		
		// 파일 목록을 JSON으로 변환한다.
		ObjectMapper mapper = new ObjectMapper();
		String fileDataListJson = null;
		if (apprForm.getFileDataList() != null) {
			fileDataListJson = mapper.writeValueAsString(apprForm.getFileDataList());
		}
		
		// 보존연한 목록
		List<ApprCode> apprPeriodList 	= this.apprCodeService.listGroupApprCodeByValue(CommonCode.APPR_PERIOD, user.getLocaleCode());
		
		//set return info
		mav.addObject("apprForm", 					apprForm);
		mav.addObject("apprSystemList", 			apprSystemList);
		mav.addObject("apprReferenceList", 			apprReferenceList);
		mav.addObject("fileDataListJson", 			fileDataListJson);
		mav.addObject("apprPeriodList", 			apprPeriodList);
		mav.addObject("searchCondition", 			apprFormSearchCondition);
		mav.addObject("isSystemAdmin", 				this.isSystemAdmin(user));
		mav.addObject("isReadAll", 					this.isReadAll(user.getPortalId()));
		
		return mav;
	}
	
	/**
	 * apprForm 결제정보 변경
	 * @param	apprForm
	 * @return 	returnValue
	 */
	@RequestMapping(value = "/updateApprInfoForm.do", method = RequestMethod.POST)
	public 	@ResponseBody String updateApprInfoForm(
			@ValidEx(groups = { UpdateApprInfoForm.class }) ApprForm apprForm, BindingResult result, SessionStatus status) {
		
		String returnValue = "false";
		try{
			if (result.hasErrors()) {
				return returnValue;
			}
			
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// userInfo
			apprForm.setRegisterId(user.getUserId());
			apprForm.setRegisterName(user.getUserName());
			apprForm.setUpdaterId(user.getUserId());
			apprForm.setUpdaterName(user.getUserName());
			
			// 폼결제정보 변경
			apprAdminFormService.updateApprInfoForm(apprForm);
			returnValue = "true";
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateApprInfoForm", ex);
		}

		return returnValue;
		
	}
	
	/**
	 * apprForm 양식내용 변경
	 * @param	apprForm
	 * @return 	returnValue
	 */
	@RequestMapping(value = "/updateApprContentForm.do", method = RequestMethod.POST)
	public @ResponseBody String updateApprContentForm(
			@ValidEx(groups = { UpdateApprContentForm.class }) ApprForm apprForm, BindingResult result, SessionStatus status) {
		
		String returnValue = "false";
		try{
			if (result.hasErrors()) {
				return returnValue;
			}
		
			// session
			User user = (User) getRequestAttribute("ikep.user");
			
			// userInfo
			apprForm.setRegisterId(user.getUserId());
			apprForm.setRegisterName(user.getUserName());
			apprForm.setUpdaterId(user.getUserId());
			apprForm.setUpdaterName(user.getUserName());
	
			// 폼양식내용 변경
			apprAdminFormService.updateApprContentForm(apprForm, user);
			returnValue = "true";
			status.setComplete();
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateApprInfoForm", ex);
		}

		return returnValue;
	}
	
	/**
	 * apprForm 카테고리 
	 * 
	 * @param 	nothing
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/viewApprFormCategory.do")
	public ModelAndView viewApprFormCategory() {
		
		// tiles
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/viewApprFormCategory");
		
		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// root codeId
		String formParentId = StringUtil.nvl(apprCodeService.getCodeIdByCodeValue(CommonCode.APPR_CATEGORY, user.getPortalId()), "");
		
		// category tree json
		String categoryTreeJson = "{}";		
		if(!"".equals(formParentId)){
			List <ApprCode> apprCodeList = apprCodeService.getApprCodeList(formParentId, user.getLocaleCode(), "");
			categoryTreeJson = TreeMaker.init(apprCodeList, "codeId", "parentCodeId", "codeName").create().toJsonString();
		}
		
		//set return info
		mav.addObject("formParentId", 		formParentId);
		mav.addObject("categoryTreeJson", 	categoryTreeJson);
		
		return mav;
	}
	
	/**
	 * apprForm item 메인
	 * 
	 * @param 	nothing
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/viewApprFormItemMain.do", method = RequestMethod.GET)
	public ModelAndView viewApprFormItemMain() {
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/viewApprFormItemMain");
		return mav;
	}
	
	/**
	 * apprForm item
	 * 
	 * @param 	itemId
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/viewApprFormItem.do", method = RequestMethod.GET)
	public ModelAndView viewApprFormItem(@RequestParam("itemId") String itemId) {
				
		// tiles
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/item/" + itemId + "Tab");

		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// 코드 리스트
		if("select".equals(itemId) || "radio".equals(itemId) || "checkbox".equals(itemId)){
			List<ApprCode> apprCodeList = this.apprCodeService.listGroupApprCodeByValue(CommonCode.APPR_CODE, user.getLocaleCode());
			mav.addObject("apprCodeList", apprCodeList);
		}
		
		return mav;
	}
	
	/**
	 * formId item
	 * 
	 * @param 	itemId
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/listApprFormHistory.do", method = RequestMethod.GET)
	public ModelAndView listApprFormHistory(@RequestParam(value = "formId", required = true) String formId){
				
		// tiles
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/listApprFormHistory");

		// session
		User user = (User) getRequestAttribute("ikep.user");
		
		// 수신자
		List <ApprForm> apprFormHistoryList =  apprAdminFormService.getApprFormHistoryList(formId);
		
		ApprForm apprForm = new ApprForm();
		apprForm.setFormId(formId);
		
		mav.addObject("apprForm", 				apprForm);
		mav.addObject("apprFormHistoryList", 	apprFormHistoryList);
		return mav;
	}
	
	/**
	 * apprForm code 정보
	 * 
	 * @param 	codeId
	 * @return	ApprCode
	 */
	@RequestMapping(value = "/ajaxListCode.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List ajaxListCode(String codeId) {
		
		List <ApprCode> apprCodeList = null;
		
		try {
			User user = (User) getRequestAttribute("ikep.user");
			apprCodeList = apprCodeService.getApprCodeList(codeId, user.getLocaleCode(), "");
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxListCode", ex);
		}
		
		return apprCodeList;
	}
	
	/**
	 * 부서 정보 검색
	 * 
	 * @param 	code
	 * @return	Group
	 */
	@RequestMapping("/getGroup.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Group getGroup(String code) {
		Group group = null;
		try {
			User sessionuser = (User) getRequestAttribute("ikep.user");		
			group = apprAdminFormService.getGroup(code, sessionuser.getLocaleCode());
		} catch (Exception ex) {
			throw new IKEP4AjaxException("getGroup", ex);
		}
		return group;
	}
	
	/**
	 * 사용자 정보 검색
	 * 
	 * @param 	id
	 * @return	User
	 */
	@RequestMapping("/getUser.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody User getUser(String id) {
		User user = null;
		try {
			User sessionuser = (User) getRequestAttribute("ikep.user");		
			user = apprAdminFormService.getUser(id, sessionuser.getLocaleCode());
		} catch (Exception ex) {
			throw new IKEP4AjaxException("getUser", ex);
		}
		return user;
	}
}