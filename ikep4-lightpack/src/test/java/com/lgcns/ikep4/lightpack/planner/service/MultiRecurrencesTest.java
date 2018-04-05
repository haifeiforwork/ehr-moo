/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: MultiRecurrencesTest.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class MultiRecurrencesTest extends AbstractTransactionalJUnit4SpringContextTests {

	private final static String portalId = "P000001";
	private final static String categoryId = "1";
	private final static String registerId = "user1005";
	private final static String registerName = "사용자1005";
	private static String userId = registerId;
	private static String userName = registerName;
	private Schedule schedule;
	private Schedule newSchedule;
	private List recurrences = new ArrayList();
	private String scheduleId;
	private static String pattern = "yyyyMMddHHmm";
	private static String[] parsePattenrs = new String[] {"yyyyMMddHHmm", "yyyyMMdd"};
	private FastDateFormat fdfDate = FastDateFormat.getInstance("yyyyMMdd");
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyyMMddHHmm");
	private UpdateScheduleVO updateVO;
	
	private List<Participant> participants = new ArrayList<Participant>();

	private List<Alarm> alarms = new ArrayList<Alarm>();

	String startDate = "201103271030";
	String endDate = "201103271330";
	String rangeStart = "20110327";
	String rangeEnd = "20110328";
	String startDate2 = "201104081030";
	String endDate2 = "201104081330";
	String rangeStart2 = "20110408";
	String rangeEnd2 = "20110430";
	String repeatType = "2"; 
	String repeatPeriod = "1"; 
	String repeatPeriodOption = "2,6";
	
	String newStartDate = "201104051030";
	String newEndDate = "201104051330";
	String newTitle = "newTitle";
	
	@Autowired
	private CalendarService service;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Before
	public void setup() {
		service.clearCall();
		
		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		user.setPortalId(portalId);
		updateVO = new UpdateScheduleVO();
		updateVO.setUser(user);

		schedule = new Schedule(null, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 1,
				Schedule.toDate(startDate), Schedule.toDate(endDate));

		Recurrences rep = new Recurrences(null, repeatType, repeatPeriod, repeatPeriodOption, Schedule.toDate(rangeStart), Schedule.toDate(rangeEnd),
				Schedule.toDate(startDate), Schedule.toDate(endDate));
		Recurrences rep2 = new Recurrences(null, repeatType, repeatPeriod, repeatPeriodOption, Schedule.toDate(rangeStart2), Schedule.toDate(rangeEnd2),
				Schedule.toDate(startDate2), Schedule.toDate(endDate2));
		
		recurrences.clear();
		recurrences = new ArrayList();
		recurrences.add(rep);
		recurrences.add(rep2);
		schedule.setRecurrences(recurrences);
		
		participants.clear();
		participants.add(new Participant(scheduleId, userId, "0")); // 공개자
		participants.add(new Participant(scheduleId, "user1004", "1")); // 참석자
	
		alarms.clear();
		alarms.add(new Alarm("11", scheduleId, "0", "60")); // 메일/60분
		alarms.add(new Alarm("22", scheduleId, "1", "30")); // SMS/30분
		
		schedule.setParticipantList(participants);
		schedule.setAlarmList(alarms);
		
		scheduleId = service.create(schedule);
		assertNotNull(scheduleId);
		
		newSchedule = new Schedule(newTitle, "수정 일정", 0, Schedule.toDate(newStartDate), Schedule.toDate(newEndDate));
		newSchedule.setCategoryId(categoryId);
	}
	@Ignore //timezone 적용후
	@Test
	public void updateCurrentRecurFirstEvent() throws ParseException {
		updateVO.setUpdateType(1); // 이번일정만
		updateVO.setNewSchedule(newSchedule);
		
		Map<String, Object> oldParams = new HashMap();
		oldParams.put("scheduleId", scheduleId);
		oldParams.put("startDate", "201103281030");
		oldParams.put("endDate", "201103281030");
		oldParams.put("repeatStartDate", rangeStart);
		oldParams.put("repeatEndDate", rangeEnd);
		oldParams.put("repeatType", repeatType);
		oldParams.put("repeatPeriod", repeatPeriod);
		oldParams.put("repeatPeriodOption", repeatPeriodOption);
		
		updateVO.setOldSchedule(oldParams);
		
		service.updateSchedule(updateVO);
		
		Schedule sch = service.read(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", newStartDate.substring(0, 8));
		p.put("endDate",   newEndDate.substring(0, 8));
		List ret = scheduleService.selectByPeriod(p);
		
		assertEquals(1, ret.size());
		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		
		assertEquals(newStartDate, nsch.get("startDate"));
		assertEquals(newEndDate, nsch.get("endDate"));
		assertNull(nsch.get("repeatType"));
		assertEquals(newTitle, nsch.get("title"));
	}
}
