/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officesupplies.web;

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
import com.lgcns.ikep4.lightpack.officesupplies.dao.OfficeSuppliesDao;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSupplies;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSuppliesSearchCondition;
import com.lgcns.ikep4.lightpack.officesupplies.service.OfficeSuppliesService;
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
@RequestMapping(value = "/lightpack/officesupplies")
public class OfficeSuppliesController extends BaseController {
	
	@Autowired
	private OfficeSuppliesService officesuppliesService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	OfficeSuppliesDao officesuppliesDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/officesuppliesUseRequestForm.do")
	public ModelAndView officesuppliesUseRequestForm() {
		String view = "/lightpack/officesupplies/officesuppliesUseRequestForm";

		ModelAndView mav = new ModelAndView(view);
		
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		List<OfficeSupplies> exceptOfficesuppliesList = officesuppliesService.exceptOfficesuppliesAllList();
		mav.addObject("exceptOfficesuppliesList", exceptOfficesuppliesList);
		
		OfficeSupplies categoryBoardId = new OfficeSupplies();
		categoryBoardId.setBoardId("100000000001");
		List<OfficeSupplies> categoryList1 = null;
		categoryList1 = officesuppliesService.listCategory(categoryBoardId);
		
		categoryBoardId.setBoardId("100000000002");
		List<OfficeSupplies> categoryList2 = null;
		categoryList2 = officesuppliesService.listCategory(categoryBoardId);
		mav.addObject("categoryList1", categoryList1);
		mav.addObject("categoryList2", categoryList2);
		
		boolean periodCheck = officesuppliesService.periodCheck();
		mav.addObject("periodCheck", periodCheck);
		mav.addObject("today", DateUtil.getTodayDateTime("yyyy.MM"));
		
		
		return mav;
	}
	
	
	
	@RequestMapping(value = "/officesuppliesUseRequestMenuView.do")
	public ModelAndView officesuppliesUseRequestMenuView() {

		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestMenuView");
		
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
		boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);
		
		mav.addObject("oftlRole", oftlRole);
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		boolean oftrRole = officesuppliesService.teamManagerCheck(map4);
		
