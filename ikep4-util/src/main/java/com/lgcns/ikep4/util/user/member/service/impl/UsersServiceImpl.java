/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util.user.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.user.member.dao.UsersDao;
import com.lgcns.ikep4.util.user.member.model.User;
import com.lgcns.ikep4.util.user.member.service.UsersService;


/**
 * TODO Javadoc주석작성
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserServiceImpl.java 19178 2012-06-11 07:46:13Z malboru80 $
 */
@Service("usersService")
@Transactional
public class UsersServiceImpl extends GenericServiceImpl<User, String> implements UsersService {

	@Autowired
	private UsersDao userDao;
	
	
	public User read(String id) {

		return userDao.get(id);
	}

	
}
