/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.servicepack.survey.model.SendLog;
import com.lgcns.ikep4.servicepack.survey.model.SendLogKey;

/**
 * 설문조사 dao
 *
 * @author ihko11
 * @version $Id: SendLogService.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Transactional
public interface SendLogService extends GenericService<SendLog,SendLogKey> {
	/**
	 * 설문 개별 메일 리스트
	 * @param surveyId
	 * @return
	 */
	public List<SendLog> getSendMailListAllBySurveyId(String surveyId);
	/**
	 * 설문 전사 메일 리스트
	 * @return
	 */
	public List<SendLog> getSendMailListAllByUser(SendLog sendLog);
	/**
	 * 미발송 개별 리스트
	 * @param surveyId
	 * @return
	 */
	public List<SendLog> getSendNotMail(String surveyId);
	/**
	 * 개별 리스트 총 카운트
	 * @param surveyId
	 * @return
	 */
	public Integer getTotalSendLog(String surveyId);
	/**
	 * 미발송 전사 리스트
	 * @param surveyId
	 * @return
	 */
	public List<SendLog> getSendNotMailListAllByUser(SendLog sendLog);
}
