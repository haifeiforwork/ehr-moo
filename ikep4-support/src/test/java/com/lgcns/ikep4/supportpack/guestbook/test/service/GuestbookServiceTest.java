package com.lgcns.ikep4.supportpack.guestbook.test.service;
/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;
import com.lgcns.ikep4.support.guestbook.service.GuestbookService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardDao 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: GuestbookServiceTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class GuestbookServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private GuestbookService guestbookService;

	private Guestbook guestbook;

	private String guestId;
	
	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {

		guestbook = new Guestbook();
		this.guestId = idgenService.getNextId();
		guestbook.setGuestbookId(this.guestId);
		guestbook.setGroupId(this.guestId);
		
		guestbook.setContents("방명록 댓글 내용 신규 입력");
		guestbook.setTargetUserId("PROFILEID");
		guestbook.setRegisterId("TESTREGID");
		guestbook.setRegisterName("테스트작성자");
		//guestbook.setUpdaterId("TESTREGID");
		//guestbook.setUpdaterName("테스트수정자");

		guestbookService.create(guestbook);

	}


	@Test
	@Ignore
	public void testCreate() {
		Guestbook result = guestbookService.read(this.guestId);
		assertNotNull(result);
	}

	@Test
	@Ignore
	public void testUpdate() {
		this.guestbook.setContents("방명록 댓글 내용 수정 입력");
		guestbookService.update(this.guestbook);
		Guestbook result = guestbookService.read(this.guestId);
		assertEquals(this.guestbook.getContents(), result.getContents());
	}

	@Test
	@Ignore
	public void testExists() {
		boolean result = guestbookService.exists("PROFILEID");
		assertTrue(result);
	}


	@Test
	@Ignore
	public void testSelectAll() {
		
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setTargetUserId("user39");
		SearchResult<Guestbook> result = guestbookService.listGuestbook(guestbookSearch);
		assertFalse(result.isEmptyRecord());
	}

	@Test
	@Ignore
	public void testGet() {
		Guestbook guestbook = guestbookService.read("PROFILEID");
		assertNull(guestbook);
		
	}

	@Test
	@Ignore
	public void testLogicalDelete() {
		guestbookService.deleteGuestbook(this.guestId);
		Guestbook result = guestbookService.read(this.guestId);
		assertNull(result);
	}
}
