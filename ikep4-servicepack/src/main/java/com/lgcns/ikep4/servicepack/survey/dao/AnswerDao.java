/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.survey.model.Answer;
import com.lgcns.ikep4.servicepack.survey.model.Question;

/**
 * 설문조사 문항 답변dao
 *
 * @author ihko11
 * @version $Id: AnswerDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface AnswerDao extends GenericDao<Answer,String> {
	/**
	 * 모든 답변 삭제
	 * @param surveyId
	 */
	public void removeAllBySurveyId(String surveyId);
	
	/**
	 * 모든 문항삭제
	 * @param questionId
	 */
	public void removeAllByQuestionId(String questionId);
	/**
	 * 모든그룹삭제
	 * @param questionGroupId
	 */
	public void removeAllByGroupId(String questionGroupId);
	/**
	 * 최대 값 가져오기
	 * @param questionId
	 * @return
	 */
	public Integer maxAnswerSeq(String questionId);
	/**
	 * 모든 문항 리스트
	 * @param questionId
	 * @return
	 */
	public List<Answer> getAllByQuestionId(String questionId);
	
	public List<Answer> getJoinSelectTypeAnswer(String surveyId);
	
	public void updateJoinQuestion(Answer Answer);
	
	public void removeJoinQuestion(String surveyId);
	
	public void updateAnswerTitle(Map<String, String> params);
}
