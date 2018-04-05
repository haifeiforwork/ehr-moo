/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.portlet.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying;

/**
 * PortletWiseSayingService 테스트 케이스
 *
 * @author 박철종
 * @version $Id: PortletWiseSayingServiceTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortletWiseSayingServiceTest extends BaseServiceTestCase {
	
	@Autowired
	private PortletWiseSayingService portletWiseSayingService;
	
	private PortletWiseSaying portletWiseSayingCreate1;
	
	private PortletWiseSaying portletWiseSayingCreate2;
	
	private PortletWiseSaying portletWiseSayingUpdate;
	
	private SearchCondition searchCondition;
	
	@Before
	public void setUp() {
		
		portletWiseSayingCreate1 = new PortletWiseSaying();
		portletWiseSayingCreate1.setWiseSayingId("1111111111");
		portletWiseSayingCreate1.setWriterName("user1");
		portletWiseSayingCreate1.setWriterEnglishName("user1");
		portletWiseSayingCreate1.setWiseSayingContents("Create1 Contents");
		portletWiseSayingCreate1.setWiseSayingEnglishContents("Create1 English Contents");
		portletWiseSayingCreate1.setRegisterId("user1");
		portletWiseSayingCreate1.setRegisterName("user1");
		portletWiseSayingCreate1.setUpdaterId("user1");
		portletWiseSayingCreate1.setUpdaterName("user1");
		
		portletWiseSayingCreate2 = new PortletWiseSaying();
		portletWiseSayingCreate2.setWiseSayingId("2222222222");
		portletWiseSayingCreate2.setWriterName("user1");
		portletWiseSayingCreate2.setWriterEnglishName("user1");
		portletWiseSayingCreate2.setWiseSayingContents("Create2 Contents");
		portletWiseSayingCreate2.setWiseSayingEnglishContents("Create2 English Contents");
		portletWiseSayingCreate2.setRegisterId("user1");
		portletWiseSayingCreate2.setRegisterName("user1");
		portletWiseSayingCreate2.setUpdaterId("user1");
		portletWiseSayingCreate2.setUpdaterName("user1");
		
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
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#listBySearchCondition(com.lgcns.ikep4.framework.web.SearchCondition)}.
	 */
	@Test
	public void testListBySearchCondition() throws Exception {
		
		portletWiseSayingService.createWiseSaying(portletWiseSayingCreate1);
		
		assertNotNull(portletWiseSayingService.listBySearchCondition(searchCondition));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		
		portletWiseSayingService.create(portletWiseSayingCreate1);
		
		assertNotNull(portletWiseSayingService.get(portletWiseSayingCreate1.getWiseSayingId()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#getRandomWiseSaying()}.
	 */
	@Test
	public void testGetRandomWiseSaying() {
		
		portletWiseSayingService.create(portletWiseSayingCreate1);
		
		assertNotNull(portletWiseSayingService.getRandomWiseSaying());
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#create(com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying)}.
	 */
	@Test
	public void testCreate() {
		
		portletWiseSayingService.create(portletWiseSayingCreate1);
		
		assertNotNull(portletWiseSayingService.get(portletWiseSayingCreate1.getWiseSayingId()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#existsWiseSaying(com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying)}.
	 */
	@Test
	public void testExistsWiseSaying() {
		
		portletWiseSayingService.create(portletWiseSayingCreate1);
		
		assertTrue(portletWiseSayingService.existsWiseSaying(portletWiseSayingCreate1));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#createWiseSaying(com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying)}.
	 */
	@Test
	public void testCreateWiseSaying() throws Exception {
		
		portletWiseSayingService.createWiseSaying(portletWiseSayingCreate1);
		
		assertNotNull(portletWiseSayingService.get(portletWiseSayingCreate1.getWiseSayingId()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#updateWiseSaying(com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying)}.
	 */
	@Test
	public void testUpdateWiseSaying() throws Exception {
		
		portletWiseSayingService.createWiseSaying(portletWiseSayingCreate1);
		portletWiseSayingService.updateWiseSaying(portletWiseSayingUpdate);	
		
		PortletWiseSaying tempWiseSaying = new PortletWiseSaying();
		tempWiseSaying = portletWiseSayingService.get(portletWiseSayingCreate1.getWiseSayingId());
		
		assertNotSame(portletWiseSayingCreate1, tempWiseSaying);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() throws Exception {
		
		portletWiseSayingService.createWiseSaying(portletWiseSayingCreate1);
		portletWiseSayingService.delete(portletWiseSayingCreate1.getWiseSayingId());
		
		PortletWiseSaying portletWiseSaying = new PortletWiseSaying();
		portletWiseSaying = portletWiseSayingService.get(portletWiseSayingCreate1.getWiseSayingId());
			
		assertNull(portletWiseSaying);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService#multiDeleteWiseSaying(java.lang.String[])}.
	 */
	@Test
	public void testMultiDeleteWiseSaying() {
		
		portletWiseSayingService.create(portletWiseSayingCreate1);
		portletWiseSayingService.create(portletWiseSayingCreate2);
		
		String[] ids = {portletWiseSayingCreate1.getWiseSayingId(), portletWiseSayingCreate2.getWiseSayingId()};
		
		portletWiseSayingService.multiDeleteWiseSaying(ids);
		
		assertNull(portletWiseSayingService.get(portletWiseSayingCreate1.getWiseSayingId()));
		assertNull(portletWiseSayingService.get(portletWiseSayingCreate2.getWiseSayingId()));
		
	}
	
}