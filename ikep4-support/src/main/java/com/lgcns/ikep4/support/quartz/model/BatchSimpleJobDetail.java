/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Batch Simple Job Detail
 * 
 * @author 주길재
 * @version $Id: BatchSimpleJobDetail.java 16489 2011-09-06 01:41:09Z giljae $
 */
public class BatchSimpleJobDetail extends BaseObject {
	private static final long serialVersionUID = 329457193899991277L;

	/**
	 * 트리거 이름
	 */
	@NotNull
	@Size(min=0,max=140)
	private String triggerName;

	/**
	 * 잡 이름
	 */
	@NotNull
	@Size(min=0,max=140)
	private String jobName;

	/**
	 * 잡 클래스
	 */
	@NotNull
	@Size(min=0,max=140)
	private String jobClass;
	
	/**
	 * 잡 설명
	 */
	@NotNull
	@Size(min=0,max=200)
	private String description;

	/**
	 * 반복 수행 시킬 인터발 시간
	 */
	@NotNull
	@Digits(integer=10, fraction=0)
	private int repeatInterval;

	/**
	 * 반복 횟수
	 */
	@NotNull
	@Digits(integer=10, fraction=0)
	private int repeatCount;
	
	/**
	 * Job Status <br/>
	 * WATING
	 * PAUSE
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
	 * @return the repeatInterval
	 */
	public int getRepeatInterval() {
		return repeatInterval;
	}

	/**
	 * @param repeatInterval the repeatInterval to set
	 */
	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	/**
	 * @return the repeatCount
	 */
	public int getRepeatCount() {
		return repeatCount;
	}

	/**
	 * @param repeatCount the repeatCount to set
	 */
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
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
