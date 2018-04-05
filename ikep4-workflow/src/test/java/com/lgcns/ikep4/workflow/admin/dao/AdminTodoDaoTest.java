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

import com.lgcns.ikep4.workflow.admin.model.AdminInstance;
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminTodo;
import com.lgcns.ikep4.workflow.admin.model.AdminTodoSearchCondition;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 워크플로우 - 현황관리 - 업무관리 : 테스트 1. 생성 2. 읽기 3. 수정 4. 삭제 5. 전체 리스트
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminTodoDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminTodoDaoTest extends BaseDaoTestCase{
	
	@Autowired
	private AdminTodoDao adminTodoDao;
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	@Autowired
	private AdminInstanceDao adminInstanceDao;
	
	private AdminTodo adminTodo; 
	
	private AdminProcess adminProcess;
	
	private AdminPartition adminPartition;
	
	private AdminInstance adminInstance;
	
	private AdminTodoSearchCondition adminTodoSearchCondition;
	
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
		adminInstanceDao.create(adminInstance);
		
		adminTodo = new AdminTodo();
		adminTodo.setInstanceId("1");
		adminTodo.setProcessId("1");
		adminTodo.setLogId("1");
		adminTodo.setTitle("1");
		adminTodo.setProcessVer("1");
		// 데이타 생성
		this.pk = adminTodoDao.create(adminTodo);
		
		adminTodoSearchCondition = new AdminTodoSearchCondition();
		adminTodoSearchCondition.terminateSearchCondition(count);
	}
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		AdminTodo result = adminTodoDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		AdminTodo result = adminTodoDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		this.adminTodo.setTitle("1");
		adminTodoDao.update(this.adminTodo);
		AdminTodo result = adminTodoDao.get(this.pk);
		assertNotSame(this.adminTodo.getLogId(), result.getLogId());
	}
	
	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		adminTodoDao.remove(this.pk);
		AdminTodo result = adminTodoDao.get(this.pk);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<AdminTodo> result = adminTodoDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = adminTodoDao.exists(this.pk);
		assertTrue(result);
	}
	
	/*
	 * 업무관리 리스트 조회건수
	 */
	@Test
	public void listTodoCount() {
		count = adminTodoDao.listTodoCount(adminTodoSearchCondition);
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/*
	 * 업무관리 리스트 조회
	 */
	@Test
	public void listTodo() {
		List<AdminTodo> result = adminTodoDao.listTodo(adminTodoSearchCondition);
		assertNotNull(result);
	}
	
	/*
	 * 업무 현황
	 
	@Test
	public void todoStateCount(){
		Map<String,Object> result = adminTodoDao.todoStateCount(adminTodoSearchCondition);
		assertNotNull(result);
	}
	
	
	 * 최근 진행중인 업무 5건
	 
	@Test
	public void listCurrentTodo(){
		List<AdminTodo> result =  adminTodoDao.listCurrentTodo(adminTodoSearchCondition);
		assertNotNull(result);
	}
	*/
}
