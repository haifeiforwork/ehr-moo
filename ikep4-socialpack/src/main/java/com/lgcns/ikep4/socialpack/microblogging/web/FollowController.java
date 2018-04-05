/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.microblogging.model.Follow;
import com.lgcns.ikep4.socialpack.microblogging.service.FollowService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * Follow 관리
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: FollowController.java 19512 2012-06-26 09:36:14Z malboru80 $
 */
@Controller
@RequestMapping(value = "/socialpack/microblogging/follow")
public class FollowController extends BaseController {

	@Autowired
	private FollowService followService;

	@Autowired
	private CacheService cacheService;
	
	/**
	 * Follow 등록한다.
	 *
	 * @param follow Follow 정보
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return 결과
	 */
	@RequestMapping(value = "/createFollow")
	public @ResponseBody String onSubmit(@Valid Follow follow, BindingResult result, SessionStatus status) {
		
		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}
		
		follow.setUserId(user.getUserId());

		if (log.isDebugEnabled()) {
			log.debug("follow:"+follow);
		}

		if(!followService.exists(follow)){
			followService.create(follow);
			
			//My Social Network 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-mySocialNetwork-portlet", "Cachemode-mySocialNetwork-portlet", "Elementkey-mySocialNetwork-portlet", follow.getUserId());
		}

		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		
		return "ok";
	}

	/**
	 * Follow 삭제한다.
	 *
	 * @param follow Follow 정보
	 * @return 결과
	 */
	@RequestMapping(value = "/removeFollow")
	public @ResponseBody String remove(Follow follow) {

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}
		
		follow.setUserId(user.getUserId());

		followService.delete(follow);
		
		//My Social Network 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent("Cachename-mySocialNetwork-portlet", "Cachemode-mySocialNetwork-portlet", "Elementkey-mySocialNetwork-portlet", follow.getUserId());

		return "ok";
	}

	/**
	 * 내가 상대방을 following하고 있는 여부를 리턴한다.
	 *
	 * @param followingUserId 상대방 userId
	 * @return ModelAndView
	 */
	@RequestMapping("/isExists")
	public @ResponseBody String isExists(String followingUserId) {

		//세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
			log.debug("ownerId:"+followingUserId);
		}
		
		Follow follow = new Follow();
		follow.setFollowingUserId(followingUserId);
		// 로그인한 사용자
		follow.setUserId(user.getUserId());
		
		String result = "true";
		if(!followService.exists(follow)){
			result = "false";
		}
		return result;
	}

}
