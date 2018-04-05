package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;

public class PortalPortletCategoryServiceTest extends BaseServiceTestCase{
	
	private PortalPortletCategory portalPortletCategory;
	
	private PortalPortletCategory updatePortalPortletCategory;
	
	private AdminSearchCondition searchCondition;
	
	@Before
	public void setUp() {
		portalPortletCategory = new PortalPortletCategory();
		portalPortletCategory.setPortletCategoryName("테스트");
		portalPortletCategory.setDescription("테스트");
		portalPortletCategory.setSystemCode("S000001");
		portalPortletCategory.setRegisterId("admin");
		portalPortletCategory.setRegisterName("관리자");
		portalPortletCategory.setUpdaterId("admin");
		portalPortletCategory.setUpdaterName("관리자");
		
		updatePortalPortletCategory = new PortalPortletCategory();
		updatePortalPortletCategory.setPortletCategoryName("update 테스트");
		updatePortalPortletCategory.setDescription("update 테스트");
		updatePortalPortletCategory.setSystemCode("S000001");
		updatePortalPortletCategory.setRegisterId("admin");
		updatePortalPortletCategory.setRegisterName("관리자");
		updatePortalPortletCategory.setUpdaterId("admin");
		updatePortalPortletCategory.setUpdaterName("관리자");
		
		searchCondition = new AdminSearchCondition();
		searchCondition.setFieldName("portletCategoryName");
		searchCondition.setUserLocaleCode("ko");
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	@Autowired
	private PortalPortletCategoryService portalPortletCategoryService;
	
	@Test
	public void testListPortletCategory() {
		portalPortletCategoryService.createPortletCategory(portalPortletCategory);
		List<PortalPortletCategory> result = portalPortletCategoryService.listPortletCategory("S000001");
		
		assertNotNull(result);
	}
	
	@Test
	public void testListPortletCategoryAll() {
		portalPortletCategoryService.createPortletCategory(portalPortletCategory);
		List<PortalPortletCategory> result = portalPortletCategoryService.listPortletCategoryAll();
		
		assertNotNull(result);
	}
	
	@Test
	public void testListBySearchCondition() {		
		portalPortletCategoryService.createPortletCategory(portalPortletCategory);
		SearchResult<PortalPortletCategory> result = portalPortletCategoryService.listBySearchCondition(searchCondition);
		
		assertNotNull(result);
	}

	@Test
	public void testCreatePortletCategory() {
		portalPortletCategoryService.createPortletCategory(portalPortletCategory);
		PortalPortletCategory result = portalPortletCategoryService.readPortletCategory(portalPortletCategory.getPortletCategoryId());
		
		assertNotNull(result);
	}

	@Test
	public void testReadPortletCategory() {
		portalPortletCategoryService.createPortletCategory(portalPortletCategory);
		PortalPortletCategory result = portalPortletCategoryService.readPortletCategory(portalPortletCategory.getPortletCategoryId());
		
		assertNotNull(result);
	}

	@Test
	public void testRemovePortletCategory() {
		portalPortletCategoryService.createPortletCategory(portalPortletCategory);
		portalPortletCategoryService.removePortletCategory(portalPortletCategory.getPortletCategoryId());
		PortalPortletCategory result = portalPortletCategoryService.readPortletCategory(portalPortletCategory.getPortletCategoryId());
		
		assertNull(result);
	}

	@Test
	public void testUpdatePortletCategory() {
		portalPortletCategoryService.createPortletCategory(portalPortletCategory);
		
		updatePortalPortletCategory.setPortletCategoryId(portalPortletCategory.getPortletCategoryId());
		
		portalPortletCategoryService.updatePortletCategory(updatePortalPortletCategory);
		PortalPortletCategory updateResult = portalPortletCategoryService.readPortletCategory(updatePortalPortletCategory.getPortletCategoryId());
		
		assertEquals(updatePortalPortletCategory.getPortletCategoryName(), updateResult.getPortletCategoryName());
	}
}
