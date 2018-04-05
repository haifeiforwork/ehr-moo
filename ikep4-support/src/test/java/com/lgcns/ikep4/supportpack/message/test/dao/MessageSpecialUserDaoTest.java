/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.message.test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.message.dao.MessageSpecialUserDao;
import com.lgcns.ikep4.support.message.model.MessageSpecialUser;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageSpecialUserDaoTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageSpecialUserDaoTest extends BaseDaoTestCase {

	@Autowired
	private MessageSpecialUserDao messageSpecialUserDao;
	
	@Before
	public void setUp() throws Exception {
		MessageSpecialUser messageSpecialUser = new MessageSpecialUser();
		messageSpecialUser.setUserId("user10");
		messageSpecialUser.setMaxMonthFilesize(100);
		this.messageSpecialUserDao.create(messageSpecialUser);
	}
	
	@Test
	public void testGet() {
		MessageSpecialUser result = this.messageSpecialUserDao.get("user10");
		assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		boolean result = this.messageSpecialUserDao.exists("user10");
		assertNotNull(result);
	}

	@Ignore
	public void testUpdate() {
	}

	@Test
	public void testRemove() {
		this.messageSpecialUserDao.remove("user10");
		MessageSpecialUser result = this.messageSpecialUserDao.get("user10");
		assertNull(result);
	}
	
	@Ignore
	public void testGetSpecialUserList() {
	}

	@Ignore
	public void testCountSpecialUserList() {
	}
	
}
