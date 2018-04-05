/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.admin.dao.AdminServiceDao;
import com.lgcns.ikep4.workflow.admin.model.AdminService;

/**
 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminServiceDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("adminServiceDao")
public class AdminServiceDaoImpl extends GenericDaoSqlmap<AdminService,String> implements AdminServiceDao{
	
	public List<AdminService> selectAll() {
		return sqlSelectForList("workflow.admin.dao.AdminServiceDao.selectAll");
	}
	
	public String create(AdminService object) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminServiceDao.insert", object);
	}

	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject("workflow.admin.dao.AdminServiceDao.selectCount", id);
		if (count > 0)
			return true;
		return false;
	}

	public AdminService get(String id) {
		return (AdminService) this.sqlSelectForObject("workflow.admin.dao.AdminServiceDao.select", id);
	}

	public void remove(String id) {
		this.sqlDelete("workflow.admin.dao.AdminServiceDao.delete", id);
	}

	public void update(AdminService object) {
		this.sqlUpdate("workflow.admin.dao.AdminServiceDao.update", object);
	}
	
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면
	 */
	public AdminService listProcessDetail(Map<String,Object> params){
		return (AdminService)this.sqlSelectForObject("workflow.admin.dao.AdminServiceDao.listProcessDetail",params);
	}
	
	/*
	 * 워크플로우 - 현황관리 - 프로세스 관리 - 프로세스 상세화면 적용일자 수정
	 */
	public Integer updateProcessApplyDate(Map<String,Object> params){
		return sqlUpdate("workflow.admin.dao.AdminServiceDao.updateProcessApplyDate",params);
	}
}
