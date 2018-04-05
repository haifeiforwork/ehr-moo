/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.todo.dao.UserSettingDao;
import com.lgcns.ikep4.lightpack.todo.model.UserSetting;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: UserSettingDaoImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Repository
public class UserSettingDaoImpl extends GenericDaoSqlmap<UserSetting, String> implements UserSettingDao {
	private static final String NAMESPACE = "lightpack.todo.dao.userSetting.";
	
	public String create(UserSetting userSetting) {
		 sqlInsert(NAMESPACE + "create", userSetting);
		 return userSetting.getUserId();
	}

	public boolean exists(String userId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", userId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public UserSetting get(String userId) {
		return (UserSetting) sqlSelectForObject(NAMESPACE + "get", userId);
	}

	public void remove(String userId) {
		sqlDelete(NAMESPACE + "remove", userId);
	}

	public void update(UserSetting userSetting) {
		sqlUpdate(NAMESPACE + "update", userSetting);
	}
}

