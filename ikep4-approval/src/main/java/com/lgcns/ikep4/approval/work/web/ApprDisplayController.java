/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.work.model.ApprDisplay;
import com.lgcns.ikep4.approval.work.search.ApprDisplaySearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprDisplayService;
import com.lgcns.ikep4.approval.work.util.DateUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 공람 관리 Controller
 * 
 * @author jeehye
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/apprDisplay")
@SessionAttributes("apprDisplay")
public class ApprDisplayController extends BaseController {

	@Autowired
	private ApprDisplayService apprDisplayService;

	@Autowired
	ApprAdminConfigService apprAdminConfigService;

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
	 * 
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId) {

		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		if (apprAdminConfig.getIsReadAll().equals("1")) {
			// IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}

	/**
	 * 공람지정 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createApprDisplayForm")
	public ModelAndView createApprDisplayForm(@RequestParam(value = "apprId", required = false) String apprId) {

		ModelAndView mav = new ModelAndView("/approval/work/apprDisplay/createApprDisplayForm");
		ApprDisplay apprDisplay = new ApprDisplay();

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));
		mav.addObject("apprId", apprId);
		mav.addObject("apprDisplay", apprDisplay);

		return mav;
	}

	/**
	 * 공람지정 저장
	 * 
	 * @param apprId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createApprDisplay.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String createApprDisplay(@RequestParam(value = "apprId", required = false) String apprId, ApprDisplay apprDisplay,
			SessionStatus status) {

		String rtnValue = "";
		try {
			User user = (User) getRequestAttribute("ikep.user");
			apprDisplay.setApprId(apprId);
			apprDisplay.setRegisterId(user.getUserId());
			apprDisplay.setRegisterName(user.getUserName());

			apprDisplayService.createApprDisplay(apprDisplay);
			// 세션 상태를 complete
			status.setComplete();
			rtnValue = "OK";

		} catch (Exception ex) {

			throw new IKEP4AjaxException("createApprDisplay", ex);
		}

		return rtnValue;

	}

	/**
	 * 공람지정 목록
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listApprDisplayPop.do")
	public ModelAndView listApprDisplayPop(@RequestParam(value = "apprId", required = false) String apprId,
			ApprDisplaySearchCondition apprDisplaySearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprDisplay/listApprDisplayPop");

		if (apprDisplaySearchCondition == null)
			apprDisplaySearchCondition = new ApprDisplaySearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();

		String portalId = (String) getRequestAttribute("ikep.portalId");

		apprDisplaySearchCondition.setUserId(userId);
		apprDisplaySearchCondition.setApprId(apprId);
		apprDisplaySearchCondition.setPortalId(portalId);

		SearchResult<ApprDisplay> searchResult = this.apprDisplayService
				.listBySearchCondition(apprDisplaySearchCondition);

		// 문서상태 목록

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprDisplay.getPageNumList());
		mav.addObject("userStatusList", ApprDisplay.getUserStatusList());

		return mav;
	}

	/**
	 * 공람자 회수
	 * 
	 * @param apprIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteApprDisplayList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String deleteApprDisplayList(@RequestParam("userIds") List<String> userIds,
			@RequestParam("displayConfirm") String displayConfirm, @RequestParam("apprId") String apprId,
			SessionStatus status) {

		String returnValue = "";

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		try {
			if (displayConfirm.equals("all")) {

				String userId = "";
				apprDisplayService.deleteApprDisplay(userId, apprId);

				status.setComplete();
				returnValue = "OK";

			} else if (displayConfirm.equals("part")) {

				for (int i = 0; i < userIds.size(); i++) {

					String userId = userIds.get(i);
					apprDisplayService.deleteApprDisplay(userId, apprId);
				}

				status.setComplete();
				returnValue = "OK";
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteApprList", ex);
		}

		return returnValue;
	}

	/**
	 * 공람대기함
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listApprDisplayWaiting.do")
	public ModelAndView listApprDisplayWaiting(ApprDisplaySearchCondition apprDisplaySearchCondition)
			throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprDisplay/listApprDisplay");

		if (apprDisplaySearchCondition == null)
			apprDisplaySearchCondition = new ApprDisplaySearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		String listType = "listApprDisplayWaiting";

		apprDisplaySearchCondition.setPortalId(portalId);
		apprDisplaySearchCondition.setUserId(userId);
		apprDisplaySearchCondition.setListType(listType);

		if (apprDisplaySearchCondition.getSortColumn() == null || apprDisplaySearchCondition.getSortColumn().equals("")) {
			apprDisplaySearchCondition.setSortColumn("dRegistDate");
			apprDisplaySearchCondition.setSortType("DESC");
		}

		SearchResult<ApprDisplay> searchResult = this.apprDisplayService
				.listByDisplaySearchCondition(apprDisplaySearchCondition);

		// 문서상태 목록

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprDisplay.getPageNumList());

		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 공람완료함
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listApprDisplayComplete.do")
	public ModelAndView listApprDisplayComplete(ApprDisplaySearchCondition apprDisplaySearchCondition)
			throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprDisplay/listApprDisplay");

		if (apprDisplaySearchCondition == null)
			apprDisplaySearchCondition = new ApprDisplaySearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		String listType = "listApprDisplayComplete";

		apprDisplaySearchCondition.setPortalId(portalId);
		apprDisplaySearchCondition.setUserId(userId);
		apprDisplaySearchCondition.setListType(listType);

		if (apprDisplaySearchCondition.getSortColumn() == null || apprDisplaySearchCondition.getSortColumn().equals("")) {
			apprDisplaySearchCondition.setSortColumn("confirmDate");
			apprDisplaySearchCondition.setSortType("DESC");
		}

		if (apprDisplaySearchCondition.getSearchStartDate() == null
				|| apprDisplaySearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprDisplaySearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprDisplaySearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprDisplay> searchResult = this.apprDisplayService
				.listByDisplaySearchCondition(apprDisplaySearchCondition);

		// 문서상태 목록

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprDisplay.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

}
