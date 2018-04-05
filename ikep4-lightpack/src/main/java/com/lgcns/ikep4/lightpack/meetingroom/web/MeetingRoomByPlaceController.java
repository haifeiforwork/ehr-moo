/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.lgcns.ikep4.support.security.acl.service.ACLService;
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
@RequestMapping(value = "/lightpack/meetingroom/place")
public class MeetingRoomByPlaceController extends BaseController {

	@Autowired
	private ACLService aclService;
	
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

	@RequestMapping(value = "/meetingRoomList.do")
	public @ResponseBody List<MeetingRoom> meetingRoomList(String buildingId) {
		List<MeetingRoom> meetingRoomList;
		try {
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			MeetingRoom meetingRoom = new MeetingRoom();
			meetingRoom.setBuildingId(buildingId);
			meetingRoom.setPortalId(portal.getPortalId());
			meetingRoom.setUseFlag("1");

			meetingRoomList = meetingRoomService.select(meetingRoom);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		
		return meetingRoomList;
	}

	@RequestMapping(value = "/cartooletcList.do")
	public @ResponseBody List<Cartooletc> cartooletcList(String regionId,String mid) {
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
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		
		return cartooletcList;
	}
	
	@RequestMapping(value = "/meetingRoomMain.do")
	public ModelAndView meetingRoomMain(String buildingId, String meetingRoomId, @RequestParam(value="date", required=false, defaultValue="0") long date) {

		ModelAndView mav = new ModelAndView("lightpack/meetingroom/tab/place/meetingRoomMain");//	/lightpack/meetingroom/place/meetingRoomMain

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

		mav.addObject("buildingId", buildingId);
		mav.addObject("meetingRoomId", meetingRoomId);
		mav.addObject("today", DateUtil.getFmtDateString(today, "yyyy-MM-dd"));//currentDate
		mav.addObject("buildingList", buildingList);

		return mav;
	}
	
	@RequestMapping(value = "/cartooletcMain.do")
	public ModelAndView cartooletcMain(String mid, String regionId, String cartooletcId, @RequestParam(value="date", required=false, defaultValue="0") long date) {

		ModelAndView mav = new ModelAndView("lightpack/meetingroom/tab/place/cartooletcMain");//	/lightpack/meetingroom/place/meetingRoomMain

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

		mav.addObject("regionId", regionId);
		mav.addObject("cartooletcId", cartooletcId);
		mav.addObject("today", DateUtil.getFmtDateString(today, "yyyy-MM-dd"));//currentDate
		mav.addObject("regionList", regionList);

		return mav;
	}
	
	@RequestMapping(value = "/meetingRoomView.do")
	public ModelAndView meetingRoomView(String meetingRoomId, Model model) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/tab/place/meetingRoomView");

		try {
			
			MeetingRoom meetingRoom = meetingRoomService.read(meetingRoomId);

			String checkFavorite = meetingRoomService.checkFavorite(meetingRoomId);

			model.addAttribute("meetingRoom", meetingRoom);
			model.addAttribute("checkFavorite", checkFavorite);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	@RequestMapping(value = "/cartooletcView.do")
	public ModelAndView cartooletcView(String cartooletcId, Model model) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/tab/place/cartooletcView");

		try {
			
			Cartooletc cartooletc = cartooletcService.read(cartooletcId);

			String checkFavorite = cartooletcService.checkFavorite(cartooletcId);

			model.addAttribute("cartooletc", cartooletc);
			model.addAttribute("checkFavorite", checkFavorite);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}
	
	@RequestMapping(value = "/meetingRoomCalendar.do")
	public ModelAndView meetingRoomCalendar() {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/tab/place/meetingRoomCalendar");

		return mav;
	}

	@RequestMapping(value = "/meetingRoomReserveList.do")
	public @ResponseBody List<Map<String, Object>> meetingRoomReserveList(String meetingRoomId, @RequestParam(value="curDate", required=false, defaultValue="0") long curDate) {

		List<Map<String, Object>> scheduleList;
		
		try {

			Date today = null;
			if(curDate == 0) {
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
			} else {
				today = new Date(curDate);
			}
			
			scheduleList = scheduleService.readMeetingRoomScheduleById(meetingRoomId, today, today);
		
			if(scheduleList.size() > 0) {
				User user = getUser();
				boolean isAdmin = isSystemAdmin(user);
				for(Map<String, Object> reserve : scheduleList) {
					if(isAdmin || user.getUserId().equals(reserve.get("registerId"))) { 
						reserve.put("schedulePublic", 0);
					}
								

					reserve.put("startDate", ((Date)reserve.get("startDate")).getTime());
					reserve.put("endDate", ((Date)reserve.get("endDate")).getTime());

				}
				//List<Map<String, String>> timeList = meetingRoomService.makeMeetingTimeList(meetingRoomId, scheduleList);
				//mav.addObject("timeList", timeList);
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return scheduleList;
	}
	
	
	@RequestMapping(value = "/cartooletcReserveList.do")
	public @ResponseBody List<Map<String, Object>> cartooletcReserveList(String cartooletcId, @RequestParam(value="curDate", required=false, defaultValue="0") long curDate) {

		List<Map<String, Object>> scheduleList;
		
		try {

			Date today = null;
			if(curDate == 0) {
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
			} else {
				today = new Date(curDate);
			}
			
			scheduleList = scheduleService.readCartooletcScheduleById(cartooletcId, today, today);
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@scheduleList.size():"+scheduleList.size());
			if(scheduleList.size() > 0) {

				User user = getUser();
				boolean isAdmin = isSystemAdmin(user);
				for(Map<String, Object> reserve : scheduleList) {
					if(isAdmin || user.getUserId().equals(reserve.get("registerId"))) { 
						reserve.put("schedulePublic", 0);
					}
								

					reserve.put("startDate", ((Date)reserve.get("startDate")).getTime());
					reserve.put("endDate", ((Date)reserve.get("endDate")).getTime());

				}
				//List<Map<String, String>> timeList = meetingRoomService.makeMeetingTimeList(meetingRoomId, scheduleList);
				//mav.addObject("timeList", timeList);
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return scheduleList;
	}
	
	
	@RequestMapping(value = "/addFavorite.do")
	public @ResponseBody
	String addFavorite(String meetingRoomId) {

		try {

			meetingRoomService.addFavorite(meetingRoomId);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}

	@RequestMapping(value = "/delFavorite.do")
	public @ResponseBody
	String delFavorite(String meetingRoomId) {

		try {

			meetingRoomService.delFavorite(meetingRoomId);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}
	
	
	@RequestMapping(value = "/addCarFavorite.do")
	public @ResponseBody
	String addCarFavorite(String cartooletcId) {

		try {

			cartooletcService.addFavorite(cartooletcId);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}

	@RequestMapping(value = "/delCarFavorite.do")
	public @ResponseBody
	String delCarFavorite(String cartooletcId) {

		try {

			cartooletcService.delFavorite(cartooletcId);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 *
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	private boolean isSystemAdmin(User user) {
		
		return this.aclService.isSystemAdmin("MeetingRoom", user.getUserId());
	}
	
	private User getUser() {
		return (User) getRequestAttribute("ikep.user");
	}

}
