/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.portal.admin.screen.model.PortalMenu;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * PortalMenuService 테스트 케이스
 *
 * @author 박철종
 * @version $Id: PortalMenuServiceTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalMenuServiceTest extends BaseServiceTestCase {
	
	@Autowired
	private PortalMenuService portalMenuService;
	
	@Autowired
	private I18nMessageService i18nMessageService;

    private MockHttpServletRequest req;

	private PortalMenu portalMenuCreate1;
	
	private PortalMenu portalMenuCreate2;
	
	private PortalMenu portalMenuChild1;
	
	private PortalMenu portalMenuChild2;
	
	private PortalMenu portalMenuChild3;
	
	private PortalMenu portalMenuChild4;
	
	private PortalMenu portalMenuChild5;
	
	private PortalMenu portalMenuChild6;
	
	private PortalMenu portalMenuUpdate;
	
	private User userSession;
	
	@Before
	public void setUp() {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
	    user.setUserId("user1");
	    user.setUserName("user1");
	    user.setLocaleCode("ko");
	    
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	    // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		List<LocaleCode> localeCodeList = i18nMessageService.selectLocaleAll();
		
		portalMenuCreate1 = new PortalMenu();
		portalMenuCreate1.setMenuId("1111111111");
		portalMenuCreate1.setMenuName("Create1 PortlaMenu");
		portalMenuCreate1.setDescription("Create1 Description");
		portalMenuCreate1.setParentMenuId(null);
		portalMenuCreate1.setSystemCode("S000001");
		portalMenuCreate1.setMenuType("CATEGORY");
		portalMenuCreate1.setUrl("Create1 Url");
		portalMenuCreate1.setTarget(null);
		portalMenuCreate1.setRegisterId("user1");
		portalMenuCreate1.setRegisterName("user1");
		portalMenuCreate1.setUpdaterId("user1");
		portalMenuCreate1.setUpdaterName("user1");
		portalMenuCreate1.setIconId("iconMenu_01");
		portalMenuCreate1.setUrlType(null);
		
		List<I18nMessage> create1I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Create1 Menu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("menuName");
			i18nMessage.setItemId("1111111111");
			
			create1I18nMessageList.add(i18nMessage);
		}
		
		portalMenuCreate1.setI18nMessageList(create1I18nMessageList);
		
		ACLResourcePermission aclResourcePermissionCreate1 = new ACLResourcePermission();
		aclResourcePermissionCreate1.setUserId("userId");
		aclResourcePermissionCreate1.setUserName("userId");
		
		PortalSecurity createSecurity1 = new PortalSecurity();
		createSecurity1.setClassName("Portal-Menu");
		createSecurity1.setAclResourcePermissionRead(aclResourcePermissionCreate1);
		createSecurity1.setOperationName("READ");
		createSecurity1.setOwnerId("user1");
		createSecurity1.setOwnerName("user1");
		
		portalMenuCreate1.setSecurity(createSecurity1);
		
		portalMenuCreate2 = new PortalMenu();
		portalMenuCreate2.setMenuId("2222222222");
		portalMenuCreate2.setMenuName("Create2 PortlaMenu");
		portalMenuCreate2.setDescription("Create2 Description");
		portalMenuCreate2.setParentMenuId(null);
		portalMenuCreate2.setSystemCode("S000001");
		portalMenuCreate2.setMenuType("CATEGORY");
		portalMenuCreate2.setUrl("Create2 Url");
		portalMenuCreate2.setTarget(null);
		portalMenuCreate2.setRegisterId("user1");
		portalMenuCreate2.setRegisterName("user1");
		portalMenuCreate2.setUpdaterId("user1");
		portalMenuCreate2.setUpdaterName("user1");
		portalMenuCreate2.setIconId("iconMenu_02");
		portalMenuCreate2.setUrlType(null);
		
		List<I18nMessage> create2I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Create2 Menu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("menuName");
			i18nMessage.setItemId("2222222222");
			
			create2I18nMessageList.add(i18nMessage);
		}
		
		portalMenuCreate2.setI18nMessageList(create2I18nMessageList);
		
		ACLResourcePermission aclResourcePermissionCreate2 = new ACLResourcePermission();
		aclResourcePermissionCreate2.setUserId("userId");
		aclResourcePermissionCreate2.setUserName("userId");
		
		PortalSecurity createSecurity2 = new PortalSecurity();
		createSecurity2.setClassName("Portal-Menu");
		createSecurity2.setAclResourcePermissionRead(aclResourcePermissionCreate2);
		createSecurity2.setOperationName("READ");
		createSecurity2.setOwnerId("user1");
		createSecurity2.setOwnerName("user1");
		
		portalMenuCreate2.setSecurity(createSecurity2);
		
		portalMenuChild1 = new PortalMenu();
		portalMenuChild1.setMenuId("3333333333");
		portalMenuChild1.setMenuName("Child1 PortlaMenu");
		portalMenuChild1.setDescription("Child1 Description");
		portalMenuChild1.setParentMenuId("1111111111");
		portalMenuChild1.setSystemCode("S000001");
		portalMenuChild1.setMenuType("ITEM");
		portalMenuChild1.setUrl("Child1 Url");
		portalMenuChild1.setTarget("INNER");
		portalMenuChild1.setRegisterId("user1");
		portalMenuChild1.setRegisterName("user1");
		portalMenuChild1.setUpdaterId("user1");
		portalMenuChild1.setUpdaterName("user1");
		portalMenuChild1.setIconId(null);
		portalMenuChild1.setUrlType("URL");
		
		List<I18nMessage> child1I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child1 Menu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("menuName");
			i18nMessage.setItemId("3333333333");
			
			child1I18nMessageList.add(i18nMessage);
		}
		
		portalMenuChild1.setI18nMessageList(child1I18nMessageList);
		
		ACLResourcePermission aclResourcePermissionChild1 = new ACLResourcePermission();
		aclResourcePermissionChild1.setUserId("userId");
		aclResourcePermissionChild1.setUserName("userId");
		
		PortalSecurity childSecurity1 = new PortalSecurity();
		childSecurity1.setClassName("Portal-Menu");
		childSecurity1.setAclResourcePermissionRead(aclResourcePermissionChild1);
		childSecurity1.setOperationName("READ");
		childSecurity1.setOwnerId("user1");
		childSecurity1.setOwnerName("user1");
		
		portalMenuChild1.setSecurity(childSecurity1);
		
		portalMenuChild2 = new PortalMenu();
		portalMenuChild2.setMenuId("4444444444");
		portalMenuChild2.setMenuName("Child2 PortlaMenu");
		portalMenuChild2.setDescription("Child2 Description");
		portalMenuChild2.setParentMenuId("1111111111");
		portalMenuChild2.setSystemCode("S000001");
		portalMenuChild2.setMenuType("ITEM");
		portalMenuChild2.setUrl("Child2 Url");
		portalMenuChild2.setTarget("INNER");
		portalMenuChild2.setRegisterId("user1");
		portalMenuChild2.setRegisterName("user1");
		portalMenuChild2.setUpdaterId("user1");
		portalMenuChild2.setUpdaterName("user1");
		portalMenuChild2.setIconId(null);
		portalMenuChild2.setUrlType("URL");
		
		List<I18nMessage> child2I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child2 Menu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("menuName");
			i18nMessage.setItemId("4444444444");
			
			child2I18nMessageList.add(i18nMessage);
		}
		
		portalMenuChild2.setI18nMessageList(child2I18nMessageList);
		
		ACLResourcePermission aclResourcePermissionChild2 = new ACLResourcePermission();
		aclResourcePermissionChild2.setUserId("userId");
		aclResourcePermissionChild2.setUserName("userId");
		
		PortalSecurity childSecurity2 = new PortalSecurity();
		childSecurity2.setClassName("Portal-Menu");
		childSecurity2.setAclResourcePermissionRead(aclResourcePermissionChild2);
		childSecurity2.setOperationName("READ");
		childSecurity2.setOwnerId("user1");
		childSecurity2.setOwnerName("user1");
		
		portalMenuChild2.setSecurity(childSecurity2);
		
		portalMenuChild3 = new PortalMenu();
		portalMenuChild3.setMenuId("5555555555");
		portalMenuChild3.setMenuName("Child3 PortlaMenu");
		portalMenuChild3.setDescription("Child3 Description");
		portalMenuChild3.setParentMenuId("1111111111");
		portalMenuChild3.setSystemCode("S000001");
		portalMenuChild3.setMenuType("ITEM");
		portalMenuChild3.setUrl("Child3 Url");
		portalMenuChild3.setTarget("INNER");
		portalMenuChild3.setRegisterId("user1");
		portalMenuChild3.setRegisterName("user1");
		portalMenuChild3.setUpdaterId("user1");
		portalMenuChild3.setUpdaterName("user1");
		portalMenuChild3.setIconId(null);
		portalMenuChild3.setUrlType("URL");
		
		List<I18nMessage> child3I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child3 Menu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("menuName");
			i18nMessage.setItemId("5555555555");
			
			child3I18nMessageList.add(i18nMessage);
		}
		
		portalMenuChild3.setI18nMessageList(child3I18nMessageList);
		
		ACLResourcePermission aclResourcePermissionChild3 = new ACLResourcePermission();
		aclResourcePermissionChild3.setUserId("userId");
		aclResourcePermissionChild3.setUserName("userId");
		
		PortalSecurity childSecurity3 = new PortalSecurity();
		childSecurity3.setClassName("Portal-Menu");
		childSecurity3.setAclResourcePermissionRead(aclResourcePermissionChild3);
		childSecurity3.setOperationName("READ");
		childSecurity3.setOwnerId("user1");
		childSecurity3.setOwnerName("user1");
		
		portalMenuChild3.setSecurity(childSecurity3);
		
		portalMenuChild4 = new PortalMenu();
		portalMenuChild4.setMenuId("6666666666");
		portalMenuChild4.setMenuName("Child4 PortlaMenu");
		portalMenuChild4.setDescription("Child4 Description");
		portalMenuChild4.setParentMenuId("2222222222");
		portalMenuChild4.setSystemCode("S000001");
		portalMenuChild4.setMenuType("ITEM");
		portalMenuChild4.setUrl("Child4 Url");
		portalMenuChild4.setTarget("INNER");
		portalMenuChild4.setRegisterId("user1");
		portalMenuChild4.setRegisterName("user1");
		portalMenuChild4.setUpdaterId("user1");
		portalMenuChild4.setUpdaterName("user1");
		portalMenuChild4.setIconId(null);
		portalMenuChild4.setUrlType("URL");
		
		List<I18nMessage> child4I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child4 Menu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("menuName");
			i18nMessage.setItemId("6666666666");
			
			child4I18nMessageList.add(i18nMessage);
		}
		
		portalMenuChild4.setI18nMessageList(child4I18nMessageList);
		
		ACLResourcePermission aclResourcePermissionChild4 = new ACLResourcePermission();
		aclResourcePermissionChild4.setUserId("userId");
		aclResourcePermissionChild4.setUserName("userId");
		
		PortalSecurity childSecurity4 = new PortalSecurity();
		childSecurity4.setClassName("Portal-Menu");
		childSecurity4.setAclResourcePermissionRead(aclResourcePermissionChild4);
		childSecurity4.setOperationName("READ");
		childSecurity4.setOwnerId("user1");
		childSecurity4.setOwnerName("user1");
		
		portalMenuChild4.setSecurity(childSecurity4);
		
		portalMenuChild5 = new PortalMenu();
		portalMenuChild5.setMenuId("7777777777");
		portalMenuChild5.setMenuName("Child5 PortlaMenu");
		portalMenuChild5.setDescription("Child5 Description");
		portalMenuChild5.setParentMenuId("2222222222");
		portalMenuChild5.setSystemCode("S000001");
		portalMenuChild5.setMenuType("ITEM");
		portalMenuChild5.setUrl("Child5 Url");
		portalMenuChild5.setTarget("INNER");
		portalMenuChild5.setRegisterId("user1");
		portalMenuChild5.setRegisterName("user1");
		portalMenuChild5.setUpdaterId("user1");
		portalMenuChild5.setUpdaterName("user1");
		portalMenuChild5.setIconId(null);
		portalMenuChild5.setUrlType("URL");
		
		List<I18nMessage> child5I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child5 Menu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("menuName");
			i18nMessage.setItemId("7777777777");
			
			child5I18nMessageList.add(i18nMessage);
		}
		
		portalMenuChild5.setI18nMessageList(child5I18nMessageList);
		
		ACLResourcePermission aclResourcePermissionChild5 = new ACLResourcePermission();
		aclResourcePermissionChild5.setUserId("userId");
		aclResourcePermissionChild5.setUserName("userId");
		
		PortalSecurity childSecurity5 = new PortalSecurity();
		childSecurity5.setClassName("Portal-Menu");
		childSecurity5.setAclResourcePermissionRead(aclResourcePermissionChild5);
		childSecurity5.setOperationName("READ");
		childSecurity5.setOwnerId("user1");
		childSecurity5.setOwnerName("user1");
		
		portalMenuChild5.setSecurity(childSecurity5);
		
		portalMenuChild6 = new PortalMenu();
		portalMenuChild6.setMenuId("8888888888");
		portalMenuChild6.setMenuName("Child6 PortlaMenu");
		portalMenuChild6.setDescription("Child6 Description");
		portalMenuChild6.setParentMenuId("2222222222");
		portalMenuChild6.setSystemCode("S000001");
		portalMenuChild6.setMenuType("ITEM");
		portalMenuChild6.setUrl("Child6 Url");
		portalMenuChild6.setTarget("INNER");
		portalMenuChild6.setRegisterId("user1");
		portalMenuChild6.setRegisterName("user1");
		portalMenuChild6.setUpdaterId("user1");
		portalMenuChild6.setUpdaterName("user1");
		portalMenuChild6.setIconId(null);
		portalMenuChild6.setUrlType("URL");
		
		List<I18nMessage> child6I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child6 Menu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("menuName");
			i18nMessage.setItemId("8888888888");
			
			child6I18nMessageList.add(i18nMessage);
		}
		
		portalMenuChild6.setI18nMessageList(child6I18nMessageList);
		
		ACLResourcePermission aclResourcePermissionChild6 = new ACLResourcePermission();
		aclResourcePermissionChild5.setUserId("userId");
		aclResourcePermissionChild5.setUserName("userId");
		
		PortalSecurity childSecurity6 = new PortalSecurity();
		childSecurity6.setClassName("Portal-Menu");
		childSecurity6.setAclResourcePermissionRead(aclResourcePermissionChild6);
		childSecurity6.setOperationName("READ");
		childSecurity6.setOwnerId("user1");
		childSecurity6.setOwnerName("user1");
		
		portalMenuChild6.setSecurity(childSecurity6);
		
		portalMenuUpdate = new PortalMenu();
		portalMenuUpdate.setMenuId("1111111111");
		portalMenuUpdate.setMenuName("Update1 PortlaMenu");
		portalMenuUpdate.setDescription("Update1 Description");
		portalMenuUpdate.setParentMenuId(null);
		portalMenuUpdate.setSystemCode("S000001");
		portalMenuUpdate.setMenuType("CATEGORY");
		portalMenuUpdate.setUrl("Update1 Url");
		portalMenuUpdate.setTarget(null);
		portalMenuUpdate.setRegisterId("user1");
		portalMenuUpdate.setRegisterName("user1");
		portalMenuUpdate.setUpdaterId("user1");
		portalMenuUpdate.setUpdaterName("user1");
		portalMenuUpdate.setIconId("iconMenu_01");
		portalMenuUpdate.setUrlType(null);
		
		ACLResourcePermission aclResourcePermissionUpdate = new ACLResourcePermission();
		aclResourcePermissionUpdate.setUserId("userId");
		aclResourcePermissionUpdate.setUserName("userId");
		
		PortalSecurity updateSecurity = new PortalSecurity();
		updateSecurity.setClassName("Portal-Menu");
		updateSecurity.setAclResourcePermissionRead(aclResourcePermissionUpdate);
		updateSecurity.setOperationName("READ");
		updateSecurity.setOwnerId("user1");
		updateSecurity.setOwnerName("user1");
		
		portalMenuUpdate.setSecurity(updateSecurity);
		
		userSession = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService#list(java.util.Map)}.
	 */
	@Test
	public void testList() {
		
		portalMenuService.create(portalMenuCreate1);
		portalMenuService.create(portalMenuChild1);
		portalMenuService.create(portalMenuChild2);
		portalMenuService.create(portalMenuChild3);
		
		portalMenuService.create(portalMenuCreate2);
		portalMenuService.create(portalMenuChild4);
		portalMenuService.create(portalMenuChild5);
		portalMenuService.create(portalMenuChild6);
			
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", "P000001");
		param.put("fieldName", "menuName");
		param.put("localeCode", userSession.getLocaleCode());	
		
		assertNotNull(portalMenuService.list(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService#menuList(java.util.Map)}.
	 */
	@Test
	public void testMenuList() {
		
		portalMenuService.create(portalMenuCreate1);
		portalMenuService.create(portalMenuChild1);
		portalMenuService.create(portalMenuChild2);
		portalMenuService.create(portalMenuChild3);
		
		portalMenuService.create(portalMenuCreate2);
		portalMenuService.create(portalMenuChild4);
		portalMenuService.create(portalMenuChild5);
		portalMenuService.create(portalMenuChild6);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", "P000001");
		param.put("operationName", "READ");
		param.put("className", "Portal-Menu");
		param.put("userId", userSession.getUserId());
		param.put("fieldName", "menuName");
		param.put("localeCode", userSession.getLocaleCode());
		
		assertNotNull(portalMenuService.menuList(param));
		
	}
	
	@Test
	public void testRead() {
	
		portalMenuService.create(portalMenuCreate1);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "menuName");
		param.put("localeCode", userSession.getLocaleCode());
		param.put("menuId", portalMenuCreate1.getMenuId());
		
		assertNotNull(portalMenuService.read(param));
		
	}
	
	@Test
	public void testCreate() {
	
		portalMenuService.create(portalMenuCreate1);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "menuName");
		param.put("localeCode", userSession.getLocaleCode());
		param.put("menuId", portalMenuCreate1.getMenuId());
		
		assertNotNull(portalMenuService.read(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService#update(com.lgcns.ikep4.portal.admin.screen.model.PortalMenu)}.
	 */
	@Test
	public void testUpdate() {
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "menuName");
		param.put("localeCode", userSession.getLocaleCode());
		param.put("menuId", portalMenuCreate1.getMenuId());
		
		portalMenuService.create(portalMenuCreate1);
		
		PortalMenu createPortalMenu = new PortalMenu();
		createPortalMenu = portalMenuService.read(param);	
		
		portalMenuUpdate.setSortOrder(createPortalMenu.getSortOrder());
		
		List<I18nMessage> updateI18nMessageList = i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, createPortalMenu.getMenuId(), "menuName");
		
		for(I18nMessage i18nMessage : updateI18nMessageList) {
			i18nMessage.setItemMessage("Update Menu("+i18nMessage.getLocaleCode()+")");
		}
		
		portalMenuUpdate.setI18nMessageList(updateI18nMessageList);
		
		portalMenuService.update(portalMenuUpdate);
		
		PortalMenu tempPortalMenu = new PortalMenu();
		tempPortalMenu = portalMenuService.read(param);
		
		assertNotSame(createPortalMenu, tempPortalMenu);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		
		portalMenuService.create(portalMenuCreate1);
		portalMenuService.delete(portalMenuCreate1.getMenuId());
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "menuName");
		param.put("localeCode", userSession.getLocaleCode());
		param.put("menuId", portalMenuCreate1.getMenuId());
		
		assertNull(portalMenuService.read(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService#moveNextPortalMenu(int, int, java.util.Map)}.
	 */
	@Test
	public void testMoveNextPortalMenu() {
		
		portalMenuService.create(portalMenuCreate1);
		portalMenuService.create(portalMenuChild1);
		portalMenuService.create(portalMenuChild2);
		portalMenuService.create(portalMenuChild3);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", "P000001");
		param.put("updaterId", userSession.getUserId());
		param.put("updaterName", userSession.getUserName());
		param.put("parentMenuId", portalMenuCreate1.getMenuId());
		
		portalMenuService.moveNextPortalMenu(0, 2, param);
		
		Map<String,String> readParam1 = new HashMap<String, String>();
		readParam1.put("fieldName", "menuName");
		readParam1.put("localeCode", userSession.getLocaleCode());
		readParam1.put("menuId", portalMenuChild1.getMenuId());
		
		PortalMenu child1PortalMenu = new PortalMenu();
		child1PortalMenu = portalMenuService.read(readParam1);
		
		Map<String,String> readParam2 = new HashMap<String, String>();
		readParam2.put("fieldName", "menuName");
		readParam2.put("localeCode", userSession.getLocaleCode());
		readParam2.put("menuId", portalMenuChild3.getMenuId());
		
		PortalMenu tempPortalMenu = new PortalMenu();
		tempPortalMenu = portalMenuService.read(readParam2);
		
		assertTrue(child1PortalMenu.getSortOrder().compareTo(tempPortalMenu.getSortOrder()) >= 0);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService#movePrevPortalMenu(int, int, java.util.Map)}.
	 */
	@Test
	public void testMovePrevPortalMenu() {
		
		portalMenuService.create(portalMenuCreate2);
		portalMenuService.create(portalMenuChild4);
		portalMenuService.create(portalMenuChild5);
		portalMenuService.create(portalMenuChild6);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", "P000001");
		param.put("updaterId", userSession.getUserId());
		param.put("updaterName", userSession.getUserName());
		param.put("parentMenuId", portalMenuCreate2.getMenuId());
		
		portalMenuService.movePrevPortalMenu(2, 0, param);
		
		Map<String,String> readParam1 = new HashMap<String, String>();
		readParam1.put("fieldName", "menuName");
		readParam1.put("localeCode", userSession.getLocaleCode());
		readParam1.put("menuId", portalMenuChild6.getMenuId());
		
		PortalMenu child6PortalMenu = new PortalMenu();
		child6PortalMenu = portalMenuService.read(readParam1);
		
		Map<String,String> readParam2 = new HashMap<String, String>();
		readParam2.put("fieldName", "menuName");
		readParam2.put("localeCode", userSession.getLocaleCode());
		readParam2.put("menuId", portalMenuChild4.getMenuId());
		
		PortalMenu tempPortalMenu = new PortalMenu();
		tempPortalMenu = portalMenuService.read(readParam2);
		
		assertTrue(child6PortalMenu.getSortOrder().compareTo(tempPortalMenu.getSortOrder()) <= 0);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalMenuService#moveOtherPortalMenu(int, java.util.Map)}.
	 */
	@Test
	public void testMoveOtherPortalMenu() {
		
		portalMenuService.create(portalMenuCreate1);
		portalMenuService.create(portalMenuChild1);
		portalMenuService.create(portalMenuChild2);
		portalMenuService.create(portalMenuChild3);
		
		portalMenuService.create(portalMenuCreate2);
		portalMenuService.create(portalMenuChild4);
		portalMenuService.create(portalMenuChild5);
		portalMenuService.create(portalMenuChild6);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("systemName", "Portal");
		param.put("portalId", "P000001");
		param.put("updaterId", userSession.getUserId());
		param.put("updaterName", userSession.getUserName());
		param.put("parentMenuId", portalMenuCreate2.getMenuId());
		param.put("menuId", portalMenuChild1.getMenuId());
		
		portalMenuService.moveOtherPortalMenu(3, param);
		
		Map<String,String> readParam1 = new HashMap<String, String>();
		readParam1.put("fieldName", "menuName");
		readParam1.put("localeCode", userSession.getLocaleCode());
		readParam1.put("menuId", portalMenuChild1.getMenuId());
		
		PortalMenu child1PortalMenu = new PortalMenu();
		child1PortalMenu = portalMenuService.read(readParam1);
		
		Map<String,String> readParam2 = new HashMap<String, String>();
		readParam2.put("fieldName", "menuName");
		readParam2.put("localeCode", userSession.getLocaleCode());
		readParam2.put("menuId", portalMenuChild6.getMenuId());
		
		PortalMenu tempPortalMenu = new PortalMenu();
		tempPortalMenu = portalMenuService.read(readParam2);
		
		assertTrue(child1PortalMenu.getSortOrder().compareTo(tempPortalMenu.getSortOrder()) >= 0);
		
	}
	
}