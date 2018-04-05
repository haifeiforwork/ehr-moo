package com.lgcns.ikep4.collpack.knowledgemonitor.portlet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPoint;
import com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model.KnowledgeMonitorPortletSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService;
import com.lgcns.ikep4.collpack.knowledgemonitor.web.CustomController;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;

@Controller
@RequestMapping(value = "/collpack/knowledgemonitor/portlet/knowledgeRank")
public class KnowledgeRankController extends CustomController{

	@Autowired
	private KnowledgeMonitorCviPointService knowledgeMonitorCviPointService;

	@Autowired
	private CacheService cacheService;
	
	/**
	 * 조회건수
	 */
	private static final int MAX_RECORD_COUNT = 10;

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		
		// 목록 캐시에서 조회
		List<KnowledgeMonitorCviPoint> knowledgeMonitorCviPointList = (List<KnowledgeMonitorCviPoint>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(knowledgeMonitorCviPointList == null) {
			// Session 정보
			User user = getSessionUser();
			String portalId = user.getPortalId();
	
			// 상위 10건 조회
			KnowledgeMonitorPortletSearchCondition searchCondition = new KnowledgeMonitorPortletSearchCondition();
			searchCondition.setPortalId(portalId);
			searchCondition.setRecordCount(MAX_RECORD_COUNT);
			knowledgeMonitorCviPointList = knowledgeMonitorCviPointService.listByPortalIdPortelet(searchCondition);
	
			// URL 설정
			for (KnowledgeMonitorCviPoint item : knowledgeMonitorCviPointList) {
				item.setItemUrl(getModuleURL(item.getModuleCode()) + item.getItemId());
			}
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, knowledgeMonitorCviPointList);
		}

		// Locale 설정
		if (!isSameLocale()) {
			for (KnowledgeMonitorCviPoint item : knowledgeMonitorCviPointList) {
				item.setUserName(item.getUserEnglishName());
			}
		}

		ModelAndView mav = new ModelAndView("collpack/knowledgemonitor/portlet/knowledgeRank/normalView");
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("knowledgeMonitorCviPointList", knowledgeMonitorCviPointList);

		return mav;
	}
	
	@RequestMapping(value = "/configView.do")
	public ModelAndView configlView(@RequestParam String portletConfigId) {

		ModelAndView mav = new ModelAndView("collpack/knowledgemonitor/portlet/knowledgeRank/normalView");

		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
}