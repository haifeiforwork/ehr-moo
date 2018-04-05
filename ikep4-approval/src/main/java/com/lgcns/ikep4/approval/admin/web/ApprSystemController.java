/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprSystem;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprSystemService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 결재시스템 관리용 Controller
 * 
 * @author  
 * @version $Id: ApprSystemController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/approval/admin/apprSystem")
public class ApprSystemController extends BaseController {

	@Autowired
	private ApprSystemService apprSystemService;

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
	
	@RequestMapping(value = "/apprSystemList.do")
	public ModelAndView apprSystemList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Approval", operationName = { "MANAGE" }, resourceName = "Approval")) AdminSearchCondition searchCondition,
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/approval/admin/apprSystem/apprSystemList");
		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		ApprSystem apprSystem = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("SYSTEM_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String systemId = request.getParameter("systemId");

			if (systemId != null && !"".equalsIgnoreCase(systemId)) {

				apprSystem = apprSystemService.read(systemId);
				apprSystem.setSystemIdCheck("modify");

				mav.addObject("oldSystemOrder", apprSystem.getSystemOrder());
			} else {
				apprSystem = new ApprSystem();
				apprSystem.setSystemOrder(apprSystemService.getMaxSystemOrder(portal.getPortalId()));
			}

			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<ApprSystem> searchResult = apprSystemService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", sessionUser.getLocaleCode());
			mav.addObject("apprSystem", apprSystem);
			mav.addObject("isSystemAdmin", this.isSystemAdmin(sessionUser));
			mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));
			
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}


	@RequestMapping(value = "/apprSystemForm.do", method = RequestMethod.GET)
	public String apprSystemForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Approval", operationName = { "MANAGE" }, resourceName = "Approval")) String systemId,
			Model model, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ApprSystem apprSystem = null;
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User sessionUser = (User) getRequestAttribute("ikep.user");

		if (systemId != null && !"".equalsIgnoreCase(systemId)) {
			apprSystem = apprSystemService.read(systemId);
			apprSystem.setSystemIdCheck("modify");

			model.addAttribute("oldSystemOrder", apprSystem.getSystemOrder());
		} else {
			apprSystem = new ApprSystem();
			apprSystem.setSystemIdCheck("new");
			apprSystem.setSystemOrder(apprSystemService.getMaxSystemOrder(portal.getPortalId()));
		}

		model.addAttribute("userLocaleCode", sessionUser.getLocaleCode());
		model.addAttribute("apprSystem", apprSystem);
		model.addAttribute("isSystemAdmin", this.isSystemAdmin(sessionUser));
		model.addAttribute("isReadAll", this.isReadAll(portal.getPortalId()));

		return "approval/admin/apprSystem/apprSystemForm";
	}


	@RequestMapping(value = "/checkSystemId.do")
	public @ResponseBody
	String checkSystemId(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Approval", operationName = { "MANAGE" }, resourceName = "Approval")) String systemId,
			AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		boolean result = apprSystemService.exists(systemId);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}


	@RequestMapping(value = "/createApprSystem.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Approval", operationName = { "MANAGE" }, 
					resourceName = "Approval")) @ValidEx ApprSystem apprSystem,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String systemId = apprSystem.getSystemId();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		boolean isCodeExist = apprSystemService.exists(systemId);

		// apprSystemCode가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {

			apprSystem.setPortalId(portal.getPortalId());
			apprSystem.setUpdaterId(user.getUserId());
			apprSystem.setUpdaterName(user.getUserName());

			apprSystemService.update(apprSystem);

		} else {

			//apprSystem.setSystemOrder(Integer.parseInt(request.getParameter("systemOrder")));
			apprSystem.setPortalId(portal.getPortalId());
			apprSystem.setRegisterId(user.getUserId());
			apprSystem.setRegisterName(user.getUserName());
			apprSystem.setUpdaterId(user.getUserId());
			apprSystem.setUpdaterName(user.getUserName());

			apprSystemService.create(apprSystem);
		}

		status.setComplete();

		return systemId;

	}


	@RequestMapping(value = "/deleteApprSystem.do")
	public String deleteApprSystem(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Approval", operationName = { "MANAGE" }, resourceName = "Approval")) @RequestParam("systemId") String systemId,
			AccessingResult accessResult, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		apprSystemService.delete(systemId);

		return "redirect:/approval/admin/apprSystem/apprSystemList.do";
	}


	@RequestMapping(value = "/goUp.do")
	public @ResponseBody
	String goUp(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Approval", operationName = { "MANAGE" }, resourceName = "Approval")) String systemId,
			String systemOrder, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("systemId", systemId);
		map.put("systemOrder", systemOrder);

		apprSystemService.goUp(map);

		return "";
	}


	@RequestMapping(value = "/goDown.do")
	public @ResponseBody
	String goDown(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Approval", operationName = { "MANAGE" }, resourceName = "Approval")) String systemId,
			String systemOrder, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("systemId", systemId);
		map.put("systemOrder", systemOrder);

		apprSystemService.goDown(map);

		return "";
	}

}