/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageMonitor.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageMonitor extends BaseObject {

	private static final long serialVersionUID = -7427823205752028718L;

	/**
	  * 번호
	  */
	private Integer rnum;
	
	/**
	  * 사용자ID
	  */
	private String userID;
	
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
	  * 월별 사용자 발송 용량 
	  */	 
	private Integer useMonthFilesize;
	
	/**
	  * 보관함 사용자 용량
	  */
	private Integer useStoredFilesize;

	public Integer getRnum() {
		return rnum;
	}

	public void setRnum(Integer rnum) {
		this.rnum = rnum;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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

	public Integer getUseMonthFilesize() {
		return useMonthFilesize;
	}

	public void setUseMonthFilesize(Integer useMonthFilesize) {
		this.useMonthFilesize = useMonthFilesize;
	}

	public Integer getUseStoredFilesize() {
		return useStoredFilesize;
	}

	public void setUseStoredFilesize(Integer useStoredFilesize) {
		this.useStoredFilesize = useStoredFilesize;
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

}
