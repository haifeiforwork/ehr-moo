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
import com.lgcns.ikep4.servicepack.survey.model.Question;

/**
 * 설문조사 문항dao
 *
 * @author ihko11
 * @version $Id: QuestionDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface QuestionDao extends GenericDao<Question,String> {
	/**
	 * 모든 문항 삭제
	 * @param surveyId
	 */
	public void removeAllBySurveyId(String surveyId);
	/**
	 * 모든 그룹삭제
	 * @param questionGroupId
	 */
	public void removeAllByGroupId(String questionGroupId);
	/**
	 * 문항 최대값
	 * @param questionGroupId
	 * @return
	 */
	public Integer maxQuestionSeq(String questionGroupId);
	/**
	 * 그룹의 모든 문항 
	 * @param questionGroupId
	 * @return
	 */
	public List<Question> getAllByQuestionGroupId(String questionGroupId);
	/**
	 * 문항리스트 순서가져오기
	 * @param question
	 * @return
	 */
	public List<Question> getSeqAllByQuestionGroupId(Question question);
	/**
	 * 모든 문항의 결과 가져오기
	 * @param surveyId
	 * @return
	 */
	public List<Question> getReportAllBySurveyId(String surveyId);
}
