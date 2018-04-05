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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprAdminFormService;
import com.lgcns.ikep4.approval.admin.service.ApprCodeService;
import com.lgcns.ikep4.approval.admin.service.ApprReceptionService;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprListService;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 결재 진행 리스트
 * 
 * @author 유승목
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/apprMain")
public class ApprMainController extends BaseController {

	@Autowired
	private ApprListService apprListService;

	@Autowired
	ApprAdminConfigService apprAdminConfigService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private ApprAdminFormService apprAdminFormService;

	@Autowired
	private ApprCodeService apprCodeService;
	
	@Autowired
	ApprReceptionService apprReceptionService;

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
	 * 결재 진행 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apprMainList.do")
	public ModelAndView mainList(@RequestParam(value = "cookieFlag", required = false) String cookieFlag) {

		ModelAndView mav = new ModelAndView("/approval/work/apprMain/apprMainList");

		User user = (User) getRequestAttribute(CommonCode.IKEP_USER);
		String portalId = (String) getRequestAttribute("ikep.portalId");

		// 검색
		String formParentId = StringUtil.nvl(
				apprCodeService.getCodeIdByCodeValue(CommonCode.APPR_CATEGORY, user.getPortalId()), "");
		ApprFormSearchCondition apprFormSearchCondition = new ApprFormSearchCondition();
		apprFormSearchCondition.setSortColumn("FORM_PARENT_ID");
		apprFormSearchCondition.setSortType("ASC");
		apprFormSearchCondition.setTemplateType("main");
		apprFormSearchCondition.setFormParentId(formParentId);
		apprFormSearchCondition.setTopFormParentId(formParentId);
		mav.addObject("searchCondition", apprFormSearchCondition);

		// 결재해야할 문서
		ApprListSearchCondition apprListSearchCondition = new ApprListSearchCondition();
		apprListSearchCondition.setUserId(user.getUserId());
		apprListSearchCondition.setPortalId(portalId);
		apprListSearchCondition.setListType("listApprTodo");
		apprListSearchCondition.setSortColumn("apprReqDate");
		apprListSearchCondition.setSortType("DESC");
		apprListSearchCondition.setLinkType("MAIN");

		SearchResult<ApprList> myTodoResult = this.apprListService.listBySearchCondition(apprListSearchCondition);
		mav.addObject("myTodoResult", myTodoResult);

		// 기안한 문서
		ApprListSearchCondition apprListSearchCondition2 = new ApprListSearchCondition();
		apprListSearchCondition2.setUserId(user.getUserId());
		apprListSearchCondition2.setPortalId(portalId);
		apprListSearchCondition2.setListType("myRequestList");
		apprListSearchCondition2.setSortColumn("apprReqDate");
		apprListSearchCondition2.setSortType("DESC");
		apprListSearchCondition2.setLinkType("MAIN");

		SearchResult<ApprList> myRequestResult = this.apprListService.listByMyRequest(apprListSearchCondition2);
		mav.addObject("myRequestResult", myRequestResult);

		// 회수,반려 문서
		ApprListSearchCondition apprListSearchCondition3 = new ApprListSearchCondition();
		apprListSearchCondition3.setUserId(user.getUserId());
		apprListSearchCondition3.setPortalId(portalId);
		apprListSearchCondition3.setListType("rejectList");
		apprListSearchCondition3.setSortColumn("apprReqDate");
		apprListSearchCondition3.setSortType("DESC");
		apprListSearchCondition3.setLinkType("MAIN");

		SearchResult<ApprList> myRejectResult = this.apprListService.listByMyRequest(apprListSearchCondition3);
		mav.addObject("myRejectResult", myRejectResult);

		// 부서수신 문서
		ApprListSearchCondition apprListSearchCondition4 = new ApprListSearchCondition();
		apprListSearchCondition4.setUserId(user.getUserId());
		apprListSearchCondition4.setPortalId(portalId);
		apprListSearchCondition4.setListType("listApprDeptRec");
		apprListSearchCondition4.setSortColumn("apprReqDate");
		apprListSearchCondition4.setSortType("DESC");
		apprListSearchCondition4.setSearchApprDocStatus("6");
		apprListSearchCondition4.setLinkType("MAIN");
		
		// 접수 담당자 여부
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());

		if (apprReceptionService.existReceptionUser(map)) {
			apprListSearchCondition4.setReceptionUser(true);
		} else {
			apprListSearchCondition4.setReceptionUser(false);
		}

		SearchResult<ApprList> deptRecResult = this.apprListService
				.listBySearchConditionDeptRec(apprListSearchCondition4);
		mav.addObject("deptRecResult", deptRecResult);

		// 개인수신 문서
		ApprListSearchCondition apprListSearchCondition5 = new ApprListSearchCondition();
		apprListSearchCondition5.setUserId(user.getUserId());
		apprListSearchCondition5.setPortalId(portalId);
		apprListSearchCondition5.setListType("listApprUserRec");
		apprListSearchCondition5.setSortColumn("apprReqDate");
		apprListSearchCondition5.setSortType("DESC");
		apprListSearchCondition5.setSearchApprDocStatus("6");
		apprListSearchCondition5.setLinkType("MAIN");

		SearchResult<ApprList> userRecResult = this.apprListService
				.listBySearchConditionDeptRec(apprListSearchCondition5);
		mav.addObject("userRecResult", userRecResult);

		// 최근 기안한 양식
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("userId", user.getUserId());
		map1.put("portalId", portalId);
		map1.put("count", 5);

		List<ApprForm> recentFormList = apprAdminFormService.getApprRecentList(map1);
		mav.addObject("recentFormList", recentFormList);

		// 즐겨 찾기한 양식
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("userId", user.getUserId());
		map2.put("portalId", portalId);
		map2.put("count", 5);

		List<ApprForm> favoriteFormList = apprAdminFormService.getApprFavoriteList(map2);
		mav.addObject("favoriteFormList", favoriteFormList);

		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(portalId));
		mav.addObject("cookieFlag", cookieFlag);

		return mav;
	}

}
