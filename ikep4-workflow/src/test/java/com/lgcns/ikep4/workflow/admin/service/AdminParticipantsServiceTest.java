/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.service;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.dao.AdminParticipantsDao;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipantSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipants;
import com.lgcns.ikep4.workflow.modeler.test.service.BaseServiceTestCase;

/**
 * 참여자 정보 조회 
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminParticipantsServiceTest.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminParticipantsServiceTest extends BaseServiceTestCase{
	
	@Autowired
	private AdminParticipantsDao adminParticipantsDao;
	
	@Autowired
	private AdminParticipantsService adminParticipantsService;
	
	private AdminParticipants adminParticipants;
	
	private AdminParticipantSearchCondition adminParticipantSearchCondition;
	
	private Integer count = 0;
	
	@Before
	public void setUp() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleTypeId", "1");
		params.put("roleTypeName", "1");
		params.put("registerId", "1");
		params.put("registerName", "1");
		params.put("updaterId", "1");
		params.put("updaterName", "1");
		adminParticipantsDao.insertRoleType(params);
		
		adminParticipants = new AdminParticipants();
		adminParticipants.setRoleId("1");
		adminParticipants.setRoleName("1");
		adminParticipants.setRoleTypeId("1");
		adminParticipants.setDescription("1");
		adminParticipants.setRegisterId("1");
		adminParticipants.setRegisterName("1");
		adminParticipants.setUpdaterId("1");
		adminParticipants.setUpdaterName("1");
		
		adminParticipantsDao.create(adminParticipants);
		
		adminParticipantSearchCondition = new AdminParticipantSearchCondition();
		adminParticipantSearchCondition.terminateSearchCondition(count);
	}
	
	/* 
	 * 롤 조회
	*/
	@Test
	public void listRole(){
		SearchResult<AdminParticipants> result = adminParticipantsService.listRole(adminParticipantSearchCondition);
		assertNotNull(result);
	}
}