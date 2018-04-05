package com.lgcns.ikep4.lightpack.facility.restful.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.facility.model.FacilitySearchCondition;
import com.lgcns.ikep4.lightpack.facility.restful.model.BodyCartooletcDetail;
import com.lgcns.ikep4.lightpack.facility.restful.model.BodyFacilityDetail;
import com.lgcns.ikep4.lightpack.facility.restful.model.BodyFacilityList;
import com.lgcns.ikep4.lightpack.facility.restful.model.BodyFacilityReserve;
import com.lgcns.ikep4.lightpack.facility.restful.model.BodyFacilitySearch;
import com.lgcns.ikep4.lightpack.facility.restful.model.BodyCartooletcList;
import com.lgcns.ikep4.lightpack.facility.restful.model.DataFacility;
import com.lgcns.ikep4.lightpack.facility.restful.model.DataFacilityCategory;
import com.lgcns.ikep4.lightpack.facility.restful.model.DataFacilityCategoryWithLevel;
import com.lgcns.ikep4.lightpack.facility.restful.model.DataFacilityDetail;
import com.lgcns.ikep4.lightpack.facility.restful.model.DataFacilityDetailWithReserve;
import com.lgcns.ikep4.lightpack.facility.restful.model.DataReserveTime;
import com.lgcns.ikep4.lightpack.facility.restful.model.DataCartooletcList;
import com.lgcns.ikep4.lightpack.facility.restful.model.DataTotalListInfo;
import com.lgcns.ikep4.lightpack.facility.restful.model.FacilityParam;
import com.lgcns.ikep4.lightpack.facility.restful.model.FacilityRoot;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomService;
import com.lgcns.ikep4.lightpack.planner.model.Schedule;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


@Path("/facility")
@Component
public class FacilityRestful {
	
	@Autowired
	private UserService userService;

	@Autowired
	private PortalService portalService;

	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
	private MeetingRoomService meetingRoomService;
	
	@Autowired
	private CartooletcService cartooletcService;

	@POST
	@Path("/retrieveFacility")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FacilityRoot retrieveFacility(FacilityParam param) {
		Head head;
		BodyFacilityList body;
		
		Portal portal = portalService.readPortal(param.getPortalId());
		
		String categoryId = param.getCategoryId();
		
		try{
			List<DataFacility> facilityList = new ArrayList<DataFacility>();
			List<DataFacilityCategory> facilityCategoryList = new ArrayList<DataFacilityCategory>();
			List<DataFacilityCategoryWithLevel> parentFacilityCategoryList = new ArrayList<DataFacilityCategoryWithLevel>();
			DataFacilityCategory currentFacilityCategoryInfo;
			
			// 설비 목록
			FacilitySearchCondition searchCondition = new FacilitySearchCondition();
			searchCondition.setFloorId(StringUtil.isEmpty(categoryId) ? "ROOT" : categoryId);
			searchCondition.setPortalId(portal.getPortalId());
			List<MeetingRoom> meetingRooms = meetingRoomService.getMeetingRoomList(searchCondition);
			for(MeetingRoom meetingRoom : meetingRooms) {
				facilityList.add(new DataFacility(meetingRoom.getMeetingRoomId(), meetingRoom.getMeetingRoomName()));
			}
			
			// 카테고리 목록
			if(StringUtil.isEmpty(categoryId)) {	// 최상위 카테고리
				MeetingRoom rootCategory = meetingRoomService.readRootCategory(portal.getPortalId());
				categoryId = rootCategory.getBuildingId();
			}
			
			// 요청한 카테고리의 하위 카테고리
			List<MeetingRoom> floorList = meetingRoomService.readChildCategory(categoryId);
			for(MeetingRoom floor : floorList) {
				facilityCategoryList.add(
					new DataFacilityCategory(
						floor.getBuildingFloorId(),
						floor.getBuildingFloorName()
					)
				);
			}
			
			// 상위 카테고리 정보 : top까지
			List<MeetingRoom> parentList = meetingRoomService.readParentsCategory(categoryId);
			for(MeetingRoom category : parentList) {
				parentFacilityCategoryList.add(
					new DataFacilityCategoryWithLevel(
						category.getBuildingFloorId(),
						category.getBuildingFloorName(),
						Integer.toString(category.getLevel())
					)
				);
			}
			
			// 요청한 카테고리 정보
			MeetingRoom currCategory = meetingRoomService.readCategory(categoryId);
			currentFacilityCategoryInfo = new DataFacilityCategory(
				currCategory.getBuildingFloorId(),currCategory.getBuildingFloorName()
			);
			
			head = new Head(0, Response.Status.OK.toString());
			body = new BodyFacilityList(facilityList, facilityCategoryList, parentFacilityCategoryList, currentFacilityCategoryInfo);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new BodyFacilityList();
		} finally {
		}
		
		return new FacilityRoot(head, body);
	}
	
