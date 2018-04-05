/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;


/**
 * Quartz Job Service Interface
 * 
 * @author 주길재
 * @version $Id: QuartzJobService.java 16489 2011-09-06 01:41:09Z giljae $
 */
@Transactional
public interface QuartzJobService {


	/**
	 * Job을 삭제한다.
	 * 
	 * @param jobName - 삭제할 Job 이름
	 * @return true(삭제 성공) or false(삭제 실패)
	 */
	public boolean deleteJob(String jobName);

	public void createJobCronTrigger(BatchCronJobDetail batchCronJobDetail);
	
	public void createJobSimpleTrigger(BatchSimpleJobDetail batchSimpleJobDetail);
	
	public void updateJobCondition(BatchCronJobDetail batchCronJobDetail);
	
	public void updateJobConditionSimple(BatchSimpleJobDetail batchSimpleJobDetail);

	/**
	 * Cron Job Detail 정보를 제공한다.
	 * 
	 * @param triggerName
	 * @param jobName
	 * @return
	 */
	public BatchCronJobDetail getCronJobDetail(String triggerName, String jobName);

	/**
	 * Simple Job Detail 정보를 제공한다.
	 * 
	 * @param triggerName
	 * @param jobName
	 * @return
	 */
	public BatchSimpleJobDetail getSimpleJobDetail(String triggerName, String jobName);

	/**
	 * Job 구동을 중지한다.
	 * 
	 * @param jobName
	 */
	public void pauseJob(String jobName);

	/**
	 * Job 구동을 재개한다.
	 * 
	 * @param jobName
	 */
	public void resumeJob(String jobName);

	/**
	 * 모든 Job 구동을 중지한다.
	 */
	public void pauseAllJob();

	/**
	 * 모든 Job 구동을 재개한다.
	 */
	public void resumeAllJob();
	
	/**
	 * Quartz 스케쥴러 구동 시작
	 *
	 */
	public void start();
	
	/**
	 * Quartz 스케쥴로 구동 중지
	 *
	 */
	public void shutdown();
}
