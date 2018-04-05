package com.lgcns.ikep4.portal.main.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.service.BaseServiceTestCase;
import com.lgcns.ikep4.portal.main.model.PortalMymenuConfig;

public class PortalMymenuConfigServiceTest extends BaseServiceTestCase{
	
	private PortalMymenuConfig portalMymenuConfig;
	
	private PortalMymenuConfig updatePortalMymenuConfig;
	
	@Before
	public void setUp() {
		portalMymenuConfig = new PortalMymenuConfig();
		
		portalMymenuConfig.setUserId("admin111");
		portalMymenuConfig.setOpenOption(0);
		portalMymenuConfig.setRegisterId("admin111");
		portalMymenuConfig.setRegisterName("관리자");
		portalMymenuConfig.setUpdaterId("admin111");
		portalMymenuConfig.setUpdaterName("관리자");
		
		
		updatePortalMymenuConfig = new PortalMymenuConfig();
		
		updatePortalMymenuConfig.setUserId("admin111");
		updatePortalMymenuConfig.setOpenOption(1);
		updatePortalMymenuConfig.setUpdaterId("admin111");
		updatePortalMymenuConfig.setUpdaterName("관리자");
	}
	
	@Autowired
	private PortalMymenuConfigService PortalMymenuConfigService;
	
	@Test
	public void testReadPortalMymenuConfig() {
		PortalMymenuConfigService.createPortalMymenuConfig(portalMymenuConfig);
		PortalMymenuConfig result = PortalMymenuConfigService.readPortalMymenuConfig(portalMymenuConfig.getUserId());
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateOpenOption() {
		PortalMymenuConfigService.createPortalMymenuConfig(portalMymenuConfig);
		
		PortalMymenuConfigService.updateOpenOption(updatePortalMymenuConfig);
		PortalMymenuConfig updateResult = PortalMymenuConfigService.readPortalMymenuConfig(updatePortalMymenuConfig.getUserId());
		
		assertEquals(updatePortalMymenuConfig.getOpenOption(), updateResult.getOpenOption());
		
	}
	
	@Test
	public void testCreatePortalMymenuConfig(){
		PortalMymenuConfigService.createPortalMymenuConfig(portalMymenuConfig);
		PortalMymenuConfig result = PortalMymenuConfigService.readPortalMymenuConfig(portalMymenuConfig.getUserId());
		
		assertNotNull(result);
	}
}
