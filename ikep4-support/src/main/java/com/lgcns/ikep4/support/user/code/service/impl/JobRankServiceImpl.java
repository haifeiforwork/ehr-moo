/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.dao.JobRankDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobRank;
import com.lgcns.ikep4.support.user.code.service.JobRankService;


/**
 * 직급 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobRankServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("jobRankService")
@Transactional
public class JobRankServiceImpl extends GenericServiceImpl<JobRank, String> implements JobRankService {

	@Autowired
	private JobRankDao jobRankDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(JobRank jobRank) {

		String result = jobRankDao.create(jobRank);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return jobRankDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public JobRank read(String jobRankCode) {

		return jobRankDao.get(jobRankCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String jobRankCode) {

		jobRankDao.remove(jobRankCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(JobRank jobRank) {

		jobRankDao.update(jobRank);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.JobRankService#list(com
	 * .lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<JobRank> list(AdminSearchCondition searchCondition) {

		Integer count = jobRankDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<JobRank> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<JobRank>(searchCondition);

		} else {

			List<JobRank> jobRankList = jobRankDao.selectAll(searchCondition);
			searchResult = new SearchResult<JobRank>(jobRankList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobRankService#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return jobRankDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.JobRankService#goUp(java
	 * .util.Map)
	 */
	public void goUp(Map<String, String> map) {

		JobRank upJobRank = jobRankDao.goUp(map);
		String upSortOrder = upJobRank.getSortOrder();

		upJobRank.setSortOrder((String) map.get("sortOrder"));

		jobRankDao.updateSortOrder(upJobRank);

		JobRank jobRank = new JobRank();
		jobRank.setJobRankCode((String) map.get("jobRankCode"));
		jobRank.setSortOrder(upSortOrder);

		jobRankDao.updateSortOrder(jobRank);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.JobRankService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		JobRank downJobRank = jobRankDao.goDown(map);
		String downSortOrder = downJobRank.getSortOrder();

		downJobRank.setSortOrder((String) map.get("sortOrder"));

		jobRankDao.updateSortOrder(downJobRank);

		JobRank jobRank = new JobRank();
		jobRank.setJobRankCode((String) map.get("jobRankCode"));
		jobRank.setSortOrder(downSortOrder);

		jobRankDao.updateSortOrder(jobRank);
	}

}