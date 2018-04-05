/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.portlet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprListService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;

/**
 * 포틀릿 공용 게시판 컨트롤 클래스.
 */
@Controller
@RequestMapping(value = "/approval/work/portlet/exam")
public class ApprPortletExamController extends BaseController{

	@Autowired
	private ApprListService apprListService;

	@Autowired
	private UserConfigService userConfigService;

	private static final String USER_CONFIG_MODULE_ID = "portlet.approval.exam";

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
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("approval/work/portlet/exam/normalView");
		
		//페이지당 레코드 카운트
		Integer pagePerRecord = this.getUserConfig().getPageCount();
		
		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();
		
		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();
		
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType("listApprRequestExam");
		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setSortColumn("examReqDate");
		apprListSearchCondition.setSortType("DESC");
		apprListSearchCondition.setPagePerRecord(pagePerRecord);
		
		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionExam(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}


	//설정화면
	@RequestMapping(value = "/configView.do", method=RequestMethod.GET)
	public ModelAndView configView(@RequestParam String portletConfigId) {
		Portal portal = (Portal)this.getRequestAttribute("ikep.portal");

		User user = (User)this.getRequestAttribute("ikep.user");

		//사용자 설정 정보를 가져온다.
		UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, portal.getPortalId());
		//페이지당 레코드 카운트
		Integer pagePerRecord = this.getUserConfig().getPageCount();
		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();
		
		apprListSearchCondition.setUserId(user.getUserId());
		apprListSearchCondition.setListType("listApprRequestExam");
		apprListSearchCondition.setPortalId(user.getPortalId());
		apprListSearchCondition.setSortColumn("examReqDate");
		apprListSearchCondition.setSortType("DESC");
		apprListSearchCondition.setPagePerRecord(pagePerRecord);
		
		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionExam(apprListSearchCondition);

		ModelAndView mav = new ModelAndView("approval/work/portlet/exam/configView");

		mav.addObject("count", userConfig == null ? PAGE_PER_RECORD : userConfig.getPageCount());
		mav.addObject("searchResult", searchResult);
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
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}

	}


	//최대화면
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId) {
		User user = (User)this.getRequestAttribute("ikep.user");

		//페이지당 레코드 카운트
		Integer pagePerRecord = this.getUserConfig().getPageCount();

		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();
		
		apprListSearchCondition.setUserId(user.getUserId());
		apprListSearchCondition.setListType("listApprRequestExam");
		apprListSearchCondition.setPortalId(user.getPortalId());
		apprListSearchCondition.setSortColumn("examReqDate");
		apprListSearchCondition.setSortType("DESC");
		apprListSearchCondition.setPagePerRecord(pagePerRecord);
		
		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionExam(apprListSearchCondition);

		ModelAndView mav = new ModelAndView("approval/work/portlet/exam/maxView");

		mav.addObject("searchResult", searchResult);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
	
}