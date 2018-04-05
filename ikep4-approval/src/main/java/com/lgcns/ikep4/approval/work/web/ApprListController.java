/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.Code;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprReceptionService;
import com.lgcns.ikep4.approval.work.model.ApprEntrust;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprEntrustService;
import com.lgcns.ikep4.approval.work.service.ApprListService;
import com.lgcns.ikep4.approval.work.service.ApprWorkDocService;
import com.lgcns.ikep4.approval.work.util.DateUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.group.service.GroupService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 전자결재 목록 관리 Controller
 * 
 * @author 서지혜
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/apprlist")
@SessionAttributes("apprlist")
public class ApprListController extends BaseController {

	@Autowired
	private ApprListService apprListService;

	@Autowired
	ApprAdminConfigService apprAdminConfigService;

	@Autowired
	ApprReceptionService apprReceptionService;

	@Autowired
	ApprWorkDocService apprWorkDocService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private ApprEntrustService apprEntrustService;

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
	 * 기안 > 내가 기안한 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprMyRequest.do")
	public ModelAndView getApMyRequest(@RequestParam(value = "linkType", defaultValue = "", required = false) String linkType,
			ApprListSearchCondition apprListSearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprMyRequest" + linkType);

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 내가 기안한 문서 listType
		String listType = "myRequestList";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setLinkType(linkType);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}
		SearchResult<ApprList> searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

		// 문서상태
		List<Code<String>> docStatusList = ApprList.getDocStatusList();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("docStatusList", docStatusList);
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 기안한 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprMyRequestComplete.do")
	public ModelAndView getApMyRequestComplete(ApprListSearchCondition apprListSearchCondition)
			throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprMyRequest");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 완료 > 기안한 문서 listType
		String listType = "myRequestListComplete";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

		// 문서상태
		// List<Code<String>> docStatusList = ApprList.getDocStatusList();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		// mav.addObject("docStatusList", docStatusList);
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 내가 기안한 문서 - 팝업
	 * 
	 * @param linkType
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/popListApprMyRequestComplete.do")
	public ModelAndView popListApprMyRequestComplete(
			@RequestParam(value = "linkType", required = false) String linkType,
			ApprListSearchCondition apprListSearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/popListApprMyRequest");

		/*
		 * if(linkType != null && linkType.equals("mywork")){
		 * mav.setViewName("/approval/work/aplist/readMyApList"); }
		 */

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();

		String listType = "myRequestListComplete";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// 문서상태 목록
		List<Code<String>> docStatusList = ApprList.getDocStatusList();

		mav.addObject("searchCondition", apprListSearchCondition);
		mav.addObject("docStatusList", docStatusList);
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;

	}

	/**
	 * 내가 기안한 문서 - 팝업 Sub
	 * 
	 * @param linkType
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/popListApprMyRequestCompleteSub.do")
	public ModelAndView popListApprMyRequestCompleteSub(
			@RequestParam(value = "linkType", required = false) String linkType,
			ApprListSearchCondition apprListSearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/popListApprMyRequestSub");

		/*
		 * if(linkType != null && linkType.equals("mywork")){
		 * mav.setViewName("/approval/work/aplist/readMyApList"); }
		 */

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		String localeCode = user.getLocaleCode();
		String userId = user.getUserId();

		String listType = "myRequestListComplete";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

		// 문서상태 목록
		List<Code<String>> docStatusList = ApprList.getDocStatusList();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("docStatusList", docStatusList);
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 기안 > 임시저장함
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprTemp.do")
	public ModelAndView listApprTemp(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprTemp");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 기안 > 임시저장함 listType
		String listType = "tempList";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("registDate");
			apprListSearchCondition.setSortType("DESC");
		}

		SearchResult<ApprList> searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 기안 > 회수/반려함
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprReject.do")
	public ModelAndView listApprReject(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprReject");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 기안 > 임시저장함 listType
		String listType = "rejectList";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}

		SearchResult<ApprList> searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

