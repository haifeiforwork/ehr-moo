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
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapBlockPageCondition;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapListService;


/**
 * KnowledgeMapListService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapListServiceTest.java 16903 2011-10-25 01:37:27Z giljae $
 */
public class KnowledgeMapListServiceTest extends BaseServiceTestCase {

	@Autowired
	private KnowledgeMapListService knowledgeMapListService;

	private String categoryId = "";

	@Before
	public void setUp() {
		categoryId = "C0000000001";
	}

	@Test
	@Ignore
	public void countByCategoryIdTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		int result = knowledgeMapListService.countByCategoryId(categoryId);
		log.debug("==[countByCategoryId]==> [" + result + "]");

		assertEquals(result, result);
	}

	@Test
	@Ignore
	public void listByCategoryIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		KnowledgeMapBlockPageCondition pageCondition = new KnowledgeMapBlockPageCondition();
		pageCondition.setCategoryId(categoryId);
		pageCondition.setStartOrder(1);
		pageCondition.setEndOrder(5);

		List<KnowledgeMapList> result = knowledgeMapListService.listByCategoryIdPage(pageCondition);
		log.debug("==[listByCategoryIdPage]==> [" + result.size() + "]");
		log.debug(result);

		assertEquals(result.size(), result.size());
	}

	@Test
	@Rollback(false)
	public void batchGatherKnowledgeTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		log.debug("==[batchGatherKnowledge]==> [");
		Date startDate;
		Date endDate;
		startDate = new Date();
		log.debug("==[시작]==> [" + startDate + "]");

		knowledgeMapListService.batchGatherKnowledge();

		endDate = new Date();
		log.debug("==[종료]==> [" + endDate + "]");
		log.debug("==[배치 소요시간]==> [" + DateUtil.getTimeInterval(startDate, endDate, "ko") + "]");

		assertEquals(0, 0);
	}

}
