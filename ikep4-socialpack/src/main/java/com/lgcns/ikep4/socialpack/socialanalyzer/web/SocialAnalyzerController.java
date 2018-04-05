/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationPolicy;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationType;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationTypePk;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Sociality;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityPolicy;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityType;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityTypePk;
import com.lgcns.ikep4.socialpack.socialanalyzer.search.SocialAnalyzerSearchCondition;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationPolicyService;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationService;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationTypeService;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SnBatchLogService;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityPolicyService;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityService;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityTypeService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * Model Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialAnalyzerController.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Controller
@RequestMapping(value = "/socialpack/socialanalyzer")
@SessionAttributes("socialanalyzer")
public class SocialAnalyzerController extends BaseController {
	@Autowired
    ACLService aclService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserService userService; 
	
	@Autowired
	private SnBatchLogService snBatchLogService;
	@Autowired
	private SocialityPolicyService socialityPolicyService;
	@Autowired
	private SocialityTypeService socialityTypeService;
	@Autowired
	private RelationPolicyService relationPolicyService;
	@Autowired
	private RelationTypeService relationTypeService;
	@Autowired
	private SocialityService socialityService;
	@Autowired
	private RelationService relationService;

	/**
	 * 관리자여부 - 메뉴용
	 * @param userId
	 * @return
	 */
	private String isAdminYn(String userId) {
		String adminYn = "N";
		if(aclService.isSystemAdmin("SocialAnalyzer", userId)) {
			adminYn = "Y";
		}
		return adminYn;
	}	
	
	/**
	 * SocialRanking 설정
	 * @return
	 */
	@RequestMapping(value = "/updateSocialRankingView")
	public ModelAndView updateSocialRankingView() { 
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/updateSocialRankingView");

		//관리자여부
		mav.addObject("adminYn", isAdminYn(user.getUserId()));
		
		//조회
		SocialityTypePk socialityTypePk= new SocialityTypePk();
		socialityTypePk.setPortalId(user.getPortalId());
		socialityTypePk.setTypeCode("I");
		SocialityType socialityTypeI = socialityTypeService.read(socialityTypePk);
		if(socialityTypeI == null) {
			socialityTypeI = new SocialityType();
			mav.addObject("socialityTypeI", socialityTypeI);
			mav.addObject("socialityTypeF", socialityTypeI);
		} else {
			mav.addObject("socialityTypeI", socialityTypeI);

			socialityTypePk.setTypeCode("F");
			mav.addObject("socialityTypeF", socialityTypeService.read(socialityTypePk));
		}
		
		SocialityPolicy socialityPolicy = socialityPolicyService.read(user.getPortalId());
		if(socialityPolicy == null) {
			socialityPolicy = new SocialityPolicy();
		}
		mav.addObject("socialityPolicy", socialityPolicy);
		
		return mav;
	}

	/**
	 * SocialRanking 저장
	 * @param socialityPolicy
	 * @param typeWeightI
	 * @param validMonthI
	 * @param typeWeightF
	 * @param validMonthF
	 * @return
	 */
	@RequestMapping(value = "/saveSocialRanking")
	public ModelAndView saveSocialRanking(SocialityPolicy socialityPolicy, 
			@RequestParam("typeWeightI") Integer typeWeightI, @RequestParam("validMonthI") Integer validMonthI,
			@RequestParam("typeWeightF") Integer typeWeightF, @RequestParam("validMonthF") Integer validMonthF) {
		User user = (User) getRequestAttribute("ikep.user");
		
		SocialityType socialityType = new SocialityType();
		socialityType.setPortalId(user.getPortalId());
		socialityType.setTypeCode("I");
		ModelBeanUtil.bindRegisterInfo(socialityType, user.getUserId(), user.getUserName());
		if(socialityTypeService.exists((SocialityTypePk)socialityType)) {
			socialityType.setTypeWeight(typeWeightI);
			socialityType.setValidMonth(validMonthI);
			socialityTypeService.update(socialityType);
			
			socialityType.setTypeCode("F");
			socialityType.setTypeWeight(typeWeightF);
			socialityType.setValidMonth(validMonthF);
			socialityTypeService.update(socialityType);
		} else {
			socialityType.setTypeName("Influence Index");
			socialityType.setTypeWeight(typeWeightI);
			socialityType.setValidMonth(validMonthI);
			socialityTypeService.create(socialityType);
			
			socialityType.setTypeCode("F");
			socialityType.setTypeName("Fellowshiop Index");
			socialityType.setTypeWeight(typeWeightF);
			socialityType.setValidMonth(validMonthF);
			socialityTypeService.create(socialityType);
		}
		
		socialityPolicy.setPortalId(user.getPortalId());
		ModelBeanUtil.bindRegisterInfo(socialityPolicy, user.getUserId(), user.getUserName());
		if(socialityPolicyService.exists(user.getPortalId())) {
			socialityPolicyService.update(socialityPolicy);
		} else {
			socialityPolicyService.create(socialityPolicy);
		}
		
		return new ModelAndView("redirect:/socialpack/socialanalyzer/updateSocialRankingView.do");
	}	

