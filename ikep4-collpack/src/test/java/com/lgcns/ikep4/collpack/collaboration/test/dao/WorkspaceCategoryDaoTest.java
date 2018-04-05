/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.collaboration.admin.dao.CategoryDao;
import com.lgcns.ikep4.collpack.collaboration.admin.model.Category;
import com.lgcns.ikep4.collpack.collaboration.admin.search.CategorySearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 김종철
 * @version $Id: WorkspaceCategoryDaoTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class WorkspaceCategoryDaoTest extends BaseDaoTestCase{
	@Autowired
	private CategoryDao workspaceCategoryDao;



	
	private Category category;
	
	private Category categorySet;

	private String pk;

	@Before
	public void setUp() {
		category = new Category();
	
		category.setPortalId(portalId);
		
		category.setCategoryId(categoryId);
		category.setCategoryName("test Category");
		category.setCategoryEnglishName("test Category");
		category.setTypeId(typeId);
		category.setSortOrder(1);
		category.setIsDelete(0);
		category.setRegisterId(registerId);
		category.setRegisterName(registerName);
		category.setUpdaterId(updaterId);
		category.setUpdaterName(updaterName);



		this.pk = workspaceCategoryDao.create(category);

		categorySet = category;
	}

	@Test
	public void testCreate() {
		Category result = workspaceCategoryDao.get(this.pk);
		assertNotNull(result);
	}

	@Test
	public void testGet() {
		Category result = workspaceCategoryDao.get(this.pk);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.category = workspaceCategoryDao.get(this.pk);
		
		this.category.setCategoryName("modified Category");

		workspaceCategoryDao.update(this.category);
		
		Category result = workspaceCategoryDao.get(this.pk);
		
		assertEquals(this.category.getCategoryName(), result.getCategoryName());
	}
	@Test
	public void testLogicalDelete() {
		workspaceCategoryDao.logicalDelete(categorySet);
		
		Category result = workspaceCategoryDao.get(this.pk);
		
		
		assertNotNull(result);
	}
	@Test
	public void testPhysicalDelete() {
		workspaceCategoryDao.physicalDelete(this.pk);
		
		Category result = workspaceCategoryDao.get(this.pk);
		
		//assertNull(result);
		assertNull(result);
	}

	@Test
	public void testExist() {
		//Category category = workspaceCategoryDao.get(this.pk);
		boolean result = workspaceCategoryDao.exists(this.pk);
		assertTrue(result);
	}


	@Test
	public void testListBySearchCondition() {
		CategorySearchCondition searchCondition = new CategorySearchCondition();
		
		searchCondition.setPortalId(portalId);
		//searchCondition.setSearchColumn("WORKSPACE_NAME");
		
		
		List<Category> result = workspaceCategoryDao.listBySearchCondition(searchCondition);
		assertNotNull(result);
	}
	@Test
	public void testCountBySearchCondition() {
		
		CategorySearchCondition searchCondition = new CategorySearchCondition();
		
		searchCondition.setPortalId(portalId);
		//searchCondition.setSearchColumn("WORKSPACE_NAME");
		
		
		int count = workspaceCategoryDao.countBySearchCondition(searchCondition);
		assertNotNull(count);
	}
	
}
