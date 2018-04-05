/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPolicyService;


/**
 * AssessmentManagementPolicyService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPolicyServiceTest.java 16291 2011-08-19 07:31:11Z giljae $
 */
public class AssessmentManagementPolicyServiceTest extends BaseServiceTestCase {

	@Autowired
	AssessmentManagementPolicyService assessmentManagementPolicyService;

	@Before
	public void setUp() {
	}

	@Test
	public void noEmptyMethodTest() {
		assertEquals(0, 0);
	}

}
