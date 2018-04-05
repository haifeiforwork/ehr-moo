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
import com.lgcns.ikep4.support.tagging.dao.TagDictionaryDao;
import com.lgcns.ikep4.support.tagging.dao.TagItemDao;
import com.lgcns.ikep4.support.tagging.dao.TagLinkDao;
import com.lgcns.ikep4.support.tagging.model.Tag;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagLinkDaoTestCase.java 16259 2011-08-18 05:40:01Z giljae $
 */
public class TagLinkDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private TagLinkDao tagLinkDao;
	
	@Autowired
	private TagDictionaryDao tagDictionaryDao;
	
	@Autowired
	private TagItemDao tagItemDao;

	private Tag tag;

	@Before
	public void setUp() {
		
		tag = new Tag();
		
		tag.setTagId("4");			
		tag.setTagItemId("2");		
		tag.setTagItemType("2");	
		tag.setRegisterId("lee");
		tag.setRegisterName("dong");
		
		tag.setTagName("tag");
		tag.setPortalId("p1");
		
		tag.setTagItemId("333");
		tag.setTagItemType("px");
		tag.setTagItemName("title");
		tag.setTagItemUrl("httt://");
		tag.setTagItemSubType("Q");
		tag.setSortOrder(0);
		
		tagDictionaryDao.create(tag);
		
		tagItemDao.create(tag);
		
		tagLinkDao.create(tag);
		
	}
	
	@Test
	public void testRead() {
		
		Tag result = tagLinkDao.read(tag.getTagId(), tag.getTagItemId(), tag.getTagItemType());
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testDelete() {
		
		tagLinkDao.remove(tag.getTagId(), tag.getTagItemId(), tag.getTagItemType());
		
		Tag result = tagLinkDao.read(tag.getTagId(), tag.getTagItemId(), tag.getTagItemType());
		
		assertNull(result);
		
	}
	

}
