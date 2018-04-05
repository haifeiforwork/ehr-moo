/* 

 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.web;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.abusereporting.base.ArConstant;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseHistory;
import com.lgcns.ikep4.support.abusereporting.model.ArAbuseQueryReturn;
import com.lgcns.ikep4.support.abusereporting.model.ArKeyword;
import com.lgcns.ikep4.support.abusereporting.model.ArKeywordModule;
import com.lgcns.ikep4.support.abusereporting.model.ArModule;
import com.lgcns.ikep4.support.abusereporting.search.ArAbuseSearchCondition;
import com.lgcns.ikep4.support.abusereporting.service.ArAbuseHistoryService;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordModuleService;
import com.lgcns.ikep4.support.abusereporting.service.ArKeywordService;
import com.lgcns.ikep4.support.abusereporting.service.ArModuleService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 
 * 금지어 관리
 *
 * @author 최성우
 * @version $Id: ArKeywordController.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Controller
@RequestMapping(value = "/support/abusereporting")
public class ArKeywordController extends BaseController {

	@Autowired
	private ArKeywordService arKeywordService;

	@Autowired
	private ArKeywordModuleService arKeywordModuleService;

	@Autowired
	private ArModuleService arModuleService;

	@Autowired
	private ArAbuseHistoryService arAbuseHistoryService;

	@Autowired
	private ACLService aclService;

	/**
	 * Abuse Reporting 금지어관리에 대한 접근 권한 확인
	 * 
	 * @param user
	 * @return
	 */
	public boolean isSystemAdmin(User user) {
		boolean isSystemAdmin = aclService.isSystemAdmin("AbuseReport", user.getUserId());
		//isSystemAdmin = true;
		return isSystemAdmin;
	}
    
	/**
	 * 금지어를 등록한다.
	 *
	 * @param arKeyword ArKeyword 정보
	 * @param result 바인딩결과
	 * @param status 세션상태
	 * @return 결과메세지
	 */
	@RequestMapping("/createArKeyword.do")
	public @ResponseBody String onSubmit(@Valid ArKeyword arKeyword, BindingResult result, SessionStatus status) {

		if (log.isDebugEnabled()) {
			log.debug("arKeyword:"+arKeyword);
		}

		if(result.hasErrors()) {
			// 에러가 존재할 경우, IKEP4AjaxValidationException을 발생 시킨다.
			throw new IKEP4AjaxValidationException(result, messageSource); //BindingResult와 BaseController의 MessageSource를 parameter로 전달해야 합니다.
		}
		
		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		if (log.isDebugEnabled()) {
			log.debug("user.getUserId():"+user.getUserId());
		}

		//권한 체크
		if(!isSystemAdmin(user)){
			throw new IKEP4AjaxException(messageSource.getMessage("ui.support.abusereporting.message.notSystemAdmin", null, new Locale(user.getLocaleCode())), null);
		}
		
		arKeyword.setRegisterId		(user.getUserId());
		arKeyword.setRegisterName	(user.getUserName());
		arKeyword.setUpdaterId		(user.getUserId());
		arKeyword.setUpdaterName	(user.getUserName());
		arKeyword.setPortalId		(user.getPortalId());

		arKeywordService.create(arKeyword);
		
		// 세션 상태를 complete하여 이중 전송 방지
		status.setComplete();
		return "ok";
	}

	/**
	 * 금지어를 삭제한다.
	 *
	 * @param keywords 금지어
	 * @return 결과메세지
	 */
	@RequestMapping(value = "/delArKeyword.do")
	public @ResponseBody String delArKeyword(String keywords) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		//권한 체크
		if(!isSystemAdmin(user)){
			throw new IKEP4AjaxException(messageSource.getMessage("ui.support.abusereporting.message.notSystemAdmin", null, new Locale(user.getLocaleCode())), null);
		}
		
		ArKeyword arkeyword = new ArKeyword();
		arkeyword.setKeywords(keywords);
		arkeyword.setPortalId(user.getPortalId());
		
		arKeywordService.delete(arkeyword);
		
		return "ok";
	}

	/**
	 * 검색조건에 해당하는 금지어조회.
	 * 
	 * @param arAbuseSearchCondition moduleCode, searchWord 등이 담긴 ArAbuseSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getKeywordList.do")
	public ModelAndView getKeywordList(ArAbuseSearchCondition arAbuseSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("getKeywordList arAbuseSearchCondition:"+arAbuseSearchCondition);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		arAbuseSearchCondition.setPortalId(user.getPortalId());
		
		ModelAndView mav = new ModelAndView("support/abusereporting/keywordList");
		
		List<String> arKeywordlist = arKeywordService.list(arAbuseSearchCondition);
		
		mav.addObject("arKeywordlist"	, arKeywordlist);
		
		return mav;
	}

	/**
	 * 금지어 전체 관리화면을 리턴한다.
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getArKeywordManageMain.do")
	public ModelAndView getArKeywordManageMain() {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		ModelAndView mav = new ModelAndView("support/abusereporting/arKeywordManageMain");
		
		ArAbuseSearchCondition arAbuseSearchCondition = new ArAbuseSearchCondition();
		List<ArModule> arModulelist = arModuleService.list();

		mav.addObject("searchCondition"		, arAbuseSearchCondition);
		mav.addObject("arModulelist"		, arModulelist);
		mav.addObject("isSystemAdmin"		, isSystemAdmin(user));
		return mav;
	}

	/**
	 * 금지어 등록/수정/조회화면을 리턴한다.
	 * 
	 * @param formType 금지어 관리 타입(등록/수정/조회)
	 * @param keyword 금지어
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getArKeywordManageForm.do")
	public ModelAndView getArKeywordManageForm(String formType, String keyword) {

		if (log.isDebugEnabled()) {
			log.debug("getArKeywordManageForm formType:"+formType + ",keyword:"+keyword);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");

		ModelAndView mav = new ModelAndView("support/abusereporting/arKeywordManageForm");
		
		List<ArModule> linkedModuleList = null;
		
		// 수정(2), 조회(3) 모드 일 때는 연결된 모듈정보 조회.
		if((ArConstant.MANAGEFORM_TYPE_UPD.equals(formType) || ArConstant.MANAGEFORM_TYPE_VIW.equals(formType)) && null != keyword){

			ArKeywordModule arKeywordModule 	= new ArKeywordModule();
			arKeywordModule.setKeyword(keyword);
			arKeywordModule.setPortalId(user.getPortalId());
			
			linkedModuleList = arKeywordModuleService.listByKeyword(arKeywordModule);
		}
		
		List<ArModule> arModulelist = arModuleService.list();

		mav.addObject("keyword"				, keyword);
		mav.addObject("linkedModuleList"	, linkedModuleList);
		mav.addObject("arModulelist"		, arModulelist);
		mav.addObject("formType"			, formType);
		mav.addObject("isSystemAdmin"		, isSystemAdmin(user));
		
		return mav;
	}

	/**
	 * 모듈에 해당하는 금지어 리스트를 조회하여 컨텐츠와 비교하여 금지어를 걸러서 리턴한다.
	 * 
	 * @param arAbuseHistory moduleCode, itemId, contents 등이 담긴 ArAbuseHistory객체
	 * @return 금지어리스트 ('|'로 구분자 사용)
	 */
	@RequestMapping(value = "/checkProhibitWord.do")
	public @ResponseBody String checkProhibitWord(ArAbuseHistory arAbuseHistory) {

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		arAbuseHistory.setPortalId(user.getPortalId());
		
		return StringUtil.replace(arAbuseHistoryService.getCheckProhibitWordList(arAbuseHistory), "|", ",");
	}

	/**
	 * 검색조건에 해당하는 ArAbuse 리포트용 리스트 조회
	 * 
	 * @param moduleCode, startDate, endDate, keyword 를 담은 ArAbuseSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getAbuseReportList.do")
	public ModelAndView getAbuseReportList(ArAbuseSearchCondition arAbuseSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("getAbuseReportList arAbuseSearchCondition:"+arAbuseSearchCondition);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		arAbuseSearchCondition.setPortalId(user.getPortalId());

		// 검색시작일이 없으면 7일전을 기본으로 한다.
		if(null == arAbuseSearchCondition.getStartDate() || "".equals(arAbuseSearchCondition.getStartDate())){
			arAbuseSearchCondition.setStartDate(DateUtil.toDate(DateUtil.getPrevDate(DateUtil.getToday("yyyyMMdd"), ArConstant.DAYS_OF_WEEK, "yyyyMMdd")));
		}

		// 검색종료일이 없으면 오늘을 기본으로 한다.
		if(null == arAbuseSearchCondition.getEndDate() || "".equals(arAbuseSearchCondition.getEndDate())){
			arAbuseSearchCondition.setEndDate(DateUtil.toDate(DateUtil.getToday("yyyyMMdd")));
		}
		
		ModelAndView mav = new ModelAndView("support/abusereporting/abuseReportList");

		try {
			SearchResult<ArAbuseHistory> searchResult = arAbuseHistoryService.listBySearchconditionForReport(arAbuseSearchCondition);

			List<ArModule> arModulelist = arModuleService.list();
			
			BoardCode boardCode = new BoardCode();
			
			mav.addObject("searchResult"		, searchResult);
			mav.addObject("searchCondition"		, searchResult.getSearchCondition());
			mav.addObject("arModulelist"		, arModulelist);
			mav.addObject("boardCode"			, boardCode);
			mav.addObject("isSystemAdmin"		, isSystemAdmin(user));

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}

	/**
	 * 검색조건에 해당하는 ArAbuse 통계용 리스트 조회
	 * 
	 * @param moduleCode, startDate, endDate 를 담은 ArAbuseSearchCondition 객체
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getAbuseStatistics.do")
	public ModelAndView getAbuseStatistics(ArAbuseSearchCondition arAbuseSearchCondition) {

		if (log.isDebugEnabled()) {
			log.debug("getAbuseStatistics arAbuseSearchCondition:"+arAbuseSearchCondition);
		}

		// 세션 객체 가지고 오기
		User user = (User) getRequestAttribute("ikep.user");
		
		arAbuseSearchCondition.setPortalId(user.getPortalId());

		// 검색시작일이 없으면 1달전을 기본으로 한다.
		if(null == arAbuseSearchCondition.getStartDate() || "".equals(arAbuseSearchCondition.getStartDate())){
			arAbuseSearchCondition.setStartDate(DateUtil.toDate(DateUtil.getPrevMonthDate(DateUtil.getToday("yyyyMMdd"), 1, "yyyyMMdd")));
		}

		// 검색종료일이 없으면 오늘을 기본으로 한다.
		if(null == arAbuseSearchCondition.getEndDate() || "".equals(arAbuseSearchCondition.getEndDate())){
			arAbuseSearchCondition.setEndDate(DateUtil.toDate(DateUtil.getToday("yyyyMMdd")));
		}
		
		ModelAndView mav = new ModelAndView("support/abusereporting/abuseStatistics");

		try {
			List<ArAbuseQueryReturn> arAbuseQueryReturnList = arAbuseHistoryService.listBySearchconditionForStatistics(arAbuseSearchCondition);

			List<ArModule> arModulelist = arModuleService.list();
			
			mav.addObject("arAbuseQueryReturnList"	, arAbuseQueryReturnList);
			mav.addObject("searchCondition"			, arAbuseSearchCondition);
			mav.addObject("arModulelist"			, arModulelist);
			mav.addObject("isSystemAdmin"			, isSystemAdmin(user));

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new IKEP4AjaxException("", ex);
		}
		
		return mav;
	}

}
