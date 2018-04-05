package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
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
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.support.user.member.model.User;

@ContextConfiguration({ "classpath:/configuration/spring/context-dao.xml",
"classpath:/configuration/spring/context-service.xml" })
public class RecurrenceTests extends AbstractTransactionalJUnit4SpringContextTests {
	private final static String portalId = "P000001";
	private final static String categoryId = "1";
	private final static String registerId = "user6004";
	private final static String registerName = "사용자6004";
	private static String userId = registerId;
	private static String userName = registerName;
	private Schedule schedule;
	private Recurrences recurrence;
	private List<Recurrences> recurrences = new ArrayList<Recurrences>();
	private List<Participant> participants = new ArrayList<Participant>();
	private List<Alarm> alarms = new ArrayList<Alarm>();
	private static String startDate;
	private static String endDate;
	private static String sdStartDate;
	private static String sdEndDate;
	private static String rangeStart;
	private static String rangeEnd;
	private static String scheduleId = null;
	private static Map<String, Object> params;
	private static User user;
	
	private static String pattern = "yyyyMMddHHmm";
	private static String[] parsePattenrs = new String[] {"yyyyMMddHHmm"};
	private FastDateFormat fdf = FastDateFormat.getInstance(pattern);
	private FastDateFormat fdfDate = FastDateFormat.getInstance("yyyyMMdd");
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyyMMddHHmm");
	
	private String repeatType, repeatPeriod, repeatPeriodOption;
	private String prevStart = "201103031030";
	private String prevEnd   = "201103041330";
	private String start = "201103081030";  // 해당일자
	private String end   = "201103091330";	// 해당일자
	private String updateStart = "201103101030";  // 변경일자
	private String updateEnd   = "201103111330";	// 변경일자
	private String nextStart = "201103131030";
	private String nextEnd   = "201103141330";
	List<Map<String, Object>> ret;
	
	@Autowired
	private CalendarService service;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Before
	public void setup() {
		service.clearCall();
	}
	
