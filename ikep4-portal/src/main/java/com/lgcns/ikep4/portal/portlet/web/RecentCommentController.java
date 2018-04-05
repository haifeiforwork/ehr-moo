package com.lgcns.ikep4.portal.portlet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;
import com.lgcns.ikep4.support.personal.service.PersonalService;
import com.lgcns.ikep4.support.user.member.model.User;


@Controller
@RequestMapping(value = "/portal/portlet/recentComment")
public class RecentCommentController extends BaseController {

	@Autowired
	private PersonalService personalService;

	@Autowired
	private PortalPortletUserConfigService portalPortletUserConfigService;

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("portal/portlet/recentComment/normalView");

		User user = (User) getRequestAttribute("ikep.user");

		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 5;
		}

		PersonalSearchCondition searchCondition = new PersonalSearchCondition();
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setRegisterId(user.getUserId());
		searchCondition.setPagePerRecord(listSize);
		searchCondition.setCommentType("feedback");

		SearchResult<Personal> searchResult = personalService.getAllForComment(searchCondition);

		mav.addObject("listSize", listSize);
		mav.addObject("searchResult", searchResult);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}

	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("portal/portlet/recentComment/configView");

		User user = (User) getRequestAttribute("ikep.user");

		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 5;
		}

		PersonalSearchCondition searchCondition = new PersonalSearchCondition();
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setRegisterId(user.getUserId());
		searchCondition.setPagePerRecord(listSize);
		searchCondition.setCommentType("feedback");

		SearchResult<Personal> searchResult = personalService.getAllForComment(searchCondition);

		mav.addObject("listSize", listSize);
		mav.addObject("searchResult", searchResult);
		mav.addObject("portletConfigId", portletConfigId);
		mav.addObject("portletId", portletId);

		return mav;
	}

	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("portal/portlet/recentComment/maxView");

		User user = (User) getRequestAttribute("ikep.user");

		int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
		if (listSize < 1) {
			listSize = 5;
		}

		PersonalSearchCondition searchCondition = new PersonalSearchCondition();
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setRegisterId(user.getUserId());
		searchCondition.setPagePerRecord(listSize);
		searchCondition.setCommentType("feedback");

		SearchResult<Personal> searchResult = personalService.getAllForComment(searchCondition);

		mav.addObject("listSize", listSize);
		mav.addObject("searchResult", searchResult);
		mav.addObject("portletConfigId", portletConfigId);

		return mav;
	}
}