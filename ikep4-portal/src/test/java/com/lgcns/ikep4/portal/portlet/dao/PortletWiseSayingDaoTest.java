/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.portlet.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying;

/**
 * PortletWiseSayingDao 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortletWiseSayingDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortletWiseSayingDaoTest extends BaseDaoTestCase {

	@Autowired
	private PortletWiseSayingDao portletWiseSayingDao;

	private PortletWiseSaying portletWiseSayingCreate;
	
	private PortletWiseSaying portletWiseSayingUpdate;
	
	private SearchCondition searchCondition;
	
	@Before
	public void setUp() {
		
		portletWiseSayingCreate = new PortletWiseSaying();
		portletWiseSayingCreate.setWiseSayingId("1111111111");
		portletWiseSayingCreate.setWriterName("user1");
		portletWiseSayingCreate.setWriterEnglishName("user1");
		portletWiseSayingCreate.setWiseSayingContents("Create Contents");
		portletWiseSayingCreate.setWiseSayingEnglishContents("Create English Contents");
		portletWiseSayingCreate.setRegisterId("user1");
		portletWiseSayingCreate.setRegisterName("user1");
		portletWiseSayingCreate.setUpdaterId("user1");
		portletWiseSayingCreate.setUpdaterName("user1");
		
		portletWiseSayingUpdate = new PortletWiseSaying();
		portletWiseSayingUpdate.setWiseSayingId("1111111111");
		portletWiseSayingUpdate.setWriterName("user1");
		portletWiseSayingUpdate.setWriterEnglishName("user1");
		portletWiseSayingUpdate.setWiseSayingContents("Update Contents");
		portletWiseSayingUpdate.setWiseSayingEnglishContents("Update English Contents");
		portletWiseSayingUpdate.setRegisterId("user1");
		portletWiseSayingUpdate.setRegisterName("user1");
		portletWiseSayingUpdate.setUpdaterId("user1");
		portletWiseSayingUpdate.setUpdaterName("user1");
		
		searchCondition = new SearchCondition();
		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(10);
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#listBySearchCondition(com.lgcns.ikep4.framework.web.SearchCondition)}.
	 */
	@Test
	public void testListBySearchCondition() {
		
		portletWiseSayingDao.create(portletWiseSayingCreate);
		
		assertNotNull(portletWiseSayingDao.listBySearchCondition(searchCondition));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#countBySearchCondition(com.lgcns.ikep4.framework.web.SearchCondition)}.
	 */
	@Test
	public void testCountBySearchCondition() {
		
		Integer tempCount = portletWiseSayingDao.countBySearchCondition(searchCondition);
			
		portletWiseSayingDao.create(portletWiseSayingCreate);
		
		assertTrue(portletWiseSayingDao.countBySearchCondition(searchCondition) > tempCount);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#countByList()}.
	 */
	@Test
	public void testCountByList() {
		
		Integer tempCount = portletWiseSayingDao.countByList();
		
		portletWiseSayingDao.create(portletWiseSayingCreate);
		
		assertTrue(portletWiseSayingDao.countByList() > tempCount);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#countBySearchCondition(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		
		portletWiseSayingDao.create(portletWiseSayingCreate);
		
		assertNotNull(portletWiseSayingDao.get(portletWiseSayingCreate.getWiseSayingId()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#getRandomWiseSaying()}.
	 */
	@Test
	public void testGetRandomWiseSaying() {
		
		portletWiseSayingDao.create(portletWiseSayingCreate);
		
		Random random = new Random();
		
		Integer count = portletWiseSayingDao.countByList();
		Integer randomCount = random.nextInt(count) + 1;
		
		assertNotNull(portletWiseSayingDao.getRandomWiseSaying(randomCount));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#existsWiseSaying(com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying)}.
	 */
	@Test
	public void testExistsWiseSaying() {
		
		portletWiseSayingDao.create(portletWiseSayingCreate);
		
		assertTrue(portletWiseSayingDao.existsWiseSaying(portletWiseSayingCreate));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#create(com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying)}.
	 */
	@Test
	public void testCreate() {
		
		portletWiseSayingDao.create(portletWiseSayingCreate);
		
		assertNotNull(portletWiseSayingDao.get(portletWiseSayingCreate.getWiseSayingId()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#update(com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying)}.
	 */
	@Test
	public void testUpdate() {
		
		portletWiseSayingDao.create(portletWiseSayingCreate);
		portletWiseSayingDao.update(portletWiseSayingUpdate);
		
		PortletWiseSaying tempWiseSaying = new PortletWiseSaying();
		tempWiseSaying = portletWiseSayingDao.get(portletWiseSayingCreate.getWiseSayingId());
		
		assertNotSame(portletWiseSayingCreate, tempWiseSaying);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		
		portletWiseSayingDao.create(portletWiseSayingCreate);
		portletWiseSayingDao.delete(portletWiseSayingCreate.getWiseSayingId());
		
		assertNull(portletWiseSayingDao.get(portletWiseSayingCreate.getWiseSayingId()));
		
	}
	
}