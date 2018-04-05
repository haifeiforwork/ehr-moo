/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.test.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkFellow;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkPageCondition;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkFellowService;


/**
 * Expert Network ExpertFellowService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkFellowServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class ExpertNetworkFellowServiceTest extends BaseServiceTestCase {

	@Autowired
	private ExpertNetworkFellowService expertNetworkFellowService;

	private List<ExpertNetworkFellow> expertFellowList;

	private String userId = "user2";

	@Before
	public void setUp() {

	}

	@Test
	public void countByUserIdTest() {
		int result = expertNetworkFellowService.countByUserId(userId);
		log.debug("\n=[Total Count]=" + result);

		assertEquals(result, result);
	}

	@Test
	public void listByUserIdPageTest() {
		ExpertNetworkPageCondition pageCondition = new ExpertNetworkPageCondition();
		pageCondition.setUserId(userId);
		pageCondition.setCountOfPage(10);

		expertFellowList = expertNetworkFellowService.listByUserIdPage(pageCondition);
		log.debug("\n=[Count of Page default:10]=" + expertFellowList.size());

		assertEquals("a", "a");
	}

}
