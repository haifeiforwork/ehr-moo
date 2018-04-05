/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.supportpack.poll.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.lgcns.ikep4.framework.web.SearchResult;

import com.lgcns.ikep4.support.poll.model.Answer;
import com.lgcns.ikep4.support.poll.model.Poll;
import com.lgcns.ikep4.support.poll.search.PollReceiverSearchCondition;
import com.lgcns.ikep4.support.poll.service.PollService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;


/**
 * PollService 테스트 케이스
 * 
 * @author 서혜숙
 * @version $Id: PollServiceTest.java 16259 2011-08-18 05:40:01Z giljae $
 */
public class PollServiceTest extends BaseServiceTestCase {

	@Autowired
	private PollService pollService;
	
	@Autowired
	private IdgenService idgenService;
	

	private Poll poll;

	private String pk;

	@Before
	public void setUp() {
		
		poll = new Poll();

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
		String[] tmpTitles = {"보기1","보기2","보기3"};
		poll.setAnswerTitles(tmpTitles);
		String[] tmpIds = { "user1", "user2", "user3" };
		poll.setAddrUserList(tmpIds);
		String[] tmpGroupIds = { "grp1", "grp2", "grp3" };
		poll.setAddrGroupTypeList(tmpGroupIds);
		poll.setTargetId("jjjj");
		poll.setTargetType("0");		
		poll.setAnswerUserId("jjjj");
		poll.setGroupId("user9");

		this.pk = pollService.create(poll);
		poll.setPollId(this.pk);
	}

	@Test
	public void testCreate() {
		Poll result = pollService.readByPoll(this.poll);		
		assertNotNull(result);	
	}

	@Test
	public void testUpdate() {
		this.poll = pollService.readByPoll(this.poll);
		this.poll.setQuestion("modified content");
		String[] tmpTitles1 = {"보기11","보기22","보기33"};
		poll.setAnswerTitles(tmpTitles1);
		String[] tmpIds1 = {"aaa1","bbb1","ccc1"};
		poll.setTargetUserIds(tmpIds1);
		poll.setTargetType("1");
		
		pollService.update(this.poll);
		Poll result = pollService.readByPoll(this.poll);
		assertEquals(this.poll.getQuestion(), result.getQuestion());

	}

	@Test
	public void testUpdateStatus() {
		this.poll = pollService.readByPoll(this.poll);
		this.poll.setStatus("1");
		pollService.updateStatus(this.poll);
		Poll result = pollService.readByPoll(this.poll);
		assertEquals(this.poll.getStatus(), result.getStatus());

	}
	
	@Test
	public void testList() {		
		Poll poll = new Poll();		
		poll.setPollId(this.pk);
		List<Answer> result = pollService.listAnswers(this.pk);
		assertNotNull(result);
	}	

	@Test
	public void testListPollReceiverBySearchCondition() {		
		PollReceiverSearchCondition pollReceiverSearchCondition = new PollReceiverSearchCondition();
		pollReceiverSearchCondition.setIsComplete("0");
		
		SearchResult<Poll> result = pollService.listPollReceiverBySearchCondition(pollReceiverSearchCondition);
		assertNotNull(result);
	}
	
	@Test
	public void testListPollAdminBySearchCondition() {		
		PollReceiverSearchCondition pollReceiverSearchCondition = new PollReceiverSearchCondition();
		pollReceiverSearchCondition.setIsComplete("0");
		
		SearchResult<Poll> result = pollService.listPollAdminBySearchCondition(pollReceiverSearchCondition);
		assertNotNull(result);
	}	
	
	@Test
	public void testDelete() {
		pollService.delete(this.pk);

		Poll result = pollService.readByPoll(poll);
		assertNull(result);
	}
	
	@Test
	public void testCheckAlreadyMailExists() {	
		int checkCount = pollService.checkAlreadyApply(poll);
		assertNotNull(checkCount);
	}	
}
