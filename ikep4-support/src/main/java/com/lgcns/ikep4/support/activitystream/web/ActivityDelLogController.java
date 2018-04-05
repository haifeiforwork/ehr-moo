package com.lgcns.ikep4.support.activitystream.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.activitystream.model.ActivityDelLog;
import com.lgcns.ikep4.support.activitystream.service.ActivityDelLogService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * ActivityDelLog 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityCodeController.java 3386 2011-03-18 07:16:40Z handul32
 *          $
 */
@Controller
@RequestMapping(value = "/support/activitystream")
public class ActivityDelLogController extends BaseController {

	/**
	 * Activity Delete Log Service
	 */
	@Autowired
	private ActivityDelLogService activityDelLogService;

	/**
	 * 엑티비티스트림 삭제 로그
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "/getActivityDelLog.do")
	public ModelAndView getUserActivityCode(String year, String month) {

		ModelAndView mav = new ModelAndView("support/activitystream/listForActivityDelLog");

		User user = (User) getRequestAttribute("ikep.user");

		String yearStr = year;
		String monthStr = month;

		String curDate = DateUtil.getToday("yyyyMMdd");
		if (StringUtil.isEmpty(year)) {
			yearStr = curDate.substring(0, 4);
		}
		if (StringUtil.isEmpty(month)) {
			monthStr = curDate.substring(4, 6);
		}

		ActivityDelLog activityDelLog = new ActivityDelLog();

		activityDelLog.setUserId(user.getUserId());
		activityDelLog.setYear(year);
		activityDelLog.setMonth(month);

		List<ActivityDelLog> list = activityDelLogService.select(activityDelLog);

		mav.addObject("year", yearStr);
		mav.addObject("month", monthStr);
		mav.addObject("activityDelLogList", list);

		return mav;
	}

}
