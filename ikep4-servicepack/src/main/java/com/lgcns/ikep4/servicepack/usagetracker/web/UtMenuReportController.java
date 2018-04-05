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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.Code;
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
import com.lgcns.ikep4.support.searchpreprocessor.util.PageCons;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 메뉴 레포트
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuReportController.java 16953 2011-11-01 06:52:07Z yu_hs $
 */
@Controller
@RequestMapping(value = "/servicepack/usagetracker/rank/")
public class UtMenuReportController extends BaseUsageTrackerController {

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


	@RequestMapping(value = "/menu")
	public ModelAndView menu(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/rank/menu");
		UtResult data = new UtResult();

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_MENU);
			searchCondition.setProcess( UsageTrackerConstance.MODULE_TYPE_MENU );

			data = reportStatus(result, searchCondition);
			mav.addObject("result", data);
			mav.addObject("searchResult", data.getMenuOrPortletLog());
			mav.addObject("searchCondition", data.getMenuOrPortletLog().getSearchCondition());

			return mav;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav.addObject("result", data);

	}
	
	@RequestMapping(value = "/simpleMenu")
	public ModelAndView simpleMenu(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/rank/simpleMenu");
		UtResult data = new UtResult();

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_MENU);
			searchCondition.setProcess( UsageTrackerConstance.MODULE_TYPE_MENU );

			data = reportStatus(result, searchCondition);
			mav.addObject("result", data);
			mav.addObject("searchResult", data.getMenuOrPortletLog());
			mav.addObject("searchCondition", data.getMenuOrPortletLog().getSearchCondition());

			return mav;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav.addObject("result", data);

	}
	
	
	@RequestMapping(value = "/portlet")
	public ModelAndView portlet(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/rank/portlet");
		UtResult data = new UtResult();

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_PORTLET);
			searchCondition.setProcess( UsageTrackerConstance.MODULE_TYPE_PORTLET );
			
			data = reportStatus(result, searchCondition);
			mav.addObject("result", data);
			mav.addObject("searchResult", data.getMenuOrPortletLog());
			mav.addObject("searchCondition", data.getMenuOrPortletLog().getSearchCondition());

			return mav;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav.addObject("result", data);

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
	public UtResult reportStatus(UtResult input, UtSearchCondition searchCondition) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		User user = (User) getRequestAttribute("ikep.user");
		UtResult result = new UtResult();
		List<UtStatistics> data = new ArrayList<UtStatistics>();

		try {

			if (input.getSearchOption() == 0) {
				input.setSearchOption(MagicNumUtils.MONTH_1);
			}

			SearchUtil searchUtil = getSearchUtil(input, user, searchCondition);
			PropertyUtils.copyProperties(result, input);
			searchCondition.setOredCriteria( searchUtil.getOredCriteria() );

			if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_MENU) {
				data =  utStatisticsService.menuMainStastic(searchUtil);
			} else if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_PORTLET){
				searchUtil.setSearchConditionString(user.getLocaleCode());
				data =  utStatisticsService.portletMainStastic(searchUtil);
			}
			
			if( data.size() > 0 )
			{
				if(input.getProcess() == UsageTrackerConstance.MODULE_TYPE_MENU) {
					result.setMaxUseAge( data.get(data.size()-1).getModuleId() );
				} else {
				result.setMaxUseAge( data.get(0).getModuleId() );
				}
				
				result.setMinUseAge( utStatisticsService.minBySearchCondition(searchCondition));
			}
			
			searchCondition.setSearchConditionString(user.getLocaleCode());
			result.setMenuOrPortletLog(utStatisticsService.listBySearchCondition(searchCondition));

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		if (data.size() <= 0) {
			UtStatistics info = new UtStatistics();
			info.setModuleId("Unknown");
			info.setUsageCount(0);
			data.add(info);
		}

		result.setData(data);

		return result;
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

	/**
	 * 코드생성
	 */
	@ModelAttribute("viewOptionList")
	public List<Code<Integer>> getViewOptionList() {
		List<Code<Integer>> viewOptionList = new ArrayList<Code<Integer>>();
		viewOptionList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_0,
				"ui.servicepack.usagetracker.common.viewoption.0"));
		viewOptionList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_1,
				"ui.servicepack.usagetracker.common.viewoption.1"));
		viewOptionList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_2,
				"ui.servicepack.usagetracker.common.viewoption.2"));
		viewOptionList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_3,
				"ui.servicepack.usagetracker.common.viewoption.3"));
		viewOptionList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_4,
				"ui.servicepack.usagetracker.common.viewoption.4"));
		viewOptionList.add(new Code<Integer>(MagicNumUtils.ARRAY_SIZE_5,
				"ui.servicepack.usagetracker.common.viewoption.5"));

		return viewOptionList;
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
	
	/**
	 * 현재 사용되고 있는 포틀릿 통계
	 * @param result
	 * @param bindingResult
	 * @param searchCondition
	 * @param sresult
	 * @return
	 */
	@RequestMapping(value = "/todayPortlet.do")
	public ModelAndView todayPortlet(UtSearchCondition searchCondition) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/rank/todayPortlet");
		
		SearchResult<UtStatistics> searchResult = utStatisticsService.listTodayPortlet(searchCondition);
		List<UtStatistics> data = utStatisticsService.listTodayPortletTop10();
		
		String maxUseAge = "";
		String minUseAge = "";
		
		if (data != null && data.size() > 0) {
			maxUseAge = data.get(data.size()-1).getModuleId();
			minUseAge = utStatisticsService.minUseageTodayPortlet();
		}
		
		mav.addObject("data", data);
		mav.addObject("maxUseAge", maxUseAge);
		mav.addObject("minUseAge", minUseAge);
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());

		return mav;
	}
}
