/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: TransitionBean.java 오후 2:32:35 
 */
public class TransitionBean extends BaseObject {

	private String transitionId = ""; //

	private String transitionName = "";

	private String transitionFrom = "";

	private String transitionTo = "";

	private String conditionType = "";

	private String conditionExpression = "";
	
	private String processId = "";
	
	private String processVer = "";
	
	private Date createDate;

	/**
	 * @return the transitionId
	 */
	public String getTransitionId() {
		return transitionId;
	}

	/**
	 * @return the transitionName
	 */
	public String getTransitionName() {
		return transitionName;
	}

	/**
	 * @return the transitionFrom
	 */
	public String getTransitionFrom() {
		return transitionFrom;
	}

	/**
	 * @return the transitionTo
	 */
	public String getTransitionTo() {
		return transitionTo;
	}

	/**
	 * @return the conditionType
	 */
	public String getConditionType() {
		return conditionType;
	}

	/**
	 * @return the conditionExpression
	 */
	public String getConditionExpression() {
		return conditionExpression;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @return the processVer
	 */
	public String getProcessVer() {
		return processVer;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param transitionId the transitionId to set
	 */
	public void setTransitionId(String transitionId) {
		this.transitionId = transitionId;
	}

	/**
	 * @param transitionName the transitionName to set
	 */
	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	/**
	 * @param transitionFrom the transitionFrom to set
	 */
	public void setTransitionFrom(String transitionFrom) {
		this.transitionFrom = transitionFrom;
	}

	/**
	 * @param transitionTo the transitionTo to set
	 */
	public void setTransitionTo(String transitionTo) {
		this.transitionTo = transitionTo;
	}

	/**
	 * @param conditionType the conditionType to set
	 */
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	/**
	 * @param conditionExpression the conditionExpression to set
	 */
	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @param processVer the processVer to set
	 */
	public void setProcessVer(String processVer) {
		this.processVer = processVer;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	
	
}
