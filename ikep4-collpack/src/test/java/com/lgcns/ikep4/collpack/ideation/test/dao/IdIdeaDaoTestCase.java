/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.dao.IdIdeaDao;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;



/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdIdeaDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class IdIdeaDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private IdIdeaDao idIdeaDao;
	
	private IdIdea idIdea;

	private String pk;
	
	@Before
	public void setUp() {
		
		
		idIdea = new IdIdea();
		
		pk = "21";
		
		idIdea.setItemId(pk);
		idIdea.setTitle("title");
		idIdea.setContents("contents");
		idIdea.setUpdaterId("user1");
		idIdea.setUpdaterName("dong");
		idIdea.setRegisterId("user1");
		idIdea.setRegisterName("동");
		idIdea.setPortalId("p1");
		
		idIdeaDao.create(idIdea);
		
	}

	@Test
	public void testCreate() {
		
		IdIdea result = idIdeaDao.get(pk);
		assertNotNull(result);
	}
	
	
	@Test
	public void testGetCountes() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setUserId(idIdea.getRegisterId());
		
		IdIdea result = idIdeaDao.getCountes(idSearch);
		assertNotNull(result);
	}
	
	
	
	@Test
	public void testList() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setStartRowIndex(0);
		idSearch.setEndRowIndex(10);
		idSearch.setUserId(idIdea.getRegisterId());
		idSearch.setLimitDate("30");
		idSearch.setTitle("title");
		//idSearch.setOrderType("best");
		idSearch.setPortalId("p1");
		
		List<IdIdea> result = idIdeaDao.list(idSearch);
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGetCountList() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setStartRowIndex(0);
		idSearch.setEndRowIndex(10);
		idSearch.setUserId(idIdea.getRegisterId());
		idSearch.setLimitDate("30");
		idSearch.setTitle("title");
	//	idSearch.setOrderType("best");
		idSearch.setPortalId("p1");
		
		int result = idIdeaDao.getCountList(idSearch);
		assertNotSame(result, 0);
	}
	
	
	@Test
	public void testGetCountAdopt() {
		
		IdSearch idSearch = new IdSearch();
		idSearch.setIsUserAdopt(IdeationConstants.IS_ADOPT);
		idSearch.setUserId(idIdea.getRegisterId());
		idSearch.setPortalId("p1");
		idSearch.setItemId(idIdea.getItemId());
		
		int adoptCount = idIdeaDao.getCountList(idSearch);
		assertNotNull(adoptCount);
	}
	
	
	
	@Test
	public void testUpdate() {
		
		idIdea.setTitle("updateTile");
		idIdea.setContents("upateContent");
		
		idIdeaDao.update(idIdea);
		
		IdIdea result = idIdeaDao.get(pk);
		assertEquals(idIdea.getTitle(), result.getTitle());
	}
	
	
	@Test
	public void testUpdateBestItem() {
		
		idIdeaDao.updateBestItem(idIdea.getItemId());
		
		IdIdea result = idIdeaDao.get(idIdea.getItemId());
		assertTrue(result.getBestItem() == 1);
	}
	
	
	@Test
	public void testUpdateBestItemInit() {
		
		idIdeaDao.updateBestItemInit();
		
		IdIdea result = idIdeaDao.get(idIdea.getItemId());
		assertTrue(result.getBestItem() == 0);
	}
	
	
	@Test
	//@Rollback(false)
	public void testUpdateRecommendItem() {
		
		idIdeaDao.updateRecommendItem(pk,1);
		
		IdIdea result = idIdeaDao.get(pk);
		assertTrue(result.getRecommendItem() >= 0);
	}
	
	@Test
	public void testUpdateAdoptItem() {
		
		idIdeaDao.updateAdoptItem(idIdea.getItemId(),1);
		
		IdIdea result = idIdeaDao.get(idIdea.getItemId());
		assertTrue(result.getAdoptItem() >= 0);
	}
	
	
	@Test
	public void testUpdateBusinessItem() {
		
		idIdeaDao.updateBusinessItem(idIdea.getItemId(),"1");
		
		IdIdea result = idIdeaDao.get(idIdea.getItemId());
		assertTrue(result.getBusinessItem().equals("1"));
	}
	
	@Test
	public void testUpdateExamination() {
		
		idIdeaDao.updateExamination(idIdea.getItemId(),"examiantion");
		
		IdIdea result = idIdeaDao.get(idIdea.getItemId());
		assertTrue(result.getExaminationComment().equals("examiantion"));
	}
	
	
	
	@Test
	public void testUpdateHitCount() {
		
		idIdeaDao.updateHitCount(idIdea.getItemId());
		
		IdIdea result = idIdeaDao.get(pk);
		assertTrue(result.getHitCount() >= 0);
	}
	
	@Test
	public void testUpdateRecommendCount() {
		
		idIdeaDao.updateRecommendCount(idIdea.getItemId());
		
		IdIdea result = idIdeaDao.get(pk);
		assertTrue(result.getRecommendCount() >= 0);
	}
	
	@Test
	public void testUpdateFavoriteCount() {
		
		idIdeaDao.updateFavoriteCount(idIdea.getItemId(),1);
		
		IdIdea result = idIdeaDao.get(pk);
		assertTrue(result.getFavoriteCount() >= 0);
	}
	
	
	@Test
	public void testUpdateAdoptCount() {
		
		idIdeaDao.updateAdoptCount(idIdea.getItemId());
		
		IdIdea result = idIdeaDao.get(pk);
		assertTrue(result.getAdoptCount() >= 0);
	}
	
	@Test
	public void testUpdateLinereplyCount() {
		
		idIdeaDao.updateLinereplyCount(idIdea.getItemId());
		
		IdIdea result = idIdeaDao.get(pk);
		assertTrue(result.getLinereplyCount() >= 0);
	}
	
	@Test
	public void testUpdateMailCount() {
		
		idIdeaDao.updateMailCount(idIdea.getItemId());
		
		IdIdea result = idIdeaDao.get(pk);
		assertTrue(result.getMailCount() >= 0);
	}
	
	
	@Test
	public void testUpdateMblogCount() {
		
		idIdeaDao.updateMblogCount(idIdea.getItemId());
		
		IdIdea result = idIdeaDao.get(pk);
		assertTrue(result.getMblogCount() >= 0);
	}
	
	@Test
	public void testRemove() {
		
		idIdeaDao.remove(pk);
		IdIdea result = idIdeaDao.get(pk);
		assertNull(result);
		
	}
	
	
	@Test
	public void testGetFavorite() {
		
		int count = idIdeaDao.getFavorite("user1", "id");
		
		
		assertTrue(count >= 0);
	}

}
