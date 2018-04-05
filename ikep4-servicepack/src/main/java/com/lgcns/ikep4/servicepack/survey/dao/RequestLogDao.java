/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.survey.model.RequestLog;
import com.lgcns.ikep4.servicepack.survey.model.RequestLogKey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;

/**
 * 설문조사 메일 배치 dao
 *
 * @author ihko11
 * @version $Id: RequestLogDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface RequestLogDao extends GenericDao<RequestLog,RequestLogKey> {
	/**
	 * 설문로그리스트
	 * @param searchCondition
	 * @return
	 */
	List<RequestLog> listBySearchCondition(SurveySearchCondition searchCondition);
	
	/**
	 * 설문로그 일반 리스트
	 * @param surveyId
	 * @return
	 */
	List<RequestLog> selectListBySurveyId(String surveyId);
	
	/**
	 * 설문 로그 카운트
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(SurveySearchCondition searchCondition); 
	/**
	 * 로그 최대값
	 * @param surveyId
	 * @return
	 */
	public Integer maxSeq(String surveyId);
	/**
	 * 설문로그 전체삭제
	 * @param surveyId
	 */
	public void removeAllBySurveyId(String surveyId);
}
