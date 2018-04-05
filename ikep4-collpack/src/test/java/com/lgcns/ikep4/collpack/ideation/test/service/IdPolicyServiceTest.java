/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.service;

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

import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;
import com.lgcns.ikep4.collpack.ideation.service.IdPolicyService;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdPolicyServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdPolicyServiceTest extends BaseServiceTestCase {

	@Autowired
	private IdPolicyService idPolicyService;
	

	private IdPolicy idPolicy;
	
	private String pk;

	@Before
	public void setUp() {
		
		idPolicy = new IdPolicy();
		
		idPolicy.setPolicyId(pk);
		idPolicy.setPolicyType("1");
		idPolicy.setRecommendCount(33);
		idPolicy.setSuggestionWeight(44);
		idPolicy.setRecommendWeight(50);
		idPolicy.setAdoptWeight(10);
		idPolicy.setBestWeight(40);
		idPolicy.setBusinessWeight(80);
		idPolicy.setFavoriteWeight(20);
		idPolicy.setLinereplyWeight(30);
		idPolicy.setRecommendWeight(10);
		idPolicy.setRegisterId("dong");
		idPolicy.setRegisterName("동");
		idPolicy.setPortalId("p1");
		
		pk = idPolicyService.create(idPolicy);
		
	}


	@Test
	public void testCreate() {
		
		IdPolicy result = idPolicyService.get(idPolicy.getPolicyType(), idPolicy.getPortalId());
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		
		idPolicy.setRecommendCount(330);
		idPolicy.setSuggestionWeight(44);
		idPolicy.setRecommendWeight(500);
		idPolicy.setAdoptWeight(100);
		idPolicy.setBestWeight(400);
		idPolicy.setBusinessWeight(80);
		idPolicy.setFavoriteWeight(20);
		idPolicy.setLinereplyWeight(30);
		idPolicy.setRecommendWeight(100);
		
		idPolicyService.update(idPolicy);
		
		IdPolicy result =  idPolicyService.get(idPolicy.getPolicyType(), idPolicy.getPortalId());
		assertEquals(result.getRecommendWeight(), 100);
	}
	
	
	@Test
	public void testRemove() {
		
		idPolicyService.remove(pk);
		IdPolicy result =  idPolicyService.get(idPolicy.getPolicyType(), idPolicy.getPortalId());
		assertNull(result);
		
	}
	
	
}
