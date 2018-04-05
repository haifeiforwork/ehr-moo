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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.meetingroom.service.ApproveService;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/meetingroom")
public class MeetingRoomController extends BaseController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private ApproveService approveService;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@RequestMapping(value = "/main.do")
	public ModelAndView meeting(@RequestParam(value="mode", required=false) String mode) {
		String view = "/lightpack/meetingroom/main";
		if("schedule".equals(mode)) view = "/lightpack/meetingroom/mainPopup";

		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("userId", user.getUserId());
		
		Boolean isMeetingRoomManager = this.isMeetingRoomManager(param);
		Boolean isCarToolManager = this.isCarToolManager(param);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("isMeetingRoomManager", isMeetingRoomManager);
		mav.addObject("isCarToolManager", isCarToolManager);
		return mav;
	}
	
	@RequestMapping(value = "/cartooletcMain.do")
	public ModelAndView cartooletcSchedule(@RequestParam(value="mode", required=false) String mode,@RequestParam(value="mid", required=false) String mid) {
		String view = "/lightpack/meetingroom/cartooletcmain";
		
		if("schedule".equals(mode)) view = "/lightpack/meetingroom/cartooletcmainPopup";
	
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		Boolean isSystemAdmin = this.isSystemAdmin(user);
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("userId", user.getUserId());
		
		Boolean isMeetingRoomManager = this.isMeetingRoomManager(param);
		Boolean isCarToolManager = this.isCarToolManager(param);
		
		mav.addObject("isSystemAdmin", isSystemAdmin);
		mav.addObject("isMeetingRoomManager", isMeetingRoomManager);
		mav.addObject("isCarToolManager", isCarToolManager);
		
		return mav;
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
