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

import com.lgcns.ikep4.collpack.qna.service.QnaPolicyService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * Q&A score저장, best 질문, 답변 베스트 선정 배치 처리 클래스
 * 
 * @author 이동희(loverfairy.com)
 * @version $Id: ScoreQuartz.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ScoreQuartz extends QuartzJobBean {

	private QnaPolicyService qnaPolicyService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = jobExecutionContext.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.qnaPolicyService = (QnaPolicyService) appContext.getBean("qnaPolicyService");

			qnaPolicyService.applyBestQnaAndSaveScore();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

}
