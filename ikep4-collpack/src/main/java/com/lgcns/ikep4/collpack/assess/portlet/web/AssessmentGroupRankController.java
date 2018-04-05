/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.portlet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;

/**
 * TODO Javadoc주석작성
 *
 * @author 
 * @version $Id: AssessmentGroupRankController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/assess/portlet/assessmentGroupRank")
public class AssessmentGroupRankController extends BaseController{

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("collpack/assess/portlet/assessmentGroupRank/normalView");

		mav.addObject("portletConfigId", portletConfigId);
		return mav;
	}
	
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("collpack/assess/portlet/assessmentGroupRank/configView");

		mav.addObject("portletConfigId", portletConfigId);
		return mav;
	}
	
}