	/**
	 * SocialDistance 설정
	 * @return
	 */
	@RequestMapping(value = "/updateSocialDistanceView")
	public ModelAndView updateSocialDistanceView() { 
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/updateSocialDistanceView");

		//관리자여부
		mav.addObject("adminYn", isAdminYn(user.getUserId()));
		
		//조회     
		RelationTypePk relationTypePk= new RelationTypePk();
		relationTypePk.setPortalId(user.getPortalId());
		relationTypePk.setTypeCode("D");
		RelationType relationTypeD = relationTypeService.read(relationTypePk);
		if(relationTypeD == null) {
			relationTypeD = new RelationType();
			mav.addObject("relationTypeD", relationTypeD);
			mav.addObject("relationTypeC", relationTypeD);
			mav.addObject("relationTypeF", relationTypeD);
			mav.addObject("relationTypeE", relationTypeD);
		} else {
			mav.addObject("relationTypeD", relationTypeD);

			relationTypePk.setTypeCode("C");
			mav.addObject("relationTypeC", relationTypeService.read(relationTypePk));

			relationTypePk.setTypeCode("F");
			mav.addObject("relationTypeF", relationTypeService.read(relationTypePk));

			relationTypePk.setTypeCode("E");
			mav.addObject("relationTypeE", relationTypeService.read(relationTypePk));
		}

		RelationPolicy relationPolicy = relationPolicyService.read(user.getPortalId());
		if(relationPolicy == null) {
			relationPolicy = new RelationPolicy();
		}
		mav.addObject("relationPolicy", relationPolicy);
		
		return mav;
	}
	
	/**
	 * SocialDistance 저장
	 * @param relationPolicy
	 * @param typeWeightD
	 * @param typeWeightE
	 * @param typeWeightC
	 * @param validMonthC
	 * @param typeWeightF
	 * @param validMonthF
	 * @return
	 */
	@RequestMapping(value = "/saveSocialDistance")
	public ModelAndView saveSocialDistance(RelationPolicy relationPolicy, 
			@RequestParam("typeWeightD") Integer typeWeightD, @RequestParam("typeWeightE") Integer typeWeightE,
			@RequestParam("typeWeightC") Integer typeWeightC, @RequestParam("validMonthC") Integer validMonthC,
			@RequestParam("typeWeightF") Integer typeWeightF, @RequestParam("validMonthF") Integer validMonthF) { 
		User user = (User) getRequestAttribute("ikep.user");

		RelationType relationType = new RelationType();
		relationType.setPortalId(user.getPortalId());
		relationType.setTypeCode("D");
		ModelBeanUtil.bindRegisterInfo(relationType, user.getUserId(), user.getUserName());
		if(relationTypeService.exists((RelationTypePk)relationType)) {
			relationType.setTypeWeight(typeWeightD);
			relationType.setValidMonth(0);
			relationTypeService.update(relationType);
			
			relationType.setTypeCode("C");
			relationType.setTypeWeight(typeWeightC);
			relationType.setValidMonth(validMonthC);
			relationTypeService.update(relationType);
			
			relationType.setTypeCode("F");
			relationType.setTypeWeight(typeWeightF);
			relationType.setValidMonth(validMonthF);
			relationTypeService.update(relationType);
			
			relationType.setTypeCode("E");
			relationType.setTypeWeight(typeWeightE);
			relationType.setValidMonth(0);
			relationTypeService.update(relationType);
		} else {
			relationType.setTypeName("Direct Network");
			relationType.setTypeWeight(typeWeightD);
			relationType.setValidMonth(0);
			relationTypeService.create(relationType);
	
			relationType.setTypeCode("C");
			relationType.setTypeName("Communication Network");
			relationType.setTypeWeight(typeWeightC);
			relationType.setValidMonth(validMonthC);
			relationTypeService.create(relationType);
			
			relationType.setTypeCode("F");
			relationType.setTypeName("Fellowship Network");
			relationType.setTypeWeight(typeWeightF);
			relationType.setValidMonth(validMonthF);
			relationTypeService.create(relationType);
			
			relationType.setTypeCode("E");
			relationType.setTypeName("Expertise Network");
			relationType.setTypeWeight(typeWeightE);
			relationType.setValidMonth(0);
			relationTypeService.create(relationType);
		}
		
		relationPolicy.setPortalId(user.getPortalId());
		ModelBeanUtil.bindRegisterInfo(relationPolicy, user.getUserId(), user.getUserName());
		if(relationPolicyService.exists(user.getPortalId())) {
			relationPolicyService.update(relationPolicy);
		} else {
			relationPolicyService.create(relationPolicy);
		}
		
		return new ModelAndView("redirect:/socialpack/socialanalyzer/updateSocialDistanceView.do");
	}
	
