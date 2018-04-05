/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.dao;

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

import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyDao;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdLinereplyDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private IdLinereplyDao idLinereplyDao;

	private IdLinereply idLinereply;

	private String pk;

	@Before
	public void setUp() {
		
		idLinereply = new IdLinereply();
		
		pk = "20";
		
		idLinereply.setItemId(null);
		idLinereply.setLinereplyId(pk);
		idLinereply.setLinereplyParentId(null);
		idLinereply.setLinereplyGroupId(pk);
		idLinereply.setContents("contents");
		idLinereply.setStep(0);
		idLinereply.setIndentation(0);
		idLinereply.setRegisterId("user1");
		idLinereply.setRegisterName("동");
		idLinereply.setUpdaterId("user1");
		idLinereply.setUpdaterName("updateName");
		idLinereply.setPortalId("p1");
		idLinereply.setAdoptLinereply(1);
		
		idLinereplyDao.create(idLinereply);
		
	}
	
	
	@Test
	public void testGet() {
		
		boolean result = false;
		
		try {
			IdLinereply test = idLinereplyDao.get(pk);
			result= true;
		} catch (Exception e) {
			result= false;
		}
		
		assertTrue(result);
		
	}
	
	
	@Test
	public void testCountByParentId() {
		
		
		int result = idLinereplyDao.getCountByParentId(pk);
		
		assertEquals(result, 0);
	}
	
	
	@Test
	public void testGetCountes() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setUserId(idLinereply.getRegisterId());
		
		IdLinereply result = idLinereplyDao.getCountes(idSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testListUserId() {
		
		boolean result = false;
		
		try {
			List<String> test = idLinereplyDao.listUserId("10");
			result= true;
		} catch (Exception e) {
			result= false;
		}
		
		assertTrue(result);
	}
	
	

	@Test
	public void testList() {
		
		IdSearch idSearch = new IdSearch();
		//idSearch.setBest("1");
		//idSearch.setItemDelete("1");
		//idSearch.setOrderType("best");
		idSearch.setEndRowIndex(10);
		idSearch.setStartRowIndex(0);
		idSearch.setItemId(idLinereply.getItemId());
		
		
		List<IdLinereply> result = idLinereplyDao.list(idSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testCountList() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setItemId(idLinereply.getItemId());
		//idSearch.setBest("1");
		//idSearch.setItemDelete("1");
		
		
		int result = idLinereplyDao.getCountList(idSearch);
		assertNotNull(result);
	}
	
	@Test
	public void testGetCountAdopt() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setIsAdopt(IdeationConstants.IS_ADOPT);
		idSearch.setUserId(idLinereply.getRegisterId());
		idSearch.setItemId(idLinereply.getItemId());
		
		int adoptCount = idLinereplyDao.getCountList(idSearch);
		assertNotNull(adoptCount);
	}
	
	
	@Test
	public void testUpdate() {
		
		idLinereply.setContents("updateContent");
		idLinereply.setUpdaterId("updateId");
		idLinereply.setUpdaterName("updateName");
		
		try{
			idLinereplyDao.update(idLinereply);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	

	
	@Test
	public void testUpdateStep() {
		
		try{
			idLinereplyDao.updateStep(pk, 1);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	@Test
	public void testUpdateLinereplyDelete() {
		
		try{
			idLinereplyDao.updateLinereplyDelete(pk, 1);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}
	
	
	
	@Test
	public void testUpdateRecommendCount() {
		
		try{
			idLinereplyDao.updateRecommendCount(pk);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}
	
	
	
	@Test
	public void testRemove() {
		
		idLinereplyDao.remove(pk);
		
		IdSearch idSearch = new IdSearch();
		idSearch.setUserId(idLinereply.getRegisterId());
		idSearch.setEndRowIndex(10);
		idSearch.setStartRowIndex(0);
		idSearch.setItemId("20");
		
		List<IdLinereply> result = idLinereplyDao.list(idSearch);
		
		assertTrue(result.isEmpty());
		
	}
	
	
	@Test
	public void testRemoveByItemId() {
		
		idLinereplyDao.removeByItemId(idLinereply.getItemId());
		
		IdSearch idSearch = new IdSearch();
		idSearch.setUserId(idLinereply.getRegisterId());
		idSearch.setEndRowIndex(10);
		idSearch.setStartRowIndex(0);
		idSearch.setItemId("20");
		
		List<IdLinereply> result = idLinereplyDao.list(idSearch);
		
		assertTrue(result.isEmpty());
		
	}
	
	

}
