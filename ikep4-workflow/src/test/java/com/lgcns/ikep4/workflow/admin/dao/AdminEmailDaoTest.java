/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.admin.model.AdminEmailLog;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLogSearchCondition;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 워크플로우 - 알람현황 관리 - E-Mail Log관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminEmailDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminEmailDaoTest extends BaseDaoTestCase{
	
	@Autowired
	private AdminEmailDao adminEmailDao;
	
	private AdminEmailLog adminEmailLog;
	
	private AdminEmailLogSearchCondition adminEmailLogSearchCondition;
	
	private String pk;
	
	private Integer count = 0;
	
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
		
		adminEmailDao.create(adminEmailLog);
		adminEmailLogSearchCondition = new AdminEmailLogSearchCondition();
		
		this.pk = String.valueOf(adminEmailLog.getLogId());
	}
	
	/**
	 * 1. 생성
	 */
	@Test
	public void testCreate() {
		AdminEmailLog result = adminEmailDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 2. 읽기
	 */
	@Test
	public void testGet() {
		AdminEmailLog result = adminEmailDao.get(this.pk);
		assertNotNull(result);
	}
	
	/**
	 * 3. 삭제
	 */
	@Test
	public void testDelete() {
		adminEmailDao.remove(String.valueOf(adminEmailLog.getLogId()));
		AdminEmailLog result = adminEmailDao.get(this.pk);
		assertNull(result);
	}
	
	/**
	 * etc. 값 여부 확인.
	 */
	@Test
	public void testExist() {
		boolean result = adminEmailDao.exists(this.pk);
		assertTrue(result);
	}
	
	@Test
	public void getEmailLogView() {
		AdminEmailLog result = adminEmailDao.getEmailLogView(this.pk);
		assertNotNull(result);
	}
	
	@Test
	public void getCountAdminEmailLogList(){
		
		Integer count = adminEmailDao.getCountAdminEmailLogList(adminEmailLogSearchCondition);
		Boolean result = false;
		if(count > 0){
			result = true;
		}
		assertTrue(result);
	}
	
	@Test
	public void getAdminEmailLogSearchList(){
		count = adminEmailDao.getCountAdminEmailLogList(adminEmailLogSearchCondition);
		adminEmailLogSearchCondition.terminateSearchCondition(count);
		List<AdminEmailLog> result = adminEmailDao.getAdminEmailLogSearchList(adminEmailLogSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void removeMulti(){
		List<String> logId =  new ArrayList<String>();
		logId.add(String.valueOf(adminEmailLog.getLogId()));
		adminEmailDao.removeMulti(logId);
		AdminEmailLog result = adminEmailDao.get(this.pk);
		assertNull(result);
	}
	
}

