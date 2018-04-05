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
import com.lgcns.ikep4.servicepack.survey.model.Response;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetail;
import com.lgcns.ikep4.servicepack.survey.model.ResponseKey;

/**
 * 설문조사 dao
 *
 * @author ihko11
 * @version $Id: ResponseService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface ResponseService extends GenericService<Response,ResponseKey> {
	/**
	 * 설문 결과 일별 리스트
	 * @param surveyId
	 * @return
	 */
	public List<Response> getDayResultList(String surveyId);
	/**
	 * 설문 결과 존재여부
	 * @param response
	 * @return
	 */
	public boolean already(Response response);
	/**
	 * 설문 결과 상세 리스트
	 * @param answerId
	 * @return
	 */
	public List<ResponseDetail> getAllByAnswerId(String answerId);
	/**
	 * 설문 결과상세 리스트
	 * @param surveyId
	 * @return
	 */
	public List<ResponseDetail> getReportDetailAllBySurveyId(String surveyId);
	/**
	 * 설문 결과 카운트
	 * @param surveyId
	 * @return
	 */
	public Integer responseAllCount(String surveyId);
}
