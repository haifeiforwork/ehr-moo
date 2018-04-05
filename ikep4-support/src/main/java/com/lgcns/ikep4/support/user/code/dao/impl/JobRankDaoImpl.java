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
import com.lgcns.ikep4.support.user.code.dao.JobRankDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobRank;


/**
 * 직급 코드 관련 Dao 구현
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobRankDaoImpl.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
@Repository("jobRankDao")
public class JobRankDaoImpl extends GenericDaoSqlmap<JobRank, String> implements JobRankDao {

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobRankDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobRank> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.JobRank.selectAll", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobRankDao#selectCount(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.JobRank.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(JobRank jobRank) {

		return (String) sqlInsert("support.user.code.dao.JobRank.insert", jobRank);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String jobRankCode) {

		String count = (String) sqlSelectForObject("support.user.code.dao.JobRank.checkCode", jobRankCode);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public JobRank get(String jobRankCode) {

		return (JobRank) sqlSelectForObject("support.user.code.dao.JobRank.select", jobRankCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(JobRank jobRank) {

		sqlUpdate("support.user.code.dao.JobRank.update", jobRank);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String jobRankCode) {

		sqlDelete("support.user.code.dao.JobRank.delete", jobRankCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobRankDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.code.dao.JobRank.getMaxSortOrder");
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobRankDao#goUp(java.util.Map)
	 */
	public JobRank goUp(Map<String, String> map) {

		return (JobRank) sqlSelectForObject("support.user.code.dao.JobRank.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobRankDao#goDown(java.util.Map)
	 */
	public JobRank goDown(Map<String, String> map) {

		return (JobRank) sqlSelectForObject("support.user.code.dao.JobRank.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobRankDao#updateSortOrder(com
	 * .lgcns.ikep4.support.user.code.model.JobRank)
	 */
	public void updateSortOrder(JobRank jobRank) {

		sqlUpdate("support.user.code.dao.JobRank.updateSortOrder", jobRank);
	}

	public List<JobRank> listJobRankAll(String portalId) {
		return sqlSelectForList("support.user.code.dao.JobRank.listJobRankAll", portalId);
	}

}
