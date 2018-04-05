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
 * @version $Id: ReadMessageUser.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class ReadMessageUser extends BaseObject {

	private static final long serialVersionUID = -3006401830310692860L;

	/**
	  * 사용자ID
	  */
	private String userId;
	
	/**
	  * 사용자명
	  */
	private String userName;
	
	/**
	  * 수신시간
	  */
	private Date readDate ;

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

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
}
