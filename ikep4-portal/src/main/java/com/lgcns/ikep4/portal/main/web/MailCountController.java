/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.main.web;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.service.MailReadService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 메일 카운트 Controller
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: MailCountController.java 16660 2011-09-26 06:25:13Z giljae $
 */
@Controller
@RequestMapping(value = "/portal/mail")
public class MailCountController extends BaseController {

	@Autowired
	private MailReadService mailReadService;

	// 필요한 개수를 얻어 json 타입으로 return.
	@RequestMapping(value = "/getMailCount.do", method = RequestMethod.POST)
	public @ResponseBody
	String getMailCount() {

        JSONObject jsonObj = new JSONObject();
		User user = (User) getRequestAttribute("ikep.user");
		int count = mailReadService.getNewMailCountFromTerrace(MailConstant.FOLDER_UNREAD, user);//안읽은메일수
		//int count = mailReadService.getNewMailCountFromTerrace(MailConstant.FOLDER_INBOX, user);//받은메일수
		jsonObj.put("count", count);

		String returnValue = jsonObj.toString(); // {"count":개수} 형태의 json 타입으로

		return returnValue;
	}
}
