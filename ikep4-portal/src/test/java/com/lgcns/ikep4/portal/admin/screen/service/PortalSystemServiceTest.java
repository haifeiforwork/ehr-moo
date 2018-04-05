/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertEquals;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.model.LocaleCode;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * PortalSystemService 테스트 케이스
 *
 * @author 박철종
 * @version $Id: PortalSystemServiceTest.java 17156 2012-01-09 06:56:28Z yu_hs $
 */
public class PortalSystemServiceTest extends BaseServiceTestCase {
	
	@Autowired
	private PortalSystemService portalSystemService;
	
	@Autowired
	private I18nMessageService i18nMessageService;

    private MockHttpServletRequest req;
    
    private List<I18nMessage> createI18nMessageList;
    
    private List<I18nMessage> child1I18nMessageList;
    
    private List<I18nMessage> child2I18nMessageList;
    
    private List<I18nMessage> child3I18nMessageList;
    
    private List<I18nMessage> updateI18nMessageList;

	private PortalSystem portalSystemCreate;
	
	private PortalSystem portalSystemChild1;

	private PortalSystem portalSystemChild2;
	
	private PortalSystem portalSystemChild3;
	
	private PortalSystem portalSystemUpdate;
	
	private ACLResourcePermission aclResourcePermissionCreate;
	
	private ACLResourcePermission aclResourcePermissionChild1;
	
	private ACLResourcePermission aclResourcePermissionChild2;
	
	private ACLResourcePermission aclResourcePermissionChild3;
	
	private ACLResourcePermission aclResourcePermissionUpdate;
	
	private Map<String,String> readParam;
	
	private Map<String,String> child1Param;
	
	private Map<String,String> child2Param;
	
	private Map<String,String> child3Param;
	
	private Map<String, String> moveUpParam;
	
	private Map<String, String> moveDownParam;
	
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
		
		List<LocaleCode> localeCodeList = i18nMessageService.selectLocaleAll();
		
		portalSystemCreate = new PortalSystem();
		portalSystemCreate.setSystemCode("1234567890");
		portalSystemCreate.setPortalId("P000001");
		portalSystemCreate.setSystemName("Create PortalSystem");
		portalSystemCreate.setDescription("Create Description");
		portalSystemCreate.setParentSystemCode(null);
		portalSystemCreate.setSystemType("ITEM");
		portalSystemCreate.setMainView(1);
		portalSystemCreate.setUrl("Create Url");
		portalSystemCreate.setTarget("INNER");
		portalSystemCreate.setOwnerId("user1");
		portalSystemCreate.setRegisterId("user1");
		portalSystemCreate.setRegisterName("user1");
		portalSystemCreate.setUpdaterId("user1");
		portalSystemCreate.setUpdaterName("user1");
		
		createI18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Create PortalSystem("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("systemName");
			i18nMessage.setMessageId("1234567890");
			
			createI18nMessageList.add(i18nMessage);
		}
		
		portalSystemCreate.setI18nMessageList(createI18nMessageList);
		
		aclResourcePermissionCreate = new ACLResourcePermission();
		aclResourcePermissionCreate.setUserId("userId");
		aclResourcePermissionCreate.setUserName("userId");
		
		PortalSecurity createSecurity = new PortalSecurity();
		createSecurity.setClassName("Portal-System");
		createSecurity.setAclResourcePermissionRead(aclResourcePermissionCreate);
		createSecurity.setOperationName("READ");
		createSecurity.setOwnerId("user1");
		createSecurity.setOwnerName("user1");
		
		portalSystemCreate.setSecurity(createSecurity);
		