		// 문서상태
		List<Code<String>> docStatusList = ApprList.getDocStatusRejectList();

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("docStatusList", docStatusList);
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 결재 >결재해야할 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprTodo.do")
	public ModelAndView listApprTodo(
			@RequestParam(value = "linkType", defaultValue = "", required = false) String linkType,
			ApprListSearchCondition apprListSearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprList" + linkType);

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String isOverall = "0";

		// 일괄결재 기능 사용 여부
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		isOverall = apprAdminConfig.getIsOverall();

		// 결재 >결재해야할 문서 listType
		String listType = "listApprTodo";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setLinkType(linkType);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchCondition(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("isOverall", isOverall);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 결재 > 결재한 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprComplete.do")
	public ModelAndView listApprComplete(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 결재 > 결재한 문서 listType
		String listType = "listApprComplete";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);

		/**
		 * Default 검색 조건
		 */
		// 문서 상태 진행중을 기본으로 조회
		if (apprListSearchCondition.getSearchApprDocStatus() == null) {
			apprListSearchCondition.setSearchApprDocStatus("1");
		}

		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchCondition(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusList", ApprList.getDocStatusList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 결재한 문서 (결재,합의,참조 포함)
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listCompleteAppr.do")
	public ModelAndView listCompleteAppr(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 완료 > 결재한 문서 listType
		String listType = "listCompleteAppr";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchCondition(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("sortTypeList", ApprList.getSortTypeList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 합의한 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprAgreement.do")
	public ModelAndView listApprAgreement(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 완료 > 합의한 문서 listType
		String listType = "listApprAgreement";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchCondition(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusList", ApprList.getDocStatusList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 참조 > 참조 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprReference.do")
	public ModelAndView listApprReference(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprRefList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 참조 > 참조 문서 listType
		String listType = "listApprReference";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);

		/**
		 * Default 검색 조건
		 */
		// 문서상태를 진행중으로 기본세팅
		if (apprListSearchCondition.getSearchApprDocStatus() == null) {
			apprListSearchCondition.setSearchApprDocStatus("1");
		}

		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}
		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionRef(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusList", ApprList.getDocStatusList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 참조 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprCompleteRef.do")
	public ModelAndView listApprCompleteRef(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprRefList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 완료 > 참조 문서 listType
		String listType = "listApprCompleteRef";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionRef(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 열람권한 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprReading.do")
	public ModelAndView listApprReading(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprRefList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 완료 > 열람권한 문서 listType
		String listType = "listApprReading";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setGroupId(user.getGroupId());

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}
		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionRead(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 열람 부여문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprReadingAssign.do")
	public ModelAndView listApprReadingAssign(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprRefList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 열람부여 문서 listType
		String listType = "listApprReadingAssign";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setGroupId(user.getGroupId());

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}
		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionRead(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 참조 > 위임문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprDelegate.do")
	public ModelAndView listApprDelegate(@RequestParam(value = "linkType", required = false) String linkType,
			ApprListSearchCondition apprListSearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprRefList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 참조 > 위임문서 listType
		String listType = "listApprDelegate";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setGroupId(user.getGroupId());

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}
		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}
		SearchResult<ApprList> searchResult = this.apprListService
				.listBySearchConditionLineEntrust(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusList", ApprList.getDocStatusList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 환경설정 > 수임현황 > view (위임기간에 결재한 목록)
	 * 
	 * @param apprListSearchCondition
	 * @param entrustId
	 * @param type
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprEntrust.do")
	public ModelAndView listApprEntrust(@RequestParam(value = "entrustId", required = false) String entrustId,
			@RequestParam(value = "entrustType", required = false) String entrustType,
			ApprListSearchCondition apprListSearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprEntrust");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String userId = user.getUserId();
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 환경설정 > 수임현황 > view listType
		String listType = "listApprEntrust";

		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setGroupId(user.getGroupId());

		// 위임정보
		ApprEntrust entrust = new ApprEntrust();
		entrust.setPortalId(portalId);
		entrust.setEntrustId(entrustId);
		entrust = apprEntrustService.entrustDetail(entrust);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

		// 위임기간
		apprListSearchCondition.setSearchStartDate(formatter.format(entrust.getStartDate()));
		apprListSearchCondition.setSearchEndDate(formatter.format(entrust.getEndDate()));

		SearchResult<ApprList> searchResult = this.apprListService
				.listBySearchConditionLineEntrust(apprListSearchCondition);

		mav.addObject("entrust", entrust);
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("entrustType", entrustType);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 임시저장, 회수/반려 목록에서 삭제
	 * 
	 * @param apprIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deleteApprList.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String deleteApprList(@RequestParam("apprIds") List<String> apprIds, SessionStatus status) {

		String returnValue = "";

		try {

			for (int i = 0; i < apprIds.size(); i++) {

				String apprId = apprIds.get(i);
				apprWorkDocService.deleteApprDoc(apprId);
			}
			status.setComplete();
			returnValue = "OK";

		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteApprList", ex);
		}

		return returnValue;
	}

	/**
	 * 완료 > 전체문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/readApprAllList.do")
	public ModelAndView listApprIntegrate(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprAllList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("userId", user.getUserId());
		List<Group> groupList = groupService.selectUserGroup(groupMap);

		// 완료 > 전체문서 listType
		String listType = "listApprIntegrate";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setGroupId(user.getGroupId());
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchGroupId() == null) {
			apprListSearchCondition.setSearchGroupId(user.getGroupId());
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = null;
		if (apprListSearchCondition.getSearchListType() == null
				|| apprListSearchCondition.getSearchListType().equals("")) {

			searchResult = this.apprListService.listBySearchConditionIntegrate(apprListSearchCondition);

		} else {
			// 결재상태별 조회
			if (apprListSearchCondition.getSearchListType().equals("appr_ad")) {
				// 기안한 문서
				apprListSearchCondition.setListType("myRequestListComplete");
				searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

			} else if (apprListSearchCondition.getSearchListType().equals("appr_al")) {
				// 결재한 문서
				apprListSearchCondition.setListType("listCompleteAppr");
				apprListSearchCondition.setSearchlistSortType("AL");
				searchResult = this.apprListService.listBySearchCondition(apprListSearchCondition);

			} else if (apprListSearchCondition.getSearchListType().equals("appr_al2")) {
				// 합의문서
				apprListSearchCondition.setListType("listApprAgreement");
				searchResult = this.apprListService.listBySearchCondition(apprListSearchCondition);

			} else if (apprListSearchCondition.getSearchListType().equals("appr_ar")) {
				// 참조문서
				apprListSearchCondition.setListType("listApprCompleteRef");
				searchResult = this.apprListService.listBySearchConditionRef(apprListSearchCondition);

			} else if (apprListSearchCondition.getSearchListType().equals("appr_are")) {
				// 열람문서
				apprListSearchCondition.setListType("listApprReading");
				searchResult = this.apprListService.listBySearchConditionRead(apprListSearchCondition);

			} else if (apprListSearchCondition.getSearchListType().equals("appr_dept")) {
				// 부서결재
				apprListSearchCondition.setListType("listApprDeptIntegrate");
				searchResult = this.apprListService.listBySearchConditionDeptRec(apprListSearchCondition);

			}
		}
		apprListSearchCondition.setListType(listType);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("typeList", ApprList.getTypeList());
		mav.addObject("groupList", groupList);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 수신 > 부서수신문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprDeptRec.do")
	public ModelAndView listApprDeptRec(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 수신 > 부서수신문서 listType
		String listType = "listApprDeptRec";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		/**
		 * Default 검색 조건
		 */
		// 문서상태를 접수로 기본세팅
		if (apprListSearchCondition.getSearchApprDocStatus() == null) {
			apprListSearchCondition.setSearchApprDocStatus("6");
		}

		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}

		// 접수자 지정/부서전체지정
		String receptionType = "";
		ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(portalId);
		receptionType = apprAdminConfig.getReceptionType();
		
		// 접수 담당자 여부
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		
		if(receptionType.equals("0")) {
			
			if (apprReceptionService.existReceptionUser(map)) {
				apprListSearchCondition.setReceptionUser(true);
			} else {
				apprListSearchCondition.setReceptionUser(false);
			}
		}else if(receptionType.equals("1")){
			apprListSearchCondition.setReceptionUser(false);
		}
		

		SearchResult<ApprList> searchResult = this.apprListService
				.listBySearchConditionDeptRec(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusList", ApprList.getDocStatusAllList());
		mav.addObject("docTypeList", ApprList.getDocTypeList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 수신 > 개인수신문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprUserRec.do")
	public ModelAndView listApprUserRec(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 수신 > 개인수신문서 listType
		String listType = "listApprUserRec";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		/**
		 * Default 검색 조건
		 */
		// 문서상태를 접수로 기본세팅
		if (apprListSearchCondition.getSearchApprDocStatus() == null) {
			apprListSearchCondition.setSearchApprDocStatus("6");
		}

		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}

		SearchResult<ApprList> searchResult = this.apprListService
				.listBySearchConditionDeptRec(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusList", ApprList.getDocStatusAllList());
		mav.addObject("docTypeList", ApprList.getDocTypeList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 부서결재문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprDeptIntegrate.do")
	public ModelAndView listApprDeptIntegrate(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/readApprList");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 완료 > 부서결재문서 listType
		String listType = "listApprDeptIntegrate";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setGroupId(user.getGroupId());

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("userId", user.getUserId());
		List<Group> groupList = groupService.selectUserGroup(groupMap);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprEndDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchGroupId() == null) {
			apprListSearchCondition.setSearchGroupId(user.getGroupId());
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService
				.listBySearchConditionDeptRec(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("groupList", groupList);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 결재 > 검토해야할 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprRequestExam.do")
	public ModelAndView listApprRequestExam(@RequestParam(value = "linkType", required = false) String linkType,
			ApprListSearchCondition apprListSearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprExam");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 결재 > 검토해야할 문서 listType
		String listType = "listApprRequestExam";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("examReqDate");
			apprListSearchCondition.setSortType("DESC");
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionExam(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 결재 > 검토한 문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprExam.do")
	public ModelAndView listApprExam(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprExam");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 결재 > 검토한 문서 listType
		String listType = "listApprExam";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("examDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionExam(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusList", ApprList.getDocStatusList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 검토완료함
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprCompleteExam.do")
	public ModelAndView listApprCompleteExam(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprExam");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 완료 > 검토완료함 listType
		String listType = "listApprCompleteExam";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("examDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listBySearchConditionExam(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusList", ApprList.getDocStatusList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 전체문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprDocAllAdmin.do")
	public ModelAndView listApprDocAllAdmin(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprMyRequest");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 완료 > 전체문서 listType
		String listType = "listApprDocAllAdmin";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusAllList", ApprList.getDocStatusAllList());
		mav.addObject("docTypeList", ApprList.getDocTypeList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 완료 > 전체결재문서
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprDocAllUser.do")
	public ModelAndView listApprDocAllUser(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprMyRequest");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 완료 > 전체결재문서 listType
		String listType = "listApprDocAllUser";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}
		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusAllList", ApprList.getDocStatusAllList());
		mav.addObject("docTypeList", ApprList.getDocTypeList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 현황관리 > 결재문서 보안관리
	 * 
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprDocSecurity.do")
	public ModelAndView listApprDocSecurity(ApprListSearchCondition apprListSearchCondition) throws ServletException,
			IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprlist/listApprMyRequest");

		if (apprListSearchCondition == null)
			apprListSearchCondition = new ApprListSearchCondition();

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");
		String userId = user.getUserId();

		// 현황관리 > 결재문서 보안관리 listType
		String listType = "listApprDocSecurity";

		apprListSearchCondition.setUserId(userId);
		apprListSearchCondition.setListType(listType);
		apprListSearchCondition.setPortalId(portalId);

		// Default 검색 조건
		if (apprListSearchCondition.getSortColumn() == null || apprListSearchCondition.getSortColumn().equals("")) {
			apprListSearchCondition.setSortColumn("apprReqDate");
			apprListSearchCondition.setSortType("DESC");
		}

		if (apprListSearchCondition.getSearchStartDate() == null || apprListSearchCondition.getSearchEndDate() == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

			apprListSearchCondition.setSearchStartDate(formatter.format(DateUtil.prevWeek(2)));
			apprListSearchCondition.setSearchEndDate(formatter.format(new Date()));
		}

		SearchResult<ApprList> searchResult = this.apprListService.listByMyRequest(apprListSearchCondition);

		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		mav.addObject("commonCode", ApprList.getPageNumList());
		mav.addObject("docStatusAllList", ApprList.getDocStatusAllList());
		mav.addObject("docTypeList", ApprList.getDocTypeList());
		mav.addObject("docSecurityList", ApprList.getDocSecurityList());
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));

		return mav;
	}

	/**
	 * 보안설정 및 해제
	 * 
	 * @param apprIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateSetSecurity.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String updateSetSecurity(@RequestParam("apprIds") List<String> apprIds, @RequestParam("isHidden") String isHidden,
			SessionStatus status) {

		String returnValue = "";

		try {

			for (int i = 0; i < apprIds.size(); i++) {

				String apprId = apprIds.get(i);
				apprListService.updateSetSecurity(apprId, isHidden);
			}

			status.setComplete();
			returnValue = "OK";

		} catch (Exception ex) {
			throw new IKEP4AjaxException("deleteApprList", ex);
		}

		return returnValue;
	}

}
