/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.service;

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

import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrDiscussionServiceTest.java 16903 2011-10-25 01:37:27Z giljae $
 */
public class FrDiscussionServiceTest extends BaseServiceTestCase {

	@Autowired
	private FrDiscussionService frDiscussionService;
	
	@Autowired
	private FrCategoryService frCategoryService;

	private FrDiscussion frDiscussion;
	
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
		user.setTimeDifference("8");
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		req.setSession(session);

	     // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		
		
		FrCategory frCategory = new FrCategory();
		
		frCategory.setCategoryName("카테고리 이름");
		frCategory.setRegisterId("dong");
		frCategory.setRegisterName("동");
		frCategory.setPortalId("p1");
		
		String categoryId = frCategoryService.create(frCategory);
		
		frDiscussion = new FrDiscussion();
		
		frDiscussion.setCategoryId(categoryId);
		frDiscussion.setTitle("title");
		frDiscussion.setContents("contents");
		frDiscussion.setImageId("junitTest");
		frDiscussion.setUpdaterId("lee");
		frDiscussion.setUpdaterName("dong");
		frDiscussion.setRegisterId("user1");
		frDiscussion.setRegisterName("동");
		frDiscussion.setPortalId("p1");
		
		pk = frDiscussionService.create(frDiscussion);
		
	}

	@Test
	//@Rollback(false)
	public void testCreate() {
		
		FrDiscussion result = frDiscussionService.get(pk);
		
		assertNotNull(result);
	}

	@Test
	public void testList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId(frDiscussion.getPortalId());
		frSearch.setUserId(frDiscussion.getRegisterId());
		
		List<FrDiscussion> result = frDiscussionService.list(frSearch);
		assertNotNull(result);
	}
	
	@Test
	public void testGetCountList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId(frDiscussion.getPortalId());
		frSearch.setUserId(frDiscussion.getRegisterId());
		
		int result = frDiscussionService.getCountList(frSearch);
		assertNotSame(result, 0);
	}
	
	
	@Test
	public void testListPopular() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId("p1");
		frSearch.setLimitDate("30");
		try{
			List<FrDiscussion> result = frDiscussionService.listPopular(frSearch);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testGetCountListPopular() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId("p1");
		frSearch.setLimitDate("30");
		
		try{
			int result = frDiscussionService.getCountListPopular(frSearch);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testUpdate() {
		
		frDiscussion.setTitle("updateTile");
		frDiscussion.setContents("upateContent");
		
		frDiscussionService.update(frDiscussion);
		
		FrDiscussion result = frDiscussionService.get(pk);
		assertEquals(frDiscussion.getTitle(), result.getTitle());
	}
	
	@Test
	public void testUpdateMailCount() {
		
		int testMail = frDiscussion.getMailCount();
		frDiscussionService.updateMailCount(pk);
		
		FrDiscussion result = frDiscussionService.get(pk);
		
		assertEquals(testMail+1, result.getMailCount());
	}
	
	@Test
	public void testUpdateMailCoun() {
		
		int testVal = frDiscussion.getMblogCount();
		frDiscussionService.updateMblogCount(pk);
		
		FrDiscussion result = frDiscussionService.get(pk);
		
		assertEquals(testVal+1, result.getMblogCount());
	}
	
	
	@Test
	public void testDelete() {
		
		frDiscussionService.remove(pk, frDiscussion.getRegisterId());
		FrDiscussion result = frDiscussionService.get(pk);
		assertNull(result);
		
	}
	
	
	
	@Test
	public void testListDiscussionByReference() {
		
		try{
			List<FrDiscussion> result = frDiscussionService.listDiscussionByReference("admin",4);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testListDiscussionByItemCount() {
		
		try{
			List<FrDiscussion> result = frDiscussionService.listDiscussionByItemCount(4,"7");
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}

}
