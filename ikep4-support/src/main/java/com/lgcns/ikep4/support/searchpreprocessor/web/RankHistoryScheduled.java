/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.web;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.searchpreprocessor.service.HistoryService;
import com.lgcns.ikep4.support.searchpreprocessor.util.MagicNumUtils;


/**
 * 검색어 히스토리 3개월 유지
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: RankHistoryScheduled.java 16276 2011-08-18 07:09:07Z giljae $
 */
public class RankHistoryScheduled extends QuartzJobBean {

	private HistoryService historyService;

	/**
	 * 실행 메소드
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.historyService = (HistoryService) appContext.getBean("historyServiceImpl");

			Date date = DateUtils.addMonths(new Date(), -1 * MagicNumUtils.MONTH_3);
			historyService.removeAll(date);
			
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}
}
