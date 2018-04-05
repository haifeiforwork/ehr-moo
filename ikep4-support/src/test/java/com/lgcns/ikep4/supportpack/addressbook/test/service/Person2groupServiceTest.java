package com.lgcns.ikep4.supportpack.addressbook.test.service;

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.model.Person2group;
import com.lgcns.ikep4.support.addressbook.service.AddrgroupService;
import com.lgcns.ikep4.support.addressbook.service.Person2groupService;
import com.lgcns.ikep4.support.addressbook.service.PersonService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Person2groupDao 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: Person2groupServiceTest.java 16259 2011-08-18 05:40:01Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class Person2groupServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private Person2groupService person2groupService;

	private Person2group person2group;
	
	@Autowired
	private PersonService personService;

	private Person person;
	
	@Autowired
	private AddrgroupService addrgroupService;

	private Addrgroup addrgroup;

	private String personId;
	private String addrgroupId;

	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {
		person = new Person();
		this.personId = idgenService.getNextId();
		this.person.setPersonId(this.personId);
		this.person.setPersonName("personName");
		this.person.setCompanyName("companyName");
		this.person.setTeamName("teamName");
		this.person.setJobRankName("jobRankName");
		this.person.setOfficePhoneno("officePhoneno");
		this.person.setMobilePhoneno("mobilePhoneno");
		this.person.setMailAddress("mailAddress");
		this.person.setPersonMemo("personMemo");
		this.person.setUserType("O");
		this.person.setCompanyUserId("companyUserId");
		this.person.setRegisterId("registerId");
		this.person.setRegisterName("registerName");
		
		personService.create(person);
		
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
		
		this.person2group = new Person2group();
		this.person2group.setPersonId(this.personId);
		this.person2group.setAddrgroupId(this.addrgroupId);
		this.person2group.setUserType("O");
		this.person2group.setRegisterId("registerId");
		this.person2group.setRegisterName("registerName");
		this.person2group.setUpdaterId("updaterId");
		this.person2group.setUpdaterName("updaterName");

		person2groupService.create(person2group);
		
	}
	
    @After
    public void tearDown() {
    	
    	this.person2group = new Person2group();
    	this.person2group.setPersonId(this.personId);
		this.person2group.setAddrgroupId(this.addrgroupId);
		person2groupService.delete(person2group);
    }

	@Test
	public void testCreate() {
		
		Person2group result = person2groupService.read(this.person2group);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {

		this.person2group = new Person2group();
		this.person2group.setPersonId(this.personId);
		this.person2group.setAddrgroupId(this.addrgroupId);
		
		this.person2group.setUserType("O");

		this.person2group.setUpdaterId("updaterId1");
		this.person2group.setUpdaterName("updaterName1");

		person2groupService.update(this.person2group);
		Person2group result = person2groupService.read(this.person2group);
		assertEquals(this.person2group.getPersonId(), result.getPersonId());
		assertEquals(this.person2group.getAddrgroupId(), result.getAddrgroupId());
		assertEquals(this.person2group.getUserType(), result.getUserType());
		assertEquals(this.person2group.getUpdaterId(), result.getUpdaterId());
		assertEquals(this.person2group.getUpdaterName(), result.getUpdaterName());

	}

	@Test
	public void testGet() {
		
		this.person2group = new Person2group();
		this.person2group.setPersonId(this.personId);
		this.person2group.setAddrgroupId(this.addrgroupId);
		
		Person2group result = person2groupService.read(this.person2group);
		assertEquals(this.person2group.getAddrgroupId(), result.getAddrgroupId());

	}

	@Test
	public void testExists() {
		
		this.person2group = new Person2group();
		this.person2group.setPersonId(this.personId);
		this.person2group.setAddrgroupId(this.addrgroupId);
		
		boolean result = person2groupService.exists(this.person2group);
		assertTrue(result);

	}

	@Test
	public void testPhysicalDelete() {
		
		this.person2group = new Person2group();
		this.person2group.setPersonId(this.personId);
		this.person2group.setAddrgroupId(this.addrgroupId);
		
		person2groupService.delete(this.person2group);
		Person2group result = person2groupService.read(this.person2group);
		assertNull(result);

	}


}
