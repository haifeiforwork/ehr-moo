/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * PortalPortletDao 테스트 케이스
 *
 * @author 한승환
 * @version $Id: PortalPortletDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */

public class PortalPortletDaoTest extends BaseDaoTestCase {

	@Autowired
	PortalPortletDao portletDao;
	
	@Autowired
	private IdgenService idgenService;
	
	private PortalPortlet portalPortlet;

	private AdminSearchCondition searchCondition;
	
	private String portletId;
	
	private String systemCode;

	@Before
	public void setUp() throws Exception {
		
		final String generatedId = idgenService.getNextId();
		
		String portalId = "P000001";
		
		this.portalPortlet = PortalPortletFixture.fixturePortalPortlet(generatedId);
		this.portletId = portletDao.createPortalPortlet(this.portalPortlet);
		this.searchCondition = new AdminSearchCondition();
		this.searchCondition.setStartRowIndex(1);
		this.searchCondition.setStartRowIndex(10);
		this.searchCondition.setPortalId(portalId);
		
		
	}

	@After
	public void tearDown() throws Exception {}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#listPortalPortletByCondition(com.lgcns.ikep4.framework.web.SearchCondition)}.
	 */
	@Test
	public void testListPortalPortletByCondition() {
		List<PortalPortlet> result  = portletDao.listPortalPortletByCondition(searchCondition);
		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#countPortalPortletByCondition(com.lgcns.ikep4.framework.web.SearchCondition)}.
	 */
	@Test
	public void testCountPortalPortletByCondition() {
		int result = portletDao.countPortalPortletByCondition(this.searchCondition);
		Assert.assertTrue(result > 0);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#listPortlet(java.lang.String)}.
	 */
	@Test
	public void testListPortlet() {
		List<PortalPortlet> result  = portletDao.listPortlet(this.systemCode);
		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#listPortletCommonCheck(java.lang.String)}.
	 */
	@Test
	public void testListPortletCommonCheck() {
		List<PortalPortlet> result = portletDao.listPortletCommonCheck(this.systemCode, "admin", "ko", "portletName");
		Assert.assertNotNull(result);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#readPortalPortlet(java.lang.String)}.
	 */
	@Test
	public void testReadPortalPortlet() {
		PortalPortlet result = portletDao.readPortalPortlet(this.portletId);
		Assert.assertNotNull(result);
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#createPortalPortlet(com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet)}.
	 */
	@Test
	public void testCreatePortalPortlet() {
		PortalPortlet result = portletDao.readPortalPortlet(this.portletId);
		Assert.assertEquals(this.portletId,result.getPortletId());
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#updatePortalPortlet(com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet)}.
	 */
	@Test
	public void testUpdatePortalPortlet() {
		int result = portletDao.updatePortalPortlet(this.portalPortlet);
		Assert.assertTrue(result > 0);
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#removePortalPortlet(java.lang.String)}.
	 */
	@Test
	public void testRemovePortalPortlet() {
		int result = portletDao.removePortalPortlet(this.portletId);
		Assert.assertTrue(result > 0);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#listPageLayoutPortlet(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testListPageLayoutPortlet() {
		//portletDao.listPageLayoutPortlet(pageLayoutId, systemCode);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#listActivePortlet(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testListActivePortlet() {
		//portletDao.listActivePortlet(systemCode, pageLayoutList)
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao#listPageLayoutMovePortlet(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testListPageLayoutMovePortlet() {
		//portletDao.listPageLayoutMovePortlet(pageLayoutId, systemCode, portletConfigId);
	}
}
