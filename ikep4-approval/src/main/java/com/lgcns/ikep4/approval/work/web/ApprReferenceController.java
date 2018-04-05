/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.work.model.ApprExam;
import com.lgcns.ikep4.approval.work.model.ApprReference;
import com.lgcns.ikep4.approval.work.service.ApprReferenceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;




/**
 * 참조자 의견 Controller
 * 
 * @author 
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/apprReference")
public class ApprReferenceController extends BaseController {

	@Autowired
	private ApprReferenceService apprReferenceService;

	@Autowired
	ApprAdminConfigService apprAdminConfigService;

	@Autowired
	private ACLService aclService;

	/**
	 * 로그인 사용자가 전자결재의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Approval", user.getUserId());
	}

	/**
	 * 환경 설정에 정의된 값을 조회한다
	 * 
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId) {

		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		if (apprAdminConfig.getIsReadAll().equals("1")) {
			// IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}
	/**
	 * 참조자 의견 입력폼
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateApprReferenceForm.do")
	public ModelAndView updateApprReferenceForm(@RequestParam(value = "apprId", required = false) String apprId) {

		ModelAndView mav = new ModelAndView("/approval/work/apprReference/updateApprReferenceForm");

		ApprReference apprReference = new ApprReference();
		apprReference.setApprId(apprId);
		
		mav.addObject("apprId", apprId);
		mav.addObject("apprReference", apprReference);
		return mav;
	}
	/**
	 * 참조자 의견 입력
	 * 
	 * @param model
	 * @param ApprExam
	 * @return
	 */
	@RequestMapping(value = "/updateApprReference.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateApprReference(Model model, ApprReference apprReference, SessionStatus status) {

		String rtnValue = "";
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprReference.getApprId());
		map.put("userId",   user.getUserId());
		map.put("isMessage",   "1");
		try {
			if (!apprReferenceService.exists(map)) {
				rtnValue = "EXIST";
			} else {
				apprReference.setUserId(user.getUserId());
				apprReferenceService.update(apprReference);

				status.setComplete();
				rtnValue = "OK";
			}
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateApprReference", ex);
		}
		return rtnValue;
	}



	@RequestMapping(value = "/listApprReference.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView listApprReference(@RequestParam(value = "apprId", required = true) String apprId,
			HttpServletRequest request, HttpServletResponse response, SessionStatus status) {

		ModelAndView modelAndView = new ModelAndView();

		try {

			modelAndView.setViewName("/approval/work/apprReference/listApprReference");

			User user = (User) getRequestAttribute("ikep.user");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			map.put("userId",   user.getUserId());
			
			List<ApprReference> list = apprReferenceService.list(apprId);

			modelAndView.addObject("list", list);
			modelAndView.addObject("refCount", list.size());
			// 세션 상태를 complete
			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("listApprReference", ex);
		}

		return this.bindResult(modelAndView);
	}


}
