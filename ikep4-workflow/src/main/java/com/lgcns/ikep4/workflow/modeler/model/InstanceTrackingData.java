/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Required;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 김동후
 * @version $Id: InstanceTrackingData.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class InstanceTrackingData extends BaseObject {
	
	/**
	 * 파티션 아이디 (인스턴스 정보)
	 */
	private String partitionId;
		
	/**
	 * 인스턴스 아이디 (인스턴스 정보)
	 */
	private String instanceId;
	
	/**
	 * 프로세스 아이디 (인스턴스 정보)
	 */
	private String processId;
	
	/**
	 * 프로세스 버전 (인스턴스 정보)
	 */
	private String processVersion;
	
	/**
	 * 프로세스 이름 (인스턴스 정보)
	 */
	private String processName;
	
	/**
	 * 인스턴스 생성일자 (인스턴스 정보)
	 */
	private String createDate;
	
	/**
	 * 인스턴스 종료일자 (인스턴스 정보)
	 */
	private String finishedDate;
	
	/**
	 * 인스턴스 상태 (인스턴스 정보)
	 * TODO : STATE 값 확인
	 */
	private String state;
	
	/**
	 * 인스턴스 제목 (인스턴스 정보)
	 */
	private String title;
	
	/**
	 * 부모(상위) 인스턴스 아이디 (인스턴스 정보)
	 */
	private String parentInsId;
	
	/**
	 * 액티비티 아이디 (액티비티 정보)
	 */
	private String activityId;
	
	/**
	 * 액티비티 명 (액티비티 정보)
	 */
	private String activityName;
	
	/**
	 * 액티비티 유형 (액티비티 사용자 정의 정보)
	 */
	private String activityType;
	
	/**
	 * 액티비티 생성일자 (액티비티 정보)
	 * TODO : 정확한 컬럼명 확인
	 */
	private String atCreateDate;
	
	/**
	 * 액티비티 완료일자 (액티비티 정보)
	 * TODO : 정확한 컬럼명 확인
	 */
	private String atFinishedDate;
	
	/**
	 * 액티비티 상태 (액티비티 정보)
	 */
	private String atState;
	
	/**
	 * 액티비티 부가 상태 (액티비티 정보)
	 */
	private String atSubState;
	
	/**
	 * 액티비티 수행자 (액티비티 사용자 정의 정보)
	 * TODO : 이름은?, 여러명일 경우는?
	 */
	private String performerId;
	
	/**
	 * 액티비티 수행자 조직 (액티비티 사용자 정의 정보)
	 * TODO : 조직명은?, 여러 조직일 경우는?
	 */
	private String performerOrg;
	
	/**
	 * 액티비티 수행결과 (액티비티 사용자 정의 정보)
	 * TODO : 테이블에 정의된 필드 없음 (확인필요)
	 */
	private String outcome;
	
	/**
	 * 인스턴스 리드타임 (액티비티 사용자 정의 정보)
	 */
	private String leadTime;
	
	/**
	 * 액티비티 리드타임 (액티비티 사용자 정의 정보)
	 */
	private String atLeadTime;
	
	/**
	 * 액티비티 재시도 횟수 (액티비티 사용자 정의 정보)
	 */
	private int retryCount;
	/**
	 * @return the partitionId
	 */
	
	public String getPartitionId() {
		return (partitionId == null ? "" : partitionId);
	}

	/**
	 * @param partitionId the partitionId to set
	 */
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}
	/**
	 * @param instanceId the instanceId to set
	 */
	@Required
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	@Required
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @return the processVersion
	 */
	public String getProcessVersion() {
		return processVersion;
	}

	/**
	 * @param processVersion the processVersion to set
	 */
	@Required
	public void setProcessVersion(String processVersion) {
		this.processVersion = processVersion;
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return (processName == null ? "" : processName);
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the finishedDate
	 */
	public String getFinishedDate() {
		return (finishedDate == null ? "" : finishedDate);
	}

	/**
	 * @param finishedDate the finishedDate to set
	 */
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}

	/**
	 * @return the state
	 */
	@Required
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return (title == null ? "" : title);
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the parentInsId
	 */
	public String getParentInsId() {
		return (parentInsId == null ? "" : parentInsId);
	}

	/**
	 * @param parentInsId the parentInsId to set
	 */
	public void setParentInsId(String parentInsId) {
		this.parentInsId = parentInsId;
	}

	/**
	 * @return the activityId
	 */
	@Required
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return (activityName == null ? "" : activityName);
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return the activityType
	 */
	public String getActivityType() {
		return (activityType == null ? "" : activityType);
	}

	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the atCreateDate
	 */
	public String getAtCreateDate() {
		return (atCreateDate == null ? "" : atCreateDate);
	}

	/**
	 * @param atCreateDate the atCreateDate to set
	 */
	public void setAtCreateDate(String atCreateDate) {
		this.atCreateDate = atCreateDate;
	}

	/**
	 * @return the atFinishedDate
	 */
	public String getAtFinishedDate() {
		return (atFinishedDate == null ? "" : atFinishedDate);
	}

	/**
	 * @param atFinishedDate the atFinishedDate to set
	 */
	public void setAtFinishedDate(String atFinishedDate) {
		this.atFinishedDate = atFinishedDate;
	}

	/**
	 * @return the atState
	 */
	public String getAtState() {
		return atState;
	}

	/**
	 * @param atState the atState to set
	 */
	@Required
	public void setAtState(String atState) {
		this.atState = atState;
	}
	
	/**
	 * @return the atSubState
	 */
	public String getAtSubState() {
		return atSubState;
	}

	/**
	 * @param atSubState the atSubState to set
	 */
	public void setAtSubState(String atSubState) {
		this.atSubState = atSubState;
	}
	
	/**
	 * @return the performerId
	 */
	public String getPerformerId() {
		return (performerId == null ? "" : performerId);
	}

	/**
	 * @param performerId the performerId to set
	 */
	public void setPerformerId(String performerId) {
		this.performerId = performerId;
	}

	/**
	 * @return the performerOrg
	 */
	public String getPerformerOrg() {
		return (performerOrg == null ? "" : performerOrg);
	}

	/**
	 * @param performerOrg the performerOrg to set
	 */
	public void setPerformerOrg(String performerOrg) {
		this.performerOrg = performerOrg;
	}

	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return (outcome == null ? "" : outcome);
	}

	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	/**
	 * @return the leadTime
	 */
	public String getLeadTime() {
		return (leadTime == null ? "" : leadTime);
	}

	/**
	 * @param leadTime the leadTime to set
	 */
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	/**
	 * @return the atLeadTime
	 */
	public String getAtLeadTime() {
		return (atLeadTime == null ? "" : atLeadTime);
	}

	/**
	 * @param atLeadTime the atLeadTime to set
	 */
	public void setAtLeadTime(String atLeadTime) {
		this.atLeadTime = atLeadTime;
	}

	/**
	 * @return the retryCount
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount the retryCount to set
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public static void main(String [] args) {

	}
}
