/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants;
import com.lgcns.ikep4.collpack.ideation.model.IdPolicy;
import com.lgcns.ikep4.collpack.ideation.service.IdPolicyService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: IdPolicyController.java 3998 2011-03-24 06:49:10Z loverfairy
 *          $$
 */
@Controller
// @SessionAttributes("idPolicy")
public class IdPolicyController extends IdeationBaseController {

	@Autowired
	private IdPolicyService idPolicyService;


	/**
	 * 제안 정책 폼
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/suggestionPolicyForm.do", method = RequestMethod.GET)
	public String getFormSuggestionPolicy(Model model, @ModelAttribute("isAdmin") boolean isAdmin) {

		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		IdPolicy suggestionPolicy = idPolicyService.get(IdeationConstants.POLICY_SUGGESTION, portal.getPortalId());

		model.addAttribute("suggestionPolicy", suggestionPolicy);

		return "collpack/ideation/suggestionPolicyForm";
	}
	
	/**
	 * 기여 활동 폼
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/contributionPolicyForm.do", method = RequestMethod.GET)
	public String getFormContributionPolicy(Model model, @ModelAttribute("isAdmin") boolean isAdmin) {

		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		IdPolicy contributionPolicy = idPolicyService.get(IdeationConstants.POLICY_CONTRIBUTION, portal.getPortalId());

		model.addAttribute("activityPolicy", contributionPolicy);

		return "collpack/ideation/contributionForm";
	}
	
	/**
	 * 우수 아이디어 정책 폼
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/bestPolicyForm.do", method = RequestMethod.GET)
	public String getFormBestPolicy(Model model, @ModelAttribute("isAdmin") boolean isAdmin) {

		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		IdPolicy bestPolicy = idPolicyService.get(IdeationConstants.POLICY_BEST, portal.getPortalId());

		model.addAttribute("bestPolicy", bestPolicy);

		return "collpack/ideation/bestPolicyForm";
	}
	
	

	/**
	 * 정책 등록
	 * @param idPolicy		정책 ID
	 * @param isAdmin
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createPolicy.do", method = RequestMethod.POST)
	public String onSubmit(IdPolicy idPolicy
						, @ModelAttribute("isAdmin") boolean isAdmin, ModelMap model) {

		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		idPolicy.setPortalId(portal.getPortalId());
		
		User user = (User) getRequestAttribute("ikep.user");
		idPolicy.setRegisterId(user.getUserId());
		idPolicy.setRegisterName(user.getUserName());

		if (StringUtil.isEmpty(idPolicy.getPolicyId())) {
			idPolicyService.create(idPolicy);
		} else {
			idPolicyService.update(idPolicy);
		}
		
		String page = "";
		if(idPolicy.getPolicyType().equals(IdeationConstants.POLICY_SUGGESTION)){
			page = "suggestionPolicyForm.do";
			
		} else if(idPolicy.getPolicyType().equals(IdeationConstants.POLICY_CONTRIBUTION)){
			page = "contributionPolicyForm.do";
			
		} else if(idPolicy.getPolicyType().equals(IdeationConstants.POLICY_BEST)){
			page = "bestPolicyForm.do";
			
		} 
		
		model.clear();
		
		return "redirect:" + page;
	}
	
	
}
