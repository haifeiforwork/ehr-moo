/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.web;

import java.text.SimpleDateFormat;
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
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.service.BuildingFloorService;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/week")
public class MeetingRoomByWeekController extends BaseController {

	@Autowired
	private MeetingRoomService meetingRoomService;

	@Autowired
	private BuildingFloorService buildingFloorService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private CartooletcService cartooletcService;

	@RequestMapping(value = "/meetingRoomMain.do")
	public ModelAndView meetingRoomMain(String buildingId, @RequestParam(value="curDate", required=false, defaultValue="0") long curDate) {

		ModelAndView mav = new ModelAndView("lightpack/meetingroom/tab/week/meetingRoomMain");//	/lightpack/meetingroom/week/meetingRoomMain
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		Date today = null;
		Date firstDayOfWeek = null;
		Date lastDayOfWeek = null;
			
		Date clientNow = timeZoneSupportService.convertTimeZone();
		today = DateUtils.truncate(clientNow, Calendar.DATE);
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(today);

		cal.setFirstDayOfWeek(Calendar.SUNDAY); 

		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); 
		cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1))); 

		firstDayOfWeek = cal.getTime();
		
		cal.add(Calendar.DAY_OF_MONTH, 6);
		
		lastDayOfWeek =  cal.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd"); 
		
		String startDate = sdf.format(firstDayOfWeek);	
		String endDate = sdf.format(lastDayOfWeek);
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("portalId", portal.getPortalId());
		paramMap.put("useFlag", "1");
		
		List<BuildingFloor> buildingList = buildingFloorService.buildingList(paramMap);

		mav.addObject("startDayOfWeek", startDate);
		mav.addObject("endDayOfWeek", endDate);
		mav.addObject("buildingList", buildingList);
		mav.addObject("buildingId", buildingId);
		
		return mav;
	}
	
	@RequestMapping(value = "/cartooletcMain.do")
	public ModelAndView cartooletcMain(String mid,String regionId, @RequestParam(value="curDate", required=false, defaultValue="0") long curDate) {

		ModelAndView mav = new ModelAndView("lightpack/meetingroom/tab/week/cartooletcMain");//	/lightpack/meetingroom/week/meetingRoomMain
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		Date today = null;
		Date firstDayOfWeek = null;
		Date lastDayOfWeek = null;
			
		Date clientNow = timeZoneSupportService.convertTimeZone();
		today = DateUtils.truncate(clientNow, Calendar.DATE);
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(today);

		cal.setFirstDayOfWeek(Calendar.SUNDAY); 

		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); 
		cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1))); 

		firstDayOfWeek = cal.getTime();
		
		cal.add(Calendar.DAY_OF_MONTH, 6);
		
		lastDayOfWeek =  cal.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd"); 
		
		String startDate = sdf.format(firstDayOfWeek);	
		String endDate = sdf.format(lastDayOfWeek);
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("portalId", portal.getPortalId());
		paramMap.put("useFlag", "1");
		paramMap.put("mid", mid);
		
		List<Cartooletc> categoryList = cartooletcService.categoryList(paramMap);
		List<Cartooletc> regionList = cartooletcService.regionList(paramMap);

		mav.addObject("startDayOfWeek", startDate);
		mav.addObject("endDayOfWeek", endDate);
		mav.addObject("regionList", regionList);
		mav.addObject("regionId", regionId);
		
		return mav;
	}
	@RequestMapping(value = "/getPeriodOfWeek.do")
	public @ResponseBody Map<String, ? extends Object> getPeriodOfWeek(String movement,
			@RequestParam(value="startDate", required=false, defaultValue="0") long startDate,
			@RequestParam(value="endDate", required=false, defaultValue="0") long endDate
		) {

		Map<String, String> result = new HashMap<String, String>();
		
		Date today = null;
		Date firstDayOfWeek = null;
		Date lastDayOfWeek = null;
		
		if (startDate == 0 || endDate == 0) {
			
			Date clientNow = timeZoneSupportService.convertTimeZone();
			today = DateUtils.truncate(clientNow, Calendar.DATE);
			
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(today);

			cal.setFirstDayOfWeek(Calendar.SUNDAY); 

			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); 
			cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1))); 

			firstDayOfWeek = cal.getTime();
			
			cal.add(Calendar.DAY_OF_MONTH, 6);
			
			lastDayOfWeek =  cal.getTime();
		} else {
			firstDayOfWeek = new Date(startDate);
			lastDayOfWeek = new Date(endDate);
		}
		
		if(!StringUtil.isEmpty(movement)) {
			
			if(movement.equals("PREV")) {
				
				Calendar startCal = Calendar.getInstance();
				Calendar endCal = Calendar.getInstance();
				
				startCal.setTime(firstDayOfWeek);
				endCal.setTime(lastDayOfWeek);
				
				startCal.add(Calendar.DATE, -7);
				endCal.add(Calendar.DATE, -7);
				
				firstDayOfWeek = startCal.getTime();
				lastDayOfWeek = endCal.getTime();
				
			} else if(movement.equals("NEXT")) {
				
				Calendar startCal = Calendar.getInstance();
				Calendar endCal = Calendar.getInstance();
				
				startCal.setTime(firstDayOfWeek);
				endCal.setTime(lastDayOfWeek);
				
				startCal.add(Calendar.DATE, 7);
				endCal.add(Calendar.DATE, 7);
				
				firstDayOfWeek = startCal.getTime();
				lastDayOfWeek = endCal.getTime();
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd"); 
		
		String start = sdf.format(firstDayOfWeek);	
		String end = sdf.format(lastDayOfWeek);
		
		result.put("startDate", start);
		result.put("endDate", end);
		
		return result;
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
	public @ResponseBody List<MeetingRoom> meetingRoomList(String buildingId,
			@RequestParam(value="startDate", required=false, defaultValue="0") long startDate,
			@RequestParam(value="endDate", required=false, defaultValue="0") long endDate) {

		List<MeetingRoom> meetingRoomList;

		try {
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			MeetingRoom meetingRoom = new MeetingRoom();
			meetingRoom.setBuildingId(buildingId);
			meetingRoom.setPortalId(portal.getPortalId());
			meetingRoom.setUseFlag("1");

			meetingRoomList = meetingRoomService.select(meetingRoom);

			Date today = null;
			Date firstDayOfWeek = null;
			Date lastDayOfWeek = null;
			
			if(startDate == 0 || endDate == 0) {
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(today);

				cal.setFirstDayOfWeek(Calendar.SUNDAY); 

				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); 
				cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1))); 

				firstDayOfWeek = cal.getTime();
				
				cal.add(Calendar.DAY_OF_MONTH, 6);
				
				lastDayOfWeek =  cal.getTime();
			} else {
				firstDayOfWeek = new Date(startDate);
				lastDayOfWeek = new Date(endDate);;
			}
			
			List<Map<String, Object>> scheduleList = scheduleService.readMeetingRoomScheduleByBuildingFloor(buildingId, null, firstDayOfWeek, lastDayOfWeek);
			if(scheduleList != null && scheduleList.size() > 0) {
				makeMeetingRoomIntoSchedule(meetingRoomList, scheduleList);
			}
			
		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return meetingRoomList;
	}
	
	@RequestMapping(value = "/cartooletcList.do")
	public @ResponseBody List<Cartooletc> cartooletcList(String mid,String regionId,
			@RequestParam(value="startDate", required=false, defaultValue="0") long startDate,
			@RequestParam(value="endDate", required=false, defaultValue="0") long endDate) {

		List<Cartooletc> cartooletcList;

		try {
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			Cartooletc cartooletc = new Cartooletc();
			cartooletc.setRegionId(regionId);
			cartooletc.setPortalId(portal.getPortalId());
			cartooletc.setUseFlag("1");
			cartooletc.setViewYn("1");
			cartooletc.setMid(mid);
			cartooletcList = cartooletcService.select(cartooletc);

			Date today = null;
			Date firstDayOfWeek = null;
			Date lastDayOfWeek = null;
			
			if(startDate == 0 || endDate == 0) {
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(today);

				cal.setFirstDayOfWeek(Calendar.SUNDAY); 

				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); 
				cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1))); 

				firstDayOfWeek = cal.getTime();
				
				cal.add(Calendar.DAY_OF_MONTH, 6);
				
				lastDayOfWeek =  cal.getTime();
			} else {
				firstDayOfWeek = new Date(startDate);
				lastDayOfWeek = new Date(endDate);;
			}
			
			List<Map<String, Object>> scheduleList = scheduleService.readCartooletcScheduleByCategoryRegion(null, regionId, firstDayOfWeek, lastDayOfWeek);
			if(scheduleList != null && scheduleList.size() > 0) {
				makeCartooletcIntoSchedule(cartooletcList, scheduleList);
			}
			
		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return cartooletcList;
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
	
	private List<List<Map<String, Object>>> makeMeetingRoomWeekTimeList(List<MeetingRoom> meetingRoomList, List<Map<String, Object>> scheduleList, String startDayOfWeek) {

		List<List<Map<String, Object>>> roomTimeList = new ArrayList<List<Map<String, Object>>>();

		for (MeetingRoom meetingRoom : meetingRoomList) {

			List<Map<String, Object>> timeList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> tmpScheduleList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> schedule : scheduleList) {

				if (meetingRoom.getMeetingRoomId().equals((String) schedule.get("meetingRoomId"))) {
					
					tmpScheduleList.add(schedule);
				}

			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isHeader", "Y");
			map.put("meetingRoomId", meetingRoom.getMeetingRoomId());
			map.put("meetingRoomName", meetingRoom.getMeetingRoomName());
			map.put("buildingName", meetingRoom.getBuildingName());
			map.put("floorName", meetingRoom.getFloorName());
			
			timeList.add(map);

			timeList.addAll(meetingRoomService.makeMeetingWeekTimeList(meetingRoom.getMeetingRoomId(), tmpScheduleList, startDayOfWeek));

			roomTimeList.add(timeList);
		}

		return roomTimeList;
	}
}
