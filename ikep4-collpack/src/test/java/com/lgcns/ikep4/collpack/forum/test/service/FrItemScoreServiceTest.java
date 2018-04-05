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
import com.lgcns.ikep4.collpack.forum.model.FrItemScore;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionScoreService;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionService;
import com.lgcns.ikep4.collpack.forum.service.FrItemScoreService;
import com.lgcns.ikep4.collpack.forum.service.FrItemService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemScoreServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrItemScoreServiceTest extends BaseServiceTestCase {

	@Autowired
	private FrItemService frItemService;
	
	@Autowired
	private FrItemScoreService frItemScoreService;
	
	@Autowired
	private FrCategoryService frCategoryService;
	
	@Autowired
	private FrDiscussionService frDiscussionService;

	private FrItemScore frItemScore;
	
	private String pk;
	
	private String discussionId;

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
		
		frCategory.setCategoryId("10");
		frCategory.setCategoryName("카테고리 이름");
		frCategory.setRegisterId("user1");
		frCategory.setRegisterName("동");
		frCategory.setPortalId("p1");
		
		String categoryId = frCategoryService.create(frCategory);
		
		
		FrDiscussion frDiscussion = new FrDiscussion();
		
		frDiscussion.setCategoryId(categoryId);
		frDiscussion.setTitle("title");
		frDiscussion.setContents("contents");
		frDiscussion.setImageId("junitTest");
		frDiscussion.setUpdaterId("lee");
		frDiscussion.setUpdaterName("dong");
		frDiscussion.setRegisterId("dong");
		frDiscussion.setRegisterName("동");
		frDiscussion.setPortalId("p1");
		
		discussionId = frDiscussionService.create(frDiscussion);
		
		
		FrItem frItem = new FrItem();
		
		
		frItem.setDiscussionId(discussionId);
		frItem.setTitle("title");
		frItem.setContents("contents");
		frItem.setUpdaterId("lee");
		frItem.setUpdaterName("dong");
		frItem.setRegisterId("dong");
		frItem.setRegisterName("동");
		frItem.setPortalId("p1");
		
		pk =  frItemService.create(frItem);
		
		frItemScore = new FrItemScore();
		
		frItemScore.setItemId(pk);
		frItemScore.setItemScore(222);
		
		frItemScoreService.create(frItemScore);
		
	}


	@Test
	public void testRemoveAll() {
		
		try{
			frItemScoreService.removeAll();
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
	}
	
}
