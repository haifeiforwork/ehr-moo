package com.lgcns.ikep4.supportpack.addressbook.test.dao;

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.addressbook.dao.AddrgroupDao;
import com.lgcns.ikep4.support.addressbook.dao.Person2groupDao;
import com.lgcns.ikep4.support.addressbook.dao.PersonDao;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.model.Person;
import com.lgcns.ikep4.support.addressbook.model.Person2group;
import com.lgcns.ikep4.support.addressbook.search.PersonSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * PersonDao 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: PersonDaoTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
            "classpath*:/configuration/spring/context-dao.xml", 
            "classpath*:/configuration/spring/context-service.xml" })
public class PersonDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private Person2groupDao person2groupDao;

	private Person2group person2group;
	
	@Autowired
	private PersonDao personDao;

	private Person person;
	
	@Autowired
	private AddrgroupDao addrgroupDao;

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
		this.person.setUserType("0");
		this.person.setCompanyUserId("companyUserId");
		this.person.setRegisterId("registerId");
		this.person.setRegisterName("registerName");
		
		personDao.create(person);
		
		addrgroup = new Addrgroup();
		this.addrgroupId = idgenService.getNextId();
		addrgroup.setAddrgroupId(this.addrgroupId);
		
		addrgroup.setAddrgroupName("addrgroupName");
		addrgroup.setAddrgroupMemo("addrgroupMemo");
		addrgroup.setGroupType("P");
		addrgroup.setPortalId("P000001");
		addrgroup.setRegisterId("registerId");
		addrgroup.setRegisterName("registerName");
		
		addrgroupDao.create(addrgroup);
		
		this.person2group = new Person2group();
		this.person2group.setPersonId(this.personId);
		this.person2group.setAddrgroupId(this.addrgroupId);
		this.person2group.setUserType("O");
		this.person2group.setRegisterId("registerId");
		this.person2group.setRegisterName("registerName");
		this.person2group.setUpdaterId("updaterId");
		this.person2group.setUpdaterName("updaterName");

		person2groupDao.create(person2group);
		
	}

	@Test
	public void testCreate() {
		Person result = personDao.get(this.person);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		
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
		

		personDao.update(this.person);
		Person result = personDao.get(this.person);
		assertEquals(this.person.getPersonName(), result.getPersonName());
		assertEquals(this.person.getCompanyName(), result.getCompanyName());
		assertEquals(this.person.getTeamName(), result.getTeamName());
		assertEquals(this.person.getJobRankName(), result.getJobRankName());
		assertEquals(this.person.getOfficePhoneno(), result.getOfficePhoneno());
		assertEquals(this.person.getMobilePhoneno(), result.getMobilePhoneno());
		assertEquals(this.person.getMailAddress(), result.getMailAddress());
		assertEquals(this.person.getPersonMemo(), result.getPersonMemo());
		assertEquals(this.person.getUserType(), result.getUserType());
		assertEquals(this.person.getCompanyUserId(), result.getCompanyUserId());

	}

	
	@Test
	public void testSelectAllPerPage() {
		
		// 뭘 기준인가 ??
		PersonSearchCondition personSearch = new PersonSearchCondition();
		//this.addrgroupId = idgenService.getNextId();
		personSearch.setRegisterId("registerId");
		personSearch.setRegisterName("registerName");
		personSearch.setUserType("0");
		
		personSearch.setPersonName("personName");
		personSearch.setCompanyName("companyName");
		personSearch.setOfficePhoneno("officePhoneno");
		personSearch.setMobilePhoneno("mobilePhoneno");
		personSearch.setPersonMemo("personMemo");
		
		personSearch.setStartRowIndex(0);
		personSearch.setEndRowIndex(10);
		
		List<Person> result = personDao.selectAllPerPage(personSearch);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testSelectAll() {
		
		// 뭘 기준인가 ??
		PersonSearchCondition personSearch = new PersonSearchCondition();
		personSearch.setAddrgroupId(this.addrgroupId);
		
		List<Person> result = personDao.selectAll(personSearch);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testSelectAllPopup() {
		
		// 뭘 기준인가 ??
		PersonSearchCondition personSearch = new PersonSearchCondition();
		personSearch.setRegisterId("registerId");
		personSearch.setPersonName("personName");
		
		List<Person> result = personDao.selectAllPopUp(personSearch);
		assertFalse(result.isEmpty());
	}
	

	@Test
	public void testSelectTotalCntPerPage() {
		
		// 뭘 기준인가 ??
		PersonSearchCondition personSearch = new PersonSearchCondition();
		//this.addrgroupId = idgenService.getNextId();
		personSearch.setRegisterId("registerId");
		personSearch.setRegisterName("registerName");
		personSearch.setUserType("0");
		
		personSearch.setPersonName("personName");
		personSearch.setCompanyName("companyName");
		personSearch.setOfficePhoneno("officePhoneno");
		personSearch.setMobilePhoneno("mobilePhoneno");
		personSearch.setPersonMemo("personMemo");
		personSearch.setStartRowIndex(0);
		personSearch.setEndRowIndex(10);
		
		Integer count = personDao.selectTotalCntPerPage(personSearch);
		assertNotNull(count);
	}
	
	@Test
	public void testSelectTotalCountAll() {
		
		// 뭘 기준인가 ??
		PersonSearchCondition personSearch = new PersonSearchCondition();
		personSearch.setAddrgroupId(this.addrgroupId);
		
		Integer count = personDao.selectTotalCountAll(personSearch);
		assertNotNull(count);
	}
	
	@Test
	public void testSelectTotalCountAllPopUp() {
		
		// 뭘 기준인가 ??
		PersonSearchCondition personSearch = new PersonSearchCondition();
		personSearch.setRegisterId("registerId");
		personSearch.setPersonName("personName");
		
		Integer count = personDao.selectTotalCountAllPopUp(personSearch);
		assertNotNull(count);
	}

	@Test
	public void testGet() {
		Person result = personDao.get(this.person);
		assertEquals(this.person.getPersonId(), result.getPersonId());
		
	}

	@Test
	public void testExists() {
		boolean result = personDao.exists(this.person);
		assertTrue(result);
		
	}

	@Test
	public void testPhysicalDelete() {
		personDao.remove(this.person);
		Person result = personDao.get(this.person);
		assertNull(result);
		
	}

}
