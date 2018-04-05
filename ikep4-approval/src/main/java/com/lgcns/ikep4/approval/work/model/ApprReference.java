/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.model;


import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 참조자 의견
 *
 * @author jeehye(jjang2g79@naver.com)
 * @version $Id: ApprEntrust.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApprReference extends BaseObject {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 결재문서 ID
	 */
	private String apprId;
	
	/**
	 * 참조자 ID
	 */
	private String userId;
	
	private	String userName;
	
	private	String userEnglishName;
	
	private String jobTitleName;
	/**
	 * 참조자 부서 ID
	 */
	private String groupId;
	
	private	String groupName;
	
	private	String groupEnglishName;
	
	/**
	 * 참조자 의견
	 */
	@Size(min=0, max=60)
	private String receiveMessage;
	
	/**
	 * 조회일시
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date viewDate;
	
 
	/**
	 * 메시지 작성일시
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date updateDate;


	/**
	 * @return the apprId
	 */
	public String getApprId() {
		return apprId;
	}


	/**
	 * @param apprId the apprId to set
	 */
	public void setApprId(String apprId) {
		this.apprId = apprId;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}


	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	/**
	 * @return the receiveMessage
	 */
	public String getReceiveMessage() {
		return receiveMessage;
	}


	/**
	 * @param receiveMessage the receiveMessage to set
	 */
	public void setReceiveMessage(String receiveMessage) {
		this.receiveMessage = receiveMessage;
	}


	/**
	 * @return the viewDate
	 */
	public Date getViewDate() {
		return viewDate;
	}


	/**
	 * @param viewDate the viewDate to set
	 */
	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}


	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	/**
	 * @return the groupEnglishName
	 */
	public String getGroupEnglishName() {
		return groupEnglishName;
	}


	/**
	 * @param groupEnglishName the groupEnglishName to set
	 */
	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}


	/**
	 * @return the jobTitleName
	 */
	public String getJobTitleName() {
		return jobTitleName;
	}


	/**
	 * @param jobTitleName the jobTitleName to set
	 */
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}


}
