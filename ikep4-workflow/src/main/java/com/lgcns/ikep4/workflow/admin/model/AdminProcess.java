/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminProcess.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminProcess  extends BaseObject{

	private static final long serialVersionUID = -8700580182383336495L;
	
	private String processName;    //프로세스 명      
	private String processVer;     //프로세스 버전     
	private String processType;    //프로세스 타입     
	private String processState;   //프로세스 상태     
	private String vendor;         //     
	private String author;         //     
	private String startMode;      //     
	private String varDefine;      //     
	private String description;    //프로세스 설명     
	private String attrExpand;     //     
	private String limitStartTime; //  
	private String limitEndTime;   // 
	private String createDate;     //생성일자  
	private String updateDate;     //수정일자   
	private String isDelete;       //  
	private String processId;      //프로세스 ID  
	private String partitionId;    //파티션 ID
	private String partitionName;  //파티션 명
	private String deployDate;     //배치 시간
	private String running;        //진행중인 건수
	private String complete;       //완료중인 건수
	
	public String getPartitionName() {
		return partitionName;
	}
	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessVer() {
		return processVer;
	}
	public void setProcessVer(String processVer) {
		this.processVer = processVer;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getProcessState() {
		return processState;
	}
	public void setProcessState(String processState) {
		this.processState = processState;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getStartMode() {
		return startMode;
	}
	public void setStartMode(String startMode) {
		this.startMode = startMode;
	}
	public String getVarDefine() {
		return varDefine;
	}
	public void setVarDefine(String varDefine) {
		this.varDefine = varDefine;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAttrExpand() {
		return attrExpand;
	}
	public void setAttrExpand(String attrExpand) {
		this.attrExpand = attrExpand;
	}
	public String getLimitStartTime() {
		return limitStartTime;
	}
	public void setLimitStartTime(String limitStartTime) {
		this.limitStartTime = limitStartTime;
	}
	public String getLimitEndTime() {
		return limitEndTime;
	}
	public void setLimitEndTime(String limitEndTime) {
		this.limitEndTime = limitEndTime;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getPartitionId() {
		return partitionId;
	}
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}
	public String getDeployDate() {
		return deployDate;
	}
	public void setDeployDate(String deployDate) {
		this.deployDate = deployDate;
	}
	public String getRunning() {
		return running;
	}
	public void setRunning(String running) {
		this.running = running;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
}

