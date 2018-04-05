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
import com.lgcns.ikep4.support.user.code.dao.JobTitleDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobTitle;


/**
 * 호칭 코드 관련 Dao 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobTitleDaoImpl.java 19023 2012-05-31 06:36:51Z malboru80 $
 */
@Repository("jobTitleDao")
public class JobTitleDaoImpl extends GenericDaoSqlmap<JobTitle, String> implements JobTitleDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobTitleDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobTitle> selectAll(AdminSearchCondition searchCondition) {

		return sqlSelectForList("support.user.code.dao.JobTitle.selectAll", searchCondition);
	}


	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobTitleDao#selectAll(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobTitle> selectJobTitleForTree(String portalId) {

		return sqlSelectForList("support.user.code.dao.JobTitle.selectJobTitleForTree", portalId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobTitleDao#selectCount(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public Integer selectCount(AdminSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("support.user.code.dao.JobTitle.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(JobTitle jobTitle) {

		return (String) sqlInsert("support.user.code.dao.JobTitle.insert", jobTitle);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String jobTitleCode) {

		String count = (String) sqlSelectForObject("support.user.code.dao.JobTitle.checkCode", jobTitleCode);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public JobTitle get(String jobTitleCode) {

		return (JobTitle) sqlSelectForObject("support.user.code.dao.JobTitle.select", jobTitleCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(JobTitle jobTitle) {

		sqlUpdate("support.user.code.dao.JobTitle.update", jobTitle);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String jobTitleCode) {

		sqlDelete("support.user.code.dao.JobTitle.delete", jobTitleCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.dao.JobTitleDao#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("support.user.code.dao.JobTitle.getMaxSortOrder");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobTitleDao#goUp(java.util.Map)
	 */
	public JobTitle goUp(Map<String, String> map) {

		return (JobTitle) sqlSelectForObject("support.user.code.dao.JobTitle.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobTitleDao#goDown(java.util.Map)
	 */
	public JobTitle goDown(Map<String, String> map) {

		return (JobTitle) sqlSelectForObject("support.user.code.dao.JobTitle.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.dao.JobTitleDao#updateSortOrder(com
	 * .lgcns.ikep4.support.user.code.model.JobTitle)
	 */
	public void updateSortOrder(JobTitle jobTitle) {

		sqlUpdate("support.user.code.dao.JobTitle.updateSortOrder", jobTitle);
	}

	public List<JobTitle> listJobTitleAll(String portalId) {
		return sqlSelectForList("support.user.code.dao.JobTitle.listJobTitleAll", portalId);
	}
}
