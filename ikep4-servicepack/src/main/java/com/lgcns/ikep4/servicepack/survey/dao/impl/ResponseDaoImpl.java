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
import com.lgcns.ikep4.servicepack.survey.dao.ResponseDao;
import com.lgcns.ikep4.servicepack.survey.model.Response;
import com.lgcns.ikep4.servicepack.survey.model.ResponseKey;

/**
 * 설문조사 참여 impl
 *
 * @author ihko11
 * @version $Id: ResponseDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class ResponseDaoImpl extends GenericDaoSqlmap<Response,ResponseKey> implements ResponseDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svResponse.";
	
	/**
	 * 참여 읽기
	 */
	public Response get(ResponseKey id) {
		return (Response) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 참여 여부
	 */
	public boolean exists(ResponseKey id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	
	/**
	 * 참여 했나 안했나
	 */
	public boolean already(Response response) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"already", response);
		return count > 0;
	}

	/**
	 * 참여자 총카운트
	 */
	public Integer responseAllCount(String surveyId) {
		return (Integer)sqlSelectForObject(NAMESPACE+"responseAllCount", surveyId);
	}
	
	/**
	 * 참여여부 생성
	 */
	public ResponseKey create(Response response) {
		sqlInsert(NAMESPACE+"create", response);
		
		return new ResponseKey(response.getResponseId(),response.getResponseUserId());
	}

	/**
	 * 참여 여부 수정
	 */
	public void update(Response response) {
		sqlUpdate(NAMESPACE+"update", response);
	}

	/**
	 * 참여 여부 삭제
	 */
	public void remove(ResponseKey id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 참여 여부 삭제
	 */
	public void removeAllBySurveyId(String surveyId) {
		sqlDelete(NAMESPACE+"removeAllBySurveyId", surveyId);
	}

	/**
	 * 일일 분석 로그
	 */
	public List<Response> getDayResultList(String surveyId){
		return  sqlSelectForList(NAMESPACE+"getDayResultList", surveyId);	
	}
	
}
