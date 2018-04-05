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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialBlogDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBlogDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })                        
public class SocialBlogDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	
	@Autowired
	private SocialBlogLayoutDao socialBlogLayoutDao;

	private SocialBlogLayout socialBlogLayout;

	private String layoutId;

	@Autowired
	private SocialBlogDao socialBlogDao;

	private SocialBlog socialBlog;

	private String blogId;
	
	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {
		
		socialBlogLayout = new SocialBlogLayout();
		this.layoutId = idgenService.getNextId();
		socialBlogLayout.setLayoutId(this.layoutId);
		socialBlogLayout.setLayoutName("layoutName");
		socialBlogLayout.setColCount(3);
		socialBlogLayout.setImageUrl("imageUrl");
		socialBlogLayout.setIsDefault("0");
		socialBlogLayout.setLayoutEnglishName("layoutEnglishName");
		
		socialBlogLayoutDao.create(socialBlogLayout);
		
		socialBlog = new SocialBlog();
		this.blogId = idgenService.getNextId();
		socialBlog.setBlogId(this.blogId);
		socialBlog.setOwnerId("ownerId1");
		socialBlog.setIntroduction("Introduction");
		socialBlog.setImageFileId("imageFileId");
		socialBlog.setImageUrl("imageUrl");
		socialBlog.setIsPrivateImage(0);
		socialBlog.setLayoutId(this.layoutId);
		
		socialBlogDao.create(socialBlog);
		
	}

	@Test
	public void testCreate() {
		socialBlog = new SocialBlog();
		socialBlog.setBlogId(this.blogId);
		socialBlog.setOwnerId("ownerId1");
		
		SocialBlog result = socialBlogDao.select(socialBlog);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		
		socialBlog = new SocialBlog();
		socialBlog.setBlogId(this.blogId);
		socialBlog.setOwnerId("ownerId1");
		socialBlog.setIntroduction("Introduction");
		socialBlog.setImageFileId("imageFileId");
		socialBlog.setImageUrl("imageUrl");
		socialBlog.setIsPrivateImage(0);
		
		socialBlogDao.update(socialBlog);
		SocialBlog result = socialBlogDao.select(socialBlog);
		
		assertEquals(this.socialBlog.getBlogId(), result.getBlogId());
		assertEquals(this.socialBlog.getOwnerId(), result.getOwnerId());
		assertEquals(this.socialBlog.getImageFileId(), result.getImageFileId());
		assertEquals(this.socialBlog.getImageUrl(), result.getImageUrl());
		assertEquals(this.socialBlog.getIsPrivateImage(), result.getIsPrivateImage());
		assertEquals(this.socialBlog.getIntroduction(), result.getIntroduction());

	}
	
	@Test
	public void testUpdateIntroduction() {
		
		socialBlog = new SocialBlog();
		socialBlog.setBlogId(this.blogId);
		socialBlog.setOwnerId("ownerId1");
		socialBlog.setIntroduction("Introduction");
		
		socialBlogDao.updateIntroduction(socialBlog);
		SocialBlog result = socialBlogDao.select(socialBlog);
		
		assertEquals(this.socialBlog.getBlogId(), result.getBlogId());
		assertEquals(this.socialBlog.getOwnerId(), result.getOwnerId());
		assertEquals(this.socialBlog.getIntroduction(), result.getIntroduction());

	}
	
	@Test
	public void testUpdateBlogBgImage() {
		
		socialBlog = new SocialBlog();
		socialBlog.setBlogId(this.blogId);
		socialBlog.setOwnerId("ownerId1");

		socialBlog.setImageFileId("imageFileId");
		socialBlog.setImageUrl("imageUrl");
		socialBlog.setIsPrivateImage(0);
		
		socialBlogDao.update(socialBlog);
		SocialBlog result = socialBlogDao.select(socialBlog);
		
		assertEquals(this.socialBlog.getBlogId(), result.getBlogId());
		assertEquals(this.socialBlog.getOwnerId(), result.getOwnerId());
		assertEquals(this.socialBlog.getImageFileId(), result.getImageFileId());
		assertEquals(this.socialBlog.getImageUrl(), result.getImageUrl());
		assertEquals(this.socialBlog.getIsPrivateImage(), result.getIsPrivateImage());

	}

	

	@Test
	public void testGet() {
		socialBlog = new SocialBlog();
		socialBlog.setBlogId(this.blogId);
		socialBlog.setOwnerId("ownerId1");
		
		SocialBlog result = socialBlogDao.select(socialBlog);
		assertNotNull(result);
		
	}

	@Test
	public void testExists() {
		boolean result = socialBlogDao.exists(socialBlog);
		assertTrue(result);
	}


}
