/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.npd.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.approval.collaboration.common.model.CbrConstants;
import com.lgcns.ikep4.approval.collaboration.npd.service.NewProductDevService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * 개발 검토 의뢰서 검토 기한 초과 알림 메일배치
 * 
 * @author 
 * @version
 */
public class ScheduleLimitMailBatch extends QuartzJobBean {
	
	private NewProductDevService newProductDevService;
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
		
		try {
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");
			
			newProductDevService = (NewProductDevService) appContext.getBean("newProductDevServiceImpl");
			
			newProductDevService.sendScheduleLimitSend();
		}catch(Exception e) {
			System.out.println("#######################ScheduleLimitMailBatch Error########################");
			e.printStackTrace();
			System.out.println("######################ScheduleLimitMailBatch Error#########################");
			throw new IKEP4ApplicationException("", e);
		}
	}

}
