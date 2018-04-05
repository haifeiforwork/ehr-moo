/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.group.web;

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
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.GroupType;
import com.lgcns.ikep4.support.user.group.service.GroupTypeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 그룹타입코드 관리용 Controller
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: GroupTypeController.java 18009 2012-04-11 11:12:24Z arthes $
 */
@Controller
@RequestMapping(value = "/portal/admin/group/grouptype")
@SessionAttributes("grouptype")
public class GroupTypeController extends BaseController {

	@Autowired
	private GroupTypeService groupTypeService;

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

		ModelAndView mav = new ModelAndView("portal/admin/group/grouptype/groupTypeList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		GroupType groupType = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("GROUP_TYPE_ID");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String id = request.getParameter("tempId");

			if (id != null && !"".equalsIgnoreCase(id)) {
				groupType = groupTypeService.read(id);
				groupType.setCodeCheck("modify");
			} else {
				groupType = new GroupType();
			}

			searchCondition.setFieldName("groupTypeName");
			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<GroupType> searchResult = groupTypeService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", sessionUser.getLocaleCode());
			mav.addObject("groupType", groupType);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}

	/**
	 * 하단의 폼 화면에 들어가는 정보를 가져온다. 첫 로딩시에는 빈 폼을 보여주고 사용자가 리스트에서 항목을 선택하는 경우 해당 항목의
	 * ID를 이용하여 정보를 가져와서 보여준다.
	 * 
	 * @param id 상세정보를 요청받은 그룹타입 ID
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

		GroupType groupType = null;

		User sessionUser = (User) getRequestAttribute("ikep.user");

		if (id != null && !"".equalsIgnoreCase(id)) {
			groupType = groupTypeService.read(id);
			groupType.setCodeCheck("modify");
		} else {
			groupType = new GroupType();
			groupType.setCodeCheck("new");
		}

		model.addAttribute("userLocaleCode", sessionUser.getLocaleCode());
		model.addAttribute("groupType", groupType);

		return "portal/admin/group/grouptype/form";
	}

	/**
	 * 등록/수정시에 해당 ID의 중복을 체크하여 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을
	 * 반환한다.
	 * 
	 * @param id 중복을 체크할 그룹타입 ID
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

		boolean result = groupTypeService.exists(id);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * 그룹타입을 신규로 등록하거나 수정한다. ID가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다. 생성, 수정이
	 * 끝난 후에는 해당 그룹타입의 ID를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param groupType 신규/수정 등록하고자 하는 그룹타입 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return id 최종 등록한 그룹타입의 상세 정보를 가져오기 위한 ID
	 */
	@RequestMapping(value = "/createGroupType.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @ValidEx GroupType groupType,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String id = groupType.getGroupTypeId();
		boolean isCodeExist = groupTypeService.exists(id);
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		groupType.setPortalId(portal.getPortalId());

		// jobGroupType가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {

			groupType.setUpdaterId("admin");
			groupType.setUpdaterName("관리자");

			groupTypeService.update(groupType);

		} else {

			groupType.setRegisterId("admin");
			groupType.setRegisterName("관리자");
			groupType.setUpdaterId("admin");
			groupType.setUpdaterName("관리자");

			groupTypeService.create(groupType);
		}

		status.setComplete();

		return id;
	}

	/**
	 * 해당 그룹타입을 삭제한다.
	 * 
	 * @param id 삭제할 그룹타입 ID
	 * @param request HttpServletRequest 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteGroupType.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("groupTypeId") String id,
			HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		groupTypeService.delete(id);

		return "redirect:/portal/admin/group/grouptype/getList.do";
	}

}
