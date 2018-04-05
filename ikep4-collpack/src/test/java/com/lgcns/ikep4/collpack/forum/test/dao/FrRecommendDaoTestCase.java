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

import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.dao.FrRecommendDao;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrRecommend;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrRecommendDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrRecommendDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrRecommendDao frRecommendDao;
	
	@Autowired
	private FrLinereplyDao frLinereplyDao;

	private FrRecommend frRecommend;

	private String pk;

	@Before
	public void setUp() {
		
		
		FrLinereply frLinereply = new FrLinereply();
		
		pk = "20";
		
		frLinereply.setItemId(null);
		frLinereply.setLinereplyId(pk);
		frLinereply.setLinereplyParentId(null);
		frLinereply.setLinereplyGroupId(pk);
		frLinereply.setContents("contents");
		frLinereply.setStep(0);
		frLinereply.setIndentation(0);
		frLinereply.setRegisterId("user1");
		frLinereply.setRegisterName("동");
		frLinereply.setPortalId("p1");
		frLinereply.setUpdaterId("user1");
		frLinereply.setUpdaterName("동");
		
		frLinereplyDao.create(frLinereply);
		
		frRecommend = new FrRecommend();
		
		frRecommend.setLinereplyId(frLinereply.getLinereplyId());
		frRecommend.setRegisterId("user1");
		
		frRecommendDao.create(frLinereply.getLinereplyId(), "user1");
		
	}

	@Test
	public void testCreate() {
		
		boolean result = frRecommendDao.exists(frRecommend.getLinereplyId(), frRecommend.getRegisterId());
		assertTrue(result);
	}
	
	@Test
	public void testCountByUserId() {
		
		int result = frRecommendDao.getCountByUserId(frRecommend.getRegisterId());
		assertNotNull(result);
	}
	
	@Test
	public void testListUserIdByLinereplyId() {
		
		List<String> result = frRecommendDao.listUserIdByLinereplyId(frRecommend.getLinereplyId());
		assertNotNull(result);
	}
	
	@Test
	public void testListUserIdByItemId() {
		
		boolean result = false;
		try {
			List<String> test = frRecommendDao.listUserIdByItemId("10");
			result = true;
		} catch (Exception e) {
			result = false;
		}
		
		assertTrue(result);
	}
	
	
	@Test
	public void testListUserIdByDiscussionId() {
		
		boolean result = false;
		try {
			List<String> test = frRecommendDao.listUserIdByDiscussionId("11");
			result = true;
		} catch (Exception e) {
			result = false;
		}
		
		assertTrue(result);
	}
	
	
	@Test
	public void testList() {
		
		List<FrRecommend> result = frRecommendDao.list(frRecommend.getLinereplyId());
		assertFalse(result.isEmpty());
	}

	
	@Test
	public void testRemove() {
		
		frRecommendDao.remove(frRecommend.getLinereplyId(), frRecommend.getRegisterId());
		
		boolean result = frRecommendDao.exists(frRecommend.getLinereplyId(), frRecommend.getRegisterId());
		assertFalse(result);
		
	}
	
	
	@Test
	public void testRemoveByLinereplyId() {
		
		frRecommendDao.removeByLinereplyId(frRecommend.getLinereplyId());
		
		boolean result = frRecommendDao.exists(frRecommend.getLinereplyId(), frRecommend.getRegisterId());
		assertFalse(result);
		
	}
	
	
	@Test
	public void testRemoveByItemId() {
		
		boolean result = false;
		
		try{
			frRecommendDao.removeByItemId("11");
			result  = true;
		} catch(Exception e){
			result  = false;
		}
		
		assertTrue(result);
		
	}
	
	
	
	@Test
	public void testRemoveByDiscussionId() {
		
		boolean result = false;
		
		try{
			frRecommendDao.removeByDiscussionId("2");
			result  = true;
		} catch(Exception e){
			result  = false;
		}
		
		assertTrue(result);
		
	}
	
	

}
