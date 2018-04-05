/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.lightpack.planner.dao.AlarmDao;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.lightpack.planner.util.Utils;
import com.lgcns.ikep4.lightpack.planner.web.CalendarController;
//import com.lgcns.ikep4.util.PropertyLoader;
//import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;

/**
 * 일정 알람 서비스 구현
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: AlarmPlannerServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service("alarmPlannerService")
@Transactional
public class AlarmPlannerServiceImpl implements AlarmPlannerService {
	
	protected final org.apache.commons.logging.Log clog = LogFactory.getLog(getClass());
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private AlarmDao alarmDao;
	
	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private SmsService smsService;

	@Autowired
	private MessageOutsideService messageOutsideService;
	
	@Autowired
	protected MessageSource messageSource = null;

	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyy.MM.dd HH:mm");
	
	private static final String MESSAGE_MAIL_CREATE = "message.lightpack.planner.alarmmail.create";
	private static final String MESSAGE_MAIL_UPDATE = "message.lightpack.planner.alarmmail.update";
	private static final String MESSAGE_MAIL_DELETE = "message.lightpack.planner.alarmmail.delete";
	
	private static Map<String, String> messageCodes = new HashMap<String, String>();
	
	static {
		messageCodes.put("create", MESSAGE_MAIL_CREATE);
		messageCodes.put("update", MESSAGE_MAIL_UPDATE);
		messageCodes.put("delete", MESSAGE_MAIL_DELETE);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService#getAlarmTargetList(java.util.Date, int)
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	public List getAlarmTargetList(Date jobTime, int interval) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobDate", DateUtils.truncate(jobTime, Calendar.DATE));
		params.put("jobDateTime", DateUtils.truncate(jobTime, Calendar.MINUTE));
		params.put("interval", interval);
		
		return alarmDao.getAlarmTargetList(params);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService#doAlarmJobSchedule(java.util.Date, int)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doAlarmJobSchedule(Date jobTime, int interval, String fileUrl) throws ParseException {
		List list = scheduleService.getAlarmTargetList(jobTime, interval);

		Set alarmIds = new HashSet();
		//clog.debug("@@@@@@@@@@@@@@@list.size():"+list.size());
		for (int i = 0, len = list.size(); i < len; i++) {
			Map items = (Map) list.get(i);
			//clog.debug("@@@@@@@@@@@@@@@scheduleId:"+items.get("scheduleId"));
			//clog.debug("@@@@@@@@@@@@@@@senderUserId:"+items.get("senderUserId"));
			//clog.debug("@@@@@@@@@@@@@@@sender:"+items.get("sender"));

			
			Object recurrence = items.get("recurrence");
			List targets = (List) items.get("details");
			List mailTarget = new ArrayList();
			List smsTarget = new ArrayList();
			List noteTarget = new ArrayList();
			List pushTarget = new ArrayList();

			//clog.debug("@@@@@@@@@@@@@@@targets.size():"+targets.size());
			for (int j = 0, jlen = targets.size(); j < jlen; j++) {
				Map dm = (Map) targets.get(j);
				//clog.debug("@@@@@@@@@@@@@@@targetUserId:"+dm.get("targetUserId"));
				//clog.debug("@@@@@@@@@@@@@@@targetUser:"+dm.get("targetUser"));
				//clog.debug("@@@@@@@@@@@@@@@toMail:"+dm.get("toMail"));
				
				int type = Integer.valueOf((String) dm.get("alarmType"));
				if (type == 0) { // 메일
					mailTarget.add(dm);
				} else if (type == 1) { // SMS
					if ((String) dm.get("mobile") != null) {
						smsTarget.add(dm);
					}
				} else if (type == 2) { // 쪽지
					noteTarget.add(dm);
				}  else if (type == 3) { // 모바일 푸시
					pushTarget.add(dm);
				} else {
					continue;
				}
				if(recurrence == null) {
					//clog.debug("@@@@@@@@@@@@@@@alarmId:"+dm.get("alarmId"));
					alarmIds.add(dm.get("alarmId"));
				}
			}
			try {
				if(mailTarget.size()>0){sendMail(items, mailTarget, fileUrl);}
				if(smsTarget.size()>0){sendSms(items, smsTarget);}
				if(noteTarget.size()>0){sendNote(items, noteTarget);}
				//푸시관련 로직 추가 
				try{
				if(pushTarget.size()>0){sendPush(items, pushTarget);}
				}catch(Exception e){clog.error("Fail to AlarmJobSchedule when sending push : ");}

			} catch (Exception e) {
				clog.error("Fail to AlarmJobSchedule : ");
				e.printStackTrace();
			}
		}
		if(alarmIds.size()>0) {
			alarmDao.updateAlarmSent(alarmIds);
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void sendMail(Map item, List targets, String fileUrl) {
		int len = targets.size();
		if(len == 0) {
			return;
		}
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("plannerTemplate.vm");
		mail.setTitle("[일정알람] "+(String) item.get("title"));

		
		
		
		String senderId = (String) item.get("senderUserId");
		List<User> senderList = alarmDao.getUserInfoList(new String[]{senderId});
		

		// 발신자
		User sender = senderList.get(0);
		


		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		for (int i = 0; i < len; i++) {
			Map rm = (Map) targets.get(i);
			recip = new HashMap<String, String>();
			String maddr = (String) rm.get("toMail");
			
			if(maddr != null) {	
				recip.put("email", (String) rm.get("toMail"));
				recip.put("name", (String) rm.get("targetUser"));
	
				recipients.add(recip);
			}
		}
		
		if(recipients.size() > 0) {
			mail.setToEmailList(recipients);
	
			Schedule schedule = calendarService.getScheduleAllData((String) item.get("scheduleId"));
			
			Map dataMap = new HashMap();
			dataMap.put("schedule", schedule);
			dataMap.put("fileUrl", fileUrl);
			dataMap.put("startStr", fdfDateTime.format(schedule.getStartDate()));
			dataMap.put("endStr", fdfDateTime.format(schedule.getEndDate()));
			
			mailSendService.sendMail(mail, dataMap, sender);
			//mailSendService.sendMail(mail, dataMap, sender);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void sendSms(Map items, List targets) {
		int len = targets.size();
		if(len == 0) {
			return;
		}
		
		Sms sms = new Sms();

		
		String senderId = (String) items.get("senderUserId");
		List<User> senderList = alarmDao.getUserInfoList(new String[]{senderId});
		

		// 발신자
		User sender = senderList.get(0);
		
		// 발신자
		String registerId = sender.getUserId();
		String registerName =sender.getUserName(); 
		String contents = (String) items.get("title");
		
		
		//schedule info
		String scheduleId = (String) items.get("scheduleId");
		
		Schedule schedule = calendarService.getScheduleAllData(scheduleId);
	        
	    //String message = "[일정알람] " + contents+ " ("+fdfDateTime.format(schedule.getStartDate())+ " ~ "+fdfDateTime.format(schedule.getEndDate())+")";
	    String message = "[일정알람] " + contents;

		// 수신자
		String[] receiverIds = new String[len];
		String[] receiverPhonenos = new String[len];
		for (int i = 0; i < len; i++) {
			Map<String, String> rm = (Map<String, String>) targets.get(i);
			receiverIds[i] = (String) rm.get("targetUserId");
			receiverPhonenos[i] = (String) rm.get("mobile");
		}

		sms.setRegisterId(registerId);
		sms.setRegisterName(registerName);
		sms.setContents(message);
		sms.setReceiverIds(receiverIds); // 배열
		sms.setReceiverPhonenos(receiverPhonenos); // 배열

		smsService.create(sms);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void sendNote(Map items, List targets) {
		int len = targets.size();
		if(len == 0) {
			return;
		}
		
		User sender = new User();
		sender.setUserId((String) items.get("senderUserId"));
		sender.setUserName((String) items.get("sender"));

		Message message = new Message();
		String senderId = sender.getUserId();
		String senderName = sender.getUserName();
		String contents = (String) items.get("title");

		String[] receiverList = new String[len];
		for (int i = 0; i < len; i++) {
			Map<String, String> rm = (Map<String, String>) targets.get(i);
			receiverList[i] = (String) rm.get("targetUserId") + "/" + (String) rm.get("targetUser");
		}

		message.setSenderId(senderId);
		message.setSenderName(senderName);
		message.setContents(contents);
		message.setReceiverList(StringUtils.join(receiverList, ","));

		messageOutsideService.sendMessageOutside(message, sender);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService#sendSimpleMail(java.lang.String, com.lgcns.ikep4.lightpack.planner.model.Schedule)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendSimpleMail(String crud, Schedule schedule) {
		List<User> recipientList = new ArrayList<User>();
		List<Participant> participiantList = schedule.getParticipantList();
		String[] userIds = new String[participiantList.size()];
		
		int i=0;
		for (Iterator iterator = participiantList.iterator(); iterator.hasNext();) {
			Participant participant = (Participant) iterator.next();
			userIds[i++] = participant.getTargetUserId();
		}
	
		recipientList = alarmDao.getUserInfoList(userIds);
		
		int len = recipientList.size();
		if(len < 1) {
			return;
		}
		
		String senderId = schedule.getRegisterId();
		List<User> senderList = alarmDao.getUserInfoList(new String[]{senderId});
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("plannerCrudMailTemplate.vm");
		

		// 발신자
		User sender = senderList.get(0);

		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		for (i = 0; i < len; i++) {
			User rm = (User) recipientList.get(i);
			recip = new HashMap<String, String>();

			recip.put("email", rm.getMail());
			recip.put("name", rm.getUserName());

			recipients.add(recip);
		}
		mail.setToEmailList(recipients);
		
		Map dataMap = new HashMap();
		String message = messageSource.getMessage(messageCodes.get(crud), null, new Locale("ko"));
		dataMap.put("message", message+" "+schedule.getTitle());	
		
		mail.setTitle(message+" "+schedule.getTitle());
		
		dataMap.put("startStr", fdfDateTime.format(schedule.getStartDate()));
		dataMap.put("endStr", fdfDateTime.format(schedule.getEndDate()));		
		if(crud.equals("delete")) {
			dataMap.put("content", schedule.getContents());
		} else {
			dataMap.put("url", Utils.getBaseUrl() + "/" + CalendarController.VIEW_URL + schedule.getScheduleId());
		}

		mailSendService.sendMail(mail, dataMap, sender);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendDeleteMail(String crud, Schedule schedule) {
		List<User> recipientList = new ArrayList<User>();
		List<Participant> participiantList = schedule.getParticipantList();
		String[] userIds = new String[1];
		userIds[0] = schedule.getManagerId();
		int i=0;
		recipientList = alarmDao.getUserInfoList(userIds);
		
		int len = recipientList.size();
		if(len < 1) {
			return;
		}
		
		String senderId = schedule.getRegisterId();
		List<User> senderList = alarmDao.getUserInfoList(new String[]{senderId});
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("plannerCrudMailTemplate.vm");
		

		// 발신자
		User sender = senderList.get(0);

		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		for (i = 0; i < len; i++) {
			User rm = (User) recipientList.get(i);
			recip = new HashMap<String, String>();

			recip.put("email", rm.getMail());
			recip.put("name", rm.getUserName());

			recipients.add(recip);
		}
		mail.setToEmailList(recipients);
		
		Map dataMap = new HashMap();
		String message = "[회의실 예약 취소]";
		dataMap.put("message", message+" "+schedule.getTitle());	
		
		mail.setTitle(message+" "+schedule.getTitle());
		
		dataMap.put("startStr", fdfDateTime.format(schedule.getStartDate()));
		dataMap.put("endStr", fdfDateTime.format(schedule.getEndDate()));		
		dataMap.put("place", schedule.getPlace());
		dataMap.put("content", schedule.getContents());

		mailSendService.sendMail(mail, dataMap, sender);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void sendPush(Map items, List targets) {
		int len = targets.size();
		if(len == 0) {
			return;
		}

		String contents = (String) items.get("title");
		String scheduleId = (String) items.get("scheduleId");
		
		//PUSH 설정
		HttpClient client = new DefaultHttpClient();
		Properties prop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String pushBaseUrl = prop.getProperty("ikep4.push.baseUrl");
		HttpPost request = new HttpPost(pushBaseUrl+"/rest/push/insertPushByItemId");
        request.addHeader("content-type", "application/json");

        BufferedReader br = null;
        
        Schedule schedule = calendarService.getScheduleAllData(scheduleId);
        
        String message = contents+ " ("+fdfDateTime.format(schedule.getStartDate())+ " ~ "+fdfDateTime.format(schedule.getEndDate())+")";

		// 수신자 푸시발송
			for (int i = 0; i < len; i++) {
				Map<String, String> rm = (Map<String, String>) targets.get(i);
				try {
			        JSONObject json = new JSONObject();
			        json.put("portalId", "P000001");
			        json.put("userId", (String) rm.get("targetUserId"));
			        json.put("pushType", "P");
			        json.put("alertTitle", "일정알람");
			        json.put("alertText", message );
			        json.put("itemId", scheduleId);
			        StringEntity input = new StringEntity( json.toString(), "UTF-8");
			        
		        	request.setEntity(input);
		        	HttpResponse response = client.execute(request);
		        	
		            //to use client again, should read the response content and close the stream.
		            //since you won't use the response content, just close the stream
		            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		            br.close();
				} catch (Exception e) {
					clog.error("Error occurs during send push messages : "+e.getMessage());
				} 
			}
			client.getConnectionManager().shutdown();
	}

}
