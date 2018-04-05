/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageSpecialUser.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageSpecialUser extends BaseObject {

	private static final long serialVersionUID = 5646631137686803461L;

	/**
	  * 사용자ID
	  */
	private String userId;
	
	/**
	  * 사용자명
	  */
	private String userName;
	/**
	  * 사용자영문명
	  */
	private String userEnglishName;
	
	/**
	  * 팀명
	  */
	private String teamName;
	/**
	  * 팀영문명
	  */
	private String teamEnglishName;
	
	/**
	  * 직위
	  */	 
	private String jobPositionName;
	/**
	  * 직위 영문
	  */	 
	private String jobPositionEnglishName;
	
	/**
	  * 월별 발송량
	  */
	private Integer maxMonthFilesize;
	
	/**
	  * 등록일자
	  */
	private Date registDate;

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

	public String getJobPositionName() {
		return jobPositionName;
	}

	public void setJobPositionName(String jobPositionName) {
		this.jobPositionName = jobPositionName;
	}

	public Integer getMaxMonthFilesize() {
		return maxMonthFilesize;
	}

	public void setMaxMonthFilesize(Integer maxMonthFilesize) {
		this.maxMonthFilesize = maxMonthFilesize;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getJobPositionEnglishName() {
		return jobPositionEnglishName;
	}

	public void setJobPositionEnglishName(String jobPositionEnglishName) {
		this.jobPositionEnglishName = jobPositionEnglishName;
	}



}
