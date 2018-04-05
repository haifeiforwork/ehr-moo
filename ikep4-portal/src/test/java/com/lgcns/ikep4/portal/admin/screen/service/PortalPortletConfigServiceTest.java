package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.support.user.member.model.User;

public class PortalPortletConfigServiceTest extends BaseServiceTestCase{
	
	private User user;
	
	private PortalPortletConfig portalPortletConfig;
	
	private PortalPortletConfig updatePortalPortletConfig;
	
	private PortalPageLayout portalPageLayout;
	
	@Before
	public void setUp() {
		user = new User();
		user.setUserId("admin");
		user.setUserName("관리자");
		user.setLocaleCode("ko");
		
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
		portalPortletConfig.setPageLayoutId("1");
		portalPortletConfig.setPortletId("100000766900");
		portalPortletConfig.setColIndex(1);
		portalPortletConfig.setRowIndex(1);
		portalPortletConfig.setViewMode("NORMAL");
		portalPortletConfig.setRegisterId("admin");
		portalPortletConfig.setRegisterName("관리자");
		portalPortletConfig.setUpdaterId("admin");
		portalPortletConfig.setUpdaterName("관리자");
		
		
		updatePortalPortletConfig = new PortalPortletConfig();
		updatePortalPortletConfig.setPageLayoutId("2");
		updatePortalPortletConfig.setPortletId("100000766900");
		updatePortalPortletConfig.setColIndex(2);
		updatePortalPortletConfig.setRowIndex(1);
		updatePortalPortletConfig.setViewMode("MIN");
		updatePortalPortletConfig.setRegisterId("admin");
		updatePortalPortletConfig.setRegisterName("관리자");
		updatePortalPortletConfig.setUpdaterId("admin");
		updatePortalPortletConfig.setUpdaterName("관리자");
	}
	
	@Autowired
	private PortalPortletConfigService portalPortletConfigService;
	
	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	@Autowired
	private PortalSystemService portalSystemService;
	
	@Test
	public void testCreatePortletConfig() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		String portalId = "P000001";
		String localeCode = "ko";
		String systemCode = portalSystemService.getSystemCode("Portal", portalId);
		
		portalPortletConfigService.createPortletConfig(portalPortletConfig, systemCode, portalId, localeCode);
		PortalPortletConfig result = portalPortletConfigDao.getPortletConfig(portalPortletConfig.getPortletConfigId());
		
		assertNotNull(result);
		
	}

	@Test
	public void testUpdatePortletConfig() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		portalPageLayout.setPageLayoutId("2");
		portalPageLayout.setColIndex(2);
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		String portalId = "P000001";
		String localeCode = "ko";
		String systemCode = portalSystemService.getSystemCode("Portal", portalId);
		
		portalPortletConfigService.createPortletConfig(portalPortletConfig, systemCode, portalId, localeCode);
		
		updatePortalPortletConfig.setPortletConfigId(portalPortletConfig.getPortletConfigId());
		
		portalPortletConfigService.updatePortletConfig(updatePortalPortletConfig, portalId, localeCode);
		PortalPortletConfig updateResult = portalPortletConfigDao.getPortletConfig(updatePortalPortletConfig.getPortletConfigId());
		
		assertTrue(updatePortalPortletConfig.getColIndex() == updateResult.getColIndex());
		
	}

	@Test
	public void testRemovePortletConfig() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		String portalId = "P000001";
		String localeCode = "ko";
		String systemCode = portalSystemService.getSystemCode("Portal", portalId);
		
		portalPortletConfigService.createPortletConfig(portalPortletConfig, systemCode, portalId, localeCode);
		portalPortletConfigService.removePortletConfig(portalPortletConfig, systemCode, portalId, user);
		PortalPortletConfig result = portalPortletConfigDao.getPortletConfig(portalPortletConfig.getPortletConfigId());
		
		assertNull(result);
	}
	
	@Test
	public void testUpdatePortletConfigViewMode() {
		
		//페이지 레이아웃 생성
		portalPageLayoutDao.createPageLayout(portalPageLayout);
		
		String portalId = "P000001";
		String localeCode = "ko";
		String systemCode = portalSystemService.getSystemCode("Portal", portalId);
		
		portalPortletConfigService.createPortletConfig(portalPortletConfig, systemCode, portalId, localeCode);
		
		updatePortalPortletConfig.setPortletConfigId(portalPortletConfig.getPortletConfigId());
		
		portalPortletConfigService.updatePortletConfigViewMode(updatePortalPortletConfig);
		PortalPortletConfig updateResult = portalPortletConfigDao.getPortletConfig(updatePortalPortletConfig.getPortletConfigId());
		
		assertEquals(updatePortalPortletConfig.getViewMode(), updateResult.getViewMode());
	}
}
