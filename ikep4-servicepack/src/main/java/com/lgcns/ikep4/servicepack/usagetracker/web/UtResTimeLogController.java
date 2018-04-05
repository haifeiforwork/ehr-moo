package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeConfig;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeLog;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeUrl;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeConfigService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeUrlService;

@Controller
@RequestMapping(value = "/servicepack/usagetracker/resTimeLog")
public class UtResTimeLogController extends BaseUsageTrackerController {
	
	@Autowired
	private UtResTimeLogService utResTimeLogService;
	
	@Autowired
	private UtResTimeUrlService utResTimeUrlService;
	
	@Autowired
	private UtResTimeConfigService utResTimeConfigService;
	
	/**
	 * 응답시간 로그 목록
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView getList(UtSearchCondition searchCondition) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		searchCondition.setPortalId(portalId);
		
		if (searchCondition.getSearchOption() < 1) { //초기로딩시
			searchCondition.setSearchOption(1); 
		} 
		
		SearchResult<UtResTimeLog> searchResult = utResTimeLogService.listBySearchCondition(searchCondition);
		
		List<UtResTimeUrl> resTimeUrlList = utResTimeUrlService.listResTimeUrl(portalId);
		
		UtResTimeConfig utResTimeConfig = utResTimeConfigService.read(portalId);
		
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/resTimeLog/list");
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("resTimeUrlList", resTimeUrlList);
		mav.addObject("utResTimeConfig", utResTimeConfig);
		
		return mav;
	}
	
	/**
	 * 설정 시간 수정
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/updateResTimeConfig")
	public ModelAndView updateResTimeConfig(UtResTimeConfig utResTimeConfig) {
		
		String portalId = (String) getRequestAttribute("ikep.portalId");
		utResTimeConfig.setPortalId(portalId);
		
		utResTimeConfigService.updateResTimeConfig(utResTimeConfig);
		
		return new ModelAndView("redirect:/servicepack/usagetracker/resTimeLog/list.do");
	}
}