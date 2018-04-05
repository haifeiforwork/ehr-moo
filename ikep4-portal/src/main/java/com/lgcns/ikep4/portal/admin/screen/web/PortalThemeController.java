/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalCode;
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;
import com.lgcns.ikep4.portal.admin.screen.service.PortalThemeService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 포탈 테마 Controller
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalThemeController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/theme")
public class PortalThemeController extends BaseController {

	@Autowired
	private PortalThemeService portalThemeService;

	/**
	 * 테마 등록 폼
	 * @param accessResult 권한체크 결과
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createThemeForm.do", method = RequestMethod.GET)
	public String createThemeForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			Model model) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		PortalTheme portalTheme = new PortalTheme();
		
		model.addAttribute("portalTheme", portalTheme);
		
		return "portal/admin/screen/theme/createThemeForm";
	}
	
	/**
	 * 테마 등록
	 * @param accessResult 권한체크 결과
	 * @param portalTheme 테마 모델
	 * @param result
	 * @param status
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/createTheme.do", method = RequestMethod.POST)
	public String createTheme(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalTheme portalTheme, BindingResult result, SessionStatus status, Model model) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		if (result.hasErrors()) {
			model.addAttribute(PREFIX_BINDING_RESULT + "portalTheme", result);
			
			return "portal/admin/screen/theme/createThemeForm";
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		portalTheme.setRegisterId(user.getUserId());
		portalTheme.setRegisterName(user.getUserName());
		portalTheme.setUpdaterId(user.getUserId());
		portalTheme.setUpdaterName(user.getUserName());
		portalTheme.setPortalId(portal.getPortalId());

		portalThemeService.createTheme(portalTheme);

		return "redirect:/portal/admin/screen/theme/readTheme.do?themeId=" + portalTheme.getThemeId();
	}
	
	/**
	 * 테마 조회
	 * @param accessResult 권한체크 결과
	 * @param themeId 테마 ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readTheme.do")
	public ModelAndView readTheme(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("themeId") String themeId) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); // IKEP4AuthorizedException을
													// throw하면
													// common/notAuthorized.jsp
													// 로 페이지 전환이 된다.
		}

		ModelAndView mav = new ModelAndView("portal/admin/screen/theme/readTheme");
		if (themeId != null) {
			PortalTheme portalTheme = portalThemeService.readTheme(themeId);
			mav.addObject("portalTheme", portalTheme);
		}
		return mav;
	}
	
	/**
	 * 테마 수정폼
	 * @param accessResult 권한체크 결과
	 * @param themeId 테마 ID
	 * @param model 모델 객체
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updateThemeForm.do", method = RequestMethod.GET)
	public String updateThemeForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value = "themeId") String themeId, Model model) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); // IKEP4AuthorizedException을
													// throw하면
													// common/notAuthorized.jsp
													// 로 페이지 전환이 된다.
		}
		
		PortalTheme portalTheme = portalThemeService.readTheme(themeId);
		
		model.addAttribute("portalTheme", portalTheme);
		
		return "portal/admin/screen/theme/updateThemeForm";
	}
	
	/**
	 * 테마 수정
	 * @param accessResult 권한체크 결과
	 * @param portalTheme 테마 모델
	 * @param result
	 * @param status
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/updateTheme.do", method = RequestMethod.POST)
	public String updateThemeModify(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@Valid PortalTheme portalTheme, BindingResult result, SessionStatus status, Model model) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); // IKEP4AuthorizedException을
													// throw하면
													// common/notAuthorized.jsp
													// 로 페이지 전환이 된다.
		}
		
		if (result.hasErrors()) {
			model.addAttribute(PREFIX_BINDING_RESULT + "portalTheme", result);
			
			return "portal/admin/screen/theme/updateThemeForm";
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		portalTheme.setUpdaterId(user.getUserId());
		portalTheme.setUpdaterName(user.getUserName());
		portalTheme.setPortalId(portal.getPortalId());		

		portalThemeService.updateTheme(portalTheme);

		return "redirect:/portal/admin/screen/theme/readTheme.do?themeId=" + portalTheme.getThemeId();
	}

	/**
	 * 테마 목록
	 * @param accessResult 권한체크 결과
	 * @param portalTheme 테마 모델
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listTheme.do")
	public ModelAndView listTheme(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@ModelAttribute PortalTheme portalTheme, AdminSearchCondition searchCondition) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); // IKEP4AuthorizedException을
													// throw하면
													// common/notAuthorized.jsp
													// 로 페이지 전환이 된다.
		}
		
		PortalCode portalCode = new PortalCode();
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<PortalTheme> searchResult = portalThemeService.listBySearchCondition(searchCondition);
		
		ModelAndView mav = new ModelAndView("portal/admin/screen/theme/listTheme");
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("portalCode", portalCode);

		return mav;
	}
	
	/**
	 * 테마 삭제
	 * @param accessResult 권한체크 결과
	 * @param themeId 테마 ID
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/removeTheme.do", method = RequestMethod.POST)
	public String removeTheme(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam(value = "themeId") String themeId) {

		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); // IKEP4AuthorizedException을
													// throw하면
													// common/notAuthorized.jsp
													// 로 페이지 전환이 된다.
		}
		
		portalThemeService.removeTheme(themeId);

		return "redirect:/portal/admin/screen/theme/listTheme.do";
	}
}