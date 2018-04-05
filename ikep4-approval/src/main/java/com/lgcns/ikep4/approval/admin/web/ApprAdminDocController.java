/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.model.ApprDoc.PreviewApprDoc;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * ApprAdminDoc 컨트롤러
 * 
 * @author wonchu
 * @version $Id: FormController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value="/approval/admin/apprAdminDoc")
@SessionAttributes("apprAdminDoc")
public class ApprAdminDocController extends BaseController {

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
	 * apprDoc 작성  미리보기  
	 * 
	 * @param 	mode (0:모달창, 1:팝업호출, 2:미리보기, 3:버전이력)
	 * @return 	ModelAndView
	 */
	@RequestMapping(value = "/createApprDocLayout.do", method = RequestMethod.GET)
	public ModelAndView createApprDocLayout(@RequestParam(value = "mode", required = true) String mode) {
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/createApprDocLayout");
		ApprDoc apprDoc = new ApprDoc();
		apprDoc.setMode(mode);
		mav.addObject("apprDoc", 	apprDoc);
		return mav;
	}
	
	/**
	 * apprDoc 작성  미리보기  
	 * 
	 * @param 	ApprDoc
	 * @return 	ModelAndView
	 */
	@RequestMapping(value = "/createApprDocLayout.do", method = RequestMethod.POST)
	public ModelAndView createApprDocLayout(
			@ValidEx(groups = { PreviewApprDoc.class }) ApprDoc apprDoc,
			BindingResult result) {
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}
		
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/createApprDocLayout");
		mav.addObject("apprDoc", 	apprDoc);
		return mav;
	}
	
	/**
	 * apprDoc 상세  미리보기  
	 * 
	 * @param 	mode (4:apprDoc 수정이력)
	 * @return 	ModelAndView
	 */
	@RequestMapping(value = "/viewApprDocLayout", method = RequestMethod.GET)
	public ModelAndView viewApprDocLayout(@RequestParam(value = "mode", required = true) String mode) {
		
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/viewApprDocLayout");
		ApprDoc apprDoc = new ApprDoc();
		apprDoc.setMode(mode);
		mav.addObject("apprDoc", 	apprDoc);
		return mav;
	}
	
	/**
	 * apprDoc 상세 미리보기  
	 * 
	 * @param 	ApprDoc
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/viewApprDocLayout.do", method = RequestMethod.POST)
	public ModelAndView vieApprDocLayout(
			@ValidEx(groups = { PreviewApprDoc.class }) ApprDoc apprDoc, 
			BindingResult result) {
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}
		
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/viewApprDocLayout");
		mav.addObject("apprDoc", 	apprDoc);
		return mav;
	}
	
	/**
	 * apprDoc 수정 미리보기  
	 * 
	 * @param 	ApprDoc
	 * @return	ModelAndView
	 */
	@RequestMapping(value = "/updateApprDocLayout.do", method = RequestMethod.POST)
	public ModelAndView updateApprDocLayout(
			@ValidEx(groups = { PreviewApprDoc.class }) ApprDoc apprDoc, 
			BindingResult result) {
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}
		
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/updateApprDocLayout");
		mav.addObject("apprDoc", 	apprDoc);
		return mav;
	}
}