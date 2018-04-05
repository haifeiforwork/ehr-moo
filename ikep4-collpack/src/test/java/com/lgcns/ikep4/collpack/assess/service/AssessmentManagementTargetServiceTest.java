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

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementTarget;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementTargetService;


/**
 * AssessmentManagementTargetService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementTargetServiceTest.java 16291 2011-08-19 07:31:11Z giljae $
 */
public class AssessmentManagementTargetServiceTest extends BaseServiceTestCase {

	@Autowired
	AssessmentManagementTargetService assessmentManagementTargetService;

	private AssessmentManagementTarget assessmentManagementTarget;

	private String portalId = "TESTPORTAL01";

	@Before
	public void setUp() {
		assessmentManagementTarget = new AssessmentManagementTarget();
		assessmentManagementTarget.setPortalId(portalId);
		assessmentManagementTarget.setModuleCode("AA");
		assessmentManagementTarget.setModuleName("NameAA");
		assessmentManagementTarget.setRequired(0);
		assessmentManagementTarget.setAvailable(0);
		assessmentManagementTarget.setSelection(0);
		assessmentManagementTarget.setRegisterId("admin");
		assessmentManagementTargetService.create(assessmentManagementTarget);

		assessmentManagementTarget.setModuleCode("BB");
		assessmentManagementTarget.setModuleName("NameBB");
		assessmentManagementTarget.setRequired(1);
		assessmentManagementTarget.setAvailable(0);
		assessmentManagementTarget.setSelection(0);
		assessmentManagementTargetService.create(assessmentManagementTarget);

		assessmentManagementTarget.setModuleCode("CC");
		assessmentManagementTarget.setModuleName("NameCC");
		assessmentManagementTarget.setRequired(1);
		assessmentManagementTarget.setAvailable(0);
		assessmentManagementTarget.setSelection(0);
		assessmentManagementTargetService.create(assessmentManagementTarget);

		assessmentManagementTarget.setModuleCode("DD");
		assessmentManagementTarget.setModuleName("NameDD");
		assessmentManagementTarget.setRequired(1);
		assessmentManagementTarget.setAvailable(1);
		assessmentManagementTarget.setSelection(1);
		assessmentManagementTargetService.create(assessmentManagementTarget);
	}

	@Test
	public void listRequiredByPortalIdTest() {
		List<AssessmentManagementTarget> result = assessmentManagementTargetService.listRequiredByPortalId(portalId);
		log.debug("==[listRequiredByPortalIdTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(1, result.size());
	}
	
	@Test
	public void listUnrequiredAvailableByPortalIdTest() {
		List<AssessmentManagementTarget> result = assessmentManagementTargetService.listUnrequiredAvailableByPortalId(portalId);
		log.debug("==[listUnrequiredAvailableByPortalIdTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(2, result.size());
	}
	
	@Test
	public void listUnrequiredUnavailableByPortalIdTest() {
		List<AssessmentManagementTarget> result = assessmentManagementTargetService.listUnrequiredUnavailableByPortalId(portalId);
		log.debug("==[listUnrequiredUnavailableByPortalIdTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(1, result.size());
	}
	
	@Test
	public void saveModulesTest() {
		assessmentManagementTarget.setModuleCodeCommaString("CC");

		assessmentManagementTargetService.saveModules(assessmentManagementTarget);

		List<AssessmentManagementTarget> result = assessmentManagementTargetService.listUnrequiredAvailableByPortalId(portalId);
		log.debug("==[saveModulesTest]==> [" + result.size() + "]");
		log.debug(result);

		int buf = 0;
		for (AssessmentManagementTarget item : result) {
			buf += item.getSelection();
		}

		assertEquals(1, buf);
	}

}
