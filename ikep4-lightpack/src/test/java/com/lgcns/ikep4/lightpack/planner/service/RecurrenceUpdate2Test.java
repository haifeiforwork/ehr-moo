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
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
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
import com.lgcns.ikep4.lightpack.planner.model.UpdateMap;
import com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.support.user.member.model.User;

@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class RecurrenceUpdate2Test extends AbstractTransactionalJUnit4SpringContextTests {

	private final static String portalId = "P000001";
	private final static String categoryId = "1";
	private final static String registerId = "user1005";
	private final static String registerName = "사용자1005";
	private static String userId = registerId;
	private static String userName = registerName;
	private Schedule schedule;
	private Schedule newSchedule;
	private List<Recurrences> recurrences = new ArrayList<Recurrences>();
	private String scheduleId;
	private FastDateFormat fdfDate = FastDateFormat.getInstance("yyyyMMdd");
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyyMMddHHmm");
	private UpdateScheduleVO updateVO;
	
	private List<Participant> participants = new ArrayList<Participant>();

	private List<Alarm> alarms = new ArrayList<Alarm>();

	Date startDate, endDate, repeatStart, repeatEnd;

	String repeatType = "1"; 
	String repeatPeriod = "5"; 
	String repeatPeriodOption = "";
	
	String newStartDate = "201105051030";
	String newEndDate = "201105051330";
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
		
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 4, 1, 10, 30, 0);	// 2011.05.01 10:30
		startDate = c.getTime();
		c.set(2011, 4, 1, 13, 30, 0);	// 2011.05.01 13:30
		endDate = c.getTime();

		schedule = new Schedule(null, portalId, categoryId, registerId, registerName, "update 테스트", "update 테스트", 1,
				new Date(startDate.getTime()), new Date(endDate.getTime()));

		recurrences = new ArrayList();
		
		c.set(2011, 4, 1, 10, 30, 0);
		repeatStart = c.getTime();
		c.set(2011, 4, 16, 13, 30, 0);
		repeatEnd = c.getTime();
		Recurrences rep = new Recurrences(repeatType, repeatPeriod, repeatPeriodOption, 
				DateUtils.truncate(repeatStart, Calendar.DATE), DateUtils.truncate(repeatEnd, Calendar.DATE),
				new Date(startDate.getTime()), new Date(endDate.getTime()));
		recurrences.add(rep);
		
		c.set(2011, 4, 22, 10, 30, 0);
		startDate = c.getTime();
		repeatStart = startDate;
		c.set(2011, 4, 22, 13, 30, 0);
		endDate = c.getTime();
		c.set(2011, 4, 28, 13, 0, 0);
		repeatEnd = c.getTime();
		rep = new Recurrences(repeatType, repeatPeriod, repeatPeriodOption, 
				DateUtils.truncate(repeatStart, Calendar.DATE), DateUtils.truncate(repeatEnd, Calendar.DATE),
				new Date(startDate.getTime()), new Date(endDate.getTime()));
		recurrences.add(rep);
		
		c.set(2011, 5, 3, 10, 30, 0);
		startDate = c.getTime();
		repeatStart = startDate;
		c.set(2011, 5, 3, 13, 30, 0);
		endDate = c.getTime();
		c.set(2011, 5, 3, 13, 0, 0);
		repeatEnd = c.getTime();
		rep = new Recurrences(repeatType, repeatPeriod, repeatPeriodOption, 
				DateUtils.truncate(repeatStart, Calendar.DATE), DateUtils.truncate(repeatEnd, Calendar.DATE),
				new Date(startDate.getTime()), new Date(endDate.getTime()));
		recurrences.add(rep);
		
		c.set(2011, 5, 9, 10, 30, 0);
		startDate = c.getTime();
		repeatStart = startDate;
		c.set(2011, 5, 9, 13, 30, 0);
		endDate = c.getTime();
		c.set(2011, 6, 1, 13, 0, 0);
		repeatEnd = c.getTime();
		rep = new Recurrences(repeatType, repeatPeriod, repeatPeriodOption, 
				DateUtils.truncate(repeatStart, Calendar.DATE), DateUtils.truncate(repeatEnd, Calendar.DATE),
				new Date(startDate.getTime()), new Date(endDate.getTime()));
		recurrences.add(rep);
		
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
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Test // 반복일정 중 처음 event 수정, UpdatePart = repeat(반복정보)
	public void updateFirstEventUpdatePartRepeat() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 4, 22, 15, 30, 0);
		Date newStartDate = c.getTime();
		c.set(2011, 4, 22, 17, 30, 0);
		Date newEndDate = c.getTime();
		
		newSchedule = new Schedule(newTitle, "수정 일정", 0, newStartDate, newEndDate);
		newSchedule.setCategoryId(categoryId);
		
		updateVO.setUpdateType(2); // 향후일정
		updateVO.setUpdatePart("repeat");	// 반복정보
		updateVO.setNewSchedule(newSchedule);
		
		c.set(2011, 4, 22, 0, 0, 0);
		Date thisRepeatStart = c.getTime();
		c.set(2011, 4, 28, 0, 0, 0);
		Date thisRepeatEnd = c.getTime();
		
		c.set(2011, 4, 22, 10, 30, 0);
		Date oldEventStart = c.getTime();
		c.set(2011, 4, 22, 13, 30, 0);
		Date oldEventEnd = c.getTime();
		
		Map<String, Object> oldParams = new HashMap();
		oldParams.put("scheduleId", scheduleId);
		oldParams.put("startDate", fdfDateTime.format(oldEventStart));
		oldParams.put("endDate", fdfDateTime.format(oldEventEnd));
		oldParams.put("repeatStartDate", fdfDateTime.format(thisRepeatStart));
		oldParams.put("repeatEndDate", fdfDateTime.format(thisRepeatEnd));
		oldParams.put("repeatType", repeatType);
		oldParams.put("repeatPeriod", repeatPeriod);
		oldParams.put("repeatPeriodOption", repeatPeriodOption);
		
		updateVO.setOldSchedule(oldParams);
		
		service.updateSchedule(updateVO);
		
		Schedule sch = service.getScheduleAllData(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110501", fdfDate.format(recur.getStartDate()));
		assertEquals("20110516", fdfDate.format(recur.getEndDate()));
		assertEquals("201105011030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105011330", fdfDateTime.format(recur.getSdEndDate()));
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Test // 반복일정 중 처음 event 수정, UpdatePart = content(반복정보)
	public void updateFirstEventUpdatePartContent() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 4, 22, 15, 30, 0);
		Date newStartDate = c.getTime();
		c.set(2011, 4, 22, 17, 30, 0);
		Date newEndDate = c.getTime();
		
		newSchedule = new Schedule(newTitle, "수정 일정", 1, newStartDate, newEndDate);
		newSchedule.setCategoryId(categoryId);
		
		updateVO.setUpdateType(2); // 향후일정
		updateVO.setUpdatePart("content");	// 내용만
		updateVO.setNewSchedule(newSchedule);
		
		c.set(2011, 4, 22, 0, 0, 0);
		Date thisRepeatStart = c.getTime();
		c.set(2011, 4, 28, 0, 0, 0);
		Date thisRepeatEnd = c.getTime();
		
		c.set(2011, 4, 22, 10, 30, 0);
		Date oldEventStart = c.getTime();
		c.set(2011, 4, 22, 13, 30, 0);
		Date oldEventEnd = c.getTime();
		
		Map<String, Object> oldParams = new HashMap();
		oldParams.put("scheduleId", scheduleId);
		oldParams.put("startDate", fdfDateTime.format(oldEventStart));
		oldParams.put("endDate", fdfDateTime.format(oldEventEnd));
		oldParams.put("repeatStartDate", fdfDateTime.format(thisRepeatStart));
		oldParams.put("repeatEndDate", fdfDateTime.format(thisRepeatEnd));
		oldParams.put("repeatType", repeatType);
		oldParams.put("repeatPeriod", repeatPeriod);
		oldParams.put("repeatPeriodOption", repeatPeriodOption);
		
		updateVO.setOldSchedule(oldParams);
		
		service.updateSchedule(updateVO);
		
		Schedule sch = service.getScheduleAllData(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110501", fdfDate.format(recur.getStartDate()));
		assertEquals("20110516", fdfDate.format(recur.getEndDate()));
		assertEquals("201105011030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105011330", fdfDateTime.format(recur.getSdEndDate()));
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", fdfDate.format(DateUtils.addDays(oldEventStart, 1)));
		p.put("endDate", "20991231");
		List ret = scheduleService.selectByPeriod(p);
		
		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		String nsid = (String) nsch.get("scheduleId");
		
		sch = service.getScheduleAllData(nsid);
		//assertEquals(3, sch.getRecurrences().size());
		// TODO: 에러 - 확인할 것 clean을 삭제한 후 발생
		//assertTrue(sch.getRecurrences().size() >= 3);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Ignore
	@Test // 반복일정 중 마지막 event 수정, UpdatePart = repeat(반복정보)
	public void updateLastEventUpdatePartRepeat() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 4, 28, 15, 30, 0);
		Date newStartDate = c.getTime();
		c.set(2011, 4, 28, 17, 30, 0);
		Date newEndDate = c.getTime();
		
		newSchedule = new Schedule(newTitle, "수정 일정", 0, newStartDate, newEndDate);
		newSchedule.setCategoryId(categoryId);
		
		updateVO.setUpdateType(2); // 향후일정
		updateVO.setUpdatePart("repeat");	// 반복정보
		updateVO.setNewSchedule(newSchedule);
		
		c.set(2011, 4, 22, 0, 0, 0);
		Date thisRepeatStart = c.getTime();
		c.set(2011, 4, 28, 0, 0, 0);
		Date thisRepeatEnd = c.getTime();
		
		c.set(2011, 4, 28, 10, 30, 0);
		Date oldEventStart = c.getTime();
		c.set(2011, 4, 28, 13, 30, 0);
		Date oldEventEnd = c.getTime();
		
		Map<String, Object> oldParams = new HashMap();
		oldParams.put("scheduleId", scheduleId);
		oldParams.put("startDate", fdfDateTime.format(oldEventStart));
		oldParams.put("endDate", fdfDateTime.format(oldEventEnd));
		oldParams.put("repeatStartDate", fdfDateTime.format(thisRepeatStart));
		oldParams.put("repeatEndDate", fdfDateTime.format(thisRepeatEnd));
		oldParams.put("repeatType", repeatType);
		oldParams.put("repeatPeriod", repeatPeriod);
		oldParams.put("repeatPeriodOption", repeatPeriodOption);
		
		updateVO.setOldSchedule(oldParams);
		
		service.updateSchedule(updateVO);
		
		Schedule sch = service.getScheduleAllData(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(2, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110501", fdfDate.format(recur.getStartDate()));
		assertEquals("20110516", fdfDate.format(recur.getEndDate()));
		assertEquals("201105011030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105011330", fdfDateTime.format(recur.getSdEndDate()));
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Ignore
	@Test // 반복일정 중 마지막 event 수정, UpdatePart = content(반복정보)
	public void updateLastEventUpdatePartContent() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 4, 28, 15, 30, 0);
		Date newStartDate = c.getTime();
		c.set(2011, 4, 28, 17, 30, 0);
		Date newEndDate = c.getTime();
		
		newSchedule = new Schedule(newTitle, "수정 일정", 1, newStartDate, newEndDate);
		newSchedule.setCategoryId(categoryId);
		
		updateVO.setUpdateType(2); // 향후일정
		updateVO.setUpdatePart("content");	// 내용만
		updateVO.setNewSchedule(newSchedule);
		
		c.set(2011, 4, 22, 0, 0, 0);
		Date thisRepeatStart = c.getTime();
		c.set(2011, 4, 28, 0, 0, 0);
		Date thisRepeatEnd = c.getTime();
		
		c.set(2011, 4, 28, 10, 30, 0);
		Date oldEventStart = c.getTime();
		c.set(2011, 4, 28, 13, 30, 0);
		Date oldEventEnd = c.getTime();
		
		Map<String, Object> oldParams = new HashMap();
		oldParams.put("scheduleId", scheduleId);
		oldParams.put("startDate", fdfDateTime.format(oldEventStart));
		oldParams.put("endDate", fdfDateTime.format(oldEventEnd));
		oldParams.put("repeatStartDate", fdfDateTime.format(thisRepeatStart));
		oldParams.put("repeatEndDate", fdfDateTime.format(thisRepeatEnd));
		oldParams.put("repeatType", repeatType);
		oldParams.put("repeatPeriod", repeatPeriod);
		oldParams.put("repeatPeriodOption", repeatPeriodOption);
		
		updateVO.setOldSchedule(oldParams);
		
		service.updateSchedule(updateVO);
		
		Schedule sch = service.getScheduleAllData(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(2, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110501", fdfDate.format(recur.getStartDate()));
		assertEquals("20110516", fdfDate.format(recur.getEndDate()));
		assertEquals("201105011030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105011330", fdfDateTime.format(recur.getSdEndDate()));
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", fdfDate.format(DateUtils.addDays(oldEventStart, 1)));
		p.put("endDate", "20991231");
		List ret = scheduleService.selectByPeriod(p);
		
		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		String nsid = (String) nsch.get("scheduleId");
		
		sch = service.getScheduleAllData(nsid);
		assertEquals(2, sch.getRecurrences().size());
	}
	
	@SuppressWarnings({ "unchecked", "unused" }) 
	@Test // 반복일정 중 중간 event 수정, UpdatePart = repeat(반복정보)
	public void updateMiddleEventUpdatePartRepeat() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 4, 6, 15, 30, 0);
		Date newStartDate = c.getTime();
		c.set(2011, 4, 6, 17, 30, 0);
		Date newEndDate = c.getTime();
		
		newSchedule = new Schedule(newTitle, "수정 일정", 0, newStartDate, newEndDate);
		newSchedule.setCategoryId(categoryId);
		
		updateVO.setUpdateType(2); // 향후일정
		updateVO.setUpdatePart("repeat");	// 반복정보
		updateVO.setNewSchedule(newSchedule);
		
		c.set(2011, 4, 1, 0, 0, 0);
		Date thisRepeatStart = c.getTime();
		c.set(2011, 4, 16, 0, 0, 0);
		Date thisRepeatEnd = c.getTime();
		
		c.set(2011, 4, 6, 10, 30, 0);
		Date oldEventStart = c.getTime();
		c.set(2011, 4, 6, 13, 30, 0);
		Date oldEventEnd = c.getTime();
		
		Map<String, Object> oldParams = new HashMap();
		oldParams.put("scheduleId", scheduleId);
		oldParams.put("startDate", fdfDateTime.format(oldEventStart));
		oldParams.put("endDate", fdfDateTime.format(oldEventEnd));
		oldParams.put("repeatStartDate", fdfDateTime.format(thisRepeatStart));
		oldParams.put("repeatEndDate", fdfDateTime.format(thisRepeatEnd));
		oldParams.put("repeatType", repeatType);
		oldParams.put("repeatPeriod", repeatPeriod);
		oldParams.put("repeatPeriodOption", repeatPeriodOption);
		
		updateVO.setOldSchedule(oldParams);
		
		service.updateSchedule(updateVO);
		
		Schedule sch = service.getScheduleAllData(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110501", fdfDate.format(recur.getStartDate()));
		assertEquals("20110501", fdfDate.format(recur.getEndDate()));
		assertEquals("201105011030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105011330", fdfDateTime.format(recur.getSdEndDate()));
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Test // 반복일정 중 중간 event 수정, UpdatePart = content(반복정보)
	public void updateMiddleEventUpdatePartContent() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2011, 4, 6, 15, 30, 0);
		Date newStartDate = c.getTime();
		c.set(2011, 4, 6, 17, 30, 0);
		Date newEndDate = c.getTime();
		
		newSchedule = new Schedule(newTitle, "수정 일정", 1, newStartDate, newEndDate);
		newSchedule.setCategoryId(categoryId);
		
		updateVO.setUpdateType(2); // 향후일정
		updateVO.setUpdatePart("content");	// 내용만
		updateVO.setNewSchedule(newSchedule);
		
		c.set(2011, 4, 1, 0, 0, 0);
		Date thisRepeatStart = c.getTime();
		c.set(2011, 4, 16, 0, 0, 0);
		Date thisRepeatEnd = c.getTime();
		
		c.set(2011, 4, 6, 10, 30, 0);
		Date oldEventStart = c.getTime();
		c.set(2011, 4, 6, 13, 30, 0);
		Date oldEventEnd = c.getTime();
		
		Map<String, Object> oldParams = new HashMap();
		oldParams.put("scheduleId", scheduleId);
		oldParams.put("startDate", fdfDateTime.format(oldEventStart));
		oldParams.put("endDate", fdfDateTime.format(oldEventEnd));
		oldParams.put("repeatStartDate", fdfDateTime.format(thisRepeatStart));
		oldParams.put("repeatEndDate", fdfDateTime.format(thisRepeatEnd));
		oldParams.put("repeatType", repeatType);
		oldParams.put("repeatPeriod", repeatPeriod);
		oldParams.put("repeatPeriodOption", repeatPeriodOption);
		
		updateVO.setOldSchedule(oldParams);
		
		service.updateSchedule(updateVO);
		
		Schedule sch = service.getScheduleAllData(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110501", fdfDate.format(recur.getStartDate()));
		assertEquals("20110501", fdfDate.format(recur.getEndDate()));
		assertEquals("201105011030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105011330", fdfDateTime.format(recur.getSdEndDate()));
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", fdfDate.format(DateUtils.addDays(oldEventStart, 1)));
		p.put("endDate", "20991231");
		List ret = scheduleService.selectByPeriod(p);
		
		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		String nsid = (String) nsch.get("scheduleId");
		
		sch = service.getScheduleAllData(nsid);
		//assertEquals(3, sch.getRecurrences().size());
		// TODO: 에러 - 확인할 것 clean을 삭제한 후 발생
		//assertTrue(sch.getRecurrences().size() >= 3);
	}
}
