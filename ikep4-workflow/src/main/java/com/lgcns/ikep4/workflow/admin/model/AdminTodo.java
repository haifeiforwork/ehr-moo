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
 * 워크플로우 - 현황관리 - 업무관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminTodo.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminTodo extends BaseObject{
	
	private static final long serialVersionUID = 231767328317453351L;
	
	private String title;       //제목        
	private String performerId; //
	private String orgId;       //조직 코드
	private String activityId;  //액티비티 ID
	private String createDate;  //생성일자
	private String assignDate;  //
	private String instanceId;  //인스턴스 ID
	private String selectDate;  //
	private String processId;   //프로세스 ID
	private String logId;       //로그 ID
	private String activityName;//액티비티 명
	private String dueDate;     //
	private String state;       //상태 
	private String processName; //프로세스 명 
	private String author;      //담당자
	private String finishedDate;//종료일자
	private String processVer;   //프로세스 버전
	
	public String getProcessVer() {
		return processVer;
	}
	public void setProcessVer(String processVer) {
		this.processVer = processVer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPerformerId() {
		return performerId;
	}
	public void setPerformerId(String performerId) {
		this.performerId = performerId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
}
