/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.searchpreprocessor.model.BatchLog;
import com.lgcns.ikep4.support.searchpreprocessor.model.History;
import com.lgcns.ikep4.support.searchpreprocessor.model.Report;
import com.lgcns.ikep4.support.searchpreprocessor.model.Result;
import com.lgcns.ikep4.support.searchpreprocessor.model.SpBaseControllerData;
import com.lgcns.ikep4.support.searchpreprocessor.search.SpSearchCondition;
import com.lgcns.ikep4.support.searchpreprocessor.service.BatchLogService;
import com.lgcns.ikep4.support.searchpreprocessor.service.HistoryService;
import com.lgcns.ikep4.support.searchpreprocessor.service.SpSnRelationService;
import com.lgcns.ikep4.support.searchpreprocessor.util.CalUtils;
import com.lgcns.ikep4.support.searchpreprocessor.util.Criteria;
import com.lgcns.ikep4.support.searchpreprocessor.util.DateUtil;
import com.lgcns.ikep4.support.searchpreprocessor.util.HistoryCons;
import com.lgcns.ikep4.support.searchpreprocessor.util.MagicNumUtils;
import com.lgcns.ikep4.support.searchpreprocessor.util.PageUtil;
import com.lgcns.ikep4.support.searchpreprocessor.util.SearchUtil;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 검색프로세스
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SearchPreprocessorController.java 16316 2011-08-22 00:26:53Z giljae $
 */

@Controller
@RequestMapping(value = "/support/searchpreprocessor")
public class SearchPreprocessorController extends SpBaseController {
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	SpSnRelationService spSnRelationService;
	
	@Autowired
	 private TagService tagService;
	
	@Autowired
	BatchLogService batchLogService;

	@Autowired
    private TimeZoneSupportService timeZoneSupportService;
	
	/**
	 * 검색프로세스 메인프로세스
	 * @param searchOption
	 * @param startIndex
	 * @param next
	 * @param process
	 * @return
	 */
	@RequestMapping(value = "/searchhistory/{process}")
	public ModelAndView searchHistory(@RequestParam(value = "searchOption", required = false) String searchOption,
			@RequestParam(value = "startIndex", required = false)  String startIndex,
			@RequestParam(value = "next", required = false)  String next,
			@PathVariable("process")  String process
			) {
		ModelAndView mav = new ModelAndView("/support/searchpreprocessor/searchhistory/history");
		User user  = getUser();
		Result result = new Result();
		
		String localProcess = process;
		String localSearchOption = searchOption;
		
		if( !StringUtils.hasText(localProcess) ){
			localProcess =  HistoryCons.SEARCH_PREPROCESSOR_HISTORY;
		}
		
		if( localProcess.equals(HistoryCons.SEARCH_PREPROCESSOR_COLLEAGUE_LIST) ){
			getRelationList(startIndex, next, localProcess, mav, user, result);
		}else if( localProcess.equals(HistoryCons.SEARCH_PREPROCESSOR_POPULAR_TAG) || 
				localProcess.equals( HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND_TAG)){
			getResultTag(localSearchOption, startIndex, next, localProcess, mav, user, result);
		}else{	
			result = getResults(localSearchOption, startIndex, next, localProcess, mav, user);
		}
		
		result.setSearchOption(localSearchOption);
		mav.addObject("result", result );
		return mav;
	}

	/**
	 * 검색프로세스 ajax call
	 * @param searchOption
	 * @param startIndex
	 * @param next
	 * @param process
	 * @return
	 */
	@RequestMapping(value = "/searchhistory/ajax/{process}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Result searchHistoryAjax(@RequestParam(value = "searchOption", required = false) String searchOption,
			@RequestParam(value = "startIndex", required = false)  String startIndex,
			@RequestParam(value = "next", required = false)  String next,
			@PathVariable("process")  String process
			) {
		
		ModelAndView mav = new ModelAndView("/support/searchpreprocessor/searchhistory/history");
		User user  = getUser();
		Result result = new Result();
		String localProcess = process;
		String localSearchOption = searchOption;
		
		if( !StringUtils.hasText(process) ){
			localProcess =  HistoryCons.SEARCH_PREPROCESSOR_HISTORY;
		}
		
		if( localProcess.equals(HistoryCons.SEARCH_PREPROCESSOR_COLLEAGUE_LIST) ){
			getRelationList(startIndex, next, localProcess, mav, user, result);
		}else if( localProcess.equals(HistoryCons.SEARCH_PREPROCESSOR_POPULAR_TAG) || 
			localProcess.equals( HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND_TAG)){
			getResultTag(localSearchOption, startIndex, next, localProcess, mav, user, result);
		}else{	
			result = getResults(localSearchOption, startIndex, next, localProcess, mav, user);
			
			List<History> historyList = result.getHistoryList();
			//ajax일경우 먼저 상요자 타임으로 변경하여 보낸다.
			for (History history : historyList) {
				if( history.getRegistDate() != null ){
					history.setRegistDate( timeZoneSupportService.convertTimeZone( history.getRegistDate() ) );
				}	
			}
		}
		result.setSearchOption(localSearchOption);
	    return result;
		    
	}
	
