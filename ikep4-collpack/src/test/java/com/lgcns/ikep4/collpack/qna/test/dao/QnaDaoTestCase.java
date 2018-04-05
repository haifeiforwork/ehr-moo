/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaDaoTestCase.java 16647 2011-09-26 02:16:42Z giljae $
 */
public class QnaDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private QnaDao qnaDao;
	

	private Qna qna;

	private String pk = "9";

	@Before
	public void setUp() {
		
		
		qna = new Qna();
		
		qna.setQnaId(pk);
		qna.setQnaGroupId(pk);
		qna.setPortalId("0");
		qna.setQnaType(0);
		qna.setTitle("test qna");
		qna.setContents("just test for DAO");
		qna.setRegisterId("user1");
		qna.setRegisterName("이동희");
		qna.setUpdaterId("user1");
		qna.setUpdaterName("이동희");
		qna.setUrgent(0);
		qna.setPortalId("p1");
		qna.setAlarmChannel("0000");
		
		qnaDao.create(qna);
		
		
	}

	@Test
	public void testCreate() {
		
		Qna result = qnaDao.get(pk);
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		qna.setContents("modified content");
		qna.setTitle("modified title");
		qna.setUpdaterId("바꿈ID");
		qna.setUpdaterName("바꿈이름");
		qna.setAlarmChannel("1111");
		qnaDao.update(qna);
		Qna result = qnaDao.get(pk);
		assertEquals(qna.getContents(), result.getContents());
	}
	
	@Test
	public void testSelectAll() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		qnaSearch.setPortalId("p1");
		
		List<Qna> result = qnaDao.listAll(qnaSearch);
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testListRelation() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		qnaSearch.setPortalId("p1");
		
		List<String> qnaIdList = new ArrayList<String>();
		qnaIdList.add("2");
		
		qnaSearch.setQnaIdList(qnaIdList);
		
		List<Qna> result = qnaDao.listRelation(qnaSearch);
		
		assertTrue(true);
	}
	
	

	@Test
	public void testListChildId() {
		
		
		List<String> result = qnaDao.listChildId(pk);
		
		assertNotNull(result);
	}
	
	@Test
	public void testSelectCount() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		qnaSearch.setPortalId(qna.getPortalId());
		qnaSearch.setCategoryId(qna.getCategoryId());
		
		int count = qnaDao.selectCount(qnaSearch);
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testSelectCountRelation() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		qnaSearch.setPortalId(qna.getPortalId());
		qnaSearch.setCategoryId(qna.getCategoryId());
		
		List<String> qnaIdList = new ArrayList<String>();
		qnaIdList.add("2");
		
		qnaSearch.setQnaIdList(qnaIdList);
		
		int count = qnaDao.selectCountRelation(qnaSearch);
		
		assertNotNull(count);
	}
	
	
	
	@Test
	public void testSelectAdoptStatus() {
		
		int count = qnaDao.selectGroupAdoptStatus(qna.getQnaGroupId());
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testGetGroup() {
		
		String groupId = "0";
		
		List<Qna> result = qnaDao.selectByGroup(groupId);
		
		assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		qnaDao.remove(pk);
		Qna result = qnaDao.get(pk);
		assertNull(result);
	}
	
	
	@Test
	public void testUpdateRecommendCount() {
		
		Qna result = qnaDao.get(pk);
		
		int recommendCount = result.getRecommendCount();
		
		qnaDao.updateRecommendCount(result.getQnaId());
		result = qnaDao.get(pk);
		
		assertTrue(recommendCount == result.getRecommendCount());
	}

	@Test
	public void testUpdateReplyCount() {
		
		Qna result = qnaDao.get(pk);
		
		int replycount = result.getReplyCount();
		
		qnaDao.updateReplyCount(result.getQnaId());
		result = qnaDao.get(pk);
		
		assertTrue(replycount == result.getReplyCount());
	}
	
	@Test
	public void testUpdateLinereplyCount() {
		
		Qna result = qnaDao.get(pk);
		
		int linereplycount = result.getLinereplyCount();
		
		qnaDao.updateLinereplyCount(result.getQnaId());
		result = qnaDao.get(pk);
		
		assertTrue(linereplycount == result.getLinereplyCount());
	}
	
	@Test
	public void testUpdateAttachFileCount() {
		
		Qna result = qnaDao.get(pk);
		
		int attachFileCount = result.getAttachFileCount();
		
		qnaDao.updateAttachFileCount(result.getQnaId(), 1);
		result = qnaDao.get(pk);
		
		assertTrue(attachFileCount + 1 == result.getAttachFileCount());
	}
	
	@Test
	public void testUpdateAnswerAdopt() {
		
		Qna result = qnaDao.get(pk);
		
		int answer = result.getAnswerAdopt();
		
		qnaDao.updateAnswerAdopt(result.getQnaId(), 1);
		
		result = qnaDao.get(pk);
		
		assertFalse(answer == result.getAnswerAdopt());
	}
	
	@Test
	public void testUpdateUrgent() {
		
		Qna result = qnaDao.get(pk);
		
		int urgent = result.getUrgent();
		
		qnaDao.updateUrgent(result.getQnaId(),1);
		
		result = qnaDao.get(pk);
		
		assertFalse(urgent == result.getUrgent());
	}
	
	@Test
	public void testUpdateItemDelete() {
		
		Qna result = qnaDao.get(pk);
		
		qnaDao.updateItemDelete(result.getQnaId(), 1);
		
		result = qnaDao.get(pk);
		
		assertTrue(result.getItemDelete()==1);
	}
	
	@Test
	public void testUpdateStatus() {
		
		Qna result = qnaDao.get(pk);
		
		String testKey = result.getStatus();
		
		qnaDao.updateStatus(result.getQnaId(),"2");
		
		result = qnaDao.get(pk);
		
		assertFalse(testKey.equals(result.getStatus()));
	}
	
	@Test
	public void testUpdateFavoriteCount() {
		
		Qna result = qnaDao.get(pk);
		
		int testKey = result.getFavoriteCount();
		
		qnaDao.updateFavoriteCount(result.getQnaId(),1);
		
		result = qnaDao.get(pk);
		
		assertFalse(testKey == result.getFavoriteCount());
	}
	
	
	@Test
	public void testUpdateAnswerNecessaryTime() {
		
		Qna result = qnaDao.get(pk);
		
		double testKey = result.getAnswerNecessaryTime();
		
		qnaDao.updateAnswerNecessaryTime(result.getQnaId(),1.1);
		
		result = qnaDao.get(pk);
		
		assertFalse(testKey == result.getAnswerNecessaryTime());
	}
	
	@Test
	public void testUpdateRecommendCountSum() {
		
		qnaDao.updateRecommendCountSum(pk);
		
		Qna result = qnaDao.get(pk);
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateGroupHitCount() {
		
		qnaDao.updateGroupHitCount(pk);
		
		Qna result = qnaDao.get(pk);
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testUpdateGroupFavoriteCount() {
		
		qnaDao.updateGroupFavoriteCount(pk, 1);
		
		Qna result = qnaDao.get(pk);
		
		assertNotNull(result);
	}
	
	
	@Test
	public void testUpdateCategoryId() {
		
		qnaDao.updateCategoryId("7", "5");
		
		Qna result = qnaDao.get(pk);
		
		assertNotNull(result);
	}
	
	
	
	@Test
	public void testCountAnswerNecessaryTime() {
		
		int count = qnaDao.getCountAnswerNecessaryTime("p1");
		
		assertNotNull(count);
	}
	
	@Test
	public void testSumAnswerNecessaryTime() {
		
		double count = qnaDao.getSumAnswerNecessaryTime("p1");
		
		assertNotNull(count);
	}
	
	@Test
	public void testCountQna() {
		
		int count = qnaDao.getCountQna("p1");
		
		assertNotNull(count);
	}
	
	@Test
	public void testCountQnaHasAnswer() {
		
		int count = qnaDao.getCountQnaHasAnswer("p1");
		
		assertNotNull(count);
	}
	
	@Test
	public void testUpdateMailCount() {
		
		int testMail = qna.getMailCount();
		qnaDao.updateMailCount(pk);
		
		Qna result = qnaDao.get(pk);
		
		assertEquals(testMail+1, result.getMailCount());
	}
	
	@Test
	public void testUpdateMailCoun() {
		
		int testVal = qna.getMblogCount();
		qnaDao.updateMblogCount(pk);
		
		Qna result = qnaDao.get(pk);
		
		assertEquals(testVal+1, result.getMblogCount());
	}
	
}
