/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprDefLineCode;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.work.model.ApprLine;
import com.lgcns.ikep4.approval.work.model.ApprUserLine;
import com.lgcns.ikep4.approval.work.model.ApprUserLineSub;
import com.lgcns.ikep4.approval.work.search.ApprLineSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprLineService;
import com.lgcns.ikep4.approval.work.service.ApprUserLineService;
import com.lgcns.ikep4.approval.work.service.ApprUserLineSubService;
import com.lgcns.ikep4.approval.work.service.ApprWorkDocService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.annotations.AccessType;
import com.lgcns.ikep4.support.security.acl.annotations.Attribute;
import com.lgcns.ikep4.support.security.acl.annotations.IsAccess;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.security.acl.validation.AccessingResult;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.code.service.JobDutyService;
import com.lgcns.ikep4.support.user.code.service.JobPositionService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 결재선 관리 Controller
 *
 * @author 
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/apprLine")
public class ApprLineController extends BaseController {
	
	@Autowired
	private	ApprLineService	apprLineService;

	@Autowired
	private	ApprUserLineService	apprUserLineService;	
	
	@Autowired
	private	ApprUserLineSubService	apprUserLineSubService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	JobDutyService jobDutyService;

	@Autowired
	JobPositionService jobPositionService;
	
	@Autowired
	private ApprWorkDocService apprWorkDocService;
	
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

	/*****************************************
	 * 문서 진행현황
	 ****************************************/
	/**
	 * 목록>문서상태 클릭>결재진행현황 Tab 
	 * @param apprId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listApprLineInfo.do")
	public ModelAndView listApprLineInfo( @RequestParam(value="apprId", required = true) String apprId,
			HttpServletRequest request, 
			HttpServletResponse response) { 

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/work/apprLine/listApprLineInfo");

		User user = (User) getRequestAttribute("ikep.user");
		
		// 기안문서 정보
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());		
		
		// apprDoc
		ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
		
		map.put("apprType",   "0");
		List<ApprLine> apprLineList = apprLineService.listApprLine(map);

		// 접수자 전결여부확인
		boolean receiveEnd = false;
		if(apprLineList!=null && apprLineList.size()==1){
			if(apprDoc.getRegisterId().equals(apprLineList.get(0).getApproverId())){
				receiveEnd=true;
			}
		}
		
		// 부서 합의 목록
/*		List<ApprDoc> subList = apprWorkDocService.subList(map);
		for(ApprLine apprLine : apprLineList) {
			for(ApprDoc apprDoc1 : subList) {
				if(apprLine.getApproverType()==1 && apprLine.getApproverId().equals(apprDoc1.getApprGroupId()) ) {
					List<ApprLine> list = apprLineService.lastApprLine(apprDoc1.getApprId());
					if(list!=null && list.size()>0)
						apprLine.setApprLineList(list);
				}
			}
		}
*/
		// 수신부서 정보
		List<ApprLine> receiveApprLineList = new ArrayList<ApprLine>();
		List<ApprDoc> receiveSubList = new ArrayList<ApprDoc>();
		map.put("apprType",   "3");
		receiveApprLineList = apprLineService.childListApprLine(map);
		
		// 수신자 전결여부확인
		boolean receiveEnd2 = false;
		if(receiveApprLineList!=null && receiveApprLineList.size()==1){
			if(apprDoc.getRegisterId().equals(receiveApprLineList.get(0).getApproverId())){
				receiveEnd2=true;
			}
		}
		
		receiveSubList = apprWorkDocService.subList(map);
		for(ApprDoc apprDoc1 : receiveSubList) {
			map.put("apprId", 	apprDoc1.getApprId());
			map.put("apprType",   "0");
			List<ApprLine> list = apprLineService.listApprLine(map);
			if(list!=null && list.size()>0) {
				apprDoc1.setLineCount(list.size());
			}
		}
		

		// 문서결재 기본 설정 정보
		ApprAdminConfig apprConfig= new ApprAdminConfig();
		apprConfig = apprAdminConfigService.adminConfigDetail(user.getPortalId());	
		