	/**
	 * 연관검색어 관련 초기페이지
	 * @return
	 */
	@RequestMapping(value = "/searchhistory/related/init")
	public ModelAndView init(){
		return new ModelAndView("/support/searchpreprocessor/searchhistory/related");
	}
	
	/**
	 * 레포트 관련 초기페이지
	 * @return
	 */
	@RequestMapping(value = "/report/reportHistory")
	public ModelAndView reportHistory(){
		return new ModelAndView("/support/searchpreprocessor/report/reportHistory");
	}
	
	/**
	 * 일일 레포트 ajax call
	 * @param startDt
	 * @param endDt
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/report/reportHistory/ajaxDay")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody  List<Report> reportHistoryDayAjax(Report param, BindingResult bindingResult, SessionStatus status,ModelMap model) {
		
		User user = getUser();
		SearchUtil searchUtil = new SearchUtil();
		
		//검색시간을 서버시간대로 바꿘다.
		param.setStartDt( timeZoneSupportService.convertServerTimeZone( param.getStartDt() ) );
		param.setEndDt( timeZoneSupportService.convertServerTimeZone( param.getEndDt() ) );
		
		Criteria criteria= searchUtil.createCriteria();
		criteria.addCriterionForJDBCDate("REGIST_DATE BETWEEN", param.getStartDt(), DateUtil.getNextday( param.getEndDt(),1), "registDate");
		criteria.addCriterion("portal_id=", user.getPortalId(), "portalId");
		
		List<Report> result =  historyService.reportDayHistory(searchUtil);
		
		List<Report> resultList = CalUtils.makeCalendar(param.getStartDt(), param.getEndDt());
		
		for (Report report : resultList) {
			for (Report dbInfo : result) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				if( df.format(report.getResultDt()).equals( df.format(dbInfo.getResultDt())))
				{
					report.setTotalCount( dbInfo.getTotalCount() );
					break;
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 *  월별레포트 ajax call
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/report/reportHistory/ajaxMonth")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody  List<Report> reportHistoryMonthAjax(HttpServletRequest request) {
		User user= getUser();
		SearchUtil searchUtil = new SearchUtil();
		Criteria criteria= searchUtil.createCriteria();
		criteria.addCriterionForJDBCDate("REGIST_DATE BETWEEN", DateUtils.addMonths(new Date(),-1*MagicNumUtils.MONTH_3) , new Date(), "registDate");
		criteria.addCriterion("portal_id=", user.getPortalId(), "portalId");
		
		List<Report> result =  historyService.reportMonthHistory(searchUtil);
		
		List<Report> resultList = CalUtils.makeMonthCalendar();
		
		for (Report report : resultList) {
			for (Report dbInfo : result) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
				
				if( df.format(report.getResultDt()).equals( df.format(dbInfo.getResultDt())))
				{
					report.setTotalCount( dbInfo.getTotalCount() );
					break;
				}
			}
		}
		
		return resultList;
	}
	
	
	/**
	 * 동료검색어 관련 프로세스
	 * @param searchKeyword
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchhistory/related/ajax")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody  List<History> search(@RequestParam(value="searchKeyword",required=false) String searchKeyword,HttpServletRequest request) {
		User user =getUser();
		SearchUtil searchUtil = new SearchUtil();
		Criteria criteria = searchUtil.createCriteria();
		
		criteria.addCriterion("h.SEARCH_KEYWORD like", searchKeyword, "searchKeyword");
		criteria.addCriterion("h.portal_id=", user.getPortalId(), "portalId");
		
		return  historyService.getRelatedList(searchUtil);
	}

	/**
	 * 검색어 기본 리스트
	 * @param searchOption
	 * @param startIndex
	 * @param next
	 * @param process
	 * @param mav
	 * @param user
	 * @param result
	 * @return
	 */
	private Result getResults(String searchOption, String startIndex, String next, String process, ModelAndView mav,
			User user) {
		try{
			mav.setViewName("/support/searchpreprocessor/searchhistory/"+process);
			
			String localProcess = process;
			String localSearchOption = searchOption;
			
			PageUtil pageUtil = new PageUtil(startIndex,next);
			SearchUtil searchUtil = new SearchUtil();
			searchUtil.setStartIndex( pageUtil.getStartIndex() );
			searchUtil.setEndIndex( pageUtil.getNext() );
			searchUtil.setUser(user);
			
			log.debug("-----------------searchUtil------------------" + searchUtil);
			
			
				
			if( !StringUtils.hasText(localSearchOption) ){
				if( localProcess.equals(HistoryCons.SEARCH_PREPROCESSOR_HISTORY) ){
					localSearchOption = HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH_PRE_3;
				}else{	
					localSearchOption = HistoryCons.SEARCH_PREPROCESSOR_OPTION_DAY;
				}
			}else if( localSearchOption.equals("real") ){
				localProcess=HistoryCons.SEARCH_PREPROCESSOR_POPULAR_REAL;
			}	
			searchUtil.setCategory(localProcess);
			
			Criteria criteria = searchUtil.createCriteria();
			
			addCriteria(localProcess, user, localSearchOption, searchUtil, criteria);
			
			
			return historyService.getList(searchUtil);
			
				
	    } catch(Exception ex) {
	        throw new IKEP4AjaxException("code", ex);
	    }
	    
	}

