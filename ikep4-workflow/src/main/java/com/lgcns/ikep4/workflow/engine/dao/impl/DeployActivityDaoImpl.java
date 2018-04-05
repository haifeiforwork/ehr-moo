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
import com.lgcns.ikep4.workflow.engine.dao.DeployActivityDao;
import com.lgcns.ikep4.workflow.engine.model.ActivityBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: DeployActivityDaoImpl.java 오후 2:41:46 
 */
@Repository("deployActivityDao")
public class DeployActivityDaoImpl extends GenericDaoSqlmap<ActivityBean, String> implements DeployActivityDao {

	
	 /* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(ActivityBean object) {
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
	public ActivityBean get(String id) {
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
	public void update(ActivityBean object) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.DeployActivityDao#insertActivity(com.lgcns.ikep4.workflow.engine.model.ActivityBean)
	 */
	public String insertActivity(ActivityBean activityBean) {
		return (String) sqlInsert("com.lgcns.ikep4.workflow.engine.model.ActivityBean.insert", activityBean);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.DeployActivityDao#selectAll()
	 */
	public List<ActivityBean> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}