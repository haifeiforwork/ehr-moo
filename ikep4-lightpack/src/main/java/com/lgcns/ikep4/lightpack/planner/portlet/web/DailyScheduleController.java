package com.lgcns.ikep4.lightpack.planner.portlet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;

@Controller
@RequestMapping(value = "/lightpack/planner/portlet/dailySchedule")
public class DailyScheduleController extends BaseController{

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("lightpack/planner/portlet/dailySchedule/normalView");
		mav.addObject("portletConfigId", portletConfigId);
		return mav;
	}
	
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("lightpack/planner/portlet/dailySchedule/maxView");
		mav.addObject("portletConfigId", portletConfigId);
		return mav;
	}
}
