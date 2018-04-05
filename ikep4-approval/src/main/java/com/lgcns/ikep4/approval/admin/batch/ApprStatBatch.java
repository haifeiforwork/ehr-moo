/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.approval.admin.model.ApprStat;
import com.lgcns.ikep4.approval.admin.service.ApprStatService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * ApprStat 배치 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ApprStatBatch.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class ApprStatBatch extends QuartzJobBean {

	/**
	 * 통계 서비스
	 */
	private ApprStatService apprStatService;

	/**
	 * 통계 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Log log = LogFactory.getLog(this.getClass());

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			log.debug("=== ApprStatBatch start ===");

			apprStatService = (ApprStatService) appContext.getBean("apprStatServiceImpl");
			
			ApprStat apprStat = new ApprStat();
			
			// 리드타임 통계 생성
			apprStatService.generateTimeStat(apprStat);

			// 사용자별 통계 생성
			apprStatService.generateUserStat(apprStat);

			// 양식별 통계 생성
			apprStatService.generateFormStat(apprStat);

			log.debug("=== ApprStatBatch end ===");

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

}
