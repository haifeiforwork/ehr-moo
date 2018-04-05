/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.web;

import java.util.List;

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
import com.lgcns.ikep4.approval.admin.model.ApprCode;
import com.lgcns.ikep4.approval.admin.model.ApprForm;
import com.lgcns.ikep4.approval.admin.model.CommonCode;
import com.lgcns.ikep4.approval.admin.search.ApprFormSearchCondition;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.approval.admin.service.ApprAdminFormService;
import com.lgcns.ikep4.approval.admin.service.ApprCodeService;
import com.lgcns.ikep4.approval.admin.service.ApprDefLineService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.web.TreeMaker;


/**
 * ApprWorkForm 컨트롤러
 * 
 * @author wonchu
 * @version $Id: FormController.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Controller
@RequestMapping(value = "/approval/work/apprWorkForm")
@SessionAttributes("apprWorkForm")
public class ApprWorkFormController extends BaseController {

	@Autowired
	private ApprAdminFormService apprAdminFormService;

	@Autowired
	private ApprCodeService apprCodeService;

	@Autowired
	private ApprDefLineService apprDefLineService;

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
	 * apprForm 카테고리 트리 및 목록 템플릿
	 * 
	 * @param nothing
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/listApprForm.do", method = RequestMethod.GET)
	public ModelAndView listApprForm(
			@RequestParam(value = "linkType", defaultValue = "", required = false) String linkType) {
		
		// tiles
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/listApprForm"+linkType);

		// session
		User user = (User) getRequestAttribute("ikep.user");

		// category root 코드 아이디를 구함
		String formParentId = StringUtil.nvl(
				apprCodeService.getCodeIdByCodeValue(CommonCode.APPR_CATEGORY, user.getPortalId()), "");

		// category tree json 생성
		String categoryTreeJson = "{}";
		if (!"".equals(formParentId)) {
			List<ApprCode> apprCodeList = apprCodeService.getApprCodeList(formParentId, user.getLocaleCode(), "1");
			categoryTreeJson = TreeMaker.init(apprCodeList, "codeId", "parentCodeId", "childCount").create()
					.toJsonString();
		}

		// 디폴트 검색 조건 셋팅
		ApprFormSearchCondition apprFormSearchCondition = new ApprFormSearchCondition();
		apprFormSearchCondition.setSortColumn("FORM_PARENT_ID");
		apprFormSearchCondition.setSortType("ASC");
		apprFormSearchCondition.setFormParentId(formParentId);
		apprFormSearchCondition.setTopFormParentId(formParentId);
		apprFormSearchCondition.setLinkType(linkType);

		ApprCode apprCode = apprCodeService.readApprCode(formParentId);
		if (apprCode != null) {
			apprFormSearchCondition.setFormParentName("ko".equals(user.getLocaleCode()) ? apprCode.getCodeKrName()
					: apprCode.getCodeEnName());
		}

		// set return info
		mav.addObject("searchCondition", apprFormSearchCondition);
		mav.addObject("categoryTreeJson", categoryTreeJson);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(user.getPortalId()));

		return mav;
	}

	@RequestMapping(value = "/listApprForm.do", method = RequestMethod.POST)
	public ModelAndView listApprForm(ApprFormSearchCondition apprFormSearchCondition) {

		String link = apprFormSearchCondition.getLinkType()==null || "".equals(apprFormSearchCondition.getLinkType())?"":apprFormSearchCondition.getLinkType();
		
		// tiles
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/listApprForm" + link);

		// session
		User user = (User) getRequestAttribute("ikep.user");

		// category tree json 생성
		String categoryTreeJson = "{}";

		List<ApprCode> apprCodeList = apprCodeService.getApprCodeList(apprFormSearchCondition.getTopFormParentId(),
				user.getLocaleCode(), "1");
		categoryTreeJson = TreeMaker.init(apprCodeList, "codeId", "parentCodeId", "childCount").create().toJsonString();

		ApprCode apprCode = apprCodeService.readApprCode(apprFormSearchCondition.getFormParentId());
		if (apprCode != null) {
			apprFormSearchCondition.setFormParentName("ko".equals(user.getLocaleCode()) ? apprCode.getCodeKrName()
					: apprCode.getCodeEnName());
		}

		// set return info
		mav.addObject("searchCondition", apprFormSearchCondition);
		mav.addObject("categoryTreeJson", categoryTreeJson);
		mav.addObject("isSystemAdmin", this.isSystemAdmin(user));
		mav.addObject("isReadAll", this.isReadAll(user.getPortalId()));

		return mav;
	}

	/**
	 * apprForm AJAX 목록
	 * 
	 * @param ApprFormSearchCondition
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ajaxListApprForm.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView ajaxListApprForm(ApprFormSearchCondition apprFormSearchCondition) {

		// jsp - 페이지에 include는 html을 생성하기 때문에 tiles를 사용 안함
		ModelAndView mav = new ModelAndView("/approval/work/apprForm/ajaxListApprForm");

		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");
			apprFormSearchCondition.setUserId(user.getUserId());
			apprFormSearchCondition.setPortalId(user.getPortalId());
			apprFormSearchCondition.setLocaleCode(user.getLocaleCode());
			apprFormSearchCondition.setUsage(1);
			apprFormSearchCondition.setMode("G");
			
			ApprAdminConfig apprAdminConfig = apprAdminConfigService.adminConfigDetail(user.getPortalId());
			
			// 수신처 미사용시 협조공문은 리스트에서 제외
			if(apprAdminConfig.getIsReceive().equals("0")){
				apprFormSearchCondition.setReceive(apprAdminConfig.getIsReceive());
			}
			
			// 폼 목록
			SearchResult<ApprForm> searchResult = apprAdminFormService.listBySearchCondition(apprFormSearchCondition);
			CommonCode commonCode = new CommonCode();

			// set return info
			mav.addObject("searchResult", searchResult);
			mav.addObject("numList", commonCode.getPageNumList());
			mav.addObject("searchCondition", searchResult.getSearchCondition());
		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxListApprForm", ex);
		}
		return mav;
	}

	/**
	 * 즐겨찾기
	 * 
	 * @param
	 * @param apprId
	 * @param String
	 * @return
	 */
	@RequestMapping(value = "/ajaxSetApprFavorite")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	void ajaxSetApprFavorite(String formId, String mode, SessionStatus status) {
		try {
			// session
			User user = (User) getRequestAttribute("ikep.user");

			ApprForm apprForm = new ApprForm();

			apprForm.setFormId(formId);
			apprForm.setMode(mode);
			apprForm.setPortalId(user.getPortalId());
			apprForm.setRegisterId(user.getUserId());
			apprForm.setRegisterName(user.getUserName());

			// apprFavorite
			apprAdminFormService.setApprFavorite(apprForm);

			status.setComplete();

		} catch (Exception ex) {
			throw new IKEP4AjaxException("ajaxSetFavorite", ex);
		}
	}

}