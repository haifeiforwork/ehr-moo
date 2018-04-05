/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.modeler.dao.PartitionDao;
import com.lgcns.ikep4.workflow.modeler.dao.PartitionProcessDao;
import com.lgcns.ikep4.workflow.modeler.dao.ProcessModelDao;
import com.lgcns.ikep4.workflow.modeler.model.InstanceTrackingData;
import com.lgcns.ikep4.workflow.modeler.model.Partition;
import com.lgcns.ikep4.workflow.modeler.model.PartitionProcess;
import com.lgcns.ikep4.workflow.modeler.model.ProcessManager;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;
import com.lgcns.ikep4.workflow.modeler.service.ProcessManagerService;


/**
 * 모델러 서비스 1. 전체 리스트 (Partition, PartitionProcess, Process_Model) 2. Partition
 * 리스트 3. Partition 생성 4. Process 생성 5. Process 리스트 6. Process 수정
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessManagerServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ProcessManagerServiceTest extends BaseServiceTestCase {
	@Autowired
	private ProcessManagerService processManagerService;
	
	@Autowired
	private PartitionDao partitionDao;

	@Autowired
	private PartitionProcessDao partitionProcessDao;

	@Autowired
	private ProcessModelDao processModelDao;

	private ProcessManager processManager;

	private String partitionId;

	private String processId;

	private Partition partition;

	private PartitionProcess partitionProcess;

	private ProcessModel processModel;
	
	private InstanceTrackingData instanceTrackingData;

	@Before
	public void setUp() {
		partition = new Partition();
		partition.setPartitionId("Partition A");
		partition.setPartitionName("Partition A");
		partition.setDescription("Partition A");

		partitionProcess = new PartitionProcess();
		partitionProcess.setPartitionId("Partition A");
		partitionProcess.setProcessId("Process A");
		partitionProcess.setProcessVer("1.0");
		partitionProcess.setRelationId("1234567890");

		processModel = new ProcessModel();
		processModel.setAuthor("lsm3174");
		processModel.setDescription("JUNIT 테스트 합니다.");
		processModel.setModelScript("xml");
		processModel.setProcessId("Process A");
		processModel.setProcessName("Process A");
		processModel.setProcessVer("1.0");
		processModel.setState("SAVED");
		processModel.setVendor("engine");
		processModel.setViewScript("xml");

		partitionId = processManagerService.createPartition(partition);
		processId = processManagerService.createProcess(partitionProcess, processModel);

	}

	//

	/**
	 * 전체 리스트 테스트
	 */
	@Test
	public void testList() {
		ProcessManager result = processManagerService.list();
		
		// 각각의 내용이 담겨 있는지 확인한다.

		assertNotNull(result.getPartitionList());
		assertNotNull(result.getPartitionProcessList());
		assertNotNull(result.getProcessModelList());
	}

	/**
	 * Partition 리스트 테스트
	 */
	@Test
	public void testListPartition() {
		ProcessManager result = processManagerService.listPartition();
		assertNotNull(result.getPartitionList());
	}

	/**
	 * Process 리스트 테스트
	 */
	@Test
	public void testListProcess() {
		ProcessManager result = processManagerService.listProcess(this.processModel);
		assertNotNull(result.getProcessModel());
	}

	/**
	 * Partition 생성 테스트
	 */
	@Test
	public void testCreatePartition() {
		partition.setPartitionId("Partition B");
		partition.setPartitionName("Partition B");
		partition.setDescription("Partition B");
		
		String partitionId = processManagerService.createPartition(this.partition);
		Partition result = partitionDao.get(partitionId);
		assertNotNull(result);
	}

	/**
	 * Process 생성 테스트
	 */
	@Test
	public void testCreateProcess() {
		partitionProcess.setPartitionId("Partition A");
		partitionProcess.setProcessId("Process C");
		partitionProcess.setRelationId("12345678901");
		processModel.setProcessId("Process C");
		processModel.setProcessName("Process C");
		
		String processId = processManagerService.createProcess(this.partitionProcess, this.processModel);
		
		// 새로 생성한 데이터가 있는지 확인한다.
		PartitionProcess partitionProcessResult = partitionProcessDao.get(processId);
		assertNotNull(partitionProcessResult);
		
		ProcessModel processModelResult = processModelDao.get(processId);
		assertNotNull(processModelResult);
		
	}
	
	/**
	 * Process 수정 테스트
	 */
	@Test
	public void testUpdateProcess() {
		processModel.setProcessName("Process B");
		
		processManagerService.updateProcess(this.partitionProcess, this.processModel);
		Boolean result = false;
		
		// 새로 업데이트 한 내용과 기존의 내용을 비교하여 수행한다. 
		if(processModel.getProcessName().equals(processModelDao.get(processId).getProcessName())){
			result = true;
		}
		assertTrue(result);
	}
	
	/**
	 * Process Intance Tracking Data 리스트 테스트
	 */
	@Test
	public void testGetInstanceTrackingXMLData() {
		String instanceId = "12E7E789AA3100000004065"; // Sample Instance ID
		String result = processManagerService.getInstanceTrackingXMLData(instanceId);
		
		assertNotNull(result);
	}
}
