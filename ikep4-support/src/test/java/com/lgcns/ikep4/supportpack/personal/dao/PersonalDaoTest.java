package com.lgcns.ikep4.supportpack.personal.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.personal.dao.PersonalDao;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class PersonalDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PersonalDao personalDao;

	@Autowired
	private IdgenService idgenService;

	private Personal personal;

	private String personalId;

	@Test
	public void listBySearchConditionForFile() {
		List<Personal> result = personalDao.listBySearchConditionForFile(new PersonalSearchCondition());
	}

	@Test
	public void countBySearchConditionForDocument() {
		Integer result = personalDao.countBySearchConditionForDocument(new PersonalSearchCondition());
	}

	@Test
	public void listBySearchConditionForDocument() {
		List<Personal> result = personalDao.listBySearchConditionForDocument(new PersonalSearchCondition());
	}

}
