/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLog;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLogSearchCondition;
import com.lgcns.ikep4.workflow.admin.service.AdminEmailService;
import com.lgcns.ikep4.workflow.workplace.model.WorkplaceCode;


/**
 * 결재 양식 선택 <br/>
 * 다음 사항 포함
 * 
 * <pre>
 * <li>양식 목록 조회</li>
 * <li>양식 선택</li>
 * </pre>
 * 
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: AdminEmailLogController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/workflow/admin/emailAdministration")
public class AdminEmailLogController extends BaseController {

	@Autowired
	private AdminEmailService adminEmailService;

	@RequestMapping(value = "/listAdminEmailLogs.do")
	public ModelAndView getAdminEmailLogList(AdminEmailLogSearchCondition adminEmailLogSearchCondition, BindingResult result, SessionStatus status) {

		//페이지 사이즈 설정.
		//apListSearchCondition.setPagePerRecord(10);

		//User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		//String localeCode = user.getLocaleCode();
		//String userId = user.getUserId();
		//apEmailLogSearchCondition.setRegistUserId(userId);
		//apEmailLogSearchCondition.setApprDocState(0);

		SearchResult<AdminEmailLog> searchResult = this.adminEmailService.readAdminEmailSearchList(adminEmailLogSearchCondition);
		
		ModelAndView mav = new ModelAndView("/workflow/admin/emailAdministration/readAdminEmailLogList");
		
		mav.addObject("emailLogList", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", new WorkplaceCode());

		return mav;
	}

	@RequestMapping(value = "/removeAdminEmailLogs.do")
	public ModelAndView removeAdminEmailLogList(AdminEmailLogSearchCondition adminEmailLogSearchCondition, HttpServletRequest req) {

		String removeResult = "removeFail";
		List<String> rmLogIds = new ArrayList<String>();
        String[] logIds = (String[]) req.getParameterValues("logIds");
        if(logIds != null) {
        	if( logIds.length > 1 ) {
        		for( int i = 0; i < logIds.length; i++)	rmLogIds.add(logIds[0]);
        		this.adminEmailService.deleteMultiAdminEmailLog(rmLogIds);
        	} else {
        		this.adminEmailService.deleteAdminEmailLog(logIds[0]);
        	}
        	removeResult = "removeSuccess";
        }
        
		SearchResult<AdminEmailLog> searchResult = this.adminEmailService.readAdminEmailSearchList(adminEmailLogSearchCondition);
		
		ModelAndView mav = new ModelAndView("/workflow/admin/emailAdministration/readAdminEmailLogList");
		
		mav.addObject("emailLogList", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("removeResult", removeResult);
		mav.addObject("commonCode", new WorkplaceCode());

		return mav;
	}

	@RequestMapping(value = "/testSend.do")
	public ModelAndView testSendMail(HttpServletRequest req) {

		
		ModelAndView mav = new ModelAndView("/workflow/admin/emailAdministration/test_send");
		String receiverEmail = req.getParameter("receiverEmail"); 
		if(receiverEmail!=null && !"".equals(receiverEmail)) {
			User user = (User) getRequestAttribute("ikep.user");
			String userId = user.getUserId();
			/* 내부 SMTP가 없어 한메일 서버를 이용하는 관계로 한메일 계정을 발신인 계정으로 설정 한다.
			 * 이 발신인 계정은 메일 발송시에만 적용하고 로그에는 전송된 발신인정보와 세션정보를 기록한다. 
			 */
			user.setUserId("mistpoet");
			user.setUserPassword("jins0119");
			user.setMail("mistpoet@hanmail.net");
			user.setUserName("장규진");			
			
			// 메일 내용 설정
			AdminEmailLog em = new AdminEmailLog();
			em.setSenderEmail(req.getParameter("senderEmail"));
			em.setSenderId(userId);
			em.setReceiver(req.getParameter("receiver"));
			em.setReceiverEmail(receiverEmail);
			em.setCcEmail(req.getParameter("ccEmail"));
			em.setBccEmail(req.getParameter("bccEmail"));
			em.setEmailTitle(req.getParameter("emailTitle"));
			em.setEmailContent(req.getParameter("emailContent"));
			
			// 메일 발송 및 로그 기록
			String rlt = this.adminEmailService.sendAdminEmail(em, user);
			
			// 발송 성공이면 'success'
			if( rlt == null || "".equals(rlt)) rlt = "faild..";
			
			mav.addObject("resultStr", rlt);
		}
		
		
		return mav;
	}

}
