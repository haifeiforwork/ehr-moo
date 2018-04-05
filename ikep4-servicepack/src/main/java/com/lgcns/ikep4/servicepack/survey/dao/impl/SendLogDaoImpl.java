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
import com.lgcns.ikep4.servicepack.survey.dao.SendLogDao;
import com.lgcns.ikep4.servicepack.survey.model.SendLog;
import com.lgcns.ikep4.servicepack.survey.model.SendLogKey;

/**
 * 설문조사 메일 로그impl
 *
 * @author ihko11
 * @version $Id: SendLogDaoImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Repository
public class SendLogDaoImpl extends GenericDaoSqlmap<SendLog,SendLogKey> implements SendLogDao {
	
	private static final String NAMESPACE = "com.lgcns.ikep4.servicepack.survey.svSendLog.";

	/**
	 * 메일 로그 읽기
	 */
	public SendLog get(SendLogKey id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 메일로그 존재여부
	 */
	public boolean exists(SendLogKey id) {
		Integer count = (Integer)sqlSelectForObject(NAMESPACE+"exists", id);
		return count > 0;
	}

	/**
	 * 메일로그 생성
	 */
	public SendLogKey create(SendLog object) {
		sqlInsert(NAMESPACE+"create", object);
		return new SendLogKey(object.getSurveyId(),object.getReceiverId());
	}

	/**
	 * 메일 로그 수정
	 */
	public void update(SendLog object) {
		sqlUpdate(NAMESPACE+"update", object);
	}

	/**
	 * 메일로그 삭제
	 */
	public void remove(SendLogKey id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 설문별 설문 대상자 메일 리스트
	 */
	public List<SendLog> getSendMailListAllBySurveyId(String surveyId) {
		return  sqlSelectForList(NAMESPACE+"getSendMailListAllBySurveyId", surveyId);	
	}
	
	/**
	 * 설문별 설문 미참여 대상자 메일 리스트
	 */
	public List<SendLog> getSendNotMail(String surveyId) {
		return  sqlSelectForList(NAMESPACE+"getSendNotMail", surveyId);	
	}
	
	/**
	 * 설문별 메일 총 카운트
	 */
	public Integer getTotalSendLog(String surveyId){
		return (Integer)sqlSelectForObject(NAMESPACE+"getTotalSendLog", surveyId);
	}
	
	/**
	 * 설문별 메일로그 전부 삭제
	 */
	public void removeAllBySurveyId(String id) {
		sqlDelete(NAMESPACE+"removeAllBySurveyId", id);
	}
	
	/**
	 * 설문별 메일 대상자 사용자 리스트
	 */
	public List<SendLog> getSendMailListAllByUser(SendLog sendLog) {
		return  sqlSelectForList(NAMESPACE+"getSendMailListAllByUser", sendLog);	
	}
	
	/**
	 * 미참가자 전사일경우
	 */
	public List<SendLog> getSendNotMailListAllByUser(SendLog sendLog) {
		return  sqlSelectForList(NAMESPACE+"getSendNotMailListAllByUser", sendLog);	
	}
}
