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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTarget;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementTargetService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Assessment Management AssessmentManagementTargetManager controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementTargetManagerController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/assess/admin")
public class AssessmentManagementTargetManagerController extends CustomController {

	@Autowired
	private AssessmentManagementTargetService assessmentManagementTargetService;

	/**
	 * 관리범위 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/targetManagerView")
	public ModelAndView batchLogViewerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean assessmentManagementAdmin = isAdmin(user.getUserId());

		// 모듈리스트
		List<AssessmentManagementTarget> assessmentManagementTargetRequiredList = assessmentManagementTargetService.listRequiredByPortalId(portalId);
		List<AssessmentManagementTarget> assessmentManagementTargetUnrequiredAvailableList = assessmentManagementTargetService.listUnrequiredAvailableByPortalId(portalId);
		List<AssessmentManagementTarget> assessmentManagementTargetUnrequiredUnavailableList = assessmentManagementTargetService.listUnrequiredUnavailableByPortalId(portalId);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/assess/admin/targetManagerView");

		mav.addObject("menuId", "31");
		mav.addObject("assessmentManagementAdmin", assessmentManagementAdmin);
		mav.addObject("assessmentManagementTargetRequiredList", assessmentManagementTargetRequiredList);
		mav.addObject("assessmentManagementTargetUnrequiredAvailableList", assessmentManagementTargetUnrequiredAvailableList);
		mav.addObject("assessmentManagementTargetUnrequiredUnavailableList", assessmentManagementTargetUnrequiredUnavailableList);

		return mav;
	}

	/**
	 * 관리범위 설정값 저장
	 * @param moduleCode (사용자가 선택한 타겟정보가 (,)로 분리된 문자열로 들어온다)
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/saveTarget")
	public ModelAndView saveModule(
			@RequestParam(value = "moduleCode", required=false, defaultValue = "") String moduleCode,
			SessionStatus status) {

		// 사용자 정보
		User user = getSessionUser();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		AssessmentManagementTarget assessmentManagementTarget = new AssessmentManagementTarget();
		assessmentManagementTarget.setPortalId(user.getPortalId());
		assessmentManagementTarget.setRegisterId(user.getUserId());
		assessmentManagementTarget.setModuleCodeCommaString(moduleCode);

		assessmentManagementTargetService.saveModules(assessmentManagementTarget);

		status.setComplete();

		// view 연결
		ModelAndView mav = new ModelAndView("redirect:/collpack/assess/admin/targetManagerView.do");

		return mav;
	}

}
