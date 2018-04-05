/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.poll.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.poll.dao.PollDao;
import com.lgcns.ikep4.support.poll.model.Answer;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.poll.search.PollReceiverSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * PollDao 테스트 케이스
 * 
 * @author 서혜숙
 * @version $Id: PollDaoTest.java 16259 2011-08-18 05:40:01Z giljae $
 */
public class PollDaoTest extends BaseDaoTestCase {

	@Autowired
	private PollDao pollDao;

	@Autowired
	private IdgenService idgenService;

	private Poll poll;

	private String pk = "99999999";

	@Before
	public void setUp() {
		poll = new Poll();

		poll.setPollId(pk);
		poll.setQuestion("투표제목");
		poll.setStartDate(DateUtil.toDate("20110301"));
		poll.setEndDate(DateUtil.toDate("20110310"));
		poll.setAnswerCount(3);
		poll.setStatus("0");
		poll.setPermissionType("0");
		poll.setRequestChannel("0");
		poll.setChartType("0");
		poll.setPortalId("pl");
		poll.setRegisterId("rgt");
		poll.setRegisterName("rgt");
		poll.setUpdaterId("upd");
		poll.setUpdaterName("upd");
		String[] tmpTitles = { "보기1", "보기2", "보기3" };
		poll.setAnswerTitles(tmpTitles);
		String[] tmpIds = { "user1", "user2", "user3" };
		poll.setAddrUserList(tmpIds);
		String[] tmpGroupIds = { "grp1", "grp2", "grp3" };
		poll.setAddrGroupTypeList(tmpGroupIds);
		poll.setTargetId("jjjj");
		poll.setTargetType("0");
		poll.setAnswerUserId("jjjj");
		poll.setAnswerId("1111");
		poll.setAnswerTitle("보기");
		poll.setGroupId("user1");

		this.pk = pollDao.create(poll);
	}

	@Test
	public void testCreate() {
		Poll result = pollDao.getByPoll(this.poll);
		pollDao.insertAnswer(this.poll);
		pollDao.insertTarget(this.poll);
		pollDao.insertResult(this.poll);
		assertNotNull(result);

		List<Answer> result1 = pollDao.selectAnswers(this.pk);
		assertFalse(result1.isEmpty());
	}

	@Test
	public void testUpdate() {
		this.poll.setQuestion("modified question");
		pollDao.update(this.poll);
		Poll result = pollDao.getByPoll(this.poll);
		assertEquals(this.poll.getQuestion(), result.getQuestion());
	}

	@Test
	public void testUpdateStatus() {
		this.poll.setStatus("1");
		pollDao.updateStatus(this.poll);
		Poll result = pollDao.getByPoll(this.poll);
		assertEquals(this.poll.getStatus(), result.getStatus());
	}

	@Test
	public void testDelete() {
		pollDao.remove(this.pk);
		pollDao.deleteAnswer(this.pk);
		pollDao.deleteTarget(this.pk);
		pollDao.deleteResult(this.pk);
		Poll result = pollDao.getByPoll(this.poll);
		assertNull(result);
	}

	@Test
	public void testExist() {
		boolean result = pollDao.exists(this.pk);
		assertTrue(result);
	}

	@Test
	public void testListPollReceiverBySearchCondition() {
		PollReceiverSearchCondition pollReceiverSearchCondition = new PollReceiverSearchCondition();
		pollReceiverSearchCondition.setIsComplete("0");

		List<Poll> result = pollDao.listPollReceiverBySearchCondition(pollReceiverSearchCondition);
		assertNotNull(result);
	}

	@Test
	public void countPollReceiverBySearchCondition() {
		PollReceiverSearchCondition pollReceiverSearchCondition = new PollReceiverSearchCondition();
		pollReceiverSearchCondition.setIsComplete("0");

		int count = pollDao.countPollReceiverBySearchCondition(pollReceiverSearchCondition);
		assertNotNull(count);
	}
	
	@Test
	public void testListPollAdminBySearchCondition() {
		PollReceiverSearchCondition pollReceiverSearchCondition = new PollReceiverSearchCondition();

		List<Poll> result = pollDao.listPollAdminBySearchCondition(pollReceiverSearchCondition);
		assertNotNull(result);
	}

	@Test
	public void countPollAdminBySearchCondition() {
		PollReceiverSearchCondition pollReceiverSearchCondition = new PollReceiverSearchCondition();

		int count = pollDao.countPollAdminBySearchCondition(pollReceiverSearchCondition);
		assertNotNull(count);
	}	
}
