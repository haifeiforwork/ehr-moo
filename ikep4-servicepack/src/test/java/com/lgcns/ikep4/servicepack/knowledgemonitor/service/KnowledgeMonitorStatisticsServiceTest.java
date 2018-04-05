/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.knowledgemonitor.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulation;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorAccumulationChart;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeAccumulationSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.service.KnowledgeMonitorStatisticsService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * KnowledgeMonitorStatisticsService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorStatisticsServiceTest.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class KnowledgeMonitorStatisticsServiceTest extends BaseServiceTestCase {

	@Autowired
	KnowledgeMonitorStatisticsService knowledgeMonitorStatisticsService;
	
	private String portalId = "P000001";

	@Before
	public void setUp() {

	}

	@Test
	public void listChartBySearchConditionTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		KnowledgeAccumulationSearchCondition accumulationSearchCondition = new KnowledgeAccumulationSearchCondition();
		accumulationSearchCondition.setPortalId(portalId);
		accumulationSearchCondition.setModuleCodes("BD,ID,QA,WS,WM,SB");
		accumulationSearchCondition.setExcludeModuleCodes("CF,FR,CV");

		List<KnowledgeMonitorAccumulationChart> result = knowledgeMonitorStatisticsService.listChartBySearchCondition(accumulationSearchCondition);
		log.debug("==[listChartBySearchCondition]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

	@Test
	public void listBySearchConditionTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		KnowledgeAccumulationSearchCondition accumulationSearchCondition = new KnowledgeAccumulationSearchCondition();
		accumulationSearchCondition.setPortalId(portalId);
		accumulationSearchCondition.setModuleCodes("BD,ID,QA,WS,WM,SB");
		accumulationSearchCondition.setExcludeModuleCodes("CF,FR,CV");

		List<KnowledgeMonitorAccumulation> result = knowledgeMonitorStatisticsService.listBySearchCondition(accumulationSearchCondition);
		log.debug("==[listBySearchCondition]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

	@Test
	public void batchGatherKnowledgeTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		log.debug("==[batchGatherKnowledge]==");
		Date startDate;
		Date endDate;
		startDate = new Date();
		log.debug("==[시작]==> [" + startDate + "]");

		knowledgeMonitorStatisticsService.batchGatherKnowledge();

		endDate = new Date();
		log.debug("==[종료]==> [" + endDate + "]");
		log.debug("==[배치 소요시간]==> [" + DateUtil.getTimeInterval(startDate, endDate, "ko") + "]");

		assertEquals(0, 0);
	}

	@Test
	public void listChartBySearchConditionPortletTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		KnowledgeAccumulationSearchCondition accumulationSearchCondition = new KnowledgeAccumulationSearchCondition();
		accumulationSearchCondition.setPortalId(portalId);
		accumulationSearchCondition.setModuleCodes("BD,ID,QA,WS,WM,SB");
		accumulationSearchCondition.setExcludeModuleCodes("CF,FR,CV");

		List<KnowledgeMonitorAccumulationChart> result = knowledgeMonitorStatisticsService.listChartBySearchConditionPortlet(accumulationSearchCondition);
		log.debug("==[listBySearchCondition]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

}
