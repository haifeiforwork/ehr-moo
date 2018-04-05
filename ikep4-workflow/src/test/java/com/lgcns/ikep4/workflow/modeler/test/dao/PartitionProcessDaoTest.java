/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.test.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.modeler.dao.PartitionDao;
import com.lgcns.ikep4.workflow.modeler.dao.PartitionProcessDao;
import com.lgcns.ikep4.workflow.modeler.dao.ProcessModelDao;
import com.lgcns.ikep4.workflow.modeler.model.Partition;
import com.lgcns.ikep4.workflow.modeler.model.PartitionProcess;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;


/**
 * 모델러 PartitionProcess DAO 테스트 (Partition, ProcessModel 관계 테이블) 1. 생성 2. 읽기 3.
 * 수정 4. 삭제 5. 전체 리스트
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: PartitionProcessDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class PartitionProcessDaoTest extends BaseDaoTestCase {
	@Autowired
	private PartitionDao partitionDao;

	@Autowired
	private PartitionProcessDao partitionProcessDao;

	@Autowired
	private ProcessModelDao processModelDao;

	private Partition partition;

	private PartitionProcess partitionProcess;

	private ProcessModel processModel;

	private String partitionId;
	
	private String processId;
	
	private String relationId;

	@Before
	public void setUp() throws SQLException {
		partition = new Partition();
		partition.setPartitionId("Partition A");
		partition.setPartitionName("Partition A");
		partition.setDescription("Partition A");

		partitionProcess = new PartitionProcess();
		partitionProcess.setPartitionId("Partition A");
		partitionProcess.setCreateDate(new Date());
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

		partitionId = partitionDao.create(partition);
		processId = processModelDao.create(processModel);
		relationId = partitionProcessDao.create(partitionProcess);
		
	}

	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		PartitionProcess result = partitionProcessDao.get(this.processId);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		PartitionProcess result = partitionProcessDao.get(this.processId);
		assertNotNull(result);
	}
	
	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<PartitionProcess> result = partitionProcessDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = partitionProcessDao.exists(this.partitionId);
		assertTrue(result);
	}
}
