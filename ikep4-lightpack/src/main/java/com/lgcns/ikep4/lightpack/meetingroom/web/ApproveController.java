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
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.model.Approve;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.meetingroom.service.ApproveService;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomMainService;
import com.lgcns.ikep4.lightpack.meetingroom.service.ReserveService;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/approve")
public class ApproveController extends BaseController {
	
	private static final String SUCCESS = "success";

	private static final String FAIL = "fail";
	
	@Autowired
	ApproveService approveService;
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CartooletcService cartooletcService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReserveService reserveService;
	
	@Autowired
	private MeetingRoomMainService meetingRoomService;
	
	@RequestMapping(value = "/approveList.do")
	public ModelAndView approveList(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/approve/approveList");

		User user = getUser();
		Portal portal = getPortal();

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}
			
			Date today = null;
			Date startDate = null;
			Date endDate = null;
			
			if(StringUtil.isEmpty(startPeriod) || StringUtil.isEmpty(endPeriod)) {
				
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(today);
				
				cal.add(Calendar.MONTH, -1); 
				
				startDate = cal.getTime();
				endDate = today;
			} else {
				
				String startYear = startPeriod.substring(0, 4);
				String startMonth = startPeriod.substring(5, 7);
				String startDay = startPeriod.substring(8, 10);
				
				String endYear = endPeriod.substring(0, 4);
				String endMonth = endPeriod.substring(5, 7);
				String endDay = endPeriod.substring(8, 10);
				
				startDate = DateUtil.toDate(startYear + startMonth + startDay);
				endDate = DateUtil.toDate(endYear + endMonth + endDay);
			}

			searchCondition.setPortalId(portal.getPortalId());
			searchCondition.setUserId(user.getUserId());
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			SearchResult<Map<String, Object>> searchResult = approveService.list(searchCondition);

			BoardCode boardCode = new BoardCode();

			Boolean isSystemAdmin = this.isSystemAdmin(user);
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("portalId", portal.getPortalId());
			param.put("userId", user.getUserId());
			
			Boolean isMeetingRoomManager = this.isMeetingRoomManager(param);
			Boolean isCarToolManager = this.isCarToolManager(param);
			
			mav.addObject("isSystemAdmin", isSystemAdmin);
			mav.addObject("isMeetingRoomManager", isMeetingRoomManager);
			mav.addObject("isCarToolManager", isCarToolManager);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/approveCarList.do")
	public ModelAndView approveCarList(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/approve/approveCarList");

		User user = getUser();
		Portal portal = getPortal();

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("SORT_ORDER");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}
			
			Date today = null;
			Date startDate = null;
			Date endDate = null;
			
			if(StringUtil.isEmpty(startPeriod) || StringUtil.isEmpty(endPeriod)) {
				
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(today);
				
				cal.add(Calendar.MONTH, -1); 
				
				startDate = cal.getTime();
				endDate = today;
			} else {
				
				String startYear = startPeriod.substring(0, 4);
				String startMonth = startPeriod.substring(5, 7);
				String startDay = startPeriod.substring(8, 10);
				
				String endYear = endPeriod.substring(0, 4);
				String endMonth = endPeriod.substring(5, 7);
				String endDay = endPeriod.substring(8, 10);
				
				startDate = DateUtil.toDate(startYear + startMonth + startDay);
				endDate = DateUtil.toDate(endYear + endMonth + endDay);
			}

			searchCondition.setPortalId(portal.getPortalId());
			searchCondition.setUserId(user.getUserId());
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			SearchResult<Map<String, Object>> searchResult = approveService.listCar(searchCondition);

			BoardCode boardCode = new BoardCode();

			Boolean isSystemAdmin = this.isSystemAdmin(user);
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("portalId", portal.getPortalId());
			param.put("userId", user.getUserId());
			
			Boolean isMeetingRoomManager = this.isMeetingRoomManager(param);
			Boolean isCarToolManager = this.isCarToolManager(param);
			
