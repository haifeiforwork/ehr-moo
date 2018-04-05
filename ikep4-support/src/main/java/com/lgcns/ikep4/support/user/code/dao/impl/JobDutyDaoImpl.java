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
import com.lgcns.ikep4.support.user.code.dao.JobDutyDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobDuty;


/**
 * 직책 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobDutyDaoImpl.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
@Repository("jobDutyDao")
public class JobDutyDaoImpl extends GenericDaoSqlmap<JobDuty, String> implements JobDutyDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobDuty> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.JobDuty.selectAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobDuty> selectJobDutyForTree(String portalId) {

		return sqlSelectForList("support.user.code.dao.JobDuty.selectJobDutyForTree", portalId);
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#selectCount(com.
	 * lgcns .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.JobDuty.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(JobDuty jobDuty) {

		return (String) sqlInsert("support.user.code.dao.JobDuty.insert", jobDuty);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String jobDutyCode) {

		String count = (String) sqlSelectForObject("support.user.code.dao.JobDuty.checkCode", jobDutyCode);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public JobDuty get(String jobDutyCode) {

		return (JobDuty) sqlSelectForObject("support.user.code.dao.JobDuty.select", jobDutyCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(JobDuty jobDuty) {

		sqlUpdate("support.user.code.dao.JobDuty.update", jobDuty);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String jobDutyCode) {

		sqlDelete("support.user.code.dao.JobDuty.delete", jobDutyCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.code.dao.JobDuty.getMaxSortOrder");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#goUp(java.util.Map)
	 */
	public JobDuty goUp(Map<String, String> map) {

		return (JobDuty) sqlSelectForObject("support.user.code.dao.JobDuty.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#goDown(java.util
	 * .Map)
	 */
	public JobDuty goDown(Map<String, String> map) {

		return (JobDuty) sqlSelectForObject("support.user.code.dao.JobDuty.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobDutyDao#updateSortOrder(com
	 * .lgcns.ikep4.support.user.code.model.JobDuty)
	 */
	public void updateSortOrder(JobDuty jobDuty) {

		sqlUpdate("support.user.code.dao.JobDuty.updateSortOrder", jobDuty);
	}

	public List<JobDuty> listJobDutyAll(String portalId) {
		return sqlSelectForList("support.user.code.dao.JobDuty.listJobDutyAll", portalId);
	}

}