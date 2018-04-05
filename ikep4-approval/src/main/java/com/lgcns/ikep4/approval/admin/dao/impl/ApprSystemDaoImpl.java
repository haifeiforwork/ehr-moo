/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.admin.dao.ApprSystemDao;
import com.lgcns.ikep4.approval.admin.model.ApprSystem;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;



/**
 * 결재 시스템 관련 Dao 구현
 * 
 * @author 
 * @version $Id: ApprSystemDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository("apprSystemDao")
public class ApprSystemDaoImpl extends GenericDaoSqlmap<ApprSystem, String> implements ApprSystemDao {

	private static final String NAMESPACE = "approval.admin.dao.ApprSystem.";
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<ApprSystem> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList(NAMESPACE + "selectAll", searchCondition);
	}
	public List<ApprSystem> selectList(String portalId) {

		return sqlSelectForList(NAMESPACE + "selectList", portalId);
	}
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#selectCount(com.
	 * lgcns .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject(NAMESPACE + "selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ApprSystem system) {

		return (String) sqlInsert(NAMESPACE + "insert", system);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String systemId) {

		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "checkSystemId", systemId);

		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApprSystem get(String systemId) {

		return (ApprSystem) sqlSelectForObject(NAMESPACE + "select", systemId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ApprSystem system) {

		sqlUpdate(NAMESPACE + "update", system);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String systemId) {

		sqlDelete(NAMESPACE + "delete", systemId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#getMaxSortOrder()
	 */
	public int getMaxSystemOrder(String portalId) {

		return (Integer) sqlSelectForObject(NAMESPACE + "getMaxSystemOrder",portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#goUp(java.util.Map)
	 */
	public ApprSystem goUp(Map<String, String> map) {

		return (ApprSystem) sqlSelectForObject(NAMESPACE + "selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#goDown(java.util
	 * .Map)
	 */
	public ApprSystem goDown(Map<String, String> map) {

		return (ApprSystem) sqlSelectForObject(NAMESPACE + "selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#updateSortOrder(com
	 * .lgcns.ikep4.support.user.code.model.JobDuty)
	 */
	public void updateSystemOrder(ApprSystem system) {

		sqlUpdate(NAMESPACE + "updateSystemOrder", system);
	}

}