/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.role.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.role.dao.RoleTypeDao;
import com.lgcns.ikep4.support.user.role.model.RoleType;

/**
 * 역할타입 DAO Implement
 *
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: RoleTypeDaoImpl.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
@Repository("roleTypeDao")
public class RoleTypeDaoImpl extends GenericDaoSqlmap<RoleType, String> implements RoleTypeDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleTypeDao#selectAll(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<RoleType> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.role.dao.RoleType.selectAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.role.dao.RoleTypeDao#selectCount(com.lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.role.dao.RoleType.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(RoleType roleType) {

		return (String) sqlInsert("support.user.role.dao.RoleType.insert", roleType);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {

		String count = (String) sqlSelectForObject("support.user.role.dao.RoleType.checkId", id);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(RoleType roleType) {

		sqlUpdate("support.user.role.dao.RoleType.update", roleType);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {

		sqlDelete("support.user.role.dao.RoleType.delete", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public RoleType get(String id) {

		return (RoleType) sqlSelectForObject("support.user.role.dao.RoleType.select", id);
	}

	public List<RoleType> listRoleTypePortal(String portalId) {
		return sqlSelectForList("support.user.role.dao.RoleType.listRoleTypePortal", portalId);
	}
}
