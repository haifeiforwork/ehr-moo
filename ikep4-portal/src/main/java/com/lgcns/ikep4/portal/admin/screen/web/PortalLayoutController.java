/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalCode;
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.portal.admin.screen.service.PortalLayoutService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 레이아웃 Controller
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalLayoutController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/layout")
public class PortalLayoutController extends BaseController {

	@Autowired
	private PortalLayoutService portalLayoutService;
	
	/**
	 * 레이아웃 등록폼
	 * 
	 * @param accessResult 권한체크 결과
	 * @param model 모델객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createLayoutForm.do")
	public String createLayoutForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			Model model, AdminSearchCondition searchCondition, HttpServletRequest request) {
		
		if(!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		} else {
			String id = request.getParameter("tempId");
			
			PortalCode portalCode = new PortalCode();
			
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");
	
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portalId);
	
			SearchResult<PortalLayout> searchResult = portalLayoutService.listBySearchCondition(searchCondition);
			
			PortalLayout portalLayout = new PortalLayout();
			
			if(!StringUtil.isEmpty(id)) {
				portalLayout = portalLayoutService.readLayout(id);
			}
			
			model.addAttribute("portalLayout", portalLayout);
			model.addAttribute("searchResult", searchResult);
			model.addAttribute("searchCondition", searchResult.getSearchCondition());
			model.addAttribute("portalCode", portalCode);
		}
		
		return "portal/admin/screen/layout/createLayoutForm";
		
	}

	/**
	 * 레이아웃 등록
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portalLayout 레이아웃 모델
	 * @param result 
	 * @return String 등록된 레이아웃 아이디
	 */
	@RequestMapping(value = "/createLayout.do", method = RequestMethod.POST)
	public @ResponseBody String createLayout(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalLayout portalLayout, BindingResult result, SessionStatus status, Model model) {

		String returnValue = "";
		
		if(!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		if(result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else {
			User user = (User) getRequestAttribute("ikep.user");
			
			portalLayout.setRegisterId(user.getUserId());
			portalLayout.setRegisterName(user.getUserName());
			portalLayout.setUpdaterId(user.getUserId());
			portalLayout.setUpdaterName(user.getUserName());

			returnValue = portalLayoutService.createLayout(portalLayout);
			
			status.setComplete();
		}
		
		return returnValue;
	}

	/**
	 * 레이아웃 조회
	 * 
	 * @param accessResult 권한체크 결과
	 * @param layoutId 레이아웃 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readLayout.do")
	public ModelAndView readLayout(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("layoutId") String layoutId) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/layout/readLayout");
		
		if (layoutId != null) {
			PortalLayout portalLayout = portalLayoutService.readLayout(layoutId);

			mav.addObject("portalLayout", portalLayout);
		}
		
		return mav;
	}

	/**
	 * 레이아웃 삭제
	 * 
	 * @param accessResult 권한체크 결과
	 * @param layoutId 레이아웃 ID
	 * @return String 공백값
	 */
	@RequestMapping(value = "/removeLayout.do", method = RequestMethod.POST)
	public @ResponseBody String removeLayout(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value = "layoutId") String layoutId, SessionStatus status) {
		
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		} else	{
			
			portalLayoutService.removeLayout(layoutId);
			
			status.setComplete();
		}
		
		return "";
	}

	/**
	 * 레이아웃 수정
	 * 
	 * @param accessResult 권한체크 결과
	 * @param portalLayout 레이아웃 모델
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updateLayout.do", method = RequestMethod.POST)
	public @ResponseBody String updateLayout(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalLayout portalLayout, BindingResult result, SessionStatus status, Model model) {

		String returnValue = "";
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		if(result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else {
			User user = (User) getRequestAttribute("ikep.user");
			
			portalLayout.setUpdaterId(user.getUserId());
			portalLayout.setUpdaterName(user.getUserName());
			
			portalLayoutService.updateLayout(portalLayout);
			
			returnValue = portalLayout.getLayoutId();
			
			status.setComplete();
		}

		return returnValue;
	}
}
