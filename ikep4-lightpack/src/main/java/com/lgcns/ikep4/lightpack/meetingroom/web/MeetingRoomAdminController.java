/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.Equipment;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.service.ApproveService;
import com.lgcns.ikep4.lightpack.meetingroom.service.BuildingFloorService;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.lightpack.meetingroom.service.EquipmentService;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/meetingroom")
public class MeetingRoomAdminController extends BaseController {
	
	@Autowired
	MeetingRoomService meetingRoomService;
	
	@Autowired
	BuildingFloorService buildingFloorService;
	
	@Autowired
	private CartooletcService cartooletcService;
	
	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	private ApproveService approveService;
	
	@Autowired
	UserDao userDao;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@RequestMapping(value = "/meetingRoomMain.do")
	public ModelAndView meetingRoomList(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/meetingroom/meetingRoomMain");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("portalId", portal.getPortalId());
		paramMap.put("useFlag", null);
		
		List<BuildingFloor> buildingList = buildingFloorService.buildingList(paramMap);

		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("userId", user.getUserId());
		
		Boolean isMeetingRoomManager = this.isMeetingRoomManager(param);
		
		String buildingId = request.getParameter("buildingId");
		String floorId = request.getParameter("floorId");
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("isMeetingRoomManager", isMeetingRoomManager);
		mav.addObject("buildingList", buildingList);
		mav.addObject("buildingId", buildingId);
		mav.addObject("floorId", floorId);

		return mav;
	}
	
	@RequestMapping(value = "/meetingRoomList.do")
	public ModelAndView meetingRoomListSub(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status, 
			String buildingId, String floorId) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/admin/meetingroom/meetingRoomList");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		try {

			MeetingRoom meetingRoom = new MeetingRoom();
			meetingRoom.setBuildingId(buildingId);
			meetingRoom.setFloorId(floorId);
			meetingRoom.setPortalId(portal.getPortalId());

			List<MeetingRoom> meetingRoomList = meetingRoomService.select(meetingRoom);

