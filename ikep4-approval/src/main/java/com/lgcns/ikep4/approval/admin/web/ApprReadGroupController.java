/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprReadGroup;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.search.ApprDefLineSearchCondition;
import com.lgcns.ikep4.approval.admin.search.ApprReadGroupSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprReadGroupService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 서지혜
 * @version $Id$
 */
 
@Controller
@RequestMapping(value = "/approval/admin/config")
@SessionAttributes("apprReadGroup")
public class ApprReadGroupController extends BaseController {

	@Autowired
	private ApprReadGroupService apprReadGroupService;
	
	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
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
	 * 부서 결제함 열람권한 설정 목록
	 * @param searchCondition
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprReadGroup.do")
	public ModelAndView listApprReadGroup( ApprReadGroupSearchCondition searchCondition,
			                            HttpServletRequest request,
			                            HttpServletResponse response) throws ServletException, IOException { 

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/admin/config/listApprReadGroup");

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		searchCondition.setPortalId(portal.getPortalId());
		
		SearchResult<ApprReadGroup> searchResult = this.apprReadGroupService.listBySearchCondition(searchCondition);			
		ApprReadGroup	commonCode	=	new	ApprReadGroup();
		
		modelAndView.addObject("searchResult",          searchResult);
		modelAndView.addObject("searchCondition", searchResult.getSearchCondition());
		modelAndView.addObject("commonCode", commonCode);
		modelAndView.addObject("isSystemAdmin", this.isSystemAdmin(user));
		modelAndView.addObject("isReadAll", this.isReadAll(portal.getPortalId()));
		
		return modelAndView;
	}
	
	/**
	 * 부서 결제함 열람권한 설정 등록 화면
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/createApprReadGroupForm.do")
	public ModelAndView createApprReadGroupForm() throws ServletException, IOException { 

		ApprReadGroup apprReadGroup = new ApprReadGroup();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/admin/config/createApprReadGroupForm");

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		modelAndView.addObject("apprReadGroup", apprReadGroup);
		modelAndView.addObject("isSystemAdmin", this.isSystemAdmin(user));
		modelAndView.addObject("isReadAll", this.isReadAll(portal.getPortalId()));
		
		return modelAndView;
	}
	
	/**
	 * 부서 결제함 열람권한 설정 저장
	 * @param apprReadGroup
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/createApprReadGroup.do", method = RequestMethod.POST)
	public String createApprReadGroup(@Valid ApprReadGroup apprReadGroup) throws ServletException, IOException { 

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		apprReadGroup.setRegisterId(user.getUserId());
		apprReadGroup.setRegisterName(user.getUserName());
		apprReadGroup.setPortalId(portal.getPortalId());
		
		apprReadGroupService.createApprReadGroup(apprReadGroup);
		
		return "redirect:/approval/admin/config/listApprReadGroup.do";
	}
	
	/**
	 * 부서 결제함 열람권한 설정 수정 화면
	 * @param userId
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateApprReadGroupForm.do")
	public ModelAndView updateApprReadGroupForm(
			@RequestParam(value="userId", required = true) String userId
			) throws ServletException, IOException { 

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/admin/config/updateApprReadGroupForm");
		
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		ApprReadGroup apprReadGroup = new ApprReadGroup();
		
		apprReadGroup = apprReadGroupService.getBasicInfo(userId,portal.getPortalId());
		List<ApprReadGroup> getGroupList = apprReadGroupService.getGroupList(userId,portal.getPortalId());
		
		modelAndView.addObject("getGroupList", getGroupList);
		modelAndView.addObject("apprReadGroup", apprReadGroup);
		modelAndView.addObject("isSystemAdmin", this.isSystemAdmin(user));
		modelAndView.addObject("isReadAll", this.isReadAll(portal.getPortalId()));

		return modelAndView;
	}
	
	/**
	 * 부서 결제함 열람권한 설정 수정
	 * @param apprReadGroup
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateApprReadGroup.do", method = RequestMethod.POST)
	public String updateApprReadGroup(@Valid ApprReadGroup apprReadGroup) throws ServletException, IOException { 

		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		apprReadGroup.setRegisterId(user.getUserId());
		apprReadGroup.setRegisterName(user.getUserName());
		apprReadGroup.setPortalId(portal.getPortalId());
		
		apprReadGroupService.updateApprReadGroup(apprReadGroup);
		
		return "redirect:/approval/admin/config/listApprReadGroup.do";
	}
	
	/**
	 * 등록된 부서 목록
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/existApprReadGroupList.do")
	public @ResponseBody List existApprReadGroupList(String userId) {	
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		List<ApprReadGroup> getGroupList = apprReadGroupService.getGroupList(userId,portal.getPortalId());
		return getGroupList;
	}
	
	/**
	 * 목록에서 부서 삭제
	 * @param userIds
	 * @param status
	 */
	@RequestMapping(value = "/deleteReadGroupAjax.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void deleteReadGroupAjax(@RequestParam("userIds") List<String> userIds, SessionStatus status) {
		String returnValue = "";
		try{
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			apprReadGroupService.deleteReadGroup(userIds,portal.getPortalId());	
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteReadGroupAjax", ex);
		}
	}
}
