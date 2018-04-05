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

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/day")
public class MeetingRoomByDayController extends BaseController {

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
	public ModelAndView meetingRoomMain(@RequestParam(value="date", required=false, defaultValue="0") long date) {

		ModelAndView mav = new ModelAndView("lightpack/meetingroom/tab/day/meetingRoomMain");//	/lightpack/meetingroom/day/meetingRoomMain

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("portalId", portal.getPortalId());
		paramMap.put("useFlag", "1");
		
		List<BuildingFloor> buildingList = buildingFloorService.buildingList(paramMap);
		
		Date today = null;
			
		if(date == 0) {
			Date clientNow = timeZoneSupportService.convertTimeZone();
			today = DateUtils.truncate(clientNow, Calendar.DATE);
		} else {
			today = new Date(date);
		}

		mav.addObject("buildingList", buildingList);
		mav.addObject("today", DateUtil.getFmtDateString(today, "yyyy.MM.dd"));
		
		return mav;
	}

	@RequestMapping(value = "/cartooletcMain.do")
	public ModelAndView cartooletcMain(String mid,@RequestParam(value="date", required=false, defaultValue="0") long date) {

		ModelAndView mav = new ModelAndView("lightpack/meetingroom/tab/day/cartooletcMain");//	/lightpack/meetingroom/day/meetingRoomMain

		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("portalId", portal.getPortalId());
		paramMap.put("useFlag", "1");
		paramMap.put("mid", mid);
		
		List<Cartooletc> categoryList = cartooletcService.categoryList(paramMap);
		List<Cartooletc> regionList = cartooletcService.regionList(paramMap);
		
		Date today = null;
			
		if(date == 0) {
			Date clientNow = timeZoneSupportService.convertTimeZone();
			today = DateUtils.truncate(clientNow, Calendar.DATE);
		} else {
			today = new Date(date);
		}

		mav.addObject("categoryList", categoryList);
		mav.addObject("regionList", regionList);
		
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
	public @ResponseBody List<MeetingRoom> meetingRoomList(String buildingId, @RequestParam(value="curDate", required=false, defaultValue="0") long curDate) {

		List<MeetingRoom> meetingRoomList;
		
		try {
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");

			MeetingRoom meetingRoom = new MeetingRoom();
			meetingRoom.setBuildingId(buildingId);
			meetingRoom.setPortalId(portal.getPortalId());
			meetingRoom.setUseFlag("1");
			
			meetingRoomList = meetingRoomService.select(meetingRoom);
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@ meetingRoomList.size():"+meetingRoomList.size());

			Date today = null;
			
			if(curDate == 0) {
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
			} else {
				today = new Date(curDate);
			}
			
			List<Map<String, Object>> scheduleList = scheduleService.readMeetingRoomScheduleByBuildingFloor(buildingId, null, today, today);
			if(scheduleList != null && scheduleList.size() > 0) {
				makeMeetingRoomIntoSchedule(meetingRoomList, scheduleList);
			}

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return meetingRoomList;
	}
	
	
	@RequestMapping(value = "/cartooletcList.do")
	public @ResponseBody List<Cartooletc> cartooletcList(String mid,String regionId, @RequestParam(value="curDate", required=false, defaultValue="0") long curDate) {

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
			
			if(curDate == 0) {
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
			} else {
				today = new Date(curDate);
			}
			
			List<Map<String, Object>> scheduleList = scheduleService.readCartooletcScheduleByCategoryRegion(null, regionId, today, today);
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
