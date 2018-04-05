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

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapCategory;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapTagging;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapCategoryService;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapTaggingService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * KnowledgeMapTaggingService Test Unit
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapTaggingServiceTest.java 16457 2011-08-30 04:20:17Z giljae $
 */
public class KnowledgeMapTaggingServiceTest extends BaseServiceTestCase {

	@Autowired
	private KnowledgeMapTaggingService knowledgeMapTaggingService;
	@Autowired
	private IdgenService idgenService;
	@Autowired
	private KnowledgeMapCategoryService knowledgeMapCategoryService;

	private KnowledgeMapTagging knowledgeMapTagging;
	private String categoryId = "";
	private KnowledgeMapCategory knowledgeMapCategory;
	private String portalId = "";

	@Before
	public void setUp() {
		portalId = idgenService.getNextId();
		String rootCategoryId = idgenService.getNextId();

		// 카테고리 생성
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

		// Tag 생성
		knowledgeMapTagging = new KnowledgeMapTagging();
		knowledgeMapTagging.setCategoryId(categoryId);
		knowledgeMapTagging.setSortOrder(1);
		knowledgeMapTagging.setTag("testTag");
		knowledgeMapTaggingService.create(knowledgeMapTagging);
	}

	@Test
	public void listByCategoryIdTest() {
		List<KnowledgeMapTagging> result = knowledgeMapTaggingService.listByCategoryId(categoryId);

		assertEquals(1, result.size());
	}

	@Test
	public void createTagsTest() {
		categoryId = idgenService.getNextId();
		knowledgeMapCategory.setCategoryId(categoryId);
		knowledgeMapCategory.setCategoryName("testCategoryNode2");
		knowledgeMapCategoryService.create(knowledgeMapCategory);

		String tags = "a,s,d,f"; 

		knowledgeMapTaggingService.createTags(categoryId, tags);

		List<KnowledgeMapTagging> result = knowledgeMapTaggingService.listByCategoryId(categoryId);

		assertEquals(4, result.size());
	}

	@Test
	public void getTagsByCategoryIdTest() {
		categoryId = idgenService.getNextId();
		knowledgeMapCategory.setCategoryId(categoryId);
		knowledgeMapCategory.setCategoryName("testCategoryNode2");
		knowledgeMapCategoryService.create(knowledgeMapCategory);

		String tags = "a,s,d,f"; 

		knowledgeMapTaggingService.createTags(categoryId, tags);

		String result = knowledgeMapTaggingService.getTagsByCategoryId(categoryId);

		assertEquals(tags, result);
	}

	@Test
	public void deleteByCategoryIdTest() {
		knowledgeMapTaggingService.deleteByCategoryId(categoryId);

		List<KnowledgeMapTagging> result = knowledgeMapTaggingService.listByCategoryId(categoryId);

		assertEquals(0, result.size());
	}

	@Test
	public void deleteByCategoryIdHierarchyTest() {
		knowledgeMapTaggingService.deleteByCategoryIdHierarchy(categoryId);

		List<KnowledgeMapTagging> result = knowledgeMapTaggingService.listByCategoryId(categoryId);

		assertEquals(0, result.size());
	}

}
