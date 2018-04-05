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
import com.lgcns.ikep4.servicepack.survey.model.Response;
import com.lgcns.ikep4.servicepack.survey.model.ResponseKey;

/**
 * 설문조사 사용자 응답dao
 *
 * @author ihko11
 * @version $Id: ResponseDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface ResponseDao extends GenericDao<Response,ResponseKey> {
	/**
	 * 모든 사용자 답변 삭제
	 * @param surveyId
	 */
	public void removeAllBySurveyId(String surveyId);
	/**
	 * 일별결과 가져오기
	 * @param surveyId
	 * @return
	 */
	public List<Response> getDayResultList(String surveyId);
	/**
	 * 결과 존재여부
	 * @param response
	 * @return
	 */
	public boolean already(Response response);
	/**
	 * 응답 count
	 * @param surveyId
	 * @return
	 */
	public Integer responseAllCount(String surveyId);
	
}
