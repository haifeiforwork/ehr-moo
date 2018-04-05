/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.quartz.dao.BatchManageDao;
import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchSearchCondition;
import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchTrigger;
import com.lgcns.ikep4.support.quartz.model.QuartzLog;
import com.lgcns.ikep4.support.quartz.service.BatchManageService;


/**
 * Batch Manage Service 구현체
 * 
 * @author 주길재
 * @version $Id: BatchManageServiceImpl.java 16273 2011-08-18 07:07:47Z giljae $
 */
@Service("batchManageService")
public class BatchManageServiceImpl extends GenericServiceImpl<Object, String> implements BatchManageService {
	@Autowired
	private BatchManageDao batchManageDao;

	/**
	 * 배치 트리거 리스트를 가져온다.
	 */
	public SearchResult<BatchTrigger> listTrigger(BatchSearchCondition searchCondition) {
		Integer count = batchManageDao.countListTrigger(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<BatchTrigger> searchResult = null;
		List<BatchTrigger> list = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BatchTrigger>(list, searchCondition);
		} else {

			list = this.batchManageDao.listTrigger(searchCondition);

			searchResult = new SearchResult<BatchTrigger>(list, searchCondition);
		}

		return searchResult;
	}
	
	public SearchResult<QuartzLog> listBatchLog(BatchSearchCondition searchCondition) {
		Integer count = batchManageDao.countListBatchLog(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<QuartzLog> searchResult = null;
		List<QuartzLog> list = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<QuartzLog>(list, searchCondition);
		} else {

			list = this.batchManageDao.listBatchLog(searchCondition);

			searchResult = new SearchResult<QuartzLog>(list, searchCondition);
		}

		return searchResult;
	}

	public BatchCronJobDetail readCronJob(String triggerName, String jobName) {
		return this.batchManageDao.readCronJob(triggerName, jobName);
	}

	public BatchSimpleJobDetail readSimpleJob(String triggerName, String jobName) {
		return this.batchManageDao.readSimpleJob(triggerName, jobName);
	}

	public void createBatchLog(QuartzLog quartzLog) {
		this.batchManageDao.createBatchLog(quartzLog);
	}

}
