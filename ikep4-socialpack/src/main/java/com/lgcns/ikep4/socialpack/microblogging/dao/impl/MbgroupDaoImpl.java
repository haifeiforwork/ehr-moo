/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Mbgroup;


/**
 * 
 * MbgroupDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class MbgroupDaoImpl extends GenericDaoSqlmap<Mbgroup, String> implements MbgroupDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.mbgroup.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Mbgroup mbgroup) {
		return (String) sqlInsert(NAMESPACE + "insert", mbgroup);
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
	public Mbgroup get(String id) {
		return (Mbgroup) sqlSelectForObject(NAMESPACE + "select", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {
		sqlUpdate(NAMESPACE + "delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Mbgroup mbgroup) {
		this.sqlUpdate(NAMESPACE + "update", mbgroup); 
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupDao#selectMyGroupList(java.lang.String)
	 */
	public List<Mbgroup> selectMyGroupList(String id) {
		return sqlSelectForList(NAMESPACE + "selectMyGroupList", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.MbgroupDao#selectBothGroup(java.util.Map)
	 */
	public List<Mbgroup> selectBothGroup(Map map) {
		return sqlSelectForList(NAMESPACE + "selectBothGroup", map);
	}

}
