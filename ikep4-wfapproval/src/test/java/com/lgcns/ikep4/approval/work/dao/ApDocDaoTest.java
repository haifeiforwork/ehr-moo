/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgcns.ikep4.approval.admin.test.dao.BaseDaoTestCase;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.wfapproval.work.dao.ApDocDao;
import com.lgcns.ikep4.wfapproval.work.model.ApDoc;
import com.lgcns.ikep4.wfapproval.work.search.ApListSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동겸
 * @version $Id: ApDocDaoTest.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApDocDaoTest extends  BaseDaoTestCase {
	@Autowired
	private ApDocDao apDocDao;
	
	@Autowired
	private IdgenService idgenService;
	
	
	private ApDoc apDoc;
	private String id;
	private String apprId;

	private ApListSearchCondition apListSearchCondition = new ApListSearchCondition();
	
	/**TODO Javadoc주석작성
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		apDoc = new ApDoc();
		
		this.apprId = idgenService.getNextId();
		
		apDoc.setApprId(this.apprId);
		apDoc.setFormId(idgenService.getNextId());
		apDoc.setProcessId("100000001025");
		apDoc.setApprDocNo("10003984");
		apDoc.setApprTitle("품위서테스트");
		apDoc.setApprSecurityCd("10003984");
		apDoc.setApprPeriodCd("10003984");
		apDoc.setApprTypeCd("10003984");
		apDoc.setRegistUserId("user1");
		apDoc.setApprDocData("html");
		apDoc.setBaseNo("1");
		apDoc.setEndNo("10");
		apDoc.setApprDocState("1");
		apDoc.setIsApprReceive("Y");
		apDoc.setApprLineType("S");

		
		apDocDao.create(apDoc);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.wfapproval.work.dao.ApListDao#getApListAll()}.
	 */
	@Test
	public void testGetApListAll() {
		//fail("Not yet implemented");
		List<ApDoc> result = this.apDocDao.selectAll(apDoc);
		assertFalse(result.isEmpty());
	}

	@Test
	public void testSelectCount() {
		//fail("Not yet implemented");
		int cnt = this.apDocDao.selectCount(apDoc);
		assertNotNull(cnt);
	}
	
	

}
