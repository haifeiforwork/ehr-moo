/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginLog;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenuLog;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtLoginLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtStatisticsService;
import com.lgcns.ikep4.servicepack.usagetracker.util.DateUtil;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;


/**
 * 메뉴 레포트
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtStatsExcelReportController.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Controller
@RequestMapping(value = "/servicepack/usagetracker/stats/excel/")
public class UtStatsExcelReportController extends BaseUsageTrackerController {

	// 현황
	@Autowired
	UtStatisticsService utStatisticsService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	UtLoginLogService utLoginLogService;

	@Autowired
	UtMenuLogService utMenuLogService;

	@Autowired
	UtMenuService utMenuService;

	@RequestMapping(value = "/login")
	public void login(UtSearchCondition searchCondition, BindingResult bindingResult, HttpServletResponse response) {

		User user = (User) this.getRequestAttribute("ikep.user");

		try {
			getSearchUtil(searchCondition);

			List<UtLoginLog> itemList = utLoginLogService.excelLoginListBySearchCondition(searchCondition);

			// 사용자 로케일 적용 getMessage
			Locale locale = new Locale(user.getLocaleCode());

			String title = messageSource.getMessage("ui.servicepack.usagetracker.stats.pagetitle.login", null, locale);
			String loginUser = messageSource.getMessage("ui.servicepack.usagetracker.stats.grid.loginUser", null,
					locale);
			String loginTime = messageSource.getMessage("ui.servicepack.usagetracker.stats.grid.loginTime", null,
					locale);

			List<Object> excelList = new ArrayList<Object>();
			for (UtLoginLog utLoginLog : itemList) {
				excelList.add(utLoginLog);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
			titleMap.put("userName", loginUser);
			titleMap.put("loginDate", loginTime);

			// 파일 저장
			ExcelUtil.saveExcel(titleMap, excelList, "loginLog.xlsx", response);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/browser")
	public ModelAndView browser(UtSearchCondition searchCondition, BindingResult bindingResult,
			HttpServletResponse response) {
		Map model = new HashMap();

		try {
			getSearchUtil(searchCondition);

			String format = "xls";
			List<UtLoginLog> itemList = utLoginLogService.excelLoginListBySearchCondition(searchCondition);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(itemList);
			model.put("itemList", source);
			model.put("format", format);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return new ModelAndView("browseLogReport", model);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/sms")
	public ModelAndView sms(UtSearchCondition searchCondition, BindingResult bindingResult,
			HttpServletResponse response) {
		Map model = new HashMap();

		try {

			String format = "xls";
			searchCondition.setSdate(com.lgcns.ikep4.util.lang.DateUtil.getFmtDateString(searchCondition.getStartDate(), "yyyyMMdd"));
			searchCondition.setEdate(com.lgcns.ikep4.util.lang.DateUtil.getFmtDateString(searchCondition.getEndDate(), "yyyyMMdd"));
			List<UtLoginLog> itemList = utLoginLogService.excelSmsListBySearchCondition(searchCondition);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(itemList);
			model.put("itemList", source);
			model.put("format", format);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return new ModelAndView("smsLogReport", model);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/menu")
	public ModelAndView menu(UtSearchCondition searchCondition, BindingResult bindingResult,
			HttpServletResponse response) {

		Map model = new HashMap();

		try {
			getSearchUtil(searchCondition);

			String format = "xls";
			List<UtMenuLog> itemList = utMenuLogService.excelMenuListBySearchCondition(searchCondition);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(itemList);
			model.put("itemList", source);
			model.put("format", format);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return new ModelAndView("menuLogReport", model);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/simpleMenu")
	public ModelAndView simpleMenu(UtSearchCondition searchCondition, BindingResult bindingResult,
			HttpServletResponse response) {

		Map model = new HashMap();

		try {
			getSimpleSearchUtil(searchCondition);

			String format = "xls";
			List<UtMenuLog> itemList = utMenuLogService.excelMenuListBySearchCondition(searchCondition);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(itemList);
			model.put("itemList", source);
			model.put("format", format);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return new ModelAndView("menuLogReport", model);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/simpleMobileMenu")
	public ModelAndView simpleMobileMenu(UtSearchCondition searchCondition, BindingResult bindingResult,
			HttpServletResponse response) {

		Map model = new HashMap();

		try {
			//getSearchUtil(searchCondition);

			String format = "xls";
			List<UtMenuLog> itemList = utMenuLogService.excelMobileMenuListBySearchCondition(searchCondition);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(itemList);
			model.put("itemList", source);
			model.put("format", format);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return new ModelAndView("menuLogReport", model);

	}

	/**
	 * 검색조건 생성
	 * 
	 * @param input
	 * @param user
	 * @return
	 */
	private void getSearchUtil(UtSearchCondition searchCondition) {
		User user = (User) getRequestAttribute("ikep.user");

		// 둘이 null이면 기본디폴트로 1주일전부터 오늘까지
		if (searchCondition.getStartDate() == null || searchCondition.getEndDate() == null) {
			searchCondition.setStartDate(DateUtil.prevWeek(1));
			searchCondition.setEndDate(new Date());
		}

		// customer type일경우
		if (searchCondition.getSearchOption() == -1) {
			searchCondition.setStartDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getStartDate()));
			searchCondition.setEndDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getEndDate()));
		} else {
			searchCondition.setStartDate(DateUtil.prevMonth(searchCondition.getSearchOption()));
			searchCondition.setEndDate(new Date());
		}

		searchCondition.setPortalId(user.getPortalId());
	}
	
	private void getSimpleSearchUtil(UtSearchCondition searchCondition) {
		User user = (User) getRequestAttribute("ikep.user");

		// 둘이 null이면 기본디폴트로 1주일전부터 오늘까지
		if (searchCondition.getStartDate() == null || searchCondition.getEndDate() == null) {
			searchCondition.setStartDate(DateUtil.prevWeek(1));
			searchCondition.setEndDate(new Date());
		}

		// customer type일경우
		if (searchCondition.getSearchOption() == -1) {
			searchCondition.setStartDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getStartDate()));
			searchCondition.setEndDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getEndDate()));
		} else {
			searchCondition.setStartDate(DateUtil.getToday());
			searchCondition.setEndDate(new Date());
		}

		searchCondition.setPortalId(user.getPortalId());
	}
}
