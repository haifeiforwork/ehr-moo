/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.userconfig.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.userconfig.dao.UserConfigDao;
import com.lgcns.ikep4.support.userconfig.model.UserConfig;

/**
 * 사용자 개인화 설정 DAO 구현 클래스
 * 
 * @author 최현식
 * @version $$Id: UserConfigDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $$
 */
@Repository
public class UserConfigDaoImpl  extends GenericDaoSqlmap<UserConfig, String> implements UserConfigDao {
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "support.userconfig.dao.UserConfig.";


	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public UserConfig get(String id) {
		UserConfig userConfig = (UserConfig)this.sqlSelectForObject(NAMESPACE + "get", id);
		return userConfig;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "exists", id);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(UserConfig userConfig) {
		this.sqlInsert(NAMESPACE + "create", userConfig);
		return userConfig.getId();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(UserConfig userConfig) {
		this.sqlUpdate(NAMESPACE + "update", userConfig);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		this.sqlDelete(NAMESPACE + "remove", id);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.dao.UserConfigDao#listByUserId(java.lang.String)
	 */
	public List<UserConfig> listByUserId(String userId) {
		@SuppressWarnings("unused")
		List<UserConfig> list = this.sqlSelectForList(NAMESPACE + "listByUserId", userId);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.dao.UserConfigDao#readByUserIdAndModuleId(com.lgcns.ikep4.support.userconfig.model.UserConfig)
	 */
	public UserConfig getByUserIdAndModuleId(UserConfig parameter) {
		@SuppressWarnings("unused")
		UserConfig userConfig = (UserConfig)this.sqlSelectForObject(NAMESPACE + "getByUserIdAndModuleId", parameter);
		return userConfig;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.dao.UserConfigDao#deleteUserConfigByUserIdAndModuleId(com.lgcns.ikep4.support.userconfig.model.UserConfig)
	 */
	public void removeUserConfigByUserIdAndModuleId(UserConfig parameter) {
		this.sqlDelete(NAMESPACE + "removeUserConfigByUserIdAndModuleId", parameter);

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.dao.UserConfigDao#existsByUserIdAndModuleId(com.lgcns.ikep4.support.userconfig.model.UserConfig)
	 */
	public Boolean existsByUserIdAndModuleId(UserConfig parameter) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "existsByUserIdAndModuleId", parameter);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.userconfig.dao.UserConfigDao#updateByUserIdAndModuleId(com.lgcns.ikep4.support.userconfig.model.UserConfig)
	 */
	public void updateByUserIdAndModuleId(UserConfig parameter) {
		this.sqlUpdate(NAMESPACE + "updateByUserIdAndModuleId", parameter);


	}


}
