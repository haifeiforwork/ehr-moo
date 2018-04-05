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

import com.lgcns.ikep4.collpack.collaboration.workspace.dao.WorkspacePortletDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspacePortlet;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspacePortletService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * BoardService 테스트 케이스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: WorkspacePortletServiceTest.java 16295 2011-08-19 07:49:49Z giljae $
 */
public class WorkspacePortletServiceTest extends BaseServiceTestCase {

	@Autowired
	private WorkspacePortletService workspacePortletService;
	
	@Autowired
	private WorkspacePortletDao workspacePortletDao;
	
	private WorkspacePortlet workspacePortlet;
	private WorkspacePortlet workspacePortletSet;
	private String pk;
	
	@Autowired
	private IdgenService idgenService;
	
	@Before
	public void setUp() {
		workspacePortlet = new WorkspacePortlet();
		
		//String newWorkspaceId=idgenService.getNextId();
		//workspace.setWorkspaceId(newWorkspaceId);
		workspacePortlet.setPortletId("WS_PORTLET_07");
		workspacePortlet.setPortletName("test Portlet");
		workspacePortlet.setViewUrl("/collpack/collaboration/main/portlet.do");
		workspacePortlet.setSortOrder(9);
		workspacePortlet.setIsDefault(1);
		workspacePortlet.setPortletEnglishName("ENG_NAME");
		
		User user = new User();
		user.setUserId("user1");
		user.setUserName("user1");
		
		this.pk = workspacePortletDao.create(workspacePortlet);

		workspacePortletSet = workspacePortlet;
	}

	@Test
	public void testCreateWorkspace() {
		WorkspacePortlet result = workspacePortletService.read(workspacePortletSet.getPortletId());
		assertNotNull(result);
	}

	@Test
	public void testlistAllWorkspace() {
		List<WorkspacePortlet> portletList = workspacePortletService.listAllWorkspace();

		assertNotNull(portletList);
	}
	@Test
	public void testlistAllWorkspacePortlet() {
		List<WorkspacePortlet> portletList = workspacePortletService.listAllWorkspacePortlet(workspaceId);
		assertNotNull(portletList);
		
	}

	@Test
	public void testlistDefaultWorkspacePortlet() {
		List<WorkspacePortlet> portletList = workspacePortletService.listDefaultWorkspacePortlet();
		assertNotNull(portletList);
		
	}

}
