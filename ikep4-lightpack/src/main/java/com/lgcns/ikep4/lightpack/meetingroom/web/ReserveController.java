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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
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
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/reserve")
public class ReserveController extends BaseController {
	
	private static final String SUCCESS = "success";

	private static final String FAIL = "fail";
	
	public static final String DUPLICATE = "duplicate";
	
	@Autowired
	private ReserveService reserveService;
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private MeetingRoomMainService meetingRoomService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private ApproveService approveService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CartooletcService cartooletcService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/reserveForm.do")
	public ModelAndView reserveForm(String scheduleId, String meetingRoomId, String day, String from, String to) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/reserve/reserveForm");

		User user = getUser();

		try {
			Schedule schedule = null;
			
			if (scheduleId != null) {
				
				schedule = calendarService.getScheduleAllData(scheduleId, user.getLocaleCode());
				
				String startDate = DateUtil.getFmtDateString(schedule.getStartDate(), "yyyy.MM.dd HH:mm");
				schedule.setSdate(startDate.substring(0, 10));
				schedule.setStime(startDate.substring(11, 16));
				
				String endDate = DateUtil.getFmtDateString(schedule.getEndDate(), "yyyy.MM.dd HH:mm");
				schedule.setEdate(endDate.substring(0, 10));
				schedule.setEtime(endDate.substring(11, 16));
				
//				if(meeting.getAlarmList() != null) {
//					
//					for(Alarm alarm : meeting.getAlarmList()) {
//						
//						meeting.setAlarmTime(alarm.getAlarmTime());
//						meeting.setAlarmType(alarm.getAlarmType());
//					}
//				}
			} else {
				
				schedule = new Schedule();
				schedule.setSdate(day.replace("-", "."));
				schedule.setEdate(day.replace("-", "."));
				schedule.setMeetingRoomId(meetingRoomId);
				schedule.setStime(from);
				schedule.setEtime(to);
				schedule.setWholeday(0);
				schedule.setRepeat(0);
				schedule.setSchedulePublic(0);
				schedule.setAlarmTime("0");
				schedule.setAlarmType("2");


				MeetingRoom meetingRoom = meetingRoomService.read(meetingRoomId);
				schedule.setPlace(meetingRoom.getBuildingName() + " " + meetingRoom.getFloorName() + " " + meetingRoom.getMeetingRoomName());
				
				String startCal = day.replace("-", "/");
				String endCal = day.replace("-", "/");
				
				mav.addObject("startDate", startCal + " " + from);
				mav.addObject("endDate", endCal + " " + to);
			}
			
			mav.addObject("schedule", schedule);
			//model.addAttribute("timeList", makeTimeList());
			mav.addObject("meetingRoomId", meetingRoomId);

			//List<PlannerCategory> categoryList = categoryService.getList(user.getLocaleCode());
			//model.addAttribute("categoryList", categoryList);

			//ObjectMapper mapper = new ObjectMapper();
			
			//try {
				
				//mav.addObject("fileDateList", mapper.writeValueAsString(schedule.getFileDataList()));
			//} catch (Exception e) {
				
				//throw new IKEP4ApplicationException("", e);
			//}

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}

	@RequestMapping(value = "/reserveCarForm.do")
	public ModelAndView reserveCarForm(String scheduleId, String cartooletcId, String day, String from, String to) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/reserve/reserveCarForm");

		User user = getUser();

		try {
			Schedule schedule = null;
			
			if (scheduleId != null) {
				
				schedule = calendarService.getScheduleAllData(scheduleId, user.getLocaleCode());
				
				String startDate = DateUtil.getFmtDateString(schedule.getStartDate(), "yyyy.MM.dd HH:mm");
				schedule.setSdate(startDate.substring(0, 10));
				schedule.setStime(startDate.substring(11, 16));
				
				String endDate = DateUtil.getFmtDateString(schedule.getEndDate(), "yyyy.MM.dd HH:mm");
				schedule.setEdate(endDate.substring(0, 10));
				schedule.setEtime(endDate.substring(11, 16));
				
			} else {
				
				schedule = new Schedule();
				schedule.setSdate(day.replace("-", "."));
				schedule.setEdate(day.replace("-", "."));

				schedule.setStime(from);
				schedule.setEtime(to);
				schedule.setWholeday(0);
				schedule.setRepeat(0);
				schedule.setSchedulePublic(0);
				schedule.setAlarmTime("0");
				schedule.setAlarmType("2");
				
				schedule.setCartooletcId(cartooletcId);

				Cartooletc cartooletc = cartooletcService.read(cartooletcId);
				schedule.setPlace(cartooletc.getCategoryName() + " " + cartooletc.getRegionName() + " " + cartooletc.getCartooletcName());
				
				String startCal = day.replace("-", "/");
				String endCal = day.replace("-", "/");
				
				mav.addObject("startDate", startCal + " " + from);
				mav.addObject("endDate", endCal + " " + to);
			}
			
			mav.addObject("schedule", schedule);
			mav.addObject("cartooletcId", cartooletcId);


		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}
	
