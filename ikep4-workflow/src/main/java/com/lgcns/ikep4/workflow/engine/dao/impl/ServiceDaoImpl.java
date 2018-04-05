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

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.engine.dao.ServiceDao;
import com.lgcns.ikep4.workflow.engine.model.InterfaceBean;
import com.lgcns.ikep4.workflow.engine.model.ServiceBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: serviceDaoImpl 오후 3:50:45 
 */
@Repository("serviceDao")
public class ServiceDaoImpl extends GenericDaoSqlmap<ServiceBean, String> implements ServiceDao {

	Log log = LogFactory.getLog(ServiceDaoImpl.class);
	
	public String create(ServiceBean serviceBean) {
		// TODO Auto-generated method stub
		sqlInsert("com.lgcns.ikep4.workflow.engine.model.ServiceBean.insert", serviceBean);
		return null;
	}
	
		
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(InterfaceBean object) {
		// TODO Auto-generated method stub
		
	}
	
	public ServiceBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(ServiceBean object) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.ServiceDao#select(com.lgcns.ikep4.workflow.engine.model.ServiceBean)
	 */
	public List<ServiceBean> selectStartSerice(ServiceBean serviceBean) {
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.ServiceBean.selectStartService", serviceBean);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.ServiceDao#completeStartSerice(com.lgcns.ikep4.workflow.engine.model.ServiceBean)
	 */
	public List<ServiceBean> completeStartSerice(ServiceBean serviceBean) {
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.ServiceBean.selectCompleteService", serviceBean);
	}
}