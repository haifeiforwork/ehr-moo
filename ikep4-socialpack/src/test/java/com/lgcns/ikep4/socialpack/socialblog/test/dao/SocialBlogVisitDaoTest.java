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
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogVisitDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlogVisit;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * SocialBlogVisitDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBlogVisitDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialBlogVisitDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired
	private SocialBlogVisitDao socialBlogVisitDao;

	private SocialBlogVisit socialBlogVisit;

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
		socialBlog.setOwnerId("ownerId");
		socialBlog.setIntroduction("Introduction");
		socialBlog.setImageFileId("imageFileId");
		socialBlog.setImageUrl("imageUrl");
		socialBlog.setIsPrivateImage(0);
		
		socialBlogDao.create(socialBlog);
		
		socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		socialBlogVisit.setVisitorId("user39");
		
		socialBlogVisitDao.create(socialBlogVisit);
		
	}
	
	@Test
	public void testCreate() {
		
		socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		socialBlogVisit.setVisitFlag("TODAY");
		
		List<SocialBlogVisit> result = socialBlogVisitDao.selectAllByblogId(socialBlogVisit);
		assertFalse(result.isEmpty());
		
	}

	@Test
	public void testSelectAllByblogId() {
		
		socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		socialBlogVisit.setVisitFlag("TODAY");
		
		List<SocialBlogVisit> result = socialBlogVisitDao.selectAllByblogId(socialBlogVisit);
		assertFalse(result.isEmpty());
		
	}
	
	@Test
	public void testSelectAllCountByblogId() {
		
		socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		socialBlogVisit.setVisitFlag("TODAY");

		Integer count = socialBlogVisitDao.selectAllCountByblogId(socialBlogVisit);
		assertNotNull(count);
	}


	@Test
	public void testPhysicalDelete() {
		
		socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		socialBlogVisit.setVisitorId("visitorId");
		
		socialBlogVisitDao.physicalDelete(socialBlogVisit);
		SocialBlogVisit result = socialBlogVisitDao.get(socialBlogVisit);
		assertNull(result);
		
	}
	
	@Test
	public void testPhysicalDeleteThreadByBlogId() {
		
		socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		socialBlogVisit.setVisitorId("visitorId");
		
		socialBlogVisitDao.physicalDeleteThreadByBlogId(socialBlogVisit);
		SocialBlogVisit result = socialBlogVisitDao.get(socialBlogVisit);
		assertNull(result);
		
	}

	@Test
	public void testGetVisitorManage() {
		
		SocialBlogVisit socialBlogVisit = new SocialBlogVisit();
		socialBlogVisit.setBlogId(socialBlog.getBlogId());
		socialBlogVisit.setBaseDate(DateUtil.getToday("yyyy.MM.dd"));
		socialBlogVisit.setBaseDateType("DAILY");
		List<SocialBlogVisit> result = socialBlogVisitDao.getVisitorManage(socialBlogVisit);	
		
		assertFalse(result.isEmpty());
		
	}
	

}
