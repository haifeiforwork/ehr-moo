/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.admin.dao.AdminInstanceDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminPartitionDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminProcessDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminTodoDao;
import com.lgcns.ikep4.workflow.admin.model.AdminInstance;
import com.lgcns.ikep4.workflow.admin.model.AdminInstanceSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminTodo;
import com.lgcns.ikep4.workflow.admin.model.AdminTodoSearchCondition;
import com.lgcns.ikep4.workflow.modeler.test.service.BaseServiceTestCase;

/**
 * 워크플로우 - 현황관리 - 데시보드
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: DashBoardAdministrationServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class DashBoardAdministrationServiceTest extends BaseServiceTestCase{
	
	@Autowired
	private DashBoardAdministrationService dshBoardAdministrationService;
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminInstanceDao adminInstanceDao;
	
	@Autowired
	private AdminTodoDao adminTodoDao;
	
	private AdminPartition adminPartition;
	
	private AdminProcess adminProcess;
	
	private AdminInstance adminInstance;
	
	private AdminTodo adminTodo;
	
	private AdminInstanceSearchCondition adminInstanceSearchCondition;
	
	private AdminTodoSearchCondition adminTodoSearchCondition;
	
	@Before
	public void setUp() {
		adminPartition = new AdminPartition();
		adminPartition.setPartitionId("1");
		adminPartition.setPartitionName("1");
		adminPartition.setDescription("1");
		adminPartitionDao.create(adminPartition);
		
		adminProcess = new AdminProcess();
		adminProcess.setProcessId("1");
		adminProcess.setProcessVer("1");
		adminProcess.setProcessName("1");
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
		adminTodoDao.create(adminTodo);
		
		adminInstanceSearchCondition = new AdminInstanceSearchCondition();
		adminInstanceSearchCondition.terminateSearchCondition(adminInstanceDao.listInstanceCount(adminInstanceSearchCondition));
		
		adminTodoSearchCondition = new AdminTodoSearchCondition();
		adminTodoSearchCondition.terminateSearchCondition(adminTodoDao.listTodoCount(adminTodoSearchCondition));
		
	}
	
	/*
	 * 파티션 전체 조회건수
	 */
	@Test
	public void partitionCount(){
		Integer count = dshBoardAdministrationService.partitionCount();
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/*
	 * 프로세스 전체 조회건수
	 */
	@Test
	public void processCount(){
		Integer count = dshBoardAdministrationService.processCount();
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	/*
	 * 인스턴스 현황 
	 */
	@Test
	public void instanceStateCount(){
		Map<String,Object> result = dshBoardAdministrationService.instanceStateCount();
		assertFalse(result.isEmpty());
	}
}	
	
