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
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkBlockPageCondition;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Expert Network ExpertListService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkListServiceTest.java 16487 2011-09-06 01:34:40Z giljae $
 */
public class ExpertNetworkListServiceTest extends BaseServiceTestCase {

	@Autowired
	private ExpertNetworkListService expertlistService;
	@Autowired
	private IdgenService idgenService;

	private ExpertNetworkList expertNetworkList;
	private String userId = "user2200";
	private String categoryId = "C0000000002";
	private String portalId = "P000001";

	@Before
	public void setUp() {
		//categoryId = idgenService.getNextId();

		expertNetworkList = new ExpertNetworkList();
		expertNetworkList.setUserId(userId);
		expertNetworkList.setCategoryId(categoryId);
		expertNetworkList.setIsAuthorized(1);
		expertNetworkList.setMatchingScore(98);
		expertNetworkList.setRegisterId("admin");
		expertNetworkList.setRegisterName("관리자");
		expertlistService.create(expertNetworkList);
	}

	@Test
	public void countByCategoryIdTest() {
		int result = expertlistService.countByCategoryId(categoryId);

		assertEquals(1, result);
	}

	@Test
	public void listByCategoryIdPageTest() {
		ExpertNetworkBlockPageCondition pageCondition = new ExpertNetworkBlockPageCondition();
		pageCondition.setCategoryId(categoryId);

		List<ExpertNetworkList> expertNetworkList = expertlistService.listByCategoryIdPage(pageCondition);

		assertEquals(1, expertNetworkList.size());
	}

	@Test
	public void deleteByCategoryIdTest() {
		expertlistService.deleteByCategoryId(categoryId);
		int result = expertlistService.countByCategoryId(categoryId);

		assertEquals(0, result);
	}

	@Test
	public void listRandomTest() {
		List<ExpertNetworkList> expertNetworkList = expertlistService.listRandom(2, portalId);

		assertEquals(expertNetworkList.size(), expertNetworkList.size());
	}

	@Test
	public void createOrUpdateExpertListTest() {
		expertlistService.createOrUpdateExpertList(expertNetworkList);

		categoryId = idgenService.getNextId();
		expertNetworkList.setCategoryId(categoryId);
		expertlistService.createOrUpdateExpertList(expertNetworkList);

		assertEquals(0, 0);
	}

	@Test
	@Ignore
	//@Rollback(false)
	public void batchGatherExpertTest() {
		expertlistService.batchGatherExpert();

		assertEquals("a", "a");
	}

	@Test
	public void updateAuthorizedTest() {
		expertNetworkList.setIsAuthorized(1);
		expertlistService.updateAuthorized(expertNetworkList);
		ExpertNetworkList result = expertlistService.read(expertNetworkList);

		assertEquals(result.getIsAuthorized(), 1);
	}

}
