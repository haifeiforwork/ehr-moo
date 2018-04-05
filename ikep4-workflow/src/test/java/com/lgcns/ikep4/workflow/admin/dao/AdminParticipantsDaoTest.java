/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.admin.model.AdminParticipantSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipants;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 참여자 정보 조회 
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminParticipantsDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminParticipantsDaoTest extends BaseDaoTestCase{
	
	@Autowired
	private AdminParticipantsDao adminParticipantsDao;
	
	private AdminParticipants adminParticipants;
	
	private AdminParticipantSearchCondition adminParticipantSearchCondition;
	
	private String pk;
	
	private Integer count = 0;

	@Before
	public void setUp() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleTypeId", "1");
		params.put("roleTypeName", "1");
		params.put("registerId", "1");
		params.put("registerName", "1");
		params.put("updaterId", "1");
		params.put("updaterName", "1");
		adminParticipantsDao.insertRoleType(params);
		
		adminParticipants = new AdminParticipants();
		adminParticipants.setRoleId("1");
		adminParticipants.setRoleName("1");
		adminParticipants.setRoleTypeId("1");
		adminParticipants.setDescription("1");
		adminParticipants.setRegisterId("1");
		adminParticipants.setRegisterName("1");
		adminParticipants.setUpdaterId("1");
		adminParticipants.setUpdaterName("1");
		
		this.pk = adminParticipantsDao.create(adminParticipants);
		
		adminParticipantSearchCondition = new AdminParticipantSearchCondition();
		adminParticipantSearchCondition.terminateSearchCondition(count);
	}
	
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		AdminParticipants result = adminParticipantsDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		AdminParticipants result = adminParticipantsDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		this.adminParticipants.setDescription("1");
		adminParticipantsDao.update(this.adminParticipants);
		AdminParticipants result = adminParticipantsDao.get(this.pk);
		assertNotSame(this.adminParticipants.getRoleId(), result.getRoleId());
	}
	
	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		adminParticipantsDao.remove(this.pk);
		AdminParticipants result = adminParticipantsDao.get(this.pk);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<AdminParticipants> result = adminParticipantsDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = adminParticipantsDao.exists(this.pk);
		assertTrue(result);
	}
	
	/*
	 * 롤 조회 조회건수
	 */
	@Test
	public void listRoleCount() {
		count = adminParticipantsDao.listRoleCount(adminParticipantSearchCondition);
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/*
	 * 롤 조회
	 */
	@Test
	public void listRole() {
		List<AdminParticipants> result = adminParticipantsDao.listRole(adminParticipantSearchCondition);
		assertNotNull(result);
	}
}