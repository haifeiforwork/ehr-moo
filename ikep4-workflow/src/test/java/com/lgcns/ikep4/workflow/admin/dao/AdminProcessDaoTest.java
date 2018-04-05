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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessSearchCondition;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_PROCESS : 테스트 1. 생성 2. 읽기 3. 수정 4. 삭제 5. 전체 리스트
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminProcessDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminProcessDaoTest extends BaseDaoTestCase{
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	private AdminProcess adminProcess; 
	
	private AdminProcessSearchCondition processSearchCondition;
	
	private AdminPartition adminPartition;
	
	private String pk;
	
	private Integer count = 0;
	
	@Before
	public void setUp() {
		adminPartition = new AdminPartition();
		adminPartition.setPartitionId("1");
		adminPartition.setPartitionName("1");
		adminPartition.setDescription("1");
		// 데이타 생성
		adminPartitionDao.create(adminPartition);

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("modelScript", "1");
		params.put("processId", "1");
		params.put("processVer", "1");
		adminProcessDao.createModel(params);
		
		adminProcess = new AdminProcess();
		adminProcess.setProcessId("1");
		adminProcess.setProcessVer("1");
		adminProcess.setProcessName("1");
		adminProcess.setPartitionId("1");
		// 데이타 생성
		this.pk = adminProcessDao.create(adminProcess);
		
		processSearchCondition = new AdminProcessSearchCondition();
		processSearchCondition.terminateSearchCondition(count);
	}
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		AdminProcess result = adminProcessDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		AdminProcess result = adminProcessDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		this.adminProcess.setProcessName("2");
		adminProcessDao.update(this.adminProcess);
		AdminProcess result = adminProcessDao.get(this.pk);
		assertNotSame(this.adminProcess.getProcessName(), result.getProcessName());
	}
	
	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		adminProcessDao.remove(this.pk);
		AdminProcess result = adminProcessDao.get(this.pk);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<AdminProcess> result = adminProcessDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = adminProcessDao.exists(this.pk);
		assertTrue(result);
	}
	
	/*
	 * 프로세스 조회(ComboBox)
	*/
	@Test
	public void listComboProcess() {
		
		List<String> result = adminProcessDao.listComboProcess("1");
		assertFalse(result.isEmpty());
	}
	
	/*
	 * 프로세스 리스트 조회건수
	 */
	@Test
	public void listProcessCount() {
		count = adminProcessDao.listProcessCount(processSearchCondition);
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/*
	 * 프로세스 리스트 조회
	 */
	@Test
	public void listProcess() {
		List<AdminProcess> result = adminProcessDao.listProcess(processSearchCondition);
		assertNotNull(result);
	}
	
	/*
	 * 프로세스 상태 변경
	 */
	@Test
	public void updateProcessState() {
		Map<String,Object> params = new HashMap<String,Object>();
		List<String>  processId = new ArrayList<String>();
		processId.add(this.pk);
		
		params.put("processState", "active");
		params.put("processId", processId);
		Integer updateCount = adminProcessDao.updateProcessState(params);
		Boolean result = false;
		if(updateCount > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/**
	 * 프로세스 전체 조회건수
	 */
	@Test
	public void processCount() {
		Integer count = adminProcessDao.processCount();
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
}
