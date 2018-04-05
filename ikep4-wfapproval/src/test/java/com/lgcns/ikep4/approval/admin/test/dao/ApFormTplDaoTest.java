package com.lgcns.ikep4.approval.admin.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.wfapproval.admin.dao.ApFormTplDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApFormTpl;

public class ApFormTplDaoTest extends BaseDaoTestCase {

	@Autowired
	private ApFormTplDao apFormTplDao;
	
	@Autowired
	private IdgenService idgenService;
	
	private ApFormTpl apFormTpl;
	
	private String formId;
	
	@Before
	public void setUp() throws Exception {
		
		apFormTpl = new ApFormTpl();
		
		this.formId = idgenService.getNextId();
		
		apFormTpl.setFormId(this.formId);
		apFormTpl.setApprDocCd("apprDocCd");
		apFormTpl.setApprFormData("양식 Template 테스트 Body Data");
		apFormTpl.setApprLineCnt(9);
		apFormTpl.setApprPeriodCd(this.formId);
		apFormTpl.setApprTitle("양식 Template 테스트");
		apFormTpl.setProcessId("APPROVAL");
		apFormTpl.setProcessType("APPROVAL");
		apFormTpl.setApprTypeCd(this.formId);
		apFormTpl.setAuthCd(this.formId);
		apFormTpl.setDiscussCd(this.formId);
		apFormTpl.setDiscussLineCnt(10);
		apFormTpl.setIsAppr("Y");
		apFormTpl.setIsApprAttach("Y");
		apFormTpl.setIsApprDoc("U");
		apFormTpl.setIsApprPeriod("U");
		apFormTpl.setIsApprTitle("Y");
		apFormTpl.setIsApprType("U");
		apFormTpl.setIsDiscuss("Y");
		apFormTpl.setMailReqCd(this.formId);
		apFormTpl.setMailEndCd(this.formId);
		apFormTpl.setMailReqWayCd(this.formId);
		apFormTpl.setMailEndWayCd(this.formId);
		apFormTpl.setIsNoneForm("Y");
		apFormTpl.setModifyUserId("testUser");
		apFormTpl.setRegistUserId("testUser");
		
		this.apFormTplDao.create(apFormTpl);
	}

	@Test
	public void testGet() {
		ApFormTpl result = this.apFormTplDao.get(this.formId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		assertTrue(this.apFormTplDao.exists(this.formId));
	}

	@Test
	public void testCreate() {
		ApFormTpl result = this.apFormTplDao.get(this.formId);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.apFormTpl = this.apFormTplDao.get(this.formId);
		this.apFormTpl.setApprTitle("Modified Title Content");
		this.apFormTplDao.update(this.apFormTpl);
		ApFormTpl result = this.apFormTplDao.get(this.formId);
		assertEquals(this.apFormTpl.getApprTitle(), result.getApprTitle());
	}

	@Test
	public void testRemove() {
		this.apFormTplDao.remove(this.formId);
		ApFormTpl result = this.apFormTplDao.get(this.formId);
		assertNull(result);
	}

}
