/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.login.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.login.dao.LoginUserDetailsDao;
import com.lgcns.ikep4.portal.login.model.UserAccount;


/**
 * Login User Details Dao 구현체
 * 
 * @author 주길재
 * @version $Id: LoginUserDetailsDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("loginUserDetailsDao")
public class LoginUserDetailsDaoImpl extends GenericDaoSqlmap<UserAccount, String> implements LoginUserDetailsDao {
	// 상수 정의
	interface Constants {
		final String NAMESPACE = "portal.login.dao.UserAccount.";
	}

	public UserAccount loadUserByUsername(String username, String portalId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("portalId", portalId);

		UserAccount userAccount = (UserAccount) sqlSelectForObject(Constants.NAMESPACE + "getUserByUsername", map);

		return userAccount;
	}

	public void changePassword(String username, String newPassword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("newPassword", newPassword);

		sqlUpdate("IkepUserDetailsManagerDao.changePassword", map);
	}


	public String create(UserAccount object) {
		// TODO Auto-generated method stub
		return (String) sqlInsert(Constants.NAMESPACE + "insert", object);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	@Deprecated
	public boolean exists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public UserAccount get(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String username) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(UserAccount object) {
		// TODO Auto-generated method stub

	}
	
	public UserAccount loadUserByUsernameMobile(String username) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", username);

		UserAccount userAccount = (UserAccount) sqlSelectForObject(Constants.NAMESPACE + "getUserByUsernameMobile", map);

		return userAccount;
	}
}
