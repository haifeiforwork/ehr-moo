/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import java.util.List;

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

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalCode;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletCategoryService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 포틀릿 그룹 Controller
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalPortletGroupController.java 745 2011-02-17 06:20:10Z
 *          limjongsang $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/portletCategory")
public class PortalPortletCategoryController extends BaseController {

	@Autowired
	private PortalPortletCategoryService portalPortletCategoryService;

	@Autowired
	private PortalSystemService portalSystemMapService;

	/**
	 * 포틀릿 카테고리 등록폼
	 * @param accessResult 권한체크 결과
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createPortletCategoryForm.do")
	public String createPortletCategoryForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			AdminSearchCondition searchCondition, Model model, HttpServletRequest request) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); // IKEP4AuthorizedException을
													// throw하면
													// common/notAuthorized.jsp
													// 로 페이지 전환이 된다.
		} else {
			String id = request.getParameter("tempId");
			
			PortalCode portalCode = new PortalCode();
			
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");
	
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portalId);
	
			SearchResult<PortalPortletCategory> searchResult = portalPortletCategoryService.listBySearchCondition(searchCondition);
			
			PortalPortletCategory portalPortletCategory = new PortalPortletCategory();
			
			List<PortalSystem> portalSystemMapList = portalSystemMapService.portalSystemListView(portalId);
			
			if(!StringUtil.isEmpty(id)) {
				portalPortletCategory = portalPortletCategoryService.readPortletCategory(id);
			}
	
			model.addAttribute("portalSystemMapList", portalSystemMapList);
			model.addAttribute("portalPortletCategory", portalPortletCategory);
			model.addAttribute("searchResult", searchResult);
			model.addAttribute("searchCondition", searchResult.getSearchCondition());
			model.addAttribute("portalCode", portalCode);
		}

		return "portal/admin/screen/portletCategory/createPortletCategoryForm";
	}

	/**
	 * 포틀릿 카테고리 저장
	 * @param accessResult 권한체크 결과
	 * @param portalPortletCategory 포틀릿 카테고리 모델
	 * @param result 
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createPortletCategory.do", method = RequestMethod.POST)
	public @ResponseBody String createPortletCategory(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalPortletCategory portalPortletCategory, BindingResult result,
			SessionStatus status, AdminSearchCondition searchCondition, Model model) {

		String returnValue = "";
		
		if(!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		if(result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else  {
			User user = (User) getRequestAttribute("ikep.user");
			
			portalPortletCategory.setRegisterId(user.getUserId());
			portalPortletCategory.setRegisterName(user.getUserName());
			portalPortletCategory.setUpdaterId(user.getUserId());
			portalPortletCategory.setUpdaterName(user.getUserName());
	
			returnValue = portalPortletCategoryService.createPortletCategory(portalPortletCategory);
			
			status.setComplete();
		}

		return returnValue;
	}

	/**
	 * 포틀릿 카테고리 조회
	 * @param accessResult 권한체크 결과
	 * @param portletCategoryId 포틀릿 카테고리 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readPortletCategory.do")
	public ModelAndView readPortletCategory(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("portletCategoryId") String portletCategoryId) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/portletCategory/readPortletCategory");
		
		if (portletCategoryId != null) {
			
			String portalId = (String) getRequestAttribute("ikep.portalId");

			List<PortalSystem> portalSystemMapList = portalSystemMapService.portalSystemListView(portalId);
			PortalPortletCategory portalPortletCategory = portalPortletCategoryService.readPortletCategory(portletCategoryId);

			mav.addObject("portalSystemMapList", portalSystemMapList);
			mav.addObject("portalPortletCategory", portalPortletCategory);
		}
		
		return mav;
	}

	/**
	 * 포틀릿 카테고리 삭제
	 * @param accessResult 권한체크 결과
	 * @param portletCategoryId 포틀릿 카테고리 ID
	 * @return String 공백값
	 */
	@RequestMapping(value = "/removePortletCategory.do", method = RequestMethod.POST)
	public @ResponseBody String removePortletCategory(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value = "portletCategoryId") String portletCategoryId, SessionStatus status) {
		
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		} else	{
			
			portalPortletCategoryService.removePortletCategory(portletCategoryId);
			
			status.setComplete();
		}
		
		return "";
	}

	/**
	 * 포틀릿 카테고리 수정
	 * @param accessResult 권한체크 결과
	 * @param portalPortletCategory 포틀릿 카테고리 모델
	 * @param result
	 * @param status
	 * @return String 수정된 포틀릿 카테고리 아이디
	 */
	@RequestMapping(value = "/updatePortletCategory.do", method = RequestMethod.POST)
	public @ResponseBody String updatePortletCategory(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalPortletCategory portalPortletCategory, BindingResult result, SessionStatus status) {
		
		String returnValue = "";
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); // IKEP4AuthorizedException을
													// throw하면
													// common/notAuthorized.jsp
													// 로 페이지 전환이 된다.
		}
		
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else {
			User user = (User) getRequestAttribute("ikep.user");
			
			portalPortletCategory.setUpdaterId(user.getUserId());
			portalPortletCategory.setUpdaterName(user.getUserName());

			portalPortletCategoryService.updatePortletCategory(portalPortletCategory);
			
			returnValue = portalPortletCategory.getPortletCategoryId();
			
			status.setComplete();
		}

		return returnValue;
	}
	
	/**
	 * 포틀릿 카테고리 리스트(시스템 코드별)
	 * @param systemCode 리스템 코드
	 * @return result 포틀릿 카테고리 목록
	 */
	@RequestMapping("/listPortletCategory.do")
	public @ResponseBody List<PortalPortletCategory> listPortletCategory(String systemCode) {
	   List<PortalPortletCategory> result = null;
	    try {
		result  = portalPortletCategoryService.listPortletCategory(systemCode);
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);

	    }
	    return result;
	}
	
}