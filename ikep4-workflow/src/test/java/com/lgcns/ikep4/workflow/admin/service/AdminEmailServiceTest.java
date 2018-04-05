/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.dao.AdminEmailDao;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLog;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLogSearchCondition;
import com.lgcns.ikep4.workflow.modeler.test.service.BaseServiceTestCase;

/**
 * 워크플로우 - 알람현황 관리 - E-Mail Log관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminEmailServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminEmailServiceTest extends BaseServiceTestCase{
	
	@Autowired
	private AdminEmailDao adminEmailDao;
	
	@Autowired
	private AdminEmailService adminEmailService;
	
	private AdminEmailLog adminEmailLog;
	
	private AdminEmailLogSearchCondition adminEmailLogSearchCondition;
	
	private String pk;
	
	@Before
	public void setUp() {
		adminEmailLog = new AdminEmailLog();
		adminEmailLog.setLogId(999999999);
		adminEmailLog.setSenderId("1");
		adminEmailLog.setSenderEmail("1");
		adminEmailLog.setReceiver("1");
		adminEmailLog.setReceiverEmail("1");
		adminEmailLog.setEmailTitle("1");
		adminEmailLog.setEmailContent("1");
		adminEmailLog.setSendDate("2010-01-01");
		adminEmailLog.setIsSuccess("1");
		adminEmailLog.setReceiveType("1");
		
		pk = adminEmailDao.create(adminEmailLog);
		adminEmailLogSearchCondition = new AdminEmailLogSearchCondition(); 
	}
	
	@Test
	public void createAdminEmailLog(){
		String createAdminEmailLog = adminEmailService.createAdminEmailLog(adminEmailLog);
		Boolean result = false;
		if(Integer.valueOf(createAdminEmailLog) > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	@Test
	public void readAdminEmail(){
		AdminEmailLog result = adminEmailService.readAdminEmail(String.valueOf(adminEmailLog.getLogId()));
		assertNotNull(result);
	}
	
	@Test
	public void readAdminEmailSearchList(){
		SearchResult<AdminEmailLog> adminEmailLog = adminEmailService.readAdminEmailSearchList(adminEmailLogSearchCondition);
		assertNotNull(adminEmailLog);
	}
}
