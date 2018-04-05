/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.servicepack.survey.dao.SurveyDao;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;

/**
 * 설문조사 impl
 *
 * @author ihko11
 * @version $Id: SurveyDaoImpl.java 16737 2011-10-03 08:26:04Z giljae $
 */
@Repository
public class SurveyDaoImpl extends GenericDaoSqlmap<Survey,String> implements SurveyDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svSurvey.";
	
	/**
	 * 설문 읽기
	 */
	public Survey get(String id) {
		return (Survey) sqlSelectForObject(NAMESPACE+"get", id);
	}
	
	public String getComment(String id) {
		return (String) sqlSelectForObject(NAMESPACE+"getComment", id);
	}

	/**
	 * 설문 존재여부
	 */
	public boolean exists(String id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}

	/**
	 * 설문 생성
	 */
	public String create(Survey survey) {
		sqlInsert(NAMESPACE+"create", survey);
		
		return survey.getSurveyId();
	}

	/**
	 * 설문 수정
	 */
	public void update(Survey survey) {
		sqlUpdate(NAMESPACE+"update", survey);
	}

	/**
	 * 설문 삭제
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 설문 승인
	 */
	public void approve(Survey survey) {
		sqlDelete(NAMESPACE+"approve", survey);
	}
	
	/**
	 * 설문 expiredate auto schedule ending
	 */
	public void exipireDateUpdate() {
		sqlDelete(NAMESPACE+"exipireDateUpdate");
	}
	
	/**
	 * 설문 반려
	 */
	public void reject(Survey survey) {
		sqlDelete(NAMESPACE+"reject", survey);
	}
	
	/**
	 * 설문 작성 리스트
	 */
	public List<Survey> listBySearchCondition(SurveySearchCondition surveySearchCondition) { 
		return (List<Survey>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", surveySearchCondition);
	}

	/**
	 * 설문 작성 카운트
	 */
	public Integer countBySearchCondition(SurveySearchCondition surveySearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", surveySearchCondition);
	} 
	
	/**
	 * 설문 승인 리스트
	 */
	public List<Survey> approveListBySearchCondition(SurveySearchCondition surveySearchCondition) { 
		return (List<Survey>)this.sqlSelectForList(NAMESPACE + "approveListBySearchCondition", surveySearchCondition);
	}

	/**
	 * 설문 승인 카운트
	 */
	public Integer approveCountBySearchCondition(SurveySearchCondition surveySearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "approveCountBySearchCondition", surveySearchCondition);
	} 
	
	/**
	 * 설문 진행 리스트
	 */
	public List<Survey> ingListBySearchCondition(SurveySearchCondition surveySearchCondition) { 
		return (List<Survey>)this.sqlSelectForList(NAMESPACE + "ingListBySearchCondition", surveySearchCondition);
	}

	/**
	 * 설문 진행 카운트
	 */
	public Integer ingCountBySearchCondition(SurveySearchCondition surveySearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "ingCountBySearchCondition", surveySearchCondition);
	} 
	
	/**
	 * 설문 종료 리스트
	 */
	public List<Survey> endListBySearchCondition(SurveySearchCondition surveySearchCondition) { 
		return (List<Survey>)this.sqlSelectForList(NAMESPACE + "endListBySearchCondition", surveySearchCondition);
	}

	/**
	 * 설문 종료 카운트
	 */
	public Integer endCountBySearchCondition(SurveySearchCondition surveySearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "endCountBySearchCondition", surveySearchCondition);
	}
	
	/**
	 * 설문 그룹 대상 가져오기
	 */
	public List getListTargetGroup(String targetId) {
		return (List) this.sqlSelectForList(NAMESPACE + "listTargetGroup", targetId);
	}	
	
	/**
	 * 설문 대상 정보 가져오기
	 */
	public List getListTarget(String targetId) {
		return (List) this.sqlSelectForList(NAMESPACE + "listTarget", targetId);
	}

    public void updateEndDate( HashMap<String, String> data ) {
       this.sqlUpdate( NAMESPACE + "updateEndDate", data );
        
    }	
}
