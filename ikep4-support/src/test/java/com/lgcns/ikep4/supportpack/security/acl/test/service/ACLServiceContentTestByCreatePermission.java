/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.security.acl.test.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.junit.Assert.assertNotNull;

import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;


/**
 * 리소스 권한 생성 테스트를 수행한다. 
 * 1. 리소스와 권한 정보가 등록되는가? 
 * 2. 유저 권한 정보가 등록되는가? 
 * 3. 그룹 권한 정보가 등록되는가? 
 * 4. 역할별 권한 정보가 등록되는가? 
 * 5. 고용형태별 권한 정보가 등록되는가? 
 * 6. 직책별 권한 정보가 등록되는가?
 * 
 * @author 주길재
 * @version $Id: ACLServiceContentTestByCreatePermission.java 16258 2011-08-18 05:37:22Z giljae $
 */
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class ACLServiceContentTestByCreatePermission extends AbstractTransactionalJUnit4SpringContextTests {
	private ACLResourcePermission aclResourcePermission;

	@Autowired
	private ACLService aclService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private String className = "BBS-BD-Item";

	private String resourceName = "ResourceName";

	private String operationName = "READ";

	@Before
	public void setUp() {
		this.aclResourcePermission = new ACLResourcePermission();
		// 리소스의 타입
		aclResourcePermission.setClassName(this.className);
		// 리소스 이름
		aclResourcePermission.setResourceName(this.resourceName);
		// 리소스에 대한 설명
		aclResourcePermission.setResourceDescription("ResourceDescription");
		// 리소스의 오픈여부, 1=오픈, 0=비오픈
		aclResourcePermission.setOpen(1);
		// 사용자 아이디
		aclResourcePermission.setUserId("user9999");
		// 사용자 이름
		aclResourcePermission.setUserName("user9999");

		// 오퍼레이션 이름
		aclResourcePermission.setOperationName(this.operationName);

		// 퍼미션 이름 & 설명을 등록하지 않으면 리소스의 이름 & 설명으로 대체한다.
		aclResourcePermission.setPermissionName("PermissionName");
		aclResourcePermission.setPermissionDescription("PermissionDescription");
	}

	/**
	 * 리소스 & 권한 생성 테스트
	 */
	@Test
	public void createResourcePermission() {
		aclService.createContentPermission(aclResourcePermission);
		assertNotNull(aclService.getContentPermission(this.className, this.resourceName, this.operationName));
	}

	/**
	 * 유저 권한 생성 테스트
	 */
	@Test
	// @Rollback(false)
	public void createUserPermission() {
		// user1, user2에게 퍼미션을 할당한다.
		aclResourcePermission.addAssignUserId("user1");
		aclResourcePermission.addAssignUserId("user2");
		aclService.createContentPermission(aclResourcePermission);
		assertNotNull(aclService.getContentPermission(this.className, this.resourceName, this.operationName));
	}

	/**
	 * 그룹 권한 생성 테스트
	 */
	@Test
	public void createGroupPermission() {
		// 그룹아이디를 지정하여 리소스 권한을 부여한다.
		aclResourcePermission.addGroupPermission("A00001", 1);
		aclResourcePermission.addGroupPermission("A30000", 0);
		aclService.createContentPermission(aclResourcePermission);
		assertNotNull(aclService.getContentPermission(this.className, this.resourceName, this.operationName));
	}

	/**
	 * 역할별 권한 생성 테스트
	 */
	@Test
	public void createRolePermission() {
		// 롤 아이디를 지정하여 리소스 권한을 부여한다.
		aclResourcePermission.addRoleId("R00001");
		aclResourcePermission.addRoleId("R00002");
		aclService.createContentPermission(aclResourcePermission);
		assertNotNull(aclService.getContentPermission(this.className, this.resourceName, this.operationName));
	}

	/**
	 * 고용 형태별 권한 생성 테스트
	 */
	@Test
	public void createJobClassPermission() {
		// Job Class Code를 지정하여 리소스 권한을 부여한다.
		aclResourcePermission.addJobClassCode("S0008");
		aclResourcePermission.addJobClassCode("S0005");
		aclService.createContentPermission(aclResourcePermission);
		assertNotNull(aclService.getContentPermission(this.className, this.resourceName, this.operationName));
	}

	/**
	 * 직책별 권한 생성 테스트
	 */
	@Test
	public void createJobDutyPermission() {
		// Job Duty Code를 지정하여 리소스 권한을 부여한다.
		aclResourcePermission.addJobDutyCode("D0001");
		aclResourcePermission.addJobDutyCode("D0002");
		aclService.createContentPermission(aclResourcePermission);
		assertNotNull(aclService.getContentPermission(this.className, this.resourceName, this.operationName));
	}
}