	private void _setup(boolean skip) throws ParseException, SQLException {
		schedule = new Schedule(scheduleId, portalId, categoryId, registerId, registerName, "crud 테스트", "crud 테스트", 1,
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		
		participants.add(new Participant(scheduleId, userId, "0")); // 공개자
		participants.add(new Participant(scheduleId, "1002020", "1")); // 참석자
		
		alarms.add(new Alarm("11", scheduleId, "0", "60")); // 메일/60분
		alarms.add(new Alarm("22", scheduleId, "1", "30")); // SMS/30분
		
		schedule.setParticipantList(participants);
		schedule.setAlarmList(alarms);
		
		user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		
		params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("sid", scheduleId);
		
		Recurrences rep = new Recurrences(scheduleId, repeatType, repeatPeriod, repeatPeriodOption, Schedule.toDate(startDate), Schedule.toDate(endDate),
				Schedule.toDate(sdStartDate), Schedule.toDate(sdEndDate));
		
		recurrences.add(rep);
		schedule.setRecurrences(recurrences);
		scheduleId = service.create(schedule);
		
		assertNotNull(scheduleId);
		
		Map<String, Object> po = new HashMap<String, Object>();
		String res = null;
		po.put("user", user);
		po.put("sid", scheduleId);
		po.put("updateType", "1"); // 이번일정만
		po.put("dayDelta", 2);
		po.put("minuteDelta", 0);
		po.put("res", res);

		params.put("startDate", start);
		params.put("endDate", end);
		params.put("updateStart", updateStart);
		params.put("updateEnd", updateEnd);
		params.put("repeatType", repeatType);
		params.put("repeatPeriod", repeatPeriod);
		params.put("repeatPeriodOption", repeatPeriodOption);
		String rptSdate = startDate.substring(0, 4) + "-" + startDate.substring(4, 6)
			+ "-" + startDate.substring(6, 8);
		params.put("repeatStartDate", rptSdate);
		params.put("repeatEndDate", endDate);

		po.put("sco", params);
		service.scheduleMove(po);
		
		if(!skip) {		
			//String newSid = (String) po.get("res");
			//assertTrue(!"0".equals(newSid));
	
			// move후 테스트
			Schedule s1 = service.read(scheduleId);
			//Schedule s2 = service.read(newSid);
			
			List<Recurrences> rcList = s1.getRecurrences();
			if(prevEnd.length() > 0) {
			}
			assertTrue(checkRecurrence(rcList, startDate, prevEnd, sdStartDate, sdEndDate));
			assertTrue(checkRecurrence(rcList, nextStart, endDate, nextStart, nextEnd));
			
			//assertTrue(checkSchedule(s2, updateStart, updateEnd));
			
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("userId", userId);
			p.put("startDate", rangeStart);
			p.put("endDate",   rangeEnd);
			ret = scheduleService.selectByPeriod(p);
		}
	}

	@Test
	public void updateDaily() throws ParseException, SQLException {
		startDate = "20100221";
		endDate = "20120623";
		sdStartDate = "201002211030";
		sdEndDate = "201002221330";
		prevStart = "201103031030";
		prevEnd   = "201103041330";
		start = "201103081030";  // 해당일자
		end   = "201103091330";	// 해당일자
		updateStart = "201103101030";  // 변경일자
		updateEnd   = "201103111330";	// 변경일자
		nextStart = "201103131030";
		nextEnd   = "201103141330";
		
		repeatType = "1"; repeatPeriod = "5"; repeatPeriodOption = "";
		
		rangeStart = "20110301";
		rangeEnd = "20110331";
		
		_setup(false);
		
		// 2월21일부터 5일마다 반복
		
		// TODO: refactoring 적용
		//assertEquals(6, ret.size());
	}
	
	@Test
	public void updateDailyFirstEvent() throws ParseException, SQLException {
		startDate = "20110426";
		endDate = "20110510";
		sdStartDate = "201104261030";
		sdEndDate = "201104261330";
		start = "201104261030";  // 해당일자
		end   = "201104261330";	// 해당일자
		updateStart = "201104251030";  // 변경일자
		updateEnd   = "201104251330";	// 변경일자
		
		repeatType = "1"; repeatPeriod = "5"; repeatPeriodOption = "";
		
		_setup(true);
		
		Schedule sch = service.read(scheduleId);
		List<Recurrences> recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		assertEquals("201105011030", fdfDateTime.format(sch.getStartDate()));
		assertEquals("201105011330", fdfDateTime.format(sch.getEndDate()));
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110501", fdfDate.format(recur.getStartDate()));
		assertEquals("20110510", fdfDate.format(recur.getEndDate()));
		assertEquals("201105011030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201105011330", fdfDateTime.format(recur.getSdEndDate()));
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", updateStart.substring(0, 8));
		p.put("endDate",   updateEnd.substring(0, 8));
		ret = scheduleService.selectByPeriod(p);
		
		// TODO: refactoring 적용
//		assertEquals(1, ret.size());
//		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		
//		assertEquals(updateStart, nsch.get("startDate"));
//		assertEquals(updateEnd, nsch.get("endDate"));
//		assertNull(nsch.get("repeatType"));
	}
	
	@Test
	public void updateDailyLastEvent() throws ParseException, SQLException {
		startDate = "20110426";
		endDate = "20110510";
		sdStartDate = "201104261030";
		sdEndDate = "201104261330";
		start = "201105061030";  // 해당일자
		end   = "201105061330";	// 해당일자
		updateStart = "201104251030";  // 변경일자
		updateEnd   = "201104251330";	// 변경일자
		
		repeatType = "1"; repeatPeriod = "5"; repeatPeriodOption = "";
		
		_setup(true);
		
		Schedule sch = service.read(scheduleId);
		List<Recurrences> recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		assertEquals("201104261030", fdfDateTime.format(sch.getStartDate()));
		assertEquals("201104261330", fdfDateTime.format(sch.getEndDate()));
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals(startDate, fdfDate.format(recur.getStartDate()));
		assertEquals("20110501", fdfDate.format(recur.getEndDate()));
		assertEquals(sdStartDate, fdfDateTime.format(recur.getSdStartDate()));
		assertEquals(sdEndDate, fdfDateTime.format(recur.getSdEndDate()));
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", updateStart.substring(0, 8));
		p.put("endDate",   updateEnd.substring(0, 8));
		ret = scheduleService.selectByPeriod(p);
		
		// TODO: refactoring 적용
//		assertEquals(1, ret.size());
//		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		
//		assertEquals(updateStart, nsch.get("startDate"));
//		assertEquals(updateEnd, nsch.get("endDate"));
//		assertNull(nsch.get("repeatType"));
	}
	@Ignore
	@Test
	public void updateDailySoleEvent() throws ParseException, SQLException {
		startDate = "20110501";
		endDate = "20110501";
		sdStartDate = "201105011030";
		sdEndDate = "201105011330";
		start = "201105011030";  // 해당일자
		end   = "201105011330";	// 해당일자
		updateStart = "201105251030";  // 변경일자
		updateEnd   = "201105251330";	// 변경일자
		
		repeatType = "1"; repeatPeriod = "5"; repeatPeriodOption = "";
		
		_setup(true);
		
		Schedule sch = service.read(scheduleId);
		List<Recurrences> recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		assertEquals(updateStart, fdfDateTime.format(sch.getStartDate()));
		assertEquals(updateEnd, fdfDateTime.format(sch.getEndDate()));
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals(updateStart.substring(0, 8), fdfDate.format(recur.getStartDate()));
		assertEquals(updateEnd.substring(0, 8), fdfDate.format(recur.getEndDate()));
		assertEquals(updateStart, fdfDateTime.format(recur.getSdStartDate()));
		assertEquals(updateEnd, fdfDateTime.format(recur.getSdEndDate()));
	}
	
	@Test
	public void weeklyDaily_single() throws ParseException, SQLException {
		startDate = "20110307";
		endDate = "20110623";
		sdStartDate = "201103071030";
		sdEndDate = "201103071330";
		prevStart = "201103211030";
		prevEnd   = "201103211330";
		start = "201104041030";   //해당일자
		end   = "201104041330";	 //해당일자
		updateStart = "201104121030";   //변경일자
		updateEnd   = "201104121330";	 //변경일자
		nextStart = "201104181030";
		nextEnd   = "201104181330";
		
		// 2주마다 월요일
		repeatType = "2"; repeatPeriod = "2"; repeatPeriodOption = "2";
		
		rangeStart = "20110301";
		rangeEnd = "20110430";
		
		_setup(false);
		
		// 2월21일부터 5일마다 반복
		
		//assertEquals(4, ret.size());
		assertTrue(ret.size() >= 4);
	}
	

	@Test
	public void weeklyDaily_multiFirstEvent() throws ParseException, SQLException {
		startDate = "20110407";
		endDate = "20110710";
		sdStartDate = "201104071030";
		sdEndDate = "201104071330";
		start = "201104071030";  // 해당일자
		end   = "201104071330";	// 해당일자
		updateStart = "201104141030";  // 변경일자
		updateEnd   = "201104141330";	// 변경일자
		
		// 2주마다 월, 목, 토 요일
		repeatType = "2"; repeatPeriod = "2"; repeatPeriodOption = "2,5,7";
		
		_setup(true);
		
		Schedule sch = service.read(scheduleId);
		List<Recurrences> recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		assertEquals("201104091030", fdfDateTime.format(sch.getStartDate()));
		assertEquals("201104091330", fdfDateTime.format(sch.getEndDate()));
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals("20110409", fdfDate.format(recur.getStartDate()));
		assertEquals(endDate, fdfDate.format(recur.getEndDate()));
		assertEquals("201104091030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201104091330", fdfDateTime.format(recur.getSdEndDate()));
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", updateStart.substring(0, 8));
		p.put("endDate",   updateEnd.substring(0, 8));
		ret = scheduleService.selectByPeriod(p);
		
		// TODO: refactoring 적용
//		assertEquals(1, ret.size());
//		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		
//		assertEquals(updateStart, nsch.get("startDate"));
//		assertEquals(updateEnd, nsch.get("endDate"));
//		assertNull(nsch.get("repeatType"));
	}
	
	@Test
	public void weeklyDaily_multiLastEvent() throws ParseException, SQLException {
		startDate = "20110407";
		endDate = "20110423";
		sdStartDate = "201104071030";
		sdEndDate = "201104071330";
		start = "201104231030";  // 해당일자
		end   = "201104231330";	// 해당일자
		updateStart = "201104141030";  // 변경일자
		updateEnd   = "201104141330";	// 변경일자
		
		// 2주마다 월, 목, 토 요일
		repeatType = "2"; repeatPeriod = "2"; repeatPeriodOption = "2,5,7";
		
		_setup(true);
		
		Schedule sch = service.read(scheduleId);
		List<Recurrences> recList = sch.getRecurrences();
		assertEquals(1, recList.size());
		assertEquals(sdStartDate, fdfDateTime.format(sch.getStartDate()));
		assertEquals(sdEndDate, fdfDateTime.format(sch.getEndDate()));
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals(startDate, fdfDate.format(recur.getStartDate()));
		assertEquals("20110421", fdfDate.format(recur.getEndDate()));
		assertEquals(sdStartDate, fdfDateTime.format(recur.getSdStartDate()));
		assertEquals(sdEndDate, fdfDateTime.format(recur.getSdEndDate()));
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", updateStart.substring(0, 8));
		p.put("endDate",   updateEnd.substring(0, 8));
		ret = scheduleService.selectByPeriod(p);
		
		// TODO: refactoring 적용
//		assertEquals(1, ret.size());
//		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		
//		assertEquals(updateStart, nsch.get("startDate"));
//		assertEquals(updateEnd, nsch.get("endDate"));
//		assertNull(nsch.get("repeatType"));
	}
	
	@Test
	public void weeklyDaily_multiMiddleEvent() throws ParseException, SQLException {
		startDate = "20110407";
		endDate = "20110423";
		sdStartDate = "201104071030";
		sdEndDate = "201104071330";
		start = "201104211030";  // 해당일자
		end   = "201104211330";	// 해당일자
		updateStart = "201104201030";  // 변경일자
		updateEnd   = "201104201330";	// 변경일자
		
		// 2주마다 월, 목, 토 요일
		repeatType = "2"; repeatPeriod = "2"; repeatPeriodOption = "2,5,7";
		
		_setup(true);
		
		Schedule sch = service.read(scheduleId);
		assertEquals(sdStartDate, fdfDateTime.format(sch.getStartDate()));
		assertEquals(sdEndDate, fdfDateTime.format(sch.getEndDate()));
		
		List<Recurrences> recList = sch.getRecurrences();
		assertEquals(2, recList.size());
		
		Recurrences recur = (Recurrences) recList.get(0);
		assertEquals(startDate, fdfDate.format(recur.getStartDate()));
		assertEquals("20110418", fdfDate.format(recur.getEndDate()));
		assertEquals(sdStartDate, fdfDateTime.format(recur.getSdStartDate()));
		assertEquals(sdEndDate, fdfDateTime.format(recur.getSdEndDate()));
		
		recur = (Recurrences) recList.get(1);
		assertEquals("20110423", fdfDate.format(recur.getStartDate()));
		assertEquals("20110423", fdfDate.format(recur.getEndDate()));
		assertEquals("201104231030", fdfDateTime.format(recur.getSdStartDate()));
		assertEquals("201104231330", fdfDateTime.format(recur.getSdEndDate()));
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", updateStart.substring(0, 8));
		p.put("endDate",   updateEnd.substring(0, 8));
		ret = scheduleService.selectByPeriod(p);
		
		// TODO: refactoring 적용
//		assertEquals(1, ret.size());
//		Map<String, Object> nsch = (Map<String, Object>) ret.get(0);
		
//		assertEquals(updateStart, nsch.get("startDate"));
//		assertEquals(updateEnd, nsch.get("endDate"));
//		assertNull(nsch.get("repeatType"));
	}

	@Test // n월마다 특정일자
	public void updateMonthlyType_A() throws ParseException, SQLException {
		startDate = "20100221";
		endDate = "20120623";
		sdStartDate = "201002211030";
		sdEndDate = "201002221330";
		prevStart = "201012211030";
		prevEnd   = "201012211330";
		start = "201102211030";  // 해당일자
		end   = "201102211330";	// 해당일자
		updateStart = "201102231030";  // 변경일자
		updateEnd   = "201102231330";	// 변경일자
		nextStart = "201104211030";
		nextEnd   = "201104211330";
		repeatType = "3"; repeatPeriod = "2"; repeatPeriodOption = "a,21";
		
		rangeStart = "20101201";
		rangeEnd = "20110430";
		
		_setup(false);
		
		// 2개월마다 21일
		
		//assertEquals(3, ret.size());
		assertTrue(ret.size() >= 3);
	}

	@Test // n월마다 몇번째 요일
	public void updateMonthlyType_B() throws ParseException, SQLException {
		prevStart = "201105261030";
		prevEnd   = "201105261330";
		start = "201107281030";  // 해당일자
		end   = "201107281330";	// 해당일자
		updateStart = "201107251030";  // 변경일자
		updateEnd   = "201107251330";	// 변경일자
		nextStart = "201109221030";
		nextEnd   = "201109221330";
		sdStartDate = "201103241030";
		sdEndDate   = "201103241330";
		startDate = "20110324";
		endDate   = "20120131";
		
		repeatType = "3"; repeatPeriod = "2"; repeatPeriodOption = "b,4,5";
		
		rangeStart = "20110301";
		rangeEnd = "20111130";
		
		_setup(false);
		
		
		// 2개월마다 4번째 목요일
		
		//assertEquals(5, ret.size());
		assertTrue(ret.size() >= 5);
	}
	
	@Test // n월마다 마지막 요일
	public void updateMonthlyType_C() throws ParseException, SQLException {
		prevStart = "201105251030";
		prevEnd   = "201105251330";
		start = "201107271030";  // 해당일자
		end   = "201107271330";	// 해당일자
		updateStart = "201107201030";  // 변경일자
		updateEnd   = "201107201330";	// 변경일자
		nextStart = "201109281030";
		nextEnd   = "201109281330";
		sdStartDate = "201103301030";
		sdEndDate   = "201103301330";
		startDate = "20110330";
		endDate   = "20120131";
		
		repeatType = "3"; repeatPeriod = "2"; repeatPeriodOption = "c,4";
		
		rangeStart = "20110701";
		rangeEnd = "20111130";
		
		_setup(false);
		
		
		// 2개월마다 마지막 수요일
		
		assertTrue(ret.size() >= 3);
	}

	@Test // n월마다 마지막 일자
	public void updateMonthlyType_D() throws ParseException, SQLException {
		prevStart = "201105311030";
		prevEnd   = "201105311330";
		start = "201107311030";  // 해당일자
		end   = "201107311330";	// 해당일자
		updateStart = "201107301030";  // 변경일자
		updateEnd   = "201107301330";	// 변경일자
		nextStart = "201109301030";
		nextEnd   = "201109301330";
		sdStartDate = "201103311030";
		sdEndDate   = "201103311330";
		startDate = "20110331";
		endDate   = "20120131";
		
		repeatType = "3"; repeatPeriod = "2"; repeatPeriodOption = "d";
		
		rangeStart = "20110401";
		rangeEnd   = "20111031";
		
		_setup(false);
		
		
		// 2개월마다 마지막 수요일
		//assertEquals(3, ret.size());
		assertTrue(ret.size() >= 3);
	}
	
	@Test // n 년마다 특정일자
	public void updateYearlyType_A() throws ParseException, SQLException {
		prevStart = "201104101130";
		prevEnd   = "201104101330";
		start = "201204101130";  // 해당일자
		end   = "201204101330";	// 해당일자
		updateStart = "201204111130";  // 변경일자
		updateEnd   = "201204111330";	// 변경일자
		nextStart = "201304101130";
		nextEnd   = "201304101330";
		
		sdStartDate = "201004101130";
		sdEndDate 	= "201004101330";
		startDate = "20100410";
		endDate   = "20190410";
		
		repeatType = "4"; repeatPeriod = "1"; repeatPeriodOption = "a,10,4";
		
		rangeStart = "20110101";
		rangeEnd   = "20140430";
		
		_setup(false);
		
		// 2개월마다 21일
		
		//assertEquals(4, ret.size());
		assertTrue(ret.size() >= 4);
	}
	
	@Test // n 년마다 몇월 몇번째 요일
	public void updateYearlyType_B() throws ParseException, SQLException {
		prevStart = "201104051130";
		prevEnd   = "201104051330";
		start = "201204101130";  // 해당일자
		end   = "201204101330";	// 해당일자
		updateStart = "201204111130";  // 변경일자
		updateEnd   = "201204111330";	// 변경일자
		nextStart = "201304021130";
		nextEnd   = "201304021330";
		
		sdStartDate = "201004061130";
		sdEndDate 	= "201004061330";
		startDate = "20100406";
		endDate   = "20190410";
		
		// 매년 4월 첫번째 화요일
		repeatType = "4"; repeatPeriod = "1"; repeatPeriodOption = "b,1,3,4";
		
		rangeStart = "20110401";
		rangeEnd   = "20110430";
		
		_setup(false);
		
		// 2개월마다 21일
		// TODO: refactoring 적용
		//assertEquals(1, ret.size());
//		assertTrue(ret.size() >= 1);
	}

	@Test // n 년마다 몇월 마지막 요일
	public void updateYearlyType_C() throws ParseException, SQLException {
		prevStart = "201104261130";
		prevEnd   = "201104261330";
		start = "201204241130";  // 해당일자
		end   = "201204241330";	// 해당일자
		updateStart = "201204111130";  // 변경일자
		updateEnd   = "201204111330";	// 변경일자
		nextStart = "201304301130";
		nextEnd   = "201304301330";
		
		sdStartDate = "201004271130";
		sdEndDate 	= "201004271330";
		startDate = "20100427";
		endDate   = "20190410";
		
		// 매년 4월 마지막 화요일
		repeatType = "4"; repeatPeriod = "1"; repeatPeriodOption = "c,3,4";
		
		rangeStart = "20110401";
		rangeEnd   = "20110430";
		
		_setup(false);
		
		// 2개월마다 21일
		// TODO: refactoring 적용
		//assertEquals(1, ret.size());
//		assertTrue(ret.size() >= 1);
	}
	
	@Test // n 년마다 몇월 마지막 날자
	public void updateYearlyType_D() throws ParseException, SQLException {
		prevStart = "201104301130";
		prevEnd   = "201104301330";
		start = "201204301130";  // 해당일자
		end   = "201204301330";	// 해당일자
		updateStart = "201204111130";  // 변경일자
		updateEnd   = "201204111330";	// 변경일자
		nextStart = "201304301130";
		nextEnd   = "201304301330";
		
		sdStartDate = "201004301130";
		sdEndDate 	= "201004301330";
		startDate = "20100430";
		endDate   = "20190410";
		
		// 매년 4월 마지막 날자
		repeatType = "4"; repeatPeriod = "1"; repeatPeriodOption = "d,4";
		
		rangeStart = "20110401";
		rangeEnd   = "20110430";
		
		_setup(false);
		
		// 2개월마다 21일
		// TODO: refactoring 적용
		//assertEquals(1, ret.size());
//		assertTrue(ret.size() >= 1);
	}
	
	@SuppressWarnings("rawtypes")
	public boolean exists(String[] exp, List src) {
		boolean ret = false;
		Map item;
		for(int i=0, len=src.size(); i<len; i++) {
			item = (Map) src.get(i);
			if(exp[0].equals(item.get("startDate")) 
					&& exp[1].equals(item.get("endDate"))) {
				return true;
			}
		}
		return ret;
	}
	
	public boolean checkRecurrence(List<Recurrences> rlist, String rp_start, String rp_end, String sd_start, String sd_end) {
		boolean ret = false;
		for (Recurrences recurrences : rlist) {
			String rps = fdf.format(recurrences.getStartDate());
			String rpe = fdf.format(recurrences.getEndDate());
			String sds = fdf.format(recurrences.getSdStartDate());
			String sde = fdf.format(recurrences.getSdEndDate());
			if( rps.substring(0, 8).equals(rp_start.substring(0, 8))
				&& rpe.substring(0, 8).equals(rp_end.substring(0, 8))
				&& sds.equals(sd_start) && sde.equals(sd_end) ) {
				ret = true;
				break;
			}
		}
		return ret;
	}
	
	public boolean checkSchedule(Schedule scd, String start, String end) {
		boolean ret = false;
		String s = fdf.format(scd.getStartDate());
		String e = fdf.format(scd.getEndDate());
		
		if(s.equals(start) && e.equals(end)) {
			ret = true;
		}
		return ret;
	}
}
