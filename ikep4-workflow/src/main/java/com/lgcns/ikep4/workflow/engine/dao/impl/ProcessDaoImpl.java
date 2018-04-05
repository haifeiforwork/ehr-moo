/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.engine.dao.ProcessDao;
import com.lgcns.ikep4.workflow.engine.model.ProcessBean;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: ProcessDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $ ProcessDaoImpl.java 오후 6:23:02
 */
@Repository("processDao")
public class ProcessDaoImpl extends GenericDaoSqlmap<ProcessBean, String> implements ProcessDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ProcessBean object) {
		// TODO Auto-generated method stub
		return null;
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
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(ProcessBean object) {
		// TODO Auto-generated method stub

	}

	
	public void updateProcess(ProcessBean processBean) {
		// TODO Auto-generated method stub
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.ProcessBean.updateProcess", processBean);
	}	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.workflow.engine.dao.InstanceDao#startProcess(java.lang
	 * .String, java.lang.String, java.lang.String)
	 */
	public List<ProcessBean> select(ProcessBean processBean) {
		// TODO Auto-generated method stub
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.ProcessBean.select", processBean);
	}	
	
	
	

}