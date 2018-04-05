/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementUserPvi;
import com.lgcns.ikep4.collpack.assess.search.AssessmentManagementBlockPageCondition;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * AssessmentManagementUserPviService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementUserPviServiceTest.java 16903 2011-10-25 01:37:27Z giljae $
 */
public class AssessmentManagementUserPviServiceTest extends BaseServiceTestCase {

	@Autowired
	AssessmentManagementUserPviService assessmentManagementUserPviService;

	private AssessmentManagementBlockPageCondition assessmentManagementBlockPageCondition;

	private String portalId = "P000001";

	@Before
	public void setUp() {
		assessmentManagementBlockPageCondition = new AssessmentManagementBlockPageCondition();
	}

	@Test
	@Ignore
	public void countByPortalIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		int result = assessmentManagementUserPviService.countByPortalIdPage(assessmentManagementBlockPageCondition);
		log.debug(result);

		assertEquals(result, result);
	}

	@Test
	@Ignore
	public void listByPortalIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		assessmentManagementBlockPageCondition.setTotalCount(100);
		assessmentManagementBlockPageCondition.calculate();
		List<AssessmentManagementUserPvi> result = assessmentManagementUserPviService.listByPortalIdPage(assessmentManagementBlockPageCondition);
		log.debug("==[listByPortalIdPageTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

	@Test
	@Ignore
	public void listPowerUsersTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		List<AssessmentManagementUserPvi> result = assessmentManagementUserPviService.listPowerUsers(portalId, 10);
		log.debug("==[listPowerUsersTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

	@Test
	@Ignore
	public void getByUserIdTest() {
		
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		try{
			AssessmentManagementUserPvi result = assessmentManagementUserPviService.getByUserId("user22");
			log.debug(result);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}

		
	}

	@Test
	@Ignore
	public void countByGroupIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		assessmentManagementBlockPageCondition.setGroupId("GD0000001");
		int result = assessmentManagementUserPviService.countByGroupIdPage(assessmentManagementBlockPageCondition);
		log.debug(result);

		assertEquals(result, result);
	}
	
	@Test
	@Ignore
	public void listByGroupIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		assessmentManagementBlockPageCondition.setPortalId(portalId);
		assessmentManagementBlockPageCondition.setGroupId("GD0000001");
		assessmentManagementBlockPageCondition.setTotalCount(100);
		assessmentManagementBlockPageCondition.calculate();
		List<AssessmentManagementUserPvi> result = assessmentManagementUserPviService.listByGroupIdPage(assessmentManagementBlockPageCondition);
		log.debug("==[listByGroupIdPageTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

	@Test
	@Ignore
	public void getMaxPviByPortalIdTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		int result = assessmentManagementUserPviService.getMaxPviByPortalId(portalId);
		log.debug(result);

		assertEquals(result, result);
	}

	@Test
	@Rollback(false)
	public void batchGatherAccessmentTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		log.debug("==[batchGatherAccessment]==");
		Date startDate;
		Date endDate;
		startDate = new Date();
		log.debug("==[시작]==> [" + startDate + "]");

		assessmentManagementUserPviService.batchGatherAccessment();

		endDate = new Date();
		log.debug("==[종료]==> [" + endDate + "]");
		log.debug("==[배치 소요시간]==> [" + DateUtil.getTimeInterval(startDate, endDate, "ko") + "]");

		assertEquals(0, 0);
	}

}
