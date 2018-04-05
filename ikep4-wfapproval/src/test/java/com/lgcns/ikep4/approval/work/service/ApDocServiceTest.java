/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.approval.admin.test.service.BaseServiceTestCase;
import com.lgcns.ikep4.wfapproval.work.model.ApDoc;
import com.lgcns.ikep4.wfapproval.work.service.ApDocService;


/**
 * TODO Javadoc주석작성
 * 
 * @author 장규진
 * @version $Id: ApDocServiceTest.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApDocServiceTest extends BaseServiceTestCase {
	@Autowired
	private ApDocService apDocService;

	private ApDoc apDoc;

	/**
	 * TODO Javadoc주석작성
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		apDoc = new ApDoc();
		String agreeType = "S";
		apDoc.setFormId("100000002121");
		// apDoc.setProcessId("100000001025");
		apDoc.setProcessId("APPROVAL_PROCESS");
		apDoc.setApprDocNo("10003984");
		apDoc.setApprTitle("품위서테스트");
		apDoc.setApprSecurityCd("10003984");
		apDoc.setApprPeriodCd("10003984");
		apDoc.setApprTypeCd("10003984");
		apDoc.setRegistUserId("user1");
		apDoc.setApprDocData("html");
		apDoc.setBaseNo("1");
		apDoc.setEndNo("10");
		apDoc.setMailReqCd("100000728672");
		apDoc.setMailEndCd("100000729178");
		apDoc.setMailReqWayCd("100000012232");
		apDoc.setMailEndWayCd("100000012241");
		apDoc.setApprLineType("S");

		apDoc.setApprDocState("1");

		apDocService.create(apDoc);

	}

	@After
	public void tearDown() throws Exception {}

	@Test
	@Ignore
	public void testGetApDocList() {
		List<ApDoc> result = apDocService.list(apDoc);
		assertFalse(result.isEmpty());
	}

}
