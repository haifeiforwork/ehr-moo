package com.lgcns.ikep4.portal.portlet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.PortletRss;
import com.lgcns.ikep4.portal.portlet.service.PortletRssService;
import com.lgcns.ikep4.support.rss.model.Channel;
import com.lgcns.ikep4.support.rss.service.ChannelService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * RSS 포틀릿 Controller
 *
 * @author 임종상
 * @version $Id: PortletRssController.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/portlet/rss")
public class PortletRssController extends BaseController {
	
	@Autowired
	PortletRssService portletRssService;
	
	@Autowired
	ChannelService channelService;
	
	/**
	 * RSS 포틀릿 조회(normalView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/rss/normalView");
		
		PortletRss portletRss = portletRssService.readPortletRss(portletConfigId);
		Channel channel = null;
		
		try {
			if(portletRss != null) {
				channel = channelService.readChannelForPortlet(portletRss.getRssUrl());
			}
			
			mav.addObject("channel", channel);
			mav.addObject("portletRss", portletRss);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	/**
	 * RSS 포틀릿 조회(maxView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("portal/portlet/rss/maxView");
		
		PortletRss portletRss = portletRssService.readPortletRss(portletConfigId);
		Channel channel = null;
		
		try {
			if(portletRss != null) {
				channel = channelService.readChannelForPortlet(portletRss.getRssUrl());
			}
			
			mav.addObject("channel", channel);
			mav.addObject("portletRss", portletRss);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	/**
	 * RSS 포틀릿 조회(configView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/rss/configView");
		
		PortletRss portletRss = portletRssService.readPortletRss(portletConfigId);
		
		if(portletRss == null) {
			portletRss = new PortletRss();
			portletRss.setPortletConfigId(portletConfigId);
		}
		
		mav.addObject("portletRss", portletRss);
		return mav;
	}
	
	/**
	 * RSS 포틀릿 등록
	 * @param portletRss RSS PORTLET 모델
	 * @return success 메세지
	 */
	@RequestMapping(value = "/createPortletRss.do")
	public @ResponseBody String createPortletRss(@ModelAttribute PortletRss portletRss) {

		User user = (User) getRequestAttribute("ikep.user");
		
		portletRss.setRegisterId(user.getUserId());
		portletRss.setRegisterName(user.getUserName());
		portletRss.setUpdaterId(user.getUserId());
		portletRss.setUpdaterName(user.getUserName());
		
		portletRssService.createPortletRss(portletRss);
		
		return "success";
	}
}