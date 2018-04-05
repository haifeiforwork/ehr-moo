/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.service.QnaCategoryService;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaServiceTest extends BaseServiceTestCase {

	@Autowired
	private QnaService qnaService;
	
	private Qna qna;

	private String pk;
	
	private MockHttpServletRequest req; // HttpServletRequest Mock을 선언합니다.
	
	private User user;

	@Before
	public void setUp() {

		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
		user.setUserId("user1");
		user.setUserName("사용자1");
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	     // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));

		
		qna = new Qna();
		
		qna.setQnaType(0);
		qna.setTitle("test qna");
		qna.setContents("just test for DAO");
		qna.setRegisterId("user1");
		qna.setRegisterName("이동희");
		qna.setUpdaterId("user1");
		qna.setUpdaterName("이동희");
		qna.setPortalId("p1");
		qna.setUrgent(0);
		
		pk = qnaService.create(qna, user);
	}

	@Test
	//@Rollback(false)
	public void testCreateExpert() {
		
		Qna testQna = new Qna();
		
		testQna.setQnaType(0);
		testQna.setTitle("test qna");
		testQna.setContents("just test for DAO");
		testQna.setRegisterId("user1");
		testQna.setRegisterName("이동희");
		testQna.setUpdaterId("user1");
		testQna.setUpdaterName("이동희");
		testQna.setPortalId("p1");
		testQna.setUrgent(0);
		
		
		String[] expertIds = new String[]{"user2/동/01047579352/dokbak2@hanmail.net","user3/희/01047579352/dokbak2@hanmail.net"};
		
		testQna.setExpertIds(expertIds);
		testQna.setExpertChannel("0010");
		
		qnaService.create(testQna, user);
	}

	@Test
	public void testUpdate() {
		qna = qnaService.read(pk);
		
		qna.setContents("modified content");
		qna.setTitle("modified title");
		qna.setUpdaterId("바꿈ID");
		qna.setUpdaterName("바꿈이름");
		qnaService.update(qna, user);
		
		Qna result = qnaService.read(pk);
		
		assertEquals(this.qna.getContents(), result.getContents());
	}

	@Test
	public void testDelete() {
		qnaService.delete(qna.getQnaId());
		Qna result = qnaService.read(pk);
		assertNull(result);
	}


	@Test
	public void testList() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		
		List<Qna> result = qnaService.list(qnaSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testListRelation() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		qnaSearch.setPortalId("p1");
		
		List<String> qnaIdList = new ArrayList<String>();
		qnaIdList.add("2");
		
		qnaSearch.setQnaIdList(qnaIdList);
		
		List<Qna> result = qnaService.listRelation(qnaSearch);
		assertNotNull(1);
	}
	
	@Test
	public void testListRelationCount() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		qnaSearch.setPortalId("p1");
		
		List<String> qnaIdList = new ArrayList<String>();
		qnaIdList.add("2");
		
		qnaSearch.setQnaIdList(qnaIdList);
		
		int result = qnaService.readCountRelation(qnaSearch);
		assertNotNull(1);
	}
	
	@Test
	public void testReadBestQna() {
		
		String result = qnaService.readBestQnaId(1, "p1","1",5);
		assertNotNull(result);
	}
	

	@Test
	public void testListCount() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		
		int result = qnaService.listCount(qnaSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testTodayReplyCount() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setNewDate("1");
		qnaSearch.setQnaType(QnaConstants.IS_REPLY);
		
		int result = qnaService.listCount(qnaSearch);
		assertNotNull(result);
	}
	
	@Test
	public void testGetGroup() {
		
		List<Qna> result = qnaService.readGroup(pk, "user1");
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateAdopt() {
		
		qnaService.approveAdopt(pk);
		
		Qna testQna = qnaService.read(pk);
		
		assertFalse(false);
	}
	
	
	
	@Test
	public void testApproveDeleteItem() {
		
		qnaService.approveDeleteItem(qna.getQnaId());
		
		Qna result = qnaService.read(qna.getQnaId());
		
		assertNull(result);
	}
	
	@Test
	public void testCancleDeleteItem() {
		
		qnaService.cancleDeleteItem(pk);
		
		Qna result = qnaService.read(pk);
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateMailCount() {
		
		int testMail = qna.getMailCount();
		qnaService.updateMailCount(pk);
		
		Qna result = qnaService.read(pk);
		
		assertEquals(testMail+1, result.getMailCount());
	}
	
	@Test
	public void testUpdateMailCoun() {
		
		int testVal = qna.getMblogCount();
		qnaService.updateMblogCount(pk);
		
		Qna result = qnaService.read(pk);
		
		assertEquals(testVal+1, result.getMblogCount());
	}
	
	
	@Test
	public void testUpdateFavorite() {
		
		qnaService.updateFavorite(qna.getQnaGroupId(), 1);
		
		Qna result = qnaService.read(pk);
		
		assertTrue(1 == result.getFavoriteCount());
	}
}
