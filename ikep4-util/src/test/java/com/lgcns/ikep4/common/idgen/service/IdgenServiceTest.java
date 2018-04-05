package com.lgcns.ikep4.common.idgen.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class IdgenServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IdgenService idgenService;

	private String id;

	@Before
	public void setUp() {

	}

	@Test
	public void testGet() {
		id = idgenService.getNextId();
		Assert.assertNotNull(id);
	}


}
