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
/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: InstanceBean.java 16245 2011-08-18 04:28:59Z giljae $ InstanceBean.java 오전 9:41:07
 */
public class InstanceBean extends BaseObject {

	private String command = "";

	private String instanceId = "";

	private String processId = "";
	
	private String processVer = "";

	private String parentInsId = "";
	
	private String instanceLogId	= "";
	
	private String activityId	= "";

	private String title = "";

	private String performerId = "";

	private String performerOrg = "";

	private String state = "";

	private String result = "";
	
	private Date createDate;
	
	private Date updateDate;

	private Date dueDate;

	private Date finishedDate;
	
	private Date openDate;
	
	private String varSchema	= "";
	
	private String appKey01	= "";
	
	private String appKey02	= "";
	
	private String appKey03	= "";
	
	private String appKey04	= "";
	
	private String appKey05	= "";
	
	private String appKey06	= "";
	
	private String appKey07	= "";
	
	private String appKey08	= "";
	
	private String appKey09	= "";
	
	private String appKey10	= "";
	
	private String resultMsg = "";

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
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
	 * @return the parentInsId
	 */
	public String getParentInsId() {
		return parentInsId;
	}

	/**
	 * @return the instanceLogId
	 */
	public String getInstanceLogId() {
		return instanceLogId;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
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
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @return the finishedDate
	 */
	public Date getFinishedDate() {
		return finishedDate;
	}
	
	/**
	 * @return the openDate
	 */
	public Date getOpenDate() {
		return openDate;
	}


	/**
	 * @return the varSchema
	 */
	public String getVarSchema() {
		return varSchema;
	}

	/**
	 * @return the appKey01
	 */
	public String getAppKey01() {
		return appKey01;
	}

	/**
	 * @return the appKey02
	 */
	public String getAppKey02() {
		return appKey02;
	}

	/**
	 * @return the appKey03
	 */
	public String getAppKey03() {
		return appKey03;
	}

	/**
	 * @return the appKey04
	 */
	public String getAppKey04() {
		return appKey04;
	}

	/**
	 * @return the appKey05
	 */
	public String getAppKey05() {
		return appKey05;
	}

	/**
	 * @return the appKey06
	 */
	public String getAppKey06() {
		return appKey06;
	}

	/**
	 * @return the appKey07
	 */
	public String getAppKey07() {
		return appKey07;
	}

	/**
	 * @return the appKey08
	 */
	public String getAppKey08() {
		return appKey08;
	}

	/**
	 * @return the appKey09
	 */
	public String getAppKey09() {
		return appKey09;
	}

	/**
	 * @return the appKey10
	 */
	public String getAppKey10() {
		return appKey10;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
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
	 * @param parentInsId the parentInsId to set
	 */
	public void setParentInsId(String parentInsId) {
		this.parentInsId = parentInsId;
	}

	/**
	 * @param instanceLogId the instanceLogId to set
	 */
	public void setInstanceLogId(String instanceLogId) {
		this.instanceLogId = instanceLogId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
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
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @param finishedDate the finishedDate to set
	 */
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}

	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}	
	
	/**
	 * @param varSchema the varSchema to set
	 */
	public void setVarSchema(String varSchema) {
		this.varSchema = varSchema;
	}

	/**
	 * @param appKey01 the appKey01 to set
	 */
	public void setAppKey01(String appKey01) {
		this.appKey01 = appKey01;
	}

	/**
	 * @param appKey02 the appKey02 to set
	 */
	public void setAppKey02(String appKey02) {
		this.appKey02 = appKey02;
	}

	/**
	 * @param appKey03 the appKey03 to set
	 */
	public void setAppKey03(String appKey03) {
		this.appKey03 = appKey03;
	}

	/**
	 * @param appKey04 the appKey04 to set
	 */
	public void setAppKey04(String appKey04) {
		this.appKey04 = appKey04;
	}

	/**
	 * @param appKey05 the appKey05 to set
	 */
	public void setAppKey05(String appKey05) {
		this.appKey05 = appKey05;
	}

	/**
	 * @param appKey06 the appKey06 to set
	 */
	public void setAppKey06(String appKey06) {
		this.appKey06 = appKey06;
	}

	/**
	 * @param appKey07 the appKey07 to set
	 */
	public void setAppKey07(String appKey07) {
		this.appKey07 = appKey07;
	}

	/**
	 * @param appKey08 the appKey08 to set
	 */
	public void setAppKey08(String appKey08) {
		this.appKey08 = appKey08;
	}

	/**
	 * @param appKey09 the appKey09 to set
	 */
	public void setAppKey09(String appKey09) {
		this.appKey09 = appKey09;
	}

	/**
	 * @param appKey10 the appKey10 to set
	 */
	public void setAppKey10(String appKey10) {
		this.appKey10 = appKey10;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	
	
	
}