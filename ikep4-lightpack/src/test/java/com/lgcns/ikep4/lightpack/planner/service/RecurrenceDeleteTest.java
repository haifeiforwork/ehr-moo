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

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;

@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class RecurrenceDeleteTest extends AbstractTransactionalJUnit4SpringContextTests {

	private final static String portalId = "P000001";
	private final static String categoryId = "1";
	private final static String registerId = "user1005";
	private final static String registerName = "사용자1005";
	private static String userId = registerId;
	private static String userName = registerName;
	private Schedule schedule;
	private List recurrences;
	private String scheduleId;
	private static String pattern = "yyyyMMddHHmm";
	private static String[] parsePattenrs = new String[] {"yyyyMMddHHmm", "yyyyMMdd"};
	private FastDateFormat fdfDate = FastDateFormat.getInstance("yyyyMMdd");
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyyMMddHHmm");

	@Autowired
	private CalendarService service;
	
	@Test // 반복일정 중 처음 event 삭제
	public void deleteFirstEvent() throws ParseException {
		String startDate = "201104261030";
		String endDate = "201104261330";
		String rangeStart = "20110426";
		String rangeEnd = "20110510";
		String repeatType = "1"; 
		String repeatPeriod = "5"; 
		String repeatPeriodOption = "";
		
		schedule = new Schedule(null, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 1,
				Schedule.toDate(startDate), Schedule.toDate(endDate));

		Recurrences rep = new Recurrences(null, repeatType, repeatPeriod, repeatPeriodOption, Schedule.toDate(rangeStart), Schedule.toDate(rangeEnd),
				Schedule.toDate(startDate), Schedule.toDate(endDate));
		
		recurrences = new ArrayList();
		recurrences.add(rep);
		schedule.setRecurrences(recurrences);
		scheduleId = service.create(schedule);
		
		assertNotNull(scheduleId);
		
		Map<String, Object> params = new HashMap();
		params.put("scheduleId", scheduleId);
		params.put("deleteType", "1");	// 이번일정만
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("repeatStartDate", rangeStart);
		params.put("repeatEndDate", rangeEnd);
		params.put("repeatType", repeatType);
		params.put("repeatPeriod", repeatPeriod);
		params.put("repeatPeriodOption", repeatPeriodOption);
		
		service.deleteRepeatSchedule(params);
		
		Schedule sch = service.read(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		assertEquals("201105011030", fdfDateTime.format(sch.getStartDate()));
		assertEquals("201105011330", fdfDateTime.format(sch.getEndDate()));
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110501", fdfDate.format(recur.getStartDate()));
		assertEquals(rangeEnd, fdfDate.format(recur.getEndDate()));
		assertEquals("201105011030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105011330", fdfDateTime.format(recur.getSdEndDate()));
	}
	
	@Test // 반복일정 중 마지막 event 삭제
	public void deleteLastEvent() throws ParseException {
		String startDate = "201104261030";
		String endDate = "201104261330";
		String rangeStart = "20110426";
		String rangeEnd = "20110510";
		String repeatType = "1"; 
		String repeatPeriod = "5"; 
		String repeatPeriodOption = "";
		
		schedule = new Schedule(null, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 1,
				Schedule.toDate(startDate), Schedule.toDate(endDate));

		Recurrences rep = new Recurrences(null, repeatType, repeatPeriod, repeatPeriodOption, Schedule.toDate(rangeStart), Schedule.toDate(rangeEnd),
				Schedule.toDate(startDate), Schedule.toDate(endDate));
		
		recurrences = new ArrayList();
		recurrences.add(rep);
		schedule.setRecurrences(recurrences);
		scheduleId = service.create(schedule);
		
		assertNotNull(scheduleId);
		
		Map<String, Object> params = new HashMap();
		params.put("scheduleId", scheduleId);
		params.put("deleteType", "1");	// 이번일정만
		params.put("startDate", "201105061030");
		params.put("endDate", "201105061330");
		params.put("repeatStartDate", rangeStart);
		params.put("repeatEndDate", rangeEnd);
		params.put("repeatType", repeatType);
		params.put("repeatPeriod", repeatPeriod);
		params.put("repeatPeriodOption", repeatPeriodOption);
		
		service.deleteRepeatSchedule(params);
		
		Schedule sch = service.read(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals(rangeStart, fdfDate.format(recur.getStartDate()));
		assertEquals("20110501", fdfDate.format(recur.getEndDate()));
		assertEquals(startDate, fdfDateTime.format(recur.getSdStartDate()));
		assertEquals(endDate, fdfDateTime.format(recur.getSdEndDate()));
	}
	
	@Test // 반복일정  중간 event 삭제
	public void deleteMiddleEvent() throws ParseException {
		String startDate = "201104261030";
		String endDate = "201104261330";
		String rangeStart = "20110426";
		String rangeEnd = "20110510";
		String repeatType = "1"; 
		String repeatPeriod = "5"; 
		String repeatPeriodOption = "";
		
		schedule = new Schedule(null, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 1,
				Schedule.toDate(startDate), Schedule.toDate(endDate));

		Recurrences rep = new Recurrences(null, repeatType, repeatPeriod, repeatPeriodOption, Schedule.toDate(rangeStart), Schedule.toDate(rangeEnd),
				Schedule.toDate(startDate), Schedule.toDate(endDate));
		
		recurrences = new ArrayList();
		recurrences.add(rep);
		schedule.setRecurrences(recurrences);
		scheduleId = service.create(schedule);
		
		assertNotNull(scheduleId);
		
		Map<String, Object> params = new HashMap();
		params.put("scheduleId", scheduleId);
		params.put("deleteType", "1");	// 이번일정만
		params.put("startDate", "201105011030");
		params.put("endDate", "201105011330");
		params.put("repeatStartDate", rangeStart);
		params.put("repeatEndDate", rangeEnd);
		params.put("repeatType", repeatType);
		params.put("repeatPeriod", repeatPeriod);
		params.put("repeatPeriodOption", repeatPeriodOption);
		
		service.deleteRepeatSchedule(params);
		
		Schedule sch = service.read(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(2, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110426", fdfDate.format(recur.getStartDate()));
		assertEquals("20110426", fdfDate.format(recur.getEndDate()));
		assertEquals("201104261030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201104261330", fdfDateTime.format(recur.getSdEndDate()));
		
		recur = (Recurrences) recList.get(1);
		assertEquals("20110506", fdfDate.format(recur.getStartDate()));
		assertEquals(rangeEnd, fdfDate.format(recur.getEndDate()));
		assertEquals("201105061030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105061330", fdfDateTime.format(recur.getSdEndDate()));
	}
	
	@Test // 복수요일 반복일정  마지막 event 삭제
	public void deleteMultiWeeklyLastEvent() throws ParseException {
		String startDate = "201104071030";
		String endDate = "201104071330";
		String rangeStart = "20110407";	//
		String rangeEnd = "20110424";
		String repeatType = "2"; 
		String repeatPeriod = "2"; 
		String repeatPeriodOption = "2,5,7";  // 월, 목, 토
		
		schedule = new Schedule(null, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 1,
				Schedule.toDate(startDate), Schedule.toDate(endDate));

		Recurrences rep = new Recurrences(null, repeatType, repeatPeriod, repeatPeriodOption, Schedule.toDate(rangeStart), Schedule.toDate(rangeEnd),
				Schedule.toDate(startDate), Schedule.toDate(endDate));
		
		recurrences = new ArrayList();
		recurrences.add(rep);
		schedule.setRecurrences(recurrences);
		scheduleId = service.create(schedule);
		
		assertNotNull(scheduleId);
		
		Map<String, Object> params = new HashMap();
		params.put("scheduleId", scheduleId);
		params.put("deleteType", "1");	// 이번일정만
		params.put("startDate", "201104231030");
		params.put("endDate", "201104231330");
		params.put("repeatStartDate", rangeStart);
		params.put("repeatEndDate", rangeEnd);
		params.put("repeatType", repeatType);
		params.put("repeatPeriod", repeatPeriod);
		params.put("repeatPeriodOption", repeatPeriodOption);
		
		service.deleteRepeatSchedule(params);
		
		Schedule sch = service.read(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals(startDate.substring(0, 8), fdfDate.format(recur.getStartDate()));
		assertEquals("20110421", fdfDate.format(recur.getEndDate()));
		assertEquals(startDate, fdfDateTime.format(recur.getSdStartDate()));
		assertEquals(endDate, fdfDateTime.format(recur.getSdEndDate()));
	}
	
	@Test // 반복일정  향후 일정 삭제
	public void deleteAfterRecurrences() throws ParseException {
		String startDate = "201104071030";
		String endDate = "201104071330";
		String rangeStart = "20110407";	//
		String rangeEnd = "20110424";
		String repeatType = "2"; 
		String repeatPeriod = "1"; 
		String repeatPeriodOption = "2,5,7";  // 월, 목, 토
		
		schedule = new Schedule(null, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 1,
				Schedule.toDate(startDate), Schedule.toDate(endDate));

		Recurrences rep = new Recurrences(null, repeatType, repeatPeriod, repeatPeriodOption, Schedule.toDate(rangeStart), Schedule.toDate(rangeEnd),
				Schedule.toDate(startDate), Schedule.toDate(endDate));
		
		recurrences = new ArrayList();
		recurrences.add(rep);
		schedule.setRecurrences(recurrences);
		scheduleId = service.create(schedule);
		
		assertNotNull(scheduleId);
		
		Map<String, Object> params = new HashMap();
		params.put("scheduleId", scheduleId);
		params.put("deleteType", "1");	// 이번일정만
		params.put("startDate", "201104231030");
		params.put("endDate", "201104231330");
		params.put("repeatStartDate", rangeStart);
		params.put("repeatEndDate", rangeEnd);
		params.put("repeatType", repeatType);
		params.put("repeatPeriod", repeatPeriod);
		params.put("repeatPeriodOption", repeatPeriodOption);
		
		service.deleteRepeatSchedule(params);
		
		Schedule sch = service.read(scheduleId);
		List recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals(startDate.substring(0, 8), fdfDate.format(recur.getStartDate()));
		assertEquals("20110421", fdfDate.format(recur.getEndDate()));
		assertEquals(startDate, fdfDateTime.format(recur.getSdStartDate()));
		assertEquals(endDate, fdfDateTime.format(recur.getSdEndDate()));
		
		params.clear();
		params.put("scheduleId", scheduleId);
		params.put("deleteType", "2");	// 향후 모든 일정
		params.put("startDate", "201104091030");
		params.put("endDate", "201104091330");
		params.put("repeatStartDate", rangeStart);
		params.put("repeatEndDate", "20110421");
		params.put("repeatType", repeatType);
		params.put("repeatPeriod", repeatPeriod);
		params.put("repeatPeriodOption", repeatPeriodOption);
		
		service.deleteRepeatSchedule(params);
		
		sch = service.read(scheduleId);
		recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		
		recur = (Recurrences) recList.get(0);
		assertEquals(startDate.substring(0, 8), fdfDate.format(recur.getStartDate()));
		assertEquals("20110407", fdfDate.format(recur.getEndDate()));
		assertEquals(startDate, fdfDateTime.format(recur.getSdStartDate()));
		assertEquals(endDate, fdfDateTime.format(recur.getSdEndDate()));
	}
}
