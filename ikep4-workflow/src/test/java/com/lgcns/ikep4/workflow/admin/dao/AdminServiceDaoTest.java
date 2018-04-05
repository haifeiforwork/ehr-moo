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

import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminService;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminServiceDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminServiceDaoTest extends BaseDaoTestCase{
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminServiceDao adminServiceDao;
	
	private AdminProcess adminProcess; 
	
	private AdminService adminService;
	
	private String pk;
	
	private Integer count = 0;
	
	@Before
	public void setUp() {
		
		adminProcess = new AdminProcess();
		adminProcess.setProcessId("1");
		adminProcess.setProcessVer("1");
		adminProcess.setProcessName("1");
		adminProcess.setPartitionId("1");
		// 데이타 생성
		adminProcessDao.create(adminProcess);
		
		adminService = new AdminService();
		adminService.setProcessId("1");
		adminService.setProcessVer("1");
		this.pk = adminServiceDao.create(adminService);
	}
	
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		AdminService result = adminServiceDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		AdminService result = adminServiceDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		adminServiceDao.update(this.adminService);
		AdminService result = adminServiceDao.get(this.pk);
		assertNotSame(this.adminService.getProcessId(), result.getProcessId());
	}
	
	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		adminServiceDao.remove(this.pk);
		AdminService result = adminServiceDao.get(this.pk);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<AdminService> result = adminServiceDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = adminServiceDao.exists(this.pk);
		assertTrue(result);
	}
	
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
	 */
	@Test
	public void listProcessDetail(){
	
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", adminProcess.getProcessId());
		params.put("processVer", adminProcess.getProcessVer());
		
		AdminService result = adminServiceDao.listProcessDetail(params);
		assertNotNull(result);
	}
	
	
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면 적용일자 수정
	 */
	@Test
	public void updateProcessApplyDate(){

		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", adminService.getProcessId());
		params.put("processVer", adminService.getProcessVer());
		params.put("applyStartDate", "2001.01.01");
		params.put("applyEndDate", "9999.12.31");
		
		count = adminServiceDao.updateProcessApplyDate(params);
		
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
}