			mav.addObject("isSystemAdmin", isSystemAdmin);
			mav.addObject("isMeetingRoomManager", isMeetingRoomManager);
			mav.addObject("isCarToolManager", isCarToolManager);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	/**
	 * 일정 조회
	 * @param scheduleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/approveView.do")
	public ModelAndView approveView(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition, @RequestParam("scheduleId") String scheduleId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/approve/approveView");
		
		try {
			
			boolean isUpdate = false;
			Schedule schedule = this.getReserveData(scheduleId);
			
			String userId = getUser().getUserId();
			String regUserId = schedule.getRegisterId();
			
			List<Map> mandators = calendarService.getMyMandators(userId);
			
			isUpdate = scheduleService.isEditable(userId, regUserId);
			
			if(isUpdate == false) {
				
				String[] users = new String[mandators.size()];
				int idx = 0;
				
				for(Map<String, Object> user : mandators) {
					
					users[idx] = user.get("mandatorId").toString();
					
					idx++;
				}
				
				isUpdate = scheduleService.isEditable(users, schedule.getRegisterId());
			}
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("roomOrTool", "room");
			param.put("scheduleId", scheduleId);
			Approve approve = approveService.getApproveMap(param);
			//Approve approve = approveService.getApprove(scheduleId);
			
			mav.addObject("mandators", mandators);
			mav.addObject("isUpdate", isUpdate);
			mav.addObject("registerId", schedule.getRegisterId());
			
			mav.addObject("scheduleInfo", (new ObjectMapper()).writeValueAsString(schedule));
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", startPeriod);
			mav.addObject("endPeriod", endPeriod);
			mav.addObject("approve", approve);
		} catch(Exception e) {
			
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}
	
	/**
	 * 일정 삭제
	 * @param params
	 * @return
	 */
	@RequestMapping("/deleteReserve")
	public @ResponseBody
	Map<String, ? extends Object> deleteReserve(@RequestBody UpdateScheduleVO params) {

		Map<String, String> result = new HashMap<String, String>();
		
		try {
			
			calendarService.deleteSchedule(params);
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("scheduleId", params.getNewSchedule().getScheduleId());
			
			approveService.delete(param);
			
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		
		return result;
	}
	
	/**
	 * 회의실 사용 승인
	 * @param 
	 * @return
	 */
	@RequestMapping("/approveUse")
	public ModelAndView approveUse(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition, @RequestParam("scheduleId") String scheduleId, String roomOrTool) {
		
		ModelAndView mav = new ModelAndView();
		
		User user = getUser();
		
		try {
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("roomOrTool", roomOrTool);
			param.put("scheduleId", scheduleId);
			Approve approve = approveService.getApproveMap(param);
			//Approve approve = approveService.getApprove(scheduleId);
			approve.setApproveStatus("A");
			approve.setUpdaterId(user.getUserId());
			approve.setUpdaterName(user.getUserName());
			approve.setRoomOrTool(roomOrTool);
			
			approveService.update(approve);
			

			Schedule schedule = calendarService.getScheduleAllData(scheduleId);
			User scheduleOwner = userService.read(schedule.getRegisterId());
			
			if("room".equals(roomOrTool)){
				mav = this.approveList(startPeriod, endPeriod, searchCondition);
				reserveService.sendReservationMail("room",  "[회의실 예약 승인완료]" , schedule, user,  scheduleOwner);
			}else if("tool".equals(roomOrTool)){
				mav = this.approveCarList(startPeriod, endPeriod, searchCondition);
				reserveService.sendReservationMail("car",  "[자원 예약 승인완료]" , schedule, user,  scheduleOwner);
			}
			
		} catch(Exception e) {
			
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}
	
	@RequestMapping("/approveFirstUse")
	public ModelAndView approveFirstUse(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition, @RequestParam("scheduleId") String scheduleId, String roomOrTool) {
		
		ModelAndView mav = new ModelAndView();
		
		User user = getUser();
		
		try {
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("roomOrTool", roomOrTool);
			param.put("scheduleId", scheduleId);
			Approve approve = approveService.getApproveMap(param);
			//Approve approve = approveService.getApprove(scheduleId);
			approve.setApproveStatus("S");
			approve.setUpdaterId(user.getUserId());
			approve.setUpdaterName(user.getUserName());
			approve.setRoomOrTool(roomOrTool);
			
			approveService.update(approve);
			

			Schedule schedule = calendarService.getScheduleAllData(scheduleId);
			//User scheduleOwner = userService.read(schedule.getRegisterId());
			
			if("room".equals(roomOrTool)){
				mav = this.approveList(startPeriod, endPeriod, searchCondition);
				//reserveService.sendReservationMail("room",  "[회의실 예약 승인완료]" , schedule, user,  scheduleOwner);
				MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
				User manager = userService.read(meetingRoom.getSubManagerId());
				reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
			}else if("tool".equals(roomOrTool)){
				mav = this.approveCarList(startPeriod, endPeriod, searchCondition);
				//reserveService.sendReservationMail("car",  "[자원 예약 승인완료]" , schedule, user,  scheduleOwner);
				Cartooletc cartooletc = cartooletcService.read(schedule.getCartooletcId());
				User manager = userService.read(cartooletc.getSubManagerId());
				reserveService.sendReservationMail("car",  "[자원 예약 승인요청]" , schedule, user,  manager);
			} 
			
		} catch(Exception e) {
			
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}
	
	@RequestMapping("/approveSecondUse")
	public ModelAndView approveSecondUse(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition, @RequestParam("scheduleId") String scheduleId, String roomOrTool) {
		
		ModelAndView mav = new ModelAndView();
		
		User user = getUser();
		
		try {
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("roomOrTool", roomOrTool);
			param.put("scheduleId", scheduleId);
			Approve approve = approveService.getApproveMap(param);
			//Approve approve = approveService.getApprove(scheduleId);
			approve.setApproveStatus("L");
			approve.setUpdaterId(user.getUserId());
			approve.setUpdaterName(user.getUserName());
			approve.setRoomOrTool(roomOrTool);
			
			approveService.update(approve);
			

			Schedule schedule = calendarService.getScheduleAllData(scheduleId);
			//User scheduleOwner = userService.read(schedule.getRegisterId());
			
			if("room".equals(roomOrTool)){
				mav = this.approveList(startPeriod, endPeriod, searchCondition);
				//reserveService.sendReservationMail("room",  "[회의실 예약 승인완료]" , schedule, user,  scheduleOwner);
				MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
				User manager = userService.read(meetingRoom.getLastManagerId());
				reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
			}else if("tool".equals(roomOrTool)){
				mav = this.approveCarList(startPeriod, endPeriod, searchCondition);
				//reserveService.sendReservationMail("car",  "[자원 예약 승인완료]" , schedule, user,  scheduleOwner);
				Cartooletc cartooletc = cartooletcService.read(schedule.getCartooletcId());
				User manager = userService.read(cartooletc.getLastManagerId());
				reserveService.sendReservationMail("car",  "[자원 예약 승인요청]" , schedule, user,  manager);
			}
			
		} catch(Exception e) {
			
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}
	
	/**
	 * 회의실 사용 승인 반려
	 * @param 
	 * @return
	 */
	@RequestMapping("/rejectUse")
	public ModelAndView rejectUse(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition, @RequestParam("scheduleId") String scheduleId, String rejectReason, String roomOrTool) {
		
		ModelAndView mav = new ModelAndView();
		
		User user = getUser();
		
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("roomOrTool", roomOrTool);
			param.put("scheduleId", scheduleId);
			Approve approve = approveService.getApproveMap(param);
			//Approve approve = approveService.getApprove(scheduleId);
			approve.setApproveStatus("R");
			approve.setRejectReason(rejectReason);
			approve.setUpdaterId(user.getUserId());
			approve.setUpdaterName(user.getUserName());
			approve.setRoomOrTool(roomOrTool);
			
			approveService.update(approve);
			
			Schedule schedule = calendarService.getScheduleAllData(scheduleId);
			User scheduleOwner = userService.read(schedule.getRegisterId());
			
			if("room".equals(roomOrTool)){
				mav = this.approveList(startPeriod, endPeriod, searchCondition);
				reserveService.sendReservationMail("room",  "[회의실 예약 반려]" , schedule, user,  scheduleOwner);
			}else if("tool".equals(roomOrTool)){
				mav = this.approveCarList(startPeriod, endPeriod, searchCondition);
				reserveService.sendReservationMail("car",  "[자원 예약 반려]" , schedule, user,  scheduleOwner);
			}
			
		} catch(Exception e) {
			
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}
	
	/**
	 * 일정의 모든 정보 제공 - 일정 상세조회에서 사용
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping("/getReserveData")
	public @ResponseBody
	Schedule getReserveData(@RequestParam("scheduleId") String scheduleId) {

		Schedule schedule = null;
		try {
			schedule = calendarService.getScheduleAllData(scheduleId, getUser().getLocaleCode());
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return schedule;
	}
	
	private User getUser() {
		
		return (User) getRequestAttribute("ikep.user");
	}
	
	private Portal getPortal() {
		
		return (Portal) getRequestAttribute("ikep.portal");
	}
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 *
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		
		return this.aclService.isSystemAdmin("MeetingRoom", user.getUserId());
	}
	
	public boolean isMeetingRoomManager(Map<String, String> param) {
		
		return this.approveService.isMeetingRoomManager(param);
	}
	public boolean isCarToolManager(Map<String, String> param) {
		
		return this.approveService.isCarToolManager(param);
	}
}
