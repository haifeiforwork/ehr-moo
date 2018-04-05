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
import com.lgcns.ikep4.workflow.admin.dao.AdminInstanceDao;
import com.lgcns.ikep4.workflow.admin.model.AdminInstance;
import com.lgcns.ikep4.workflow.admin.model.AdminInstanceSearchCondition;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_INSTANCE
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminInstanceDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("adminInstanceDao")
public class AdminInstanceDaoImpl extends GenericDaoSqlmap<AdminInstance,String> implements AdminInstanceDao{
	
	public List<AdminInstance> selectAll() {
		return sqlSelectForList("workflow.admin.dao.AdminInstanceDao.selectAll");
	}
	
	public String create(AdminInstance object) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminInstanceDao.insert", object);
	}

	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject("workflow.admin.dao.AdminInstanceDao.selectCount", id);
		if (count > 0)
			return true;
		return false;
	}

	public AdminInstance get(String id) {
		return (AdminInstance) this.sqlSelectForObject("workflow.admin.dao.AdminInstanceDao.select", id);
	}

	public void remove(String id) {
		this.sqlDelete("workflow.admin.dao.AdminInstanceDao.delete", id);
	}

	public void update(AdminInstance object) {
		this.sqlUpdate("workflow.admin.dao.AdminInstanceDao.update", object);
	}
	
	/*
	 * 인스턴스 리스트 조회
	 */
	public List<AdminInstance> listInstance(AdminInstanceSearchCondition adminInstanceSearchCondition) {
		return sqlSelectForList("workflow.admin.dao.AdminInstanceDao.listInstance",adminInstanceSearchCondition);
	}
	
	/*
	 * 인스턴스 리스트 조회건수
	 */
	public Integer listInstanceCount(AdminInstanceSearchCondition adminInstanceSearchCondition) {
		return (Integer)sqlSelectForObject("workflow.admin.dao.AdminInstanceDao.listInstanceCount",adminInstanceSearchCondition);
	}
	
	/*
	 * 인스턴스 상태 변경
	 */
	public Integer updateInstanceState(Map<String,Object> params) {
		return sqlUpdate("workflow.admin.dao.AdminInstanceDao.updateInstanceStateChange",params);
	}
	
	/*
	 * 인스턴스 현황 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> instanceStateCount(){
		return (Map<String,Object>)sqlSelectForObject("workflow.admin.dao.AdminInstanceDao.instanceStateCount");
	}
	
	/*
	 * 프로세스별 인스턴스 진행현황
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> listCurrentInstance() {
		return (List)sqlSelectForList("workflow.admin.dao.AdminInstanceDao.listCurrentInstance");
	}
	
	/*
	 * 프로세스별  인스턴스 누적현황
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> listAccumulateInstance(String searchCondition) {
		return (List)sqlSelectForList("workflow.admin.dao.AdminInstanceDao.listAccumulateInstance",searchCondition);
	}
}
