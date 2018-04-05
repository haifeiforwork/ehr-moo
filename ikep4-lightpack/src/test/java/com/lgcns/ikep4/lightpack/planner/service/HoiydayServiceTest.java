package com.lgcns.ikep4.lightpack.planner.service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.lightpack.BaseDaoTestCase;
import com.lgcns.ikep4.lightpack.planner.model.Holiday;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.support.user.member.model.User;

public class HoiydayServiceTest extends BaseDaoTestCase {

	@Autowired
	private CalendarService service;
	
	private final static String portalId = "P000001";
	
	private static String holidayDate = "20111010";
	
	private static String holidayDateStr = "20110210";
	
	private static User user = new User();
	
	private static String holidayId;
	
	private static Holiday holiday;
	
	@Before
	public void setup() {
		holiday = new Holiday("휴무일 테스트", Schedule.toDate(holidayDate));
		holiday.setNation("KOR");
		
		user.setUserId("user1");
		user.setUserName("사용자1");
		
		holidayId = service.createHoliday(user, portalId, holiday);
	}
	
	@Test
	public void cnrHoliday() {
		Holiday h = service.getHoliday(holidayId);
		assertEquals(holidayId, h.getHolidayId());
	}
	
	@Test
	public void holidayDateString() {
		Holiday h = new Holiday("휴무일 테스트", Schedule.toDate(holidayDate));
		h.setHolidayDateStr(holidayDateStr);
		
		String hid = service.createHoliday(user, portalId, h);
		Holiday nh = service.getHoliday(hid);
		
		assertEquals(Schedule.toDate(holidayDateStr), nh.getHolidayDate());
	}
	
	@Test
	public void getHolidayList() {
		List list = service.getHolidayList();
		assertTrue(list.size() >= 1);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getHolidayListByPeriod() {
		Long start = Schedule.toDate("20111001").getTime()/1000;
		Long end   = Schedule.toDate("20111031").getTime()/1000;
		List list = service.getHolidayList(start, end, "KOR");
		
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void getHoydayListByPeriodAndNation() {
		Long start = Schedule.toDate("20111001").getTime();
		Long end   = Schedule.toDate("20111031").getTime();
		List list = service.getHolidayList(start, end, "KOR");
		
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void updateHoliday() {
		holiday.setHolidayName("updateTest");
		service.updateHoliday(holiday);
		
		Holiday h = service.getHoliday(holidayId);
		assertEquals("updateTest", h.getHolidayName());
	}
	
	@Test
	public void updateHolidayStr() {
		holiday.setHolidayDate(null);
		holiday.setHolidayDateStr(holidayDateStr);
		service.updateHoliday(holiday);
		
		Holiday h = service.getHoliday(holidayId);
		assertEquals(Schedule.toDate(holidayDateStr), h.getHolidayDate());
	}
	
	@Test
	public void deleteHoliday() {
		String[] holidayIdList = {holidayId};
		
		service.deleteHoliday(holidayIdList);
		Holiday h = service.getHoliday(holidayId);
		
		assertNull(h);
	}
}
