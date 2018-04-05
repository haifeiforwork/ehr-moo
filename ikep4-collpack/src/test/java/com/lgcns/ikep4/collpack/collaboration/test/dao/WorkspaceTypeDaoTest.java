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

import com.lgcns.ikep4.collpack.collaboration.admin.dao.WorkspaceTypeDao;
import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.workspace.search.WorkspaceSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 김종철
 * @version $Id: WorkspaceTypeDaoTest.java 16289 2011-08-19 06:52:01Z giljae $
 */
public class WorkspaceTypeDaoTest extends BaseDaoTestCase{
	@Autowired
	private WorkspaceTypeDao workspaceTypeDao;



	
	private WorkspaceType workspaceType;
	
	private WorkspaceType workspaceTypeSet;

	private String pk;

	@Before
	public void setUp() {
		workspaceType = new WorkspaceType();
	
		workspaceType.setPortalId(portalId);
		
		workspaceType.setTypeId("1");
		workspaceType.setTypeName("Cafe");
		workspaceType.setTypeEnglishName("Cafe");
		workspaceType.setIsOrganization(1);
		workspaceType.setSortOrder(1);
		workspaceType.setRegisterId(registerId);
		workspaceType.setRegisterName(registerName);


		this.pk = workspaceTypeDao.create(workspaceType);

		workspaceTypeSet = workspaceType;
	}

	@Test
	public void testCreate() {
		WorkspaceType result = workspaceTypeDao.get(workspaceTypeSet);
		assertNotNull(result);
	}

	@Test
	public void testGet() {
		WorkspaceType result = workspaceTypeDao.get(workspaceTypeSet);
		assertNotNull(result);
	}
	@Test
	public void testTypeName() {
		String typeName = workspaceTypeDao.getTypeName(workspaceTypeSet);
		assertNotNull(typeName);
	}
	@Test
	public void testUpdate() {
		this.workspaceType = workspaceTypeDao.get(workspaceTypeSet);
		
		this.workspaceType.setTypeName("Cafe2");

		workspaceTypeDao.update(this.workspaceType);
		
		WorkspaceType result = workspaceTypeDao.get(workspaceTypeSet);
		
		assertEquals(this.workspaceType.getTypeName(), result.getTypeName());
	}

	@Test
	public void testExist() {
		//Category category = workspaceCategoryDao.get(this.pk);
		boolean result = workspaceTypeDao.exists(workspaceTypeSet);
		assertTrue(result);
	}


	@Test
	public void testListWorkspaceType() {
		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		
		searchCondition.setPortalId(portalId);
		//searchCondition.setSearchColumn("WORKSPACE_NAME");
		
		
		List<WorkspaceType> result = workspaceTypeDao.listWorkspaceType(searchCondition.getPortalId());
		assertNotNull(result);
	}
	@Test
	public void testListWorkspaceTypeAll() {
		
		WorkspaceSearchCondition searchCondition = new WorkspaceSearchCondition();
		
		searchCondition.setPortalId(portalId);
		//searchCondition.setSearchColumn("WORKSPACE_NAME");
		
		
		List<WorkspaceType> result  = workspaceTypeDao.listWorkspaceTypeAll(searchCondition.getPortalId());
		assertNotNull(result);
	}
	@Test
	public void testPhysicalDelete() {

		
		workspaceTypeDao.logicalDelete(workspaceTypeSet);
		WorkspaceType result = workspaceTypeDao.get(workspaceTypeSet);
		
		//assertNull(result);
		assertNotNull(result);
	}	
	
}
