/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspaceDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;



/**
 * BoardDao 테스트 케이스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: WorkspaceDaoTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class WorkspaceDaoTest extends BaseDaoTestCase {

	@Autowired
	private WorkspaceDao workspaceDao;


	
	private Workspace workspace;
	
	private Workspace workspaceSet;

	private String pk;

	@Before
	public void setUp() {
		workspace = new Workspace();
	
		workspace.setWorkspaceId(newWorkspaceId);
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
		
		this.pk = workspaceDao.create(workspace);
		this.pk=workspace.getWorkspaceId();
		workspaceSet = workspace;
	}

	@Test
	public void testCreate() {
		Workspace result = workspaceDao.get(workspaceSet);
		assertNotNull(result);
	}

	@Test
	public void testGet() {
		Workspace result = workspaceDao.get(workspaceSet);
		assertNotNull(result);
	}

	@Test
	public void testUpdate() {
		this.workspace = workspaceDao.get(workspaceSet);
		
		this.workspace.setDescription("modified content");

		workspaceDao.update(this.workspace);
		
		Workspace result = workspaceDao.get(workspaceSet);
		
		assertEquals(this.workspace.getDescription(), result.getDescription());
	}
	@Test
	public void testUpdateStatus() {
		
		this.workspace = workspaceDao.get(workspaceSet);
		
		this.workspace.setWorkspaceStatus("O");


		workspaceDao.updateStatus(this.workspace);
		
		Workspace result = workspaceDao.get(workspaceSet);
		
		assertEquals(this.workspace.getWorkspaceStatus(), result.getWorkspaceStatus());
	}
	@Test
	public void testPhysicalDelete() {
		workspaceDao.physicalDelete(this.pk);
		
		Workspace result = workspaceDao.get(workspaceSet);
		
		//assertNull(result);
		assertNull(result);
	}

	@Test
	public void testExist() {
		Workspace workspace = workspaceDao.get(workspaceSet);
		boolean result = workspaceDao.exists(workspace);
		assertTrue(result);
	}


	@Test
	public void testListBySearchCondition() {
		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		
		searchCondition.setPortalId(portalId);
		searchCondition.setSearchColumn("WORKSPACE_NAME");
		
		
		List<Workspace> result = workspaceDao.listBySearchCondition(searchCondition);
		assertNotNull(result);
	}
	@Test
	public void testCountBySearchCondition() {
		
		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		
		searchCondition.setPortalId(portalId);
		//searchCondition.setSearchColumn("WORKSPACE_NAME");
		
		
		int count = workspaceDao.countBySearchCondition(searchCondition);
		assertNotNull(count);
	}
	
}
