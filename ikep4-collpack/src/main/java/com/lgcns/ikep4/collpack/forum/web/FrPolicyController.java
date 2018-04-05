/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgcns.ikep4.collpack.forum.constants.ForumConstants;
import com.lgcns.ikep4.collpack.forum.model.FrPolicy;
import com.lgcns.ikep4.collpack.forum.service.FrPolicyService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: FrPolicyController.java 3998 2011-03-24 06:49:10Z loverfairy
 *          $$
 */
@Controller
// @SessionAttributes("frPolicy")
public class FrPolicyController extends ForumBaseController {

	@Autowired
	private FrPolicyService frPolicyService;

	/**
	 * 인기토론 정책 폼
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/popularPolicyForm.do", method = RequestMethod.GET)
	public String getFormPopularPolicy(Model model, @ModelAttribute("isAdmin") boolean isAdmin) {

		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		FrPolicy popularDisPolicy = frPolicyService.get(ForumConstants.POLICY_POPULAR_DISCUSSION, portal.getPortalId());

		FrPolicy popularItemPolicy = frPolicyService.get(ForumConstants.POLICY_POPULAR_ITEM, portal.getPortalId());

		model.addAttribute("popularDisPolicy", popularDisPolicy);

		model.addAttribute("popularItemPolicy", popularItemPolicy);

		return "collpack/forum/popularPolicyForm";
	}

	/**
	 * 토론활동점수 정책 폼
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/activityPolicyForm.do", method = RequestMethod.GET)
	public String getFormActivityPolicy(Model model, @ModelAttribute("isAdmin") boolean isAdmin) {

		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		FrPolicy activityPolicy = frPolicyService.get(ForumConstants.POLICY_ACTIVITY, portal.getPortalId());

		model.addAttribute("activityPolicy", activityPolicy);

		return "collpack/forum/activityPolicyForm";
	}
	
	/**
	 * 우수토론 정책 폼
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

		FrPolicy bestPolicy = frPolicyService.get(ForumConstants.POLICY_BEST, portal.getPortalId());

		model.addAttribute("bestPolicy", bestPolicy);

		return "collpack/forum/bestPolicyForm";
	}
	
	/**
	 * 토론분석 정책 폼
	 * @param model
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/analysisPolicyForm.do", method = RequestMethod.GET)
	public String getFormAnalysisPolicy(Model model, @ModelAttribute("isAdmin") boolean isAdmin) {

		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}

		Portal portal = (Portal) getRequestAttribute("ikep.portal");

		FrPolicy analysisPolicy = frPolicyService.get(ForumConstants.POLICY_ANALYSIS, portal.getPortalId());

		model.addAttribute("analysisPolicy", analysisPolicy);

		return "collpack/forum/analysisPolicyForm";
	}
	

	/**
	 * 정책 등록
	 * @param frPolicy	정책 객체
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/createPolicy.do", method = RequestMethod.POST)
	public String onSubmit(FrPolicy frPolicy
						, @ModelAttribute("isAdmin") boolean isAdmin) {

		// 권한 체크
		if (!isAdmin) {
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		frPolicy.setPortalId(portal.getPortalId());
		
		User user = (User) getRequestAttribute("ikep.user");
		frPolicy.setRegisterId(user.getUserId());
		frPolicy.setRegisterName(user.getUserName());

		if (StringUtil.isEmpty(frPolicy.getPolicyId())) {
			frPolicyService.create(frPolicy);
		} else {
			frPolicyService.update(frPolicy);
		}
		
		String page = "";
		if(frPolicy.getPolicyType().equals(ForumConstants.POLICY_ACTIVITY)){
			page = "activityPolicyForm.do";
			
		} else if(frPolicy.getPolicyType().equals(ForumConstants.POLICY_ANALYSIS)){
			page = "analysisPolicyForm.do";
			
		} else if(frPolicy.getPolicyType().equals(ForumConstants.POLICY_BEST)){
			page = "bestPolicyForm.do";
			
		} 

		return "redirect:" + page;
	}
	
	
	/**
	 * 인기 토론 정책 등록
	 * @param disPolicyId			토론정책 ID
	 * @param policyTypeDis			정책 타입
	 * @param maxCountDis			최대 개수
	 * @param itemWeightDis			토론글 가중치
	 * @param linereplyWeightDis	토론의견 가중치
	 * @param agreementWeightDis	찬성 가중치
	 * @param favoriteWeightDis		즐겨찾기 가중치
	 * @param recommendWeightDis	추천 가중치
	 * @param itemPolicyId			토론글정책 ID
	 * @param policyTypeItem		토론글 정책 타입
	 * @param maxCountItem			토론글 최대 개수
	 * @param linereplyWeightItem	토론글의 댓글 개수
	 * @param agreementWeightItem	토론글의 찬성 가중치
	 * @param favoriteWeightItem	토론글에 즐겨찾기 가중치
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/createPopularPolicy.do", method = RequestMethod.POST)
	public String onSubmitPopular(@RequestParam("disPolicyId") String disPolicyId
						, @RequestParam("policyTypeDis") String policyTypeDis
						, @RequestParam("maxCountDis") int maxCountDis
						, @RequestParam("itemWeightDis") int itemWeightDis
						, @RequestParam("linereplyWeightDis") int linereplyWeightDis
						, @RequestParam("agreementWeightDis") int agreementWeightDis
						, @RequestParam("favoriteWeightDis") int favoriteWeightDis
						, @RequestParam("recommendWeightDis") int recommendWeightDis
						, @RequestParam("itemPolicyId") String itemPolicyId
						, @RequestParam("policyTypeItem") String policyTypeItem
						, @RequestParam("maxCountItem") int maxCountItem
						, @RequestParam("linereplyWeightItem") int linereplyWeightItem
						, @RequestParam("agreementWeightItem") int agreementWeightItem
						, @RequestParam("favoriteWeightItem") int favoriteWeightItem
						, @ModelAttribute("isAdmin") boolean isAdmin) {
		
		//권한 체크
		if(!isAdmin){
			throw new IKEP4AjaxException("Access Error", null);
		}
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		User user = (User) getRequestAttribute("ikep.user");
		

		
		//질문 정책
		FrPolicy dcFrPolicy = new FrPolicy();
		dcFrPolicy.setPolicyId(disPolicyId);
		dcFrPolicy.setPolicyType(policyTypeDis);
		dcFrPolicy.setMaxCount(maxCountDis);
		dcFrPolicy.setItemWeight(itemWeightDis);
		dcFrPolicy.setLinereplyWeight(linereplyWeightDis);
		dcFrPolicy.setAgreementWeight(agreementWeightDis);
		dcFrPolicy.setFavoriteWeight(favoriteWeightDis);
		dcFrPolicy.setRecommendWeight(recommendWeightDis);
		dcFrPolicy.setPortalId(portal.getPortalId());
		dcFrPolicy.setRegisterId(user.getUserId());
		dcFrPolicy.setRegisterName(user.getUserName());
		
		//답변 정책
		FrPolicy itPolicy = new FrPolicy();
		itPolicy.setPolicyId(itemPolicyId);
		itPolicy.setPolicyType(policyTypeItem);
		itPolicy.setMaxCount(maxCountItem);
		itPolicy.setLinereplyWeight(linereplyWeightItem);
		itPolicy.setAgreementWeight(agreementWeightItem);
		itPolicy.setFavoriteWeight(favoriteWeightItem);
		itPolicy.setPortalId(portal.getPortalId());
		itPolicy.setRegisterId(user.getUserId());
		itPolicy.setRegisterName(user.getUserName());
		
		if(StringUtil.isEmpty(disPolicyId)){
			frPolicyService.create(dcFrPolicy);
		} else {
			frPolicyService.update(dcFrPolicy);
		}
		
		if(StringUtil.isEmpty(itemPolicyId)){
			frPolicyService.create(itPolicy);
		} else {
			frPolicyService.update(itPolicy);
		}
		
		return "redirect:popularPolicyForm.do";
	}
	
	
}
