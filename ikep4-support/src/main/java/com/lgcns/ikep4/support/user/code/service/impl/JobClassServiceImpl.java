/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.code.dao.JobClassDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobClass;
import com.lgcns.ikep4.support.user.code.service.JobClassService;


/**
 * 직급상태 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobClassServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("jobClassService")
@Transactional
public class JobClassServiceImpl extends GenericServiceImpl<JobClass, String> implements JobClassService {

	@Autowired
	private JobClassDao jobClassDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(JobClass jobClass) {

		String result = jobClassDao.create(jobClass);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return jobClassDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public JobClass read(String jobClassCode) {

		return jobClassDao.get(jobClassCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String jobClassCode) {

		jobClassDao.remove(jobClassCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(JobClass jobClass) {

		jobClassDao.update(jobClass);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobClassService#list(com.lgcns
	 * .ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<JobClass> list(AdminSearchCondition searchCondition) {

		Integer count = jobClassDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<JobClass> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<JobClass>(searchCondition);

		} else {

			List<JobClass> jobClassList = jobClassDao.selectAll(searchCondition);

			List<JobClass> returnList = new ArrayList<JobClass>();
			for (JobClass jobClass : jobClassList) {
				if (!searchCondition.getUserLocaleCode().equals("ko")) {
					jobClass.setJobClassName(jobClass.getJobClassEnglishName());
				}
				returnList.add(jobClass);
			}

			searchResult = new SearchResult<JobClass>(returnList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobClassService#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return jobClassDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobClassService#goUp(java.util
	 * .Map)
	 */
	public void goUp(Map<String, String> map) {

		JobClass upJobClass = jobClassDao.goUp(map);
		String upSortOrder = upJobClass.getSortOrder();

		upJobClass.setSortOrder((String) map.get("sortOrder"));

		jobClassDao.updateSortOrder(upJobClass);

		JobClass jobClass = new JobClass();
		jobClass.setJobClassCode((String) map.get("jobClassCode"));
		jobClass.setSortOrder(upSortOrder);

		jobClassDao.updateSortOrder(jobClass);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobClassService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		JobClass downJobClass = jobClassDao.goDown(map);
		String downSortOrder = downJobClass.getSortOrder();

		downJobClass.setSortOrder((String) map.get("sortOrder"));

		jobClassDao.updateSortOrder(downJobClass);

		JobClass jobClass = new JobClass();
		jobClass.setJobClassCode((String) map.get("jobClassCode"));
		jobClass.setSortOrder(downSortOrder);

		jobClassDao.updateSortOrder(jobClass);
	}

}
