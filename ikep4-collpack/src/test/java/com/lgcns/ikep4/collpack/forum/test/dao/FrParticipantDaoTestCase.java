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
import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.dao.FrParticipantDao;
import com.lgcns.ikep4.collpack.forum.model.FrCategory;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrParticipant;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrParticipantDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrParticipantDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrParticipantDao frParticipantDao;
	
	@Autowired
	private FrDiscussionDao frDiscussionDao;
	
	@Autowired
	private FrCategoryDao frCategoryDao;

	private FrParticipant frParticipant;

	private String pk;

	@Before
	public void setUp() {
		
		
		FrCategory frCategory = new FrCategory();
		
		frCategory.setCategoryId("10");
		frCategory.setCategoryName("카테고리 이름");
		frCategory.setRegisterId("user2");
		frCategory.setRegisterName("동");
		frCategory.setPortalId("p1");
		
		frCategoryDao.create(frCategory);
		
		
		FrDiscussion frDiscussion = new FrDiscussion();
		
		pk= "11";
		
		frDiscussion.setDiscussionId(pk);
		frDiscussion.setCategoryId(frCategory.getCategoryId());
		frDiscussion.setTitle("title");
		frDiscussion.setContents("contents");
		frDiscussion.setImageId("img");
		frDiscussion.setUpdaterId("lee");
		frDiscussion.setUpdaterName("dong");
		frDiscussion.setRegisterId("user1");
		frDiscussion.setRegisterName("동");
		frDiscussion.setPortalId("p1");
		
		frDiscussionDao.create(frDiscussion);
		
	
		
		frParticipant = new FrParticipant();
		
		frParticipant.setDiscussionId(frDiscussion.getDiscussionId());
		frParticipant.setParticipationType("1");
		frParticipant.setRegisterId("user1");
		
		frParticipantDao.create(frDiscussion.getDiscussionId(), "1", "user1");
		
	}

	@Test
	public void testCreate() {
		
		FrParticipant result = frParticipantDao.get(frParticipant.getDiscussionId(), frParticipant.getParticipationType(), frParticipant.getRegisterId());
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setDiscussionId(frParticipant.getDiscussionId());
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		
		List<FrParticipant> result = frParticipantDao.list(frSearch);
		assertFalse(result.isEmpty());
	}

	
	@Test
	public void testCountList() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setDiscussionId(frParticipant.getDiscussionId());
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		
		int result = frParticipantDao.getCountList(frSearch);
		assertNotNull(result);
		
	}
	
	
	@Test
	public void testRemove() {
		
		frParticipantDao.remove(frParticipant.getDiscussionId(), frParticipant.getParticipationType(), frParticipant.getRegisterId());
		
		FrParticipant result = frParticipantDao.get(frParticipant.getDiscussionId(), frParticipant.getParticipationType(), frParticipant.getRegisterId());
		assertNull(result);
		
	}
	
	
	@Test
	public void testRemoveByDiscussion() {
		
		frParticipantDao.removeByDiscussion(frParticipant.getDiscussionId());
		
		FrParticipant result = frParticipantDao.get(frParticipant.getDiscussionId(), frParticipant.getParticipationType(), frParticipant.getRegisterId());
		assertNull(result);
		
	}
	

}
