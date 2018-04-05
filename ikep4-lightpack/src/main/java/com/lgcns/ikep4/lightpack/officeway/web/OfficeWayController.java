/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officeway.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.officeway.dao.OfficeWayDao;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWay;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWaySearchCondition;
import com.lgcns.ikep4.lightpack.officeway.service.OfficeWayService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.role.model.Role;
import com.lgcns.ikep4.support.user.role.model.RoleType;
import com.lgcns.ikep4.support.user.role.service.RoleService;
import com.lgcns.ikep4.util.excel.ExcelModelConstants;
import com.lgcns.ikep4.util.excel.ExcelViewModel;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/lightpack/officeway")
public class OfficeWayController extends BaseController {
	
	@Autowired
	private OfficeWayService officewayService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	OfficeWayDao officewayDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/officewayUseRequestForm.do")
	public ModelAndView officewayUseRequestForm() {
		String view = "/lightpack/officeway/officewayUseRequestForm";

		ModelAndView mav = new ModelAndView(view);
		
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		List<OfficeWay> exceptOfficewayList = officewayService.exceptOfficewayAllList();
		mav.addObject("exceptOfficewayList", exceptOfficewayList);
		
		OfficeWay categoryBoardId = new OfficeWay();
		categoryBoardId.setBoardId("100000000001");
		List<OfficeWay> categoryList1 = null;
		categoryList1 = officewayService.listCategory(categoryBoardId);
		
		categoryBoardId.setBoardId("100000000002");
		List<OfficeWay> categoryList2 = null;
		categoryList2 = officewayService.listCategory(categoryBoardId);
		mav.addObject("categoryList1", categoryList1);
		mav.addObject("categoryList2", categoryList2);
		
		boolean periodCheck = officewayService.periodCheck();
		mav.addObject("periodCheck", periodCheck);
		mav.addObject("today", DateUtil.getTodayDateTime("yyyy.MM"));
		
		
		return mav;
	}
	
	
	
	@RequestMapping(value = "/officewayUseRequestMenuView.do")
	public ModelAndView officewayUseRequestMenuView() {

		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestMenuView");
		
		boolean ofmlRole = false;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userId", user.getUserId());
		map1.put("roleName", "OFML");
		int ofml = userDao.getUserRoleCheck(map1);
		if(ofml > 0){
			ofmlRole = true;
		}
		mav.addObject("ofmlRole", ofmlRole);
		
		boolean ofmrRole = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "OFMR");
		int ofmr = userDao.getUserRoleCheck(map2);
		if(ofmr > 0){
			ofmrRole = true;
		}
		mav.addObject("ofmrRole", ofmrRole);
		
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		boolean oftlRole = officewayService.teamLeaderCheck(map3);
		
		mav.addObject("oftlRole", oftlRole);
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		boolean oftrRole = officewayService.teamManagerCheck(map4);
		
