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
 * 워크플로우 - 현황관리 - IKEP4_WF_INSTANCE
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminInstance.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminInstance extends BaseObject{
	
	private static final long serialVersionUID = 231767328317453351L;
	
	private String instanceId;   //인스턴스 ID
	private String parentInsId;  //부모인스턴스 ID
	private String createDate;   //생성일자
	private String title;        //제목
	private String finishedDate; //종료일자
	private String performerId;  //
	private String state;        //인스턴스 상태(진행중:RUNNING,완료:COMPLETE,중지:SUSPEND,오류:FAULT)
	private String performerOrg; //
	private String result;       //
	private String dueDate;      //
	private String processId;    //프로세스 ID
	private String processVer;   //프로세스 버전
	private String partitionName;//파티션 명
	private String processName;  //프로세스 명
	private String partitionId;  //파티션ID
	
	
	public String getPartitionId() {
		return partitionId;
	}
	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getParentInsId() {
		return parentInsId;
	}
	public void setParentInsId(String parentInsId) {
		this.parentInsId = parentInsId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
	public String getPerformerId() {
		return performerId;
	}
	public void setPerformerId(String performerId) {
		this.performerId = performerId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPerformerOrg() {
		return performerOrg;
	}
	public void setPerformerOrg(String performerOrg) {
		this.performerOrg = performerOrg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProcessVer() {
		return processVer;
	}
	public void setProcessVer(String processVer) {
		this.processVer = processVer;
	}
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
}
