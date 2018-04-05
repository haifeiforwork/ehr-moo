package com.lgcns.ikep4.supportpack.recent.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.recent.dao.RecentDao;
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class RecentDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private RecentDao recentDao;

	@Autowired
	private IdgenService idgenService;

	private Recent recent;

	private String recentId;

	@Before
	public void setUp() {

	}

	@Test
	public void listBySearchConditionForPeople() {
		List<Recent> result = recentDao.listBySearchConditionForPeople(new RecentSearchCondition());
	}

	@Test
	public void listBySearchConditionForDocument() {
		List<Recent> result = recentDao.listBySearchConditionForDocument(new RecentSearchCondition());
	}

}
