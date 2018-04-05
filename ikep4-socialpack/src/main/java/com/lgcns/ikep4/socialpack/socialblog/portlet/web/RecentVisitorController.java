package com.lgcns.ikep4.socialpack.socialblog.portlet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.portal.portlet.model.WeatherInfo;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogService;
import com.lgcns.ikep4.socialpack.socialblog.service.SocialBlogVisitService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 최근 블로그 방문한 방문자 관련 포틀릿
 *
 * @author 이형운
 * @version $Id: RecentVisitorController.java 19512 2012-06-26 09:36:14Z malboru80 $
 */
@Controller
@RequestMapping(value = "/socialpack/socialblog/portlet/recentVisitor")
public class RecentVisitorController extends BaseController{

	/**
	 * 초기 블로그 방문자 포틀릿 리스트 사이즈
	 */
	public static final Integer DEFAULT_LIST_SIZE			= 5;
	
	/**
	 * 초기 블로그 방문자 포틀릿 페이지 Index
	 */
	public static final Integer DEFAULT_PAGE_INDEX			= 1;
	
	/**
	 * 블로그 방문자 정보 컨트롤용 Service
	 */
	@Autowired
	SocialBlogVisitService socialBlogVisitService;
	
	/**
	 * 포틀릿 사용자별 Config 정보 컨트롤용 Service
	 */
	@Autowired
	private PortalPortletUserConfigService portalPortletUserConfigService;
	
	/**
	 * 블로그 기본 정보 컨트롤용 Service 
	 */
	@Autowired
	private SocialBlogService socialBlogService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 블로그 방문자 정보 조회 포틀릿 일반 조회
	 * @param portletConfigId 포틀릿 Config Id
	 * @return 일반 포틀릿 View
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView( @RequestParam String portletConfigId, @RequestParam String portletId ) {
		ModelAndView mav = new ModelAndView("socialpack/socialblog/portlet/recentVisitor/normalView");
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		SocialBlog socialBlog = getBlogInfo(sessionuser.getUserId());
		
		SocialBlogVisit socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		int totalVisitCount = socialBlogVisitService.selectAllCountByblogId(socialBlogVisit);
		mav.addObject("totalVisitCount", totalVisitCount);
		
		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if(listSize < DEFAULT_PAGE_INDEX) {
			listSize = DEFAULT_LIST_SIZE;
		}
		
		//최근 블로그 방문자 포틀릿 목록 캐시에서 조회
		List<SocialBlogVisit> resultSocialBlogVisit = (List<SocialBlogVisit>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(resultSocialBlogVisit == null) {
			socialBlogVisit.setRecentVisitCnt(listSize);
			socialBlogVisit.setSessUserLocale(sessionuser.getLocaleCode());
			resultSocialBlogVisit = socialBlogVisitService.selectAllByblogId(socialBlogVisit);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, resultSocialBlogVisit);
		}
		
		mav.addObject("resultSocialBlogVisit", resultSocialBlogVisit);
		mav.addObject("size", resultSocialBlogVisit.size());
		mav.addObject("listSize", listSize);
		mav.addObject("portletConfigId", portletConfigId);
		
		return mav;

	}
	
	/**
	 * 블로그 방문자 정보 조회 포틀릿 Config 페이지 조회
	 * @param portletConfigId 포틀릿 Config Id
	 * @return Config 포틀릿 View
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView( @RequestParam String portletConfigId, @RequestParam String portletId ) {
		ModelAndView mav = new ModelAndView("socialpack/socialblog/portlet/recentVisitor/configView");
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		SocialBlog socialBlog = getBlogInfo(sessionuser.getUserId());
		
		SocialBlogVisit socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		int totalVisitCount = socialBlogVisitService.selectAllCountByblogId(socialBlogVisit);
		mav.addObject("totalVisitCount", totalVisitCount);
		
		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if(listSize < DEFAULT_PAGE_INDEX) {
			listSize = DEFAULT_LIST_SIZE;
		}
		
		//최근 블로그 방문자 포틀릿 목록 캐시에서 조회
		List<SocialBlogVisit> resultSocialBlogVisit = (List<SocialBlogVisit>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(resultSocialBlogVisit == null) {
			socialBlogVisit.setRecentVisitCnt(listSize);
			socialBlogVisit.setSessUserLocale(sessionuser.getLocaleCode());
			resultSocialBlogVisit = socialBlogVisitService.selectAllByblogId(socialBlogVisit);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, resultSocialBlogVisit);
		}
		
		mav.addObject("resultSocialBlogVisit", resultSocialBlogVisit);
		mav.addObject("size", resultSocialBlogVisit.size());
		mav.addObject("listSize", listSize);
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);
		
		return mav;
	}
	
	
	/**
	 * 해당 사용자의 블로그 기본 정보를 조회 하기 위한 메서드
	 * @param blogOwnerId 대상 블로거 ID
	 * @return 블로그 기본 정보
	 */
	public SocialBlog getBlogInfo(String blogOwnerId) {

		User sessionuser = (User) getRequestAttribute("ikep.user");
		
		// 해당 블로그에 정보를 받아 온다.
		SocialBlog searchSocialBlog = new SocialBlog();
		searchSocialBlog.setOwnerId(blogOwnerId);
		
		return socialBlogService.select(searchSocialBlog,sessionuser.getLocaleCode());
	}
}