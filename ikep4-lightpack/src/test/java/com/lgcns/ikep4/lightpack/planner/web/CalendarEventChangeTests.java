package com.lgcns.ikep4.lightpack.planner.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.lightpack.BaseDaoTestCase;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.support.user.member.model.User;

public class CalendarEventChangeTests extends BaseDaoTestCase {

	@Autowired
	private CalendarService service;
	
	private final static String portalId = "P000001";

	private static String scheduleId = "200001";

	private final static String categoryId = "1";

	private final static String startDate = "20110222";

	private final static String endDate = "20110623";

	private final static String sdStartDate = "201102221030";

	private final static String sdEndDate = "201102231330";

	private final static String registerId = "111";

	private final static String registerName = "홍길동";

	private static String userId = registerId;

	private Schedule schedule;

	private Recurrences recurrence;

	private List<Participant> participants = new ArrayList<Participant>();

	private List<Alarm> alarms = new ArrayList<Alarm>();
	
	@Before
	public void create() {
		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 0,
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		recurrence = new Recurrences(scheduleId, "0", "", "", Schedule.toDate(startDate), Schedule.toDate(endDate),
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		
		List<Recurrences> recurrenceList = new ArrayList();
		recurrenceList.add(recurrence);
		participants.add(new Participant(scheduleId, userId, "0")); // 공개자
		participants.add(new Participant(scheduleId, "1002020", "1")); // 참석자
		alarms.add(new Alarm("11", scheduleId, "0", "60")); // 메일/60분
		alarms.add(new Alarm("22", scheduleId, "1", "30")); // SMS/30분
		
		schedule.setRecurrences(recurrenceList);
		schedule.setAlarmList(alarms);
		
		String id = service.create(schedule);
		assertNotNull(id);
		assertTrue(!scheduleId.equals(id));
		scheduleId = id;
	}
	
	// 전체 일정 이동
	@Ignore
	@Test
	public void moveRecurOptionWholeEvent() throws Exception {
		int dayDelta = 3, minuteDelta = -30;
		
		User user = new User();
		user.setUserId("user1");
		user.setUserName("abc");
		
		Map params = new HashMap();
		params.put("sid", scheduleId);
		params.put("dayDelta", dayDelta);
		params.put("minuteDelta", minuteDelta);
		params.put("user", user);
		params.put("updateType", 3);		// 전체일정 수정
		params.put("start", "20110312");
		params.put("end", "20110312");
		
		service.scheduleMove(params);
		assertEquals("success", params.get("res"));
//		
//		start = DateUtils.addDays(start, dayDelta);
//		start = DateUtils.addMinutes(start, minuteDelta);
//		
//		end  = DateUtils.addDays(end, dayDelta);
//		end  = DateUtils.addMinutes(end, minuteDelta);
//		
//		Schedule result = service.read(sid);
//		assertEquals(start, result.getStartDate());
//		assertEquals(end, result.getEndDate());
	}
}
