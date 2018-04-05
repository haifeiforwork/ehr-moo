/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicy;
import com.lgcns.ikep4.collpack.assess.model.AssessmentManagementPolicyPK;
import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementPolicyService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Assessment Management AssessmentManagementAssessmentCriteriaManager controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementAssessmentCriteriaManagerController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/assess/admin")
public class AssessmentManagementAssessmentCriteriaManagerController extends CustomController {

	@Autowired
	private AssessmentManagementPolicyService assessmentManagementPolicyService;

	/**
	 * 평가 기준관리 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/assessmentCriteriaManagerView")
	public ModelAndView batchLogViewerView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		// 관리자 권한
		boolean assessmentManagementAdmin = isAdmin(user.getUserId());

		// 평가 기준
		AssessmentManagementPolicy assessmentManagementPolicy = assessmentManagementPolicyService.read(new AssessmentManagementPolicyPK(portalId));

		// view 연결
		ModelAndView mav = new ModelAndView();
		mav.setViewName("collpack/assess/admin/assessmentCriteriaManagerView");

		mav.addObject("menuId", "32");
		mav.addObject("assessmentManagementAdmin", assessmentManagementAdmin);
		mav.addObject("assessmentManagementPolicy", assessmentManagementPolicy);

		return mav;
	}

	/**
	 * 평가 기준관리 저장
	 * @param assessmentManagementPolicy
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/savePolicy")
	@ResponseStatus(HttpStatus.OK)
	public void savePolicy(@ValidEx AssessmentManagementPolicy assessmentManagementPolicy, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// Session 정보
		User user = getSessionUser();
		String userId = user.getUserId();
		String portalId = user.getPortalId();

		// 관리자 권한 체크
		checkAdmin(userId);

		try {
			// 포털ID
			assessmentManagementPolicy.setPortalId(portalId);
			// 수정자 정보 입력
			assessmentManagementPolicy.setRegisterId(userId);

			assessmentManagementPolicyService.update(assessmentManagementPolicy);

			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("savePolicyAjax", ex);
    	}
	}

}
