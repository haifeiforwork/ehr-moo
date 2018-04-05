/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.support.cache.service.CacheService;

/**
 * 사용자 포틀릿 설정값 Controller
 *
 * @author 한승환
 * @version $Id: PortalPortletUserConfigController.java 19511 2012-06-26 09:36:03Z malboru80 $
 */
@Controller
@RequestMapping(value = "/portal/admin/screen/portlet")
public class PortalPortletUserConfigController extends BaseController {

	@Autowired
	PortalPortletUserConfigService portalPortletUserConfigService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 사용자 포틀릿 목록 갯수설정 셋팅
	 * 
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @param listSize 목록 갯수
	 */
	@RequestMapping(value = "/setPortletListSize.do")
	@ResponseStatus(HttpStatus.OK)
	public void setPortletListSize(@RequestParam String portletConfigId, @RequestParam int listSize, @RequestParam String portletId) {
		portalPortletUserConfigService.setListSize(portletConfigId, listSize);
		
		// 포틀릿 contents 캐시 Element 삭제
		cacheService.removeCacheElementPortletContent(portletId, portletConfigId);
	}	
	
	/**
	 * 사용자 포틀릿 설정 셋팅
	 * 
	 * @param portletConfigId 포틀릿 설정 아이디
	 * @param propertyName 목록 갯수
	 */
	@RequestMapping(value = "/setPortletUserConfig.do")
	@ResponseStatus(HttpStatus.OK)
	public void setPortletUserConfig(@RequestParam String portletConfigId, @RequestParam String propertyName , @RequestParam String propertyValue) {
		portalPortletUserConfigService.setPortletUserConfig(portletConfigId, propertyName, propertyValue);
	}	
}
