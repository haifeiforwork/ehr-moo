package com.lgcns.ikep4.support.sms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.sms.model.SmsCode;
import com.lgcns.ikep4.support.sms.model.SmsConfig;
import com.lgcns.ikep4.support.sms.search.SmsSearchCondition;
import com.lgcns.ikep4.support.sms.service.SmsConfigService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * sms 사용자별 건수 설정 controller
 *
 * @author 
 * @version $Id: SmsConfigController.java 3628 2011-07-27 01:01:50Z dev07 $
 */
@Controller
@RequestMapping(value = "/support/smsConfig")
public class SmsConfigController extends BaseController {
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	/**
	 * sms 사용자별 건수 설정 리스트
	 * @param searchCondition
	 * @return
	 */
	@RequestMapping(value = "/listSmsConfig.do")
	public ModelAndView listBoardView(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "SMS", operationName = { "MANAGE" }, resourceName = "SMS")) AccessingResult accessResult,
			SmsSearchCondition searchCondition) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		ModelAndView mav = new ModelAndView("/support/smsConfig/listSmsConfig");
		
		SmsCode smsCode = new SmsCode();
		
		SmsConfig smsDefConfig = smsConfigService.readSmsConfig("sms_def_count");
		SearchResult<SmsConfig> searchResult = smsConfigService.listSmsConfig(searchCondition);

		mav.addObject("smsDefConfig", smsDefConfig);
		mav.addObject("smsCode", smsCode);
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		
		return mav;
	}
	
	/**
	 * sms 사용자별 건수 등록/수정
	 * @param smsConfig
	 * @return
	 */
	@RequestMapping(value = "/createSmsConfig.do")
	public @ResponseBody String createSmsConfig(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "SMS", operationName = { "MANAGE" }, resourceName = "SMS")) AccessingResult accessResult,
			SmsConfig smsConfig) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		User sessionuser = (User) getRequestAttribute("ikep.user");
		smsConfig.setRegisterId(sessionuser.getUserId());
		smsConfig.setRegisterName(sessionuser.getUserName());
		smsConfig.setUpdaterId(sessionuser.getUserId());
		smsConfig.setUpdaterName(sessionuser.getUserName());
		
		SmsConfig smsConfigCheck = smsConfigService.readSmsConfig(smsConfig.getUserId());
		
		if(smsConfigCheck == null) {
			//등록
			smsConfigService.createSmsConfig(smsConfig);
		} else {
			//수정
			smsConfigService.updateSmsConfig(smsConfig);
		}
		
		return "success";
	}
	
	/**
	 * sms 사용자별 건수 등록/수정
	 * @param smsConfig
	 * @return
	 */
	@RequestMapping(value = "/removeSmsConfig.do")
	public @ResponseBody String removeSmsConfig(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "SMS", operationName = { "MANAGE" }, resourceName = "SMS")) AccessingResult accessResult,
			SmsConfig smsConfig) {
		
		if (!accessResult.isAccess()) { // true : 승인, false : 비승인
			throw new IKEP4AuthorizedException(); 
		}
		
		smsConfigService.removeSmsConfig(smsConfig.getUserIds());
		
		return "success";
	}
}