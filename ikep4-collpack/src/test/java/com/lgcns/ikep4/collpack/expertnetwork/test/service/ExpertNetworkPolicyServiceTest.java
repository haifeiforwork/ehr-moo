/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPolicy;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPolicyService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Expert Network ExpertPolicyService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPolicyServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class ExpertNetworkPolicyServiceTest extends BaseServiceTestCase {

	@Autowired
	private ExpertNetworkPolicyService expertNetworkPolicyService;
	@Autowired
	private IdgenService idgenService;

	private ExpertNetworkPolicy expertNetworkPolicy;
	private String portalId = "P000000022";
	private int followWeight = 65;

	@Before
	public void setUp() {
		expertNetworkPolicy = new ExpertNetworkPolicy();
		expertNetworkPolicy.setId(idgenService.getNextId());
		expertNetworkPolicy.setFavoriteWeight(55);
		expertNetworkPolicy.setFollowWeight(followWeight);
		expertNetworkPolicy.setGuestbookWeight(75);
		expertNetworkPolicy.setPortalId(portalId);
		expertNetworkPolicy.setRegisterId("admin");
		expertNetworkPolicy.setRegisterName("관리자");
		expertNetworkPolicyService.create(expertNetworkPolicy);
	}

	@Test
	public void getByPortalIdTest() {
		ExpertNetworkPolicy expertNetworkPolicy = expertNetworkPolicyService.getByPortalId(portalId);

		assertEquals(expertNetworkPolicy.getFollowWeight(), followWeight);
	}

}
