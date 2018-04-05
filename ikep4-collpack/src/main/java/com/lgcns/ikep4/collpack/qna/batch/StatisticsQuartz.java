/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.qna.service.QnaStatisticsService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * Q&A 평균시간, 평균 답변수 처리 배치 처리 클래스
 * 
 * @author 이동희(loverfairy.com)
 * @version $Id: StatisticsQuartz.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class StatisticsQuartz extends QuartzJobBean {

	private QnaStatisticsService qnaStatisticsService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = jobExecutionContext.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.qnaStatisticsService = (QnaStatisticsService) appContext.getBean("qnaStatisticsService");
			
			qnaStatisticsService.create();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
