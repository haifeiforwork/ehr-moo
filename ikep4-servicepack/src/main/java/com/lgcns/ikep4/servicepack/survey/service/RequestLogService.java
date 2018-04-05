/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.survey.model.RequestLog;
import com.lgcns.ikep4.servicepack.survey.model.RequestLogKey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;

/**
 * 설문조사 dao
 *
 * @author ihko11
 * @version $Id: RequestLogService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface RequestLogService extends GenericService<RequestLog,RequestLogKey> {
	/**
	 * 설문 배치로그
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<RequestLog> listBySearchCondition(SurveySearchCondition searchCondition);
	
	/**
	 * 설문 배치 시퀀스
	 * @param surveyId
	 * @return
	 */
	public Integer maxSeq(String surveyId);
	
	/**
	 * 설문 배치 일반 로그
	 * @param surveyId
	 * @return
	 */
	public List<RequestLog> selectListBySurveyId(String surveyId);
}
