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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;

/**
 * PortalMenuDAO 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalMenuDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalMenuDaoTest extends BaseDaoTestCase {

	@Autowired
	private PortalMenuDao portalMenuDao;

	private PortalMenu portalMenuCreate;
	
	private PortalMenu portalMenuUpdate;
	
	private Map<String,String> readParam;
	
	@Before
	public void setUp() {
		
		portalMenuCreate = new PortalMenu();
		portalMenuCreate.setMenuId("1234567890");
		portalMenuCreate.setMenuName("Create PortalMenu");
		portalMenuCreate.setDescription("Create Description");
		portalMenuCreate.setParentMenuId(null);
		portalMenuCreate.setSystemCode("S000001");
		portalMenuCreate.setMenuType("CATEGORY");
		portalMenuCreate.setUrl(null);
		portalMenuCreate.setTarget(null);
		portalMenuCreate.setRegisterId("user1");
		portalMenuCreate.setRegisterName("user1");
		portalMenuCreate.setUpdaterId("user1");
		portalMenuCreate.setUpdaterName("user1");
		
		portalMenuUpdate = new PortalMenu();
		portalMenuUpdate.setMenuId("1234567890");
		portalMenuUpdate.setMenuName("Update PortalMenu");
		portalMenuUpdate.setDescription("Update Description");
		portalMenuUpdate.setParentMenuId(null);
		portalMenuUpdate.setSystemCode("S000001");
		portalMenuUpdate.setMenuType("ITEM");
		portalMenuUpdate.setUrl("http://www.naver.com");
		portalMenuUpdate.setTarget("INNER");
		portalMenuUpdate.setRegisterId("user1");
		portalMenuUpdate.setRegisterName("user1");
		portalMenuUpdate.setUpdaterId("user1");
		portalMenuUpdate.setUpdaterName("user1");

		readParam = new HashMap<String, String>();
		readParam.put("systemName", "Portal");
		readParam.put("portalId", "P000001");
		readParam.put("fieldName", "menuName");
		readParam.put("localeCode", "ko");
		readParam.put("menuId", portalMenuCreate.getMenuId());
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao#list(java.util.Map)}.
	 */
	@Test
	public void testList() {
		
		portalMenuDao.create(portalMenuCreate);
		
		List<PortalMenu> result = portalMenuDao.list(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao#menuList(java.util.Map)}.
	 */
	@Test
	public void testMenuList() {
		
		portalMenuDao.create(portalMenuCreate);
		
		List<PortalMenu> result = portalMenuDao.menuList(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao#listByParentMenuId(java.util.Map)}.
	 */
	@Test
	public void testListByParentMenuId() {
		
		portalMenuDao.create(portalMenuCreate);
		
		List<PortalMenu> result = portalMenuDao.listByParentMenuId(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		
		portalMenuDao.create(portalMenuCreate);
		
		PortalMenu result = portalMenuDao.get(portalMenuCreate.getMenuId());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao#create(com.lgcns.ikep4.portal.admin.screen.model.PortalMenu)}.
	 */
	@Test
	public void testCreate() {
		
		portalMenuDao.create(portalMenuCreate);	
		
		PortalMenu result = portalMenuDao.get((String) readParam.get("menuId"));
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		
		portalMenuDao.create(portalMenuCreate);
		portalMenuDao.delete(portalMenuCreate.getMenuId());
		
		PortalMenu result = portalMenuDao.get((String) readParam.get("menuId"));
		
		assertNull(result);
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalMenuDao#update(com.lgcns.ikep4.portal.admin.screen.model.PortalMenu)}.
	 */
	@Test
	public void testUpdate() {
		
		portalMenuDao.create(portalMenuCreate);
		
		PortalMenu tempCreate = portalMenuDao.get((String) readParam.get("menuId"));
		
		portalMenuUpdate.setSortOrder(tempCreate.getSortOrder());
		
		portalMenuDao.update(portalMenuUpdate);
		
		PortalMenu tempUpdate = portalMenuDao.get((String) readParam.get("menuId"));
		
		assertNotSame(tempCreate, tempUpdate);
		
	}
	
}