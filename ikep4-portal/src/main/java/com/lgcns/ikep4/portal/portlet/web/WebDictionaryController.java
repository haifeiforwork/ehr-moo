package com.lgcns.ikep4.portal.portlet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;

@Controller
@RequestMapping(value = "/portal/portlet/webDictionary")
public class WebDictionaryController extends BaseController{

	/**
	 * 사전 포틀릿 조회(normalView) 
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView() {
		return new ModelAndView("portal/portlet/webDictionary/normalView");
	}
	
	/**
	 * 사전 포틀릿 조회(maxView) 
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView() {
		return new ModelAndView("portal/portlet/webDictionary/maxView");
	}
}