/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkPolicy;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPolicyService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Expert Network InterestExpertPolicyManagement controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkInterestExpertPolicyManagementController.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/expertnetwork/admin")
public class ExpertNetworkInterestExpertPolicyManagementController extends CustomTreeController {

	@Autowired
	private ExpertNetworkPolicyService expertNetworkPolicyService;

	/**
	 * [Ajax]
	 * 인기전문가 정책관리 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/interestExpertPolicyManagementViewAjax")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView interestExpertPolicyManagementAjaxView(SessionStatus status) {

		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		ExpertNetworkPolicy expertNetworkPolicy = expertNetworkPolicyService.getByPortalId(portalId);

		// view 연결
		ModelAndView mav = new ModelAndView("/collpack/expertnetwork/admin/interestExpertPolicyManagement");
		
		mav.addObject("expertPolicy", expertNetworkPolicy);

		return mav;
	}

	/**
	 * 인기전문가 정책 저장
	 * @param expertNetworkPolicy - 인기전문가 정책
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/savePolicy")
	@ResponseStatus(HttpStatus.OK)
	public void savePolicy(@ValidEx ExpertNetworkPolicy expertNetworkPolicy, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// Session 정보
		User user = getSessionUser();
		String userId = user.getUserId();

		// 관리자 권한 체크
		checkAdmin(userId);

		try {
			// 수정자 정보 입력
			expertNetworkPolicy.setRegisterId(userId);
			expertNetworkPolicy.setRegisterName(user.getUserName());

			expertNetworkPolicyService.update(expertNetworkPolicy);

			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("savePolicyAjax", ex);
    	}
	}

}
