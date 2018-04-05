/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.TriggersRemove;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.engine.dao.WorkListDao;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: WorkListDaoImpl.java 오후 3:50:45 
 */
@Repository("workListDao")
public class WorkListDaoImpl extends GenericDaoSqlmap<WorkItemBean, String> implements WorkListDao {

	Log log = LogFactory.getLog(WorkListDaoImpl.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.WorkListDao#todoList()
	 */
	@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	public List<WorkItemBean> todoList(String userId) {
		// TODO Auto-generated method stub
		log.info("# com.lgcns.ikep4.workflow.engine.model.WorkItemBean.todoList ");
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.WorkItemBean.todoList", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.WorkListDao#runningList()
	 */
	public List<WorkItemBean> runningList(String userId) {
		// TODO Auto-generated method stub
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.WorkItemBean.runningList", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.WorkListDao#completedList()
	 */
	public List<WorkItemBean> completedList(String userId) {
		// TODO Auto-generated method stub
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.WorkItemBean.completedList", userId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(WorkItemBean object) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.WorkListDao#selectAll()
	 */
	public List<WorkItemBean> selectAll(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public WorkItemBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(WorkItemBean object) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}


	public void insert(WorkItemBean workItemBean) {
		// TODO Auto-generated method stub
		sqlInsert("com.lgcns.ikep4.workflow.engine.model.WorkItemBean.insert", workItemBean);
	}

	public void delete(WorkItemBean workItemBean) {
		// TODO Auto-generated method stub
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.WorkItemBean.delete", workItemBean);
	}
	
	public List<WorkItemBean> select(String insLogId) {
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.WorkItemBean.select", insLogId);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.WorkListDao#updateDelegate(com.lgcns.ikep4.workflow.engine.model.WorkItemBean)
	 */
	public boolean updateDelegate(WorkItemBean workItemBean) {
		// TODO Auto-generated method stub
		int result =  sqlUpdate("com.lgcns.ikep4.workflow.engine.model.WorkItemBean.updateDelegate", workItemBean);
		return (0 < result);
	}
	


}