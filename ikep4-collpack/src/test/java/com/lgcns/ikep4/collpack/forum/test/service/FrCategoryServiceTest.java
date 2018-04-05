/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.service.FrCategoryService;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrCategoryServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrCategoryServiceTest extends BaseServiceTestCase {

	@Autowired
	private FrCategoryService frCategoryService;

	private FrCategory frCategory;
	
	private String pk;

	@Before
	public void setUp() {
		
		frCategory = new FrCategory();
		
		frCategory.setCategoryName("카테고리 이름");
		frCategory.setRegisterId("dong");
		frCategory.setRegisterName("동");
		frCategory.setPortalId("p1");
		
		pk = frCategoryService.create(frCategory);
	}

	@Test
	//@Rollback(false)
	public void testCreate() {
		
		FrCategory result = frCategoryService.get(pk);
		
		assertNotNull(result);
	}

	@Test
	public void testList() {
		
		List<FrCategory> result = frCategoryService.list("p1");
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		
		frCategory.setCategoryName("카테고리 이름");
		
		frCategoryService.update(frCategory);
		
		FrCategory result = frCategoryService.get(pk);
		
		assertEquals(frCategory.getCategoryName(), result.getCategoryName());
	}
	
	
	@Test
	public void testUpdateCategoryOrder() {
		
		frCategoryService.updateCategoryOrder(pk);
		FrCategory result = frCategoryService.get(pk);
		assertNotNull(result);
	}
	
	
	@Test
	public void testDelete() {
		
		frCategoryService.delete(pk, "p1");
		FrCategory result = frCategoryService.get(pk);
		assertNull(result);
		
	}
}
