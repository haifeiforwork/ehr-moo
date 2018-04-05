/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Batch Trigger 정보 모델
 * 
 * @author 주길재
 * @version $Id: BatchTrigger.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class BatchTrigger extends BaseObject {
	private static final long serialVersionUID = 874926400195116932L;

	/**
	 * Trigger 이름
	 */
	private String triggerName;

	/**
	 * Job 이름
	 */
	private String jobName;

	/**
	 * Trigger 타입 <br/>
	 * CRON, SIMPLE
	 */
	private String triggerType;
	
	/**
	 * Trigger 상태 <br/>
	 * PAUSED, WATING
	 */
	private String triggerStatus;

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
	 * @return the triggerType
	 */
	public String getTriggerType() {
		return triggerType;
	}

	/**
	 * @param triggerType the triggerType to set
	 */
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	/**
	 * @return the triggerStatus
	 */
	public String getTriggerStatus() {
		return triggerStatus;
	}

	/**
	 * @param triggerStatus the triggerStatus to set
	 */
	public void setTriggerStatus(String triggerStatus) {
		if(!"PAUSED".equals(triggerStatus)) {
			triggerStatus = "WAITING";
		}
		
		this.triggerStatus = triggerStatus;
	}

}
