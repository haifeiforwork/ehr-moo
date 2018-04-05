/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.survey.dao.QuestionDao;
import com.lgcns.ikep4.servicepack.survey.model.Question;

/**
 * 설문조사 문항 impl
 *
 * @author ihko11
 * @version $Id: QuestionDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class QuestionDaoImpl extends GenericDaoSqlmap<Question,String> implements QuestionDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svQuestion.";
	/**
	 * 설문 문항 읽기
	 */
	public Question get(String id) {
		return (Question) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 설문 문항 존재 여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 설문 문항 최대값
	 */
	public Integer maxQuestionSeq(String questionGroupId) {
		return (Integer)sqlSelectForObject(NAMESPACE+"maxQuestionSeq", questionGroupId);
	}

	/**
	 * 설문 문항 생성
	 */
	public String create(Question question) {
		sqlInsert(NAMESPACE+"create", question);
		
		return question.getQuestionId();
	}

	/**
	 * 설문 문항 수정
	 */
	public void update(Question Question) {
		sqlUpdate(NAMESPACE+"update", Question);
	}

	/**
	 * 설문 문항 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 설문 그룹별 설문문항 삭제
	 */
	public void removeAllByGroupId(String questionGroupId) {
		sqlDelete(NAMESPACE+"removeAllByGroupId", questionGroupId);
	}
	
	/**
	 * 설문별 설문 문항 삭제
	 */
	public void removeAllBySurveyId(String surveyId) {
		sqlDelete(NAMESPACE+"removeAllBySurveyId", surveyId);
	}
	
	/**
	 * 설문 그룹별 설문 문항 리스트
	 */
	public List<Question> getAllByQuestionGroupId(String questionGroupId){
		return sqlSelectForList(NAMESPACE+"getAllByQuestionGroupId", questionGroupId);
	}
	
	/**
	 * 설문별 설문 문항 리스트
	 */
	public List<Question> getSeqAllByQuestionGroupId(Question question){
		return sqlSelectForList(NAMESPACE+"getSeqAllByQuestionGroupId", question);
	}
	
	/**
	 * excel report시 타이틀 출력 
	 */
	public List<Question> getReportAllBySurveyId(String surveyId){
		return sqlSelectForList(NAMESPACE+"getReportAllBySurveyId", surveyId);
	}
	
	
}
