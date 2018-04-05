/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.model;

import java.sql.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * IKEP4_WF_PROCESS_MODEL 테이블 VO
 * 
 * @author 이승민(lsm3174@built1.com)
 * @version $Id: ProcessModel.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ProcessModel extends BaseObject {

	/**
	 * 벤더
	 */
	private String vendor;

	/**
	 * 모델 스크립트
	 */
	private String modelScript;

	/**
	 * 프로세스 아이디
	 */
	private String processId;

	/**
	 * 프로세스 버전
	 */
	private String processVer;

	/**
	 * 등록 날짜
	 */
	private Date createDate;

	/**
	 * 뷰 스크립트
	 */
	private String viewScript;

	/**
	 * 등록자
	 */
	private String author;

	/**
	 * 수정 날짜
	 */
	private Date updateDate;

	/**
	 * 상태
	 */
	private String state;

	/**
	 * 프로세스 이름
	 */
	private String processName;

	/**
	 * 저장 날짜
	 */
	private Date saveDate;

	/**
	 * 디플로이 날짜
	 */
	private Date deployDate;

	/**
	 * 프로세스 설명
	 */
	private String description;
	
	/**
	 * 프로세스 상태
	 */
	private String processState;
	
	/**
	 * 파티션 아이디
	 */
	private String partitionId;
	
	/**
	 * 파티션 이름
	 */
	private String partitionName;

	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return the modelScript
	 */
	public String getModelScript() {
		return modelScript;
	}

	/**
	 * @param modelScript the modelScript to set
	 */
	public void setModelScript(String modelScript) {
		this.modelScript = modelScript;
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
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @return the processVer
	 */
	public String getProcessVer() {
		return processVer;
	}

	/**
	 * @param processVer the processVer to set
	 */
	public void setProcessVer(String processVer) {
		this.processVer = processVer;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the viewScript
	 */
	public String getViewScript() {
		return viewScript;
	}

	/**
	 * @param viewScript the viewScript to set
	 */
	public void setViewScript(String viewScript) {
		this.viewScript = viewScript;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the state
	 */
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
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @return the saveDate
	 */
	public Date getSaveDate() {
		return saveDate;
	}

	/**
	 * @param saveDate the saveDate to set
	 */
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	/**
	 * @return the deployDate
	 */
	public Date getDeployDate() {
		return deployDate;
	}

	/**
	 * @param deployDate the deployDate to set
	 */
	public void setDeployDate(Date deployDate) {
		this.deployDate = deployDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param deployDate the deployDate to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the processState
	 */
	public String getProcessState() {
		return processState;
	}

	/**
	 * @param processState the processState to set
	 */
	public void setProcessState(String processState) {
		this.processState = processState;
	}
	
	/**
	 * @return the processState
	 */
	public String getPartitionId() {
		return partitionId;
	}

	/**
	 * @param processState the processState to set
	 */
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}
	
	/**
	 * @return the processState
	 */
	public String getPartitionName() {
		return partitionName;
	}

	/**
	 * @param processState the processState to set
	 */
	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

}
