/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 접수 담당자 관리
 *
 * @author jeehye
 * @version $Id: $
 */
public class ApprReception extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 포탈ID
	 */
	private String portalId;
	
	/**
	 * 접수 담당자 ID
	 */
	private String receptionistId;
	
	/**
	 * 부서 ID
	 */
	private String groupId;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date registDate;
	
	/**
	 * 접수 담당자 리스트
	 */
	private String selectedGroupUserList;
	
	
	/**
	 * 접수자 아이디
	 */
	private String userId;
	
	/**
	 * 접수자 이름
	 */
	private String userName;
	
	/**
	 * 접수자 영문명
	 */
	private String userEnglishName;
	
	/**
	 * 접수자 직급명
	 */
	private String jobTitleName;
	
	/**
	 * 접수자 직급 영문명
	 */
	private String jobTitleEnglishName;
	

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getReceptionistId() {
		return receptionistId;
	}

	public void setReceptionistId(String receptionistId) {
		this.receptionistId = receptionistId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getSelectedGroupUserList() {
		return selectedGroupUserList;
	}

	public void setSelectedGroupUserList(String selectedGroupUserList) {
		this.selectedGroupUserList = selectedGroupUserList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}
	
}
