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
 * @version $Id: PerformerBean.java 오전 11:18:52
 */
public class ParticipantBean extends BaseObject {

	private String participantSeq = "";
	
	private String participantId = "";

	private String participantName = "";

	private String participantType = "";
	
	private String performerId = "";
	
	private String ruleContraint = "";
	
	private String indexVar = "";
	
	private String activityId = "";

	private String processId = "";
	
	private String processVer = "";
	
	private Date createDate;
	
	private String useIndex = "";

	/**
	 * @return the participantSeq
	 */
	public String getParticipantSeq() {
		return participantSeq;
	}

	/**
	 * @return the participantId
	 */
	public String getParticipantId() {
		return participantId;
	}

	/**
	 * @return the participantName
	 */
	public String getParticipantName() {
		return participantName;
	}

	/**
	 * @return the participantType
	 */
	public String getParticipantType() {
		return participantType;
	}

	/**
	 * @return the performerId
	 */
	public String getPerformerId() {
		return performerId;
	}

	/**
	 * @return the ruleContraint
	 */
	public String getRuleContraint() {
		return ruleContraint;
	}

	/**
	 * @return the indexVar
	 */
	public String getIndexVar() {
		return indexVar;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
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
	 * @return the useIndex
	 */
	public String getUseIndex() {
		return useIndex;
	}

	/**
	 * @param participantSeq the participantSeq to set
	 */
	public void setParticipantSeq(String participantSeq) {
		this.participantSeq = participantSeq;
	}

	/**
	 * @param participantId the participantId to set
	 */
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	/**
	 * @param participantName the participantName to set
	 */
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	/**
	 * @param participantType the participantType to set
	 */
	public void setParticipantType(String participantType) {
		this.participantType = participantType;
	}

	/**
	 * @param performerId the performerId to set
	 */
	public void setPerformerId(String performerId) {
		this.performerId = performerId;
	}

	/**
	 * @param ruleContraint the ruleContraint to set
	 */
	public void setRuleContraint(String ruleContraint) {
		this.ruleContraint = ruleContraint;
	}

	/**
	 * @param indexVar the indexVar to set
	 */
	public void setIndexVar(String indexVar) {
		this.indexVar = indexVar;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
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

	/**
	 * @param useIndex the useIndex to set
	 */
	public void setUseIndex(String useIndex) {
		this.useIndex = useIndex;
	}

	
	
	
	
	
}
