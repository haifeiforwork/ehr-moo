package com.lgcns.ikep4.support.activitystream.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.model.ActivityCode;
import com.lgcns.ikep4.support.activitystream.model.ActivityStream;
import com.lgcns.ikep4.support.activitystream.model.ActivityStreamSearchCondition;
import com.lgcns.ikep4.support.activitystream.service.ActivityCodeService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * ActivityStream 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityStreamController.java 3386 2011-03-18 07:16:40Z
 *          handul32 $
 */
@Controller
@RequestMapping(value = "/support/activitystream")
public class ActivityStreamController extends BaseController {
	
	/**
	 * ActivityStream 서비스
	 */
	@Autowired
	private ActivityStreamService activityStreamService;
	
	/**
	 * Activity Code 서비스
	 */
	@Autowired
	private ActivityCodeService activityCodeService;
	
	/**
	 * Group 서비스
	 */
	@Autowired
	private GroupService groupService;


	/**
	 * 엑티비티스트림 리스트 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getListForActivityStream.do")
	public ModelAndView getListForActivityStream() {

		ModelAndView mav = new ModelAndView("support/activitystream/listForActivityStream");

		ActivityStreamSearchCondition searchCondition = new ActivityStreamSearchCondition();

		User user = (User) getRequestAttribute("ikep.user");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("localeCode", user.getLocaleCode());
		map.put("userId", user.getUserId());
		List<Group> groupList = groupService.selectUserGroup(map);

		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setRegisterId(user.getUserId());

		mav.addObject("groupList", groupList);
		mav.addObject("searchCondition", searchCondition);

		return mav;
	}

	/**
	 * 엑티비티스트림 리스트 검색
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/getSubListForActivityStream.do")
	public ModelAndView getSubListForActivityStream(ActivityStreamSearchCondition searchCondition) {

		ModelAndView mav = new ModelAndView("/support/activitystream/subListForActivityStream");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			searchCondition.setUserLocaleCode(user.getLocaleCode());
			searchCondition.setRegisterId(user.getUserId());

			List<String> groupList = new ArrayList<String>();
			if (searchCondition.getGroupList() != null) {
				for (String group : searchCondition.getGroupList()) {
					if (!StringUtil.isEmpty(group)) {
						groupList.add(group);
					}
				}
				searchCondition.setGroupList(groupList);
			}

			// 사용자 코드값이 있는지 체크함
			List<ActivityCode> userActivityCodeList = activityCodeService.select(user);

			boolean isUserCode = false;
			for (ActivityCode activityCode : userActivityCodeList) {
				if (!StringUtil.isEmpty(activityCode.getSubscriptionActivityCode())) {
					isUserCode = true;
				}
			}

			if (!isUserCode) {
				searchCondition.setIsUserCode("");
			}
			else {
				searchCondition.setIsUserCode("userCode");
			}

			SearchResult<ActivityStream> searchResult = activityStreamService.getAll(searchCondition, user);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());

		} catch (Exception ex) {
			// ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

}
