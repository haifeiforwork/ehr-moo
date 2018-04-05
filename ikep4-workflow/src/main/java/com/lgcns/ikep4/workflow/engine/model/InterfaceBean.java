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
public class InterfaceBean extends BaseObject {

	private String seqId = "";

	private String interfaceType = "";

	private String processId = "";
	
	private String activityId = "";
	
	private String userId = "";
	
	private String systemCode = "";
	
	private String title = "";
	
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
	
	private String screenUrl = "";
	
	private String parameter = "";

	private Date executeDate;
	
	private Date createDate;
	
	private String flag = "";

	private String resultMsg = "";

	/**
	 * @return the seqId
	 */
	public String getSeqId() {
		return seqId;
	}

	/**
	 * @return the interfaceType
	 */
	public String getInterfaceType() {
		return interfaceType;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
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
	 * @return the screenUrl
	 */
	public String getScreenUrl() {
		return screenUrl;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @return the executeDate
	 */
	public Date getExecuteDate() {
		return executeDate;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	/**
	 * @param interfaceType the interfaceType to set
	 */
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @param screenUrl the screenUrl to set
	 */
	public void setScreenUrl(String screenUrl) {
		this.screenUrl = screenUrl;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @param executeDate the executeDate to set
	 */
	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}


	
	
	


}