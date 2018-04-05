/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.front.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.portal.front.dao.UserCountDao;
import com.lgcns.ikep4.portal.front.service.UserCountService;


/**
 * User Count Service
 * 
 * @author 조경식
 * @version $Id: UserCountServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service(value = "userCountService")
public class UserCountServiceImpl implements UserCountService {
	@Autowired
	UserCountDao userCountDao;
	
	public String userCount() {
		return userCountDao.userCount();
	}
	
}
