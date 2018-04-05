/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.collaboration.alliance.search.AllianceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.alliance.service.AllianceService;
import com.lgcns.ikep4.framework.web.SearchResult;


import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * TODO Javadoc주석작성
 *
 * @author 김종철
 * @version $Id: AllianceServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class AllianceServiceTest extends BaseServiceTestCase{
	@Autowired
	private AllianceService allianceService;
	@Autowired
	private IdgenService idgenService;

	private Alliance alliance;
	
	private Alliance allianceSet;

	private String pk;

	@Before
	public void setUp() {
		alliance = new Alliance();

		//String allianceId = idgenService.getNextId();
		//alliance.setAllianceId(allianceId);
		alliance.setPortalId(portalId);
		alliance.setWorkspaceId(workspaceId);
		alliance.setToWorkspaceId(toWorkspaceId);
		alliance.setRequestReason("test Alliance");
		alliance.setStatus("1");
		alliance.setRegisterId(registerId);
		alliance.setRegisterName(registerName);
		alliance.setUpdaterId(updaterId);
		alliance.setUpdaterName(updaterName);
		
		this.pk = allianceService.create(alliance);
		alliance.setAllianceId(this.pk);
		
		allianceSet = alliance;
	}

	@Test
	public void testCreate() {
		//AllianceSearchCondition searchCondition = new AllianceSearchCondition();
		
		
		Alliance result = allianceService.read(allianceSet);
		assertNotNull(result);
	}

	@Test
	public void testGet() {
		Alliance result = allianceService.read(allianceSet);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.alliance = allianceService.read(allianceSet);
		
		this.alliance.setRequestReason("modified RequestReason");

		allianceService.update(this.alliance);
		
		Alliance result = allianceService.read(allianceSet);
		
		assertEquals(this.alliance.getRequestReason(), result.getRequestReason());
		
		
	}
	/*@Test
	public void testUpdateStatus() {
		this.alliance = allianceService.read(allianceSet);
		
		this.alliance.setStatus("0");
		
		allianceService.updateStatus(this.alliance);
		
		Alliance result = allianceService.read(allianceSet);
		
		assertEquals(this.alliance.getStatus(), result.getStatus());
	}*/	
	
	/*@Test
	public void testLogicalDelete() {
		allianceDao.logicalDelete(allianceSet);
		
		Alliance result = allianceDao.get(this.pk);
		
		
		assertNotNull(result);
	}*/
	@Test
	public void testPhysicalDelete() {
		List<String> allianceIds = new ArrayList<String>();
		allianceIds.add(allianceSet.getAllianceId());
		
		allianceService.physicalDelete(allianceIds);
		
		Alliance result = allianceService.read(allianceSet.getAllianceId());
		
		assertNull(result);
		//assertNull(result.getAllianceId());
	}

	/*@Test
	public void testExist() {
		//Category category = workspaceCategoryDao.get(this.pk);
		boolean result = allianceDao.exists(this.pk);
		assertTrue(result);
	}*/


	@Test
	public void testListBySearchCondition() {
		AllianceSearchCondition searchCondition = new AllianceSearchCondition();
		
		searchCondition.setPortalId(portalId);
		//searchCondition.setSearchColumn("WORKSPACE_NAME");
		
		searchCondition.setWorkspaceId(workspaceId);
		
		SearchResult<Alliance> result = allianceService.listBySearchCondition(searchCondition);
		assertNotNull(result);
	}


}
