/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.forum.service.FrUserActivitiesService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * 사용자 활동 점수 저장, 랭킹 수정 배치 처리 클래스
 * 
 * @author 이동희(loverfairy.com)
 * @version $Id: ActivityQuartz.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ActivityQuartz extends QuartzJobBean {

	private FrUserActivitiesService frUserActivitiesService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.frUserActivitiesService = (FrUserActivitiesService) appContext.getBean("frUserActivitiesService");

			this.frUserActivitiesService.updateActivityScore();
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

}