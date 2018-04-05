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
 * 워크플로우 - 현황관리 - IKEP4_WF_ACTIVITY 
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminActivity.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminActivity extends BaseObject{
	
	private static final long serialVersionUID = 231767328317453351L;
	
	private String activityId;     //액티비티 ID
	private String activityName;   //액티비티 명
	private String startMode;      //
	private String subType;        //
	private String gatewayType;    //
	private String endMode;        //
	private String loopType;       //
	private String description;     //액티비티 설명
	private String subProcessId;  //서브 프로세스 ID
	private String activityType;   //액티비티 타입
	private String varDefine;      //
	private String createDate;     //생성일자
	private String attrExpand;     //
	private String updateDate;     //수정일자
	private String author;          //작성자
	private String processId;      //프로세스 ID
	private String processVer;     //프로세스 버전
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getStartMode() {
		return startMode;
	}
	public void setStartMode(String startMode) {
		this.startMode = startMode;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getGatewayType() {
		return gatewayType;
	}
	public void setGatewayType(String gatewayType) {
		this.gatewayType = gatewayType;
	}
	public String getEndMode() {
		return endMode;
	}
	public void setEndMode(String endMode) {
		this.endMode = endMode;
	}
	public String getLoopType() {
		return loopType;
	}
	public void setLoopType(String loopType) {
		this.loopType = loopType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubProcessId() {
		return subProcessId;
	}
	public void setSubProcessId(String subProcessId) {
		this.subProcessId = subProcessId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getVarDefine() {
		return varDefine;
	}
	public void setVarDefine(String varDefine) {
		this.varDefine = varDefine;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAttrExpand() {
		return attrExpand;
	}
	public void setAttrExpand(String attrExpand) {
		this.attrExpand = attrExpand;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
}
