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
import com.lgcns.ikep4.workflow.admin.dao.AdminTodoDao;
import com.lgcns.ikep4.workflow.admin.model.AdminTodo;
import com.lgcns.ikep4.workflow.admin.model.AdminTodoSearchCondition;

/**
 * 워크플로우 - 현황관리 - 업무관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminTodoDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("adminTodoDao")
public class AdminTodoDaoImpl extends GenericDaoSqlmap<AdminTodo,String> implements AdminTodoDao{
	
	public List<AdminTodo> selectAll() {
		return sqlSelectForList("workflow.admin.dao.AdminTodoDao.selectAll");
	}
	
	public String create(AdminTodo object) {
		return (String) this.sqlInsert("workflow.admin.dao.AdminTodoDao.insert", object);
	}

	public boolean exists(String id) {
		Integer count = (Integer) this.sqlSelectForObject("workflow.admin.dao.AdminTodoDao.selectCount", id);
		if (count > 0)
			return true;
		return false;
	}

	public AdminTodo get(String id) {
		return (AdminTodo) this.sqlSelectForObject("workflow.admin.dao.AdminTodoDao.select", id);
	}

	public void remove(String id) {
		this.sqlDelete("workflow.admin.dao.AdminTodoDao.delete", id);
	}

	public void update(AdminTodo object) {
		this.sqlUpdate("workflow.admin.dao.AdminTodoDao.update", object);
	}
	
	/*
	 * 업무관리 리스트 조회
	 */
	public List<AdminTodo> listTodo(AdminTodoSearchCondition adminTodoSearchCondition) {
		return sqlSelectForList("workflow.admin.dao.AdminTodoDao.listTodo",adminTodoSearchCondition);
	}
	
	/*
	 * 업무관리 리스트 조회건수
	 */
	public Integer listTodoCount(AdminTodoSearchCondition adminTodoSearchCondition) {
		return (Integer)sqlSelectForObject("workflow.admin.dao.AdminTodoDao.listTodoCount",adminTodoSearchCondition);
	}
	
	/*
	 * 업무 현황
	 
	@SuppressWarnings("unchecked")
	public Map<String,Object> todoStateCount(AdminTodoSearchCondition adminTodoSearchCondition) {
		return (Map<String,Object>)sqlSelectForObject("workflow.admin.dao.AdminTodoDao.todoStateCount",adminTodoSearchCondition);
	}
	*/
	/*
	 * 최근 진행중인 업무 5건
	 
	public List<AdminTodo> listCurrentTodo(AdminTodoSearchCondition adminTodoSearchCondition) {
		return sqlSelectForList("workflow.admin.dao.AdminTodoDao.listCurrentTodo",adminTodoSearchCondition);
	}
	*/
}
