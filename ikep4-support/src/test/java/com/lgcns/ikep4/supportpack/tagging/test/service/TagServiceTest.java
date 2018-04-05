/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.tagging.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagServiceTest.java 16904 2011-10-25 01:39:09Z giljae $
 */
public class TagServiceTest extends BaseServiceTestCase {

	@Autowired
	private TagService tagService;

	private Tag tag;
	
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
		user.setTimeDifference("1");
		session.setAttribute("ikep.user", user); // User 모델을 ikep.user라는 키로 세션에 저장합니다.
		
		req.setSession(session);

	    // RequestContextHolder에 위에서 작업한 request 객체를 저장합니다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(req));
		
		
		tag = new Tag();
		
		tag.setTagName("수영, 국가");
		tag.setTagItemId("2");
		tag.setTagItemType("qa");
		tag.setTagItemName("게시글입니다.");
		tag.setTagItemUrl("http://");
		tag.setRegisterId("user1");
		tag.setRegisterName("lee dong");
		tag.setPortalId("P000001");
		
		pk = tagService.create(tag);
	}

	@Test
	//@Rollback(false)
	public void testList() {
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("P000001");
		//tagSearch.setTagItemType("qa");
		tagSearch.setRegisterId("user1");
		tagSearch.setStartRowIndex(0);
		tagSearch.setEndRowIndex(100);
		tagSearch.setLimitDate("1");
		
//		List<String> userIdList = new ArrayList<String>();
//		userIdList.add("user1");
//		tagSearch.setUserIdList(userIdList);
		
		List<Tag> list = tagService.list(tagSearch);
		
		assertNotNull(list);
	}
	
	
	@Test
	public void testListByIsSubtype() {
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("P000001");
		tagSearch.setTagItemType(TagConstants.ITEM_TYPE_WORKSPACE);
		tagSearch.setStartRowIndex(0);
		tagSearch.setEndRowIndex(100);
		
		tagSearch.setIsNotSubType(TagConstants.IS_NOT_SUB_TYPE);
		//tagSearch.setIsSubType(TagConstants.IS_SUB_TYPE);
		
		List<Tag> list = tagService.list(tagSearch);
		
		assertNotNull(list);
		
		
		int count = tagService.getCount(tagSearch);
		
		assertNotNull(count);
	}
	
	
	
	@Test
	public void testListTagSearchByIsSubType() {
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("P000001");
		tagSearch.setTagItemType("WS,QA,CV");
		tagSearch.setIsSubType(TagConstants.IS_SUB_TYPE);
		tagSearch.setTagId("100000678411");
		tagSearch.setStartRowIndex(0);
		tagSearch.setEndRowIndex(100);
		
		List<Tag> list = tagService.listTagSearch(tagSearch);
		
		assertNotNull(list);
		
		
		int count = tagService.getCountSearch(tagSearch);
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testListTagSearch() {
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("P000001");
		tagSearch.setTagItemType("qa");
		tagSearch.setTagName("영화");
		tagSearch.setStartRowIndex(0);
		tagSearch.setEndRowIndex(100);
		
		List<Tag> list = tagService.listTagSearch(tagSearch);
		
		assertNotNull(list);
	}
	
	@Test
	public void testCountSearch() {
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("P000001");
		tagSearch.setTagItemType("qa");
		tagSearch.setTagName("게시판");
		tagSearch.setStartRowIndex(0);
		tagSearch.setEndRowIndex(100);
		
		int count = tagService.getCountSearch(tagSearch);		
		assertNotNull(count);
	}
	
	
	@Test
	public void testListTag() {
		
		List<Tag> list = tagService.listTagByItemId(tag.getTagItemId(), tag.getTagItemType());
		
		assertNotNull(list);
	}
	
	@Test
	public void testListItemId() {
		
		
		
		Map<String, Object> itemIdMap = tagService.listItemId(tag.getTagItemId(), tag.getTagItemType(), 1, 10);
		
		int count = (Integer)itemIdMap.get("count");
		List<String> list = (List<String>)itemIdMap.get("list");
		
		assertNotNull(list);
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testListItemIdByTag() {
		
		Tag searchTag = new Tag();
		searchTag.setTagItemId(tag.getTagItemId());
		searchTag.setTagItemType(tag.getTagItemType()+",PE");
		searchTag.setPageIndex(1);
		searchTag.setPagePer(10);
		searchTag.setGroupType("tagItemSubType");
		
		Map<String, Object> itemIdMap = tagService.listItemId(searchTag);
		
		int count = (Integer)itemIdMap.get("count");
		List<String> list = (List<String>)itemIdMap.get("list");
		
		assertNotNull(list);
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testListItemIdAndTagId() {
		
		Map<String, Object> itemIdMap = tagService.listItemId("123", tag.getTagItemId(), tag.getTagItemType(), 1, 10);
		
		int count = (Integer)itemIdMap.get("count");
		List<String> list = (List<String>)itemIdMap.get("list");
		
		assertNotNull(list);
		
		assertNotNull(count);
	}
	
	
	
	@Test
	public void testListItemIdAsRandom() {
		
		Tag searchTag = new Tag();
		searchTag.setTagItemId("user1");
		searchTag.setTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO);
		searchTag.setEndRowIndex(2);
		searchTag.setAsTagItemType(TagConstants.ITEM_TYPE_PROFILE_PRO);
		
		List<String> list = tagService.listItemIdAsRandom(searchTag);
		
		
		assertNotNull(list);
		
	}
	
	
	@Test
	public void testListItemByTagIdList() {
		
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("P000001");
		
		List userList = new ArrayList();
		userList.add("user1");
		userList.add("user2");
		userList.add("user3");
		tagSearch.setTagIdList(userList);
		tagSearch.setStartRowIndex(0);
		tagSearch.setEndRowIndex(10);
		tagSearch.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);
		
		
		List<Tag> list = tagService.list(tagSearch);
		
		Map<String, Object> itemIdMap = tagService.listItemByTagList(list, 1, 10);
		
		int count = (Integer)itemIdMap.get("count");
		List<Tag> tagList = (List<Tag>)itemIdMap.get("list");
		
		assertNotNull(tagList);
		
		assertNotNull(count);
	}
	
	
	
	@Test
	public void testListItemByTagList() {
		
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("P000001");
		List userList = new ArrayList();
		userList.add("user1");
		userList.add("user2");
		userList.add("user3");
		tagSearch.setUserIdList(userList);
		tagSearch.setStartRowIndex(0);
		tagSearch.setEndRowIndex(10);
		tagSearch.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY);
		
		
		List<Tag> list = tagService.list(tagSearch);
		
		Map<String, Object> itemIdMap = tagService.listItemByTagList(list, 1, 10);
		
		int count = (Integer)itemIdMap.get("count");
		List<Tag> tagList = (List<Tag>)itemIdMap.get("list");
		
		assertNotNull(tagList);
		
		assertNotNull(count);
	}
	
	@Test
	public void testCount() {
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("p1");
		tagSearch.setTagItemType("qa");
		tagSearch.setStartRowIndex(0);
		tagSearch.setEndRowIndex(100);
		
		List<String> userIdList = new ArrayList<String>();
		userIdList.add("user1");
		tagSearch.setUserIdList(userIdList);
		
		int count = tagService.getCount(tagSearch);
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testListItemAsCount() {
		
		
		Tag tagSearch = new Tag();
		tagSearch.setPortalId("P000001");
		tagSearch.setTagItemType("PE");
		tagSearch.setTagName("영화");
		
		
		Map<String, Object> itemIdMap = tagService.listItemAsCount(tagSearch, 1, 10);
		
		int count = (Integer)itemIdMap.get("count");
		List<Tag> tagList = (List<Tag>)itemIdMap.get("list");
		
		assertNotNull(tagList);
		
		assertNotNull(count);
	}
	
	@Test
	public void testUpdate() {
		
		tag.setTagItemName("update");
		tagService.update(tag);
		
		
		List<Tag> list = tagService.listTagByItemId(tag.getTagItemId(), tag.getTagItemType());
		
		assertNotNull(list);
	}

	@Test
	public void testDelete() {
		
		tagService.delete(tag.getTagItemId(), tag.getTagItemType());
		
		List<Tag> list = tagService.listTagByItemId(tag.getTagItemId(), tag.getTagItemType());
		
		assertTrue(list.size() == 0);
	}
	
	@Test
	public void testListTeamType() {
		
		List<Tag> result = tagService.listTeamType(tag.getPortalId());
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testListItemCount() {
		
		List<Tag> result = tagService.listItemCount(tag.getTagItemId(), tag.getTagItemType());
		
		
		
		assertNotNull(result);
	}

	
}
