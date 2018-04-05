/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.guestbook.portlet.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;
import com.lgcns.ikep4.support.guestbook.service.GuestbookService;
import com.lgcns.ikep4.support.profile.model.ProfileVisit;
import com.lgcns.ikep4.support.profile.service.ProfileVisitService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 방명록 최근 작성 목록 포틀릿으로 가져 오기
 *
 * @author 이형운
 * @version $Id: RecentGuestBookController.java 19513 2012-06-26 09:36:28Z malboru80 $
 */
@Controller
@RequestMapping(value = "/support/guestbook/portlet/recentGuestbook")
public class RecentGuestBookController extends BaseController {
	
	/**
	 * 포틀릿 내부에서 사용할 디폴트 조회 리스트 사이즈
	 */
	public static final Integer DEFAULT_LIST_SIZE			= 5;
	
	/**
	 * 포틀릿 내부에서 사용할 디폴트 Page Index
	 */
	public static final Integer DEFAULT_PAGE_INDEX			= 1;
	
	/**
	 * 방명록 조회용 Service
	 */
	@Autowired
	private GuestbookService guestbookService;
	
	/**
	 * 방명록 사용할 프로파일 Service 
	 */
	@Autowired
	ProfileVisitService profileVisitService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 방명록 포틀릿에서 Normal view 페이지를 반환한다.
	 * @param portletConfigId 포틀릿 Config Id
	 * @return Normal view 페이지
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView( @RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("support/guestbook/portlet/recentGuestbook/normalView");
		User sessionuser = (User) getRequestAttribute("ikep.user");
		
		Map<String, Object> portletConfigMap = new HashMap<String, Object>();
		portletConfigMap.put("portletConfigId", portletConfigId);
		portletConfigMap.put("propertyName", "LIST");
		
		int listSize = Integer.parseInt(guestbookService.readPortletPropertyValue(portletConfigMap)== null?"0":guestbookService.readPortletPropertyValue(portletConfigMap));
		if(listSize < DEFAULT_PAGE_INDEX) {
			listSize = DEFAULT_LIST_SIZE;
		}
		
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setTargetUserId(sessionuser.getUserId());
		guestbookSearch.setPagePerRecord(listSize);
		guestbookSearch.setSessUserLocale(sessionuser.getLocaleCode());
		
		//최근 방명록 목록 캐시에서 조회
		SearchResult<Guestbook> searchResult = (SearchResult<Guestbook>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
        if(searchResult == null ) {
        	searchResult = guestbookService.listGuestbook(guestbookSearch);
        	
        	// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, searchResult);
        }

		mav.addObject("searchResult", searchResult);
		mav.addObject("size", searchResult.getRecordCount());
		mav.addObject("guestbookSearch", guestbookSearch);
		mav.addObject("listSize", listSize);
		mav.addObject("portletConfigId", portletConfigId);
		
		return mav;
	}

	/**
	 * 방명록 포틀릿에서 Max view 페이지를 반환한다.
	 * @param portletConfigId 포틀릿 Config Id
	 * @return Max view 페이지
	 */
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView( @RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("support/guestbook/portlet/recentGuestbook/maxView");

		Map<String, Object> portletConfigMap = new HashMap<String, Object>();
		portletConfigMap.put("portletConfigId", portletConfigId);
		portletConfigMap.put("propertyName", "LIST");
		
		int listSize = Integer.parseInt(guestbookService.readPortletPropertyValue(portletConfigMap)== null?"0":guestbookService.readPortletPropertyValue(portletConfigMap));
		if(listSize < DEFAULT_PAGE_INDEX) {
			listSize = DEFAULT_LIST_SIZE;
		}
		
		mav.addObject("listSize", listSize);
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);
		
		return mav;
	}
	
