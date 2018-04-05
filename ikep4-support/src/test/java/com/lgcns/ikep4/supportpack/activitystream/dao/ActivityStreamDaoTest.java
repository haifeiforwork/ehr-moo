package com.lgcns.ikep4.supportpack.activitystream.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lgcns.ikep4.support.activitystream.dao.ActivityStreamDao;
import com.lgcns.ikep4.support.activitystream.model.ActivityStream;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class ActivityStreamDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ActivityStreamDao activityStreamDao;

	@Autowired
	private IdgenService idgenService;

	private ActivityStream activityStream;

	private String activityStreamId;

	@Before
	public void setUp() {

		activityStream = new ActivityStream();

		activityStreamId = idgenService.getNextId();
		activityStream.setActivityStreamId(activityStreamId);
		activityStream.setItemTypeCode("BD");
		activityStream.setActivityCode("DocPost");
		activityStream.setActorId("11");
		activityStream.setTargetId("11");
		activityStream.setRegisterId("11");
		activityStream.setRegisterName("11");
		activityStream.setUpdaterId("11");
		activityStream.setUpdaterName("11");

		activityStreamDao.create(activityStream);

	}

	@Test
	public void testCreate() {
		ActivityStream result = activityStreamDao.get(activityStreamId);
		Assert.assertNotNull(result);

	}

	@Test
	public void testGet() {
		ActivityStream result = activityStreamDao.get(activityStreamId);
		Assert.assertNotNull(result);
	}

	@Test
	public void countBySearchCondition() {
		Integer result = activityStreamDao.countBySearchCondition(new ActivityStreamSearchCondition());
	}

	@Test
	public void listBySearchCondition() {
		List<ActivityStream> result = activityStreamDao.listBySearchCondition(new ActivityStreamSearchCondition());
	}

}
