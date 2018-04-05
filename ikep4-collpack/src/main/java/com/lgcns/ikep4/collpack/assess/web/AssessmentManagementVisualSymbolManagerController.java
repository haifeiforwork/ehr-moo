/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPviSymbol;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPviSymbolService;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Assessment Management AssessmentManagementVisualSymbolManager controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementVisualSymbolManagerController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/assess/admin")
public class AssessmentManagementVisualSymbolManagerController extends CustomController {

	@Autowired
	private AssessmentManagementPviSymbolService assessmentManagementPviSymbolService;

	@Autowired
	private AssessmentManagementUserPviService assessmentManagementUserPviService;
	

	/**
	 * Visual Symbol 관리 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/visualSymbolManagerView")
	public ModelAndView batchLogViewerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean assessmentManagementAdmin = isAdmin(user.getUserId());

		// PVI 최대값
		int maxPvi = assessmentManagementUserPviService.getMaxPviByPortalId(portalId);

		// Symbol 정보 리스트
		List<AssessmentManagementPviSymbol> assessmentManagementPviSymbolList = assessmentManagementPviSymbolService.listByPortalId(portalId);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/assess/admin/visualSymbolManagerView");

		mav.addObject("menuId", "35");
		mav.addObject("assessmentManagementAdmin", assessmentManagementAdmin);
		mav.addObject("maxPvi", maxPvi);
		mav.addObject("assessmentManagementPviSymbolList", assessmentManagementPviSymbolList);

		return mav;
	}

	/**
	 * Visual Symbol 저장
	 * @param symbolMap - symbol 데이터 Map
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/saveVisualSymbol")
	public ModelAndView saveContentCriteria(
			@RequestParam Map<String, String> symbolMap,
			SessionStatus status) {

		// 사용자 정보
		User user = getSessionUser();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		// Symbol 저장
		assessmentManagementPviSymbolService.saveVisualSymbol(symbolMap, user.getUserId());

		status.setComplete();

		// view 연결
		ModelAndView mav = new ModelAndView("redirect:/collpack/assess/admin/visualSymbolManagerView.do");

		return mav;
	}

}
