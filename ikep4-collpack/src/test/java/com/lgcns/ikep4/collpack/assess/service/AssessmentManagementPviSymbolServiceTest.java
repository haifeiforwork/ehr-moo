/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPviSymbol;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPviSymbolService;


/**
 * AssessmentManagementPviSymbolService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPviSymbolServiceTest.java 16291 2011-08-19 07:31:11Z giljae $
 */
public class AssessmentManagementPviSymbolServiceTest extends BaseServiceTestCase {

	@Autowired
	AssessmentManagementPviSymbolService assessmentManagementPviSymbolService;

	private AssessmentManagementPviSymbol assessmentManagementPviSymbol;

	private String portalId = "TestPortal01";

	@Before
	public void setUp() {
		assessmentManagementPviSymbol = new AssessmentManagementPviSymbol();
		assessmentManagementPviSymbol.setPortalId(portalId);
		assessmentManagementPviSymbol.setRegisterId("admin");

		for (int i = 0; i < 10; i++) {
			assessmentManagementPviSymbol.setSymbolId("SB000000" + i);
			assessmentManagementPviSymbol.setStep(i);
			assessmentManagementPviSymbol.setSectionValue(10);
			assessmentManagementPviSymbol.setSectionStartScore(i * 10 + 1);
			assessmentManagementPviSymbol.setSectionEndScore(i * 10 + 1 + 9);
			assessmentManagementPviSymbolService.create(assessmentManagementPviSymbol);
		}
	}

	@Test
	public void listByPortalIdTest() {
		List<AssessmentManagementPviSymbol> result = assessmentManagementPviSymbolService.listByPortalId(portalId);
		log.debug("==[listByPortalIdTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(10, result.size());
	}

	@Test
	public void saveVisualSymbolTest() {
		Map<String, String> symbolMap = new HashMap<String, String>();

		for (int i = 0; i < 10; i++) {
			symbolMap.put("symbolId" + i, "SB000000" + i);
			symbolMap.put("sectionValue" + i, "10");
			symbolMap.put("sectionStartScore" + i, "" + (i * 100 + 1));
			symbolMap.put("sectionEndScore" + i, "" + (i * 10 + 1 + 9));
		}

		assessmentManagementPviSymbolService.saveVisualSymbol(symbolMap, "user22");

		List<AssessmentManagementPviSymbol> result = assessmentManagementPviSymbolService.listByPortalId(portalId);
		log.debug("==[saveVisualSymbolTest]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(101, result.get(1).getSectionStartScore());
	}

}
