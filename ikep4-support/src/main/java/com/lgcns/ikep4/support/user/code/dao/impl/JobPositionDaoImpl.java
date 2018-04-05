/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.code.dao.JobPositionDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobPosition;


/**
 * 직위 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobPositionDaoImpl.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
@Repository("jobPositionDao")
public class JobPositionDaoImpl extends GenericDaoSqlmap<JobPosition, String> implements JobPositionDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobPositionDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobPosition> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.JobPosition.selectAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobPositionDao#selectCount(com.
	 * lgcns .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.JobPosition.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(JobPosition jobPosition) {

		return (String) sqlInsert("support.user.code.dao.JobPosition.insert", jobPosition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String jobPositionCode) {

		String count = (String) sqlSelectForObject("support.user.code.dao.JobPosition.checkCode", jobPositionCode);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public JobPosition get(String jobPositionCode) {

		return (JobPosition) sqlSelectForObject("support.user.code.dao.JobPosition.select", jobPositionCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(JobPosition jobPosition) {

		sqlUpdate("support.user.code.dao.JobPosition.update", jobPosition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String jobPositionCode) {

		sqlDelete("support.user.code.dao.JobPosition.delete", jobPositionCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobPositionDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.code.dao.JobPosition.getMaxSortOrder");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobPositionDao#goUp(java.util.Map)
	 */
	public JobPosition goUp(Map<String, String> map) {

		return (JobPosition) sqlSelectForObject("support.user.code.dao.JobPosition.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobPositionDao#goDown(java.util
	 * .Map)
	 */
	public JobPosition goDown(Map<String, String> map) {

		return (JobPosition) sqlSelectForObject("support.user.code.dao.JobPosition.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobPositionDao#updateSortOrder(com
	 * .lgcns.ikep4.support.user.code.model.JobPosition)
	 */
	public void updateSortOrder(JobPosition jobPosition) {

		sqlUpdate("support.user.code.dao.JobPosition.updateSortOrder", jobPosition);
	}

	public List<JobPosition> listJobPositionAll(String portalId) {
		return sqlSelectForList("support.user.code.dao.JobPosition.listJobPositionAll", portalId);
	}

}