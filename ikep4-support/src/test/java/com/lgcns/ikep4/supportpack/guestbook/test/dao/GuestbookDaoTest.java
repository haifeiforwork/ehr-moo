package com.lgcns.ikep4.supportpack.guestbook.test.dao;
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

import com.lgcns.ikep4.support.guestbook.dao.GuestbookDao;
import com.lgcns.ikep4.support.guestbook.model.Guestbook;
import com.lgcns.ikep4.support.guestbook.search.GuestbookSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardDao 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: GuestbookDaoTest.java 16258 2011-08-18 05:37:22Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class GuestbookDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private GuestbookDao guestbookDao;

	private Guestbook guestbook;
	private Guestbook guestbook2;

	private String guestId;
	private String guestId2;
	
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

		guestbookDao.create(guestbook);
		
		guestbook2 = new Guestbook();
		this.guestId2 = idgenService.getNextId();
		guestbook2.setGuestbookId(this.guestId2);
		guestbook2.setGroupId(this.guestId);
		guestbook2.setParentId(this.guestId);
		
		guestbook2.setContents("방명록 댓글 내용 신규 입력");
		guestbook2.setTargetUserId("PROFILEID");
		guestbook2.setRegisterId("TESTREGID");
		guestbook2.setRegisterName("테스트작성자");
		//guestbook.setUpdaterId("TESTREGID");
		//guestbook.setUpdaterName("테스트수정자");

		guestbookDao.create(guestbook2);

	}


	@Test
	public void testCreate() {
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setGuestbookId(this.guestId);
		Guestbook result = guestbookDao.selectGuestbook(guestbookSearch);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.guestbook.setContents("방명록 댓글 내용 수정 입력");
		guestbookDao.update(this.guestbook);

		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setGuestbookId(this.guestId);
		Guestbook result = guestbookDao.selectGuestbook(guestbookSearch);
		assertEquals(this.guestbook.getContents(), result.getContents());
	}

	public void testDelete() {
		guestbookDao.remove(this.guestId);

		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setGuestbookId(this.guestId);
		Guestbook result = guestbookDao.selectGuestbook(guestbookSearch);
		
		assertNull(result);
	}

	@Test
	public void testExists() {
		boolean result = guestbookDao.exists("PROFILEID");
		assertTrue(result);
	}


	@Test
	public void testSelectAll() {
		
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setTargetUserId("PROFILEID");
		guestbookSearch.setPageIndex(1);
		guestbookSearch.setStartRowIndex(1);
		guestbookSearch.setEndRowIndex(10);
		guestbookSearch.setSessUserLocale("ko");
		List<Guestbook> result = guestbookDao.selectAll(guestbookSearch);
		assertFalse(result.isEmpty());
	}

	@Test
	public void testSelectGuestbook() {
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setGuestbookId(this.guestId);
		Guestbook result = guestbookDao.selectGuestbook(guestbookSearch);
		assertNotNull(result);
		
	}
	
	@Test
	public void testSelectGuestbookByGroup() {
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setTargetUserId("PROFILEID");
		guestbookSearch.setPageIndex(1);
		guestbookSearch.setStartRowIndex(1);
		guestbookSearch.setEndRowIndex(10);
		guestbookSearch.setSessUserLocale("ko");
		guestbookSearch.setGuestbookId(this.guestId);
		guestbookSearch.setGroupId(this.guestId);
		List<Guestbook> result = guestbookDao.selectGuestbookByGroup(guestbookSearch);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testSelectAllMore() {
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setTargetUserId("PROFILEID");
		guestbookSearch.setPageIndex(1);
		guestbookSearch.setStartRowIndex(1);
		guestbookSearch.setEndRowIndex(10);
		guestbookSearch.setSessUserLocale("ko");
		guestbookSearch.setGuestbookId(this.guestId);
		List<Guestbook> result = guestbookDao.selectAllMore(guestbookSearch);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testSelectGuestbookByChild() {
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setTargetUserId("PROFILEID");
		guestbookSearch.setPageIndex(1);
		guestbookSearch.setStartRowIndex(1);
		guestbookSearch.setEndRowIndex(10);
		guestbookSearch.setSessUserLocale("ko");
		guestbookSearch.setParentId(this.guestId);
		
		List<Guestbook> result = guestbookDao.selectGuestbookByChild(guestbookSearch);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testSelectAllCount() {
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setParentId(this.guestId);
		Integer count = guestbookDao.selectAllCount(guestbookSearch);
		assertNotNull(count);
		
	}
	
	
	@Test
	public void testUpdateStep() {

		guestbookDao.updateStep(this.guestbook.getGroupId(), this.guestbook.getStep(), this.guestbook.getTargetUserId());
		
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setGuestbookId(this.guestId);
		Guestbook result = guestbookDao.selectGuestbook(guestbookSearch);
		assertEquals(this.guestbook.getStep()+1, result.getStep());
	}
	
	

	@Test
	public void testRemoveGuestbook() {
		
		guestbookDao.removeGuestbook(this.guestId);
		GuestbookSearchCondition guestbookSearch = new GuestbookSearchCondition();
		guestbookSearch.setGuestbookId(this.guestId);
		Guestbook result = guestbookDao.selectGuestbook(guestbookSearch);
		assertNull(result);
	}
}
