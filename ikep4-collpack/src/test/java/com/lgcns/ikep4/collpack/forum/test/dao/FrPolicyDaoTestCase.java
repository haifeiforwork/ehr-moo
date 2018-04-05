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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrPolicyDao;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrPolicyDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrPolicyDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrPolicyDao frPolicyDao;

	private FrPolicy frPolicy;

	private String pk;

	@Before
	public void setUp() {
		frPolicy = new FrPolicy();
		
		pk = "20";
		
		frPolicy.setPolicyId(pk);
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
		
		frPolicyDao.create(frPolicy);
		
	}

	@Test
	public void testCreate() {
		
		FrPolicy result = frPolicyDao.get(frPolicy.getPolicyType(), frPolicy.getPortalId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testList() {
		
		List<FrPolicy> result = frPolicyDao.list(frPolicy.getPolicyType());
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
		
		frPolicyDao.update(frPolicy);
		
		FrPolicy result =  frPolicyDao.get(frPolicy.getPolicyType(), frPolicy.getPortalId());
		assertEquals(result.getItemWeight(), 60);
	}
	
	
	@Test
	public void testRemove() {
		
		frPolicyDao.remove(pk);
		FrPolicy result =  frPolicyDao.get(frPolicy.getPolicyType(), frPolicy.getPortalId());
		assertNull(result);
		
	}
	

}
