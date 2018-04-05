package com.lgcns.ikep4.portal.admin.screen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletConfigService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포틀릿 셋팅 Controller
 *
 * @author 임종상
 * @version $Id: PortalPortletConfigController.java 19462 2012-06-22 07:19:41Z malboru80 $
 */
 
@Controller
@RequestMapping(value = "/portal/admin/screen/portletConfig")
public class PortalPortletConfigController extends BaseController {
	
	@Autowired
	PortalPortletConfigService portalPortletConfigService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 포틀릿 config 등록
	 * @param portalPortletConfig 포틀릿 셋팅  모델
	 * @param systemCode 시스템 코드
	 * @return 포틀릿 config ID
	 */
	@RequestMapping(value = "/createPortletConfig.do")
	public @ResponseBody String createPortletConfig(@ModelAttribute PortalPortletConfig portalPortletConfig, @RequestParam String systemCode) {
		
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		portalPortletConfig.setRegisterId(user.getUserId());
		portalPortletConfig.setRegisterName(user.getUserName());
		portalPortletConfig.setUpdaterId(user.getUserId());
		portalPortletConfig.setUpdaterName(user.getUserName());
		portalPortletConfig.setViewMode("NORMAL");
		
		portalPortletConfigService.createPortletConfig(portalPortletConfig, systemCode, portal.getPortalId(), user.getLocaleCode());
		
		// 포탈 사용자 포틀릿 목록 캐시 Element 삭제
		cacheService.removeCacheElement("userPortlet");
		
		return portalPortletConfig.getPortletConfigId();
	}
	
	/**
	 * 포틀릿 config 수정
	 * @param portalPortletConfig 포틀릿 셋팅  모델
	 * @param systemCode 시스템 코드
	 * @return success 메세지
	 */
	@RequestMapping(value = "/updatePortletConfig.do")
	public @ResponseBody String updatePortletConfig(@ModelAttribute PortalPortletConfig portalPortletConfig, @RequestParam String systemCode) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		portalPortletConfig.setUpdaterId(user.getUserId());
		portalPortletConfig.setUpdaterName(user.getUserName());
		
		portalPortletConfigService.updatePortletConfig(portalPortletConfig, systemCode, user.getLocaleCode());
		
		// 포탈 사용자 포틀릿 캐시 Element 삭제
		cacheService.removeCacheElement("userPortlet");
		
		return "success";
	}
	
	/**
	 * 포틀릿 config 삭제
	 * @param portalPortletConfig 포틀릿 셋팅  모델
	 * @param systemCode 시스템 코드
	 * @return success 메세지
	 */
	@RequestMapping(value = "/removePortletConfig.do")
	public @ResponseBody String removePortletConfig(@ModelAttribute PortalPortletConfig portalPortletConfig, @RequestParam String systemCode) {
		
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		portalPortletConfigService.removePortletConfig(portalPortletConfig, systemCode, portal.getPortalId(), user);
		
		// 포탈 사용자 포틀릿 목록 캐시 Element 삭제
		cacheService.removeCacheElement("userPortlet");
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent(portalPortletConfig.getPortletId(), portalPortletConfig.getPortletConfigId());
		
		return "success";
	}
	
	/**
	 * 포틀릿 config viewMode 수정
	 * @param portalPortletConfig 포틀릿 셋팅  모델
	 * @return success 메세지
	 */
	@RequestMapping(value = "/updatePortletConfigViewMode.do")
	public @ResponseBody String updatePortletConfigViewMode(@ModelAttribute PortalPortletConfig portalPortletConfig) {

		portalPortletConfigService.updatePortletConfigViewMode(portalPortletConfig);
		
		// 포탈 사용자 포틀릿 목록 캐시 Element 삭제
		cacheService.removeCacheElement("userPortlet");
		
		return "success";
	}
}
