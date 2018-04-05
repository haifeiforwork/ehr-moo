package com.lgcns.ikep4.lightpack.planner.restful.resources;

import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.web.ReserveController;
import com.lgcns.ikep4.lightpack.planner.dao.AlarmDao;
import com.lgcns.ikep4.lightpack.planner.dao.ParticipantDao;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.EventInfoVO;
import com.lgcns.ikep4.lightpack.planner.model.Participant;
import com.lgcns.ikep4.lightpack.planner.model.PlannerCategory;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.model.UpdateScheduleVO;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerBodyCountAll;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerBodyEntrustUserList;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerBodyFavoriteUserList;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerBodyPlanCategory;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerBodyPlanCnt;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerBodyPlanDetail;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerBodyPlanList;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerBodyWorkGroupList;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataAlarm;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataAttach;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataCountAll;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataEntrustUser;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataFacilities;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataFavoriteUser;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataHoliday;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataPlan;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataPlanCategory;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataPlanDetail;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataReference;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataShare;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataTodo;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataUnfixedTime;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerDataWorkGroup;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerParam;
import com.lgcns.ikep4.lightpack.planner.restful.model.PlannerRoot;
import com.lgcns.ikep4.lightpack.planner.service.AlarmPlannerService;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.lightpack.planner.search.ScheduleSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.addressbook.model.Addrgroup;
import com.lgcns.ikep4.support.addressbook.search.AddrgroupSearchCondition;
import com.lgcns.ikep4.support.addressbook.service.AddrgroupService;
import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.MultiPart;

@Path("/planner")
@Component
public class PlannerRestful {

	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private WorkspaceService workspaceService;
	
	@Autowired
	private AlarmPlannerService alarmPlannerService;
	
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private PortalFavoriteService portalFavoriteService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private PlannerCategoryService categoryService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ScheduleDao scheduleDao;

	@Autowired
	private AlarmDao alarmDao;

	@Autowired
	private ParticipantDao participantDao;
	
	
	/**
	 * 위임자 목록
	 * @return
	 */
	@POST
	@Path("/retrieveEntrustUserList")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot getEntrustUserList(PlannerParam param) {
		Head head;
		PlannerBodyEntrustUserList body;
		
		Portal portal = portalService.readPortal(param.getPortalId());
		User user = userService.read(param.getUserId());
		
		boolean isDefaultLocale = portal.getDefaultLocaleCode().equals(user.getLocaleCode());
		
		try{
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyEntrustUserList();
			
			List<Map> mandatorList = calendarService.getMyMandators(param.getUserId());
			for(Map<String, Object> mandator : mandatorList) {
				String userId = mandator.get("mandatorId").toString();
				String userName = isDefaultLocale ? mandator.get("userName").toString() : mandator.get("userEnglishName").toString();
				String userDept = isDefaultLocale ? mandator.get("teamName").toString() : mandator.get("teamEnglishName").toString();
				String userTitle = isDefaultLocale ? mandator.get("jobTitleName").toString() : mandator.get("jobTitleEnglishName").toString();
				
				body.addEntrustUser(new PlannerDataEntrustUser(userId, userName, userDept, userTitle));
			}
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new PlannerBodyEntrustUserList();
		} finally {
		}
		
		return new PlannerRoot(head, body);
	}
	
	/**
	 * 팀 목록
	 * @return
	 */
	@POST
	@Path("/retrieveWorkGroupList")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot getWorkGroupList(PlannerParam param) {
		Head head;
		PlannerBodyWorkGroupList body;
		
		try{
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyWorkGroupList();
			
			Map<String, String> userInfo = new HashMap<String,String>();
			userInfo.put("memberId", param.getUserId());
			userInfo.put("portalId", param.getPortalId());
			userInfo.put("isPlanner", "1");
			List<Workspace> myWorkSpace = workspaceService.listMyCollaborationForMobile(userInfo);
			
			for(Workspace team : myWorkSpace) {
				String groupId = team.getWorkspaceId();
				String groupName = team.getWorkspaceName();
				body.addWorkGroup(new PlannerDataWorkGroup(groupId, groupName, "group"));
			}
			
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new PlannerBodyWorkGroupList();
		} finally {
		}
		
		return new PlannerRoot(head, body);
	}
	
