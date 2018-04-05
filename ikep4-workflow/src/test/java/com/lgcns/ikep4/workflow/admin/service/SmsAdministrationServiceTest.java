/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.model.AdminSms;
import com.lgcns.ikep4.workflow.admin.model.AdminSmsSearchCondition;
import com.lgcns.ikep4.workflow.modeler.test.service.BaseServiceTestCase;

/**
 * 워크플로우 - 현황관리 - 업무 관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: SmsAdministrationServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class SmsAdministrationServiceTest extends BaseServiceTestCase{
	
	@Autowired
	private SmsAdministrationService smsAdministrationService;
	
	private AdminSmsSearchCondition adminSmsSearchCondition;
	
	@Before
	public void setUp() {
		adminSmsSearchCondition = new AdminSmsSearchCondition();
	}

	/*
	 * 인스턴스 리스트 조회
	 */
	@Test
	public void listTodo() {
		SearchResult<AdminSms> result = smsAdministrationService.listSms(this.adminSmsSearchCondition);
		assertNotNull(result);
	}
}
