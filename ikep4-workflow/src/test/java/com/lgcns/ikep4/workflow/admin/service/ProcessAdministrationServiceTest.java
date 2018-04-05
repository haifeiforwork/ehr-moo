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
import com.lgcns.ikep4.workflow.admin.dao.AdminActivityDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminPartitionDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminProcessDao;
import com.lgcns.ikep4.workflow.admin.dao.AdminServiceDao;
import com.lgcns.ikep4.workflow.admin.model.AdminActivity;
import com.lgcns.ikep4.workflow.admin.model.AdminActivitySearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminService;
import com.lgcns.ikep4.workflow.modeler.test.service.BaseServiceTestCase;

/**
 * 워크플로우 - 현황관리 - 프로세스 관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: ProcessAdministrationServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */

public class ProcessAdministrationServiceTest extends BaseServiceTestCase{
	
	@Autowired
	private ProcessAdministrationService processAdministrationService;
	
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	@Autowired
	private AdminProcessDao adminProcessDao;
	
	@Autowired
	private AdminActivityDao adminActivityDao;
	
	@Autowired
	private AdminServiceDao adminServiceDao;
	
	private AdminPartition adminPartition;
	
	private AdminProcess adminProcess;
	
	private AdminService adminService;
	
	private AdminProcessSearchCondition processSearchCondition;
	
	private AdminActivitySearchCondition activitySearchCondition;
	
	private String partition_id;
	
	private String pk;
	
	private Integer count = 0;
	
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
		// 데이타 생성
		this.pk = adminProcessDao.create(adminProcess);
		
		adminService = new AdminService();
		adminService.setProcessId("1");
		adminService.setProcessVer("1");
		adminServiceDao.create(adminService);
		
		processSearchCondition = new AdminProcessSearchCondition();
		processSearchCondition.terminateSearchCondition(adminProcessDao.listProcessCount(processSearchCondition));
		
		activitySearchCondition = new AdminActivitySearchCondition();
		activitySearchCondition.terminateSearchCondition(adminActivityDao.listActivityCount(activitySearchCondition));
	}
	/*
	* 파티션 조회(ComboBox)
	*/ 
	@Test
	public void listComboPartition(){
		List<AdminPartition> result = processAdministrationService.listComboPartition();
		assertNotNull(result);
	}
	
	/*
	* 프로세스 조회(ComboBox)
	*/ 
	@Test
	public void listComboProcess(){
		List<String> result = processAdministrationService.listComboProcess(this.partition_id);
		assertNotNull(result);
	}
	
	/* 
	 * 프로세스 리스트 조회
	*/
	@Test
	public void listProcess(){
		SearchResult<AdminProcess> result = processAdministrationService.listProcess(processSearchCondition);
		assertNotNull(result);
	}
	
	/*
	 * 프로세스 상태 변경  
	*/
	@Test
	public void updateProcessState(){
		this.adminProcess.setProcessName("2");
		Map<String,Object> params = new HashMap<String,Object>();
		List<String>  processId = new ArrayList<String>();
		processId.add(this.pk);
		
		params.put("processState", "active");
		params.put("processId", processId);
		Integer updateCount = processAdministrationService.updateProcessState(params);
		Boolean result = false;
		if(updateCount > 0){
			result = true;
		}
		assertTrue(result);
	}
	/*
	 * 액티비티 리스트 조회
	*/ 
	@Test
	public void listActivity(){
		SearchResult<AdminActivity> result = processAdministrationService.listActivity(activitySearchCondition);
		assertNotNull(result);
	}
	
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
	 */
	@Test
	public void listProcessDetail(){
	
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processId", adminService.getProcessId());
		params.put("processVer", adminService.getProcessVer());
		
		AdminService result = processAdministrationService.listProcessDetail(params);
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
		
		count = processAdministrationService.updateProcessApplyDate(params);
		
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
}
