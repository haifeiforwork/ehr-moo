/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLog;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementInitializationLogService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Assessment Management AssessmentManagementAssessmentScoreInitialize controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementAssessmentScoreInitializeController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/assess/admin")
public class AssessmentManagementAssessmentScoreInitializeController extends CustomController {

	@Autowired
	private AssessmentManagementInitializationLogService assessmentManagementInitializationLogService;

	/**
	 * 평가점수 초기화 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/assessmentScoreInitializeView")
	public ModelAndView batchLogViewerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean assessmentManagementAdmin = isAdmin(user.getUserId());

		// 평가점수 초기화리스트
		List<AssessmentManagementInitializationLog> assessmentManagementInitializationLogList = assessmentManagementInitializationLogService.listByPortalId(portalId);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/assess/admin/assessmentScoreInitializeView");

		mav.addObject("menuId", "33");
		mav.addObject("assessmentManagementAdmin", assessmentManagementAdmin);
		mav.addObject("assessmentManagementInitializationLogList", assessmentManagementInitializationLogList);

		return mav;
	}

	/**
	 * 평가점수 초기화 처리
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/scoreInitialize")
	public ModelAndView saveModule(SessionStatus status) {

		// 사용자 정보
		User user = getSessionUser();
		String userId = user.getUserId();
		String portalId = user.getPortalId();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		// 초기화 처리
		assessmentManagementInitializationLogService.scoreInitialization(portalId, userId);

		// 로그생성
		AssessmentManagementInitializationLog assessmentManagementInitializationLog = new AssessmentManagementInitializationLog();
		assessmentManagementInitializationLog.setPortalId(portalId);
		assessmentManagementInitializationLog.setRegisterId(userId);
		assessmentManagementInitializationLogService.create(assessmentManagementInitializationLog);

		status.setComplete();

		// view 연결
		ModelAndView mav = new ModelAndView("redirect:/collpack/assess/admin/assessmentScoreInitializeView.do");

		return mav;
	}

}
