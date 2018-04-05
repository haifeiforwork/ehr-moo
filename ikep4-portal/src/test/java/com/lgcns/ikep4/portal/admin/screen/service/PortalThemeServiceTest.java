package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;

public class PortalThemeServiceTest extends BaseServiceTestCase{
	
	private PortalTheme portalTheme;
	
	private PortalTheme updatePortalTheme;
	
	private AdminSearchCondition searchCondition;
	
	@Before
	public void setUp() {
		portalTheme = new PortalTheme();
		
		portalTheme.setThemeName("테스트테마");
		portalTheme.setDescription("테스트테마");
		portalTheme.setPortalId("P000001");
		portalTheme.setDefaultOption(1);
		portalTheme.setCssPath("testCss");
		portalTheme.setPreviewImageId("1");
		portalTheme.setRegisterId("user1");
		portalTheme.setRegisterName("사용자1");
		portalTheme.setUpdaterId("user1");
		portalTheme.setUpdaterName("사용자1");
		
		updatePortalTheme = new PortalTheme();
		
		updatePortalTheme.setThemeName("update 테스트테마");
		updatePortalTheme.setDescription("update 테스트테마");
		updatePortalTheme.setPortalId("P000001");
		updatePortalTheme.setDefaultOption(0);
		updatePortalTheme.setCssPath("update testCss");
		updatePortalTheme.setPreviewImageId("update 1");
		updatePortalTheme.setRegisterId("user1");
		updatePortalTheme.setRegisterName("사용자1");
		updatePortalTheme.setUpdaterId("user1");
		updatePortalTheme.setUpdaterName("사용자1");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("themeName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Autowired
	private PortalThemeService portalThemeService;
	
	@Test
	public void testCreateTheme() {
		portalThemeService.createTheme(portalTheme);
		PortalTheme result = portalThemeService.readTheme(portalTheme.getThemeId());
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateTheme() {
		portalThemeService.createTheme(portalTheme);
		
		updatePortalTheme.setThemeId(portalTheme.getThemeId());
		
		portalThemeService.updateTheme(updatePortalTheme);
		PortalTheme updateResult = portalThemeService.readTheme(updatePortalTheme.getThemeId());
		
		assertEquals(updatePortalTheme.getThemeName(), updateResult.getThemeName());
	}
	
	@Test
	public void testReadTheme() {
		portalThemeService.createTheme(portalTheme);
		PortalTheme result = portalThemeService.readTheme(portalTheme.getThemeId());
		
		assertNotNull(result);
	}

	@Test
	public void testListTheme() {
		portalThemeService.createTheme(portalTheme);
		List<PortalTheme> result = portalThemeService.listTheme(portalTheme);
		
		assertNotNull(result);
	}
	
	@Test
	public void testListBySearchCondition() {		
		portalThemeService.createTheme(portalTheme);
		SearchResult<PortalTheme> result = portalThemeService.listBySearchCondition(searchCondition);
		
		assertNotNull(result);
	}
	
	@Test
	public void testRemoveTheme() {
		portalThemeService.createTheme(portalTheme);
		portalThemeService.removeTheme(portalTheme.getThemeId());
		PortalTheme result = portalThemeService.readTheme(portalTheme.getThemeId());
		
		assertNull(result);
	}
}
