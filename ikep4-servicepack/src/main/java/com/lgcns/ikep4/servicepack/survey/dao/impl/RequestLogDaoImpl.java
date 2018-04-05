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
import com.lgcns.ikep4.servicepack.survey.dao.RequestLogDao;
import com.lgcns.ikep4.servicepack.survey.model.RequestLog;
import com.lgcns.ikep4.servicepack.survey.model.RequestLogKey;
import com.lgcns.ikep4.servicepack.survey.search.SurveySearchCondition;

/**
 * 설문조사 메일 발송 배치로그
 *
 * @author ihko11
 * @version $Id: RequestLogDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class RequestLogDaoImpl extends GenericDaoSqlmap<RequestLog,RequestLogKey> implements RequestLogDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svRequestLog.";

	/**
	 * 읽기
	 */
	public RequestLog get(RequestLogKey id) {
		return (RequestLog) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 존재여부
	 */
	public boolean exists(RequestLogKey id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}

	/**
	 * 메일로그생성
	 */
	public RequestLogKey create(RequestLog object) {
		sqlInsert(NAMESPACE+"create", object);
		return new RequestLogKey(object.getSendSeq(),object.getSurveyId());
	}

	/**
	 * 메일로그수정
	 */
	public void update(RequestLog object) {
		sqlUpdate(NAMESPACE+"update", object);
	}

	/**
	 * 메일로그삭제
	 */
	public void remove(RequestLogKey id) {
		sqlDelete(NAMESPACE+"remove", id);
	}

	/**
	 * 설문전체삭제
	 */
	public void removeAllBySurveyId(String id) {
		sqlDelete(NAMESPACE+"removeAllBySurveyId", id);
	}
	
	
	
	/**
	 * 메일 로그 최대값
	 */
	public Integer maxSeq(String surveyId) {
		return (Integer)sqlSelectForObject(NAMESPACE+"maxSeq", surveyId);
	}

	/**
	 * 설문로그 리스트
	 */
	public List<RequestLog> listBySearchCondition(SurveySearchCondition searchCondition) { 
		List<RequestLog> surveyItemList = (List<RequestLog>)this.sqlSelectForList(NAMESPACE + "listBySearchCondition", searchCondition);
		
		return surveyItemList;
	}

	/**
	 * 설문 로그 리스트 가져오기
	 */
	public List<RequestLog> selectListBySurveyId(String surveyId){
		List<RequestLog> surveyItemList = (List<RequestLog>)this.sqlSelectForList(NAMESPACE + "selectListBySurveyId", surveyId);
		
		return surveyItemList;
	}
	
	/**
	 * 설문 로그 카운트
	 */
	public Integer countBySearchCondition(SurveySearchCondition searchCondition) {
		
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
		
		return count;
	} 
}
