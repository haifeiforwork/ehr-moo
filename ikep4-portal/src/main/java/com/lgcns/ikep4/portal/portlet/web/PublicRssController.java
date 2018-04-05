package com.lgcns.ikep4.portal.portlet.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.portlet.model.PublicRss;
import com.lgcns.ikep4.portal.portlet.service.PublicRssService;
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
@RequestMapping(value = "/portal/portlet/publicRss")
public class PublicRssController extends BaseController {
	
	private static final int DEFAULT_TAB_COUNT = 1;

	private static final int DEFAULT_LIST_COUNT = 5;

	@Autowired
	PublicRssService publicRssService;
	
	@Autowired
	ChannelService channelService;
	
	/**
	 * RSS 포틀릿 조회(normalView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/publicRss/normalView");

		List<PublicRss> publicRssList = publicRssService.getPublicRssList();
	
		try {

			List<Channel> channelList = new ArrayList<Channel>();
			Channel temp = new Channel();
			
			for(int i=0;i<publicRssList.size();i++){
				temp = null;
				
				temp = channelService.readChannelForPortlet(publicRssList.get(i).getRssUrl());
				temp.setChannelId(publicRssList.get(i).getPortletConfigId());
				channelList.add(temp);
			}
			
			PublicRss publicRss = new PublicRss();
			
			if(!publicRssList.isEmpty()){
				publicRss.setTabCount(publicRssList.get(0).getTabCount());
				publicRss.setListCount(publicRssList.get(0).getListCount());
			}else{
				publicRss.setTabCount(DEFAULT_TAB_COUNT);
				publicRss.setListCount(DEFAULT_LIST_COUNT);
			}
			
			mav.addObject("publicRss", publicRss);
			mav.addObject("channelList", channelList);
			mav.addObject("publicRssList", publicRssList);
			
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;
	}
	
	/**
	 * RSS 포틀릿 채널별 목록(normalView)
	 * @param portletConfigId 포틀릿 config ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/rssItemList.do")
	public ModelAndView rssItemList(@RequestParam String portletConfigId) {
		
		ModelAndView mav = new ModelAndView("portal/portlet/publicRss/rssItemList");
		
		PublicRss publicRss = publicRssService.readPublicRss(portletConfigId);

		Channel channel = null;
		
		try {
			if(publicRss != null) {
				channel = channelService.readChannelForPortlet(publicRss.getRssUrl());
			}

			mav.addObject("channel", channel);
			mav.addObject("publicRss", publicRss);

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
		
		ModelAndView mav = new ModelAndView("portal/portlet/publicRss/configView");
	
		PublicRss publicRss = new PublicRss();
		
		try {
			List<PublicRss> publicRssList = publicRssService.getPublicRssList();
			if(!publicRssList.isEmpty()){
				publicRss.setTabCount(publicRssList.get(0).getTabCount());
				publicRss.setListCount(publicRssList.get(0).getListCount());
			}else{
				publicRss.setTabCount(1);
				publicRss.setListCount(5);
			}
			mav.addObject("publicRss", publicRss);
			mav.addObject("publicRssList", publicRssList);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}
		
		return mav;

	}
	
	/**
	 * RSS 포틀릿 TabCount 등록(configView)
	 * @param portletRss RSS PORTLET 모델
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/changeTabCount.do")
	public @ResponseBody List<PublicRss> changeTabCount() {
		List<PublicRss> publicRssList = publicRssService.getPublicRssList();
		return publicRssList;
	}
	
	/**
	 * RSS 포틀릿 등록
	 * @param portletRss RSS PORTLET 모델
	 * @return success 메세지
	 */
	@RequestMapping(value = "/createPublicRss.do")
	public @ResponseBody String createPortletRss(@ModelAttribute PublicRss publicRss) {

		User user = (User) getRequestAttribute("ikep.user");
		
		publicRss.setRegisterId(user.getUserId());
		publicRss.setRegisterName(user.getUserName());
		publicRss.setUpdaterId(user.getUserId());
		publicRss.setUpdaterName(user.getUserName());
		
		publicRssService.createPublicRss(publicRss);
		
		return "success";
	}
}