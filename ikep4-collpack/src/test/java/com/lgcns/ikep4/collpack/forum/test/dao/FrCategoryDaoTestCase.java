/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrCategoryDao;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrCategoryDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrCategoryDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrCategoryDao frCategoryDao;

	private FrCategory frCategory;

	private String pk;

	@Before
	public void setUp() {
		frCategory = new FrCategory();
		
		pk = "20";
		
		frCategory.setCategoryId(pk);
		frCategory.setCategoryName("카테고리 이름");
		frCategory.setRegisterId("dong");
		frCategory.setRegisterName("동");
		frCategory.setPortalId("p1");
		
		frCategoryDao.create(frCategory);
		
	}

	@Test
	public void testCreate() {
		
		FrCategory result = frCategoryDao.get(pk);
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		
		frCategory.setCategoryName("카테고리 이름");
		
		frCategoryDao.update(frCategory);
		
		FrCategory result = frCategoryDao.get(pk);
		assertEquals(frCategory.getCategoryName(), result.getCategoryName());
	}
	
	@Test
	public void testList() {
		
		List<FrCategory> result = frCategoryDao.list("p1");
		assertFalse(result.isEmpty());
	}

	
	@Test
	public void testUpdateOrder() {
		
		frCategoryDao.updateOrder(pk, 1);
		FrCategory result = frCategoryDao.get(pk);
		assertNotNull(result);
		
	}
	
	@Test
	public void testRemove() {
		
		frCategoryDao.remove(pk);
		FrCategory result = frCategoryDao.get(pk);
		assertNull(result);
		
	}
	

}
