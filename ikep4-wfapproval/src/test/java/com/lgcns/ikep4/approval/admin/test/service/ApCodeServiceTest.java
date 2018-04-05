package com.lgcns.ikep4.approval.admin.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.wfapproval.admin.model.ApCode;
import com.lgcns.ikep4.wfapproval.admin.service.ApCodeService;


public class ApCodeServiceTest extends BaseServiceTestCase {

	@Autowired
	private ApCodeService apCodeService;

	private ApCode apcode;

	private String codeId;

	@Before
	public void setUp() throws Exception {

		apcode = new ApCode();

		// apcode.setCodeId(this.codeId);
		apcode.setKrName("코드 테스트");
		apcode.setEnName("코드 테스트");
		apcode.setCodeDesc("코드 테스트 설명");
		apcode.setCodeOrder("0");
		apcode.setCodeType("S");
		apcode.setCodeValue("codeValue");
		apcode.setCodeUse("Y");
		// apcode.setParentCodeId(this.codeId);

		this.codeId = apCodeService.createApCode(apcode);
	}
	
	@After
	public void tearDown() throws Exception {}

	@Test
	public void testListRootApCode() {
		List<ApCode> result = apCodeService.listRootApCode("2");
		assertNotNull(result);
	}

	@Test
	public void testListGroupApCode() {
		List<ApCode> result = apCodeService.listGroupApCode(this.codeId);
		assertNotNull(result);
	}

	@Test
	public void testReadApCode() {
		ApCode result = apCodeService.readApCode(codeId);
		assertNotNull(result);
	}

	@Test
	public void testCreateApCode() {
		ApCode result = apCodeService.readApCode(codeId);
		assertNotNull(result);
	}

	@Test
	public void testUpdateApCode() {
		this.apcode = apCodeService.readApCode(this.codeId);
		this.apcode.setCodeDesc("modified content");
		apCodeService.updateApCode(this.apcode);
		ApCode result = apCodeService.readApCode(this.codeId);
		assertEquals(this.apcode.getCodeDesc(), result.getCodeDesc());
	}

	@Test
	public void testDeleteApCode() {
		apCodeService.deleteApCode(this.codeId);
		ApCode result = apCodeService.readApCode(this.codeId);
		assertNull(result);
	}

}
