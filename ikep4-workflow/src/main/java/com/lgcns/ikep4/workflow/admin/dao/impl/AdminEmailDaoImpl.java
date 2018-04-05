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

import com.lgcns.ikep4.workflow.admin.dao.AdminEmailDao;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLog;
import com.lgcns.ikep4.workflow.admin.model.AdminEmailLogSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 결재 코드관리 DAO 구현
 * 
 * @author 장규진
 * @version $Id: AdminEmailDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("adminEmailDao")
public class AdminEmailDaoImpl extends GenericDaoSqlmap<AdminEmailLog, String> implements AdminEmailDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.workflow.admin.dao.AdminEmail.";

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public AdminEmailLog get(String logId) {
		return (AdminEmailLog)sqlSelectForObject(NAMESPACE + "selectOne", logId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public AdminEmailLog getEmailLogView(String logId) {
		return (AdminEmailLog)sqlSelectForObject(NAMESPACE + "selectOne", logId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(String logId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "selectCount", logId);

		if (count > 0)
			return true;

		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.workflow.admin.dao.AdminEmailDao#getCountList(com.lgcns.ikep4.workflow.admin.model.AdminEmailLogSearchCondition)
	 */
	public Integer getCountAdminEmailLogList(AdminEmailLogSearchCondition adminEmailLogSearchCondition) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "countEmailLogList", adminEmailLogSearchCondition);

		return count;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 */
	public List<AdminEmailLog> getAdminEmailLogSearchList(AdminEmailLogSearchCondition adminEmailLogSearchCondition) {
		List<AdminEmailLog> adminEmailLogList = (List<AdminEmailLog>) this.sqlSelectForList(NAMESPACE + "searchEmailLogList",
				adminEmailLogSearchCondition);

		return adminEmailLogList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(AdminEmailLog adminEmail) {
		return (String) sqlInsert(NAMESPACE + "create", adminEmail);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String logId) {
		sqlDelete(NAMESPACE + "delete", logId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#removeMulti(java.io.Serializable
	 * )
	 */
	public void removeMulti(List<String> logIds) {
		sqlDelete(NAMESPACE + "deleteMulti", logIds);
	}	

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	// @TriggersRemove(cacheName = "adminemailCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void update(AdminEmailLog adminEmail) {
		//sqlUpdate(NAMESPACE + "update", apEmail);
		return;
	}
	
	
	
}
