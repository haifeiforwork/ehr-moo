/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkPopularService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * Expert Network Batch Scheduler
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkQuartz.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkQuartz extends QuartzJobBean {

	/**
	 * 인기전문가 개수
	 */
	public static final int POPULAR_COUNT = 20;

	private ExpertNetworkPopularService expertNetworkPopularService;

	private ExpertNetworkListService expertNetworkListService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.expertNetworkPopularService = (ExpertNetworkPopularService) appContext.getBean("expertNetworkPopularServiceImpl");
			this.expertNetworkListService = (ExpertNetworkListService) appContext.getBean("expertNetworkListServiceImpl");

			// 인기전문가 집계
			this.expertNetworkPopularService.batchGatherPopular(POPULAR_COUNT);

			// 전문가 집계
			this.expertNetworkListService.batchGatherExpert();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		
	}





}
