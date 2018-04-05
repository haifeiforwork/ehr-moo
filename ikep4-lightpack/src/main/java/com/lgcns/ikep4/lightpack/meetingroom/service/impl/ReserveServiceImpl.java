/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.dao.ReserveDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.meetingroom.service.ReserveService;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.lightpack.planner.util.Utils;
import com.lgcns.ikep4.lightpack.planner.web.CalendarController;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Service
public class ReserveServiceImpl implements ReserveService {
	
	private static String[] PARSE_PATTERNS = new String[] {"yyyyMMddHHmm", "yyyy-MM-dd", "yyyyMMdd"};
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyy.MM.dd HH:mm");
	
	@Autowired
	private ReserveDao reserveDao;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private MailSendService mailSendService;
	
	public SearchResult<Map<String, Object>> list(MeetingRoomSearchCondition searchCondition) {
		
		Integer count = reserveDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = reserveDao.selectAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> listCar(MeetingRoomSearchCondition searchCondition) {
		
		Integer count = reserveDao.selectCarCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = reserveDao.selectAllCar(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	public List<Map<String, Object>> selectExists(Schedule schedule) {
		
		List<Map<String, Object>> list = null;
		
//		if(schedule.getRepeat() == 1) {
			list = new ArrayList<Map<String, Object>>();
			try {
				Date endDate = schedule.getEndDate();
				if(schedule.getRepeat() == 1 && schedule.getRecurrences().size() > 0) {
					for(Recurrences recurrence : schedule.getRecurrences()) {
						if(endDate.getTime() < recurrence.getEndDate().getTime()) {
							endDate = recurrence.getEndDate();
						}
					}
				}
				
				List<Map<String, Object>> eventList = scheduleService.selectMeetingRoomByReserveList(schedule.getMeetingRoomId(), schedule.getStartDate(), endDate, schedule.getScheduleId());	////schedule.getMandatorId()
				
				if(eventList.size() > 0) {
					List<Map<String, Object>> repeatEventList = scheduleService.getMeetingRoomRepeatList(schedule);
					if(repeatEventList.size() > 0) {
						for(Map<String, Object> event : eventList) {
							long sDate = DateUtils.parseDate(event.get("startDate").toString(), PARSE_PATTERNS).getTime();
							long eDate = DateUtils.parseDate(event.get("endDate").toString(), PARSE_PATTERNS).getTime();
							for(Map<String, Object> repeatEvent : repeatEventList) {
								long rsDate = ((Date)repeatEvent.get("startDate")).getTime();
								long reDate = ((Date)repeatEvent.get("endDate")).getTime();
								if( (rsDate <= sDate && reDate >= eDate) || 
									(rsDate <= sDate && reDate > sDate) ||
									(rsDate < eDate && reDate >= eDate) ||
									(rsDate >= sDate && reDate <= eDate)
								) {
									list.add(event);
									break;
								}
							}
						}
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
//		} else {
//			list = reserveDao.selectExists(schedule);
//		}
		
		return list;
	}

	
	public List<Map<String, Object>> selectCarExists(Schedule schedule) {
		
		List<Map<String, Object>> list = null;
		
//		if(schedule.getRepeat() == 1) {
			list = new ArrayList<Map<String, Object>>();
			try {
				Date endDate = schedule.getEndDate();
				if(schedule.getRepeat() == 1 && schedule.getRecurrences().size() > 0) {
					for(Recurrences recurrence : schedule.getRecurrences()) {
						if(endDate.getTime() < recurrence.getEndDate().getTime()) {
							endDate = recurrence.getEndDate();
						}
					}
				}
				
				List<Map<String, Object>> eventList = scheduleService.selectCartooletcByReserveList(schedule.getCartooletcId(), schedule.getStartDate(), endDate, schedule.getScheduleId());	////schedule.getMandatorId()
				
				if(eventList.size() > 0) {
					List<Map<String, Object>> repeatEventList = scheduleService.getCartooletcRepeatList(schedule);
					if(repeatEventList.size() > 0) {
						for(Map<String, Object> event : eventList) {
							long sDate = DateUtils.parseDate(event.get("startDate").toString(), PARSE_PATTERNS).getTime();
							long eDate = DateUtils.parseDate(event.get("endDate").toString(), PARSE_PATTERNS).getTime();
							for(Map<String, Object> repeatEvent : repeatEventList) {
								long rsDate = ((Date)repeatEvent.get("startDate")).getTime();
								long reDate = ((Date)repeatEvent.get("endDate")).getTime();
								if( (rsDate <= sDate && reDate >= eDate) || 
									(rsDate <= sDate && reDate > sDate) ||
									(rsDate < eDate && reDate >= eDate) ||
									(rsDate >= sDate && reDate <= eDate)
								) {
									list.add(event);
									break;
								}
							}
						}
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
//		} else {
//			list = reserveDao.selectExists(schedule);
//		}
		
		return list;
	}
	
	public String create(MeetingRoom arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public MeetingRoom read(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(MeetingRoom arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendReservationMail(String roomOrCar, String message , Schedule schedule, User sender, User manager) {
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("plannerCrudMailTemplate.vm");
		



		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		recip = new HashMap<String, String>();

		recip.put("email", manager.getMail());
		recip.put("name", manager.getUserName());

		recipients.add(recip);

		mail.setToEmailList(recipients);
		mail.setTitle(message+" "+schedule.getTitle());
		
		Map dataMap = new HashMap();
	
		
		dataMap.put("startStr", fdfDateTime.format(schedule.getStartDate()));
		dataMap.put("endStr", fdfDateTime.format(schedule.getEndDate()));		
		
		if(roomOrCar.equals("room")) {
			if(message.contains("승인요청")){
				dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/meetingroom/approve/approveList.do");
			}else{
				dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/meetingroom/reserve/myReserveList.do");
			}
		} else {
			if(message.contains("승인요청")){
				dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/meetingroom/approve/approveCarList.do");
			}else{
				dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/meetingroom/reserve/myCarReserveList.do");
			}
		}
		
		dataMap.put("message", message+" "+schedule.getTitle());	
		dataMap.put("roomApproveMail", "true");

		mailSendService.sendMail(mail, dataMap, sender);
	}
}
