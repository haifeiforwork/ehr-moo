/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.lgcns.ikep4.approval.work.model.ApprExam;
import com.lgcns.ikep4.approval.work.service.ApprExamService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 검토요청 Controller
 * 
 * @author jeehye
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/apprExam")
@SessionAttributes("apprExam")
public class ApprExamController extends BaseController {

	@Autowired
	private ApprExamService apprExamService;

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
	 * 검토 요청 설정
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateApprExamForm")
	public ModelAndView updateApprExamForm(@RequestParam(value = "apprId", required = false) String apprId) {

		ModelAndView mav = new ModelAndView("/approval/work/apprExam/updateApprExamForm");
		ApprExam apprExam = new ApprExam();

		mav.addObject("apprId", apprId);
		mav.addObject("apprExam", apprExam);
		mav.addObject("isOpenList", ApprExam.getIsOpenList());
		mav.addObject("isRequestList", ApprExam.getIsRequestList());

		return mav;
	}

	/**
	 * 검토 요청 설정 저장
	 * 
	 * @param model
	 * @param ApprExam
	 * @return
	 */
	@RequestMapping(value = "/updateApprExam.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateApprExam(Model model, ApprExam apprExam, SessionStatus status) {

		String rtnValue = "";
		try {

			if (apprExamService.existSetExamUser(apprExam)) {
				// status.setComplete();
				rtnValue = "EXIST";

			} else {
				User user = (User) getRequestAttribute("ikep.user");

				apprExam.setExamReqId(user.getUserId());

				String examFirstReqId = apprExamService.examFirstReqId(apprExam.getApprId());
				if (examFirstReqId == null || examFirstReqId.equals("")) {
					apprExam.setExamFirstReqId(user.getUserId());
				} else {
					apprExam.setExamFirstReqId(examFirstReqId);
				}
				apprExamService.apprExamCreate(apprExam);

				status.setComplete();
				rtnValue = "OK";
			}

		} catch (Exception ex) {

			throw new IKEP4AjaxException("updateApprExam", ex);
		}

		return rtnValue;

	}

	/**
	 * 검토 요청 중복 아이디 체크
	 * 
	 * @param model
	 * @param ApprExam
	 * @return
	 */
	@RequestMapping(value = "/existExamUser.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String existExamUser(Model model, ApprExam apprExam, SessionStatus status) {

		String rtnValue = "";
		try {

			if (!apprExamService.existSetExamUser(apprExam)) {
				// 세션 상태를 complete
				status.setComplete();
				rtnValue = "OK";
			}

		} catch (Exception ex) {

			throw new IKEP4AjaxException("existExamUser", ex);
		}

		return rtnValue;

	}

	@RequestMapping(value = "/listApprExamInfo.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listApprLineInfo(@RequestParam(value = "apprId", required = true) String apprId,
			HttpServletRequest request, HttpServletResponse response, SessionStatus status) {

		ModelAndView modelAndView = new ModelAndView();

		try {

			modelAndView.setViewName("/approval/work/apprExam/listApprExamInfo");

			User user = (User) getRequestAttribute("ikep.user");
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			ApprExam apprExam = new ApprExam();
			apprExam.setApprId(apprId);
			apprExam.setUserId(user.getUserId());

			List<ApprExam> listApprExamInfo = apprExamService.listApprExamInfo(apprExam);

			modelAndView.addObject("listApprExamInfo", listApprExamInfo);
			modelAndView.addObject("isSystemAdmin", this.isSystemAdmin(user));
			modelAndView.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

			// 세션 상태를 complete
			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("listApprExamInfo", ex);
		}

		return this.bindResult(modelAndView);
	}

	@RequestMapping(value = "/updateApprExamInfoSave.do")
	public ModelAndView updateApprExamInfoSave(@RequestParam(value = "apprId", required = true) String apprId,
			@RequestParam(value = "examContent", required = true) String examContent,
			@RequestParam(value = "examUserId", required = true) String examUserId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/work/apprExam/listApprExamInfo");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		User user = (User) getRequestAttribute("ikep.user");

		ApprExam apprExam = new ApprExam();
		apprExam.setApprId(apprId);
		apprExam.setExamUserId(examUserId);
		apprExam.setExamContent(examContent);

		apprExamService.updateApprExamInfoSave(apprExam);

		modelAndView.addObject("isSystemAdmin", this.isSystemAdmin(user));
		modelAndView.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		return modelAndView;
	}

}
