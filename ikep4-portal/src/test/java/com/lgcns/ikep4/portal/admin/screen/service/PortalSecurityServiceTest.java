/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;

/**
 * TODO Javadoc주석작성
 *
 * @author 占싼쏙옙환
 * @version $Id: PortalSecurityServiceTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalSecurityServiceTest  extends BaseServiceTestCase {

	@Autowired
	private PortalSecurityService portalSecurityService;
	
	@Autowired
	ACLService aclService;
	
	
	private PortalSecurity security;

	
	final String CLASS_NAME = "Portal-Portlet";
	
	final String RESOURCE_NAME = "testresourceName";
	
	final String OPERATION_NAME = "READ";
	/**
	 * 000 하는 메서드
	 *
	 * @param  param param설명
	 * @return 
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {
		
		security = new PortalSecurity();
		
		// 리소스의 타입을 입력한다.
		security.setClassName(CLASS_NAME);
		// 리소스 이름을 입력한다.
		security.setResourceName(RESOURCE_NAME);
		// 리소스에 대한 설명을 입력한다.
		security.setResourceDescription(OPERATION_NAME);
		// 오퍼레이션 이름을 입력한다.
		security.setOperationName("READ");
		
		security.setOwnerName("admin");
		
		security.setOwnerId("admin");
		
		security.setAddrUserListAll("admin");
	
		ACLResourcePermission aclResourcePermissionRead = new ACLResourcePermission();
		
		aclResourcePermissionRead.setOpen(0);

		security.setAclResourcePermissionRead(aclResourcePermissionRead);
		
		portalSecurityService.createSystemPermission(security);
		
	}

	/**
	 * 000 하는 메서드
	 *
	 * @param  param param설명
	 * @return 
	 * @throws Exception 
	 */
	@After
	public void tearDown() throws Exception {}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService#createSystemPermission(com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity)}.
	 */
	@Test
	public void testCreateSystemPermission() {
		
		
		// 리소스에 대한 권한 정보를 읽어 온다.
		ACLResourcePermission aclResourcePermissionRead = aclService.getSystemPermission(CLASS_NAME, RESOURCE_NAME, OPERATION_NAME);

		// 권한에 대한 상세정보를 조회 한다.
		if(aclResourcePermissionRead != null)
		{
			aclResourcePermissionRead = aclService.listDetailPermission(aclResourcePermissionRead);
		}
		
		assertEquals(aclResourcePermissionRead.getOpen(),0);
		assertEquals("admin",aclResourcePermissionRead.getAssignUserIdList().get(0));
		
	}
	
	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService#updateSystemPermissionAndResource(com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity)}.
	 */
	@Test
	public void testUpdateSystemPermissionAndResource() {
		
		security.setAddrUserListAll("user1");
		
		ACLResourcePermission aclResourcePermissionReadTemp = new ACLResourcePermission();
		
		aclResourcePermissionReadTemp.setOpen(0);

		security.setAclResourcePermissionRead(aclResourcePermissionReadTemp);
		
		portalSecurityService.updateSystemPermissionAndResource(security);
		
		// 리소스에 대한 권한 정보를 읽어 온다.
		ACLResourcePermission aclResourcePermissionRead = aclService.getSystemPermission(CLASS_NAME, RESOURCE_NAME, OPERATION_NAME);

		// 권한에 대한 상세정보를 조회 한다.
		if(aclResourcePermissionRead != null)
		{
			aclResourcePermissionRead = aclService.listDetailPermission(aclResourcePermissionRead);
		}
		
		assertEquals(aclResourcePermissionRead.getOpen(),0);
		assertEquals("user1",aclResourcePermissionRead.getAssignUserIdList().get(0));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService#updateSystemPermission(com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity, java.lang.String)}.
	 */
	@Test
	public void testUpdateSystemPermission() {
		
		security.setAddrUserListAll("user1");
		
		ACLResourcePermission aclResourcePermissionReadTemp = new ACLResourcePermission();
		
		aclResourcePermissionReadTemp.setOpen(0);

		security.setAclResourcePermissionRead(aclResourcePermissionReadTemp);
		
		portalSecurityService.updateSystemPermission(security, "P000001");
		
		// 리소스에 대한 권한 정보를 읽어 온다.
		ACLResourcePermission aclResourcePermissionRead = aclService.getSystemPermission(CLASS_NAME, RESOURCE_NAME, OPERATION_NAME);

		// 권한에 대한 상세정보를 조회 한다.
		if(aclResourcePermissionRead != null)
		{
			aclResourcePermissionRead = aclService.listDetailPermission(aclResourcePermissionRead);
		}
		
		assertEquals(aclResourcePermissionRead.getOpen(),0);
		assertEquals("user1",aclResourcePermissionRead.getAssignUserIdList().get(0));
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService#deleteSystemPermission(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeleteSystemPermission() {
		portalSecurityService.deleteSystemPermission(CLASS_NAME, RESOURCE_NAME);
		
		ACLResourcePermission aclResourcePermissionRead = aclService.getSystemPermission(CLASS_NAME, RESOURCE_NAME, OPERATION_NAME);
		
		assertNull(aclResourcePermissionRead);
	}

}
