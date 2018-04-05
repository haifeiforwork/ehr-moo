/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.service.impl;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.quartz.dao.BatchManageDao;
import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;


/**
 * Quartz Job Service 구현체
 * 
 * @author 주길재
 * @version $Id: QuartzJobServiceImpl.java 14040 2011-05-31 07:37:30Z happyi1018
 *          $
 */
@Service
public class QuartzJobServiceImpl implements QuartzJobService {
	@Autowired
	@Qualifier("quartzScheduler")
	private SchedulerFactoryBean schedulerFactoryBean;

	private Scheduler scheduler = null;

	@Autowired
	private BatchManageDao batchManageDao;

	private final String JOB_GROUP = "IKEP4_JOBS";

	private final String PAUSED_JOB = "PAUSED";

	/**
	 * Quartz Scheduler 구동 시작
	 */
	public void start() {
		try {
			if (this.scheduler == null) {
				this.scheduler = this.schedulerFactoryBean.getScheduler();

				//scheduler가 널이 아니고, 종료되어 있지 않으면 구동한다.
				if (this.scheduler != null && !this.scheduler.isShutdown()) {
					this.scheduler.start();
				}
			}
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

	/**
	 * Quartz Scheduler 구동 중지
	 */
	public void shutdown() {
		try {
			if (this.scheduler != null && !this.scheduler.isShutdown()) {
				this.scheduler.shutdown();

				this.scheduler = null;
			}
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

	public void createJobSimpleTrigger(BatchSimpleJobDetail batchSimpleJobDetail) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		String jobName = batchSimpleJobDetail.getJobName();
		String jobClass = batchSimpleJobDetail.getJobClass();
		String description = batchSimpleJobDetail.getDescription();
		String jobListener = batchSimpleJobDetail.getJobListener();
		String triggerName = batchSimpleJobDetail.getTriggerName();
		int repeatInterval = batchSimpleJobDetail.getRepeatInterval();
		int repeatCount = batchSimpleJobDetail.getRepeatCount();
		String jobStatus = batchSimpleJobDetail.getJobStatus();

		try {
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP, Class.forName(jobClass));
			jobDetail.setRequestsRecovery(true); // Job의 load balancing과
													// failover를 지원

			jobDetail.setDescription(description); // description

			// jobListener 이름을 등록
			if (jobListener != null && !"".equals(jobListener)) {
				String[] jobListenerNames = jobListener.split("\\.");
				jobDetail.addJobListener(jobListenerNames[jobListenerNames.length - 1]);
			}

			scheduler.addJob(jobDetail, true);

			SimpleTrigger trigger = new SimpleTrigger(triggerName, JOB_GROUP);
			trigger.setJobName(jobName); // Job Name 설정
			trigger.setJobGroup(JOB_GROUP); // Job Group 설정
			trigger.setRepeatInterval(repeatInterval); // 반복 주기 설정
			trigger.setRepeatCount(repeatCount); // 반복 횟수 설정
			trigger.setDescription(description); // description
			scheduler.startDelayed(5000); // startDelay 설정

			// jobListener를 등록한다.
			if (jobListener != null && !"".equals(jobListener)) {
				// JobListener 로 부터 JobListener를 생성
				Object jobListenerInstance = Class.forName(jobListener).newInstance();
				JobListener jl = (JobListener) jobListenerInstance;

				scheduler.addJobListener(jl);
			}

			scheduler.scheduleJob(trigger);

			// pauseJob or resumeJob
			if (PAUSED_JOB.equals(jobStatus)) {
				scheduler.pauseJob(jobName, JOB_GROUP);
			} else {
				scheduler.resumeJob(jobName, JOB_GROUP);
			}

		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Create the Simple Trigger Job errors", se);
		} catch (ClassNotFoundException ce) {
			throw new IKEP4ApplicationException("Create the Cron Trigger occured ClassNotFoundException errors", ce);
		} catch (IllegalAccessException ile) {
			throw new IKEP4ApplicationException("Create the Cron Trigger occured IllegalAccessException errors", ile);
		} catch (InstantiationException ie) {
			throw new IKEP4ApplicationException("Create the Cron Trigger occured InstantiationException errors", ie);
		}
	}
	

	public boolean deleteJob(String jobName) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		try {
			return scheduler.deleteJob(jobName, JOB_GROUP);
		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Delete the Simple Trigger Job errors", se);
		}
	}

	public void createJobCronTrigger(BatchCronJobDetail batchCronJobDetail) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		String jobName = batchCronJobDetail.getJobName();
		String jobClass = batchCronJobDetail.getJobClass();
		String jobListener = batchCronJobDetail.getJobListener();
		String description = batchCronJobDetail.getDescription();
		String triggerName = batchCronJobDetail.getTriggerName();
		String cronExpression = batchCronJobDetail.getCronExpression();
		String jobStatus = batchCronJobDetail.getJobStatus();

		try {
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP, Class.forName(jobClass));
			jobDetail.setRequestsRecovery(true); // Job의 load balancing과
													// failover를 지원

			jobDetail.setDescription(description); // description

			// jobListener 이름을 등록
			if (jobListener != null && !"".equals(jobListener)) {
				String[] jobListenerNames = jobListener.split("\\.");
				jobDetail.addJobListener(jobListenerNames[jobListenerNames.length - 1]);
			}

			scheduler.addJob(jobDetail, true);

			CronTrigger trigger = new CronTrigger(triggerName, JOB_GROUP);

			trigger.setJobName(jobName); // Job Name 설정
			trigger.setJobGroup(JOB_GROUP); // Job Group 설정
			trigger.setCronExpression(cronExpression); // cron 표현식
			trigger.setDescription(description); // description

			// jobListener를 등록한다.
			if (jobListener != null && !"".equals(jobListener)) {
				// JobListener 로 부터 JobListener를 생성
				Object jobListenerInstance = Class.forName(jobListener).newInstance();
				JobListener jl = (JobListener) jobListenerInstance;

				scheduler.addJobListener(jl);
			}

			scheduler.scheduleJob(trigger);

			// pauseJob or resumeJob
			if (PAUSED_JOB.equals(jobStatus)) {
				scheduler.pauseJob(jobName, JOB_GROUP);
			} else {
				scheduler.resumeJob(jobName, JOB_GROUP);
			}
		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Create the Cron Trigger Job errors", se);
		} catch (ParseException pe) {
			throw new IKEP4ApplicationException("Create the Cron Trigger Job parse errors", pe);
		} catch (ClassNotFoundException ce) {
			throw new IKEP4ApplicationException("Create the Cron Trigger occured ClassNotFoundException errors", ce);
		} catch (InstantiationException ie) {
			throw new IKEP4ApplicationException("Create the Cron Trigger occured InstantiationException errors", ie);
		} catch (IllegalAccessException ile) {
			throw new IKEP4ApplicationException("Create the Cron Trigger occured IllegalAccessException errors", ile);
		}
	}

	public void pauseJob(String jobName) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		try {
			scheduler.pauseJob(jobName, JOB_GROUP);
		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Pause Job errors", se);
		}
	}

	public void resumeJob(String jobName) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		try {
			scheduler.resumeJob(jobName, JOB_GROUP);
		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Resume Job errors", se);
		}
	}

	public void pauseAllJob() {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		try {
			scheduler.pauseAll();
		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Pause All Job errors", se);
		}
	}

	public void resumeAllJob() {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		try {
			scheduler.resumeAll();
		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Resume All Job errors", se);
		}
	}

	public BatchCronJobDetail getCronJobDetail(String triggerName, String jobName) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		BatchCronJobDetail batchCronJobDetail = new BatchCronJobDetail();

		try {
			JobDetail jobDetail = scheduler.getJobDetail(jobName, JOB_GROUP);

			String[] jobListenerName = jobDetail.getJobListenerNames();
			JobListener jobListener = null;
			if (jobListenerName.length > 0) {
				jobListener = scheduler.getJobListener(jobListenerName[0]);
			}

			CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerName, JOB_GROUP);

			String jobStatus = this.batchManageDao.getJobStatus(jobName);
			String jobCondition = this.batchManageDao.getJobCondition(jobName);

			batchCronJobDetail.setJobClass(jobDetail.getJobClass().toString().replaceAll("class ", ""));
			batchCronJobDetail.setJobName(jobDetail.getName());
			batchCronJobDetail.setTriggerName(cronTrigger.getName());
			batchCronJobDetail.setDescription(jobDetail.getDescription());
			batchCronJobDetail.setJobStatus(jobStatus);
			batchCronJobDetail.setJobCondition(jobCondition);
			

			if (jobListener != null) {
				batchCronJobDetail.setJobListener(jobListener.getClass().toString().replaceAll("class ", ""));
			} else {
				batchCronJobDetail.setJobListener("");
			}

			batchCronJobDetail.setCronExpression(cronTrigger.getCronExpression());

		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Get the Cron Trigger Job errors", se);
		}

		return batchCronJobDetail;
	}

	public BatchSimpleJobDetail getSimpleJobDetail(String triggerName, String jobName) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		BatchSimpleJobDetail batchSimpleJobDetail = new BatchSimpleJobDetail();

		try {
			JobDetail jobDetail = scheduler.getJobDetail(jobName, JOB_GROUP);

			String[] jobListenerName = jobDetail.getJobListenerNames();
			JobListener jobListener = null;
			if (jobListenerName.length > 0) {
				jobListener = scheduler.getJobListener(jobListenerName[0]);
			}

			SimpleTrigger simpleTrigger = (SimpleTrigger) scheduler.getTrigger(triggerName, JOB_GROUP);

			String jobStatus = this.batchManageDao.getJobStatus(jobName);
			String jobCondition = this.batchManageDao.getJobCondition(jobName);

			// Simple Job Class path
			batchSimpleJobDetail.setJobClass(jobDetail.getJobClass().toString().replaceAll("class ", ""));
			// Simple Job name
			batchSimpleJobDetail.setJobName(jobDetail.getName());
			// Simple Job repeat count
			batchSimpleJobDetail.setRepeatCount(simpleTrigger.getRepeatCount());
			// Simple Job Trigger name
			batchSimpleJobDetail.setTriggerName(simpleTrigger.getName());
			// Simple Job repeat interval
			batchSimpleJobDetail.setRepeatInterval((int) simpleTrigger.getRepeatInterval());
			// Simple Job Description
			batchSimpleJobDetail.setDescription(jobDetail.getDescription());
			batchSimpleJobDetail.setJobStatus(jobStatus);
			batchSimpleJobDetail.setJobCondition(jobCondition);

			if (jobListener != null) {
				batchSimpleJobDetail.setJobListener(jobListener.getClass().toString().replaceAll("class ", ""));
			} else {
				batchSimpleJobDetail.setJobListener("");
			}

		} catch (SchedulerException se) {
			throw new IKEP4ApplicationException("Get the Simple Trigger Job errors", se);
		}

		return batchSimpleJobDetail;
	}
	
	public void updateJobCondition(BatchCronJobDetail batchCronJobDetail) {
		this.batchManageDao.updateJobCondition(batchCronJobDetail);
	}
	
	public void updateJobConditionSimple(BatchSimpleJobDetail batchSimpleJobDetail){
		this.batchManageDao.updateJobConditionSimple(batchSimpleJobDetail);
	}
}