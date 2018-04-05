/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.quartz.dao.BatchManageDao;
import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchSearchCondition;
import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchTrigger;
import com.lgcns.ikep4.support.quartz.model.QuartzLog;


/**
 * Batch Manage Dao 구현체
 * 
 * @author 주길재
 * @version $Id: BatchManageDaoImpl.java 16316 2011-08-22 00:26:53Z giljae $
 */
@Repository("batchManageDao")
public class BatchManageDaoImpl extends GenericDaoSqlmap<Object, String> implements BatchManageDao {
	// 상수 정의
	interface Constants {
		final String NAMESPACE = "support.quartz.dao.BatchManage.";
	}

	/**
	 * Trigger의 count를 가져온다.
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countListTrigger(BatchSearchCondition searchCondition) {
		return (Integer) sqlSelectForObject(Constants.NAMESPACE + "countListTrigger", searchCondition);
	}
	
	public Integer countListBatchLog(BatchSearchCondition searchCondition) {
		return (Integer) sqlSelectForObject(Constants.NAMESPACE + "countListBatchLog", searchCondition);
	}

	/**
	 * Trigger 리스트를 가져온다.
	 */
	@SuppressWarnings("unchecked")
	public List<BatchTrigger> listTrigger(BatchSearchCondition searchCondition) {
		List<BatchTrigger> batchTriggerList = null;

		try {
			batchTriggerList = (List<BatchTrigger>) getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listTrigger", searchCondition);
		} catch (SQLException sqlException) {

		}

		return batchTriggerList;
	}
	
	@SuppressWarnings("unchecked")
	public List<QuartzLog> listBatchLog(BatchSearchCondition searchCondition) {
		List<QuartzLog> batchLogList = null;

		try {
			batchLogList = (List<QuartzLog>) getSqlMapClientTemplate().getSqlMapClient().queryForList(
					Constants.NAMESPACE + "listBatchLog", searchCondition);
		} catch (SQLException sqlException) {

		}

		return batchLogList;
	}

	public BatchCronJobDetail readCronJob(String triggerName, String jobName) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("triggerName", triggerName);
		paramMap.put("jobName", jobName);

		return (BatchCronJobDetail) sqlSelectForObject(Constants.NAMESPACE + "readCronJob", paramMap);
	}

	public BatchSimpleJobDetail readSimpleJob(String triggerName, String jobName) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("triggerName", triggerName);
		paramMap.put("jobName", jobName);

		return (BatchSimpleJobDetail) sqlSelectForObject(Constants.NAMESPACE + "readSimpleJob", paramMap);
	}

	public void createBatchLog(QuartzLog quartzLog) {
		sqlInsert(Constants.NAMESPACE + "createBatchLog", quartzLog);
	}
	
	public String getJobStatus(String jobName) {
		return (String) sqlSelectForObject(Constants.NAMESPACE + "getJobStatus", jobName);
	}
	
	public String getJobCondition(String jobName) {
		return (String) sqlSelectForObject(Constants.NAMESPACE + "getJobCondition", jobName);
	}
	
	public void updateJobCondition(BatchCronJobDetail batchCronJobDetail){
		sqlUpdate(Constants.NAMESPACE + "updateJobCondition", batchCronJobDetail);
	}
	
	public void updateJobConditionSimple(BatchSimpleJobDetail batchSimpleJobDetail){
		sqlUpdate(Constants.NAMESPACE + "updateJobConditionSimple", batchSimpleJobDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@Deprecated
	public String create(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	@Deprecated
	public boolean exists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	@Deprecated
	public Object get(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@Deprecated
	public void remove(String username) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@Deprecated
	public void update(Object object) {
		// TODO Auto-generated method stub

	}
}
