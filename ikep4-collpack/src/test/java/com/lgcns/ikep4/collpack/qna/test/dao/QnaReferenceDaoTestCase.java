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
import com.lgcns.ikep4.collpack.qna.dao.QnaExpertDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaReferenceDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.model.QnaExpert;
import com.lgcns.ikep4.collpack.qna.model.QnaReference;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaReferenceDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaReferenceDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private QnaReferenceDao qnaReferenceDao;
	
	@Autowired
	private QnaDao qnaDao;

	private QnaReference qnaReference;

	@Before
	public void setUp() {

		Qna qna = new Qna();
		
		qna.setQnaId("2");
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
		
		
		qnaReference = new QnaReference();
		
		qnaReference.setQnaId("2");
		qnaReference.setRegisterId("leedong");
		
		qnaReferenceDao.create(qnaReference);
		
	}

	@Test
	public void testCreate() {
		
		int count = qnaReferenceDao.getCountByRegisterId(qnaReference.getQnaId(), qnaReference.getRegisterId());
		
		assertNotNull(count);
	}
	
	
	@Test
	public void testDelete() {
		
		qnaReferenceDao.removeQna(qnaReference.getQnaId());
		
		int result = qnaReferenceDao.getCountByRegisterId(qnaReference.getQnaId(), qnaReference.getRegisterId());
		
		assertEquals(result, 0);
		
	}
	

}
