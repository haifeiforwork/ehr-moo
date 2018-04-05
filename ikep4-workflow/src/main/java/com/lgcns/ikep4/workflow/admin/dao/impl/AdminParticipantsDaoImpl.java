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
import com.lgcns.ikep4.workflow.admin.dao.AdminParticipantsDao;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipantSearchCondition;
import com.lgcns.ikep4.workflow.admin.model.AdminParticipants;

/**
 * 참여자 정보 조회
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminParticipantsDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("adminParticipantsDao")
public class AdminParticipantsDaoImpl extends GenericDaoSqlmap<AdminParticipants,String> implements AdminParticipantsDao{
		
	public List<AdminParticipants> selectAll() {
		return sqlSelectForList("workflow.admin.dao.AdminParticipantsDao.selectAll");
	}
	
	public String create(AdminParticipants object) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminParticipantsDao.insert", object);
	}
	
	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject("workflow.admin.dao.AdminParticipantsDao.selectCount", id);
		if (count > 0)
			return true;
		return false;
	}

	public AdminParticipants get(String id) {
		return (AdminParticipants) this.sqlSelectForObject("workflow.admin.dao.AdminParticipantsDao.select", id);
	}

	public void remove(String id) {
		this.sqlDelete("workflow.admin.dao.AdminParticipantsDao.delete", id);
	}

	public void update(AdminParticipants object) {
		this.sqlUpdate("workflow.admin.dao.AdminParticipantsDao.update", object);
	}
	
	public String insertRoleType(Map<String,Object> params) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminParticipantsDao.insertRoleType", params);
	}
	
	/*
	 * 롤 조회
	 */
	public List<AdminParticipants> listRole(AdminParticipantSearchCondition adminParticipantSearchCondition) {
		return sqlSelectForList("workflow.admin.dao.AdminParticipantsDao.listRole",adminParticipantSearchCondition);
	}
	
	/*
	 * 롤 조회 건수
	 */
	public Integer listRoleCount(AdminParticipantSearchCondition adminParticipantSearchCondition) {
		return (Integer) sqlSelectForObject("workflow.admin.dao.AdminParticipantsDao.listRoleCount",adminParticipantSearchCondition);
	}
}