	/**
	 * 동적 검색옵션생성
	 * @param process
	 * @param user
	 * @param localSearchOption
	 * @param searchUtil
	 * @param criteria
	 */
	private void addCriteria(String process, User user, String localSearchOption, SearchUtil searchUtil,
			Criteria criteria) {
		
		if( !process.equals(HistoryCons.SEARCH_PREPROCESSOR_POPULAR_REAL) )
		{	
			if( localSearchOption.equals( HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK ) ){
				criteria.addCriterionForJDBCDate("h.REGIST_DATE >=", DateUtil.prevWeek(1), "registDate");
			}else if( localSearchOption.equals( HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH ) ){
				criteria.addCriterionForJDBCDate("h.REGIST_DATE >=", DateUtil.prevMonth(1), "registDate");
			}else if( localSearchOption.equals( HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH_PRE_3 ) ){
				criteria.addCriterionForJDBCDate("h.REGIST_DATE >=", DateUtil.prevMonth(MagicNumUtils.MONTH_3), "registDate");
			}else{
				criteria.addCriterionForJDBCDate("h.REGIST_DATE >=", DateUtil.getToday(), "registDate");
			}
		}
		
		criteria.addCriterion("h.portal_id=", user.getPortalId(), "portalId");
		
		if( process.equals(HistoryCons.SEARCH_PREPROCESSOR_HISTORY)){
			criteria.addCriterion("h.REGISTER_ID =", user.getUserId() , "registerId");
			searchUtil.setOrderByClause("h.REGIST_DATE desc");
		}
		else
		{
			getRelationUser(process, user, searchUtil, criteria);
			
			searchUtil.setOrderByClause("FREQUENCY_COUNT desc");
		}
	}

	/**
	 * 동료검색어 동료정보 동적생성
	 * @param process
	 * @param user
	 * @param searchUtil
	 * @param criteria
	 */
	private void getRelationUser(String process, User user, SearchUtil searchUtil, Criteria criteria) {
		if( process.equals(HistoryCons.SEARCH_PREPROCESSOR_COLLEAGUE) || process.equals(HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND) )
		{
			SearchUtil searchUtil2 = new SearchUtil();
			searchUtil2.setUser(user);
			searchUtil2.setEndIndex(SpBaseControllerData.RELATION_LIMIT_COUNT);
			List<User> relationUserList = spSnRelationService.getSnRelationList(searchUtil2);
			
			List<String> relationTemp = new ArrayList<String>();
			//내꺼는 뺀다.
			//relationTemp.add(user.getUserId());
			
			for (User userInfo : relationUserList) {
				relationTemp.add(userInfo.getUserId());
			}
			
			searchUtil.setRelationUserList(relationTemp);
			criteria.addCriterion("h.REGISTER_ID in", relationTemp, "registerId");
		}
	}
	
