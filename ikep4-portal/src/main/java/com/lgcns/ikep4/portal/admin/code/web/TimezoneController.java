/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.code.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.Timezone;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.TimezoneService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TIMEZONE 코드 관리용 Controller
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: TimezoneController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/code/timezone")
@SessionAttributes("timezone")
public class TimezoneController extends BaseController {

	@Autowired
	private TimezoneService timezoneService;

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

		ModelAndView mav = new ModelAndView("portal/admin/code/timezone/timezoneList");

		User sessionUser = (User) getRequestAttribute("ikep.user");

		Timezone timezone = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String id = request.getParameter("tempId");

			String userLocaleCode = sessionUser.getLocaleCode();

			if (id != null && !"".equalsIgnoreCase(id)) {
				timezone = timezoneService.read(id);
				timezone.setCodeCheck("modify");
				timezone.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
						id, "timezoneName"));

				mav.addObject("oldSortOrder", timezone.getSortOrder());
			} else {
				timezone = new Timezone();
				timezone.setI18nMessageList(i18nMessageService.makeInitLocaleList("timezoneName"));
			}

			searchCondition.setFieldName("timezoneName");
			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());

			SearchResult<Timezone> searchResult = timezoneService.selectAll(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", userLocaleCode);
			mav.addObject("timezone", timezone);
			mav.addObject("localeSize", i18nMessageService.selectLocaleAll().size());
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
	 * @param id 상세정보를 요청받은 타임존 ID
	 * @param fieldName 다국어메시지 표현에 필요한 필드네임
	 * @param itemTypeCode 다국어메시지 표현에 필요한 ItemType의 Code
	 * @param model Model 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String 상세정보화면 URI
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

		Timezone timezone = null;

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();

		if (id != null && !"".equalsIgnoreCase(id)) {
			timezone = timezoneService.read(id);
			timezone.setCodeCheck("modify");
			timezone.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, id,
					"timezoneName"));

			model.addAttribute("oldSortOrder", timezone.getSortOrder());
		} else {
			timezone = new Timezone();
			timezone.setCodeCheck("new");
			timezone.setI18nMessageList(i18nMessageService.makeInitLocaleList("timezoneName"));
		}

		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("timezone", timezone);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());

		return "portal/admin/code/timezone/form";
	}

	/**
	 * 등록/수정시에 해당 ID의 중복을 체크하여
	 * 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을 반환한다.
	 * 
	 * @param id 중복을 체크할 타임존 ID
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

		boolean result = timezoneService.exists(id);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * 타임존을 신규로 등록하거나 수정한다.
	 * ID가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다.
	 * 생성, 수정이 끝난 후에는 해당 타임존의 ID를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param timezone 신규/수정 등록하고자 하는 타임존 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return id 최종 등록한 타임존의 상세 정보를 가져오기 위한 ID
	 */
	@RequestMapping(value = "/createTimezone.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid Timezone timezone,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String id = timezone.getTimezoneId();
		boolean isCodeExist = timezoneService.exists(id);

		// timezoneCode가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {
			List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(timezone.getI18nMessageList(),
					IKepConstant.ITEM_TYPE_CODE_PORTAL, timezone.getTimezoneId());
			timezone.setUpdaterId("admin");
			timezone.setUpdaterName("관리자");
			timezone.setI18nMessageList(i18nMessageList);

			timezoneService.update(timezone);
		} else {
			List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(timezone.getI18nMessageList(),
					IKepConstant.ITEM_TYPE_CODE_PORTAL, timezone.getTimezoneId());
			timezone.setSortOrder(timezoneService.getMaxSortOrder());
			timezone.setRegisterId("admin");
			timezone.setRegisterName("관리자");
			timezone.setUpdaterId("admin");
			timezone.setUpdaterName("관리자");
			timezone.setI18nMessageList(i18nMessageList);

			timezoneService.create(timezone);
		}

		status.setComplete();

		return id;
	}

	/**
	 * 해당 타임존을 삭제한다.
	 * 
	 * @param id 삭제할 타임존 ID
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteTimezone.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("timezoneId") String id,
			AccessingResult accessResult, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		timezoneService.delete(id);

		return "redirect:/portal/admin/code/timezone/getList.do";
	}

	/**
	 * 해당 타임존을 목록에서 한 단계 위로 올린다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param id 목록에서 한 단계 위로 올릴 타임존의 ID
	 * @param sortOrder 해당 호칭에 새로 업데이트될 SortOrder
	 * @return String
	 */
	@RequestMapping(value = "/goUp.do")
	public @ResponseBody
	String goUp(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String id, String sortOrder) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("timezoneId", id);
		map.put("sortOrder", sortOrder);

		timezoneService.goUp(map);

		return "";
	}

	/**
	 * 해당 타임존을 목록에서 한 단계 아래로 내린다.
	 * 
	 * @param accessResult 사용자 권한체크 결과
	 * @param id 목록에서 한 단계 아래로 내릴 타임존의 ID
	 * @param sortOrder 해당 호칭에 새로 업데이트될 SortOrder
	 * @return String
	 */
	@RequestMapping(value = "/goDown.do")
	public @ResponseBody
	String goDown(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AccessingResult accessResult,
			String id, String sortOrder) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("timezoneId", id);
		map.put("sortOrder", sortOrder);

		timezoneService.goDown(map);

		return "";
	}

}
