/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;

/**
 * TODO Javadoc주석작성
 *
 * @author 한승환
 * @version $Id: PortalPortletFixture.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalPortletFixture {
	public final static PortalPortlet fixturePortalPortlet(String portletId)
	{
		PortalPortlet portalPortlet = new PortalPortlet();
		portalPortlet.setPortletId(portletId);
		portalPortlet.setPortletName("JUnitTest");
		portalPortlet.setPortletCategoryId("100000000336");
		portalPortlet.setSystemCode("S000001");
		portalPortlet.setPortletType("HTML");
		portalPortlet.setPreviewImageId("img_portlet_06");
		portalPortlet.setOwnerId("admin");
		portalPortlet.setRegisterId("admin");
		portalPortlet.setUpdaterId("admin");
		portalPortlet.setRegisterName("어드민");
		portalPortlet.setUpdaterName("어드민");
		portalPortlet.setNormalViewUrl("/portal/portlet/worldClock/normalView.do");
		portalPortlet.setReloadMode(1);
		portalPortlet.setRemoveMode(1);
		portalPortlet.setConfigMode(1);
		portalPortlet.setConfigViewUrl("/portal/portlet/worldClock/normalView.do");
		portalPortlet.setHeaderMode(1);
		portalPortlet.setHelpViewUrl("/portal/portlet/worldClock/normalView.do");
		portalPortlet.setHelpMode(1);
		portalPortlet.setLinkType("PortletSimple");
		portalPortlet.setMaxMode(1);
		portalPortlet.setMaxViewUrl("/portal/portlet/worldClock/normalView.do");
		portalPortlet.setMoveable(1);
		portalPortlet.setMultipleMode(1);
		
		return portalPortlet;
	}
	
}
