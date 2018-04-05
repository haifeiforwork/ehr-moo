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


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * BoardService 테스트 케이스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: WorkspaceServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class WorkspaceServiceTest extends BaseServiceTestCase {

	@Autowired
	private WorkspaceService workspaceService;
	

	
	private Workspace workspace;
	private Workspace workspaceSet;
	private String pk;
	@Autowired
	private IdgenService idgenService;
	@Before
	public void setUp() {
		workspace = new Workspace();
		
		//String newWorkspaceId=idgenService.getNextId();
		//workspace.setWorkspaceId(newWorkspaceId);
		
		workspace.setWorkspaceName("test Workspace");
		workspace.setTypeId(typeId);
		workspace.setDescription("just test for DAO");
		workspace.setWorkspaceStatus("WO");
		workspace.setRegisterId(registerId);
		workspace.setRegisterName(registerName);
		workspace.setUpdaterId(updaterId);
		workspace.setUpdaterName(updaterName);
		workspace.setTeamId("111");
		workspace.setOpenType(0);
		workspace.setPortalId(portalId);
		User user = new User();
		user.setUserId("user1");
		user.setUserName("user1");
		
		this.pk = workspaceService.createWorkspace(workspace,user);
		
		//this.pk=workspace.getWorkspaceId();
		workspace.setWorkspaceId(this.pk);
		workspaceSet = workspace;
	}

	@Test
	public void testCreateWorkspace() {
		Workspace result = workspaceService.read(workspaceSet);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.workspace = workspaceService.read(workspaceSet);
		this.workspace.setDescription("modified content");
		
		workspaceService.update(this.workspace);
		Workspace result = workspaceService.read(workspaceSet);
		
		assertEquals(this.workspace.getDescription(), result.getDescription());

	}
	@Test
	public void testUpdateStatis() {
		this.workspace = workspaceService.read(workspaceSet);
		this.workspace.setWorkspaceStatus("O");
		
		workspaceService.updateStatus(this.workspace);
		
		Workspace result = workspaceService.read(workspaceSet);
		
		assertEquals(this.workspace.getWorkspaceStatus(), result.getWorkspaceStatus());
		
	}
	/**@Test
	public void testPhysicalDelete() {
		this.workspaceService.physicalDelete(this.pk);
		
		Workspace result = this.workspaceService.read(workspaceSet);
		assertNull(result);
	}*/

	@Test
	public void testExist() {
		boolean result = workspaceService.exists(workspaceSet);
		assertTrue(result);
	}

	@Test
	public void testList() {
		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		
		searchCondition.setPortalId(portalId);
		searchCondition.setSearchColumn("WORKSPACE_NAME");
		
		SearchResult<Workspace> result = workspaceService.listBySearchCondition(searchCondition);
		assertNotNull(result);
	}
}
