/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.socialpack.microblogging.model.Setting;
import com.lgcns.ikep4.socialpack.microblogging.service.SettingService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * 사용자별 마이크로블로깅 환경 설정 관리
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: SettingController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/microblogging/setting")
public class SettingController extends BaseController {

	@Autowired
	private SettingService settingService;

	/**
	 * 사용자 환경설정을 등록 수정한다.
	 *
	 * @param setting Setting 정보
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return 결과
	 */
	@RequestMapping("/createSetting")
	public @ResponseBody String onSubmit(@Valid Setting setting, BindingResult result, SessionStatus status) {

		if (log.isDebugEnabled()) {
			log.debug("setting:"+setting);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}

		setting.setUserId	(user.getUserId());
		setting.setUpdaterId(user.getUserId());
		
		if(!settingService.exists(user.getUserId()))
		{
			settingService.create(setting);
		}
		else
		{
			settingService.update(setting);
		}
		
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return "ok";
	}

	/**
	 * 사용자 환경설정 등록용 팝업을 리턴한다.
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getSettingForm.do")
	public ModelAndView getSettingForm() {
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}

		ModelAndView mav = new ModelAndView("/socialpack/microblogging/settingFormPopup");
		if (null != user.getUserId()) {
			Setting setting = settingService.read(user.getUserId());
			mav.addObject("setting", setting);
		}
		return mav;
	}

}
