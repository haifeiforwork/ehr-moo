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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.admin.model.AdminActivity;
import com.lgcns.ikep4.workflow.admin.model.AdminActivitySearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_ACTIVITY : 테스트 1. 생성 2. 읽기 3. 수정 4. 삭제 5. 전체 리스트
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminActivityDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminActivityDaoTest extends BaseDaoTestCase{
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminActivityDao adminActivityDao;
	
	private AdminProcess adminProcess; 
	
	private AdminActivity adminActivity; 
	
	private AdminActivitySearchCondition adminActivitySearchCondition;
	
	private String pk;
	
	private Integer count = 0;
	
	@Before
	public void setUp() {
		
		adminProcess = new AdminProcess();
		adminProcess.setProcessId("1");
		adminProcess.setProcessVer("1");
		adminProcess.setProcessName("1");
		// 데이타 생성
		adminProcessDao.create(adminProcess);
		
		adminActivity = new AdminActivity();
		adminActivity.setActivityId("1");
		adminActivity.setProcessId("1");
		adminActivity.setDescription("1");
		adminActivity.setProcessVer("1");
		// 데이타 생성
		this.pk = adminActivityDao.create(adminActivity);
		
		adminActivitySearchCondition = new AdminActivitySearchCondition();
		adminActivitySearchCondition.setProcessId("1");
		adminActivitySearchCondition.terminateSearchCondition(count);
		
	}
	
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		AdminActivity result = adminActivityDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		AdminActivity result = adminActivityDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		this.adminActivity.setDescription("1");
		adminActivityDao.update(this.adminActivity);
		AdminActivity result = adminActivityDao.get(this.pk);
		assertNotSame(this.adminActivity.getActivityId(), result.getActivityId());
	}
	
	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		adminActivityDao.remove(this.pk);
		AdminActivity result = adminActivityDao.get(this.pk);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<AdminActivity> result = adminActivityDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = adminActivityDao.exists(this.pk);
		assertTrue(result);
	}
	
	/*
	 * 액티비티 리스트 조회건수
	 */
	@Test
	public void listActivityCount() {
		count = adminActivityDao.listActivityCount(adminActivitySearchCondition);
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/*
	 * 액티비티 리스트 조회
	 */
	@Test
	public void listActivity() {
		List<AdminActivity> result = adminActivityDao.listActivity(adminActivitySearchCondition);
		assertNotNull(result);
	}
}