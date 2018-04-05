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
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;


/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaCategoryDaoTestCase.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaCategoryDaoTestCase extends BaseDaoTestCase  {
	
	@Autowired
	private QnaCategoryDao qnaCategoryDao;

	private QnaCategory qnaCategory;

	private String pk;

	@Before
	public void setUp() {
		qnaCategory = new QnaCategory();
		
		pk = "20";
		
		qnaCategory.setCategoryId(pk);
		qnaCategory.setCategoryName("카테고리 이름");
		qnaCategory.setRegisterId("dong");
		qnaCategory.setRegisterName("동");
		qnaCategory.setPortalId("p1");
		qnaCategory.setUpdaterId("dong");
		qnaCategory.setUpdaterName("updateName");
		
		qnaCategoryDao.create(qnaCategory);
		
	}

	@Test
	public void testCreate() {
		
		QnaCategory result = qnaCategoryDao.get(pk);
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		
		qnaCategory.setCategoryName("카테고리 이름");
		qnaCategory.setUpdaterId("hee");
		qnaCategory.setUpdaterName("updateChanegeName");
		
		qnaCategoryDao.update(qnaCategory);
		
		QnaCategory result = qnaCategoryDao.get(pk);
		assertEquals(qnaCategory.getCategoryName(), result.getCategoryName());
	}
	
	@Test
	public void testListAll() {
		
		List<QnaCategory> result = qnaCategoryDao.listAll("p1");
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void testSelectCategoryIdByCategoryName() {
		
		String result = qnaCategoryDao.selectCategoryIdByCategoryName("카테고리 이름", "p1");
		assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		
		qnaCategoryDao.remove(pk);
		QnaCategory result = qnaCategoryDao.get(pk);
		assertNull(result);
		
	}
	
	@Test
	public void testUpdateOrder() {
		
		qnaCategoryDao.updateOrder(pk, 1);
		QnaCategory result = qnaCategoryDao.get(pk);
		assertNotNull(result);
		
	}
	
	@Test
	public void testUpdateCategoryOrder() {
		
		qnaCategoryDao.updateCategoryOrder(pk, 2);
		QnaCategory result = qnaCategoryDao.get(pk);
		assertNotNull(result);
		
	}
	
	

}
