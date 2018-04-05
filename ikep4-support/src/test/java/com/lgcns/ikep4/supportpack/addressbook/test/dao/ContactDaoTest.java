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

import com.lgcns.ikep4.support.addressbook.dao.ContactDao;
import com.lgcns.ikep4.support.addressbook.model.Contact;
import com.lgcns.ikep4.support.addressbook.search.ContactSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * ContactDao 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: ContactDaoTest.java 16259 2011-08-18 05:40:01Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
            "classpath*:/configuration/spring/context-dao.xml", 
            "classpath*:/configuration/spring/context-service.xml" })
public class ContactDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ContactDao contactDao;

	private Contact contact;
	
	private Contact contact2;

	private String contactId;
	
	private String contactId2;
	
	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {
		
		contact = new Contact();
		this.contactId = idgenService.getNextId();
		contact.setContactId(this.contactId);
		
		contact.setContactType("PF");
		contact.setContactUserId("contactUserId");
		contact.setContactUserName("contactUserName");
		contact.setRegisterId("registerId");
		contact.setRegisterName("registerName");
		
		contactDao.create(contact);
		
		contact2 = new Contact();
		this.contactId2 = idgenService.getNextId();
		contact2.setContactId(this.contactId2);
		
		contact2.setContactType("PF");
		contact2.setContactUserId("contactUserId");
		contact2.setContactUserName("contactUserName");
		contact2.setRegisterId("registerId");
		contact2.setRegisterName("registerName");
		
		contactDao.create(contact2);
		
	}

	@Test
	public void testCreate() {
		Contact result = contactDao.get(this.contact);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		
		this.contact.setContactId(this.contactId);
		this.contact.setContactType("PF");
		this.contact.setContactUserId("contactUserId");
		this.contact.setContactUserName("contactUserName");
		
		contactDao.update(this.contact);
		Contact result = contactDao.get(this.contact);
		assertEquals(this.contact.getContactId(), result.getContactId());
		assertEquals(this.contact.getContactType(), result.getContactType());
		assertEquals(this.contact.getContactUserId(), result.getContactUserId());
		assertEquals(this.contact.getContactUserName(), result.getContactUserName());

	}

	@Test
	public void testGet() {
		Contact result = contactDao.get(this.contact);
		assertEquals(this.contact.getContactId(), result.getContactId());
		
	}

	@Test
	public void testExists() {
		boolean result = contactDao.exists(this.contact);
		assertTrue(result);
		
	}

	@Test
	public void testPhysicalDelete() {
		contactDao.remove(this.contact);
		Contact result = contactDao.get(this.contact);
		assertNull(result);
		
	}
	
	@Test
	public void testSelectAll() {
		
		// 뭘 기준인가 ??
		ContactSearchCondition contactSearch = new ContactSearchCondition();
		//this.addrgroupId = idgenService.getNextId();
		contactSearch.setContactUserId("contactUserId");
		contactSearch.setFetchSize(100);
		contactSearch.setSearchContactId(this.contactId2);
		contactSearch.setRegisterId("registerId");
		contactSearch.setSearchType("pre");

		
		List<Contact> result = contactDao.selectAll(contactSearch);
		assertFalse(result.isEmpty());
	}

}
