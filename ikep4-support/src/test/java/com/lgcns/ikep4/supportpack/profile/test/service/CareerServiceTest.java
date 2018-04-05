package com.lgcns.ikep4.supportpack.profile.test.service;

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.profile.model.Career;
import com.lgcns.ikep4.support.profile.service.CareerService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * BoardDao 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: CareerServiceTest.java 16259 2011-08-18 05:40:01Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class CareerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private CareerService careerService;

	private Career career;

	private String careerId;
	
	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {
		
		Date today = new Date();
		career = new Career();
		this.careerId = idgenService.getNextId();
		career.setCareerId(this.careerId);
		
		career.setCompanyName("companyName");
		career.setWorkStartDate(today);
		career.setWorkEndDate(DateUtil.getRelativeDate(today, 0, 0, -10));
		career.setRoleName("RoleName");
		career.setWorkChange("workChange");
		career.setRegisterId("registerId");
		career.setRegisterName("registerName");
		
		careerService.create(career);
		
	}

	@Test
	public void testCreate() {
		Career result = careerService.read(this.careerId);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		
		Date today = new Date();
		this.career.setCompanyName("companyName");
		this.career.setWorkStartDate(today);
		this.career.setWorkEndDate(DateUtil.getRelativeDate(today, 0, 0, -10));
		this.career.setRoleName("RoleName");
		this.career.setWorkChange("workChange");
		
		careerService.update(this.career);
		Career result = careerService.read(this.careerId);
		assertEquals(this.career.getCompanyName(), result.getCompanyName());
		//assertEquals(this.career.getWorkStartDate(), result.getWorkStartDate());
		//assertEquals(this.career.getWorkEndDate(), result.getWorkEndDate());
		assertEquals(this.career.getRoleName(), result.getRoleName());
		assertEquals(this.career.getWorkChange(), result.getWorkChange());
		
		
	}

	@Test
	public void testDelete() {
		careerService.delete(this.careerId);
		Career result = careerService.read(this.careerId);
		assertNull(result);
	}


	@Test
	public void testSelectAll() {
		List<Career> result = careerService.list(this.career);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testSelectRecent5() {
		List<Career> result = careerService.listRecent5(this.career);
		assertFalse(result.isEmpty());
	}

	public void testGet() {
		Career career = careerService.read(this.careerId);
		assertNull(career);
		
	}

	@Test
	public void testExists() {
		boolean result = careerService.exists(this.careerId);
		assertTrue(result);
		
	}

}
