/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.engine.model.DelegateBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.workplace.dao.PersonalSetDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이재경
 * @version $Id: PersonalSetDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository
public class PersonalSetDaoImpl extends GenericDaoSqlmap<WorkItemBean, String> implements PersonalSetDao{
	
	private static final String NAMESPACE = "workflow.workplace.dao.PersonalSet.";
	
	public int delegateCreate(DelegateBean delegateBean){
		return this.sqlUpdate(NAMESPACE + "delegateCreate", delegateBean);
	}
	
	public DelegateBean delegateDetail(DelegateBean delegateBean){
		return (DelegateBean)this.sqlSelectForObject(NAMESPACE + "delegateDetail", delegateBean);
	}
	
	public int delegateUpdate(DelegateBean delegateBean){
		return this.sqlUpdate(NAMESPACE + "delegateUpdate", delegateBean);
	}
	
	public WorkItemBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(WorkItemBean object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(WorkItemBean object) {
		// TODO Auto-generated method stub
		
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	} 
}