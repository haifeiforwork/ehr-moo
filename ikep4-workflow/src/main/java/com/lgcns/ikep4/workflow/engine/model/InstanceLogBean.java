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
 * @version $Id: InstanceBean.java 오후 3:21:59
 */
public class InstanceLogBean extends BaseObject {

	private String instanceLogId = "";

	private String instanceId = "";

	private String processId = "";

	private String processVer = "";
	
	private String miSeq = "";
	
	private String miBlock = "";

	private String parentInsId = "";

	private String activityId = "";

	private String beforeLogId = "";

	private String afterLogId = "";

	private String message = "";

	private String title = "";

	private String performerId = "";

	private String performerOrg = "";

	private String delegatorId = "";

	private String varLog = "";

	private String state = "";

	private String result = "";

	private Date createDate;

	private Date assignDate;

	private Date selectDate;

	private Date dueDate;

	private Date updateDate;

	private Date finishedDate;

	/**
	 * @return the instanceLogId
	 */
	public String getInstanceLogId() {
		return instanceLogId;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
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
	 * @return the miSeq
	 */
	public String getMiSeq() {
		return miSeq;
	}

	/**
	 * @return the miBlock
	 */
	public String getMiBlock() {
		return miBlock;
	}

	/**
	 * @return the parentInsId
	 */
	public String getParentInsId() {
		return parentInsId;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @return the beforeLogId
	 */
	public String getBeforeLogId() {
		return beforeLogId;
	}

	/**
	 * @return the afterLogId
	 */
	public String getAfterLogId() {
		return afterLogId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the performerId
	 */
	public String getPerformerId() {
		return performerId;
	}

	/**
	 * @return the performerOrg
	 */
	public String getPerformerOrg() {
		return performerOrg;
	}

	/**
	 * @return the delegatorId
	 */
	public String getDelegatorId() {
		return delegatorId;
	}

	/**
	 * @return the varLog
	 */
	public String getVarLog() {
		return varLog;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}

	/**
	 * @return the selectDate
	 */
	public Date getSelectDate() {
		return selectDate;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @return the finishedDate
	 */
	public Date getFinishedDate() {
		return finishedDate;
	}

	/**
	 * @param instanceLogId the instanceLogId to set
	 */
	public void setInstanceLogId(String instanceLogId) {
		this.instanceLogId = instanceLogId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
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
	 * @param miSeq the miSeq to set
	 */
	public void setMiSeq(String miSeq) {
		this.miSeq = miSeq;
	}

	/**
	 * @param miBlock the miBlock to set
	 */
	public void setMiBlock(String miBlock) {
		this.miBlock = miBlock;
	}

	/**
	 * @param parentInsId the parentInsId to set
	 */
	public void setParentInsId(String parentInsId) {
		this.parentInsId = parentInsId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @param beforeLogId the beforeLogId to set
	 */
	public void setBeforeLogId(String beforeLogId) {
		this.beforeLogId = beforeLogId;
	}

	/**
	 * @param afterLogId the afterLogId to set
	 */
	public void setAfterLogId(String afterLogId) {
		this.afterLogId = afterLogId;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param performerId the performerId to set
	 */
	public void setPerformerId(String performerId) {
		this.performerId = performerId;
	}

	/**
	 * @param performerOrg the performerOrg to set
	 */
	public void setPerformerOrg(String performerOrg) {
		this.performerOrg = performerOrg;
	}

	/**
	 * @param delegatorId the delegatorId to set
	 */
	public void setDelegatorId(String delegatorId) {
		this.delegatorId = delegatorId;
	}

	/**
	 * @param varLog the varLog to set
	 */
	public void setVarLog(String varLog) {
		this.varLog = varLog;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * @param selectDate the selectDate to set
	 */
	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param finishedDate the finishedDate to set
	 */
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}

	
	
	
}