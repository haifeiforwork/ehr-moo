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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.main.model.PortalSearchCondition;
import com.lgcns.ikep4.support.security.acl.dao.ACLDao;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;

/**
 * PortalDAO 테스트 케이스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalDaoTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalDaoTest extends BaseDaoTestCase {
	
	@Autowired
	private PortalDao portalDao;
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	@Qualifier("aclSystemDao")
	private ACLDao aclSystemDao;
	
	@Autowired
	private UserDao userDao;
	
	private Portal createPortal;
	
	private Portal updatePortal;
	
	private User addAdmin;
	
	private User updateAdmin;
	
	private PortalSearchCondition searchCondition;
	
	@Before
	public void setUp() {
		
		searchCondition = new PortalSearchCondition();
		
		createPortal = new Portal();
		
		createPortal.setPortalId("1");
		createPortal.setPortalName("테스트 포탈");
		createPortal.setDescription("테스트 포탈");
		createPortal.setDefaultOption(0);
		createPortal.setLoginImageId("createLoginImageId");
		createPortal.setLogoImageId("createLogoImageId");
		createPortal.setPortalHost("test.lgcns.com");
		createPortal.setPortalPath("/portalPath");
		createPortal.setActive(1);
		createPortal.setRegisterId("admin");
		createPortal.setRegisterName("관리자");
		createPortal.setUpdaterId("admin");
		createPortal.setUpdaterName("관리자");
		createPortal.setDefaultLocaleCode("ko");
		
		updatePortal = new Portal();
		
		updatePortal.setPortalId("1");
		updatePortal.setPortalName("update테스트 포탈");
		updatePortal.setDescription("update테스트 포탈");
		updatePortal.setDefaultOption(0);
		updatePortal.setLoginImageId("updateLoginImageId");
		updatePortal.setLogoImageId("updateLogoImageId");
		updatePortal.setPortalHost("ikep.lgcns.com");
		updatePortal.setPortalPath("/updatePortalPath");
		updatePortal.setActive(0);
		updatePortal.setRegisterId("admin");
		updatePortal.setRegisterName("관리자");
		updatePortal.setUpdaterId("admin");
		updatePortal.setUpdaterName("관리자");
		updatePortal.setDefaultLocaleCode("en");
		updatePortal.setAddAdmins("[{\"userId\":\"createUserId\",\"userPassword\":\"portal\",\"userName\":\"createUserName\"}]");
		
		addAdmin = new User();
		
		addAdmin.setUserId("createAdminId");
		addAdmin.setUserPassword("portal");
		addAdmin.setUserPassword(EncryptUtil.encryptSha(addAdmin.getUserId() + addAdmin.getUserPassword()));
		addAdmin.setUserName("createAdminName");
		addAdmin.setPortalId(createPortal.getPortalId());
		addAdmin.setUserStatus("C");
		addAdmin.setUserEnglishName(addAdmin.getUserName());
		addAdmin.setTeamName("NONE");
		addAdmin.setTimezoneId("GMT000000009");
		addAdmin.setLeader("0");
		addAdmin.setLocaleCode("ko");	
		addAdmin.setRegisterId("admin");
		addAdmin.setRegisterName("admin");
		addAdmin.setUpdaterId("admin");
		addAdmin.setUpdaterName("admin");
		
		updateAdmin = new User();
		
		updateAdmin.setUserId(addAdmin.getUserId());
		updateAdmin.setUserPassword(addAdmin.getUserPassword());
		updateAdmin.setUserName("updateAdminName");
		updateAdmin.setPortalId(addAdmin.getPortalId());
		updateAdmin.setUpdaterId(addAdmin.getUpdaterId());
		updateAdmin.setUpdaterName(addAdmin.getUpdaterName());
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#getPortalDomain(java.lang.String)}.
	 */
	@Test
	public void testGetPortalDomain() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		Portal result = portalDao.getPortalDomain(createPortal.getPortalHost());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#getPortalContextPath(java.lang.String)}.
	 */
	@Test
	public void testGetPortalContextPath() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		Portal result = portalDao.getPortalContextPath(createPortal.getPortalPath());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#getPortalDefault()}.
	 */
	@Test
	public void testGetPortalDefault() {
		
		createPortal.setDefaultOption(1);
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		Portal result = portalDao.getPortalDefault();
		
		assertTrue(createPortal.getPortalId().equals(result.getPortalId()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#updatePortalLoginImageId(com.lgcns.ikep4.portal.main.model.Portal)}.
	 */
	@Test
	public void testUpdatePortalLoginImageId() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		portalDao.updatePortalLoginImageId(updatePortal);
		
		Portal result = portalDao.getPortal(updatePortal.getPortalId());
		
		assertTrue(updatePortal.getLoginImageId().equals(result.getLoginImageId()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#updatePortalLogoImageId(com.lgcns.ikep4.portal.main.model.Portal)}.
	 */
	@Test
	public void testUpdatePortalLogoImageId() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		portalDao.updatePortalLogoImageId(updatePortal);
		
		Portal result = portalDao.getPortal(updatePortal.getPortalId());
		
		assertTrue(updatePortal.getLogoImageId().equals(result.getLogoImageId()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#getPortal(java.lang.String)}.
	 */
	@Test
	public void testGetPortal() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		Portal result = portalDao.getPortal(createPortal.getPortalId());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#createPortal(com.lgcns.ikep4.portal.main.model.Portal)}.
	 */
	@Test
	public void testCreatePortal() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		Portal result = portalDao.getPortal(createPortal.getPortalId());
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#updatePortal(com.lgcns.ikep4.portal.main.model.Portal)}.
	 */
	@Test
	public void testUpdatePortal() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		portalDao.updatePortal(updatePortal);
		
		Portal createResult = portalDao.getPortal(createPortal.getPortalId());
		Portal updateResult = portalDao.getPortal(updatePortal.getPortalId());
		
		assertNotSame(createResult, updateResult);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#countListPortal(com.lgcns.ikep4.portal.admin.screen.dao.PortalDaoTest.searchCondition)}.
	 */
	@Test
	public void testCountListPortal() {
		
		int tempCount = portalDao.countListPortal(searchCondition);
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		int count = portalDao.countListPortal(searchCondition);
		
		assertTrue(tempCount<count);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#listPortal(com.lgcns.ikep4.portal.admin.screen.dao.PortalDaoTest.searchCondition)}.
	 */
	@Test
	public void testListPortal() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		List<Portal> result = portalDao.listPortal(searchCondition);
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#removePortal(java.lang.String)}.
	 */
	@Test
	public void testRemovePortal() {
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		portalDao.removePortal(createPortal.getPortalId());
		
		Portal result = portalDao.getPortal(createPortal.getPortalId());
		
		assertNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#resetDefaultOption()}.
	 */
	@Test
	public void testResetDefaultOption() {
		
		createPortal.setDefaultOption(1);
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		portalDao.resetDefaultOption();
		
		Portal result = portalDao.getPortalDefault();
		
		assertNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#listAdminUser(java.util.List, java.lang.String)}.
	 */
	@Test
	public void testListAdminUser() {
		
		List<User> result = new ArrayList<User>();
		
		createPortal.setDefaultOption(1);
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		List<String> assignUserIdList = new ArrayList<String>();
		
		assignUserIdList.add(addAdmin.getUserId());
		
		portalDao.createAdminUser(addAdmin);
		
		String className = "Portal";
		String resourceName = "Portal";
		String operationName = "MANAGE";
		
		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
		aclResourcePermission.setClassName(className);
		aclResourcePermission.setResourceName(resourceName);
		aclResourcePermission.setOperationName(operationName);
		
		aclResourcePermission.setAssignUserIdList(assignUserIdList);
		aclResourcePermission.setUserId("admin");
		aclResourcePermission.setUserName("admin");
		
		// 리소스 아이디에 해당하는 정보를 가져온다.
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission(className, resourceName, operationName);
		String permissionId = permissionMap.get("permissionId").toString();
		
		boolean isEmpty = (aclResourcePermission.getAssignUserIdList() == null)
		|| (aclResourcePermission.getAssignUserIdList().size() == 0);

		if (!isEmpty) {
			
			aclSystemDao.createUserPermission(aclResourcePermission, permissionId);
		}
		
		ACLResourcePermission permission = new ACLResourcePermission();
		
		// 리소스에 대한 권한 정보를 읽어 온다.
		permission = aclService.getSystemPermission(className, resourceName, operationName);
		
		// 권한에 대한 상세정보를 조회 한다.
		if(permission != null)
		{
			result = portalDao.listAdminUser(permission.getAssignUserIdList(), createPortal.getPortalId());
		}
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#createAdminUser(com.lgcns.ikep4.support.user.member.model.User)}.
	 */
	@Test
	public void testCreateAdminUser() {
		
		List<User> result = new ArrayList<User>();
		
		createPortal.setDefaultOption(1);
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		
		List<String> assignUserIdList = new ArrayList<String>();
		
		assignUserIdList.add(addAdmin.getUserId());
		
		portalDao.createAdminUser(addAdmin);
		
		String className = "Portal";
		String resourceName = "Portal";
		String operationName = "MANAGE";
		
		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
		aclResourcePermission.setClassName(className);
		aclResourcePermission.setResourceName(resourceName);
		aclResourcePermission.setOperationName(operationName);
		
		aclResourcePermission.setAssignUserIdList(assignUserIdList);
		aclResourcePermission.setUserId("admin");
		aclResourcePermission.setUserName("admin");
		
		// 리소스 아이디에 해당하는 정보를 가져온다.
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission(className, resourceName, operationName);
		String permissionId = permissionMap.get("permissionId").toString();
		
		boolean isEmpty = (aclResourcePermission.getAssignUserIdList() == null)
		|| (aclResourcePermission.getAssignUserIdList().size() == 0);

		if (!isEmpty) {
			
			aclSystemDao.createUserPermission(aclResourcePermission, permissionId);
		}
		
		ACLResourcePermission permission = new ACLResourcePermission();
		
		// 리소스에 대한 권한 정보를 읽어 온다.
		permission = aclService.getSystemPermission(className, resourceName, operationName);
		
		// 권한에 대한 상세정보를 조회 한다.
		if(permission != null)
		{
			result = portalDao.listAdminUser(permission.getAssignUserIdList(), createPortal.getPortalId());
		}
		
		assertNotNull(result);
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#updateAdminUser(com.lgcns.ikep4.support.user.member.model.User)}.
	 */
	@Test
	public void testUpdateAdminUser() {
		
		createPortal.setDefaultOption(1);
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		portalDao.createAdminUser(addAdmin);
		
		User temp = userDao.get(addAdmin.getUserId());
		
		portalDao.updateAdminUser(updateAdmin, updateAdmin.getPortalId());
		
		User result = userDao.get(updateAdmin.getUserId());
		
		assertTrue(!temp.getUserName().equals(result.getUserName()));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.dao.PortalDao#deleteAdminUser(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeleteAdminUser() {
		
		createPortal.setDefaultOption(1);
		
		//기본 포탈로 생성할 경우 기존의 기본 포탈을 기본 아님으로 변경
		if(createPortal.getDefaultOption() == 1) {
			portalDao.resetDefaultOption();
		}
		
		//사용 여부가 미사용일 경우 기본 포탈로 생성되지 않도록 함
		if(createPortal.getActive() == 0) {
			createPortal.setDefaultOption(0);
		}
		
		portalDao.createPortal(createPortal);
		portalDao.createAdminUser(addAdmin);
		
		portalDao.deleteAdminUser(addAdmin.getUserId(), addAdmin.getPortalId());
		
		User result = userDao.get(addAdmin.getUserId());
		
		assertNull(result);
		
	}
	
}