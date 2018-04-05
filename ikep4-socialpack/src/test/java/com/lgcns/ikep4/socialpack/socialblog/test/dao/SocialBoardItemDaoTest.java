package com.lgcns.ikep4.socialpack.socialblog.test.dao;

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardItemSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialBoardItemDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBoardItemDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialBoardItemDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SocialCategoryDao socialCategoryDao;

	private SocialCategory socialCategory;

	private String categoryId;
	
	@Autowired
	private SocialBlogDao socialBlogDao;

	private SocialBlog socialBlog;

	private String blogId;
	
	@Autowired
	private SocialBoardItemDao socialBoardItemDao;

	private SocialBoardItem socialBoardItem;
	
	private SocialBoardItemSearchCondition socialBoardItemSearchCondition;

	private String itemId;
	
	
	//private UserDao userdao;
	//private User user;
	
	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {
		
		//user = new User();
		//user.setUserId("testuserId");
		//user.setUserName("testuserName");
		//user.setUserPassword("userPassword");
		//user.setUserStatus("C");
		//user.setUserEnglishName("userEnglishName");
		//user.setTeamName("teamName");
		//user.setLeader("0");
		//user.setRegisterId("registerId");
		//user.setRegisterName("registerName");
		//user.setUpdaterId("updaterId");
		//user.setUpdaterName("updaterName");
		//userdao.create(user);
		
		socialBlog = new SocialBlog();
		this.blogId = idgenService.getNextId();
		socialBlog.setBlogId(this.blogId);
		socialBlog.setOwnerId("user39");
		socialBlog.setIntroduction("Introduction");
		socialBlog.setImageFileId("imageFileId");
		socialBlog.setImageUrl("imageUrl");
		socialBlog.setIsPrivateImage(0);
		
		socialBlogDao.create(socialBlog);
		
		socialCategory = new SocialCategory();
		this.categoryId = idgenService.getNextId();
		socialCategory.setCategoryId(this.categoryId);
		socialCategory.setCategoryName("categoryName");
		socialCategory.setSortOrder(0);
		socialCategory.setBlogId(this.blogId);
		
		socialCategoryDao.create(socialCategory);
		
		socialBoardItem = new SocialBoardItem();
		this.itemId = idgenService.getNextId();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setTitle("title");
		socialBoardItem.setContents("contents");
		socialBoardItem.setCategoryId(this.categoryId);
		socialBoardItem.setRecommendCount(0);
		socialBoardItem.setLinereplyCount(0);
		socialBoardItem.setAttachFileCount(0);
		socialBoardItem.setHitCount(0);
		socialBoardItem.setReadPermission("0");
		socialBoardItem.setRegisterId("user39");
		socialBoardItem.setRegisterName("testuserName");
		
		socialBoardItemDao.create(socialBoardItem);
		
	}

	@Test
	public void testCreate() {
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setSessUserLocale("ko");
		
		SocialBoardItem result = socialBoardItemDao.get(socialBoardItem);
		assertNotNull(result);
	}
	
	@Test
	public void testSelectItemBySearchCondition() {
		
		socialBoardItemSearchCondition = new SocialBoardItemSearchCondition();
		socialBoardItemSearchCondition.setRegisterId("user39");
		socialBoardItemSearchCondition.setItemId(this.itemId);
		socialBoardItemSearchCondition.setCategoryId(this.categoryId);
		socialBoardItemSearchCondition.setReadPermission("0");
		socialBoardItemSearchCondition.setStartRowIndex(0);
		socialBoardItemSearchCondition.setEndRowIndex(10);
		
		List<SocialBoardItem> result = socialBoardItemDao.listBySearchCondition(socialBoardItemSearchCondition);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testSelectItemCountBySearchCondition() {
		
		socialBoardItemSearchCondition = new SocialBoardItemSearchCondition();
		socialBoardItem.setRegisterId("registerId");
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setCategoryId(this.categoryId);
		socialBoardItem.setReadPermission("0");
		
		Integer count = socialBoardItemDao.countBySearchCondition(socialBoardItemSearchCondition);
		assertNotNull(count);
	}

	@Test
	public void testUpdate() {
		
		socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setTitle("title");
		socialBoardItem.setContents("contents");
		socialBoardItem.setCategoryId(this.categoryId);
		socialBoardItem.setReadPermission("0");

		socialBoardItemDao.update(socialBoardItem);
		
		SocialBoardItem getSocialBoardItem = new SocialBoardItem();
		getSocialBoardItem.setItemId(this.itemId);
		getSocialBoardItem.setSessUserLocale("ko");
		SocialBoardItem result = socialBoardItemDao.get(getSocialBoardItem);
		
		assertEquals(this.socialBoardItem.getTitle(), result.getTitle());
		assertEquals(this.socialBoardItem.getContents(), result.getContents());
		assertEquals(this.socialBoardItem.getCategoryId(), result.getCategoryId());
		assertEquals(this.socialBoardItem.getReadPermission(), result.getReadPermission());

	}

	@Test
	public void testGet() {
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setSessUserLocale("ko");
		
		SocialBoardItem result = socialBoardItemDao.get(socialBoardItem);
		assertNotNull(result);
	}
	
	@Test
	public void testPhysicalDelete() {
		socialBoardItemDao.physicalDelete(this.itemId);
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setSessUserLocale("ko");
		SocialBoardItem result = socialBoardItemDao.get(socialBoardItem);
		assertNull(result);
	}
	
	@Test
	public void testUpdateRecommendCount() {
		socialBoardItemDao.updateRecommendCount(this.itemId);
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setSessUserLocale("ko");
		
		SocialBoardItem result = socialBoardItemDao.get(socialBoardItem);
		assertEquals(this.socialBoardItem.getRecommendCount(), result.getRecommendCount());
	}
	
	
	@Test
	public void testUpdateLinereplyCount() {
		socialBoardItemDao.updateLinereplyCount(this.itemId);
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setSessUserLocale("ko");
		
		SocialBoardItem result = socialBoardItemDao.get(socialBoardItem);
		assertEquals(this.socialBoardItem.getLinereplyCount(), result.getLinereplyCount());
	}
	
	@Test
	public void testUpdateMailCount() {
		socialBoardItemDao.updateMailCount(this.itemId);
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setSessUserLocale("ko");
		
		SocialBoardItem result = socialBoardItemDao.get(socialBoardItem);
		assertEquals(this.socialBoardItem.getLinereplyCount(), result.getLinereplyCount());
	}
	
	
	@Test
	public void testUpdateMBlogCount() {
		socialBoardItemDao.updateMBlogCount(this.itemId);
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setSessUserLocale("ko");
		
		SocialBoardItem result = socialBoardItemDao.get(socialBoardItem);
		assertEquals(this.socialBoardItem.getLinereplyCount(), result.getLinereplyCount());
	}
	
	@Test
	public void testUpdateHitCount() {
		socialBoardItemDao.updateHitCount(this.itemId);
		
		SocialBoardItem socialBoardItem = new SocialBoardItem();
		socialBoardItem.setItemId(this.itemId);
		socialBoardItem.setSessUserLocale("ko");
		
		SocialBoardItem result = socialBoardItemDao.get(socialBoardItem);
		assertEquals(this.socialBoardItem.getHitCount(), result.getHitCount());
	}
	
}