	@POST
	@Path("/retrieveFacilityList")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FacilityRoot retrieveFacilityList(FacilityParam param) {
		Head head;
		BodyFacilitySearch body;
		
		Portal portal = portalService.readPortal(param.getPortalId());
		User user = userService.read(param.getUserId());
		
		try{
			FacilitySearchCondition searchCondition = new FacilitySearchCondition();
			searchCondition.setPortalId(portal.getPortalId());
			searchCondition.setUserId(user.getUserId());
//			searchCondition.setIsDfaultLocale(isDefaultLocale ? "Y" : "N");
			searchCondition.setSearchWord(param.getSearchWord());
			List<MeetingRoom> facilityList = meetingRoomService.getMeetingRoomList(searchCondition);
			
			List<DataFacilityDetail> list = new ArrayList<DataFacilityDetail>();
			for(MeetingRoom facility :  facilityList) {
				list.add(
					new DataFacilityDetail(
						facility.getMeetingRoomId(),
						facility.getMeetingRoomName(),
						facility.getCategoryId(),
						facility.getBuildingFloorName()
					)
				);
			}
			
			DataTotalListInfo totalListInfo = new DataTotalListInfo((int)Math.ceil(list.size()/searchCondition.getPagePerRecord()), list.size());
			
			head = new Head(0, Response.Status.OK.toString());
			body = new BodyFacilitySearch(list, totalListInfo);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new BodyFacilitySearch(null, null);
		} finally {
		}
		
		return new FacilityRoot(head, body);
	}
	
