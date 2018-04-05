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
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.service.IdIdeaService;
import com.lgcns.ikep4.collpack.ideation.service.IdLinereplyService;
import com.lgcns.ikep4.support.user.member.model.User;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdLinereplyServiceTest extends BaseServiceTestCase {

	@Autowired
	private IdLinereplyService idLinereplyService;
	
	@Autowired
	private IdIdeaService idIdeaService;

	private IdLinereply idLinereply;
	
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
		
		
		idLinereply = new IdLinereply();
		
		idLinereply.setItemId(itemId);
		idLinereply.setContents("contents");
		idLinereply.setStep(0);
		idLinereply.setIndentation(0);
		idLinereply.setRegisterId("user1");
		idLinereply.setRegisterName("동");
		idLinereply.setUpdaterId("user1");
		idLinereply.setUpdaterName("updateName");
		idLinereply.setPortalId("p1");
		idLinereply.setAdoptLinereply(0);
		
		pk = idLinereplyService.create(idLinereply);
		
	}


	@Test
	public void testCountByParentId() {
		
		
		int result = idLinereplyService.getCountByParentId(pk);
		
		assertEquals(result, 0);
	}
	
	public void testList() {
		
		IdSearch idSearch = new IdSearch();
		//idSearch.setBest("1");
		//idSearch.setItemDelete("1");
		idSearch.setOrderType("best");
		idSearch.setEndRowIndex(10);
		idSearch.setStartRowIndex(0);
		idSearch.setItemId(idLinereply.getItemId());
		
		
		List<IdLinereply> result = idLinereplyService.list(idSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testCountList() {
		
		IdSearch idSearch = new IdSearch();
		//idSearch.setBest("1");
		//idSearch.setItemDelete("1");
		idSearch.setOrderType("best");
		
		
		int result = idLinereplyService.getCountList(idSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testUpdate() {
		
		idLinereply.setContents("updateContent");
		idLinereply.setUpdaterId("updateId");
		idLinereply.setUpdaterName("updateName");
		
		try{
			idLinereplyService.update(idLinereply);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testUpdateDeleteFlagTure() {
		
		try{
			idLinereplyService.updateDeleteFlagTure(pk, idLinereply.getItemId());
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	

	@Test
	public void testUpdateDeleteFlagFalse() {
		
		try{
			idLinereplyService.updateDeleteFlagFalse(pk, idLinereply.getItemId());
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testRemove() {
		
		idLinereplyService.remove(pk);
		
		IdSearch idSearch = new IdSearch();
		idSearch.setUserId(idLinereply.getRegisterId());
		idSearch.setEndRowIndex(10);
		idSearch.setStartRowIndex(0);
		idSearch.setItemId("20");
		
		List<IdLinereply> result = idLinereplyService.list(idSearch);
		
		assertTrue(result.isEmpty());
		
	}
	
	
}