	/**
	 * 방명록 포틀릿에서 실제 페이징 되어 보이는 방명록 글 목록 페이지를 반환한다.
	 * @param portletConfigId 포틀릿 Config Id
	 * @param paramPageIndex 페이징 인덱스 값
	 * @return 실제 페이징 되어 보이는 방명록 글 목록
	 */
	@RequestMapping(value = "/maxGuestBookListView.do")
	public ModelAndView maxGuestBookListView( @RequestParam String portletConfigId, @RequestParam String portletId
											, @RequestParam(value = "pageIndex", required = false, defaultValue="1") String paramPageIndex ) {
		ModelAndView mav = new ModelAndView("support/guestbook/portlet/recentGuestbook/maxGuestBookListView");
		User sessionuser = (User) getRequestAttribute("ikep.user");
		
		Map<String, Object> portletConfigMap = new HashMap<String, Object>();
		portletConfigMap.put("portletConfigId", portletConfigId);
		portletConfigMap.put("propertyName", "LIST");
		
		int listSize = Integer.parseInt(guestbookService.readPortletPropertyValue(portletConfigMap)== null?"0":guestbookService.readPortletPropertyValue(portletConfigMap));
		if(listSize < DEFAULT_PAGE_INDEX) {
			listSize = DEFAULT_LIST_SIZE;
		}
		
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setTargetUserId(sessionuser.getUserId());
		guestbookSearch.setPagePerRecord(listSize);
		guestbookSearch.setSessUserLocale(sessionuser.getLocaleCode());
		String pageIndex = paramPageIndex;
		if( StringUtil.isEmpty(pageIndex) ){
			pageIndex = String.valueOf(DEFAULT_PAGE_INDEX);
		}
		guestbookSearch.setPageIndex(Integer.parseInt(pageIndex));
		
		//최근 방명록 목록 캐시에서 조회
		SearchResult<Guestbook> searchResult = (SearchResult<Guestbook>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
        if(searchResult == null ) {
        	searchResult = guestbookService.listGuestbook(guestbookSearch);
        	
        	// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, searchResult);
        }
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("size", searchResult.getRecordCount());
		mav.addObject("guestbookSearch", guestbookSearch);
		mav.addObject("listSize", listSize);
		mav.addObject("pageIndex", pageIndex);
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);
		mav.addObject("targetUserId", sessionuser.getUserId());
		
		
		return mav;
	}
	
	/**
	 * 방명록 포틀릿에서 Config view 페이지를 반환한다.
	 * @param portletConfigId 포틀릿 Config Id
	 * @return Config view 페이지
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView( @RequestParam String portletConfigId, @RequestParam String portletId  ) {
		ModelAndView mav = new ModelAndView("support/guestbook/portlet/recentGuestbook/configView");
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		
		ProfileVisit profileVisit = new ProfileVisit();
		profileVisit.setOwnerId(sessionuser.getUserId());
		int totalVisitCount = profileVisitService.selectAllCountByOwnerId(profileVisit);
		mav.addObject("totalVisitCount", totalVisitCount);
		
		Map<String, Object> portletConfigMap = new HashMap<String, Object>();
		portletConfigMap.put("portletConfigId", portletConfigId);
		portletConfigMap.put("propertyName", "LIST");
		
		int listSize = Integer.parseInt(guestbookService.readPortletPropertyValue(portletConfigMap)== null?"0":guestbookService.readPortletPropertyValue(portletConfigMap));
		if(listSize < DEFAULT_PAGE_INDEX) {
			listSize = DEFAULT_LIST_SIZE;
		}
		
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setTargetUserId(sessionuser.getUserId());
		guestbookSearch.setPagePerRecord(listSize);
		guestbookSearch.setSessUserLocale(sessionuser.getLocaleCode());
		
		//최근 방명록 목록 캐시에서 조회
		SearchResult<Guestbook> searchResult = (SearchResult<Guestbook>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
        if(searchResult == null ) {
        	searchResult = guestbookService.listGuestbook(guestbookSearch);
        	
        	// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, searchResult);
        }
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("size", searchResult.getRecordCount());
		mav.addObject("guestbookSearch", guestbookSearch);
		mav.addObject("listSize", listSize);
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);
		return mav;
	}
	
	
}
