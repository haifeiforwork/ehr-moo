/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.survey.dao.RequestLogDao;
import com.lgcns.ikep4.servicepack.survey.model.RequestLog;
import com.lgcns.ikep4.servicepack.survey.model.RequestLogKey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;
import com.lgcns.ikep4.servicepack.survey.service.RequestLogService;

/**
 * 설문조사 메일 배치dao
 *
 * @author ihko11
 * @version $Id: RequestLogServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class RequestLogServiceImpl extends GenericServiceImpl<RequestLog,RequestLogKey> implements RequestLogService {
	protected final Log log = LogFactory.getLog(getClass());
	
	private RequestLogDao requestLogDao;
	
	@Autowired
	public RequestLogServiceImpl(RequestLogDao dao) {
		super(dao);
		this.requestLogDao = dao;
	}
	
	
	/**
	 * 설문조사 메일 배치로그리스트
	 */
	public SearchResult<RequestLog> listBySearchCondition(SurveySearchCondition searchCondition){
	
		SearchResult<RequestLog> searchResult = null;
	
		Integer count = this.requestLogDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		
		if( searchCondition.isEmptyRecord() ){
			searchResult = new SearchResult<RequestLog>(searchCondition); 
		}
		else
		{
			List<RequestLog> surveyItemList = requestLogDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<RequestLog>(surveyItemList, searchCondition); 
		}
	
		return searchResult;
	}
	
	/**
	 * 설문 배치 로그 일반
	 */
	public List<RequestLog> selectListBySurveyId(String surveyId){
		return this.requestLogDao.selectListBySurveyId(surveyId);
	}
	
	/**
	 * 설문 배치 시퀀스
	 */
	public Integer maxSeq(String surveyId) {
		return requestLogDao.maxSeq( surveyId );
	}

}
