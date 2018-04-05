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

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdReference;
import com.lgcns.ikep4.collpack.ideation.service.IdIdeaService;
import com.lgcns.ikep4.collpack.ideation.service.IdReferenceService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdReferenceServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdReferenceServiceTest extends BaseServiceTestCase {

	@Autowired
	private IdIdeaService idIdeaService;
	
	@Autowired
	private IdReferenceService idReferenceService;
	

	private IdReference idReference;
	
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
		
		
		
		IdIdea	idIdea = new IdIdea();
		
		idIdea.setTitle("title");
		idIdea.setContents("contents");
		idIdea.setUpdaterId("lee");
		idIdea.setUpdaterName("dong");
		idIdea.setRegisterId("user1");
		idIdea.setRegisterName("동");
		idIdea.setPortalId("p1");
		
		pk = idIdeaService.create(idIdea);
		
		idReference = new IdReference();
		
		idReference.setItemId(pk);
		idReference.setRegisterId("user1");
		
		idReferenceService.create(pk, "user1");
		
	}


	@Test
	public void testList() {
		
		List<IdReference> result = idReferenceService.list(idReference.getItemId());
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testRemove() {
		try {
			idReferenceService.remove(idReference.getItemId(), idReference.getRegisterId());
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		
		
	}
	
}
