/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.Code;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * 사용 설정
 *
 * @author 서지혜
 * @version $Id$
 */
 
@Controller
@RequestMapping(value = "/approval/admin/config")
@SessionAttributes("apprConfig")
public class ApprAdminConfigController extends BaseController {

	@Autowired
	private ApprAdminConfigService apprAdminConfigService;
	
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
	 * @param portalId
	 * @return
	 */
	public boolean isReadAll(String portalId){
		
		boolean isRead = false;
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		if(apprAdminConfig.getIsReadAll().equals("1")) {
			//IKEP4_APPR_READ_ALL에 존재하는지 확인
			User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
			isRead = apprAdminConfigService.existReadAllAuth(user.getUserId());
		}
		return isRead;
	}
	
	/**
	 * 결재 기능 사용 관리
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateApprConfigForm")
	public ModelAndView updateApprConfigForm(ApprAdminConfig apprAdminConfig){
		
		ModelAndView mav = new ModelAndView("/approval/admin/config/updateApprConfigForm");
		
		if( apprAdminConfig == null ){ apprAdminConfig = new ApprAdminConfig();}
		
		//세션의 user id를 가져와서 bean에 셋팅한다.
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		ApprAdminConfig apprAdminConfigDetail= new ApprAdminConfig();
		apprAdminConfigDetail = apprAdminConfigService.adminConfigDetail(portal.getPortalId());
		
		//if( apprEntrustDetail == null ){ apprEntrustDetail = new ApprEntrust();}
		
		mav.addObject("apprAdminConfig", apprAdminConfigDetail);

		List<Code<String>> usageList = ApprAdminConfig.getUsageList();
		mav.addObject("usageList", usageList);
		
		List<Code<String>> chargeList = ApprAdminConfig.getChargeList();
		mav.addObject("chargeList", chargeList);
		
		List<Code<String>> messageOpendList = ApprAdminConfig.getMessageOpenList();
		mav.addObject("messageOpendList", messageOpendList);
		
		List<Code<String>> docNoMethodList = ApprAdminConfig.getDocNoMethodList();
		mav.addObject("docNoMethodList", docNoMethodList);
		
		List<Code<String>> lineViewTypeList = ApprAdminConfig.getLineViewTypeList();
		mav.addObject("lineViewTypeList", lineViewTypeList);
		
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portal.getPortalId()));
		
		return mav;
	}
	
	/**
	 * 결재 기능 사용  저장
	 * @param model
	 * @param apprNotice
	 * @return
	 */
	@RequestMapping(value = "/adminConfigSetSave.do", method = RequestMethod.POST)
	public String noticeSetSave(Model model,ApprAdminConfig apprAdminConfig) {
		
		String adminConfigYFlag = "1";
		String adminConfigNFlag = "0";
		
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		apprAdminConfig.setRegisterId(user.getUserId());
		apprAdminConfig.setRegisterName(user.getUserName());
		apprAdminConfig.setPortalId(portal.getPortalId());
		
		if(apprAdminConfig.getIsReference() == null || !apprAdminConfig.getIsReference().equals(adminConfigYFlag)) apprAdminConfig.setIsReference(adminConfigNFlag);
		if(apprAdminConfig.getIsReservation() == null || !apprAdminConfig.getIsReservation().equals(adminConfigYFlag)) apprAdminConfig.setIsReservation(adminConfigNFlag);
		if(apprAdminConfig.getIsArbitraryDecision() == null || !apprAdminConfig.getIsArbitraryDecision().equals(adminConfigYFlag)) apprAdminConfig.setIsArbitraryDecision(adminConfigNFlag);
		if(apprAdminConfig.getIsPostApproval() == null || !apprAdminConfig.getIsPostApproval().equals(adminConfigYFlag)) apprAdminConfig.setIsPostApproval(adminConfigNFlag);
		
		ApprAdminConfig apprAdminConfigDetail = apprAdminConfigService.adminConfigDetail(portal.getPortalId());
		
		if(apprAdminConfigDetail != null) {
			
			apprAdminConfig.setConfigId(apprAdminConfigDetail.getConfigId());
			apprAdminConfigService.adminConfigUpdate(apprAdminConfig);
			
		}else {
			apprAdminConfigService.adminConfigCreate(apprAdminConfig);
		}
		
		return "redirect:/approval/admin/config/updateApprConfigForm.do";
	}
	
	
}
