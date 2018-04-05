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
import com.lgcns.ikep4.workflow.engine.dao.InterfaceDao;
import com.lgcns.ikep4.workflow.engine.dao.WorkListDao;
import com.lgcns.ikep4.workflow.engine.model.InterfaceBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: InterfaceDaoImpl 오후 3:50:45 
 */
@Repository("interfaceDao")
public class InterfaceDaoImpl extends GenericDaoSqlmap<InterfaceBean, String> implements InterfaceDao {

	Log log = LogFactory.getLog(InterfaceDaoImpl.class);
	
	public InterfaceDaoImpl() {
		
	}
	
	public String create(InterfaceBean object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public InterfaceBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(InterfaceBean object) {
		// TODO Auto-generated method stub
		
	}
	
	public List<InterfaceBean> readInterface() {
		// TODO Auto-generated method stub
		log.info("## readInterface() : ");
		return sqlSelectForList("com.lgcns.ikep4.workflow.engine.model.InterfaceBean.readInterface");
	}

	public void updateInterface(InterfaceBean instanceBean) {
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.InterfaceBean.updateFlag", instanceBean);
	}

}