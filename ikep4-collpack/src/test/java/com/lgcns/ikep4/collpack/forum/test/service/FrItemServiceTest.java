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
import com.lgcns.ikep4.collpack.forum.model.FrDiscussionScore;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionScoreService;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionService;
import com.lgcns.ikep4.collpack.forum.service.FrItemService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrItemServiceTest extends BaseServiceTestCase {

	@Autowired
	private FrCategoryService frCategoryService;
	
	@Autowired
	private FrDiscussionService frDiscussionService;
	
	@Autowired
	private FrItemService frItemService;
	
	
	private FrItem frItem;
	
	private String pk;
	
	private String categoryId;

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
		
		
		
		FrCategory frCategory = new FrCategory();
		
		frCategory.setCategoryName("카테고리 이름");
		frCategory.setRegisterId("user2");
		frCategory.setRegisterName("동");
		frCategory.setPortalId("p1");
		
		categoryId = frCategoryService.create(frCategory);
		
		
		FrDiscussion frDiscussion = new FrDiscussion();
		
		frDiscussion.setDiscussionId("11");
		frDiscussion.setCategoryId(categoryId);
		frDiscussion.setTitle("title");
		frDiscussion.setContents("contents");
		frDiscussion.setImageId("junitTest");
		frDiscussion.setUpdaterId("user1");
		frDiscussion.setUpdaterName("dong");
		frDiscussion.setRegisterId("user1");
		frDiscussion.setRegisterName("동");
		frDiscussion.setPortalId("p1");
		
		String discussionId = frDiscussionService.create(frDiscussion);
		
		frItem = new FrItem();
		
		frItem.setItemId(discussionId);
		frItem.setDiscussionId(frDiscussion.getDiscussionId());
		frItem.setTitle("title");
		frItem.setContents("contents");
		frItem.setUpdaterId("user1");
		frItem.setUpdaterName("dong");
		frItem.setRegisterId("user1");
		frItem.setRegisterName("동");
		frItem.setPortalId("p1");
		
		pk = frItemService.create(frItem);
		
	}
	
	
	@Test
	public void testCreate() {
		
		FrItem result = frItemService.get(frItem.getItemId(), frItem.getRegisterId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setTitle("title");
		frSearch.setOrderType("best");
		frSearch.setPortalId("p1");
		
		List<FrItem> result = frItemService.list(frSearch);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGetCountList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setTitle("title");
		frSearch.setOrderType("best");
		frSearch.setPortalId("p1");
		
		int result = frItemService.getCountList(frSearch);
		assertNotSame(result, 0);
	}
	
	
	@Test
	public void testListWithDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setPortalId("p1");
		
		List<FrItem> result = frItemService.listWithDiscussion(frSearch);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGetCountListWithDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setPortalId("p1");
		
		int result = frItemService.getCountListWithDiscussion(frSearch);
		assertNotSame(result, 0);
	}
	
	@Test
	public void testListLastWithDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setPortalId("p1");
		
		List<FrItem> result = frItemService.listLastWithDiscussion(frSearch);
		assertFalse(result.isEmpty());
	}
	
	
	@Test
	public void testGetCountListLastWithDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setPortalId("p1");
		
		int result = frItemService.getCountListLastWithDiscussion(frSearch);
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
			List<FrItem> result = frItemService.listPopular(frSearch);
			assertTrue(true);
		}catch(Exception e){
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
			int result = frItemService.getCountListPopular(frSearch);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testListRandomCategory() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId("p1");
		frSearch.setCategoryId(categoryId);
		
		List<FrItem> result = frItemService.listItemRandomCategory(frSearch);
		assertFalse(result.isEmpty());
	}
	
	
	@Test
	public void testUpdate() {
		
		frItem.setTitle("updateTile");
		frItem.setContents("upateContent");
		
		frItemService.update(frItem);
		
		FrItem result = frItemService.get(pk, frItem.getRegisterId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testUpdateFavoriteCount() {
		
		frItemService.updateFavoriteCount(frItem.getItemId(), 1);
		
		FrItem result = frItemService.get(pk, frItem.getRegisterId());
		assertTrue(result.getFavoriteCount() >= 0);
	}
	
	


	@Test
	public void testRemove() {
		
		frItemService.remove(pk);
		
		FrItem result = frItemService.get(pk, frItem.getRegisterId());
		assertNull(result);
		
	}
	
	
}
