/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaLinereply;
import com.lgcns.ikep4.collpack.qna.service.QnaLinereplyService;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaLinereplyServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaLinereplyServiceTest extends BaseServiceTestCase {

	@Autowired
	private QnaLinereplyService qnaLinereplyService;
	
	@Autowired
	private QnaService qnaService;
	
	private QnaLinereply qnaLinereply;
	
	private String pk;
	
	private MockHttpServletRequest req; // HttpServletRequest Mock을 선언합니다.


	@Before
	public void setUp() {
		
		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
		User user = new User();
		// User 모델에 데이터를 setting 합니다.
		user.setUserId("user1");
		user.setUserName("사용자1");
		user.setMail("loverfairy@gmail.com");
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	     // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		
		Qna qna = new Qna();
		
		qna.setQnaType(0);
		qna.setTitle("test qna");
		qna.setContents("just test for DAO");
		qna.setRegisterId("user1");
		qna.setRegisterName("이동희");
		qna.setUpdaterId("user1");
		qna.setUpdaterName("이동희");
		qna.setPortalId("p1");
		qna.setUrgent(0);

		String qnaId = qnaService.create(qna, user);
		
		
		qnaLinereply = new QnaLinereply();
		
		qnaLinereply.setQnaId(qnaId);			
		qnaLinereply.setContents("just test for DAO");
		qnaLinereply.setRegisterId("donghee.lee");
		qnaLinereply.setRegisterName("이동희");
		qnaLinereply.setUpdaterId("donghee.lee");
		qnaLinereply.setUpdaterName("이동희");
		qnaLinereply.setStep(0);
		qnaLinereply.setIndentation(0);
		qnaLinereply.setLinereplyDelete(0);
		
		pk = qnaLinereplyService.create(qnaLinereply);
	}

	@Test
	public void testCreate() {
		QnaLinereply result = qnaLinereplyService.read(qnaLinereply.getLinereplyId());
		
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		
		qnaLinereply = qnaLinereplyService.read(qnaLinereply.getLinereplyId());
		
		qnaLinereply.setContents("just update for DAO");
		qnaLinereply.setUpdaterId("donghee.lee");
		qnaLinereply.setUpdaterName("이동희");
		
		qnaLinereplyService.update(qnaLinereply);
		
		QnaLinereply result = qnaLinereplyService.read(qnaLinereply.getLinereplyId());
		
		assertEquals(qnaLinereply.getContents(), result.getContents());
	}

	@Test
	public void testDelete() {
		qnaLinereplyService.delete(qnaLinereply.getLinereplyId());
		QnaLinereply result = qnaLinereplyService.read(qnaLinereply.getLinereplyId());
		assertNull(result);
	}


	@Test
	public void testList() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		
		List<QnaLinereply> result = qnaLinereplyService.list(qnaSearch);
		assertNotNull(result);
	}
	
	@Test
	public void testGetCount() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setQnaId("1");
		qnaSearch.setItemDelete(0);
		
		int result = qnaLinereplyService.getCount(qnaSearch);
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateItemDelteOk() {
		
		qnaLinereplyService.deleteLinereplyFlag(qnaLinereply.getLinereplyId());
		
		QnaLinereply result = qnaLinereplyService.read(qnaLinereply.getLinereplyId());
		
		assertNull(result);
	}
	
	@Test
	public void testUpdateItemDelteNo() {
		
		qnaLinereplyService.aliveLinereplyDeleteFlay(pk);
		
		QnaLinereply result = qnaLinereplyService.read(pk);
		
		assertNotNull(result);
	}
}