		portalSystemChild1 = new PortalSystem();
		portalSystemChild1.setSystemCode("1111111111");
		portalSystemChild1.setPortalId("P000001");
		portalSystemChild1.setSystemName("Child1 PortalSystem");
		portalSystemChild1.setDescription("Child1 Description");
		portalSystemChild1.setParentSystemCode("1234567890");
		portalSystemChild1.setSystemType("ITEM");
		portalSystemChild1.setMainView(1);
		portalSystemChild1.setUrl("Child1 Url");
		portalSystemChild1.setTarget("INNER");
		portalSystemChild1.setOwnerId("user1");
		portalSystemChild1.setRegisterId("user1");
		portalSystemChild1.setRegisterName("user1");
		portalSystemChild1.setUpdaterId("user1");
		portalSystemChild1.setUpdaterName("user1");
		
		child1I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child1 PortalSystem("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("systemName");
			i18nMessage.setItemId("1111111111");
			
			child1I18nMessageList.add(i18nMessage);
		}
		
		portalSystemChild1.setI18nMessageList(child1I18nMessageList);
		
		aclResourcePermissionChild1 = new ACLResourcePermission();
		aclResourcePermissionChild1.setUserId("userId");
		aclResourcePermissionChild1.setUserName("userId");
		
		PortalSecurity child1Security = new PortalSecurity();
		child1Security.setClassName("Portal-System");
		child1Security.setAclResourcePermissionRead(aclResourcePermissionChild1);
		child1Security.setOperationName("READ");
		child1Security.setOwnerId("user1");
		child1Security.setOwnerName("user1");
		
		portalSystemChild1.setSecurity(child1Security);
		
		portalSystemChild2 = new PortalSystem();
		portalSystemChild2.setSystemCode("2222222222");
		portalSystemChild2.setPortalId("P000001");
		portalSystemChild2.setSystemName("Child2 PortalSystem");
		portalSystemChild2.setDescription("Child2 Description");
		portalSystemChild2.setParentSystemCode("1234567890");
		portalSystemChild2.setSystemType("ITEM");
		portalSystemChild2.setMainView(1);
		portalSystemChild2.setUrl("Child2 Url");
		portalSystemChild2.setTarget("INNER");
		portalSystemChild2.setOwnerId("user1");
		portalSystemChild2.setRegisterId("user1");
		portalSystemChild2.setRegisterName("user1");
		portalSystemChild2.setUpdaterId("user1");
		portalSystemChild2.setUpdaterName("user1");
		
		child2I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child2 PortalSystem("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("systemName");
			i18nMessage.setItemId("2222222222");
			
			child2I18nMessageList.add(i18nMessage);
		}
		
		portalSystemChild2.setI18nMessageList(child2I18nMessageList);
		
		aclResourcePermissionChild2 = new ACLResourcePermission();
		aclResourcePermissionChild2.setUserId("userId");
		aclResourcePermissionChild2.setUserName("userId");
		
		PortalSecurity child2Security = new PortalSecurity();
		child2Security.setClassName("Portal-System");
		child2Security.setAclResourcePermissionRead(aclResourcePermissionChild2);
		child2Security.setOperationName("READ");
		child2Security.setOwnerId("user1");
		child2Security.setOwnerName("user1");
		
		portalSystemChild2.setSecurity(child2Security);
		
		portalSystemChild3 = new PortalSystem();
		portalSystemChild3.setSystemCode("3333333333");
		portalSystemChild3.setPortalId("P000001");
		portalSystemChild3.setSystemName("Child3 PortalSystem");
		portalSystemChild3.setDescription("Child3 Description");
		portalSystemChild3.setParentSystemCode("1234567890");
		portalSystemChild3.setSystemType("ITEM");
		portalSystemChild3.setMainView(1);
		portalSystemChild3.setUrl("Child3 Url");
		portalSystemChild3.setTarget("INNER");
		portalSystemChild3.setOwnerId("user1");
		portalSystemChild3.setRegisterId("user1");
		portalSystemChild3.setRegisterName("user1");
		portalSystemChild3.setUpdaterId("user1");
		portalSystemChild3.setUpdaterName("user1");
		
		child3I18nMessageList = new ArrayList<I18nMessage>();
		
