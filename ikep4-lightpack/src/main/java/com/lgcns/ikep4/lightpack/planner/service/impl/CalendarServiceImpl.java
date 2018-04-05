/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;

import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.WeekDayList;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.lightpack.meetingroom.model.Approve;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.service.ApproveService;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomMainService;
import com.lgcns.ikep4.lightpack.meetingroom.service.ReserveService;
import com.lgcns.ikep4.lightpack.meetingroom.web.ReserveController;
import com.lgcns.ikep4.lightpack.planner.dao.AlarmDao;
import com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.EventInfoVO;
import com.lgcns.ikep4.lightpack.planner.model.Holiday;
import com.lgcns.ikep4.lightpack.planner.model.Mandator;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Recurrences;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO;
import com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.lightpack.planner.util.Utils;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.ical.ICalUtil;
import com.lgcns.ikep4.util.ical.model.IAlarm;
import com.lgcns.ikep4.util.ical.model.IAttendee;
import com.lgcns.ikep4.util.ical.model.ICalendar;
import com.lgcns.ikep4.util.ical.model.IDur;
import com.lgcns.ikep4.util.ical.model.IEvent;
import com.lgcns.ikep4.util.ical.model.IRecur;
import com.lgcns.ikep4.util.ical.model.ITimeZone;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


