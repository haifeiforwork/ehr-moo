/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.survey.dao.AnswerDao;
import com.lgcns.ikep4.servicepack.survey.model.Answer;

/**
 * 설문조사 설문 질문impl
 *
 * @author ihko11
 * @version $Id: AnswerDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class AnswerDaoImpl extends GenericDaoSqlmap<Answer,String> implements AnswerDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svAnswer.";
	/**
	 * 설문 답변 읽기
	 */
	public Answer get(String id) {
		return (Answer) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 설문 답변 존재여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 설문 답변 생성
	 */
	public String create(Answer question) {
		sqlInsert(NAMESPACE+"create", question);
		
		return question.getAnswerId();
	}

	/**
	 * 설문 답변 수정
	 */
	public void update(Answer Answer) {
		sqlUpdate(NAMESPACE+"update", Answer);
	}
	public void updateJoinQuestion(Answer Answer) {
		sqlUpdate(NAMESPACE+"updateJoinQuestion", Answer);
	}
	/**
	 * 설문 답변 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 설문 문항별 설문 질문 삭제
	 */
	public void removeAllByQuestionId(String questionId) {
		sqlDelete(NAMESPACE+"removeAllByQuestionId", questionId);
	}
	
	/**
	 * 설문 그룹별 설문 질문 삭제
	 */
	public void removeAllByGroupId(String questionGroupId) {
		sqlDelete(NAMESPACE+"removeAllByGroupId", questionGroupId);
	}
	
	/**
	 * 설문별 설문 질문 삭제
	 */
	public void removeAllBySurveyId(String surveyId) {
		sqlDelete(NAMESPACE+"removeAllBySurveyId", surveyId);
	}
	
	/**
	 * 설문 질문 max seq
	 */
	public Integer maxAnswerSeq(String questionId) {
		return (Integer)sqlSelectForObject(NAMESPACE+"maxAnswerSeq", questionId);
	}
	
	/**
	 * 설문 문항별 설문 질문 리스트
	 */
	public List<Answer> getAllByQuestionId(String questionId){
		return sqlSelectForList(NAMESPACE+"getAllByQuestionId", questionId);
	}
	public List<Answer> getJoinSelectTypeAnswer(String surveyId){
		return sqlSelectForList(NAMESPACE+"getJoinSelectTypeAnswer", surveyId);
	}
	
	public void removeJoinQuestion(String surveyId) {
		sqlDelete(NAMESPACE+"removeJoinQuestion", surveyId);
	}
	public void updateAnswerTitle(Map<String, String> params) {
		sqlUpdate(NAMESPACE + "updateAnswerTitle", params);
	}
}
