package com.lgcns.ikep4.collpack.knowledgemonitor.portlet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;

@Controller
@RequestMapping(value = "/collpack/knowledgemonitor/portlet/knowledgeDivision")
public class KnowledgeDivisionController extends BaseController{

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("collpack/knowledgemonitor/portlet/knowledgeDivision/normalView");
		
		mav.addObject("portletConfigId", portletConfigId);
		return mav;
	}
	
	@RequestMapping(value = "/configView.do")
	public ModelAndView configlView(@RequestParam String portletConfigId) {

		ModelAndView mav = new ModelAndView("collpack/knowledgemonitor/portlet/knowledgeDivision/normalView");

		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
}