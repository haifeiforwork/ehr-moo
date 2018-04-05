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

import com.lgcns.ikep4.workflow.admin.model.AdminInstance;
import com.lgcns.ikep4.workflow.admin.model.AdminInstanceSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_INSTANCE : 테스트 1. 생성 2. 읽기 3. 수정 4. 삭제 5. 전체 리스트
 *
 * @author 이성훈
 * @version $Id: AdminInstanceDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminInstanceDaoTest extends BaseDaoTestCase{
	
	@Autowired
	private AdminInstanceDao adminInstanceDao;
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	private AdminInstance adminInstance;
	
	private AdminProcess adminProcess;
	
	private AdminPartition adminPartition;
	
	private AdminInstanceSearchCondition adminInstanceSearchCondition;
	
	private String pk;
	
	private String partition_id;
	
	private Integer count = 0;
	
	@Before
	public void setUp() {
		adminPartition = new AdminPartition();
		adminPartition.setPartitionId("1");
		adminPartition.setPartitionName("1");
		adminPartition.setDescription("1");
		// 데이타 생성
		this.partition_id = adminPartitionDao.create(adminPartition);
		
		adminProcess = new AdminProcess();
		adminProcess.setProcessId("1");
		adminProcess.setProcessVer("1");
		adminProcess.setProcessName("1");
		adminProcess.setPartitionId("1");
		// 데이타 생성
		adminProcessDao.create(adminProcess);
		
		adminInstance = new AdminInstance();
		adminInstance.setInstanceId("1");
		adminInstance.setProcessId("1");
		adminInstance.setProcessVer("1");
		adminInstance.setTitle("1");
		// 데이타 생성
		this.pk = adminInstanceDao.create(adminInstance);
		
		adminInstanceSearchCondition = new AdminInstanceSearchCondition();
		adminInstanceSearchCondition.terminateSearchCondition(count);
	}
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		AdminInstance result = adminInstanceDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		AdminInstance result = adminInstanceDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		this.adminInstance.setTitle("2");
		adminInstanceDao.update(this.adminInstance);
		AdminInstance result = adminInstanceDao.get(this.pk);
		assertNotSame(this.adminInstance.getInstanceId(), result.getInstanceId());
	}
	
	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		adminInstanceDao.remove(this.pk);
		AdminInstance result = adminInstanceDao.get(this.pk);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<AdminInstance> result = adminInstanceDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = adminInstanceDao.exists(this.pk);
		assertTrue(result);
	}
	
	/*
	 * 프로세스 조회(ComboBox)
	*/
	@Test
	public  void listComboProcess(){
		List<String> result = adminProcessDao.listComboProcess(partition_id);
		assertNotNull(result);
	}
	
	/*
	 * 인스턴스 리스트 조회건수
	*/
	@Test
	public void listInstanceCount() {
		count = adminInstanceDao.listInstanceCount(adminInstanceSearchCondition);
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/*
	 * 인스턴스 리스트 조회
	 */
	@Test
	public void listInstance() {
		List<AdminInstance> result = adminInstanceDao.listInstance(adminInstanceSearchCondition);
		assertNotNull(result);
	}
	
	
	/*
	 * 인스턴스 상태 변경
	 */
	@Test
	public void updateInstanceState() {
		Map<String,Object> params = new HashMap<String,Object>();
		List<String>  instanceId = new ArrayList<String>();
		instanceId.add(this.pk);
		
		params.put("instanceState", "active");
		params.put("instanceId", instanceId);
		Integer updateCount = adminInstanceDao.updateInstanceState(params);
		Boolean result = false;
		if(updateCount > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/*
	 * 인스턴스 현황 
	 */
	@Test
	public void instanceStateCount(){
		Map<String,Object> result = adminInstanceDao.instanceStateCount();
		assertNotNull(result);
	}
}
