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
import com.lgcns.ikep4.workflow.admin.dao.AdminPartitionDao;
import com.lgcns.ikep4.workflow.admin.model.AdminPartition;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_PARTITION
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminPartitionDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("adminPartitionDao")
public class AdminPartitionDaoImpl extends GenericDaoSqlmap<AdminPartition,String> implements AdminPartitionDao{
	
	public List<AdminPartition> selectAll() {
		return sqlSelectForList("workflow.admin.dao.AdminPartitionDao.selectAll");
	}
	
	public String create(AdminPartition object) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminPartitionDao.insert", object);
	}

	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject("workflow.admin.dao.AdminPartitionDao.selectCount", id);
		if (count > 0)
			return true;
		return false;
	}

	public AdminPartition get(String id) {
		return (AdminPartition) this.sqlSelectForObject("workflow.admin.dao.AdminPartitionDao.select", id);
	}

	public void remove(String id) {
		this.sqlDelete("workflow.admin.dao.AdminPartitionDao.delete", id);
	}

	public void update(AdminPartition object) {
		this.sqlUpdate("workflow.admin.dao.AdminPartitionDao.update", object);
	}
	
	/*
	 * 파티션 전체 조회건수
	 */
	public Integer partitionCount(){
		return (Integer)this.sqlSelectForObject("workflow.admin.dao.AdminPartitionDao.partitionCount");
	}
}
