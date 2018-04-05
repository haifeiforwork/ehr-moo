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
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.model.FrUserActivities;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionScoreService;
import com.lgcns.ikep4.collpack.forum.service.FrItemScoreService;
import com.lgcns.ikep4.collpack.forum.service.FrLinereplyService;
import com.lgcns.ikep4.collpack.forum.service.FrUserActivitiesService;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrBatchTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrBatchTest extends BaseServiceTestCase {

	
	@Autowired
	private FrUserActivitiesService frUserActivitiesService;
	

	@Autowired
	private FrItemScoreService frItemScoreService;
	
	@Autowired
	private FrDiscussionScoreService frDiscussionScoreService;
	
	@Autowired
	private FrLinereplyService frLinereplyService;
	
	
	@Test
	//@Rollback(false)
	@Ignore
	public void testUpdateActivityScore() {
		
		try {
			frUserActivitiesService.updateActivityScore();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
	
	@Test
	//@Rollback(false)
	@Ignore
	public void testUpdateScoreWidthBest() {
		
		try{
			frItemScoreService.updateScore();
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
	}
	
	
	@Test
	//@Rollback(false)
	@Ignore
	public void testUpdateScoreDis() {
		
		try{
			frDiscussionScoreService.updateScore();
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
	}
	
	
	@Test
	//@Rollback(false)
	@Ignore
	public void testUpdateLinereplyBest() {
		
		try{
			frLinereplyService.updateBest();
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
}
