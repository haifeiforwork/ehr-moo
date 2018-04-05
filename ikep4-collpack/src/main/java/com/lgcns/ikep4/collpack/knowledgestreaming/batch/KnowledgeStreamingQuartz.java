/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.knowledgestreaming.service.KnowledgeStreamingService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;

/**
 * Knowledge Monitor Batch Scheduler
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorQuartz.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeStreamingQuartz extends QuartzJobBean {

	@Autowired
	private KnowledgeStreamingService knowledgeStreamingService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler()
					.getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext
					.get("applicationContext");

			this.knowledgeStreamingService = (KnowledgeStreamingService) appContext
					.getBean("knowledgeStreamingServiceImpl");

			// 지식 활용 수준 집계 데이터 배치 입력
			this.knowledgeStreamingService.batchInsert();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

}