	/**
	 * 배치 로그 조회
	 * @param searchY
	 * @param searchM
	 * @return
	 */
	@RequestMapping(value = "/listBatchLogView")
	public ModelAndView listBatchLogView(@RequestParam(value="searchY", required=false) String searchY, @RequestParam(value="searchM", required=false) String searchM) { 
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listBatchLogView");
		
		//관리자여부
		mav.addObject("adminYn", isAdminYn(user.getUserId()));
		
		//조회
		if(StringUtil.isEmpty(searchY)) {
			searchY = DateUtil.getToday("yyyy");
			searchM = DateUtil.getToday("MM");
		} 
		mav.addObject("batchLogList", snBatchLogService.listBatchLog(searchY + searchM));
		mav.addObject("searchY", searchY);
		mav.addObject("searchM", searchM);
		
		return mav;
	}

	/**
	 * SocialRanking 조회
	 * @param socialAnalyzerSearchCondition
	 * @return
	 */
	@RequestMapping(value = "/listSocialRankingView")
	public ModelAndView listSocialRankingView(SocialAnalyzerSearchCondition socialAnalyzerSearchCondition) { 
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listSocialRankingView");

		//관리자여부
		mav.addObject("adminYn", isAdminYn(user.getUserId()));
		
		//조회
		if(socialAnalyzerSearchCondition == null) {
			socialAnalyzerSearchCondition = new SocialAnalyzerSearchCondition();
		}
		SearchResult<Sociality> searchResult = socialityService.listSociality(socialAnalyzerSearchCondition);
		mav.addObject("searchResult", searchResult);
		mav.addObject("searchCondition", searchResult.getSearchCondition());
		return mav;
	}

