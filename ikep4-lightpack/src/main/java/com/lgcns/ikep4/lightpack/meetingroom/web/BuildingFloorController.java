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
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.service.ApproveService;
import com.lgcns.ikep4.lightpack.meetingroom.service.BuildingFloorService;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 시스템  Controller
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemController.java 17712 2012-03-28 06:40:15Z unshj $
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom/buildingfloor")
@SessionAttributes("buildingfloor")
public class BuildingFloorController extends BaseController {
	
	private static final String ROOT_ID = "MR000000";
	
	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 시스템 관리 service
	 */
	@Autowired
	private BuildingFloorService buildingFloorService;
	
	@Autowired
	private CartooletcService cartooletcService;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private ApproveService approveService;
	
	/**
	 * 포탈 시스템 관리 메인 페이지
	 * @param portalSystem 시스템 model
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/buildingFloorMain.do")
	public ModelAndView buildingFloorMain(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="MeetingRoom", operationName={"MANAGE"}, resourceName="MeetingRoom")) 
			BuildingFloor buildingFloor, HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav = new ModelAndView("/lightpack/meetingroom/buildingfloor/main");
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		boolean result = buildingFloorService.exists(ROOT_ID);

		if(!result) {
			
			BuildingFloor rootBf = new BuildingFloor();
			
			rootBf.setBuildingFloorId(ROOT_ID);
			rootBf.setBuildingFloorName("건물/층");
			rootBf.setBuildingFloorEnglishName("Building/Floor");
			rootBf.setParentBuildingFloorId(null);
			rootBf.setUseFlag("1");
			rootBf.setSortOrder(buildingFloorService.getMaxSortOrder());
			rootBf.setPortalId(null);
			ModelBeanUtil.bindRegisterInfo(rootBf, user.getUserId(), user.getUserName());
			
			buildingFloorService.create(rootBf);
		}
		
		String id = request.getParameter("tempId");
		
		BuildingFloor bf = new BuildingFloor();
		
		if(!StringUtil.isEmpty(id))
		{
			bf = buildingFloorService.read(id);
		}
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("buildingFloorId", id);
		paramMap.put("portalId", portal.getPortalId());
		
		int childCount = buildingFloorService.getChildCount(paramMap);
		
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("userId", user.getUserId());
		
		Boolean isMeetingRoomManager = this.isMeetingRoomManager(param);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("isMeetingRoomManager", isMeetingRoomManager);
		mav.addObject("buildingFloor", bf);
		mav.addObject("childCount", childCount);
		
		return mav;
	}
	
	/**
	 * 시스템 트리 목록
	 * @param systemCode 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return Map<String, Object> 시스템 트리 목록
	 */
	@RequestMapping("/getBuildingFloorTree.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> getBuildingFloorTree(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="MeetingRoom", operationName={"MANAGE"}, resourceName="MeetingRoom")) 
			@RequestParam(value = "buildingFloorId", required = false) String buildingFloorId, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		Map<String, Object> item = new HashMap<String, Object>();
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		try {			
			Map<String, String> param = new HashMap<String, String>();
			param.put("portalId", portal.getPortalId());	
			
			if(!StringUtil.isEmpty(buildingFloorId)) {
				param.put("parentBuildingFloorId", buildingFloorId);
			}
			
			List<BuildingFloor> buildingFloorList = buildingFloorService.treeList(param);
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			for(BuildingFloor buildingFloor : buildingFloorList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "buildingfloor");
				map.put("code", buildingFloor.getBuildingFloorId());
				map.put("name", buildingFloor.getBuildingFloorName());
				map.put("parent", buildingFloor.getParentBuildingFloorId());
				map.put("sortOrder", buildingFloor.getSortOrder());
				map.put("level", buildingFloor.getDepthLevel());
				
				Map<String,String> paramMap = new HashMap<String, String>();
				paramMap.put("buildingFloorId", buildingFloor.getBuildingFloorId());
				paramMap.put("portalId", portal.getPortalId());
				
				int childCount = buildingFloorService.getChildCount(paramMap);
				
				map.put("hasChild", childCount);
				list.add(map);
			}

			item.put("items", list);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("id", ex);
		}

