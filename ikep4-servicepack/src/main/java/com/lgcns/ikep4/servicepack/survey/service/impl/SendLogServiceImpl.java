/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.servicepack.survey.dao.SendLogDao;
import com.lgcns.ikep4.servicepack.survey.model.SendLog;
import com.lgcns.ikep4.servicepack.survey.model.SendLogKey;
import com.lgcns.ikep4.servicepack.survey.service.SendLogService;

/**
 * 설문조사 dao
 *
 * @author ihko11
 * @version $Id: SendLogServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class SendLogServiceImpl extends GenericServiceImpl<SendLog,SendLogKey> implements SendLogService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SendLogDao sendLogDao;
	
	@Autowired
	public SendLogServiceImpl(SendLogDao dao) {
		super(dao);
		this.sendLogDao = dao;
	}
	
	/**
	 * 메일 로그 작성
	 */
	@Override
	public SendLogKey create(SendLog sendLog) {
		return sendLogDao.create(sendLog);
	}

	/**
	 * 보낸메일 리스트
	 */
	public List<SendLog> getSendMailListAllBySurveyId(String surveyId) {
		return  sendLogDao.getSendMailListAllBySurveyId(surveyId);
	}
	
	/**
	 * 보낸 메일 사용자 리스트
	 */
	public List<SendLog> getSendMailListAllByUser(SendLog sendLog) {
		return  sendLogDao.getSendMailListAllByUser(sendLog);
	}
	
	
	/**
	 * 미참여자 메일 리스트
	 */
	public List<SendLog> getSendNotMail(String surveyId) {
		return  sendLogDao.getSendNotMail(surveyId);
	}
	
	/**
	 * 미참가자 전사일경우
	 */
	public List<SendLog> getSendNotMailListAllByUser(SendLog sendLog) {
		return  sendLogDao.getSendNotMailListAllByUser(sendLog);
	}
	
	/**
	 * 참여자 총 카운트
	 */
	public Integer getTotalSendLog(String surveyId){
		return  sendLogDao.getTotalSendLog(surveyId);
	}

}
