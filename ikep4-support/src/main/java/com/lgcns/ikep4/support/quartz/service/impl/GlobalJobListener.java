/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgcns.ikep4.support.quartz.model.QuartzLog;
import com.lgcns.ikep4.support.quartz.service.BatchManageService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * TODO Javadoc주석작성
 * 
 * @author 주길재
 * @version $Id: GlobalJobListener.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public class GlobalJobListener implements JobListener {
	protected final Log log = LogFactory.getLog(GlobalJobListener.class);

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private BatchManageService batchManageService;

	String listenerType = "Non global";

	public void setListenerType(String listenerType) {
		this.listenerType = listenerType;
	}

	/**
	 * <p>
	 * Get the name of the <code>JobListener</code>.
	 * </p>
	 */
	public String getName() {
		return "GlobalJobListener";
	}

	/**
	 * <p>
	 * Called by the <code>{@link Scheduler}</code> when a
	 * <code>{@link org.quartz.JobDetail}</code> is about to be executed (an
	 * associated <code>{@link Trigger}</code> has occurred).
	 * </p>
	 * <p>
	 * This method will not be invoked if the execution of the Job was vetoed by
	 * a <code>{@link TriggerListener}</code>.
	 * </p>
	 * 
	 * @see #jobExecutionVetoed(JobExecutionContext)
	 */
	public void jobToBeExecuted(JobExecutionContext context) {
	}

	/**
	 * <p>
	 * Called by the <code>{@link Scheduler}</code> when a
	 * <code>{@link org.quartz.JobDetail}</code> was about to be executed (an
	 * associated <code>{@link Trigger}</code> has occurred), but a
	 * <code>{@link TriggerListener}</code> vetoed it's execution.
	 * </p>
	 * 
	 * @see #jobToBeExecuted(JobExecutionContext)
	 */
	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	/**
	 * <p>
	 * Called by the <code>{@link Scheduler}</code> after a
	 * <code>{@link org.quartz.JobDetail}</code> has been executed, and be for
	 * the associated <code>Trigger</code>'s <code>triggered(xx)</code> method
	 * has been called.
	 * </p>
	 */
	@SuppressWarnings("unused")
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// TODO 배치 로그 테이블에 저장하는 루틴 구현
		QuartzLog quartzLog = new QuartzLog();
		quartzLog.setId(idgenService.getNextId());
		quartzLog.setJobName(context.getJobDetail().getName());
		quartzLog.setStartDate(context.getFireTime());
		quartzLog.setEndDate(new Date());
		
		log.info("====================Job Executed!======================");
		log.info("[GlobalJobListener] JobName : " + context.getJobDetail().getName());
		log.info("[GlobalJobListener] Job Start Date : " + context.getFireTime());
		log.info("[GlobalJobListener] Job End Date : " + quartzLog.getEndDate());

		if (jobException != null) {
			quartzLog.setErrorCause(jobException.getMessage());
			quartzLog.setResult("F");
			log.info("[GlobalJobListener] Result : F");
			log.info("[GlobalJobListener] Error Cause : " + jobException.getMessage());
			log.info("=======================================================");
			this.batchManageService.createBatchLog(quartzLog);
		} else {
			try {
				SimpleTrigger simpleTrigger = (SimpleTrigger) context.getTrigger();
				quartzLog.setResult("C");
				log.info("[GlobalJobListener] Result : C");
			}catch(Exception e) {
				quartzLog.setResult("C");
				log.info("=======================================================");
				this.batchManageService.createBatchLog(quartzLog);
			}
		}
	}

}