	/**
	 * 전사 목록
	 * @return
	 */
	@POST
	@Path("/retrieveCompanyList")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot retrieveCompanyList(PlannerParam param) {
		Head head;
		PlannerBodyWorkGroupList body;
		
		try{
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyWorkGroupList();
			
			Map<String, String> userInfo = new HashMap<String,String>();
			userInfo.put("memberId", param.getUserId());
			userInfo.put("portalId", param.getPortalId());
			List<Workspace> myWorkSpace = workspaceService.getPresentPlannerMenuList(param.getUserId());
			
			for(Workspace team : myWorkSpace) {
				if("전체".equals(team.getWorkspaceId())||"본사".equals(team.getWorkspaceId())||"진주".equals(team.getWorkspaceId())||"울산".equals(team.getWorkspaceId())||"대구".equals(team.getWorkspaceId())) { 
					String groupId = team.getWorkspaceId();
					String groupName = team.getWorkspaceId();
					body.addWorkGroup(new PlannerDataWorkGroup(groupId, groupName, "company"));
				}
			}
			
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new PlannerBodyWorkGroupList();
		} finally {
		}
		
		return new PlannerRoot(head, body);
	}
	
	
	/**
	 * Favorite 사용자 목록
	 * @return
	 */
	@POST
	@Path("/retrieveFavoriteUserList")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot retrieveFavoriteUserList(PlannerParam param) {
		Head head;
		PlannerBodyFavoriteUserList body;
		
		Portal portal = portalService.readPortal(param.getPortalId());
		User user = userService.read(param.getUserId());
		
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.defaultLocaleCode", portal.getDefaultLocaleCode(), RequestAttributes.SCOPE_SESSION);
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try{
			List<PlannerDataFavoriteUser> favoriteUserList = new ArrayList<PlannerDataFavoriteUser>();
			
			PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();
			searchCondition.setCurrentCount(0);
			searchCondition.setPagePerRecord(100);
			searchCondition.setRegisterId(param.getUserId());
			SearchResult<PortalFavorite> favoriteSearchResult= portalFavoriteService.getAllForPeople(searchCondition);
			for(PortalFavorite favorite : favoriteSearchResult.getEntity()) {
				String userId = favorite.getTargetId(),
					userName = favorite.getTitle(),
					userDept = favorite.getTeamName(),
					userTitle = favorite.getJobTitleName();
				favoriteUserList.add(new PlannerDataFavoriteUser(userId, userName, userDept, userTitle));
			}
			
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyFavoriteUserList(favoriteUserList);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new PlannerBodyFavoriteUserList();
		} finally {
			//RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new PlannerRoot(head, body);
	}
	
	/**
	 * 카테고리 정보 조회
	 * @return
	 */
	@POST
	@Path("/retrievePlanCategoryList")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot retrievePlanCategoryList(PlannerParam param) {
		Head head;
		PlannerBodyPlanCategory body;
		
		//Portal portal = portalService.readPortal(param.getPortalId());
		User user = userService.read(param.getUserId());
		
		//RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try{
			List<PlannerDataPlanCategory> planCategoryList = new ArrayList<PlannerDataPlanCategory>();
			
			List<PlannerCategory> categoryList = categoryService.getList(user.getLocaleCode());
			for(PlannerCategory category : categoryList) {
				planCategoryList.add(new PlannerDataPlanCategory(category.getCategoryId(), category.getCategoryName(), category.getIcon()));
			}
			
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyPlanCategory(planCategoryList);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new PlannerBodyPlanCategory();
		} finally {
			//RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new PlannerRoot(head, body);
	}
	
	/**
	 * 나의 일정 갯수
	 * @return
	 */
	@POST
	@Path("/retrieveCountAll")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot retrieveCountAll(PlannerParam param) {
		Head head;
		PlannerBodyCountAll body;

		User user = userService.read(param.getUserId());
		
		//scheduleService.portalQuickMenuCountAll 메소드에서 세션정보를 참조하기때문에 User 정보 저장 함
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		try{
			PlannerDataCountAll returnData = new PlannerDataCountAll();
			
			Integer totalCount = 0 ;
			
			totalCount=scheduleService.getUserScheduleCount(user.getUserId(), new Date());
					
			returnData.setScheduleCount(totalCount.toString());
			
			returnData.setTotalCount(totalCount.toString());
			
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyCountAll(returnData);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new PlannerBodyCountAll();
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new PlannerRoot(head, body);
	}
	
	/**
	 * 휴일 목록 읽기
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map>  calFeedHoliday(long start, long end, User user) {
		List<Map> list = new ArrayList<Map>();

		try {
			list = calendarService.getHolidayList(start, end, user.getNationCode());
		} catch (Exception ex) {
			// throw new Ikep4AjaxException("", null, ex);
		}
		return list;
	}
	
	@POST
	@Path("/retrievePlanList")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot getPlanList(PlannerParam param) {
		Head head;
		PlannerBodyPlanList body;
		
		try{
			User user = userService.read(param.getUserId());
			RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
			
			Date clientNow = timeZoneSupportService.convertTimeZone();
			Date today = DateUtils.truncate(clientNow, Calendar.DATE);
			
			String searchUserId = param.getSearchUserId();
			String startStr = param.getSearchStartDate(), endStr = param.getSearchEndDate();
			String searchValue = param.getSearchValue();
			Date startDate, endDate;
			
			
			if(startStr == null || StringUtil.isEmpty(startStr)) startDate = new Date(today.getTime());
			else startDate = DateUtil.toDate(startStr);
			
			if(endStr == null || StringUtil.isEmpty(endStr)) endDate = new Date(today.getTime());
			else endDate = DateUtil.toDate(endStr);
			
			startDate.setHours(0); startDate.setMinutes(0); startDate.setSeconds(0);
			endDate.setHours(0); endDate.setMinutes(0); endDate.setSeconds(0);
			
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyPlanList();
			
			List<Map<String, Object>> scheduleList;
			if(StringUtil.isEmpty(searchValue)) {
				// 휴일 목록
				List<Map> holidayList = calFeedHoliday(startDate.getTime(), endDate.getTime(), user);
				for(Map holiday : holidayList) {
					//{isPortalNation=false, startDate=2013-02-11 00:00:00.0, title=建国記念日, nationName=日本, nationCode=JPN}
					String holidayName = holiday.get("title").toString();
					String holidayDate = holiday.get("startDate").toString();
					body.addHoliday(new PlannerDataHoliday(holidayName, holidayDate));
				}
				
				//전사 목록
				if("E".equals(param.getPlannerType())) {
					//공장 위치. null 이면 전체.
					String workAreaName = param.getSearchArea();
					scheduleList = scheduleService.selectByPeriodForCompany(user.getUserId(), workAreaName, startDate.getTime(), endDate.getTime());
				//팀 목록
				} else if ("W".equals(param.getPlannerType())){
					String workspaceId = param.getWorkspaceId();
					scheduleList = scheduleService.selectByPeriodForWorkspace(user.getUserId(), workspaceId, startDate.getTime(), endDate.getTime());
				//사용자 목록
				} else {
					scheduleList = scheduleService.selectByPeriodByTargetUser(
					user.getUserId(), searchUserId, startDate.getTime(), endDate.getTime());
				}
			} else {	// 일정 검색
				Portal portal = portalService.readPortal(param.getPortalId());
				
				int pageNumber = 1, pagePerRecord = 20;
				String strPageNumber = param.getSearchPageNumber(), strPagePerRecord = param.getSearchPagePerRecord();
				if(!StringUtil.isEmpty(strPageNumber)) pageNumber = Integer.valueOf(strPageNumber);
				if(!StringUtil.isEmpty(strPagePerRecord)) pagePerRecord = Integer.valueOf(strPagePerRecord);
				
				ScheduleSearchCondition searchCondition = new ScheduleSearchCondition();
				searchCondition.setTargetId(searchUserId);
				searchCondition.setTargetType("user");
				searchCondition.setDefaultPortalLocale(user.getLocaleCode().equals(portal.getDefaultLocaleCode()));
				searchCondition.setStartDate(startDate);
				searchCondition.setEndDate(endDate);
				searchCondition.setKeyword(searchValue);//URLDecoder.decode(keyword, "UTF-8")
				searchCondition.setSearchFields("title,contents".split(","));
				
				
				searchCondition.setPagePerRecord( pagePerRecord );
				searchCondition.setPageIndex( pageNumber );
				searchCondition.setStartRowIndex((searchCondition.getPageIndex() - 1) * searchCondition.getPagePerRecord());
				searchCondition.setEndRowIndex(searchCondition.getStartRowIndex() + searchCondition.getPagePerRecord());
				
				Map<String, Object> searchResult = scheduleService.selectByPeriodScheduleSearch(user.getUserId(), searchCondition);
				scheduleList = (List<Map<String, Object>>)searchResult.get("eventList");
			}
			
			for(Map<String, Object> plan : scheduleList) {
//				int scheduleType = (Integer)plan.get("scheduleType");
				//if(scheduleType == 0) continue;	// mobile에서 UI처리 할때까지 보류
				
				//{icon=category2, startDate=Sun Feb 03 00:00:00 KST 2013, scheduleId=101200064679, wholeday=1, repeatPeriod=null, categoryId=2, repeatType=null, participantId=, endDate=Sun Feb 03 00:00:00 KST 2013, repeatEndDate=null, facilityId=, attendanceRequest=0, schedulePublic=0, workspaceId=, categoryName=, title=종일 일정1111111111111111111111, color=event_class_type1, registerId=admin, repeatPeriodOption=null, place=, repeatStartDate=null, sendmail=0}
				String isRepeat, secretFlag, itemStartDate, itemStartTime, itemEndDate, itemEndTime;
				String itemId = (String)plan.get("scheduleId");
				String isWholeDay = plan.get("wholeday").toString();
				String itemColor = (String)plan.get("color");
				String itemTitle = plan.get("title").toString();
				String itemPlace = (String)plan.get("place");
				String regUserId = plan.get("registerId").toString();
				
				String repeatType = (String)plan.get("repeatType");
				isRepeat = (StringUtil.isEmpty(repeatType) || "0".equals(repeatType)) ? "0" : "1";
				
				String schedulePublic = plan.get("schedulePublic").toString();
				if(schedulePublic.equals("1")) {
					boolean viewable = (Boolean)plan.get("viewable");
					if(viewable) secretFlag = "SHR";	// 비공개인데 볼수 있는 경우
					else secretFlag = "EXC";	// 볼수 없는 경우
				}
				else secretFlag = "PUB";	// 공개
				
				Date sDate = (Date)plan.get("startDate");
				Date eDate = (Date)plan.get("endDate");
				itemStartDate = DateUtil.getFmtDateString(sDate, "yyyy.MM.dd");
				itemStartTime = DateUtil.getFmtDateString(sDate, "HH:mm");
				itemEndDate = DateUtil.getFmtDateString(eDate, "yyyy.MM.dd");
				itemEndTime = DateUtil.getFmtDateString(eDate, "HH:mm");
				
				String itemCategoryId = plan.get("categoryId").toString();
				String itemCategoryName = plan.get("categoryName").toString();
//				String itemCategoryIcon = plan.get("icon").toString();
				String itemWorkspaceId = (String)plan.get("workspaceId");
				
				body.addPlan(new PlannerDataPlan(itemId, isWholeDay, isRepeat, secretFlag, itemColor, itemTitle,
						itemPlace, itemStartDate, itemStartTime, itemEndDate, itemEndTime, itemCategoryId,
						itemCategoryName, "", regUserId, itemWorkspaceId, "1"));
			}
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new PlannerBodyPlanList();
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new PlannerRoot(head, body);
	}
	
	@POST
	@Path("/retrievePlanDetail")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot getPlanDetail(PlannerParam param) {
		Head head;
		PlannerBodyPlanDetail body;
		
		Portal portal = portalService.readPortal(param.getPortalId());
		User user = userService.read(param.getUserId());
		
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		String scheduleId = param.getItemId();
		boolean isDefaultLocale = portal.getDefaultLocaleCode().equals(user.getLocaleCode());
		
		try{
			Schedule schedule = calendarService.getScheduleAllDataWithMobile(scheduleId, user.getLocaleCode());
			
			String secretFlag = "", isSecret, itemStartDate, itemStartTime, itemEndDate, itemEndTime;
			Date sDate = schedule.getStartDate();
			Date eDate = schedule.getEndDate();
			
			int schedulePublic = schedule.getSchedulePublic();
			isSecret = (schedulePublic == 1) ? "1" : "0";
			
			itemStartDate = DateUtil.getFmtDateString(sDate, "yyyy.MM.dd");
			itemStartTime = DateUtil.getFmtDateString(sDate, "HH:mm");
			itemEndDate = DateUtil.getFmtDateString(eDate, "yyyy.MM.dd");
			itemEndTime = DateUtil.getFmtDateString(eDate, "HH:mm");
			
			User regUser = userService.read(schedule.getRegisterId());
			
			List<PlannerDataShare> shareList = new ArrayList<PlannerDataShare>();
			List<PlannerDataReference> referList = new ArrayList<PlannerDataReference>();
			List<PlannerDataFacilities> facilityList = new ArrayList<PlannerDataFacilities>();
			List<PlannerDataAttach> attachList = new ArrayList<PlannerDataAttach>();
			List<PlannerDataAlarm> alarmList = new ArrayList<PlannerDataAlarm>();
			List<PlannerDataUnfixedTime> unfixedTimeList = new ArrayList<PlannerDataUnfixedTime>();
			
//			int scheduleType = schedule.getScheduleType();
			String isUnfixedPlan = "0";
			
//			PlannerDataPlanDetail planDetail = new PlannerDataPlanDetail(
//				schedule.getScheduleId(), ((Integer)schedule.getWholeday()).toString(),
//				((Integer)schedule.getRepeat()).toString(), secretFlag, schedule.getColor(), schedule.getTitle(),
//				schedule.getPlace(), itemStartDate, itemStartTime, itemEndDate, itemEndTime,
//				schedule.getCategoryId(), schedule.getCategoryName(), schedule.getIcon(),
//				schedule.getContents(),	regUser.getUserId(),(isDefaultLocale ? regUser.getUserName() : regUser.getUserEnglishName()),
//				(isDefaultLocale ? regUser.getTeamName() : regUser.getTeamEnglishName()), (isDefaultLocale ? regUser.getJobTitleName() : regUser.getJobTitleEnglishName()),
//				Integer.toString(schedule.getSendmail()), Integer.toString(schedule.getAttendanceRequest()),
//				schedule.getWorkspaceId(), schedule.getWorkspaceName(), isUnfixedPlan, isSecret
//			);
			
			PlannerDataPlanDetail planDetail = new PlannerDataPlanDetail(
				schedule.getScheduleId(), ((Integer)schedule.getWholeday()).toString(),
				((Integer)schedule.getRepeat()).toString(), secretFlag, schedule.getColor(), schedule.getTitle(),schedule.getPlace(), 
				itemStartDate, itemStartTime, itemEndDate, itemEndTime,
				schedule.getCategoryId(), schedule.getCategoryName(), "",
				schedule.getContents(),	regUser.getUserId(),(isDefaultLocale ? regUser.getUserName() : regUser.getUserEnglishName()),
				(isDefaultLocale ? regUser.getTeamName() : regUser.getTeamEnglishName()), (isDefaultLocale ? regUser.getJobTitleName() : regUser.getJobTitleEnglishName()),
				"", Integer.toString(schedule.getAttendanceRequest()),
				schedule.getWorkspaceId(), schedule.getWorkspaceName(), isUnfixedPlan, isSecret, schedule.getSchedulePrivate()+"", 
				schedule.getCartooletcId(), schedule.getCartooletcName(), schedule.getMeetingRoomId()
			);
			
			// 참여/참조자
			List<Participant> participants = schedule.getParticipantList();
			if(participants != null && participants.size() > 0) {
				for(Participant participant : participants) {
					String targetName = isDefaultLocale ? participant.getTargetUserName() : participant.getTargetUserEnglishName();
					String targetTeamName = isDefaultLocale ? participant.getTargetUserTeamName() : participant.getTargetUserTeamEnglishName();
					String targetJobTitleName = isDefaultLocale ? participant.getTargetUserJobTitleName() : participant.getTargetUserJobTitleEnglishName();
					if(participant.getTargetType().equals("1")) {	// 참여(참가)자
						shareList.add(new PlannerDataShare(participant.getTargetUserId(), targetName, targetTeamName, targetJobTitleName ));
					} else {	// 참조자
						referList.add(new PlannerDataReference(participant.getTargetUserId(), targetName, targetTeamName, targetJobTitleName ));
					}
				}
			}
			
			//설비
			List<MeetingRoom> facilities = schedule.getMeetingRoomList();
			if(facilities != null && facilities.size() > 0) {
				for(MeetingRoom facility : facilities) {
					facilityList.add(new PlannerDataFacilities(facility.getMeetingRoomId(),
							(isDefaultLocale ? facility.getMeetingRoomName() : facility.getMeetingRoomEnglishName()) ));
				}
			}
			
			//첨부파일
			List<FileData> files = schedule.getFileDataList();
			if(files != null && files.size() > 0) {
				for(FileData file : files) {
					String fileUrl = CommonConstant.File_DOWNLOAD_BASE_URL + file.getFileId();
					attachList.add(new PlannerDataAttach(file.getFileRealName(), file.getFilePath(), file.getFileId(),
							((Integer)file.getFileSize()).toString(), file.getFileName(), CommonConstant.IKEP_BASE_URL + fileUrl));
				}
			}
			
			List<Alarm> alarms = schedule.getAlarmList();
			if(alarms != null && alarms.size() > 0) {
				for(Alarm alarm : alarms) {
					alarmList.add(new PlannerDataAlarm(alarm.getAlarmType(), alarm.getAlarmTime()));
				}
			}
			
//			if(scheduleType == 0) {
//				for(ScheduleUnfixedTime time : schedule.getUnfixedTimeList()) {
//					String startDate = DateUtil.getFmtDateString(time.getStartDate(), "yyyy.MM.dd HH:mm");
//					String endDate = DateUtil.getFmtDateString(time.getEndDate(), "yyyy.MM.dd HH:mm");
//					unfixedTimeList.add(new PlannerDataUnfixedTime(startDate, endDate));
//				}
//			}
			
			
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyPlanDetail(planDetail, shareList, referList, facilityList, attachList, alarmList, unfixedTimeList);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new PlannerBodyPlanDetail();
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new PlannerRoot(head, body);
	}
	
	@POST 
	@Path("/retrievePlanCnt")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot getPlanCnt(PlannerParam param) {
		Head head;
		PlannerBodyPlanCnt body;
		
		Date date;
		String searchDate = param.getSearchDate();
		if(searchDate == null && StringUtil.isEmpty(searchDate))
			date = timeZoneSupportService.convertTimeZone();
		else
			date = DateUtil.toDate(searchDate);

		try {
			User user = userService.read(param.getUserId());
			RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
			
			int cnt = scheduleService.getUserScheduleCount(param.getUserId(), date);
			
			head = new Head(0, Response.Status.OK.toString());
			body = new PlannerBodyPlanCnt(cnt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			head = new Head(1, "Error");//Response.Status.INTERNAL_SERVER_ERROR.toString()
			body = new PlannerBodyPlanCnt(0);
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new PlannerRoot(head, body);
	}
	
	@POST
	@Path("/insertPlan")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot insertPlan(MultiPart multipart) {
		Head head;
		
		//Portal portal;
		User user = null;
		
		try{
			user = userService.read(getMultipartParamValue(multipart, "userId"));
	    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
	    	
	    	Portal portal = portalService.readPortalDefault();
	    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.portal", portal, RequestAttributes.SCOPE_SESSION);
	    	
			Schedule schedule = getGenerateMultipartToSchedule(multipart, user);
			
			String id;
			if(StringUtil.isEmpty(schedule.getTrusteeId())) {
//				id = calendarService.createWithSendmail(schedule, user);
				id = calendarService.create(schedule, user);
			}
			else {
				User trustee = userService.read(schedule.getTrusteeId());
				schedule.setMandatorId(user.getUserId());
				id = calendarService.createByTrustee(schedule, trustee);
			}
			
			//메일보내기 1 : 보냄, 0 : 안보냄
			if (id != null && "1".equals(getMultipartParamValue(multipart,"sendMail"))) {
				alarmPlannerService.sendSimpleMail("create", schedule);
			}
			
			if (id != null) {
				if(id.equals(ReserveController.DUPLICATE)) {	// 설비 예약 시간 중복
					head = new Head(2, ReserveController.DUPLICATE);
				} else {
					head = new Head(0, Response.Status.OK.toString());	// id
				}
			} else {
				head = new Head(1, "fail");
			}
		} catch(Exception e) {
			head = new Head(1, "Error");
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new PlannerRoot(head, null);
	}
	
	private Schedule getGenerateMultipartToSchedule(MultiPart multipart, User user) throws Exception {
		Schedule schedule = new Schedule();
		
		List<Alarm> alarmList = new ArrayList<Alarm>();
		List<Participant> participantList = new ArrayList<Participant>();
		List<MeetingRoom> meetingRoomList = new ArrayList<MeetingRoom>();
		//List<Recurrences> recurrencesList = null;
		List<FileData> uploadList = new ArrayList<FileData>();
		List<FileLink> fileLinkList = new ArrayList<FileLink>();
		
		Date startDate=null, endDate=null;
		String[] startTime=null, endTime=null;
		String[] alarmType=null, alarmTime=null, share=null, refer=null, meetingRooms=null;
		String workspaceId = null, trusteeId = null, cartooletcId = null, meetingRoomId = null;
		
		List<BodyPart> bodypart = multipart.getBodyParts();
		for(BodyPart bp : bodypart) {
			FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
			FormDataContentDisposition disposition = fdbodypart.getFormDataContentDisposition();
			
			//파일명이 없는것은 Content 로 간주 한다.
			if(disposition.getFileName() == null){
				String name = fdbodypart.getName();
				String value = fdbodypart.getValue();
				
				if(     "itemId".equals(name)) schedule.setScheduleId(value);
				else if("portalId".equals(name)) schedule.setPortalId(value);
				else if("isWholeDay".equals(name)) schedule.setWholeday(Integer.valueOf(value));
//				else if("isSendMail".equals(name)) schedule.setSendmail(Integer.valueOf(value));
				else if("isSecret".equals(name)) schedule.setSchedulePublic(Integer.valueOf(value));
				else if("isAttendance".equals(name)) schedule.setAttendanceRequest(Integer.valueOf(value));
				else if("itemColor".equals(name)) schedule.setColor(value);
				else if("itemTitle".equals(name)) schedule.setTitle(value);
				else if("itemPlace".equals(name)) schedule.setPlace(value);
				else if("itemContent".equals(name)) schedule.setContents(value);
				else if("categoryId".equals(name)) schedule.setCategoryId(value);
				else if("itemStartDate".equals(name)) startDate = DateUtil.toDate(value);
				else if("itemEndDate".equals(name)) endDate = DateUtil.toDate(value);
				else if("itemStartTime".equals(name)) startTime = StringUtil.isEmpty(value) ? null : value.split(":");
				else if("itemEndTime".equals(name)) endTime = StringUtil.isEmpty(value) ? null : value.split(":");
				else if("alarmType".equals(name)) alarmType = StringUtil.isEmpty(value) ? null : value.split("\\|");
				else if("alarmTime".equals(name)) alarmTime = StringUtil.isEmpty(value) ? null : value.split("\\|");
				else if("shareList".equals(name)) share = StringUtil.isEmpty(value) ? null : value.split("\\|");
				else if("referenceList".equals(name)) refer = StringUtil.isEmpty(value) ? null : value.split("\\|");
				//else if("facilitiesList".equals(name)) meetingRooms = StringUtil.isEmpty(value) ? null : value.split("\\|");
				else if("workspaceId".equals(name)) workspaceId = value;
				else if("trusteeId".equals(name)) trusteeId = value;
				else if("schedulePrivate".equals(name)) schedule.setSchedulePrivate(Integer.parseInt(value));
				else if("itemPlace".equals(name)) { schedule.setPlace(value); System.out.println("itemplace : "+value); }
				else if("cartooletcId".equals(name)) cartooletcId = value;
				else if("meetingRoomId".equals(name)) meetingRoomId = value;
				else System.out.println("name : " + name);
			    
			//첨부파일 업로드
			} else {
				String fileName = URLDecoder.decode(disposition.getFileName(), "utf8");
				BodyPartEntity bpEntity = (BodyPartEntity)fdbodypart.getEntity();
				InputStream is = bpEntity.getInputStream();

				if(fileName != null && !"".equals(fileName)) {
					uploadList.add(fileService.uploadFile(fileName, is, user));
				}
			}
		}
		
		if(workspaceId != null && !StringUtil.isEmpty(workspaceId))
			schedule.setWorkspaceId(workspaceId);
		
		if(trusteeId != null && !StringUtil.isEmpty(trusteeId))
			schedule.setTrusteeId(trusteeId);
		
		if(startTime != null && startTime.length > 0) {
			startDate.setHours(Integer.valueOf(startTime[0]));
			startDate.setMinutes(Integer.valueOf(startTime[1]));
		}
		
		if(endTime != null && endTime.length > 0) {
			endDate.setHours(Integer.valueOf(endTime[0]));
			endDate.setMinutes(Integer.valueOf(endTime[1]));
		}
		
		schedule.setStartDate(startDate);
		schedule.setEndDate(endDate);
		
		if(share != null && share.length > 0) {	// 참여자
			for(String targetUserId : share) {
				if(!StringUtil.isEmpty(targetUserId)) {
					Participant participant = new Participant();
					participant.setTargetType("1");
					participant.setTargetUserId(targetUserId);
					participantList.add(participant);
				}
			}
		}
		
		if(refer != null && refer.length > 0) {	// 참조자
			for(String targetUserId : refer) {
				if(!StringUtil.isEmpty(targetUserId)) {
					Participant participant = new Participant();
					participant.setTargetType("2");
					participant.setTargetUserId(targetUserId);
					participantList.add(participant);
				}
			}
		}
		
		if(participantList.size() > 0)
			schedule.setParticipantList(participantList);
		
		if(alarmType != null && alarmType.length > 0 && alarmTime != null && alarmType.length == alarmTime.length) {
			for(int i=0;i<alarmType.length;i++) {
				if(!alarmTime[i].equals("0")) {
					Alarm alarm = new Alarm();
					alarm.setAlarmType(alarmType[i]);
					alarm.setAlarmTime(alarmTime[i]);
					alarmList.add(alarm);
				}
			}
		}
		if(alarmList.size() > 0) {
			schedule.setAlarmRequest(1);
			schedule.setAlarmList(alarmList);
		} else 
			schedule.setAlarmRequest(0);
		/*
		if(meetingRooms != null && meetingRooms.length > 0) {
			for(String meetingRoomId : meetingRooms) {
				if(!StringUtil.isEmpty(meetingRoomId)) {
					MeetingRoom meetingRoom = new MeetingRoom();
					meetingRoom.setMeetingRoomId(meetingRoomId);
					schedule.setMeetingRoomId(meetingRoomId);
					meetingRoomList.add(meetingRoom);
				}
			}
			schedule.setMeetingRoomList(meetingRoomList);
		}
		*/
		if(meetingRoomId != null && !StringUtil.isEmpty(meetingRoomId))
			schedule.setMeetingRoomId(meetingRoomId);
		
		if(cartooletcId != null && !StringUtil.isEmpty(cartooletcId))
			schedule.setCartooletcId(cartooletcId);
		
		schedule.setRepeat(0);	// mobile에서는 반복일정 처리하지 않음.
		schedule.setRegisterId(user.getUserId());
		schedule.setRegisterName(user.getUserName());
		schedule.setUpdaterId(user.getUserId());
		schedule.setUpdaterName(user.getUserName());
		
		if ((schedule.getScheduleId() == null || StringUtil.isEmpty(schedule.getScheduleId()))	// 신규 등록
				&& uploadList != null && uploadList.size() > 0) {
			schedule.setFileDataList(uploadList);
			
			//FileLinkList 생성
			for(FileData fileData : uploadList){
				FileLink fileLink = new FileLink();
				fileLink.setFileId(fileData.getFileId());
				fileLink.setFileSize(fileData.getFileSize()+"");
				fileLink.setRegisterId(fileData.getRegisterId());
				fileLink.setRegisterName(fileData.getRegisterName());
				fileLink.setUpdaterId(fileData.getUpdaterId());
				fileLink.setUpdaterName(fileData.getUpdaterName());
				fileLink.setFlag("add");
				fileLinkList.add(fileLink);
			}
			schedule.setFileLinkList(fileLinkList);
		}
		
		return schedule;
	}
	
	private String getMultipartParamValue(MultiPart multipart, String key) {
		String value = "";
		List<BodyPart> bodypart = multipart.getBodyParts();
		
		for(BodyPart bp : bodypart) {
			FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
			
			if(key.equals(fdbodypart.getName())){
				value = fdbodypart.getValue();
		    	break;
			}
		}
		
		return value;
	}
	
	@POST
	@Path("/deletePlan")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot deletePlan(PlannerParam param) {
		Head head;
		
		try{
			Schedule schedule = null;
			schedule = getScheduleAllData(param.getItemId());
			
			// 반복일정 삭제 대상이 아님 : 반복일정의 경우 deleteSchedule(UpdateScheduleVO) 써야함
			calendarService.deleteSchedule(param.getItemId());
			
			//메일보내기 1 : 보냄, 0 : 안보냄
			if (param.getItemId() != null && "1".equals(param.getSendMail())) {
				alarmPlannerService.sendSimpleMail("delete",schedule);
			}
			
			head = new Head(0, Response.Status.OK.toString());
		} catch(Exception e) {
			head = new Head(1, "Error");
		}
		
		return new PlannerRoot(head, null);
	}
	
	@POST
	@Path("/updatePlan")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot updatePlan(MultiPart multipart) {
		Head head;
		
		//Portal portal;
		User user = null;
		
		try{
			user = userService.read(getMultipartParamValue(multipart, "userId"));
	    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
	    	
	    	Portal portal = portalService.readPortalDefault();
	    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.portal", portal, RequestAttributes.SCOPE_SESSION);
	    	
			Schedule schedule = getGenerateMultipartToSchedule(multipart, user);
			UpdateScheduleVO updateScheduleVo = new UpdateScheduleVO();
			updateScheduleVo.setUpdateType(0);	// 모바일에서는 반복일정 수정하지 않음.
			updateScheduleVo.setUser(user);
			updateScheduleVo.setNewSchedule(schedule);
			
//			String id = calendarService.updateScheduleWithSendmail(updateScheduleVo);
			String id = calendarService.updateScheduleNew(updateScheduleVo, false);
			
			//메일보내기 1 : 보냄, 0 : 안보냄
			if ("1".equals(getMultipartParamValue(multipart,"sendMail"))) {
				alarmPlannerService.sendSimpleMail("update", updateScheduleVo.getNewSchedule());
			}
			
			if (id != null) {
				if(id.equals(ReserveController.DUPLICATE)) {	// 설비 예약 시간 중복
					head = new Head(2, ReserveController.DUPLICATE);
				} else {
					head = new Head(0, Response.Status.OK.toString());	// id
				}
			} else {
				head = new Head(1, "fail");
			}
		} catch(Exception e) {
			head = new Head(1, "Error");
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new PlannerRoot(head, null);
	}
	
	/**
	 * 일정 복사, 전사 -> 나의일정
	 * @return
	 */
	@POST
	@Path("/copyScheduleFromCompany")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot copyScheduleFromCompany(PlannerParam param) {
		Head head;
		
		try{
			User user = userService.read(param.getUserId());
			
			UpdateScheduleVO params = new UpdateScheduleVO();
			EventInfoVO eventInfo = new EventInfoVO();
			eventInfo.setScheduleId(param.getScheduleId());
			params.setEventInfo(eventInfo);
			calendarService.copyMySchedule(params, user);
			
			head = new Head(0, "Okay");
		} catch(Exception e) {
			head = new Head(1, "Error");
		}
		
		return new PlannerRoot(head, null);
	}
	/**
	 * 일정 복사, 전사 -> 팀일정
	 * @return
	 */
	@POST
	@Path("/copyScheduleFromTeam")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PlannerRoot copyScheduleFromTeam(PlannerParam param) {
		Head head;
		
		try{
			User user = userService.read(param.getUserId());
			
			UpdateScheduleVO params = new UpdateScheduleVO();
			EventInfoVO eventInfo = new EventInfoVO();
			eventInfo.setScheduleId(param.getScheduleId());
			params.setEventInfo(eventInfo);
			calendarService.copyTeamSchedule(params, user);
			
			head = new Head(0, "Okay");
		} catch(Exception e) {
			head = new Head(1, "Error");
		}
		
		return new PlannerRoot(head, null);
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
	
	
}
