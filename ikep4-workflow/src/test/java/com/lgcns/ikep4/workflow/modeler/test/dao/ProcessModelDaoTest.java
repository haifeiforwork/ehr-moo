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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.lgcns.ikep4.workflow.modeler.dao.ProcessModelDao;
import com.lgcns.ikep4.workflow.modeler.model.ProcessModel;


/**
 * 모델러 ProcessModel DAO 테스트 1. 생성 2. 읽기 3. 수정 4. 삭제 5. 전체 리스트
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessModelDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ProcessModelDaoTest extends BaseDaoTestCase {
	@Autowired
	private ProcessModelDao processModelDao;

	private ProcessModel processModel;

	private String processId;

	@Before
	public void setUp() throws SQLException {
		processModel = new ProcessModel();
		Double math = Math.random();
		// Author
		processModel.setAuthor("lsm3174");
		// Description
		processModel.setDescription("JUNIT 테스트 합니다.");
		// ModelScript
		processModel.setModelScript("xml");
		// ProcessId
		processModel.setProcessId("Process " + math);
		// ProcessName
		processModel.setProcessName("Process " + math);
		// ProcessVer
		processModel.setProcessVer("1.0");
		// State
		processModel.setState("SAVED");
		// Vendor
		processModel.setVendor("engine");
		// ViewScript
		processModel.setViewScript("xml");
		
		// ProcessModel 생성.
		this.processId = processModelDao.create(processModel);
	}

	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		ProcessModel result = processModelDao.get(this.processId);
		assertNotNull(result);
	}

	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		ProcessModel result = processModelDao.get(this.processId);
		assertNotNull(result);
	}

	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		this.processModel.setProcessName("Process B");
		processModel.setProcessVer("2.0");

		processModelDao.update(this.processModel);
		ProcessModel result = processModelDao.get(this.processId);
		
		// 새로 업데이트 한 내용과 기존의 내용을 비교하여 수행한다. 
		assertNotSame(this.processModel.getProcessId(), result.getProcessId());
		assertNotSame(this.processModel.getProcessVer(), result.getProcessVer());
	}

	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		processModelDao.remove(this.processId);
		ProcessModel result = processModelDao.get(this.processId);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<ProcessModel> result = processModelDao.selectAll();
		assertFalse(result.isEmpty());
	}

	/**
	 * etc 값 여부 확인
	 */
	@Test
	public void testExist() {
		boolean result = processModelDao.exists(this.processId);
		assertTrue(result);
	}
	
	/**
	 * 6. 단일 Row 프로세스 읽기. 
	 */
	@Test
	public void testSelectProcessModel() {
		processModel = processModelDao.selectProcessModel(this.processModel);
		assertNotNull(processModel);
	}
}
