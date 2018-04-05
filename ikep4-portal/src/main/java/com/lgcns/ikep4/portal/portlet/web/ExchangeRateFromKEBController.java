package com.lgcns.ikep4.portal.portlet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;

@Controller
@RequestMapping(value = "/portal/portlet/exchangeRateFromKEB")
public class ExchangeRateFromKEBController extends BaseController{

	/**
	 * 환율 포틀릿 조회(normalView) 
	 * 
	 * @return 포워딩 정보
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView() {
		return new ModelAndView("portal/portlet/exchangeRateFromKEB/normalView");
	}
}