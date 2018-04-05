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

import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.wfapproval.admin.dao.ApCodeDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;

public class ApCodeDaoTest extends BaseDaoTestCase {

	@Autowired
	private ApCodeDao apCodeDao;
	
	@Autowired
	private IdgenService idgenService;
	
	private ApCode apCode;
	
	private String codeId;
	
	@Before
	public void setUp() throws Exception {
		apCode = new ApCode();
		
		this.codeId = idgenService.getNextId();
		
		apCode.setCodeDesc("코드관리 설명 테스트");
		apCode.setCodeId(codeId);
		apCode.setParentCodeId("000000000001");
		apCode.setCodeOrder("0");
		apCode.setCodeType("S");
		apCode.setCodeUse("Y");
		apCode.setCodeValue("TEST_ID");
		apCode.setEnName("Test Id");
		apCode.setKrName("테스트 아이디");
		
		this.apCodeDao.create(apCode);
	}

	@Test
	public void testListRootApCode() {
		List<ApCode> result = this.apCodeDao.listRootApCode("2");
		assertFalse(result.isEmpty());
	}

	@Test
	public void testListGroupApCode() {
		List<ApCode> result = this.apCodeDao.listGroupApCode(this.apCode.getParentCodeId());
		assertFalse(result.isEmpty());
	}

	@Test
	public void testGet() {
		ApCode result = this.apCodeDao.get(this.codeId);
		assertNotNull(result);
	}

	@Test
	public void testExists() {
		assertTrue(this.apCodeDao.exists(this.codeId));
	}

	@Test
	public void testCreate() {
		ApCode result = this.apCodeDao.get(this.codeId);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.apCode = this.apCodeDao.get(this.codeId);
		this.apCode.setKrName("Modified Title Content");
		this.apCodeDao.update(this.apCode);
		ApCode result = this.apCodeDao.get(this.codeId);
		assertEquals(this.apCode.getKrName(), result.getKrName());
	}

	@Test
	public void testRemove() {
		this.apCodeDao.remove(this.codeId);
		ApCode result = this.apCodeDao.get(this.codeId);
		assertNull(result);
	}

}
