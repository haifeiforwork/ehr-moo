/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.workflow.admin.model.AdminSms;
import com.lgcns.ikep4.workflow.admin.model.AdminSmsSearchCondition;
import com.lgcns.ikep4.workflow.admin.service.SmsAdministrationService;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;

/**
 * WorkPlace 컨트롤러
 * 
 * @author 이재경
 * @version $Id: SmsAdministrationController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value="/workflow/admin/smsAdministration")
public class SmsAdministrationController extends BaseController {

	@Autowired
	private SmsAdministrationService smsAdministrationService;
	
	/**
	 * SMS관리 리스트
	 * @return
	 */
	@RequestMapping(value = "/smsHistoryList.do")
	public ModelAndView SmsHistoryList(AdminSmsSearchCondition adminSmsSearchCondition) {
		
		ModelAndView mav = new ModelAndView("/workflow/admin/smsAdministration/smsHistoryList");

		SearchResult<AdminSms> searchResult = smsAdministrationService.listSms(adminSmsSearchCondition);
		
		mav.addObject("searchResult", searchResult);
		mav.addObject("adminSmsSearchCondition", searchResult.getSearchCondition());
		mav.addObject("workplaceCode", new WorkplaceCode());
		
		return mav;
	}
}