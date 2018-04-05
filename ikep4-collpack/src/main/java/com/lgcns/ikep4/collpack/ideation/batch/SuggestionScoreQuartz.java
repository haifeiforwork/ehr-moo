/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.ideation.service.IdUserActivitiesService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * 제안 점수 저장, 랭킹 수정 배치 처리 클래스
 * 
 * @author 이동희(loverfairy.com)
 * @version $Id: SuggestionScoreQuartz.java 12417 2011-05-20 07:53:57Z
 *          loverfairy $
 */
public class SuggestionScoreQuartz extends QuartzJobBean {

	private IdUserActivitiesService idUserActivitiesService;

	/**
	 * 연결정보가 없는 파일 삭제처리 배치 처리
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.idUserActivitiesService = (IdUserActivitiesService) appContext.getBean("idUserActivitiesService");

			this.idUserActivitiesService.updateSuggestionScore();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
