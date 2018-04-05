package com.lgcns.ikep4.socialpack.socialanalyzer.portlet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Sociality;
import com.lgcns.ikep4.socialpack.socialanalyzer.search.SocialAnalyzerSearchCondition;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityService;
import com.lgcns.ikep4.support.cache.service.CacheService;

@Controller
@RequestMapping(value = "/socialpack/socialanalyzer/portlet/socialRanking")
public class SocialRankingController  extends BaseController {
	@Autowired
	PortalPortletUserConfigService portalPortletUserConfigService;

	@Autowired
	private SocialityService socialityService;
	
	@Autowired
	private CacheService cacheService;

	/**
	 * 포틀릿 일반 형식
	 * @param portletConfigId
	 * @return
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/portlet/socialRanking/normalView");
		
		// 목록 캐시에서 조회
		SearchResult<Sociality>  searchResult = (SearchResult<Sociality> ) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(searchResult == null) {
			//조회
			searchResult = socialityService.listSociality(new SocialAnalyzerSearchCondition());
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, searchResult);
		}
		
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("searchResult", searchResult);
		
		return mav;
	}
}
