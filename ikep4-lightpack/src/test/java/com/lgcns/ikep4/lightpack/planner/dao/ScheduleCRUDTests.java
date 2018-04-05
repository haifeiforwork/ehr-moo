/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.lightpack.planner.dao.AlarmDao;
import com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ScheduleCRUDTests.java 16302 2011-08-19 08:43:50Z giljae $
 */
@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class ScheduleCRUDTests extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ScheduleDao scheduleDao;

	@Autowired
	private ParticipantDao participantDao;

	@Autowired
	private AlarmDao alarmDao;

	private final static String portalId = "P000001";

	private final static String scheduleId = "200003";

	private final static String categoryId = "1";

	private final static String startDate = "20110222";

	private final static String endDate = "20110623";

	private final static String sdStartDate = "201102221030";

	private final static String sdEndDate = "201102231330";

	private final static String registerId = "user1";

	private final static String registerName = "사용자1";

	private static String userId = registerId;

	private Schedule schedule;

	private Recurrences recurrences;

	private List<Participant> participants = new ArrayList<Participant>();

	private List<Alarm> alarms = new ArrayList<Alarm>();
	
	private boolean isInitialized = false;

	@Before
	public void init() {
		if(isInitialized) {
			return;
		}

		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		recurrences = new Recurrences(scheduleId, "0", "", "", Schedule.toDate(startDate), Schedule.toDate(endDate),
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		participants.add(new Participant(scheduleId, userId, "0")); // 공개자
		participants.add(new Participant(scheduleId, "1002020", "1")); // 참석자
		alarms.add(new Alarm("1", scheduleId, "0", "60")); // 메일/60분
		alarms.add(new Alarm("2", scheduleId, "1", "30")); // SMS/30분
		
		scheduleDao.createSchedule(schedule);
		scheduleDao.createRecurrences(recurrences);
		participantDao.create(participants);
		alarmDao.create(alarms);
		
		isInitialized = true;
	}

	@Test
	public void scheduleRecurrenceSelect() {
		Schedule sd = scheduleDao.get(scheduleId);
		assertEquals(scheduleId, sd.getScheduleId());
		String sdt = DateFormatUtils.format(sd.getRecurrences().get(0).getSdStartDate(), "yyyyMMddHHmm");
		assertEquals(sdStartDate, sdt);
	}
	
	@Ignore  // TODO:
	@Test
	public void participantSelect() {
		List<Participant> ps = participantDao.getList(scheduleId);
		assertTrue(ps.size() > 0);
	}
	
	@Test
	public void alarmSelect() {
		List<Alarm> ps = alarmDao.getList(scheduleId);
		assertTrue(ps.size() > 0);
	}
	
	@Test
	public void scheduleDelete() {
		scheduleDao.remove(scheduleId);
		
		Schedule sd = scheduleDao.get(scheduleId);
		assertNull(sd);
		
		alarmDao.remove(scheduleId);
		List ps = alarmDao.getList(scheduleId);
		assertTrue(ps.size() == 0);
		
		participantDao.remove(scheduleId);
		ps = participantDao.getList(scheduleId);
		assertTrue(ps.size() == 0);		
	}
	
	@Test
	public void subTabledelete() {
		alarmDao.remove(scheduleId);
		List ps = alarmDao.getList(scheduleId);
		assertTrue(ps.size() == 0);
		
		participantDao.remove(scheduleId);
		ps = participantDao.getList(scheduleId);
		assertTrue(ps.size() == 0);
		
		isInitialized = false;
	}
	
	@Test
	public void normalUpdate() {
		String updatedTitle = "수정된 일정";
		Schedule sd = scheduleDao.get(scheduleId);
		sd.setTitle(updatedTitle);
		
		scheduleDao.update(sd);
		sd = scheduleDao.get(scheduleId);
		
		assertEquals(updatedTitle, sd.getTitle());
	}
	
	@Ignore  // TODO:
	@Test
	public void move() {
		Schedule sc = scheduleDao.get(scheduleId);
		
		Date start = sc.getStartDate();
		Date end   = sc.getEndDate();
		
		int dayDelta = -7, minuteDelta = -30;
		
		User user = new User();
		user.setUserId("1");
		user.setUserName("abc");
		Map params = new HashMap();
		params.put("sid", scheduleId);
		params.put("dayDelta", dayDelta);
		params.put("minuteDelta", minuteDelta);
		params.put("user", user);
		params.put("updateType", 0);
		
		scheduleDao.scheduleMove(params);		
											
		start = DateUtils.addDays(start, dayDelta);
		start = DateUtils.addMinutes(start, minuteDelta);
		
		end  = DateUtils.addDays(end, dayDelta);
		end  = DateUtils.addMinutes(end, minuteDelta);
		
		Schedule result = scheduleDao.get(scheduleId);
		assertEquals(start, result.getStartDate());
		assertEquals(end, result.getEndDate());
	}
	
	@Ignore  // TODO:
	@Test
	public void resize() {
		Schedule sc = scheduleDao.get(scheduleId);
		
		Date start = sc.getStartDate();
		Date end   = sc.getEndDate();
		
		int dayDelta = -7, minuteDelta = -30;
		
		User user = new User();
		user.setUserId("1");
		user.setUserName("abc");
		Map params = new HashMap();
		params.put("sid", scheduleId);
		params.put("dayDelta", dayDelta);
		params.put("minuteDelta", minuteDelta);
		params.put("user", user);
		
		scheduleDao.scheduleResize(params);		
													
		end  = DateUtils.addDays(end, dayDelta);
		end  = DateUtils.addMinutes(end, minuteDelta);
		
		Schedule result = scheduleDao.get(scheduleId);
		assertEquals(start, result.getStartDate());
		assertEquals(end, result.getEndDate());
	}
	
	private int getCount(String si, List list) {
		int count = 0;
		for(int i=0, len=list.size(); i<len;  i++) {
			Map m = (Map) list.get(i);
			if(si.equals(m.get("scheduleId"))) {
				count = count + 1;
			}
		}	
		return count;
	}
}