		for(LocaleCode localeCode : localeCodeList) {
			I18nMessage i18nMessage = new I18nMessage();
			i18nMessage.setItemMessage("Child2 PortalSystem("+localeCode.getLocaleCode()+")");
			i18nMessage.setLocaleCode(localeCode.getLocaleCode());
			i18nMessage.setFieldName("systemName");
			i18nMessage.setItemId("2222222222");
			
			child3I18nMessageList.add(i18nMessage);
		}
		
		portalSystemChild3.setI18nMessageList(child3I18nMessageList);
		
		aclResourcePermissionChild3 = new ACLResourcePermission();
		aclResourcePermissionChild3.setUserId("userId");
		aclResourcePermissionChild3.setUserName("userId");
		
		PortalSecurity child3Security = new PortalSecurity();
		child3Security.setClassName("Portal-System");
		child3Security.setAclResourcePermissionRead(aclResourcePermissionChild3);
		child3Security.setOperationName("READ");
		child3Security.setOwnerId("user1");
		child3Security.setOwnerName("user1");
		
		portalSystemChild3.setSecurity(child3Security);
		
		portalSystemUpdate = new PortalSystem();
		portalSystemUpdate.setSystemCode("1234567890");
		portalSystemUpdate.setPortalId("P000001");
		portalSystemUpdate.setSystemName("Update PortalSystem");
		portalSystemUpdate.setDescription("Update Description");
		portalSystemUpdate.setParentSystemCode(null);
		portalSystemUpdate.setSystemType("ITEM");
		portalSystemUpdate.setMainView(1);
		portalSystemUpdate.setUrl("Update Url");
		portalSystemUpdate.setTarget("INNER");
		portalSystemUpdate.setOwnerId("user1");
		portalSystemUpdate.setRegisterId("user1");
		portalSystemUpdate.setRegisterName("user1");
		portalSystemUpdate.setUpdaterId("user1");
		portalSystemUpdate.setUpdaterName("user1");
		portalSystemUpdate.setOldSystemCode("1234567890");
		
		aclResourcePermissionUpdate = new ACLResourcePermission();
		aclResourcePermissionUpdate.setUserId("userId");
		aclResourcePermissionUpdate.setUserName("userId");
		
		PortalSecurity updateSecurity = new PortalSecurity();
		updateSecurity.setClassName("Portal-System");
		updateSecurity.setAclResourcePermissionRead(aclResourcePermissionUpdate);
		updateSecurity.setOperationName("READ");
		updateSecurity.setOwnerId("user1");
		updateSecurity.setOwnerName("user1");
		
		portalSystemUpdate.setSecurity(updateSecurity);
		
		portalSystemUpdate.setAclResourcePermissionRead(aclResourcePermissionUpdate);
		
		readParam = new HashMap<String, String>();
		readParam.put("portalId", "P000001");
		readParam.put("fieldName", "systemName");
		readParam.put("localeCode", "ko");
		readParam.put("systemCode", portalSystemCreate.getSystemCode());
		
		child1Param = new HashMap<String, String>();
		child1Param.put("portalId", "P000001");
		child1Param.put("fieldName", "systemName");
		child1Param.put("localeCode", "ko");
		child1Param.put("systemCode", portalSystemChild1.getSystemCode());
		
		child2Param = new HashMap<String, String>();
		child2Param.put("portalId", "P000001");
		child2Param.put("fieldName", "systemName");
		child2Param.put("localeCode", "ko");
		child2Param.put("systemCode", portalSystemChild2.getSystemCode());
		
		child3Param = new HashMap<String, String>();
		child3Param.put("portalId", "P000001");
		child3Param.put("fieldName", "systemName");
		child3Param.put("localeCode", "ko");
		child3Param.put("systemCode", portalSystemChild3.getSystemCode());
		
		moveUpParam = new HashMap<String, String>();
		
