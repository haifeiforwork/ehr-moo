/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.knowledgehub.service.KnowledgeMapPopularListService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * Knowledge Map Batch Scheduler
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapQuartz.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapQuartz extends QuartzJobBean {

	private KnowledgeMapPopularListService knowledgeMapPopularListService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.knowledgeMapPopularListService = (KnowledgeMapPopularListService) appContext.getBean("knowledgeMapPopularListServiceImpl");
			
			// 일별 인기지식 집계
			this.knowledgeMapPopularListService.batchGatherKnowledge();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
