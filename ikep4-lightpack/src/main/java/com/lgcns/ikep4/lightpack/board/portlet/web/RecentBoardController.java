/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.portlet.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.portlet.service.BoardPortletService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;

/**
 * 포틀릿 최신 게시글 컨트롤 클래스
 */
@Controller
@RequestMapping(value = "/lightpack/board/portlet/recentBoard")
public class RecentBoardController extends BaseController{

	@Autowired
	private BoardPortletService boardPortletService;

	@Autowired
	private UserConfigService userConfigService;
	
	@Autowired
	private CacheService cacheService;

	private static final String USER_CONFIG_MODULE_ID = "portlet.bbs.recent";

	private static final Integer PAGE_PER_RECORD = 5;

	private UserConfig getUserConfig() {
		User user = (User)this.getRequestAttribute("ikep.user");

		Portal portal = (Portal)this.getRequestAttribute("ikep.portal");

		//사용자 설정 정보를 가져온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, portal.getPortalId());


		//사용자 설정 정보가  없으면 페이지 당 레코드 갯수를 디폴트 갯수로 표시하고 사용자 설정을 생성한다.
		if(userConfig == null) {
			userConfig = new UserConfig();

			userConfig.setPortalId(portal.getPortalId());
			userConfig.setUserId(user.getUserId());
			userConfig.setPageCount(PAGE_PER_RECORD);
			userConfig.setModuleId(USER_CONFIG_MODULE_ID);

			this.userConfigService.createUserConfig(userConfig);
		}

		return userConfig;
	}


	//기본화면
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		User user = (User)this.getRequestAttribute("ikep.user");

		//최근 게시글 목록 캐시에서 조회
		List<BoardItem> boardItemList = (List<BoardItem>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(boardItemList == null) {
			//페이지당 레코드 카운트
			Integer pagePerRecord = this.getUserConfig().getPageCount();
			
			boardItemList = this.boardPortletService.listRecentBoardItem(user.getUserId(), pagePerRecord);
			
			// 캐시에 저장
			cacheService.addCacheElementPortletContent(portletId, portletConfigId, boardItemList);
		}

		ModelAndView mav = new ModelAndView("lightpack/board/portlet/recentBoard/normalView");

		mav.addObject("boardItemList", boardItemList);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}

	//설정화면
	@RequestMapping(value = "/configView.do", method=RequestMethod.GET)
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		Portal portal = (Portal)this.getRequestAttribute("ikep.portal");

		User user = (User)this.getRequestAttribute("ikep.user");

		//사용자 설정 정보를 가져온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, portal.getPortalId());
		
		//최근 게시글 목록 캐시에서 조회
		List<BoardItem> boardItemList = (List<BoardItem>) cacheService.cacheCheckPortletContent(portletId, portletConfigId);
		
		if(boardItemList == null) {
			//페이지당 레코드 카운트
			Integer pagePerRecord = this.getUserConfig().getPageCount();
	
			boardItemList = this.boardPortletService.listRecentBoardItem(user.getUserId(), pagePerRecord);
		}

		ModelAndView mav = new ModelAndView("lightpack/board/portlet/recentBoard/configView");

		mav.addObject("count", userConfig == null ? PAGE_PER_RECORD : userConfig.getPageCount());
		mav.addObject("boardItemList", boardItemList);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
	@RequestMapping(value = "/updatePageCount.do")
	public @ResponseBody void config(@RequestParam("count") Integer count) {
		try {
			User user = (User)this.getRequestAttribute("ikep.user");

			Portal portal = (Portal)this.getRequestAttribute("ikep.portal");

			//사용자 설정 정보를 가져온다.
			UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, portal.getPortalId());


			//사용자 설정 정보가  없으면 페이지 당 레코드 갯수를 디폴트 갯수로 표시하고 사용자 설정을 생성한다.
			if(userConfig == null) {
				userConfig = new UserConfig();

				userConfig.setPortalId(portal.getPortalId());
				userConfig.setUserId(user.getUserId());
				userConfig.setPageCount(PAGE_PER_RECORD);
				userConfig.setModuleId(USER_CONFIG_MODULE_ID);

				this.userConfigService.createUserConfig(userConfig);

			} else {
				userConfig.setPageCount(count);
				this.userConfigService.updateUserConfig(userConfig);
			}
			
			// 포틀릿 contents 캐시 Element 삭제
			cacheService.removeCacheElementPortletContent("Cachename-recentBoard-portlet", "Cachemode-recentBoard-portlet", "Elementkey-recentBoard-portlet", user.getUserId());
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}

	}


	//최대화면
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		User user = (User)this.getRequestAttribute("ikep.user");

		//페이지당 레코드 카운트
		Integer pagePerRecord = this.getUserConfig().getPageCount();
		
		List<BoardItem> boardItemList = this.boardPortletService.listRecentBoardItem(user.getUserId(), pagePerRecord);

		ModelAndView mav = new ModelAndView("lightpack/board/portlet/recentBoard/maxView");

		mav.addObject("boardItemList", boardItemList);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
}
