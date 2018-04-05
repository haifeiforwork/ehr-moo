package com.lgcns.ikep4.socialpack.microblogging.portlet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.WeatherInfo;
import com.lgcns.ikep4.socialpack.microblogging.base.Constant;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.socialpack.microblogging.service.FollowService;
import com.lgcns.ikep4.socialpack.microblogging.service.MblogService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;

@Controller
@RequestMapping(value = "/socialpack/microblogging/portlet/mySocialNetwork")
public class MySocialNetworkController extends BaseController{

	@Autowired
	private MblogService mblogService;

	@Autowired
	private FollowService followService;
	
	@Autowired
	private CacheService cacheService;
	
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		if (log.isDebugEnabled()) {
			//log.debug("portletConfigId:" + portletConfigId);
		}
		ModelAndView mav = new ModelAndView("socialpack/microblogging/portlet/mySocialNetwork/normalView");

		User user = (User) getRequestAttribute("ikep.user");
		
		//My Social Network 포틀릿 목록 캐시에서 조회
		List<User> followingList = (List<User>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(followingList == null) {
			MblogSearchCondition mblogSearchCondition = new MblogSearchCondition();
			mblogSearchCondition.setOwnerId(user.getUserId());
			mblogSearchCondition.setLoginId(user.getUserId());
			mblogSearchCondition.setStandardType("pre");
			mblogSearchCondition.setFetchSize(Constant.FETCH_SIZE_8);
			
			followingList = mblogService.followingList(mblogSearchCondition);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, followingList);
		}

		mav.addObject("listSize", Constant.LIST_SIZE_9);
		mav.addObject("followingList", followingList);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}

	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId) {
		if (log.isDebugEnabled()) {
			log.debug("portletConfigId:" + portletConfigId);
		}
		
		ModelAndView mav = new ModelAndView("socialpack/microblogging/portlet/mySocialNetwork/maxView");

		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}

	@RequestMapping(value = "/getFollowingList.do")
	public ModelAndView getFollowingList(MblogSearchCondition mblogSearchCondition) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setStandardType("pre");
		mblogSearchCondition.setFetchSize(Constant.FETCH_SIZE_8);
		
		if (log.isDebugEnabled()) {
			log.debug("getFollowingList =====================");
			log.debug("user.getUserId():" + user.getUserId());
			log.debug("mblogSearchCondition:" + mblogSearchCondition);
		}

		// 1. following List
		ModelAndView mav = new ModelAndView("socialpack/microblogging/portlet/mySocialNetwork/userList");
		List<User> list = mblogService.followingList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getFollowingList list:" + list);
			log.debug("getFollowingList list.size():" + list.size());
			log.debug("getFollowingList  mblogSearchCondition.getNowSize():" + mblogSearchCondition.getNowSize());
		}

		// 2. following 수
		int followingCount = followService.myFollowingCount(user.getUserId());
		if (log.isDebugEnabled()) {
			log.debug("getFollowingList followingCount:" + followingCount);
		}

		mav.addObject("followingCount", followingCount);
		mav.addObject("userList", list);
		
		return mav;
	}

	@RequestMapping(value = "/getFollowerList.do")
	public ModelAndView getFollowerList(MblogSearchCondition mblogSearchCondition) {
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		mblogSearchCondition.setLoginId(user.getUserId());
		mblogSearchCondition.setOwnerId(user.getUserId());
		mblogSearchCondition.setStandardType("pre");
		mblogSearchCondition.setFetchSize(Constant.FETCH_SIZE_8);
		
		if (log.isDebugEnabled()) {
			log.debug("getFollowerList =====================");
			log.debug("user.getUserId():" + user.getUserId());
			log.debug("mblogSearchCondition:" + mblogSearchCondition);
		}

		// 1. follower List
		ModelAndView mav = new ModelAndView("socialpack/microblogging/portlet/mySocialNetwork/userList");
		List<User> list = mblogService.followerList(mblogSearchCondition);
		if (log.isDebugEnabled()) {
			log.debug("getFollowerList list:" + list);
			log.debug("getFollowerList list.size():" + list.size());
			log.debug("getFollowingList  mblogSearchCondition.getNowSize():" + mblogSearchCondition.getNowSize());
		}

		// 2. follower 수
		int followerCount = followService.myFollowerCount(user.getUserId());
		if (log.isDebugEnabled()) {
			log.debug("getFollowerList followerCount:" + followerCount);
		}

		mav.addObject("followerCount", followerCount);
		mav.addObject("userList", list);
		
		return mav;
	}

}
