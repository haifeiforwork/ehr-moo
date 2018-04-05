/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: ProcessBean.java 오후 2:32:24 
 */
public class ProcessBean extends BaseObject {

	static final long serialVersionUID = 197103427696074848L;

	private String partitionId = "";
	
	private String partitionName = "";
	
	private String processId = "";

	private String processVer = "";

	private String processName = "";

	private String processType = "";
	
	private String processState = "";

	private String vendor = "";

	private String authorId = "";
	
	private String authorName = "";

	private String startMode = "";

	private String varDefine = "";
	
	private String activityId = "";
	
	private String activityName = "";

	private String description = "";

	private String attrExpand = "";

	private String limitStartTime = "";

	private String limitEndTime = "";
	
	private Date createDate;
	
	private String userEnglishName = "";
	
	private Date applyStartDate;
	
	private Date applyEndDate;
	
	
	private Map<String, ActivityBean>	activityHash		= new HashMap<String, ActivityBean>();
	private Map<String, TransitionBean>	transitionHash		= new HashMap<String, TransitionBean>();
	private Map<String, DatafieldBean>	datafieldHash		= new HashMap<String, DatafieldBean>();	


	
	

	/**
	 * @return the partitionId
	 */
	public String getPartitionId() {
		return partitionId;
	}

	/**
	 * @return the partitionName
	 */
	public String getPartitionName() {
		return partitionName;
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
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * @return the processState
	 */
	public String getProcessState() {
		return processState;
	}

	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * @return the authorId
	 */
	public String getAuthorId() {
		return authorId;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @return the startMode
	 */
	public String getStartMode() {
		return startMode;
	}

	/**
	 * @return the varDefine
	 */
	public String getVarDefine() {
		return varDefine;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the attrExpand
	 */
	public String getAttrExpand() {
		return attrExpand;
	}

	/**
	 * @return the limitStartTime
	 */
	public String getLimitStartTime() {
		return limitStartTime;
	}

	/**
	 * @return the limitEndTime
	 */
	public String getLimitEndTime() {
		return limitEndTime;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param partitionId the partitionId to set
	 */
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	/**
	 * @param partitionName the partitionName to set
	 */
	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
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
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}

	/**
	 * @param processState the processState to set
	 */
	public void setProcessState(String processState) {
		this.processState = processState;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @param startMode the startMode to set
	 */
	public void setStartMode(String startMode) {
		this.startMode = startMode;
	}

	/**
	 * @param varDefine the varDefine to set
	 */
	public void setVarDefine(String varDefine) {
		this.varDefine = varDefine;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param attrExpand the attrExpand to set
	 */
	public void setAttrExpand(String attrExpand) {
		this.attrExpand = attrExpand;
	}

	/**
	 * @param limitStartTime the limitStartTime to set
	 */
	public void setLimitStartTime(String limitStartTime) {
		this.limitStartTime = limitStartTime;
	}

	/**
	 * @param limitEndTime the limitEndTime to set
	 */
	public void setLimitEndTime(String limitEndTime) {
		this.limitEndTime = limitEndTime;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * 
	 * @param activityId
	 * @param activityBean
	 */
	public void addActivityBean(String activityId, ActivityBean activityBean) {
		activityHash.put(activityId, activityBean);
	}
	
	/**
	 * 
	 * @param activityId
	 * @return
	 */
	public ActivityBean getActivityBean(String activityId) {
		ActivityBean activityBean		= null;
		activityBean					= (ActivityBean) activityHash.get(activityId);
		return activityBean;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, ActivityBean> getActivityHash() {
		return activityHash;
	}
	
	/**
	 * 
	 * @param transitionId
	 * @param transitionBean
	 */
	public void addTransitionBean(String transitionId, TransitionBean transitionBean) {
		transitionHash.put(transitionId, transitionBean);
	}
	
	/**
	 * 
	 * @param transitionId
	 * @return
	 */
	public TransitionBean getTransitionBean(String transitionId) {
		TransitionBean transitionBean	= null;
		transitionBean					= (TransitionBean) transitionHash.get(transitionId);
		return transitionBean;
	}	
	
	/**
	 * 
	 * @return
	 */
	public Map<String, TransitionBean> getTransitionHash() {
		return transitionHash;
	}	
	
	/**
	 * 
	 * @param datafieldId
	 * @param datafieldBean
	 */
	public void addDatafieldBean(String datafieldId, DatafieldBean datafieldBean) {
		datafieldHash.put(datafieldId, datafieldBean);
	}
	
	/**
	 * 
	 * @param datafieldId
	 * @return
	 */
	public DatafieldBean getDatafieldBean(String datafieldId) {
		DatafieldBean datafieldBean	= null;
		if(datafieldHash.containsKey(datafieldId)) {
			datafieldBean					= (DatafieldBean) datafieldHash.get(datafieldId);
		}
		return datafieldBean;
	}	
	
	/**
	 * 
	 * @return
	 */
	public Map<String, DatafieldBean> getDatafieldHash() {
		return datafieldHash;
	}

	/**
	 * @return the userEnglishName
	 */
	public String getUserEnglishName() {
		return userEnglishName;
	}

	/**
	 * @param userEnglishName the userEnglishName to set
	 */
	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	/**
	 * @return the applyStartDate
	 */
	public Date getApplyStartDate() {
		return applyStartDate;
	}

	/**
	 * @param applyStartDate the applyStartDate to set
	 */
	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}

	/**
	 * @return the applyEndDate
	 */
	public Date getApplyEndDate() {
		return applyEndDate;
	}

	/**
	 * @param applyEndDate the applyEndDate to set
	 */
	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
	}			
	
	
	
	
}
