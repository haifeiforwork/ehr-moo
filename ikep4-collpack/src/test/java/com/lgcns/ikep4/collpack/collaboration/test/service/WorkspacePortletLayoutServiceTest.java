/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.test.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortletLayout;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspacePortletLayoutService;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * BoardService 테스트 케이스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: WorkspacePortletLayoutServiceTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class WorkspacePortletLayoutServiceTest extends BaseServiceTestCase {

	@Autowired
	private WorkspacePortletLayoutService workspacePortletLayoutService;
	

	
	private WorkspacePortletLayout workspacePortletLayout;
	private WorkspacePortletLayout workspacePortletLayoutSet;
	private String pk;
	@Autowired
	private IdgenService idgenService;
	@Before
	public void setUp() {
		workspacePortletLayout = new WorkspacePortletLayout();
		
		//String newWorkspaceId=idgenService.getNextId();
		//workspace.setWorkspaceId(newWorkspaceId);
		
		workspacePortletLayout.setPortletId("WS_PORTLET_06");
		workspacePortletLayout.setColIndex(7);
		workspacePortletLayout.setRowIndex(2);
		workspacePortletLayout.setIsBoardPortlet(1);
		workspacePortletLayout.setWorkspaceId(workspaceId);
		workspacePortletLayout.setBoardId(registerName);

		User user = new User();
		user.setUserId("user1");
		user.setUserName("user1");
		
		this.pk = workspacePortletLayoutService.create(workspacePortletLayout);
		
		//this.pk=workspace.getWorkspaceId();
		workspacePortletLayout.setPortletLayoutId(this.pk);
		workspacePortletLayoutSet = workspacePortletLayout;
	}

	@Test
	public void testRead() {
		WorkspacePortletLayout result = workspacePortletLayoutService.read(this.pk);
		assertNotNull(result);
	}

	@Test
	public void testexists() {
		boolean hasLayout = workspacePortletLayoutService.exists(this.pk);

		assertTrue(hasLayout);

	}
	@Test
	public void testUpdate() {
		this.workspacePortletLayoutSet = workspacePortletLayoutService.read(this.pk);
		this.workspacePortletLayoutSet.setColIndex(3);
		
		workspacePortletLayoutService.update(this.workspacePortletLayoutSet);
		
		WorkspacePortletLayout result = workspacePortletLayoutService.read(this.pk);
		
		assertEquals(this.workspacePortletLayoutSet.getColIndex(), result.getColIndex());
		
	}


	/*@Test
	public void testlistWorkspacePortletLayoutByWorkspace() {
		List<WorkspacePortletLayout> result = workspacePortletLayoutService.listWorkspacePortletLayoutByWorkspace(workspaceId);
		assertNotNull(result);
	}*/
	@Test
	public void testlistLayoutByWorkspace() {
		List<WorkspacePortletLayout> result = workspacePortletLayoutService.listLayoutByWorkspace(workspaceId);
		assertNotNull(result);
	}

	//@Test
	//public void testphysicalDeleteByWorkspaceId() {
	//	List<WorkspacePortletLayout> result = workspacePortletLayoutService.physicalDeleteByWorkspaceId(workspaceId);
	//	assertNotNull(result);
	//}	
}
