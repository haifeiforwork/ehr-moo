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
import com.lgcns.ikep4.support.user.code.dao.JobPositionDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobPosition;
import com.lgcns.ikep4.support.user.code.service.JobPositionService;


/**
 * 직위 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobPositionServiceImpl.java 16276 2011-08-18 07:09:07Z giljae $
 */
@Service("jobPositionService")
@Transactional
public class JobPositionServiceImpl extends GenericServiceImpl<JobPosition, String> implements JobPositionService {

	@Autowired
	private JobPositionDao jobPositionDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(JobPosition jobPosition) {

		String result = jobPositionDao.create(jobPosition);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return jobPositionDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public JobPosition read(String jobPositionCode) {

		return jobPositionDao.get(jobPositionCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String jobPositionCode) {

		jobPositionDao.remove(jobPositionCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(JobPosition jobPosition) {

		jobPositionDao.update(jobPosition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobPositionService#list(com
	 * .lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<JobPosition> list(AdminSearchCondition searchCondition) {

		Integer count = jobPositionDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<JobPosition> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<JobPosition>(searchCondition);

		} else {

			List<JobPosition> jobPositionList = jobPositionDao.selectAll(searchCondition);
			searchResult = new SearchResult<JobPosition>(jobPositionList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobPositionService#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return jobPositionDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobPositionService#goUp(java
	 * .util.Map)
	 */
	public void goUp(Map<String, String> map) {

		JobPosition upJobPosition = jobPositionDao.goUp(map);
		String upSortOrder = upJobPosition.getSortOrder();

		upJobPosition.setSortOrder((String) map.get("sortOrder"));

		jobPositionDao.updateSortOrder(upJobPosition);

		JobPosition jobPosition = new JobPosition();
		jobPosition.setJobPositionCode((String) map.get("jobPositionCode"));
		jobPosition.setSortOrder(upSortOrder);

		jobPositionDao.updateSortOrder(jobPosition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobPositionService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		JobPosition downJobPosition = jobPositionDao.goDown(map);
		String downSortOrder = downJobPosition.getSortOrder();

		downJobPosition.setSortOrder((String) map.get("sortOrder"));

		jobPositionDao.updateSortOrder(downJobPosition);

		JobPosition jobPosition = new JobPosition();
		jobPosition.setJobPositionCode((String) map.get("jobPositionCode"));
		jobPosition.setSortOrder(downSortOrder);

		jobPositionDao.updateSortOrder(jobPosition);
	}

}