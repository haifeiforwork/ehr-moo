/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.role.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleTypeService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 역할타입 관리용 Controller
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleTypeController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/role/roletype")
@SessionAttributes("roletype")
public class RoleTypeController extends BaseController {

	@Autowired
	private RoleTypeService roleTypeService;

	@Autowired
	private I18nMessageService i18nMessageService;

	/**
	 * searchCondition에 맞춰 상단의 목록을 가져옴
	 * 
	 * @param searchCondition 목록 검색 조건
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @param status SesstionStatus 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getList.do")
	public ModelAndView getList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AdminSearchCondition searchCondition,
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/role/roletype/roleTypeList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		RoleType roleType = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("ROLE_TYPE_ID");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String id = request.getParameter("tempId");
			String userLocaleCode = sessionUser.getLocaleCode();

			if (id != null && !"".equalsIgnoreCase(id)) {
				roleType = roleTypeService.read(id);
				roleType.setCodeCheck("modify");
				roleType.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
						id, "roleTypeName"));
			} else {
				roleType = new RoleType();
				roleType.setI18nMessageList(i18nMessageService.makeInitLocaleList("roleTypeName"));
			}

			searchCondition.setFieldName("roleTypeName");
			searchCondition.setUserLocaleCode(userLocaleCode);
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<RoleType> searchResult = roleTypeService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", userLocaleCode);
			mav.addObject("roleType", roleType);
			mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}

	/**
	 * 하단의 폼 화면에 들어가는 정보를 가져온다. 첫 로딩시에는 빈 폼을 보여주고 사용자가 리스트에서 항목을 선택하는 경우 해당 항목의
	 * ID를 이용하여 정보를 가져와서 보여준다.
	 * 
	 * @param id 상세정보를 요청받은 역할타입 ID
	 * @param fieldName 다국어메시지 표현에 필요한 필드네임
	 * @param itemTypeCode 다국어메시지 표현에 필요한 ItemType의 Code
	 * @param model Model 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return
	 */
	@RequestMapping(value = "/getForm.do", method = RequestMethod.GET)
	public String getForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String id,
			String fieldName, String itemTypeCode, Model model, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		RoleType roleType = null;

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();

		if (id != null && !"".equalsIgnoreCase(id)) {
			roleType = roleTypeService.read(id);
			roleType.setCodeCheck("modify");
			roleType.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id,
					"roleTypeName"));
		} else {
			roleType = new RoleType();
			roleType.setCodeCheck("new");
			roleType.setI18nMessageList(i18nMessageService.makeInitLocaleList("roleTypeName"));
		}

		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("roleType", roleType);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());

		return "portal/admin/role/roletype/form";
	}

	/**
	 * 등록/수정시에 해당 ID의 중복을 체크하여 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을
	 * 반환한다.
	 * 
	 * @param id 중복을 체크할 역할타입 ID
	 * @param accessResult 사용자 권한체크 결과
	 * @return String 중복 유무를 알려주는 문자열 Flag	
	 */
	@RequestMapping(value = "/checkId.do")
	public @ResponseBody
	String checkId(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String id,
			AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		boolean result = roleTypeService.exists(id);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * 역할타입을 신규로 등록하거나 수정한다. ID가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다. 생성, 수정이
	 * 끝난 후에는 해당 역할타입의 ID를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param roleType 신규/수정 등록하고자 하는 역할타입 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return id 최종 등록한 역할타입의 상세 정보를 가져오기 위한 ID
	 */
	@RequestMapping(value = "/createRoleType.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) RoleType roleType,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String id = roleType.getRoleTypeId();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		boolean isCodeExist = roleTypeService.exists(id);

		// jobRoleType가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {
			List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(roleType.getI18nMessageList(),
					IKepConstant.ITEM_TYPE_CODE_PORTAL, roleType.getRoleTypeId());
			roleType.setPortalId(portal.getPortalId());
			roleType.setUpdaterId("admin");
			roleType.setUpdaterName("관리자");
			roleType.setI18nMessageList(i18nMessageList);

			roleTypeService.update(roleType);
		} else {
			List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(roleType.getI18nMessageList(),
					IKepConstant.ITEM_TYPE_CODE_PORTAL, roleType.getRoleTypeId());
			roleType.setPortalId(portal.getPortalId());
			roleType.setRegisterId("admin");
			roleType.setRegisterName("관리자");
			roleType.setUpdaterId("admin");
			roleType.setUpdaterName("관리자");
			roleType.setI18nMessageList(i18nMessageList);

			roleTypeService.create(roleType);
		}

		status.setComplete();

		return id;
	}

	/**
	 * 해당 역할타입을 삭제한다.
	 * 
	 * @param id 삭제할 역할타입 ID
	 * @param request HttpServletRequest 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteRoleType.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("roleTypeId") String id,
			HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		roleTypeService.delete(id);

		return "redirect:/portal/admin/role/roletype/getList.do";
	}

}
