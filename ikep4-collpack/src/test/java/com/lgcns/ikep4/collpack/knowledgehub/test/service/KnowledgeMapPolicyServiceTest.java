/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapPolicy;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPolicyService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * KnowledgeMapPolicyService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPolicyServiceTest.java 16457 2011-08-30 04:20:17Z giljae $
 */
public class KnowledgeMapPolicyServiceTest extends BaseServiceTestCase {

	@Autowired
	private KnowledgeMapPolicyService knowledgeMapPolicyService;
	@Autowired
	private IdgenService idgenService;

	private KnowledgeMapPolicy knowledgeMapPolicy;
	private String portalId = "";
	private String policyId = "";

	@Before
	public void setUp() {
		portalId = idgenService.getNextId();
		policyId = idgenService.getNextId();

	}

	@Test
	public void listByUserIdTest() {
		knowledgeMapPolicy = new KnowledgeMapPolicy();
		knowledgeMapPolicy.setPolicyId(policyId);
		knowledgeMapPolicy.setPolicyType("0");
		knowledgeMapPolicy.setHitWeight(82);
		knowledgeMapPolicy.setRecommendWeight(103);
		knowledgeMapPolicy.setFavoriteWeight(22);
		knowledgeMapPolicy.setPortalId(portalId);
		knowledgeMapPolicyService.create(knowledgeMapPolicy);
		
		KnowledgeMapPolicy result = knowledgeMapPolicyService.getByPortalId(portalId);

		assertEquals(policyId, result.getPolicyId());
	}

}
