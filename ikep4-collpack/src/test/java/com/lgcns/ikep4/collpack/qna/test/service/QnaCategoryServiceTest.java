/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.service.QnaCategoryService;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaCategoryServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class QnaCategoryServiceTest extends BaseServiceTestCase {

	@Autowired
	private QnaCategoryService qnaCategoryService;

	private QnaCategory qnaCategory;
	
	private String pk;

	@Before
	public void setUp() {
		
		qnaCategory = new QnaCategory();
		
		qnaCategory.setCategoryName("카테고리 이름");
		qnaCategory.setRegisterId("dong");
		qnaCategory.setRegisterName("동");
		qnaCategory.setPortalId("p1");
		qnaCategory.setUpdaterId("dong");
		qnaCategory.setUpdaterName("updateName");
		
		pk = qnaCategoryService.create(qnaCategory);
	}

	@Test
	//@Rollback(false)
	public void testCreate() {
		
		QnaCategory result = qnaCategoryService.read(pk);
		
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		
		qnaCategory = qnaCategoryService.read(pk);
		
		qnaCategory.setCategoryName("카테고리 이름");
		qnaCategory.setUpdaterId("hee");
		qnaCategory.setUpdaterName("updateChanegeName");
		
		qnaCategoryService.update(qnaCategory);
		
		QnaCategory result = qnaCategoryService.read(pk);
		
		assertEquals(qnaCategory.getCategoryName(), result.getCategoryName());
	}

	@Test
	public void testDelete() {
		
		qnaCategoryService.delete(pk, "p1");
		QnaCategory result = qnaCategoryService.read(pk);
		assertNull(result);
		
	}

	@Test
	public void testList() {
		
		List<QnaCategory> result = qnaCategoryService.list("p1");
		assertNotNull(result);
	}
	
	@Test
	public void testApplyOrderCategory() {
		
		qnaCategoryService.applyOrderCategory(pk, "100000002866", "up");
		QnaCategory result = qnaCategoryService.read(pk);
		assertNotNull(result);
	}
	
	@Test
	public void testUpdateCategoryOrder() {
		
		qnaCategoryService.updateCategoryOrder(pk);
		QnaCategory result = qnaCategoryService.read(pk);
		assertNotNull(result);
	}
}
