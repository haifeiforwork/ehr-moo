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
import com.lgcns.ikep4.workflow.engine.dao.InstanceDao;
import com.lgcns.ikep4.workflow.engine.model.InstanceBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 
 * @version $Id: InstanceDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $ InstanceDaoImpl.java 오전 10:28:15
 */
@Repository("instanceDao")
public class InstanceDaoImpl extends GenericDaoSqlmap<InstanceBean, String> implements InstanceDao {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(InstanceBean object) {
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
	public InstanceBean get(String id) {
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
	public void update(InstanceBean instanceBean) {
		// TODO Auto-generated method stub
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InstanceBean.update", instanceBean);
	}
	
	
	public void updateResult(InstanceBean instanceBean) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.InstanceDao#updateKeyData(com.lgcns.ikep4.workflow.engine.model.InstanceBean)
	 */
	public void updateKeyData(InstanceBean instanceBean) {
		// TODO Auto-generated method stub
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InstanceBean.updateKeyData", instanceBean);
	}
	
	
	public void updateVarSchema(InstanceBean instanceBean) {
		// TODO Auto-generated method stub
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InstanceBean.updateVarSchema", instanceBean);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.InstanceDao#startProcess(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void startProcess(InstanceBean instanceBean) {
		// TODO Auto-generated method stub
		sqlInsert("com.lgcns.ikep4.workflow.engine.model.InstanceBean.insert", instanceBean);
		
	}
	
	public void deleteInstance(InstanceBean instanceBean) {
		// TODO Auto-generated method stub
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.InstanceBean.deleteVarLogIns", instanceBean);
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.InstanceBean.deleteVarIns", instanceBean);
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.InstanceBean.deleteLogIns", instanceBean);
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.InstanceBean.deleteTodoIns", instanceBean);
		sqlDelete("com.lgcns.ikep4.workflow.engine.model.InstanceBean.deleteIns", instanceBean);
	}
	
	public InstanceBean select(InstanceBean instanceBean) {
		// TODO Auto-generated method stub
		return (InstanceBean) this.sqlSelectForObject("com.lgcns.ikep4.workflow.engine.model.InstanceBean.select", instanceBean);
	}
}