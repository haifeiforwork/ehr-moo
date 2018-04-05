/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.code.web;

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
import com.lgcns.ikep4.support.user.code.model.OperationCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.code.service.OperationCodeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * OPERATION 코드 관리용 Controller
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: OperationCodeController.java 5303 2011-04-06 05:42:54Z yangsae
 *          $
 */
@Controller
@RequestMapping(value = "/portal/admin/code/operationcode")
@SessionAttributes("operationCode")
public class OperationCodeController extends BaseController {

	@Autowired
	private OperationCodeService operationCodeService;

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

		ModelAndView mav = new ModelAndView("portal/admin/code/operationcode/operationCodeList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		OperationCode operationCode = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("OPERATION_ID");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String id = request.getParameter("tempId");

			String userLocaleCode = sessionUser.getLocaleCode();

			if (id != null && !"".equalsIgnoreCase(id)) {
				operationCode = operationCodeService.read(id);
				operationCode.setCodeCheck("modify");
				operationCode.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
						id, "operationName"));
				
			} else {
				operationCode = new OperationCode();
				operationCode.setI18nMessageList(i18nMessageService.makeInitLocaleList("operationName"));
			}

			searchCondition.setFieldName("operationName");
			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<OperationCode> searchResult = operationCodeService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", userLocaleCode);
			mav.addObject("operationCode", operationCode);
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
	 * @param id 상세정보를 요청받은 Operation ID
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

		OperationCode operationCode = null;

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();

		if (id != null && !"".equalsIgnoreCase(id)) {
			operationCode = operationCodeService.read(id);
			operationCode.setCodeCheck("modify");
			operationCode.setI18nMessageList(i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL,
					id, "operationName"));
		} else {
			operationCode = new OperationCode();
			operationCode.setCodeCheck("new");
			operationCode.setI18nMessageList(i18nMessageService.makeInitLocaleList("operationName"));
		}

		model.addAttribute("userLocaleCode", userLocaleCode);
		model.addAttribute("operationCode", operationCode);
		model.addAttribute("localeSize", i18nMessageService.selectLocaleAll().size());

		return "portal/admin/code/operationcode/form";
	}

	/**
	 * 등록/수정시에 해당 ID의 중복을 체크하여
	 * 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을 반환한다.
	 * 
	 * @param id 중복을 체크할 Operation ID
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

		boolean result = operationCodeService.exists(id);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * Operation을 신규로 등록하거나 수정한다.
	 * Code가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다.
	 * 생성, 수정이 끝난 후에는 해당 Operation의 Code를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param operationCode 신규/수정 등록하고자 하는 Operation 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return id 최종 등록한 Operation의 상세 정보를 가져오기 위한 ID
	 */
	@RequestMapping(value = "/createOperationCode.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid OperationCode operationCode,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String id = operationCode.getOperationId();
		boolean isCodeExist = operationCodeService.exists(id);
		
		// operationCode가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {
			List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
					operationCode.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
					operationCode.getOperationId());
			operationCode.setUpdaterId("admin");
			operationCode.setUpdaterName("관리자");
			operationCode.setI18nMessageList(i18nMessageList);

			operationCodeService.update(operationCode);
		} else {
			List<I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(
					operationCode.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL,
					operationCode.getOperationId());
			operationCode.setRegisterId("admin");
			operationCode.setRegisterName("관리자");
			operationCode.setUpdaterId("admin");
			operationCode.setUpdaterName("관리자");
			operationCode.setI18nMessageList(i18nMessageList);

			operationCodeService.create(operationCode);
		}

		status.setComplete();

		return id;
	}

	/**
	 * 해당 Operation을 삭제한다.
	 * 
	 * @param code 삭제할 Operation Code
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteOperationCode.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("operationId") String id,
			AccessingResult accessResult, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		operationCodeService.delete(id);

		return "redirect:/portal/admin/code/operationcode/getList.do";
	}

}