		return item;
	}
	
	@RequestMapping(value = "/getFloorList.do")
	public @ResponseBody List<BuildingFloor> getFloorList(String buildingId) {
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");		
		
		List<BuildingFloor> floorList = new ArrayList<BuildingFloor>();
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("parentBuildingFloorId", buildingId);
		paramMap.put("portalId", portal.getPortalId());
		paramMap.put("useFlag", null);
		
		floorList = buildingFloorService.floorList(paramMap);
		
		return floorList;
	}
	
	@RequestMapping(value = "/getToolList.do")
	public @ResponseBody List<Cartooletc> getToolList(String buildingId) {
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");		
		
		List<Cartooletc> toolList = new ArrayList<Cartooletc>();
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("parentBuildingFloorId", buildingId);
		paramMap.put("meetingRoomId", "");
		paramMap.put("portalId", portal.getPortalId());
		paramMap.put("useFlag", null);
		
		toolList = cartooletcService.toolList(paramMap);
		
		return toolList;
	}
	
	/**
	 * 시스템 등록 및 수정 폼
	 * @param systemCode 시스템 코드
	 * @param portalSystem 시스템 model
	 * @param result BindingResult
	 * @param status BindingResult
	 * @param accessResult 접근 가능 여부
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/buildingFloorForm.do")
	public ModelAndView getBuildingFloorForm(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="MeetingRoom", operationName={"MANAGE"}, resourceName="MeetingRoom")) 
			BuildingFloor buildingFloor, BindingResult result, SessionStatus status, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		ModelAndView mav;
		
		if (result.hasErrors())	{
			
			mav = new ModelAndView("/lightpack/meetingroom/buildingfloor/main");
		} else	{
			
			mav = new ModelAndView("/lightpack/meetingroom/admin/buildingfloor/buildingFloorForm");
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			
			String id = buildingFloor.getBuildingFloorId();
			
			BuildingFloor bf = new BuildingFloor();
			
			if(buildingFloor != null) {
				
				bf.setParentBuildingFloorId(buildingFloor.getParentBuildingFloorId());
			}
				
			int childCount = 0;
			
			if(!StringUtil.isEmpty(id)) {
				
				bf = buildingFloorService.read(id);
				
				Map<String,String> paramMap = new HashMap<String, String>();
				paramMap.put("buildingFloor", id);
				paramMap.put("portalId", portal.getPortalId());
				
				childCount = buildingFloorService.getChildCount(paramMap);
			}
			
			String type = "";
			
			if(!StringUtil.isEmpty(bf.getParentBuildingFloorId()) && bf.getParentBuildingFloorId().equals(ROOT_ID)) {
				
				type = "BUILDING";
			} else if(!StringUtil.isEmpty(bf.getParentBuildingFloorId()) && !bf.getParentBuildingFloorId().equals(ROOT_ID)) {
				
				type = "FLOOR";
				
				BuildingFloor parentBf = new BuildingFloor();
				parentBf = buildingFloorService.read(bf.getParentBuildingFloorId());
				
				mav.addObject("parentBuildingName", parentBf.getBuildingFloorName());
				mav.addObject("parentBuildingUseFlag", parentBf.getUseFlag());
			}
			
			mav.addObject("buildingFloor", bf);
			mav.addObject("childCount", childCount);
			mav.addObject("type", type);
			
			status.setComplete();
		}
		
		return mav;
	}
	
	/**
	 * 시스템 생성
	 * @param portalSystem 시스템 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param accessResult 접근 가능 여부
	 * @return String 생성된 시스템 코드
	 * @throws Exception
	 */
	@RequestMapping(value = "/buildingFloorCreate.do", method = RequestMethod.POST)
	public @ResponseBody String buildingFloorCreate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="MeetingRoom", operationName={"MANAGE"}, resourceName="MeetingRoom")) 
			@Valid BuildingFloor buildingFloor, BindingResult result, SessionStatus status, 
			HttpServletRequest request, AccessingResult accessResult) {
		
		String returnValue = "";
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{
			User user = (User) getRequestAttribute("ikep.user");
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");		
			
			buildingFloor.setBuildingFloorId(this.idgenService.getNextId());
			buildingFloor.setSortOrder(buildingFloorService.getMaxSortOrder());
			buildingFloor.setPortalId(portal.getPortalId());
			
			ModelBeanUtil.bindRegisterInfo(buildingFloor, user.getUserId(), user.getUserName());

			returnValue = buildingFloorService.create(buildingFloor);
			
			status.setComplete();
		}
		
		return returnValue;
	}
	
	/**
	 * 시스템 수정
	 * @param portalSystem 시스템 model
	 * @param result BindingResult
	 * @param status SessionStatus
	 * @param request HttpServletRequest
	 * @param model Model 객체
	 * @param accessResult 접근 가능 여부
	 * @return String 수정된 시스템 코드
	 * @throws Exception
	 */
	@RequestMapping(value = "/buildingFloorUpdate.do", method = RequestMethod.POST)
	public @ResponseBody String buildingFloorUpdate(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="MeetingRoom", operationName={"MANAGE"}, resourceName="MeetingRoom")) 
			@Valid BuildingFloor buildingFloor, BindingResult result, SessionStatus status, 
			HttpServletRequest request, Model model, AccessingResult accessResult) {
		
		String returnValue = "";
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		if (result.hasErrors())	{
			throw new IKEP4AjaxValidationException(result, messageSource);
		} else	{
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");
			
			String buildingFloorId = buildingFloor.getBuildingFloorId();
		    
			buildingFloor.setPortalId(portal.getPortalId());
			
			ModelBeanUtil.bindRegisterInfo(buildingFloor, user.getUserId(), user.getUserName());
			
			buildingFloorService.update(buildingFloor);
			
			BuildingFloor bf = new BuildingFloor();
			
			bf = buildingFloorService.read(buildingFloorId);
			
			returnValue = buildingFloor.getBuildingFloorId();
			
			status.setComplete();
			
			model.addAttribute("buildingFloor", bf);
		}
		
		return returnValue;
	}
	
	/**
	 * 시스템 삭제
	 * @param systemCode 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return String
	 */
	@RequestMapping(value = "/buildingFloorDelete.do", method = RequestMethod.POST)
	public @ResponseBody String buildingFloorDelete(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			String buildingFloorId, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		buildingFloorService.delete(buildingFloorId);
		
		return "";
	}
	
	/**
	 * 시스템 트리 목록 우클릭 메뉴의 위로 이동
	 * @param prevNodeId 트리 순서상 앞에 위치하는 시스템 코드
	 * @param curNodeId 이동하고자 하는 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return String
	 */
	@RequestMapping(value = "/moveUpBuildingFloor.do", method = RequestMethod.POST)
	public @ResponseBody String moveUpBuildingFloor(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="MeetingRoom", operationName={"MANAGE"}, resourceName="MeetingRoom")) 
			String prevNodeId, String curNodeId, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String prevSortOrder = buildingFloorService.read(prevNodeId).getSortOrder();
		String curSortOrder = buildingFloorService.read(curNodeId).getSortOrder();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("prevSortOrder", prevSortOrder);
		map.put("prevNodeId", prevNodeId);
		map.put("curSortOrder", curSortOrder);
		map.put("curNodeId", curNodeId);
		map.put("updaterId", user.getUserId());
		map.put("updaterName", user.getUserName());

		buildingFloorService.moveUpBuildingFloor(map);

		return "";
		
	}
	
	/**
	 * 시스템 트리 목록 우클릭 메뉴의 아래로 이동
	 * @param nextNodeId 트리 순서상 뒤에 위치하는 시스템 코드
	 * @param curNodeId 이동하고자 하는 시스템 코드
	 * @param accessResult 접근 가능 여부
	 * @return String
	 */
	@RequestMapping(value = "/moveDownBuildingFloor.do", method = RequestMethod.POST)
	public @ResponseBody String moveDownBuildingFloor(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="MeetingRoom", operationName={"MANAGE"}, resourceName="MeetingRoom")) 
			String nextNodeId, String curNodeId, AccessingResult accessResult) {
		
		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		User user = (User) getRequestAttribute("ikep.user");
		
		String nextSortOrder = buildingFloorService.read(nextNodeId).getSortOrder();
		String curSortOrder = buildingFloorService.read(curNodeId).getSortOrder();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("nextSortOrder", nextSortOrder);
		map.put("nextNodeId", nextNodeId);
		map.put("curSortOrder", curSortOrder);
		map.put("curNodeId", curNodeId);
		map.put("updaterId", user.getUserId());
		map.put("updaterName", user.getUserName());

		buildingFloorService.moveDownBuildingFloor(map);

		return "";
		
	}
	
	/**
	 * 시스템 트리 목록의  드래그 앤 드랍 이동
	 * @param currNodeSystemCode 이동하고자 하는 시스템 코드
	 * @param currParentSystemCode 이동하고자 하는 시스템 코드의 상위 시스템 코드
	 * @param prevNodeSystemCode 트리 순서상 앞에 위치하는 시스템 코드
	 * @param prevParentSystemCode 트리 순서상 앞에 위치하는 시스템 코드의 상위 시스템 코드
	 * @param accessResult 접근 가능 여부
	 */
	@RequestMapping(value = "/moveDndPortalSystem.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateSortOrder(@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Portal", operationName={"MANAGE"}, resourceName="Portal")) 
			@RequestParam("currNodeSystemCode") String currNodeSystemCode, 
			@RequestParam("currParentSystemCode") String currParentSystemCode,
			@RequestParam("prevNodeSystemCode") String prevNodeSystemCode,
			@RequestParam("prevParentSystemCode") String prevParentSystemCode,
			AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		User user = (User) this.getRequestAttribute("ikep.user");
		
		if(currParentSystemCode.equals(prevNodeSystemCode)) {	
			
			BuildingFloor prevPortalSystem  = buildingFloorService.read(prevNodeSystemCode);
			String prevSortOrder = prevPortalSystem.getSortOrder();
			String currPortalSystemSortOrder = String.valueOf((Integer.parseInt(prevSortOrder) + 1));	
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("sortOrder", StringUtils.leftPad(currPortalSystemSortOrder, 13, "0"));
			param.put("systemCode", currNodeSystemCode);
			param.put("parentSystemCode", currParentSystemCode);
			param.put("updaterId", user.getUserId());
			param.put("updaterName", user.getUserName());
			param.put("prevSortOrder", prevSortOrder);
			buildingFloorService.moveDndOneNode(param);
			
		} else if(currParentSystemCode.equals(prevParentSystemCode)) {
			
			BuildingFloor prevPortalSystem  = buildingFloorService.read(prevNodeSystemCode);
			String prevSortOrder = prevPortalSystem.getSortOrder();
			String currPortalSystemSortOrder = String.valueOf((Integer.parseInt(prevSortOrder) + 1));	
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("sortOrder", StringUtils.leftPad(currPortalSystemSortOrder, 13, "0"));
			param.put("systemCode", currNodeSystemCode);
			param.put("parentSystemCode", currParentSystemCode);
			param.put("prevSystemCode", prevNodeSystemCode);
			param.put("updaterId", user.getUserId());
			param.put("updaterName", user.getUserName());
			param.put("prevSortOrder", prevSortOrder);
			
			buildingFloorService.moveDndSameNode(param);
		} else {
			
			BuildingFloor prevPortalSystem  = buildingFloorService.read(prevNodeSystemCode);
			String prevSortOrder = prevPortalSystem.getSortOrder();
			String currPortalSystemSortOrder = String.valueOf((Integer.parseInt(prevSortOrder) + 1));	
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("sortOrder", StringUtils.leftPad(currPortalSystemSortOrder, 13, "0"));
			param.put("systemCode", currNodeSystemCode);
			param.put("parentSystemCode", currParentSystemCode);
			param.put("updaterId", user.getUserId());
			param.put("updaterName", user.getUserName());
			param.put("prevSortOrder", prevSortOrder);
			
			buildingFloorService.moveDndOtherNode(param);
		}
		
	}
	
	@RequestMapping(value = "/checkMeetingRoom.do")
	public @ResponseBody Boolean checkMeetingRoom(String buildingFloorId) {
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		Boolean checkMeetingRoom = false;

		try {

			checkMeetingRoom = buildingFloorService.existsMeetingRoomByFloorId(portal.getPortalId(), buildingFloorId);

		} catch (Exception ex) {
			
			throw new IKEP4AjaxException("", ex);
		}

		return checkMeetingRoom;
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