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

import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutColumnDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogLayoutDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayout;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogLayoutColumn;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialCategoryDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBlogLayoutColumnDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialBlogLayoutColumnDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SocialBlogLayoutDao socialBlogLayoutDao;

	private SocialBlogLayout socialBlogLayout;

	private String layoutId;

	@Autowired
	private SocialBlogLayoutColumnDao socialBlogLayoutColumnDao;

	private SocialBlogLayoutColumn socialBlogLayoutColumn;

	private String categoryId;
	
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
		
		socialBlogLayoutColumn = new SocialBlogLayoutColumn();
		socialBlogLayoutColumn.setLayoutId(this.layoutId);
		socialBlogLayoutColumn.setColIndex(1);
		socialBlogLayoutColumn.setWidth(100);
		socialBlogLayoutColumn.setIsFixed(1);
		
		socialBlogLayoutColumnDao.create(socialBlogLayoutColumn);
		
	}

	@Test
	public void testCreate() {
		
		socialBlogLayoutColumn = new SocialBlogLayoutColumn();
		socialBlogLayoutColumn.setLayoutId(this.layoutId);
		socialBlogLayoutColumn.setColIndex(1);
		SocialBlogLayoutColumn result = socialBlogLayoutColumnDao.get(socialBlogLayoutColumn);
		
		assertNotNull(result);
	}
	
	@Test
	public void testGet() {

		socialBlogLayoutColumn = new SocialBlogLayoutColumn();
		socialBlogLayoutColumn.setLayoutId(this.layoutId);
		socialBlogLayoutColumn.setColIndex(1);
		SocialBlogLayoutColumn result = socialBlogLayoutColumnDao.get(socialBlogLayoutColumn);
		
		assertNotNull(result);
		
	}

	@Test
	public void testExists() {
		
		socialBlogLayoutColumn = new SocialBlogLayoutColumn();
		socialBlogLayoutColumn.setLayoutId(this.layoutId);
		socialBlogLayoutColumn.setColIndex(1);
		boolean result = socialBlogLayoutColumnDao.exists(socialBlogLayoutColumn);
		
		assertTrue(result);
		
	}
	
	
	@Test
	public void testListByLayoutId() {
		
		List<SocialBlogLayoutColumn> result = socialBlogLayoutColumnDao.listByLayoutId(this.layoutId);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testCountByLayoutId() {
		
		Integer count = socialBlogLayoutColumnDao.countByLayoutId(this.layoutId);
		assertNotNull(count);
		
	}
	
	
	@Test
	public void getFixedLayoutColumn() {
		
		Integer result = socialBlogLayoutColumnDao.getFixedLayoutColumn(this.layoutId);
		assertNotNull(result);
		
	}

	@Test
	public void testUpdate() {
		
		socialBlogLayoutColumn = new SocialBlogLayoutColumn();
		socialBlogLayoutColumn.setLayoutId(this.layoutId);
		socialBlogLayoutColumn.setColIndex(1);
		socialBlogLayoutColumn.setWidth(200);
		socialBlogLayoutColumn.setIsFixed(1);
		
		socialBlogLayoutColumnDao.update(socialBlogLayoutColumn);

		SocialBlogLayoutColumn result = socialBlogLayoutColumnDao.get(socialBlogLayoutColumn);
		assertEquals(this.socialBlogLayoutColumn.getWidth(), result.getWidth());
		assertEquals(this.socialBlogLayoutColumn.getIsFixed(), result.getIsFixed());

	}

	@Test
	public void testPhysicalDelete() {
		
		socialBlogLayoutColumn = new SocialBlogLayoutColumn();
		socialBlogLayoutColumn.setLayoutId(this.layoutId);
		socialBlogLayoutColumn.setColIndex(1);
		
		socialBlogLayoutColumnDao.physicalDelete(socialBlogLayoutColumn);
		SocialBlogLayoutColumn result = socialBlogLayoutColumnDao.get(socialBlogLayoutColumn);
		assertNull(result);
		
	}

}
