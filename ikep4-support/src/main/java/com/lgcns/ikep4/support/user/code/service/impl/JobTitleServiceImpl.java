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
import com.lgcns.ikep4.support.user.code.dao.JobTitleDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobTitle;
import com.lgcns.ikep4.support.user.code.service.JobTitleService;


/**
 * 호칭 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobTitleServiceImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Service("jobTitleService")
@Transactional
public class JobTitleServiceImpl extends GenericServiceImpl<JobTitle, String> implements JobTitleService {

	@Autowired
	private JobTitleDao jobTitleDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(JobTitle jobTitle) {

		String result = jobTitleDao.create(jobTitle);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return jobTitleDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public JobTitle read(String jobTitleCode) {

		return jobTitleDao.get(jobTitleCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String jobTitleCode) {

		jobTitleDao.remove(jobTitleCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(JobTitle jobTitle) {

		jobTitleDao.update(jobTitle);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobTitleService#list(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<JobTitle> list(AdminSearchCondition searchCondition) {

		Integer count = jobTitleDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<JobTitle> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<JobTitle>(searchCondition);

		} else {

			List<JobTitle> jobTitleList = jobTitleDao.selectAll(searchCondition);
			searchResult = new SearchResult<JobTitle>(jobTitleList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobTitleService#list(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobTitle> selectJobTitleList(String portalId) {

		List<JobTitle> jobTitleList = jobTitleDao.selectJobTitleForTree(portalId);
				
		return jobTitleList;
	}
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobTitleService#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return jobTitleDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobTitleService#goUp(java.util
	 * .Map)
	 */
	public void goUp(Map<String, String> map) {

		JobTitle upJobTitle = jobTitleDao.goUp(map);
		String upSortOrder = upJobTitle.getSortOrder();

		upJobTitle.setSortOrder((String) map.get("sortOrder"));

		jobTitleDao.updateSortOrder(upJobTitle);

		JobTitle jobTitle = new JobTitle();
		jobTitle.setJobTitleCode((String) map.get("jobTitleCode"));
		jobTitle.setSortOrder(upSortOrder);

		jobTitleDao.updateSortOrder(jobTitle);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobTitleService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		JobTitle downJobTitle = jobTitleDao.goDown(map);
		String downSortOrder = downJobTitle.getSortOrder();

		downJobTitle.setSortOrder((String) map.get("sortOrder"));

		jobTitleDao.updateSortOrder(downJobTitle);

		JobTitle jobTitle = new JobTitle();
		jobTitle.setJobTitleCode((String) map.get("jobTitleCode"));
		jobTitle.setSortOrder(downSortOrder);

		jobTitleDao.updateSortOrder(jobTitle);
	}

}
