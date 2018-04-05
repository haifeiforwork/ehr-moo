/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.approval.admin.model.Code;

/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 서지혜
 * @version $Id$
 */
 
public class ApprReadGroup extends BaseObject {

	
	private static final long serialVersionUID = 8900711193377442735L;

	/**
	 * portal ID
	 */
	private String portalId;

	/**
	 * 사용자ID
	 */
	private String userId;
	
	/**
	 * 사용자명
	 */
	private String userName;
	
	/**
	 * 부서ID
	 */
	private String 	groupId;
	
	/**
	 * 부서명
	 */
	private String 	groupName;
	
	/**
	 * 사용자 부서명
	 */
	private String 	teamName;
	
	/**
	 * 사용자 직급
	 */
	private String 	jobTitleName;
	
	/**
	 * 부서목록
	 */
	private String 	groupSet;
	
	/**
	 * 등록자 ID
	 */
	private String 	registerId;
	
	/**
	 * 등록자명
	 */
	private String 	registerName;
	
	
	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date registDate;

	private String delFlag;

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> PAGE_NUM_LIST = Arrays.asList(		
			new Code<Integer>(10, "10"),
			new Code<Integer>(15, "15"),
			new Code<Integer>(20, "20"),
			new Code<Integer>(30, "30"),
			new Code<Integer>(40, "40"),
			new Code<Integer>(50, "50")
	);
	
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


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	
	public List<Code<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}

	public String getGroupSet() {
		return groupSet;
	}


	public void setGroupSet(String groupSet) {
		this.groupSet = groupSet;
	}


	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public String getJobTitleName() {
		return jobTitleName;
	}


	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}


	public String getPortalId() {
		return portalId;
	}


	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}
