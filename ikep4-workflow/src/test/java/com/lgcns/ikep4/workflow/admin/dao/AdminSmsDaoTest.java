/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.workflow.admin.model.AdminSms;
import com.lgcns.ikep4.workflow.admin.model.AdminSmsSearchCondition;
import com.lgcns.ikep4.workflow.modeler.test.dao.BaseDaoTestCase;

/**
 * 워크플로우 - 알림현황관리 - SMS 관리
 *
 * @author 이재경
 * @version $Id: AdminSmsDaoTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminSmsDaoTest extends BaseDaoTestCase{
	
	@Autowired
	private AdminSmsDao adminSmsDao;
	
	private AdminSmsSearchCondition adminSmsSearchCondition;
	
	@Before
	public void setUp() {
		
		adminSmsSearchCondition = new AdminSmsSearchCondition();
	}
	/**
	 * 1. 생성
	 */
	@Test
	public void listBySearchCondition() {
		List<AdminSms> result = (List<AdminSms>)adminSmsDao.listBySearchCondition(this.adminSmsSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void countBySearchCondition() {
		int result = adminSmsDao.countBySearchCondition(this.adminSmsSearchCondition);
		assertNotNull(result);
	}
}
