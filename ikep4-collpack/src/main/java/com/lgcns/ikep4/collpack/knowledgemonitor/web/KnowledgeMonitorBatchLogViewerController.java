/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Monitor KnowledgeMonitorBatchLogViewer controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorBatchLogViewerController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgemonitor/admin")
public class KnowledgeMonitorBatchLogViewerController extends CustomController {

	//@Autowired
	//private KnowledgeMonitorCommentPolicyService knowledgeMonitorCommentPolicyService;

	//@Autowired
	//private KnowledgeMonitorStatisticsService knowledgeMonitorStatisticsService;
	//@Autowired
	//private KnowledgeMonitorCviPointService knowledgeMonitorCviPointService;

	
	/**
	 * 관리프로그램 실행로그 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/batchLogViewerView")
	public ModelAndView batchLogViewerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		//String portalId = user.getPortalId();

		// 관리자 권한
		boolean knowledgeMonitorAdmin = isAdmin(user.getUserId());

		// 정책리스트
		//List<KnowledgeMonitorCommentPolicy> knowledgeMonitorCommentPolicyList = knowledgeMonitorCommentPolicyService.listByPortalId(portalId);

		// 일별 지식등록 건수 집계
		//knowledgeMonitorStatisticsService.batchGatherKnowledge();

		// 일별 지식/CVI 집계
		//knowledgeMonitorCviPointService.batchGatherKnowledge();

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/knowledgemonitor/admin/batchLogViewerView");

		mav.addObject("menuId", "34");
		mav.addObject("knowledgeMonitorAdmin", knowledgeMonitorAdmin);
		//mav.addObject("knowledgeMonitorCommentPolicyList", knowledgeMonitorCommentPolicyList);

		return mav;
	}



}
