/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.servicepack.survey.model.Question;

/**
 * 설문조사 문항dao
 *
 * @author ihko11
 * @version $Id: QuestionService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface QuestionService extends GenericService<Question,String> {
	/**
	 * 설문 문항 리스트 
	 * @param questionGroupId
	 * @return
	 */
	public List<Question> getAllByQuestionGroupId(String questionGroupId);
	/**
	 * 설문 문항별 결과 리스트
	 * @param surveyId
	 * @return
	 */
	public List<Question> getReportAllBySurveyId(String surveyId);
	
	
	public List<Question> getJoinSelectTypeAnswer(String surveyId);
	
	public void updateJoinAnswer(Question question);
	
	public void removeJoinQuestion(String surveyId);
	
	public void updateAnswerTitle(Map<String, String> params);
}
