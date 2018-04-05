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

import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogPortletDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogPortlet;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialCategoryDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBlogPortletDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialBlogPortletDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired
	private SocialBlogPortletDao socialBlogLPortletDao;

	private SocialBlogPortlet socialBlogPortlet;

	private String portletId;
	
	@Autowired
	private IdgenService idgenService;

	@Before
	public void setUp() {
		
		
		socialBlogPortlet = new SocialBlogPortlet();
		this.portletId = idgenService.getNextId();
		socialBlogPortlet.setPortletId(this.portletId);
		socialBlogPortlet.setPortletName("portletName");
		socialBlogPortlet.setViewUrl("viewUrl");
		socialBlogPortlet.setIsDefault(1);
		socialBlogPortlet.setSortOrder(1);
		socialBlogPortlet.setPortletEnglishName("portletEnglishName");
		
		
		socialBlogLPortletDao.create(socialBlogPortlet);
		
	}

	@Test
	public void testCreate() {
		SocialBlogPortlet result = socialBlogLPortletDao.get(this.portletId);
		assertNotNull(result);
	}
	
	@Test
	public void testGet() {
		SocialBlogPortlet result = socialBlogLPortletDao.get(this.portletId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		boolean result = socialBlogLPortletDao.exists(this.portletId);
		assertTrue(result);
		
	}
	
	
	@Test
	public void testListAllPortlet() {
		
		SocialBlogPortlet socialBlogPortlet = new SocialBlogPortlet();
		socialBlogPortlet.setIsDefault(-1);
		List<SocialBlogPortlet> result = socialBlogLPortletDao.listAllPortlet(socialBlogPortlet);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testCountAllPortlet() {
		
		SocialBlogPortlet socialBlogPortlet = new SocialBlogPortlet();
		socialBlogPortlet.setIsDefault(-1);
		Integer count = socialBlogLPortletDao.countAllPortlet(socialBlogPortlet);
		assertNotNull(count);
		
	}

	@Test
	public void testUpdate() {
		
		socialBlogPortlet = new SocialBlogPortlet();
		socialBlogPortlet.setPortletId(this.portletId);
		socialBlogPortlet.setPortletName("portletName11");
		socialBlogPortlet.setViewUrl("viewUrl11");
		socialBlogPortlet.setIsDefault(0);
		socialBlogPortlet.setSortOrder(1);
		socialBlogPortlet.setPortletEnglishName("portletEnglishName");
		
		socialBlogLPortletDao.update(socialBlogPortlet);

		SocialBlogPortlet result = socialBlogLPortletDao.get(this.portletId);
		assertEquals(this.socialBlogPortlet.getPortletName(), result.getPortletName());
		assertEquals(this.socialBlogPortlet.getViewUrl(), result.getViewUrl());
		assertEquals(this.socialBlogPortlet.getIsDefault(), result.getIsDefault());
		assertEquals(this.socialBlogPortlet.getSortOrder(), result.getSortOrder());

	}

}
