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

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.tagging.dao.TagConfigDao;
import com.lgcns.ikep4.support.tagging.dao.TagDao;
import com.lgcns.ikep4.support.tagging.dao.TagDictionaryDao;
import com.lgcns.ikep4.support.tagging.dao.TagItemDao;
import com.lgcns.ikep4.support.tagging.dao.TagLinkDao;
import com.lgcns.ikep4.support.tagging.model.Tag;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: TagDaoTestCase.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class TagDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private TagLinkDao tagLinkDao;
	
	@Autowired
	private TagDictionaryDao tagDictionaryDao;
	
	@Autowired
	private TagItemDao tagItemDao;
	
	@Autowired
	private TagConfigDao tagConfigDao;

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
		
		tagDictionaryDao.create(tag);
		
		tagItemDao.create(tag);
		
		tagLinkDao.create(tag);
		
		tag.setDisplayStep(1);
		tag.setTagFrequency(20);
		tag.setPortalId("p1");
		
		tagConfigDao.create(tag);
		
		
	}
	
	@Test
	public void testListMain() {
		
		Tag search = new Tag();
		
		search.setRegisterId(tag.getRegisterName());
		search.setListType("main");
		search.setTagOrder("tagName");
		search.setEndRowIndex(100);
		search.setStartRowIndex(0);
		
		List<Tag> result = tagDao.listCloud(search);
		
		assertNotNull(result);
		
		int count = tagDao.readCount(tag);
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testList() {
		
		Tag search = new Tag();
		
		String[] tagItemTypes = new String[1];
		tagItemTypes[0] = "2";
		
		//search.setRegisterId(tag.getRegisterName());
		search.setTagOrder("tagName");
		search.setTagItemType("2");
		search.setEndRowIndex(100);
		search.setStartRowIndex(0);
		
		List<Tag> result = tagDao.listCloud(search);
		
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testListCount() {
		
		Tag search = new Tag();
		
		String[] tagItemTypes = new String[1];
		tagItemTypes[0] = "2";
		
		//search.setRegisterId(tag.getRegisterName());
		search.setTagOrder("tagName");
		search.setTagItemType("2");
		search.setEndRowIndex(100);
		search.setStartRowIndex(0);
		
		int count = tagDao.readCount(tag);
		
		assertNotNull(count);
	}
	
	
	
	@Test
	public void testListSearch() {
		
		Tag search = new Tag();
		
		search.setTagName("title");
		//search.setTagItemId("4");
		search.setTagItemType("2");
		search.setEndRowIndex(100);
		search.setStartRowIndex(0);
		
		List<Tag> result = tagDao.listSearch(tag);
		
		assertNotNull(result);
		
	}
	
	
	
	@Test
	public void testReadCountSearch() {
		
		Tag search = new Tag();
		
		search.setTagName("title");
		//search.setTagItemId("4");
		search.setTagItemType("2");
		search.setEndRowIndex(100);
		search.setStartRowIndex(0);
		
		
		int count = tagDao.getCountSearch(search);
		
		assertNotNull(count);
	}



	@Test
	public void testListByItem() {
		
		List<Tag> result = tagDao.listByItem(tag.getTagItemId(), tag.getTagItemType());
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testListItemId() {
		
		Tag search = new Tag();
		
		search.setTagId(tag.getTagId());
		search.setTagItemId("4");
		search.setTagItemType("WW");
		search.setEndRowIndex(100);
		search.setStartRowIndex(0);
		search.setTagName("aa");
		search.setAsTagItemType("PR");
		
		List<String> result = tagDao.listItemId(tag);
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testCountListItemId() {
		
		Tag search = new Tag();
		
		search.setTagItemId("4");
		search.setTagItemType("2");
		search.setEndRowIndex(100);
		search.setStartRowIndex(0);
		
		int result = tagDao.getCountListItemId(search);
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testListTeamType() {
		
		List<Tag> result = tagDao.listTeamType(tag.getPortalId());
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testListlistItemCount() {
		
		List<Tag> result = tagDao.listItemCount(tag.getTagItemId(), tag.getTagItemType());
		
		assertNotNull(result);
	}


}
