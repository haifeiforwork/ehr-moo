/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchSearchCondition;
import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchTrigger;
import com.lgcns.ikep4.support.quartz.model.QuartzLog;


/**
 * Batch Manage Dao
 * 
 * @author 주길재
 * @version $Id: BatchManageDao.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface BatchManageDao extends GenericDao<Object, String> {
	/**
	 * trigger 리스트를 가져온다.
	 * 
	 * @return
	 */
	public List<BatchTrigger> listTrigger(BatchSearchCondition searchCondition);
	
	/**
	 * Batch Log 리스트를 가져온다.
	 * @param searchCondition
	 * @return
	 */
	public List<QuartzLog> listBatchLog(BatchSearchCondition searchCondition);

	/**
	 * trigger 리스트 갯수를 가져온다.
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countListTrigger(BatchSearchCondition searchCondition);
	
	/**
	 * Batch Log 리스트 갯수를 가져온다.
	 * @param searchCondition
	 * @return
	 */
	public Integer countListBatchLog(BatchSearchCondition searchCondition);

	/**
	 * Cron Job 정보
	 * @paeam triggerName
	 * @param jobName
	 * @return
	 */
	public BatchCronJobDetail readCronJob(String triggerName, String jobName);

	/**
	 * Simple Job 정보
	 * @param triggerName
	 * @param jobName
	 * @return
	 */
	public BatchSimpleJobDetail readSimpleJob(String triggerName, String jobName);
	
	/**
	 * Batch 로그 생성
	 * @param quartzLog
	 */
	public void createBatchLog(QuartzLog quartzLog);
	
	/**
	 * Job 상태를 가져온다.
	 * @param jobName
	 * @return
	 */
	public String getJobStatus(String jobName);
	
	public String getJobCondition(String jobName);
	
	public void updateJobCondition(BatchCronJobDetail batchCronJobDetail);
	
	public void updateJobConditionSimple(BatchSimpleJobDetail batchSimpleJobDetail);
}
