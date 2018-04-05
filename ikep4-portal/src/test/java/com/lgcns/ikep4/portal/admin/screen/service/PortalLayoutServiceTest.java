package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;

public class PortalLayoutServiceTest extends BaseServiceTestCase{
	
	private PortalLayout portalLayout;
	
	private PortalLayout updatePortalLayout;
	
	private AdminSearchCondition searchCondition;
	
	@Before
	public void setUp() {
		portalLayout = new PortalLayout();
		
		portalLayout.setLayoutName("3 Tiles");
		portalLayout.setDescription("3 Tiles");
		portalLayout.setCommon(0);
		portalLayout.setLayoutNum("3");
		portalLayout.setRegisterId("admin");
		portalLayout.setRegisterName("관리자");
		portalLayout.setUpdaterId("admin");
		portalLayout.setUpdaterName("관리자");
		
		updatePortalLayout = new PortalLayout();
		
		updatePortalLayout.setLayoutName("update 3 Tiles");
		updatePortalLayout.setDescription("update 3 Tiles");
		updatePortalLayout.setCommon(0);
		updatePortalLayout.setLayoutNum("2");
		updatePortalLayout.setRegisterId("admin");
		updatePortalLayout.setRegisterName("관리자");
		updatePortalLayout.setUpdaterId("admin");
		updatePortalLayout.setUpdaterName("관리자");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("layoutName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Autowired
	private PortalLayoutService portalLayoutService;
	
	@Test
	public void testListLayout() {
		portalLayoutService.createLayout(portalLayout);
		List<PortalLayout> result = portalLayoutService.listLayout();
		
		assertNotNull(result);
	}
	
	@Test
	public void testListBySearchCondition() {		
		portalLayoutService.createLayout(portalLayout);
		SearchResult<PortalLayout> result = portalLayoutService.listBySearchCondition(searchCondition);
		
		assertNotNull(result);
	}
	
	@Test
	public void testCreateLayout() {
		portalLayoutService.createLayout(portalLayout);
		PortalLayout result = portalLayoutService.readLayout(portalLayout.getLayoutId());
		
		assertNotNull(result);
	}
	
	@Test
	public void testReadLayout() {
		portalLayoutService.createLayout(portalLayout);
		PortalLayout result = portalLayoutService.readLayout(portalLayout.getLayoutId());
		
		assertNotNull(result);
	}

	@Test
	public void testRemoveLayout() {
		portalLayoutService.createLayout(portalLayout);
		portalLayoutService.removeLayout(portalLayout.getLayoutId());
		PortalLayout result = portalLayoutService.readLayout(portalLayout.getLayoutId());
		
		assertNull(result);
	}

	@Test
	public void testUpdateLayout() {
		portalLayoutService.createLayout(portalLayout);
		
		updatePortalLayout.setLayoutId(portalLayout.getLayoutId());
		
		portalLayoutService.updateLayout(updatePortalLayout);
		PortalLayout updateResult = portalLayoutService.readLayout(updatePortalLayout.getLayoutId());
		
		assertEquals(updatePortalLayout.getLayoutName(), updateResult.getLayoutName());
	}
}
