/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util.user.member.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.util.user.member.dao.UsersDao;
import com.lgcns.ikep4.util.user.member.model.User;


/**
 * 사용자 관리 DAO 구현
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserDaoImpl.java 19178 2012-06-11 07:46:13Z malboru80 $
 */
@Repository("UsersDao")
public class UsersDaoImpl extends GenericDaoSqlmap<User, String> implements UsersDao {


	
	public User get(String id) {

		return (User) sqlSelectForObject("util.user.member.dao.Users.select", id);
	}

	public String create(User arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(User arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
