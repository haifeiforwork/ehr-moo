/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchSearchCondition;
import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchTrigger;
import com.lgcns.ikep4.support.quartz.model.QuartzLog;


/**
 * Batch Manage Service
 * 
 * @author 주길재
 * @version $Id: BatchManageService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface BatchManageService extends GenericService<Object, String> {
	public SearchResult<BatchTrigger> listTrigger(BatchSearchCondition searchCondition);

	public BatchCronJobDetail readCronJob(String triggerName, String jobName);

	public BatchSimpleJobDetail readSimpleJob(String triggerName, String jobName);
	
	/**
	 * Batch Job log 저장
	 * @param quartzLog
	 */
	public void createBatchLog(QuartzLog quartzLog);
	
	public SearchResult<QuartzLog> listBatchLog(BatchSearchCondition searchCondition);
}