		ApprDefLineCode	commonCode	=	new	ApprDefLineCode();
		modelAndView.addObject("apprLineList",			apprLineList);
		modelAndView.addObject("commonCode", commonCode);
		modelAndView.addObject("apprDoc", apprDoc);
		//modelAndView.addObject("subList", subList);
		modelAndView.addObject("receiveApprLineList",	receiveApprLineList);
		modelAndView.addObject("receiveSubList",		receiveSubList);
		modelAndView.addObject("receiveEnd",		receiveEnd);
		modelAndView.addObject("receiveEnd2",		receiveEnd2);
		modelAndView.addObject("apprConfig",		apprConfig);
		return modelAndView;
	}
	/**
	 * Admin>결재문서관리>조회>결재복원클릭>현재 문서 결재진행 목록
	 * @param apprId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listApprLineAdmin.do")
	public ModelAndView listApprLineAdmin( @RequestParam(value="apprId", required = true) String apprId,
			HttpServletRequest request, HttpServletResponse response) { 

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/work/apprLine/listApprLineAdmin");

		User user = (User) getRequestAttribute("ikep.user");
		// 기안문서 정보
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());
		
		// apprDoc
		ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
		map.put("apprType",   "0");
		map.put("orderBy",   "DESC");
		List<ApprLine> apprLineList = apprLineService.listApprLine(map);
		// 접수자 전결여부확인
		boolean receiveEnd = false;
		if(apprLineList!=null && apprLineList.size()==1){
			if(apprDoc.getRegisterId().equals(apprLineList.get(0).getApproverId())){
				receiveEnd=true;
			}
		}		
		// 부서 합의 목록
		/*List<ApprDoc> subList = apprWorkDocService.subList(map);
		for(ApprLine apprLine : apprLineList) {
			for(ApprDoc apprDoc1 : subList) {
				if(apprLine.getApproverType()==1 && apprLine.getApproverId().equals(apprDoc1.getApprGroupId()) ) {
					List<ApprLine> list = apprLineService.lastApprLine(apprDoc1.getApprId());
					if(list!=null && list.size()>0)
						apprLine.setApprLineList(list);
				}
			}
		}*/
		// 수신처 목록
		//map.put("apprType",   "3");
		//List<ApprDoc> subRecList = apprWorkDocService.subList(map);		
			
		ApprDefLineCode	commonCode	=	new	ApprDefLineCode();

		modelAndView.addObject("apprLineList",			apprLineList);
		modelAndView.addObject("commonCode", commonCode);
		modelAndView.addObject("apprDoc", apprDoc);
		//modelAndView.addObject("subList", subList);
		//modelAndView.addObject("subRecList", subRecList);
		modelAndView.addObject("receiveEnd",		receiveEnd);
		return modelAndView;
	}	
	/**
	 * 문서조회>합의 부서 클릭>합의 부서 전체 결재 진행 현황 Tab
	 * @param apprId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listApprLineSubAll.do")
	public ModelAndView listApprLineSubAll( @RequestParam(value="apprId", required = true) String apprId,
			@RequestParam(value="apprType", required = true) int apprType,
			HttpServletRequest request, HttpServletResponse response) { 

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/work/apprLine/listApprLineSubAll");

		User user = (User) getRequestAttribute("ikep.user");
		// 기안문서 정보
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());
		// 부서 합의 목록
		map.put("apprType",  apprType+"");		
		// apprDoc
		ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
		
		if(apprDoc.getParentApprId()!=null) {
			map.put("apprId", 	apprDoc.getParentApprId());
		}
		
		// child 결재선 정보 ( 부서합의/수신처 )
		// apprType = 3 : 수신처
		// apprType != 3 : 부서합의		
		List<ApprLine> apprLineList = apprLineService.childListApprLine(map);
		// 접수자 전결여부확인
		boolean receiveEnd = false;
		if(apprLineList!=null && apprLineList.size()==1){
			if(apprDoc.getRegisterId().equals(apprLineList.get(0).getApproverId())){
				receiveEnd=true;
			}
		}
		List<ApprDoc> subList = apprWorkDocService.subList(map);
		for(ApprDoc apprDoc1 : subList) {
			map.put("apprId", 	apprDoc1.getApprId());
			List<ApprLine> list = apprLineService.listApprLine(map);
			if(list!=null && list.size()>0) {
				apprDoc1.setLineCount(list.size());
			}
		}
		
		ApprDefLineCode	commonCode	=	new	ApprDefLineCode();

		modelAndView.addObject("apprLineList",	apprLineList);
		modelAndView.addObject("commonCode", commonCode);
		modelAndView.addObject("apprDoc", apprDoc);
		modelAndView.addObject("subList", subList);
		modelAndView.addObject("apprType", apprType);
		modelAndView.addObject("receiveEnd",		receiveEnd);
		return modelAndView;
	}	
	/**
	 * 문서조회>진행현황 클릭> 문서 결재 진행 현황 Tab 
	 * @param apprId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listApprLineForm.do")
	public ModelAndView listApprLineForm( @RequestParam(value="apprId", required = true) String apprId,
			@RequestParam(value="groupId", required = false) String groupId,
			HttpServletRequest request, HttpServletResponse response) { 

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/work/apprLine/listApprLineForm");

		User user = (User) getRequestAttribute("ikep.user");
		// 기안문서 정보
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());
		map.put("groupId",   groupId);		
		// apprDoc
		ApprDoc apprDoc = new ApprDoc();
		
		if(groupId==null || groupId.equals("")) {
			apprDoc = apprWorkDocService.readApprDoc(map);
		} else {
			apprDoc = apprWorkDocService.readApprDocSub(map);
		}
		
		// 합의부서
		List<ApprLine> apprLineList = new ArrayList<ApprLine>();
		List<ApprDoc> subList = new ArrayList<ApprDoc>();

		map.put("apprType",   "0");
		apprLineList = apprLineService.listApprLine(map);
		// 접수자 전결여부확인
		boolean receiveEnd = false;
		if(apprLineList!=null && apprLineList.size()==1){
			if(apprDoc.getRegisterId().equals(apprLineList.get(0).getApproverId())){
				receiveEnd=true;
			}
		}			
		subList = apprWorkDocService.subList(map);
		for(ApprLine apprLine : apprLineList) {
			for(ApprDoc apprDoc1 : subList) {
				if(apprLine.getApproverType()==1 && apprLine.getApproverId().equals(apprDoc1.getApprGroupId()) ) {
					List<ApprLine> list = apprLineService.lastApprLine(apprDoc1.getApprId());
					if(list!=null && list.size()>0)
						apprLine.setApprLineList(list);
				}
			}
		}		

		// 수신부서 정보
		List<ApprLine> receiveApprLineList = new ArrayList<ApprLine>();
		List<ApprDoc> receiveSubList = new ArrayList<ApprDoc>();
		// child 결재선 정보 ( 부서합의/수신처 )
		// apprType = 3 : 수신처
		// apprType != 3 : 부서합의	
		map.put("apprType",   "3");
		receiveApprLineList = apprLineService.childListApprLine(map);		
		
		// 수신자 전결여부확인
		boolean receiveEnd2 = false;
		if(receiveApprLineList!=null && receiveApprLineList.size()==1){
			if(apprDoc.getRegisterId().equals(receiveApprLineList.get(0).getApproverId())){
				receiveEnd2=true;
			}
		}		
		receiveSubList = apprWorkDocService.subList(map);
		for(ApprDoc apprDoc1 : receiveSubList) {
			map.put("apprId", 	apprDoc1.getApprId());
			// 수신부서내 결재만 가져오기
			map.put("apprType",   "0");
			List<ApprLine> list = apprLineService.listApprLine(map);
			if(list!=null && list.size()>0) {
				apprDoc1.setLineCount(list.size());
			}
		}			
		
		ApprDefLineCode	commonCode	=	new	ApprDefLineCode();

		modelAndView.addObject("apprLineList",			apprLineList);
		modelAndView.addObject("subList",				subList);		
		modelAndView.addObject("receiveApprLineList",	receiveApprLineList);
		modelAndView.addObject("receiveSubList",		receiveSubList);		
		modelAndView.addObject("commonCode",			commonCode);
		modelAndView.addObject("apprDoc",				apprDoc);
		modelAndView.addObject("receiveEnd",		receiveEnd);
		modelAndView.addObject("receiveEnd2",		receiveEnd2);
		return modelAndView;
	}

	
	/*****************************************
	 * 문서 결재처리
	 ****************************************/
	/**
	 * 문서상태 및 결재 상태 Return
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/getDocStatus.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getDocStatus(String apprId, 
			SessionStatus status,
			@RequestParam(value = "entrustUserId", required = false) String entrustUserId) {

		String returnValue = "";
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");		
		try{
			// 문서정보조회
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			map.put("locale",   user.getLocaleCode());		
			ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
			
			if(apprDoc==null)
			{
				returnValue ="NODOC";
				status.setComplete();
				return returnValue;
			}
			
			boolean isSystemAdmin = this.isSystemAdmin(user);
			if(!isSystemAdmin) {
				// 결재자 결재정보
				ApprLine apprLine = new ApprLine();
				apprLine.setApprId(apprId);
				if(entrustUserId!=null && !entrustUserId.equals("")) {
					apprLine.setApproverId(entrustUserId);
				} else {
					apprLine.setApproverId(user.getUserId());
				}
					
				apprLine = apprLineService.readLine(apprLine);
				
				//문서상태 (0:임시저장, 1:진행중, 2:완료, 3:반려, 4:회수, 5:오류,6:접수대기 ..)
				if(apprDoc.getApprDocStatus()!=1) {// 
					returnValue = "DOC"+apprDoc.getApprDocStatus();
				} else if(apprLine.getApprStatus()>1 || apprLine.getApprStatus()==0) {
					//현재 사용자가 결재했는지 확인( 0:대기,1:진행,2:승인,3:반려,4:합의..)
					returnValue = "LINE"+apprLine.getApprStatus();
					//status.setComplete();
				} else {
					returnValue ="OK";
					//status.setComplete();
				}
			} else {
				returnValue ="OK";
			}
			status.setComplete();		
		} catch (Exception ex) {
			throw new IKEP4AjaxException("getDocStatus.do", ex);
		}
		
		return returnValue;
	}
	/**
	 * 문서상태 및 결재 상태 Return
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/existsDocStatus.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String existsDocStatus(String apprId, 
			SessionStatus status,
			@RequestParam(value = "entrustUserId", required = false) String entrustUserId) {

		String returnValue = "";
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");		
		try{
			// 문서정보조회
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			map.put("locale",   user.getLocaleCode());		
			ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
			
			if(apprDoc==null)
			{
				returnValue ="NODOC";
				//return returnValue;
			}
			else {
				returnValue ="OK";
				//status.setComplete();
			}
			status.setComplete();
		} catch (Exception ex) {
			throw new IKEP4AjaxException("existsDocStatus.do", ex);
		}
		
		return returnValue;
	}	
	/**
	 * 결재 비밀번호 확인
	 * 
	 * @param nowApprPassword
	 * @param newApprPassword
	 * @return
	 */
	@RequestMapping(value = "/confirmApprPassword.do")
	public @ResponseBody
	String confirmApprPassword(String apprPassword) {

		try {
			User user = (User) getRequestAttribute("ikep.user");
			User apprUser = userService.read(user.getUserId());
			if (EncryptUtil.encryptSha(apprPassword).equals(apprUser.getApprovalPassword())
					&& !StringUtil.isEmpty(apprUser.getApprovalPassword())) {
				return "ok";
			} else {
				return "nowPasswordError";
			}
		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}
	}	
	
	/**
	 * 결재처리 폼
	 * @param apprId
	 * @return
	 */
	@RequestMapping(value = "/updateApprovalForm.do")
	public ModelAndView updateApprovalForm(@RequestParam(value = "apprId", required = true) String apprId,
			@RequestParam(value = "entrustUserId", required = false) String entrustUserId) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		ModelAndView mav = new ModelAndView("/approval/work/apprLine/updateApprovalForm");
		ApprLine apprLine = new ApprLine();
		apprLine.setApprId(apprId);
		
		if(entrustUserId!=null && !entrustUserId.equals("")){
			apprLine.setApproverId(entrustUserId);
		} else {
			apprLine.setApproverId(user.getUserId());
		}
		apprLine = apprLineService.readLine(apprLine);
		apprLine.setEntrustUserId(entrustUserId);
		
		String portalId = (String) getRequestAttribute("ikep.portalId");

		
		// 문서결재 기본 설정 정보 ( 결재 비밀번호 사용 여부 (0:미사용, 1:사용) )
		String isPassword = "0"; 
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		isPassword = apprAdminConfig.getIsPassword();
		
		mav.addObject("isPassword", isPassword);
		mav.addObject("apprId", apprId);
		mav.addObject("apprLine", apprLine);
		return mav;
	}
	
	/**
	 * 문서 결재 결재처리 
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateApproval.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateApproval(ApprLine apprLine,ApprLineSearchCondition searchCondition, SessionStatus status, HttpServletRequest request) {
		String returnValue = "";
		try{
			
			User user = (User) getRequestAttribute("ikep.user");
			//apprLine.setEntrustUserId(entrustUserId);
			apprLineService.updateApproval(apprLine,user,request);
			// 세션 상태를 complete
			status.setComplete();
			returnValue = "OK";
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteAjaxDefLine", ex);
		}
		
		return returnValue;
	}
	
	/**
	 * 접수자 전결 처리 폼
	 * @param apprId
	 * @return
	 */
	@RequestMapping(value = "/updateRecEndForm.do")
	public ModelAndView updateRecEndForm(@RequestParam(value = "apprId", required = true) String apprId,
			@RequestParam(value = "entrustUserId", required = false) String entrustUserId) {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		ModelAndView mav = new ModelAndView("/approval/work/apprLine/updateRecEndForm");

		ApprLine apprLine = new ApprLine();		
		apprLine.setApprId(apprId);
		//apprLine.setApproverId(user.getUserId());
		if(entrustUserId!=null && !entrustUserId.equals("")){
			apprLine.setApproverId(entrustUserId);
		} else {
			apprLine.setApproverId(user.getUserId());
		}
		
		ApprLine apprLineSelect = new ApprLine();		
		apprLineSelect.setApprId(apprId);
		//apprLineSelect.setApproverId(user.getUserId());
		if(entrustUserId!=null && !entrustUserId.equals("")){
			apprLineSelect.setApproverId(entrustUserId);
		} else {
			apprLineSelect.setApproverId(user.getUserId());
		}
		apprLineSelect = apprLineService.readLine(apprLineSelect);
		// 결재자 정보가 없는 경우 ( 부서 합의, 수신처가 부서인 경우 )
		if(apprLineSelect==null){
			apprLine.setApprType(0);// 결재
			apprLine.setApprStatus(1);// 진행			
		} else {
			apprLine = apprLineSelect;
		}
		apprLine.setEntrustUserId(entrustUserId);
		
		String portalId		=	(String) getRequestAttribute("ikep.portalId");
		
		// 문서결재 기본 설정 정보 ( 결재 비밀번호 사용 여부 (0:미사용, 1:사용) )
		String isPassword	=	"0"; 		
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		isPassword = apprAdminConfig.getIsPassword();
		
		mav.addObject("isPassword", isPassword);
		mav.addObject("apprId", apprId);
		mav.addObject("apprLine", apprLine);
		
		return mav;
	}
	
	/**
	 * 접수자 전결처리
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateRecEndApproval.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateReceiveApproval(ApprLine apprLine,SessionStatus status, HttpServletRequest request) {

		String returnValue = "";
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		try{				
			apprLineService.updateApprovalRecEnd(apprLine,user,request);			
			status.setComplete();
			returnValue = "OK";			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateAllianceStatus", ex);
		}		
		return returnValue;
	}	
	/**
	 * 결재처리 폼(여러건)
	 * @param apprId
	 * @return
	 */
	@RequestMapping(value = "/updateMultiApprovalForm.do")
	public ModelAndView updateMultiApprovalForm(@RequestParam(value = "apprIds", required = false) String apprIds,
			@RequestParam(value = "entrustUserIds", required = false) String entrustUserIds) {
		
		ModelAndView mav = new ModelAndView("/approval/work/apprLine/updateMultiApprovalForm");

		ApprLine apprLine = new ApprLine();
		//apprLine.setEntrustUserId(entrustUserId);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		// 문서결재 기본 설정 정보 ( 결재 비밀번호 사용 여부 (0:미사용, 1:사용) )
		String isPassword = "0"; 
		
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		isPassword = apprAdminConfig.getIsPassword();
		
		mav.addObject("isPassword",		isPassword);
		mav.addObject("apprLine",		apprLine);
		mav.addObject("apprIds",		apprIds);	
		mav.addObject("entrustUserIds",	entrustUserIds);
		return mav;
	}
	
	/**
	 * 문서 결재 일괄승인/일괄반려 처리
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateApprovalStatus.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateApprovalStatus(ApprLine apprLine,
			@RequestParam("apprIds") List<String> apprIds,
			@RequestParam("entrustUserIds") List<String> entrustUserIds,			
			SessionStatus status,
			HttpServletRequest request) {

		String returnValue = "";
		int success	=	0;
		int total	=	apprIds.size();
		User user = (User) getRequestAttribute("ikep.user");
		
		// 문서정보조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("locale",   user.getLocaleCode());		
		ApprDoc apprDoc = new ApprDoc();
		
		try{			
			for (int i = 0; i < apprIds.size(); i++) {
				String apprId = apprIds.get(i);
				String entrustUserId = null;
				if(entrustUserIds!=null && entrustUserIds.size()>0)
					entrustUserId=entrustUserIds.get(i);
				map.put("apprId", 	apprId);
				apprDoc = apprWorkDocService.readApprDoc(map);
				
				ApprLine chkApprLine = new ApprLine();
				chkApprLine.setApprId(apprId);				
				
				if(entrustUserId!=null && !entrustUserId.equals("")){
					chkApprLine.setApproverId(entrustUserId);
				} else {
					chkApprLine.setApproverId(user.getUserId());
				}				
				//chkApprLine.setApproverId(user.getUserId());	
				
				chkApprLine = apprLineService.readLine(chkApprLine);
				
				if(apprDoc.getApprDocStatus()==1 && chkApprLine.getApprStatus()==1){
					success++;
				} else {
					continue;
				}
				
				apprLine.setApprId(apprId);
				apprLine.setApprStatus(apprLine.getApprStatus());
				if(entrustUserId!=null && !entrustUserId.equals("")){
					apprLine.setApproverId(entrustUserId);
					apprLine.setEntrustUserId(entrustUserId);
				} else {
					apprLine.setApproverId(user.getUserId());
					apprLine.setEntrustUserId("");
				}				
				//apprLine.setApproverId(user.getUserId());			
				apprLineService.updateApproval(apprLine,user,request);
			}

			status.setComplete();
			returnValue = "OK:"+success+":"+total;
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateAllianceStatus", ex);
		}
		
		return returnValue;
	}
	/**
	 * 결재 취소
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateApprovalCancel.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateApprovalCancel(ApprLine apprLine, 
			SessionStatus status,
			@RequestParam(value = "entrustUserId", required = false) String entrustUserId) {

		String returnValue = "";
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprLine.getApprId());
		if(entrustUserId!=null && !entrustUserId.equals("")) {
			map.put("approverId",   entrustUserId);
		} else {
			map.put("approverId",   user.getUserId());
		}		
		//map.put("approverId",   user.getUserId());
		
		try{
			// 다음 결재자의 결재여부 (결재:true,진행중 :false)
			boolean cancel = apprLineService.existsNextApprProgress(map);			
			if(!cancel) {
				apprLineService.updateApprovalLineCancel(apprLine,user);
				returnValue = "OK";
			} else {
				returnValue = "false";
			}
			status.setComplete();			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateApprovalCancel.do", ex);
		}		
		return returnValue;
	}

	/**
	 * 현황관리>결재문서관리>문서조회>결재복원 시 문서상태 및 결재 상태 확인
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/getDocComplete.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getDocComplete(String apprId, 
			SessionStatus status) {

		String returnValue = "";
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");		
		try{
			// 문서정보조회
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", 	apprId);
			map.put("locale",   user.getLocaleCode());		
			ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
			
			// 결재자 결재정보
			ApprLine apprLine = new ApprLine();
			apprLine.setApprId(apprId);
			apprLine.setApproverId(user.getUserId());
			
			apprLine = apprLineService.readLine(apprLine);
			
			//문서상태 (0:임시저장, 1:진행중, 2:완료, 3:반려, 4:회수, 5:오류,6:접수대기 ..)
			/**if(!apprDoc.getApprDocStatus().equals("1")) {// 
				returnValue = "DOC"+apprDoc.getApprDocStatus();
			} else if(apprLine.getApprStatus()>1 || apprLine.getApprStatus()==0) {
				//현재 사용자가 결재했는지 확인( 0:대기,1:진행,2:승인,3:반려,4:합의..)
				returnValue = "LINE"+apprLine.getApprStatus();
			} else {
				returnValue ="OK";
			}**/
			if(apprDoc.getApprDocStatus()==1){
				returnValue ="OK";
			} else {
				returnValue ="false";
			} 
			
			status.setComplete();			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("getDocComplete.do", ex);
		}
		
		return returnValue;
	}
	
	/**
	 * 현황관리>결재문서관리>문서조회>결재복원>결재 복원 클릭
	 * 
	 * @param member
	 * @param memberIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateApprovalLineRestore.do", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateApprovalLineRestore(@IsAccess(@Attribute(type = AccessType.SYSTEM, className = "Approval", operationName = { "MANAGE" }, resourceName = "Approval")) AdminSearchCondition searchCondition,
			AccessingResult accessResult,String apprId,@RequestParam("approverIds") String[] approverIds, SessionStatus status) {

		// FALSE인 경우 미승인 된 것이므로 IKEP4AuthorizedException을
		// THROW하여 common/notAuthorized.jsp로 페이지 전환
		if (!accessResult.isAccess()) {
			throw new IKEP4AuthorizedException();
		}
		
		String returnValue = "";		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");		
		try{
			apprLineService.updateApprovalLineRestore(apprId,approverIds,user);
			status.setComplete();
			returnValue = "OK";
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateApprovalCancel.do", ex);
		}		
		return returnValue;
	}
	
	
	/*****************************************
	 * 결재선/수신처 변경
	 ****************************************/	
	/**
	 * 결재선 지정 팝업 View
	 * @param selectType
	 * @param controlTabType
	 * @param controlType
	 * @param searchSubFlag
	 * @param defLineId
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/createApprLineView.do")
	public ModelAndView createApprLineView(@RequestParam(value="selectType", required=true, defaultValue="ALL" ) String selectType
			, @RequestParam(value="controlTabType", required=true, defaultValue="1:0:0:0")  String controlTabType
			, @RequestParam(value="controlType", required=true, defaultValue="ORG" ) String controlType
			, @RequestParam(value="searchSubFlag", required=true, defaultValue="false" ) String searchSubFlag
			, @RequestParam(value="apprId", required = false) String apprId
			, @RequestParam(value="apprType") String apprType
			, @RequestParam(value = "entrustUserId", required = false) String entrustUserId
		) throws JsonGenerationException, JsonMappingException, IOException {
		
		User user = (User) getRequestAttribute("ikep.user");
		
		ModelAndView	mav			=	new ModelAndView();	
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());		
		
		if(apprType.equals("3")) {
			// 수신처 지정 팝업 페이지 
			mav.setViewName("/approval/work/apprLine/createApprReceiveLineView");
			
			// 사용자 정의 수신처 정보
			map.put("apprType", apprType);
			List<ApprUserLine>	apprUserLineList	=	apprUserLineService.listApprUserLine(map);
			mav.addObject("apprUserLineList",apprUserLineList);
		} else {
			//결재선 지정 팝업
			mav.setViewName("/approval/work/apprLine/createApprLineView");

			// 사용자 정의 결재 정보
			map.put("apprType", "0");
			List<ApprUserLine>	apprUserLineList	=	apprUserLineService.listApprUserLine(map);
			mav.addObject("apprUserLineList",apprUserLineList);			
		}
		
		// 기안문서 정보
		ApprDoc	apprDoc	=	new ApprDoc();		
		Map<String, String> apprDocMap = new HashMap<String, String>();
		apprDocMap.put("apprId", 	apprId);
		apprDocMap.put("locale",   user.getLocaleCode());
		
		// apprDoc
		if(apprId!=null && !apprId.equals("")) {
			apprDoc = apprWorkDocService.readApprDoc(apprDocMap);
		} else {
			apprDoc.setApprId(apprId);						
		}
		// 기안문서 정보		
		ApprLine apprLine = new ApprLine();
		apprLine.setApprId(apprId);
		if(entrustUserId !=null && !entrustUserId.equals("")) {
			apprLine.setApproverId(entrustUserId);		
		} else {
			apprLine.setApproverId(user.getUserId());	
		}
		
		apprLine = apprLineService.readLine(apprLine);
		
		// 결재가 진행중인지 확인
		boolean progress = apprLineService.countProgress(apprId);
		
		mav.addObject("apprDoc", apprDoc);
		mav.addObject("selectType", selectType);
		mav.addObject("controlTabType", controlTabType);
		mav.addObject("controlType", controlType);
		mav.addObject("searchSubFlag", searchSubFlag);
		mav.addObject("userLocaleCode", user.getLocaleCode());
		mav.addObject("apprLine", apprLine);
		mav.addObject("progress", progress);
		mav.addObject("entrustUserId", entrustUserId);
		mav.addObject("isSystemAdmin", 			this.isSystemAdmin(user));
		return mav;
	}
	
	/**
	 * 결재선 지정, 수신처 지정 저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/saveApprLine.do", method = RequestMethod.POST)
	public @ResponseBody String saveApprLine(ApprLine apprLine, BindingResult result,HttpServletRequest request,  SessionStatus status) {
		String returnValue = "";
		try{

			User user = (User) getRequestAttribute("ikep.user");			
			returnValue=apprLineService.create(apprLine,user);
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("saveApprLine.do", ex);
		}

		return returnValue;
	}	

	/**
	 * 결재선 변경 저장 ( viewApprDoc 결재선 변경 )
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateApprLine.do", method = RequestMethod.POST)
	public @ResponseBody String updateApprLine(ApprLine apprLine, BindingResult result,
			HttpServletRequest request, 
			SessionStatus status,
			@RequestParam(value = "entrustUserId", required = false) String entrustUserId) {
		String returnValue = "";
		try{
			User user = (User) getRequestAttribute("ikep.user");
			// apprType 3 : 수신처변경, 그외는 결재선 변경
			returnValue=apprLineService.updateApprLine(apprLine,user);
			// 세션 상태를 complete
			status.setComplete();
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateApprLine.do", ex);
		}
		return returnValue;
	}

	/**
	 * 수신처 변경 저장 ( viewApprDoc 수신처 변경 )
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateReceiverApprLine.do", method = RequestMethod.POST)
	public @ResponseBody String updateReceiverApprLine(ApprLine apprLine, 
			BindingResult result,
			HttpServletRequest request,  
			SessionStatus status,
			@RequestParam(value = "entrustUserId", required = false) String entrustUserId) {
		String returnValue = "";
		try{
			User user = (User) getRequestAttribute("ikep.user");
			
			// apprType 3 : 수신처변경, 그외는 결재선 변경
			returnValue=apprLineService.updateReceiverApprLine(apprLine,user);

			// 세션 상태를 complete
			status.setComplete();			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("updateReceiverApprLine.do", ex);
		}
		return returnValue;
	}		
	
	/*****************************************
	 * 사용자 정의 결재선/수신처 관리
	 ****************************************/	
	/**
	 * Ajax 사용자 정의 결재선 목록 조회
	 * @param apprId
	 * @return
	 */
	@RequestMapping("/getApprUserLine.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<ApprUserLine> getApprUserLine(@RequestParam(value="apprType", required = true) String apprType) {

		User user = (User) getRequestAttribute("ikep.user");
		
		List<ApprUserLine> list =	null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("apprType", apprType);
		try {
			list = apprUserLineService.listApprUserLine(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return list;		
	}
	
	/**
	 * Ajax 사용자 정의 결재선 목록 조회
	 * @param apprId
	 * @return
	 */
	@RequestMapping("/getApprUserLineSub.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<ApprUserLineSub> getApprUserLineSub(@RequestParam(value="userLineId", required = true) String userLineId) {

		List<ApprUserLineSub> list =	null;
		try {
			list = apprUserLineSubService.listApprUserLineSub(userLineId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return list;		
	}	
	
	/**
	 * 사용자 정의 결재선 저장
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxSaveApprUserLine.do", method = RequestMethod.POST)
	public @ResponseBody String ajaxSaveApprUserLine(ApprUserLine apprUserLine, BindingResult result,HttpServletRequest request,  SessionStatus status) {
		String returnValue = "";
		try{			
			User user = (User) getRequestAttribute("ikep.user");
			
			//Default 결재선 등록 & 결재선 Flow 정보 등록
			apprUserLine.setUserId(user.getUserId());
			returnValue=apprUserLineService.create(apprUserLine);

			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("saveApprLine.do", ex);
		}

		return returnValue;
	}	
	
	
	
	/**
	 * 사용자 호칭/직책정보
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getUserInfo.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	User requestUserJobPosDutyList(@RequestParam(value="userId", required = true) String userId) {
		User user	= new User();
		try {
			user	=	userService.read(userId);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return user;		
	}
	
	/**
	 * 직책을 조회 하는 메서드 <br/>
	 * 기준 GroupId를 기준으로 하위의 조직도를 데이타를 조회
	 *
	 * @return 조회된 직책 리스트 Map
	 */
	@RequestMapping("/requestJobDutyList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Map<String, Object> requestJobDutyList() {

		// searchType
		List<Map<String, Object>> list = null;
		Map<String, Object> item = new HashMap<String, Object>();

		try {
			list = getJobDutyList();
			item.put("items", list);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return item;

	}
	private List<Map<String, Object>> getJobDutyList() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");
		
		AdminSearchCondition searchCondition = new AdminSearchCondition();
		if (StringUtil.isEmpty(searchCondition.getSortColumn())) {
			searchCondition.setSortColumn("SORT_ORDER");
		}
		if (StringUtil.isEmpty(searchCondition.getSortType())) {
			searchCondition.setSortType("DESC");
		}
		searchCondition.setFieldName("jobDutyName");
		searchCondition.setUserLocaleCode(user.getLocaleCode());
		searchCondition.setPortalId(portalId);

		SearchResult<JobDuty> searchResult = jobDutyService.list(searchCondition);		

		// 직책정보
		if (searchResult.getEntity() != null) {
			List<JobDuty> jobDutyList = searchResult.getEntity();
			for (JobDuty jobDuty : jobDutyList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "user");
				
				if (user.getLocaleCode().equals("ko")) {
					map.put("name", jobDuty.getJobDutyName());
				} else {
					map.put("name", jobDuty.getJobDutyEnglishName());
				}
				map.put("code", "");
				map.put("parent", "jobDuty");
				map.put("id", jobDuty.getJobDutyCode());
				map.put("empNo", "");
				map.put("email", "");
				map.put("mobile", "");
				map.put("jobTitle", "");
				map.put("teamName", "");
				map.put("icon", "");
				list.add(map);
			}
		}

		return list;
	}
	
	/**
	 * 사용자 정의 결재선/수신처 삭제
	 * @param 
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/ajaxDeleteApprUserLine.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void ajaxDeleteApprUserLine(@RequestParam("userLineId") String userLineId,
			SessionStatus status) {
		try{
			
			apprUserLineSubService.delete(userLineId);	
			apprUserLineService.delete(userLineId);	
			// 세션 상태를 complete
			status.setComplete();
			
		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteAjaxDefLine", ex);
		}
	}	

	
	
	
	
	/*****************************************
	 * 문서 결재의견
	 ****************************************/
	/**
	 * 결재의견 
	 * @param apprId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listApprLineMessage.do")
	public ModelAndView listApprLineMessage( @RequestParam(value="apprId", required = true) String apprId,
			HttpServletRequest request, HttpServletResponse response) { 

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/approval/work/apprLine/listApprLineMessage");

		User user = (User) getRequestAttribute("ikep.user");
		
		// 기안문서 정보
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);
		map.put("locale",   user.getLocaleCode());
		
		// apprDoc
		ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
		
		map.put("apprType",   "0");
		List<ApprLine> apprLineList = apprLineService.listApprLine(map);
		
		// 접수자 전결여부확인
		boolean receiveEnd = false;
		if(apprLineList!=null && apprLineList.size()==1){
			if(apprDoc.getRegisterId().equals(apprLineList.get(0).getApproverId())){
				receiveEnd=true;
			}
		}		
		// 부서 합의 목록		
		List<ApprDoc> subList = apprWorkDocService.subList(map);
		for(ApprLine apprLine : apprLineList) {
			for(ApprDoc apprDoc1 : subList) {
				if(apprLine.getApproverType()==1 && apprLine.getApproverId().equals(apprDoc1.getApprGroupId()) ) {
					List<ApprLine> list = apprLineService.lastApprLine(apprDoc1.getApprId());
					if(list!=null && list.size()>0)
						apprLine.setApprLineList(list);
				}
			}
		}
		// 수신처 목록
		map.put("apprType",   "3");
		List<ApprDoc> subRecList = apprWorkDocService.subList(map);

		ApprDefLineCode	commonCode	=	new	ApprDefLineCode();

		modelAndView.addObject("apprLineList",			apprLineList);
		modelAndView.addObject("commonCode", commonCode);
		modelAndView.addObject("apprDoc", apprDoc);
		modelAndView.addObject("subList", subList);
		modelAndView.addObject("subRecList", subRecList);
		modelAndView.addObject("receiveEnd", receiveEnd);
		return modelAndView;
	}
	
	/**
	 * Ajax 결재선 목록 조회
	 * @param apprId
	 * @return
	 */
	/*@RequestMapping("/ajaxListApprLine.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<ApprLine> ajaxListApprLine(@RequestParam(value="apprId", required = true) String apprId) {
		List<ApprLine> list =	null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("apprId", 	apprId);		
		try {
			list = apprLineService.listApprLine(map);
		} catch (Exception ex) {
			throw new IKEP4AjaxException("code", ex);
		}

		return list;		
	}*/
	
	
	
	
	
}
