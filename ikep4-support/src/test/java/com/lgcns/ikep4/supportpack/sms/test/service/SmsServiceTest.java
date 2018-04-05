/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.sms.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.lgcns.ikep4.framework.web.SearchResult;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.search.SmsReceiverSearchCondition;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * SmsService 테스트 케이스
 * 
 * @author 서혜숙
 * @version $Id: SmsServiceTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class SmsServiceTest extends BaseServiceTestCase {

	@Autowired
	private SmsService smsService;

	private MockHttpServletRequest req;
	
	User user;
	
	private Sms sms;
	
	private SmsReceiverSearchCondition smsSearch;

	private String pk;

	@Before
	public void setUp() {
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성
		
		user = new User();
		user.setUserId("user96");
		user.setUserName("사용자96");	
		
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장
		req.setSession(session);

		// RequestContextHolder에 위에서 작업한 request 객체를 저장
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		sms = new Sms();

		sms.setReceiverId("user1");
		sms.setReceiverPhoneno("01029872345");
		sms.setReceiverPhonenos(sms.getReceiverPhoneno().split("[-]"));
		sms.setReceiverIds(sms.getReceiverId().split("[-]"));		
		sms.setContents("just test for DAO");
		sms.setResultCode("0");
		sms.setRegisterId("user38850");
		sms.setRegisterName("사용자38850");	

		this.pk = smsService.create(sms);
	}

	@Test
	public void testCreate() {
		Sms result = smsService.read(this.pk);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.sms = smsService.read(this.pk);
		this.sms.setContents("modified content");
		smsService.update(this.sms);
		Sms result = smsService.read(this.pk);
		assertEquals(this.sms.getContents(), result.getContents());

	}

	@Test
	public void testDelete() {
		this.smsService.delete(this.pk);
		Sms result = this.smsService.read(this.pk);
		assertNull(result);
	}
	
	@Test
	public void testExist() {
		boolean result = smsService.exists(this.pk);
		assertTrue(result);
	}

	@Test
	public void testList() {
		List<Sms> result = smsService.list(smsSearch);
		assertNotNull(result);
	}
	
	@Test
	public void testListRecentBottomCount() {
		int count = smsService.listRecentBottomCount(smsSearch);
		assertNotNull(count);
	}
	
	@Test
	public void testListSmsReceiverBySearchCondition() {
		SmsReceiverSearchCondition smsReceiverSearchCondition = new SmsReceiverSearchCondition();
		smsReceiverSearchCondition.setRegisterId("01029872345");
		
		SearchResult<Sms> result = smsService.listSmsReceiverBySearchCondition(smsReceiverSearchCondition);
		assertNotNull(result);
	}	
	
	@Test
	public void testDeleteRecentSms() {
		smsService.deleteRecentSms();
		Sms result = this.smsService.read(this.pk);
		assertNotNull(result);
	}	
}
