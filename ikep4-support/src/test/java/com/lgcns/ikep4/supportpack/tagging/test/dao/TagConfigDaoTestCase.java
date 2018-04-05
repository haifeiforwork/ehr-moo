/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.tagging.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.tagging.dao.TagConfigDao;
import com.lgcns.ikep4.support.tagging.model.Tag;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagConfigDaoTestCase.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class TagConfigDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private TagConfigDao tagConfigDao;

	private Tag tag;

	@Before
	public void setUp() {
		
		tag = new Tag();
		
		tag.setDisplayStep(1);
		tag.setTagFrequency(20);
		tag.setPortalId("p1");
		
		tagConfigDao.create(tag);
		
	}
	
	@Test
	public void testList() {
		
		List<Tag> result = tagConfigDao.listByPortalId("p1");
		
		assertNotNull(result);
	}

	
	@Test
	public void testUpdate() {
		
		tag.setTagFrequency(30);
		
		tagConfigDao.update(tag);
		
		List<Tag> result = tagConfigDao.listByPortalId("p1");
		
		assertNotNull(result);
	}
	

	
	
	@Test
	public void testDelete() {
		
		tagConfigDao.remove(1, "p1");
		
		List<Tag> result = tagConfigDao.listByPortalId("p1");
		
		assertEquals(result.size(),0);
		
	}
	

}
