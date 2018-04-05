/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.model.ApprDoc;
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.model.ApprOfficialConfig;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprAdminFormService;
import com.lgcns.ikep4.approval.admin.service.ApprOfficialConfigService;
import com.lgcns.ikep4.approval.work.model.ApprList;
import com.lgcns.ikep4.approval.work.model.ApprOfficial;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprOfficialService;
import com.lgcns.ikep4.approval.work.service.ApprWorkDocService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 공문 시행
 * 
 * @author 유승목
 * @version $Id$
 */
@Controller
@RequestMapping(value = "/approval/work/apprOfficial")
public class ApprOfficialController extends BaseController {

	@Autowired
	private ApprOfficialConfigService apprOfficialConfigService;

	@Autowired
	private ApprOfficialService apprOfficialService;

	@Autowired
	private ApprWorkDocService apprWorkDocService;

	@Autowired
	ApprAdminConfigService apprAdminConfigService;

	@Autowired
	private ApprAdminFormService apprAdminFormService;

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
	 * 공문 시행 작성
	 * 
	 * @param apprId
	 * @return
	 */
	@RequestMapping(value = "/apprOfficialForm.do")
	public ModelAndView apprOfficialForm(String apprId) {

		ModelAndView mav = new ModelAndView("/approval/work/apprOfficial/apprOfficialForm");

		try {
			User user = (User) getRequestAttribute("ikep.user");

			// 공문 설정 정보
			ApprOfficialConfig officialConfig = apprOfficialConfigService.read("");

			// 결재 문서 정보
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", apprId);
			map.put("locale", user.getLocaleCode());
			ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
			ApprForm apprForm = apprAdminFormService.readApprForm(apprDoc.getFormId());

			mav.addObject("officialConfig", officialConfig);
			mav.addObject("apprDoc", apprDoc);
			mav.addObject("apprForm", apprForm);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 공문 시행 저장
	 * 
	 * @param apprOfficial
	 * @return
	 */
	@RequestMapping(value = "/saveApprOfficial.do")
	public String saveApprOfficial(ApprOfficial apprOfficial) {

		try {

			User user = (User) getRequestAttribute("ikep.user");
			apprOfficial.setRegisterId(user.getUserId());
			apprOfficial.setRegisterName(user.getUserName());

			String portalId = (String) getRequestAttribute("ikep.portalId");
			apprOfficial.setPortalId(portalId);

			if (StringUtil.isEmpty(apprOfficial.getIsHeader())) {
				apprOfficial.setIsHeader("0");
			}
			if (StringUtil.isEmpty(apprOfficial.getIsFooter())) {
				apprOfficial.setIsFooter("0");
			}
			if (StringUtil.isEmpty(apprOfficial.getIsHomepage())) {
				apprOfficial.setIsHomepage("0");
			}
			if (StringUtil.isEmpty(apprOfficial.getIsInCharge())) {
				apprOfficial.setIsInCharge("0");
			}
			if (StringUtil.isEmpty(apprOfficial.getIsMail())) {
				apprOfficial.setIsMail("0");
			}
			if (StringUtil.isEmpty(apprOfficial.getIsSeal())) {
				apprOfficial.setIsSeal("0");
			}

			apprOfficialService.create(apprOfficial);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "redirect:/approval/work/apprOfficial/listApprOfficial.do";
	}

	/**
	 * 공문 시행 삭제
	 * 
	 * @param officialId
	 * @return
	 */
	@RequestMapping(value = "/deleteApprOfficial.do")
	public @ResponseBody
	String deleteApprOfficial(String officialId) {

		try {

			apprOfficialService.delete(officialId);

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return "ok";
	}

	/**
	 * 공문 시행 목록
	 * 
	 * @param linkType
	 * @param apprListSearchCondition
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listApprOfficial.do")
	public ModelAndView getApMyRequest(@RequestParam(value = "linkType", required = false) String linkType,
			ApprListSearchCondition apprListSearchCondition) throws ServletException, IOException {

		ModelAndView mav = new ModelAndView("/approval/work/apprOfficial/listApprOfficial");

		try {
			if (apprListSearchCondition == null) {
				apprListSearchCondition = new ApprListSearchCondition();
			}

			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");

			String localeCode = user.getLocaleCode();
			String userId = user.getUserId();

			String listType = "myRequestList";

			apprListSearchCondition.setUserId(userId);
			apprListSearchCondition.setListType(listType);
			apprListSearchCondition.setPortalId(portalId);

			if (StringUtil.isEmpty(apprListSearchCondition.getSortColumn())) {
				apprListSearchCondition.setSortColumn("registDate");
			}
			if (StringUtil.isEmpty(apprListSearchCondition.getSortType())) {
				apprListSearchCondition.setSortType("DESC");
			}

			SearchResult<ApprOfficial> searchResult = apprOfficialService.list(apprListSearchCondition);

			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
			mav.addObject("commonCode", ApprList.getPageNumList());
			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portalId));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * 공문 시행 화면
	 * 
	 * @param officialId
	 * @return
	 */
	@RequestMapping(value = "/viewApprOfficial.do")
	public ModelAndView viewApprOfficial(String officialId, HttpServletRequest req) {

		ModelAndView mav = new ModelAndView("/approval/work/apprOfficial/viewApprOfficial");

		try {
			User user = (User) getRequestAttribute("ikep.user");
			String portalId = (String) getRequestAttribute("ikep.portalId");

			// 공문 정보
			ApprOfficial apprOfficial = apprOfficialService.read(officialId);

			// 결재 문서 정보
			Map<String, String> map = new HashMap<String, String>();
			map.put("apprId", apprOfficial.getApprId());
			map.put("locale", user.getLocaleCode());
			ApprDoc apprDoc = apprWorkDocService.readApprDoc(map);
			ApprForm apprForm = apprAdminFormService.readApprForm(apprDoc.getFormId());

			mav.addObject("apprOfficial", apprOfficial);
			mav.addObject("apprForm", apprForm);
			mav.addObject("apprDoc", apprDoc);
			mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
			mav.addObject("isReadAll", this.isReadAll(portalId));
			mav.addObject("ikepBaseUrl", HttpUtil.getWebAppUrl(req));

		} catch (Exception ex) {
			throw new IKEP4ApplicationException("", ex);
		}

		return mav;
	}

	/**
	 * PC 저장
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createApprOfficialPC.do")
	public ModelAndView createApprDocPC() {
		ModelAndView mav = new ModelAndView("/approval/work/apprOfficial/createApprOfficialPC");
		return mav;
	}

	/**
	 * 인쇄
	 * 
	 * @return
	 */
	@RequestMapping(value = "/viewApprOfficialPrint.do")
	public ModelAndView viewApprDocPrint() {
		ModelAndView mav = new ModelAndView("/approval/work/apprOfficial/viewApprOfficialPrint");
		return mav;
	}
}
