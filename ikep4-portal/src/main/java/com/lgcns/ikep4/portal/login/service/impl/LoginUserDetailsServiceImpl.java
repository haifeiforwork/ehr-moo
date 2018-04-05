/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.portal.login.dao.LoginUserDetailsDao;
import com.lgcns.ikep4.portal.login.model.UserAccount;
import com.lgcns.ikep4.portal.login.service.LoginUserDetailsService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Login User Detail Service
 * 
 * @author 주길재
 * @version $Id: LoginUserDetailsServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service(value = "loginUserDetailsService")
public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {
	@Autowired
	LoginUserDetailsDao loginUserDetailsDao;
	
	public UserAccount loadUserByUsername(String username, String portalId) {
		return loginUserDetailsDao.loadUserByUsername(username, portalId);
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		//Get the username
		User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		loginUserDetailsDao.changePassword(user.getUserId(), newPassword);
		
		// 세션에서 제거
	}
	
	public void catchPassword(String username, String password, String encryptPassword){
		UserAccount userAccount= new UserAccount();
		userAccount.setPassword(password);
		userAccount.setUpdatePasswordDate(encryptPassword);
		userAccount.setUsername(username);
		loginUserDetailsDao.create(userAccount);
	}
	
	public UserAccount loadUserByUsernameMobile(String username) {
		return loginUserDetailsDao.loadUserByUsernameMobile(username);
	}
}
