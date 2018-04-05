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
import com.lgcns.ikep4.workflow.admin.dao.AdminActivityDao;
import com.lgcns.ikep4.workflow.admin.model.AdminActivity;
import com.lgcns.ikep4.workflow.admin.model.AdminActivitySearchCondition;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_ACTIVITY 
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminActivityDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("adminActivityDao")
public class AdminActivityDaoImpl extends GenericDaoSqlmap<AdminActivity,String> implements AdminActivityDao{
	
	public List<AdminActivity> selectAll() {
		return sqlSelectForList("workflow.admin.dao.AdminActivityDao.selectAll");
	}
	
	public String create(AdminActivity object) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminActivityDao.insert", object);
	}

	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject("workflow.admin.dao.AdminActivityDao.selectCount", id);
		if (count > 0)
			return true;
		return false;
	}

	public AdminActivity get(String id) {
		return (AdminActivity) this.sqlSelectForObject("workflow.admin.dao.AdminActivityDao.select", id);
	}

	public void remove(String id) {
		this.sqlDelete("workflow.admin.dao.AdminActivityDao.delete", id);
	}

	public void update(AdminActivity object) {
		this.sqlUpdate("workflow.admin.dao.AdminActivityDao.update", object);
	}
	
	/*
	 * 액티비티 리스트 조회
	 */
	public List<AdminActivity> listActivity(AdminActivitySearchCondition activitySearchCondition) {
		return sqlSelectForList("workflow.admin.dao.AdminActivityDao.listActivity",activitySearchCondition);
	}
	
	/*
	 * 액티비티 리스트 조회건수
	 */
	public Integer listActivityCount(AdminActivitySearchCondition activitySearchCondition) {
		return (Integer)sqlSelectForObject("workflow.admin.dao.AdminActivityDao.listActivityCount",activitySearchCondition);
	}
}
