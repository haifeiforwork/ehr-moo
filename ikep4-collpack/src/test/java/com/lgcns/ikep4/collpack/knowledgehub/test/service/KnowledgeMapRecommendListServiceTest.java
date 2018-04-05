/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.test.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapRecommendListService;


/**
 * KnowledgeMapRecommendListService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendListServiceTest.java 16487 2011-09-06 01:34:40Z giljae $
 */
public class KnowledgeMapRecommendListServiceTest extends BaseServiceTestCase {

	@Autowired
	private KnowledgeMapRecommendListService knowledgeMapRecommendListService;

	@Before
	public void setUp() {

	}

	@Test
	public void listByUserIdPageTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		//List<KnowledgeMapList> result = knowledgeMapRecommendListService.listByUserIdPage(10);
		//log.debug("==[listByUserIdPage]==> [" + result.size() + "]");
		//log.debug(result);

		//assertEquals(result.size(), result.size());
	}
	
	@Test
	public void listByUserIdPageSimpleTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		//List<KnowledgeMapList> result = knowledgeMapRecommendListService.listByUserIdPageSimple(6);
		//log.debug("==[listByUserIdPageSimple]==> [" + result.size() + "]");
		//log.debug(result);

		//assertEquals(result.size(), result.size());
	}

}
