package com.lgcns.ikep4.support.activitystream.web;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.activitystream.model.ActivityCode;
import com.lgcns.ikep4.support.activitystream.service.ActivityCodeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * ActivityCode 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityCodeController.java 3386 2011-03-18 07:16:40Z handul32
 *          $
 */
@Controller
@RequestMapping(value = "/support/activitystream")
public class ActivityCodeController extends BaseController {
	
	/**
	 * Activity Code Service
	 */
	@Autowired
	private ActivityCodeService activityCodeService;

	/**
	 * 사용자 Activiey Code 뷰 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserActivityCode.do")
	public ModelAndView getUserActivityCode() {

		ModelAndView mav = new ModelAndView("support/activitystream/listForUserActivityCode");

		User user = (User) getRequestAttribute("ikep.user");

		List<ActivityCode> userActivityCodeList = activityCodeService.select(user);

		mav.addObject("userActivityCodeList", userActivityCodeList);

		return mav;
	}

	/**
	 * 사용자 Activiey Code 저장 처리
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/saveUserActivityCode.do")
	public ModelAndView saveUserActivityCode(ActivityCode activityCode) {

		ModelAndView mav = new ModelAndView("support/activitystream/listForUserActivityCode");

		try {

			User user = (User) getRequestAttribute("ikep.user");

			activityCode.setRegisterId(user.getUserId());
			activityCode.setRegisterName(user.getUserName());
			activityCode.setUpdaterId(user.getUserId());
			activityCode.setUpdaterName(user.getUserName());
			activityCode.setUserId(user.getUserId());

			activityCodeService.create(activityCode);

			List<ActivityCode> userActivityCodeList = activityCodeService.select(user);

			mav.addObject("userActivityCodeList", userActivityCodeList);

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}
	
	
	/**
	 * Activiey Config 뷰 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getActivityConfig.do")
	public ModelAndView getActivityConfig() {

		ModelAndView mav = new ModelAndView("support/activitystream/listForActivityConfig");
		
		User user = (User) getRequestAttribute("ikep.user");
		
		ActivityCode activityCode = new ActivityCode();
		activityCode.setUserId(user.getUserId());
		
		ActivityCode object = activityCodeService.selectConfig(activityCode);
		
		if(object == null) {
			Properties prop = PropertyLoader.loadProperties("/configuration/activitystream-conf.properties");
			String defaultMaxSaveValue = prop.getProperty("ikep4.support.activitystream.maxSaveValue");
			
			object = new ActivityCode();
			object.setMaxSaveValue(defaultMaxSaveValue);
		}

		mav.addObject("activityCode", object);

		return mav;
	}

	/**
	 * Activiey Config 저장 처리
	 * 
	 * @param searchCondition
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "/saveActivityConfig.do")
	public ModelAndView saveActivityConfig(ActivityCode activityCode) {

		ModelAndView mav = new ModelAndView("support/activitystream/listForActivityConfig");

		try {
			
			User user = (User) getRequestAttribute("ikep.user");
			
			activityCode.setUserId(user.getUserId());
			activityCode.setRegisterId(user.getUserId());
			activityCode.setRegisterName(user.getUserName());
			activityCode.setUpdaterId(user.getUserId());
			activityCode.setUpdaterName(user.getUserName());
			
			activityCodeService.removeConfig(activityCode);
			activityCodeService.createConfig(activityCode);

			ActivityCode object = activityCodeService.selectConfig(activityCode);

			mav.addObject("activityCode", object);

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

}
