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
import com.lgcns.ikep4.support.user.code.dao.JobDutyDao;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.support.user.code.model.JobDuty;
import com.lgcns.ikep4.support.user.code.model.JobTitle;
import com.lgcns.ikep4.support.user.code.service.JobDutyService;
import com.lgcns.ikep4.support.user.code.service.LocaleCodeService;


/**
 * 직책 코드 관리 서비스 구현체
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: JobDutyServiceImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Service("jobDutyService")
@Transactional
public class JobDutyServiceImpl extends GenericServiceImpl<JobDuty, String> implements JobDutyService {

	@Autowired
	private JobDutyDao jobDutyDao;
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(JobDuty jobDuty) {

		String result = jobDutyDao.create(jobDuty);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String code) {

		return jobDutyDao.exists(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public JobDuty read(String jobDutyCode) {

		return jobDutyDao.get(jobDutyCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String jobDutyCode) {

		jobDutyDao.remove(jobDutyCode);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(JobDuty jobDuty) {

		jobDutyDao.update(jobDuty);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.JobDutyService#list(com
	 * .lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public SearchResult<JobDuty> list(AdminSearchCondition searchCondition) {

		Integer count = jobDutyDao.selectCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<JobDuty> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<JobDuty>(searchCondition);

		} else {

			List<JobDuty> jobDutyList = jobDutyDao.selectAll(searchCondition);

			List<JobDuty> returnList = new ArrayList<JobDuty>();
			for (JobDuty jobDuty : jobDutyList) {
				if (!"ko".equals(searchCondition.getUserLocaleCode())) {
					jobDuty.setJobDutyName(jobDuty.getJobDutyEnglishName());
				}
				returnList.add(jobDuty);
			}

			searchResult = new SearchResult<JobDuty>(returnList, searchCondition);
		}

		return searchResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.JobDutyService#list(com
	 * .lgcns.ikep4.support.user.code.model.AdminSearchCondition)
	 */
	public List<JobDuty> selectJobDutyList(String portalId) {
		
		List<JobDuty> jobDutyList = jobDutyDao.selectJobDutyForTree(portalId);		

		//2012-12-07 최재영
		//회장님을 별도로 보여드리기 위한 노력...
		for (JobDuty jobDuty : jobDutyList) {
			
			if("10".equals(jobDuty.getJobDutyCode()))
			{
				jobDuty.setNum("1");
			}
		}
		
		return jobDutyList;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.support.user.code.service.JobDutyService#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return jobDutyDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.JobDutyService#goUp(java
	 * .util.Map)
	 */
	public void goUp(Map<String, String> map) {

		JobDuty upJobDuty = jobDutyDao.goUp(map);
		String upSortOrder = upJobDuty.getSortOrder();

		upJobDuty.setSortOrder((String) map.get("sortOrder"));

		jobDutyDao.updateSortOrder(upJobDuty);

		JobDuty jobDuty = new JobDuty();
		jobDuty.setJobDutyCode((String) map.get("jobDutyCode"));
		jobDuty.setSortOrder(upSortOrder);

		jobDutyDao.updateSortOrder(jobDuty);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.support.user.code.service.JobDutyService#goDown(java
	 * .util.Map)
	 */
	public void goDown(Map<String, String> map) {

		JobDuty downJobDuty = jobDutyDao.goDown(map);
		String downSortOrder = downJobDuty.getSortOrder();

		downJobDuty.setSortOrder((String) map.get("sortOrder"));

		jobDutyDao.updateSortOrder(downJobDuty);

		JobDuty jobDuty = new JobDuty();
		jobDuty.setJobDutyCode((String) map.get("jobDutyCode"));
		jobDuty.setSortOrder(downSortOrder);

		jobDutyDao.updateSortOrder(jobDuty);
	}

}