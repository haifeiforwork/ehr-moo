/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.security.acl.test.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.lgcns.ikep4.support.security.acl.dao.ACLDao;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;


/**
 * ACLSystemDao의 기능을 테스트 한다.
 * 
 * @author 주길재
 * @version $Id: ACLDaoSystemTest.java 16259 2011-08-18 05:40:01Z giljae $
 */
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"}) 
public class ACLDaoSystemTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	@Qualifier("aclSystemDao")
	private ACLDao aclSystemDao;
	
	@Autowired
	ACLService aclService;

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
	 * 접근 리소스에 대한 권한 아이디 조회 테스트
	 */
	@Test
	public void testListResourcePermission() {
		String[] operationNames = { "READ" };
		assertNotNull(aclSystemDao.listResourcePermission("BBS-BD-Item", operationNames, "TestResourceGIL"));
	}

	/**
	 * 사용자가 소속된 그룹권한/역할권한/사용자권한 조회
	 */
	@Test
	public void testListAllPermission() {
		String[] operationNames = { "READ" };
		List<Map<String, Object>> resourcePermissionList = aclSystemDao.listResourcePermission("BBS-BD-Item", operationNames,
				"TestResourceGIL");

		int resourcePermissionSize = resourcePermissionList.size();
		String[] permissionIds = new String[resourcePermissionSize];
		Map<String, Object> resultMap = null;

		for (int index = 0; index < resourcePermissionSize; index++) {
			resultMap = resourcePermissionList.get(index);
			permissionIds[index] = resultMap.get("permissionId").toString();
		}

		assertNotNull(aclSystemDao.listAllPermission(permissionIds, "user1"));
	}

	/**
	 * 리소스 아이디 가져오기
	 */
	@Test
	public void testGetResourceId() {
		assertNotNull(aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL"));
	}

	/**
	 * 리소스 아이디에 대한 권한 아이디 리스트 가져오기
	 */
	@Test
	public void testListPermissionId() {
		String resourceId = aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL");
		assertNotNull(aclSystemDao.listPermissionId(resourceId));
	}

	/**
	 * 베이스 퍼미션 정보 가져오기
	 */
	@Test
	public void testGetBasePermission() {
		assertNotNull(aclSystemDao.getBasePermission("BBS-BD-Item", "TestResourceGIL", "READ"));
	}

	/**
	 * 그룹 권한 정보 가져오기
	 */
	@Test
	public void testListGroupPermission() {
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission("BBS-BD-Item", "TestResourceGIL", "READ");
		String permissionId = permissionMap.get("permissionId").toString();
		assertNotNull(aclSystemDao.listGroupPermission(permissionId));
	}

	/**
	 * 역할 권한 정보 가져오기
	 */
	@Test
	public void testListRolePermission() {
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission("BBS-BD-Item", "TestResourceGIL", "READ");
		String permissionId = permissionMap.get("permissionId").toString();
		assertNotNull(aclSystemDao.listRolePermission(permissionId));
	}

	/**
	 * 사용자 권한 정보 가져오기
	 */
	@Test
	public void testListUserPermission() {
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission("BBS-BD-Item", "TestResourceGIL", "READ");
		String permissionId = permissionMap.get("permissionId").toString();
		assertNotNull(aclSystemDao.listUserPermission(permissionId));
	}

	/**
	 * 고용 형태 권한 정보 가져오기
	 */
	@Test
	public void testListJobClassPermission() {
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission("BBS-BD-Item", "TestResourceGIL", "READ");
		String permissionId = permissionMap.get("permissionId").toString();
		assertNotNull(aclSystemDao.listJobClassPermission(permissionId));
	}

	/**
	 * 직책 권한 정보 가져오기
	 */
	@Test
	public void testListJobDutyPermission() {
		Map<String, Object> permissionMap = aclSystemDao.getBasePermission("BBS-BD-Item", "TestResourceGIL", "READ");
		String permissionId = permissionMap.get("permissionId").toString();
		assertNotNull(aclSystemDao.listJobDutyPermission(permissionId));
	}

	/**
	 * 그룹 권한 삭제
	 */
	@Test
	public void testRemoveGroupPermission() {
		String resourceId = aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL");
		List<String> permissionIdList = aclSystemDao.listPermissionId(resourceId);
		aclSystemDao.removeGroupPermission(permissionIdList);

		for (String permissionId : permissionIdList) {
			assertEquals(aclSystemDao.listGroupPermission(permissionId).size(),0);
		}
	}

	/**
	 * 역할 권한 삭제
	 */
	@Test
	public void testRemoveRolePermission() {
		String resourceId = aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL");
		List<String> permissionIdList = aclSystemDao.listPermissionId(resourceId);
		aclSystemDao.removeRolePermission(permissionIdList);
		
		for (String permissionId : permissionIdList) {
			assertEquals(aclSystemDao.listRolePermission(permissionId).size(),0);
		}
	}

	/**
	 * 사용자 권한 삭제
	 */
	@Test
	public void testRemoveUserPermission() {
		String resourceId = aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL");
		List<String> permissionIdList = aclSystemDao.listPermissionId(resourceId);
		aclSystemDao.removeUserPermission(permissionIdList);
		
		for (String permissionId : permissionIdList) {
			assertEquals(aclSystemDao.listUserPermission(permissionId).size(),0);
		}
	}

	/**
	 * 고용형태 권한 삭제
	 */
	@Test
	public void testRemoveJobClassPermission() {
		String resourceId = aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL");
		List<String> permissionIdList = aclSystemDao.listPermissionId(resourceId);
		aclSystemDao.removeJobClassPermission(permissionIdList);
		
		for (String permissionId : permissionIdList) {
			assertEquals(aclSystemDao.listJobClassPermission(permissionId).size(),0);
		}
	}

	/**
	 * 직책 권한 삭제
	 */
	@Test
	public void testRemoveJobDutyPermission() {
		String resourceId = aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL");
		List<String> permissionIdList = aclSystemDao.listPermissionId(resourceId);
		aclSystemDao.removeJobDutyPermission(permissionIdList);
		
		for (String permissionId : permissionIdList) {
			assertEquals(aclSystemDao.listJobDutyPermission(permissionId).size(),0);
		}
	}

	/**
	 * 퍼미션 삭제
	 */
	@Test
	public void testRemoveBasePermission() {
		String resourceId = aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL");
		List<String> permissionIdList = aclSystemDao.listPermissionId(resourceId);
		aclSystemDao.removeGroupPermission(permissionIdList);
		aclSystemDao.removeRolePermission(permissionIdList);
		aclSystemDao.removeUserPermission(permissionIdList);
		aclSystemDao.removeJobClassPermission(permissionIdList);
		aclSystemDao.removeJobDutyPermission(permissionIdList);
		aclSystemDao.removeBasePermission(permissionIdList);
		
		assertEquals(aclSystemDao.listPermissionId(resourceId).size(),0);
	}

	/**
	 * 리소스 삭제 
	 * 릴레이션이 연결된 테이블 부터 순차적으로 삭제 해야 한다.
	 */
	@Test
	public void testRemoveReource() {
		String resourceId = aclSystemDao.getResourceId("BBS-BD-Item", "TestResourceGIL");
		List<String> permissionIdList = aclSystemDao.listPermissionId(resourceId);
		aclSystemDao.removeGroupPermission(permissionIdList);
		aclSystemDao.removeRolePermission(permissionIdList);
		aclSystemDao.removeUserPermission(permissionIdList);
		aclSystemDao.removeJobClassPermission(permissionIdList);
		aclSystemDao.removeJobDutyPermission(permissionIdList);
		aclSystemDao.removeBasePermission(permissionIdList);
		aclSystemDao.removeReource(resourceId);
		assertEquals(aclSystemDao.listPermissionId(resourceId).size(),0);
	}
}
