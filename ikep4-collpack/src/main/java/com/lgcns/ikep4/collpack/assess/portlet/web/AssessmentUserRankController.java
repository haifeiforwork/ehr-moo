/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.portlet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.collpack.assess.web.CustomController;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;

@Controller
@RequestMapping(value = "/collpack/assess/portlet/assessmentUserRank")
public class AssessmentUserRankController extends CustomController{

	@Autowired
	private AssessmentManagementUserPviService assessmentManagementUserPviService;

	@Autowired
	private CacheService cacheService;
	
	/**
	 * 조회건수
	 */
	private static final int MAX_RECORD_COUNT = 10;

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {

		// 목록 캐시에서 조회
		List<AssessmentManagementUserPvi> assessmentManagementUserPviList = (List<AssessmentManagementUserPvi>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(assessmentManagementUserPviList == null) {
			// Session 정보
			User user = getSessionUser();
			String portalId = user.getPortalId();
			
			// 상위 10건 조회
			AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition = new AssessmentManagementBlockPageCondition();
			assessmentManagementBlockPageCondition.setPortalId(portalId);
			assessmentManagementBlockPageCondition.setTotalCount(MAX_RECORD_COUNT);
			assessmentManagementBlockPageCondition.calculate();
			assessmentManagementUserPviList = assessmentManagementUserPviService.listByPortalIdPage(assessmentManagementBlockPageCondition);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, assessmentManagementUserPviList);
		}

		// Locale 설정
		if (!isSameLocale()) {
			for (AssessmentManagementUserPvi item : assessmentManagementUserPviList) {
				item.setUserName(item.getUserEnglishName());
				item.setTeamName(item.getTeamEnglishName());
				item.setJobTitleName(item.getJobTitleEnglishName());
			}
		}

		ModelAndView mav = new ModelAndView("collpack/assess/portlet/assessmentUserRank/normalView");

		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("assessmentManagementUserPviList", assessmentManagementUserPviList);

		return mav;
	}
	
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("collpack/assess/portlet/assessmentUserRank/configView");

		mav.addObject("portletConfigId", portletConfigId);
		return mav;
	}
	
}
