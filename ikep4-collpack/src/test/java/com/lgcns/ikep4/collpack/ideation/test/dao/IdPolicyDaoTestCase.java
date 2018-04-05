/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.ideation.dao.IdPolicyDao;
import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdPolicyDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdPolicyDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private IdPolicyDao idPolicyDao;

	private IdPolicy idPolicy;

	private String pk;

	@Before
	public void setUp() {
		
		idPolicy = new IdPolicy();
		
		pk = "20";
		
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
		
		idPolicyDao.create(idPolicy);
		
	}

	@Test
	public void testCreate() {
		
		IdPolicy result = idPolicyDao.get(idPolicy.getPolicyType(), idPolicy.getPortalId());
		assertNotNull(result);
	}
	
	
	@Test
	public void testList() {
		
		List<IdPolicy> result = idPolicyDao.list(idPolicy.getPolicyType());
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		
		idPolicy.setRecommendCount(33);
		idPolicy.setSuggestionWeight(44);
		idPolicy.setRecommendWeight(50);
		idPolicy.setAdoptWeight(30);
		idPolicy.setBestWeight(40);
		idPolicy.setBusinessWeight(80);
		idPolicy.setFavoriteWeight(20);
		idPolicy.setLinereplyWeight(30);
		idPolicy.setRecommendWeight(10);
		idPolicy.setRegisterId("dong");
		idPolicy.setRegisterName("동");
		idPolicy.setPortalId("p1");
		
		idPolicyDao.update(idPolicy);
		
		IdPolicy result =  idPolicyDao.get(idPolicy.getPolicyType(), idPolicy.getPortalId());
		assertEquals(result.getAdoptWeight(), 30);
	}
	
	
	@Test
	public void testRemove() {
		
		idPolicyDao.remove(pk);
		IdPolicy result =  idPolicyDao.get(idPolicy.getPolicyType(), idPolicy.getPortalId());
		assertNull(result);
		
	}
	

}
