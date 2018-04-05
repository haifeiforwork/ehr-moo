/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrUserActivitiesDao;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrUserActivitiesDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrUserActivitiesDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrUserActivitiesDao frUserActivitiesDao;
	
	private FrUserActivities frUserActivities;


	@Before
	public void setUp() {
		
		frUserActivities = new FrUserActivities();
		
		frUserActivities.setUserId("user666");
		frUserActivities.setDiscussionRank(1);
		frUserActivities.setDiscussionScore(30);
		frUserActivities.setDiscussionCount(10);
		frUserActivities.setItemCount(20);
		frUserActivities.setLinereplyCount(10);
		frUserActivities.setBestItemCount(2);
		frUserActivities.setBestLinereplyCount(3);
		frUserActivities.setFavoriteCount(20);
		frUserActivities.setAgreementCount(10);
		frUserActivities.setOppositionCount(2);
		frUserActivities.setRecommendCount(2);
		
		frUserActivitiesDao.create(frUserActivities);
		
	}

	@Test
	public void testCreate() {
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		
		boolean result = frUserActivitiesDao.exists(frUserActivities.getUserId());
		assertTrue(result);
	}
	
	
	@Test
	public void testList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setIsBest("1");
		frSearch.setOrderType("ranking");
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		
		List<FrUserActivities> result = frUserActivitiesDao.list(frSearch);
		assertFalse(result.isEmpty());
	}
	
	
	
	@Test
	public void testUpdate() {
		
		frUserActivities.setDiscussionRank(50);
		frUserActivities.setDiscussionScore(50);
		frUserActivities.setDiscussionCount(50);
		frUserActivities.setItemCount(50);
		frUserActivities.setLinereplyCount(50);
		frUserActivities.setBestItemCount(5);
		frUserActivities.setBestLinereplyCount(5);
		frUserActivities.setFavoriteCount(50);
		frUserActivities.setAgreementCount(50);
		frUserActivities.setOppositionCount(5);
		frUserActivities.setRecommendCount(5);
		
		frUserActivitiesDao.update(frUserActivities);
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testUpdateIncrease() {
		
		FrUserActivities obj = new FrUserActivities();
		
		obj.setDiscussionCount(1);
		obj.setUserId(frUserActivities.getUserId());
		
		frUserActivitiesDao.updateIncrease(obj);
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testUpdateDiscussionCount() {
		
		frUserActivitiesDao.updateDiscussionCount(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	@Test
	public void testUpdateuItemCount() {
		
		frUserActivitiesDao.updateItemCount(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}

	@Test
	public void testUpdateLinereplyCount() {
		
		frUserActivitiesDao.updateLinereplyCount(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testUpdateFavoriteCount() {
		
		frUserActivitiesDao.updateFavoriteCount(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testUpdateAgreementCount() {
		
		frUserActivitiesDao.updateAgreementCount(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	@Test
	public void testUpdateOppositionCount() {
		
		frUserActivitiesDao.updateOppositionCount(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testUpdateRecommendCount() {
		
		frUserActivitiesDao.updateRecommendCount(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testRemove() {
		
		frUserActivitiesDao.remove(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesDao.get(frUserActivities.getUserId());
		assertNull(result);
		
	}
	
	@Test
	public void testUserInfo() {
		
		FrUserActivities result = frUserActivitiesDao.getUserInfo(frUserActivities.getUserId());
		assertNotNull(result);
	}
	
}