		mav.addObject("oftrRole", oftrRole);
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadExcelOfficesupplies.do")
	public ModelAndView downloadExcelOfficesupplies( OfficeSuppliesSearchCondition searchCondition, HttpServletResponse response) {
		ExcelViewModel excel = new ExcelViewModel();  
		try {				
		
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		
		List<OfficeSupplies> officeSuppliesList = officesuppliesService.teamsRequestDetailList(searchCondition);
		
		String fileName = "사무용품신청_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( officeSuppliesList.size() > 0 ) {
			
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
		    		    
		    excel.setDataList(officeSuppliesList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/officesuppliesRequestDelete.do")
	public @ResponseBody String officesuppliesRequestDelete(
			OfficeSupplies officesupplies
	) {

		officesuppliesService.officesuppliesRequestDelete(officesupplies);
		return "success";
	}
	
	@RequestMapping(value = "/downloadExcelOfficesuppliesTeam.do")
	public ModelAndView downloadExcelOfficesuppliesTeam( OfficeSuppliesSearchCondition searchCondition, HttpServletResponse response, String teamId) {
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
			
			List<OfficeSupplies> officeSuppliesList = officesuppliesService.teamRequestDetailList(searchCondition);
		
		String fileName = "officesupplies_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( officeSuppliesList.size() > 0 ) {
			
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
		    		    
		    excel.setDataList(officeSuppliesList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/downloadExcelOfficesuppliesOtherTeam.do")
	public ModelAndView downloadExcelOfficesuppliesOtherTeam( OfficeSuppliesSearchCondition searchCondition, HttpServletResponse response, String teamId) {
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
			searchCondition.setStatus1("A");
			List<OfficeSupplies> officeSuppliesList = officesuppliesService.teamRequestDetailList(searchCondition);
		
		String fileName = "officesupplies_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( officeSuppliesList.size() > 0 ) {
			
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
		    		    
		    excel.setDataList(officeSuppliesList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/officesuppliesUseRequestMyListUpdateForm.do")
	public ModelAndView officesuppliesUseRequestMyListUpdateForm(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestMyListUpdateForm");
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
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.myRequestList(searchCondition);
			
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
			boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officesuppliesService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			
			List<OfficeSupplies> exceptOfficesuppliesList = officesuppliesService.exceptOfficesuppliesAllList();
			mav.addObject("exceptOfficesuppliesList", exceptOfficesuppliesList);

			OfficeSupplies categoryBoardId = new OfficeSupplies();
			categoryBoardId.setBoardId("100000000001");
			List<OfficeSupplies> categoryList1 = null;
			categoryList1 = officesuppliesService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OfficeSupplies> categoryList2 = null;
			categoryList2 = officesuppliesService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			boolean periodCheck = officesuppliesService.periodCheck();
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
	
	@RequestMapping(value = "/officesuppliesUseRequestMyList.do")
	public ModelAndView officesuppliesUseRequestMyList(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestMyList");
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
				searchCondition.setStartMonth(DateUtil.getPrevMonthDate(DateUtil.getToday("yyyyMMdd"), 6, "MM"));
				searchCondition.setEndYear(DateUtil.getToday("yyyy"));
				searchCondition.setEndMonth(DateUtil.getToday("MM"));
				searchCondition.setSearchStartDate(DateUtil.getPrevDate(DateUtil.getToday("yyyyMMdd"), 180, "yyyy")+DateUtil.getPrevMonthDate(DateUtil.getToday("yyyyMMdd"), 6, "MM"));
				searchCondition.setSearchEndDate(DateUtil.getToday("yyyy")+DateUtil.getToday("MM"));
			}else{
				searchCondition.setSearchStartDate(searchCondition.getStartYear()+searchCondition.getStartMonth());
				searchCondition.setSearchEndDate(searchCondition.getEndYear()+searchCondition.getEndMonth());
			}
			
			searchCondition.setPagePerRecord(50);
			
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.myRequestList(searchCondition);
			
			int price1 = officesuppliesService.selectRequestUserPrice(searchCondition);
			int price2 = officesuppliesService.selectRequestTeamPrice(searchCondition);
			int price3 = officesuppliesService.selectRequestMyPrice1(searchCondition);
			int price4 = officesuppliesService.selectRequestMyPrice2(searchCondition);
			
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officesuppliesService.periodCheck();
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
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestTeamList.do")
	public ModelAndView officesuppliesUseRequestTeamList(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition, String teamId, String viewMonth) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestTeamList");
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
			boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);
			if(oftlRole){
				searchCondition.setAuth("TC");
			}
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officesuppliesService.teamManagerCheck(map4);
			if(oftrRole){
				searchCondition.setAuth("TR");
			}
			mav.addObject("oftrRole", oftrRole);
			List <OfficeSupplies> officesuppliesUseTeamList = null;
			if(oftlRole || oftrRole){
				officesuppliesUseTeamList = officesuppliesService.officesuppliesUseTeamList(user.getUserId());
				searchCondition.setUserId(user.getUserId());
				if(teamId != null){
					searchCondition.setTeamId(teamId);
				}else{
					searchCondition.setTeamId("");
				}
			}
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.teamRequestList(searchCondition);

			int price4 = officesuppliesService.teamRequestPrice1(searchCondition);
			int price5 = officesuppliesService.teamRequestPrice2(searchCondition);
			
			
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officesuppliesService.periodCheck();
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
			mav.addObject("officesuppliesUseTeamList", officesuppliesUseTeamList);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestTeamListPayment.do")
	public ModelAndView officesuppliesUseRequestTeamListPayment(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment");
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
			boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);
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
			boolean oftrRole = officesuppliesService.teamManagerCheck(map4);
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
			
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.teamRequestList(searchCondition);

			//int price4 = officesuppliesService.teamRequestPrice1(searchCondition);
			int price5 = officesuppliesService.teamRequestPrice2(searchCondition);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officesuppliesService.periodCheck();
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
	
	@RequestMapping(value = "/officesuppliesUseRequestOtherTeamList.do")
	public ModelAndView officesuppliesUseRequestOtherTeamList(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition, String teamId, String viewMonth) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestOtherTeamList");
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
			searchCondition.setStatus1("A");
			
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
			
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.teamRequestList(searchCondition);
			
			int price4 = officesuppliesService.teamRequestPrice1(searchCondition);
			int price5 = officesuppliesService.teamRequestPrice2(searchCondition);
			
			List <OfficeSupplies> officesuppliesUseTeamList = officesuppliesService.officesuppliesUseTeamListAll();

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
			boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officesuppliesService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officesuppliesService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("officesuppliesUseTeamList", officesuppliesUseTeamList);
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
	
	
	@RequestMapping(value = "/officesuppliesUseRequestStatistics.do")
	public ModelAndView officesuppliesUseRequestStatistics(OfficeSupplies officesupplies) {
		
		String year = DateUtil.getToday("yyyy");
		
		//OfficeSupplies period = officesuppliesService.getPeriod();
		if(officesupplies.getYear() == null){
			officesupplies.setYear(year);
		}else{
			year = officesupplies.getYear();
		}
		List<OfficeSupplies> statisticsList1 = officesuppliesService.getStatisticsList1(officesupplies);
		List<OfficeSupplies> statisticsList2 = officesuppliesService.getStatisticsList2(officesupplies);
		List<OfficeSupplies> statisticsList3 = officesuppliesService.getStatisticsList3(officesupplies);
		
		int price6 = officesuppliesService.getTotalPrice(officesupplies);
		
		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestStatistics");
		
		List <OfficeSupplies> officesuppliesUseTeamList = officesuppliesService.officesuppliesUseTeamListAll();
		
		mav.addObject("statisticsList1", statisticsList1);
		mav.addObject("statisticsList2", statisticsList2);
		mav.addObject("statisticsList3", statisticsList3);
		mav.addObject("price6", price6);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		mav.addObject("officesuppliesUseTeamList", officesuppliesUseTeamList);
		mav.addObject("teamId", officesupplies.getTeamId());
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestTeamStatistics.do")
	public ModelAndView officesuppliesUseRequestTeamStatistics(OfficeSupplies officesupplies) {
		
		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestTeamStatistics");
		String year = DateUtil.getToday("yyyy");
		User user = getUser();
		//OfficeSupplies period = officesuppliesService.getPeriod();
		if(officesupplies.getYear() == null){
			officesupplies.setYear(year);
		}else{
			year = officesupplies.getYear();
		}
		if(officesupplies.getTeamId() == null){
			officesupplies.setTeamId(user.getGroupId());
		}
		List<OfficeSupplies> statisticsList1 = officesuppliesService.getStatisticsList1(officesupplies);
		List<OfficeSupplies> statisticsList2 = officesuppliesService.getStatisticsList2(officesupplies);
		List<OfficeSupplies> statisticsList3 = officesuppliesService.getStatisticsList3(officesupplies);
		
		int price6 = officesuppliesService.getTotalPrice(officesupplies);
		
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);
		mav.addObject("oftlRole", oftlRole);
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		boolean oftrRole = officesuppliesService.teamManagerCheck(map4);
		mav.addObject("oftrRole", oftrRole);
		List <OfficeSupplies> officesuppliesUseTeamList = null;
		if(oftlRole || oftrRole){
			officesuppliesUseTeamList = officesuppliesService.officesuppliesUseTeamList(user.getUserId());
		}
		
		mav.addObject("statisticsList1", statisticsList1);
		mav.addObject("statisticsList2", statisticsList2);
		mav.addObject("statisticsList3", statisticsList3);
		mav.addObject("price6", price6);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		mav.addObject("officesuppliesUseTeamList", officesuppliesUseTeamList);
		mav.addObject("teamId", officesupplies.getTeamId());
		mav.addObject("oftrRole", oftrRole);
		mav.addObject("oftlRole", oftlRole);
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesTeamAuthSave.do")
	public ModelAndView officesuppliesTeamAuthSave(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/OfficesuppliesTeamAuthList.do";
		ModelAndView mav = new ModelAndView(view);
		
		officesuppliesService.officesuppliesTeamAuthSave(officesupplies);
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesTeamAuthEditForm.do")
	public ModelAndView officesuppliesTeamAuthEditForm(String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesTeamAuthEditForm");
		
		OfficeSupplies teamAuthInfo = officesuppliesService.getOfficesuppliesTeamAuthInfo(teamId);
		mav.addObject("teamAuthInfo", teamAuthInfo);
		return mav;
	}
	@RequestMapping(value = "/officesuppliesUseRequestOtherTeamListPayment.do")
	public ModelAndView officesuppliesUseRequestOtherTeamListPayment(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestOtherTeamListPayment");
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
			searchCondition.setStatus1("A");
			
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
			
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.teamRequestList(searchCondition);
			
			List <OfficeSupplies> officesuppliesUseTeamList = officesuppliesService.officesuppliesUseTeamListAll();
			
			int price4 = officesuppliesService.teamRequestPrice1(searchCondition);
			int price5 = officesuppliesService.teamRequestPrice2(searchCondition);
			

			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);

			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officesuppliesService.teamManagerCheck(map4);

			mav.addObject("oftrRole", oftrRole);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = officesuppliesService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("officesuppliesUseTeamList", officesuppliesUseTeamList);
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
	
	@RequestMapping(value = "/officesuppliesUseRequestTeamListUpdateForm.do")
	public ModelAndView officesuppliesUseRequestTeamListUpdateForm(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestTeamListUpdateForm");
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
			boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);
			if(oftlRole){
				searchCondition.setAuth("TC");
				searchCondition.setTeamId("");
			}
			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officesuppliesService.teamManagerCheck(map4);
			if(oftrRole){
				searchCondition.setAuth("TR");
				searchCondition.setTeamId("");
			}
			mav.addObject("oftrRole", oftrRole);
			
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.teamRequestList(searchCondition);

			
			List<OfficeSupplies> exceptOfficesuppliesList = officesuppliesService.exceptOfficesuppliesAllList();
			OfficeSupplies categoryBoardId = new OfficeSupplies();
			categoryBoardId.setBoardId("100000000001");
			List<OfficeSupplies> categoryList1 = null;
			categoryList1 = officesuppliesService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OfficeSupplies> categoryList2 = null;
			categoryList2 = officesuppliesService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			mav.addObject("exceptOfficesuppliesList", exceptOfficesuppliesList);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("today", DateUtil.getTodayDateTime("yyyy.MM"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestOtherTeamListUpdateForm.do")
	public ModelAndView officesuppliesUseRequestOtherTeamListUpdateForm(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestOtherTeamListUpdateForm");
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
			searchCondition.setStatus1("A");
			searchCondition.setSearchStatus("TA");
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.teamRequestList(searchCondition);

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
			boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);

			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officesuppliesService.teamManagerCheck(map4);

			mav.addObject("oftrRole", oftrRole);
			List<OfficeSupplies> exceptOfficesuppliesList = officesuppliesService.exceptOfficesuppliesAllList();
			
			OfficeSupplies categoryBoardId = new OfficeSupplies();
			categoryBoardId.setBoardId("100000000001");
			List<OfficeSupplies> categoryList1 = null;
			categoryList1 = officesuppliesService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OfficeSupplies> categoryList2 = null;
			categoryList2 = officesuppliesService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			mav.addObject("exceptOfficesuppliesList", exceptOfficesuppliesList);
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
	
	@RequestMapping(value = "/officesuppliesUseRequestTeamsList.do")
	public ModelAndView officesuppliesUseRequestTeamsList(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestTeamsList");
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
			boolean oftlRole = officesuppliesService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = officesuppliesService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			*/
			
			
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.teamsRequestList(searchCondition);
			
			int price3 = officesuppliesService.teamsRequestPrice(searchCondition);
			
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
	
	@RequestMapping("/officesuppliesUseRequestView.do")
	public ModelAndView officesuppliesRequestView(@RequestParam("officesuppliesId") String officesuppliesId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestView");
		
		OfficeSupplies officesupplies = officesuppliesService.getOfficeSuppliesUseRequestView(officesuppliesId);
		
		mav.addObject("officesupplies", officesupplies);

		return mav;
	}
	
	@RequestMapping("/officesuppliesUseRequestMyView.do")
	public ModelAndView officesuppliesRequestMyView(@RequestParam("officesuppliesId") String officesuppliesId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestMyView");
		
		OfficeSupplies officesupplies = officesuppliesService.getOfficeSuppliesUseRequestView(officesuppliesId);
		
		mav.addObject("officesupplies", officesupplies);

		return mav;
	}
	
	@RequestMapping("/officesuppliesUseRequestAllView.do")
	public ModelAndView officesuppliesRequestAllView(@RequestParam("officesuppliesId") String officesuppliesId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestAllView");
		
		OfficeSupplies officesupplies = officesuppliesService.getOfficeSuppliesUseRequestView(officesuppliesId);
		
		mav.addObject("officesupplies", officesupplies);

		return mav;
	}
	
	@RequestMapping("/officesuppliesApproveUse")
	public ModelAndView officesuppliesApproveUse(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition, @RequestParam("officesuppliesId") String officesuppliesId) {
		
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OfficeSupplies officesupplies = new OfficeSupplies();
			officesupplies.setApproveUserId(user.getUserId());
			officesupplies.setApproveStatus("A");
			officesupplies.setOfficeSuppliesId(officesuppliesId);
			officesuppliesService.officesuppliesApproveUse(officesupplies);
			
			OfficeSupplies tempOfficeSupplies = officesuppliesService.getOfficeSuppliesUseRequestView(officesuppliesId);
			
			officesuppliesService.sendOfficeSuppliesUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempOfficeSupplies, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping("/officesuppliesApproveUseConfirm")
	public ModelAndView officesuppliesApproveUseConfirm(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition, @RequestParam("officesuppliesId") String officesuppliesId) {
		
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestAllList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OfficeSupplies officesupplies = new OfficeSupplies();
			officesupplies.setApproveUserId(user.getUserId());
			officesupplies.setApproveStatus("C");
			officesupplies.setOfficeSuppliesId(officesuppliesId);
			officesuppliesService.officesuppliesApproveUse(officesupplies);
			
			OfficeSupplies tempOfficeSupplies = officesuppliesService.getOfficeSuppliesUseRequestView(officesuppliesId);
			
			officesuppliesService.sendOfficeSuppliesUseRequestMail("cfm", "USB 사용 요청이 승인이 확인되었습니다" , tempOfficeSupplies, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping("/officesuppliesRejectUse")
	public ModelAndView officesuppliesRejectUse(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition, @RequestParam("officesuppliesId") String officesuppliesId, String rejectReason) {
		
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OfficeSupplies officesupplies = new OfficeSupplies();
			officesupplies.setApproveUserId(user.getUserId());
			officesupplies.setApproveStatus("R");
			officesupplies.setOfficeSuppliesId(officesuppliesId);
			officesupplies.setRejectReason(rejectReason);
			officesuppliesService.officesuppliesApproveUse(officesupplies);
			
			OfficeSupplies tempOfficeSupplies = officesuppliesService.getOfficeSuppliesUseRequestView(officesuppliesId);
			
			officesuppliesService.sendOfficeSuppliesUseRequestMail("rej", "USB 사용 요청이 반려되었습니다" , tempOfficeSupplies, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestList.do")
	public ModelAndView officesuppliesUseRequestList(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestList");
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
			
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.requestList(searchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestAllList.do")
	public ModelAndView officesuppliesUseRequestAllList(String startPeriod, String endPeriod, OfficeSuppliesSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesUseRequestAllList");
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
			
			SearchResult<Map<String, Object>> searchResult = officesuppliesService.requestAllList(searchCondition);

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
	
	@RequestMapping(value = "/officesuppliesUseRequest.do")
	public ModelAndView officesuppliesUseRequest(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesUseRequest(officesupplies, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesCheckBoxUseRequest.do")
	public ModelAndView officesuppliesCheckBoxUseRequest(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesCheckBoxUseRequest(officesupplies, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesTeamCheckBoxUseRequest.do")
	public ModelAndView officesuppliesTeamCheckBoxUseRequest(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesCheckBoxUseRequest(officesupplies, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesManageCheckBoxUseRequest.do")
	public ModelAndView officesuppliesManageCheckBoxUseRequest(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestOtherTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesManageCheckBoxUseRequest(officesupplies, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesCheckBoxGroupRequest.do")
	public ModelAndView officesuppliesCheckBoxGroupRequest(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestTeamsList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesCheckBoxGroupRequest(officesupplies, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/savePeriod.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String savePeriod(OfficeSupplies officesupplies) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		officesupplies.setUpdaterId(user.getUserId());
		officesupplies.setUpdaterName(user.getUserName());
		
		officesuppliesService.savePeriod(officesupplies);
		
		return "success";
	}
	
	@RequestMapping(value = "/editPeriodForm.do")
	public ModelAndView editPeriodForm(OfficeSupplies officesupplies) {
		
		String year = DateUtil.getToday("yyyy");
		
		//OfficeSupplies period = officesuppliesService.getPeriod();
		if(officesupplies.getYear() != null){
			year = officesupplies.getYear();
		}
		List<OfficeSupplies> periodList = officesuppliesService.getPeriodList(year);
		
		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/editPeriodForm");
		
		mav.addObject("periodList", periodList);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		return mav;
	}
	
	@RequestMapping(value = "/OfficesuppliesTeamAuthList.do")
	public ModelAndView OfficesuppliesTeamAuthList(OfficeSupplies officesupplies) {
		
		
		List<OfficeSupplies> officesuppliesTeamAuthList = officesuppliesService.getOfficesuppliesTeamAuthList();
		
		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/officesuppliesTeamAuthList");
		
		mav.addObject("officesuppliesTeamAuthList", officesuppliesTeamAuthList);
		return mav;
	}
	
	
	@RequestMapping(value = "/roleList.do")
	public ModelAndView roleList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AdminSearchCondition searchCondition,
			AccessingResult accessResult) {

		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/roleList");

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

		return "redirect:/lightpack/officesupplies/roleList.do";
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

			role.setCodeCheck("modify");

			mav.addObject("currRoleId", role.getRoleId());
			mav.addObject("userList", userList);
			mav.addObject("roleGroupList", roleGroupList);
			mav.addObject("roleUserList", roleUserList);
		} else {
			role = new Role();
			role.setCodeCheck("new");
		}

		mav.addObject("userLocaleCode", userLocaleCode);
		mav.addObject("role", role);
		mav.addObject("roleTypeList", roleTypeList);

		mav.setViewName("/lightpack/officesupplies/roleForm");

		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestUpdate.do")
	public ModelAndView officesuppliesUseRequestUpdate(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesUseRequestUpdate(officesupplies, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestTeamUpdate.do")
	public ModelAndView officesuppliesUseRequestTeamUpdate(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesUseRequestUpdate(officesupplies, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestOtherTeamUpdate.do")
	public ModelAndView officesuppliesUseRequestOtherTeamUpdate(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestOtherTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesUseRequestUpdate(officesupplies, user);
		
		mav.addObject("teamId", officesupplies.getTeamId());
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseReqUpdate.do")
	public ModelAndView officesuppliesUseReqUpdate(OfficeSupplies officesupplies) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		officesuppliesService.officesuppliesUseRequestUpdate(officesupplies);
		
		String teamLeader = officesuppliesDao.getTeamLeader(user.getUserId());
		
		if(user.getUserId().equals(teamLeader)){
			OfficeSupplies tempofficesupplies = new OfficeSupplies();
			tempofficesupplies.setApproveUserId(teamLeader);
			tempofficesupplies.setApproveStatus("A");
			tempofficesupplies.setOfficeSuppliesId(officesupplies.getOfficeSuppliesId());
			officesuppliesService.officesuppliesApproveUse(tempofficesupplies);
			
			OfficeSupplies tempOfficeSupplies = officesuppliesService.getOfficeSuppliesUseRequestView(officesupplies.getOfficeSuppliesId());
			
			officesuppliesService.sendOfficeSuppliesUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempOfficeSupplies, user);
		}else{
			officesuppliesService.sendOfficeSuppliesUseRequestMail("req", "USB 사용 승인 요청" , officesupplies, user);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestDelete.do")
	public ModelAndView officesuppliesUseRequestDelete(@RequestParam("officesuppliesId") String officesuppliesId) {
		String view = "redirect:/lightpack/officesupplies/officesuppliesUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		OfficeSupplies officesupplies = new OfficeSupplies();
		officesupplies.setOfficeSuppliesId(officesuppliesId);
		officesupplies.setIsDelete("1");
		officesuppliesService.officesuppliesUseRequestDelete(officesupplies);
		
		return mav;
	}
	
	@RequestMapping(value = "/officesuppliesUseRequestUpdateForm.do")
	public ModelAndView officesuppliesUseRequestUpdateForm(@RequestParam("officesuppliesId") String officesuppliesId) {
		String view = "/lightpack/officesupplies/officesuppliesUseRequestUpdateForm";
		ModelAndView mav = new ModelAndView(view);
		
		OfficeSupplies officesupplies = officesuppliesService.getOfficeSuppliesUseRequestView(officesuppliesId);
		
		User user = (User) getRequestAttribute("ikep.user");
		String teamLeader = officesuppliesDao.getTeamLeader(user.getUserId());
		User leader = new User();
		leader = userService.read(teamLeader);
		mav.addObject("leader", leader);
		mav.addObject("officesupplies", officesupplies);
		
		return mav;
	}
	
	@RequestMapping(value = "/exceptOfficesuppliesList.do")
	public ModelAndView exceptOfficesuppliesList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) OfficeSuppliesSearchCondition searchCondition,
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {


		ModelAndView mav = new ModelAndView("/lightpack/officesupplies/exceptOfficesuppliesList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}*/

			SearchResult<Map<String, Object>> searchResult = officesuppliesService.exceptOfficesuppliesList(searchCondition);
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


		boolean result = officesuppliesService.existsProductno(productNo);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}
	
	@RequestMapping(value = "/createOfficesuppliesExcept.do", method = RequestMethod.POST)
	public @ResponseBody
	String createOfficesuppliesExcept(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @ValidEx OfficeSupplies officeSupplies,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		String productNo = officeSupplies.getProductNo();
		User user = (User) getRequestAttribute("ikep.user");
		boolean isCodeExist = officesuppliesService.existsProductno(productNo);

		// companyCodeCode가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {

			officeSupplies.setUpdaterId(user.getUserId());
			officeSupplies.setUpdaterName(user.getUserName());

			officesuppliesService.updateOfficesuppliesExcept(officeSupplies);

		} else {

			officeSupplies.setRegisterId(user.getUserId());
			officeSupplies.setRegisterName(user.getUserName());
			officeSupplies.setUpdaterId(user.getUserId());
			officeSupplies.setUpdaterName(user.getUserName());

			officesuppliesService.createOfficesuppliesExcept(officeSupplies);
		}

		status.setComplete();

		return productNo;

	}
	
	@RequestMapping(value = "/deleteOfficesuppliesExcept.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("productNo") String productNo,
			AccessingResult accessResult, HttpServletRequest request) {

		OfficeSupplies officeSupplies = new OfficeSupplies();
		officeSupplies.setProductNo(productNo);
		officesuppliesService.deleteOfficesuppliesExcept(officeSupplies);

		return "redirect:/lightpack/officesupplies/exceptOfficesuppliesList.do";
	}
	
	@RequestMapping(value = "/editCategory")
	public ModelAndView editCategory() {

		OfficeSupplies categoryBoardId = new OfficeSupplies();
		categoryBoardId.setBoardId("100000000001");

		List<OfficeSupplies> categoryList1 = null;
		categoryList1 = officesuppliesService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("categoryList", categoryList1);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/editReason")
	public ModelAndView editReason() {

		OfficeSupplies categoryBoardId = new OfficeSupplies();
		categoryBoardId.setBoardId("100000000002");

		List<OfficeSupplies> categoryList1 = null;
		categoryList1 = officesuppliesService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("categoryList", categoryList1);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/createCategoryBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategoryBoard(OfficeSupplies category ,@RequestParam(value = "boardId") String boardId) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		String addNameList = category.getAddNameList();
		String delIdList = category.getDelIdList();
		String updateNameList = category.getUpdateNameList();
		String updateIdList = category.getUpdateIdList();

		OfficeSupplies receiveCategoryNmList = new OfficeSupplies();
		receiveCategoryNmList.setAddNameList(addNameList);
		receiveCategoryNmList.setDelIdList(delIdList);
		receiveCategoryNmList.setUpdateIdList(updateIdList);
		receiveCategoryNmList.setUpdateNameList(updateNameList);
		receiveCategoryNmList.setBoardId(boardId);
		
		receiveCategoryNmList.setRegisterId(user.getUserId());
		receiveCategoryNmList.setRegisterName(user.getUserName());
		receiveCategoryNmList.setAlignList(category.getAlignList());

		List<OfficeSupplies> list = new ArrayList<OfficeSupplies>();
		list.add(receiveCategoryNmList);
		officesuppliesService.insertCategoryNm(list);
		return "success";
	}
	
}
