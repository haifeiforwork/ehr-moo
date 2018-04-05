/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtLoginLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtStatisticsService;
import com.lgcns.ikep4.servicepack.usagetracker.util.Criteria;
import com.lgcns.ikep4.servicepack.usagetracker.util.DateUtil;
import com.lgcns.ikep4.servicepack.usagetracker.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;
import com.lgcns.ikep4.servicepack.usagetracker.util.UsageTrackerConstance;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 메뉴 레포트
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuExcelReportController.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Controller
@RequestMapping(value = "/servicepack/usagetracker/rank/excel/")
public class UtMenuExcelReportController extends BaseUsageTrackerController {

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/menu")
	public ModelAndView menu(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		
		Map model = new HashMap();

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_MENU);
			searchCondition.setProcess( UsageTrackerConstance.MODULE_TYPE_MENU );
			searchCondition.setEndRowIndex(MagicNumUtils.ARRAY_SIZE_MAX);

			if (searchCondition.getSearchOption() == 0) {
				searchCondition.setSearchOption(MagicNumUtils.MONTH_1);
			}

			List<UtStatistics> itemList = reportStatus(result,searchCondition);

			String format = "xls";

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(itemList);
			model.put("itemList", source);
			model.put("format", format);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return new ModelAndView("menuRankReport", model);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/portlet")
	public ModelAndView portlet(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		Map model = new HashMap();

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_PORTLET);
			searchCondition.setProcess( UsageTrackerConstance.MODULE_TYPE_PORTLET );
			searchCondition.setEndRowIndex(MagicNumUtils.ARRAY_SIZE_MAX);

			List<UtStatistics> itemList = reportStatus(result,searchCondition);

			String format = "xls";

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(itemList);
			model.put("itemList", source);
			model.put("format", format);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ModelAndView("portletRankReport", model);

	}
	
	/**
	 * 현재 포틀릿 통계 엑셀다운로드
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/todayPortlet.do")
	public ModelAndView todayPortlet(UtSearchCondition searchCondition) {
		Map model = new HashMap();

		searchCondition.setEndRowIndex(MagicNumUtils.ARRAY_SIZE_MAX);

		SearchResult<UtStatistics> searchResult = utStatisticsService.excelListTodayPortlet(searchCondition);
		
		String format = "xls";

		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(searchResult.getEntity());
		model.put("itemList", source);
		model.put("format", format);
		
		return new ModelAndView("portletRankReport", model);
	}

	/**
	 * 실질적인 로직을 처리하는 부분
	 * 
	 * @param process
	 * @param viewOption
	 * @param searchOption
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<UtStatistics> reportStatus(UtResult input, UtSearchCondition searchCondition) {
		User user = (User) getRequestAttribute("ikep.user");
		List<UtStatistics> data = new ArrayList<UtStatistics>();

		try {

			if (input.getSearchOption() == 0) {
				input.setSearchOption(MagicNumUtils.MONTH_1);
			}

			SearchUtil searchUtil = getSearchUtil(input, user, searchCondition);
			searchCondition.setOredCriteria( searchUtil.getOredCriteria() );

			data = utStatisticsService.excelListBySearchCondition(searchCondition);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		
		return data;

	}

	/**
	 * 검색조건 생성
	 * 
	 * @param input
	 * @param user
	 * @return
	 */
	private SearchUtil getSearchUtil(UtResult input, User user, UtSearchCondition searchCondition) {
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setViewOption(input.getViewOption());

		Criteria criteria = searchUtil.createCriteria();
		criteria.addCriterion("s.module_type = ", input.getProcess(), "moduleType");

		// 둘이 null이면 기본디폴트로 1주일전부터 오늘까지
		if (input.getStartDate() == null || input.getEndDate() == null) {
			input.setStartDate(DateUtil.prevWeek(1));
			input.setEndDate(new Date());
		}

		// customer type일경우
		if (input.getSearchOption() == -1) {
			input.setStartDate(timeZoneSupportService.convertServerTimeZone(input.getStartDate()));
			input.setEndDate(timeZoneSupportService.convertServerTimeZone(input.getEndDate()));

			searchCondition.setStartDate(input.getStartDate());
			searchCondition.setEndDate(input.getEndDate());

			criteria.addCriterionForJDBCDate("s.MODULE_DATE between ", DateUtil.getNextday(input.getStartDate(), 0),
					DateUtil.getNextday(input.getEndDate(), MagicNumUtils.DAY_1), "moduleDate");
		} else {
			criteria.addCriterionForJDBCDate("s.MODULE_DATE >=", DateUtil.prevMonth(input.getSearchOption()),
					"moduleDate");

			searchCondition.setStartDate(DateUtil.prevMonth(input.getSearchOption()));
			searchCondition.setEndDate(new Date());
		}

		criteria.addCriterion("s.portal_id=", user.getPortalId(), "portalId");
		searchCondition.setPortalId(user.getPortalId());

		return searchUtil;
	}

}
