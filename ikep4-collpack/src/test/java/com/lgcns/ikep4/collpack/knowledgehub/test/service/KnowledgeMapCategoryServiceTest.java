/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * KnowledgeMapCategoryService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapCategoryServiceTest.java 16457 2011-08-30 04:20:17Z giljae $
 */
public class KnowledgeMapCategoryServiceTest extends BaseServiceTestCase {

	@Autowired
	private KnowledgeMapCategoryService knowledgeMapCategoryService;
	@Autowired
	private IdgenService idgenService;

	private KnowledgeMapCategory knowledgeMapCategory;
	private String categoryId = "";
	private String portalId = "";

	@Before
	public void setUp() {
		portalId = idgenService.getNextId();
		String rootCategoryId = idgenService.getNextId();

		knowledgeMapCategory = new KnowledgeMapCategory();
		knowledgeMapCategory.setCategoryId(rootCategoryId);
		knowledgeMapCategory.setCategoryParentId(rootCategoryId);
		knowledgeMapCategory.setCategoryName("testCategory");
		knowledgeMapCategory.setSortOrder(1);
		knowledgeMapCategory.setPortalId(portalId);
		knowledgeMapCategory.setRegisterId("admin");
		knowledgeMapCategory.setRegisterName("관리자");
		knowledgeMapCategoryService.create(knowledgeMapCategory);
		
		categoryId = idgenService.getNextId();
		knowledgeMapCategory.setCategoryId(categoryId);
		knowledgeMapCategory.setCategoryName("testCategoryNode1");
		knowledgeMapCategoryService.create(knowledgeMapCategory);
	}

	@Test
	public void readCreateRootCategoryTest() {
		KnowledgeMapCategory result = knowledgeMapCategoryService.readCreateRootCategory(knowledgeMapCategory);

		assertEquals(portalId, result.getPortalId());
	}

	@Test
	public void listByCategoryParentIdTest() {
		List<KnowledgeMapCategory> result = knowledgeMapCategoryService.listByCategoryParentId(knowledgeMapCategory.getCategoryParentId());

		assertEquals(1, result.size());
	}

	@Test
	public void listAndChildCountByCategoryParentIdTest() {
		List<KnowledgeMapCategory> result = knowledgeMapCategoryService.listAndChildCountByCategoryParentId(knowledgeMapCategory.getCategoryParentId());

		assertEquals(1, result.size());
	}

	@Test
	public void listAndChildCountAndTagsByCategoryParentIdTest() {
		List<KnowledgeMapCategory> result = knowledgeMapCategoryService.listAndChildCountAndTagsByCategoryParentId(knowledgeMapCategory.getCategoryParentId());

		assertEquals(1, result.size());
	}

	@Test
	public void createWithTagsTest() {
		String tags = "a,s,d,f";
		knowledgeMapCategory.setTags(tags);

		KnowledgeMapCategory result = knowledgeMapCategoryService.createWithTags(knowledgeMapCategory);

		assertNotNull(result);
	}

	@Test
	public void updateWithTagsTest() {
		String tags = "a,s,d,f";
		knowledgeMapCategory.setTags(tags);

		knowledgeMapCategoryService.updateWithTags(knowledgeMapCategory);

		assertEquals(1, 1);
	}

	@Test
	public void deleteWithTagsHierarchyTest() {
		knowledgeMapCategoryService.deleteWithTagsHierarchy(categoryId);

		assertEquals(1, 1);
	}

	@Test
	public void countByCategoryNameTest() {
		int result = knowledgeMapCategoryService.countByCategoryName(knowledgeMapCategory);

		assertEquals(1, result);
	}

}
