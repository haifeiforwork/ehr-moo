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
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.LocaleCodeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 로케일코드 관리용 Controller
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: LocaleCodeController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/code/localecode")
@SessionAttributes("localecode")
public class LocaleCodeController extends BaseController {

	@Autowired
	private LocaleCodeService localeCodeService;

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

		ModelAndView mav = new ModelAndView("portal/admin/code/localecode/localeCodeList");

		User sessionUser = (User) getRequestAttribute("ikep.user");

		LocaleCode localeCodeVO = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String code = request.getParameter("tempCode");

			String userLocaleCode = sessionUser.getLocaleCode();

			if (code != null && !"".equalsIgnoreCase(code)) {
				localeCodeVO = localeCodeService.read(code);
				localeCodeVO.setCodeCheck("modify");
				localeCodeVO.setI18nMessageList(i18nMessageService.makeExistLocaleList(
						IKepConstant.ITEM_TYPE_CODE_PORTAL, code, "localeName"));

				mav.addObject("oldSortOrder", localeCodeVO.getSortOrder());
			} else {
				localeCodeVO = new LocaleCode();
				localeCodeVO.setI18nMessageList(i18nMessageService.makeInitLocaleList("localeName"));
			}

			searchCondition.setFieldName("localeName");
			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());

			SearchResult<LocaleCode> searchResult = localeCodeService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", userLocaleCode);
			mav.addObject("localeCodeVO", localeCodeVO);
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
	 * @param code 상세정보를 요청받은 Locale Code
	 * @param fieldName 다국어메시지 표현에 필요한 필드네임
	 * @param itemTypeCode 다국어메시지 표현에 필요한 ItemType의 Code
	 * @param model Model 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String 상세정보화면 URI
	 */
	@RequestMapping(value = "/getForm.do", method = RequestMethod.GET)
	public String getForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String code,
			String fieldName, String itemTypeCode, Model model, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		LocaleCode localeCodeVO = null;

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();

		if (code != null && !"".equalsIgnoreCase(code)) {
			localeCodeVO = localeCodeService.read(code);
			localeCodeVO.setCodeCheck("modify");
			localeCodeVO.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
					code, "localeName"));

			model.addAttribute("oldSortOrder", localeCodeVO.getSortOrder());
		} else {
			localeCodeVO = new LocaleCode();
			localeCodeVO.setCodeCheck("new");
			localeCodeVO.setI18nMessageList(i18nMessageService.makeInitLocaleList("localeName"));
		}

		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("localeCodeVO", localeCodeVO);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());

		return "portal/admin/code/localecode/form";
	}

	/**
	 * 등록/수정시에 해당 ID의 중복을 체크하여
	 * 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을 반환한다.
	 * 
	 * @param code 중복을 체크할 Locale Code
	 * @param accessResult 사용자 권한체크 결과
	 * @return String 중복 유무를 알려주는 문자열 Flag
	 */
	@RequestMapping(value = "/checkCode.do")
	public @ResponseBody
	String checkCode(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String code,
			AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		boolean result = localeCodeService.exists(code);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * Locale을 신규로 등록하거나 수정한다.
	 * Code가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다.
	 * 생성, 수정이 끝난 후에는 해당 Locale의 Code를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param localeCodeVO 신규/수정 등록하고자 하는 Locale 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return 최종 등록한 Locale의 상세 정보를 가져오기 위한 Code
	 */
	@RequestMapping(value = "/createLocaleCode.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid LocaleCode localeCodeVO,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String code = localeCodeVO.getLocaleCode();
		boolean isCodeExist = localeCodeService.exists(code);

		if (isCodeExist) {
			List<I18nMessage> i18nMessageList = i18nMessageService
					.fillMandatoryField(localeCodeVO.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
							localeCodeVO.getLocaleCode());
			localeCodeVO.setUpdaterId("admin");
			localeCodeVO.setUpdaterName("관리자");
			localeCodeVO.setI18nMessageList(i18nMessageList);

			localeCodeService.update(localeCodeVO);
		} else {
			List<I18nMessage> i18nMessageList = i18nMessageService
					.fillMandatoryField(localeCodeVO.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
							localeCodeVO.getLocaleCode());
			localeCodeVO.setSortOrder(localeCodeService.getMaxSortOrder());
			localeCodeVO.setI18nMessageList(i18nMessageList);
			localeCodeVO.setRegisterId("admin");
			localeCodeVO.setRegisterName("관리자");
			localeCodeVO.setUpdaterId("admin");
			localeCodeVO.setUpdaterName("관리자");

			localeCodeService.create(localeCodeVO);
		}

		status.setComplete();

		return code;
	}

	/**
	 * 해당 Locale을 삭제한다.
	 * 
	 * @param code 삭제할 Locale Code
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteLocaleCode.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("localeCode") String code,
			AccessingResult accessResult, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		localeCodeService.remove(code);

		return "redirect:/portal/admin/code/localecode/getList.do";
	}

	/**
	 * 해당 Locale을 목록에서 한 단계 위로 올린다.
	 * 
	 * @param code 목록에서 한 단계 위로 올릴 Locale의 Code
	 * @param sortOrder 해당 Locale에 새로 업데이트될 SortOrder
	 * @param accessResult 사용자 권한체크 결과
	 * @return String
	 */
	@RequestMapping(value = "/goUp.do")
	public @ResponseBody
	String goUp(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String code,
			String sortOrder, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("localeCode", code);
		map.put("sortOrder", sortOrder);

		localeCodeService.goUp(map);

		return "";
	}

	/**
	 * 해당 Locale을 목록에서 한 단계 아래로 내린다.
	 * 
	 * @param code 목록에서 한 단계 아래로 내릴 Locale의 Code
	 * @param sortOrder 해당 Locale에 새로 업데이트될 SortOrder
	 * @param accessResult 사용자 권한체크 결과
	 * @return String
	 */
	@RequestMapping(value = "/goDown.do")
	public @ResponseBody
	String goDown(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String code,
			String sortOrder, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("localeCode", code);
		map.put("sortOrder", sortOrder);

		localeCodeService.goDown(map);

		return "";
	}
}
