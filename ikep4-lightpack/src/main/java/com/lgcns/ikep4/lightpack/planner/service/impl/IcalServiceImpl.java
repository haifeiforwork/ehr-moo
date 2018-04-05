/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.IcalService;

/**
 * ICal4j 연동 서비스 구현
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: IcalServiceImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Service("icalService")
public class IcalServiceImpl implements IcalService {
	
	@Autowired
	CalendarService calendarService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.IcalService#importIcal(java.io.InputStream)
	 */
//	public boolean importIcal(User user, String portalId, InputStream is) {
//		boolean res = false;
//		ICalendar ical = ICalUtil.readICalendar(is);
//		List<IEvent> ievents = ical.getIeventList();
//		
//		Schedule schedule;
//		Recurrences recurrence;
//		String scheduleId = null;
//		Date now = new Date();
//		
//		for (Iterator iterator = ievents.iterator(); iterator.hasNext();) {
//			IEvent iEvent = (IEvent) iterator.next();
//			schedule = new Schedule();
//			schedule.setStartDate(iEvent.getDtstart());
//			schedule.setEndDate(iEvent.getDtend());
//			schedule.setTitle(iEvent.getSummary());
//			schedule.setContents(iEvent.getDescription());
//			schedule.setRegistDate(now);
//			schedule.setRegisterId(user.getUserId());
//			schedule.setRegisterName(user.getUserName());
//			schedule.setUpdateDate(now);
//			schedule.setUpdaterId(user.getUserId());
//			schedule.setUpdaterName(user.getUserName());
//			schedule.setPortalId(portalId);
//			schedule.setCategoryId(PlannerCategory.DEFAULT_CATEGORY_ID);
//			
//			IRecur irecur = iEvent.getIrecur();
//			if(irecur != null) {
//				recurrence = convertIEventToRecurrency(iEvent);
//			}
//			
//			
//			res = false;
//			scheduleId = null;
//			
//			scheduleId = calendarService.create(schedule);
//			if(scheduleId != null) {
//				res = true;
//			} 
//		}
//		
//		return res;
//	}
	
//	private Recurrences convertIEventToRecurrency(IEvent iEvent) {
//		IRecur irecur = iEvent.getIrecur();
//		Recurrences recur = new Recurrences();
//		
//		String frequency = irecur.getFrequency();
//		String roption = "";
//		if(frequency.equals("DAILY")) {
//			recur.setRepeatType("1");
//		} else if(frequency.equals("WEEKLY")) {
//			recur.setRepeatType("2");
//			WeekDayList dayList = irecur.getDayList();
//			List<Integer> buff = new ArrayList<Integer>();
//			for (Iterator iterator = dayList.iterator(); iterator.hasNext();) {
//				WeekDay weekday = (WeekDay) iterator.next();
//				buff.add(WeekDay.getCalendarDay(weekday));
//			}
//			roption =StringUtils.join(buff, ",");
//		} else if(frequency.equals("MONTHLY")) {
//			recur.setRepeatType("3");
//			
//		} else if(frequency.equals("YEARLY")) {
//			recur.setRepeatType("4");
//		} else {
//			//throw new Exception();
//		}
//		recur.setRepeatPeriod(String.valueOf(irecur.getCount()));
//		recur.setStartDate(DateUtils.truncate(iEvent.getDtstart(), Calendar.DATE));
//		recur.setEndDate(DateUtils.truncate(irecur.getUntil(), Calendar.DATE));
//		
//		return recur;
//	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.IcalService#getICalendarList(java.lang.String, long, long)
	 */
//	public List<ICalendar> getICalendarList(String userId, long start, long end) {
//		FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
//		Map<String, Object> params = new HashMap<String, Object>();
//
//		params.put("userId", userId);
//		params.put("startDate", fdf.format(new Date(start)));
//		params.put("endDate", fdf.format(new Date(end)));
//		List<Map<String, Object>> cals = calendarService.selectByPeriod(params);
//		
//		return null;
//	}
// 
//	public ICalendar ScheduleToICalendar(Schedule schedule) {
//		// Calendar 셋팅
//		ICalendar icalendar = new ICalendar();
//
//		// Calendar의 Event 셋팅
//		List<IEvent> ieventList = new ArrayList<IEvent>();
//		IEvent ievent = new IEvent();
//
//		ievent.setUid(schedule.getScheduleId());
//		ievent.setSummary(schedule.getTitle());
//		ievent.setDtstart(new net.fortuna.ical4j.model.DateTime(schedule.getStartDate()));
//		ievent.setDtend(new net.fortuna.ical4j.model.DateTime(schedule.getEndDate()));
//
//		ievent.setOrganizer(schedule.getRegisterName());
//		
//		List<com.lgcns.ikep4.lightpack.planner.model.Participant> attendeeList = schedule.getParticipantList();
//		com.lgcns.ikep4.lightpack.planner.model.Participant attendee;
//		for(int i=0, len=attendeeList.size(); i<len; i++) {
//			attendee = attendeeList.get(i);
//			ievent.addAttendeeList(attendee.getTargetUserId());
//		}
//
//		// Event의 반복설정 셋팅
////		IRecur irecur = new IRecur();
////		irecur.setFrequency(Recur.WEEKLY);
////		irecur.setInterval(new Integer(1));
////
////		WeekDayList dayList = new WeekDayList();
////		dayList.add(WeekDay.SU);
////		dayList.add(WeekDay.MO);
////		irecur.setDayList(dayList);
////
////		ievent.setIrecur(irecur);
//
//		// Event의 알람 셋팅
////		List<IAlarm> ialarmList = new ArrayList<IAlarm>();
////		IAlarm ialarm = new IAlarm();
////
////		ialarm.setAction("EMAIL");
////		ialarm.setSummary("email noti...");
////		ialarm.setDescription("email noti...");
////		ialarm.addAttendeeList(new String("Mailto:B@example5.com"));
////		ialarm.addAttendeeList(new String("Mailto:B@example6.com"));
//
////		IDur idur = new IDur();
////		idur.setDays(0);
////		idur.setHours(3);
////		idur.setMinutes(0);
////		idur.setSeconds(0);
//
////		ialarm.setIdur(idur);
////
////		ialarmList.add(ialarm);
////
////		ievent.setIalarmList(ialarmList);
//
//		ieventList.add(ievent);
//
//		icalendar.setIeventList(ieventList);
//
//		return icalendar;
//	}
}
