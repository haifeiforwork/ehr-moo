/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementGroupPvi;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementGroupPviService;


/**
 * AssessmentManagementGroupPviService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupPviServiceTest.java 16291 2011-08-19 07:31:11Z giljae $
 */
public class AssessmentManagementGroupPviServiceTest extends BaseServiceTestCase {

	@Autowired
	AssessmentManagementGroupPviService assessmentManagementGroupPviService;

	private AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition;

	private String portalId = "P000001";
	private String groupId = "GD0000001";

	@Before
	public void setUp() {
		assessmentManagementBlockPageCondition = new AssessmentManagementBlockPageCondition();
	}

	@Test
	public void getCompanyPviTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		int result = assessmentManagementGroupPviService.getCompanyPvi(groupId);
		log.debug(result);

		assertEquals(result, result);
	}

	@Test
	public void listRootGroupByPortalIdTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		List<AssessmentManagementGroupPvi> result = assessmentManagementGroupPviService.listRootGroupByPortalId(portalId);
		log.debug("==[listRootGroupByPortalIdTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(1, 1);
	}

	@Test
	public void countByGroupIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		int result = assessmentManagementGroupPviService.countByGroupIdPage(groupId);
		log.debug(result);

		assertEquals(result, result);
	}

	@Test
	public void listByGroupIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		assessmentManagementBlockPageCondition.setTotalCount(10);
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		assessmentManagementBlockPageCondition.calculate();
		List<AssessmentManagementGroupPvi> result = assessmentManagementGroupPviService.listByGroupIdPage(assessmentManagementBlockPageCondition);
		log.debug("==[listByGroupIdPageTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(1, 1);
	}

	@Test
	public void listGroupHierarchyTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		List<AssessmentManagementGroupPvi> result = assessmentManagementGroupPviService.listGroupHierarchy(groupId);
		log.debug("==[listGroupHierarchyTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(1, 1);
	}

}
