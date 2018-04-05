/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrCategoryDao;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionScoreDao;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussionScore;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrDiscussionScoreDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrDiscussionScoreDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrDiscussionScoreDao frDiscussionScoreDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	@Autowired
	private FrCategoryDao frCategoryDao;

	private FrDiscussionScore frDiscussionScore;

	private String pk;

	@Before
	public void setUp() {
		
		
		FrCategory frCategory = new FrCategory();
		
		frCategory.setCategoryId("10");
		frCategory.setCategoryName("카테고리 이름");
		frCategory.setRegisterId("user1");
		frCategory.setRegisterName("동");
		frCategory.setPortalId("p1");
		
		frCategoryDao.create(frCategory);
		
		
		FrDiscussion frDiscussion = new FrDiscussion();
		
		pk = "20";
		
		frDiscussion.setDiscussionId(pk);
		frDiscussion.setCategoryId(frCategory.getCategoryId());
		frDiscussion.setTitle("title");
		frDiscussion.setContents("contents");
		frDiscussion.setImageId("img");
		frDiscussion.setUpdaterId("lee");
		frDiscussion.setUpdaterName("dong");
		frDiscussion.setRegisterId("dong");
		frDiscussion.setRegisterName("동");
		frDiscussion.setPortalId("p1");
		
		frDiscussionDao.create(frDiscussion);
		
		frDiscussionScore = new FrDiscussionScore();
		
		
		frDiscussionScore.setDiscussionId(pk);
		frDiscussionScore.setDiscussionScore(20);
		
		frDiscussionScoreDao.create(frDiscussionScore);
		
	}

	
	@Test
	public void testRemoveAll() {
		
		
		try{
			frDiscussionScoreDao.removeAll();
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
		
	}
	
	@Test
	public void testRemoveCount() {
		
		try{
			frDiscussionScoreDao.removeCount(10);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testRemove() {
		
		try{
			frDiscussionScoreDao.remove(frDiscussionScore.getDiscussionId());
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	

}
