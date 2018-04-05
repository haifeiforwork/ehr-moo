/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.admin.dao.AdminSmsDao;
import com.lgcns.ikep4.workflow.admin.model.AdminSms;
import com.lgcns.ikep4.workflow.admin.model.AdminSmsSearchCondition;

/**
 * TODO Javadoc주석작성
 *
 * @author 이재경
 * @version $Id: AdminSmsDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository
public class AdminSmsDaoImpl extends GenericDaoSqlmap<AdminSms, String> implements AdminSmsDao{
	
	private static final String NAMESPACE = "workflow.admin.dao.AdminSms.";
	
	public List<AdminSms> listBySearchCondition(AdminSmsSearchCondition adminSmsSearchCondition) {
		return (List<AdminSms>)this.sqlSelectForList(NAMESPACE + "listSmsHistoryList", adminSmsSearchCondition);
	}

	public Integer countBySearchCondition(AdminSmsSearchCondition adminSmsSearchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countSmsHistoryList", adminSmsSearchCondition);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
	}

	public AdminSms get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String create(AdminSms object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(AdminSms object) {
		// TODO Auto-generated method stub
		
	}
}