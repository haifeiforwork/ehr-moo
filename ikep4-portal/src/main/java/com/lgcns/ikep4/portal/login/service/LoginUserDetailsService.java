/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.login.service;

import com.lgcns.ikep4.portal.login.model.UserAccount;


/**
 * Login User Detail Service Interface
 * 
 * @author 주길재
 * @version $Id: LoginUserDetailsService.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface LoginUserDetailsService {
	public UserAccount loadUserByUsername(String username, String portalId);

	public void changePassword(String oldPassword, String newPassword);
	
	public void catchPassword(String username, String password, String encryptPassword);
	
	public UserAccount loadUserByUsernameMobile(String username);

}
