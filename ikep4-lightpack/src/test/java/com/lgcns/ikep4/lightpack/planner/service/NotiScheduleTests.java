/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgcns.ikep4.lightpack.planner.dao.AlarmDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: NotiScheduleTests.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
		"classpath:/configuration/spring/context-service.xml" })
public class NotiScheduleTests extends AbstractTransactionalJUnit4SpringContextTests {
	private final static String portalId = "P000001";

	private static String scheduleId = "200001";

	private final static String categoryId = "1";

	private final static String registerId = "user1004";

	private final static String registerName = "사용자1004";

	private static String userId = registerId;

	private Schedule schedule;

	private List<Participant> participants = new ArrayList<Participant>();

	private List<Alarm> alarms = new ArrayList<Alarm>();

	private static String[] parsePatterns = new String[] { "yyyyMMddHHmm", "yyyy-MM-dd", "yyyyMMdd" };

	@Autowired
	private CalendarService calendarService;

	@Autowired
	private AlarmPlannerService alarmPlannerService;

	User user;

	@Before
	public void setUp() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();

		user = new User();
		user.setUserId("user1");
		user.setUserName("사용자1");

		session.setAttribute("ikep.user", user);
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		calendarService.clearCall();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Ignore
	@Test
	public void notiSchedule() throws ParseException {

		Calendar c = Calendar.getInstance();
		Date jobTime = c.getTime();
		
		int alarmTime = 120;
		String alarmTimeStr = String.valueOf(alarmTime);
		int interval = 5;
		
		c.add(Calendar.MINUTE, + (alarmTime + interval));
		c.set(Calendar.SECOND, 0);
		
		Date start = c.getTime();
		Date end = DateUtils.addHours(start, 1);
		
		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "alarm 테스트", "alarm 테스트", 0,
				start, end);
		
		participants.add(new Participant(scheduleId, "user10047", "1")); // 참석자
		participants.add(new Participant(scheduleId, "user15", "2")); // 참조자
		participants.add(new Participant(scheduleId, "user234", "1")); // 참석자
		
		alarms.add(new Alarm("11", scheduleId, "0", alarmTimeStr)); // 메일
		alarms.add(new Alarm("22", scheduleId, "1", "30")); // SMS
		alarms.add(new Alarm("33", scheduleId, "2", "60")); // 
		
		schedule.setParticipantList(participants);
		schedule.setAlarmList(alarms);
		
		String sd1 = calendarService.create(schedule);
		
		
		/************************************************************************/
			
		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "alarm repeat 테스트", "alarm repeat 테스트", 1,
				start, end);
		
		String repeatType = "1", repeatPeriod = "1", repeatPeriodOption = "";
		Date repeatStart = start;
		Date repeatEnd = DateUtils.addDays(start, 7);
		
		Recurrences rep = new Recurrences(scheduleId, repeatType, repeatPeriod, repeatPeriodOption, repeatStart, repeatEnd,
				start, end);
		
		List recurrences = new ArrayList();
		recurrences.add(rep);
		schedule.setRecurrences(recurrences);
		
		participants.clear();
		participants.add(new Participant(scheduleId, "user10011", "1")); // 참석자
		participants.add(new Participant(scheduleId, "user10095", "2")); // 참조자
		participants.add(new Participant(scheduleId, "user234", "1")); // 참석자
		
		alarms.clear();
		alarms.add(new Alarm("111", scheduleId, "0", alarmTimeStr)); // 메일
		alarms.add(new Alarm("221", scheduleId, "1", "30")); // SMS
		alarms.add(new Alarm("331", scheduleId, "2", "60")); // 
		
		schedule.setParticipantList(participants);
		schedule.setAlarmList(alarms);
		
		String sd2 = calendarService.create(schedule);
		
		alarmPlannerService.doAlarmJobSchedule(jobTime, 5, "");
		assertTrue(true);
	}
}
