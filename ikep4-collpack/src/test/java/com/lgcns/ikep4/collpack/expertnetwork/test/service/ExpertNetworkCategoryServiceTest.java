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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkCategory;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkCategoryService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Expert Network ExpertCategoryService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkCategoryServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class ExpertNetworkCategoryServiceTest extends BaseServiceTestCase {

	@Autowired
	private ExpertNetworkCategoryService expertNetworkCategoryService;
	@Autowired
	private IdgenService idgenService;

	private ExpertNetworkCategory expertNetworkCategory;
	private String categoryId = "";
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
	}

	@Test
	public void readCreateRootCategoryTest() {
		ExpertNetworkCategory result = expertNetworkCategoryService.readCreateRootCategory(expertNetworkCategory);

		assertEquals(portalId, result.getPortalId());
	}

	@Test
	public void listByCategoryParentIdTest() {
		List<ExpertNetworkCategory> result = expertNetworkCategoryService.listByCategoryParentId(expertNetworkCategory.getCategoryParentId());

		assertEquals(1, result.size());
	}

	@Test
	public void listAndChildCountByCategoryParentIdTest() {
		List<ExpertNetworkCategory> result = expertNetworkCategoryService.listAndChildCountByCategoryParentId(expertNetworkCategory.getCategoryParentId());

		assertEquals(1, result.size());
	}

	@Test
	public void listAndChildCountAndTagsByCategoryParentIdTest() {
		List<ExpertNetworkCategory> result = expertNetworkCategoryService.listAndChildCountAndTagsByCategoryParentId(expertNetworkCategory.getCategoryParentId());

		assertEquals(1, result.size());
	}

	@Test
	public void createWithTagsTest() {
		String tags = "a,s,d,f";
		expertNetworkCategory.setTags(tags);

		ExpertNetworkCategory result = expertNetworkCategoryService.createWithTags(expertNetworkCategory);

		assertNotNull(result);
	}

	@Test
	public void updateWithTagsTest() {
		String tags = "a,s,d,f";
		expertNetworkCategory.setTags(tags);

		expertNetworkCategoryService.updateWithTags(expertNetworkCategory);

		assertEquals(1, 1);
	}

	@Test
	public void deleteWithTagsHierarchyTest() {
		expertNetworkCategoryService.deleteWithTagsHierarchy(categoryId);

		assertEquals(1, 1);
	}

	@Test
	public void countByCategoryNameTest() {
		int result = expertNetworkCategoryService.countByCategoryName(expertNetworkCategory);

		assertEquals(1, result);
	}

}
