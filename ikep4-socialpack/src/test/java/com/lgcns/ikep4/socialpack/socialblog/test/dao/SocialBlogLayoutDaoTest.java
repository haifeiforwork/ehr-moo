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

import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialCategoryDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBlogLayoutDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialBlogLayoutDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired
	private SocialBlogLayoutDao socialBlogLayoutDao;

	private SocialBlogLayout socialBlogLayout;

	private String layoutId;
	
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
		socialBlogLayout.setLayoutEnglishName("layoutEnglistName");
		
		socialBlogLayoutDao.create(socialBlogLayout);
		
	}

	@Test
	public void testCreate() {
		SocialBlogLayout result = socialBlogLayoutDao.get(this.layoutId);
		assertNotNull(result);
	}
	
	@Test
	public void testGet() {
		SocialBlogLayout result = socialBlogLayoutDao.get(this.layoutId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		boolean result = socialBlogLayoutDao.exists(this.layoutId);
		assertTrue(result);
		
	}
	
	
	@Test
	public void testListSocialBlogLayout() {
		
		List<SocialBlogLayout> result = socialBlogLayoutDao.listSocialBlogLayout();
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testCountSocialBlogLayout() {
		
		Integer count = socialBlogLayoutDao.countSocialBlogLayout();
		assertNotNull(count);
		
	}
	
	@Test
	public void testGetDefaultLayout() {
		
		SocialBlogLayout result = socialBlogLayoutDao.getDefaultLayout();
		assertNotNull(result);
		
	}
	
	@Test
	public void testIsDefaultLayout() {
		
		boolean result = socialBlogLayoutDao.isDefaultLayout(this.layoutId);
		assertFalse(result);
		
	}

	@Test
	public void testUpdate() {
		
		socialBlogLayout = new SocialBlogLayout();
		socialBlogLayout.setLayoutId(this.layoutId);
		socialBlogLayout.setLayoutName("layoutName");
		socialBlogLayout.setColCount(2);
		socialBlogLayout.setImageUrl("imageUrl");
		socialBlogLayout.setIsDefault("0");
		socialBlogLayout.setLayoutEnglishName("layoutEnglishName");
		
		socialBlogLayoutDao.update(socialBlogLayout);

		SocialBlogLayout result = socialBlogLayoutDao.get(this.layoutId);
		assertEquals(this.socialBlogLayout.getLayoutName(), result.getLayoutName());
		assertEquals(this.socialBlogLayout.getColCount(), result.getColCount());
		assertEquals(this.socialBlogLayout.getImageUrl(), result.getImageUrl());
		assertEquals(this.socialBlogLayout.getIsDefault(), result.getIsDefault());

	}

	@Test
	public void testPhysicalDelete() {
		
		socialBlogLayoutDao.physicalDelete(this.layoutId);
		SocialBlogLayout result = socialBlogLayoutDao.get(this.layoutId);
		assertNull(result);
		
	}

}
