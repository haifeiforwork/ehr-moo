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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortletLayout;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialCategoryDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBlogPortletLayoutDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialBlogPortletLayoutDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired
	private SocialBlogPortletDao socialBlogPortletDao;

	private SocialBlogPortlet socialBlogPortlet;

	private String portletId;
	
	@Autowired
	private SocialBlogPortletLayoutDao socialBlogPortletLayoutDao;

	private SocialBlogPortletLayout socialBlogPortletLayout;

	private String portletLayoutId;
	
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
		
		
		socialBlogPortlet = new SocialBlogPortlet();
		this.portletId = idgenService.getNextId();
		socialBlogPortlet.setPortletId(this.portletId);
		socialBlogPortlet.setPortletName("portletName");
		socialBlogPortlet.setViewUrl("viewUrl");
		socialBlogPortlet.setIsDefault(1);
		socialBlogPortlet.setSortOrder(1);
		socialBlogPortlet.setPortletEnglishName("portletEnglishName");
		
		socialBlogPortletDao.create(socialBlogPortlet);
		
		
		socialBlogPortlet = new SocialBlogPortlet();
		this.portletId = idgenService.getNextId();
		socialBlogPortlet.setPortletId(this.portletId);
		socialBlogPortlet.setPortletName("portletName");
		socialBlogPortlet.setViewUrl("viewUrl");
		socialBlogPortlet.setIsDefault(1);
		socialBlogPortlet.setSortOrder(1);
		socialBlogPortlet.setPortletEnglishName("portletEnglishName");
		
		socialBlogPortletDao.create(socialBlogPortlet);
		
		
		socialBlogPortletLayout = new SocialBlogPortletLayout();
		this.portletLayoutId = idgenService.getNextId();
		socialBlogPortletLayout.setPortletLayoutId(this.portletLayoutId);
		socialBlogPortletLayout.setBlogId(this.blogId);
		socialBlogPortletLayout.setPortletId(this.portletId);
		socialBlogPortletLayout.setColIndex(1);
		socialBlogPortletLayout.setRowIndex(1);
		
		socialBlogPortletLayoutDao.create(socialBlogPortletLayout);
		
	}

	@Test
	public void testCreate() {
		SocialBlogPortletLayout result = socialBlogPortletLayoutDao.get(this.portletLayoutId);
		assertNotNull(result);
	}
	
	@Test
	public void testGet() {
		SocialBlogPortletLayout result = socialBlogPortletLayoutDao.get(this.portletLayoutId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		boolean result = socialBlogPortletLayoutDao.exists(this.portletLayoutId);
		assertTrue(result);
		
	}
	
	
	@Test
	public void testListByBlogId() {
		
		List<SocialBlogPortletLayout> result = socialBlogPortletLayoutDao.listByBlogId(this.blogId);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testCountByBlogId() {
		
		Integer count = socialBlogPortletLayoutDao.countByBlogId(this.blogId);
		assertNotNull(count);
		
	}

	@Test
	public void testUpdate() {
		
		socialBlogPortletLayout = new SocialBlogPortletLayout();
		socialBlogPortletLayout.setPortletLayoutId(this.portletLayoutId);
		socialBlogPortletLayout.setBlogId(this.blogId);
		socialBlogPortletLayout.setPortletId(this.portletId);
		socialBlogPortletLayout.setColIndex(2);
		socialBlogPortletLayout.setRowIndex(3);
		
		socialBlogPortletLayoutDao.update(socialBlogPortletLayout);

		SocialBlogPortletLayout result = socialBlogPortletLayoutDao.get(this.portletLayoutId);
		assertEquals(this.socialBlogPortletLayout.getColIndex(), result.getColIndex());
		assertEquals(this.socialBlogPortletLayout.getRowIndex(), result.getRowIndex());

	}
	
	@Test
	public void testPhysicalDeleteThreadByBlogId() {
		socialBlogPortletLayoutDao.physicalDeleteThreadByBlogId(this.blogId);
		SocialBlogPortletLayout result = socialBlogPortletLayoutDao.get(this.portletLayoutId);
		assertNull(result);
	}
	

}
