/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrUserActivitiesService;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrUserActivitiesServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrUserActivitiesServiceTest extends BaseServiceTestCase {

	
	@Autowired
	private FrUserActivitiesService frUserActivitiesService;
	

	private FrUserActivities frUserActivities;
	

	@Before
	public void setUp() {
		
		
		frUserActivities = new FrUserActivities();
		
		frUserActivities.setUserId("user1112");
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
		
		frUserActivitiesService.create(frUserActivities);
		
	}

	@Test
	public void testCreate() {
		
		FrUserActivities result = frUserActivitiesService.get(frUserActivities.getUserId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setIsBest("1");
		frSearch.setOrderType("ranking");
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		
		List<FrUserActivities> result = frUserActivitiesService.list(frSearch);
		assertFalse(result.isEmpty());
	}
	
	
	@Test
	public void testRemove() {
		
		frUserActivitiesService.remove(frUserActivities.getUserId());
		
		FrUserActivities result = frUserActivitiesService.get(frUserActivities.getUserId());
		assertNull(result);
		
	}
	
	@Test
	public void testUserInfo() {
		
		FrUserActivities result = frUserActivitiesService.getUserInfo(frUserActivities.getUserId());
		assertNotNull(result);
	}
	
}
