package com.lgcns.ikep4.portal.portlet.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.portal.portlet.service.RssNewsService;
import com.lgcns.ikep4.support.rss.model.ChannelCategory;
import com.lgcns.ikep4.support.rss.model.ChannelSearchCondition;
import com.lgcns.ikep4.support.rss.service.ChannelCategoryService;
import com.lgcns.ikep4.support.rss.service.ChannelItemService;
import com.lgcns.ikep4.support.user.member.model.User;



@Controller
@RequestMapping(value = "/portal/portlet/rssNews")
public class RssNewsController extends BaseController {

	@Autowired
	private ChannelItemService channelItemService;
	
	@Autowired
	private ChannelCategoryService channelCategoryService;
	
	@Autowired
	RssNewsService rssNewsService;

	@Autowired
	private PortalPortletUserConfigService portalPortletUserConfigService;

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {

		ModelAndView mav = new ModelAndView("portal/portlet/rssNews/normalView");

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();

		User user = (User) getRequestAttribute("ikep.user");

		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 5;
		}

		searchCondition.setOwnerId(user.getUserId());
		searchCondition.setSortColumn("ITEM_PUBLISH_DATE");
		searchCondition.setSortType("DESC");
				
		SearchResult<ChannelCategory> searchResult = channelCategoryService.getAllforRSSnewsPortlet(searchCondition,listSize);
		
		mav.addObject("listSize", listSize);
		mav.addObject("searchResult", searchResult);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
	
	@RequestMapping(value = "/configView.do")
	public ModelAndView configlView(@RequestParam String portletConfigId, @RequestParam String portletId) {

		ModelAndView mav = new ModelAndView("portal/portlet/rssNews/configView");

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();

		User user = (User) getRequestAttribute("ikep.user");

		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 3;
		}

		searchCondition.setPagePerRecord(listSize);
		searchCondition.setOwnerId(user.getUserId());
		searchCondition.setSortColumn("ITEM_PUBLISH_DATE");
		searchCondition.setSortType("DESC");
	
		List<ChannelCategory> categoryList = channelCategoryService.getlistforRSSnewsPortletConfig(user.getUserId());

		mav.addObject("listSize", listSize);
		//mav.addObject("searchResult", searchResult);
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("categoryList", categoryList);
		mav.addObject("portletId", portletId);

		return mav;
	}
	
	//설정화면
	@RequestMapping(value = "/updatePortlet.do")
	public @ResponseBody void updatePortlet(@RequestParam("categoryId") String[] categoryIdList, String portletConfigId) {
		try {
			User user = (User)getRequestAttribute("ikep.user");
			rssNewsService.deleteSelectedFolder(portletConfigId);
			rssNewsService.saveSelectedFolder(Arrays.asList(categoryIdList), user.getUserId(),portletConfigId);
		} catch(Exception exception) {
			throw new IKEP4AjaxException("code", exception);
		}
	}

	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId) {

		ModelAndView mav = new ModelAndView("portal/portlet/rssNews/normalView");

		ChannelSearchCondition searchCondition = new ChannelSearchCondition();

		User user = (User) getRequestAttribute("ikep.user");

		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 5;
		}

		searchCondition.setOwnerId(user.getUserId());
		searchCondition.setSortColumn("ITEM_PUBLISH_DATE");
		searchCondition.setSortType("DESC");
				
		SearchResult<ChannelCategory> searchResult = channelCategoryService.getAllforRSSnews(searchCondition,listSize);
		
		mav.addObject("listSize", listSize);
		mav.addObject("searchResult", searchResult);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
}