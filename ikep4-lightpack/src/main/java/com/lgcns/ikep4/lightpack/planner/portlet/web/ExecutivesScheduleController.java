package com.lgcns.ikep4.lightpack.planner.portlet.web;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

@Controller
@RequestMapping(value = "/lightpack/planner/portlet/executivesSchedule")
public class ExecutivesScheduleController extends BaseController{

	@Autowired
	private PortalFavoriteService favoriteService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/normalView.do")
	public ModelAndView normalView(@RequestParam String portletConfigId, @RequestParam(value="executivesScheduleDate", required=false) String personalScheduleDate, @RequestParam(value="executivesUserId", required=false) String personalUserId) {
		ModelAndView mav = new ModelAndView("lightpack/planner/portlet/executivesSchedule/normalView");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		
		
		List<User> teamLeaderList = userService.listTeamLeader(user.getGroupId());
		String tmpExeUser = "";
		int cnt = 0;
		if(teamLeaderList != null && teamLeaderList.size() > 0) {
			for(User tmpUser : teamLeaderList) {
				if(cnt < 1){
					tmpExeUser = tmpUser.getUserId();
				}
				cnt++;
			}
		}
		
		if(StringUtil.isEmpty(personalUserId)) {
			personalUserId = tmpExeUser + "^M";
		}
		
		PortalFavoriteSearchCondition searchCondition = new PortalFavoriteSearchCondition();
		searchCondition.setFieldName("jobTitleName");
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setRegisterId(user.getUserId());
		searchCondition.setEndRowIndex(1000000);

		SearchResult<PortalFavorite> searchResultForPeople = favoriteService.getSummaryForPeople(searchCondition);

		String today = DateUtil.getToday("yyyyMMdd");
		
		String dateValue1 = today;
		String dateValue2 = DateUtil.getNextDate(today, 1, "yyyyMMdd");
		String dateValue3 = DateUtil.getNextDate(today, 2, "yyyyMMdd");
		String dateValue4 = DateUtil.getNextDate(today, 3, "yyyyMMdd");
		String dateValue5 = DateUtil.getNextDate(today, 4, "yyyyMMdd");
		String dateValue6 = DateUtil.getNextDate(today, 5, "yyyyMMdd");
		String dateValue7 = DateUtil.getNextDate(today, 6, "yyyyMMdd");
		
		String dateText1 = DateUtil.getFmtDateString(dateValue1, "MM.dd", Locale.ENGLISH);
		String dateText2 = DateUtil.getFmtDateString(dateValue2, "MM.dd", Locale.ENGLISH);
		String dateText3 = DateUtil.getFmtDateString(dateValue3, "MM.dd", Locale.ENGLISH);
		String dateText4 = DateUtil.getFmtDateString(dateValue4, "MM.dd", Locale.ENGLISH);
		String dateText5 = DateUtil.getFmtDateString(dateValue5, "MM.dd", Locale.ENGLISH);
		String dateText6 = DateUtil.getFmtDateString(dateValue6, "MM.dd", Locale.ENGLISH);
		String dateText7 = DateUtil.getFmtDateString(dateValue7, "MM.dd", Locale.ENGLISH);
		
		String dateWeekDay1 = DateUtil.getFmtDateString(dateValue1, "EEE.", Locale.ENGLISH);
		String dateWeekDay2 = DateUtil.getFmtDateString(dateValue2, "EEE.", Locale.ENGLISH);
		String dateWeekDay3 = DateUtil.getFmtDateString(dateValue3, "EEE.", Locale.ENGLISH);
		String dateWeekDay4 = DateUtil.getFmtDateString(dateValue4, "EEE.", Locale.ENGLISH);
		String dateWeekDay5 = DateUtil.getFmtDateString(dateValue5, "EEE.", Locale.ENGLISH);
		String dateWeekDay6 = DateUtil.getFmtDateString(dateValue6, "EEE.", Locale.ENGLISH);
		String dateWeekDay7 = DateUtil.getFmtDateString(dateValue7, "EEE.", Locale.ENGLISH);
		
		if(StringUtil.isEmpty(personalScheduleDate)) {
			personalScheduleDate = today;
		}
		
		mav.addObject("dateValue1", dateValue1);
		mav.addObject("dateValue2", dateValue2);
		mav.addObject("dateValue3", dateValue3);
		mav.addObject("dateValue4", dateValue4);
		mav.addObject("dateValue5", dateValue5);
		mav.addObject("dateValue6", dateValue6);
		mav.addObject("dateValue7", dateValue7);
		
		mav.addObject("dateText1", dateText1);
		mav.addObject("dateText2", dateText2);
		mav.addObject("dateText3", dateText3);
		mav.addObject("dateText4", dateText4);
		mav.addObject("dateText5", dateText5);
		mav.addObject("dateText6", dateText6);
		mav.addObject("dateText7", dateText7);
		
		mav.addObject("dateWeekDay1", dateWeekDay1);
		mav.addObject("dateWeekDay2", dateWeekDay2);
		mav.addObject("dateWeekDay3", dateWeekDay3);
		mav.addObject("dateWeekDay4", dateWeekDay4);
		mav.addObject("dateWeekDay5", dateWeekDay5);
		mav.addObject("dateWeekDay6", dateWeekDay6);
		mav.addObject("dateWeekDay7", dateWeekDay7);
		
		mav.addObject("personalScheduleDate", personalScheduleDate);
		mav.addObject("personalUserId", personalUserId);
		mav.addObject("selectPersonalUserId", personalUserId.split("\\^")[0]);
		mav.addObject("selectPersonalUserType", personalUserId.split("\\^")[1]);
		mav.addObject("portletConfigId", portletConfigId);
		
		mav.addObject("searchResultForPeople", searchResultForPeople);
		mav.addObject("teamLeaderList", teamLeaderList);
		
		return mav;
	}
}