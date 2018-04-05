/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtStatisticsService;


/**
 * 사용량 통계 배치
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtStatisticsScheduled.java 16489 2011-09-06 01:41:09Z giljae $
 */
public class UtStatisticsScheduled extends QuartzJobBean{
	
	private UtStatisticsService utStatisticsService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
		
		try {
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");
			
			this.utStatisticsService = (UtStatisticsService)appContext.getBean("utStatisticsServiceImpl");
            utStatisticsService.utStatisticsBatch();
		}catch(Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		
		
	}
}
