/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Assessment Management AssessmentManagementBatchLogViewer controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementBatchLogViewerController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/assess/admin")
public class AssessmentManagementBatchLogViewerController extends CustomController {

	//@Autowired
	//private KnowledgeMonitorCommentPolicyService knowledgeMonitorCommentPolicyService;
	//@Autowired
	//private AssessmentManagementUserPviService assessmentManagementUserPviService;

	/**
	 * 사용자 평가순위 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/batchLogViewerView")
	public ModelAndView batchLogViewerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		//String portalId = user.getPortalId();

		// 관리자 권한
		boolean assessmentManagementAdmin = isAdmin(user.getUserId());

		// 정책리스트
		//List<KnowledgeMonitorCommentPolicy> knowledgeMonitorCommentPolicyList = knowledgeMonitorCommentPolicyService.listByPortalId(portalId);
		//assessmentManagementUserPviService.batchGatherAccessment();

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/assess/admin/batchLogViewerView");

		mav.addObject("menuId", "34");
		mav.addObject("assessmentManagementAdmin", assessmentManagementAdmin);
		//mav.addObject("knowledgeMonitorCommentPolicyList", knowledgeMonitorCommentPolicyList);

		return mav;
	}

}
