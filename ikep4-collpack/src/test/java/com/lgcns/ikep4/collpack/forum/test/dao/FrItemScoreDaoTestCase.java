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
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrItemScoreDao;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrItemScore;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemScoreDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrItemScoreDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrItemScoreDao frItemScoreDao;
	
	@Autowired
	private FrItemDao frItemDao;

	private FrItemScore frItemScore;

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
		
		frItemScore = new FrItemScore();
		
		
		frItemScore.setItemId(pk);
		frItemScore.setItemScore(222);
		
		frItemScoreDao.create(frItemScore);
		
	}
	
	
	@Test
	public void testRemoveAll() {
		
		try{
			frItemScoreDao.removeAll();
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
		
	}
	
	@Test
	public void testRemoveCount() {
		
		
		try{
			frItemScoreDao.removeCount(10);
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testRemoveByDiscussionId() {
		
		try{
			frItemScoreDao.removeByDiscussionId("2");
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testRemove() {
		
		try{
			frItemScoreDao.remove(frItemScore.getItemId());
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	

}
