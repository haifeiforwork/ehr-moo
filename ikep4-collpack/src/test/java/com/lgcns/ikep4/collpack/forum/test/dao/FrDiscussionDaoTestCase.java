/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrCategoryDao;
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.qna.model.Qna;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrDiscussionDaoTestCase.java 16903 2011-10-25 01:37:27Z giljae $
 */
public class FrDiscussionDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	@Autowired
	private FrCategoryDao frCategoryDao;

	private FrDiscussion frDiscussion;

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
		
		frDiscussion = new FrDiscussion();
		
		pk = "20";
		
		frDiscussion.setDiscussionId(pk);
		frDiscussion.setCategoryId(frCategory.getCategoryId());
		frDiscussion.setTitle("title");
		frDiscussion.setContents("contents");
		frDiscussion.setImageId("img");
		frDiscussion.setUpdaterId("lee");
		frDiscussion.setUpdaterName("dong");
		frDiscussion.setRegisterId("user1");
		frDiscussion.setRegisterName("동");
		frDiscussion.setPortalId("p1");
		
		frDiscussionDao.create(frDiscussion);
		
	}

	@Test
	public void testCreate() {
		
		FrDiscussion result = frDiscussionDao.get(pk);
		assertNotNull(result);
	}
	
	
	@Test
	public void testGetCountByUserId() {
		
		
		int result = frDiscussionDao.getCountByUserId(frDiscussion.getRegisterId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId(frDiscussion.getPortalId());
		frSearch.setUserId(frDiscussion.getRegisterId());
		
		List<FrDiscussion> result = frDiscussionDao.list(frSearch);
		assertTrue(result.size() > 0);
	}
	
	@Test
	public void testGetCountList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId(frDiscussion.getPortalId());
		frSearch.setUserId(frDiscussion.getRegisterId());
		
		int result = frDiscussionDao.getCountList(frSearch);
		assertNotSame(result, 0);
	}
	
	
	@Test
	public void testListPopular() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId("p1");
		frSearch.setLimitDate("30");
		try{
			List<FrDiscussion> result = frDiscussionDao.listPopular(frSearch);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testGetCountListPopular() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId("p1");
		frSearch.setLimitDate("30");
		
		try{
			int result = frDiscussionDao.getCountListPopular(frSearch);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
	}
	
	
	
	@Test
	public void testUpdate() {
		
		frDiscussion.setTitle("updateTile");
		frDiscussion.setContents("upateContent");
		
		frDiscussionDao.update(frDiscussion);
		
		FrDiscussion result = frDiscussionDao.get(pk);
		assertEquals(frDiscussion.getTitle(), result.getTitle());
	}
	
	
	@Test
	public void testUpdateHitCount() {
		
		frDiscussionDao.updateHitCount(frDiscussion.getDiscussionId());
		
		FrDiscussion result = frDiscussionDao.get(pk);
		assertTrue(result.getHitCount() >= 0);
	}
	
	@Test
	public void testUpdateItemCount() {
		
		frDiscussionDao.updateItemCount(frDiscussion.getDiscussionId());
		
		FrDiscussion result = frDiscussionDao.get(pk);
		assertTrue(result.getItemCount() >= 0);
	}
	
	@Test
	public void testUpdateLinereplyCount() {
		
		frDiscussionDao.updateLinereplyCount(frDiscussion.getDiscussionId());
		
		FrDiscussion result = frDiscussionDao.get(pk);
		assertTrue(result.getItemCount() >= 0);
	}
	
	@Test
	public void testUpdateParticipationCount() {
		
		frDiscussionDao.updateParticipationCount(frDiscussion.getDiscussionId());
		
		FrDiscussion result = frDiscussionDao.get(pk);
		assertTrue(result.getParticipationCount() >= 0);
	}
	
	
	@Test
	public void testUpdateMailCount() {
		
		int testMail = frDiscussion.getMailCount();
		frDiscussionDao.updateMailCount(pk);
		
		FrDiscussion result = frDiscussionDao.get(pk);
		
		assertEquals(testMail+1, result.getMailCount());
	}
	
	@Test
	public void testUpdateMailCoun() {
		
		int testVal = frDiscussion.getMblogCount();
		frDiscussionDao.updateMblogCount(pk);
		
		FrDiscussion result = frDiscussionDao.get(pk);
		
		assertEquals(testVal+1, result.getMblogCount());
	}
	
	
	@Test
	public void testDelete() {
		
		frDiscussionDao.remove(pk);
		FrDiscussion result = frDiscussionDao.get(pk);
		assertNull(result);
		
	}
	
	
	@Test
	public void testListDiscussionByReference() {
		
		try{
			List<FrDiscussion> result = frDiscussionDao.listDiscussionByReference("admin",4);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testListDiscussionByItemCount() {
		
		try{
			List<FrDiscussion> result = frDiscussionDao.listDiscussionByItemCount(4,"7");
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}


}
