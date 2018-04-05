/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereplyRecommend;
import com.lgcns.ikep4.collpack.ideation.service.IdIdeaService;
import com.lgcns.ikep4.collpack.ideation.service.IdLinereplyRecommendService;
import com.lgcns.ikep4.collpack.ideation.service.IdLinereplyService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyRecommendServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdLinereplyRecommendServiceTest extends BaseServiceTestCase {

	@Autowired
	private IdLinereplyService idLinereplyService;
	
	@Autowired
	private IdLinereplyRecommendService idLinereplyRecommendService;
	
	
	@Autowired
	private IdIdeaService idIdeaService;
	
	
	private IdLinereplyRecommend idLinereplyRecommend;
	
	
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
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	     // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		
		
		IdIdea idIdea = new IdIdea();
		
		idIdea.setTitle("title");
		idIdea.setContents("contents");
		idIdea.setUpdaterId("lee");
		idIdea.setUpdaterName("dong");
		idIdea.setRegisterId("user1");
		idIdea.setRegisterName("동");
		idIdea.setPortalId("p1");
		
		String itemId = idIdeaService.create(idIdea);
		
		IdLinereply idLinereply = new IdLinereply();
		
		idLinereply.setItemId(itemId);
		idLinereply.setLinereplyGroupId(pk);
		idLinereply.setContents("contents");
		idLinereply.setStep(0);
		idLinereply.setIndentation(0);
		idLinereply.setRegisterId("user1");
		idLinereply.setRegisterName("동");
		idLinereply.setUpdaterId("user1");
		idLinereply.setUpdaterName("updateName");
		idLinereply.setPortalId("p1");
		idLinereply.setAdoptLinereply(1);
		
		pk = idLinereplyService.create(idLinereply);
		
		idLinereplyRecommend = new IdLinereplyRecommend();
		
		idLinereplyRecommend.setLinereplyId(idLinereply.getLinereplyId());
		idLinereplyRecommend.setRegisterId("user1");
		
		idLinereplyRecommendService.create(idLinereply.getLinereplyId(), "user1");
		
	}

	@Test
	public void testList() {
		
		List<IdLinereplyRecommend> result = idLinereplyRecommendService.list(pk);
		assertFalse(result.isEmpty());
	}
	
	
	@Test
	public void testRemove() {
		
		try {
			idLinereplyRecommendService.remove(pk, idLinereplyRecommend.getRegisterId());
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testExists() {
		
		try{
			idLinereplyRecommendService.exists(pk, idLinereplyRecommend.getRegisterId());
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
	}
	
}
