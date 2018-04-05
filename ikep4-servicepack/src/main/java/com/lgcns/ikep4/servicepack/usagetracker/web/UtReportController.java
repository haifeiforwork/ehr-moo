/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.servicepack.survey.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtStatisticsService;
import com.lgcns.ikep4.servicepack.usagetracker.util.CalUtils;
import com.lgcns.ikep4.servicepack.usagetracker.util.Criteria;
import com.lgcns.ikep4.servicepack.usagetracker.util.DateUtil;
import com.lgcns.ikep4.servicepack.usagetracker.util.SearchUtil;
import com.lgcns.ikep4.servicepack.usagetracker.util.UsageTrackerConstance;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 메뉴 레포트
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtReportController.java 16953 2011-11-01 06:52:07Z yu_hs $
 */
@Controller
@RequestMapping(value = "/servicepack/usagetracker/")
public class UtReportController extends BaseUsageTrackerController {

	// 현황
	@Autowired
	UtStatisticsService utStatisticsService;

	@RequestMapping(value = "/index")
	public ModelAndView init() {
		
        utStatisticsService.utStatisticsBatch();
        
		return new ModelAndView("/servicepack/usagetracker/index");
	}
	
	@RequestMapping(value = "/simpleIndex")
	public ModelAndView simpleIndex() {
		
        utStatisticsService.utStatisticsBatch();
        
		return new ModelAndView("/servicepack/usagetracker/simpleIndex");
	}

	/**
	 * 메인 모듈 ajax
	 * 
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/main/ajax/{process}/report", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<UtStatistics> reportMainAjax(@PathVariable("process") int process) {
		User user = (User) getRequestAttribute("ikep.user");
		List<UtStatistics> result = new ArrayList<UtStatistics>();

		try {
			SearchUtil searchUtil = new SearchUtil();
			Criteria criteria = searchUtil.createCriteria();
			criteria.addCriterion("s.module_type = ", process, "moduleType");
			criteria.addCriterionForJDBCDate("s.MODULE_DATE >=", DateUtil.prevMonth(MagicNumUtils.MONTH_1), "moduleDate");
			criteria.addCriterion("s.portal_id=", user.getPortalId(), "portalId");

			if (process == UsageTrackerConstance.MODULE_TYPE_LOGIN) {
				List<UtStatistics> dbInfoList = utStatisticsService.loginMainStastic(searchUtil);

				// 요일별 셋팅
				result = CalUtils.makeWeekCalendar();

				for (UtStatistics utStatistics : result) {
					for (UtStatistics dbinfo : dbInfoList) {
						if (utStatistics.getModuleId().equals(dbinfo.getModuleId())) {
							utStatistics.setModuleId(dbinfo.getModuleId());
							utStatistics.setUsageCount(dbinfo.getUsageCount());
							break;
						}
					}

				}

			} else if (process == UsageTrackerConstance.MODULE_TYPE_BROWSE) {
				result = utStatisticsService.browseMainStastic(searchUtil);
			} else if (process == UsageTrackerConstance.MODULE_TYPE_MENU) {
				result = utStatisticsService.menuMainStastic(searchUtil);
			} else if (process == UsageTrackerConstance.MODULE_TYPE_PORTLET) {
				searchUtil.setSearchConditionString(user.getLocaleCode());
				result = utStatisticsService.portletMainStastic(searchUtil);
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		if (result.size() <= 0) {
			UtStatistics info = new UtStatistics();
			info.setModuleId("Unknown");
			info.setUsageCount(0);
			info.setDataYn("N");
			result.add(info);
		}

		return result;
	}
}
