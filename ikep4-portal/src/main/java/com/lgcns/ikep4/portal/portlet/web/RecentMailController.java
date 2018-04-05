package com.lgcns.ikep4.portal.portlet.web;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailReadService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;


@Controller
@RequestMapping(value = "/portal/portlet/recentMail")
public class RecentMailController extends BaseController {

	@Autowired
	private MailReadService mailReadService;

	@Autowired
	private PortalPortletUserConfigService portalPortletUserConfigService;

	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("portal/portlet/recentMail/normalView");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
			if (listSize < 1) {
				listSize = 5;
			}

			 List<Mail> mailList = mailReadService.getRecentMailInfo(user, listSize);

			mav.addObject("listSize", listSize);
			mav.addObject("mailList", mailList);
			mav.addObject("portletConfigId", portletConfigId);
			
            Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
			
            mav.addObject("mailServer", prop.getProperty("ikep4.support.mail.externalserver"));

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}

	@RequestMapping(value = "/configView.do")
	public ModelAndView configView(@RequestParam String portletConfigId, @RequestParam String portletId) {
		ModelAndView mav = new ModelAndView("portal/portlet/recentMail/configView");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
			if (listSize < 1) {
				listSize = 5;
			}

			List<Mail> mailList = mailReadService.getRecentMailInfo(user, listSize);

			mav.addObject("listSize", listSize);
			mav.addObject("mailList", mailList);
			mav.addObject("portletConfigId", portletConfigId);
			mav.addObject("portletId", portletId);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}

	@RequestMapping(value = "/maxView.do")
	public ModelAndView maxView(@RequestParam String portletConfigId) {
		ModelAndView mav = new ModelAndView("portal/portlet/recentMail/maxView");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			int listSize = portalPortletUserConfigService.readListSize(portletConfigId);
			if (listSize < 1) {
				listSize = 5;
			}

			List<Mail> mailList = mailReadService.getMailList("", user, listSize);

			mav.addObject("listSize", listSize);
			mav.addObject("mailList", mailList);
			mav.addObject("portletConfigId", portletConfigId);

		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
}