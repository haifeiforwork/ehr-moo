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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * PortalMenuDAO 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalQuickMenuDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalQuickMenuDaoTest extends BaseDaoTestCase {

	@Autowired
	private PortalQuickMenuDao portalQuickMenuDao;
	
	private MockHttpServletRequest req;

	private PortalQuickMenu portalQuickMenuCreate;
	
	private PortalQuickMenu portalQuickMenuUpdate;
	
	private PortalQuickMenu portalUserQuickMenuCreate;
	
	private PortalQuickMenu portalUserQuickMenuUpdate;
	
	private Map<String,String> readParam;
	
	private User userSession;
	
	@Before
	public void setUp() {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
	    user.setUserId("user1");
	    user.setUserName("user1");
	    
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	    // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		portalQuickMenuCreate = new PortalQuickMenu();
		portalQuickMenuCreate.setQuickMenuId("1234567890");
		portalQuickMenuCreate.setQuickMenuName("Create QuickMenu");
		portalQuickMenuCreate.setSortOrder("0000000000001");
		portalQuickMenuCreate.setMoreUrl("Create MoreUrl");
		portalQuickMenuCreate.setTarget("INNER");
		portalQuickMenuCreate.setCount(0);
		portalQuickMenuCreate.setCountUrl("Create CountUrl");
		portalQuickMenuCreate.setRegisterId("user1");
		portalQuickMenuCreate.setRegisterName("user1");
		portalQuickMenuCreate.setUpdaterId("user1");
		portalQuickMenuCreate.setUpdaterName("user1");
		portalQuickMenuCreate.setPortalId("P000001");
		portalQuickMenuCreate.setIconId("quick_icon");
		portalQuickMenuCreate.setIntervalTime(0);
		portalQuickMenuCreate.setMoreUrlType("URL");
		
		portalQuickMenuUpdate = new PortalQuickMenu();
		portalQuickMenuUpdate.setQuickMenuId("1234567890");
		portalQuickMenuUpdate.setQuickMenuName("Update QuickMenu");
		portalQuickMenuUpdate.setSortOrder("0000000000001");
		portalQuickMenuUpdate.setMoreUrl("Update MoreUrl");
		portalQuickMenuUpdate.setTarget("INNER");
		portalQuickMenuUpdate.setCount(0);
		portalQuickMenuUpdate.setCountUrl("Update CountUrl");
		portalQuickMenuUpdate.setRegisterId("user1");
		portalQuickMenuUpdate.setRegisterName("user1");
		portalQuickMenuUpdate.setUpdaterId("user1");
		portalQuickMenuUpdate.setUpdaterName("user1");
		portalQuickMenuUpdate.setPortalId("P000001");
		portalQuickMenuUpdate.setIconId("quick_icon");
		portalQuickMenuUpdate.setIntervalTime(0);
		portalQuickMenuUpdate.setMoreUrlType("URL");
		
		portalUserQuickMenuCreate = new PortalQuickMenu();
		portalUserQuickMenuCreate.setQuickMenuId("1234567890");
		portalUserQuickMenuCreate.setUserId("user1");
		portalUserQuickMenuCreate.setSortOrder("0000000000001");
		portalUserQuickMenuCreate.setRegisterId("user1");
		portalUserQuickMenuCreate.setRegisterName("user1");
		portalUserQuickMenuCreate.setUpdaterId("user1");
		portalUserQuickMenuCreate.setUpdaterName("user1");
		
		portalUserQuickMenuUpdate = new PortalQuickMenu();
		portalUserQuickMenuUpdate.setQuickMenuId("1234567890");
		portalUserQuickMenuUpdate.setUserId("user1");
		portalUserQuickMenuUpdate.setSortOrder("0000000000001");
		portalUserQuickMenuUpdate.setRegisterId("user1");
		portalUserQuickMenuUpdate.setRegisterName("user1");
		portalUserQuickMenuUpdate.setUpdaterId("user1");
		portalUserQuickMenuUpdate.setUpdaterName("user1");
		
		readParam = new HashMap<String, String>();
		readParam.put("portalId", "P000001");
		readParam.put("fieldName", "quickMenuName");
		readParam.put("localeCode", "ko");
		readParam.put("menuId", portalQuickMenuCreate.getQuickMenuId());
		
		userSession = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#list(java.util.Map)}.
	 */
	@Test
	public void testList() {
		
		portalQuickMenuDao.create(portalQuickMenuCreate);
		
		List<PortalQuickMenu> result = portalQuickMenuDao.list(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#quickMenuList(java.util.Map)}.
	 */
	@Test
	public void testQuickMenuList() {
		
		portalQuickMenuDao.create(portalQuickMenuCreate);
		
		List<PortalQuickMenu> result = portalQuickMenuDao.quickMenuList(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#listByUserId(java.util.Map)}.
	 */
	@Test
	public void testListByUserId() {
		
		portalQuickMenuDao.create(portalQuickMenuCreate);
		portalQuickMenuDao.createUserQuickMenu(portalUserQuickMenuCreate);
		
		List<PortalQuickMenu> result = portalQuickMenuDao.listByUserId(readParam);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#listBySortOrder(java.lang.String)}.
	 */
	@Test
	public void testListBySortOrder() {
		
		portalQuickMenuDao.create(portalQuickMenuCreate);
		
		List<PortalQuickMenu> result = portalQuickMenuDao.listBySortOrder("P000001");
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		
		portalQuickMenuDao.create(portalQuickMenuCreate);
		
		PortalQuickMenu result = portalQuickMenuDao.get(portalQuickMenuCreate.getQuickMenuId());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#existsByUserId(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testExistsByUserId() {
		
		portalQuickMenuDao.create(portalQuickMenuCreate);
		portalQuickMenuDao.createUserQuickMenu(portalUserQuickMenuCreate);
				
		boolean result = portalQuickMenuDao.existsByUserId("P000001", userSession.getUserId());
		
		assertTrue(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#create(com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu)}.
	 */
	@Test
	public void testCreate() {

		portalQuickMenuDao.create(portalQuickMenuCreate);
		
		PortalQuickMenu result = portalQuickMenuDao.get(portalQuickMenuCreate.getQuickMenuId());
		
		assertNotNull(result);
		
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#update(com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu)}.
	 */
	@Test
	public void testUpdate() {

		portalQuickMenuDao.create(portalQuickMenuCreate);
		
		PortalQuickMenu tempCreate = portalQuickMenuDao.get(portalQuickMenuCreate.getQuickMenuId());
		
		portalQuickMenuDao.update(portalQuickMenuUpdate);
		
		PortalQuickMenu tempUpdate = portalQuickMenuDao.get(portalQuickMenuUpdate.getQuickMenuId());
		
		assertNotSame(tempCreate, tempUpdate);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#update(java.lang.String)}.
	 */
	public void testDelete() {
		
		portalQuickMenuDao.create(portalQuickMenuCreate);
		portalQuickMenuDao.delete(portalQuickMenuCreate.getQuickMenuId());
		
		PortalQuickMenu result = portalQuickMenuDao.get(portalQuickMenuCreate.getQuickMenuId());
		
		assertNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#createUserQuickMenu(com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu)}.
	 */
	public void testCreateUserQuickMenu(PortalQuickMenu object) {
		
		portalQuickMenuDao.create(portalQuickMenuCreate);
		portalQuickMenuDao.createUserQuickMenu(portalQuickMenuCreate);
				
		boolean result = portalQuickMenuDao.existsByUserId("P000001", userSession.getUserId());
		
		assertTrue(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalQuickMenuDao#deleteUserQuickMenu(java.lang.String)}.
	 */
	public void testDeleteUserQuickMenu() {
		
		portalQuickMenuDao.createUserQuickMenu(portalQuickMenuCreate);
		portalQuickMenuDao.deleteUserQuickMenu(userSession.getUserId());
		
		PortalQuickMenu result = portalQuickMenuDao.get(portalQuickMenuCreate.getQuickMenuId());
		
		assertNull(result);
		
	}
	
}