/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.sms.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.support.sms.service.SmsService;


/**
 * sms 수발신 목록 배치(최근 3개월이내 최다 100건)
 * 
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: SmsQuartz.java 16083 2011-08-03 02:19:31Z giljae $
 */
public class SmsQuartz extends QuartzJobBean {

	private SmsService smsService;

	/**
	 * sms 삭제처리 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.smsService = (SmsService) appContext.getBean("smsService");

			this.smsService.deleteRecentSms();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}
}
