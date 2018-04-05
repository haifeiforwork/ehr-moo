/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.model;

import java.util.Date;

//import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Quartz Log Model
 * 
 * @author 주길재
 * @version $Id: QuartzLog.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public class QuartzLog extends BaseObject {
	private static final long serialVersionUID = -2867424595799944564L;

	/**
	 * sequence id
	 */
	private String id;

	/**
	 * batch job 이름
	 */
	private String jobName;

	/**
	 * batch 실행 결과 <br/>
	 * C: 성공 F: 실패
	 */
	private String result;

	/**
	 * 시작 시각
	 */
	private Date startDate;

	/**
	 * 종료 시각
	 */
	private Date endDate;

	/**
	 * 실패시 실패원인 기록
	 */
	private String errorCause;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the errorCause
	 */
	public String getErrorCause() {
		return errorCause;
	}

	/**
	 * @param errorCause the errorCause to set
	 */
	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}
}