	/**
	 * Excel 저장
	 * @param socialAnalyzerSearchCondition
	 * @param response
	 */
	@RequestMapping(value = "/saveExcel")
	public void saveExcel(SocialAnalyzerSearchCondition socialAnalyzerSearchCondition, HttpServletResponse response) { 
		User user = (User) getRequestAttribute("ikep.user");
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
		SearchResult<Sociality> searchResult = socialityService.listSociality(socialAnalyzerSearchCondition);
		List<Sociality> socialityList = searchResult.getEntity();
		
		List<Object> dataList = new ArrayList<Object>();
		for(Sociality item : socialityList) {
			dataList.add(item);
		}
		
		LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
		titleMap.put("rNum", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column1", null, new Locale(user.getLocaleCode())));
		if(user.getLocaleCode().equals(portal.getDefaultLocaleCode())) {
			titleMap.put("userName", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column2.name", null, new Locale(user.getLocaleCode())));
			titleMap.put("jobTitleName", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column2.jobTitleName", null, new Locale(user.getLocaleCode())));
			titleMap.put("teamName", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column3", null, new Locale(user.getLocaleCode())));		
		} else {
			titleMap.put("userEnglishName", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column2.name", null, new Locale(user.getLocaleCode())));
			titleMap.put("jobTitleEnglishName", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column2.jobTitleName", null, new Locale(user.getLocaleCode())));
			titleMap.put("teamEnglishName", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column3", null, new Locale(user.getLocaleCode())));		
		}
		titleMap.put("topPercent", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column4", null, new Locale(user.getLocaleCode())));
		titleMap.put("indexSociality", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column5", null, new Locale(user.getLocaleCode())));
		titleMap.put("indexInfluence", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column6", null, new Locale(user.getLocaleCode())));
		titleMap.put("indexFellowship", messageSource.getMessage("ui.socialpack.socialanalyzer.listSocialRankingView.table.column7", null, new Locale(user.getLocaleCode())));
 
		String fileName = "socialRanking.xlsx";
		
		ExcelUtil.saveExcel(titleMap, dataList, fileName, response);
	}
	
	/**
	 * SocialGraph 조회
	 * @param tabIndex
	 * @param searchId
	 * @param searchText
	 * @return
	 */
	@RequestMapping(value = "/listSocialGraphView")
	public ModelAndView listSocialGraphView(@RequestParam(value="tabIndex", required=false) Integer tabIndex, @RequestParam(value="searchId", required=false) String searchId, @RequestParam(value="searchText", required=false) String searchText) { 
		User user = (User) getRequestAttribute("ikep.user");
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listSocialGraphView");

		//관리자여부
		mav.addObject("adminYn", isAdminYn(user.getUserId()));
		
		//조회
		if(StringUtil.isEmpty(searchId)) {
			mav.addObject("userInfo", user);
			mav.addObject("searchId", user.getUserId());
			mav.addObject("searchText", "");
			mav.addObject("tabIndex", 0);
			tabIndex = 0;
			searchId = user.getUserId();
		} else {
			mav.addObject("userInfo", userService.read(searchId));
			mav.addObject("searchId", searchId);
			mav.addObject("searchText", searchText);
			mav.addObject("tabIndex", tabIndex);
		}

		//관계 사용자 조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", searchId);
		if(tabIndex == 0) {
			map.put("type", "O");
			mav.addObject("socialityList", relationService.listPerson(map));
		} else if(tabIndex == 1) {
			map.put("type", "D");
			mav.addObject("socialityList", relationService.listPerson(map));
		} else if(tabIndex == 2) {
			map.put("type", "C");
			mav.addObject("socialityList", relationService.listPerson(map));
		} else if(tabIndex == 3) {
			map.put("type", "F");
			mav.addObject("socialityList", relationService.listPerson(map));
		} else if(tabIndex == 4) {
			map.put("type", "E");
			mav.addObject("socialityList", relationService.listPerson(map));
		}

		return mav;
	}
	
	/**
	 * 소셜 네트웍 조회-Overall
	 * @param userID
	 * @return
	 */
	@RequestMapping(value = "/tab0XML")
	public ModelAndView tab0XML(@RequestParam(value="userID") String userID) { 
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listPersonXML");

		//조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("type", "O");
		mav.addObject("socialNetworkList", relationService.listPerson(map));
		mav.addObject("userInfo", userService.read(userID));
		
		return mav;
	}
	
	/**
	 * 소셜 네트웍 조회-Direct
	 * @param userID
	 * @return
	 */
	@RequestMapping(value = "/tab1XML")
	public ModelAndView tab1XML(@RequestParam(value="userID") String userID) { 
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listPersonXML");

		//조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("type", "D");
		mav.addObject("socialNetworkList", relationService.listPerson(map));
		mav.addObject("userInfo", userService.read(userID));
		
		return mav;
	}

	/**
	 * 소셜 네트웍 조회-Communication
	 * @param userID
	 * @return
	 */
	@RequestMapping(value = "/tab2XML")
	public ModelAndView tab2XML(@RequestParam(value="userID") String userID) { 
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listPersonXML");

		//조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("type", "C");
		mav.addObject("socialNetworkList", relationService.listPerson(map));
		mav.addObject("userInfo", userService.read(userID));
		
		return mav;
	}

	/**
	 * 소셜 네트웍 조회-Fellowship
	 * @param userID
	 * @return
	 */
	@RequestMapping(value = "/tab3XML")
	public ModelAndView tab3XML(@RequestParam(value="userID") String userID) { 
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listPersonXML");

		//조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("type", "F");
		mav.addObject("socialNetworkList", relationService.listPerson(map));
		mav.addObject("userInfo", userService.read(userID));
		
		return mav;
	}
	
	/**
	 * 소셜 네트웍 조회-Expertise
	 * @param userID
	 * @return
	 */
	@RequestMapping(value = "/tab4XML")
	public ModelAndView tab4XML(@RequestParam(value="userID") String userID) { 
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listPersonXML");

		//조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("type", "E");
		mav.addObject("socialNetworkList", relationService.listPerson(map));
		mav.addObject("userInfo", userService.read(userID));
		
		return mav;
	}
	
	/**
	 * 소셜 네트웍 조회-그룹별
	 * @param userID
	 * @return
	 */
	@RequestMapping(value = "/tab5XML")
	public ModelAndView tab5XML(@RequestParam(value="userID") String userID) { 
		ModelAndView mav = new ModelAndView("/socialpack/socialanalyzer/listGroupXML");

		//조회
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", userID);
		map.put("type", "D");
		mav.addObject("directList", relationService.listGroup(map));

		map.put("type", "C");
		mav.addObject("communicationList", relationService.listGroup(map));

		map.put("type", "F");
		mav.addObject("fellowshipList", relationService.listGroup(map));

		map.put("type", "E");
		mav.addObject("expertiseList", relationService.listGroup(map));

		mav.addObject("userInfo", userService.read(userID));
		
		return mav;
	}
}