	/**
	 * 일정의 모든 정보 제공 - 일정 상세조회에서 사용
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping("/getReserveData")
	public @ResponseBody Schedule getReserveData(@RequestParam("scheduleId") String scheduleId) {

		Schedule schedule = null;
		try {
			schedule = calendarService.getScheduleAllData(scheduleId, getUser().getLocaleCode());
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return schedule;
	}
	
	@RequestMapping("/checkDuplicate")
	public @ResponseBody List<Map<String, Object>> checkDuplicate(@RequestBody Schedule schedule) {

		User user = getUser();
		
		List<Map<String, Object>> dupliList =reserveService.selectExists(schedule);
		
		return dupliList;
	}
	
	@RequestMapping("/checkCarDuplicate")
	public @ResponseBody List<Map<String, Object>> checkCarDuplicate(@RequestBody Schedule schedule) {

		User user = getUser();
		
		List<Map<String, Object>> dupliList =reserveService.selectCarExists(schedule);
		
		return dupliList;
	}
	
	/**
	 * 일정 생성
	 * @param schedule
	 * @param response
	 * @return
	 */
	@RequestMapping("/createReserve.do")
	public @ResponseBody
	Map<String, ? extends Object> createReserve(@RequestBody Schedule schedule, HttpServletResponse response) {

		Map<String, String> result = new HashMap<String, String>();
		
		try {
			User user = getUser();
			
			schedule.setPortalId(user.getPortalId());
			schedule.setRegisterId(user.getUserId());
			schedule.setRegisterName(user.getUserName());
			schedule.setUpdaterId(user.getUserId());
			schedule.setUpdaterName(user.getUserName());
			
			String id = calendarService.create(schedule, user, schedule.isSendmail());
			
			
			
			if (id != null) {
				if(id.equals(ReserveController.DUPLICATE)) {
					result.put("success", ReserveController.DUPLICATE);
				} else {
					result.put("scheduleId", id);
					result.put("success", SUCCESS);
					/*MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
					if( "N".equals(meetingRoom.getAutoApprove()) && !StringUtil.isEmpty(meetingRoom.getManagerId())){
						User manager = userService.read(meetingRoom.getManagerId());
						reserveService.sendReservationMail("room",  "[회의실 예약 승인요청]" , schedule, user,  manager);
					}*/
				}
			} else {
				
				result.put("success", FAIL);
			}
			
/*
			List<Map<String, Object>> list =reserveService.selectExists(schedule);
			
			if(list.size() < 1) {

				String id = calendarService.create(schedule, user, schedule.isSendmail());
				
				MeetingRoom meetingRoom = meetingRoomService.read(schedule.getMeetingRoomId());
				
				String managerId = meetingRoom.getManagerId();
				
				Approve approve = new Approve();
				
				approve.setScheduleId(id);
				approve.setMeetingRoomId(schedule.getMeetingRoomId());
				approve.setApproveStatus("W");
				ModelBeanUtil.bindRegisterInfo(approve, user.getUserId(), user.getUserName());
				
				if(!StringUtil.isEmpty(managerId)) {
					
					approve.setApproveStatus("W");
				} else {
	
					approve.setApproveStatus("A");
				}
				
				approveService.create(approve);
				
				if (id != null) {
					
					result.put("scheduleId", id);
					result.put("success", SUCCESS);
				} else {
					
					result.put("success", FAIL);
				}
				
				result.put("meetingRoomId", schedule.getMeetingRoomId());
			} else {
				
				result.put("success", DUPLICATE);
			}
*/
		} catch (Exception ex) {
			
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		
		return result;
	}
	
	
	/**
	 * 일정 생성
	 * @param schedule
	 * @param response
	 * @return
	 */
	@RequestMapping("/createCarReserve.do")
	public @ResponseBody
	Map<String, ? extends Object> createCarReserve(@RequestBody Schedule schedule, HttpServletResponse response) {

		Map<String, String> result = new HashMap<String, String>();
		
		try {
			User user = getUser();
			
			schedule.setPortalId(user.getPortalId());
			schedule.setRegisterId(user.getUserId());
			schedule.setRegisterName(user.getUserName());
			schedule.setUpdaterId(user.getUserId());
			schedule.setUpdaterName(user.getUserName());
			
			String id = calendarService.create(schedule, user, schedule.isSendmail());
			
			if (id != null) {
				if(id.equals(ReserveController.DUPLICATE)) {
					result.put("success", ReserveController.DUPLICATE);
				} else {
					result.put("scheduleId", id);
					result.put("success", SUCCESS);
					Cartooletc cartooletc = cartooletcService.read(schedule.getCartooletcId());
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@:cartooletc.getAutoApprove():"+cartooletc.getAutoApprove()+":"+cartooletc.getManagerId());
					
					if( "N".equals(cartooletc.getAutoApprove()) && !StringUtil.isEmpty(cartooletc.getManagerId())){
						User manager = userService.read(cartooletc.getManagerId());
						//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@:"+manager.getMail()+":"+manager.getUserName());
						reserveService.sendReservationMail("car",  "[자원 예약 승인요청]" , schedule, user,  manager);
					}
				}
			} else {
				
				result.put("success", FAIL);
			}

		} catch (Exception ex) {
			
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		
		return result;
	}
	
	/**
	 * 일정 수정
	 * @param param
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateReserve")
	public @ResponseBody
	Map<String, ? extends Object> updateReserve(@RequestBody UpdateScheduleVO param, HttpServletResponse response) {

		Map<String, String> result = new HashMap<String, String>();
		
		try {
				param.setUser(getUser());
				
				String check = calendarService.updateScheduleNew(param, param.isSendmail());
				
				if(check != null && check.equals(ReserveController.DUPLICATE)) {
					result.put("success", ReserveController.DUPLICATE);
				} else {
					result.put("meetingRoomId", param.getNewSchedule().getMeetingRoomId());
					result.put("success", SUCCESS);
				}
		} catch (Exception ex) {
			
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		
		return result;
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
			//param.put("scheduleId", params.getNewSchedule().getScheduleId());
			param.put("scheduleId", params.getEventInfo().getScheduleId());
			param.put("roomOrTool", "room");
			approveService.delete(param);
			
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/deleteReserveCar.do")
	public @ResponseBody
	String deleteReserveCar(HttpServletRequest request, SessionStatus status, String[] scheduleIds) {

		
		try {
			for(int i=0;i<scheduleIds.length;i++){
				
				Map<String, String> param = new HashMap<String, String>();
				param.put("scheduleId",scheduleIds[i]);
				param.put("roomOrTool", "tool");
				approveService.delete(param);
			}

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}
	/**
	 * 일정 조회
	 * @param scheduleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/reserveView.do")
	public ModelAndView reserveView(@RequestParam("scheduleId") String scheduleId,@RequestParam("roomortool") String roomortool) {
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/reserve/reserveView");
		
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
		param.put("roomOrTool", roomortool);
		param.put("scheduleId", scheduleId);
		Approve approve = approveService.getApproveMap(param);
		//Approve approve = approveService.getApprove(scheduleId);
		
		mav.addObject("mandators", mandators);
		mav.addObject("isUpdate", isUpdate);
		mav.addObject("registerId", schedule.getRegisterId());
		if(isUpdate) {
			mav.addObject("meetingRoom", meetingRoomService.read(schedule.getMeetingRoomId()));
		}

		try {
			mav.addObject("scheduleInfo", (new ObjectMapper()).writeValueAsString(schedule));
			mav.addObject("approve", approve);
		} catch(Exception e) {
			
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}
	
	/**
	 * 일정 조회
	 * @param scheduleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/myReserveView.do")
	public ModelAndView myReserveView(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition, @RequestParam("scheduleId") String scheduleId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/reserve/myReserveView");
		
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
		
		try {
			
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

	@RequestMapping(value = "/myReserveForm.do")
	public ModelAndView myReserveForm(String scheduleId, String meetingRoomId, String day, String from, String to, String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/reserve/myReserveForm");

		User user = getUser();

		try {
			Schedule schedule = null;
			
			if (scheduleId != null) {
				
				schedule = calendarService.getScheduleAllData(scheduleId, user.getLocaleCode());
				
				String startDate = DateUtil.getFmtDateString(schedule.getStartDate(), "yyyy.MM.dd HH:mm");
				schedule.setSdate(startDate.substring(0, 10));
				schedule.setStime(startDate.substring(11, 16));
				
				String endDate = DateUtil.getFmtDateString(schedule.getEndDate(), "yyyy.MM.dd HH:mm");
				schedule.setEdate(endDate.substring(0, 10));
				schedule.setEtime(endDate.substring(11, 16));
				
//				if(meeting.getAlarmList() != null) {
//					
//					for(Alarm alarm : meeting.getAlarmList()) {
//						
//						meeting.setAlarmTime(alarm.getAlarmTime());
//						meeting.setAlarmType(alarm.getAlarmType());
//					}
//				}
			} else {
				
				schedule = new Schedule();
				schedule.setSdate(day.replace("-", "."));
				schedule.setEdate(day.replace("-", "."));
				schedule.setMeetingRoomId(meetingRoomId);
				schedule.setStime(from);
				schedule.setEtime(to);
				schedule.setWholeday(0);
				schedule.setRepeat(0);
				schedule.setSchedulePublic(0);
				schedule.setAlarmTime("0");
				schedule.setAlarmType("2");
				schedule.setMeetingRoomId(meetingRoomId);

				MeetingRoom meetingRoom = meetingRoomService.read(meetingRoomId);
				schedule.setPlace(meetingRoom.getMeetingRoomName());
				
				String startCal = day.replace("-", "/");
				String endCal = day.replace("-", "/");
				
				mav.addObject("startDate", startCal + " " + from);
				mav.addObject("endDate", endCal + " " + to);
			}
			
			mav.addObject("schedule", schedule);
			//model.addAttribute("timeList", makeTimeList());
			mav.addObject("meetingRoomId", meetingRoomId);
			
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", startPeriod);
			mav.addObject("endPeriod", endPeriod);

			//List<PlannerCategory> categoryList = categoryService.getList(user.getLocaleCode());
			//model.addAttribute("categoryList", categoryList);

			//ObjectMapper mapper = new ObjectMapper();
			
			//try {
				
				//mav.addObject("fileDateList", mapper.writeValueAsString(schedule.getFileDataList()));
			//} catch (Exception e) {
				
				//throw new IKEP4ApplicationException("", e);
			//}

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}
	
	/**
	 * 일정 조회
	 * @param scheduleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/reservePopUpView.do")
	public ModelAndView reservePopUpView(@RequestParam("scheduleId") String scheduleId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/reserve/reservePopUpView");
		
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
		
		mav.addObject("mandators", mandators);
		mav.addObject("isUpdate", isUpdate);
		
		try {
			
			mav.addObject("scheduleInfo", (new ObjectMapper()).writeValueAsString(schedule));
		} catch(Exception e) {
			
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}
	
	@RequestMapping(value = "/myReserveList.do")
	public ModelAndView myReserveList(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/reserve/myReserveList");

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
				startDate = DateUtils.truncate(startDate, Calendar.DATE);
				
				endDate = DateUtil.toDate(endYear + endMonth + endDay);
				endDate = DateUtils.truncate(endDate, Calendar.DATE);
			}

			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());
			searchCondition.setUserId(user.getUserId());
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			SearchResult<Map<String, Object>> searchResult = reserveService.list(searchCondition);

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
	
	
	
	@RequestMapping(value = "/myCarReserveList.do")
	public ModelAndView myCarReserveList(String startPeriod, String endPeriod, MeetingRoomSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/reserve/myCarReserveList");

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
				startDate = DateUtils.truncate(startDate, Calendar.DATE);
				
				endDate = DateUtil.toDate(endYear + endMonth + endDay);
				endDate = DateUtils.truncate(endDate, Calendar.DATE);
			}

			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());
			searchCondition.setUserId(user.getUserId());
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			SearchResult<Map<String, Object>> searchResult = reserveService.listCar(searchCondition);

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
	
//	private List<Map<String, String>> makeTimeList() {
//		
//		List<Map<String, String>> timeList = new ArrayList<Map<String, String>>();
//		
//		String timeStr = "";
//		
//		for (int i = 7; i < 22; i++) {
//			
//			if (i < 10) {
//				
//				timeStr = "0" + i;
//				
//			} else {
//				
//				timeStr = String.valueOf(i);
//			}
//			
//			Map<String, String> map1 = new HashMap<String, String>();
//			Map<String, String> map2 = new HashMap<String, String>();
//			
//			map1.put("id", timeStr + ":00");
//			map2.put("id", timeStr + ":30");
//			
//			timeList.add(map1);
//			timeList.add(map2);
//		}
//
//		return timeList;
//	}
	
	public List<Alarm> setAlarmList(String alarmType, String alarmTime) {

		List<Alarm> alarmList = new ArrayList<Alarm>();

		if (!StringUtil.isEmpty(alarmType) && !alarmTime.equals("0")) {
			
			Alarm alarm = new Alarm();
			alarm.setAlarmType(alarmType);
			alarm.setAlarmTime(alarmTime);
			
			alarmList.add(alarm);
		}

		return alarmList;
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