		moveDownParam = new HashMap<String, String>();
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#portalSystemListView(java.lang.String)}.
	 */
	@Test
	public void testPortalSystemListView() {
		
		portalSystemService.create(portalSystemCreate);
		
		assertNotNull(portalSystemService.portalSystemListView(portalSystemCreate.getPortalId()));
		
	}
	
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#treeList(java.util.Map)}.
	 */
	@Test
	public void testTreeList() {
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.create(portalSystemChild1);
		portalSystemService.create(portalSystemChild2);
		
		assertNotNull(portalSystemService.treeList(readParam));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#portalSystemLinkList(java.util.Map)}.
	 */
	@Test
	public void testPortalSystemLinkList() {
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.create(portalSystemChild1);
		portalSystemService.create(portalSystemChild2);
		
		assertNotNull(portalSystemService.portalSystemLinkList(readParam));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#read(java.util.Map)}.
	 */
	@Test
	public void testRead() {
		
		portalSystemService.create(portalSystemCreate);
		
		assertNotNull(portalSystemService.read(readParam));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#exists(java.lang.String)}.
	 */
	@Test
	public void testExists() {
		
		portalSystemService.create(portalSystemCreate);
		
		assertTrue(portalSystemService.exists(portalSystemCreate.getSystemCode()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#getChildCount(java.util.Map)}.
	 */
	@Test
	public void testGetChildCount() {
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.create(portalSystemChild1);
		portalSystemService.create(portalSystemChild2);
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("systemCode", portalSystemCreate.getSystemCode());
		paramMap.put("portalId", portalSystemCreate.getPortalId());
		
		assertTrue(portalSystemService.getChildCount(paramMap) == 2);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#getSystemCode(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetSystemCode() {
		
		portalSystemService.create(portalSystemCreate);
		
		String systemCode = portalSystemService.getSystemCode(portalSystemCreate.getSystemName(), portalSystemCreate.getPortalId());
		
		assertEquals(portalSystemCreate.getSystemCode(), systemCode);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#create(com.lgcns.ikep4.portal.admin.screen.model.PortalSystem)}.
	 */
	@Test
	public void testCreate() {
		
		portalSystemService.create(portalSystemCreate);
		
		assertNotNull(portalSystemService.read(readParam));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#update(com.lgcns.ikep4.portal.admin.screen.model.PortalSystem)}.
	 */
	@Test
	public void testUpdate() {
		
		portalSystemService.create(portalSystemCreate);
		
		PortalSystem tempCreate = portalSystemService.read(readParam);
		
		portalSystemUpdate.setSortOrder(tempCreate.getSortOrder());
		
		updateI18nMessageList = i18nMessageService.makeExistLocaleList(IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemCreate.getSystemCode(), "systemName");
		
		for(I18nMessage i18nMessage : updateI18nMessageList) {
			i18nMessage.setItemMessage("Update QuickMenu("+i18nMessage.getLocaleCode()+")");
		}
		
		portalSystemUpdate.setI18nMessageList(updateI18nMessageList);
		
		portalSystemService.update(portalSystemUpdate);

		PortalSystem tempUpdate = portalSystemService.read(readParam);
		
		assertNotSame(tempCreate, tempUpdate);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.delete(portalSystemCreate.getSystemCode());
		
		assertNull(portalSystemService.read(readParam));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#moveUpPortalSystem(java.util.Map)}.
	 */
	@Test
	public void testMoveUpPortalSystem() {
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.create(portalSystemChild1);
		portalSystemService.create(portalSystemChild2);
		
		PortalSystem child1 = portalSystemService.read(child1Param);
		PortalSystem child2 = portalSystemService.read(child2Param);
		
		moveUpParam.put("prevSortOrder", child1.getSortOrder());
		moveUpParam.put("prevNodeId", child1.getSystemCode());
		moveUpParam.put("curSortOrder", child2.getSortOrder());
		moveUpParam.put("curNodeId", child2.getSystemCode());
		moveUpParam.put("updaterId", "user1");
		moveUpParam.put("updaterName", "user1");
		
		portalSystemService.moveUpPortalSystem(moveUpParam);
		
		PortalSystem tempChild = portalSystemService.read(child1Param);
		
		assertEquals(child2.getSortOrder(), tempChild.getSortOrder());
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#moveDownPortalSystem(java.util.Map)}.
	 */
	@Test
	public void testMoveDownPortalSystem() {
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.create(portalSystemChild1);
		portalSystemService.create(portalSystemChild2);
		
		PortalSystem child1 = portalSystemService.read(child1Param);
		PortalSystem child2 = portalSystemService.read(child2Param);
		
		moveDownParam.put("nextSortOrder", child2.getSortOrder());
		moveDownParam.put("nextNodeId", child2.getSystemCode());
		moveDownParam.put("curSortOrder", child1.getSortOrder());
		moveDownParam.put("curNodeId", child1.getSystemCode());
		moveDownParam.put("updaterId", "user1");
		moveDownParam.put("updaterName", "user1");
		
		portalSystemService.moveDownPortalSystem(moveDownParam);
		
		PortalSystem tempChild = portalSystemService.read(child2Param);
		
		assertEquals(child1.getSortOrder(), tempChild.getSortOrder());
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#moveDndOneNode(java.util.Map)}.
	 */
	@Test
	public void testMoveDndOneNode() {
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.create(portalSystemChild1);
		portalSystemService.create(portalSystemChild2);
		
		PortalSystem child1 = portalSystemService.read(child1Param);
		PortalSystem child2 = portalSystemService.read(child2Param);
		
		Map<String, String> dndParam = new HashMap<String, String>();
		dndParam.put("systemCode", child2.getSystemCode());
		dndParam.put("parentSystemCode", child1.getSystemCode());
		dndParam.put("updaterId", "user1");
		dndParam.put("updaterName", "user1");
		
		portalSystemService.moveDndOneNode(dndParam);
		
		PortalSystem tempChild = portalSystemService.read(child2Param);
		
		assertEquals(child1.getSystemCode(), tempChild.getParentSystemCode());
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#moveDndSameNode(java.util.Map)}.
	 */
	@Test
	public void testMoveDndSameNode() {
		
		portalSystemChild2.setParentSystemCode(portalSystemChild1.getSystemCode());
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.create(portalSystemChild1);
		portalSystemService.create(portalSystemChild2);
		
		PortalSystem root = portalSystemService.read(readParam);
		PortalSystem child1 = portalSystemService.read(child1Param);
		PortalSystem child2 = portalSystemService.read(child2Param);
		
		Map<String, String> dndParam = new HashMap<String, String>();
		dndParam.put("systemCode", child2.getSystemCode());
		dndParam.put("parentSystemCode", root.getSystemCode());
		dndParam.put("prevSystemCode", child1.getSystemCode());
		dndParam.put("updaterId", "user1");
		dndParam.put("updaterName", "user1");
		
		portalSystemService.moveDndSameNode(dndParam);
		
		PortalSystem tempChild = portalSystemService.read(child2Param);
		
		assertEquals(root.getSystemCode(), tempChild.getParentSystemCode());
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSystemService#moveDndOtherNode(java.util.Map)}.
	 */
	@Test
	public void testMoveDndOtherNode() {
		
		portalSystemChild2.setParentSystemCode(portalSystemChild1.getSystemCode());
		portalSystemChild3.setParentSystemCode(portalSystemChild2.getSystemCode());
		
		portalSystemService.create(portalSystemCreate);
		portalSystemService.create(portalSystemChild1);
		portalSystemService.create(portalSystemChild2);
		portalSystemService.create(portalSystemChild3);
		
		PortalSystem root = portalSystemService.read(readParam);
		PortalSystem child3 = portalSystemService.read(child3Param);
		
		Map<String, String> dndParam = new HashMap<String, String>();
		dndParam.put("systemCode", child3.getSystemCode());
		dndParam.put("parentSystemCode", root.getSystemCode());
		dndParam.put("updaterId", "user1");
		dndParam.put("updaterName", "user1");
		
		portalSystemService.moveDndOtherNode(dndParam);
		
		PortalSystem tempChild = portalSystemService.read(child3Param);
		
		assertEquals(root.getSystemCode(), tempChild.getParentSystemCode());
		
	}
	
}