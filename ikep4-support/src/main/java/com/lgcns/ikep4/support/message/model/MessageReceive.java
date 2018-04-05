/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.message.util.MessageUtil;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageReceive.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class MessageReceive extends BaseObject {

	private static final long serialVersionUID = 5883870647966045581L;

	/**
	  * 번호
	  */
	private Integer rnum;
	
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
	  * 등록일자
	  */
	private Date readDate;
	
	/**
	  * 전체 건수
	  */
	private Integer recodeCount;

	public Integer getRnum() {
		return rnum;
	}

	public void setRnum(Integer rnum) {
		this.rnum = rnum;
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

	@JsonSerialize(using=MessageUtil.class)
	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public Integer getRecodeCount() {
		return recodeCount;
	}

	public void setRecodeCount(Integer recodeCount) {
		this.recodeCount = recodeCount;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}


}
