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

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.engine.dao.DeployProcessDao;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: DeployProcessDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $ DeployProcessDaoImpl.java 오전 10:11:54
 */
@Repository("deployProcessDao")
public class DeployProcessDaoImpl extends GenericDaoSqlmap<ProcessBean, String> implements DeployProcessDao {

	
	Log log = LogFactory.getLog(DeployProcessDaoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ProcessBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ProcessBean selectProcess(ProcessBean processBean) {
		return (ProcessBean) sqlSelectForObject("com.lgcns.ikep4.workflow.engine.model.ProcessBean.select", processBean);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String processId) {
		String count = (String) sqlSelectForObject("com.lgcns.ikep4.workflow.engine.model.ProcessBean.selectCount", processId);
		return (("1").equals(count));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public String create(ProcessBean processBean) {
		return (String) sqlInsert("com.lgcns.ikep4.workflow.engine.model.ProcessBean.insert", processBean);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void update(ProcessBean processBean) {
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.ProcessBean.update", processBean);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public int updateProcess(ProcessBean processBean) {
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteParticipant", processBean);
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteManual", processBean);
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteActivity", processBean);
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteTransition", processBean);
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteNotify", processBean);	
		
		return sqlUpdate("com.lgcns.ikep4.workflow.engine.model.ProcessBean.updateProcess", processBean);
	}	

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public boolean remove(ProcessBean processBean) {
		boolean isSuccess 	= false;
		try {
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteVarSchema", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteVarLog", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteTodoList", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteInstanceLog", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteInstanceVar", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteProcessInstance", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteNotify", processBean);	
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteParticipant", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteManual", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteActivity", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteTransition", processBean);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteService", processBean);
			// sqlDelete("processBean.deleteProcessModel", processId);
			// sqlDelete("processBean.deleteProcessView", processId);
			sqlDelete("com.lgcns.ikep4.workflow.engine.model.ProcessBean.deleteProcess", processBean);
			isSuccess		= true;
		} catch(Exception e) {
			log.debug("# Error (DeployProcessDaoImpl.remove) : " + e);
		}
		return isSuccess;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.modeler.dao.PartitionDao#selectAll()
	 */
	@Cacheable(cacheName = "sampleCache")
	// @Cacheable 어노테이션으로 캐시 대상을 명시한다.
	public List<ProcessBean> selectAll() {
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.ProcessBean.selectAll");
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}