package com.lgcns.ikep4.portal.portlet.web;


import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;

@Controller
@RequestMapping(value = "/portal/portlet/WebKorWeather")
public class WebKorWeatherController extends BaseController{

	/**
	 * 날씨 포틀릿 조회(normalView) 
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView() {
		
		ModelAndView mav = new ModelAndView("portal/portlet/korWeather/normalView");
		
		Date today = new Date();	
		Calendar cur = Calendar.getInstance();		
		cur.setTime(today);	    
		mav.addObject("APM", cur.get(Calendar.AM_PM));
		return mav; 
	}
}