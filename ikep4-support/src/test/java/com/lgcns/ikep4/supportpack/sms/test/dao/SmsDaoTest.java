/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.sms.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.sms.dao.SmsDao;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.search.SmsReceiverSearchCondition;


/**
 * SmsDao 테스트 케이스
 * 
 * @author 서혜숙
 * @version $Id: SmsDaoTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class SmsDaoTest extends BaseDaoTestCase {

	@Autowired
	private SmsDao smsDao;

	private Sms sms;
	
	private SmsReceiverSearchCondition smsSearch;

	private String pk;

	@Before
	public void setUp() {
		sms = new Sms();

		sms.setReceiverId("user1");
		sms.setReceiverPhoneno("01029872345");
		sms.setContents("just test for DAO");
		sms.setResultCode("0");
		sms.setRegisterId("user38850");
		sms.setRegisterName("사용자38850");
		
		this.pk = smsDao.create(sms);
	}

	@Test
	public void testCreate() {
		Sms result = smsDao.get(this.pk);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.sms.setContents("modified content");
		smsDao.update(this.sms);
		Sms result = smsDao.get(this.pk);
		assertEquals(this.sms.getContents(), result.getContents());
	}

	@Test
	public void testDelete() {
		smsDao.remove(this.pk);
		Sms result = smsDao.get(this.pk);
		assertNull(result);
	}

	@Test
	public void testExist() {
		boolean result = smsDao.exists(this.pk);
		assertTrue(result);
	}

	@Test
	public void testSelectRecentBottom() {
		List<Sms> result = smsDao.selectRecentBottom(this.smsSearch);
		assertNotNull(result);
	}

	@Test
	public void testSelectRecentBottomCount() {
		int count = smsDao.selectRecentBottomCount(this.smsSearch);
		assertNotNull(count);
	}		
	
	@Test
	public void testSmsReceiverSearchCondition() {	
		SmsReceiverSearchCondition smsReceiverSearchCondition = new SmsReceiverSearchCondition();
		smsReceiverSearchCondition.setRegisterId("01029872345");

		List<Sms> result = smsDao.listSmsReceiverBySearchCondition(smsReceiverSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testCountSmsReceiverBySearchCondition() {	
		SmsReceiverSearchCondition smsReceiverSearchCondition = new SmsReceiverSearchCondition();
		smsReceiverSearchCondition.setRegisterId("01029872345");

		int count = smsDao.countSmsReceiverBySearchCondition(smsReceiverSearchCondition);
		assertNotNull(count);
	}	
	
}
