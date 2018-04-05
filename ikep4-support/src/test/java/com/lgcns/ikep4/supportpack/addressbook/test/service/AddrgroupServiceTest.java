package com.lgcns.ikep4.supportpack.addressbook.test.service;

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.AddrgroupService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardDao 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: AddrgroupServiceTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class AddrgroupServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private AddrgroupService addrgroupService;

	private Addrgroup addrgroup;

	private String addrgroupId;
	
	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {
		
		addrgroup = new Addrgroup();
		this.addrgroupId = idgenService.getNextId();
		addrgroup.setAddrgroupId(this.addrgroupId);
		
		addrgroup.setAddrgroupName("addrgroupName");
		addrgroup.setAddrgroupMemo("addrgroupMemo");
		addrgroup.setGroupType("P");
		addrgroup.setPortalId("P000001");
		addrgroup.setRegisterId("registerId");
		addrgroup.setRegisterName("registerName");
		
		addrgroupService.create(addrgroup);
		
	}

	@Test
	public void testCreate() {
		Addrgroup result = addrgroupService.read(this.addrgroup);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		
		this.addrgroup.setAddrgroupId(this.addrgroupId);
		this.addrgroup.setAddrgroupName("addrgroupName");
		this.addrgroup.setAddrgroupMemo("addrgroupMemo");
		this.addrgroup.setGroupType("P");
		this.addrgroup.setPortalId("P000001");

		
		addrgroupService.update(this.addrgroup);
		Addrgroup result = addrgroupService.read(this.addrgroup);
		assertEquals(this.addrgroup.getAddrgroupName(), result.getAddrgroupName());
		assertEquals(this.addrgroup.getAddrgroupMemo(), result.getAddrgroupMemo());
		assertEquals(this.addrgroup.getGroupType(), result.getGroupType());
		assertEquals(this.addrgroup.getPortalId(), result.getPortalId());

	}

	
	@Test
	public void testSelectAll() {
		
		// 뭘 기준인가 ??
		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		//this.addrgroupId = idgenService.getNextId();
		addrgroupSearch.setRegisterId("registerId");
		addrgroupSearch.setRegisterName("registerName");
		addrgroupSearch.setAddrgroupName("addrgroupName");
		addrgroupSearch.setGroupType("P");
		addrgroupSearch.setPortalId("P000001");
		
		addrgroupSearch.setStartRowIndex(0);
		addrgroupSearch.setEndRowIndex(10);
		
		SearchResult<Addrgroup> result = addrgroupService.list(addrgroupSearch);
		assertFalse(result.isEmptyRecord());
	}

	
	@Test
	public void testSelectAllTotalCount() {
		
		// 뭘 기준인가 ??
		AddrgroupSearchCondition addrgroupSearch = new AddrgroupSearchCondition();
		//this.addrgroupId = idgenService.getNextId();
		addrgroupSearch.setRegisterId("registerId");
		addrgroupSearch.setRegisterName("registerName");
		addrgroupSearch.setAddrgroupName("addrgroupName");
		addrgroupSearch.setGroupType("P");
		addrgroupSearch.setPortalId("P000001");
		addrgroupSearch.setStartRowIndex(0);
		addrgroupSearch.setEndRowIndex(10);
		
		Integer count = addrgroupService.listCount(addrgroupSearch);
		assertNotNull(count);
	}
	
	@Test
	public void testGet() {
		Addrgroup result = addrgroupService.read(this.addrgroup);
		assertEquals(this.addrgroup.getAddrgroupId(), result.getAddrgroupId());
		
	}

	@Test
	public void testExists() {
		boolean result = addrgroupService.exists(this.addrgroup);
		assertTrue(result);
		
	}

	@Test
	public void testPhysicalDelete() {
		addrgroupService.delete(this.addrgroup);
		Addrgroup result = addrgroupService.read(this.addrgroup);
		assertNull(result);
		
	}

}
