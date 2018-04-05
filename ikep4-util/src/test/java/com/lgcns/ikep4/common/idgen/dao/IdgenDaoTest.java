package com.lgcns.ikep4.common.idgen.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.util.idgen.dao.IdgenDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class IdgenDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IdgenDao idgenDao;

	private String id;

	@Before
	public void setUp() {

	}

	@Test
	public void testGet() {
		id = idgenDao.getNextId();
		Assert.assertNotNull(id);
	}

	public void testCreate() {

	}

	public void testUpdate() {}

	public void testPhysicalDelete() {

	}

	public void testExists() {
		// TODO Auto-generated method stub

	}

	public void testLogicalDelete() {
		// TODO Auto-generated method stub

	}

}