		mav.addObject("oftrRole", oftrRole);
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadExcelOfficeway.do")
	public ModelAndView downloadExcelOfficeway( OfficeWaySearchCondition searchCondition, HttpServletResponse response) {
		ExcelViewModel excel = new ExcelViewModel();  
		try {				
		
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		
		List<OfficeWay> officeWayList = officewayService.teamsRequestDetailList(searchCondition);
		
		String fileName = "사무용품신청_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( officeWayList.size() > 0 ) {
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    //excel.setTitle(fileName+ DateUtil.getTodayDateTime("yyyyMM"));  
		    
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.addColumn("신청일", "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("부서명", "teamId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("신청자", "registerName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("제품번호", "productNo", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("품목", "productName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("신청수량", "amount1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("단위", "unit", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("단가", "price1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("금액", "price2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("사유", "category", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("팀상태", "status1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("담당부서상태", "status2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    		    
		    excel.setDataList(officeWayList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/officewayRequestDelete.do")
	public @ResponseBody String officewayRequestDelete(
			OfficeWay officeway
	) {

		officewayService.officewayRequestDelete(officeway);
		return "success";
	}
	
	@RequestMapping(value = "/downloadExcelOfficewayTeam.do")
	public ModelAndView downloadExcelOfficewayTeam( OfficeWaySearchCondition searchCondition, HttpServletResponse response, String teamId) {
		ExcelViewModel excel = new ExcelViewModel();  
		try {				
			User user = getUser();
		
			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			

			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setUserId(user.getUserId());
			if(teamId != null){
				searchCondition.setTeamId(teamId);
			}else{
				searchCondition.setTeamId(user.getGroupId());
			}
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			if(searchCondition.getStartYear() == null && searchCondition.getStartMonth() == null && searchCondition.getEndYear() == null && searchCondition.getEndMonth() == null){
				searchCondition.setStartYear(DateUtil.getToday("yyyy"));
				searchCondition.setStartMonth(DateUtil.getToday("MM"));
				searchCondition.setEndYear(DateUtil.getToday("yyyy"));
				searchCondition.setEndMonth(DateUtil.getToday("MM"));
				searchCondition.setSearchStartDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
				searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			
			List<OfficeWay> officeWayList = officewayService.teamRequestDetailList(searchCondition);
		
		String fileName = "officeway_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( officeWayList.size() > 0 ) {
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    //excel.setTitle(fileName+ DateUtil.getTodayDateTime("yyyyMM"));  
		    
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.addColumn("신청일", "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("부서명", "teamId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("신청자", "registerName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    //excel.addColumn("분류", "categoryName1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("제품번호", "productNo", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("품목", "productName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("신청수량", "amount1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("단위", "unit", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("단가", "price1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("금액", "price2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("사유", "categoryName2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("팀상태", "status1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("담당부서상태", "status2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    		    
		    excel.setDataList(officeWayList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/downloadExcelOfficewayOtherTeam.do")
	public ModelAndView downloadExcelOfficewayOtherTeam( OfficeWaySearchCondition searchCondition, HttpServletResponse response, String teamId) {
		ExcelViewModel excel = new ExcelViewModel();  
		try {				
			User user = getUser();
		
			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			

			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setUserId(user.getUserId());
			if(teamId != null){
				searchCondition.setTeamId(teamId);
			}else{
				searchCondition.setTeamId(user.getGroupId());
			}
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			if(searchCondition.getStartYear() == null && searchCondition.getStartMonth() == null && searchCondition.getEndYear() == null && searchCondition.getEndMonth() == null){
				searchCondition.setStartYear(DateUtil.getToday("yyyy"));
				searchCondition.setStartMonth(DateUtil.getToday("MM"));
				searchCondition.setEndYear(DateUtil.getToday("yyyy"));
				searchCondition.setEndMonth(DateUtil.getToday("MM"));
				searchCondition.setSearchStartDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
				searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			searchCondition.setStatus1("S");
			List<OfficeWay> officeWayList = officewayService.teamRequestDetailList(searchCondition);
		
		String fileName = "officeway_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( officeWayList.size() > 0 ) {
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    //excel.setTitle(fileName+ DateUtil.getTodayDateTime("yyyyMM"));  
		    
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.addColumn("신청일", "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("부서명", "teamId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("신청자", "registerName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    //excel.addColumn("분류", "categoryName1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("제품번호", "productNo", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("품목", "productName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("신청수량", "amount1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("단위", "unit", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("단가", "price1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("금액", "price2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("사유", "categoryName2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("팀상태", "status1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    excel.addColumn("담당부서상태", "status2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    		    
		    excel.setDataList(officeWayList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/officewayUseRequestMyListUpdateForm.do")
	public ModelAndView officewayUseRequestMyListUpdateForm(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestMyListUpdateForm");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
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
				cal2.add(Calendar.MONTH, +0); 
				
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
			searchCondition.setTeamId(user.getGroupId());
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			searchCondition.setStatus1("T");
			SearchResult<Map<String, Object>> searchResult = officewayService.myRequestList(searchCondition);
			
			boolean ofmlRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OFML");
			int ofml = userDao.getUserRoleCheck(map1);
			if(ofml > 0){
				ofmlRole = true;
			}
			mav.addObject("ofmlRole", ofmlRole);

			boolean ofmrRole = false;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("userId", user.getUserId());
			map2.put("roleName", "OFMR");
			int ofmr = userDao.getUserRoleCheck(map2);
			if(ofmr > 0){
				ofmrRole = true;
			}
			mav.addObject("ofmrRole", ofmrRole);

			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officewayService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officewayService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			
			List<OfficeWay> exceptOfficewayList = officewayService.exceptOfficewayAllList();
			mav.addObject("exceptOfficewayList", exceptOfficewayList);

			OfficeWay categoryBoardId = new OfficeWay();
			categoryBoardId.setBoardId("100000000001");
			List<OfficeWay> categoryList1 = null;
			categoryList1 = officewayService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OfficeWay> categoryList2 = null;
			categoryList2 = officewayService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			boolean periodCheck = officewayService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("today", DateUtil.getTodayDateTime("yyyy.MM"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestAdminUpdateForm.do")
	public ModelAndView officewayUseRequestAdminUpdateForm(String officewayId,OfficeWaySearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestAdminUpdateForm");

		try {
			OfficeWay officeway = officewayService.getOfficeWayUseRequestView(officewayId);
			OfficeWay categoryBoardId = new OfficeWay();
			categoryBoardId.setBoardId("100000000001");
			List<OfficeWay> categoryList1 = null;
			categoryList1 = officewayService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OfficeWay> categoryList2 = null;
			categoryList2 = officewayService.listCategory(categoryBoardId);
			
			String regDt = officeway.getRegistDate().substring(0, 7).replaceAll("-", ".");
			
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			mav.addObject("officeway", officeway);
			mav.addObject("regDt", regDt);
			mav.addObject("searchCondition", searchCondition);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestMyList.do")
	public ModelAndView officewayUseRequestMyList(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestMyList");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
			Date today = null;
			Date startDate = null;
			Date endDate = null;
			String tempMonth = "";
			if(StringUtil.isEmpty(startPeriod) || StringUtil.isEmpty(endPeriod)) {
				
				Date clientNow = timeZoneSupportService.convertTimeZone();
				today = DateUtils.truncate(clientNow, Calendar.DATE);
				
				Calendar cal1 = Calendar.getInstance(); 
				Calendar cal2 = Calendar.getInstance(); 
				//cal1.setTime(today);
				
				cal1.add(Calendar.MONTH, -1); 
				cal2.add(Calendar.MONTH, +0); 
				
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
			searchCondition.setTeamId(user.getGroupId());
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			if(searchCondition.getStartYear() == null && searchCondition.getStartMonth() == null && searchCondition.getEndYear() == null && searchCondition.getEndMonth() == null){
				
				searchCondition.setStartYear(DateUtil.getPrevDate(DateUtil.getToday("yyyyMMdd"), 180, "yyyy"));
				searchCondition.setStartMonth(DateUtil.getPrevDate(DateUtil.getToday("yyyyMMdd"), 180, "MM"));
				//searchCondition.setStartMonth(DateUtil.getPrevMonthDate(DateUtil.getToday("yyyyMMdd"), 6, "MM"));
				searchCondition.setEndYear(DateUtil.getToday("yyyy"));
				searchCondition.setEndMonth(DateUtil.getToday("MM"));
				searchCondition.setSearchStartDate(DateUtil.getPrevDate(DateUtil.getToday("yyyyMMdd"), 180, "yyyy")+DateUtil.getPrevDate(DateUtil.getToday("yyyyMMdd"), 180, "MM"));
				searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			
			searchCondition.setPagePerRecord(50);
			
			SearchResult<Map<String, Object>> searchResult = officewayService.myRequestList(searchCondition);
			
			OfficeWay periodInfo = officewayService.periodInfo();
			
			int price1 = officewayService.selectRequestUserPrice(searchCondition);
			int price2 = officewayService.selectRequestTeamPrice(searchCondition);
			int price3 = officewayService.selectRequestMyPrice1(searchCondition);
			int price4 = officewayService.selectRequestMyPrice2(searchCondition);
			
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officewayService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			mav.addObject("price1", price1);
			mav.addObject("price2", price2);
			mav.addObject("price3", price3);
			mav.addObject("price4", price4);
			mav.addObject("periodInfo", periodInfo);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestTeamList.do")
	public ModelAndView officewayUseRequestTeamList(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition, String teamId, String viewMonth) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestTeamList");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
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
				cal2.add(Calendar.MONTH, +0); 
				
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
			
			if(teamId != null){
				searchCondition.setTeamId(teamId);
			}else{
				searchCondition.setTeamId(user.getGroupId());
			}
			
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			if(searchCondition.getStartYear() == null && searchCondition.getStartMonth() == null && searchCondition.getEndYear() == null && searchCondition.getEndMonth() == null){
				if(viewMonth != null){
					searchCondition.setSearchStartDate(viewMonth);
					searchCondition.setSearchEndDate(viewMonth);
					searchCondition.setStartYear(viewMonth.substring(0, 4));
					searchCondition.setStartMonth(viewMonth.substring(4, 6));
					searchCondition.setEndYear(viewMonth.substring(0, 4));
					searchCondition.setEndMonth(viewMonth.substring(4, 6));
					searchCondition.setSearchStatus("MA");
				}else{
					searchCondition.setStartYear(DateUtil.getToday("yyyy"));
					searchCondition.setStartMonth(DateUtil.getToday("MM"));
					searchCondition.setEndYear(DateUtil.getToday("yyyy"));
					searchCondition.setEndMonth(DateUtil.getToday("MM"));
					searchCondition.setSearchStartDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
					searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
				}
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			searchCondition.setPagePerRecord(50);
			/*boolean ofmlRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OFML");
			int ofml = userDao.getUserRoleCheck(map1);
			if(ofml > 0){
				ofmlRole = true;
			}
			mav.addObject("ofmlRole", ofmlRole);

			boolean ofmrRole = false;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("userId", user.getUserId());
			map2.put("roleName", "OFMR");
			int ofmr = userDao.getUserRoleCheck(map2);
			if(ofmr > 0){
				ofmrRole = true;
			}
			mav.addObject("ofmrRole", ofmrRole);*/

			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officewayService.teamLeaderCheck(map3);
			if(oftlRole){
				searchCondition.setAuth("TC");
			}
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officewayService.teamManagerCheck(map4);
			if(oftrRole){
				searchCondition.setAuth("TR");
			}
			mav.addObject("oftrRole", oftrRole);
			List <OfficeWay> officewayUseTeamList = null;
			if(oftlRole || oftrRole){
				officewayUseTeamList = officewayService.officewayUseTeamList(user.getUserId());
				searchCondition.setUserId(user.getUserId());
				if(teamId != null){
					searchCondition.setTeamId(teamId);
				}else{
					searchCondition.setTeamId("");
				}
			}
			SearchResult<Map<String, Object>> searchResult = officewayService.teamRequestList(searchCondition);

			int price4 = officewayService.teamRequestPrice1(searchCondition);
			int price5 = officewayService.teamRequestPrice2(searchCondition);
			
			
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officewayService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			mav.addObject("price4", price4);
			mav.addObject("price5", price5);
			mav.addObject("teamId", searchCondition.getTeamId());
			mav.addObject("officewayUseTeamList", officewayUseTeamList);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestTeamListPayment.do")
	public ModelAndView officewayUseRequestTeamListPayment(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestTeamListPayment");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
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
				cal2.add(Calendar.MONTH, +0); 
				
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
			if(teamId != null){
				searchCondition.setTeamId(teamId);
			}else{
				searchCondition.setTeamId(user.getGroupId());
			}
			
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			if(searchCondition.getStartYear() == null && searchCondition.getStartMonth() == null && searchCondition.getEndYear() == null && searchCondition.getEndMonth() == null){
				searchCondition.setStartYear(DateUtil.getToday("yyyy"));
				searchCondition.setStartMonth(DateUtil.getToday("MM"));
				searchCondition.setEndYear(DateUtil.getToday("yyyy"));
				searchCondition.setEndMonth(DateUtil.getToday("MM"));
				searchCondition.setSearchStartDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
				searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			
			/*boolean ofmlRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OFML");
			int ofml = userDao.getUserRoleCheck(map1);
			if(ofml > 0){
				ofmlRole = true;
			}
			mav.addObject("ofmlRole", ofmlRole);

			boolean ofmrRole = false;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("userId", user.getUserId());
			map2.put("roleName", "OFMR");
			int ofmr = userDao.getUserRoleCheck(map2);
			if(ofmr > 0){
				ofmrRole = true;
			}
			mav.addObject("ofmrRole", ofmrRole);*/
			
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officewayService.teamLeaderCheck(map3);
			if(oftlRole){
				searchCondition.setSearchStatus("TC");
				searchCondition.setUserId(user.getUserId());
				searchCondition.setAuth("TC");
				if(teamId != null){
					searchCondition.setTeamId(teamId);
				}else{
					searchCondition.setTeamId("");
				}
			}
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officewayService.teamManagerCheck(map4);
			if(oftrRole){
				searchCondition.setSearchStatus("TS");
				searchCondition.setUserId(user.getUserId());
				searchCondition.setAuth("TR");
				if(teamId != null){
					searchCondition.setTeamId(teamId);
				}else{
					searchCondition.setTeamId("");
				}
			}
			mav.addObject("oftrRole", oftrRole);

			searchCondition.setPagePerRecord(50);
			
			SearchResult<Map<String, Object>> searchResult = officewayService.teamRequestList(searchCondition);

			//int price4 = officewayService.teamRequestPrice1(searchCondition);
			int price5 = officewayService.teamRequestPrice2(searchCondition);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officewayService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			//mav.addObject("price4", price4);
			mav.addObject("price5", price5);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestOtherTeamList.do")
	public ModelAndView officewayUseRequestOtherTeamList(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition, String teamId, String viewMonth) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestOtherTeamList");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
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
				cal2.add(Calendar.MONTH, +0); 
				
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
			//searchCondition.setUserId(user.getUserId());
			if(teamId != null){
				searchCondition.setTeamId(teamId);
			}
			
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			searchCondition.setStatus1("S");
			
			if(searchCondition.getStartYear() == null && searchCondition.getStartMonth() == null && searchCondition.getEndYear() == null && searchCondition.getEndMonth() == null){
				
				if(viewMonth != null){
					searchCondition.setSearchStartDate(viewMonth);
					searchCondition.setSearchEndDate(viewMonth);
					searchCondition.setStartYear(viewMonth.substring(0, 4));
					searchCondition.setStartMonth(viewMonth.substring(4, 6));
					searchCondition.setEndYear(viewMonth.substring(0, 4));
					searchCondition.setEndMonth(viewMonth.substring(4, 6));
					searchCondition.setSearchStatus("MA");
				}else{
					searchCondition.setStartYear(DateUtil.getToday("yyyy"));
					searchCondition.setStartMonth(DateUtil.getToday("MM"));
					searchCondition.setEndYear(DateUtil.getToday("yyyy"));
					searchCondition.setEndMonth(DateUtil.getToday("MM"));
					searchCondition.setSearchStartDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
					searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
				}
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			
			searchCondition.setPagePerRecord(50);
			
			SearchResult<Map<String, Object>> searchResult = officewayService.teamRequestList(searchCondition);
			
			int price4 = officewayService.teamRequestPrice1(searchCondition);
			int price5 = officewayService.teamRequestPrice2(searchCondition);
			
			List <OfficeWay> officewayUseTeamList = officewayService.officewayUseTeamListAll();

			boolean ofmlRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OFML");
			int ofml = userDao.getUserRoleCheck(map1);
			if(ofml > 0){
				ofmlRole = true;
			}
			mav.addObject("ofmlRole", ofmlRole);

			boolean ofmrRole = false;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("userId", user.getUserId());
			map2.put("roleName", "OFMR");
			int ofmr = userDao.getUserRoleCheck(map2);
			if(ofmr > 0){
				ofmrRole = true;
			}
			mav.addObject("ofmrRole", ofmrRole);

			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officewayService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officewayService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officewayService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("officewayUseTeamList", officewayUseTeamList);
			mav.addObject("teamId", teamId);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			mav.addObject("price4", price4);
			mav.addObject("price5", price5);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	
	@RequestMapping(value = "/officewayUseRequestStatistics.do")
	public ModelAndView officewayUseRequestStatistics(OfficeWay officeway) {
		
		String year = DateUtil.getToday("yyyy");
		
		//OfficeWay period = officewayService.getPeriod();
		if(officeway.getYear() == null){
			officeway.setYear(year);
		}else{
			year = officeway.getYear();
		}
		List<OfficeWay> statisticsList1 = officewayService.getStatisticsList1(officeway);
		List<OfficeWay> statisticsList2 = officewayService.getStatisticsList2(officeway);
		List<OfficeWay> statisticsList3 = officewayService.getStatisticsList3(officeway);
		
		int price6 = officewayService.getTotalPrice(officeway);
		
		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestStatistics");
		
		List <OfficeWay> officewayUseTeamList = officewayService.officewayUseTeamListAll();
		
		mav.addObject("statisticsList1", statisticsList1);
		mav.addObject("statisticsList2", statisticsList2);
		mav.addObject("statisticsList3", statisticsList3);
		mav.addObject("price6", price6);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		mav.addObject("officewayUseTeamList", officewayUseTeamList);
		mav.addObject("teamId", officeway.getTeamId());
		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestTeamStatistics.do")
	public ModelAndView officewayUseRequestTeamStatistics(OfficeWay officeway) {
		
		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestTeamStatistics");
		String year = DateUtil.getToday("yyyy");
		User user = getUser();
		//OfficeWay period = officewayService.getPeriod();
		if(officeway.getYear() == null){
			officeway.setYear(year);
		}else{
			year = officeway.getYear();
		}
		if(officeway.getTeamId() == null){
			officeway.setTeamId(user.getGroupId());
		}
		List<OfficeWay> statisticsList1 = officewayService.getStatisticsList1(officeway);
		List<OfficeWay> statisticsList2 = officewayService.getStatisticsList2(officeway);
		List<OfficeWay> statisticsList3 = officewayService.getStatisticsList3(officeway);
		
		int price6 = officewayService.getTotalPrice(officeway);
		
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		boolean oftlRole = officewayService.teamLeaderCheck(map3);
		mav.addObject("oftlRole", oftlRole);
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		boolean oftrRole = officewayService.teamManagerCheck(map4);
		mav.addObject("oftrRole", oftrRole);
		List <OfficeWay> officewayUseTeamList = null;
		if(oftlRole || oftrRole){
			officewayUseTeamList = officewayService.officewayUseTeamList(user.getUserId());
		}
		
		mav.addObject("statisticsList1", statisticsList1);
		mav.addObject("statisticsList2", statisticsList2);
		mav.addObject("statisticsList3", statisticsList3);
		mav.addObject("price6", price6);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		mav.addObject("officewayUseTeamList", officewayUseTeamList);
		mav.addObject("teamId", officeway.getTeamId());
		mav.addObject("oftrRole", oftrRole);
		mav.addObject("oftlRole", oftlRole);
		return mav;
	}
	
	@RequestMapping(value = "/officewayTeamAuthSave.do")
	public ModelAndView officewayTeamAuthSave(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/OfficewayTeamAuthList.do";
		ModelAndView mav = new ModelAndView(view);
		
		officewayService.officewayTeamAuthSave(officeway);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayTeamAuthEditForm.do")
	public ModelAndView officewayTeamAuthEditForm(String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayTeamAuthEditForm");
		
		OfficeWay teamAuthInfo = officewayService.getOfficewayTeamAuthInfo(teamId);
		mav.addObject("teamAuthInfo", teamAuthInfo);
		return mav;
	}
	@RequestMapping(value = "/officewayUseRequestOtherTeamListPayment.do")
	public ModelAndView officewayUseRequestOtherTeamListPayment(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestOtherTeamListPayment");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
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
				cal2.add(Calendar.MONTH, +0); 
				
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
			if(teamId != null){
				searchCondition.setTeamId(teamId);
			}
			
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			searchCondition.setStatus1("S");
			
			if(searchCondition.getStartYear() == null && searchCondition.getStartMonth() == null && searchCondition.getEndYear() == null && searchCondition.getEndMonth() == null){
				searchCondition.setStartYear(DateUtil.getToday("yyyy"));
				searchCondition.setStartMonth(DateUtil.getToday("MM"));
				searchCondition.setEndYear(DateUtil.getToday("yyyy"));
				searchCondition.setEndMonth(DateUtil.getToday("MM"));
				searchCondition.setSearchStartDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
				searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			
			searchCondition.setPagePerRecord(50);
			
			boolean ofmlRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OFML");
			int ofml = userDao.getUserRoleCheck(map1);
			if(ofml > 0){
				ofmlRole = true;
				searchCondition.setSearchStatus("MC");
			}
			mav.addObject("ofmlRole", ofmlRole);

			boolean ofmrRole = false;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("userId", user.getUserId());
			map2.put("roleName", "OFMR");
			int ofmr = userDao.getUserRoleCheck(map2);
			if(ofmr > 0){
				ofmrRole = true;
				searchCondition.setSearchStatus("TA");
			}
			mav.addObject("ofmrRole", ofmrRole);
			
			SearchResult<Map<String, Object>> searchResult = officewayService.teamRequestList(searchCondition);
			
			List <OfficeWay> officewayUseTeamList = officewayService.officewayUseTeamListAll();
			
			int price4 = officewayService.teamRequestPrice1(searchCondition);
			int price5 = officewayService.teamRequestPrice2(searchCondition);
			

			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officewayService.teamLeaderCheck(map3);

			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officewayService.teamManagerCheck(map4);

			mav.addObject("oftrRole", oftrRole);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officewayService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("officewayUseTeamList", officewayUseTeamList);
			mav.addObject("teamId", teamId);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			mav.addObject("price4", price4);
			mav.addObject("price5", price5);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestTeamListUpdateForm.do")
	public ModelAndView officewayUseRequestTeamListUpdateForm(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestTeamListUpdateForm");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			

			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());
			searchCondition.setUserId(user.getUserId());
			searchCondition.setTeamId(user.getGroupId());
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			searchCondition.setStatus1("S");
			
			boolean ofmlRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OFML");
			int ofml = userDao.getUserRoleCheck(map1);
			if(ofml > 0){
				ofmlRole = true;
			}
			mav.addObject("ofmlRole", ofmlRole);

			boolean ofmrRole = false;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("userId", user.getUserId());
			map2.put("roleName", "OFMR");
			int ofmr = userDao.getUserRoleCheck(map2);
			if(ofmr > 0){
				ofmrRole = true;
			}
			mav.addObject("ofmrRole", ofmrRole);

			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officewayService.teamLeaderCheck(map3);
			if(oftlRole){
				searchCondition.setAuth("TC");
				searchCondition.setTeamId("");
			}
			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officewayService.teamManagerCheck(map4);
			if(oftrRole){
				searchCondition.setAuth("TR");
				searchCondition.setTeamId("");
			}
			mav.addObject("oftrRole", oftrRole);
			
			SearchResult<Map<String, Object>> searchResult = officewayService.teamRequestList(searchCondition);

			
			List<OfficeWay> exceptOfficewayList = officewayService.exceptOfficewayAllList();
			OfficeWay categoryBoardId = new OfficeWay();
			categoryBoardId.setBoardId("100000000001");
			List<OfficeWay> categoryList1 = null;
			categoryList1 = officewayService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OfficeWay> categoryList2 = null;
			categoryList2 = officewayService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			mav.addObject("exceptOfficewayList", exceptOfficewayList);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("today", DateUtil.getTodayDateTime("yyyy.MM"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestOtherTeamListUpdateForm.do")
	public ModelAndView officewayUseRequestOtherTeamListUpdateForm(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestOtherTeamListUpdateForm");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			

			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setPortalId(portal.getPortalId());
			searchCondition.setUserId(user.getUserId());
			searchCondition.setTeamId(teamId);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			searchCondition.setStatus1("S");
			searchCondition.setSearchStatus("TA");
			SearchResult<Map<String, Object>> searchResult = officewayService.teamRequestList(searchCondition);

			boolean ofmlRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OFML");
			int ofml = userDao.getUserRoleCheck(map1);
			if(ofml > 0){
				ofmlRole = true;
			}
			mav.addObject("ofmlRole", ofmlRole);

			boolean ofmrRole = false;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("userId", user.getUserId());
			map2.put("roleName", "OFMR");
			int ofmr = userDao.getUserRoleCheck(map2);
			if(ofmr > 0){
				ofmrRole = true;
			}
			mav.addObject("ofmrRole", ofmrRole);

			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officewayService.teamLeaderCheck(map3);

			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officewayService.teamManagerCheck(map4);

			mav.addObject("oftrRole", oftrRole);
			List<OfficeWay> exceptOfficewayList = officewayService.exceptOfficewayAllList();
			
			OfficeWay categoryBoardId = new OfficeWay();
			categoryBoardId.setBoardId("100000000001");
			List<OfficeWay> categoryList1 = null;
			categoryList1 = officewayService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OfficeWay> categoryList2 = null;
			categoryList2 = officewayService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			mav.addObject("exceptOfficewayList", exceptOfficewayList);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("teamId", teamId);
			mav.addObject("today", DateUtil.getTodayDateTime("yyyy.MM"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestTeamsList.do")
	public ModelAndView officewayUseRequestTeamsList(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestTeamsList");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
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
				cal2.add(Calendar.MONTH, +0); 
				
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
			searchCondition.setTeamId(user.getGroupId());
			searchCondition.setStartDate(startDate);
			searchCondition.setEndDate(endDate);
			
			if(StringUtil.isEmpty(searchCondition.getSearchType())) {
				
				searchCondition.setSearchType("ALL");
			}
			
			if(searchCondition.getStartYear() == null && searchCondition.getStartMonth() == null && searchCondition.getEndYear() == null && searchCondition.getEndMonth() == null){
				searchCondition.setStartYear(DateUtil.getToday("yyyy"));
				searchCondition.setStartMonth(DateUtil.getToday("MM"));
				searchCondition.setEndYear(DateUtil.getToday("yyyy"));
				searchCondition.setEndMonth(DateUtil.getToday("MM"));
				searchCondition.setSearchStartDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
				searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			
			boolean ofmlRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OFML");
			int ofml = userDao.getUserRoleCheck(map1);
			if(ofml > 0){
				ofmlRole = true;
				searchCondition.setSearchStatus("MC");
			}
			mav.addObject("ofmlRole", ofmlRole);

			boolean ofmrRole = false;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("userId", user.getUserId());
			map2.put("roleName", "OFMR");
			int ofmr = userDao.getUserRoleCheck(map2);
			if(ofmr > 0){
				ofmrRole = true;
				searchCondition.setSearchStatus("TA");
			}
			mav.addObject("ofmrRole", ofmrRole);

			/*Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officewayService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officewayService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			*/
			
			
			SearchResult<Map<String, Object>> searchResult = officewayService.teamsRequestList(searchCondition);
			
			int price3 = officewayService.teamsRequestPrice(searchCondition);
			
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("price3", price3);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping("/officewayUseRequestView.do")
	public ModelAndView officewayRequestView(@RequestParam("officewayId") String officewayId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestView");
		
		OfficeWay officeway = officewayService.getOfficeWayUseRequestView(officewayId);
		
		mav.addObject("officeway", officeway);

		return mav;
	}
	
	@RequestMapping("/officewayUseRequestMyView.do")
	public ModelAndView officewayRequestMyView(@RequestParam("officewayId") String officewayId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestMyView");
		
		OfficeWay officeway = officewayService.getOfficeWayUseRequestView(officewayId);
		
		mav.addObject("officeway", officeway);

		return mav;
	}
	
	@RequestMapping("/officewayUseRequestAllView.do")
	public ModelAndView officewayRequestAllView(@RequestParam("officewayId") String officewayId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestAllView");
		
		OfficeWay officeway = officewayService.getOfficeWayUseRequestView(officewayId);
		
		mav.addObject("officeway", officeway);

		return mav;
	}
	
	@RequestMapping("/officewayApproveUse")
	public ModelAndView officewayApproveUse(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition, @RequestParam("officewayId") String officewayId) {
		
		String view = "redirect:/lightpack/officeway/officewayUseRequestList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OfficeWay officeway = new OfficeWay();
			officeway.setApproveUserId(user.getUserId());
			officeway.setApproveStatus("A");
			officeway.setOfficeWayId(officewayId);
			officewayService.officewayApproveUse(officeway);
			
			OfficeWay tempOfficeWay = officewayService.getOfficeWayUseRequestView(officewayId);
			
			officewayService.sendOfficeWayUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempOfficeWay, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping("/officewayApproveUseConfirm")
	public ModelAndView officewayApproveUseConfirm(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition, @RequestParam("officewayId") String officewayId) {
		
		String view = "redirect:/lightpack/officeway/officewayUseRequestAllList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OfficeWay officeway = new OfficeWay();
			officeway.setApproveUserId(user.getUserId());
			officeway.setApproveStatus("C");
			officeway.setOfficeWayId(officewayId);
			officewayService.officewayApproveUse(officeway);
			
			OfficeWay tempOfficeWay = officewayService.getOfficeWayUseRequestView(officewayId);
			
			officewayService.sendOfficeWayUseRequestMail("cfm", "USB 사용 요청이 승인이 확인되었습니다" , tempOfficeWay, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping("/officewayRejectUse")
	public ModelAndView officewayRejectUse(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition, @RequestParam("officewayId") String officewayId, String rejectReason) {
		
		String view = "redirect:/lightpack/officeway/officewayUseRequestList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OfficeWay officeway = new OfficeWay();
			officeway.setApproveUserId(user.getUserId());
			officeway.setApproveStatus("R");
			officeway.setOfficeWayId(officewayId);
			officeway.setRejectReason(rejectReason);
			officewayService.officewayApproveUse(officeway);
			
			OfficeWay tempOfficeWay = officewayService.getOfficeWayUseRequestView(officewayId);
			
			officewayService.sendOfficeWayUseRequestMail("rej", "USB 사용 요청이 반려되었습니다" , tempOfficeWay, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestList.do")
	public ModelAndView officewayUseRequestList(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestList");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
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
				cal2.add(Calendar.MONTH, +0); 
				
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
			
			SearchResult<Map<String, Object>> searchResult = officewayService.requestList(searchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestAllList.do")
	public ModelAndView officewayUseRequestAllList(String startPeriod, String endPeriod, OfficeWaySearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayUseRequestAllList");
		User user = getUser();
		Portal portal = getPortal();

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				
				searchCondition.setSortType("ASC");
			}*/
			
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
				cal2.add(Calendar.MONTH, +0); 
				
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
			
			SearchResult<Map<String, Object>> searchResult = officewayService.requestAllList(searchCondition);

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
	
	@RequestMapping(value = "/officewayUseRequest.do")
	public ModelAndView officewayUseRequest(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayUseRequest(officeway, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayCheckBoxUseRequest.do")
	public ModelAndView officewayCheckBoxUseRequest(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayCheckBoxUseRequest(officeway, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayTeamCheckBoxUseRequest.do")
	public ModelAndView officewayTeamCheckBoxUseRequest(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayCheckBoxUseRequest(officeway, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayManageCheckBoxUseRequest.do")
	public ModelAndView officewayManageCheckBoxUseRequest(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestOtherTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayManageCheckBoxUseRequest(officeway, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayCheckBoxGroupRequest.do")
	public ModelAndView officewayCheckBoxGroupRequest(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestTeamsList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayCheckBoxGroupRequest(officeway, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/savePeriod.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String savePeriod(OfficeWay officeway) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		officeway.setUpdaterId(user.getUserId());
		officeway.setUpdaterName(user.getUserName());
		
		officewayService.savePeriod(officeway);
		
		return "success";
	}
	
	@RequestMapping(value = "/editPeriodForm.do")
	public ModelAndView editPeriodForm(OfficeWay officeway) {
		
		String year = DateUtil.getToday("yyyy");
		
		//OfficeWay period = officewayService.getPeriod();
		if(officeway.getYear() != null){
			year = officeway.getYear();
		}
		List<OfficeWay> periodList = officewayService.getPeriodList(year);
		
		ModelAndView mav = new ModelAndView("/lightpack/officeway/editPeriodForm");
		
		mav.addObject("periodList", periodList);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		return mav;
	}
	
	@RequestMapping(value = "/OfficewayTeamAuthList.do")
	public ModelAndView OfficewayTeamAuthList(OfficeWay officeway) {
		
		
		List<OfficeWay> officewayTeamAuthList = officewayService.getOfficewayTeamAuthList();
		
		ModelAndView mav = new ModelAndView("/lightpack/officeway/officewayTeamAuthList");
		
		mav.addObject("officewayTeamAuthList", officewayTeamAuthList);
		return mav;
	}
	
	
	@RequestMapping(value = "/roleList.do")
	public ModelAndView roleList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AdminSearchCondition searchCondition,
			AccessingResult accessResult) {

		ModelAndView mav = new ModelAndView("/lightpack/officeway/roleList");

		try {
			User sessionUser = (User) getRequestAttribute("ikep.user");

			if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("ROLE_ID");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}

			searchCondition.setFieldName("roleName");
			searchCondition.setUserLocaleCode(sessionUser.getLocaleCode());
			searchCondition.setPortalId("P000001");
			searchCondition.setSearchColumn("title");
			searchCondition.setSearchWord("OF");

			SearchResult<Role> searchResult = roleService.list(searchCondition);
			
			searchCondition.setSearchWord("");

			List<RoleType> roleTypeList = roleService.selectTypeId(sessionUser.getLocaleCode());

			BoardCode boardCode = new BoardCode();

			mav.addObject("userLocaleCode", sessionUser.getLocaleCode());
			mav.addObject("searchResult", searchResult);
			mav.addObject("roleTypeList", roleTypeList);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("boardCode", boardCode);
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/roleOnSubmit.do")
	public String roleOnSubmit(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @Valid Role role,
			BindingResult result, SessionStatus status, HttpServletRequest request, AccessingResult accessResult) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		/*if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}

		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}*/

		User sessionUser = (User) getRequestAttribute("ikep.user");
		boolean isCodeExist = roleService.exists(role.getRoleId());

		// 그룹의 등록/수정을 위해 필요한 기본 정보 추가
		List<Group> tempGroupList = role.getGroupList();

		if (tempGroupList != null) {
			for (int i = 0; i < tempGroupList.size(); i++) {
				Group tempGroup = tempGroupList.get(i);
				tempGroup.setRoleId(role.getRoleId());
				tempGroup.setRegisterId(sessionUser.getUserId());
				tempGroup.setRegisterName(sessionUser.getUserName());
				tempGroup.setUpdaterId(sessionUser.getUserId());
				tempGroup.setUpdaterName(sessionUser.getUserName());
			}
		}

		// 사용자의 등록/수정을 위해 필요한 기본 정보 추가
		List<User> tempUserList = role.getUserList();

		if (tempUserList != null) {
			for (int i = 0; i < tempUserList.size(); i++) {
				User tempUser = tempUserList.get(i);
				tempUser.setRoleId(role.getRoleId());
				tempUser.setRegisterId(sessionUser.getUserId());
				tempUser.setRegisterName(sessionUser.getUserName());
				tempUser.setUpdaterId(sessionUser.getUserId());
				tempUser.setUpdaterName(sessionUser.getUserName());
			}
		}

		if (isCodeExist) {

			role.setGroupList(tempGroupList);
			role.setUserList(tempUserList);
			role.setPortalId("P000001");
			role.setUpdaterId(sessionUser.getUserId());
			role.setUpdaterName(sessionUser.getUserName());

			roleService.update(role);
		} else {

			role.setGroupList(tempGroupList);
			role.setUserList(tempUserList);
			role.setPortalId("P000001");
			role.setRegisterId(sessionUser.getUserId());
			role.setRegisterName(sessionUser.getUserName());
			role.setUpdaterId(sessionUser.getUserId());
			role.setUpdaterName(sessionUser.getUserName());

			roleService.create(role);
		}

		status.setComplete();

		return "redirect:/lightpack/officeway/roleList.do";
	}
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/roleForm.do", method = RequestMethod.POST)
	public ModelAndView roleForm(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam(value = "roleId") String roleId,
			HttpServletRequest request, AccessingResult accessResult) {


		ModelAndView mav = new ModelAndView();

		Role role = null;

		String id = request.getParameter("roleId");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		String userLocaleCode = sessionUser.getLocaleCode();
		List<RoleType> roleTypeList = roleService.selectTypeId(userLocaleCode);

		if (id != null && !"".equals(id)) {
			role = roleService.read(id);

			List userList = role.getUserList();

			// 해당 역할이 부여된 그룹의 리스트를 가져온다.
			List<Role> roleGroupList = roleService.selectRoleGroupList(id);

			// 해당 역할이 부여된 사용자의 리스트를 가져온다.
			List<Map<String, String>> roleUserList = roleService.selectRoleUserList(id);
			
			List<Role> roleGroupList1 = roleService.selectByNameRoleGroupList("OFML");
			List<Map<String, String>> roleUserList1 = roleService.selectByNameRoleUserList("OFML");
			
			List<Role> roleGroupList2 = roleService.selectByNameRoleGroupList("OFMR");
			List<Map<String, String>> roleUserList2 = roleService.selectByNameRoleUserList("OFMR");

			role.setCodeCheck("modify");

			mav.addObject("currRoleId", role.getRoleId());
			mav.addObject("userList", userList);
			mav.addObject("roleGroupList", roleGroupList);
			mav.addObject("roleUserList", roleUserList);
			mav.addObject("roleGroupList1", roleGroupList1);
			mav.addObject("roleUserList1", roleUserList1);
			mav.addObject("roleGroupList2", roleGroupList2);
			mav.addObject("roleUserList2", roleUserList2);
		} else {
			role = new Role();
			role.setCodeCheck("new");
		}

		mav.addObject("userLocaleCode", userLocaleCode);
		mav.addObject("role", role);
		mav.addObject("roleTypeList", roleTypeList);

		mav.setViewName("/lightpack/officeway/roleForm");

		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestUpdate.do")
	public ModelAndView officewayUseRequestUpdate(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayUseRequestUpdate(officeway, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestAdminUpdate.do")
	public ModelAndView officewayUseRequestAdminUpdate(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestOtherTeamList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayUseRequestAdminUpdate(officeway, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestTeamUpdate.do")
	public ModelAndView officewayUseRequestTeamUpdate(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayUseRequestUpdate(officeway, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestOtherTeamUpdate.do")
	public ModelAndView officewayUseRequestOtherTeamUpdate(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestOtherTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayUseRequestUpdate(officeway, user);
		
		mav.addObject("teamId", officeway.getTeamId());
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayUseReqUpdate.do")
	public ModelAndView officewayUseReqUpdate(OfficeWay officeway) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officewayService.officewayUseRequestUpdate(officeway);
		
		String teamLeader = officewayDao.getTeamLeader(user.getUserId());
		
		if(user.getUserId().equals(teamLeader)){
			OfficeWay tempofficeway = new OfficeWay();
			tempofficeway.setApproveUserId(teamLeader);
			tempofficeway.setApproveStatus("A");
			tempofficeway.setOfficeWayId(officeway.getOfficeWayId());
			officewayService.officewayApproveUse(tempofficeway);
			
			OfficeWay tempOfficeWay = officewayService.getOfficeWayUseRequestView(officeway.getOfficeWayId());
			
			officewayService.sendOfficeWayUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempOfficeWay, user);
		}else{
			officewayService.sendOfficeWayUseRequestMail("req", "USB 사용 승인 요청" , officeway, user);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestDelete.do")
	public ModelAndView officewayUseRequestDelete(@RequestParam("officewayId") String officewayId) {
		String view = "redirect:/lightpack/officeway/officewayUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		OfficeWay officeway = new OfficeWay();
		officeway.setOfficeWayId(officewayId);
		officeway.setIsDelete("1");
		officewayService.officewayUseRequestDelete(officeway);
		
		return mav;
	}
	
	@RequestMapping(value = "/officewayUseRequestUpdateForm.do")
	public ModelAndView officewayUseRequestUpdateForm(@RequestParam("officewayId") String officewayId) {
		String view = "/lightpack/officeway/officewayUseRequestUpdateForm";
		ModelAndView mav = new ModelAndView(view);
		
		OfficeWay officeway = officewayService.getOfficeWayUseRequestView(officewayId);
		
		User user = (User) getRequestAttribute("ikep.user");
		String teamLeader = officewayDao.getTeamLeader(user.getUserId());
		User leader = new User();
		leader = userService.read(teamLeader);
		mav.addObject("leader", leader);
		mav.addObject("officeway", officeway);
		
		return mav;
	}
	
	@RequestMapping(value = "/exceptOfficewayList.do")
	public ModelAndView exceptOfficewayList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) OfficeWaySearchCondition searchCondition,
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {


		ModelAndView mav = new ModelAndView("/lightpack/officeway/exceptOfficewayList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}*/

			SearchResult<Map<String, Object>> searchResult = officewayService.exceptOfficewayList(searchCondition);
			BoardCode boardCode = new BoardCode();
			mav.addObject("searchResult", searchResult);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			
		} catch (Exception e) {
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/checkProductno.do")
	public @ResponseBody
	String checkProductno(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) String productNo,
			AccessingResult accessResult) {


		boolean result = officewayService.existsProductno(productNo);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}
	
	@RequestMapping(value = "/createOfficewayExcept.do", method = RequestMethod.POST)
	public @ResponseBody
	String createOfficewayExcept(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @ValidEx OfficeWay officeWay,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		String productNo = officeWay.getProductNo();
		User user = (User) getRequestAttribute("ikep.user");
		boolean isCodeExist = officewayService.existsProductno(productNo);

		// companyCodeCode가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {

			officeWay.setUpdaterId(user.getUserId());
			officeWay.setUpdaterName(user.getUserName());

			officewayService.updateOfficewayExcept(officeWay);

		} else {

			officeWay.setRegisterId(user.getUserId());
			officeWay.setRegisterName(user.getUserName());
			officeWay.setUpdaterId(user.getUserId());
			officeWay.setUpdaterName(user.getUserName());

			officewayService.createOfficewayExcept(officeWay);
		}

		status.setComplete();

		return productNo;

	}
	
	@RequestMapping(value = "/deleteOfficewayExcept.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("productNo") String productNo,
			AccessingResult accessResult, HttpServletRequest request) {

		OfficeWay officeWay = new OfficeWay();
		officeWay.setProductNo(productNo);
		officewayService.deleteOfficewayExcept(officeWay);

		return "redirect:/lightpack/officeway/exceptOfficewayList.do";
	}
	
	@RequestMapping(value = "/editCategory")
	public ModelAndView editCategory() {

		OfficeWay categoryBoardId = new OfficeWay();
		categoryBoardId.setBoardId("100000000001");

		List<OfficeWay> categoryList1 = null;
		categoryList1 = officewayService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("categoryList", categoryList1);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/editReason")
	public ModelAndView editReason() {

		OfficeWay categoryBoardId = new OfficeWay();
		categoryBoardId.setBoardId("100000000002");

		List<OfficeWay> categoryList1 = null;
		categoryList1 = officewayService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("categoryList", categoryList1);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/createCategoryBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategoryBoard(OfficeWay category ,@RequestParam(value = "boardId") String boardId) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		String addNameList = category.getAddNameList();
		String delIdList = category.getDelIdList();
		String updateNameList = category.getUpdateNameList();
		String updateIdList = category.getUpdateIdList();

		OfficeWay receiveCategoryNmList = new OfficeWay();
		receiveCategoryNmList.setAddNameList(addNameList);
		receiveCategoryNmList.setDelIdList(delIdList);
		receiveCategoryNmList.setUpdateIdList(updateIdList);
		receiveCategoryNmList.setUpdateNameList(updateNameList);
		receiveCategoryNmList.setBoardId(boardId);
		
		receiveCategoryNmList.setRegisterId(user.getUserId());
		receiveCategoryNmList.setRegisterName(user.getUserName());
		receiveCategoryNmList.setAlignList(category.getAlignList());

		List<OfficeWay> list = new ArrayList<OfficeWay>();
		list.add(receiveCategoryNmList);
		officewayService.insertCategoryNm(list);
		return "success";
	}
	
}
