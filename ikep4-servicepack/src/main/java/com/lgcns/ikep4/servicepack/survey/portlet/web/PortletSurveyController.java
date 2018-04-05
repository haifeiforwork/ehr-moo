/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.portlet.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.searchpreprocessor.util.PageCons;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.support.userconfig.service.UserConfigService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.servicepack.survey.model.Code;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.servicepack.survey.portlet.services.PortletSurveyService;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;
import com.lgcns.ikep4.servicepack.survey.web.BaseSurveyController;

/**
 * 포틀릿 공용 게시판 컨트롤 클래스.
 */
@Controller
@SessionAttributes("searchCondition")
@RequestMapping(value = "/servicepack/survey/portlet")
public class PortletSurveyController extends BaseSurveyController{
	
	@Autowired
	private PortletSurveyService portletSurveyService;
	
	@Autowired
	private UserConfigService userConfigService;	
	
	@Autowired
	private PortalPortletUserConfigService portalPortletUserConfigService;
	/** The Constant DEFAULT_PAGE_PER_RECORD. */

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

	/**
	 * 포틀릿 일반 화면 담당 메소드
	 *
	 * @return the model and view
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(SurveySearchCondition searchCondition, BindingResult result, String erroMessage,
			SessionStatus status, ModelMap model, HttpServletRequest request) {
			User user = (User) getRequestAttribute("ikep.user");

			SurveySearchCondition localSearchCondition = getSearchCondition(searchCondition, model, request);

			localSearchCondition.setSurveyStatus(SurveyStatus.ANSERING.getCode()); // 진행중인것만
																				// 조회
			localSearchCondition.setResponseUserId(user.getUserId()); // 참여자 아이디가 없는경우
			localSearchCondition.setPortalId( user.getPortalId() );

			SearchResult<Survey> searchResult = portletSurveyService.ingListBySearchCondition(localSearchCondition);

			ModelAndView mav = new ModelAndView("servicepack/survey/portlet/Survey/normalView");
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("erroMessage", erroMessage);
			
			Portal portal = (Portal)this.getRequestAttribute("ikep.portal");			
			UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, portal.getPortalId());
			mav.addObject("count", userConfig == null ? PAGE_PER_RECORD : userConfig.getPageCount());

			getCodeList(mav);
			return mav;		
	}

/**
 * 리스트 검색조건 유지
 * @param searchCondition
 * @param model
 * @param request
 * @return
 */
private SurveySearchCondition getSearchCondition(SurveySearchCondition searchCondition, ModelMap model,
		HttpServletRequest request) {
	if (model.containsKey("searchCondition") && !StringUtils.hasText(searchCondition.getRedirect())) {
		String url = request.getRequestURI();
		SurveySearchCondition sessionSearchCondition = (SurveySearchCondition) model.get("searchCondition");
		if (url.indexOf(sessionSearchCondition.getRedirect() + ".do") >= 0) {
			return sessionSearchCondition;
		}
	}
	
	return searchCondition;
}

public void getCodeList(ModelAndView mav) {
	@SuppressWarnings("unchecked")
	List<Code<Integer>> pageNumList = Arrays.asList(new Code<Integer>(PageCons.PAGE_PER_ROW_10, "10"), new Code<Integer>(PageCons.PAGE_PER_ROW_15, "15"),
			new Code<Integer>(PageCons.PAGE_PER_ROW_20, "20"), new Code<Integer>(PageCons.PAGE_PER_ROW_30, "30"), new Code<Integer>(PageCons.PAGE_PER_ROW_40, "40"),
			new Code<Integer>(PageCons.PAGE_PER_ROW_50, "50"));

	mav.addObject("codeList", pageNumList);

	List<Code<Integer>> contentsTypeList = new ArrayList<Code<Integer>>();
	contentsTypeList.add(new Code<Integer>(0, "ui.servicepack.survey.create.contentsType.text"));
	contentsTypeList.add(new Code<Integer>(1, "ui.servicepack.survey.create.contentsType.image"));
	mav.addObject("contentsTypeList", contentsTypeList);

	List<Code<Integer>> anonymousList = new ArrayList<Code<Integer>>();
	anonymousList.add(new Code<Integer>(0, "ui.servicepack.survey.create.anonymous.yes"));
	anonymousList.add(new Code<Integer>(1, "ui.servicepack.survey.create.anonymous.no"));
	mav.addObject("anonymousList", anonymousList);

	List<Code<Integer>> rejectButtonList = new ArrayList<Code<Integer>>();
	rejectButtonList.add(new Code<Integer>(0, "ui.servicepack.survey.create.rejectButton.display"));
	rejectButtonList.add(new Code<Integer>(1, "ui.servicepack.survey.create.rejectButton.none"));
	mav.addObject("rejectButtonList", rejectButtonList);

	List<Code<Integer>> resultOpenList = new ArrayList<Code<Integer>>();
	resultOpenList.add(new Code<Integer>(0, "ui.servicepack.survey.create.resultOpen.public"));
	resultOpenList.add(new Code<Integer>(1, "ui.servicepack.survey.create.resultOpen.private"));
	mav.addObject("resultOpenList", resultOpenList);

	List<Code<Integer>> surveyTargetList = new ArrayList<Code<Integer>>();
	surveyTargetList.add(new Code<Integer>(0, "ui.servicepack.survey.create.surveyTarget.all"));
	surveyTargetList.add(new Code<Integer>(1, "ui.servicepack.survey.create.surveyTarget.one"));
	mav.addObject("surveyTargetList", surveyTargetList);

	User user = (User) getRequestAttribute("ikep.user");
	mav.addObject("user", user);
	
}



	//설정화면
//@RequestMapping(value = "/configView.do", method=RequestMethod.GET)
@RequestMapping(value = "/configView.do")
public ModelAndView configView() {
	Portal portal = (Portal)this.getRequestAttribute("ikep.portal");

	User user = (User)this.getRequestAttribute("ikep.user");


	//사용자 설정 정보를 가져온다.
	UserConfig userConfig = this.userConfigService.readUserConfigByUserIdAndModuleId(user.getUserId(), USER_CONFIG_MODULE_ID, portal.getPortalId());

	ModelAndView mav =  new ModelAndView("servicepack/survey/portlet/Survey/configView");

	mav.addObject("count", userConfig == null ? PAGE_PER_RECORD : userConfig.getPageCount());

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
			userConfig.setPageCount((count != null && count > 0)?count:PAGE_PER_RECORD);
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


}