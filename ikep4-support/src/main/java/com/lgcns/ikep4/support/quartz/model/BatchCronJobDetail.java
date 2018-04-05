/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Batch Cron Job Model
 * 
 * @author 주길재
 * @version $Id: BatchCronJobDetail.java 16489 2011-09-06 01:41:09Z giljae $
 */
public class BatchCronJobDetail extends BaseObject {
	private static final long serialVersionUID = 874777193855116932L;

	/**
	 * 트리거 이름
	 */
	@NotNull
	@Size(min = 0, max = 140)
	private String triggerName;

	/**
	 * 잡 이름
	 */
	@NotNull
	@Size(min = 0, max = 140)
	private String jobName;

	/**
	 * 잡 클래스 (패키지명 포함)
	 */
	@NotNull
	@Size(min = 0, max = 140)
	private String jobClass;

	/**
	 * 크론 표현식
	 */
	@NotNull
	@Size(min = 0, max = 80)
	private String cronExpression;

	/**
	 * 잡 설명
	 */
	@NotNull
	@Size(min = 0, max = 200)
	private String description;
	
	/**
	 * Job Status
	 */
	@NotNull
	private String jobStatus;

	/**
	 * Job Listener
	 */
	private String jobListener;
	
	private String jobCondition;

	/**
	 * @return the triggerName
	 */
	public String getTriggerName() {
		return triggerName;
	}

	/**
	 * @param triggerName the triggerName to set
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobClass
	 */
	public String getJobClass() {
		return jobClass;
	}

	/**
	 * @param jobClass the jobClass to set
	 */
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * @return the jobListener
	 */
	public String getJobListener() {
		return jobListener;
	}

	/**
	 * @param jobListener the jobListener to set
	 */
	public void setJobListener(String jobListener) {
		this.jobListener = jobListener;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		if(!"PAUSED".equals(jobStatus)) {
			jobStatus = "WAITING";
		}
		this.jobStatus = jobStatus;
	}

	public String getJobCondition() {
		return jobCondition;
	}

	public void setJobCondition(String jobCondition) {
		this.jobCondition = jobCondition;
	}
	
}
