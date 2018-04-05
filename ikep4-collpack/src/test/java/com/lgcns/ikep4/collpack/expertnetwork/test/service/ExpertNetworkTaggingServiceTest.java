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

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkTagging;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkTaggingService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Expert Network ExpertTaggingService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkTaggingServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class ExpertNetworkTaggingServiceTest extends BaseServiceTestCase {

	@Autowired
	private ExpertNetworkTaggingService expertNetworkTaggingService;
	@Autowired
	private IdgenService idgenService;
	@Autowired
	private ExpertNetworkCategoryService expertNetworkCategoryService;

	private ExpertNetworkTagging expertNetworkTagging;
	private String categoryId = "";
	private ExpertNetworkCategory expertNetworkCategory;
	private String portalId = "";

	@Before
	public void setUp() {
		portalId = idgenService.getNextId();
		String rootCategoryId = idgenService.getNextId();

		expertNetworkCategory = new ExpertNetworkCategory();
		expertNetworkCategory.setCategoryId(rootCategoryId);
		expertNetworkCategory.setCategoryParentId(rootCategoryId);
		expertNetworkCategory.setCategoryName("testCategory");
		expertNetworkCategory.setSortOrder(1);
		expertNetworkCategory.setPortalId(portalId);
		expertNetworkCategory.setRegisterId("admin");
		expertNetworkCategory.setRegisterName("관리자");
		expertNetworkCategoryService.create(expertNetworkCategory);
		
		categoryId = idgenService.getNextId();
		expertNetworkCategory.setCategoryId(categoryId);
		expertNetworkCategory.setCategoryName("testCategoryNode1");
		expertNetworkCategoryService.create(expertNetworkCategory);

		expertNetworkTagging = new ExpertNetworkTagging();
		expertNetworkTagging.setCategoryId(categoryId);
		expertNetworkTagging.setSortOrder(1);
		expertNetworkTagging.setTag("testTag");
		expertNetworkTaggingService.create(expertNetworkTagging);
	}

	@Test
	public void listByCategoryIdTest() {
		List<ExpertNetworkTagging> result = expertNetworkTaggingService.listByCategoryId(categoryId);

		assertEquals(1, result.size());
	}

	@Test
	public void createTagsTest() {
		categoryId = idgenService.getNextId();
		expertNetworkCategory.setCategoryId(categoryId);
		expertNetworkCategory.setCategoryName("testCategoryNode2");
		expertNetworkCategoryService.create(expertNetworkCategory);

		String tags = "a,s,d,f"; 

		expertNetworkTaggingService.createTags(categoryId, tags);

		List<ExpertNetworkTagging> result = expertNetworkTaggingService.listByCategoryId(categoryId);

		assertEquals(4, result.size());
	}

	@Test
	public void getTagsByCategoryIdTest() {
		categoryId = idgenService.getNextId();
		expertNetworkCategory.setCategoryId(categoryId);
		expertNetworkCategory.setCategoryName("testCategoryNode2");
		expertNetworkCategoryService.create(expertNetworkCategory);

		String tags = "a,s,d,f"; 

		expertNetworkTaggingService.createTags(categoryId, tags);

		String result = expertNetworkTaggingService.getTagsByCategoryId(categoryId);

		assertEquals(tags, result);
	}

	@Test
	public void deleteByCategoryIdTest() {
		expertNetworkTaggingService.deleteByCategoryId(categoryId);

		List<ExpertNetworkTagging> result = expertNetworkTaggingService.listByCategoryId(categoryId);

		assertEquals(0, result.size());
	}

	@Test
	public void deleteByCategoryIdHierarchyTest() {
		expertNetworkTaggingService.deleteByCategoryIdHierarchy(categoryId);

		List<ExpertNetworkTagging> result = expertNetworkTaggingService.listByCategoryId(categoryId);

		assertEquals(0, result.size());
	}

}
