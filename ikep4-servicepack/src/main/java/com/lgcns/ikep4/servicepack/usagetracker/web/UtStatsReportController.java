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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.servicepack.usagetracker.model.Code;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginLog;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtMenu;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResult;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtLoginLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuLogService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtMenuService;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtStatisticsService;
import com.lgcns.ikep4.servicepack.usagetracker.util.CalUtils;
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
 * @version $Id: UtStatsReportController.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Controller
@RequestMapping(value = "/servicepack/usagetracker/stats/")
public class UtStatsReportController extends BaseUsageTrackerController {

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
	public ModelAndView login(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/stats/login");
		UtResult data = new UtResult();

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_LOGIN);

			data = reportStatus(result, searchCondition);
			mav.addObject("result", data);
			mav.addObject("searchResult", data.getUtLoginLog());
			mav.addObject("searchCondition", data.getUtLoginLog().getSearchCondition());

			return mav;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav.addObject("result", data);
	}

	@RequestMapping(value = "/browser")
	public ModelAndView browser(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/stats/browser");
		UtResult data = new UtResult();

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_BROWSE);
			data = reportStatus(result, searchCondition);

			//Usage count 제일 큰거 선택
			int usageCount = 0;
			UtStatistics maxStat = new UtStatistics();
			for(UtStatistics statistics : data.getData()){
				if(usageCount < statistics.getUsageCount()){
					maxStat = statistics;
					usageCount = statistics.getUsageCount();
				}
				
			}
			
			data.setMaxUseAge(maxStat.getModuleId());
			
			mav.addObject("result", data);
			mav.addObject("searchResult", data.getUtLoginLog());
			mav.addObject("searchCondition", data.getUtLoginLog().getSearchCondition());

			return mav;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav.addObject("result", data);

	}
	
	@RequestMapping(value = "/sms")
	public ModelAndView sms(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/stats/sms");
		UtResult data = new UtResult();

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_SMS);
			data = reportStatus(result, searchCondition);

			List<UtLoginLog> smsList = new ArrayList<UtLoginLog>();
			List<UtLoginLog> smsTotalList = new ArrayList<UtLoginLog>();
			searchCondition.setSdate(com.lgcns.ikep4.util.lang.DateUtil.getFmtDateString(searchCondition.getStartDate(), "yyyyMMdd"));
			searchCondition.setEdate(com.lgcns.ikep4.util.lang.DateUtil.getFmtDateString(searchCondition.getEndDate(), "yyyyMMdd"));
			searchCondition.setWorkspace("");
			searchCondition.setFlg("1");
			smsList=  utLoginLogService.smsUseListBySearchCondition(searchCondition);
			searchCondition.setFlg("2");
			smsTotalList=  utLoginLogService.smsUseListBySearchCondition(searchCondition);
			//mav.addObject("result", data);
			//mav.addObject("searchResult", data.getUtLoginLog());
			mav.addObject("smsList", smsList);
			mav.addObject("smsTotalList", smsTotalList);
			mav.addObject("searchCondition", searchCondition);

			return mav;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav.addObject("result", data);

	}

	@RequestMapping(value = "/menu")
	public ModelAndView menu(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/stats/menu");
		UtResult data = new UtResult();
		User user = (User) getRequestAttribute("ikep.user");

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_MENU);

			UtMenu utMenu = new UtMenu();
			utMenu.setPortalId(user.getPortalId());
			utMenu.setUsage(UsageTrackerConstance.UT_CONFIG_MENU_USAGE_USE);

			List<UtMenu> utMenuList = utMenuService.comboList(utMenu);

			if (!StringUtils.hasText(result.getMenuId())) {
				if (utMenuList.size() > 0) {
					result.setMenuId(utMenuList.get(0).getMenuId());
				}
			}
			mav.addObject("menuList", utMenuList);

			data = reportStatus(result, searchCondition);
			mav.addObject("result", data);
			mav.addObject("searchResult", data.getUtMenuLog());
			mav.addObject("searchCondition", data.getUtMenuLog().getSearchCondition());

			return mav;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav.addObject("result", data);

	}
	
	@RequestMapping(value = "/simpleMenu")
	public ModelAndView simpleMenu(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/stats/simpleMenu");
		UtResult data = new UtResult();
		User user = (User) getRequestAttribute("ikep.user");

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_MENU);

			UtMenu utMenu = new UtMenu();
			utMenu.setPortalId(user.getPortalId());
			utMenu.setUsage(UsageTrackerConstance.UT_CONFIG_MENU_USAGE_USE);

			List<UtMenu> utMenuList = utMenuService.comboList(utMenu);

			
			mav.addObject("menuList", utMenuList);

			data = simpleReportStatus(result, searchCondition);
			mav.addObject("result", data);
			mav.addObject("searchResult", data.getUtMenuLog());
			mav.addObject("searchCondition", data.getUtMenuLog().getSearchCondition());

			return mav;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mav.addObject("result", data);

	}
	
	@RequestMapping(value = "/simpleMobileMenu")
	public ModelAndView simpleMobileMenu(UtResult result, BindingResult bindingResult, UtSearchCondition searchCondition,
			BindingResult sresult) {
		ModelAndView mav = new ModelAndView("/servicepack/usagetracker/stats/simpleMobileMenu");
		UtResult data = new UtResult();
		User user = (User) getRequestAttribute("ikep.user");

		try {
			result.setProcess(UsageTrackerConstance.MODULE_TYPE_MENU);

			UtMenu utMenu = new UtMenu();
			utMenu.setPortalId(user.getPortalId());
			utMenu.setUsage(UsageTrackerConstance.UT_CONFIG_MENU_USAGE_USE);

			List<UtMenu> utMenuList = utMenuService.mobileComboList(utMenu);
			List<UtMenu> utSubMenuList = null;
			
			if(searchCondition.getMenuId() != null && searchCondition.getMenuId() != ""){
				utMenu.setMenuId(searchCondition.getMenuId());
				utSubMenuList = utMenuService.mobileSubComboList(utMenu);
			}
			mav.addObject("menuList", utMenuList);
			mav.addObject("subMenuList", utSubMenuList);

			data = mobileReportStatus(result, searchCondition);
			mav.addObject("result", data);
			mav.addObject("searchResult", data.getUtMenuLog());
			mav.addObject("searchCondition", data.getUtMenuLog().getSearchCondition());

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

			if (input.getViewOption() == 0) {
				input.setViewOption(MagicNumUtils.ARRAY_SIZE_0);
			}

			if (input.getSearchOption() == 0) {
				input.setSearchOption(MagicNumUtils.MONTH_1);
			}

			SearchUtil searchUtil = getSearchUtil(input, user, searchCondition);

			PropertyUtils.copyProperties(result, input);

			if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_BROWSE) {
				data = utStatisticsService.browseStastic(searchUtil);
				result.setUtLoginLog(utLoginLogService.listBySearchCondition(searchCondition));
			} else if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_SMS) {
				
			} else{
				List<UtStatistics> dbInfoList = utStatisticsService.publicStastic(searchUtil);
				data = getLoginOrMenu(input, result, dbInfoList);

				if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_LOGIN) {
					result.setUtLoginLog(utLoginLogService.listBySearchCondition(searchCondition));
				} else if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_MENU) {
					result.setUtMenuLog(utMenuLogService.listBySearchCondition(searchCondition));
				}
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		if (data.size() <= 0) {
			UtStatistics info = new UtStatistics();
			info.setModuleId("Unknown");
			info.setUsageCount(0);
			info.setDataYn("N");
			data.add(info);
		}

		result.setData(data);

		return result;
	}
	
	public UtResult simpleReportStatus(UtResult input, UtSearchCondition searchCondition) throws IllegalAccessException,
	InvocationTargetException, NoSuchMethodException {
		User user = (User) getRequestAttribute("ikep.user");
		UtResult result = new UtResult();
		List<UtStatistics> data = new ArrayList<UtStatistics>();
		
		try {
		
			if (input.getViewOption() == 0) {
				input.setViewOption(MagicNumUtils.ARRAY_SIZE_0);
			}
		
			if (input.getSearchOption() == 0) {
				input.setSearchOption(MagicNumUtils.MONTH_1);
			}
		
			SearchUtil searchUtil = getSimpleSearchUtil(input, user, searchCondition);
		
			PropertyUtils.copyProperties(result, input);
		
			if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_BROWSE) {
				data = utStatisticsService.browseStastic(searchUtil);
				result.setUtLoginLog(utLoginLogService.listBySearchCondition(searchCondition));
			} else {
				List<UtStatistics> dbInfoList = utStatisticsService.publicStastic(searchUtil);
				data = getLoginOrMenu(input, result, dbInfoList);
		
				if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_LOGIN) {
					result.setUtLoginLog(utLoginLogService.listBySearchCondition(searchCondition));
				} else if (input.getProcess() == UsageTrackerConstance.MODULE_TYPE_MENU) {
					result.setUtMenuLog(utMenuLogService.listBySearchCondition(searchCondition));
				}
			}
		
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		
		if (data.size() <= 0) {
			UtStatistics info = new UtStatistics();
			info.setModuleId("Unknown");
			info.setUsageCount(0);
			info.setDataYn("N");
			data.add(info);
		}
		
		result.setData(data);
		
		return result;
	}
	
	public UtResult mobileReportStatus(UtResult input, UtSearchCondition searchCondition) throws IllegalAccessException,
	InvocationTargetException, NoSuchMethodException {
		UtResult result = new UtResult();
		User user = (User) getRequestAttribute("ikep.user");
		try {
			if (input.getViewOption() == 0) {
				input.setViewOption(MagicNumUtils.ARRAY_SIZE_0);
			}
		
			if (input.getSearchOption() == 0) {
				input.setSearchOption(MagicNumUtils.MONTH_1);
			}
			
			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setViewOption(input.getViewOption());

			Criteria criteria = searchUtil.createCriteria();
			criteria.addCriterion("s.module_type = ", input.getProcess(), "moduleType");
			
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
			result.setUtMenuLog(utMenuLogService.mobileListBySearchCondition(searchCondition));
		
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}
		
		return result;
		}

	private List<UtStatistics> getLoginOrMenu(UtResult input, UtResult result, List<UtStatistics> dbInfoList) {
		List<UtStatistics> data = new ArrayList<UtStatistics>();

		String maxModuleId = "00";
		int maxUsageCount = 0;
		
		if (input.getViewOption() == MagicNumUtils.ARRAY_SIZE_0 || input.getViewOption() == MagicNumUtils.ARRAY_SIZE_1) { // 시간별
			data = CalUtils.makeHourCalendar();
		} else if (input.getViewOption() == MagicNumUtils.ARRAY_SIZE_2
				|| input.getViewOption() == MagicNumUtils.ARRAY_SIZE_3) {// 일별
			data = CalUtils.makeDayCalendar();
		} else if (input.getViewOption() == MagicNumUtils.ARRAY_SIZE_4
				|| input.getViewOption() == MagicNumUtils.ARRAY_SIZE_5) {// 월별
			data = CalUtils.makeMonthCalendar();
		}

		for (UtStatistics utStatistics : data) {
			for (UtStatistics dbinfo : dbInfoList) {
				String dbinfoModuleId = dbinfo.getModuleId();
				if(dbinfoModuleId.length() == 1) {
					dbinfoModuleId = "0" + dbinfoModuleId;
				}
				
				if (utStatistics.getModuleId().equals(dbinfoModuleId)) {
					//utStatistics.setModuleId(dbinfo.getModuleId());
					utStatistics.setUsageCount(dbinfo.getUsageCount());
					break;
				}
			}

			if (utStatistics.getUsageCount() > maxUsageCount) {
				maxUsageCount = utStatistics.getUsageCount();
				maxModuleId = utStatistics.getModuleId();
			}

			result.setTotalSum(result.getTotalSum() + utStatistics.getUsageCount());
		}

		if ((input.getViewOption() == MagicNumUtils.ARRAY_SIZE_1 || input.getViewOption() == MagicNumUtils.ARRAY_SIZE_3 || input
				.getViewOption() == MagicNumUtils.ARRAY_SIZE_5) && result.getTotalSum() > 0) {
			for (UtStatistics utStatistics : data) {
				utStatistics
						.setUsageCount((utStatistics.getUsageCount() * MagicNumUtils.MOD_100 / result.getTotalSum()));
			}
		}

		if (data.size() > 0) {
			String fChar = "0", sChar = "0";
			try {fChar = String.valueOf(maxModuleId.charAt(0)); } catch(Exception e){}
			try {sChar = String.valueOf(maxModuleId.charAt(1)); } catch(Exception e){}
			result.setMinUseAge(fChar);
			result.setMaxUseAge(sChar);
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

		// 메뉴별 로그
		if (StringUtils.hasText(input.getMenuId())) {
			criteria.addCriterion("s.module_id=", input.getMenuId(), "moduleId");
			searchCondition.setMenuId(input.getMenuId());
		}

		criteria.addCriterion("s.portal_id=", user.getPortalId(), "portalId");
		searchCondition.setPortalId(user.getPortalId());

		return searchUtil;
	}
	
	private SearchUtil getMobileSearchUtil(UtResult input, User user, UtSearchCondition searchCondition) {
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

		// 메뉴별 로그
		if (StringUtils.hasText(input.getMenuId())) {
			criteria.addCriterion("s.module_id=", input.getMenuId(), "moduleId");
			searchCondition.setMenuId(input.getMenuId());
		}

		criteria.addCriterion("s.portal_id=", user.getPortalId(), "portalId");
		searchCondition.setPortalId(user.getPortalId());

		return searchUtil;
	}
	
	private SearchUtil getSimpleSearchUtil(UtResult input, User user, UtSearchCondition searchCondition) {
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setViewOption(input.getViewOption());

		Criteria criteria = searchUtil.createCriteria();
		criteria.addCriterion("s.module_type = ", input.getProcess(), "moduleType");

		if (input.getStartDate() == null || input.getEndDate() == null) {
			input.setStartDate(DateUtil.getToday());
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

			searchCondition.setStartDate(DateUtil.getToday());
			searchCondition.setEndDate(new Date());
		}

		// 메뉴별 로그
		if (StringUtils.hasText(input.getMenuId())) {
			criteria.addCriterion("s.module_id=", input.getMenuId(), "moduleId");
			searchCondition.setMenuId(input.getMenuId());
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
}
