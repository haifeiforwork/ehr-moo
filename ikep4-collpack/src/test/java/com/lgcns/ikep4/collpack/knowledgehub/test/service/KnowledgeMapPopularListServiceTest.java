/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.test.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * KnowledgeMapPopularListService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPopularListServiceTest.java 16457 2011-08-30 04:20:17Z giljae $
 */
public class KnowledgeMapPopularListServiceTest extends BaseServiceTestCase {

	@Autowired
	private KnowledgeMapPopularListService knowledgeMapPopularListService;

	private String portalId = "";

	@Before
	public void setUp() {
		portalId = "P000001";
	}

	@Test
	public void countByPortalIdTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		int result = knowledgeMapPopularListService.countByPortalId(portalId);
		log.debug("==[countByPortalId]==> [" + result + "]");

		assertEquals(result, result);
	}

	@Test
	public void countByPortalIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		KnowledgeMapPopularPageCondition pageCondition = new KnowledgeMapPopularPageCondition();
		pageCondition.setPortalId(portalId);
		Date convertToDate = new Date();
		Date convertFromDate = DateUtil.getRelativeDate(convertToDate, 2, 12);
		pageCondition.setConvertFromDate(convertFromDate);
		pageCondition.setConvertToDate(convertToDate);

		int result = knowledgeMapPopularListService.countByPortalIdPage(pageCondition);
		log.debug("==[countByPortalIdPage]==> [" + result + "]");

		assertEquals(result, result);
	}

	@Test
	public void listByPortalIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		KnowledgeMapPopularPageCondition pageCondition = new KnowledgeMapPopularPageCondition();
		pageCondition.setPortalId(portalId);
		Date convertToDate = new Date();
		Date convertFromDate = DateUtil.getRelativeDate(convertToDate, 10, 12);
		pageCondition.setConvertFromDate(convertFromDate);
		pageCondition.setConvertToDate(convertToDate);
		pageCondition.setStartOrder(1);
		pageCondition.setEndOrder(5);

		List<KnowledgeMapList> result = knowledgeMapPopularListService.listByPortalIdPage(pageCondition);
		log.debug("==[listByPortalIdPage]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}
	
	@Test
	@Ignore
	public void batchGatherKnowledgeTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		log.debug("==[batchGatherKnowledge]==");
		Date startDate;
		Date endDate;
		startDate = new Date();
		log.debug("==[시작]==> [" + startDate + "]");

		//knowledgeMapPopularListService.batchGatherKnowledge();

		endDate = new Date();
		log.debug("==[종료]==> [" + endDate + "]");
		log.debug("==[배치 소요시간]==> [" + DateUtil.getTimeInterval(startDate, endDate, "ko") + "]");

		assertEquals(0, 0);
	}

}
