/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 일정 참가자 (공개자, 참여자, 참조자)
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: Participant.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class Participant extends BaseObject {

	private static final long serialVersionUID = 7723180731739548611L;

	/**
	 * 대상 일정 아이디
	 */
	private String scheduleId;
	
	/**
	 * 일정 연락 대상자 ID
	 */
	private String targetUserId;
	
	/**
	 * 일정 연락 대상자 이름
	 */
	private String targetUserName;
	
	/**
	 * 일정 연락 대상자 영문이름
	 */
	private String targetUserEnglishName;
	
	/**
	 * 연락대상자 정보
	 */
	private String targetUserInfo;
	
	/**
	 * 연락대상자 영문 정보
	 */
	private String targetUserEnglishInfo;
	
	private String targetUserJobTitleName;
	private String targetUserJobTitleEnglishName;
	
	private String targetUserTeamName;
	private String targetUserTeamEnglishName;
	
	private String targetUserMobile;
	
	/**
	 * 메일계정
	 */
	private String mail;
	
	
	/**
	 * 일정 연락 대상 타입 (0 : 공개자, 1 : 참석자, 2 : 참조자)
	 */
	private String targetType;
	
	/**
	 * 일정 참여 여부 (0 : 미정, 1 : 참여, 2 : 불참)
	 * 
	 */
	private int isAccept = 0;
	
	/**
	 * 불참 사유
	 */
	private String abscentReason;

	public Participant() {}
	
	public Participant(String scheduleId, String targetId, String targetType) {
		this.scheduleId = scheduleId;
		this.targetUserId = targetId;
		this.targetType = targetType;
	}
	/**
	 * @return the targetType
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * @param targetType the targetType to set
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	/**
	 * @return the isAccept
	 */
	public int getIsAccept() {
		return isAccept;
	}

	/**
	 * @param isAccept the isAccept to set
	 */
	public void setIsAccept(int isAccept) {
		this.isAccept = isAccept;
	}

	/**
	 * @return the abscentReason
	 */
	public String getAbscentReason() {
		return abscentReason;
	}

	/**
	 * @param abscentReason the abscentReason to set
	 */
	public void setAbscentReason(String abscentReason) {
		this.abscentReason = abscentReason;
	}

	/**
	 * @return the scheduleId
	 */
	public String getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the targetUserId
	 */
	public String getTargetUserId() {
		return targetUserId;
	}

	/**
	 * @param targetUserId the targetUserId to set
	 */
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getTargetUserInfo() {
		return targetUserInfo;
	}

	public void setTargetUserInfo(String targetUserInfo) {
		this.targetUserInfo = targetUserInfo;
	}

	public String getTargetUserName() {
		return targetUserName;
	}

	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}

	public String getTargetUserEnglishName() {
		return targetUserEnglishName;
	}

	public void setTargetUserEnglishName(String targetUserEnglishName) {
		this.targetUserEnglishName = targetUserEnglishName;
	}

	public String getTargetUserEnglishInfo() {
		return targetUserEnglishInfo;
	}

	public void setTargetUserEnglishInfo(String targetUserEnglishInfo) {
		this.targetUserEnglishInfo = targetUserEnglishInfo;
	}
	
	public String getTargetUserJobTitleName() {
		return targetUserJobTitleName;
	}

	public void setTargetUserJobTitleName(String targetUserJobTitleName) {
		this.targetUserJobTitleName = targetUserJobTitleName;
	}

	public String getTargetUserJobTitleEnglishName() {
		return targetUserJobTitleEnglishName;
	}

	public void setTargetUserJobTitleEnglishName(String targetUserJobTitleEnglishName) {
		this.targetUserJobTitleEnglishName = targetUserJobTitleEnglishName;
	}

	public String getTargetUserTeamName() {
		return targetUserTeamName;
	}

	public void setTargetUserTeamName(String targetUserTeamName) {
		this.targetUserTeamName = targetUserTeamName;
	}

	public String getTargetUserTeamEnglishName() {
		return targetUserTeamEnglishName;
	}

	public void setTargetUserTeamEnglishName(String targetUserTeamEnglishName) {
		this.targetUserTeamEnglishName = targetUserTeamEnglishName;
	}

	public String getTargetUserMobile() {
		return targetUserMobile;
	}

	public void setTargetUserMobile(String targetUserMobile) {
		this.targetUserMobile = targetUserMobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
}
