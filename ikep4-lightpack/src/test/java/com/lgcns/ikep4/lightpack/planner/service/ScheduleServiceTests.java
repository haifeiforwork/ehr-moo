/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: ScheduleServiceTests.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class ScheduleServiceTests extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ScheduleService scheduleService;
	
	private int timeDifference = 17; //(GMT-08:00) 미서부태평양표준시 (USA & Canada)
	private String timeDifferenceStr = "17"; //(GMT-08:00) 미서부태평양표준시 (USA & Canada)
	private MockHttpServletRequest req;
	private User user = new User();
	
//	@Before
//	public void init() {
//		req = new MockHttpServletRequest(); // HttpServletRequest Mock객체를 생성합니다.
//		MockHttpSession session = new MockHttpSession(); // HttpSession Mock객체를 생성합니다.
//		user.setUserId("user3005");
//		user.setTimeDifference(timeDifferenceStr);
//
//		session.setAttribute("ikep.user", user); 
//		req.setSession(session);
//	}
//	
//	@Test
//	public void selectByPeriodDaily() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2011, 5, 30);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", start, end);
//		assertEquals(18, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWeekly() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 0, 1);
//		Date start = c.getTime();
//		c.set(2011, 0, 31);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", start, end);
//		
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", "20110101", "20110131");
//		assertEquals(8, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodMonthlyA() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 11, 1);
//		Date start = c.getTime();
//		c.set(2012, 0, 31);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", "20111201", "20120131");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodMonthlyB() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2010, 2, 1);
//		Date start = c.getTime();
//		c.set(2010, 4, 31);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", "20100301", "20100531");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodMonthlyC() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2009, 9, 1);
//		Date start = c.getTime();
//		c.set(2009, 11, 31);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", "20091001", "20091231");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodMonthlyD() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2009, 0, 1);
//		Date start = c.getTime();
//		c.set(2009, 3, 30);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3005", "20090101", "20090430");
//		assertEquals(4, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodYearlyA() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2012, 5, 30);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3010", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3010", "20110601", "20120630");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodYearlyB() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2012, 5, 3);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3011", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3011", "20110601", "20120603");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodYearlyC() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2012, 5, 31);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3012", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3012", "20110601", "20120630");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodYearlyD() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2012, 5, 30);
//		Date end = c.getTime();
//		
//		List<Map<String, Object>> res = scheduleService.selectByPeriod("user3013", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriod("user3013", "20110601", "20120630");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceDaily() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2011, 5, 30);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", "20110601", "20110630");
//		assertEquals(18, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceWeekly() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 0, 1);
//		Date start = c.getTime();
//		c.set(2011, 0, 31);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", "20110101", "20110131");
//		assertEquals(8, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceMonthlyA() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 11, 1);
//		Date start = c.getTime();
//		c.set(2012, 0, 31);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", "20111201", "20120131");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceMonthlyB() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2010, 2, 1);
//		Date start = c.getTime();
//		c.set(2010, 4, 31);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", "20100301", "20100531");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceMonthlyC() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2009, 9, 1);
//		Date start = c.getTime();
//		c.set(2009, 11, 31);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", "20091001", "20091231");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceMonthlyD() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2009, 0, 1);
//		Date start = c.getTime();
//		c.set(2009, 3, 30);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730251", "20090101", "20090430");
//		assertEquals(4, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceYearlyA() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2012, 5, 30);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730171", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730171", "20110601", "20120630");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceYearlyB() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2012, 5, 30);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730199", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730199", "20110601", "20120603");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceYearlyC() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2012, 5, 30);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730455", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730455", "20110601", "20120630");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void selectByPeriodWorkspaceYearlyD() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2012, 5, 30);
//		Date end = c.getTime();
//		List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730655", start, end);
//		//List<Map<String, Object>> res = scheduleService.selectByPeriodForWorkspace("100000730655", "20110601", "20120630");
//		assertEquals(2, res.size());
//	}
//	
//	@Test
//	public void readUsersSchedule() throws ParseException {
//		Calendar c = Calendar.getInstance();
//		c.set(2011, 5, 1);
//		Date start = c.getTime();
//		c.set(2011, 5, 30);
//		Date end = c.getTime();
//		String[] users = new String[] {"user3005"};
//		
//		List<Map<String, Object>> res = scheduleService.readUsersSchedule(users, start, end);
//		//List<Map<String, Object>> res = scheduleService.readUsersSchedule(users, "20110601", "20110630");
//		assertEquals(18, res.size());
//	}
	
	@Test
	@Ignore
	public void getUserScheduleCount() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 5, 7);
		int count = scheduleService.getUserScheduleCount("user2004", c.getTime());
		assertEquals(3, count);
	}
	
	@Test
	@Ignore
	public void getAlarmTargetList() throws ParseException {
		List<Map> res = scheduleService.getAlarmTargetList(new Date(), 5);
		for (Map alarmInfo : res) {
			System.out.println(alarmInfo);
		}
		assertEquals(4, res.size());
	}
}
