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

import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.service.IdIdeaService;
import com.lgcns.ikep4.support.user.member.model.User;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdIdeaServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdIdeaServiceTest extends BaseServiceTestCase {

	
	@Autowired
	private IdIdeaService idIdeaService;
	
	private IdIdea idIdea;
	
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
		
		
		idIdea = new IdIdea();
		
		idIdea.setItemId(pk);
		idIdea.setTitle("title");
		idIdea.setContents("contents");
		idIdea.setUpdaterId("user1");
		idIdea.setUpdaterName("dong");
		idIdea.setRegisterId("user1");
		idIdea.setRegisterName("동");
		idIdea.setPortalId("p1");
		
		pk = idIdeaService.create(idIdea);
		
	}
	
	
	@Test
	public void testCreate() {
		
		IdIdea result = idIdeaService.get(idIdea.getItemId(), idIdea.getRegisterId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testList() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setStartRowIndex(0);
		idSearch.setEndRowIndex(10);
		idSearch.setUserId(idIdea.getRegisterId());
		idSearch.setLimitDate("30");
		idSearch.setTitle("title");
		idSearch.setOrderType("recommend");
		idSearch.setPortalId("p1");
		
		List<IdIdea> result = idIdeaService.list(idSearch);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGetCountList() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setStartRowIndex(0);
		idSearch.setEndRowIndex(10);
		idSearch.setUserId(idIdea.getRegisterId());
		idSearch.setLimitDate("30");
		idSearch.setTitle("title");
		idSearch.setOrderType("recommend");
		idSearch.setPortalId("p1");
		
		int result = idIdeaService.getCountList(idSearch);
		assertNotSame(result, 0);
	}
	
	
	
	
	@Test
	public void testUpdate() {
		
		idIdea.setTitle("updateTile");
		idIdea.setContents("upateContent");
		
		idIdeaService.update(idIdea);
		
		IdIdea result = idIdeaService.get(pk, idIdea.getRegisterId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testUpdateFavoriteCount() {
		
		idIdeaService.updateFavoriteCount(idIdea.getItemId(), 1, idIdea.getRegisterId());
		
		IdIdea result = idIdeaService.get(pk, idIdea.getRegisterId());
		assertTrue(result.getFavoriteCount() >= 0);
	}
	
	
	@Test
	public void testUpdateMailCount() {
		
		idIdeaService.updateMailCount(idIdea.getItemId());
		
		IdIdea result = idIdeaService.get(pk, idIdea.getRegisterId());
		assertTrue(result.getMailCount() >= 0);
	}
	
	
	@Test
	public void testUpdateMblogCount() {
		
		idIdeaService.updateMblogCount(idIdea.getItemId());
		
		IdIdea result = idIdeaService.get(pk, idIdea.getRegisterId());
		assertTrue(result.getMblogCount() >= 0);
	}
	
	
	@Test
	public void testUpdateBusinessItem() {
		
		idIdeaService.updateBusinessItem(idIdea.getItemId(), "1");
		
		IdIdea result = idIdeaService.get(pk, idIdea.getRegisterId());
		assertTrue(result.getBusinessItem().equals("1"));
	}
	
	
	
	@Test
	public void testUpdateExamination() {
		
		idIdeaService.updateExamination(idIdea.getItemId(), "exam");
		
		IdIdea result = idIdeaService.get(pk, idIdea.getRegisterId());
		assertTrue(result.getExaminationComment().equals("exam"));
	}

	@Test
	public void testRemove() {
		
		//idIdeaService.remove(pk);
		
		IdIdea result = idIdeaService.get(pk, idIdea.getRegisterId());
		assertNull(result);
		
	}
	
	
}
