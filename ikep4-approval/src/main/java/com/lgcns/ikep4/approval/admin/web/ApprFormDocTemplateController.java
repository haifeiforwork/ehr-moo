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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprFormDocTemplate;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.model.ApprFormDocTemplate.CreateApprFormDocTemplate;
import com.lgcns.ikep4.approval.admin.model.ApprFormDocTemplate.DeleteApprFormDocTemplate;
import com.lgcns.ikep4.approval.admin.model.ApprFormDocTemplate.UpdateApprFormDocTemplate;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprFormDocTemplateService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * ApprFormDocTemplate 컨트롤러
 * 
 * @author wonchu
 * @version $Id: FormController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/approval/admin/apprFormDocTemplate")
@SessionAttributes("apprFormDocTemplate")
public class ApprFormDocTemplateController extends BaseController {

	@Autowired
	private ApprFormDocTemplateService apprFormDocTemplateService;

	/**
	 * apprForm Doc template 목록
	 * 
	 * @param 	ApprFormSearchCondition
	 * @return 	ModelAndView
	 */
	@RequestMapping(value = "/listApprFormDocTemplate.do")
	public ModelAndView listApprFormDocTemplate(
			ApprFormSearchCondition apprFormSearchCondition) {

		// tiles
		ModelAndView mav = new ModelAndView("/approval/admin/apprForm/listApprFormDocTemplate");

		// session
		User user = (User) getRequestAttribute("ikep.user");
		apprFormSearchCondition.setUserId(user.getUserId());
		apprFormSearchCondition.setPortalId(user.getPortalId());

		// 폼 목록
		SearchResult<ApprFormDocTemplate> searchResult = apprFormDocTemplateService.listBySearchCondition(apprFormSearchCondition);

		// page per recode count
		CommonCode commonCode = new CommonCode();
		ApprFormDocTemplate apprFormDocTemplate = new ApprFormDocTemplate();

		// set return info
		mav.addObject("searchResult", searchResult);
		mav.addObject("apprFormDocTemplate", apprFormDocTemplate);
		mav.addObject("numList", commonCode.getPageNumList());
		mav.addObject("searchCondition", searchResult.getSearchCondition());

		return mav;
	}

	/**
	 * apprForm Doc template 등록
	 * 
	 * @param 	ApprFormDocTemplate
	 * @return 	리다이렉트
	 */
	@RequestMapping(value = "/createApprFormDocTemplate.do", method = RequestMethod.POST)
	public String createApprFormDocTemplate(
			@ValidEx(groups = { CreateApprFormDocTemplate.class }) ApprFormDocTemplate apprFormDocTemplate,
			BindingResult result, SessionStatus status, Model model) {

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// session
		User user = (User) getRequestAttribute("ikep.user");

		// userInfo
		apprFormDocTemplate.setPortalId(user.getPortalId());
		apprFormDocTemplate.setRegisterId(user.getUserId());
		apprFormDocTemplate.setRegisterName(user.getUserName());

		// template 등록
		apprFormDocTemplateService.createApprFormDocTemplate(apprFormDocTemplate);

		status.setComplete();
		return "redirect:/approval/admin/apprFormDocTemplate/listApprFormDocTemplate.do?templateType=" + apprFormDocTemplate.getTemplateType();
	}

	/**
	 * apprForm Doc template 수정
	 * 
	 * @param 	ApprFormDocTemplate
	 * @return 	리다이렉트
	 */
	@RequestMapping(value = "/updateApprFormDocTemplate.do", method = RequestMethod.POST)
	public String updateApprFormDocTemplate(
			@ValidEx(groups = { UpdateApprFormDocTemplate.class }) ApprFormDocTemplate apprFormDocTemplate,
			BindingResult result, SessionStatus status, Model model) {

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// session
		User user = (User) getRequestAttribute("ikep.user");

		// userInfo
		apprFormDocTemplate.setPortalId(user.getPortalId());
		apprFormDocTemplate.setRegisterId(user.getUserId());

		// template 수정
		apprFormDocTemplateService.updateApprFormDocTemplate(apprFormDocTemplate);

		status.setComplete();
		return "redirect:/approval/admin/apprFormDocTemplate/listApprFormDocTemplate.do?templateType=" + apprFormDocTemplate.getTemplateType();
	}

	/**
	 * apprForm Doc template 삭제
	 * 
	 * @param 	ApprFormDocTemplate
	 * @return 	리다이렉트
	 */
	@RequestMapping(value = "/deleteApprFormDocTemplate.do", method = RequestMethod.POST)
	public String deleteApprFormDocTemplate(
			@ValidEx(groups = { DeleteApprFormDocTemplate.class }) ApprFormDocTemplate apprFormDocTemplate,
			BindingResult result, SessionStatus status, Model model) {

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// session
		User user = (User) getRequestAttribute("ikep.user");

		// userInfo
		apprFormDocTemplate.setPortalId(user.getPortalId());
		apprFormDocTemplate.setRegisterId(user.getUserId());

		// template 삭제
		apprFormDocTemplateService.deleteApprFormDocTemplate(apprFormDocTemplate);

		status.setComplete();
		return "redirect:/approval/admin/apprFormDocTemplate/listApprFormDocTemplate.do?templateType=" + apprFormDocTemplate.getTemplateType();
	}

}