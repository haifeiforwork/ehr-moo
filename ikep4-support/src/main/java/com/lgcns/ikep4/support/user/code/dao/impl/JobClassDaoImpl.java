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
import com.lgcns.ikep4.support.user.code.dao.JobClassDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobClass;


/**
 * 직급상태 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobClassDaoImpl.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
@Repository("jobClassDao")
public class JobClassDaoImpl extends GenericDaoSqlmap<JobClass, String> implements JobClassDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobClassDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobClass> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.JobClass.selectAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobClassDao#selectCount(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.JobClass.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(JobClass jobClass) {

		return (String) sqlInsert("support.user.code.dao.JobClass.insert", jobClass);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String jobClassCode) {

		String count = (String) sqlSelectForObject("support.user.code.dao.JobClass.checkCode", jobClassCode);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public JobClass get(String jobClassCode) {

		return (JobClass) sqlSelectForObject("support.user.code.dao.JobClass.select", jobClassCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(JobClass jobClass) {

		sqlUpdate("support.user.code.dao.JobClass.update", jobClass);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String jobClassCode) {

		sqlDelete("support.user.code.dao.JobClass.delete", jobClassCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobClassDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.code.dao.JobClass.getMaxSortOrder");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobClassDao#goUp(java.util.Map)
	 */
	public JobClass goUp(Map<String, String> map) {

		return (JobClass) sqlSelectForObject("support.user.code.dao.JobClass.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobClassDao#goDown(java.util.Map)
	 */
	public JobClass goDown(Map<String, String> map) {

		return (JobClass) sqlSelectForObject("support.user.code.dao.JobClass.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobClassDao#updateSortOrder(com
	 * .lgcns.ikep4.support.user.code.model.JobClass)
	 */
	public void updateSortOrder(JobClass jobClass) {

		sqlUpdate("support.user.code.dao.JobClass.updateSortOrder", jobClass);
	}

	public List<JobClass> listJobClassAll(String portalId) {
		return sqlSelectForList("support.user.code.dao.JobClass.listJobClassAll", portalId);
	}

}
