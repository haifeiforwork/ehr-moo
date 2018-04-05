/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomMainService;
import com.lgcns.ikep4.lightpack.meetingroom.web.ReserveController;
import com.lgcns.ikep4.lightpack.planner.model.CSExcelDownSearchCondition;
import com.lgcns.ikep4.lightpack.planner.model.FavoriteTarget;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.ScheduleExcel;
import com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.CompanyScheduleExcelService;
import com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.ical.ICalUtil;
import com.lgcns.ikep4.util.ical.model.ICalendar;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 일정관리 Controller
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: CalendarController.java 19655 2012-07-05 06:15:25Z yu_hs $
 */
@Controller
@RequestMapping(value = "lightpack/planner/calendar")
public class CalendarController extends BaseController {

	private static final String SUCCESS = "success";

	private static final String FAIL = "fail";

	public static final String VIEW_URL = "lightpack/planner/calendar/viewSchedule.do?docPopup=true&scheduleId=";
	
	@SuppressWarnings("unused")
	private static final Object[][] String = null;

	@Autowired
	private CalendarService service;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private PlannerCategoryService categoryService;
	
	@Autowired
	private PortalFavoriteService portalFavoriteService;
	
	@Autowired
	private MeetingRoomMainService meetingRoomService;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	
	@Autowired
	private CompanyScheduleExcelService companyScheduleExcelService;	
	
	@Autowired
	private WorkspaceService workspaceService;

	@RequestMapping("/init")
	public ModelAndView list(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Planner", operationName = { "MANAGE" }, resourceName = "Planner")) AccessingResult result) {
		if (result.isAccess()) {
			setRequestAttribute("isPlannerAdmin", true);
		} else {
			setRequestAttribute("isPlannerAdmin", false);
		}
		
		Boolean hasCompanyScheduleAdminPermission = this.aclService.hasSystemPermission("CompanySchedule", "MANAGE","CompanySchedule", getUser().getUserId());//전사일정 관리자 
		
		if (hasCompanyScheduleAdminPermission) {
			setRequestAttribute("isCompanyScheduleAdmin", true);
		} else {
			setRequestAttribute("isCompanyScheduleAdmin", false);
		}
		PortalFavoriteSearchCondition searchCondition =new PortalFavoriteSearchCondition();
		searchCondition.setCurrentCount(0);
		searchCondition.setPagePerRecord(100);
		searchCondition.setRegisterId(getUser().getUserId());
		SearchResult<PortalFavorite> favoriteSearchResult= this.portalFavoriteService.getAllForPeople(searchCondition);
		
		searchCondition.setCurrentCount(0);
		searchCondition.setPagePerRecord(100);
		searchCondition.setRegisterId(getUser().getUserId());
		SearchResult<PortalFavorite> favoriteTeamSearchResult= this.portalFavoriteService.getAllForTeam(searchCondition);
		
		ModelAndView modelAndView = new ModelAndView("lightpack/planner/calendar");
		modelAndView.addObject("favoriteSearchResult", favoriteSearchResult);
		modelAndView.addObject("favoriteTeamSearchResult", favoriteTeamSearchResult);
		modelAndView.addObject("favoriteSearchCondition", favoriteSearchResult.getSearchCondition());
		modelAndView.addObject("favoriteTeamSearchCondition", favoriteTeamSearchResult.getSearchCondition());
		return modelAndView;
	}
	
