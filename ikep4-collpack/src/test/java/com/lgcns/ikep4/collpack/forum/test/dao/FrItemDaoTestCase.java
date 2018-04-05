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
import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrItemDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrItemDao frItemDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	@Autowired
	private FrCategoryDao frCategoryDao;

	private FrItem frItem;

	private String pk;
	
	private String categoryId;

	@Before
	public void setUp() {
		
		FrCategory frCategory = new FrCategory();
		
		categoryId = "10";
		
		frCategory.setCategoryId(categoryId);
		frCategory.setCategoryName("카테고리 이름");
		frCategory.setRegisterId("user2");
		frCategory.setRegisterName("동");
		frCategory.setPortalId("p1");
		
		frCategoryDao.create(frCategory);
		
		
		FrDiscussion frDiscussion = new FrDiscussion();
		
		frDiscussion.setDiscussionId("11");
		frDiscussion.setCategoryId(frCategory.getCategoryId());
		frDiscussion.setTitle("title");
		frDiscussion.setContents("contents");
		frDiscussion.setImageId("img");
		frDiscussion.setUpdaterId("user1");
		frDiscussion.setUpdaterName("dong");
		frDiscussion.setRegisterId("user1");
		frDiscussion.setRegisterName("동");
		frDiscussion.setPortalId("p1");
		
		frDiscussionDao.create(frDiscussion);
		
		frItem = new FrItem();
		
		pk = "20";
		
		frItem.setItemId(pk);
		frItem.setDiscussionId(frDiscussion.getDiscussionId());
		frItem.setTitle("title");
		frItem.setContents("contents");
		frItem.setUpdaterId("user1");
		frItem.setUpdaterName("dong");
		frItem.setRegisterId("user1");
		frItem.setRegisterName("동");
		frItem.setPortalId("p1");
		
		frItemDao.create(frItem);
		
	}

	@Test
	public void testCreate() {
		
		FrItem result = frItemDao.get(pk);
		assertNotNull(result);
	}
	
	
	@Test
	public void testGetCountes() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setUserId(frItem.getRegisterId());
		
		FrItem result = frItemDao.getCountes(frSearch);
		assertNotNull(result);
	}
	
	
	
	@Test
	public void testListUserId() {
		
		List<String> result = frItemDao.listUserId(frItem.getDiscussionId());
		
		assertFalse(result.isEmpty());
	}
	
	
	@Test
	public void testList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setTitle("title");
		frSearch.setOrderType("best");
		frSearch.setPortalId("p1");
		
		List<FrItem> result = frItemDao.list(frSearch);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGetCountList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setTitle("title");
		frSearch.setOrderType("best");
		frSearch.setPortalId("p1");
		
		int result = frItemDao.getCountList(frSearch);
		assertNotSame(result, 0);
	}
	
	
	@Test
	public void testListWithDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setPortalId("p1");
		
		List<FrItem> result = frItemDao.listWithDiscussion(frSearch);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGetCountListWithDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setPortalId("p1");
		
		int result = frItemDao.getCountListWithDiscussion(frSearch);
		assertNotSame(result, 0);
	}
	
	
	
	@Test
	public void testListLastWithDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setPortalId("p1");
		
		List<FrItem> result = frItemDao.listLastWithDiscussion(frSearch);
		assertFalse(result.isEmpty());
	}
	
	
	@Test
	public void testGetCountListLastWithDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setUserId(frItem.getRegisterId());
		frSearch.setLimitDate("30");
		frSearch.setPortalId("p1");
		
		int result = frItemDao.getCountListLastWithDiscussion(frSearch);
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
			List<FrItem> result = frItemDao.listPopular(frSearch);
			assertTrue(true);
		}catch(Exception e){
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
			int result = frItemDao.getCountListPopular(frSearch);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testListRandomCategory() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setStartRowIndex(0);
		frSearch.setEndRowIndex(10);
		frSearch.setPortalId("p1");
		frSearch.setCategoryId(categoryId);
		
		List<FrItem> result = frItemDao.listItemRandomCategory(frSearch);
		assertFalse(result.isEmpty());
	}
	
	
	
	
	@Test
	public void testUpdate() {
		
		frItem.setTitle("updateTile");
		frItem.setContents("upateContent");
		
		frItemDao.update(frItem);
		
		FrItem result = frItemDao.get(pk);
		assertEquals(frItem.getTitle(), result.getTitle());
	}
	
	
	@Test
	public void testUpdateBestItem() {
		
		frItemDao.updateBestItem(frItem.getItemId());
		
		FrItem result = frItemDao.get(frItem.getItemId());
		assertTrue(result.getBestItem() == 1);
	}
	
	
	@Test
	public void testUpdateBestItemInit() {
		
		frItemDao.updateBestItemInit();
		
		FrItem result = frItemDao.get(frItem.getItemId());
		assertTrue(result.getBestItem() == 0);
	}
	
	
	
	@Test
	public void testUpdateHitCount() {
		
		frItemDao.updateHitCount(frItem.getItemId());
		
		FrItem result = frItemDao.get(pk);
		assertTrue(result.getHitCount() >= 0);
	}
	
	@Test
	public void testUpdateLinereplyCount() {
		
		frItemDao.updateLinereplyCount(frItem.getItemId());
		
		FrItem result = frItemDao.get(pk);
		assertTrue(result.getLinereplyCount() >= 0);
	}
	
	@Test
	public void testUpdateAgreementCount() {
		
		frItemDao.updateAgreementCount(frItem.getItemId());
		
		FrItem result = frItemDao.get(pk);
		assertTrue(result.getAgreementCount() >= 0);
	}
	
	@Test
	public void testUpdateOppositionCount() {
		
		frItemDao.updateOppositionCount(frItem.getItemId());
		
		FrItem result = frItemDao.get(pk);
		assertTrue(result.getOppositionCount() >= 0);
	}
	
	@Test
	public void testUpdateFavoriteCount() {
		
		frItemDao.updateFavoriteCount(frItem.getItemId(), 1);
		
		FrItem result = frItemDao.get(pk);
		assertTrue(result.getFavoriteCount() >= 0);
	}
	
	
	@Test
	public void testRemove() {
		
		frItemDao.remove(pk);
		FrItem result = frItemDao.get(pk);
		assertNull(result);
		
	}
	
	@Test
	public void testRemoveByDiscussionId() {
		
		frItemDao.removeByDiscussionId(frItem.getDiscussionId());
		FrItem result = frItemDao.get(pk);
		assertNull(result);
		
	}

}
