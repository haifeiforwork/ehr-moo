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

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrLinereplyDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class FrLinereplyDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private FrLinereplyDao frLinereplyDao;

	private FrLinereply frLinereply;

	private String pk;

	@Before
	public void setUp() {
		
		frLinereply = new FrLinereply();
		
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
		frLinereply.setUpdaterId("user1");
		frLinereply.setUpdaterName("updateName");
		frLinereply.setPortalId("p1");
		
		frLinereplyDao.create(frLinereply);
		
	}
	
	
	@Test
	public void testGet() {
		
		boolean result = false;
		
		try {
			FrLinereply test = frLinereplyDao.get(pk);
			result= true;
		} catch (Exception e) {
			result= false;
		}
		
		assertTrue(result);
		
	}
	
	
	@Test
	public void testCountByParentId() {
		
		
		int result = frLinereplyDao.getCountByParentId(pk);
		
		assertEquals(result, 0);
	}
	
	
	@Test
	public void testGetCountes() {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setUserId(frLinereply.getRegisterId());
		
		FrLinereply result = frLinereplyDao.getCountes(frSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testListUserId() {
		
		boolean result = false;
		
		try {
			List<String> test = frLinereplyDao.listUserId("10");
			result= true;
		} catch (Exception e) {
			result= false;
		}
		
		assertTrue(result);
	}
	
	
	@Test
	public void testListUserIdByDiscussionId() {
		
		boolean result = false;
		
		try {
			List<String> test = frLinereplyDao.listUserIdByDiscussionId("22");
			result= true;
		} catch (Exception e) {
			result= false;
		}
		
		assertTrue(result);
	}

	@Test
	public void testList() {
		
		FrSearch frSearch = new FrSearch();
		//frSearch.setBest("1");
		//frSearch.setItemDelete("1");
		frSearch.setOrderType("best");
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		frSearch.setItemId(frLinereply.getItemId());
		
		
		List<FrLinereply> result = frLinereplyDao.list(frSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testCountList() {
		
		FrSearch frSearch = new FrSearch();
		//frSearch.setBest("1");
		//frSearch.setItemDelete("1");
		frSearch.setOrderType("best");
		
		
		int result = frLinereplyDao.getCountList(frSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testListWithItemDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		//frSearch.setBest("1");
		//frSearch.setItemDelete("1");
		frSearch.setUserId(frLinereply.getRegisterId());
		frSearch.setOrderType("best");
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		
		
		List<FrLinereply> result = frLinereplyDao.listWithItemDiscussion(frSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testCountListWithItemDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		//frSearch.setBest("1");
		//frSearch.setItemDelete("1");
		frSearch.setUserId(frLinereply.getRegisterId());
		frSearch.setOrderType("best");
		
		
		int result = frLinereplyDao.getCountListWithItemDiscussion(frSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testListLastWithItemDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		//frSearch.setBest("1");
		//frSearch.setItemDelete("1");
		frSearch.setUserId(frLinereply.getRegisterId());
		frSearch.setOrderType("best");
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		
		
		List<FrLinereply> result = frLinereplyDao.listLastWithItemDiscussion(frSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testCountListLastWithItemDiscussion() {
		
		FrSearch frSearch = new FrSearch();
		//frSearch.setBest("1");
		//frSearch.setItemDelete("1");
		frSearch.setUserId(frLinereply.getRegisterId());
		frSearch.setOrderType("best");
		
		
		int result = frLinereplyDao.getCountListLastWithItemDiscussion(frSearch);
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		
		frLinereply.setContents("updateContent");
		frLinereply.setUpdaterId("updateId");
		frLinereply.setUpdaterName("updateName");
		
		try{
			frLinereplyDao.update(frLinereply);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	

	
	@Test
	public void testUpdateStep() {
		
		try{
			frLinereplyDao.updateStep(pk, 1);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testUpdateLinereplyDelete() {
		
		try{
			frLinereplyDao.updateLinereplyDelete(pk, 1);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testUpdateBestLinereply() {
		
		try{
			frLinereplyDao.updateBestLinereply(pk);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testUpdateBestLinereplyInit() {
		
		try{
			frLinereplyDao.updateBestLinereplyInit();
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testUpdateRecommendCount() {
		
		try{
			frLinereplyDao.updateRecommendCount(pk);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}
	
	
	
	@Test
	public void testRemove() {
		
		frLinereplyDao.remove(pk);
		
		FrSearch frSearch = new FrSearch();
		frSearch.setUserId(frLinereply.getRegisterId());
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		frSearch.setItemId("20");
		
		List<FrLinereply> result = frLinereplyDao.list(frSearch);
		
		assertTrue(result.isEmpty());
		
	}
	
	
	@Test
	public void testRemoveByItemId() {
		
		frLinereplyDao.removeByItemId(frLinereply.getItemId());
		
		FrSearch frSearch = new FrSearch();
		frSearch.setUserId(frLinereply.getRegisterId());
		frSearch.setEndRowIndex(10);
		frSearch.setStartRowIndex(0);
		frSearch.setItemId("20");
		
		List<FrLinereply> result = frLinereplyDao.list(frSearch);
		
		assertTrue(result.isEmpty());
		
	}
	
	
	@Test
	public void testRemoveByDiscussionId() {
		
		try{
			frLinereplyDao.removeByDiscussionId("20");
			
			assertTrue(true);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
	

}
