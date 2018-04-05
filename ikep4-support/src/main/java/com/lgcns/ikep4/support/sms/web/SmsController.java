/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.sms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.sms.constants.SmsConstants;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.model.SmsConfig;
import com.lgcns.ikep4.support.sms.search.SmsReceiverSearchCondition;
import com.lgcns.ikep4.support.sms.service.SmsConfigService;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙
 * @version $Id: SmsController.java 14488 2011-06-08 00:59:08Z loverfairy $
 */
@Controller
@RequestMapping(value = "/support/sms")
public class SmsController extends BaseController {

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private UserService userService;

	/**
	 * SMS 전송
	 * 
	 * @param sms
	 * @param result
	 * @param status
	 * @return
	 */	
	@RequestMapping(value = "/createSms.do", method = RequestMethod.POST)
	public String onSubmit(Sms sms, BindingResult result, SessionStatus status) {		
		User user = (User) getRequestAttribute("ikep.user");
		sms.setRegisterId(user.getUserId());
		sms.setRegisterName(user.getUserName());
		
		sms.setReceiverPhonenos(sms.getReceiverPhoneno().split("[-]"));
		sms.setReceiverIds(sms.getReceiverId().split("[-]"));
		
		if(!StringUtil.isEmpty(user.getMobile())){
			sms.setSenderPhoneno(user.getMobile());
		}
		else{
			sms.setSenderPhoneno("****");
		}
		smsService.create(sms); 	
 
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return "redirect:sms.do";
	}

	/**
	 * 발신함 목록
	 * 
	 * @param searchCondition
	 * @return
	 */	
	@RequestMapping(value = "/listSms.do")
	public ModelAndView listBoardView(SmsReceiverSearchCondition searchCondition) {   
		User user = (User) getRequestAttribute("ikep.user");
		searchCondition.setRegisterId(user.getUserId());
		if ( "2".equals(searchCondition.getCurMode())) {
			searchCondition.setPagePerRecord(SmsConstants.LIST_SMS_SCREEN_PAGE_PER_RECORD); //4(스크린형)
		} else {
			searchCondition.setPagePerRecord(SmsConstants.LIST_SMS_PAGE_PER_RECORD); //10(게시판형)
		}

		SearchResult<Sms> searchResult = this.smsService.listSmsReceiverBySearchCondition(searchCondition);   

		return new ModelAndView("/support/sms/listSms")
		.addObject("searchResult", searchResult)
		.addObject("searchCondition", searchResult.getSearchCondition());				
	}
	
