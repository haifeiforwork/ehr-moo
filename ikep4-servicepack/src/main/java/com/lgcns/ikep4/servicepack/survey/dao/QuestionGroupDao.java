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
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;

/**
 * 설문조사 그룹dao
 *
 * @author ihko11
 * @version $Id: QuestionGroupDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface QuestionGroupDao extends GenericDao<QuestionGroup,String> {
	/**
	 * 그룹삭제
	 * @param surveyId
	 */
	public void removeAllBySurveyId(String surveyId);
	/**
	 * 그룹 최대값
	 * @param surveyId
	 * @return
	 */
	public Integer maxQuestionGroupSeq(String surveyId);
	/**
	 * 모드그룹 리스트
	 * @param surveyId
	 * @return
	 */
	public List<QuestionGroup> getAllBySurveyId(String surveyId);
}
