package com.lgcns.ikep4.lightpack.planner.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.lightpack.planner.model.Holiday;
import com.lgcns.ikep4.lightpack.planner.service.CalendarService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.Nation;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;

@Controller
@RequestMapping(value = "lightpack/planner/holiday")
public class HolidayController extends BaseController {
	
	@Autowired
	private CalendarService service;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public ModelAndView list(
			@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Planner", operationName={"MANAGE"}, resourceName="Planner")) AccessingResult result) {
		if(!result.isAccess()) {
			throw new IKEP4AuthorizedException(); 
		}
		ModelAndView mav = new ModelAndView("lightpack/planner/holiday/list");
		try {
			List<Holiday> list = service.getHolidayList();
			mav.addObject("itemList", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/editForm")
	public ModelAndView editForm(@RequestParam("holidayId") String holidayId,
			@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Planner", operationName={"MANAGE"}, resourceName="Planner")) AccessingResult result) {
		// TODO: 권한관리는 spring intercept에서 통합관리...
		if(!result.isAccess()) {
			throw new IKEP4AuthorizedException(); 
		}
		ModelAndView mav = new ModelAndView("lightpack/planner/holiday/editForm");
		try {
			Holiday holiday = new Holiday();
			List<Nation> nations = userService.selectNationAll(getUser().getLocaleCode());
			if(StringUtils.isNotBlank(holidayId)) {
				holiday = service.getHoliday(holidayId);
			}
			mav.addObject(holiday);
			mav.addObject("nations", nations);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	@RequestMapping("/create")
	public ModelAndView create(@ModelAttribute("holiday") Holiday holiday,
			@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Planner", operationName={"MANAGE"}, resourceName="Planner")) AccessingResult result) {
		// TODO: 권한관리는 spring intercept에서 통합관리...
		if(!result.isAccess()) {
			throw new IKEP4AuthorizedException(); 
		}
		User user = getUser();
		String portalId = user.getPortalId();
		String hid = holiday.getHolidayId();
		try {
			if(hid == null || "".equals(hid)) {
				service.createHoliday(user, portalId, holiday);
			} else {
				service.updateHoliday(holiday);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("redirect:/lightpack/planner/holiday/list.do");
	}
	
	@RequestMapping("/delete")
	public ModelAndView delte(@RequestParam("hid") String[] hid,
			@IsAccess(@Attribute(type=AccessType.SYSTEM, className="Planner", operationName={"MANAGE"}, resourceName="Planner")) AccessingResult result) {
		// TODO: 권한관리는 spring intercept에서 통합관리...
		if(!result.isAccess()) {
			throw new IKEP4AuthorizedException(); 
		}
		try {
			service.deleteHoliday(hid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("redirect:/lightpack/planner/holiday/list.do");
	}
	
	@RequestMapping("/checkName")
	public @ResponseBody String
	calFeedMySchedule1(@RequestParam("nation") String nation, @RequestParam("holidayName") String holidayName) {
		boolean isExist = service.isExistName(nation, holidayName);
		
		return isExist ? "fail" : "success";
	}

	
	private User getUser() {
		return (User)getRequestAttribute("ikep.user"); 
	}
}
