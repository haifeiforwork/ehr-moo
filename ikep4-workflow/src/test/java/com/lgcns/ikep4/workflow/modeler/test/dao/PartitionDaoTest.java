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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.lgcns.ikep4.workflow.modeler.dao.PartitionDao;
import com.lgcns.ikep4.workflow.modeler.model.Partition;


/**
 * 모델러 Partition DAO 테스트 1. 생성 2. 읽기 3. 수정 4. 삭제 5. 전체 리스트
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: PartitionDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class PartitionDaoTest extends BaseDaoTestCase {
	@Autowired
	private PartitionDao partitionDao;

	private Partition partition;

	private String partitionId;

	@Before
	public void setUp() {
		partition = new Partition();
		// Partition Id
		partition.setPartitionId("Partition A");
		// Partition Name
		partition.setPartitionName("Partition A");
		// Partition Description
		partition.setDescription("Partition A");
		
		// Partition 생성.
		this.partitionId = partitionDao.create(partition);
	}
	
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		Partition result = partitionDao.get(this.partitionId);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		Partition result = partitionDao.get(this.partitionId);
		assertNotNull(result);
	}
	
	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		this.partition.setPartitionName("Partition B");
		this.partition.setDescription("Partition B");
		partitionDao.update(this.partition);
		Partition result = partitionDao.get(this.partitionId);
		
		// 새로 업데이트 한 내용과 기존의 내용을 비교하여 수행한다. 
		assertNotSame(this.partition.getPartitionName(), result.getPartitionName());
		assertNotSame(this.partition.getDescription(), result.getDescription());
	}
	
	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		partitionDao.remove(this.partitionId);
		Partition result = partitionDao.get(this.partitionId);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<Partition> result = partitionDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = partitionDao.exists(this.partitionId);
		assertTrue(result);
	}
}
