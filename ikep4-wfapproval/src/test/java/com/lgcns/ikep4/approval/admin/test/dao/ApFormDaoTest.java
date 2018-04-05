/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.wfapproval.admin.dao.ApFormDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;

/**
 * TODO Javadoc주석작성
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormDaoTest.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApFormDaoTest extends BaseDaoTestCase {

	@Autowired
	private ApFormDao apFormDao;
	
	@Autowired
	private IdgenService idgenService;
	
	private ApForm apform;
	
	private String formId;

	@Before
	public void setUp() throws Exception {
		
		apform = new ApForm();
		
		this.formId = idgenService.getNextId();
		
		apform.setFormId(this.formId);
		apform.setFormName("양식 테스트");
		apform.setFormClassCd("10003984");
		apform.setFormTypeCd("10003984");
		apform.setFormDesc("양식 테스트 설명");
		apform.setIsUse("N");
		apform.setRegistUserId("test");
		apform.setModifyUserId("test");
		
		apFormDao.create(apform);
		
		//this.formId = apFormDao.create(apform);
	}

	@Test
	public void testGetApFormList() {
		List<ApForm> result = apFormDao.getApFormList();
		assertFalse(result.isEmpty());
	}

	@Test
	public void testGet() {
		ApForm result = apFormDao.get(this.formId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		assertTrue(apFormDao.exists(this.formId));
	}

	@Test
	public void testCreate() {
		ApForm result = apFormDao.get(this.formId);
		assertNotNull(result);
	}

	@Test
	//@Rollback(false)
	public void testUpdate() {
		this.apform = apFormDao.get(this.formId);
		this.apform.setFormDesc("modified content");
		apFormDao.update(this.apform);
		ApForm result = apFormDao.get(this.formId);
		assertEquals(this.apform.getFormDesc(), result.getFormDesc());
	}

	@Test
	public void testRemove() {
		apFormDao.remove(this.formId);
		ApForm result = apFormDao.get(this.formId);
		assertNull(result);
	}
}