			mav.addObject("meetingRoomList", meetingRoomList);

		} catch (Exception ex) {
			
			ex.printStackTrace();
			
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/meetingRoomForm.do")
	public ModelAndView meetingRoomForm(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status, 
			String meetingRoomId, Model model) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/meetingroom/meetingRoomForm");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");

		try {
			
			MeetingRoom meetingRoom = null;
			
			BuildingFloor buildingFloor = new BuildingFloor();

			Map<String,String> param = new HashMap<String, String>();
			param.put("portalId", portal.getPortalId());
			param.put("useFlag", null);
			
			List<BuildingFloor> buildingList = buildingFloorService.buildingList(param);
			
			model.addAttribute("buildingList", buildingList);

			//List<Equipment> equipmentList = equipmentService.equipmentList(portal.getPortalId());
			
			//model.addAttribute("equipmentList", equipmentList);

			if (meetingRoomId != null) {
				
				meetingRoom = meetingRoomService.read(meetingRoomId);
				
				buildingFloor.setBuildingFloorId(meetingRoom.getBuildingId());
			} else {
				
				meetingRoom = new MeetingRoom();
				
				if (buildingList.size() > 0) {
					
					buildingFloor.setBuildingFloorId(buildingList.get(0).getBuildingFloorId());
				}
			}
			
			model.addAttribute("meetingRoom", meetingRoom);

			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("parentBuildingFloorId", buildingFloor.getBuildingFloorId());
			paramMap.put("meetingRoomId", meetingRoom.getMeetingRoomId());
			paramMap.put("portalId", portal.getPortalId());
			paramMap.put("useFlag", null);
			
			List<BuildingFloor> floorList = buildingFloorService.floorList(paramMap);
			List<Cartooletc> toolList = cartooletcService.toolList(paramMap);
			
			User manager = new User();
			User subManager = new User();
			User lastManager = new User();
			String managerName = "";
			String subManagerName = "";
			String lastManagerName = "";
			 
			if(meetingRoom != null && !StringUtil.isEmpty(meetingRoom.getManagerId())) {
			
				manager = userDao.get(meetingRoom.getManagerId());
				
				if(manager != null && !StringUtil.isEmpty(manager.getUserName())) {
					
					managerName = manager.getUserName();
				}
				
				if(manager != null && !StringUtil.isEmpty(manager.getJobTitleName())) {
					
					managerName += " " + manager.getJobTitleName();
				}
				
				if(manager != null && !StringUtil.isEmpty(manager.getTeamName())) {
					
					managerName += " " + manager.getTeamName();
				}
			}
			
			if(meetingRoom != null && !StringUtil.isEmpty(meetingRoom.getSubManagerId())) {
				
				subManager = userDao.get(meetingRoom.getSubManagerId());
				
				if(subManager != null && !StringUtil.isEmpty(subManager.getUserName())) {
					
					subManagerName = subManager.getUserName();
				}
				
				if(subManager != null && !StringUtil.isEmpty(subManager.getJobTitleName())) {
					
					subManagerName += " " + subManager.getJobTitleName();
				}
				
				if(subManager != null && !StringUtil.isEmpty(subManager.getTeamName())) {
					
					subManagerName += " " + subManager.getTeamName();
				}
				
			}
			
			if(meetingRoom != null && !StringUtil.isEmpty(meetingRoom.getLastManagerId())) {
				
				lastManager = userDao.get(meetingRoom.getLastManagerId());
				
				if(lastManager != null && !StringUtil.isEmpty(lastManager.getUserName())) {
					
					lastManagerName = lastManager.getUserName();
				}
				
				if(lastManager != null && !StringUtil.isEmpty(lastManager.getJobTitleName())) {
					
					lastManagerName += " " + lastManager.getJobTitleName();
				}
				
				if(lastManager != null && !StringUtil.isEmpty(lastManager.getTeamName())) {
					
					lastManagerName += " " + lastManager.getTeamName();
				}
				
			}
			
			Boolean isSystemAdmin = this.isSystemAdmin(user);
			
			Map<String,String> parameter = new HashMap<String, String>();
			parameter.put("portalId", portal.getPortalId());
			parameter.put("userId", user.getUserId());
			
			Boolean isMeetingRoomManager = this.isMeetingRoomManager(parameter);
			
			model.addAttribute("isSystemAdmin", isSystemAdmin);
			model.addAttribute("isMeetingRoomManager", isMeetingRoomManager);
			model.addAttribute("managerName", managerName);
			model.addAttribute("subManagerName", subManagerName);
			model.addAttribute("lastManagerName", lastManagerName);
			model.addAttribute("floorList", floorList);
			model.addAttribute("toolList", toolList);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}
	
	@RequestMapping(value = "/createMeetingRoom.do")
	public @ResponseBody String createMeetingRoom(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status,
			@ValidEx MeetingRoom meetingRoom, BindingResult result) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		try {
			
			if (result.hasErrors()) {
				
				throw new IKEP4AjaxValidationException(result, messageSource);
			}
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			meetingRoom.setPortalId(portal.getPortalId());

			/*
			String equipmentStr = "";
			
			if(meetingRoom.getEquipmentList() != null) {
				
				for (String equipment : meetingRoom.getEquipmentList()) {
					
					equipmentStr = equipmentStr + equipment + "^";
				}
			}
			
			if (equipmentStr.length() > 0) {
				
				equipmentStr = equipmentStr.substring(0, equipmentStr.length() - 1);
			}
			
			meetingRoom.setEquipment(equipmentStr);
			*/

			if (StringUtil.isEmpty(meetingRoom.getMeetingRoomId())) {
				meetingRoomService.create(meetingRoom);
				meetingRoomService.createToolRoomMap(meetingRoom);
			} else {
				meetingRoomService.update(meetingRoom);
				meetingRoomService.createToolRoomMap(meetingRoom);
			}

			return "ok";

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}
	}
	
	@RequestMapping(value = "/sortMeetingRoom.do")
	public @ResponseBody
	String sortMeetingRoom(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status,
			String buildingId, String meetingRoomId, String sortOrder) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		try {
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			MeetingRoom meetingRoom = new MeetingRoom();
			meetingRoom.setBuildingId(buildingId);
			meetingRoom.setPortalId(portal.getPortalId());

			List<MeetingRoom> meetingRoomList = meetingRoomService.select(meetingRoom);

			meetingRoomService.changeSort(meetingRoomList, meetingRoomId, Integer.parseInt(sortOrder));

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}
	
	@RequestMapping(value = "/deleteMeetingRoom.do")
	public @ResponseBody
	String deleteMeetingRoom(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "MeetingRoom", operationName = { "MANAGE" }, resourceName = "MeetingRoom")) 
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status,
			String[] meetingRoomIds) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			
			throw new IKEP4AuthorizedException();
		}
		
		try {

			meetingRoomService.delete(meetingRoomIds);

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
	public boolean isSystemAdmin(User user) {
		
		return this.aclService.isSystemAdmin("MeetingRoom", user.getUserId());
	}
	
	public boolean isMeetingRoomManager(Map<String, String> param) {
		
		return this.approveService.isMeetingRoomManager(param);
	}
}