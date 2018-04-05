/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.userconfig.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.userconfig.dao.UserConfigDao;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 개인화 설정 DAO 테스트 클래스
 *
 * @author 최현식
 * @version $Id: UserConfigDaoTest.java 16316 2011-08-22 00:26:53Z giljae $
 */
@ContextConfiguration({"classpath:/configuration/spring/context-dao.xml", "classpath:/configuration/spring/context-service.xml"})
public class UserConfigDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private UserConfigDao userConfigDao;

	@Autowired
	private IdgenService idgenService;

	private UserConfig userConfig;


	private String createdId;


	@Before
	public void setUp() {
		String newId = this.idgenService.getNextId();

		this.userConfig = new UserConfig();

		this.userConfig.setId(newId);
		this.userConfig.setPageCount(1);
		this.userConfig.setLayout("A");
		this.userConfig.setModuleId("M1");
		this.userConfig.setUserId("USER-A");
		this.userConfig.setPortalId("P-A");

		this.createdId = this.userConfigDao.create(this.userConfig);

	}

	/**
	 * Test method for {@link com.lgcns.ikep4.support.userconfig.dao.impl.UserConfigDaoImpl#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		UserConfig selectedUserConfig = this.userConfigDao.get(this.createdId);

		Assert.assertNotNull(selectedUserConfig);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.support.userconfig.dao.impl.UserConfigDaoImpl#exists(java.lang.String)}.
	 */
	@Test
	public void testExists() {
		Boolean existed = this.userConfigDao.exists(this.createdId);

		Assert.assertTrue(existed);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.support.userconfig.dao.impl.UserConfigDaoImpl#create(com.lgcns.ikep4.support.userconfig.model.UserConfig)}.
	 */
	@Test
	public void testCreate() {
		UserConfig createdUserConfig = this.userConfigDao.get(this.createdId);

		Assert.assertNotNull(createdUserConfig);
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.support.userconfig.dao.impl.UserConfigDaoImpl#update(com.lgcns.ikep4.support.userconfig.model.UserConfig)}.
	 */
	@Test
	public void testUpdate() {
		UserConfig selectedUserConfig = this.userConfigDao.get(this.createdId);

		selectedUserConfig.setPageCount(2);
		selectedUserConfig.setLayout("B");
		selectedUserConfig.setModuleId("M2");
		selectedUserConfig.setUserId("USER-B");
		selectedUserConfig.setPortalId("P-B");

		this.userConfigDao.update(selectedUserConfig);

		UserConfig updatedUserConfig = this.userConfigDao.get(this.createdId);

		Assert.assertEquals(selectedUserConfig.getLayout(), updatedUserConfig.getLayout());
		Assert.assertEquals(selectedUserConfig.getPageCount(), updatedUserConfig.getPageCount());
		Assert.assertEquals(selectedUserConfig.getModuleId(), updatedUserConfig.getModuleId());
		Assert.assertEquals(selectedUserConfig.getUserId(), updatedUserConfig.getUserId());
		Assert.assertEquals(selectedUserConfig.getPortalId(), updatedUserConfig.getPortalId());
	}

	/**
	 * Test method for {@link com.lgcns.ikep4.support.userconfig.dao.impl.UserConfigDaoImpl#remove(java.lang.String)}.
	 */
	@Test
	public void testRemove() {
		this.userConfigDao.remove(this.createdId);

		UserConfig selectedUserConfig = this.userConfigDao.get(this.createdId);

		Assert.assertNull(selectedUserConfig);
	}

	@Test
	public void testListByUserId() {
		List<UserConfig> list = this.userConfigDao.listByUserId("USER-A");

		Assert.assertNotNull(list);

		Assert.assertTrue(list.size() == 1);
	}


	@Test
	public void testGetByUserIdAndModuleId() {
		UserConfig userConfig = new UserConfig();

		userConfig.setModuleId("M1");
		userConfig.setUserId("USER-A");
		userConfig.setPortalId("P-A");

		UserConfig selectedUserConfig = this.userConfigDao.getByUserIdAndModuleId(userConfig);

		Assert.assertNotNull(selectedUserConfig);
	}

	@Test
	public void testRemoveUserConfigByUserIdAndModuleId() {
		UserConfig userConfig = new UserConfig();

		userConfig.setModuleId("M1");
		userConfig.setUserId("USER-A");
		userConfig.setPortalId("P-A");
		this.userConfigDao.removeUserConfigByUserIdAndModuleId(userConfig);

		UserConfig selectedUserConfig = this.userConfigDao.get(this.createdId);

		Assert.assertNull(selectedUserConfig);
	}

}
