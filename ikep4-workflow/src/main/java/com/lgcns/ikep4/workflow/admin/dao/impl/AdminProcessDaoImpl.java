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
import com.lgcns.ikep4.workflow.admin.dao.AdminProcessDao;
import com.lgcns.ikep4.workflow.admin.model.AdminProcess;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessDash;
import com.lgcns.ikep4.workflow.admin.model.AdminProcessSearchCondition;

/**
 * 워크플로우 - 현황관리 - IKEP4_WF_PROCESS
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminProcessDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("adminProcessDao")
public class AdminProcessDaoImpl extends GenericDaoSqlmap<AdminProcess,String> implements AdminProcessDao{
	
	public List<AdminProcess> selectAll() {
		return sqlSelectForList("workflow.admin.dao.AdminProcessDao.selectAll");
	}
	
	public String create(AdminProcess object) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminProcessDao.insert", object);
	}
	
	public String createModel(Map<String,Object> params) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminProcessDao.insertProcessModel", params);
	}

	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject("workflow.admin.dao.AdminProcessDao.selectCount", id);
		if (count > 0)
			return true;
		return false;
	}

	public AdminProcess get(String id) {
		return (AdminProcess) this.sqlSelectForObject("workflow.admin.dao.AdminProcessDao.select", id);
	}

	public void remove(String id) {
		this.sqlDelete("workflow.admin.dao.AdminProcessDao.delete", id);
	}

	public void update(AdminProcess object) {
		this.sqlUpdate("workflow.admin.dao.AdminProcessDao.update", object);
	}
	
	/*
	 * 프로세스 조회(ComboBox)
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> listComboProcess(String partitionId) {
		return (List)sqlSelectForList("workflow.admin.dao.AdminProcessDao.listComboProcess",partitionId);
	}
	
	/*
	 * 프로세스 리스트 조회
	 */
	public List<AdminProcess> listProcess(AdminProcessSearchCondition processSearchCondition) {
		return sqlSelectForList("workflow.admin.dao.AdminProcessDao.listProcess",processSearchCondition);
	}
	
	/*
	 * 프로세스 리스트 조회건수
	 */
	public Integer listProcessCount(AdminProcessSearchCondition processSearchCondition) {
		return (Integer)sqlSelectForObject("workflow.admin.dao.AdminProcessDao.listProcessCount",processSearchCondition);
	}
	
	/*
	 * 프로세스 상태 변경
	 */
	public Integer updateProcessState(Map<String,Object> params) {
		return sqlUpdate("workflow.admin.dao.AdminProcessDao.updateProcessStateChange",params);
	}
	
	/*
	 * 프로세스 전체 조회건수
	 */
	public Integer processCount(){
		return (Integer)this.sqlSelectForObject("workflow.admin.dao.AdminProcessDao.processCount");
	}
	
	/*
	 * 프로세스별  인스턴스건수
	 */
	public List<AdminProcessDash> processInstanceCount(String searchCondition){
		return (List)sqlSelectForList("workflow.admin.dao.AdminProcessDao.processInstanceCount", searchCondition);
		//(String)sqlSelectForObject("workflow.admin.dao.AdminProcessDao.processInstanceCount", searchCondition);
	}
}
