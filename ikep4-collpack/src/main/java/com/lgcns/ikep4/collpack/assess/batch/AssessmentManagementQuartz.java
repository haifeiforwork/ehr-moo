/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.assess.service.AssessmentManagementUserPviService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * Assessment Management Batch Scheduler Knowledge Monitor 의 배치가 실행된후 돌려야 데이터가
 * 정확하다.<br/>
 * (자료등록/우수자료수 데이터 집계시 IKEP4_KN_CVI_POINT 데이터를 사용하기때문에)
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementQuartz.java 15517 2011-06-24 01:12:56Z
 *          jins02 $
 */
public class AssessmentManagementQuartz extends QuartzJobBean {

	private AssessmentManagementUserPviService assessmentManagementUserPviService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.assessmentManagementUserPviService = (AssessmentManagementUserPviService) appContext
					.getBean("assessmentManagementUserPviServiceImpl");

			// 일별 개인별/조직별 평가 집계
			assessmentManagementUserPviService.batchGatherAccessment();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

}
