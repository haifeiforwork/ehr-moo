/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;

/**
 * PortalPortletCategoryDAO 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalPortletCategoryDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalPortletCategoryDaoTest extends BaseDaoTestCase {

	@Autowired
	private PortalPortletCategoryDao portalPortletCategoryDao;

	private PortalPortletCategory createPortalPortletCategory;
	
	private PortalPortletCategory updatePortalPortletCategory;
	
	private AdminSearchCondition searchCondition;
	
	@Before
	public void setUp() {
		
		createPortalPortletCategory = new PortalPortletCategory();
		createPortalPortletCategory.setPortletCategoryId("1234567890");
		createPortalPortletCategory.setPortletCategoryName("테스트");
		createPortalPortletCategory.setDescription("테스트");
		createPortalPortletCategory.setSystemCode("S000001");
		createPortalPortletCategory.setRegisterId("admin");
		createPortalPortletCategory.setRegisterName("관리자");
		createPortalPortletCategory.setUpdaterId("admin");
		createPortalPortletCategory.setUpdaterName("관리자");
		
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
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao#getPortletCategory(java.lang.String)}.
	 */
	@Test
	public void testGetPortletCategory() {
		
		String id = portalPortletCategoryDao.createPortletCategory(createPortalPortletCategory);
		
		PortalPortletCategory result = portalPortletCategoryDao.getPortletCategory(id);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao#createPortletCategory(com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory)}.
	 */
	@Test
	public void testCreatePortletCategory() {
		
		String id = portalPortletCategoryDao.createPortletCategory(createPortalPortletCategory);
		
		PortalPortletCategory result = portalPortletCategoryDao.getPortletCategory(id);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao#listPortletCategory(java.lang.String)}.
	 */
	@Test
	public void testListPortletCategory() {
		
		portalPortletCategoryDao.createPortletCategory(createPortalPortletCategory);
		
		List<PortalPortletCategory> result = portalPortletCategoryDao.listPortletCategory(createPortalPortletCategory.getSystemCode());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao#listPortletCategoryAll()}.
	 */
	@Test
	public void testListPortletCategoryAll() {
		
		portalPortletCategoryDao.createPortletCategory(createPortalPortletCategory);
		
		List<PortalPortletCategory> result = portalPortletCategoryDao.listPortletCategoryAll();
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao#listBySearchCondition(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)}.
	 */
	@Test
	public void testListBySearchCondition() {
		
		portalPortletCategoryDao.createPortletCategory(createPortalPortletCategory);
		
		List<PortalPortletCategory> result = portalPortletCategoryDao.listBySearchCondition(searchCondition);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao#countBySearchCondition(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)}.
	 */
	@Test
	public void testCountBySearchCondition() {
		
		Integer tempCount = portalPortletCategoryDao.countBySearchCondition(searchCondition);
		
		portalPortletCategoryDao.createPortletCategory(createPortalPortletCategory);
		
		Integer count = portalPortletCategoryDao.countBySearchCondition(searchCondition);
		
		assertTrue(count > tempCount);
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao#removePortletCategory(java.lang.String)}.
	 */
	@Test
	public void testRemovePortletCategory() {
		
		String id = portalPortletCategoryDao.createPortletCategory(createPortalPortletCategory);
		portalPortletCategoryDao.removePortletCategory(id);
		
		PortalPortletCategory result = portalPortletCategoryDao.getPortletCategory(id);
		
		assertNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao#updatePortletCategory(com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory)}.
	 */
	@Test
	public void testUpdatePortletCategory() {
		
		String id = portalPortletCategoryDao.createPortletCategory(createPortalPortletCategory);
		
		PortalPortletCategory tempCreate = portalPortletCategoryDao.getPortletCategory(id);
		
		updatePortalPortletCategory.setPortletCategoryId(id);
		
		portalPortletCategoryDao.updatePortletCategory(updatePortalPortletCategory);
		
		PortalPortletCategory tempUpdate = portalPortletCategoryDao.getPortletCategory(id);
		
		assertNotSame(tempCreate, tempUpdate);
		
	}
	
}