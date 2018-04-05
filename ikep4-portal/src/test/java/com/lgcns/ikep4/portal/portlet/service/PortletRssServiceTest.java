package com.lgcns.ikep4.portal.portlet.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.portlet.model.PortletRss;

public class PortletRssServiceTest extends BaseServiceTestCase{
	
	private PortletRss portletRss;
	
	private PortalPortletConfig portalPortletConfig;
	
	private PortalPageLayout portalPageLayout;
	
	@Before
	public void setUp() {
		
		portalPageLayout = new PortalPageLayout();
		portalPageLayout.setPageLayoutId("1");
		portalPageLayout.setPageId("100000126387");
		portalPageLayout.setLayoutId("100000000393");
		portalPageLayout.setOwnerId("admin");
		portalPageLayout.setColIndex(1);
		portalPageLayout.setWidth(32.5);
		portalPageLayout.setRegisterId("admin");
		portalPageLayout.setRegisterName("관리자");
		portalPageLayout.setUpdaterId("admin");
		portalPageLayout.setUpdaterName("관리자");
		
		
		portalPortletConfig = new PortalPortletConfig();
		portalPortletConfig.setPortletConfigId("1");
		portalPortletConfig.setColIndex(1);
		portalPortletConfig.setPageLayoutId("1");
		portalPortletConfig.setPortletId("100000001044");
		portalPortletConfig.setRowIndex(1);
		portalPortletConfig.setViewMode("NORMAL");
		portalPortletConfig.setRegisterId("admin");
		portalPortletConfig.setRegisterName("관리자");
		portalPortletConfig.setUpdaterId("admin");
		portalPortletConfig.setUpdaterName("관리자");
		
		
		portletRss = new PortletRss();
		
		portletRss.setPortletConfigId("1");
		portletRss.setRssUrl("http://www.naver.com");
		portletRss.setListCount(5);
		portletRss.setRegisterId("admin");
		portletRss.setRegisterName("관리자");
		portletRss.setUpdaterId("admin");
		portletRss.setUpdaterName("관리자");
	}
	
	@Autowired
	private PortletRssService portletRssService;
	
	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	@Test
	public void testCreatePortletRss() {
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		portletRssService.createPortletRss(portletRss);
		PortletRss result = portletRssService.readPortletRss(portletRss.getPortletConfigId());
		
		assertNotNull(result);
	}

	@Test
	public void testReadPortletRss() {
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		//포틀릿 config 생성
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		portletRssService.createPortletRss(portletRss);
		PortletRss result = portletRssService.readPortletRss(portletRss.getPortletConfigId());
		
		assertNotNull(result);
	}
}
