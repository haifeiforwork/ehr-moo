/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.security.acl.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * ACLService 단위 테스트 (정적 - System)
 * 
 * @author 주길재(giljae@gmail.com)
 * @version $Id: AccessControlListServiceTest.java 1283 2011-02-24 11:03:31Z
 *          giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ACLServiceSystemTest extends AbstractTransactionalJUnit4SpringContextTests {
	private ACLResourcePermission aclResourcePermission;

	@Autowired
	private ACLService aclService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	@Before
	public void setUp() {
		aclResourcePermission = new ACLResourcePermission();
		// 리소스의 타입
		aclResourcePermission.setClassName("BBS-BD-Item");
		// 리소스 이름
		aclResourcePermission.setResourceName("TestResourceGIL");
		// 리소스에 대한 설명
		aclResourcePermission.setResourceDescription("ResourceDescription");
		// 리소스의 오픈여부, 1=오픈, 0=비오픈
		aclResourcePermission.setOpen(1);
		// 사용자 아이디
		aclResourcePermission.setUserId("user9999");
		// 사용자 이름
		aclResourcePermission.setUserName("user9999");
		// 오퍼레이션 이름
		aclResourcePermission.setOperationName("READ");

		// 퍼미션 이름 & 설명을 등록하지 않으면 리소스의 이름 & 설명으로 대체한다.
		aclResourcePermission.setPermissionName("PermissionName");
		aclResourcePermission.setPermissionDescription("PermissionDescription");

		// user1, user2에게 퍼미션을 할당한다.
		aclResourcePermission.addAssignUserId("user132");
		aclResourcePermission.addAssignUserId("user232");

		// // 그룹아이디를 지정하여 리소스 권한을 부여한다.
		// aclResourcePermission.addGroupPermission("A00001", 1);
		// aclResourcePermission.addGroupPermission("A30000", 0);
		//
		// // 롤 아이디를 지정하여 리소스 권한을 부여한다.
		// aclResourcePermission.addRoleId("R00001");
		// aclResourcePermission.addRoleId("R00002");
		//
		// // Job Class Code를 지정하여 리소스 권한을 부여한다.
		// aclResourcePermission.addJobClassCode("S0008");
		// aclResourcePermission.addJobClassCode("S0005");
		//
		// // Job Duty Code를 지정하여 리소스 권한을 부여한다.
		// aclResourcePermission.addJobDutyCode("D0001");
		// aclResourcePermission.addJobDutyCode("D0002");
	}

	@Test
	public void isAdmin() {
		aclResourcePermission.setOperationName("MANAGE");
		aclResourcePermission.setResourceName("BBS-BD-Item");
		aclService.createSystemPermission(aclResourcePermission);
		// 시스템에 대해서 관리 권한이 있는지 체크
		assertTrue(aclService.isSystemAdmin("BBS-BD-Item", "user9999"));
		// 시스템에 대해서 관리 권한이 없을 경우 체크
		assertFalse(aclService.isSystemAdmin("Portal", "user9999"));
	}

	@Test
	public void hasOwnerPermission() {
		// 사용자가 접근 Resource의 owner이고 Resource의 open값이 0일경우, true를 반환한다.
		aclResourcePermission.setOpen(0);
		aclService.createSystemPermission(aclResourcePermission);
		assertTrue(aclService.hasSystemPermission("BBS-BD-Item", "READ", "TestResourceGIL", "user9999"));
	}

	@Test
	public void hasOwnerPermissionByMultiOperation() {
		// 멀티 Operation일 경우,
		// 사용자가 접근 Resource의 owner이고 Resource의 open값이 0일경우, true를 반환한다.
		aclResourcePermission.setOpen(0);
		aclService.createSystemPermission(aclResourcePermission);
		String[] operationNames = { "READ", "MAIN_MANAGE" };
		assertTrue(aclService.hasSystemPermission("BBS-BD-Item", operationNames, "TestResourceGIL", "user9999"));
	}

	@Test
	public void hasOpenPermission() {
		// 사용자가 접근 Resource의 owner가 아니지만 Resource의 open값이 1일 경우, true를 반환한다.
		aclResourcePermission.setOpen(1);
		aclService.createSystemPermission(aclResourcePermission);
		assertTrue(aclService.hasSystemPermission("BBS-BD-Item", "READ", "TestResourceGIL", "user1"));
	}

	@Test
	public void hasUserPermission() {
		// 사용자가 접근 Resource의 owner가 아니고, 리소스의 open값도 0이지만, 그룹권한/역할권한/사용자권한 중에 접근
		// 권한이 있는 경우, true를 반환한다.
		this.aclResourcePermission.setOpen(0);
		aclService.createSystemPermission(this.aclResourcePermission);
		assertTrue(aclService.hasSystemPermission("BBS-BD-Item", "READ", "TestResourceGIL", "user132"));
	}

	@Test
	public void hasUserPermissionByMultiOperation() {
		// 멀티 Operation일 경우,
		// 사용자가 접근 Resource의 owner가 아니고, 리소스의 open값도 0이지만, 그룹권한/역할권한/사용자권한 중에 접근
		// 권한이 있는 경우, true를 반환한다.
		aclResourcePermission.setOpen(0);
		aclService.createSystemPermission(aclResourcePermission);
		String[] operationNames = { "READ", "MAIN_MANAGE" };
		assertTrue(aclService.hasSystemPermission("BBS-BD-Item", operationNames, "TestResourceGIL", "user132"));
	}

	@Test
	public void hasNotPermission() {
		// 사용자가 접근 Resource의 owner가 아니고, 리소스의 open값도 0이고, 그룹권한/역할권한/사용자권한 중에 접근
		// 권한이 없는 경우, false를 반환한다.
		aclResourcePermission.setOpen(0);
		aclService.createSystemPermission(aclResourcePermission);
		assertFalse(aclService.hasSystemPermission("BBS-BD-Item", "READ", "TestResourceGIL", "user2230"));
	}

	@Test
	public void hasNotPermissionByMultiOperation() {
		// 멀티 Operation일 경우,
		// 사용자가 접근 Resource의 owner가 아니고, 리소스의 open값도 0이고, 그룹권한/역할권한/사용자권한 중에 접근
		// 권한이 없는 경우, false를 반환한다.
		aclResourcePermission.setOpen(0);
		aclService.createSystemPermission(aclResourcePermission);
		String[] operationNames = { "READ", "MAIN_MANAGE" };
		assertFalse(aclService.hasSystemPermission("BBS-BD-Item", operationNames, "TestResourceGIL", "user224"));
	}
	
	@Test
	public void listSystemAdminAsUser() {
		List<User> userList = aclService.listSystemAdminAsUser("KMap");
		assertTrue(userList.size() > 0);
	}
	
	@Test
	@Ignore
	public void listSystemAdminAsGroup() {
		List<Group> groupList = aclService.listSystemAdminAsGroup("Coll-BD");
		assertTrue(groupList.size() > 0);
	}
	
	@Test
	@Ignore
	public void listSystemAdminAsAll() {
		List<User> userList = aclService.listSystemAdminAsAll("Coll-BD");
		assertTrue(userList.size() > 0);
	}
}
