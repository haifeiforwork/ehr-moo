/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapPolicy;
import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPolicyService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.validator.annotation.ValidEx;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Knowledge Map PopularityKnowledgePolicyManagement controller
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPopularityKnowledgePolicyManagementController.java 16457 2011-08-30 04:20:17Z giljae $
 */
@Controller
@RequestMapping(value = "/collpack/knowledgehub/admin")
public class KnowledgeMapPopularityKnowledgePolicyManagementController extends CustomTreeController {

	@Autowired
	private KnowledgeMapPolicyService knowledgeMapPolicyService;

	/**
	 * [Ajax]
	 * 인기지식 정책 관리 View
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/popularityKnowledgePolicyManagementViewAjax")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView popularityKnowledgePolicyManagementAjaxView(SessionStatus status) {
		// Session 정보
		User user = getSessionUser();
		String portalId = user.getPortalId();

		KnowledgeMapPolicy knowledgeMapPolicy = knowledgeMapPolicyService.getByPortalId(portalId);

		// view 연결
		ModelAndView mav = new ModelAndView("collpack/knowledgehub/admin/popularityKnowledgePolicyManagement");

		mav.addObject("knowledgeMapPolicy", knowledgeMapPolicy);

		return mav;
	}

	/**
	 * 인기지식 정책 저장
	 * @param knowledgeMapPolicy - 정책
	 * @param result
	 * @param status
	 */
	@RequestMapping(value = "/savePolicy")
	@ResponseStatus(HttpStatus.OK)
	public void savePolicy(@ValidEx KnowledgeMapPolicy knowledgeMapPolicy, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			throw new IKEP4AjaxValidationException(result, messageSource);
		}

		// Session 정보
		User user = getSessionUser();
		String userId = user.getUserId();

		// 관리자 권한 체크
		checkAdmin(userId);

		try {
			knowledgeMapPolicyService.update(knowledgeMapPolicy);

			status.setComplete();
    	} catch(Exception ex) {
    		throw new IKEP4AjaxException("savePolicyAjax", ex);
    	}
	}

}
