/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.security.acl.test.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;


/**
 * 리소스에 대한 권한 수정 기능을 테스트 한다.
 * 
 * @author 주길재
 * @version $Id: ACLServiceSystemTestByUpdatePermission.java 16258 2011-08-18 05:37:22Z giljae $
 */
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class ACLServiceSystemTestByUpdatePermission extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private ACLService aclService;

	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	@Before
	public void setUp() {
		ACLResourcePermission aclResourcePermission = new ACLResourcePermission();
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
		aclResourcePermission.addAssignUserId("user1");
		aclResourcePermission.addAssignUserId("user2");

		// 그룹아이디를 지정하여 리소스 권한을 부여한다.
		aclResourcePermission.addGroupPermission("A00001", 1);
		aclResourcePermission.addGroupPermission("A30000", 0);

		// 롤 아이디를 지정하여 리소스 권한을 부여한다.
		aclResourcePermission.addRoleId("R00001");
		aclResourcePermission.addRoleId("R00002");

		// Job Class Code를 지정하여 리소스 권한을 부여한다.
		aclResourcePermission.addJobClassCode("S0008");
		aclResourcePermission.addJobClassCode("S0005");

		// Job Duty Code를 지정하여 리소스 권한을 부여한다.
		aclResourcePermission.addJobDutyCode("D0001");
		aclResourcePermission.addJobDutyCode("D0002");
		aclService.createSystemPermission(aclResourcePermission);
	}

	/**
	 * 리소스에 대한 권한 수정 기능을 테스트
	 */
	@Test
	public void updatePermission() {
		// 현재 권한 정보
		ACLResourcePermission firstAclRp = aclService.getSystemPermission("BBS-BD-Item", "TestResourceGIL", "READ");
		// 변경할 권한 정보
		ACLResourcePermission secondAclRp = new ACLResourcePermission();
		// 테스트를 위해서 객체 복사
		BeanUtils.copyProperties(firstAclRp, secondAclRp);
		// 복사된 secondAclRp의 데이터 변경
		// Permission Description 변경
		secondAclRp.setPermissionDescription("testtest");
		secondAclRp.setOpen(0);
		// Job Duty Code를 지정안함
		List<String> jobDutyCodeList = new ArrayList<String>();
		secondAclRp.setJobDutyCodeList(jobDutyCodeList);
		
		// 권한 정보 업데이트
		aclService.updateSystemPermission(secondAclRp, "P000001");
		
		// 수정한 데이터를 가져온다.
		ACLResourcePermission thirdAclRp = aclService.getSystemPermission("BBS-BD-Item", "TestResourceGIL", "READ");
		
		// 업데이트 되었는지 확인
		assertTrue(firstAclRp.equals(thirdAclRp));
	}
}
