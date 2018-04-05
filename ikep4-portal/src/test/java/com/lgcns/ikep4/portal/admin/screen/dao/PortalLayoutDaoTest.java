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

import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;

/**
 * PortalLayoutDAO 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalLayoutDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalLayoutDaoTest extends BaseDaoTestCase {

	@Autowired
	private PortalLayoutDao portalLayoutDao;

	private PortalLayout createPortalLayout;
	
	private PortalLayout updatePortalLayout;
	
	private AdminSearchCondition searchCondition;
	
	@Before
	public void setUp() {
		
		createPortalLayout = new PortalLayout();
		
		createPortalLayout.setLayoutId("1234567890");
		createPortalLayout.setLayoutName("3 Tiles");
		createPortalLayout.setDescription("3 Tiles");
		createPortalLayout.setCommon(0);
		createPortalLayout.setLayoutNum("3");
		createPortalLayout.setRegisterId("admin");
		createPortalLayout.setRegisterName("관리자");
		createPortalLayout.setUpdaterId("admin");
		createPortalLayout.setUpdaterName("관리자");
		
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
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao#getLayout(java.lang.String)}.
	 */
	@Test
	public void testGetLayout() {
		
		String id = portalLayoutDao.createLayout(createPortalLayout);
		
		PortalLayout result = portalLayoutDao.getLayout(id);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao#createLayout(com.lgcns.ikep4.portal.admin.screen.model.PortalLayout)}.
	 */
	@Test
	public void testCreateLayout() {
		
		String id = portalLayoutDao.createLayout(createPortalLayout);
		
		PortalLayout result = portalLayoutDao.getLayout(id);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao#listLayout()}.
	 */
	@Test
	public void testListLayout() {
		
		portalLayoutDao.createLayout(createPortalLayout);
		
		List<PortalLayout> result = portalLayoutDao.listLayout();
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao#listBySearchCondition(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)}.
	 */
	@Test
	public void testListBySearchCondition() {
		
		portalLayoutDao.createLayout(createPortalLayout);
		
		List<PortalLayout> result = portalLayoutDao.listBySearchCondition(searchCondition);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao#countBySearchCondition(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)}.
	 */
	@Test
	public void testCountBySearchCondition() {
		
		Integer tempCount = portalLayoutDao.countBySearchCondition(searchCondition);
		
		portalLayoutDao.createLayout(createPortalLayout);
		
		Integer count = portalLayoutDao.countBySearchCondition(searchCondition);
		
		assertTrue(count > tempCount);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao#listLayoutCommonCheck(int)}.
	 */
	@Test
	public void testListLayoutCommonCheck() {
		
		portalLayoutDao.createLayout(createPortalLayout);
		
		List<PortalLayout> result = portalLayoutDao.listLayoutCommonCheck(createPortalLayout.getCommon());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao#removeLayout(java.lang.String)}.
	 */
	@Test
	public void testRemoveLayout() {
		
		String id = portalLayoutDao.createLayout(createPortalLayout);
		portalLayoutDao.removeLayout(id);
		
		PortalLayout result = portalLayoutDao.getLayout(id);
		
		assertNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao#updateLayout(com.lgcns.ikep4.portal.admin.screen.model.PortalLayout)}.
	 */
	@Test
	public void testUpdateLayout() {
		
		String id = portalLayoutDao.createLayout(createPortalLayout);
		
		PortalLayout tempCreate = portalLayoutDao.getLayout(id);
		
		updatePortalLayout.setLayoutId(id);
		
		portalLayoutDao.updateLayout(updatePortalLayout);
		
		PortalLayout tempUpdate = portalLayoutDao.getLayout(id);
		
		assertNotSame(tempCreate, tempUpdate);
		
	}
	
}