/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementInitializationLog;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementInitializationLogService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * AssessmentManagementInitializationLogService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementInitializationLogServiceTest.java 16291 2011-08-19 07:31:11Z giljae $
 */
public class AssessmentManagementInitializationLogServiceTest extends BaseServiceTestCase {

	@Autowired
	AssessmentManagementInitializationLogService assessmentManagementInitializationLogService;

	private AssessmentManagementInitializationLog assessmentManagementInitializationLog;

	private String portalId = "TESTPORTAL01";
	private String historyId = "H00001";

	@Before
	public void setUp() {
		assessmentManagementInitializationLog = new AssessmentManagementInitializationLog();
		assessmentManagementInitializationLog.setHistoryId(historyId);
		assessmentManagementInitializationLog.setPortalId(portalId);
		assessmentManagementInitializationLog.setRegisterId("admin");
		assessmentManagementInitializationLogService.create(assessmentManagementInitializationLog);
	}

	@Test
	public void listByPortalIdTest() {
		List<AssessmentManagementInitializationLog> result = assessmentManagementInitializationLogService.listByPortalId(portalId);
		log.debug("==[listByPortalId]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(1, result.size());
	}

	@Test
	public void scoreInitializationTest() {
		log.debug("==[scoreInitialization]==");
		Date startDate;
		Date endDate;
		startDate = new Date();
		log.debug("==[시작]==> [" + startDate + "]");

		assessmentManagementInitializationLogService.scoreInitialization("P000001", "admin");

		endDate = new Date();
		log.debug("==[종료]==> [" + endDate + "]");
		log.debug("==[배치 소요시간]==> [" + DateUtil.getTimeInterval(startDate, endDate, "ko") + "]");

		assertEquals(1, 1);
	}

}
