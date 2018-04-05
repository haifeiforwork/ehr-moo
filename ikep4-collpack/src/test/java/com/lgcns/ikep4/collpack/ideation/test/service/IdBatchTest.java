/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.ideation.service.IdIdeaService;
import com.lgcns.ikep4.collpack.ideation.service.IdUserActivitiesService;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdBatchTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdBatchTest extends BaseServiceTestCase {

	
	@Autowired
	private IdUserActivitiesService idUserActivitiesService;
	
	@Autowired
	private IdIdeaService idIdeaService;
	
	
	@Test
	//@Rollback(false)
	@Ignore
	public void testUpdateContributionScore() {
		
		try {
			idUserActivitiesService.updateContributionScore();
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
		
	}
	
	
	@Test
	//@Rollback(false)
	@Ignore
	public void testUpdateSuggestionScore() {
		
		try{
			idUserActivitiesService.updateSuggestionScore();
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
	}
	
	
	
	@Test
	//@Rollback(false)
	@Ignore
	public void testUpdateBest() {
		
		try{
			idIdeaService.updateBest();
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
}
