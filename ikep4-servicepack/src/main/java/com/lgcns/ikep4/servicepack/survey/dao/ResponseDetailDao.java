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
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetail;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetailKey;

/**
 * 설문조사 사용자 응답 상세dao
 *
 * @author ihko11
 * @version $Id: ResponseDetailDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface ResponseDetailDao extends GenericDao<ResponseDetail,ResponseDetailKey> {
	/**
	 * 설문 응답 순서번호
	 * @param responseId
	 * @return
	 */
	public Integer maxResponseSeq(String responseId);
	/**
	 * 설문응답 삭제
	 * @param responseId
	 */
	public void removeAllByResponseId(String responseId);
	/**
	 * 설문관련 모든 응답 지우기
	 * @param surveyId
	 */
	public void removeAllBySurveyId(String surveyId);
	/**
	 * 모든 응답 답변에 대해 가져오기
	 * @param answerId
	 * @return
	 */
	public List<ResponseDetail> getAllByAnswerId(String answerId);
	/**
	 * 모든 상세 결과 가져오기
	 * @param surveyId
	 * @return
	 */
	public List<ResponseDetail> getReportDetailAllBySurveyId(String surveyId);
	
}
