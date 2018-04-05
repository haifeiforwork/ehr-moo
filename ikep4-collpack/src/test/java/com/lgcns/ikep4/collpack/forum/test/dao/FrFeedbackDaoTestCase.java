/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrFeedbackDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.model.FrFeedback;
import com.lgcns.ikep4.collpack.forum.model.FrItem;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrFeedbackDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrFeedbackDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrFeedbackDao frFeedbackDao;
	
	@Autowired
	private FrItemDao frItemDao;

	private FrFeedback frFeedback;

	private String pk;

	@Before
	public void setUp() {
		
		
		FrItem frItem = new FrItem();
		
		pk = "20";
		
		frItem.setItemId(pk);
		frItem.setDiscussionId(null);
		frItem.setTitle("title");
		frItem.setContents("contents");
		frItem.setUpdaterId("lee");
		frItem.setUpdaterName("dong");
		frItem.setRegisterId("dong");
		frItem.setRegisterName("동");
		frItem.setPortalId("p1");
		
		frItemDao.create(frItem);
		
		frFeedback = new FrFeedback();
		
		pk = "20";
		
		frFeedback.setFeedbackType("1");
		frFeedback.setItemId(pk);
		frFeedback.setRegisterId("lee");
		
		frFeedbackDao.create(frFeedback);
		
	}

	@Test
	public void testCreate() {
		
		FrFeedback result = frFeedbackDao.get(frFeedback.getItemId(), frFeedback.getFeedbackType(), frFeedback.getRegisterId());
		assertNotNull(result);
	}
	
	@Test
	public void testExists() {
		
		boolean result = frFeedbackDao.exists(frFeedback.getItemId(), frFeedback.getFeedbackType(), frFeedback.getRegisterId());
		
		assertTrue(result);
	}
	
	@Test
	public void testCountByUserIdAsType() {
		
		int result = frFeedbackDao.getCountByUserIdAsType(frFeedback.getFeedbackType(), frFeedback.getRegisterId());
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testListUserId() {
		
		List<String> result = frFeedbackDao.listUserId(frFeedback.getItemId());
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testListUserIdByDiscussionId() {
		boolean result = false;
		try {
			List<String> list = frFeedbackDao.listUserIdByDiscussionId("2");
			result = true;
		} catch (Exception e) {
			result = false;
		}
		
		assertTrue(result);
	}
	
	@Test
	public void testList() {
		
		List<FrFeedback> result = frFeedbackDao.list(pk);
		assertFalse(result.isEmpty());
	}

	
	@Test
	public void testRemove() {
		
		frFeedbackDao.remove(frFeedback.getItemId(), frFeedback.getFeedbackType(), frFeedback.getRegisterId());
		
		boolean result = frFeedbackDao.exists(frFeedback.getItemId(), frFeedback.getFeedbackType(), frFeedback.getRegisterId());
		
		assertFalse(result);
		
	}
	
	@Test
	public void testRemoveByItemId() {
		
		frFeedbackDao.removeByItemId(frFeedback.getItemId());
		
		boolean result = frFeedbackDao.exists(frFeedback.getItemId(), frFeedback.getFeedbackType(), frFeedback.getRegisterId());
		
		assertFalse(result);
		
	}
	
	@Test
	public void testRemoveByDiscussionId() {
		
		try{
			frFeedbackDao.removeByDiscussionId("20");
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
		
	}
	

}
