package com.lgcns.ikep4.portal.admin.screen.service;

import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSecurity;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdmin;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdminSearchCondition;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;

/**
 * 
 * 시스템 Admin 테스트케이스 
 *
 * @author 임종상
 * @version $Id: SystemAdminServiceTest.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class SystemAdminServiceTest extends BaseServiceTestCase{
	
	private SystemAdmin systemAdmin;
	
	private SystemAdminSearchCondition searchCondition;
	
	private PortalSecurity security;
	
	@Before
	public void setUp() {
		security = new PortalSecurity();
		security.setAclResourcePermissionRead(new ACLResourcePermission());
		security.setResourceName("Portal");
		security.setResourceDescription("Portal");
		security.setClassName("Portal");
		security.setOperationName("MANAGE");
		security.setOwnerId("admin");
		security.setOwnerName("관리자");
		
		systemAdmin = new SystemAdmin();
		systemAdmin.setResourceName("Portal");
		systemAdmin.setSecurity(security);
		
		searchCondition = new SystemAdminSearchCondition();
	}
	
	@Autowired
	private SystemAdminService systemAdminService;
	
	@Autowired
	ACLService aclService;
	
	@Test
	public void testListSystemAdmin() {
		SearchResult<SystemAdmin> result= systemAdminService.listSystemAdmin(searchCondition);
		
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testUpdateSystemAdmin() {
		//시큐리티 객체 추가 해야됨
		systemAdminService.updateSystemAdmin(systemAdmin, "P000001");
		
		ACLResourcePermission result = aclService.getSystemPermission(systemAdmin.getSecurity().getClassName(),systemAdmin.getSecurity().getResourceName(), systemAdmin.getSecurity().getOperationName());
		
		assertTrue(result.getOpen() == 0);
	}
}
