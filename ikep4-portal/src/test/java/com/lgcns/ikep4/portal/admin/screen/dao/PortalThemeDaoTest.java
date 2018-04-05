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

import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;

/**
 * PortalThemeDAO 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalThemeDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalThemeDaoTest extends BaseDaoTestCase {

	@Autowired
	private PortalThemeDao portalThemeDao;

	private PortalTheme createPortalTheme;
	
	private PortalTheme updatePortalTheme;
	
	private AdminSearchCondition searchCondition;
	
	@Before
	public void setUp() {
		
		createPortalTheme = new PortalTheme();
		
		createPortalTheme.setThemeId("1234567890");
		createPortalTheme.setThemeName("테스트테마");
		createPortalTheme.setDescription("테스트테마");
		createPortalTheme.setPortalId("P000001");
		createPortalTheme.setDefaultOption(1);
		createPortalTheme.setCssPath("testCss");
		createPortalTheme.setPreviewImageId("1");
		createPortalTheme.setRegisterId("user1");
		createPortalTheme.setRegisterName("사용자1");
		createPortalTheme.setUpdaterId("user1");
		createPortalTheme.setUpdaterName("사용자1");
		
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
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao#createTheme(com.lgcns.ikep4.portal.admin.screen.model.PortalTheme)}.
	 */
	@Test
	public void testCreateTheme() {
		
		String id = portalThemeDao.createTheme(createPortalTheme);
		
		PortalTheme result = portalThemeDao.getTheme(id);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao#getTheme(java.lang.String)}.
	 */
	@Test
	public void testGetTheme() {
		
		String id = portalThemeDao.createTheme(createPortalTheme);
		
		PortalTheme result = portalThemeDao.getTheme(id);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao#listTheme(com.lgcns.ikep4.portal.admin.screen.model.PortalTheme)}.
	 */
	@Test
	public void testListTheme() {
		
		portalThemeDao.createTheme(createPortalTheme);
		
		List<PortalTheme> result = portalThemeDao.listTheme(createPortalTheme);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao#listBySearchCondition(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)}.
	 */
	@Test
	public void testListBySearchCondition() {
		
		portalThemeDao.createTheme(createPortalTheme);
		
		List<PortalTheme> result = portalThemeDao.listBySearchCondition(searchCondition);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao#countBySearchCondition(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)}.
	 */
	@Test
	public void testCountBySearchCondition() {
		
		searchCondition.setPortalId("P000001");
		
		Integer tempCount = portalThemeDao.countBySearchCondition(searchCondition);
		
		portalThemeDao.createTheme(createPortalTheme);
		
		Integer count = portalThemeDao.countBySearchCondition(searchCondition);
		
		assertTrue(count > tempCount);
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao#updateTheme(com.lgcns.ikep4.portal.admin.screen.model.PortalTheme)}.
	 */
	@Test
	public void testUpdateTheme() {
		
		String id = portalThemeDao.createTheme(createPortalTheme);
		
		PortalTheme tempCreate = portalThemeDao.getTheme(id);
		
		updatePortalTheme.setThemeId(id);
		
		portalThemeDao.updateTheme(updatePortalTheme);
		
		PortalTheme tempUpdate = portalThemeDao.getTheme(id);
		
		assertNotSame(tempCreate, tempUpdate);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao#removeTheme(java.lang.String)}.
	 */
	@Test
	public void testRemoveTheme() {
		
		String id = portalThemeDao.createTheme(createPortalTheme);
		portalThemeDao.removeTheme(id);
		
		PortalTheme result = portalThemeDao.getTheme(id);
		
		assertNull(result);
		
	}
	
}