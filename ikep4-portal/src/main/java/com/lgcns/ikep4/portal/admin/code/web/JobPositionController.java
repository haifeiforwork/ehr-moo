/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.code.web;

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
import com.lgcns.ikep4.support.user.code.model.JobPosition;
import com.lgcns.ikep4.support.user.code.service.JobPositionService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 직위코드 관리용 Controller
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobPositionController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/admin/code/jobposition")
@SessionAttributes("jobposition")
public class JobPositionController extends BaseController {

	@Autowired
	private JobPositionService jobPositionService;

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

		ModelAndView mav = new ModelAndView("portal/admin/code/jobposition/jobPositionList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		JobPosition jobPosition = null;

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			String jobPositionCode = request.getParameter("tempCode");

			if (jobPositionCode != null && !"".equalsIgnoreCase(jobPositionCode)) {

				jobPosition = jobPositionService.read(jobPositionCode);
				jobPosition.setCodeCheck("modify");

				mav.addObject("oldSortOrder", jobPosition.getSortOrder());
			} else {
				jobPosition = new JobPosition();
				jobPosition.setSortOrder(jobPositionService.getMaxSortOrder());
			}

			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());

			SearchResult<JobPosition> searchResult = jobPositionService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("userLocaleCode", sessionUser.getLocaleCode());
			mav.addObject("jobPosition", jobPosition);
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
	 * @param code 상세정보를 요청받은 직위 Code
	 * @param model Model 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @return String 상세정보화면 URI
	 */
	@RequestMapping(value = "/getForm.do", method = RequestMethod.GET)
	public String getForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String code,
			Model model, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		JobPosition jobPosition = null;

		User sessionUser = (User) getRequestAttribute("ikep.user");

		if (code != null && !"".equalsIgnoreCase(code)) {
			jobPosition = jobPositionService.read(code);
			jobPosition.setCodeCheck("modify");

			model.addAttribute("oldSortOrder", jobPosition.getSortOrder());
		} else {
			jobPosition = new JobPosition();
			jobPosition.setCodeCheck("new");
			jobPosition.setSortOrder(jobPositionService.getMaxSortOrder());
		}

		model.addAttribute("userLocaleCode", sessionUser.getLocaleCode());
		model.addAttribute("jobPosition", jobPosition);

		return "portal/admin/code/jobposition/form";
	}

	/**
	 * 등록/수정시에 해당 Code의 중복을 체크한다.
	 * 중복된 경우 "duplicated" 중복이 아닌 경우 "success"라는 문자열을 반환한다.
	 * 
	 * @param code 중복을 체크할 직위 Code
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

		boolean result = jobPositionService.exists(code);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}

	/**
	 * 직위을 신규로 등록하거나 수정한다.
	 * Code가 중복되는 경우 수정, 중복되지 않는 경우 생성 프로세스를 진행한다.
	 * 생성, 수정이 끝난 후에는 해당 ItemType의 Code를 반환하여 form을 불러오는데 사용한다.
	 * 
	 * @param jobPosition 신규/수정 등록하고자 하는 직위(JobPosition) 객체
	 * @param accessResult 사용자 권한체크 결과
	 * @param result BindingResult 객체
	 * @param status SessionStatus 객체
	 * @param request HttpServletRequest 객체
	 * @return 최종 등록한 직위의 상세 정보를 가져오기 위한 Code
	 */
	@RequestMapping(value = "/createJobPosition.do", method = RequestMethod.POST)
	public @ResponseBody
	String onSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @ValidEx JobPosition jobPosition,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		String code = jobPosition.getJobPositionCode();
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		boolean isCodeExist = jobPositionService.exists(code);

		// jobPositionCode가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {

			jobPosition.setPortalId(portal.getPortalId());
			jobPosition.setUpdaterId("admin");
			jobPosition.setUpdaterName("관리자");

			jobPositionService.update(jobPosition);

		} else {

			jobPosition.setSortOrder(request.getParameter("sortOrder"));
			jobPosition.setPortalId(portal.getPortalId());
			jobPosition.setRegisterId("admin");
			jobPosition.setRegisterName("관리자");
			jobPosition.setUpdaterId("admin");
			jobPosition.setUpdaterName("관리자");

			jobPositionService.create(jobPosition);
		}

		status.setComplete();

		return code;

	}

	/**
	 * 해당 직위을 삭제한다.
	 * 
	 * @param code 삭제할 직위 Code
	 * @param accessResult 사용자 권한체크 결과
	 * @param request HttpServletRequest 객체
	 * @return String redirect 되는 URI
	 */
	@RequestMapping(value = "/deleteJobPosition.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("jobPositionCode") String code,
			AccessingResult accessResult, HttpServletRequest request) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		jobPositionService.delete(code);

		return "redirect:/portal/admin/code/jobposition/getList.do";
	}

	/**
	 * 해당 직위을 목록에서 한 단계 위로 올린다.
	 * 
	 * @param code 목록에서 한 단계 위로 올릴 직위의 Code
	 * @param sortOrder 해당 직위에 새로 업데이트될 SortOrder
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
		map.put("jobPositionCode", code);
		map.put("sortOrder", sortOrder);

		jobPositionService.goUp(map);

		return "";
	}

	/**
	 * 해당 직위을 목록에서 한 단계 아래로 내린다.
	 * 
	 * @param code 목록에서 한 단계 아래로 내릴 직위의 Code
	 * @param sortOrder 해당 직위에 새로 업데이트될 SortOrder
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
		map.put("jobPositionCode", code);
		map.put("sortOrder", sortOrder);

		jobPositionService.goDown(map);

		return "";
	}

}