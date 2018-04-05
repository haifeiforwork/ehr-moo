/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussionScore;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrItemScore;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionScoreService;
import com.lgcns.ikep4.collpack.forum.service.FrDiscussionService;
import com.lgcns.ikep4.collpack.forum.service.FrItemScoreService;
import com.lgcns.ikep4.collpack.forum.service.FrItemService;
import com.lgcns.ikep4.collpack.forum.service.FrPolicyService;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrPolicyServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrPolicyServiceTest extends BaseServiceTestCase {

	@Autowired
	private FrPolicyService frPolicyService;
	

	private FrPolicy frPolicy;
	
	private String pk;

	@Before
	public void setUp() {
		
		frPolicy = new FrPolicy();
		
		frPolicy.setPolicyType("1");
		frPolicy.setParticipationWeight(10);
		frPolicy.setFeedbackWeight(20);
		frPolicy.setDiscussionWeight(20);
		frPolicy.setItemWeight(20);
		frPolicy.setLinereplyWeight(30);
		frPolicy.setMaxCount(10);
		frPolicy.setBestItemWeight(20);
		frPolicy.setBestLinereplyWeight(10);
		frPolicy.setFavoriteWeight(20);
		frPolicy.setAgreementWeight(20);
		frPolicy.setRecommendWeight(10);
		frPolicy.setTargetTerm(30);
		frPolicy.setRegisterId("dong");
		frPolicy.setRegisterName("동");
		frPolicy.setPortalId("p1");
		
		pk = frPolicyService.create(frPolicy);
		
	}


	@Test
	public void testCreate() {
		
		FrPolicy result = frPolicyService.get(frPolicy.getPolicyType(), frPolicy.getPortalId());
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		
		frPolicy.setParticipationWeight(60);
		frPolicy.setFeedbackWeight(70);
		frPolicy.setFavoriteWeight(30);
		frPolicy.setBestItemWeight(80);
		frPolicy.setBestLinereplyWeight(80);
		frPolicy.setDiscussionWeight(90);
		frPolicy.setItemWeight(60);
		frPolicy.setLinereplyWeight(90);
		frPolicy.setMaxCount(70);
		frPolicy.setRecommendWeight(30);
		frPolicy.setTargetTerm(70);
		frPolicy.setAgreementWeight(50);
		
		frPolicyService.update(frPolicy);
		
		FrPolicy result =  frPolicyService.get(frPolicy.getPolicyType(), frPolicy.getPortalId());
		assertEquals(result.getItemWeight(), 60);
	}
	
	
	@Test
	public void testRemove() {
		
		frPolicyService.remove(pk);
		FrPolicy result =  frPolicyService.get(frPolicy.getPolicyType(), frPolicy.getPortalId());
		assertNull(result);
		
	}
	
	
}
