/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.ideation.dao.IdUserActivitiesDao;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdUserActivitiesDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdUserActivitiesDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private IdUserActivitiesDao idUserActivitiesDao;
	
	private IdUserActivities idUserActivities;


	@Before
	public void setUp() {
		
		idUserActivities = new IdUserActivities();
		
		idUserActivities.setUserId("user33");
		idUserActivities.setSuggestionRank(1);
		idUserActivities.setSuggestionScore(100);
		idUserActivities.setContributionRank(1);
		idUserActivities.setContributionScore(200);
		idUserActivities.setItemCount(20);
		idUserActivities.setRecommendItemCount(20);
		idUserActivities.setAdoptItemCount(30);
		idUserActivities.setBestItemCount(2);
		idUserActivities.setBusinessItemCount(20);
		idUserActivities.setRecommendCount(2);
		idUserActivities.setAdoptCount(20);
		idUserActivities.setLinereplyCount(10);
		idUserActivities.setFavoriteCount(20);
		
		
		idUserActivitiesDao.create(idUserActivities);
		
	}

	@Test
	public void testCreate() {
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		
		boolean result = idUserActivitiesDao.exists(idUserActivities.getUserId());
		assertTrue(result);
	}
	
	
	@Test
	public void testList() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setIsBest("1");
		idSearch.setOrderType("ranking");
		idSearch.setEndRowIndex(10);
		idSearch.setStartRowIndex(0);
		
		List<IdUserActivities> result = idUserActivitiesDao.list(idSearch);
		assertFalse(result.isEmpty());
	}
	
	
	
	@Test
	public void testUpdate() {
		
		idUserActivities.setSuggestionRank(1);
		idUserActivities.setSuggestionScore(200);
		idUserActivities.setContributionRank(1);
		idUserActivities.setContributionScore(200);
		idUserActivities.setItemCount(200);
		idUserActivities.setRecommendItemCount(20);
		idUserActivities.setAdoptItemCount(30);
		idUserActivities.setBestItemCount(200);
		idUserActivities.setBusinessItemCount(20);
		idUserActivities.setRecommendCount(20);
		idUserActivities.setAdoptCount(20);
		idUserActivities.setLinereplyCount(10);
		idUserActivities.setFavoriteCount(20);
		
		idUserActivitiesDao.update(idUserActivities);
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testUpdateIncrease() {
		
		IdUserActivities obj = new IdUserActivities();
		
		obj.setAdoptCount(1);
		obj.setUserId(idUserActivities.getUserId());
		
		idUserActivitiesDao.updateIncrease(obj);
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testUpdateItemCount() {
		
		idUserActivitiesDao.updateItemCount(idUserActivities.getUserId());
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	@Test
	public void testUpdateBusinessItemCount() {
		
		idUserActivitiesDao.updateBusinessItemCount(idUserActivities.getUserId());
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	@Test
	public void testUpdateRecommendCount() {
		
		idUserActivitiesDao.updateRecommendCount(idUserActivities.getUserId());
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	@Test
	public void testUpdateRecommendItemCount() {
		
		idUserActivitiesDao.updateRecommendItemCount(idUserActivities.getUserId());
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}

	@Test
	public void testUpdateFavoriteCount() {
		
		idUserActivitiesDao.updateFavoriteCount(idUserActivities.getUserId(),20);
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testUpdateAdoptCount() {
		
		idUserActivitiesDao.updateAdoptCount(idUserActivities.getUserId());
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	@Test
	public void testUpdateAdoptiItemCount() {
		
		idUserActivitiesDao.updateAdoptiItemCount(idUserActivities.getUserId());
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNotNull(result);
		
	}
	
	@Test
	public void testRemove() {
		
		idUserActivitiesDao.remove(idUserActivities.getUserId());
		
		IdUserActivities result = idUserActivitiesDao.get(idUserActivities.getUserId());
		assertNull(result);
		
	}
	
	@Test
	public void testUserInfo() {
		
		IdUserActivities result = idUserActivitiesDao.getUserInfo(idUserActivities.getUserId());
		assertNotNull(result);
	}
	
	
}
