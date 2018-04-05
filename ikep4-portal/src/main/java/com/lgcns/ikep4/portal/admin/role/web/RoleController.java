/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.role.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 역할관리용 컨트롤러
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/role/role")
@SessionAttributes("role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	/**
	 * searchCondition에 맞춰 목록을 가져옴
	 * 
	 * @param searchCondition 목록 검색 조건
	 * @param accessResult 사용자 권한체크 결과
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/list.do")
	public ModelAndView list(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AdminSearchCondition searchCondition,
			AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/role/role/list");

		try {
			User sessionUser = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("ROLE_ID");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			searchCondition.setFieldName("roleName");
			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<Role> searchResult = roleService.list(searchCondition);

			List<RoleType> roleTypeList = roleService.selectTypeId(sessionUser.getLocaleCode());

			BoardCode boardCode = new BoardCode();

			mav.addObject("userLocaleCode", sessionUser.getLocaleCode());
			mav.addObject("searchResult", searchResult);
			mav.addObject("roleTypeList", roleTypeList);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}

	/**
	 * 폼 화면에 들어가는 해당 역할의 정보를 가져옴
	 * 
	 * @param roleId 상세정보를 요청받은 역할 ID
	 * @param request HttpServletRequest 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return ModelAndView
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/form.do", method = RequestMethod.POST)
	public ModelAndView form(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam(value = "roleId") String roleId,
			HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView();

		Role role = null;

		String id = request.getParameter("roleId");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();
		List<RoleType> roleTypeList = roleService.selectTypeId(userLocaleCode);

		if (id != null && !"".equals(id)) {
			role = roleService.read(id);

			List userList = role.getUserList();

			// 해당 역할이 부여된 그룹의 리스트를 가져온다.
			List<Role> roleGroupList = roleService.selectRoleGroupList(id);

			// 해당 역할이 부여된 사용자의 리스트를 가져온다.
			List<Map<String, String>> roleUserList = roleService.selectRoleUserList(id);

			role.setCodeCheck("modify");

			mav.addObject("currRoleId", role.getRoleId());
			mav.addObject("userList", userList);
			mav.addObject("roleGroupList", roleGroupList);
			mav.addObject("roleUserList", roleUserList);
		} else {
			role = new Role();
			role.setCodeCheck("new");
		}

		mav.addObject("userLocaleCode", userLocaleCode);
		mav.addObject("role", role);
		mav.addObject("roleTypeList", roleTypeList);

		mav.setViewName("portal/admin/role/role/form");

		return mav;
	}

	/**
	 * 역할을 신규로 등록하거나 수정한다. ID가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다.
	 * 
	 * @param role 신규/수정 등록하고자 하는 역할 객체
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/onSubmit.do")
	public String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid Role role,
			BindingResult result, SessionStatus status, HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		boolean isCodeExist = roleService.exists(role.getRoleId());

		// 그룹의 등록/수정을 위해 필요한 기본 정보 추가
		List<Group> tempGroupList = role.getGroupList();

		if (tempGroupList != null) {
			for (int i = 0; i < tempGroupList.size(); i++) {
				Group tempGroup = tempGroupList.get(i);
				tempGroup.setRoleId(role.getRoleId());
				tempGroup.setRegisterId(sessionUser.getUserId());
				tempGroup.setRegisterName(sessionUser.getUserName());
				tempGroup.setUpdaterId(sessionUser.getUserId());
				tempGroup.setUpdaterName(sessionUser.getUserName());
			}
		}

		// 사용자의 등록/수정을 위해 필요한 기본 정보 추가
		List<User> tempUserList = role.getUserList();

		if (tempUserList != null) {
			for (int i = 0; i < tempUserList.size(); i++) {
				User tempUser = tempUserList.get(i);
				tempUser.setRoleId(role.getRoleId());
				tempUser.setRegisterId(sessionUser.getUserId());
				tempUser.setRegisterName(sessionUser.getUserName());
				tempUser.setUpdaterId(sessionUser.getUserId());
				tempUser.setUpdaterName(sessionUser.getUserName());
			}
		}

		if (isCodeExist) {

			role.setGroupList(tempGroupList);
			role.setUserList(tempUserList);
			role.setPortalId(portal.getPortalId());
			role.setUpdaterId(sessionUser.getUserId());
			role.setUpdaterName(sessionUser.getUserName());

			roleService.update(role);
		} else {

			role.setGroupList(tempGroupList);
			role.setUserList(tempUserList);
			role.setPortalId(portal.getPortalId());
			role.setRegisterId(sessionUser.getUserId());
			role.setRegisterName(sessionUser.getUserName());
			role.setUpdaterId(sessionUser.getUserId());
			role.setUpdaterName(sessionUser.getUserName());

			roleService.create(role);
		}

		status.setComplete();

		return "redirect:/portal/admin/role/role/list.do";
	}

	/**
	 * 해당 역할을 삭제한다.
	 * 
	 * @param role 삭제할 역할 객체
	 * @param request HttpServletRequest 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/delete.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) Role role,
			HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		roleService.remove(role);

		return "redirect:/portal/admin/role/role/list.do";
	}

}