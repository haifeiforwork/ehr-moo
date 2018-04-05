package com.lgcns.ikep4.lightpack.cafe.portlet.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;


@Controller
@RequestMapping(value = "/lightpack/cafe/portlet/myCafe")
public class MyCafePortletController extends BaseController {

	@Autowired
	private CafeService cafeService;

	@Autowired
	private PortalPortletUserConfigService portalPortletUserConfigService;
	
	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("lightpack/cafe/portlet/myCafe/normalView");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 5;
		}

		//MY CAFE 목록 캐시에서 조회
		List<Cafe> myCafeList = (List<Cafe>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(myCafeList == null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", portal.getPortalId());
			map.put("memberId", user.getUserId());

			myCafeList = cafeService.listMyCafePortlet(map);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, myCafeList);
		}
		
		myCafeList = cafeService.listMyCafeBoardItemPortlet(myCafeList);
		
		mav.addObject("listSize", listSize);
		mav.addObject("myCafeList", myCafeList);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}

	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("lightpack/cafe/portlet/myCafe/configView");

		return mav;
	}

	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("lightpack/cafe/portlet/myCafe/maxView");

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 5;
		}

		//MY CAFE 목록 캐시에서 조회
		List<Cafe> myCafeList = (List<Cafe>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(myCafeList == null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("portalId", portal.getPortalId());
			map.put("memberId", user.getUserId());
	
			myCafeList = cafeService.listMyCafePortlet(map);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, myCafeList);
		}
		
		myCafeList = cafeService.listMyCafeBoardItemPortlet(myCafeList);

		mav.addObject("listSize", listSize);
		mav.addObject("myCafeList", myCafeList);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
}