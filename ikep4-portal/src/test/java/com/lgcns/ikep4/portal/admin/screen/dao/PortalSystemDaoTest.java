/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;

/**
 * PortalSystemDAO 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemDaoTest.java 17156 2012-01-09 06:56:28Z yu_hs $
 */
public class PortalSystemDaoTest extends BaseDaoTestCase {

	@Autowired
	private PortalSystemDao portalSystemDao;

	private PortalSystem portalSystemCreate;
	
	private PortalSystem portalSystemChild1;
	
	private PortalSystem portalSystemChild2;
	
	private PortalSystem portalSystemUpdate;
	
	private Map<String,String> readParam;
	
	@Before
	public void setUp() {
		
		portalSystemCreate = new PortalSystem();
		portalSystemCreate.setSystemCode("1234567890");
		portalSystemCreate.setPortalId("P000001");
		portalSystemCreate.setSystemName("Create PortalSystem");
		portalSystemCreate.setDescription("Create Description");
		portalSystemCreate.setParentSystemCode(null);
		portalSystemCreate.setSystemType("ITEM");
		portalSystemCreate.setMainView(1);
		portalSystemCreate.setUrl("Create Url");
		//portalSystemCreate.setSortOrder("1");
		portalSystemCreate.setTarget("INNER");
		portalSystemCreate.setOwnerId("user1");
		portalSystemCreate.setRegisterId("user1");
		portalSystemCreate.setRegisterName("user1");
		//portalSystemCreate.setRegistDate(registDate);
		portalSystemCreate.setUpdaterId("user1");
		portalSystemCreate.setUpdaterName("user1");
		//portalSystemUrlCreate.setUpdateDate(updateDate);
		
		portalSystemChild1 = new PortalSystem();
		portalSystemChild1.setSystemCode("1111111111");
		portalSystemChild1.setPortalId("P000001");
		portalSystemChild1.setSystemName("Create Child1 PortalSystem");
		portalSystemChild1.setDescription("Create Child1 Description");
		portalSystemChild1.setParentSystemCode("1234567890");
		portalSystemChild1.setSystemType("ITEM");
		portalSystemChild1.setMainView(1);
		portalSystemChild1.setUrl("Create Child1 Url");
		//portalSystemChild1.setSortOrder("1");
		portalSystemChild1.setTarget("INNER");
		portalSystemChild1.setOwnerId("user1");
		portalSystemChild1.setRegisterId("user1");
		portalSystemChild1.setRegisterName("user1");
		//portalSystemChild1.setRegistDate(registDate);
		portalSystemChild1.setUpdaterId("user1");
		portalSystemChild1.setUpdaterName("user1");
		//portalSystemChild1.setUpdateDate(updateDate);
		
		portalSystemChild2 = new PortalSystem();
		portalSystemChild2.setSystemCode("2222222222");
		portalSystemChild2.setPortalId("P000001");
		portalSystemChild2.setSystemName("Create Child2 PortalSystem");
		portalSystemChild2.setDescription("Create Child2 Description");
		portalSystemChild2.setParentSystemCode("1234567890");
		portalSystemChild2.setSystemType("ITEM");
		portalSystemChild2.setMainView(1);
		portalSystemChild2.setUrl("Create Child2 Url");
		//portalSystemChild2.setSortOrder("1");
		portalSystemChild2.setTarget("INNER");
		portalSystemChild2.setOwnerId("user1");
		portalSystemChild2.setRegisterId("user1");
		portalSystemChild2.setRegisterName("user1");
		//portalSystemChild2.setRegistDate(registDate);
		portalSystemChild2.setUpdaterId("user1");
		portalSystemChild2.setUpdaterName("user1");
		//portalSystemChild2.setUpdateDate(updateDate);
		
		portalSystemUpdate = new PortalSystem();
		portalSystemUpdate.setSystemCode("1234567890");
		portalSystemUpdate.setPortalId("P000001");
		portalSystemUpdate.setSystemName("Update PortalSystem");
		portalSystemUpdate.setDescription("Update Description");
		portalSystemUpdate.setParentSystemCode(null);
		portalSystemUpdate.setSystemType("ITEM");
		portalSystemUpdate.setMainView(1);
		portalSystemUpdate.setUrl("Update Url");
		//portalSystemUpdate.setSortOrder("1");
		portalSystemUpdate.setTarget("INNER");
		portalSystemUpdate.setOwnerId("user1");
		portalSystemUpdate.setRegisterId("user1");
		portalSystemUpdate.setRegisterName("user1");
		//portalSystemUpdate.setRegistDate(registDate);
		portalSystemUpdate.setUpdaterId("user1");
		portalSystemUpdate.setUpdaterName("user1");
		//portalSystemUpdate.setUpdateDate(updateDate);

		readParam = new HashMap<String, String>();
		readParam.put("portalId", "P000001");
		readParam.put("fieldName", "systemName");
		readParam.put("localeCode", "ko");
		readParam.put("systemCode", portalSystemCreate.getSystemCode());
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#portalSystemListView(java.lang.String)}.
	 */
	@Test
	public void testPortalSystemListView() {
		
		portalSystemDao.create(portalSystemCreate);
		
		List<PortalSystem> result = portalSystemDao.portalSystemListView(portalSystemCreate.getPortalId());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		
		portalSystemDao.create(portalSystemCreate);
		
		PortalSystem tempSystem = portalSystemDao.get(portalSystemCreate.getSystemCode());
		
		assertEquals(portalSystemCreate.getSystemCode(), tempSystem.getSystemCode());
		
	}
	
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#treeList(java.util.Map)}.
	 */
	@Test
	public void testTreeList() {
		
		portalSystemDao.create(portalSystemCreate);
		
		List<PortalSystem> result = portalSystemDao.treeList(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#linkList(java.util.Map)}.
	 */
	@Test
	public void testLinkList() {
		
		portalSystemDao.create(portalSystemCreate);
		
		List<PortalSystem> result = portalSystemDao.linkList(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#exists(java.lang.String)}.
	 */
	@Test
	public void testExists() {
		
		portalSystemDao.create(portalSystemCreate);
		
		boolean result = portalSystemDao.exists(portalSystemCreate.getSystemCode());
		
		assertTrue(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#getChildCount(java.util.Map)}.
	 */
	@Test
	public void testGetChildCount() {
		
		portalSystemDao.create(portalSystemCreate);
		portalSystemDao.create(portalSystemChild1);
		portalSystemDao.create(portalSystemChild2);
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("systemCode", portalSystemCreate.getSystemCode());
		paramMap.put("portalId", portalSystemCreate.getPortalId());
		
		int result = portalSystemDao.getChildCount(paramMap);
		
		assertTrue(result == 2);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#getMaxSortOrder()}.
	 */
	@Test
	public void testGetMaxSortOrder() {
		
		String result = (String) portalSystemDao.getMaxSortOrder();
		
		assertNotNull(result);
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#create(com.lgcns.ikep4.portal.admin.screen.model.PortalSystem)}.
	 */
	@Test
	public void testCreate() {
		
		portalSystemDao.create(portalSystemCreate);	
		
		PortalSystem result = portalSystemDao.get((String) readParam.get("systemCode"));
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#update(com.lgcns.ikep4.portal.admin.screen.model.PortalSystem)}.
	 */
	@Test
	public void testUpdate() {
		
		portalSystemDao.create(portalSystemCreate);
		
		PortalSystem tempCreate = portalSystemDao.get((String) readParam.get("systemCode"));
		
		portalSystemUpdate.setSortOrder(tempCreate.getSortOrder());
		
		portalSystemDao.update(portalSystemUpdate);
		
		PortalSystem tempUpdate = portalSystemDao.get((String) readParam.get("systemCode"));
		
		assertNotSame(tempCreate, tempUpdate);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		
		portalSystemDao.create(portalSystemCreate);
		portalSystemDao.delete(portalSystemCreate.getSystemCode());
		
		PortalSystem result = portalSystemDao.get((String) readParam.get("systemCode"));
		
		assertNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao#getSystemCode(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetSystemCode() {
		
		portalSystemDao.create(portalSystemCreate);
		
		String systemCode = portalSystemDao.getSystemCode(portalSystemCreate.getSystemName(), portalSystemCreate.getPortalId());
		
		assertEquals(portalSystemCreate.getSystemCode(), systemCode);
		
	}
	
}