package com.lgcns.ikep4.socialpack.socialblog.test.dao;

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemRecommendDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItemRecommend;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialBoardItemRecommendDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBoardItemRecommendDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialBoardItemRecommendDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

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

	private String itemId;

	@Autowired
	private SocialBoardItemRecommendDao socialBoardItemRecommendDao;

	private SocialBoardItemRecommend socialBoardItemRecommend;


	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {
		
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
		socialBoardItem.setRegisterName("registerName");
		
		socialBoardItemDao.create(socialBoardItem);
		
		socialBoardItemRecommend = new SocialBoardItemRecommend();
		socialBoardItemRecommend.setRegisterId("user39");
		socialBoardItemRecommend.setItemId(itemId);
		
		socialBoardItemRecommendDao.create(socialBoardItemRecommend);
		
	}

	@Test
	public void testCreate() {
		
		socialBoardItemRecommend = new SocialBoardItemRecommend();
		socialBoardItemRecommend.setRegisterId("user39");
		socialBoardItemRecommend.setItemId(itemId);
		SocialBoardItemRecommend result = socialBoardItemRecommendDao.get(socialBoardItemRecommend);
		assertNotNull(result);
		
	}
	
	@Test
	public void testGet() {
		socialBoardItemRecommend = new SocialBoardItemRecommend();
		socialBoardItemRecommend.setRegisterId("user39");
		socialBoardItemRecommend.setItemId(itemId);
		SocialBoardItemRecommend result = socialBoardItemRecommendDao.get(socialBoardItemRecommend);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		socialBoardItemRecommend = new SocialBoardItemRecommend();
		socialBoardItemRecommend.setRegisterId("user39");
		socialBoardItemRecommend.setItemId(itemId);
		boolean result = socialBoardItemRecommendDao.exists(socialBoardItemRecommend);
		assertTrue(result);
	}

	@Test
	public void testPhysicalDelete() {
		socialBoardItemRecommend = new SocialBoardItemRecommend();
		socialBoardItemRecommend.setRegisterId("user39");
		socialBoardItemRecommend.setItemId(itemId);
		socialBoardItemRecommendDao.physicalDelete(socialBoardItemRecommend);

		SocialBoardItemRecommend result = socialBoardItemRecommendDao.get(socialBoardItemRecommend);
		assertNull(result);
	}

}
