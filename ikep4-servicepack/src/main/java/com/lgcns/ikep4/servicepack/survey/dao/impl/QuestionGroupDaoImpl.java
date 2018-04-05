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
import com.lgcns.ikep4.servicepack.survey.dao.QuestionGroupDao;
import com.lgcns.ikep4.servicepack.survey.model.QuestionGroup;

/**
 * 설문조사 문항 그룹impl
 *
 * @author ihko11
 * @version $Id: QuestionGroupDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class QuestionGroupDaoImpl extends GenericDaoSqlmap<QuestionGroup,String> implements QuestionGroupDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svQuestionGroup.";
	
	/**
	 * 그룹 읽기
	 */
	public QuestionGroup get(String id) {
		return (QuestionGroup) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 그룹 존재 여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 설문 문항 그룹 max seq
	 */
	public Integer maxQuestionGroupSeq(String surveyId) {
		return (Integer)sqlSelectForObject(NAMESPACE+"maxQuestionGroupGroupSeq", surveyId);
	}
	
	/**
	 * 그룹 생성
	 */
	public String create(QuestionGroup question) {
		sqlInsert(NAMESPACE+"create", question);
		
		return question.getQuestionGroupId();
	}

	/**
	 * 그룹 수정
	 */
	public void update(QuestionGroup QuestionGroup) {
		sqlUpdate(NAMESPACE+"update", QuestionGroup);
	}

	/**
	 * 그룹 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 설문별 설문 문항그룹 삭제
	 */
	public void removeAllBySurveyId(String surveyId) {
		sqlDelete(NAMESPACE+"removeAllBySurveyId", surveyId);
	}
	
	/**
	 * 설문별 설문 문항그룹 리스트
	 */
	public List<QuestionGroup> getAllBySurveyId(String surveyId){
		return sqlSelectForList(NAMESPACE+"getAllBySurveyId", surveyId);
	}
}
