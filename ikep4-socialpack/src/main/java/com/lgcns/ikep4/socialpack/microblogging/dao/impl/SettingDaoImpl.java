/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.microblogging.dao.SettingDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Setting;


/**
 * 
 * settingDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: SettingDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class SettingDaoImpl extends GenericDaoSqlmap<Setting, String> implements SettingDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.setting.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Setting setting) {
		return (String) sqlInsert(NAMESPACE + "insert", setting);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String id) {

		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", id);
		if (count > 0){
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Setting get(String id) {
		return (Setting) sqlSelectForObject(NAMESPACE + "select", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {
		sqlDelete(NAMESPACE + "delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Setting setting) {
		this.sqlUpdate(NAMESPACE + "update", setting); 
	}

}
