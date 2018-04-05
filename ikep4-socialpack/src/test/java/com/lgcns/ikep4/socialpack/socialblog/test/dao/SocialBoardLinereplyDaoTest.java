package com.lgcns.ikep4.socialpack.socialblog.test.dao;

/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBlogDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardItemDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialBoardLinereplyDao;
import com.lgcns.ikep4.socialpack.socialblog.dao.SocialCategoryDao;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBlog;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardItem;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialBoardLinereply;
import com.lgcns.ikep4.socialpack.socialblog.model.SocialCategory;
import com.lgcns.ikep4.socialpack.socialblog.search.SocialBoardLinereplySearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * SocialBoardLinereplyDaoTest 테스트 케이스
 * 
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: SocialBoardLinereplyDaoTest.java 16246 2011-08-18 04:48:28Z giljae $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
                                "classpath*:/configuration/spring/context-dao.xml", 
                                "classpath*:/configuration/spring/context-service.xml" })
public class SocialBoardLinereplyDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

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
	private SocialBoardLinereplyDao socialBoardLinereplyDao;

	private SocialBoardLinereplySearchCondition socialBoardLinereplySearchCondition;
	
	private SocialBoardLinereply socialBoardLinereply;

	private String linereplyId;
	
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
		socialBoardItem.setRegisterId("ownerId");
		socialBoardItem.setRegisterName("registerName");
		
		socialBoardItemDao.create(socialBoardItem);
		
		socialBoardLinereply = new SocialBoardLinereply();
		this.linereplyId = idgenService.getNextId();
		socialBoardLinereply.setLinereplyId(this.linereplyId);
		socialBoardLinereply.setItemId(this.itemId);
		socialBoardLinereply.setLinereplyGroupId(this.linereplyId);
		socialBoardLinereply.setLinereplyParentId("");
		socialBoardLinereply.setStep(0);
		socialBoardLinereply.setIndentation(0);
		socialBoardLinereply.setContents("contents");
		socialBoardLinereply.setLinereplyDelete(0);
		socialBoardLinereply.setRegisterId("user39");
		socialBoardLinereply.setRegisterName("registerName");
		socialBoardLinereply.setUpdaterId("user39");
		socialBoardLinereply.setUpdaterName("updaterName");
		
		socialBoardLinereplyDao.create(socialBoardLinereply);
		
	}

	@Test
	public void testCreate() {
		SocialBoardLinereply result = socialBoardLinereplyDao.get(this.linereplyId);
		assertNotNull(result);
	}
	
	@Test
	public void testGet() {
		SocialBoardLinereply result = socialBoardLinereplyDao.get(this.linereplyId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		boolean result = socialBoardLinereplyDao.exists(this.linereplyId);
		assertTrue(result);
	}

	@Test
	public void testListBySearchCondition() {
		
		socialBoardLinereplySearchCondition = new SocialBoardLinereplySearchCondition();
		socialBoardLinereplySearchCondition.setLinereplyId(this.linereplyId);
		socialBoardLinereplySearchCondition.setItemId(this.itemId);
		socialBoardLinereplySearchCondition.setLinereplyGroupId(this.linereplyId);
		socialBoardLinereplySearchCondition.setStartRowIndex(0);
		socialBoardLinereplySearchCondition.setEndRowIndex(10);
				
		List<SocialBoardLinereply> result = socialBoardLinereplyDao.listBySearchCondition(socialBoardLinereplySearchCondition);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testCountBySearchCondition() {
		
		socialBoardLinereplySearchCondition = new SocialBoardLinereplySearchCondition();
		socialBoardLinereplySearchCondition.setLinereplyId(this.linereplyId);
		socialBoardLinereplySearchCondition.setItemId(this.itemId);
		socialBoardLinereplySearchCondition.setLinereplyGroupId(this.linereplyId);
		socialBoardLinereplySearchCondition.setStartRowIndex(0);
		socialBoardLinereplySearchCondition.setEndRowIndex(10);
				
		Integer count = socialBoardLinereplyDao.countBySearchCondition(socialBoardLinereplySearchCondition);
		assertNotNull(count);
	}
	
	
	@Test
	public void testCountChildren() {
		Integer count = socialBoardLinereplyDao.countChildren(this.linereplyId);
		assertNotNull(count);
	}
	

	@Test
	public void testUpdate() {
		
		socialBoardLinereply = new SocialBoardLinereply();
		socialBoardLinereply.setLinereplyId(this.linereplyId);
		socialBoardLinereply.setItemId(this.itemId);
		socialBoardLinereply.setLinereplyGroupId(this.linereplyId);
		//socialBoardLinereply.setLinereplyParentId("");
		socialBoardLinereply.setStep(0);
		socialBoardLinereply.setIndentation(0);
		socialBoardLinereply.setContents("contents");
		socialBoardLinereply.setLinereplyDelete(1);
		socialBoardLinereply.setUpdaterId("user39");
		socialBoardLinereply.setUpdaterName("updaterName");

		
		socialBoardLinereplyDao.update(socialBoardLinereply);
		SocialBoardLinereply result = socialBoardLinereplyDao.get(this.linereplyId);
		assertEquals(this.socialBoardLinereply.getLinereplyId(), result.getLinereplyId());
		assertEquals(this.socialBoardLinereply.getItemId(), result.getItemId());
		assertEquals(this.socialBoardLinereply.getLinereplyGroupId(), result.getLinereplyGroupId());
		assertEquals(this.socialBoardLinereply.getStep(), result.getStep());
		assertEquals(this.socialBoardLinereply.getIndentation(), result.getIndentation());
		assertEquals(this.socialBoardLinereply.getLinereplyDelete(), result.getLinereplyDelete());
		assertEquals(this.socialBoardLinereply.getUpdaterId(), result.getUpdaterId());
		assertEquals(this.socialBoardLinereply.getUpdaterName(), result.getUpdaterName());

	}

	@Test
	public void testUpdateStep() {
		
		socialBoardLinereply = new SocialBoardLinereply();
		socialBoardLinereply.setLinereplyId(this.linereplyId);
		socialBoardLinereply.setStep(0);
		
		socialBoardLinereplyDao.updateStep(socialBoardLinereply);
		SocialBoardLinereply result = socialBoardLinereplyDao.get(this.linereplyId);
		assertEquals(this.socialBoardLinereply.getLinereplyId(), result.getLinereplyId());
		assertNotSame(this.socialBoardLinereply.getStep(), result.getStep());

	}
	

	@Test
	public void testLogicalDelete() {
		
		socialBoardLinereply = new SocialBoardLinereply();
		socialBoardLinereply.setLinereplyId(this.linereplyId);
		socialBoardLinereply.setLinereplyDelete(1);
		socialBoardLinereply.setUpdaterId("user39");
		socialBoardLinereply.setUpdaterName("updaterName");

		
		socialBoardLinereplyDao.logicalDelete(socialBoardLinereply);
		SocialBoardLinereply result = socialBoardLinereplyDao.get(this.linereplyId);
		assertEquals(this.socialBoardLinereply.getLinereplyId(), result.getLinereplyId());
		assertEquals(this.socialBoardLinereply.getLinereplyDelete(), result.getLinereplyDelete());
		assertEquals(this.socialBoardLinereply.getUpdaterId(), result.getUpdaterId());
		assertEquals(this.socialBoardLinereply.getUpdaterName(), result.getUpdaterName());

	}
	
	@Test
	public void testPhysicalDelete() {
		socialBoardLinereplyDao.physicalDelete(this.linereplyId);
		SocialBoardLinereply result = socialBoardLinereplyDao.get(this.linereplyId);
		assertNull(result);
	}
	
	@Test
	public void testPhysicalDeleteThreadByLinereplyId() {
		socialBoardLinereplyDao.physicalDeleteThreadByLinereplyId(this.linereplyId);
		SocialBoardLinereply result = socialBoardLinereplyDao.get(this.linereplyId);
		assertNull(result);
	}
	
	@Test
	public void testphysicalDeleteThreadByItemId() {
		socialBoardLinereplyDao.physicalDeleteThreadByItemId(this.itemId);
		SocialBoardLinereply result = socialBoardLinereplyDao.get(this.linereplyId);
		assertNull(result);
	}
	
	@Test
	public void testListTop5Linereply() {
		
		Map<String, Object> recentCmtMap = new HashMap<String, Object>();
		recentCmtMap.put("blogOwnerId", "user39");
		recentCmtMap.put("rowTopXCount", 5);
		
		try{
			List<SocialBoardLinereply> result = socialBoardLinereplyDao.listTopXLinereply(recentCmtMap);
			assertTrue(true);
		} catch (Exception e){
			assertTrue(false);
		}
		
	}

}
