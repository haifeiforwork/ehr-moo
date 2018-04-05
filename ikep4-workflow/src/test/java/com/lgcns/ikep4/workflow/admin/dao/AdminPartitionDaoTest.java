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

import com.lgcns.ikep4.workflow.admin.model.AdminPartition;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_PARTITION : 테스트 1. 생성 2. 읽기 3. 수정 4. 삭제 5. 전체 리스트
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminPartitionDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminPartitionDaoTest extends BaseDaoTestCase{
	
	@Autowired
	private AdminPartitionDao adminPartitionDao;
	
	private AdminPartition adminPartition; 
	
	private String pk;
	
	@Before
	public void setUp() {
		adminPartition = new AdminPartition();
		adminPartition.setPartitionId("1");
		adminPartition.setPartitionName("1");
		adminPartition.setDescription("1");
		// 데이타 생성
		this.pk = adminPartitionDao.create(adminPartition);
	}
	
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		AdminPartition result = adminPartitionDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		AdminPartition result = adminPartitionDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 3. 수정
	 */
	@Test
	public void testUpdate() {
		this.adminPartition.setPartitionName("2");
		adminPartitionDao.update(this.adminPartition);
		AdminPartition result = adminPartitionDao.get(this.pk);
		assertNotSame(this.adminPartition.getPartitionName(), result.getPartitionName());
	}
	
	/**
	 * 4. 삭제
	 */
	@Test
	public void testDelete() {
		adminPartitionDao.remove(this.pk);
		AdminPartition result = adminPartitionDao.get(this.pk);
		assertNull(result);
	}

	/**
	 * 5. 전체 리스트
	 */
	@Test
	public void testSelectAll() {
		List<AdminPartition> result = adminPartitionDao.selectAll();
		assertFalse(result.isEmpty());
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = adminPartitionDao.exists(this.pk);
		assertTrue(result);
	}
	
	/**
	 * 파티션 전체 조회건수
	 */
	@Test
	public void partitionCount() {
		Integer count = adminPartitionDao.partitionCount();
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
}
