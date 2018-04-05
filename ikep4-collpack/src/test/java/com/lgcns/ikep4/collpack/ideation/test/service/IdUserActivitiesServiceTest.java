/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.ideation.model.IdSearch;
import com.lgcns.ikep4.collpack.ideation.model.IdUserActivities;
import com.lgcns.ikep4.collpack.ideation.service.IdUserActivitiesService;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdUserActivitiesServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdUserActivitiesServiceTest extends BaseServiceTestCase {

	
	@Autowired
	private IdUserActivitiesService idUserActivitiesService;
	

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
		
		idUserActivitiesService.create(idUserActivities);
		
	}

	@Test
	public void testCreate() {
		
		IdUserActivities result = idUserActivitiesService.get(idUserActivities.getUserId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testList() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setIsBest("1");
		idSearch.setIsSuggestion("1");
		idSearch.setEndRowIndex(10);
		idSearch.setStartRowIndex(0);
		
		List<IdUserActivities> result = idUserActivitiesService.list(idSearch);
		assertFalse(result.isEmpty());
		
	}
	
	
	@Test
	public void testRemove() {
		
		idUserActivitiesService.remove(idUserActivities.getUserId());
		
		IdUserActivities result = idUserActivitiesService.get(idUserActivities.getUserId());
		assertNull(result);
		
	}
	
	@Test
	public void testUserInfo() {
		
		IdUserActivities result = idUserActivitiesService.getUserInfo(idUserActivities.getUserId());
		assertNotNull(result);
	}
	
	
}
