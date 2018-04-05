/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.service;

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
import com.lgcns.ikep4.collpack.qna.model.QnaRecommend;
import com.lgcns.ikep4.collpack.qna.service.QnaRecommendService;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaRecommendServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaRecommendServiceTest extends BaseServiceTestCase {

	@Autowired
	private QnaRecommendService qnaRecommendService;
	
	@Autowired
	private QnaService qnaService;
	
	private QnaRecommend qnaRecommend;

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
		
		qnaRecommend = new QnaRecommend();
		
		qnaRecommend.setQnaId(qnaId);
		qnaRecommend.setRegisterId("dong");
		
		qnaRecommendService.create(qnaRecommend);
	}

	@Test
	public void testCreate() {
		QnaRecommend result = qnaRecommendService.read(qnaRecommend.getQnaId(), qnaRecommend.getRegisterId());
		
		assertNotNull(result);
	}


	@Test
	public void testDelete() {
		qnaRecommendService.delete(qnaRecommend.getQnaId(), qnaRecommend.getRegisterId());
		QnaRecommend result = qnaRecommendService.read(qnaRecommend.getQnaId(), qnaRecommend.getRegisterId());
		assertNull(result);
	}


	@Test
	public void testList() {
		
		List<QnaRecommend> result = qnaRecommendService.list(qnaRecommend.getQnaId());
		assertNotNull(result);
	}
}