	/**
	 * 검색어 테그리스트
	 * @param searchOption
	 * @param startIndex
	 * @param next
	 * @param process
	 * @param mav
	 * @param user
	 * @param result
	 * @return
	 */
	private void getResultTag(String searchOption, String startIndex, String next, String process, ModelAndView mav,
			User user, Result result){
		
		String localSearchOption = searchOption;
		
		PageUtil pageUtil = new PageUtil(startIndex,next);
		mav.setViewName("/support/searchpreprocessor/searchhistory/"+process);
		
		if( !StringUtils.hasText(localSearchOption) ){ 
			localSearchOption = HistoryCons.SEARCH_PREPROCESSOR_OPTION_DAY;
		}
		
		Tag tag = new Tag();
	    tag.setTagOrder(TagConstants.ORDER_TYPE_FREQUENCY); //원하는 정렬순서로 바꿀수 있음. TagConstants에 정의되어 있음.
	    
	    tag.setPortalId( user.getPortalId() );
	    
	    if(  process.equals( HistoryCons.SEARCH_PREPROCESSOR_RECOMMEND_TAG))
	    {
	    	SearchUtil searchUtil = new SearchUtil();
			searchUtil.setUser(user);
			searchUtil.setEndIndex(SpBaseControllerData.RELATION_LIMIT_COUNT);
	    	   //태그 가져오기
			List<User> relationUserList = spSnRelationService.getSnRelationList(searchUtil);
			
			List<String> relationTemp = new ArrayList<String>();
			relationTemp.add(user.getUserId());
			
			for (User userInfo : relationUserList) {
				relationTemp.add(userInfo.getUserId());
			}
	    	tag.setUserIdList(relationTemp);    //사용자 리스트 (List 형식 이여야 함.)
	    }
	    	

	    if( localSearchOption.equals( HistoryCons.SEARCH_PREPROCESSOR_OPTION_WEEK ) ){
	    	tag.setLimitDate("7");     //허용 날짜
	    }else if( localSearchOption.equals( HistoryCons.SEARCH_PREPROCESSOR_OPTION_MONTH ) ){
			tag.setLimitDate("30");     //허용 날짜
		}else{
			tag.setLimitDate("1");     //허용 날짜
		}

	    
	     int count = tagService.getCount(tag);
	     tag.setEndRowIndex( pageUtil.getStartIndex()+pageUtil.getNext() );
	     tag.setStartRowIndex( pageUtil.getStartIndex() );
	    
	    result.setEndIndex( pageUtil.getStartIndex()+pageUtil.getNext() );
	    result.setTotalCount( count );
	    
	    if( count > 0 )
	    {	
	    	List<Tag> tagList = tagService.list(tag);
	    	result.setTagList(tagList);
	    }
	}
	
	/**
	 * 검색어 동료리스트
	 * @param startIndex
	 * @param next
	 * @param process
	 * @param mav
	 * @param user
	 * @param result
	 * @return
	 */
	private void getRelationList(String startIndex, String next, String process, ModelAndView mav,
			User user, Result result){
		mav.setViewName("/support/searchpreprocessor/searchhistory/"+process);

		PageUtil pageUtil = new PageUtil(startIndex,next);
		SearchUtil searchUtil = new SearchUtil();
		searchUtil.setStartIndex( pageUtil.getStartIndex() );
		searchUtil.setEndIndex( pageUtil.getNext() );
		searchUtil.setUser(user);
		
		int count = spSnRelationService.countSnRelationDetailList(searchUtil);
		
	    result.setEndIndex( pageUtil.getStartIndex()+pageUtil.getNext() );
	    result.setTotalCount( count );

	    if( count > 0 )
	    {
	    	List<User> userList = spSnRelationService.getSnRelationDetailList(searchUtil);
	    	result.setUserList(userList);
	    }
	}
	
	/**
	 * 배치 로그 히스토리
	 * @param searchCondition
	 * @param result
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchlog/history")
	public ModelAndView searchHistory(SpSearchCondition searchCondition, BindingResult result, SessionStatus status,ModelMap model) {
		ModelAndView mav = new ModelAndView("/support/searchpreprocessor/batchlog/history");
		User user  = getUser();
		
		if( !isAdmin() ) {
			mav.setViewName("redirect:/support/searchpreprocessor/searchhistory/history.do");
		}else{	
			if( StringUtils.hasText(searchCondition.getSearchColumn()) ){
				searchCondition.setSearchColumn("rankSchedule");
			}
			
			searchCondition.setPortalId(user.getPortalId());
			//받는쪽 테이블의 portalId구현
			
			SearchResult<BatchLog> searchResult = batchLogService.listBySearchCondition(searchCondition);
			
			mav.addObject("searchResult", searchResult);
			mav.addObject("searchCondition", searchResult.getSearchCondition());
		}
		
		return mav;
	}
	
	
}
