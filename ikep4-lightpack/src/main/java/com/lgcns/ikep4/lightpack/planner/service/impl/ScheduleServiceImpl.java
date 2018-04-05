/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao;
import com.lgcns.ikep4.lightpack.planner.model.FavoriteTarget;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.search.ScheduleSearchCondition;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.lightpack.planner.util.Utils;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.util.excel.StringUtils;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.GroupDetail;
import com.lgcns.ikep4.util.model.ScheduleDetail;


/**
 * 일정관리 서비스 구현
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: ScheduleServiceImpl.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
@Service("scheduleService")
public class ScheduleServiceImpl extends GenericServiceImpl<Schedule, String> implements ScheduleService {
	
	private static final String PATTERN_DATETIME = "yyyyMMddHHmm";
	private static String[] PARSE_PATTERNS = new String[] {"yyyyMMddHHmm", "yyyy-MM-dd", "yyyyMMdd"};
	private static FastDateFormat fdf = FastDateFormat.getInstance(PATTERN_DATETIME);
	
	private static final int REPEAT_TYPE_DAILY = 1;
	private static final int REPEAT_TYPE_WEEKLY = 2;
	private static final int REPEAT_TYPE_MONTHLY = 3;
	private static final int REPEAT_TYPE_YEARLY = 4;
	
	@Autowired
	private ScheduleDao scheduleDao;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private ACLService aclService;
	
	@Autowired
	private IdgenService idgenService;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriod(java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<Map<String, Object>> selectByPeriod(String userId, Date startDate, Date endDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("userId", userId);
		params.put("startDate", start);
		params.put("endDate", end);

		List<Map<String, Object>> rowList = scheduleDao.selectByPeriod(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodHb(String userId,String hbName, Date startDate, Date endDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("userId", userId);
		params.put("startDate", start);
		params.put("endDate", end);
		params.put("hbName", hbName);

		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodHb(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodNew(String sessionUserId,String userId, Date startDate, Date endDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("sessionUserId", sessionUserId);
		params.put("userId", userId);
		params.put("startDate", start);
		params.put("endDate", end);

		//List<Map<String, Object>> rowList = scheduleDao.selectByPeriod(params);
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodNew(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodNewHb(String sessionUserId,String hbName,String userId, Date startDate, Date endDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("sessionUserId", sessionUserId);
		params.put("userId", userId);
		params.put("startDate", start);
		params.put("endDate", end);
		params.put("hbName", hbName);

		//List<Map<String, Object>> rowList = scheduleDao.selectByPeriod(params);
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodNewHb(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		return eventList;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriod(java.lang.String, long, long)
	 */
	public List<Map<String, Object>> selectByPeriod(String userId, long startDate, long endDate) throws ParseException {
		return selectByPeriod(userId, new Date(startDate), new Date(endDate));
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriod(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> selectByPeriod(String userId, String startDate, String endDate)
			throws ParseException {
		Date start = DateUtils.parseDate(startDate, PARSE_PATTERNS);
		Date end = DateUtils.parseDate(endDate, PARSE_PATTERNS);
		return selectByPeriod(userId, start, end);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodByMySchedule(java.lang.String, long, long)
	 */
	public List<Map<String, Object>> selectByPeriodByMySchedule(String userId, long startDate, long endDate)
			throws ParseException {
		List<Map<String, Object>> eventList = selectByPeriod(userId, new Date(startDate), new Date(endDate));
		setAuthoEvent(userId, eventList);
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodByMyScheduleHb(String userId, String hbName,long startDate, long endDate)
		throws ParseException {
		List<Map<String, Object>> eventList = selectByPeriodHb(userId,hbName, new Date(startDate), new Date(endDate));
		setAuthoEvent(userId, eventList);
		return eventList;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodByTargetUser(java.lang.String, java.lang.String, long, long)
	 */
	public List<Map<String, Object>> selectByPeriodByTargetUser(String userId, String targetUserId, long startDate,
			long endDate) throws ParseException {
		//List<Map<String, Object>> eventList = selectByPeriod(targetUserId, new Date(startDate), new Date(endDate));
		List<Map<String, Object>> eventList = selectByPeriodNew(userId,targetUserId, new Date(startDate), new Date(endDate));
		setAuthoEvent(userId, eventList);
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodByTargetUserHb(String userId,String hbName, String targetUserId, long startDate,
			long endDate) throws ParseException {
		//List<Map<String, Object>> eventList = selectByPeriod(targetUserId, new Date(startDate), new Date(endDate));
		List<Map<String, Object>> eventList = selectByPeriodNewHb(userId,hbName,targetUserId, new Date(startDate), new Date(endDate));
		setAuthoEvent(userId, eventList);
		return eventList;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#readMyTodaySchedule(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> readMyTodaySchedule(String userId) throws ParseException {
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date today = DateUtils.truncate(clientNow, Calendar.DATE);
		List<Map<String, Object>> eventList = selectByPeriod(userId, today, today);
		Collections.sort(eventList, new EventCompare());
		Date sd, ed;
		for (Map<String, Object> event : eventList) {
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		return eventList;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodForWorkspace(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> selectByPeriodForWorkspace(String workspaceId, Date startDate, Date endDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("workspaceId", workspaceId);
		params.put("startDate", start);
		params.put("endDate", end);

		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForWorkspace(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);

		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForWorkspaceHb(String workspaceId,String hbName, Date startDate, Date endDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("workspaceId", workspaceId);
		params.put("startDate", start);
		params.put("endDate", end);
		params.put("hbName", hbName);

		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForWorkspaceHb(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);

		return eventList;
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodForWorkspace(java.lang.String, long, long)
	 */
	public List<Map<String, Object>> selectByPeriodForWorkspace(String workspaceId, long startDate, long endDate) throws ParseException {
		return selectByPeriodForWorkspace(workspaceId, new Date(startDate), new Date(endDate));
	}
	

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodForWorkspace(java.lang.String, java.lang.String, long, long)
	 */
	public List<Map<String, Object>> selectByPeriodForWorkspace(String userId, String workspaceId, long start, long end)
			throws ParseException {
		List<Map<String, Object>> eventList = selectByPeriodForWorkspace(workspaceId, new Date(start), new Date(end));
		
		setAuthoEvent(userId, eventList);
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForWorkspaceHb(String userId,String hbName, String workspaceId, long start, long end)
	throws ParseException {
	List<Map<String, Object>> eventList = selectByPeriodForWorkspaceHb(workspaceId,hbName, new Date(start), new Date(end));
	
	setAuthoEvent(userId, eventList);
	return eventList;
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodForWorkspace(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> selectByPeriodForCompany(String workAreaName, Date startDate, Date endDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("workAreaName", workAreaName);
		params.put("startDate", start);
		params.put("endDate", end);

		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForCompany(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);

		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForCompanyPortlet(String workAreaName, Date startDate, Date endDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("workAreaName", workAreaName);
		params.put("startDate", start);
		params.put("endDate", end);

		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForCompanyPortlet(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);

		return eventList;
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodForWorkspace(java.lang.String, long, long)
	 */
	public List<Map<String, Object>> selectByPeriodForCompany(String workAreaName, long startDate, long endDate) throws ParseException {
		return selectByPeriodForCompany(workAreaName, new Date(startDate), new Date(endDate));
	}
	

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodForWorkspace(java.lang.String, java.lang.String, long, long)
	 */
	public List<Map<String, Object>> selectByPeriodForCompany(String userId, String workAreaName, long start, long end)
			throws ParseException {
		List<Map<String, Object>> eventList = selectByPeriodForCompany(workAreaName, new Date(start), new Date(end));
		setAuthoEvent(userId, eventList);
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForCompanyPortlet(String userId, String workAreaName, long start, long end)
		throws ParseException {
	List<Map<String, Object>> eventList = selectByPeriodForCompanyPortlet(workAreaName, new Date(start), new Date(end));
	setAuthoEvent(userId, eventList);
	return eventList;
	}

	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#readWorkspaceSchedule(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> readWorkspaceSchedule(String workspaceId, String startDate, String viewUrl) throws ParseException {
		Date start = DateUtils.parseDate(startDate, PARSE_PATTERNS);
		List<Map<String, Object>> eventList = selectByPeriodForWorkspace(workspaceId, start, start);
		Collections.sort(eventList, new EventCompare());
		Date sd, ed;
		String url = viewUrl, scheduleId;
		for (Map<String, Object> event : eventList) {
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			scheduleId = (String) event.get("scheduleId");
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
			event.put("title", (String) event.get("title"));
			event.put("returl", url+scheduleId);
		}
		return eventList;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#readUsersSchedule(java.lang.String[], java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> readUsersSchedule(String[] users, Date startDate, Date endDate)
			throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("users", users);
		params.put("startDate", start);
		params.put("endDate", end);

		List<Map<String, Object>> rowList = scheduleDao.readUsersSchedule(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);

		return eventList;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#readUsersSchedule(java.lang.String[], long, long)
	 */
	public List<Map<String, Object>> readUsersSchedule(String[] users, long startDate, long endDate) throws ParseException {
		return readUsersSchedule(users, new Date(startDate), new Date(endDate));
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#readUsersSchedule(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> readUsersSchedule(Map<String, Object> params) throws ParseException {
		List<String> userList = (List<String>) params.get("users");
		
		String[] users = userList.toArray(new String[0]);
		long start = (Long) params.get("startDate");
		long end = (Long) params.get("endDate");
		List<Map<String, Object>> eventList =  readUsersSchedule(users, new Date(start), new Date(end));
		setAuthoEvent(users, eventList);
		return eventList;
	}


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#getUserScheduleCount(java.lang.String, java.util.Date)
	 */
	public int getUserScheduleCount(String userId, Date startDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 

		params.put("userId", userId);
		params.put("startDate", start);
		params.put("endDate", start);
		
		int countEvent = scheduleDao.getUserNormalEventCount(params);
		List<Map<String, Object>> rowList = scheduleDao.readUserRecurrence(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, start, true);
		return countEvent + eventList.size();
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodByTrustee(java.lang.String, java.lang.String, long, long)
	 */
	public List<Map<String, Object>> selectByPeriodByTrustee(String userId, String mandatorId, long startLong,
			long endLong) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date startDate = new Date(startLong);
		Date endDate = new Date(endLong);
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("userId", mandatorId);
		params.put("startDate", start);
		params.put("endDate", end);

		List<Map<String, Object>> rowList = scheduleDao.selectByPeriod(params);
		convertToClientTimezone(rowList);
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		setAuthoEvent(mandatorId, eventList);
		return eventList;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#getAlarmTargetList(java.util.Date, int)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAlarmTargetList(Date jobTime, int interval) throws ParseException {
		Date jobDateTime = DateUtils.truncate(jobTime, Calendar.MINUTE);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobDate", DateUtils.truncate(jobTime, Calendar.DATE));
		params.put("jobDateTime", jobDateTime);
		params.put("interval", interval);
		
		List alarmInfoList = scheduleDao.getAlarmTargetNormalList(params);
		List rowList = scheduleDao.getAlarmTargetRecurrenceEvents(params);
		Map alarmInfo;
		List<Map<String, Object>> eventList = normalizeEvent(rowList, jobTime, jobTime, true);
		for (Map event : eventList) {
			event.put("jobDateTime", jobDateTime);
			event.put("interval", interval);
			alarmInfo = scheduleDao.getAlarmInfo(event);
			if(alarmInfo != null) {
				alarmInfo.put("recurrence", true);
				alarmInfoList.add(alarmInfo);
			}
		}
		
		return alarmInfoList;
	}
	
	protected List<Map<String, Object>> normalizeEvent(List<Map<String, Object>> rowList, String startDate, String endDate) throws ParseException {
		Date searchStart = DateUtils.parseDate(startDate, PARSE_PATTERNS);
		Date searchEnd = DateUtils.parseDate(endDate, PARSE_PATTERNS);
		return normalizeEvent(rowList, searchStart, searchEnd, false);
	}
	
	protected List<Map<String, Object>> normalizeEvent(List<Map<String, Object>> rowList, Date startDate, Date endDate, boolean ignoreTimezone) throws ParseException {
		List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> recurrList = null;
		Date searchStart = startDate;
		Date searchEnd = endDate;

		for (Map<String, Object> row : rowList) {
			if (row.get("repeatType") == null) {
				eventList.add(row);
			} else {
				recurrList = normalizeRecurrenceEvent(row, searchStart, searchEnd);
				if (recurrList != null) {
					eventList.addAll(recurrList);
				}
			}
		}
		return eventList;			
	}

	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> normalizeRecurrenceEvent(Map<String, Object> row, Date searchStart, Date searchEnd) throws ParseException {
		Date repeatStart = (Date) row.get("repeatStartDate");
		Date repeatEnd = (Date) row.get("repeatEndDate");
		Date rangeStart = searchStart.compareTo(repeatStart) < 0 ? repeatStart : searchStart;
		Date rangeEnd = searchEnd.compareTo(repeatEnd) < 0 ? searchEnd : repeatEnd;

		int dayDiff = Utils.getDayDiff(rangeEnd, rangeStart);
		dayDiff += 1;

		List<Map<String, Object>> recurreceEventList = new ArrayList<Map<String, Object>>();
		Date cpdate;
		String repeatTypeStr = (String) row.get("repeatType");
		int repeatType = Integer.valueOf(repeatTypeStr);
		String rperiodStr = (String) row.get("repeatPeriod");
		int rperiod = Integer.valueOf(rperiodStr);
		String roption = (String) row.get("repeatPeriodOption");
		
		Date sdStartDate = (Date) row.get("startDate");
		Date sdEndDate = (Date) row.get("endDate");
		int diff = 0;
		boolean isValid = false;
		boolean result = false;
		
		for (int i = 0; i < dayDiff; i++) {
			isValid = false;
			Map<String, Object> revent;
			cpdate = DateUtils.addDays(rangeStart, i);
			switch (repeatType) {
			case REPEAT_TYPE_DAILY: 
				diff = Utils.getDayDiff(cpdate, repeatStart);
				if(diff%rperiod == 0) {
					isValid = true;
				}
				break;
			case REPEAT_TYPE_WEEKLY: 
				if(Utils.isContainDow(cpdate, roption)) {
					Date d = Utils.getSameDowDate(repeatStart, cpdate);
					diff = Utils.getDayDiff(d, repeatStart);
					if( (diff/7)%rperiod == 0) {
						isValid = true;
					}
				}
				break;
			case REPEAT_TYPE_MONTHLY:
				result = validateMonthlyYearly(roption, cpdate, repeatStart, rperiod);
				if(result) {
					// check period
					int bd = Utils.betweenMonth(repeatStart, cpdate);
					if(bd%rperiod == 0) {
						isValid = true;
					}
				}
				break;
			case REPEAT_TYPE_YEARLY:
				result = validateMonthlyYearly(roption, cpdate, repeatStart, rperiod);
				if(result) {
					// check period
					int bd = Utils.betweenMonth(repeatStart, cpdate);
					if(bd%(rperiod*12) == 0) {
						isValid = true;
					}
				}
				break;
			}
			if(isValid) {
				revent = (Map<String, Object>) ((HashMap<String, Object>)row).clone();
				revent.put("startDate", Utils.addDateTime(cpdate, sdStartDate));
				revent.put("endDate", Utils.addDateTime(cpdate, sdStartDate, sdStartDate, sdEndDate));
				recurreceEventList.add(revent);
			}
		}
		return recurreceEventList;
	}
	
	protected boolean validateMonthlyYearly(String roption, Date cpdate, Date repeatStart, int rperiod) {
		boolean result = false;
		String optTypeArr[] = roption.split(",");
		if(optTypeArr.length == 0) {
			return false;  // 비정상적인 경우
		}
		
		if("a".equals(optTypeArr[0])) { // 특정일자
			int optDay = Integer.valueOf(optTypeArr[1]);
			if( optDay == (int) (DateUtils.getFragmentInDays(cpdate, Calendar.MONTH)) ) {
				result = true;
			}
		} else if("b".equals(optTypeArr[0])) { // 몇번째, 요일
			int nth = Integer.valueOf(optTypeArr[1]);
			Calendar c = Calendar.getInstance();
			c.setTime(cpdate);
			if(nth == c.get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
				int dow = Integer.valueOf(optTypeArr[2]);
				if(dow == c.get(Calendar.DAY_OF_WEEK)) {
					result = true;
				}
			}
		} else if("c".equals(optTypeArr[0])) { // 마지막 요일
			int dow = Integer.valueOf(optTypeArr[1]);
			Date lastDate = Utils.getLastDateByDow(cpdate, dow);
			if(cpdate.equals(DateUtils.truncate(lastDate, Calendar.DATE))) {
				result = true;
			}
		} else if("d".equals(optTypeArr[0])) { // 마지막 일자
			Date lastDate = Utils.getLastDate(cpdate);
			if(cpdate.equals(DateUtils.truncate(lastDate, Calendar.DATE))) {
				result = true;
			}
		}
		return result;
	}

	protected void convertToClientTimezone(List<Map<String, Object>> eventList) {
		Date start, end, rptStart, rptEnd,
		now = timeZoneSupportService.currentServerTime();

		for (Map<String, Object> event : eventList) {
			BigDecimal wholedayDecimal = (BigDecimal) event.get("wholeday");
			int wholeday = wholedayDecimal.intValue();
			if(wholeday == 1) { // allDay event
				start = convertToClientTimeZoneOnlyDate((Date) event.get("startDate"), now);
				end = convertToClientTimeZoneOnlyDate((Date) event.get("endDate"), now);
			} else {
				start = timeZoneSupportService.convertTimeZone((Date) event.get("startDate"));
				end = timeZoneSupportService.convertTimeZone((Date) event.get("endDate"));
			}

			Date rptStartObj = (Date) event.get("repeatStartDate");
			rptStart = rptStartObj != null ? convertToClientTimeZoneOnlyDate(rptStartObj, now) : null;
			
			Date rptEndObj = (Date) event.get("repeatEndDate");
			rptEnd = rptEndObj != null ? convertToClientTimeZoneOnlyDate(rptEndObj, now) : null;
			
			event.put("startDate", start);
			event.put("endDate", end);
			event.put("repeatStartDate", rptStart);
			event.put("repeatEndDate", rptEnd);
		}		
	}
	public Date convertToServerTimezoneOnlyDate(Date d, Date t) {
		Date res = Utils.addDateTime(d, t);
		res = timeZoneSupportService.convertServerTimeZone(res);
		res = DateUtils.truncate(res, Calendar.DATE);
		return res;
	}
	
	protected Date convertToClientTimeZoneOnlyDate(Date d, Date t) {
		Date res = Utils.addDateTime(d, t);
		res = timeZoneSupportService.convertTimeZone(res);
		res = DateUtils.truncate(res, Calendar.DATE);
		return res;
	}
	
	public boolean isEditable(String userId, String regUserId) {
		return regUserId.equals(userId);
	}
	
	public boolean isEditable(String[] users, String regUserId) {
		return ( ArrayUtils.indexOf(users, regUserId) > -1 );
	}
	
	/**
	 * 일정의 editable, viewable setting
	 * @param userId
	 * @param rowList
	 */
	protected void setAuthoEvent(String userId, List<Map<String, Object>> eventList) {
		for (Map<String, Object> event : eventList) {
			BigDecimal isPrivateDecimal = (BigDecimal) event.get("schedulePublic");
			int isPrivate = isPrivateDecimal.intValue();
			//String eventOwnerId = (String) event.get("registerId");
			String participants = (String) event.get("participantId");
			
			String[] participantsList = (participants != null && !"company".equals(participants)) ? participants.split(",") : null;
			
			boolean viewable = false;
			boolean editable = isEditable(userId, event.get("registerId").toString());
			
			if(!editable) {
				editable = this.aclService.hasSystemPermission("Planner", "MANAGE","Planner", userId);//일정 전체 관리자는 수정 권한 준다. 
			}
			
			if(!editable) {
				if(1 == isPrivate) { 	// 비공개
					if(participantsList != null && ArrayUtils.indexOf(participantsList, userId) > -1) {	// 참여자
						viewable = true;
					}
				} else {
					viewable = true;
				}
			} else {
				viewable = true;
			}
			
			event.put("editable", editable);
			event.put("viewable", viewable);
			
			String meetingRoomId = (String)event.get("meetingRoomId");
			
			boolean resizable = false;
			boolean draggable = false;
			
			if(StringUtil.isEmpty(meetingRoomId)){
				if(editable){
					 resizable = true;
					 draggable = true;
				}else{
					 resizable = false;
					 draggable = false;
				}
			}else{//회의실예약이 있는 일정은 사이징과 드레깅 막는다.
				 resizable = false;
				 draggable = false;
			}
			
			if("company".equals(participants)){//전사일정은 무조건 사이징과 드레깅 막는다.
				 resizable = false;
				 draggable = false;
			}
			
			event.put("resizable", resizable);
			event.put("draggable", draggable);
			
/*
			if(1 == isPrivate) { 	// 비공개
				if(userId.equals(eventOwnerId)) {
					event.put("editable", true);
					event.put("viewable", true);
				} else if(participantsList != null && ArrayUtils.indexOf(participantsList, userId) != -1) {
					event.put("editable", false);
					event.put("viewable", true);
				} else {
					event.put("editable", false);
					event.put("viewable", false);
				}
			} else {	// 공개
				if(userId.equals(eventOwnerId)) {
					event.put("editable", true);
					event.put("viewable", true);
				} else {
					event.put("editable", false);
					event.put("viewable", true);
				}
			}
*/
		}
	}
	
	protected void setAuthoEvent(String[] users, List<Map<String, Object>> eventList) {
		for (Map<String, Object> event : eventList) {
			BigDecimal isPrivateDecimal = (BigDecimal) event.get("schedulePublic");
			int isPrivate = isPrivateDecimal.intValue();
			String eventOwnerId = (String) event.get("registerId");
			String participantId = (String) event.get("participantId");
			
			boolean viewable = false;
			boolean editable = eventOwnerId != null ? isEditable(users, eventOwnerId) : false;	//eventOwnerId : 참여자의 일정인 경우 registerId를 받아 오지 않으므로 예외처리
			
			
			if(!editable) {
				if(1 == isPrivate) { 	// 비공개
					if(participantId != null && ArrayUtils.indexOf(users, participantId) > -1) {	// 참여자
						viewable = true;
					}
				} else {
					viewable = true;
				}
			} else {
				viewable = true;
			}
			
			event.put("editable", editable);
			event.put("viewable", viewable);

/*			
			if(1 == isPrivate) {	// 비공개
				if(ArrayUtils.indexOf(users, eventOwnerId) != -1) {
					event.put("editable", true);
					event.put("viewable", true);
				} else if(participantId != null && ArrayUtils.indexOf(users, participantId) != -1) {
					event.put("editable", false);
					event.put("viewable", true);
				} else {
					event.put("editable", false);
					event.put("viewable", false);
				}
			} else { // 공개
				if(ArrayUtils.indexOf(users, eventOwnerId) != -1) {
					event.put("editable", true);
					event.put("viewable", true);
				} else {
					event.put("editable", false);
					event.put("viewable", true);
				}
			}
*/
		}
		
	}
	
	class EventCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> a, Map<String, Object> b) {
			Date astart = (Date) a.get("startDate");			
			Date bstart = (Date) b.get("startDate");
			
			return astart.compareTo(bstart);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriod(java.util.Map)
	 */
	public List<Map<String, Object>> selectByPeriod(Map<String, Object> params) throws ParseException {
		String userId = (String) params.get("userId");
		Date start = DateUtils.parseDate((String) params.get("startDate"), PARSE_PATTERNS);
		Date end = DateUtils.parseDate((String) params.get("endDate"), PARSE_PATTERNS);
		return selectByPeriod(userId, start, end);
	}
	
	public List<Map<String, Object>> readMeetingRoomScheduleById(String meetingRoomId, Date fromDate, Date toDate) throws ParseException {
		
		List<Map<String, Object>> eventList = selectByPeriodForMeetingRoomById(meetingRoomId, fromDate, toDate);
		
		Collections.sort(eventList, new EventCompare());
		/*
		Date sd, ed;
		
		for (Map<String, Object> event : eventList) {
			
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		*/
		return eventList;
	}
	
	public List<Map<String, Object>> readCartooletcScheduleById(String cartooletcId, Date fromDate, Date toDate) throws ParseException {
		
		List<Map<String, Object>> eventList = selectByPeriodForCartooletcById(cartooletcId, fromDate, toDate);
		
		Collections.sort(eventList, new EventCompare());
		/*
		Date sd, ed;
		
		for (Map<String, Object> event : eventList) {
			
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		*/
		return eventList;
	}
	
	public List<Map<String, Object>> readMeetingRoomScheduleByBuildingFloor(String buildingId, String floorId, Date fromDate, Date toDate) throws ParseException {
		
		List<Map<String, Object>> eventList = selectByPeriodForMeetingRoomByBuildingFloor(buildingId, floorId, fromDate, toDate);
		
		Collections.sort(eventList, new EventCompare());
		
		Date sd, ed;
		
		for (Map<String, Object> event : eventList) {
			
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		
		return eventList;
	}
	
	public List<Map<String, Object>> readCartooletcScheduleByCategoryRegion(String categoryId, String regionId, Date fromDate, Date toDate) throws ParseException {
		
		List<Map<String, Object>> eventList = selectByPeriodForCartooletcByCategoryRegion( categoryId,  regionId,  fromDate,  toDate);
		
		Collections.sort(eventList, new EventCompare());
		
		Date sd, ed;
		
		for (Map<String, Object> event : eventList) {
			
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		
		return eventList;
	}
	
	public List<Map<String, Object>> readMeetingRoomScheduleByFavorite(String portalId, String userId, Date fromDate, Date toDate) throws ParseException {
		
		List<Map<String, Object>> eventList = selectByPeriodForMeetingRoomByFavorite(portalId, userId, fromDate, toDate);
		
		Collections.sort(eventList, new EventCompare());
		
		Date sd, ed;
		
		for (Map<String, Object> event : eventList) {
			
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		
		return eventList;
	}
	public List<Map<String, Object>> readCartooletcScheduleByFavorite(String portalId, String userId, Date fromDate, Date toDate) throws ParseException {
		
		List<Map<String, Object>> eventList = selectByPeriodForCartooletcByFavorite(portalId, userId, fromDate, toDate);
		
		Collections.sort(eventList, new EventCompare());
		
		Date sd, ed;
		
		for (Map<String, Object> event : eventList) {
			
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		
		return eventList;
	}
	
	public List<Map<String, Object>> readMeetingRoomScheduleByRecent(String portalId, Date fromDate, Date toDate) throws ParseException {
		
		List<Map<String, Object>> eventList = selectByPeriodForMeetingRoomByRecent(portalId, fromDate, toDate);
		
		Collections.sort(eventList, new EventCompare());
		
		Date sd, ed;
		
		for (Map<String, Object> event : eventList) {
			
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		
		return eventList;
	}
	
	public List<Map<String, Object>> readCartooletcScheduleByRecent(String portalId, Date fromDate, Date toDate) throws ParseException {
		
		List<Map<String, Object>> eventList = selectByPeriodForCartooletcByRecent(portalId, fromDate, toDate);
		
		Collections.sort(eventList, new EventCompare());
		
		Date sd, ed;
		
		for (Map<String, Object> event : eventList) {
			
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForMeetingRoomById(String meetingRoomId, Date startDate, Date endDate) throws ParseException {
			
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("meetingRoomId", meetingRoomId);
		params.put("startDate", start);
		params.put("endDate", end);
		
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForMeetingRoom(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForCartooletcById(String cartooletcId, Date startDate, Date endDate) throws ParseException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("cartooletcId", cartooletcId);
		params.put("startDate", start);
		params.put("endDate", end);
		
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForCartooletc(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		return eventList;
	}
	
	public List<Map<String, Object>> selectMeetingRoomByReserveList(String meetingRoomId, Date startDate, Date endDate, String scheduleId) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("meetingRoomId", meetingRoomId);
		params.put("startDate", start);
		params.put("endDate", end);
		if(scheduleId != null && !scheduleId.equals("")) {
			params.put("scheduleId", scheduleId);
		}
		
		List<Map<String, Object>> rowList = scheduleDao.selectMeetingRoomByReserveList(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		Collections.sort(eventList, new EventCompare());
		
		for (Map<String, Object> event : eventList) {
			event.put("startDate", fdf.format((Date) event.get("startDate")));
			event.put("endDate", fdf.format((Date) event.get("endDate")));
		}
		
		return eventList;
	}
		
	public List<Map<String, Object>> selectCartooletcByReserveList(String cartooletcId, Date startDate, Date endDate, String scheduleId) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("cartooletcId", cartooletcId);
		params.put("startDate", start);
		params.put("endDate", end);
		if(scheduleId != null && !scheduleId.equals("")) {
			params.put("scheduleId", scheduleId);
		}
		
		List<Map<String, Object>> rowList = scheduleDao.selectCartooletcByReserveList(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		Collections.sort(eventList, new EventCompare());
		
		for (Map<String, Object> event : eventList) {
			event.put("startDate", fdf.format((Date) event.get("startDate")));
			event.put("endDate", fdf.format((Date) event.get("endDate")));
		}
		
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForMeetingRoomByBuildingFloor(String buildingId, String floorId, Date startDate, Date endDate) throws ParseException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("buildingId", buildingId);
		params.put("floorId", floorId);
		params.put("startDate", start);
		params.put("endDate", end);
		
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForMeetingRoom(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		return eventList;
	}
	public List<Map<String, Object>> selectByPeriodForCartooletcByCategoryRegion(String categoryId, String regionId, Date startDate, Date endDate) throws ParseException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("categoryId", categoryId);
		params.put("regionId", regionId);
		params.put("startDate", start);
		params.put("endDate", end);
		
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForCartooletc(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForMeetingRoomByFavorite(String portalId, String userId, Date startDate, Date endDate) throws ParseException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("portalId", portalId);
		params.put("userId", userId);
		params.put("startDate", start);
		params.put("endDate", end);
		
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForMeetingRoomByFavorite(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		return eventList;
	}
	public List<Map<String, Object>> selectByPeriodForCartooletcByFavorite(String portalId, String userId, Date startDate, Date endDate) throws ParseException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("portalId", portalId);
		params.put("userId", userId);
		params.put("startDate", start);
		params.put("endDate", end);
		
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForCartooletcByFavorite(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		return eventList;
	}
	public List<Map<String, Object>> selectByPeriodForMeetingRoomByRecent(String portalId, Date startDate, Date endDate) throws ParseException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("portalId", portalId);
		params.put("startDate", start);
		params.put("endDate", end);
		
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForMeetingRoomByRecent(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		return eventList;
	}
	
	public List<Map<String, Object>> selectByPeriodForCartooletcByRecent(String portalId, Date startDate, Date endDate) throws ParseException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow);
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		params.put("portalId", portalId);
		params.put("startDate", start);
		params.put("endDate", end);
		
		List<Map<String, Object>> rowList = scheduleDao.selectByPeriodForCartooletcByRecent(params);
		
		convertToClientTimezone(rowList);
		
		List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
		
		return eventList;
	}
	
	public List<Map<String, Object>> getMeetingRoomRepeatList(Schedule schedule) {
		Map<String, Object> event = new HashMap<String, Object>();
		event.put("wholeday", schedule.getWholeday());
		event.put("startDate", schedule.getStartDate());
		event.put("endDate", schedule.getEndDate());
		if(schedule.getRepeat() == 1 && schedule.getRecurrences().size() > 0) {
			for(Recurrences recurrence : schedule.getRecurrences()) {
				event.put("repeatStartDate", recurrence.getStartDate());
				event.put("repeatEndDate", recurrence.getEndDate());
				
				event.put("repeatType", recurrence.getRepeatType());
				event.put("repeatPeriod", recurrence.getRepeatPeriod());
				event.put("repeatPeriodOption", recurrence.getRepeatPeriodOption());
			}
		}
		
		List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
		eventList.add(event);
		
		
		List<Map<String, Object>> repeatList = null;
		try {
			repeatList = normalizeEvent(eventList, schedule.getStartDate(), (Date)event.get("repeatEndDate"), false);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return repeatList;
	}
	
	public List<Map<String, Object>> getCartooletcRepeatList(Schedule schedule) {
		Map<String, Object> event = new HashMap<String, Object>();
		event.put("wholeday", schedule.getWholeday());
		event.put("startDate", schedule.getStartDate());
		event.put("endDate", schedule.getEndDate());
		if(schedule.getRepeat() == 1 && schedule.getRecurrences().size() > 0) {
			for(Recurrences recurrence : schedule.getRecurrences()) {
				event.put("repeatStartDate", recurrence.getStartDate());
				event.put("repeatEndDate", recurrence.getEndDate());
				
				event.put("repeatType", recurrence.getRepeatType());
				event.put("repeatPeriod", recurrence.getRepeatPeriod());
				event.put("repeatPeriodOption", recurrence.getRepeatPeriodOption());
			}
		}
		
		List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
		eventList.add(event);
		
		
		List<Map<String, Object>> repeatList = null;
		try {
			repeatList = normalizeEvent(eventList, schedule.getStartDate(), (Date)event.get("repeatEndDate"), false);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return repeatList;
	}
	
	public List<Schedule> getCompanyScheduleWaitList() {
		return  scheduleDao.getCompanyScheduleWaitList();
	}
	
	public List<FavoriteTarget> selectFavoriteTeam(String userId){
		return scheduleDao.selectFavoriteTeam(userId);
	}
	
	public List<FavoriteTarget> selectFavoriteUser(String userId){
		return scheduleDao.selectFavoriteUser(userId);
	}
	
	public void favoriteSetting(Schedule schedule, String flg){
		
		if(flg.equals("1")){
			schedule.setItemTypeCode("PF");
			schedule.setType("PEOPLE");
			scheduleDao.deleteFavoriteSetting(schedule);
			List<String> targetUserIds = schedule.getTargetList();
			if( targetUserIds != null){
				for( int i = 0 ; i < targetUserIds.size() ; i++ ){
					schedule.setTargetGroupId(targetUserIds.get(i));
					schedule.setFavoriteId(idgenService.getNextId());
					scheduleDao.insertFavoriteUser(schedule);
				}
			}
		}else{
			schedule.setItemTypeCode("GP");
			schedule.setType("GROUP");
			scheduleDao.deleteFavoriteSetting(schedule);
			List<String> targetGroupIds = schedule.getTargetGroupList();
			if( targetGroupIds != null){
				for( int i = 0 ; i < targetGroupIds.size() ; i++ ){
					schedule.setTargetGroupId(targetGroupIds.get(i));
					schedule.setFavoriteId(idgenService.getNextId());
					scheduleDao.insertFavoriteTeam(schedule);
				}
			}
		}
	}
	
	public List<Map<String, Object>> readUserDateSchedule(String userId, String startDate, String endDate) throws ParseException {
		List<Map<String, Object>> eventList = selectByPeriod(userId, startDate, endDate);
		Collections.sort(eventList, new EventCompare());
		Date sd, ed;
		for (Map<String, Object> event : eventList) {
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
		}
		return eventList;
	}
	
	public List<Map<String, Object>> selectFacilityReserveListWithoutSchedule(Schedule schedule) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		// import 데이타인 경우 DateTime으로 들어와 오류가 발생되어서 변경함
		Date startDate = new Date(schedule.getStartDate().getTime());
		Date endDate = new Date(schedule.getEndDate().getTime());
		if(schedule.getWholeday() == 1) {
			startDate.setHours(0);
			startDate.setMinutes(0);
			startDate.setSeconds(0);
			
			endDate.setDate(endDate.getDate() + 1);
			endDate.setHours(0);
			endDate.setMinutes(0);
			endDate.setSeconds(0);
		}
		
		if(schedule.getRepeat() == 1 && schedule.getRecurrences().size() > 0) {
			for(Recurrences recurrence : schedule.getRecurrences()) {
				if(endDate.getTime() < recurrence.getEndDate().getTime()) {
					endDate = recurrence.getEndDate();
				}
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", schedule.getScheduleId());
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		List<MeetingRoom> facilityList = schedule.getMeetingRoomList();
		if(facilityList.size() > 0 ) {
			List<String> facilityIds = new ArrayList<String>();
			for(MeetingRoom facility : facilityList) {
				facilityIds.add(facility.getMeetingRoomId());
			}
			params.put("facilities", facilityIds);
		}
		
		
		
		try {
			List<Map<String, Object>> eventList = selectMeetingRoomByReserveList(schedule.getMeetingRoomId(), startDate, endDate, schedule.getScheduleId());
			
	
			if(eventList.size() > 0) {
				List<Map<String, Object>> repeatEventList = generateRepeatList(schedule);
				if(repeatEventList.size() > 0) {
					for(Map<String, Object> event : eventList) {
						String eventFacilityId = (String)event.get("meetingRoomId");
						for(MeetingRoom facility : schedule.getMeetingRoomList()) {
							if(facility.getMeetingRoomId().equals(eventFacilityId)) {
								long sDate = DateUtils.parseDate(event.get("startDate").toString(), PARSE_PATTERNS).getTime();
								long eDate = DateUtils.parseDate(event.get("endDate").toString(), PARSE_PATTERNS).getTime();

								if("1".equals(event.get("wholeday").toString()))	// 종일 일정이면...
									eDate += (24*60*60*1000);
								
								for(Map<String, Object> repeatEvent : repeatEventList) {
									long rsDate = ((Date)repeatEvent.get("startDate")).getTime();
									long reDate = ((Date)repeatEvent.get("endDate")).getTime();
									
									if("1".equals(repeatEvent.get("wholeday").toString()))	// 종일 일정이면...
										reDate += (24*60*60*1000);
									
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
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Map<String, Object>> generateRepeatList(Schedule schedule) {
		Map<String, Object> event = new HashMap<String, Object>();
		event.put("wholeday", schedule.getWholeday());
		event.put("startDate", schedule.getStartDate());
		event.put("endDate", schedule.getEndDate());
		if(schedule.getRepeat() == 1 && schedule.getRecurrences().size() > 0) {
			for(Recurrences recurrence : schedule.getRecurrences()) {
				event.put("repeatStartDate", recurrence.getStartDate());
				event.put("repeatEndDate", recurrence.getEndDate());
				
				event.put("repeatType", recurrence.getRepeatType());
				event.put("repeatPeriod", recurrence.getRepeatPeriod());
				event.put("repeatPeriodOption", recurrence.getRepeatPeriodOption());
			}
		}
		
		List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
		eventList.add(event);
		
		
		List<Map<String, Object>> repeatList = null;
		try {
			repeatList = normalizeEvent(eventList, schedule.getStartDate(), (Date)event.get("repeatEndDate"), false);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return repeatList;
	}

	public List<Map<String, Object>> portalQuickMenuCountAll(String userId, Date startDate) throws ParseException {
		Map<String, Object> params = new HashMap<String, Object>();
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 

		params.put("userId", userId);
		params.put("startDate", start);
		params.put("endDate", start);
		
		List<Map<String, Object>> resultList = scheduleDao.portalQuickMenuCountAll(params);
		
		for(int i = 0; i < resultList.size(); i++) {
			Map<String, Object> countMap = resultList.get(i);
			
			if("SCHEDULE".equals((String)countMap.get("countModule"))) {
				List<Map<String, Object>> rowList = scheduleDao.readUserRecurrence(params);
				convertToClientTimezone(rowList);
				List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, start, true);
				
				Integer scheduleCnt = Integer.parseInt(countMap.get("cnt").toString())  + eventList.size(); 
				
				countMap.put("cnt", scheduleCnt);
				
				break;
			}
		}
		
		return resultList;
	}
	
	public Map<String, Object> selectByPeriodScheduleSearch(String userId, ScheduleSearchCondition searchCondition)
	throws ParseException {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Date startDate = searchCondition.getStartDate();
		Date endDate = searchCondition.getEndDate();
		
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date start = convertToServerTimezoneOnlyDate(startDate, clientNow); 
		Date end = convertToServerTimezoneOnlyDate(endDate, clientNow);
		
		searchCondition.setStartDate(start);
		searchCondition.setEndDate(end);
		
		int totSearchRecordCount = scheduleDao.searchScheduleCount(searchCondition);
		result.put("totalRecordCount", totSearchRecordCount);
		
		if(totSearchRecordCount > 0) {
			List<Map<String, Object>> rowList = scheduleDao.searchSchedule(searchCondition);
			convertToClientTimezone(rowList);
			//List<Map<String, Object>> eventList = normalizeEvent(rowList, startDate, endDate, false);
			
			setAuthoEvent(userId, rowList);
			
			result.put("eventList", rowList);
		}
		
		return result;
	}
	
	public void updateScheduleSyncData(List<ScheduleDetail> scheduleDetail)
	{
		for (ScheduleDetail scheduleDetail1 : scheduleDetail) {
			scheduleDao.updateScheduleSyncData(scheduleDetail1);
	    }
	}
	
	public void insertScheduleSyncData()
	{
		List<Schedule> sapScheduleList = scheduleDao.sapScheduleList();
		String atextCode = "";
		for(int i = 0; i < sapScheduleList.size(); i++) {
			Schedule sapSchedule = sapScheduleList.get(i);
			if(sapSchedule.getAtext().equals("휴가")){
				sapSchedule.setCategoryId("2");
				sapSchedule.setSearchCode("1");
				atextCode = "2";
			}else if(sapSchedule.getAtext().equals("출장")){
				sapSchedule.setCategoryId("1");
				sapSchedule.setSearchCode("2");
				atextCode = "1";
			}else{
				sapSchedule.setCategoryId("1");
				sapSchedule.setSearchCode("0");
				atextCode = "1";
			}
			
			if(sapSchedule.getRequestReason().equals("생리휴가")){
				sapSchedule.setRequestReason("기타휴가");
			} 
			sapSchedule.setScheduleId(idgenService.getNextId());
			scheduleDao.insertSapSchedule(sapSchedule);
			
			if(!sapSchedule.getAtext().equals("휴직")){
				String newId = idgenService.getNextId();
				scheduleDao.copyTeamSapSchedule(sapSchedule.getAwart(), atextCode, sapSchedule.getScheduleId(), newId);
			}
		}
		scheduleDao.updateScheduleSyncDataEnd();
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#readMyTodaySchedule(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> readMyWebDiarySchedule(String userId) throws ParseException {
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date today = DateUtils.truncate(clientNow, Calendar.DATE);
		List<Map<String, Object>> eventList = selectByPeriod(userId, today, today);
		Collections.sort(eventList, new EventCompare());
		Date sd, ed;
		for (Map<String, Object> event : eventList) {
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
			event.put("startTime", fdf.format(sd).substring(8));
			event.put("endTime", fdf.format(ed).substring(8));
			int am = Integer.parseInt(fdf.format(sd).substring(10));
			String sTime = fdf.format(sd).substring(8,10);
			if (am < 30 ){
				event.put("timeGbn", sTime+"A");
			}else{
				event.put("timeGbn", sTime+"P");
			}
		}
		return eventList;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#selectByPeriodForWorkspace(java.lang.String, java.lang.String, long, long)
	 */
	public List<Map<String, Object>> readTeamWebDiarySchedule(String userId, String workspaceId, long start, long end)
			throws ParseException {
		List<Map<String, Object>> eventList = selectByPeriodForWorkspace(workspaceId, new Date(start), new Date(end));
		Collections.sort(eventList, new EventCompare());
		Date sd, ed;
		for (Map<String, Object> event : eventList) {
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
			event.put("startTime", fdf.format(sd).substring(8));
			event.put("endTime", fdf.format(ed).substring(8));
			int am = Integer.parseInt(fdf.format(sd).substring(10));
			String sTime = fdf.format(sd).substring(8,10);
			if (am < 30 ){
				event.put("timeGbn", sTime+"A");
			}else{
				event.put("timeGbn", sTime+"P");
			}
		}
		
		setAuthoEvent(userId, eventList);
		return eventList;
	}

	public List<Map<String, Object>> webDiaryReadUserDateSchedule(String userId, String startDate, String endDate) throws ParseException {
		List<Map<String, Object>> eventList = selectByPeriod(userId, startDate, endDate);
		Collections.sort(eventList, new EventCompare());
		Date sd, ed;
		for (Map<String, Object> event : eventList) {
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
			event.put("startTime", fdf.format(sd).substring(8));
			event.put("endTime", fdf.format(ed).substring(8));
			int am = Integer.parseInt(fdf.format(sd).substring(10));
			String sTime = fdf.format(sd).substring(8,10);
			if (am < 30 ){
				event.put("timeGbn", sTime+"A");
			}else{
				event.put("timeGbn", sTime+"P");
			}
		}
		return eventList;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.ScheduleService#readWorkspaceSchedule(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> webDiaryReadWorkspaceSchedule(String workspaceId, String startDate, String viewUrl) throws ParseException {
		Date start = DateUtils.parseDate(startDate, PARSE_PATTERNS);
		List<Map<String, Object>> eventList = selectByPeriodForWorkspace(workspaceId, start, start);
		Collections.sort(eventList, new EventCompare());
		Date sd, ed;
		String url = viewUrl, scheduleId;
		for (Map<String, Object> event : eventList) {
			sd = (Date) event.get("startDate");
			ed = (Date) event.get("endDate");
			scheduleId = (String) event.get("scheduleId");
			event.put("startDate", fdf.format(sd));
			event.put("endDate", fdf.format(ed));
			event.put("title", (String) event.get("title"));
			event.put("returl", url+scheduleId);
			event.put("startTime", fdf.format(sd).substring(8));
			event.put("endTime", fdf.format(ed).substring(8));
			int am = Integer.parseInt(fdf.format(sd).substring(10));
			String sTime = fdf.format(sd).substring(8,10);
			if (am < 30 ){
				event.put("timeGbn", sTime+"A");
			}else{
				event.put("timeGbn", sTime+"P");
			}
		}
		return eventList;
	}
	


}
