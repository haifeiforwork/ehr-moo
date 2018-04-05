/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.poll.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서혜숙
 * @version $Id: Target.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Target extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4384194596792625462L;

	/**
	 * 온라인 투표 ID
	 */
	@NotEmpty
	private String pollId;

	/**
	 * 대상자 ID
	 */
	@NotEmpty
	private String targetId;
	
	/**
	 * 대상자 구분
	 */
	@NotEmpty
	private String targetType;	

	/**
	 * 등록자 ID
	 */
	@NotEmpty
	private String registerId;		

	/**
	 * 등록자 이름
	 */
	@NotEmpty
	private String registerName;	

	/**
	 * 등록일시
	 */
	@NotEmpty
	private Date registDate;

	/**
	 * 사용자 ID
	 */
	private String userId;

	/**
	 * 사용자의 이름
	 */
	private String userName;
	
	/**
	 * 사용자의 영문 이름
	 */	
	private String userEnglishName;
	
	/**
	 * 사용자 직급 영문명
	 */		
	private String jobTitleEnglishName;

	/**
	 * 사용자 부서(그룹) 이름
	 */
	private String teamName;
	
	/**
	 * 사용자 부서(그룹) 영문명
	 */	
	private String teamEnglishName;

	/**
	 * 사용자의 이메일 주소
	 */
	private String mail;

	/**
	 * 사용자 직급명
	 */
	private String jobTitleName;

	/**
	 * 그룹아이디
	 */	
	private String groupId;

	/**
	 * 그룹명
	 */		
	private String groupName;
	
	/**
	 * 그룹영문명
	 */		
	private String groupEnglishName;

	/**
	 * 그룹타입명
	 */		
	private String groupTypeName;
	
	/**
	 * 그룹타입 영문명
	 */		
	private String groupTypeEnglishName;
	
	/**
	 * @return the registerId
	 */		
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */		
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}	
	
	/**
	 * @return the registerName
	 */		
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */		
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */		
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */		
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}	
	
	public String getPollId() {
		return pollId;
	}

	public void setPollId(String pollId) {
		this.pollId = pollId;
	}
	
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupTypeName() {
		return groupTypeName;
	}

	public void setGroupTypeName(String groupTypeName) {
		this.groupTypeName = groupTypeName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}	
	
	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	public String getGroupTypeEnglishName() {
		return groupTypeEnglishName;
	}

	public void setGroupTypeEnglishName(String groupTypeEnglishName) {
		this.groupTypeEnglishName = groupTypeEnglishName;
	}	

	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}	
}
