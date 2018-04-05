/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.code.web;

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
import com.lgcns.ikep4.support.user.code.model.ItemType;
import com.lgcns.ikep4.support.user.code.service.ItemTypeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 아이템타입코드 관리용 Controller
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: ItemTypeController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/code/itemtype")
@SessionAttributes("itemType")
public class ItemTypeController extends BaseController {

	@Autowired
	private ItemTypeService itemTypeService;

	/**
	 * searchCondition에 맞춰 상단의 목록을 가져옴
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param searchCondition 목록 검색 조건
	 * @param request HttpServletRequest 객체
	 * @param status SesstionStatus 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getList.do")
	public ModelAndView getList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			AdminSearchCondition searchCondition, HttpServletRequest request, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ModelAndView mav = new ModelAndView("portal/admin/code/itemtype/itemTypeList");

		ItemType itemType = null;

		try {
			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("ITEM_TYPE_CODE");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String code = request.getParameter("tempCode");
			if (code != null && !"".equalsIgnoreCase(code)) {
				itemType = itemTypeService.read(code);
				itemType.setCodeCheck("modify");
			} else {
				itemType = new ItemType();
			}

			searchCondition.setFieldName("itemTypeName");
			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<ItemType> searchResult = itemTypeService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("itemType", itemType);
			mav.addObject("boardCode", boardCode);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}

	/**
	 * 하단의 폼 화면에 들어가는 정보를 가져온다.
	 * 첫 로딩시에는 빈 폼을 보여주고 사용자가 리스트에서 항목을 선택하는 경우 
	 * 해당 항목의 Code를 이용하여 정보를 가져와서 보여준다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param code 상세정보를 요청받은 Item Type Code
	 * @param model Model 객체
	 * @return String 상세정보화면 URI
	 */
	@RequestMapping(value = "/getForm.do", method = RequestMethod.GET)
	public String getForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String code, Model model) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		ItemType itemType = null;

		User user = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = user.getLocaleCode();

		if (code != null) {
			itemType = itemTypeService.read(code);
			itemType.setCodeCheck("modify");
		} else {
			itemType = new ItemType();
		}

		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("itemType", itemType);

		return "portal/admin/code/itemtype/form";
	}

	/**
	 * 등록/수정시에 해당 ID의 중복을 체크하여
	 * 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을 반환한다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param code 중복을 체크할 Item Type Code
	 * @return String 중복 유무를 알려주는 문자열 Flag
	 */
	@RequestMapping(value = "/checkCode.do")
	public @ResponseBody
	String checkCode(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String code) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		boolean result = itemTypeService.exists(code);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * ItemType 을 신규 등록하거나 수정한다.
	 * Code가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다.
	 * 생성, 수정이 끝난 후에는 해당 ItemType의 Code를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param itemType 신규/수정 등록하고자 하는 ItemType 객체
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return code 최종 등록한 Item Type의 상세 정보를 가져오기 위한 Code
	 */
	@RequestMapping(value = "/createItemType.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @ValidEx ItemType itemType,
			BindingResult result, SessionStatus status, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String code = itemType.getItemTypeCode();
		boolean isCodeExist = itemTypeService.exists(code);

		// jobItemType가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {

			itemType.setUpdaterId("admin");
			itemType.setUpdaterName("관리자");

			itemTypeService.update(itemType);
		} else {

			itemType.setRegisterId("admin");
			itemType.setRegisterName("관리자");
			itemType.setUpdaterId("admin");
			itemType.setUpdaterName("관리자");

			itemTypeService.create(itemType);
		}

		status.setComplete();

		return code;
	}

	/**
	 * 해당 ItemType 을 삭제한다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param code 삭제할 Item Type Code
	 * @param request HttpServletRequest 객체
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteItemType.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			@RequestParam("itemTypeCode") String code, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		itemTypeService.delete(code);

		return "redirect:/portal/admin/code/itemtype/getList.do";
	}

}
