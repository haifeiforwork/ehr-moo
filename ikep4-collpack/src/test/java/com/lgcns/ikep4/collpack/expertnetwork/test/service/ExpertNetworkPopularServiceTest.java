/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPopular;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPopularService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Expert Network ExpertPopularService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPopularServiceTest.java 16487 2011-09-06 01:34:40Z giljae $
 */
public class ExpertNetworkPopularServiceTest extends BaseServiceTestCase {

	@Autowired
	private ExpertNetworkPopularService expertNetworkPopularService;
	@Autowired
	private IdgenService idgenService;

	private ExpertNetworkPopular expertNetworkPopular;
	private List<ExpertNetworkPopular> expertPopularList;

	private String portalId = "";

	@Before
	public void setUp() {
		portalId = idgenService.getNextId();

		expertNetworkPopular = new ExpertNetworkPopular();
		expertNetworkPopular.setUserId("user2200");
		expertNetworkPopular.setScore(90);
		expertNetworkPopular.setSortOrder(1);
		expertNetworkPopular.setPortalId(portalId);
		expertNetworkPopularService.create(expertNetworkPopular);
	}

	@Test
	public void listByPortalIdTest() {
		expertPopularList = expertNetworkPopularService.listByPortalId(portalId);

		assertNotNull(expertPopularList);
	}

	@Test
	@Ignore
	//@Rollback(false)
	public void batchGatherPopularTest() {
		// 테스트 데이터 생성이 곤란하여 로그 정보로 판단
		log.debug("==[batchGatherKnowledge]==> [");
		Date startDate;
		Date endDate;
		startDate = new Date();
		log.debug("==[시작]==> [" + startDate + "]");

		expertNetworkPopularService.batchGatherPopular(20);

		endDate = new Date();
		log.debug("==[종료]==> [" + endDate + "]");
		log.debug("==[배치 소요시간]==> [" + DateUtil.getTimeInterval(startDate, endDate, "ko") + "]");

		assertEquals(0, 0);
	}

}