	@POST
	@Path("/retrieveFacilityDetail")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FacilityRoot retrieveFacilityDetail(FacilityParam param) {
		Head head;
		BodyFacilityDetail body;
		
		Portal portal = portalService.readPortal(param.getPortalId());
		User user = userService.read(param.getUserId());
		
		boolean isDefaultLocale = portal.getDefaultLocaleCode().equals(user.getLocaleCode());
		
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		String facilityId = param.getFacilityId();
		String dateStr = param.getDate();
		
		try{
			Date clientNow = timeZoneSupportService.convertTimeZone();
			Date date = scheduleService.convertToServerTimezoneOnlyDate(DateUtil.toDate(dateStr), clientNow);
			
			DataFacilityDetailWithReserve facilityInfo;
			List<DataReserveTime> reserveTimeList = new ArrayList<DataReserveTime>();
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("facilityId", facilityId);
			MeetingRoom facility = meetingRoomService.read(facilityId);
			
			String facilityName = isDefaultLocale ? facility.getMeetingRoomName() : facility.getMeetingRoomEnglishName();
			String categoryName = facility.getBuildingFloorName();
			facilityInfo = new DataFacilityDetailWithReserve(facilityId, facilityName,
					facility.getCategoryId(), categoryName, DateUtil.getFmtDateString(date, "yyyy.MM.dd"));
			
			Schedule schedule = new Schedule();
			schedule.setStartDate(date);
			schedule.setEndDate(new Date(date.getTime() + (24*60*60*1000)));
			schedule.setMeetingRoomId(param.getFacilityId());
			
			List<MeetingRoom> facilityList = new ArrayList<MeetingRoom>();
			facilityList.add(facility);
			schedule.setMeetingRoomList(facilityList);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
			List<Map<String, Object>> eventList = scheduleService.selectFacilityReserveListWithoutSchedule(schedule);
			for(Map<String, Object> event : eventList) {
				Date startDate = formatter.parse(event.get("startDate").toString());
				Date endDate = formatter.parse(event.get("endDate").toString());
				String strStartDate = DateUtil.getFmtDateString(startDate, "HH:mm");
				String strEndDate = DateUtil.getFmtDateString(endDate, "HH:mm");
				if(startDate.getTime() < schedule.getStartDate().getTime()) strStartDate = "00:00";
				if(endDate.getTime() >= schedule.getEndDate().getTime()) strEndDate = "24:00";
				reserveTimeList.add(new DataReserveTime(strStartDate, strEndDate));
			}
			
			head = new Head(0, Response.Status.OK.toString());
			body = new BodyFacilityDetail(facilityInfo, reserveTimeList);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new BodyFacilityDetail();
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new FacilityRoot(head, body);
	}
	
	@POST
	@Path("/retrieveFacilityReserve")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FacilityRoot retrieveFacilityReserve(FacilityParam param) {
		Head head;
		BodyFacilityReserve body;
		
		User user = userService.read(param.getUserId());
		
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		//일단 다 되는 걸로
		try{
			head = new Head(0, "OK");
			body = new BodyFacilityReserve(true, "");
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new BodyFacilityReserve(false, "");
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new FacilityRoot(head, body); 
	}
	@POST
	@Path("/retrieveCartooletc")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FacilityRoot retrieveCartooletc(FacilityParam param) {
		Head head;
		BodyCartooletcList body;
		
		String regionId = "";
		try{
			head = new Head(0, Response.Status.OK.toString());
			body = new BodyCartooletcList();
			
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("portalId", param.getPortalId());
			paramMap.put("useFlag", "1");
			paramMap.put("mid", param.getMeetingRoomId());
			
			List<Cartooletc> regionList = cartooletcService.regionList(paramMap);
			
			if(regionList.size() > 0 ) {
				for(Cartooletc regionIds : regionList) {
					regionId = regionIds.getRegionId();
				}
			}
			
			Cartooletc cartooletc = new Cartooletc();
			cartooletc.setRegionId(regionId);
			cartooletc.setPortalId(param.getPortalId());
			cartooletc.setUseFlag("1");
			cartooletc.setMid(param.getMeetingRoomId());
			
			// 요청한 미팅룸 자원 리스트
			List<Cartooletc> tooletsList = cartooletcService.select(cartooletc);
			
			for(Cartooletc tooletc : tooletsList) {
				String cartooletcId = tooletc.getCartooletcId();
				String cartooletcName = tooletc.getCartooletcName();
				body.addCartooletc(new DataCartooletcList(cartooletcId, cartooletcName));
			}
			
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new BodyCartooletcList();
		} finally {
		}
		
		return new FacilityRoot(head, body);
	}
	@POST
	@Path("/retrieveCartooletcDetail")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public FacilityRoot retrieveCartooletcDetail(FacilityParam param) {
		Head head;
		BodyCartooletcDetail body;
		
		Portal portal = portalService.readPortal(param.getPortalId());
		User user = userService.read(param.getUserId());
		
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		String cartooletcId = param.getCartooletcId();
		String dateStr = param.getDate();
		
		try{
			Date clientNow = timeZoneSupportService.convertTimeZone();
			Date date = scheduleService.convertToServerTimezoneOnlyDate(DateUtil.toDate(dateStr), clientNow);
			
			List<DataReserveTime> reserveTimeList = new ArrayList<DataReserveTime>();
			
			Schedule schedule = new Schedule();
			schedule.setStartDate(date);
			schedule.setEndDate(new Date(date.getTime() + (24*60*60*1000)));

			List<Map<String, Object>> eventList = scheduleService.readCartooletcScheduleById(cartooletcId, date, date);
			for(Map<String, Object> event : eventList) {
				Date startDate = (Date) event.get("startDate");
				Date endDate = (Date)event.get("endDate");
				String strStartDate = DateUtil.getFmtDateString(startDate, "HH:mm");
				String strEndDate = DateUtil.getFmtDateString(endDate, "HH:mm");
				if(startDate.getTime() < schedule.getStartDate().getTime()) strStartDate = "00:00";
				if(endDate.getTime() >= schedule.getEndDate().getTime()) strEndDate = "24:00";
				reserveTimeList.add(new DataReserveTime(strStartDate, strEndDate));
			}
			
			head = new Head(0, Response.Status.OK.toString());
			body = new BodyCartooletcDetail(reserveTimeList);
		} catch(Exception e) {
			head = new Head(1, "Error");
			body = new BodyCartooletcDetail();
		} finally {
			RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		}
		
		return new FacilityRoot(head, body);
	}
}