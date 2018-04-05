/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.usb.web;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.usb.dao.UsbDao;
import com.lgcns.ikep4.lightpack.usb.model.Usb;
import com.lgcns.ikep4.lightpack.usb.model.UsbSearchCondition;
import com.lgcns.ikep4.lightpack.usb.service.UsbService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
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
@RequestMapping(value = "/lightpack/usb")
public class UsbController extends BaseController {
	
	@Autowired
	private UsbService usbService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	UsbDao usbDao;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/usbUseRequestForm.do")
	public ModelAndView usbUseRequestForm() {
		String view = "/lightpack/usb/usbUseRequestForm";

		ModelAndView mav = new ModelAndView(view);
		
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		String teamLeader = usbDao.getTeamLeader(user.getUserId());
		User leader = new User();
		leader = userService.read(teamLeader);
		mav.addObject("leader", leader);
		
		/*Map<String,String> param = new HashMap<String, String>();
		param.put("portalId", portal.getPortalId());
		param.put("userId", user.getUserId());*/
		
		return mav;
	}
	
	@RequestMapping(value = "/usbUseRequestMenuView.do")
	public ModelAndView usbUseRequestMenuView() {

		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/lightpack/usb/usbUseRequestMenuView");
		boolean usbrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "USBADM");
		int usbRole = userDao.getUserRoleCheck(map);
		if(usbRole > 0){
			usbrole = true;
		}
		mav.addObject("usbrole", usbrole);
		return mav;
	}
	
	@RequestMapping(value = "/usbUseRequestMyList.do")
	public ModelAndView usbUseRequestMyList(String startPeriod, String endPeriod, UsbSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/usb/usbUseRequestMyList");
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
				
				Calendar cal1 = Calendar.getInstance(); 
				Calendar cal2 = Calendar.getInstance(); 
				//cal1.setTime(today);
				
				cal1.add(Calendar.MONTH, -1); 
				cal2.add(Calendar.MONTH, +1); 
				
				startDate = cal1.getTime();
				endDate = cal2.getTime();
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
			searchCondition.setPagePerRecord(50);
			SearchResult<Map<String, Object>> searchResult = usbService.myRequestList(searchCondition);

			
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping("/usbUseRequestView.do")
	public ModelAndView usbRequestView(@RequestParam("usbId") String usbId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/usb/usbUseRequestView");
		
		Usb usb = usbService.getUsbUseRequestView(usbId);
		
		mav.addObject("usb", usb);

		return mav;
	}
	
	@RequestMapping("/usbUseRequestMyView.do")
	public ModelAndView usbRequestMyView(@RequestParam("usbId") String usbId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/usb/usbUseRequestMyView");
		
		Usb usb = usbService.getUsbUseRequestView(usbId);
		
		mav.addObject("usb", usb);

		return mav;
	}
	
	@RequestMapping("/usbUseRequestAllView.do")
	public ModelAndView usbRequestAllView(@RequestParam("usbId") String usbId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/usb/usbUseRequestAllView");
		
		Usb usb = usbService.getUsbUseRequestView(usbId);
		
		mav.addObject("usb", usb);

		return mav;
	}
	
	@RequestMapping("/usbApproveUse")
	public ModelAndView usbApproveUse(String startPeriod, String endPeriod, UsbSearchCondition searchCondition, @RequestParam("usbId") String usbId) {
		
		String view = "redirect:/lightpack/usb/usbUseRequestList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			Usb usb = new Usb();
			usb.setApproveUserId(user.getUserId());
			usb.setApproveStatus("A");
			usb.setUsbId(usbId);
			usbService.usbApproveUse(usb);
			
			Usb tempUsb = usbService.getUsbUseRequestView(usbId);
			
			usbService.sendUsbUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempUsb, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping("/usbApproveUseConfirm")
	public ModelAndView usbApproveUseConfirm(String startPeriod, String endPeriod, UsbSearchCondition searchCondition, @RequestParam("usbId") String usbId) {
		
		String view = "redirect:/lightpack/usb/usbUseRequestAllList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			Usb usb = new Usb();
			usb.setApproveUserId(user.getUserId());
			usb.setApproveStatus("C");
			usb.setUsbId(usbId);
			usbService.usbApproveUse(usb);
			
			Usb tempUsb = usbService.getUsbUseRequestView(usbId);
			
			usbService.sendUsbUseRequestMail("cfm", "USB 사용 요청이 승인이 확인되었습니다" , tempUsb, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping("/usbRejectUse")
	public ModelAndView usbRejectUse(String startPeriod, String endPeriod, UsbSearchCondition searchCondition, @RequestParam("usbId") String usbId, String rejectReason) {
		
		String view = "redirect:/lightpack/usb/usbUseRequestList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			Usb usb = new Usb();
			usb.setApproveUserId(user.getUserId());
			usb.setApproveStatus("R");
			usb.setUsbId(usbId);
			usb.setRejectReason(rejectReason);
			usbService.usbApproveUse(usb);
			
			Usb tempUsb = usbService.getUsbUseRequestView(usbId);
			
			usbService.sendUsbUseRequestMail("rej", "USB 사용 요청이 반려되었습니다" , tempUsb, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping(value = "/usbUseRequestList.do")
	public ModelAndView usbUseRequestList(String startPeriod, String endPeriod, UsbSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/usb/usbUseRequestList");
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
				
				Calendar cal1 = Calendar.getInstance(); 
				Calendar cal2 = Calendar.getInstance(); 
				//cal1.setTime(today);
				
				cal1.add(Calendar.MONTH, -1); 
				cal2.add(Calendar.MONTH, +1); 
				
				startDate = cal1.getTime();
				endDate = cal2.getTime();
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
			searchCondition.setPagePerRecord(50);
			SearchResult<Map<String, Object>> searchResult = usbService.requestList(searchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/usbUseRequestAllList.do")
	public ModelAndView usbUseRequestAllList(String startPeriod, String endPeriod, UsbSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/usb/usbUseRequestAllList");
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
				
				Calendar cal1 = Calendar.getInstance(); 
				Calendar cal2 = Calendar.getInstance(); 
				//cal1.setTime(today);
				
				cal1.add(Calendar.MONTH, -1); 
				cal2.add(Calendar.MONTH, +1); 
				
				startDate = cal1.getTime();
				endDate = cal2.getTime();
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
			searchCondition.setPagePerRecord(50);
			SearchResult<Map<String, Object>> searchResult = usbService.requestAllList(searchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	private User getUser() {
		
		return (User) getRequestAttribute("ikep.user");
	}
	
	private Portal getPortal() {
		
		return (Portal) getRequestAttribute("ikep.portal");
	}
	
	@RequestMapping(value = "/usbUseRequest.do")
	public ModelAndView usbUseRequest(Usb usb) {
		String view = "redirect:/lightpack/usb/usbUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		if(usb.getForeverYn().equals("1")){
			usb.setEndDate("9999-01-01");
		}
		
		String id = usbService.usbUseRequest(usb, user);
		
		String teamLeader = usbDao.getTeamLeader(user.getUserId());
		
		if(user.getUserId().equals(teamLeader)){
			Usb tempusb = new Usb();
			tempusb.setApproveUserId(teamLeader);
			tempusb.setApproveStatus("A");
			tempusb.setUsbId(id);
			usbService.usbApproveUse(tempusb);
			
			Usb tempUsb = usbService.getUsbUseRequestView(id);
			
			usbService.sendUsbUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempUsb, user);
		}else{
			usbService.sendUsbUseRequestMail("req", "USB 사용 승인 요청" , usb, user);
		}
		
		return mav;
	}
	
	
	@RequestMapping(value = "/usbUseReqUpdate.do")
	public ModelAndView usbUseReqUpdate(Usb usb) {
		String view = "redirect:/lightpack/usb/usbUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		usbService.usbUseRequestUpdate(usb);
		
		String teamLeader = usbDao.getTeamLeader(user.getUserId());
		
		if(user.getUserId().equals(teamLeader)){
			Usb tempusb = new Usb();
			tempusb.setApproveUserId(teamLeader);
			tempusb.setApproveStatus("A");
			tempusb.setUsbId(usb.getUsbId());
			usbService.usbApproveUse(tempusb);
			
			Usb tempUsb = usbService.getUsbUseRequestView(usb.getUsbId());
			
			usbService.sendUsbUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempUsb, user);
		}else{
			usbService.sendUsbUseRequestMail("req", "USB 사용 승인 요청" , usb, user);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/usbUseRequestDelete.do")
	public ModelAndView usbUseRequestDelete(@RequestParam("usbId") String usbId) {
		String view = "redirect:/lightpack/usb/usbUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Usb usb = new Usb();
		usb.setUsbId(usbId);
		usb.setIsDelete("1");
		usbService.usbUseRequestDelete(usb);
		
		return mav;
	}
	
	@RequestMapping(value = "/usbUseRequestUpdateForm.do")
	public ModelAndView usbUseRequestUpdateForm(@RequestParam("usbId") String usbId) {
		String view = "/lightpack/usb/usbUseRequestUpdateForm";
		ModelAndView mav = new ModelAndView(view);
		
		Usb usb = usbService.getUsbUseRequestView(usbId);
		
		User user = (User) getRequestAttribute("ikep.user");
		String teamLeader = usbDao.getTeamLeader(user.getUserId());
		User leader = new User();
		leader = userService.read(teamLeader);
		mav.addObject("leader", leader);
		mav.addObject("usb", usb);
		
		return mav;
	}
	
}
