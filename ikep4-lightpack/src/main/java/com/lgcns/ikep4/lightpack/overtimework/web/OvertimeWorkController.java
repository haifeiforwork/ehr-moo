/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.overtimework.web;

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

import com.lgcns.ikep4.collpack.kms.admin.dao.AdminPermissionDao;
import com.lgcns.ikep4.collpack.kms.admin.model.AdminTeamLeader;
import com.lgcns.ikep4.collpack.kms.admin.service.AdminPermissionService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.overtimework.dao.OvertimeWorkDao;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWork;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWorkSearchCondition;
import com.lgcns.ikep4.lightpack.overtimework.service.OvertimeWorkService;
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
@RequestMapping(value = "/lightpack/overtimework")
public class OvertimeWorkController extends BaseController {
	
	@Autowired
	private OvertimeWorkService overtimeworkService;
	
	@Autowired
	private TimeZoneSupportService timeZoneSupportService;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	OvertimeWorkDao overtimeworkDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AdminPermissionService adminPermissionService;
	
	@Autowired
	private AdminPermissionDao adminPermissionDao;
	
	@RequestMapping(value = "/overtimeworkUseRequestForm.do")
	public ModelAndView overtimeworkUseRequestForm() {
		String view = "/lightpack/overtimework/overtimeworkUseRequestForm";

		ModelAndView mav = new ModelAndView(view);
		
		//Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		List<OvertimeWork> exceptOvertimeworkList = overtimeworkService.exceptOvertimeworkAllList();
		mav.addObject("exceptOvertimeworkList", exceptOvertimeworkList);
		
		OvertimeWork categoryBoardId = new OvertimeWork();
		categoryBoardId.setBoardId("100000000001");
		List<OvertimeWork> categoryList1 = null;
		categoryList1 = overtimeworkService.listCategory(categoryBoardId);
		
		categoryBoardId.setBoardId("100000000002");
		List<OvertimeWork> categoryList2 = null;
		categoryList2 = overtimeworkService.listCategory(categoryBoardId);
		mav.addObject("categoryList1", categoryList1);
		mav.addObject("categoryList2", categoryList2);
		
		boolean periodCheck = overtimeworkService.periodCheck();
		mav.addObject("periodCheck", periodCheck);
		mav.addObject("today", DateUtil.getTodayDateTime("yyyy.MM"));
		
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkInOutRegistForm.do")
	public ModelAndView overtimeworkInOutRegistForm(String saveYn) {
		String view = "/lightpack/overtimework/overtimeworkInOutRegistForm";

		ModelAndView mav = new ModelAndView(view);
		
		mav.addObject("saveYn", saveYn);
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkInOutUpdateForm.do")
	public ModelAndView overtimeworkInOutUpdateForm(String overtimeworkId) {
		String view = "/lightpack/overtimework/overtimeworkInOutUpdateForm";

		
		OvertimeWork tempOvertimeWork = new OvertimeWork();
		
		tempOvertimeWork = overtimeworkService.overtimeworkInOutDetail(overtimeworkId);
		ModelAndView mav = new ModelAndView(view);
		
		mav.addObject("tempOvertimeWork", tempOvertimeWork);
		return mav;
	}
	
	
	
	@RequestMapping(value = "/overtimeworkUseRequestMenuView.do")
	public ModelAndView overtimeworkUseRequestMenuView() {

		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestMenuView");
		
		boolean owusrRole = false;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userId", user.getUserId());
		map1.put("roleName", "OWUSR");
		int owusr = userDao.getUserRoleCheck(map1);
		if(owusr > 0){
			owusrRole = true;
		}
		mav.addObject("owusrRole", owusrRole);
		
		boolean owadmRole = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "OWADM");
		int owadm = userDao.getUserRoleCheck(map2);
		if(owadm > 0){
			owadmRole = true;
		}
		mav.addObject("owadmRole", owadmRole);
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadExcelOvertimework.do")
	public ModelAndView downloadExcelOvertimework( OvertimeWorkSearchCondition searchCondition, HttpServletResponse response) {
		ExcelViewModel excel = new ExcelViewModel();  
		try {				
		
		
		if (searchCondition.getPagePerRecord() == null) {
			searchCondition.setPagePerRecord(10);
		}
		searchCondition.setPageIndex(1);
		
		List<OvertimeWork> overtimeWorkList = overtimeworkService.teamsRequestDetailList(searchCondition);
		
		String fileName = "사무용품신청_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( overtimeWorkList.size() > 0 ) {
			
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
		    		    
		    excel.setDataList(overtimeWorkList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/overtimeworkRequestDelete.do")
	public @ResponseBody String overtimeworkRequestDelete(
			OvertimeWork overtimework
	) {

		overtimeworkService.overtimeworkRequestDelete(overtimework);
		return "success";
	}
	
	@RequestMapping(value = "/downloadExcelOvertimeworkInOutMyList.do")
	public ModelAndView downloadExcelOvertimeworkInOutMyList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {
		ExcelViewModel excel = new ExcelViewModel();  
		try {		
			User user = getUser();
			Portal portal = getPortal();
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
			
			//searchCondition.setTeamId(user.getGroupId());
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
			
			boolean owadmRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OWADM ");
			int owadm = userDao.getUserRoleCheck(map1);
			if(owadm > 0){
				owadmRole = true;
			}else{
				//searchCondition.setSearchWord(user.getUserName());
				if(StringUtil.isEmpty(searchCondition.getTeamCode())) {
					searchCondition.setTeamCode(user.getGroupId());
				}else if(searchCondition.getTeamCode().equals("all")){
					searchCondition.setTeamCode("");
				}
				//searchCondition.setSearchColumn("userName");
				//searchCondition.setUserId(user.getUserId());
			}
			
			List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
			List<AdminTeamLeader> teamList = null;
			
			if(searchCondition.getWorkPlaceName() !=null ){
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}else{
				searchCondition.setWorkPlaceName("본사");
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}
			List<OvertimeWork> overtimeWorkList = overtimeworkService.overtimeworkInOutExcelList(searchCondition);
		
			String fileName = "work_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		if( overtimeWorkList.size() > 0 ) {
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    //excel.setTitle(fileName+ DateUtil.getTodayDateTime("yyyyMM"));  
		    
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.addColumn("NO", "num", 5,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("출입시간", "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("구분", "inoutFlag", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("소속", "companyCodeName", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("사업장", "workPlaceName", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("부서", "groupName", 15,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("성명", "userName", 7,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("직책", "jobTitleName", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("근무자 확인", "checkFlag", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("확인시간", "checkDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    		    
		    excel.setDataList(overtimeWorkList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/downloadExcelOvertimeworkInOutUnidentifiedList.do")
	public ModelAndView downloadExcelOvertimeworkInOutUnidentifiedList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {
		ExcelViewModel excel = new ExcelViewModel();  
		try {		
			User user = getUser();
			Portal portal = getPortal();
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
			
			//searchCondition.setTeamId(user.getGroupId());
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
			
			boolean owadmRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OWADM ");
			int owadm = userDao.getUserRoleCheck(map1);
			if(owadm > 0){
				owadmRole = true;
			}else{
				//searchCondition.setSearchWord(user.getUserName());
				if(StringUtil.isEmpty(searchCondition.getTeamCode())) {
					searchCondition.setTeamCode(user.getGroupId());
				}else if(searchCondition.getTeamCode().equals("all")){
					searchCondition.setTeamCode("");
				}
				//searchCondition.setSearchColumn("userName");
				//searchCondition.setUserId(user.getUserId());
			}
			
			List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
			List<AdminTeamLeader> teamList = null;
			
			if(searchCondition.getWorkPlaceName() !=null ){
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}else{
				searchCondition.setWorkPlaceName("본사");
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}
			
			searchCondition.setCheckFlag("N");
			List<OvertimeWork> overtimeWorkList = overtimeworkService.overtimeworkInOutExcelList(searchCondition);
		
			String fileName = "work_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		if( overtimeWorkList.size() > 0 ) {
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    //excel.setTitle(fileName+ DateUtil.getTodayDateTime("yyyyMM"));  
		    
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.addColumn("NO", "num", 5,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("출입시간", "registDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("구분", "inoutFlag", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("소속", "companyCodeName", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("사업장", "workPlaceName", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("부서", "groupName", 15,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("성명", "userName", 7,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("직책", "jobTitleName", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("근무자 확인", "checkFlag", 10,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("확인시간", "checkDate", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT);
		    		    
		    excel.setDataList(overtimeWorkList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/downloadExcelOvertimeworkOtherTeam.do")
	public ModelAndView downloadExcelOvertimeworkOtherTeam( OvertimeWorkSearchCondition searchCondition, HttpServletResponse response, String teamId) {
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
			List<OvertimeWork> overtimeWorkList = overtimeworkService.teamRequestDetailList(searchCondition);
		
		String fileName = "overtimework_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( overtimeWorkList.size() > 0 ) {
			
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
		    		    
		    excel.setDataList(overtimeWorkList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
	}	
	
	@RequestMapping(value = "/overtimeworkUseRequestMyListUpdateForm.do")
	public ModelAndView overtimeworkUseRequestMyListUpdateForm(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestMyListUpdateForm");
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
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.myRequestList(searchCondition);
			
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
			boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = overtimeworkService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			
			List<OvertimeWork> exceptOvertimeworkList = overtimeworkService.exceptOvertimeworkAllList();
			mav.addObject("exceptOvertimeworkList", exceptOvertimeworkList);

			OvertimeWork categoryBoardId = new OvertimeWork();
			categoryBoardId.setBoardId("100000000001");
			List<OvertimeWork> categoryList1 = null;
			categoryList1 = overtimeworkService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OvertimeWork> categoryList2 = null;
			categoryList2 = overtimeworkService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			boolean periodCheck = overtimeworkService.periodCheck();
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
	
	@RequestMapping(value = "/overtimeworkInOutMyList.do")
	public ModelAndView overtimeworkInOutMyList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkInOutMyList");
		User user = getUser();
		Portal portal = getPortal();

		try {

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
			
			//searchCondition.setTeamId(user.getGroupId());
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
			
			boolean owadmRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OWADM ");
			int owadm = userDao.getUserRoleCheck(map1);
			if(owadm > 0){
				owadmRole = true;
			}else{
				/*if(StringUtil.isEmpty(searchCondition.getSearchWord())) {
					searchCondition.setSearchWord(user.getUserName());
				}else{
					searchCondition.setSearchWord(user.getUserName());
				}*/
				if(StringUtil.isEmpty(searchCondition.getTeamCode())) {
					searchCondition.setTeamCode(user.getGroupId());
				}else if(searchCondition.getTeamCode().equals("all")){
					searchCondition.setTeamCode("");
				}
				//searchCondition.setSearchColumn("userName");
				//searchCondition.setUserId(user.getUserId());
			}
			
			List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
			List<AdminTeamLeader> teamList = null;
			
			if(searchCondition.getWorkPlaceName() !=null ){
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}else{
				searchCondition.setWorkPlaceName("본사");
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.overtimeworkInOutMyList(searchCondition);
			
			
			//List <OvertimeWork> overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamListAll();
			
			
			mav.addObject("owadmRole", owadmRole);
			
			BoardCode boardCode = new BoardCode();
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			//mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
			mav.addObject("workPlaceList", workPlaceList);
			mav.addObject("teamList", teamList);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkInOutMyListPrint.do")
	public ModelAndView overtimeworkInOutMyListPrint(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkInOutMyListPrint");
		User user = getUser();
		Portal portal = getPortal();

		try {

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
			
			//searchCondition.setTeamId(user.getGroupId());
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
			
			searchCondition.setPagePerRecord(1000);
			
			boolean owadmRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OWADM ");
			int owadm = userDao.getUserRoleCheck(map1);
			if(owadm > 0){
				owadmRole = true;
			}else{
				/*if(StringUtil.isEmpty(searchCondition.getSearchWord())) {
					searchCondition.setSearchWord(user.getUserName());
				}else{
					searchCondition.setSearchWord(user.getUserName());
				}*/
				if(StringUtil.isEmpty(searchCondition.getTeamCode())) {
					searchCondition.setTeamCode(user.getGroupId());
				}else if(searchCondition.getTeamCode().equals("all")){
					searchCondition.setTeamCode("");
				}
				//searchCondition.setSearchColumn("userName");
				//searchCondition.setUserId(user.getUserId());
			}
			
			List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
			List<AdminTeamLeader> teamList = null;
			
			if(searchCondition.getWorkPlaceName() !=null ){
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}else{
				searchCondition.setWorkPlaceName("본사");
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.overtimeworkInOutMyList(searchCondition);
			
			
			//List <OvertimeWork> overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamListAll();
			
			
			mav.addObject("owadmRole", owadmRole);
			
			BoardCode boardCode = new BoardCode();
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			//mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
			mav.addObject("workPlaceList", workPlaceList);
			mav.addObject("teamList", teamList);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkInOutUnidentifiedList.do")
	public ModelAndView overtimeworkInOutUnidentifiedList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkInOutUnidentifiedList");
		User user = getUser();
		Portal portal = getPortal();

		try {

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
				
				cal1.add(Calendar.DATE, -2); 
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
			
			//searchCondition.setTeamId(user.getGroupId());
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
			
			boolean owadmRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OWADM ");
			int owadm = userDao.getUserRoleCheck(map1);
			if(owadm > 0){
				owadmRole = true;
			}else{
				/*if(StringUtil.isEmpty(searchCondition.getSearchWord())) {
					searchCondition.setSearchWord(user.getUserName());
				}else{
					searchCondition.setSearchWord(user.getUserName());
				}*/
				if(StringUtil.isEmpty(searchCondition.getTeamCode())) {
					searchCondition.setTeamCode(user.getGroupId());
				}else if(searchCondition.getTeamCode().equals("all")){
					searchCondition.setTeamCode("");
				}
				//searchCondition.setSearchColumn("userName");
				//searchCondition.setUserId(user.getUserId());
			}
			
			List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
			List<AdminTeamLeader> teamList = null;
			
			if(searchCondition.getWorkPlaceName() !=null ){
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}else{
				searchCondition.setWorkPlaceName("본사");
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}
			searchCondition.setCheckFlag("N");
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.overtimeworkInOutMyList(searchCondition);
			
			
			//List <OvertimeWork> overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamListAll();
			
			
			mav.addObject("owadmRole", owadmRole);
			
			BoardCode boardCode = new BoardCode();
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			//mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
			mav.addObject("workPlaceList", workPlaceList);
			mav.addObject("teamList", teamList);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkInOutUnidentifiedListPrint.do")
	public ModelAndView overtimeworkInOutUnidentifiedListPrint(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkInOutMyListPrint");
		User user = getUser();
		Portal portal = getPortal();

		try {

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
				
				cal1.add(Calendar.DATE, -2); 
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
			
			//searchCondition.setTeamId(user.getGroupId());
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
			
			searchCondition.setPagePerRecord(1000);
			
			boolean owadmRole = false;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userId", user.getUserId());
			map1.put("roleName", "OWADM ");
			int owadm = userDao.getUserRoleCheck(map1);
			if(owadm > 0){
				owadmRole = true;
			}else{
				/*if(StringUtil.isEmpty(searchCondition.getSearchWord())) {
					searchCondition.setSearchWord(user.getUserName());
				}else{
					searchCondition.setSearchWord(user.getUserName());
				}*/
				if(StringUtil.isEmpty(searchCondition.getTeamCode())) {
					searchCondition.setTeamCode(user.getGroupId());
				}else if(searchCondition.getTeamCode().equals("all")){
					searchCondition.setTeamCode("");
				}
				//searchCondition.setSearchColumn("userName");
				//searchCondition.setUserId(user.getUserId());
			}
			
			List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
			List<AdminTeamLeader> teamList = null;
			
			if(searchCondition.getWorkPlaceName() !=null ){
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}else{
				searchCondition.setWorkPlaceName("본사");
				teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
			}
			searchCondition.setCheckFlag("N");
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.overtimeworkInOutMyList(searchCondition);
			
			
			//List <OvertimeWork> overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamListAll();
			
			
			mav.addObject("owadmRole", owadmRole);
			
			BoardCode boardCode = new BoardCode();
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
			//mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
			mav.addObject("workPlaceList", workPlaceList);
			mav.addObject("teamList", teamList);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestTeamList.do")
	public ModelAndView overtimeworkUseRequestTeamList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition, String teamId, String viewMonth) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestTeamList");
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
			boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);
			if(oftlRole){
				searchCondition.setAuth("TC");
			}
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = overtimeworkService.teamManagerCheck(map4);
			if(oftrRole){
				searchCondition.setAuth("TR");
			}
			mav.addObject("oftrRole", oftrRole);
			List <OvertimeWork> overtimeworkUseTeamList = null;
			if(oftlRole || oftrRole){
				overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamList(user.getUserId());
				searchCondition.setUserId(user.getUserId());
				if(teamId != null){
					searchCondition.setTeamId(teamId);
				}else{
					searchCondition.setTeamId("");
				}
			}
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.teamRequestList(searchCondition);

			int price4 = overtimeworkService.teamRequestPrice1(searchCondition);
			int price5 = overtimeworkService.teamRequestPrice2(searchCondition);
			
			
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = overtimeworkService.periodCheck();
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
			mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkInOutAllList.do")
	public ModelAndView overtimeworkInOutAllList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkInOutAllList");
		User user = getUser();
		Portal portal = getPortal();

		try {

			
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
			searchCondition.setPagePerRecord(50);

			SearchResult<Map<String, Object>> searchResult = overtimeworkService.teamRequestList(searchCondition);

			
			BoardCode boardCode = new BoardCode();
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
			mav.addObject("nowYear", DateUtil.getToday("yyyy"));
			mav.addObject("nowMonth", DateUtil.getToday("MM"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestTeamListPayment.do")
	public ModelAndView overtimeworkUseRequestTeamListPayment(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestTeamListPayment");
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
			boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);
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
			boolean oftrRole = overtimeworkService.teamManagerCheck(map4);
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
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.teamRequestList(searchCondition);

			//int price4 = overtimeworkService.teamRequestPrice1(searchCondition);
			int price5 = overtimeworkService.teamRequestPrice2(searchCondition);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = overtimeworkService.periodCheck();
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
	
	@RequestMapping(value = "/overtimeworkUseRequestOtherTeamList.do")
	public ModelAndView overtimeworkUseRequestOtherTeamList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition, String teamId, String viewMonth) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestOtherTeamList");
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
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.teamRequestList(searchCondition);
			
			int price4 = overtimeworkService.teamRequestPrice1(searchCondition);
			int price5 = overtimeworkService.teamRequestPrice2(searchCondition);
			
			List <OvertimeWork> overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamListAll();

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
			boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = overtimeworkService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = overtimeworkService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
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
	
	
	@RequestMapping(value = "/overtimeworkUseRequestStatistics.do")
	public ModelAndView overtimeworkUseRequestStatistics(OvertimeWork overtimework) {
		
		String year = DateUtil.getToday("yyyy");
		
		//OvertimeWork period = overtimeworkService.getPeriod();
		if(overtimework.getYear() == null){
			overtimework.setYear(year);
		}else{
			year = overtimework.getYear();
		}
		List<OvertimeWork> statisticsList1 = overtimeworkService.getStatisticsList1(overtimework);
		List<OvertimeWork> statisticsList2 = overtimeworkService.getStatisticsList2(overtimework);
		List<OvertimeWork> statisticsList3 = overtimeworkService.getStatisticsList3(overtimework);
		
		int price6 = overtimeworkService.getTotalPrice(overtimework);
		
		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestStatistics");
		
		List <OvertimeWork> overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamListAll();
		
		mav.addObject("statisticsList1", statisticsList1);
		mav.addObject("statisticsList2", statisticsList2);
		mav.addObject("statisticsList3", statisticsList3);
		mav.addObject("price6", price6);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
		mav.addObject("teamId", overtimework.getTeamId());
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestTeamStatistics.do")
	public ModelAndView overtimeworkUseRequestTeamStatistics(OvertimeWork overtimework) {
		
		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestTeamStatistics");
		String year = DateUtil.getToday("yyyy");
		User user = getUser();
		//OvertimeWork period = overtimeworkService.getPeriod();
		if(overtimework.getYear() == null){
			overtimework.setYear(year);
		}else{
			year = overtimework.getYear();
		}
		if(overtimework.getTeamId() == null){
			overtimework.setTeamId(user.getGroupId());
		}
		List<OvertimeWork> statisticsList1 = overtimeworkService.getStatisticsList1(overtimework);
		List<OvertimeWork> statisticsList2 = overtimeworkService.getStatisticsList2(overtimework);
		List<OvertimeWork> statisticsList3 = overtimeworkService.getStatisticsList3(overtimework);
		
		int price6 = overtimeworkService.getTotalPrice(overtimework);
		
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);
		mav.addObject("oftlRole", oftlRole);
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		boolean oftrRole = overtimeworkService.teamManagerCheck(map4);
		mav.addObject("oftrRole", oftrRole);
		List <OvertimeWork> overtimeworkUseTeamList = null;
		if(oftlRole || oftrRole){
			overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamList(user.getUserId());
		}
		
		mav.addObject("statisticsList1", statisticsList1);
		mav.addObject("statisticsList2", statisticsList2);
		mav.addObject("statisticsList3", statisticsList3);
		mav.addObject("price6", price6);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
		mav.addObject("teamId", overtimework.getTeamId());
		mav.addObject("oftrRole", oftrRole);
		mav.addObject("oftlRole", oftlRole);
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkTeamAuthSave.do")
	public ModelAndView overtimeworkTeamAuthSave(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/OvertimeworkTeamAuthList.do";
		ModelAndView mav = new ModelAndView(view);
		
		overtimeworkService.overtimeworkTeamAuthSave(overtimework);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUserCardSave.do")
	public ModelAndView overtimeworkUserCardSave(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/OvertimeworkUserCardList.do";
		ModelAndView mav = new ModelAndView(view);
		
		overtimeworkService.overtimeworkUserCardSave(overtimework);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkTeamAuthEditForm.do")
	public ModelAndView overtimeworkTeamAuthEditForm(String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkTeamAuthEditForm");
		
		OvertimeWork teamAuthInfo = overtimeworkService.getOvertimeworkTeamAuthInfo(teamId);
		mav.addObject("teamAuthInfo", teamAuthInfo);
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUserCardEditForm.do")
	public ModelAndView overtimeworkUserCardEditForm(String userId) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUserCardEditForm");
		
		OvertimeWork userCardInfo = overtimeworkService.getOvertimeworkUserCardInfo(userId);
		mav.addObject("userCardInfo", userCardInfo);
		return mav;
	}
	@RequestMapping(value = "/overtimeworkUseRequestOtherTeamListPayment.do")
	public ModelAndView overtimeworkUseRequestOtherTeamListPayment(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestOtherTeamListPayment");
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
			
			searchCondition.setPagePerRecord(15);
			
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
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.teamRequestList(searchCondition);
			
			List <OvertimeWork> overtimeworkUseTeamList = overtimeworkService.overtimeworkUseTeamListAll();
			
			int price4 = overtimeworkService.teamRequestPrice1(searchCondition);
			int price5 = overtimeworkService.teamRequestPrice2(searchCondition);
			

			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("userId", user.getUserId());
			boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);

			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = overtimeworkService.teamManagerCheck(map4);

			mav.addObject("oftrRole", oftrRole);
			
			BoardCode boardCode = new BoardCode();
			boolean periodCheck = overtimeworkService.periodCheck();
			mav.addObject("periodCheck", periodCheck);
			mav.addObject("boardCode", boardCode);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("overtimeworkUseTeamList", overtimeworkUseTeamList);
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
	
	@RequestMapping(value = "/overtimeworkUseRequestTeamListUpdateForm.do")
	public ModelAndView overtimeworkUseRequestTeamListUpdateForm(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestTeamListUpdateForm");
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
			boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);
			if(oftlRole){
				searchCondition.setAuth("TC");
				searchCondition.setTeamId("");
			}
			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = overtimeworkService.teamManagerCheck(map4);
			if(oftrRole){
				searchCondition.setAuth("TR");
				searchCondition.setTeamId("");
			}
			mav.addObject("oftrRole", oftrRole);
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.teamRequestList(searchCondition);

			
			List<OvertimeWork> exceptOvertimeworkList = overtimeworkService.exceptOvertimeworkAllList();
			OvertimeWork categoryBoardId = new OvertimeWork();
			categoryBoardId.setBoardId("100000000001");
			List<OvertimeWork> categoryList1 = null;
			categoryList1 = overtimeworkService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OvertimeWork> categoryList2 = null;
			categoryList2 = overtimeworkService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			mav.addObject("exceptOvertimeworkList", exceptOvertimeworkList);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("searchCondition", searchCondition);
			mav.addObject("today", DateUtil.getTodayDateTime("yyyy.MM"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestOtherTeamListUpdateForm.do")
	public ModelAndView overtimeworkUseRequestOtherTeamListUpdateForm(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition, String teamId) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestOtherTeamListUpdateForm");
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
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.teamRequestList(searchCondition);

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
			boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);

			mav.addObject("oftlRole", oftlRole);

			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = overtimeworkService.teamManagerCheck(map4);

			mav.addObject("oftrRole", oftrRole);
			List<OvertimeWork> exceptOvertimeworkList = overtimeworkService.exceptOvertimeworkAllList();
			
			OvertimeWork categoryBoardId = new OvertimeWork();
			categoryBoardId.setBoardId("100000000001");
			List<OvertimeWork> categoryList1 = null;
			categoryList1 = overtimeworkService.listCategory(categoryBoardId);
			
			categoryBoardId.setBoardId("100000000002");
			List<OvertimeWork> categoryList2 = null;
			categoryList2 = overtimeworkService.listCategory(categoryBoardId);
			mav.addObject("categoryList1", categoryList1);
			mav.addObject("categoryList2", categoryList2);
			
			mav.addObject("exceptOvertimeworkList", exceptOvertimeworkList);
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
	
	@RequestMapping(value = "/overtimeworkUseRequestTeamsList.do")
	public ModelAndView overtimeworkUseRequestTeamsList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestTeamsList");
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
			boolean oftlRole = overtimeworkService.teamLeaderCheck(map3);
			
			mav.addObject("oftlRole", oftlRole);
			
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("userId", user.getUserId());
			boolean oftrRole = overtimeworkService.teamManagerCheck(map4);
			
			mav.addObject("oftrRole", oftrRole);
			*/
			
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.teamsRequestList(searchCondition);
			
			int price3 = overtimeworkService.teamsRequestPrice(searchCondition);
			
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
	
	@RequestMapping("/overtimeworkUseRequestView.do")
	public ModelAndView overtimeworkRequestView(@RequestParam("overtimeworkId") String overtimeworkId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestView");
		
		OvertimeWork overtimework = overtimeworkService.getOvertimeWorkUseRequestView(overtimeworkId);
		
		mav.addObject("overtimework", overtimework);

		return mav;
	}
	
	@RequestMapping("/overtimeworkUseRequestMyView.do")
	public ModelAndView overtimeworkRequestMyView(@RequestParam("overtimeworkId") String overtimeworkId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestMyView");
		
		OvertimeWork overtimework = overtimeworkService.getOvertimeWorkUseRequestView(overtimeworkId);
		
		mav.addObject("overtimework", overtimework);

		return mav;
	}
	
	@RequestMapping("/overtimeworkUseRequestAllView.do")
	public ModelAndView overtimeworkRequestAllView(@RequestParam("overtimeworkId") String overtimeworkId) {
		
		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestAllView");
		
		OvertimeWork overtimework = overtimeworkService.getOvertimeWorkUseRequestView(overtimeworkId);
		
		mav.addObject("overtimework", overtimework);

		return mav;
	}
	
	@RequestMapping("/overtimeworkApproveUse")
	public ModelAndView overtimeworkApproveUse(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition, @RequestParam("overtimeworkId") String overtimeworkId) {
		
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OvertimeWork overtimework = new OvertimeWork();
			overtimework.setApproveUserId(user.getUserId());
			overtimework.setApproveStatus("A");
			overtimework.setOvertimeWorkId(overtimeworkId);
			overtimeworkService.overtimeworkApproveUse(overtimework);
			
			OvertimeWork tempOvertimeWork = overtimeworkService.getOvertimeWorkUseRequestView(overtimeworkId);
			
			overtimeworkService.sendOvertimeWorkUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempOvertimeWork, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping("/overtimeworkApproveUseConfirm")
	public ModelAndView overtimeworkApproveUseConfirm(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition, @RequestParam("overtimeworkId") String overtimeworkId) {
		
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestAllList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OvertimeWork overtimework = new OvertimeWork();
			overtimework.setApproveUserId(user.getUserId());
			overtimework.setApproveStatus("C");
			overtimework.setOvertimeWorkId(overtimeworkId);
			overtimeworkService.overtimeworkApproveUse(overtimework);
			
			OvertimeWork tempOvertimeWork = overtimeworkService.getOvertimeWorkUseRequestView(overtimeworkId);
			
			overtimeworkService.sendOvertimeWorkUseRequestMail("cfm", "USB 사용 요청이 승인이 확인되었습니다" , tempOvertimeWork, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping("/overtimeworkRejectUse")
	public ModelAndView overtimeworkRejectUse(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition, @RequestParam("overtimeworkId") String overtimeworkId, String rejectReason) {
		
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestList.do";
		ModelAndView mav = new ModelAndView(view);
		
		User user = getUser();
		
		try {
			
			OvertimeWork overtimework = new OvertimeWork();
			overtimework.setApproveUserId(user.getUserId());
			overtimework.setApproveStatus("R");
			overtimework.setOvertimeWorkId(overtimeworkId);
			overtimework.setRejectReason(rejectReason);
			overtimeworkService.overtimeworkApproveUse(overtimework);
			
			OvertimeWork tempOvertimeWork = overtimeworkService.getOvertimeWorkUseRequestView(overtimeworkId);
			
			overtimeworkService.sendOvertimeWorkUseRequestMail("rej", "USB 사용 요청이 반려되었습니다" , tempOvertimeWork, user);
			
			
		} catch(Exception e) {
			
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestList.do")
	public ModelAndView overtimeworkUseRequestList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestList");
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
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.requestList(searchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("startPeriod", DateUtil.getFmtDateString(startDate, "yyyy.MM.dd"));
			mav.addObject("endPeriod", DateUtil.getFmtDateString(endDate, "yyyy.MM.dd"));
		} catch (Exception e) {
			
			throw new IKEP4AjaxException("", e);
		}

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestAllList.do")
	public ModelAndView overtimeworkUseRequestAllList(String startPeriod, String endPeriod, OvertimeWorkSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUseRequestAllList");
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
			
			SearchResult<Map<String, Object>> searchResult = overtimeworkService.requestAllList(searchCondition);

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
	
	@RequestMapping(value = "/overtimeworkInOutRegist.do")
	public ModelAndView overtimeworkInOutRegist(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkInOutRegistForm.do?saveYn=Y";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		String overtimeworkId = overtimeworkService.overtimeworkInOutRegist(overtimework, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkInOutUpdate.do")
	public ModelAndView overtimeworkInOutUpdate(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkInOutUnidentifiedList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		String overtimeworkId = overtimeworkService.overtimeworkInOutUpdate(overtimework, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkInOutAutoRegist.do")
	public @ResponseBody OvertimeWork overtimeworkInOutAutoRegist(OvertimeWork overtimework) {
		
		
		OvertimeWork tempOvertimeWork = new OvertimeWork();
		
		User user = (User) getRequestAttribute("ikep.user");
		String cardUserId = overtimeworkService.cardUserId(overtimework.getCardId());
		if (!StringUtil.isEmpty(cardUserId)) {
			overtimework.setOvertimeworkerId(cardUserId);
			String overtimeworkId = overtimeworkService.overtimeworkInOutRegist(overtimework, user);
			tempOvertimeWork = overtimeworkService.overtimeworkInOutDetail(overtimeworkId);
		}
		return tempOvertimeWork;
	}
	
	@RequestMapping(value = "/overtimeworkCheckBoxUseRequest.do")
	public ModelAndView overtimeworkCheckBoxUseRequest(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimeworkService.overtimeworkCheckBoxUseRequest(overtimework, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkTeamCheckBoxUseRequest.do")
	public ModelAndView overtimeworkTeamCheckBoxUseRequest(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimeworkService.overtimeworkCheckBoxUseRequest(overtimework, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkManageCheckBoxUseRequest.do")
	public ModelAndView overtimeworkManageCheckBoxUseRequest(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestOtherTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimeworkService.overtimeworkManageCheckBoxUseRequest(overtimework, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkCheckBoxGroupRequest.do")
	public ModelAndView overtimeworkCheckBoxGroupRequest(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestTeamsList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimeworkService.overtimeworkCheckBoxGroupRequest(overtimework, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/savePeriod.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String savePeriod(OvertimeWork overtimework) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimework.setUpdaterId(user.getUserId());
		overtimework.setUpdaterName(user.getUserName());
		
		overtimeworkService.savePeriod(overtimework);
		
		return "success";
	}
	
	@RequestMapping(value = "/editPeriodForm.do")
	public ModelAndView editPeriodForm(OvertimeWork overtimework) {
		
		String year = DateUtil.getToday("yyyy");
		
		//OvertimeWork period = overtimeworkService.getPeriod();
		if(overtimework.getYear() != null){
			year = overtimework.getYear();
		}
		List<OvertimeWork> periodList = overtimeworkService.getPeriodList(year);
		
		ModelAndView mav = new ModelAndView("/lightpack/overtimework/editPeriodForm");
		
		mav.addObject("periodList", periodList);
		mav.addObject("nowYear", DateUtil.getToday("yyyy"));
		mav.addObject("year", year);
		return mav;
	}
	
	@RequestMapping(value = "/OvertimeworkUserCardList.do")
	public ModelAndView OvertimeworkUserCardList(OvertimeWork overtimework, OvertimeWorkSearchCondition searchCondition) {
		
		
		
		
		ModelAndView mav = new ModelAndView("/lightpack/overtimework/overtimeworkUserCardList");
		List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
		List<AdminTeamLeader> teamList = null;
		
		if(searchCondition.getWorkPlaceName() !=null ){
			teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
		}else{
			searchCondition.setWorkPlaceName("본사");
			teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
		}
		searchCondition.setPagePerRecord(50);
		SearchResult<Map<String, Object>> searchResult = overtimeworkService.getOvertimeworkUserCardList(searchCondition);
		
		mav.addObject("searchResult", searchResult)
		.addObject("workPlaceList", workPlaceList)
		.addObject("teamList", teamList)
		.addObject("searchCondition", searchCondition);
		return mav;
	}
	
	@RequestMapping(value = "/downloadExcelOvertimeworkUserCardList.do")
	public ModelAndView downloadExcelOvertimeworkUserCardList(OvertimeWork overtimework, OvertimeWorkSearchCondition searchCondition) {
		
		
		 ExcelViewModel excel = new ExcelViewModel();  
		try {				
		
		searchCondition.setPageIndex(1);
			
		List<String> workPlaceList = adminPermissionService.getWorkPlaceNameList();
		List<AdminTeamLeader> teamList = null;
		
		if(searchCondition.getWorkPlaceName() !=null ){
			teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
		}else{
			searchCondition.setWorkPlaceName("본사");
			teamList = adminPermissionDao.listTeamCodes(searchCondition.getWorkPlaceName());
		}
		searchCondition.setPagePerRecord(3000);
		searchCondition.setEndRowIndex(3000);
		List<OvertimeWork> overtimeWorkList = overtimeworkService.getOvertimeworkUserCardListExcel(searchCondition);
		
		
		String fileName = "사용자카드정보_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

		
		if( overtimeWorkList.size() > 0 ) {
			
			excel.setFileName(fileName);   
		    excel.setSheetName("Sheet");   
		    
		    //excel.setTitle(fileName+ DateUtil.getTodayDateTime("yyyyMM"));  
		    
		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
		    excel.addColumn("아이디", "userId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("이름", "userName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
	    	excel.addColumn("직급/직책", "jobDutyName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("Email", "mail", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("소속", "companyCodeName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("사업장", "workPlaceName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("부서", "groupName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    excel.addColumn("CARD ID", "cardId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT); 
		    		    
		    excel.setDataList(overtimeWorkList);			
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
		 
		 
	}
	
	
	@RequestMapping(value = "/roleList.do")
	public ModelAndView roleList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) AdminSearchCondition searchCondition,
			AccessingResult accessResult) {

		ModelAndView mav = new ModelAndView("/lightpack/overtimework/roleList");

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
			searchCondition.setSearchWord("OW");

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

		return "redirect:/lightpack/overtimework/roleList.do";
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

		mav.setViewName("/lightpack/overtimework/roleForm");

		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestUpdate.do")
	public ModelAndView overtimeworkUseRequestUpdate(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimeworkService.overtimeworkUseRequestUpdate(overtimework, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestTeamUpdate.do")
	public ModelAndView overtimeworkUseRequestTeamUpdate(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimeworkService.overtimeworkUseRequestUpdate(overtimework, user);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestOtherTeamUpdate.do")
	public ModelAndView overtimeworkUseRequestOtherTeamUpdate(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestOtherTeamListPayment.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimeworkService.overtimeworkUseRequestUpdate(overtimework, user);
		
		mav.addObject("teamId", overtimework.getTeamId());
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseReqUpdate.do")
	public ModelAndView overtimeworkUseReqUpdate(OvertimeWork overtimework) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User) getRequestAttribute("ikep.user");
		
		overtimeworkService.overtimeworkUseRequestUpdate(overtimework);
		
		String teamLeader = overtimeworkDao.getTeamLeader(user.getUserId());
		
		if(user.getUserId().equals(teamLeader)){
			OvertimeWork tempovertimework = new OvertimeWork();
			tempovertimework.setApproveUserId(teamLeader);
			tempovertimework.setApproveStatus("A");
			tempovertimework.setOvertimeWorkId(overtimework.getOvertimeWorkId());
			overtimeworkService.overtimeworkApproveUse(tempovertimework);
			
			OvertimeWork tempOvertimeWork = overtimeworkService.getOvertimeWorkUseRequestView(overtimework.getOvertimeWorkId());
			
			overtimeworkService.sendOvertimeWorkUseRequestMail("apr", "USB 사용 요청이 승인되었습니다" , tempOvertimeWork, user);
		}else{
			overtimeworkService.sendOvertimeWorkUseRequestMail("req", "USB 사용 승인 요청" , overtimework, user);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestDelete.do")
	public ModelAndView overtimeworkUseRequestDelete(@RequestParam("overtimeworkId") String overtimeworkId) {
		String view = "redirect:/lightpack/overtimework/overtimeworkUseRequestMyList.do";
		ModelAndView mav = new ModelAndView(view);
		
		OvertimeWork overtimework = new OvertimeWork();
		overtimework.setOvertimeWorkId(overtimeworkId);
		overtimework.setIsDelete("1");
		overtimeworkService.overtimeworkUseRequestDelete(overtimework);
		
		return mav;
	}
	
	@RequestMapping(value = "/overtimeworkUseRequestUpdateForm.do")
	public ModelAndView overtimeworkUseRequestUpdateForm(@RequestParam("overtimeworkId") String overtimeworkId) {
		String view = "/lightpack/overtimework/overtimeworkUseRequestUpdateForm";
		ModelAndView mav = new ModelAndView(view);
		
		OvertimeWork overtimework = overtimeworkService.getOvertimeWorkUseRequestView(overtimeworkId);
		
		User user = (User) getRequestAttribute("ikep.user");
		String teamLeader = overtimeworkDao.getTeamLeader(user.getUserId());
		User leader = new User();
		leader = userService.read(teamLeader);
		mav.addObject("leader", leader);
		mav.addObject("overtimework", overtimework);
		
		return mav;
	}
	
	@RequestMapping(value = "/exceptOvertimeworkList.do")
	public ModelAndView exceptOvertimeworkList(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) OvertimeWorkSearchCondition searchCondition,
			AccessingResult accessResult, HttpServletRequest request, SessionStatus status) {


		ModelAndView mav = new ModelAndView("/lightpack/overtimework/exceptOvertimeworkList");

		User sessionUser = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		try {

			/*if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
				searchCondition.setSortColumn("productNo");
			}
			if (StringUtil.isEmpty(searchCondition.getSortType())) {
				searchCondition.setSortType("ASC");
			}*/

			SearchResult<Map<String, Object>> searchResult = overtimeworkService.exceptOvertimeworkList(searchCondition);
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


		boolean result = overtimeworkService.existsProductno(productNo);

		if (result) {
			return "duplicated";
		} else {
			return "success";
		}
	}
	
	@RequestMapping(value = "/createOvertimeworkExcept.do", method = RequestMethod.POST)
	public @ResponseBody
	String createOvertimeworkExcept(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @ValidEx OvertimeWork overtimeWork,
			AccessingResult accessResult, BindingResult result, SessionStatus status, HttpServletRequest request) {

		String productNo = overtimeWork.getProductNo();
		User user = (User) getRequestAttribute("ikep.user");
		boolean isCodeExist = overtimeworkService.existsProductno(productNo);

		// companyCodeCode가 이미 존재하는 경우, 기존의 코드를 수정하는 프로세스
		if (isCodeExist) {

			overtimeWork.setUpdaterId(user.getUserId());
			overtimeWork.setUpdaterName(user.getUserName());

			overtimeworkService.updateOvertimeworkExcept(overtimeWork);

		} else {

			overtimeWork.setRegisterId(user.getUserId());
			overtimeWork.setRegisterName(user.getUserName());
			overtimeWork.setUpdaterId(user.getUserId());
			overtimeWork.setUpdaterName(user.getUserName());

			overtimeworkService.createOvertimeworkExcept(overtimeWork);
		}

		status.setComplete();

		return productNo;

	}
	
	@RequestMapping(value = "/deleteOvertimeworkExcept.do")
	public String delete(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Portal", operationName = { "MANAGE" }, resourceName = "Portal")) @RequestParam("productNo") String productNo,
			AccessingResult accessResult, HttpServletRequest request) {

		OvertimeWork overtimeWork = new OvertimeWork();
		overtimeWork.setProductNo(productNo);
		overtimeworkService.deleteOvertimeworkExcept(overtimeWork);

		return "redirect:/lightpack/overtimework/exceptOvertimeworkList.do";
	}
	
	@RequestMapping(value = "/editCategory")
	public ModelAndView editCategory() {

		OvertimeWork categoryBoardId = new OvertimeWork();
		categoryBoardId.setBoardId("100000000001");

		List<OvertimeWork> categoryList1 = null;
		categoryList1 = overtimeworkService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("categoryList", categoryList1);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/editReason")
	public ModelAndView editReason() {

		OvertimeWork categoryBoardId = new OvertimeWork();
		categoryBoardId.setBoardId("100000000002");

		List<OvertimeWork> categoryList1 = null;
		categoryList1 = overtimeworkService.listCategory(categoryBoardId);		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("categoryList", categoryList1);
		model.addObject("categoryBoardId", categoryBoardId);
		return model;
	}
	
	@RequestMapping(value = "/createCategoryBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createCategoryBoard(OvertimeWork category ,@RequestParam(value = "boardId") String boardId) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		String addNameList = category.getAddNameList();
		String delIdList = category.getDelIdList();
		String updateNameList = category.getUpdateNameList();
		String updateIdList = category.getUpdateIdList();

		OvertimeWork receiveCategoryNmList = new OvertimeWork();
		receiveCategoryNmList.setAddNameList(addNameList);
		receiveCategoryNmList.setDelIdList(delIdList);
		receiveCategoryNmList.setUpdateIdList(updateIdList);
		receiveCategoryNmList.setUpdateNameList(updateNameList);
		receiveCategoryNmList.setBoardId(boardId);
		
		receiveCategoryNmList.setRegisterId(user.getUserId());
		receiveCategoryNmList.setRegisterName(user.getUserName());
		receiveCategoryNmList.setAlignList(category.getAlignList());

		List<OvertimeWork> list = new ArrayList<OvertimeWork>();
		list.add(receiveCategoryNmList);
		overtimeworkService.insertCategoryNm(list);
		return "success";
	}
	
}
