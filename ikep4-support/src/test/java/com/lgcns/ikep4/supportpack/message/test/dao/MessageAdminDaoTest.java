/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.message.test.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.support.message.dao.MessageAdminDao;
import com.lgcns.ikep4.support.message.model.MessageAdmin;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageAdminDaoTest.java 16904 2011-10-25 01:39:09Z giljae $
 */
public class MessageAdminDaoTest extends BaseDaoTestCase {

	@Autowired
	private MessageAdminDao messageAdminDao;
	
	@Before
	public void setUp() throws Exception {
		MessageAdmin messageAdmin = new MessageAdmin();
		messageAdmin.setPortalId("P000000");
		messageAdmin.setMaxMonthFilesize(1000);
		messageAdmin.setMaxStoredFilesize(1000);
		messageAdmin.setMaxAttachFilesize(100);
		messageAdmin.setMaxReceiverCount(100);
		messageAdmin.setKeepDays(30);
		messageAdmin.setDiskSize(100000);
		messageAdmin.setManagerAlarmRatio(80);
		//this.messageAdminDao.create(messageAdmin);
	}
	
	@Test
	@Ignore
	public void testGet() {
		MessageAdmin result = this.messageAdminDao.get("P000000");
		assertNotNull(result);
	}
	
	@Test
	@Ignore
	public void testGetUserAdmin() {
		MessageAdmin result = this.messageAdminDao.getUserAdmin("user1");
		assertNotNull(result);
	}
	
	@Test
	@Ignore
	public void testExists() {
		boolean result = this.messageAdminDao.exists("P000000");
		assertNotNull(result);
	}

	@Ignore
	public void testUpdate() {
		
	}
	
	@Test
	@Ignore
	public void testremove() {
		this.messageAdminDao.remove("P000000");
		MessageAdmin result = this.messageAdminDao.get("P000000");
		assertNull(result);
	}
	
	@Ignore
	public void testgetUserVolumnInfoList() {
	}

	@Ignore
	public void testcountUserVolumnInfoList() {
	}
	
	@Ignore
	public void testmessageWeekChartList() {
	}
	
	@Ignore
	public void testmessageDayChartList() {
	}
	
	@Test
	@Rollback(false)
	public void testRemoveBatch() {
		
		try{
			this.messageAdminDao.removeBatch();
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
	}
}
