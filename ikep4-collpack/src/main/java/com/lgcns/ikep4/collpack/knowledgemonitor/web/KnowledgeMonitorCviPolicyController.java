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
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Monitor KnowledgeMonitorCviPolicy controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPolicyController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgemonitor/admin")
public class KnowledgeMonitorCviPolicyController extends CustomController {

	@Autowired
	private KnowledgeMonitorModuleService knowledgeMonitorModuleService;

	/**
	 * ModuleCode를 (,)로 분리된 문자열로 변환
	 * @param knowledgeMonitorModuleList
	 * @return String - (,)로 분리된 모듈 항목
	 */
	private String getModuleCommaString(List<KnowledgeMonitorModule> knowledgeMonitorModuleList) {
		StringBuffer sb = new StringBuffer();
		for (KnowledgeMonitorModule item : knowledgeMonitorModuleList) {
			sb.append(CommonConstant.COMMA_SEPARATER);
			sb.append(item.getModuleCode());
		}

		if (1 > sb.length()) {
			return "";
		} else {
			return sb.toString().substring(1);
		}
	}

	/**
	 * CVI/우수자료 기준관리 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/contentCriteriaManagerView")
	public ModelAndView contentCriteriaManagerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean knowledgeMonitorAdmin = isAdmin(user.getUserId());

		// 정책리스트
		List<KnowledgeMonitorModule> knowledgeMonitorModuleList = knowledgeMonitorModuleService.listContentPolicyByPortalId(portalId);

		// 평가기간
		int cviEvaluationTerm = 0;
		if (0 < knowledgeMonitorModuleList.size()) {
			cviEvaluationTerm = knowledgeMonitorModuleList.get(0).getCviEvaluationTerm();
		}

		// 조회된 모듈 변환
		String moduleCodeCommaString = getModuleCommaString(knowledgeMonitorModuleList);

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/knowledgemonitor/admin/contentCriteriaManagerView");

		mav.addObject("menuId", "32");
		mav.addObject("knowledgeMonitorAdmin", knowledgeMonitorAdmin);
		mav.addObject("knowledgeMonitorModuleList", knowledgeMonitorModuleList);
		mav.addObject("cviEvaluationTerm", cviEvaluationTerm);
		mav.addObject("moduleCodeCommaString", moduleCodeCommaString);

		return mav;
	}

	/**
	 * CVI/우수자료 정책 저장
	 * @param policyMap - 정책 맵
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/saveContentCriteria")
	public ModelAndView saveContentCriteria(
			@RequestParam Map<String, String> policyMap,
			SessionStatus status) {

		// 사용자 정보
		User user = getSessionUser();

		// 관리자 권한 체크
		checkAdmin(user.getUserId());

		// 정책 저장
		knowledgeMonitorModuleService.saveContentCriteria(policyMap, user);

		status.setComplete();

		// view 연결
		ModelAndView mav = new ModelAndView("redirect:/collpack/knowledgemonitor/admin/contentCriteriaManagerView.do");

		return mav;
	}


}
