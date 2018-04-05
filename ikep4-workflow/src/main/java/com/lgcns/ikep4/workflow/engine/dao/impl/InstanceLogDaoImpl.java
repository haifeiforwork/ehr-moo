/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.engine.dao.InstanceLogDao;
import com.lgcns.ikep4.workflow.engine.model.InstanceLogBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: WorkListDaoImpl.java 오전 11:24:31 
 */
@Repository("instanceLogDao")
public class InstanceLogDaoImpl extends GenericDaoSqlmap<InstanceLogBean, String> implements InstanceLogDao {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(InstanceLogBean object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public InstanceLogBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(InstanceLogBean instanceLogBean) {
		// TODO Auto-generated method stub
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InstanceLogBean.update", instanceLogBean);
	}
	
	public void updateSelectWorkItem(InstanceLogBean instanceLogBean) {
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InstanceLogBean.updateSelectWorkItem", instanceLogBean);
	}
	

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void insert(InstanceLogBean instanceLogBean) {
		// TODO Auto-generated method stub
		sqlInsert("com.lgcns.ikep4.workflow.engine.model.InstanceLogBean.insert", instanceLogBean);
	}	
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.InstanceLogDao#updateDelegate(com.lgcns.ikep4.workflow.engine.model.InstanceLogBean)
	 */
	public boolean updateDelegate(InstanceLogBean instanceLogBean) {
		// TODO Auto-generated method stub
		int result 	= sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InstanceLogBean.updateDelegate", instanceLogBean);
		return (0 < result);
	}
	
}