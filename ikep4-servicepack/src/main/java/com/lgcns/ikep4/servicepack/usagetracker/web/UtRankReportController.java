/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.Code;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtLoginRankService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtStatisticsService;
import com.lgcns.ikep4.servicepack.usagetracker.util.DateUtil;
import com.lgcns.ikep4.servicepack.usagetracker.util.MagicNumUtils;
import com.lgcns.ikep4.support.searchpreprocessor.util.PageCons;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;


/**
 * 메뉴 레포트
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtRankReportController.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Controller
@RequestMapping(value = "/servicepack/usagetracker/rank/")
public class UtRankReportController extends BaseUsageTrackerController {

	// 현황
	@Autowired
	UtStatisticsService utStatisticsService;

	// 현황
	@Autowired
	UtLoginRankService utLoginRankService;

	@Autowired
	private TimeZoneSupportService timeZoneSupportService;

	@RequestMapping(value = "/login")
	public ModelAndView login(UtSearchCondition searchCondition, BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/rank/login");

		try {
			User user = (User) getRequestAttribute("ikep.user");
			searchCondition.setPortalId(user.getPortalId());

			if (searchCondition.getSearchOption() == 0) {
				searchCondition.setSearchOption(MagicNumUtils.MONTH_1);
			}

			if (searchCondition.getStartDate() == null || searchCondition.getEndDate() == null) {
				searchCondition.setStartDate(DateUtil.prevWeek(1));
				searchCondition.setEndDate(new Date());
			}

			if (searchCondition.getSearchOption() == -1) {
				searchCondition.setStartDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getStartDate()));
				searchCondition.setEndDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getEndDate()));
			}

			SearchResult<UtStatistics> searchResult = utLoginRankService.listBySearchCondition(searchCondition);
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return mav;
	}

	// report일경우
	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * @RequestMapping(value = "/excel/login") public ModelAndView
	 * excelLogin(UtSearchCondition searchCondition, BindingResult sresult) {
	 * Map model = new HashMap(); try { User user = (User)
	 * getRequestAttribute("ikep.user");
	 * searchCondition.setPortalId(user.getPortalId()); if
	 * (searchCondition.getSearchOption() == 0) {
	 * searchCondition.setSearchOption(MagicNumUtils.MONTH_1); } if
	 * (searchCondition.getStartDate() == null || searchCondition.getEndDate()
	 * == null) { searchCondition.setStartDate(DateUtil.prevWeek(1));
	 * searchCondition.setEndDate(new Date()); } searchCondition.setEndRowIndex(
	 * MagicNumUtils.ARRAY_SIZE_MAX); if (searchCondition.getSearchOption() ==
	 * -1) {
	 * searchCondition.setStartDate(timeZoneSupportService.convertServerTimeZone
	 * (searchCondition .getStartDate()));
	 * searchCondition.setEndDate(timeZoneSupportService
	 * .convertServerTimeZone(searchCondition.getEndDate())); }
	 * List<UtStatistics> itemList =
	 * utLoginRankService.excelListBySearchCondition(searchCondition); String
	 * format="xls"; JRBeanCollectionDataSource source = new
	 * JRBeanCollectionDataSource(itemList); model.put("itemList", source);
	 * model.put("format", format); } catch (Exception e) {
	 * log.error(e.getMessage()); } return new ModelAndView("loginRankReport",
	 * model); }
	 */

	@RequestMapping(value = "/excel/login")
	public void excelLogin(UtSearchCondition searchCondition, BindingResult sresult, HttpServletResponse response) {
        

		try {
			User user = (User) getRequestAttribute("ikep.user");
			searchCondition.setPortalId(user.getPortalId());

			if (searchCondition.getSearchOption() == 0) {
				searchCondition.setSearchOption(MagicNumUtils.MONTH_1);
			}

			if (searchCondition.getStartDate() == null || searchCondition.getEndDate() == null) {
				searchCondition.setStartDate(DateUtil.prevWeek(1));
				searchCondition.setEndDate(new Date());
			}

			searchCondition.setEndRowIndex(MagicNumUtils.ARRAY_SIZE_MAX);

			if (searchCondition.getSearchOption() == -1) {
				searchCondition.setStartDate(timeZoneSupportService.convertServerTimeZone(searchCondition
						.getStartDate()));
				searchCondition.setEndDate(timeZoneSupportService.convertServerTimeZone(searchCondition.getEndDate()));
			}

			List<UtStatistics> itemList = utLoginRankService.excelListBySearchCondition(searchCondition);

			// 사용자 로케일 적용 getMessage
			Locale locale = new Locale(user.getLocaleCode());

			String title = messageSource.getMessage("ui.servicepack.usagetracker.rank.pagetitle.login", null, locale);
			String loginUser = messageSource.getMessage("ui.servicepack.usagetracker.stats.grid.loginUser", null,
					locale);
			String usageCount = messageSource.getMessage("ui.servicepack.usagetracker.rank.grid.usageCount", null,
					locale);

			List<Object> excelList = new ArrayList<Object>();
			for (UtStatistics utStatistics : itemList) {
				if (utStatistics.getUsageCount() == null || utStatistics.getUsageCount() < 1) {
					utStatistics.setUsageCount(0);
				}
				excelList.add(utStatistics);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
			titleMap.put("usageCount", usageCount);
			titleMap.put("userName", loginUser);

			// 파일 저장
			ExcelUtil.saveExcel(titleMap, excelList, "loginRankLog.xlsx", response);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * 코드생성
	 */
	@ModelAttribute("searchOptionList")
	public List<Code<Integer>> getSearchOption() {
		List<Code<Integer>> searchOptionList = new ArrayList<Code<Integer>>();
		searchOptionList.add(new Code<Integer>(MagicNumUtils.MONTH_12,
				"ui.servicepack.usagetracker.common.searchoption.0"));
		searchOptionList.add(new Code<Integer>(MagicNumUtils.MONTH_6,
				"ui.servicepack.usagetracker.common.searchoption.1"));
		searchOptionList.add(new Code<Integer>(MagicNumUtils.MONTH_3,
				"ui.servicepack.usagetracker.common.searchoption.2"));
		searchOptionList.add(new Code<Integer>(MagicNumUtils.MONTH_1,
				"ui.servicepack.usagetracker.common.searchoption.3"));

		return searchOptionList;
	}

	@SuppressWarnings("unchecked")
	@ModelAttribute("pageNumList")
	public List<Code<Integer>> getPageNumList() {
		List<Code<Integer>> pageNumList = Arrays.asList(new Code<Integer>(PageCons.PAGE_PER_ROW_10, "10"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_15, "15"), new Code<Integer>(PageCons.PAGE_PER_ROW_20, "20"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_30, "30"), new Code<Integer>(PageCons.PAGE_PER_ROW_40, "40"),
				new Code<Integer>(PageCons.PAGE_PER_ROW_50, "50"));

		return pageNumList;
	}
}
