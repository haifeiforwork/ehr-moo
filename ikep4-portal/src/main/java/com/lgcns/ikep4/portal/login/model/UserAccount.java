/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.login.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 주길재
 * @version $Id: UserAccount.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class UserAccount extends BaseObject {
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;
	
	private String updatePasswordDate;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUpdatePasswordDate() {
		return updatePasswordDate;
	}

	public void setUpdatePasswordDate(String updatePasswordDate) {
		this.updatePasswordDate = updatePasswordDate;
	}

	
	
}
