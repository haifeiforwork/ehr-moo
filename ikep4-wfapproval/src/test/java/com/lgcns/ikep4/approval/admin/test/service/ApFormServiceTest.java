/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition;
import com.lgcns.ikep4.wfapproval.admin.service.ApFormService;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormServiceTest.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApFormServiceTest extends BaseServiceTestCase {

	@Autowired
	private ApFormService apFormService;

	private ApForm apform;

	private String formId;

	@Before
	public void setUp() throws Exception {

		apform = new ApForm();

		// this.formId = idgenService.getNextId();

		apform.setFormId(this.formId);
		apform.setFormName("양식 테스트");
		apform.setFormClassCd("10003984");
		apform.setFormTypeCd("10003984");
		apform.setFormDesc("양식 테스트 설명");
		apform.setIsUse("N");
		apform.setRegistUserId("test");
		apform.setModifyUserId("test");

		// apFormService.createApForm(apform);
		this.formId = apFormService.createApForm(apform);

		/*
		 * apform.setApFormTpl(new ApFormTpl());
		 * apform.getApFormTpl().setFormId(this.formId);
		 */
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testGetApFormList() {
		SearchResult<ApForm> result = apFormService.listApFormAll(new ApFormSearchCondition());
		assertFalse(result.isEmptyRecord());
	}

	@Test
	public void testGet() {
		ApForm result = apFormService.readApForm(this.formId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		assertTrue(apFormService.existsApForm(this.formId));
	}

	@Test
	public void testCreate() {
		ApForm result = apFormService.readApForm(this.formId);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.apform = apFormService.readApForm(this.formId);
		this.apform.setFormDesc("modified content");
		apFormService.updateApForm(this.apform);
		ApForm result = apFormService.readApForm(this.formId);
		assertEquals(this.apform.getFormDesc(), result.getFormDesc());
	}

	@Test
	public void testRemove() {
		apFormService.deleteApForm(this.formId);
		ApForm result = apFormService.readApForm(this.formId);
		assertNull(result);
	}

}
