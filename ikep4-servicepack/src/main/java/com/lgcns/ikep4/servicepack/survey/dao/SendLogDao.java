/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.survey.model.SendLog;
import com.lgcns.ikep4.servicepack.survey.model.SendLogKey;

/**
 * 설문조사 메일 리스트 dao
 *
 * @author ihko11
 * @version $Id: SendLogDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface SendLogDao extends GenericDao<SendLog,SendLogKey> {
	/**
	 * 메일 리스트 가져오기
	 * @param surveyId
	 * @return
	 */
	public List<SendLog> getSendMailListAllBySurveyId(String surveyId);
	/**
	 * 모든 사용자 리스트 가져오기
	 * @return
	 */
	public List<SendLog> getSendMailListAllByUser(SendLog sendLog);
	/**
	 * 미응답자 메일 리스트 가져오기
	 * @param surveyId
	 * @return
	 */
	public List<SendLog> getSendNotMail(String surveyId);
	/**
	 * 메일 발송리스트 총 카운트
	 * @param surveyId
	 * @return
	 */
	public Integer getTotalSendLog(String surveyId);
	/**
	 * 메일 발송리스트 삭제
	 * @param id
	 */
	public void removeAllBySurveyId(String id) ;
	/**
	 * 미응답자 전사일경우 리스트 가져오기
	 * @param surveyId
	 * @return
	 */
	public List<SendLog> getSendNotMailListAllByUser(SendLog sendLog);
}