/**
 * 일정관리 서비스 구현
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: CalendarServiceImpl.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
@Service("calendarService")
public class CalendarServiceImpl extends GenericServiceImpl<Schedule, String> implements CalendarService {

	@SuppressWarnings("unused")
	private static String[] PATTERN_DATE = { "yyyyMMdd" };

	@Autowired
	private ScheduleDao scheduleDao;

	@Autowired
	private AlarmDao alarmDao;

	@Autowired
	private ParticipantDao participantDao;

	@SuppressWarnings("unused")
	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private FileService fileService;

	@Autowired
	private AlarmPlannerService alarmPlannerService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private UserDao userDao;

	private final static String pattern_date = "yyyyMMdd";

	private final static String pattern_datetime = "yyyyMMddHHmm";

	private final static String pattern_datetime_dash = "yyyy-MM-dd HH:mm";

	private static String[] parsePatterns = new String[] { pattern_datetime, pattern_date, pattern_datetime_dash };
	
	@Autowired
	private ReserveService reserveService;
	
	@Autowired
	private MeetingRoomMainService meetingRoomService;
	
	
	@Autowired
	private CartooletcService cartooletcService;
	
	
	@Autowired
	private ApproveService approveService;
	
	@Autowired
	private UserService userService;

	@Autowired
	public CalendarServiceImpl(ScheduleDao dao) {
		this.dao = dao;
		scheduleDao = dao;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String create(Schedule schedule, User user) {
		String scheduleId;
		String mandatorId = schedule.getMandatorId();

		if (user != null) {
			convertToServerTimezone(schedule);
		}
		
		List<Map<String, Object>> meetingRoomReserveList = null;
		if(schedule.getMeetingRoomId() != null && !schedule.getMeetingRoomId().equals("")) {	// 회의실 예약이 있으면...
			meetingRoomReserveList = reserveService.selectExists(schedule);	// 중복인지 체크
			if(meetingRoomReserveList.size() > 0) {
				return ReserveController.DUPLICATE;
			}
		}

		List<Map<String, Object>> cartooletcReserveList = null;
		if(schedule.getCartooletcId() != null && !schedule.getCartooletcId().equals("")) {	// 자원예약이 있으면...
			cartooletcReserveList = reserveService.selectCarExists(schedule);	// 중복인지 체크
			if(cartooletcReserveList.size() > 0) {
				return ReserveController.DUPLICATE;
			}
		}
		
		
		// 수임자가 작성한 일정
		boolean isMandatorSchedule = StringUtils.isNotBlank(mandatorId);

		if (!isMandatorSchedule) {
			scheduleId = scheduleDao.create(schedule);
		} else {
			scheduleId = scheduleDao.createByTrustee(schedule);
		}
		
		_createSchedule(schedule, scheduleId);

		if (user != null && schedule.getFileLinkList() != null && schedule.getFileLinkList().size() > 0) {
			if (!isMandatorSchedule) {
				fileService.saveFileLink(schedule.getFileLinkList(), scheduleId, IKepConstant.ITEM_TYPE_CODE_PLANNER,
						user);
			} else {
				User mandator = scheduleDao.getMandator(mandatorId);
				fileService.saveFileLink(schedule.getFileLinkList(), scheduleId, IKepConstant.ITEM_TYPE_CODE_PLANNER,
						mandator);
			}
		}
		
		if(meetingRoomReserveList != null && meetingRoomReserveList.size() == 0) {	// 회의실 예약이 가능하면...
			MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
			
			Approve approve = new Approve();
			
			approve.setScheduleId(scheduleId);
			approve.setMeetingRoomId(schedule.getMeetingRoomId());
			approve.setApproveStatus("A");
			ModelBeanUtil.bindRegisterInfo(approve, user.getUserId(), user.getUserName());
			
			if(!StringUtil.isEmpty(meetingRoom.getManagerId()) && "N".equals(meetingRoom.getAutoApprove()))
				approve.setApproveStatus("W");
			
			if( "N".equals(meetingRoom.getAutoApprove()) && !StringUtil.isEmpty(meetingRoom.getManagerId())){
				User manager = userService.read(meetingRoom.getManagerId());
				reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
			}
			
			if( "N".equals(meetingRoom.getAutoApprove()) && !StringUtil.isEmpty(meetingRoom.getSubManagerId())){
				User manager = userService.read(meetingRoom.getSubManagerId());
				reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
			}
			 
			if( "N".equals(meetingRoom.getAutoApprove()) && !StringUtil.isEmpty(meetingRoom.getLastManagerId())){
				User manager = userService.read(meetingRoom.getLastManagerId());
				reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
			}
			
			approveService.create(approve);
		}
		if(cartooletcReserveList != null && cartooletcReserveList.size() == 0) {	// 회의실 예약이 가능하면...
			Cartooletc cartooletc = cartooletcService.read(schedule.getCartooletcId());
			
			Approve approve = new Approve();
			
			approve.setScheduleId(scheduleId);
			approve.setMeetingRoomId(schedule.getCartooletcId());
			approve.setApproveStatus("A");
			ModelBeanUtil.bindRegisterInfo(approve, user.getUserId(), user.getUserName());
			
			if(!StringUtil.isEmpty(cartooletc.getManagerId()) && "N".equals(cartooletc.getAutoApprove()))
				approve.setApproveStatus("W");
			
			if( "N".equals(cartooletc.getAutoApprove()) && !StringUtil.isEmpty(cartooletc.getManagerId())){
				User manager = userService.read(cartooletc.getManagerId());
				reserveService.sendReservationMail("car",  "[자원 예약 승인요청]" , schedule, user,  manager);
			}
			approveService.create(approve);
		}
		
		// 푸쉬 발송
		List<Participant> participantList = schedule.getParticipantList();
		
		//PUSH 설정
		BufferedReader br = null;
		HttpClient client = new DefaultHttpClient();
		Properties prop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String pushBaseUrl = prop.getProperty("ikep4.push.baseUrl");
		HttpPost request = new HttpPost(pushBaseUrl+"/rest/push/insertPushByItemId");
        request.addHeader("content-type", "application/json");
        
		if(participantList!=null) {
			for(Participant entity : participantList) {
				try {
			        JSONObject json = new JSONObject();
			        json.put("portalId", "P000001");
			        json.put("userId", entity.getTargetUserId());
			        json.put("pushType", "P");
			        json.put("alertTitle", "일정");
			        json.put("alertText", schedule.getTitle());
			        json.put("itemId", schedule.getScheduleId());
			        StringEntity input = new StringEntity( json.toString(), "UTF-8");
			        
		        	request.setEntity(input);
		        	HttpResponse response = client.execute(request);
		        	
		        	//to use client again, should read the response content and close the stream.
		            //since you won't use the response content, just close the stream
		            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            br.close();
		            
		        	//verify the valid error code first
		            System.out.println("returnCode : "+response.getStatusLine().getStatusCode());
		
				} catch (Exception e) {
					log.error("Error occurs during send push messages : "+e.getMessage());
				} 
			}
			client.getConnectionManager().shutdown();
		} 
		
		return scheduleId;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	protected String createByCopy(Schedule schedule, User user) {
		String scheduleId;
		String mandatorId = schedule.getMandatorId();

		if (user != null) {
			convertToServerTimezone(schedule);
		}

		// 수임자가 작성한 일정
		boolean isMandatorSchedule = StringUtils.isNotBlank(mandatorId);

		if (!isMandatorSchedule) {
			scheduleId = scheduleDao.create(schedule);
		} else {
			scheduleId = scheduleDao.createByTrustee(schedule);
		}

		_createSchedule(schedule, scheduleId);

		if (user != null && schedule.getFileLinkList() != null && schedule.getFileLinkList().size() > 0) {
			if (!isMandatorSchedule) {
				fileService.copyByFileLinkList(schedule.getFileLinkList(), scheduleId,
						IKepConstant.ITEM_TYPE_CODE_PLANNER, user);
			} else {
				User mandator = scheduleDao.getMandator(mandatorId);
				fileService.copyByFileLinkList(schedule.getFileLinkList(), scheduleId,
						IKepConstant.ITEM_TYPE_CODE_PLANNER, mandator);
			}
		}
		return scheduleId;
	}

	@Override
	public String create(Schedule schedule) {
		return create(schedule, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#create(com.
	 * lgcns.ikep4.lightpack.planner.model.Schedule,
	 * com.lgcns.ikep4.support.user.member.model.User, boolean)
	 */
	public String create(Schedule schedule, User user, boolean sendmail) {
		String scheduleId = create(schedule, user);
		if (scheduleId != null && !scheduleId.equals(ReserveController.DUPLICATE) && sendmail) {
			alarmPlannerService.sendSimpleMail("create", schedule);
		}
		return scheduleId;
	}

	@Transactional
	public String createByTrustee(Schedule schedule, User user) {
		List<Map<String, Object>> meetingRoomReserveList = null;
		if(schedule.getMeetingRoomId() != null && !schedule.getMeetingRoomId().equals("")) {	// 회의실이 있으면...
			meetingRoomReserveList = reserveService.selectExists(schedule);	// 회의실 예약이 중복인지 체크
			if(meetingRoomReserveList.size() > 1) {
				return ReserveController.DUPLICATE;
			}
		}
		
		String scheduleId = scheduleDao.createByTrustee(schedule);
		
		_createSchedule(schedule, scheduleId);

		if (schedule.getFileLinkList() != null && schedule.getFileLinkList().size() > 0) {
			fileService.saveFileLink(schedule.getFileLinkList(), scheduleId, IKepConstant.ITEM_TYPE_CODE_PLANNER, user);
		}
		
		if(meetingRoomReserveList != null && meetingRoomReserveList.size() == 0) {	// 회의실 예약이 가능하면...
			MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
			
			Approve approve = new Approve();
			
			approve.setScheduleId(scheduleId);
			approve.setMeetingRoomId(schedule.getMeetingRoomId());
			approve.setApproveStatus("W");
			ModelBeanUtil.bindRegisterInfo(approve, user.getUserId(), user.getUserName());
			
			if(!StringUtil.isEmpty(meetingRoom.getManagerId())) approve.setApproveStatus("W");
			else approve.setApproveStatus("A");
			
			if( "N".equals(meetingRoom.getAutoApprove()) && !StringUtil.isEmpty(meetingRoom.getManagerId())){
				User manager = userService.read(meetingRoom.getManagerId());
				reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
			}
			
			approveService.create(approve);
		}
		
		return scheduleId;
	}

	private void _createSchedule(Schedule schedule, String scheduleId) {
		List<Recurrences> recurList = schedule.getRecurrences();
		if (recurList != null && recurList.size() > 0) {
			for (Recurrences recur : recurList) {
				recur.setScheduleId(scheduleId);
				scheduleDao.createRecurrences(recur);
			}
		}

		List<Alarm> alarms = schedule.getAlarmList();
		if (alarms != null && alarms.size() > 0) {
			String alarmId;
			for (Alarm alarm : alarms) {
				alarmId = idgenService.getNextId();
				alarm.setAlarmId(alarmId);
				alarm.setScheduleId(scheduleId);
			}
			alarmDao.create(alarms);
		}

		List<Participant> participantList = schedule.getParticipantList();
		if (participantList != null && participantList.size() > 0) {
			participantDao.create(participantList, scheduleId);
		}
		
	}

	@Transactional
	public void updateSchedule(Schedule schedule, User user) {
		String scheduleId = schedule.getScheduleId();
		scheduleDao.removeScheduleSubData(scheduleId);

		convertToServerTimezone(schedule);
		scheduleDao.update(schedule);
		_createSchedule(schedule, scheduleId);
		
		if(!StringUtil.isEmpty(schedule.getCartooletcId())) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("roomOrTool", "tool");
			param.put("scheduleId", schedule.getScheduleId());
			Approve approveInfo1 = approveService.getApproveMap(param);
			if(approveInfo1 != null){
				if(!approveInfo1.getApproveStatus().equals("W")) {	//승인 대기 상태가 아니면 승인 대기로 수정
					Cartooletc cartooletc = cartooletcService.read(schedule.getCartooletcId());
					if(cartooletc.getManagerId() != null && cartooletc.getAutoApprove().equals("N")) {//자원예약이 자동승인이 아닐경우
						approveInfo1.setApproveStatus("W");
						approveInfo1.setUpdaterId(user.getUserId());
						approveInfo1.setUpdaterName(user.getUserName());
						approveInfo1.setMeetingRoomId(schedule.getCartooletcId());
						approveInfo1.setRoomOrTool("tool");
						approveService.update(approveInfo1);
						if( "N".equals(cartooletc.getAutoApprove()) && !StringUtil.isEmpty(cartooletc.getManagerId())){
							User manager = userService.read(cartooletc.getManagerId());
							reserveService.sendReservationMail("car",  "[자원 예약 승인요청]" , schedule, user,  manager);
						}
					}else{//자동승인일경우
						approveInfo1.setUpdaterId(user.getUserId());
						approveInfo1.setUpdaterName(user.getUserName());
						approveInfo1.setMeetingRoomId(schedule.getCartooletcId());
						approveInfo1.setRoomOrTool("tool");
						approveService.update(approveInfo1);
					}
				}else{
					Cartooletc cartooletc = cartooletcService.read(schedule.getCartooletcId());
					if(cartooletc.getManagerId() != null && cartooletc.getAutoApprove().equals("N")) {//자원예약이 자동승인이 아닐경우
						approveInfo1.setApproveStatus("W");
						approveInfo1.setUpdaterId(user.getUserId());
						approveInfo1.setUpdaterName(user.getUserName());
						approveInfo1.setMeetingRoomId(schedule.getCartooletcId());
						approveInfo1.setRoomOrTool("tool");
						approveService.update(approveInfo1);
						if( "N".equals(cartooletc.getAutoApprove()) && !StringUtil.isEmpty(cartooletc.getManagerId())){
							User manager = userService.read(cartooletc.getManagerId());
							reserveService.sendReservationMail("car",  "[자원 예약 승인요청]" , schedule, user,  manager);
						}
					}else{//자동승인일경우
						approveInfo1.setUpdaterId(user.getUserId());
						approveInfo1.setUpdaterName(user.getUserName());
						approveInfo1.setMeetingRoomId(schedule.getCartooletcId());
						approveInfo1.setRoomOrTool("tool");
						approveService.update(approveInfo1);
					}
				}
			}else{//기존에자원예약이 없을 경우 새로 매핑된 자원을 스케줄에 넣어줌
				Cartooletc cartooletc = cartooletcService.read(schedule.getCartooletcId());
				
				Approve approve = new Approve();
				
				approve.setScheduleId(scheduleId);
				approve.setMeetingRoomId(schedule.getCartooletcId());
				approve.setApproveStatus("A");
				ModelBeanUtil.bindRegisterInfo(approve, user.getUserId(), user.getUserName());
				
				if(!StringUtil.isEmpty(cartooletc.getManagerId()) && "N".equals(cartooletc.getAutoApprove()))
					approve.setApproveStatus("W");
				
				approveService.create(approve);
			}
		}else{
			//System.out.println("##############################schedule.getMeetingRoomId():"+schedule.getMeetingRoomId());
			Map<String, String> params =new HashMap<String, String>();
			//System.out.println("##############################schedule.getScheduleId():"+schedule.getScheduleId());
			params.put("scheduleId", schedule.getScheduleId());
			params.put("roomOrTool", "tool");
			//System.out.println("##############################params.get(scheduleId):"+params.get("scheduleId"));
			approveService.delete(params);
		}
		
		// 회의실이고 승인된 건이면 회의실 관리자를 조회해 있으면 비승인 상태로 변경해줌
		//System.out.println("##############################schedule.getMeetingRoomId():"+schedule.getMeetingRoomId());
		if(!StringUtil.isEmpty(schedule.getMeetingRoomId())) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("roomOrTool", "room");
			param.put("scheduleId", schedule.getScheduleId());
			Approve approveInfo = approveService.getApproveMap(param);
			//Approve approveInfo = approveService.getApprove(schedule.getScheduleId());
			if(approveInfo != null){
				if(!approveInfo.getApproveStatus().equals("W")) {	//승인 대기 상태가 아니면 승인 대기로 수정
					MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
					if(meetingRoom.getManagerId() != null && meetingRoom.getAutoApprove().equals("N")) {//회의실예약이 자동승인이 아닐경우
						approveInfo.setApproveStatus("W");
						approveInfo.setUpdaterId(user.getUserId());
						approveInfo.setUpdaterName(user.getUserName());
						approveInfo.setMeetingRoomId(schedule.getMeetingRoomId());
						approveInfo.setRoomOrTool("room");
						approveService.update(approveInfo);
						if( "N".equals(meetingRoom.getAutoApprove()) && !StringUtil.isEmpty(meetingRoom.getManagerId())){
							User manager = userService.read(meetingRoom.getManagerId());
							reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
						}
					}else{//자동승인일경우
						approveInfo.setUpdaterId(user.getUserId());
						approveInfo.setUpdaterName(user.getUserName());
						approveInfo.setMeetingRoomId(schedule.getMeetingRoomId());
						approveInfo.setRoomOrTool("room");
						approveService.update(approveInfo);
					}
				}else{
					MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
					if(meetingRoom.getManagerId() != null && meetingRoom.getAutoApprove().equals("N")) {//회의실예약이 자동승인이 아닐경우
						approveInfo.setApproveStatus("W");
						approveInfo.setUpdaterId(user.getUserId());
						approveInfo.setUpdaterName(user.getUserName());
						approveInfo.setMeetingRoomId(schedule.getMeetingRoomId());
						approveInfo.setRoomOrTool("room");
						approveService.update(approveInfo);
						if( "N".equals(meetingRoom.getAutoApprove()) && !StringUtil.isEmpty(meetingRoom.getManagerId())){
							User manager = userService.read(meetingRoom.getManagerId());
							reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
						}
					}else{//자동승인일경우
						approveInfo.setUpdaterId(user.getUserId());
						approveInfo.setUpdaterName(user.getUserName());
						approveInfo.setMeetingRoomId(schedule.getMeetingRoomId());
						approveInfo.setRoomOrTool("room");
						approveService.update(approveInfo);
					}
				}
			}else{//기존에 회의실예약이 없을 경우 새로 매핑된 회의실을 스케줄에 넣어줌
				MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
				
				Approve approve = new Approve();
				
				approve.setScheduleId(scheduleId);
				approve.setMeetingRoomId(schedule.getMeetingRoomId());
				approve.setApproveStatus("A");
				ModelBeanUtil.bindRegisterInfo(approve, user.getUserId(), user.getUserName());
				
				if(!StringUtil.isEmpty(meetingRoom.getManagerId()) && "N".equals(meetingRoom.getAutoApprove()))
					approve.setApproveStatus("W");
				
				approveService.create(approve);
			}
		}else{
			//System.out.println("##############################schedule.getMeetingRoomId():"+schedule.getMeetingRoomId());
			Map<String, String> params =new HashMap<String, String>();
			//System.out.println("##############################schedule.getScheduleId():"+schedule.getScheduleId());
			params.put("scheduleId", schedule.getScheduleId());
			params.put("roomOrTool", "room");
			//System.out.println("##############################params.get(scheduleId):"+params.get("scheduleId"));
			approveService.delete(params);
		}

		// 첨부파일
		List<FileLink> fileLinkList = schedule.getFileLinkList();
		//System.out.println("##############################");
		if (user != null && fileLinkList != null && fileLinkList.size() > 0) {
			//System.out.println("##############################");
			fileService.saveFileLink(fileLinkList, scheduleId, IKepConstant.ITEM_TYPE_CODE_PLANNER, user);
		}
		
		// 푸쉬 발송
		List<Participant> participantList = schedule.getParticipantList();
		
		//PUSH 설정
		BufferedReader br = null;
		HttpClient client = new DefaultHttpClient();
		Properties prop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String pushBaseUrl = prop.getProperty("ikep4.push.baseUrl");
		HttpPost request = new HttpPost(pushBaseUrl+"/rest/push/insertPushByItemId");
        request.addHeader("content-type", "application/json");
        
		if(participantList!=null) {
			for(Participant entity : participantList) {
				try {
			        JSONObject json = new JSONObject();
			        json.put("portalId", "P000001");
			        json.put("userId", entity.getTargetUserId());
			        json.put("pushType", "P");
			        json.put("alertTitle", "일정");
			        json.put("alertText", schedule.getTitle());
			        json.put("itemId", schedule.getScheduleId());
			        StringEntity input = new StringEntity( json.toString(), "UTF-8");
			        
		        	request.setEntity(input);
		        	HttpResponse response = client.execute(request);
		        	
		        	//to use client again, should read the response content and close the stream.
		            //since you won't use the response content, just close the stream
		            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            br.close();
		            
		        	//verify the valid error code first
		            System.out.println("returnCode : "+response.getStatusLine().getStatusCode());
		
				} catch (Exception e) {
					log.error("Error occurs during send push messages : "+e.getMessage());
				} 
			}
			client.getConnectionManager().shutdown();
		} 
	}

	public void updateSchedule(Schedule schedule) {
		updateSchedule(schedule, null);
	}

	
	public void updateCompanyScheduleApprove(String[] scheduleIdList) {
		scheduleDao.updateCompanyScheduleApprove(scheduleIdList);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#updateSchedule
	 * (com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO)
	 */
	public String updateScheduleNew(UpdateScheduleVO data) throws ParseException {
		int updateType = data.getUpdateType();
		//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%updateType:"+updateType);
		Schedule newSchedule = data.getNewSchedule();
		User user = data.getUser();

		newSchedule.assignUser(user);
		
		List<Map<String, Object>> meetingRoomReserveList = null;
		if(newSchedule.getMeetingRoomId() != null && !newSchedule.getMeetingRoomId().equals("")) {	// 회의실이 있으면...
			meetingRoomReserveList = reserveService.selectExists(newSchedule);	// 회의실 예약이 중복인지 체크
			if(meetingRoomReserveList.size() > 0) {
				return ReserveController.DUPLICATE;
			}
		}

		if (updateType == 0 || (newSchedule.getMeetingRoomId() != null && !newSchedule.getMeetingRoomId().equals(""))) {	// 반복인지 아닌지 확인 : 0 이면 반복 아님
			/**
			 *  회의실에서 수정의 경우 0으로 넘어옴
			 *  updateSchedule 메서드에서 기존의 반복 정보 및 참여/참조자, 알람 정보를 삭제하고 새로 입력하므로 문제 없음.
			 */
			updateSchedule(newSchedule, user);
			return "";
		}

		EventInfoVO eventInfo = data.getEventInfo();
		String scheduleId = newSchedule.getScheduleId();

		convertToServerTimezone(eventInfo);
		getEventSibling(eventInfo);

		Date prevStart = eventInfo.getPrevStart();
		Date prevEnd = eventInfo.getPrevEnd();
		Date nextStart = eventInfo.getNextStart();
		Date nextEnd = eventInfo.getNextEnd();
		Date repeatStartDate = eventInfo.getRepeatStartDate();
		Date repeatEndDate = eventInfo.getRepeatEndDate();
		Date start = eventInfo.getStart();
		Date end = eventInfo.getEnd();
		String newScheduleId = null;
		boolean isFirstEvent = DateUtils.truncate(repeatStartDate, Calendar.DATE).compareTo(
				DateUtils.truncate(prevStart, Calendar.DATE)) > 0;
		boolean isLastEvent = DateUtils.truncate(repeatEndDate, Calendar.DATE).compareTo(
				DateUtils.truncate(nextStart, Calendar.DATE)) < 0;

		if (updateType == 1) { // 이번 일정만
			if (isFirstEvent && isLastEvent) { // event가 하나밖에 없는 반복일정
				int count = scheduleDao.getCountRepeatEvents(scheduleId);
				if (count == 1) {
					deleteSchedule(scheduleId);
				} else {
					scheduleDao.deleteCurrenctRepeat(scheduleId, start);
				}
			} else if (isFirstEvent) {
				int count = scheduleDao.isFirstRepeatRow(scheduleId, repeatStartDate);
				if (count == 0) {
					scheduleDao.updateScheduleDate(scheduleId, nextStart, nextEnd);
				}
				scheduleDao.shiftNextRepeatDate(scheduleId, nextStart, nextEnd);
			} else if (isLastEvent) {
				scheduleDao.shiftPrevRepeatDate(scheduleId, prevStart, prevEnd);
			} else {
				scheduleDao.insertNextRecurrence(scheduleId, nextStart, nextEnd, start);
				scheduleDao.updatePrevRecurrenceEndDate(scheduleId, prevStart, prevStart);
			}
			newSchedule.setRepeat(0);
			newSchedule.setRecurrences(null);
			createByCopy(newSchedule, user);
		} else if (updateType == 2) { // 향후 모든 일정
			/**
			 * getIsDirtyRepeat 적용전 소스
			 * scheduleDao.shiftPrevRepeatDate(scheduleId, prevStart,
			 * prevStart); scheduleDao.deleteAfterRepeat(scheduleId, prevStart);
			 * createByCopy(newSchedule, user); if(isFirstEvent || isLastEvent)
			 * { int count = scheduleDao.getCountRepeatEvents(scheduleId);
			 * if(count == 0) { deleteSchedule(scheduleId); } }
			 */
			if ("yes".equals(data.getIsDirtyRepeat())) {
				scheduleDao.shiftPrevRepeatDate(scheduleId, prevStart, prevStart);
				scheduleDao.deleteAfterRepeat(scheduleId, prevStart);
				createByCopy(newSchedule, user);
			} else { // 내용등만 수정 기존 repeat 활용
				scheduleDao.insertNextRecurrence(scheduleId, start, end, start);
				scheduleDao.updatePrevRecurrenceEndDate(scheduleId, prevStart, prevStart);
				newSchedule.setRecurrences(null);
				newScheduleId = createByCopy(newSchedule, user);
				scheduleDao.updateAfterRepeatScheduleId(scheduleId, newScheduleId, prevStart);
			}
		} else if (updateType == 3) { // 모든 반복일정 수정	: 회의실 일정인 경우 모든 반복 일정 수정되도록 함.
			if ("yes".equals(data.getIsDirtyRepeat())) {	// 반복 조건이 변경되었으면...
				createByCopy(newSchedule, user);
				deleteSchedule(scheduleId);
			} else {
				newSchedule.setRecurrences(null);
				newScheduleId = createByCopy(newSchedule, user);
				scheduleDao.updateRepeatScheduleId(newScheduleId, scheduleId);
			}
			
			// 회의실이고 승인된 건이면 회의실 관리자를 조회해 있으면 비승인 상태로 변경해줌
			if(newSchedule.getMeetingRoomId() != null) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("roomOrTool", "room");
				param.put("scheduleId", newSchedule.getScheduleId());
				Approve approveInfo = approveService.getApproveMap(param);
				//Approve approveInfo = approveService.getApprove(newSchedule.getScheduleId());
				if(!approveInfo.getApproveStatus().equals("W")) {	//승인 대기 상태가 아니면 승인 대기로 수정
					MeetingRoom meetingRoom = meetingRoomService.read(newSchedule.getMeetingRoomId());
					if(meetingRoom.getManagerId() != null && meetingRoom.getAutoApprove().equals("N")) {
						approveInfo.setApproveStatus("W");
						approveInfo.setUpdaterId(user.getUserId());
						approveInfo.setUpdaterName(user.getUserName());
						approveService.update(approveInfo);
					}
				}
			}
			
			if(newSchedule.getCartooletcId() != null) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("roomOrTool", "tool");
				param.put("scheduleId", newSchedule.getScheduleId());
				Approve approveInfo = approveService.getApproveMap(param);
				if(!approveInfo.getApproveStatus().equals("W")) {	//승인 대기 상태가 아니면 승인 대기로 수정
					Cartooletc cartooletc = cartooletcService.read(newSchedule.getCartooletcId());
					if(cartooletc.getManagerId() != null && cartooletc.getAutoApprove().equals("N")) {
						approveInfo.setApproveStatus("W");
						approveInfo.setUpdaterId(user.getUserId());
						approveInfo.setUpdaterName(user.getUserName());
						approveService.update(approveInfo);
						if( "N".equals(cartooletc.getAutoApprove()) && !StringUtil.isEmpty(cartooletc.getManagerId())){
							User manager = userService.read(cartooletc.getManagerId());
							reserveService.sendReservationMail("car",  "[자원 예약 승인요청]" , newSchedule, user,  manager);
						}
					}
				}
			}
		}
		
		return "";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#updateSchedule
	 * (com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO, boolean)
	 * 사용되지 않음 : 2012.07.02
	 */
	public void updateSchedule(UpdateScheduleVO data, boolean sendmail) {
		try {
			updateSchedule(data);
			if (sendmail) {
				alarmPlannerService.sendSimpleMail("update", data.getNewSchedule());
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#updateSchedule
	 * (com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO, boolean)
	 */
	@Transactional
	public String updateScheduleNew(UpdateScheduleVO data, boolean sendmail) {
		String result = "";
		try {
			result = updateScheduleNew(data);
			if (sendmail) {
				alarmPlannerService.sendSimpleMail("update", data.getNewSchedule());
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		
		return result;
	}

	public void deleteSchedule(String scheduleId) {
		
		//팀일정삭제
		scheduleDao.removeTeamSchedule(scheduleId);
		//SAP일정삭제
		scheduleDao.removeSapSchedule(scheduleId);
		
		scheduleDao.remove(scheduleId);

		fileService.removeItemFile(scheduleId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#deleteSchedule
	 * (java.lang.String, boolean)
	 */
	public void deleteSchedule(String scheduleId, boolean sendmail) {
		Schedule schedule = null;
		if (sendmail) {
			schedule = getScheduleAllData(scheduleId);
		}
		if (schedule != null && sendmail) {
			alarmPlannerService.sendSimpleMail("delete", schedule);
		}
		deleteSchedule(scheduleId);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#deleteSchedule
	 * (com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO)
	 */
	public void deleteSchedule(UpdateScheduleVO params) throws ParseException {
		int deleteType = params.getDeleteType();
		boolean sendmail = params.isSendmail();
		EventInfoVO eventInfo = params.getEventInfo();
		String scheduleId = eventInfo.getScheduleId();
		
		Schedule schedule = null;
		schedule = getScheduleAllData(scheduleId);
		
		if(schedule.getManagerId() != null){
			alarmPlannerService.sendDeleteMail("delete", schedule);
		}
		
		
		if (deleteType == 0 || deleteType == 3) {
			deleteSchedule(scheduleId, sendmail);
			return;
		}

		/*Schedule schedule = null;
		if (sendmail) {
			schedule = getScheduleAllData(scheduleId);
		}*/
		if (schedule != null && sendmail) {
			alarmPlannerService.sendSimpleMail("delete", schedule);
		}
		convertToServerTimezone(eventInfo);
		getEventSibling(eventInfo);

		Date prevStart = eventInfo.getPrevStart();
		Date prevEnd = eventInfo.getPrevEnd();
		Date nextStart = eventInfo.getNextStart();
		Date nextEnd = eventInfo.getNextEnd();
		Date repeatStartDate = eventInfo.getRepeatStartDate();
		Date repeatEndDate = eventInfo.getRepeatEndDate();
		Date start = eventInfo.getStart();

		boolean isFirstEvent = DateUtils.truncate(repeatStartDate, Calendar.DATE).compareTo(
				DateUtils.truncate(prevStart, Calendar.DATE)) > 0;
		boolean isLastEvent = DateUtils.truncate(repeatEndDate, Calendar.DATE).compareTo(
				DateUtils.truncate(nextStart, Calendar.DATE)) < 0;

		if (deleteType == 1) { // 이번 일정만
			// 해당 일정이 반복일정중 첫번째일 경우
			if (isFirstEvent && isLastEvent) {
				int count = scheduleDao.getCountRepeatEvents(scheduleId);
				if (count == 1) {
					deleteSchedule(scheduleId);
				} else {
					scheduleDao.deleteCurrenctRepeat(scheduleId, start);
				}
			} else if (isFirstEvent) {
				scheduleDao.updateScheduleDate(scheduleId, nextStart, nextEnd);
				scheduleDao.shiftNextRepeatDate(scheduleId, nextStart, nextEnd);
			} else if (isLastEvent) {
				scheduleDao.shiftPrevRepeatDate(scheduleId, prevStart, prevEnd);
			} else {
				// 기존 반복일정 복사 및 반복 일자등 수정
				scheduleDao.insertNextRecurrence(scheduleId, nextStart, nextEnd, start);
				scheduleDao.updatePrevRecurrenceEndDate(scheduleId, prevStart, prevStart);
			}
		} else if (deleteType == 2) { // 향후 모든 일정
			// 해당 일정이 반복일정중 첫번째일 경우
			if (isFirstEvent) {
				int count = scheduleDao.getCountRepeatEvents(scheduleId);
				if (count == 1) {
					deleteSchedule(scheduleId);
				} else {
					scheduleDao.deleteAfterRepeat(scheduleId, prevStart);
				}
			} else {
				scheduleDao.deleteAfterRecurrences(scheduleId, prevStart);
				scheduleDao.updatePrevRecurrenceEndDate(scheduleId, prevStart, prevEnd);
			}
		}


	}
	
	public void copyMySchedule(UpdateScheduleVO params, User user) throws ParseException {
		EventInfoVO eventInfo = params.getEventInfo();
		String scheduleId = eventInfo.getScheduleId();
		scheduleDao.copyMySchedule(scheduleId, idgenService.getNextId(), user.getUserId(), user.getUserName());

	}
	
	public void copyTeamSchedule(UpdateScheduleVO params, User user) throws ParseException {
		EventInfoVO eventInfo = params.getEventInfo();
		String scheduleId = eventInfo.getScheduleId();
		scheduleDao.copyTeamSchedule(scheduleId, idgenService.getNextId(), user.getUserId(), user.getUserName(), user.getGroupId());

	}

	public void removeScheduleUpdateDisplay(UpdateScheduleVO params) throws ParseException {
		Schedule newSchedule = params.getNewSchedule();
		String scheduleId = newSchedule.getScheduleId();
		int updateDisplay = newSchedule.getUpdateDisplay();
		scheduleDao.updateScheduleUpdateDisplay(scheduleId, ""+updateDisplay);
	}
	public void scheduleMove(Map<String, Object> params) throws ParseException, SQLException {
		scheduleDao.scheduleMove(params);
	}

	public void scheduleMoveAllday(Map<String, Object> params) throws ParseException, SQLException {
		scheduleDao.scheduleMoveAllday(params);
	}

	public void scheduleMoveTime(Map<String, Object> params) throws ParseException, SQLException {

		// Date startDate =
		// timeZoneSupportService.convertServerTimeZone(params.get("startDate").toString(),"yyyyMMdd hh24:mm");
		// Date endDate =
		// timeZoneSupportService.convertServerTimeZone(params.get("endDate").toString(),"yyyyMMdd hh24:mm");

		Date startDate = timeZoneSupportService.convertServerTimeZone(params.get("startDate").toString(),
				"yyyyMMdd HH:mm:ss");
		Date endDate = timeZoneSupportService.convertServerTimeZone(params.get("endDate").toString(),
				"yyyyMMdd HH:mm:ss");

		// startDate = timeZoneSupportService.convertServerTimeZone(startDate);
		// endDate = timeZoneSupportService.convertServerTimeZone(endDate);

		params.put("startDate", startDate);
		params.put("endDate", endDate);

		scheduleDao.scheduleMoveTime(params);
	}

	@Override
	public Schedule read(String id) {
		return scheduleDao.get(id);
	}

	@SuppressWarnings("unused")
	private void copySchedule(String scheduleId, Date start, Date end) {
		scheduleDao.copySchedule(scheduleId, idgenService.getNextId(), start, end);
	}

	public void scheduleResize(Map<String, Object> params) {
		scheduleDao.scheduleResize(params);
	}

	public Mandator getTrustee(String userId) {
		return scheduleDao.getTrustee(userId);
	}

	public Mandator createMandator(User user, String portalId, String trusteeId) {
		Mandator mandator = new Mandator();
		Date now = new Date();

		mandator.setMandatorId(user.getUserId());
		mandator.setRegisterId(user.getUserId());
		mandator.setRegisterName(user.getUserName());
		mandator.setPortalId(portalId);
		mandator.setRegistDate(now);
		mandator.setStartDate(now);
		mandator.setTrusteeId(trusteeId);

		mandator.setEndDate(Schedule.toDate("99991231"));
		scheduleDao.createMandator(mandator);

		return mandator;
	}

	public void deleteMandator(String mandatorId) {
		scheduleDao.deleteMandator(mandatorId);
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getMyMandators(String trusteeId) {
		return scheduleDao.getMyMandators(trusteeId);
	}

	public String createHoliday(User user, String portalId, Holiday holiday) {
		String id = idgenService.getNextId();
		String ds = holiday.getHolidayDateStr();

		if (ds != null) {
			ds = ds.replaceAll("[.|-]", "");
			Date d = Schedule.toDate(ds);
			holiday.setHolidayDate(d);
		}

		holiday.setHolidayId(id);
		holiday.setPortalId(portalId);
		holiday.setRegisterId(user.getUserId());
		holiday.setRegisterName(user.getUserName());

		scheduleDao.createHoliday(holiday);

		return id;
	}

	public Holiday getHoliday(String holidayId) {
		return scheduleDao.getHoliday(holidayId);
	}

	@SuppressWarnings("unchecked")
	public List<Holiday> getHolidayList() {
		return scheduleDao.getHolidayList();
	}


	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#getHolidayList
	 * (long, long, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public List<Map> getHolidayList(long start, long end, String nationCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date startDate = new Date(start);
		Date endDate = new Date(end);

		params.put("start", startDate);
		params.put("end", endDate);
		params.put("nation", nationCode);
		
		if(startDate.getMonth() > endDate.getMonth()) {
			params.put("isTwoYear", true);
		} else {
			params.put("isTwoYear", false);
		}

		return scheduleDao.getHolidayList(params);
	}

	public void updateHoliday(Holiday holiday) {
		String ds = holiday.getHolidayDateStr();

		if (ds != null) {
			ds = ds.replaceAll("[.|-]", "");
			Date d = Schedule.toDate(ds);
			holiday.setHolidayDate(d);
		}

		scheduleDao.updateHoliday(holiday);
	}

	public void deleteHoliday(String[] holidayIdList) {
		scheduleDao.deleteHoliday(holidayIdList);
	}

	public Schedule getScheduleAllData(String scheduleId, String locale) {
		Schedule schedule = scheduleDao.get(scheduleId, locale);
		List<Participant> participantList = participantDao.getList(scheduleId);
		List<Alarm> alarmList = alarmDao.getList(scheduleId);

		schedule.setParticipantList(participantList);
		schedule.setAlarmList(alarmList);

		schedule.setFileDataList(fileService.getItemFile(scheduleId, "N"));

		convertToClientTimezone(schedule);
		return schedule;
	}

	public Schedule getScheduleAllData(String scheduleId) {
		Schedule schedule = scheduleDao.get(scheduleId);
		List<Participant> participantList = participantDao.getList(scheduleId);
		List<Alarm> alarmList = alarmDao.getList(scheduleId);

		schedule.setParticipantList(participantList);
		schedule.setAlarmList(alarmList);

		schedule.setFileDataList(fileService.getItemFile(scheduleId, "N"));

		return schedule;
	}

	public Map<String, Object> getScheduleSubInfo(String scheduleId) {
		Map<String, Object> res = new HashMap<String, Object>();

		Schedule schedule = new Schedule();
		schedule.setScheduleId(scheduleId);

		fileService.getItemFile(scheduleId, "N");

		res.put("participantList", participantDao.getList(scheduleId));
		res.put("alarmList", alarmDao.getList(scheduleId));
		res.put("fileDataList", fileService.getItemFile(scheduleId, "N"));

		return res;
	}

	protected void getEventSibling(EventInfoVO eventInfo) throws ParseException {
		Date start = eventInfo.getStartDate();
		Date end = eventInfo.getEndDate();
		Date updateStart = eventInfo.getUpdateStart();
		Date updateEnd = eventInfo.getUpdateEnd();

		int interval = eventInfo.getRepeatPeriod();
		int rtype = eventInfo.getRepeatType();
		String roption = eventInfo.getRepeatPeriodOption();
		String[] ra = StringUtils.isNotEmpty(roption) ? roption.split(",") : null;

		Date prevStart = null, prevEnd = null, nextStart = null, nextEnd = null;

		if (rtype == 1) { // daily
			prevStart = DateUtils.addDays(start, -interval);
			nextStart = DateUtils.addDays(start, interval);
		} else if (rtype == 2 && ra.length == 1) { // weekly - 단수요일
			prevStart = DateUtils.addWeeks(start, -interval);
			nextStart = DateUtils.addWeeks(start, interval);
		} else if (rtype == 2 && ra.length > 1) { // weekly - 복수요일
			Date repeatStartDate = eventInfo.getRepeatStartDate();
			prevStart = weeklyMultiType(start, -interval, ra, repeatStartDate);
			nextStart = weeklyMultiType(start, interval, ra, repeatStartDate);
		} else if (rtype == 3 && "a".equals(ra[0])) { // 특정일자
			int day = Integer.valueOf(ra[1]);
			prevStart = monthlyAtype(start, -interval, day);
			nextStart = monthlyAtype(start, interval, day);
		} else if (rtype == 3 && "b".equals(ra[0])) { // 몇번째 요일
			int dowm = Integer.valueOf(ra[1]);
			int dow = Integer.valueOf(ra[2]);

			prevStart = monthlyBtype(start, -interval, dowm, dow);
			nextStart = monthlyBtype(start, interval, dowm, dow);
		} else if (rtype == 3 && "c".equals(ra[0])) { // 마지막 요일
			int dow = Integer.valueOf(ra[1]);

			prevStart = monthlyCtype(start, -interval, dow);
			nextStart = monthlyCtype(start, interval, dow);
		} else if (rtype == 3 && "d".equals(ra[0])) { // 마지막 일자
			prevStart = monthlyDtype(start, -interval);
			nextStart = monthlyDtype(start, interval);
		} else if (rtype == 4 && "a".equals(ra[0])) { // n년마다 특정일자
			int day = Integer.valueOf(ra[1]);
			int mon = Integer.valueOf(ra[2]);

			prevStart = yearlyAtype(start, -interval, mon, day);
			nextStart = yearlyAtype(start, interval, mon, day);
		} else if (rtype == 4 && "b".equals(ra[0])) { // n년마다 몇번째 요일
			int cnt = Integer.valueOf(ra[1]);
			int dow = Integer.valueOf(ra[2]);
			int mon = Integer.valueOf(ra[3]);

			prevStart = yearlyBtype(start, -interval, cnt, dow, mon);
			nextStart = yearlyBtype(start, interval, cnt, dow, mon);
		} else if (rtype == 4 && "c".equals(ra[0])) { // n년마다 마지막 요일
			int dow = Integer.valueOf(ra[1]);
			int mon = Integer.valueOf(ra[2]);

			prevStart = yearlyCtype(start, -interval, dow, mon);
			nextStart = yearlyCtype(start, interval, dow, mon);
		} else if (rtype == 4 && "d".equals(ra[0])) { // n년마다 마지막 일자
			prevStart = yearlyDtype(start, -interval);
			nextStart = yearlyDtype(start, interval);
		}

		long diffDate = (end.getTime() - start.getTime());
		prevEnd = new Date(prevStart.getTime() + diffDate);
		nextEnd = new Date(nextStart.getTime() + diffDate);

		eventInfo.setPrevStart(prevStart);
		eventInfo.setPrevEnd(prevEnd);

		eventInfo.setStart(start);
		eventInfo.setEnd(end);
		eventInfo.setNextStart(nextStart);
		eventInfo.setNextEnd(nextEnd);
		eventInfo.setUpdateStart(updateStart);
		eventInfo.setUpdateEnd(updateEnd);
	}

	public static Date weeklyMultiType(Date d, int interval, String[] ropt, Date repeatStartDate) {
		Calendar c = Calendar.getInstance();
		String[] sropt = (String[]) ArrayUtils.clone(ropt);
		Arrays.sort(sropt);
		int len = ropt.length;
		int dow = Utils.getDow(d);
		int idx = Arrays.binarySearch(sropt, String.valueOf(dow));

		if (idx == 0) {
			if (interval < 0) {
				Date currentBaseDate = Utils.getSameDowDate(repeatStartDate, d);
				Date targetDate = DateUtils.addWeeks(currentBaseDate, interval);
				c.setTime(targetDate);
				c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(sropt[len - 1]));
			} else if (interval > 0) {
				c.setTime(d);
				c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(sropt[idx + 1]));
			}
		} else if (idx == len - 1) {
			if (interval < 0) {
				c.setTime(d);
				c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(sropt[idx - 1]));
			} else if (interval > 0) {
				Date currentBaseDate = Utils.getSameDowDate(repeatStartDate, d);
				Date targetDate = DateUtils.addWeeks(currentBaseDate, interval);
				c.setTime(targetDate);
				c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(sropt[0]));
			}
		} else {
			c.setTime(d);
			if (interval < 0) {
				c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(sropt[idx - 1]));
			} else if (interval > 0) {
				c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(sropt[idx + 1]));
			}
		}

		return c.getTime();
	}

	private Date monthlyAtype(Date d, int interval, int day) {
		Date td = DateUtils.addMonths(d, interval);
		td = DateUtils.setDays(td, day);
		return td;
	}

	private Date monthlyBtype(Date d, int interval, int cnt, int dow) {
		Date td = DateUtils.addMonths(d, interval);
		Calendar c = Calendar.getInstance();
		c.setTime(td);
		c.set(Calendar.DAY_OF_WEEK, dow);
		c.set(Calendar.DAY_OF_WEEK_IN_MONTH, cnt);

		return c.getTime();
	}

	/**
	 * 해당일자 interval 월의 마지막 요일(dow) 날짜 계산
	 * 
	 * @param d
	 * @param interval
	 * @param dow
	 * @return
	 */
	private Date monthlyCtype(Date d, int interval, int dow) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, interval);

		int last = c.getActualMaximum(Calendar.DATE);
		c.set(Calendar.DATE, last);
		int cdow = c.get(Calendar.DAY_OF_WEEK);

		int diff = cdow - dow;
		if (diff < 0) {
			diff += 7;
		}
		c.add(Calendar.DATE, -diff);

		return c.getTime();
	}

	private Date monthlyDtype(Date d, int interval) {
		Date td = DateUtils.addMonths(d, interval);
		Calendar c = Calendar.getInstance();
		c.setTime(td);
		int last = c.getActualMaximum(Calendar.DATE);
		c.set(Calendar.DATE, last);

		return c.getTime();
	}

	private Date yearlyAtype(Date d, int interval, int mon, int day) {
		Date td = DateUtils.addYears(d, interval);
		td = DateUtils.setMonths(td, mon - 1);
		td = DateUtils.setDays(td, day);
		return td;
	}

	private Date yearlyBtype(Date d, int interval, int cnt, int dow, int mon) {
		Date td = DateUtils.addYears(d, interval);
		Calendar c = Calendar.getInstance();
		c.setTime(td);
		c.set(Calendar.MONTH, mon - 1);
		c.set(Calendar.DAY_OF_WEEK, dow);
		c.set(Calendar.DAY_OF_WEEK_IN_MONTH, cnt);

		return c.getTime();
	}

	private Date yearlyCtype(Date d, int interval, int dow, int mon) {
		Date td = DateUtils.addYears(d, interval);
		Calendar c = Calendar.getInstance();
		c.setTime(td);
		c.set(Calendar.MONTH, mon - 1);

		int last = c.getActualMaximum(Calendar.DATE);
		c.set(Calendar.DATE, last);
		int cdow = c.get(Calendar.DAY_OF_WEEK);

		int diff = cdow - dow;
		if (diff < 0) {
			diff += 7;
		}
		c.add(Calendar.DATE, -diff);

		return c.getTime();
	}

	private Date yearlyDtype(Date d, int interval) {
		Date td = DateUtils.addYears(d, interval);
		Calendar c = Calendar.getInstance();
		c.setTime(td);
		int last = c.getActualMaximum(Calendar.DATE);
		c.set(Calendar.DATE, last);

		return c.getTime();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#updateAcceptInfo
	 * (java.util.Map)
	 */
	public void updateAcceptInfo(Map<String, String> params) {
		participantDao.updateAcceptInfo(params);
	}

	/**
	 * 일정 시각을 서버 시간대의 시각으로 변환
	 * 
	 * @param schedule
	 */
	protected void convertToServerTimezone(Schedule schedule) {
		Date start, end, clientNow = timeZoneSupportService.convertTimeZone();
		boolean allDay = schedule.getWholeday() == 1 ? true : false;

		if (allDay) {
			start = convertToServerTimezoneOnlyDate(schedule.getStartDate(), clientNow);
			end = convertToServerTimezoneOnlyDate(schedule.getEndDate(), clientNow);
		} else {
			start = timeZoneSupportService.convertServerTimeZone(schedule.getStartDate());
			end = timeZoneSupportService.convertServerTimeZone(schedule.getEndDate());
		}
		schedule.setStartDate(start);
		schedule.setEndDate(end);

		List<Recurrences> list = schedule.getRecurrences();
		Date rptStart, rptEnd, sdStart, sdEnd;
		if (list != null && list.size() > 0) {
			Recurrences recurr = list.get(0);

			rptStart = convertToServerTimezoneOnlyDate(recurr.getStartDate(), clientNow);
			rptEnd = convertToServerTimezoneOnlyDate(recurr.getEndDate(), clientNow);
			if (allDay) {
				sdStart = convertToServerTimezoneOnlyDate(recurr.getSdStartDate(), clientNow);
				sdEnd = convertToServerTimezoneOnlyDate(recurr.getSdEndDate(), clientNow);
			} else {
				sdStart = timeZoneSupportService.convertServerTimeZone(recurr.getSdStartDate());
				sdEnd = timeZoneSupportService.convertServerTimeZone(recurr.getSdEndDate());
			}

			recurr.setStartDate(rptStart);
			recurr.setEndDate(rptEnd);
			recurr.setSdStartDate(sdStart);
			recurr.setSdEndDate(sdEnd);
		}
	}

	/**
	 * 일정정보 시각을 서버 시간대의 시각으로 변환
	 * 
	 * @param eventInfo
	 */
	private void convertToServerTimezone(EventInfoVO eventInfo) {
		Date clientNow = timeZoneSupportService.convertTimeZone();
		Date startDate = eventInfo.getStartDate();
		Date endDate = eventInfo.getEndDate();
		Date updateStart = eventInfo.getUpdateStart();
		Date updateEnd = eventInfo.getUpdateEnd();
		Date repeatStartDate = eventInfo.getRepeatStartDate();

		if (eventInfo.getWholeday() == 1) { // allday event
			startDate = convertToServerTimezoneOnlyDate(startDate, clientNow);
			endDate = convertToServerTimezoneOnlyDate(endDate, clientNow);
			if (updateStart != null) {
				updateStart = convertToServerTimezoneOnlyDate(updateStart, clientNow);
				updateEnd = convertToServerTimezoneOnlyDate(updateEnd, clientNow);
			}
		} else {
			startDate = timeZoneSupportService.convertServerTimeZone(startDate);
			endDate = timeZoneSupportService.convertServerTimeZone(endDate);
			if (updateStart != null) {
				updateStart = timeZoneSupportService.convertServerTimeZone(updateStart);
				updateEnd = timeZoneSupportService.convertServerTimeZone(updateEnd);
			}
		}
		if (repeatStartDate != null) {
			repeatStartDate = convertToServerTimezoneOnlyDate(repeatStartDate, clientNow);
		}

		eventInfo.setStartDate(startDate);
		eventInfo.setEndDate(endDate);
		eventInfo.setUpdateStart(updateStart);
		eventInfo.setUpdateEnd(updateEnd);
		eventInfo.setRepeatStartDate(repeatStartDate);
	}

	/**
	 * 일정 시각을 사용자 시간대의 시각으로 변환
	 * 
	 * @param schedule
	 */
	protected void convertToClientTimezone(Schedule schedule) {
		Date start, end, now = timeZoneSupportService.currentServerTime();
		boolean allDay = schedule.getWholeday() == 1 ? true : false;

		if (allDay) {
			start = convertToClientTimeZoneOnlyDate(schedule.getStartDate(), now);
			end = convertToClientTimeZoneOnlyDate(schedule.getEndDate(), now);
		} else {
			start = timeZoneSupportService.convertTimeZone(schedule.getStartDate());
			end = timeZoneSupportService.convertTimeZone(schedule.getEndDate());
		}
		schedule.setStartDate(start);
		schedule.setEndDate(end);

		List<Recurrences> list = schedule.getRecurrences();
		Date rptStart, rptEnd, sdStart, sdEnd;
		if (list != null && list.size() > 0) {
			Recurrences recurr = list.get(0);

			rptStart = convertToClientTimeZoneOnlyDate(recurr.getStartDate(), now);
			rptEnd = convertToClientTimeZoneOnlyDate(recurr.getEndDate(), now);
			if (allDay) {
				sdStart = convertToClientTimeZoneOnlyDate(recurr.getSdStartDate(), now);
				sdEnd = convertToClientTimeZoneOnlyDate(recurr.getSdEndDate(), now);
			} else {
				sdStart = timeZoneSupportService.convertTimeZone(recurr.getSdStartDate());
				sdEnd = timeZoneSupportService.convertTimeZone(recurr.getSdEndDate());
			}

			recurr.setStartDate(rptStart);
			recurr.setEndDate(rptEnd);
			recurr.setSdStartDate(sdStart);
			recurr.setSdEndDate(sdEnd);
		}
	}

	protected Date convertToServerTimezoneOnlyDate(Date d, Date t) {
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

	protected void convertToClientTimezone(List<Schedule> scheduleList) {
		Date now = timeZoneSupportService.currentServerTime();

		for (Schedule schedule : scheduleList) {
			int wholeday = schedule.getWholeday();
			if (wholeday == 1) {
				schedule.setStartDate(convertToClientTimeZoneOnlyDate(schedule.getStartDate(), now));
				schedule.setEndDate(convertToClientTimeZoneOnlyDate(schedule.getEndDate(), now));
			} else {
				schedule.setStartDate(timeZoneSupportService.convertTimeZone(schedule.getStartDate()));
				schedule.setEndDate(timeZoneSupportService.convertTimeZone(schedule.getEndDate()));
			}

			for (Recurrences recurrence : schedule.getRecurrences()) {
				recurrence.setStartDate(convertToClientTimeZoneOnlyDate(recurrence.getStartDate(), now));
				recurrence.setEndDate(convertToClientTimeZoneOnlyDate(recurrence.getEndDate(), now));

				recurrence.setSdStartDate(convertToClientTimeZoneOnlyDate(recurrence.getSdStartDate(), now));
				recurrence.setSdEndDate(convertToClientTimeZoneOnlyDate(recurrence.getSdEndDate(), now));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#getMandator
	 * (java.lang.String)
	 */
	public User getMandator(String mandatorId) {
		return scheduleDao.getMandator(mandatorId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#getUserInfo
	 * (java.lang.String)
	 */
	public Map<String, String> getUserInfo(String userId) {
		return scheduleDao.getUserInfo(userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#findUser(java
	 * .lang.String)
	 */
	public Map<String, Object> findUserByName(String userName) {
		List<String> list = scheduleDao.findUserByName(userName, 2);
		Map<String, Object> result = new HashMap<String, Object>();
		int count = list.size();

		if (count == 1) {
			result.put("userId", list.get(0));
		}
		result.put("count", count);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#isExistName
	 * (java.lang.String, java.lang.String)
	 */
	public boolean isExistName(String nation, String holidayName) {
		return scheduleDao.isExistName(nation, holidayName);
	}

	enum VAlarmType {
		AUDIO, DISPLAY, EMAIL, PROCEDURE
	}

	/**
	 * VCalendar의 alarm정보를 ikep alarm정보로 변환하여 반환
	 * 
	 * @param iAlarm
	 * @return alarm
	 */
	private List<Alarm> iAlarm2alarm(List<IAlarm> ialarmList) {
		List<Alarm> alarmList = null;
		if (ialarmList != null) {
			alarmList = new ArrayList<Alarm>();
			for (IAlarm ialarm : ialarmList) {
				Alarm alarm = new Alarm();
				String alarmType = "0"; // email
				String alarmTime = "30"; // 30분 전

				/*VAlarmType type = VAlarmType.valueOf(ialarm.getAction());
				switch (type) {
					case AUDIO: alarmType = "1"; break; // sms
					case DISPLAY: alarmType = "2"; break; // memo
					case PROCEDURE: alarmType = "2"; break; // memo
				}*/

				String alarmString = ialarm.getTrigger();
				if(alarmString.indexOf("-") == 0	// 해당 일정 시각 이전 알람인 경우만 적용
						&& alarmString.substring(alarmString.length()-1).equalsIgnoreCase("M")) {	// 분단위 설정인 경우만 적용
					alarmString = alarmString.substring(3, alarmString.length() - 1);
	
					Integer alarmMinute = new Integer(alarmString);
					if (alarmMinute > 30 && alarmMinute <= 60)
						alarmTime = "60"; // 1시간 전
					else if (alarmMinute > 60 && alarmMinute <= 120)
						alarmTime = "120"; // 2시간 전
					else if (alarmMinute > 120)
						alarmTime = "1440"; // 하루 전
	
					alarm.setAlarmType(alarmType);
					alarm.setAlarmTime(alarmTime);
					
					alarmList.add(alarm);
				}
			}
		}
		
		return alarmList;
	}

	/**
	 * ikep schedule의 알람 설정을 ICalendar 알람으로 변경
	 * 
	 * @param alarmList
	 * @return
	 */
	private List<IAlarm> alarms2ialarms(List<Alarm> alarmList, User user) {
		List<IAlarm> ialarmList = null;

		if (alarmList != null && alarmList.size() > 0) {
			ialarmList = new ArrayList<IAlarm>();
			boolean isDuplicate;
			List<Integer> alarmTimeList = new ArrayList<Integer>();
			for (Alarm alarm : alarmList) {
				isDuplicate = false;
				int time = new Integer(alarm.getAlarmTime());

				for (int alarmTime : alarmTimeList) { // 중복시간 체크 : alarmType을
														// 체크하지 않으므로 동일 시간에 서로
														// 다른 alarm이 설정 되어 있으면
														// 걸러냄
					if (alarmTime == time) {
						isDuplicate = true;
						break;
					}
				}

				if (isDuplicate == false) {
					alarmTimeList.add(time);

					IAlarm ialarm = new IAlarm();
					ialarm.setSummary("");
					ialarm.setDescription("");
					ialarm.setAction("EMAIL");

					List<IAttendee> iattandeeList = new ArrayList<IAttendee>();
					IAttendee iattendee = new IAttendee();
					iattendee.setMail("MAILTO:" + ( user.getMail() != null && !user.getMail().equals("") ? user.getMail() : user.getUserId()) );
					iattendee.setCn(user.getUserName());
					iattendee.setMember(user.getUserId());
					iattandeeList.add(iattendee);
					ialarm.setAttendeeList(iattandeeList);

					IDur idur = new IDur();
					idur.setMinutes(time * -1);

					ialarm.setIdur(idur);
					// ialarm.setTrigger("-PT" + time + "M");
					ialarmList.add(ialarm);
				}
			}
		}

		return ialarmList;
	}

	enum VRecurType {
		SECONDLY, MINUTELY, HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY
	}

	enum VRecurWeek {
		SU, MO, TU, WE, TH, FR, SA
	}

	/**
	 * VCalendar의 반복 정보를 ikep recurrences정보로 변환하여 반환
	 * 
	 * @param iRecur
	 * @return recurrences
	 */
	@SuppressWarnings("deprecation")
	private Recurrences iRecur2recurrences(IRecur iRecur, Date startDate, Date endDate) {

		Recurrences recurrences = null;

		if (iRecur != null && iRecur.getFrequency() != null && !iRecur.getFrequency().equals("")) {

			VRecurType type = VRecurType.valueOf(iRecur.getFrequency());

			recurrences = new Recurrences();

			String weekDayStr = "";
			WeekDayList dayList = iRecur.getDayList();
			if (dayList.size() > 0) {
				WeekDay weekDay = null;
				for (int i = 0; i < dayList.size(); i++) {
					weekDay = (WeekDay) dayList.get(i);
					switch (VRecurWeek.valueOf(weekDay.getDay())) {
					case SU:
						weekDayStr += "1,";
						break;
					case MO:
						weekDayStr += "2,";
						break;
					case TU:
						weekDayStr += "3,";
						break;
					case WE:
						weekDayStr += "4,";
						break;
					case TH:
						weekDayStr += "5,";
						break;
					case FR:
						weekDayStr += "6,";
						break;
					case SA:
						weekDayStr += "7,";
						break;
					}
				}
				weekDayStr = weekDayStr.substring(0, weekDayStr.length() - 1);
			}

			switch (type) {
			case DAILY:
				recurrences.setRepeatType("1");
				recurrences.setRepeatPeriodOption("");
				break;
			case WEEKLY:
				recurrences.setRepeatType("2");
				dayList = iRecur.getDayList();
				recurrences.setRepeatPeriodOption(weekDayStr);
				break;
			case MONTHLY:
				recurrences.setRepeatType("3");
				if (iRecur.getSetPosList() == null || iRecur.getSetPosList().size() < 1) {
					// 날짜 기준
					List<Integer> dList = iRecur.getMonthDayList();
					if (dList.size() > 0) {
						recurrences.setRepeatPeriodOption("a," + dList.get(0));
					} else {
						Calendar startCal = Calendar.getInstance();
						startCal.setTime(startDate);
						
						recurrences.setRepeatPeriodOption("a," + startCal.get(Calendar.DAY_OF_MONTH));
					}
				} else {
					// 요일 기준
					recurrences.setRepeatPeriodOption("b," + iRecur.getSetPosList().get(0) + "," + weekDayStr);
				}
				break;
			case YEARLY:
				recurrences.setRepeatType("4");
				if (iRecur.getSetPosList() == null || iRecur.getSetPosList().size() < 1) {
					// 날짜 기준
					List<Integer> dList = iRecur.getMonthDayList();
					List<Integer> mList = iRecur.getMonthList();
					if (dList.size() > 0) {
						recurrences.setRepeatPeriodOption("a," + dList.get(0) + "," + mList.get(0));
					} else {
						Calendar startCal = Calendar.getInstance();
						startCal.setTime(startDate);
						
						recurrences.setRepeatPeriodOption("a," + startCal.get(Calendar.DAY_OF_MONTH) + "," + startCal.get(Calendar.MONTH));
					}
				} else {
					// 요일 기준
					recurrences.setRepeatPeriodOption("b," + iRecur.getSetPosList().get(0) + "," + weekDayStr + ","
							+ (startDate.getMonth() + 1));
				}
				break;

			}

			// 종료일 셋팅
			Date recEndDate = iRecur.getUntil();
			
			if (type.name().equals("DAILY") && iRecur.getCount() > 0) {
				Calendar cal = Calendar.getInstance();

				cal.setTime(startDate);
				cal.add(Calendar.DAY_OF_MONTH, iRecur.getCount() - 1);
				
				recEndDate = cal.getTime();
			}
			
			if (recEndDate == null) {
				recEndDate = new Date(8098, 11, 31); //9998-12-31 00:00:00.000
			}
			
			// 반복횟수 셋팅
			if (iRecur.getInterval() > 0) {
				recurrences.setRepeatPeriod(String.valueOf(iRecur.getInterval()));
			} else {
				recurrences.setRepeatPeriod("1");
			}

			// 기타 일자 셋팅
			recurrences.setStartDate(startDate);
			recurrences.setEndDate(recEndDate);
			recurrences.setSdStartDate(startDate);
			recurrences.setSdEndDate(endDate);
		}

		return recurrences;
	}

	/**
	 * ikep recurrences정보를 VCalendar의 반복 정보로 변환하여 반환
	 * 
	 * @param iRecur
	 * @return recurrences
	 */
	private IRecur recurrences2iRecur(Recurrences recurrences) {

		IRecur irecur = null;

		if (recurrences != null) {

			irecur = new IRecur();

			String repeatType = recurrences.getRepeatType();
			String repeatPeriodOption = recurrences.getRepeatPeriodOption();
			StringTokenizer token = repeatPeriodOption != null && !repeatPeriodOption.equals("") ? new StringTokenizer(
					repeatPeriodOption, ",") : null;
			String tokenStr = "";
			String tokenGubun = "";

			// 매일
			if (repeatType.equals("1")) {
				irecur.setFrequency("DAILY");
				
				Calendar startCal = Calendar.getInstance();
				Calendar endCal = Calendar.getInstance();

				startCal.setTime(recurrences.getStartDate());	// 시작일을 calendar 변수에 세팅
				endCal.setTime(recurrences.getEndDate());		// 종료일을 calendar 변수에 세팅
				
				// 종료일 - 시작일 = 총 걸리는 시간
				long diffMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
				 
				int diffDays = (int) (diffMillis / (24 * 60 * 60 * 1000));	//	밀리 초 단위로 나눠서 일 차이를 구함
				
				irecur.setCount(diffDays + 1);	// 반복 횟 수 (시작일과 종료일자 차이에 하루를 더함.
				
				// 매주
			} else if (repeatType.equals("2")) {
				irecur.setFrequency("WEEKLY");

				// 요일 리스트 셋팅
				WeekDayList dayList = new WeekDayList();
				while (token.hasMoreTokens()) {

					tokenStr = token.nextToken();

					if (tokenStr.equals("1")) {
						dayList.add(WeekDay.SU);
					} else if (tokenStr.equals("2")) {
						dayList.add(WeekDay.MO);
					} else if (tokenStr.equals("3")) {
						dayList.add(WeekDay.TU);
					} else if (tokenStr.equals("4")) {
						dayList.add(WeekDay.WE);
					} else if (tokenStr.equals("5")) {
						dayList.add(WeekDay.TH);
					} else if (tokenStr.equals("6")) {
						dayList.add(WeekDay.FR);
					} else if (tokenStr.equals("7")) {
						dayList.add(WeekDay.SA);
					}
				}

				irecur.setDayList(dayList);

				// 매월
			} else if (repeatType.equals("3")) {
				irecur.setFrequency("MONTHLY");

				WeekDayList dayList = new WeekDayList();
				tokenGubun = token.nextToken();

				// 날짜 기준
				if (tokenGubun.equals("a")) {
					// 요일 기준
				} else {
					// 포지션 셋팅
					List<Integer> postList = new ArrayList<Integer>();
					postList.add(Integer.parseInt(token.nextToken()));
					irecur.setSetPosList(postList);

					// 요일 셋팅
					tokenStr = token.nextToken();

					if (tokenStr.equals("1")) {
						dayList.add(WeekDay.SU);
					} else if (tokenStr.equals("2")) {
						dayList.add(WeekDay.MO);
					} else if (tokenStr.equals("3")) {
						dayList.add(WeekDay.TU);
					} else if (tokenStr.equals("4")) {
						dayList.add(WeekDay.WE);
					} else if (tokenStr.equals("5")) {
						dayList.add(WeekDay.TH);
					} else if (tokenStr.equals("6")) {
						dayList.add(WeekDay.FR);
					} else if (tokenStr.equals("7")) {
						dayList.add(WeekDay.SA);
					}

					irecur.setDayList(dayList);
				}

				// 매년
			} else if (repeatType.equals("4")) {
				irecur.setFrequency("YEARLY");

				WeekDayList dayList = new WeekDayList();
				tokenGubun = token.nextToken();

				// 날짜 기준
				if (tokenGubun.equals("a")) {
					// 요일 기준
				} else {
					// 포지션 셋팅
					List<Integer> postList = new ArrayList<Integer>();
					postList.add(Integer.parseInt(token.nextToken()));
					irecur.setSetPosList(postList);

					// 요일 셋팅
					tokenStr = token.nextToken();

					if (tokenStr.equals("1")) {
						dayList.add(WeekDay.SU);
					} else if (tokenStr.equals("2")) {
						dayList.add(WeekDay.MO);
					} else if (tokenStr.equals("3")) {
						dayList.add(WeekDay.TU);
					} else if (tokenStr.equals("4")) {
						dayList.add(WeekDay.WE);
					} else if (tokenStr.equals("5")) {
						dayList.add(WeekDay.TH);
					} else if (tokenStr.equals("6")) {
						dayList.add(WeekDay.FR);
					} else if (tokenStr.equals("7")) {
						dayList.add(WeekDay.SA);
					}

					irecur.setDayList(dayList);
				}

			}

			// 반복횟수, 종료일자 셋팅
			irecur.setInterval(Integer.parseInt(recurrences.getRepeatPeriod()));
			//irecur.setUntil(new net.fortuna.ical4j.model.Date(recurrences.getEndDate().getTime()));
			irecur.setUntil(new net.fortuna.ical4j.model.Date(DateUtil.getRelativeDate(recurrences.getEndDate(),0,0,1).getTime()));

		}

		return irecur;
	}
	
	private void reviseScheduleTime(IEvent ievent, long offset) {
		ievent.setDtstart(new net.fortuna.ical4j.model.DateTime(ievent.getDtstart().getTime() + offset));
		ievent.setDtend(new net.fortuna.ical4j.model.DateTime(ievent.getDtend().getTime() + offset));
		
		IRecur recur = ievent.getIrecur();
		if(recur != null) {
			recur.setUntil(new net.fortuna.ical4j.model.Date(recur.getUntil().getTime() + offset));
		}
	}
	
	private long getTimezoneOffset(String timezoneId) {
		long offset = 0;
		Date nowDate = new Date();
		
		TimeZone tz = Calendar.getInstance().getTimeZone();	// System timezone
		TimeZone icalTz = TimeZone.getTimeZone(timezoneId);	// export timezone
		
		if(icalTz.getID() != tz.getID()) {
			offset = (long)tz.getOffset(nowDate.getTime()) - icalTz.getOffset(nowDate.getTime());
		}
		
		return offset;
	}

	/**
	 * import 받은 스케줄 변환 저장
	 * 
	 * @param inputStream
	 * @return
	 */
	public boolean importSchedule(InputStream importSchedule, User user) {
		boolean result = true;

		String uid;
		boolean isIKEPSchedule;
		ICalendar iCalendar = ICalUtil.readICalendar(importSchedule);
		
		long tzOffset = getTimezoneOffset(iCalendar.getiTimeZone().getTzId());
		
		List<IEvent> iEventList = iCalendar.getIeventList();
		for (IEvent iEvent : iEventList) {
			uid = iEvent.getUid();
			isIKEPSchedule = false;

			if (uid.indexOf("ikep") > -1) {
				isIKEPSchedule = true;
				int hostSplitPos = uid.indexOf("@");
				uid = uid.substring(uid.indexOf("ikep_") + 5, hostSplitPos > -1 ? hostSplitPos : uid.length()); // ikep
																												// schedule
																												// id

				Schedule s = scheduleDao.get(uid);
				
				if (s != null) {
					continue; // 이미 있는 일정이면 export하지 않음.
				}
			}
			
			if(tzOffset != 0) {	// timezone 보정 시간 적용
				reviseScheduleTime(iEvent, tzOffset);
			}

			Schedule schedule = new Schedule();

			schedule.setPortalId(user.getPortalId());
			schedule.setRegisterId(user.getUserId());
			schedule.setRegisterName(user.getUserName());
			schedule.setUpdaterId(user.getUserId());
			schedule.setUpdaterName(user.getUserName());

			
			List<Alarm> alarmList = iAlarm2alarm(iEvent.getIalarmList());
			schedule.setAlarmList(alarmList);
			schedule.setAlarmRequest(alarmList != null && alarmList.size() > 0 ? 1 : 0);

			schedule.setCategoryId("1"); // 업무
			schedule.setCategoryName("");

			schedule.setTitle(iEvent.getSummary());
			schedule.setContents(iEvent.getDescription());

			// 시간 정보가 없은 종일 일정의 경우 변환 과정에서 시간이 9시로 설정되므로 9시간을 빼서 0시로 등록하도록 수정함.
			if(iEvent.getWholeday() == 1) {
				schedule.setWholeday(1); // 종일일정
				
				Date startDate = new Date();
				
				// 시간 정보가 없는 경우 오전 9시로 등록이 됨으로 9시간을 빼서 0시로 맞춤.
				startDate.setTime(iEvent.getDtstart2().getTime() - ((long)1000 * 60 * 60 * 9));
				
				schedule.setStartDate(startDate);
				schedule.setEndDate(startDate);
			} else {
				schedule.setWholeday(0); // 시간일정
				schedule.setStartDate(iEvent.getDtstart());
				schedule.setEndDate(iEvent.getDtend());
			}

			schedule.setPlace(iEvent.getLocation()); // 장소

			schedule.setSchedulePublic((iEvent.getTransp() != null && iEvent.getTransp()
					.equalsIgnoreCase("TRANSPARENT")) ? 1 : 0); // 공개 여부

			// schedule.setScheduleType("0"); //0 : 일정, 1 : 이벤트, 2 : To-Do

			schedule.setSendmail(false);

			schedule.setWorkspaceId(""); // 개인 일정을 취급

			Recurrences recurrences = iRecur2recurrences(iEvent.getIrecur(), schedule.getStartDate(), schedule.getEndDate()); // 반복설정
			if (recurrences != null) {
				List<Recurrences> recurrencesList = new ArrayList<Recurrences>();
				recurrencesList.add(recurrences);
				schedule.setRecurrences(recurrencesList);
				schedule.setRepeat(1);
			} else {
				schedule.setRepeat(0);
			}

			List<IAttendee> iattendeeList = iEvent.getAttendeeList(); // 참여/참조자
			if (isIKEPSchedule == true && iattendeeList != null && iattendeeList.size() > 0) {
				List<Participant> participantList = new ArrayList<Participant>();
				for (IAttendee iattendee : iattendeeList) {
					String userId = iattendee.getMember();
					if (userId != null && !userId.equals("")) {
						User userInfo = userDao.get(userId);
						if (userInfo != null) {
							Participant participant = new Participant();
							participant.setTargetUserId(userInfo.getUserId());
							participant.setTargetType("1");
							participantList.add(participant);
						}
					}
				}

				schedule.setParticipantList(participantList);
			}

			schedule.setFileLinkList(new ArrayList<FileLink>()); // 첨부파일

			try {
				create(schedule, null);	//user
			} catch (Exception e) {
				result = false;
			}
		}

		return result;
	}

	enum exportType {
		ALL, MONTH, ETC
	}

	/**
	 * 지정된 기간 동안의 일정 export
	 * 
	 * @param params
	 * @param user
	 */
	@SuppressWarnings("deprecation")
	public ICalendar exportSchedule(Map<String, Object> params, User user) {
		ICalendar calendar = new ICalendar();
		calendar.setProdid("-//LG CNS//IKEP Calendar 1.0//" + user.getLocaleCode().toUpperCase());
		calendar.setXprop("X-IKEP-STAMP:Y\nX-OWNER-ID:" + user.getUserId());

		Calendar c = Calendar.getInstance();
		TimeZone tz = c.getTimeZone();
		//long offset = tz.getOffset(new Date().getTime());
		ITimeZone itimezone = new ITimeZone();
		itimezone.setTzId(tz.getID());
		//itimezone.setTzName(tz.getDisplayName());
		//itimezone.setTzOffsetFrom(offset);
		//itimezone.setTzOffsetTo(offset);
		//itimezone.setDtstart(new net.fortuna.ical4j.model.DateTime(new Date(1970, 1, 1).getTime()));
		calendar.setiTimeZone(itimezone);
		
		Date coStartDate, coEndDate, clientNow = timeZoneSupportService.convertTimeZone();
		switch(exportType.valueOf(params.get("exportType").toString())) {
			case MONTH :
				coEndDate = new Date();
				coStartDate = new Date(coEndDate.getYear(), coEndDate.getMonth()-1, coEndDate.getDate()+1);
				break;
			case ETC :
				coStartDate = (Date) params.get("startDate");
				coEndDate = (Date) params.get("endDate");
				break;
			default :	// ALL
				coStartDate = new Date(1, 0, 1);	//1901.01.01 00:00:00.000
				coEndDate = new Date(8098, 11, 31);	//9998.12.31 00:00:00.000
		}
		
		params.put("userId", user.getUserId());
		params.put("startDate", convertToServerTimezoneOnlyDate(coStartDate, clientNow));
		params.put("endDate", convertToServerTimezoneOnlyDate(coEndDate, clientNow));

		List<Schedule> scheduleList = scheduleDao.exportSchedule(params);
		//convertToClientTimezone(scheduleList);	// export timezone이 server timezone이므로 별도로 변환하지 않음

		for(Schedule schedule : scheduleList) {
			IEvent event = new IEvent();
			event.setUid("ikep_" + schedule.getScheduleId());
			
			List<Participant> participantList = schedule.getParticipantList();
			if(participantList != null) {	// 참여자 정보 설정
				for(Participant participant : participantList) {
					IAttendee iattendee = new IAttendee();
					
					String email = participant.getMail();
					iattendee.setMail("MAILTO:" + (email != null && !email.equals("") ? participant.getMail() : participant.getTargetUserId()));
					iattendee.setCn(participant.getTargetUserInfo());
					iattendee.setMember(participant.getTargetUserId());
					
					event.addAttendeeList(iattendee);
				}
			}
			
			event.setIalarmList(alarms2ialarms(schedule.getAlarmList(), user));

			IAttendee organizer = new IAttendee();
			organizer.setCn(user.getUserName() + " " + user.getJobTitleName() + " " + user.getTeamName());
			organizer.setMember(user.getUserId());
			organizer.setMail("MAILTO:" + (user.getMail() != null && !user.getMail().equals("") ? user.getMail() : user.getUserId()) );
			event.setOrganizer(organizer);
			
			event.setSummary(schedule.getTitle());
			event.setDescription(schedule.getContents());

			if(schedule.getWholeday() == 1) {// 종일일정 : 시간 정보를 없앰.
				Date startDate = schedule.getStartDate();
				Date endDate = schedule.getEndDate();
				startDate.setDate(startDate.getDate() + 1);
				endDate.setDate(endDate.getDate() + 2);
				event.setDtstart(new net.fortuna.ical4j.model.DateTime(startDate.getTime()));
				event.setDtend(new net.fortuna.ical4j.model.DateTime(endDate.getTime()));
				event.setWholeday(1);
			} else {
				event.setDtstart(new net.fortuna.ical4j.model.DateTime(schedule.getStartDate().getTime()));
				event.setDtend(new net.fortuna.ical4j.model.DateTime(schedule.getEndDate().getTime()));
				event.setWholeday(0);
			}

			event.setLocation(schedule.getPlace());	// 장소
			
			if(schedule.getSchedulePublic() == 1) { // 공개 여부
				event.setTransp("TRANSPARENT");
				event.setClazz("PUBLIC");
			} else {
				event.setTransp("OPAQUE");
				event.setClazz("PRIVATE");
			}

			List<Recurrences> recList = schedule.getRecurrences();
			if(schedule.getRepeat() == 1 && recList.size() > 0) {
				Recurrences recurrence = recList.get(0);
				for(int i=1;i<recList.size();i++) {
					Recurrences otherRec = recList.get(i);
					if(recurrence.getStartDate().getTime() > otherRec.getStartDate().getTime()) {
						recurrence.setStartDate(otherRec.getStartDate());
					}
					if(recurrence.getEndDate().getTime() < otherRec.getEndDate().getTime()) {
						recurrence.setEndDate(otherRec.getEndDate());
					}
				}
				event.setIrecur(recurrences2iRecur(recurrence));	// 반복일정
			}
			
			calendar.addIEventList(event);
		}
		
		return calendar;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.planner.service.CalendarService#updateSchedule
	 * (com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Transactional
	public void updateSchedule(UpdateScheduleVO data) throws ParseException {
		int updateType = data.getUpdateType();
		Schedule newSchedule = data.getNewSchedule();
		User user = data.getUser();

		newSchedule.assignUser(user);

		if (updateType == 0) {
			updateSchedule(newSchedule, user);
			return;
		}

		Map oldSchedule = data.getOldSchedule();
		String scheduleId = (String) oldSchedule.get("scheduleId");

		getEventSibling(oldSchedule);

		Date prevStart, prevEnd, nextStart, nextEnd;
		prevStart = (Date) oldSchedule.get("prevStart");
		prevEnd = (Date) oldSchedule.get("prevEnd");

		nextStart = (Date) oldSchedule.get("nextStart");
		nextEnd = (Date) oldSchedule.get("nextEnd");

		String rpsd = ((String) oldSchedule.get("repeatStartDate")).replaceAll("-", "");
		Date repeatStartDate = DateUtils.parseDate(rpsd, parsePatterns);

		String rped = ((String) oldSchedule.get("repeatEndDate")).replaceAll("-", "");
		Date repeatEndDate = DateUtils.parseDate(rped, parsePatterns);

		boolean isFirstEvent = DateUtils.truncate(repeatStartDate, Calendar.DATE).compareTo(
				DateUtils.truncate(prevStart, Calendar.DATE)) > 0;
		boolean isLastEvent = DateUtils.truncate(repeatEndDate, Calendar.DATE).compareTo(
				DateUtils.truncate(nextStart, Calendar.DATE)) < 0;
		String updatePart = data.getUpdatePart();

		if (updateType == 1) { // 이번 일정만
			if (isFirstEvent && isLastEvent) { // event가 하나밖에 없는 반복일정
				int count = scheduleDao.getCountRepeatEvents(scheduleId);
				if (count == 1) {
					deleteSchedule(scheduleId);
				} else {
					scheduleDao.deleteCurrenctRepeat(scheduleId, (Date) oldSchedule.get("start"));
				}
			} else if (isFirstEvent) {
				int count = scheduleDao.isFirstRepeatRow(scheduleId, repeatStartDate);
				if (count == 0) {
					scheduleDao.updateScheduleDate(scheduleId, nextStart, nextEnd);
				}
				scheduleDao.shiftNextRepeatDate(scheduleId, nextStart, nextEnd);
			} else if (isLastEvent) {
				scheduleDao.shiftPrevRepeatDate(scheduleId, prevStart, prevEnd);
			} else {
				scheduleDao.insertNextRecurrence(oldSchedule);
				scheduleDao.updatePrevRecurrenceEndDate(scheduleId, prevStart, prevStart);
			}
			newSchedule.setRepeat(0);
			newSchedule.setRecurrences(null);
			createByCopy(newSchedule, user);
		} else if (updateType == 2) { // 향후 모든 일정
			scheduleDao.shiftPrevRepeatDate(scheduleId, prevStart, prevStart);
			if (updatePart.equals("repeat")) {
				scheduleDao.deleteAfterRepeat(scheduleId, prevStart);
				createByCopy(newSchedule, user);
			} else { // 내용만 수정 (약속시간 제외)
				newSchedule.setRecurrences(null);
				String newScheduleId = createByCopy(newSchedule, user);
				scheduleDao.updateAfterRepeatScheduleId(scheduleId, newScheduleId, prevStart);
			}
			if (isFirstEvent && isLastEvent) {
				int count = scheduleDao.getCountRepeatEvents(scheduleId);
				if (count == 0) {
					deleteSchedule(scheduleId);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.CalendarService#
	 * deleteRepeatSchedule(java.util.Map)
	 */
	@SuppressWarnings("deprecation")
	@Transactional
	public void deleteRepeatSchedule(Map<String, Object> params) throws ParseException {
		String scheduleId = (String) params.get("scheduleId");
		int deleteType = Integer.valueOf((String) params.get("deleteType"));

		if (deleteType == 3) { // 전체일정
			deleteSchedule(scheduleId);
			return;
		}

		getEventSibling(params);

		Date prevStart, prevEnd, nextStart, nextEnd;
		prevStart = (Date) params.get("prevStart");
		prevEnd = (Date) params.get("prevEnd");

		nextStart = (Date) params.get("nextStart");
		nextEnd = (Date) params.get("nextEnd");

		String rpsd = ((String) params.get("repeatStartDate")).replaceAll("-", "");
		Date repeatStartDate = DateUtils.parseDate(rpsd, parsePatterns);

		String rped = ((String) params.get("repeatEndDate")).replaceAll("-", "");
		Date repeatEndDate = DateUtils.parseDate(rped, parsePatterns);

		boolean isFirstEvent = repeatStartDate.compareTo(DateUtils.truncate(prevStart, Calendar.DATE)) > 0;
		boolean isLastEvent = repeatEndDate.compareTo(DateUtils.truncate(nextStart, Calendar.DATE)) < 0;

		if (deleteType == 1) { // 이번 일정만
			// 해당 일정이 반복일정중 첫번째일 경우
			if (isFirstEvent && isLastEvent) {
				int count = scheduleDao.getCountRepeatEvents(scheduleId);
				if (count == 1) {
					deleteSchedule(scheduleId);
				} else {
					scheduleDao.deleteCurrenctRepeat(scheduleId, (Date) params.get("start"));
				}
			} else if (isFirstEvent) {
				scheduleDao.updateScheduleDate(scheduleId, nextStart, nextEnd);
				scheduleDao.shiftNextRepeatDate(scheduleId, nextStart, nextEnd);
			} else if (isLastEvent) {
				scheduleDao.shiftPrevRepeatDate(scheduleId, prevStart, prevEnd);
			} else {
				// 기존 반복일정 복사 및 반복 일자등 수정
				scheduleDao.insertNextRecurrence(params);
				scheduleDao.updatePrevRecurrenceEndDate(scheduleId, prevStart, prevEnd);
			}
		} else if (deleteType == 2) { // 향후 모든 일정
			// 해당 일정이 반복일정중 첫번째일 경우
			if (isFirstEvent) {
				int count = scheduleDao.getCountRepeatEvents(scheduleId);
				if (count == 1) {
					deleteSchedule(scheduleId);
				} else {
					scheduleDao.deleteAfterRepeat(scheduleId, prevStart);
				}
			} else {
				scheduleDao.deleteAfterRecurrences(scheduleId, prevStart);
				scheduleDao.updatePrevRecurrenceEndDate(scheduleId, prevStart, prevEnd);
			}
		}
	}

	public void getEventSibling(Map<String, Object> params) throws ParseException {
		Date start = DateUtils.parseDate((String) params.get("startDate"), parsePatterns);
		Date end = DateUtils.parseDate((String) params.get("endDate"), parsePatterns);
		Date updateStart = params.get("updateStart") != null ? DateUtils.parseDate((String) params.get("updateStart"),
				parsePatterns) : null;
		Date updateEnd = params.get("updateEnd") != null ? DateUtils.parseDate((String) params.get("updateEnd"),
				parsePatterns) : null;

		int interval = Integer.valueOf((String) params.get("repeatPeriod"));
		int rtype = Integer.valueOf((String) params.get("repeatType"));
		String roption = (String) params.get("repeatPeriodOption");
		String[] ra = StringUtils.isNotEmpty(roption) ? roption.split(",") : null;

		Date prevStart = null, prevEnd = null, nextStart = null, nextEnd = null;

		if (rtype == 1) { // daily
			prevStart = DateUtils.addDays(start, -interval);
			nextStart = DateUtils.addDays(start, interval);
		} else if (rtype == 2 && ra.length == 1) { // weekly - 단수요일
			prevStart = DateUtils.addWeeks(start, -interval);
			nextStart = DateUtils.addWeeks(start, interval);
		} else if (rtype == 2 && ra.length > 1) { // weekly - 복수요일
			Date repeatStartDate = DateUtils.parseDate(((String) params.get("repeatStartDate")).replace("-", ""),
					parsePatterns);
			// Date repeatEndDate =
			// DateUtils.parseDate(((String)
			// params.get("repeatEndDate")).replace("-", ""), parsePattenrs);
			prevStart = weeklyMultiType(start, -interval, ra, repeatStartDate);
			nextStart = weeklyMultiType(start, interval, ra, repeatStartDate);
		} else if (rtype == 3 && "a".equals(ra[0])) { // 특정일자
			int day = Integer.valueOf(ra[1]);
			prevStart = monthlyAtype(start, -interval, day);
			nextStart = monthlyAtype(start, interval, day);
		} else if (rtype == 3 && "b".equals(ra[0])) { // 몇번째 요일
			int dowm = Integer.valueOf(ra[1]);
			int dow = Integer.valueOf(ra[2]);

			prevStart = monthlyBtype(start, -interval, dowm, dow);
			nextStart = monthlyBtype(start, interval, dowm, dow);
		} else if (rtype == 3 && "c".equals(ra[0])) { // 마지막 요일
			int dow = Integer.valueOf(ra[1]);

			prevStart = monthlyCtype(start, -interval, dow);
			nextStart = monthlyCtype(start, interval, dow);
		} else if (rtype == 3 && "d".equals(ra[0])) { // 마지막 일자
			prevStart = monthlyDtype(start, -interval);
			nextStart = monthlyDtype(start, interval);
		} else if (rtype == 4 && "a".equals(ra[0])) { // n년마다 특정일자
			int day = Integer.valueOf(ra[1]);
			int mon = Integer.valueOf(ra[2]);

			prevStart = yearlyAtype(start, -interval, mon, day);
			nextStart = yearlyAtype(start, interval, mon, day);
		} else if (rtype == 4 && "b".equals(ra[0])) { // n년마다 몇번째 요일
			int cnt = Integer.valueOf(ra[1]);
			int dow = Integer.valueOf(ra[2]);
			int mon = Integer.valueOf(ra[3]);

			prevStart = yearlyBtype(start, -interval, cnt, dow, mon);
			nextStart = yearlyBtype(start, interval, cnt, dow, mon);
		} else if (rtype == 4 && "c".equals(ra[0])) { // n년마다 마지막 요일
			int dow = Integer.valueOf(ra[1]);
			int mon = Integer.valueOf(ra[2]);

			prevStart = yearlyCtype(start, -interval, dow, mon);
			nextStart = yearlyCtype(start, interval, dow, mon);
		} else if (rtype == 4 && "d".equals(ra[0])) { // n년마다 마지막 일자
			prevStart = yearlyDtype(start, -interval);
			nextStart = yearlyDtype(start, interval);
		}

		long diffDate = (end.getTime() - start.getTime());
		prevEnd = new Date(prevStart.getTime() + diffDate);
		nextEnd = new Date(nextStart.getTime() + diffDate);

		params.put("prevStart", prevStart);
		params.put("prevEnd", prevEnd);
		params.put("start", start);
		params.put("end", end);
		params.put("nextStart", nextStart);
		params.put("nextEnd", nextEnd);
		params.put("updateStart", updateStart);
		params.put("updateEnd", updateEnd);
	}

	public void clearCall() {
		// scheduleDao.clearCall();
	}

	public List<Map> getHolidayList(long start, long end, User userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Schedule getScheduleAllDataWithMobile(String scheduleId,
			String locale) {
		// TODO Auto-generated method stub
		Schedule schedule = scheduleDao.get(scheduleId, locale);
		List<Participant> participantList = participantDao.getList(scheduleId);
		List<Alarm> alarmList = alarmDao.getList(scheduleId);

		schedule.setParticipantList(participantList);
		schedule.setAlarmList(alarmList);

		schedule.setFileDataList(fileService.getItemFileAll(scheduleId, "N"));

		convertToClientTimezone(schedule);
		
//		if(schedule.getScheduleType() == 0) {	// 미정 일정이면
//			List<ScheduleUnfixedTime> unfixedTimeList = unfixedTimeDao.getAll(scheduleId);
//			schedule.setUnfixedTimeList(unfixedTimeList);
//		}
		
		return schedule;
	}
}
/**********************************************************************************
 * ----------------------------------------------------------------------------
 * ------- 반복유형 option 비고
 * --------------------------------------------------------
 * --------------------------- daily 1
 * ------------------------------------------
 * ----------------------------------------- weekly 2 1 ~ 7(요일) 요일(복수가능) 구분자는
 * 콤마(,) 일요일 = 1, 토요일 = 7
 * --------------------------------------------------------
 * --------------------------- monthly 3 a,[d] 특정일자(d: 1 ~ 31) b,[n,w] 몇번째,
 * 요일(n:1~6, w:1~7) c,[w] 마지막 요일(w: 1~7) d 마지막 일자
 * --------------------------------
 * --------------------------------------------------- yearly 4 a,[d] 특정일자
 * b,[n,w] 몇번째, 요일(n:1~5, w:1~7) c,[w] 마지막 요일(w: 1~7) d 마지막 일자
 * ------------------
 * -----------------------------------------------------------------
 *************************************************************************************/