	/**
	 * SMS 팝업 화면
	 * 
	 * @param searchCondition
	 * @param result
	 * @param gubun
	 * @param receiverId
	 * @param status
	 * @return
	 */		
	@RequestMapping(value = "/sms.do", method = RequestMethod.GET)
	public ModelAndView sms(
			@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "SMS", operationName = { "MANAGE" }, resourceName = "SMS")) AccessingResult accessResult,
			SmsReceiverSearchCondition searchCondition, BindingResult result, @RequestParam(value = "gubun", required = false) String gubun,@RequestParam(value = "receiverId", required = false) String receiverId, SessionStatus status) {
		User user = (User) getRequestAttribute("ikep.user");

		searchCondition.setRegisterId(user.getUserId());
		searchCondition.setPagePerRecord(SmsConstants.RECENT_SMS_PAGE_PER_RECORD); //최근방송내역(6)
		searchCondition.setGubun(gubun); 		   //파라미터로 넘어온 인자 gubun(내부사용자일경우 1, 외부사용자일 경우 2)
		searchCondition.setReceiverId(receiverId); //파라미터로 넘어온 인자 receiverId(내부사용자일경우 userId, 외부사용자일 경우 phoneNum)
		ModelAndView mav = new ModelAndView("/support/sms/sms");

		SearchResult<Sms> searchResult = smsService.listSmsReceiverBySearchCondition(searchCondition);

		List<Sms> listRecentBottom = smsService.list(searchCondition);
		int totalRecentBottomCount = smsService.listRecentBottomCount(searchCondition);
		
		int smsCount = 0;  
		SmsConfig smsConfig = smsConfigService.readSmsConfig(user.getUserId());
		
		if(smsConfig == null) {
			SmsConfig smsDefConfig = smsConfigService.readSmsConfig("sms_def_count");
			
			if(smsDefConfig == null) {
				smsCount = 50;
			} else {
				smsCount = smsDefConfig.getSmsCount();
			}
		} else {
			smsCount = smsConfig.getSmsCount();
		}
		User receiveUser1 = new User();
		User receiveUser2 = new User();
		receiveUser1 = userService.read(receiverId);
		if(receiveUser1 == null){
			receiveUser2.setUserStatus("");
			receiveUser2.setUserId("");
			receiveUser2.setUserName("");
			receiveUser2.setMobile("");
			mav.addObject("receiveUser", receiveUser2);
		}else{
			mav.addObject("receiveUser", receiveUser1);
		}
		
		mav.addObject("smsCount", smsCount);
		mav.addObject("smsAdmin", accessResult.isAccess());
		mav.addObject("smsRecentBottom", listRecentBottom);	
		mav.addObject("searchResult", searchResult);
		mav.addObject("smsRecentBottomCount", totalRecentBottomCount);	
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		return mav;
	}

	@RequestMapping(value = "/messengerSms.do", method = RequestMethod.GET)
	public ModelAndView messengerSms(@RequestParam(value = "gubun", required = false) String gubun,
			@RequestParam(value = "receiverId", required = false) String receiverId) {
		
		
		
		User user = (User) getRequestAttribute("ikep.user");

		SmsReceiverSearchCondition searchCondition = new SmsReceiverSearchCondition();
		searchCondition.setRegisterId(user.getUserId());
		searchCondition.setPagePerRecord(SmsConstants.RECENT_SMS_PAGE_PER_RECORD); //최근방송내역(6)
		searchCondition.setGubun(gubun); 		   //파라미터로 넘어온 인자 gubun(내부사용자일경우 1, 외부사용자일 경우 2)
		searchCondition.setReceiverId(receiverId); //파라미터로 넘어온 인자 receiverId(내부사용자일경우 userId, 외부사용자일 경우 phoneNum)
		ModelAndView mav = new ModelAndView("/support/sms/sms");

		SearchResult<Sms> searchResult = smsService.listSmsReceiverBySearchCondition(searchCondition);

		List<Sms> listRecentBottom = smsService.list(searchCondition);
		int totalRecentBottomCount = smsService.listRecentBottomCount(searchCondition);
		
		int smsCount = 0;  
		SmsConfig smsConfig = smsConfigService.readSmsConfig(user.getUserId());
		
		if(smsConfig == null) {
			SmsConfig smsDefConfig = smsConfigService.readSmsConfig("sms_def_count");
			
			if(smsDefConfig == null) {
				smsCount = 50;
			} else {
				smsCount = smsDefConfig.getSmsCount();
			}
		} else {
			smsCount = smsConfig.getSmsCount();
		}
		
		Boolean hasSMSAdminPermission = this.aclService.hasSystemPermission("SMS", "MANAGE","SMS", user.getUserId());
		
		
		mav.addObject("smsCount", smsCount);
		mav.addObject("smsAdmin", hasSMSAdminPermission);
		mav.addObject("smsRecentBottom", listRecentBottom);	
		mav.addObject("searchResult", searchResult);
		mav.addObject("smsRecentBottomCount", totalRecentBottomCount);	
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		return mav;
	}
	
	/**
	 * 아이디별 당월발송건수
	 * 
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @return
	 */		
	@RequestMapping(value = "/getRemainSmsCount.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody int remainSmsCount(SmsReceiverSearchCondition searchCondition,BindingResult result, SessionStatus status) {

		int totalRecentBottomCount = smsService.listRecentBottomCount(searchCondition);
	
		return totalRecentBottomCount;
	}	

}
