/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.servicepack.survey.service.SurveyService;


/**
 * 설문 자동 ending 배치
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SurveyEndingScheduled.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SurveyEndingScheduled extends QuartzJobBean {

	protected final Log log = LogFactory.getLog(this.getClass());

	private SurveyService surveyService;

	/**
	 * 실행 메소드
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.surveyService = (SurveyService) appContext.getBean("surveyServiceImpl");

			surveyService.exipireDateUpdate();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new IKEP4ApplicationException("", e);
		}

	}
}
