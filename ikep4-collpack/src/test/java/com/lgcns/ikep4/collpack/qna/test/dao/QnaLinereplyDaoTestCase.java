/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.qna.dao.QnaCategoryDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaLinereplyDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.model.QnaLinereply;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaLinereplyDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaLinereplyDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private QnaLinereplyDao qnaLinereplyDao;
	
	@Autowired
	private QnaDao qnaDao;

	private QnaLinereply qnaLinereply;

	private String pk;

	@Before
	public void setUp() {
		
		

		Qna qna = new Qna();
		
		qna.setQnaId("277");
		qna.setQnaGroupId("2");
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
		
		qnaDao.create(qna);
		
		
		
		qnaLinereply = new QnaLinereply();
		
		
		qnaLinereply.setQnaId("277");
		qnaLinereply.setLinereplyId("44");
		qnaLinereply.setContents("content");
		qnaLinereply.setRegisterId("dong");
		qnaLinereply.setRegisterName("동");
		qnaLinereply.setUpdaterId("dong");
		qnaLinereply.setUpdaterName("updateName");
		qnaLinereply.setLinereplyGroupId("0");
		
		qnaLinereplyDao.create(qnaLinereply);
		
		
		qnaLinereply.setQnaId("277");
		qnaLinereply.setLinereplyParentId("44");
		qnaLinereply.setLinereplyId("45");
		qnaLinereply.setContents("content");
		qnaLinereply.setRegisterId("dong");
		qnaLinereply.setRegisterName("동");
		qnaLinereply.setUpdaterId("dong");
		qnaLinereply.setUpdaterName("updateName");
		qnaLinereply.setLinereplyGroupId("0");
		
		qnaLinereplyDao.create(qnaLinereply);
		
	}

	@Test
	public void testCreate() {
		
		QnaLinereply result = qnaLinereplyDao.get(qnaLinereply.getLinereplyId());
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		
		qnaLinereply.setContents("update content");
		qnaLinereply.setUpdaterId("hee");
		qnaLinereply.setUpdaterName("updateChanegeName");
		
		qnaLinereplyDao.update(qnaLinereply);
		
		QnaLinereply result = qnaLinereplyDao.get(qnaLinereply.getLinereplyId());
		assertEquals(qnaLinereply.getContents(), result.getContents());
	}
	
	@Test
	public void testSelectAll() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setEndRowIndex(10);
		qnaSearch.setStartRowIndex(0);
		
		List<QnaLinereply> result = qnaLinereplyDao.selectAll(qnaSearch);
		assertNotNull(result);
	}
	
	@Test
	public void testGetCount() {
		
		Qna qnaSearch = new Qna();
		
		qnaSearch.setQnaId("555");
		
		int result = qnaLinereplyDao.getCount(qnaSearch);
		assertNotNull(result);
	}
	
	
	@Test
	public void testSelectCountByParentId() {
		
		int count = qnaLinereplyDao.selectCountByParentId("0");
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testDelete() {
		
		qnaLinereplyDao.remove(qnaLinereply.getLinereplyId());
		QnaLinereply result = qnaLinereplyDao.get(qnaLinereply.getLinereplyId());
		assertNull(result);
		
	}
	
	@Test
	public void testUpdateItemDelete() {
		
		QnaLinereply result = qnaLinereplyDao.get(qnaLinereply.getLinereplyId());
		
		int itemDelete = result.getLinereplyDelete();
		
		qnaLinereplyDao.updateLinereplyDelete(qnaLinereply.getLinereplyId(), 1);
		
		result = qnaLinereplyDao.get(qnaLinereply.getLinereplyId());
		
		
		assertFalse(itemDelete == result.getLinereplyDelete());
	}
	

}
