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
import com.lgcns.ikep4.workflow.engine.dao.InstanceVarDao;
import com.lgcns.ikep4.workflow.engine.model.InstanceVarBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박철순
 * @version $Id: InstanceVarDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("instanceVarDao")
public class InstanceVarDaoImpl extends GenericDaoSqlmap<InstanceVarBean, String> implements InstanceVarDao {

	public String create(InstanceVarBean instanceVarBean) {
		// TODO Auto-generated method stub
		sqlInsert("com.lgcns.ikep4.workflow.engine.model.InstanceVarBean.insert", instanceVarBean);
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public InstanceVarBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	public void update(InstanceVarBean instanceVarBean) {
		// TODO Auto-generated method stub
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InstanceVarBean.update", instanceVarBean);

	}
	
	public void delete(InstanceVarBean instanceVarBean) {
		// TODO Auto-generated method stub
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InstanceVarBean.delete", instanceVarBean);

	}	

	public WorkItemBean getInstanceVar(InstanceVarBean instanceVarBean) {
		// TODO Auto-generated method stub
		return (WorkItemBean) sqlSelectForObject("com.lgcns.ikep4.workflow.engine.model.InstanceVarBean.select", instanceVarBean);
	}

}