	/**
	 * 나의 일정 읽기
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/calFeedMySchedule")
	public @ResponseBody Map<String, Object> 
	calFeedMySchedule1(@RequestParam("startDate") long startDate, @RequestParam("endDate") long endDate,
			@RequestParam(value = "hbName", required = false) String hbName) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			User user = getUser();
			
			//list = scheduleService.selectByPeriodByMySchedule(user.getUserId(), startDate, endDate);
			
			list = scheduleService.selectByPeriodByMyScheduleHb(user.getUserId(),hbName, startDate, endDate);

			Map<String, String> userInfo = new HashMap<String, String>();
			userInfo.put("teamName", user.getTeamName());
			userInfo.put("userName", user.getUserName());
			String userTitleStr="";
			if(StringUtil.isEmpty(user.getJobDutyName())){
				userTitleStr = user.getJobTitleName();
			}else{
				userTitleStr = user.getJobDutyName();
			}
			userInfo.put("jobTitleName", userTitleStr);
			userInfo.put("teamEnglishName", user.getTeamEnglishName());
			userInfo.put("userEnglishName", user.getUserEnglishName());
			userInfo.put("jobTitleEnglishName", user.getJobTitleEnglishName());

			result.put("userInfo", userInfo);
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("events", list);
		return result;
	}
	
	/**
	 * 휴일 목록 읽기
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/calFeedHoliday")
	public @ResponseBody Map<String, Object>  calFeedHoliday(@RequestParam("startDate") long start, @RequestParam("endDate") long end) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map> list = new ArrayList<Map>();

		try {
			list = service.getHolidayList(start, end, getUser().getNationCode());
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("events", list);
		return result;
	}
	
	/**
	 * 전사일정 기간별 일정 읽기
	 * @param start
	 * @param end
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping("/calFeedCompanySchedule")
	public @ResponseBody
	Map<String, Object> calFeedCompanySchedule(@RequestParam("startDate") long start,
			@RequestParam("endDate") long end, @RequestParam("workAreaName") String workAreaName) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		

		
		try {

			//String workareaname = URLDecoder.decode(workAreaName,"UTF-8");
			
			list = scheduleService.selectByPeriodForCompany(getUser().getUserId(), workAreaName, start, end);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("events", list);
		return result;
	}
	
	@RequestMapping("/calFeedCompanySchedulePortlet")
	public @ResponseBody
	Map<String, Object> calFeedCompanySchedulePortlet(@RequestParam("startDate") long start,
			@RequestParam("endDate") long end, @RequestParam("workAreaName") String workAreaName) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		

		
		try {

			//String workareaname = URLDecoder.decode(workAreaName,"UTF-8");
			
			list = scheduleService.selectByPeriodForCompanyPortlet(getUser().getUserId(), workAreaName, start, end);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("events", list);
		return result;
	}
	
	/**
	 * 팀 기간별 일정 읽기
	 * @param start
	 * @param end
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping("/calFeedWorkspaceSchedule")
	public @ResponseBody
	Map<String, Object> calFeedWorkspaceSchedule(@RequestParam("startDate") long start,
			@RequestParam("endDate") long end, @RequestParam("workspaceId") String workspaceId,
			@RequestParam(value = "hbName", required = false) String hbName) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> userInfo = new HashMap<String, String>();
		Workspace workspace = new Workspace();
		if(StringUtil.isEmpty(workspaceId)){
			

			Map<String, String> wsMap = new HashMap<String, String>();
			wsMap.put("portalId", getPortal().getPortalId());
			wsMap.put("memberId", getUser().getUserId());
			wsMap.put("teamId", getUser().getGroupId());
			workspace = workspaceService.getDefaultWorkspace(wsMap);
			workspaceId = workspace.getWorkspaceId();

		}else{
			workspace = workspaceService.getWorkspace(workspaceId);
		}
		userInfo.put("workspaceName", workspace.getWorkspaceName());
		userInfo.put("workspaceId", workspace.getWorkspaceId());
		//System.out.println("#######################################:"+workspace.getWorkspaceName()+":"+workspace.getWorkspaceId());
		try {
			//list = scheduleService.selectByPeriodForWorkspace(getUser().getUserId(), workspaceId, start, end);
			list = scheduleService.selectByPeriodForWorkspaceHb(getUser().getUserId(),hbName, workspaceId, start, end);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("userInfo", userInfo);
		result.put("events", list);
		return result;
	}
	/**
	 * 팀 기간별 일정 읽기 (Workspace 모듈에서 사용)
	 * @param start
	 * @param end
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping("/getWorkspaceSchedule")
	public @ResponseBody
	Map<String, Object> getWorkspaceSchedule(@RequestParam("start") long start,
			@RequestParam("end") long end, @RequestParam("workspaceId") String workspaceId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			list = scheduleService.selectByPeriodForWorkspace(getUser().getUserId(), workspaceId, start, end);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("events", list);
		return result;
	}
	
	/**
	 * 공유 workspace 오늘의 일정 읽기 (Workspace에서 사용)
	 * 
	 * @param workspaceId, startDate, endDate
	 * @return
	 */
	@RequestMapping("/readWorkspaceSchedule")
	public @ResponseBody
	List<Map<String, Object>> readWorkspaceSchedule(@RequestParam("workspaceId") String workspaceId,
			@RequestParam("startDate") String startDate) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			list = scheduleService.readWorkspaceSchedule(workspaceId, startDate, VIEW_URL);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return list;
	}

	/**
	 * 타 사용자 일정 읽기
	 * @param targetUserId
	 * @param start
	 * @param end 
	 * @return
	 */
	@RequestMapping("/calFeedTargetUserSchedule")
	public @ResponseBody
	Map<String, Object> calFeedTargetUserSchedule(
			@RequestParam(value = "targetUserId") String targetUserId,
			@RequestParam(value = "startDate") long startDate,
			@RequestParam(value = "endDate") long endDate,
			@RequestParam(value = "hbName", required = false) String hbName) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> userInfo = new HashMap<String, String>();
		Map<String, Object> result = new HashMap<String, Object>();

		try {			
			userInfo = service.getUserInfo(targetUserId);
			//list = scheduleService.selectByPeriodByTargetUser(getUser().getUserId(), targetUserId, startDate, endDate);
			list = scheduleService.selectByPeriodByTargetUserHb(getUser().getUserId(),hbName, targetUserId, startDate, endDate);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("events", list);
		result.put("userInfo", userInfo);
		return result;
	}
	
	/**
	 * 위임자 일정 정보 읽기
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/calFeedByTrustee")
	public @ResponseBody
	Map<String, Object> calFeedByTrustee(@RequestParam("mandatorId") String mandatorId,
			@RequestParam(value = "startDate", required = false) long startDate,
			@RequestParam(value = "endDate", required = false) long endDate) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> mandatorInfo = new HashMap<String, String>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {			
			list = scheduleService.selectByPeriodByTrustee(getUser().getUserId(), mandatorId, startDate, endDate);
			mandatorInfo = service.getUserInfo(mandatorId);
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("events", list);
		result.put("mandatorInfo", mandatorInfo);
		return result;
	}
	
	/**
	 * profile 오늘의 일정 에서 사용
	 * 
	 * @param userId
	 * @param startDate
	 * @return
	 */
	@RequestMapping("/readMySchedule")
	public @ResponseBody
	List<Map<String, Object>> readMySchedule(@RequestParam("userId") String userId) {

		//Map<String, String> params = new HashMap<String, String>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			list = scheduleService.readMyTodaySchedule(userId);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return list;
	}
	
	/**
	 * 참여자 일정 확인용 일정 읽기 (참여자 확인 스크립트에서 사용)
	 * 
	 * @param users, startDate, endDate
	 * @return
	 */
	@RequestMapping("/readUsersSchedule")
	public @ResponseBody
	List<Map<String, Object>> readUsersSchedule(@RequestBody Map<String, Object> params) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			list = scheduleService.readUsersSchedule(params);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return list;
	}
	
	// 워크스페이스메뉴에서 호출
	@RequestMapping("/workspaceInit")
	public ModelAndView workspaceInit() {
		return new ModelAndView("lightpack/planner/calendar/workspaceInit");
	}
	
	/**
	 * left menu에서 필요한 기본 데이터 읽기
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getInitData")
	public @ResponseBody Map<String, Object> getInitData() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			User user = getUser();
			
			List<Map> mandatorList = service.getMyMandators(user.getUserId());
			List<PlannerCategory> categoryList = categoryService.getList(getUser().getLocaleCode());
			
			result.put("mandatorList", mandatorList);
			result.put("categoryList", categoryList);
			result.put("userInfo", user);
			result.put("portalInfo", getPortal());
			
			Boolean hasCompanyScheduleAdminPermission = this.aclService.hasSystemPermission("CompanySchedule", "MANAGE","CompanySchedule", getUser().getUserId());//전사일정 관리자 
			
			if (hasCompanyScheduleAdminPermission) {
				result.put("companyScheduleAdminFlag", true);
			} else {
				result.put("companyScheduleAdminFlag", false);
			}
			
			result.put("success", SUCCESS);
		} catch(Exception ex) {
			result.put("success", FAIL);
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 나의 일정 갯수
	 * @return
	 */
	@RequestMapping("/getMyScheduleCount")
	public @ResponseBody String getMyScheduleCount() {
		String result = "";
		try {
			int count = scheduleService.getUserScheduleCount(getUser().getUserId(), new Date());
			result = "{\"count\":" + count + "}"; 
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 일정 조회
	 * @param scheduleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/viewSchedule")
	public ModelAndView viewSchedule(@RequestParam("scheduleId") String scheduleId) {
		ModelAndView mav = new ModelAndView("lightpack/planner/viewSchedule");
		
		boolean isUpdate = false;
		Schedule schedule = this.getScheduleAllData(scheduleId);
		
		if (schedule == null) {
			throw new IKEP4AuthorizedException(messageSource, "ui.lightpack.planner.common.message.deletedItem");
		}
		
		String userId = getUser().getUserId();
		String regUserId = schedule.getRegisterId();
		
		List<Map> mandators = service.getMyMandators(userId);
		
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
		if(isUpdate == false) {
			
			isUpdate  = this.aclService.hasSystemPermission("Planner", "MANAGE","Planner", getUser().getUserId());//일정 전체 관리자는 수정 권한 준다. 
		
		}
		
		if(!StringUtil.isEmpty(schedule.getMeetingRoomId())) {
			
			mav.addObject("meetingRoom", true);
		} else {
			
			mav.addObject("meetingRoom", false);
		}
		
		mav.addObject("mandators", mandators);
		mav.addObject("isUpdate", isUpdate);
		mav.addObject("everySchedule", schedule.getEveryoneSchedule());
		try {
			mav.addObject("scheduleInfo", (new ObjectMapper()).writeValueAsString(schedule));
		} catch(Exception e) {
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}
	
	/**
	 * 수임자가 위임자 일정 생성
	 * 
	 * @return
	 */
	@RequestMapping("/createByTrustee")
	public @ResponseBody
	Map<String, ? extends Object> createByTrustee(@RequestBody Schedule schedule, HttpServletResponse response) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			User user = getUser();
			schedule.setPortalId(user.getPortalId());
			String id = service.createByTrustee(schedule, user);
			if (id != null) {
				if(id.equals(ReserveController.DUPLICATE)) {
					result.put("success", ReserveController.DUPLICATE);
				} else {
					result.put("scheduleId", id);
					result.put("success", SUCCESS);
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
	 * 일정 생성
	 * @param schedule
	 * @param response
	 * @return
	 */
	@RequestMapping("/create")
	public @ResponseBody
	Map<String, ? extends Object> create(@RequestBody Schedule schedule, HttpServletResponse response) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			User user = getUser();
			schedule.setPortalId(user.getPortalId());
			schedule.setRegisterId(user.getUserId());
			schedule.setRegisterName(user.getUserName());
			schedule.setUpdaterId(user.getUserId());
			schedule.setUpdaterName(user.getUserName());

			String id = service.create(schedule, user, schedule.isSendmail());
			if (id != null) {
				if(id.equals(ReserveController.DUPLICATE)) {
					result.put("success", ReserveController.DUPLICATE);
				} else {
					result.put("scheduleId", id);
					result.put("success", SUCCESS);
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
	@RequestMapping("/updateSchedule")
	public @ResponseBody
	Map<String, ? extends Object> updateNew(@RequestBody UpdateScheduleVO param, HttpServletResponse response) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			param.setUser(getUser());
			String check = service.updateScheduleNew(param, param.isSendmail());
			if(check != null && check.equals(ReserveController.DUPLICATE)) {
				result.put("success", ReserveController.DUPLICATE);
			} else {
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
	@RequestMapping("/deleteSchedule")
	public @ResponseBody
	Map<String, ? extends Object> deleteSchedule(@RequestBody UpdateScheduleVO params) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			service.deleteSchedule(params);
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	@RequestMapping("/copyMySchedule")
	public @ResponseBody
	Map<String, ? extends Object> copyMySchedule(@RequestBody UpdateScheduleVO params) {
		User user = getUser();
		Map<String, String> result = new HashMap<String, String>();
		try {
			service.copyMySchedule(params, user);
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	@RequestMapping("/copyTeamSchedule")
	public @ResponseBody
	Map<String, ? extends Object> copyTeamSchedule(@RequestBody UpdateScheduleVO params) {
		User user = getUser();
		Map<String, String> result = new HashMap<String, String>();
		try {
			service.copyTeamSchedule(params, user);
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	
	
	/**
	 * 삭제표시
	 * */
	@RequestMapping("/strikeSchedule")
	public @ResponseBody
	Map<String, ? extends Object> strikeSchedule(@RequestBody UpdateScheduleVO params) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			params.setUser(getUser());
			service.removeScheduleUpdateDisplay(params);
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	/**
	 * 일정의 모든 정보 제공 - 일정 상세조회에서 사용
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping("/getScheduleAllData")
	public @ResponseBody
	Schedule getScheduleAllData(@RequestParam("scheduleId") String scheduleId) {

		Schedule schedule = null;
		try {
			schedule = service.getScheduleAllData(scheduleId, getUser().getLocaleCode());
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return schedule;
	}
	
	/**
	 * 일정의 서버 정보 제공(알람,첨여/참조,파일정보)
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping("/scheduleSubInfo")
	public @ResponseBody
	Map<String, Object> scheduleSubInfo(@RequestParam("scheduleId") String scheduleId) {

		Map<String, Object> result = null;
		try {
			result = service.getScheduleSubInfo(scheduleId);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 일정상세보기화면에서 사용하는 참여/불참 정보 갱신하는 API
	 * @param params
	 * @return
	 */
	@RequestMapping("/updateAcceptInfo")
	public @ResponseBody
	Map<String, Object> updateAcceptInfo(@RequestBody Map<String, String> params) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			service.updateAcceptInfo(params);
			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 일반 일정 일자 변경
	 * @param params
	 * @return
	 */
	@RequestMapping("/move")
	public @ResponseBody
	Map<String, ? extends Object> move(@RequestBody Map<String, Object> params) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			User user = getUser();
			params.put("user", user);
			service.scheduleMove(params);

			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	@RequestMapping("/moveAllday")
	public @ResponseBody
	Map<String, ? extends Object> moveAllday(@RequestBody Map<String, Object> params) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			User user = getUser();
			params.put("user", user);
			service.scheduleMoveAllday(params);

			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	@RequestMapping("/moveTime")
	public @ResponseBody
	Map<String, ? extends Object> moveTime(@RequestBody Map<String, Object> params) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			User user = getUser();
			params.put("user", user);
			service.scheduleMoveTime(params);

			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}

	/**
	 * 일반 일정 시간 범위 변동
	 * @param params
	 * @return
	 */
	@RequestMapping("/resize")
	public @ResponseBody
	Map<String, ? extends Object> resize(@RequestBody Map<String, Object> params) {

		Map<String, String> result = new HashMap<String, String>();
		try {
			User user = getUser();
			params.put("user", user);
			service.scheduleResize(params);

			result.put("success", SUCCESS);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * 타사용자 정보 읽기 (사용자 검색 및 일정 보기에서 사용)
	 * @param userName
	 * @return
	 */
	@RequestMapping("/findUserByName")
	public @ResponseBody
	Map<String, Object> findUser(@RequestParam("userName") String userName) {
		Map<String, Object> result = null;
		try {
			result = service.findUserByName(userName);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	/**
	 * Workalignment메뉴에서 호출
	 * @param result
	 * @return
	 */
	@RequestMapping("/workalignmentInit")
	public ModelAndView workalimentInit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Planner", operationName = { "MANAGE" }, resourceName = "Planner")) AccessingResult result) {
		if (result.isAccess()) {
			setRequestAttribute("isPlannerAdmin", true);
		} else {
			setRequestAttribute("isPlannerAdmin", false);
		}
		return new ModelAndView("lightpack/planner/workalignmentInit");
	}
	
	
	private User getUser() {
		return (User) getRequestAttribute("ikep.user");
	}
	
	private Portal getPortal() {
		return (Portal) getRequestAttribute("ikep.portal");
	}
	
	/**
	 * 위임자 일정 관리
	 * 
	 * @return
	 */
	@RequestMapping("/initByTrustee")
	public ModelAndView initTrustee() {
		return new ModelAndView("lightpack/planner/calendar/");
	}
	
	/**
	 * 스케줄 파일 import : upload
	 */
	@RequestMapping("/importSchedule")
	public void importSchedule(@RequestParam(value = "scheduleFile") CommonsMultipartFile scheduleFile, HttpServletResponse res) {
		boolean result = false;
		try {
			InputStream uploadFile = scheduleFile.getInputStream();
			
			result = service.importSchedule(uploadFile, getUser());
			if(result) {
				res.getWriter().println("<script type=\"text/javascript\">parent.completeImport();</script>");
			}
		} catch(IOException e) {
			
		}
	}
	
	/**
	 * 스케줄 파일 export : download
	 *
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/exportSchedule", method=RequestMethod.POST)
	public void exportSchedule(@RequestParam(value="exportType") String exportType,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,
			HttpServletResponse res, HttpServletRequest req) {
		
		try {
			String[] arrStart = startDate.split("\\.");
			String[] arrEnd = endDate.split("\\.");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("exportType", exportType);
			params.put("startDate", new Date(new Integer(arrStart[0])-1900, new Integer(arrStart[1])-1, new Integer(arrStart[2])));
			params.put("endDate", new Date(new Integer(arrEnd[0])-1900, new Integer(arrEnd[1])-1, new Integer(arrEnd[2])));
			
			ICalendar calendar = service.exportSchedule(params, getUser());
			
			if(calendar.getIeventList() != null) {
				ICalUtil.saveICalendar(calendar, "iKEP4_Schedule.ics", res);
			} else {
				res.getWriter().println("<script type=\"text/javascript\">parent.noExportEvent();</script>");
			}
		} catch (IOException e) {
			
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/companyScheduleList")
	public ModelAndView companyScheduleList() {
		ModelAndView mav = new ModelAndView("lightpack/planner/companyScheduleList");
		try {
			List<Schedule> list = scheduleService.getCompanyScheduleWaitList();
			mav.addObject("companyScheduleList", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	@RequestMapping("/companyScheduleDelete")
	public ModelAndView companyScheduleDelete(@RequestParam("cid") String[] cid,
			@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Planner", operationName={"MANAGE"}, resourceName="Planner")) AccessingResult result) {
		if(!result.isAccess()) {
			throw new IKEP4AuthorizedException(); 
		}
		try {
			for(int i=0;i<cid.length;i++){
				service.deleteSchedule(cid[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("redirect:/lightpack/planner/calendar/companyScheduleList.do");
	}
	
	@RequestMapping("/companyScheduleApprove")
	public ModelAndView companyScheduleApprove(@RequestParam("cid") String[] cid,
			@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Planner", operationName={"MANAGE"}, resourceName="Planner")) AccessingResult result) {
		if(!result.isAccess()) {
			throw new IKEP4AuthorizedException(); 
		}
		try {
			service.updateCompanyScheduleApprove(cid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("redirect:/lightpack/planner/calendar/init.do");
	}
	
	@RequestMapping("/companyScheduleExcelForm")
	public ModelAndView excelForm(HttpServletRequest req) {


		ModelAndView mav = new ModelAndView("lightpack/planner/companyScheduleExcelForm");

		// 더블 서밋방지 Token 셋팅
		String token = HttpUtil.setToken(req);
		mav.addObject("token", token);

		return mav;
	}
	
	@RequestMapping("/favoriteSettingForm")
	public ModelAndView favoriteSettingForm(HttpServletRequest req,
			@RequestParam(value="flg", required=false) String flg,
			@RequestParam(value="sflg", required=false, defaultValue = "0") String sflg) {

		ModelAndView mav = new ModelAndView("lightpack/planner/favoriteSettingForm");
		User user = getUser();
		Schedule schedule = new Schedule();
		List<FavoriteTarget> targetList1 = scheduleService.selectFavoriteTeam(user.getUserId());
		
		schedule.setTargetGroup(targetList1);
		
		List<FavoriteTarget> targetList2 = scheduleService.selectFavoriteUser(user.getUserId());
		schedule.setTargetUser(targetList2);
		
		mav.addObject("flg", flg);
		mav.addObject("sflg", sflg);
		mav.addObject("schedule", schedule);

		return mav;
	}
	
	@RequestMapping("/favoriteSetting")
	public ModelAndView favoriteSetting(HttpServletRequest req,
			Schedule schedule,
			@RequestParam(value="flg", required=false) String flg) {
		User user = getUser();
		schedule.setRegisterId(user.getUserId());
		schedule.setRegisterName(user.getUserName());
		schedule.setUpdaterId(user.getUserId());
		schedule.setUpdaterName(user.getUserName());
		scheduleService.favoriteSetting(schedule,flg);
		
		

		return new ModelAndView("redirect:/lightpack/planner/calendar/favoriteSettingForm.do?flg="+flg+"&sflg=1");
	}
	
	
	@RequestMapping("/companyScheduleExcelUpload")
	public ModelAndView excelUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest req) {



		ModelAndView mav = new ModelAndView("lightpack/planner/companyScheduleExcelResult");

		try {

			// 더블 서밋방지 Token 체크
			if (HttpUtil.isValidToken(req)) {

				InputStream inp = file.getInputStream();

				User userSession = getUser();
				Portal portal = getPortal();
				
				String className = "com.lgcns.ikep4.lightpack.planner.model.ScheduleExcel";

				List<Object> list = ExcelUtil.readExcel(className, inp, 1);

				int successCount = 0;
				int failCount = 0;
				List<ScheduleExcel> scheduleExcelList = new ArrayList<ScheduleExcel>();

				for (Object obj : list) {

					try {

						ScheduleExcel scheduleExcel = (ScheduleExcel) obj;
						//System.out.println("#################"+scheduleExcel.getStartDate());
						if(StringUtil.isEmpty(""+scheduleExcel.getStartDate())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("start_date field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("시작일이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(StringUtil.isEmpty(""+scheduleExcel.getEndDate())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("end_date field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("종료일이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(StringUtil.isEmpty(scheduleExcel.getStartTime())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("start_time field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("시작시간이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(StringUtil.isEmpty(scheduleExcel.getEndTime())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("start_time field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("종료시간이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(StringUtil.isEmpty(scheduleExcel.getTitle())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("title field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("일정제목이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						 if(
								 (!StringUtil.isEmpty(scheduleExcel.getWorkAreaName()))
								 &&
						         (!("본사".equals(scheduleExcel.getWorkAreaName())||"울산".equals(scheduleExcel.getWorkAreaName())||"대구".equals(scheduleExcel.getWorkAreaName())||"진주".equals(scheduleExcel.getWorkAreaName())))
						    ){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("work_area_name field is 본사 or 울산 or 대구 or 진주 : Check file template or row data");
							scheduleExcel.setErrMsg("사업장은 [본사,울산,대구,진주] 중 하나이거나 빈값으로 입력해주십시요.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(!("0".equals(scheduleExcel.getWholeday())||"1".equals(scheduleExcel.getWholeday()))){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("wholeday field is 0 or 1 : Check file template or row data");
							scheduleExcel.setErrMsg("종일일정 구분이 없습니다. 종일일정1, 일반일정0으로 입력하십시요.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						
					
						scheduleExcel.setRegisterId(userSession.getUserId());
						scheduleExcel.setRegisterName(userSession.getUserName());
						scheduleExcel.setUpdaterId(userSession.getUserId());
						scheduleExcel.setUpdaterName(userSession.getUserName());
						scheduleExcel.setPortalId(portal.getPortalId());

						String rslt = service.create(ExcelToSchedule(scheduleExcel), userSession);
						if("duplicate".equals(rslt)){

							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("meeting room is duplicated");
							scheduleExcel.setErrMsg("이미 예약된 회의실입니다. 시간 또는 회의실을 변경하십시요.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
						}else{

							scheduleExcel.setSuccessYn("Y");
							successCount++;
						}
			


					} catch (Exception e) {

						ScheduleExcel scheduleExcel = (ScheduleExcel) obj;
						scheduleExcel.setSuccessYn("N");
						scheduleExcel.setErrMsg(e.getMessage());
						scheduleExcelList.add(scheduleExcel);
						failCount++;
					}
				}

				mav.addObject("scheduleExcelList", scheduleExcelList);
				mav.addObject("totalCount", list.size());
				mav.addObject("successCount", successCount);
				mav.addObject("failCount", failCount);

				// Token 초기화
				String token = HttpUtil.setToken(req);
				mav.addObject("token", token);
			}

		} catch (Exception e) {
			mav.addObject("totalCount", 0);
			return mav;
		}

		return mav;
	}
	
	@RequestMapping("/companyScheduleEcmExcelUpload")
	public ModelAndView ecmExcelUpload(String ecmFileUrl2, HttpServletRequest req) {



		ModelAndView mav = new ModelAndView("lightpack/planner/companyScheduleExcelResult");

		try {

			// 더블 서밋방지 Token 체크
			if (HttpUtil.isValidToken(req)) {

				
				Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

				String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
				String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
				String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
				
				URL url = new URL(ecmFileUrl2);
				File srcFile2 = new File(tempUploadRoot+"/planerExcel.xls");
				FileUtils.copyURLToFile(url, srcFile2);
				
				FileInputStream uploadFile = new FileInputStream(srcFile2);
				
				
				User userSession = getUser();
				Portal portal = getPortal();
				
				String className = "com.lgcns.ikep4.lightpack.planner.model.ScheduleExcel";

				List<Object> list = ExcelUtil.readEcmExcel(className, uploadFile, 1);

				int successCount = 0;
				int failCount = 0;
				List<ScheduleExcel> scheduleExcelList = new ArrayList<ScheduleExcel>();

				for (Object obj : list) {

					try {

						ScheduleExcel scheduleExcel = (ScheduleExcel) obj;
						//System.out.println("#################"+scheduleExcel.getStartDate());
						if(StringUtil.isEmpty(""+scheduleExcel.getStartDate())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("start_date field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("시작일이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(StringUtil.isEmpty(""+scheduleExcel.getEndDate())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("end_date field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("종료일이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(StringUtil.isEmpty(scheduleExcel.getStartTime())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("start_time field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("시작시간이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(StringUtil.isEmpty(scheduleExcel.getEndTime())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("start_time field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("종료시간이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(StringUtil.isEmpty(scheduleExcel.getTitle())){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("title field is empty : Check file template or row data");
							scheduleExcel.setErrMsg("일정제목이 없습니다.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						 if(
								 (!StringUtil.isEmpty(scheduleExcel.getWorkAreaName()))
								 &&
						         (!("본사".equals(scheduleExcel.getWorkAreaName())||"울산".equals(scheduleExcel.getWorkAreaName())||"대구".equals(scheduleExcel.getWorkAreaName())||"진주".equals(scheduleExcel.getWorkAreaName())))
						    ){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("work_area_name field is 본사 or 울산 or 대구 or 진주 : Check file template or row data");
							scheduleExcel.setErrMsg("사업장은 [본사,울산,대구,진주] 중 하나이거나 빈값으로 입력해주십시요.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						if(!("0".equals(scheduleExcel.getWholeday())||"1".equals(scheduleExcel.getWholeday()))){
							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("wholeday field is 0 or 1 : Check file template or row data");
							scheduleExcel.setErrMsg("종일일정 구분이 없습니다. 종일일정1, 일반일정0으로 입력하십시요.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
							continue;
						}
						
					
						scheduleExcel.setRegisterId(userSession.getUserId());
						scheduleExcel.setRegisterName(userSession.getUserName());
						scheduleExcel.setUpdaterId(userSession.getUserId());
						scheduleExcel.setUpdaterName(userSession.getUserName());
						scheduleExcel.setPortalId(portal.getPortalId());

						String rslt = service.create(ExcelToSchedule(scheduleExcel), userSession);
						if("duplicate".equals(rslt)){

							scheduleExcel.setSuccessYn("N");
							//scheduleExcel.setErrMsg("meeting room is duplicated");
							scheduleExcel.setErrMsg("이미 예약된 회의실입니다. 시간 또는 회의실을 변경하십시요.");
							scheduleExcelList.add(scheduleExcel);
							failCount++;
						}else{

							scheduleExcel.setSuccessYn("Y");
							successCount++;
						}
			


					} catch (Exception e) {

						ScheduleExcel scheduleExcel = (ScheduleExcel) obj;
						scheduleExcel.setSuccessYn("N");
						scheduleExcel.setErrMsg(e.getMessage());
						scheduleExcelList.add(scheduleExcel);
						failCount++;
					}
				}

				mav.addObject("scheduleExcelList", scheduleExcelList);
				mav.addObject("totalCount", list.size());
				mav.addObject("successCount", successCount);
				mav.addObject("failCount", failCount);

				// Token 초기화
				String token = HttpUtil.setToken(req);
				mav.addObject("token", token);
			}

		} catch (Exception e) {
			mav.addObject("totalCount", 0);
			return mav;
		}

		return mav;
	}
	
	public String getFilePath(String path) {

		String date = getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
	
	public String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}
	
	public Schedule ExcelToSchedule(ScheduleExcel scheduleExcel ){
		Schedule schedule =new Schedule();
		schedule.setCategoryId("1");
		schedule.setPortalId(scheduleExcel.getPortalId());
		schedule.setRegisterId(scheduleExcel.getRegisterId());
		schedule.setRegisterName(scheduleExcel.getRegisterName());
		schedule.setUpdaterId(scheduleExcel.getUpdaterId());
		schedule.setUpdaterName(scheduleExcel.getUpdaterName());
		schedule.setStartDate(ScheduleExcel.toDate(scheduleExcel.getStartDate()+StringUtil.replace( scheduleExcel.getStartTime(), ":", "")));
		//System.out.println("#####################schedule.getStartDate:"+schedule.getStartDate());
		schedule.setEndDate(ScheduleExcel.toDate(scheduleExcel.getEndDate()+StringUtil.replace(scheduleExcel.getEndTime(), ":", "")));
		schedule.setTitle(scheduleExcel.getTitle());
		if(StringUtil.isEmpty(scheduleExcel.getContents())){
			scheduleExcel.setContents(scheduleExcel.getTitle());//일정내용이 없으면 제목을 대신넣는다.
		}
		schedule.setContents(scheduleExcel.getContents());
		schedule.setWorkAreaName(StringUtil.nvl(scheduleExcel.getWorkAreaName(), ""));
		schedule.setEveryoneSchedule(1);
		schedule.setApproveResult(0);
		schedule.setWholeday(Integer.parseInt(scheduleExcel.getWholeday()));
		schedule.setMeetingRoomId(StringUtil.nvl(scheduleExcel.getMeetingRoomId(),""));
		if(!StringUtil.isEmpty(schedule.getMeetingRoomId())){
			MeetingRoom meetingRoom = meetingRoomService.read(scheduleExcel.getMeetingRoomId());
			schedule.setPlace(meetingRoom.getBuildingName()+" "+meetingRoom.getFloorName()+" "+meetingRoom.getMeetingRoomName());
		}else{
			schedule.setPlace(scheduleExcel.getPlace());
		}

		
		return schedule;
	}
	
	
	@RequestMapping(value = "/chkDownloadExcelMeetingRoom")
	public @ResponseBody String downloadExcelCareer() {
		
		Integer downloadRowCnt = 0;

		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setPortalId(getPortal().getPortalId());

		List<MeetingRoom> meetingRoomList = meetingRoomService.select(meetingRoom);
		
		if( meetingRoomList != null && meetingRoomList.size() > 0 ){
			downloadRowCnt = meetingRoomList.size();
		}

		return ""+downloadRowCnt;
		
	}
	
	@RequestMapping(value = "/downloadExcelMeetingRoom.do")
	public void downloadExcelCareer( HttpServletResponse response) {
		
		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setPortalId(getPortal().getPortalId());
		meetingRoom.setUseFlag("1");
		
		List<MeetingRoom> meetingRoomList = meetingRoomService.select(meetingRoom);
		
		String fileName = "시스템_예약가능_회의실_목록_" + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xlsx";

		if( meetingRoomList.size() > 0 ) {

			List<Object> excelDownloadList = new ArrayList<Object>();
			for (MeetingRoom excelMeetingRoom : meetingRoomList) {

				excelDownloadList.add(excelMeetingRoom);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
			//titleMap.put("registerName", "registerName");

			titleMap.put("buildingName", "buildingName");
			titleMap.put("floorName", "floorName");
			titleMap.put("meetingRoomName", "meetingRoomName");
			titleMap.put("meetingRoomId", "meetingRoomId");

			// 파일 저장
			ExcelUtil.saveExcel(titleMap, excelDownloadList, fileName, response, "시스템_예약가능_회의실_목록_" + DateUtil.getTodayDateTime("yyyyMMdd"));
			
		}

	}	
	
	

	@RequestMapping(value = "/companyScheduleExcelList")
	public ModelAndView companyScheduleExcelList(String startPeriod, String endPeriod, CSExcelDownSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("lightpack/planner/companyScheduleExcelList");

		User user = getUser();
		Portal portal = getPortal();

		try {

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("FILE_REAL_NAME");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("DESC");
			}
			
			Date today = null;
			Date startDate = null;
			Date endDate = null;
			
			if(StringUtil.isEmpty(startPeriod) || StringUtil.isEmpty(endPeriod)) {
				
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(today);
				cal.add(Calendar.YEAR, -1); 
				
				startDate = cal.getTime();
				
				cal.add(Calendar.YEAR, +2); 
				endDate = cal.getTime();
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

			SearchResult<Map<String, Object>> searchResult = companyScheduleExcelService.getCompanyScheduleExcelFileList(searchCondition);

			BoardCode boardCode = new BoardCode();

			//Boolean isSystemAdmin = this.isSystemAdmin(user);
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("portalId", portal.getPortalId());
			param.put("userId", user.getUserId());
			
			//Boolean isMeetingRoomManager = this.isMeetingRoomManager(param);
			
			//mav.addObject("isSystemAdmin", isSystemAdmin);
			//mav.addObject("isMeetingRoomManager", isMeetingRoomManager);
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
	@RequestMapping(value = "/companyScheduleExcelListUpload.do")
	public @ResponseBody Map<String, Object> companyScheduleExcelListUpload(String fileId) {
		

		
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		try {
			companyScheduleExcelService.upLoadCompanyScheduleExcel(fileId, user, portalId);
			result.put("status", "success");
		} catch (Exception e) {  
			result.put("status", "fail"); 
		}
		
		return result;
	}
	
	@RequestMapping(value = "/companyScheduleEcmExcelListUpload.do")
	public ModelAndView companyScheduleEcmExcelListUpload(String ecmFileName,String ecmFileUrl1,String ecmFileUrl2,String ecmFilePath,String ecmFileId) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		companyScheduleExcelService.upLoadCompanyScheduleExcelEcm(ecmFileName,ecmFileUrl1,ecmFileUrl2,ecmFilePath,ecmFileId, user);
		
		return new ModelAndView("redirect:/lightpack/planner/calendar/companyScheduleExcelList.do");
	}
	
	
	@RequestMapping(value = "/companyScheduleExcelDelete.do")
	public @ResponseBody
	String companyScheduleExcelDelete(HttpServletRequest request, SessionStatus status, String[] chkFileIds) {

		
		try {

			companyScheduleExcelService.delete(chkFileIds);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "ok";
	}
	
	@RequestMapping(value = "/companyScheduleExcelDownload.do")
	public @ResponseBody Map<String, Object> companyScheduleExcelDownload(String excelYearMonth) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		CSExcelDownSearchCondition searchCondition= new CSExcelDownSearchCondition();
		
		Date startDate = DateUtil.toDate(excelYearMonth + "01");
		Date endDate = DateUtil.toDate(excelYearMonth + "01");
		
		try {
			searchCondition.setPortalId(portal.getPortalId());
			searchCondition.setUserId(user.getUserId());
			searchCondition.setStartDate(DateUtil.getRelativeDate(startDate,-1));
			searchCondition.setEndDate(endDate);
			
			SearchResult<Map<String, Object>> searchResult = companyScheduleExcelService.getCompanyScheduleExcelFileList(searchCondition);
			List<Map<String, Object>>  fileDataList = searchResult.getEntity();
			for (Map<String, Object> fileData : fileDataList) {

				result.put("fileId", fileData.get("fileId"));
				break;
			}
			
			result.put("status", "success");
		} catch (Exception e) {  
			result.put("status", "fail"); 
		}
		return result;
	}
	
	/**
	 * 개인 일정 에서 사용
	 * 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/readUserDateSchedule.do")
	public @ResponseBody
	List<Map<String, Object>> readUserDateSchedule(@RequestParam("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			list = scheduleService.readUserDateSchedule(userId, startDate, endDate);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return list;
	}	

	/**
	 * 나의 일정 읽기
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/webDiaryMySchedule")
	public @ResponseBody Map<String, Object> 
	webDiaryMySchedule(@RequestParam("startDate") long startDate, @RequestParam("endDate") long endDate) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			User user = getUser();
			
			list = scheduleService.selectByPeriodByMySchedule(user.getUserId(), startDate, endDate);

			Map<String, String> userInfo = new HashMap<String, String>();
			userInfo.put("teamName", user.getTeamName());
			userInfo.put("userName", user.getUserName());
			String userTitleStr="";
			if(StringUtil.isEmpty(user.getJobDutyName())){
				userTitleStr = user.getJobTitleName();
			}else{
				userTitleStr = user.getJobDutyName();
			}
			userInfo.put("jobTitleName", userTitleStr);
			userInfo.put("teamEnglishName", user.getTeamEnglishName());
			userInfo.put("userEnglishName", user.getUserEnglishName());
			userInfo.put("jobTitleEnglishName", user.getJobTitleEnglishName());

			result.put("userInfo", userInfo);
			result.put("success", SUCCESS);
			result.put("events", list);
		} catch (Exception ex) {
			result.put("success", FAIL);
			// throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}

	/**
	 * 팀 기간별 일정 읽기
	 * @param start
	 * @param end
	 * @param workspaceId
	 * @return
	 */
	@RequestMapping("/webDiaryTeamSchedule")
	public @ResponseBody Map<String, Object> webDiaryTeamSchedule(@RequestParam("startDate") long start,
			@RequestParam("endDate") long end, @RequestParam("workspaceId") String workspaceId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> userInfo = new HashMap<String, String>();
		Workspace workspace = new Workspace();
		if(StringUtil.isEmpty(workspaceId)){

			Map<String, String> wsMap = new HashMap<String, String>();
			wsMap.put("portalId", getPortal().getPortalId());
			wsMap.put("memberId", getUser().getUserId());
			wsMap.put("teamId", getUser().getGroupId());
			workspace = workspaceService.getDefaultWorkspace(wsMap);
			workspaceId = workspace.getWorkspaceId();

		}else{
			workspace = workspaceService.getWorkspace(workspaceId);
		}
		userInfo.put("workspaceName", workspace.getWorkspaceName());
		userInfo.put("workspaceId", workspace.getWorkspaceId());
		//System.out.println("#######################################:"+workspace.getWorkspaceName()+":"+workspace.getWorkspaceId());
		try {
			list = scheduleService.selectByPeriodForWorkspace(getUser().getUserId(), workspaceId, start, end);
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		result.put("userInfo", userInfo);
		result.put("events", list);
		return result;
	}

	@RequestMapping("/webDiaryCalendar")
	public ModelAndView webDiaryCalendar(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, String scheduleId, String workspaceId) {

		ModelAndView mav = new ModelAndView("/lightpack/planner/webdiary/webDiaryCalendar");

		return mav.addObject("scheduleId", scheduleId).addObject("startDate", startDate).addObject("endDate", endDate).addObject("workspaceId", workspaceId);
	}

	@RequestMapping("/webDiaryCalendarView")
	public ModelAndView webDiaryCalendarView(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, String scheduleId, String workspaceId) {
		boolean isUpdate = false;
		Schedule schedule = this.getScheduleAllData(scheduleId);
		String userId = getUser().getUserId();
		String regUserId = schedule.getRegisterId();
		isUpdate = scheduleService.isEditable(userId, regUserId);
		ModelAndView mav = new ModelAndView("/lightpack/planner/webdiary/webDiaryCalendarView");

		return mav.addObject("scheduleId", scheduleId).addObject("startDate", startDate).addObject("endDate", endDate).addObject("workspaceId", workspaceId).addObject("isUpdate", isUpdate);

	}

	/**
	 * 일정 조회
	 * @param scheduleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/webDiaryViewSchedule")
	public ModelAndView webDiaryViewSchedule(@RequestParam("scheduleId") String scheduleId) {
		ModelAndView mav = new ModelAndView("lightpack/planner/webdiary/webDiaryViewSchedule");
		
		boolean isUpdate = false;
		Schedule schedule = this.getScheduleAllData(scheduleId);
		
		if (schedule == null) {
			throw new IKEP4AuthorizedException(messageSource, "ui.lightpack.planner.common.message.deletedItem");
		}
		
		String userId = getUser().getUserId();
		String regUserId = schedule.getRegisterId();
		
		List<Map> mandators = service.getMyMandators(userId);
		
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
		if(isUpdate == false) {
			
			isUpdate  = this.aclService.hasSystemPermission("Planner", "MANAGE","Planner", getUser().getUserId());//일정 전체 관리자는 수정 권한 준다. 
		
		}
		
		if(!StringUtil.isEmpty(schedule.getMeetingRoomId())) {
			
			mav.addObject("meetingRoom", true);
		} else {
			
			mav.addObject("meetingRoom", false);
		}
		
		mav.addObject("mandators", mandators);
		mav.addObject("isUpdate", isUpdate);
		mav.addObject("everySchedule", schedule.getEveryoneSchedule());
		try {
			mav.addObject("scheduleInfo", (new ObjectMapper()).writeValueAsString(schedule));
		} catch(Exception e) {
			//mav.addObject("scheduleInfo", null);
		}

		return mav;
	}

	/**
	 * left menu에서 필요한 기본 데이터 읽기
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getWebDiaryInitData")
	public @ResponseBody Map<String, Object> getWebDiaryInitData() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			User user = getUser();
			
			List<Map> mandatorList = service.getMyMandators(user.getUserId());
			List<PlannerCategory> categoryList = categoryService.getList(getUser().getLocaleCode());
			
			result.put("mandatorList", mandatorList);
			result.put("categoryList", categoryList);
			result.put("userInfo", user);
			result.put("portalInfo", getPortal());
			
			Boolean hasCompanyScheduleAdminPermission = this.aclService.hasSystemPermission("CompanySchedule", "MANAGE","CompanySchedule", getUser().getUserId());//전사일정 관리자 
			
			if (hasCompanyScheduleAdminPermission) {
				result.put("companyScheduleAdminFlag", true);
			} else {
				result.put("companyScheduleAdminFlag", false);
			}
			
			result.put("success", SUCCESS);
		} catch(Exception ex) {
			result.put("success", FAIL);
			//throw new Ikep4AjaxException("", null, ex);
		}
		return result;
	}
	
	
}
