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
import com.lgcns.ikep4.servicepack.survey.dao.ResponseDetailDao;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetail;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetailKey;

/**
 * 설문조사 참여 상세impl
 *
 * @author ihko11
 * @version $Id: ResponseDetailDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class ResponseDetailDaoImpl extends GenericDaoSqlmap<ResponseDetail,ResponseDetailKey> implements ResponseDetailDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svResponseDetail.";
	
	/**
	 * 참여여부 상세읽기
	 */
	public ResponseDetail get(ResponseDetailKey id) {
		return (ResponseDetail) sqlSelectForObject(NAMESPACE+"get", id);
	}

	/**
	 * 참여 여부 상세 존재
	 */
	public boolean exists(ResponseDetailKey id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}
	/**
	 * 참여 여부 상세 생성
	 */
	public ResponseDetailKey create(ResponseDetail responseDetail) {
		sqlInsert(NAMESPACE+"create", responseDetail);
		
		return new ResponseDetailKey(responseDetail.getAnswerId(), responseDetail.getQuestionId(), responseDetail.getResponseId());
	}

	/**
	 * 참여 여부 상세 수정
	 */
	public void update(ResponseDetail responseDetail) {
		sqlUpdate(NAMESPACE+"update", responseDetail);
	}

	/**
	 * 참여 여부 상세 삭제
	 */
	public void remove(ResponseDetailKey id) {
		sqlDelete(NAMESPACE+"remove", id);
	}
	
	/**
	 * 설문 참여 max seq
	 */
	public Integer maxResponseSeq(String responseId) {
		return (Integer)sqlSelectForObject(NAMESPACE+"maxResponseSeq", responseId);
	}
	
	/**
	 * 설문 참여자별 설문 참여 모두 삭제
	 */
	public void removeAllByResponseId(String id) {
		sqlDelete(NAMESPACE+"removeAllByResponseId", id);
	}
	
	/**
	 * 설문별 설문 참여 모두 삭제
	 */
	public void removeAllBySurveyId(String surveyId) {
		sqlDelete(NAMESPACE+"removeAllBySurveyId", surveyId);
	}
	
	/**
	 * 설문 답변 문항별 참여삭제
	 */
	public List<ResponseDetail> getAllByAnswerId(String answerId){
		return sqlSelectForList(NAMESPACE+"getAllByAnswerId", answerId);
	}
	
	/**
	 * 설문 답변 exceldownload
	 */
	public List<ResponseDetail> getReportDetailAllBySurveyId(String surveyId){
		return sqlSelectForList(NAMESPACE+"getReportDetailAllBySurveyId", surveyId);
	}
	
}
