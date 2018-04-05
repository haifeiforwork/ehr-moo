/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorModule;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorModuleService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * KnowledgeMonitorModuleService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModuleServiceTest.java 16827 2011-10-17 07:27:32Z giljae $
 */
public class KnowledgeMonitorModuleServiceTest extends BaseServiceTestCase {

	@Autowired
	KnowledgeMonitorModuleService knowledgeMonitorModuleService;

	private KnowledgeMonitorModule knowledgeMonitorModule;

	private String portalId = "TESTPORTAL01";

	@Before
	public void setUp() {
		knowledgeMonitorModule = new KnowledgeMonitorModule();
		knowledgeMonitorModule.setModuleCode("BD");
		knowledgeMonitorModule.setModuleName("게시판");
		knowledgeMonitorModule.setPortalId(portalId);
		knowledgeMonitorModule.setIsUsage(1);
		knowledgeMonitorModule.setHitWeight(10);
		knowledgeMonitorModule.setRecommendWeight(10);
		knowledgeMonitorModule.setLinereplyWeight(10);
		knowledgeMonitorModule.setFavoriteWeight(10);
		knowledgeMonitorModule.setDistributeWeight(10);
		knowledgeMonitorModule.setGoodDocCutline(500);
		knowledgeMonitorModule.setGoodLinereplyCutline(10);
		knowledgeMonitorModule.setCviEvaluationTerm(12);
		knowledgeMonitorModule.setRegisterId("admin");
		knowledgeMonitorModule.setRegisterName("관리자");
		knowledgeMonitorModule.setUpdaterId("admin");
		knowledgeMonitorModule.setUpdaterName("관리자");
		knowledgeMonitorModuleService.create(knowledgeMonitorModule);

		knowledgeMonitorModule.setModuleCode("SB");
		knowledgeMonitorModule.setModuleName("블로그");
		knowledgeMonitorModule.setIsUsage(0);
		knowledgeMonitorModuleService.create(knowledgeMonitorModule);
	}

	@Test
	public void listByPortalIdTest() {
		List<KnowledgeMonitorModule> result = knowledgeMonitorModuleService.listByPortalId(portalId);
		log.debug("==[listByPortalId]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(2, result.size());
	}

	@Test
	public void listUsableByPortalIdTest() {
		List<KnowledgeMonitorModule> result = knowledgeMonitorModuleService.listUsableByPortalId(portalId);
		log.debug("==[listUsableByPortalId]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(1, result.size());
	}

	@Test
	public void saveModulesTest() {
		knowledgeMonitorModule.setModuleCodeCommaString("BD,SB");
		knowledgeMonitorModuleService.saveModules(knowledgeMonitorModule);

		List<KnowledgeMonitorModule> result = knowledgeMonitorModuleService.listUsableByPortalId(portalId);
		assertEquals(2, result.size());
	}

	@Test
	public void listContentPolicyByPortalIdTest() {
		List<KnowledgeMonitorModule> result = knowledgeMonitorModuleService.listContentPolicyByPortalId(portalId);

		assertEquals(1, result.size());
	}

	@Test
	public void saveContentCriteriaTest() {
		User user = new User();
		user.setPortalId(portalId);
		user.setUserId("admin");
		user.setUserName("관리자");

		Map<String, String> map = new HashMap<String, String>();
		map.put("moduleCodeCommaString", "BD");
		map.put("evaluationTerm", "12");
		map.put("BDHit", "22");

		knowledgeMonitorModuleService.saveContentCriteria(map, user);

		int buf = 0;
		List<KnowledgeMonitorModule> result = knowledgeMonitorModuleService.listContentPolicyByPortalId(portalId);
		for (KnowledgeMonitorModule item : result) {
			if (item.getModuleCode().equals("BD")) {
				buf = item.getHitWeight();
			}
		}

		assertEquals(22, buf);
	}

	@Test
	public void listCommentPolicyByPortalIdTest() {
		List<KnowledgeMonitorModule> result = knowledgeMonitorModuleService.listCommentPolicyByPortalId(portalId);

		assertEquals(1, result.size());
	}

	@Test
	public void saveCommentCriteriaTest() {
		User user = new User();
		user.setPortalId(portalId);
		user.setUserId("admin");
		user.setUserName("관리자");

		Map<String, String> map = new HashMap<String, String>();
		map.put("BD", "22");

		knowledgeMonitorModuleService.saveCommentCriteria(map, user);

		int buf = 0;
		List<KnowledgeMonitorModule> result = knowledgeMonitorModuleService.listCommentPolicyByPortalId(portalId);
		for (KnowledgeMonitorModule item : result) {
			if (item.getModuleCode().equals("BD")) {
				buf = item.getGoodLinereplyCutline();
			}
		}

		assertEquals(22, buf);
	}

}
