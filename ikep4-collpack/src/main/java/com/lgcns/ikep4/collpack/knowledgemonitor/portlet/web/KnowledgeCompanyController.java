package com.lgcns.ikep4.collpack.knowledgemonitor.portlet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService;
import com.lgcns.ikep4.collpack.knowledgemonitor.web.CustomController;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;

@Controller
@RequestMapping(value = "/collpack/knowledgemonitor/portlet/knowledgeCompany")
public class KnowledgeCompanyController extends CustomController{

	@Autowired
	private KnowledgeMonitorStatisticsService knowledgeMonitorStatisticsService;
	
	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {

		// 목록 캐시에서 조회
		List<KnowledgeMonitorAccumulationChart> knowledgeMonitorAccumulationChartList = (List<KnowledgeMonitorAccumulationChart>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(knowledgeMonitorAccumulationChartList == null) {
			// Session 정보
			User user = getSessionUser();
			String portalId = user.getPortalId();

			KnowledgeAccumulationSearchCondition searchCondition = new KnowledgeAccumulationSearchCondition();
			searchCondition.setPortalId(portalId);

			knowledgeMonitorAccumulationChartList = knowledgeMonitorStatisticsService.listChartBySearchConditionPortlet(searchCondition);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, knowledgeMonitorAccumulationChartList);
		}

		ModelAndView mav = new ModelAndView("collpack/knowledgemonitor/portlet/knowledgeCompany/normalView");

		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("knowledgeMonitorAccumulationChartList", knowledgeMonitorAccumulationChartList);

		return mav;
	}

	@RequestMapping(value = "/configView.do")
	public ModelAndView configlView(@RequestParam String portletConfigId) {

		ModelAndView mav = new ModelAndView("collpack/knowledgemonitor/portlet/knowledgeCompany/configView");

		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
}