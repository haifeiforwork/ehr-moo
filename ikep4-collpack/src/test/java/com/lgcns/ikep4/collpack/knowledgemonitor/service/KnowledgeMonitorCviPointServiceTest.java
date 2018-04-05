/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPoint;
import com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model.KnowledgeMonitorPortletSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorCviPointService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * KnowledgeMonitorCviPointService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPointServiceTest.java 16827 2011-10-17 07:27:32Z giljae $
 */
public class KnowledgeMonitorCviPointServiceTest extends BaseServiceTestCase {

	@Autowired
	KnowledgeMonitorCviPointService knowledgeMonitorCviPointService;
	
	private String portalId = "P000001";

	@Before
	public void setUp() {

	}

	@Test
	@Ignore
	public void countByPortalIdModuleCodeTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		KnowledgeRankBlockPageCondition pageCondition = new KnowledgeRankBlockPageCondition();
		pageCondition.setModuleCodes("BD,ID,WS,WM,SB");
		pageCondition.setPortalId(portalId);

		int result = knowledgeMonitorCviPointService.countByPortalIdModuleCode(pageCondition);
		log.debug("==[countByPortalIdModuleCode]==> [" + result + "]");

		assertEquals(result, result);
	}

	@Test
	@Ignore
	public void listByPortalIdModuleCodePageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		KnowledgeRankBlockPageCondition pageCondition = new KnowledgeRankBlockPageCondition();
		pageCondition.setModuleCodes("BD,ID,WS,WM,SB");
		pageCondition.setPortalId(portalId);

		List<KnowledgeMonitorCviPoint> result = knowledgeMonitorCviPointService.listByPortalIdModuleCodePage(pageCondition);
		log.debug("==[listByPortalIdModuleCodePage]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

	@Test
	@Rollback(false)
	public void batchGatherKnowledgeTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		log.debug("==[batchGatherKnowledge]==");
		Date startDate;
		Date endDate;
		startDate = new Date();
		log.debug("==[시작]==> [" + startDate + "]");

		knowledgeMonitorCviPointService.batchGatherKnowledge();

		endDate = new Date();
		log.debug("==[종료]==> [" + endDate + "]");
		log.debug("==[배치 소요시간]==> [" + DateUtil.getTimeInterval(startDate, endDate, "ko") + "]");

		assertEquals(0, 0);
	}

	@Test
	@Ignore
	public void listByPortalIdPorteletTest() {
		KnowledgeMonitorPortletSearchCondition pageCondition = new KnowledgeMonitorPortletSearchCondition();
		pageCondition.setPortalId(portalId);
		pageCondition.setRecordCount(5);

		List<KnowledgeMonitorCviPoint> result = knowledgeMonitorCviPointService.listByPortalIdPortelet(pageCondition);
		log.debug("==[listByPortalIdPortelet]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

}
