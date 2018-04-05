/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/recent")
public class MeetingRoomByRecentController extends BaseController {

	@Autowired
	private MeetingRoomService meetingRoomService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private CartooletcService cartooletcService;

	@RequestMapping(value = "/meetingRoomMain.do")
	public ModelAndView meetingRoomMain(@RequestParam(value="date", required=false, defaultValue="0") long date) {

		ModelAndView mav = new ModelAndView("lightpack/meetingroom/tab/recent/meetingRoomMain");//	/lightpack/meetingroom/recent/meetingRoomMain
		
		Date today = null;
			
		if(date == 0) {
			Date clientNow = timeZoneSupportService.convertTimeZone();
			today = DateUtils.truncate(clientNow, Calendar.DATE);
		} else {
			today = new Date(date);
		}
		
		mav.addObject("today", DateUtil.getFmtDateString(today, "yyyy.MM.dd"));

		return mav;
	}


	@RequestMapping(value = "/cartooletcMain.do")
	public ModelAndView cartooletcMain(@RequestParam(value="date", required=false, defaultValue="0") long date) {

		ModelAndView mav = new ModelAndView("lightpack/meetingroom/tab/recent/cartooletcMain");//	/lightpack/meetingroom/day/meetingRoomMain

		
		Date today = null;
			
		if(date == 0) {
			Date clientNow = timeZoneSupportService.convertTimeZone();
			today = DateUtils.truncate(clientNow, Calendar.DATE);
		} else {
			today = new Date(date);
		}

		mav.addObject("today", DateUtil.getFmtDateString(today, "yyyy.MM.dd"));
		
		return mav;
	}
	/**
	 * 일별 회의실 일정 조회
	 * 
	 * @param roomGroupId
	 * @param roomFloorId
	 * @param currentYear
	 * @param currentMonth
	 * @param currentDay
	 * @return
	 */
	@RequestMapping(value = "/meetingRoomList.do")
	public @ResponseBody List<MeetingRoom> meetingRoomList(@RequestParam(value="curDate", required=false, defaultValue="0") long curDate) {

		List<MeetingRoom> meetingRoomList;

		try {
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");

			MeetingRoom meetingRoom = new MeetingRoom();
			meetingRoom.setIsRecent("Y");
			meetingRoom.setPortalId(portal.getPortalId());
			meetingRoom.setRegisterId(user.getUserId());
			meetingRoom.setUseFlag("1");
			
			meetingRoomList = meetingRoomService.select(meetingRoom);

			Date today = null;
			
			if(curDate == 0) {
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
			} else {
				today = new Date(curDate);
			}
			
			List<Map<String, Object>> scheduleList = scheduleService.readMeetingRoomScheduleByRecent(portal.getPortalId(), today, today);
			if(scheduleList != null && scheduleList.size() > 0) {
				makeMeetingRoomIntoSchedule(meetingRoomList, scheduleList);
			}
		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return meetingRoomList;
	}
	
	@RequestMapping(value = "/cartooletcList.do")
	public @ResponseBody List<Cartooletc> cartooletcList(String mid, @RequestParam(value="curDate", required=false, defaultValue="0") long curDate) {

		List<Cartooletc> cartooletcList;
		
		try {
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");

			Cartooletc cartooletc = new Cartooletc();
			cartooletc.setIsRecent("Y");
			cartooletc.setPortalId(portal.getPortalId());
			cartooletc.setRegisterId(user.getUserId());
			cartooletc.setUseFlag("1");
			cartooletc.setViewYn("1");
			cartooletc.setMid(mid);
			cartooletcList = cartooletcService.select(cartooletc);


			Date today = null;
			
			if(curDate == 0) {
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
			} else {
				today = new Date(curDate);
			}
			
			List<Map<String, Object>> scheduleList = scheduleService.readCartooletcScheduleByRecent(portal.getPortalId(), today, today);
			if(scheduleList != null && scheduleList.size() > 0) {
				makeCartooletcIntoSchedule(cartooletcList, scheduleList);
			}

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return cartooletcList;
	}
	private void makeMeetingRoomIntoSchedule(List<MeetingRoom> meetingRoomList, List<Map<String, Object>> scheduleList) {
		for (MeetingRoom meetingRoom : meetingRoomList) {
			List<Map<String, Object>> roomSchedules = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> schedule : scheduleList) {
				if (meetingRoom.getMeetingRoomId().equals((String) schedule.get("meetingRoomId"))) {
					roomSchedules.add(schedule);
				}
			}
			meetingRoom.setScheduleList(roomSchedules);
		}
	}

	
	private void makeCartooletcIntoSchedule(List<Cartooletc> cartooletcList, List<Map<String, Object>> scheduleList) {
		for (Cartooletc cartooletc : cartooletcList) {
			List<Map<String, Object>> roomSchedules = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> schedule : scheduleList) {
				if (cartooletc.getCartooletcId().equals((String) schedule.get("cartooletcId"))) {
					roomSchedules.add(schedule);
				}
			}
			cartooletc.setScheduleList(roomSchedules);
		}
	}
	
	private List<List<Map<String, String>>> makeMeetingRoomTimeList(List<MeetingRoom> meetingRoomList, List<Map<String, Object>> scheduleList) {

		List<List<Map<String, String>>> roomTimeList = new ArrayList<List<Map<String, String>>>();

		for (MeetingRoom meetingRoom : meetingRoomList) {

			List<Map<String, String>> timeList = new ArrayList<Map<String, String>>();
			List<Map<String, Object>> tmpScheduleList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> schedule : scheduleList) {

				if (meetingRoom.getMeetingRoomId().equals((String) schedule.get("meetingRoomId"))) {
					
					tmpScheduleList.add(schedule);
				}

			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("isHeader", "Y");
			map.put("meetingRoomId", meetingRoom.getMeetingRoomId());
			map.put("meetingRoomName", meetingRoom.getMeetingRoomName());
			map.put("buildingName", meetingRoom.getBuildingName());
			map.put("floorName", meetingRoom.getFloorName());
			
			timeList.add(map);

			timeList.addAll(meetingRoomService.makeMeetingTimeList(meetingRoom.getMeetingRoomId(), tmpScheduleList));

			roomTimeList.add(timeList);
		}

		return roomTimeList;
	}
}
