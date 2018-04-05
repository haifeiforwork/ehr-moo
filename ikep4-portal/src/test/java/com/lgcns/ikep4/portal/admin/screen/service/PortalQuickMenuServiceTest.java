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
import com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * PortalQuickMenuService 테스트 케이스
 *
 * @author 박철종
 * @version $Id: PortalQuickMenuServiceTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalQuickMenuServiceTest extends BaseServiceTestCase {
	
	@Autowired
	private PortalQuickMenuService portalQuickMenuService;
	
	@Autowired
	private I18nMessageService i18nMessageService;

    private MockHttpServletRequest req;
    
    private List<I18nMessage> create1I18nMessageList;
    
    private List<I18nMessage> create2I18nMessageList;
    
    private List<I18nMessage> create3I18nMessageList;
    
    private List<I18nMessage> updateI18nMessageList;

	private PortalQuickMenu portalQuickMenuCreate1;
	
	private PortalQuickMenu portalQuickMenuCreate2;

	private PortalQuickMenu portalQuickMenuCreate3;
	
	private PortalQuickMenu portalQuickMenuUpdate;
	
	private PortalQuickMenu portalUserQuickMenuCreate1;

	private PortalQuickMenu portalUserQuickMenuCreate2;
	
	private PortalQuickMenu portalUserQuickMenuCreate3;
	
	private ACLResourcePermission aclResourcePermissionCreate1;
	
	private ACLResourcePermission aclResourcePermissionCreate2;
	
	private ACLResourcePermission aclResourcePermissionCreate3;
	
	private ACLResourcePermission aclResourcePermissionUpdate;
	
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
		
		portalQuickMenuCreate1 = new PortalQuickMenu();
		portalQuickMenuCreate1.setQuickMenuId("1111111111");
		portalQuickMenuCreate1.setQuickMenuName("Create1 QuickMenu");
		portalQuickMenuCreate1.setMoreUrl("Create1 MoreUrl");
		portalQuickMenuCreate1.setTarget("INNER");
		portalQuickMenuCreate1.setCount(0);
		portalQuickMenuCreate1.setCountUrl("Create1 CountUrl");
		portalQuickMenuCreate1.setRegisterId("user1");
		portalQuickMenuCreate1.setRegisterName("user1");
		portalQuickMenuCreate1.setUpdaterId("user1");
		portalQuickMenuCreate1.setUpdaterName("user1");
		portalQuickMenuCreate1.setPortalId("P000001");
		portalQuickMenuCreate1.setIconId("quick_icon");
		portalQuickMenuCreate1.setIntervalTime(0);
		portalQuickMenuCreate1.setMoreUrlType("URL");
		
		create1I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Create1 QuickMenu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("quickMenuName");
			i18nMessage.setItemId("1111111111");
			
			create1I18nMessageList.add(i18nMessage);
		}
		
		portalQuickMenuCreate1.setI18nMessageList(create1I18nMessageList);
		
		aclResourcePermissionCreate1 = new ACLResourcePermission();
		aclResourcePermissionCreate1.setUserId("userId");
		aclResourcePermissionCreate1.setUserName("userId");
		
		PortalSecurity createSecurity1 = new PortalSecurity();
		createSecurity1.setClassName("Portal-QuickMenu");
		createSecurity1.setAclResourcePermissionRead(aclResourcePermissionCreate1);
		createSecurity1.setOperationName("READ");
		createSecurity1.setOwnerId("user1");
		createSecurity1.setOwnerName("user1");
		
		portalQuickMenuCreate1.setSecurity(createSecurity1);
		
		portalQuickMenuCreate2 = new PortalQuickMenu();
		portalQuickMenuCreate2.setQuickMenuId("2222222222");
		portalQuickMenuCreate2.setQuickMenuName("Create2 QuickMenu");
		portalQuickMenuCreate2.setMoreUrl("Create2 MoreUrl");
		portalQuickMenuCreate2.setTarget("INNER");
		portalQuickMenuCreate2.setCount(0);
		portalQuickMenuCreate2.setCountUrl("Create2 CountUrl");
		portalQuickMenuCreate2.setRegisterId("user1");
		portalQuickMenuCreate2.setRegisterName("user1");
		portalQuickMenuCreate2.setUpdaterId("user1");
		portalQuickMenuCreate2.setUpdaterName("user1");
		portalQuickMenuCreate2.setPortalId("P000001");
		portalQuickMenuCreate2.setIconId("quick_icon");
		portalQuickMenuCreate2.setIntervalTime(0);
		portalQuickMenuCreate2.setMoreUrlType("URL");
		
		create2I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Create2 QuickMenu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("quickMenuName");
			i18nMessage.setItemId("2222222222");
			
			create2I18nMessageList.add(i18nMessage);
		}
		
		portalQuickMenuCreate2.setI18nMessageList(create2I18nMessageList);
		
		aclResourcePermissionCreate2 = new ACLResourcePermission();
		aclResourcePermissionCreate2.setUserId("userId");
		aclResourcePermissionCreate2.setUserName("userId");
		
		PortalSecurity createSecurity2 = new PortalSecurity();
		createSecurity2.setClassName("Portal-QuickMenu");
		createSecurity2.setAclResourcePermissionRead(aclResourcePermissionCreate2);
		createSecurity2.setOperationName("READ");
		createSecurity2.setOwnerId("user1");
		createSecurity2.setOwnerName("user1");
		
		portalQuickMenuCreate2.setSecurity(createSecurity2);
		
		portalQuickMenuCreate3 = new PortalQuickMenu();
		portalQuickMenuCreate3.setQuickMenuId("3333333333");
		portalQuickMenuCreate3.setQuickMenuName("Create3 QuickMenu");
		portalQuickMenuCreate3.setMoreUrl("Create3 MoreUrl");
		portalQuickMenuCreate3.setTarget("INNER");
		portalQuickMenuCreate3.setCount(0);
		portalQuickMenuCreate3.setCountUrl("Create3 CountUrl");
		portalQuickMenuCreate3.setRegisterId("user1");
		portalQuickMenuCreate3.setRegisterName("user1");
		portalQuickMenuCreate3.setUpdaterId("user1");
		portalQuickMenuCreate3.setUpdaterName("user1");
		portalQuickMenuCreate3.setPortalId("P000001");
		portalQuickMenuCreate3.setIconId("quick_icon");
		portalQuickMenuCreate3.setIntervalTime(0);
		portalQuickMenuCreate3.setMoreUrlType("URL");
		
		create3I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Create3 QuickMenu("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("quickMenuName");
			i18nMessage.setItemId("3333333333");
			
			create3I18nMessageList.add(i18nMessage);
		}
		
		portalQuickMenuCreate3.setI18nMessageList(create3I18nMessageList);
		
		aclResourcePermissionCreate3 = new ACLResourcePermission();
		aclResourcePermissionCreate3.setUserId("userId");
		aclResourcePermissionCreate3.setUserName("userId");
		
		PortalSecurity createSecurity3 = new PortalSecurity();
		createSecurity3.setClassName("Portal-QuickMenu");
		createSecurity3.setAclResourcePermissionRead(aclResourcePermissionCreate3);
		createSecurity3.setOperationName("READ");
		createSecurity3.setOwnerId("user1");
		createSecurity3.setOwnerName("user1");
		
		portalQuickMenuCreate3.setSecurity(createSecurity3);
		
		portalQuickMenuUpdate = new PortalQuickMenu();
		portalQuickMenuUpdate.setQuickMenuId("1111111111");
		portalQuickMenuUpdate.setQuickMenuName("Update QuickMenu");
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
		
		aclResourcePermissionUpdate = new ACLResourcePermission();
		aclResourcePermissionUpdate.setUserId("userId");
		aclResourcePermissionUpdate.setUserName("userId");
		
		PortalSecurity updateSecurity = new PortalSecurity();
		updateSecurity.setClassName("Portal-QuickMenu");
		updateSecurity.setAclResourcePermissionRead(aclResourcePermissionUpdate);
		updateSecurity.setOperationName("READ");
		updateSecurity.setOwnerId("user1");
		updateSecurity.setOwnerName("user1");
		
		portalQuickMenuUpdate.setSecurity(updateSecurity);
		
		portalUserQuickMenuCreate1 = new PortalQuickMenu();
		portalUserQuickMenuCreate1.setQuickMenuId("1111111111");
		portalUserQuickMenuCreate1.setUserId("user1");
		portalUserQuickMenuCreate1.setSortOrder("0000000000001");
		portalUserQuickMenuCreate1.setRegisterId("user1");
		portalUserQuickMenuCreate1.setRegisterName("user1");
		portalUserQuickMenuCreate1.setUpdaterId("user1");
		portalUserQuickMenuCreate1.setUpdaterName("user1");
		
		portalUserQuickMenuCreate2 = new PortalQuickMenu();
		portalUserQuickMenuCreate2.setQuickMenuId("2222222222");
		portalUserQuickMenuCreate2.setUserId("user1");
		portalUserQuickMenuCreate2.setSortOrder("0000000000002");
		portalUserQuickMenuCreate2.setRegisterId("user1");
		portalUserQuickMenuCreate2.setRegisterName("user1");
		portalUserQuickMenuCreate2.setUpdaterId("user1");
		portalUserQuickMenuCreate2.setUpdaterName("user1");
		
		portalUserQuickMenuCreate3 = new PortalQuickMenu();
		portalUserQuickMenuCreate3.setQuickMenuId("3333333333");
		portalUserQuickMenuCreate3.setUserId("user1");
		portalUserQuickMenuCreate3.setSortOrder("0000000000003");
		portalUserQuickMenuCreate3.setRegisterId("user1");
		portalUserQuickMenuCreate3.setRegisterName("user1");
		portalUserQuickMenuCreate3.setUpdaterId("user1");
		portalUserQuickMenuCreate3.setUpdaterName("user1");
		
		userSession = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#list(java.util.Map)}.
	 */
	@Test
	public void testList() { 
	
		portalQuickMenuService.create(portalQuickMenuCreate1);
		portalQuickMenuService.create(portalQuickMenuCreate2);
		portalQuickMenuService.create(portalQuickMenuCreate3);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", "P000001");
		param.put("fieldName", "quickMenuName");
		param.put("localeCode", userSession.getLocaleCode());	
		
		assertNotNull(portalQuickMenuService.list(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#quickMenuList(java.util.Map)}.
	 */
	@Test
	public void testQuickMenuList() {
	
		portalQuickMenuService.create(portalQuickMenuCreate1);
		portalQuickMenuService.create(portalQuickMenuCreate2);
		portalQuickMenuService.create(portalQuickMenuCreate3);
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", "P000001");
		param.put("fieldName", "quickMenuName");
		param.put("localeCode", userSession.getLocaleCode());
		
		assertNotNull(portalQuickMenuService.quickMenuList(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#quickMenuListByUserId(java.util.Map)}.
	 */
	@Test
	public void testQuickMenuListByUserId() {
		
		Map<String, String> quickMenuParam = new HashMap<String, String>();
		quickMenuParam.put("portalId", "P000001");
		quickMenuParam.put("operationName", "READ");
		quickMenuParam.put("className", "Portal-QuickMenu");
		quickMenuParam.put("userId", "user1");
		quickMenuParam.put("fieldName", "menuName");
		quickMenuParam.put("localeCode", "ko");
		
		portalQuickMenuService.create(portalQuickMenuCreate1);
		portalQuickMenuService.create(portalQuickMenuCreate2);
		portalQuickMenuService.create(portalQuickMenuCreate3);
		
		String[] ids = {portalQuickMenuCreate1.getQuickMenuId(), portalQuickMenuCreate2.getQuickMenuId(), portalQuickMenuCreate3.getQuickMenuId()};
		
		portalQuickMenuService.setUserQuickMenu(ids, "user1", "user1");
		
		assertNotNull(portalQuickMenuService.quickMenuListByUserId(quickMenuParam));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#read(java.util.Map)}.
	 */
	@Test
	public void testRead() {
		
		portalQuickMenuService.create(portalQuickMenuCreate1);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "quickMenuName");
		param.put("localeCode", userSession.getLocaleCode());
		param.put("quickMenuId", portalQuickMenuCreate1.getQuickMenuId());
		
		assertNotNull(portalQuickMenuService.read(param));
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#create(com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu)}.
	 */
	@Test
	public void testCreate() {
		
		portalQuickMenuService.create(portalQuickMenuCreate1);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "quickMenuName");
		param.put("localeCode", userSession.getLocaleCode());
		param.put("quickMenuId", portalQuickMenuCreate1.getQuickMenuId());
		
		assertNotNull(portalQuickMenuService.read(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#update(com.lgcns.ikep4.portal.admin.screen.model.PortalQuickMenu)}.
	 */
	@Test
	public void testUpdate() {
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "quickMenuName");
		param.put("localeCode", userSession.getLocaleCode());
		param.put("quickMenuId", portalQuickMenuCreate1.getQuickMenuId());
		
		portalQuickMenuService.create(portalQuickMenuCreate1);
		
		PortalQuickMenu createPortalQuickMenu = new PortalQuickMenu();
		createPortalQuickMenu = portalQuickMenuService.read(param);	
		
		portalQuickMenuUpdate.setSortOrder(createPortalQuickMenu.getSortOrder());
		
		updateI18nMessageList = i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, createPortalQuickMenu.getQuickMenuId(), "quickMenuName");
		
		for(I18nMessage i18nMessage : updateI18nMessageList) {
			i18nMessage.setItemMessage("Update QuickMenu("+i18nMessage.getLocaleCode()+")");
		}
		
		portalQuickMenuUpdate.setI18nMessageList(updateI18nMessageList);
		portalQuickMenuService.update(portalQuickMenuUpdate);
		
		PortalQuickMenu tempPortalQuickMenu = new PortalQuickMenu();
		tempPortalQuickMenu = portalQuickMenuService.read(param);
		
		assertNotSame(createPortalQuickMenu, tempPortalQuickMenu);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		
		portalQuickMenuService.create(portalQuickMenuCreate1);
		portalQuickMenuService.delete(portalQuickMenuCreate1.getQuickMenuId());
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("fieldName", "quickMenuName");
		param.put("localeCode", userSession.getLocaleCode());
		param.put("quickMenuId", portalQuickMenuCreate1.getQuickMenuId());
		
		assertNull(portalQuickMenuService.read(param));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#moveNextPortalQuickMenu(int, java.util.Map)}.
	 */
	@Test
	public void testMoveNextPortalQuickMenu() {
		
		portalQuickMenuService.create(portalQuickMenuCreate1);
		portalQuickMenuService.create(portalQuickMenuCreate2);
		portalQuickMenuService.create(portalQuickMenuCreate3);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", "P000001");
		map.put("fieldName", "quickMenuName");
		map.put("localeCode", userSession.getLocaleCode());
		
		List<PortalQuickMenu> list = portalQuickMenuService.list(map);
		
		int startCount = 0;
		String creat3SortOrder = "";
		
		for(int i=0; i<list.size(); i++) {
			PortalQuickMenu portalQuickMenu = new PortalQuickMenu();
			portalQuickMenu = (PortalQuickMenu) list.get(i);
			
			if(portalQuickMenuCreate1.getQuickMenuId().equals(portalQuickMenu.getQuickMenuId())) {
				startCount = i;
			}
			
			if(portalQuickMenuCreate3.getQuickMenuId().equals(portalQuickMenu.getQuickMenuId())) {
				creat3SortOrder = portalQuickMenu.getSortOrder();
			}
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", "P000001");
		param.put("updaterId", userSession.getUserId());
		param.put("updaterName", userSession.getUserName());
		
		//startCount번째 퀵메뉴가 startCount+2번째 퀵메뉴 자리로 이동시
		portalQuickMenuService.moveNextPortalQuickMenu(startCount, startCount+2, param);
		
		Map<String,String> readParam = new HashMap<String, String>();
		readParam.put("fieldName", "quickMenuName");
		readParam.put("localeCode", userSession.getLocaleCode());
		readParam.put("quickMenuId", portalQuickMenuCreate1.getQuickMenuId());
		
		PortalQuickMenu tempPortalQuickMenu = new PortalQuickMenu();
		tempPortalQuickMenu = portalQuickMenuService.read(readParam);
		
		assertTrue(tempPortalQuickMenu.getSortOrder().compareTo(creat3SortOrder) >= 0);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#movePrevPortalQuickMenu(int, java.util.Map)}.
	 */
	@Test
	public void testMovePrevPortalQuickMenu() {
		
		portalQuickMenuService.create(portalQuickMenuCreate1);
		portalQuickMenuService.create(portalQuickMenuCreate2);
		portalQuickMenuService.create(portalQuickMenuCreate3);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", "P000001");
		map.put("fieldName", "quickMenuName");
		map.put("localeCode", userSession.getLocaleCode());
		
		List<PortalQuickMenu> list = portalQuickMenuService.list(map);
		
		int startCount = 0;
		String creat1SortOrder = "";
		
		for(int i=0; i<list.size(); i++) {
			PortalQuickMenu portalQuickMenu = new PortalQuickMenu();
			portalQuickMenu = (PortalQuickMenu) list.get(i);
			
			if(portalQuickMenuCreate3.getQuickMenuId().equals(portalQuickMenu.getQuickMenuId())) {
				startCount = i;
			}
			
			if(portalQuickMenuCreate1.getQuickMenuId().equals(portalQuickMenu.getQuickMenuId())) {
				creat1SortOrder = portalQuickMenu.getSortOrder();
			}
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("portalId", "P000001");
		param.put("updaterId", userSession.getUserId());
		param.put("updaterName", userSession.getUserName());
		
		//startCount번째 퀵메뉴가 startCount-2번째 퀵메뉴 자리로 이동시
		portalQuickMenuService.movePrevPortalQuickMenu(startCount, startCount-2, param);
		
		Map<String,String> readParam = new HashMap<String, String>();
		readParam.put("fieldName", "quickMenuName");
		readParam.put("localeCode", userSession.getLocaleCode());
		readParam.put("quickMenuId", portalQuickMenuCreate3.getQuickMenuId());
		
		PortalQuickMenu tempPortalQuickMenu = new PortalQuickMenu();
		tempPortalQuickMenu = portalQuickMenuService.read(readParam);
		
		assertTrue(tempPortalQuickMenu.getSortOrder().compareTo(creat1SortOrder) <= 0);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalQuickMenuService#setUserQuickMenu(java.lang.String[], java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetUserQuickMenu() {
		
		Map<String, String> quickMenuParam = new HashMap<String, String>();
		quickMenuParam.put("portalId", "P000001");
		quickMenuParam.put("operationName", "READ");
		quickMenuParam.put("className", "Portal-QuickMenu");
		quickMenuParam.put("userId", "user1");
		quickMenuParam.put("fieldName", "menuName");
		quickMenuParam.put("localeCode", "ko");
		
		portalQuickMenuService.create(portalQuickMenuCreate1);
		portalQuickMenuService.create(portalQuickMenuCreate2);
		portalQuickMenuService.create(portalQuickMenuCreate3);
		
		String[] ids = {portalQuickMenuCreate1.getQuickMenuId(), portalQuickMenuCreate2.getQuickMenuId(), portalQuickMenuCreate3.getQuickMenuId()};
		
		portalQuickMenuService.setUserQuickMenu(ids, "user1", "user1");
		
		assertNotNull(portalQuickMenuService.quickMenuListByUserId(quickMenuParam));
		
	}
}