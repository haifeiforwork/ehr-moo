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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialCategoryDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialCategoryDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialCategoryDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired
	private SocialCategoryDao socialCategoryDao;

	private SocialCategory socialCategory;

	private String categoryId;
	
	@Autowired
	private SocialBlogDao socialBlogDao;

	private SocialBlog socialBlog;

	private String blogId;
	
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
		
	}

	@Test
	public void testCreate() {
		SocialCategory result = socialCategoryDao.get(this.categoryId);
		assertNotNull(result);
	}
	
	@Test
	public void testGet() {
		SocialCategory result = socialCategoryDao.get(this.categoryId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		boolean result = socialCategoryDao.exists(this.categoryId);
		assertTrue(result);
		
	}
	
	
	@Test
	public void testListByBlogId() {
		
		List<SocialCategory> result = socialCategoryDao.listByBlogId(this.blogId);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testCountBlogId() {
		
		Integer count = socialCategoryDao.countBlogId(this.blogId);
		assertNotNull(count);
		
	}
	
	@Test
	public void testGetMaxSortOrder() {
		
		Integer count = socialCategoryDao.getMaxSortOrder(this.blogId);
		assertNotNull(count);
		
	}

	@Test
	public void testUpdate() {
		
		socialCategory = new SocialCategory();
		socialCategory.setCategoryId(this.categoryId);
		socialCategory.setCategoryName("categoryName111");
		socialCategory.setSortOrder(1);
		socialCategory.setBlogId(this.blogId);
		
		socialCategoryDao.update(socialCategory);

		SocialCategory result = socialCategoryDao.get(this.categoryId);
		assertEquals(this.socialCategory.getCategoryId(), result.getCategoryId());
		assertEquals(this.socialCategory.getCategoryName(), result.getCategoryName());
		assertEquals(this.socialCategory.getSortOrder(), result.getSortOrder());
		assertEquals(this.socialCategory.getBlogId(), result.getBlogId());

	}

	@Test
	public void testPhysicalDelete() {
		
		socialCategoryDao.physicalDelete(this.categoryId);
		SocialCategory result = socialCategoryDao.get(this.categoryId);
		assertNull(result);
		
	}

}
