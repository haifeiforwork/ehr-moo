/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Monitor KnowledgeMonitorCommentPolicy controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCommentPolicyController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgemonitor/admin")
public class KnowledgeMonitorCommentPolicyController extends CustomController {

	@Autowired
	private KnowledgeMonitorModuleService knowledgeMonitorModuleService;

	/**
	 * 우수 Comment 기준관리 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/commentCriteriaManagerView")
	public ModelAndView commentCriteriaManagerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean knowledgeMonitorAdmin = isAdmin(user.getUserId());

		// 정책리스트
		List<KnowledgeMonitorModule> knowledgeMonitorModuleList = knowledgeMonitorModuleService.listCommentPolicyByPortalId(portalId);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/knowledgemonitor/admin/commentCriteriaManagerView");

		mav.addObject("menuId", "33");
		mav.addObject("knowledgeMonitorAdmin", knowledgeMonitorAdmin);
		mav.addObject("knowledgeMonitorModuleList", knowledgeMonitorModuleList);

		return mav;
	}

	/**
	 * 정책 저장
	 * @param policyMap
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/saveCommentCriteria")
	public ModelAndView saveCommentCriteria(
			@RequestParam Map<String, String> policyMap,
			SessionStatus status) {

		// 사용자 정보
		User user = getSessionUser();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		// 정책 저장
		knowledgeMonitorModuleService.saveCommentCriteria(policyMap, user);

		status.setComplete();

		// view 연결
		ModelAndView mav = new ModelAndView("redirect:/collpack/knowledgemonitor/admin/commentCriteriaManagerView.do");

		return mav;
	}

}
