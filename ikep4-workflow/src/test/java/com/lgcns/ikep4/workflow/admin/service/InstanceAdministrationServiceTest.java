/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.dao.AdminInstanceDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminPartitionDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminProcessDao;
import com.lgcns.ikep4.workflow.admin.model.AdminInstance;
import com.lgcns.ikep4.workflow.admin.model.AdminInstanceSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.modeler.test.service.BaseServiceTestCase;

/**
 * 워크플로우 - 현황관리 - 인스턴스 관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: InstanceAdministrationServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class InstanceAdministrationServiceTest extends BaseServiceTestCase{
	@Autowired
	private InstanceAdministrationService instanceAdministrationService;
	
	@Autowired
	private AdminInstanceDao adminInstanceDao;
	
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	private AdminProcess adminProcess;
	
	private AdminPartition adminPartition;
	
	private AdminInstance adminInstance;
	
	private AdminInstanceSearchCondition adminInstanceSearchCondition;
	
	private String partition_id;
	
	private String pk;
	
	@Before
	public void setUp() {
		adminPartition = new AdminPartition();
		adminPartition.setPartitionId("1");
		adminPartition.setPartitionName("1");
		adminPartition.setDescription("1");
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
		adminInstanceSearchCondition.terminateSearchCondition(adminInstanceDao.listInstanceCount(adminInstanceSearchCondition));
	}
	/*
	* 파티션 조회(ComboBox)
	*/ 
	@Test
	public void listComboPartition(){
		List<AdminPartition> result = instanceAdministrationService.listComboPartition();
		assertNotNull(result);
	}
	
	/*
	* 프로세스 조회(ComboBox)
	*/ 
	@Test
	public void listComboProcess(){
		List<String> result = instanceAdministrationService.listComboProcess(this.partition_id);
		assertNotNull(result);
	}
	
	/* 
	 * 인스턴스 리스트 조회
	*/
	@Test
	public void listInstance(){
		SearchResult<AdminInstance> result = instanceAdministrationService.listInstance(adminInstanceSearchCondition);
		assertNotNull(result);
	}
	
	/*
	 * 프로세스 상태 변경  
	*/
	@Test
	public void updateInstanceState(){
		this.adminInstance.setTitle("222");
		Map<String,Object> params = new HashMap<String,Object>();
		List<String>  instanceId = new ArrayList<String>();
		instanceId.add(this.pk);
		
		params.put("instanceState", "active");
		params.put("instanceId", instanceId);
		Integer updateCount = instanceAdministrationService.updateInstanceState(params);
		Boolean result = false;
		if(updateCount > 0){
			result = true;
		}
		assertTrue(result);
	}
}
