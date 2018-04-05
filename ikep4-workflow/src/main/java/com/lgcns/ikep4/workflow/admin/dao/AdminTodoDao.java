/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.workflow.admin.model.AdminTodo;
import com.lgcns.ikep4.workflow.admin.model.AdminTodoSearchCondition;

/**
 * 워크플로우 - 현황관리 - 업무관리
 *
 * @author 이성훈(smart727@built1.com)
 * @version $Id: AdminTodoDao.java 16245 2011-08-18 04:28:59Z giljae $
 */
public interface AdminTodoDao extends GenericDao<AdminTodo,String>{
	public List<AdminTodo> selectAll();
	public String create(AdminTodo object);
	public boolean exists(String id);
	public AdminTodo get(String id);
	public void remove(String id);
	public void update(AdminTodo object);
	/*
	 * 업무관리 리스트 조회
	 */
	public List<AdminTodo> listTodo(AdminTodoSearchCondition adminTodoSearchCondition);
	/*
	 * 업무관리 리스트 조회건수
	 */
	public Integer listTodoCount(AdminTodoSearchCondition adminTodoSearchCondition);
	/*
	 * 업무 현황
	 */
	//public Map<String,Object> todoStateCount(AdminTodoSearchCondition adminTodoSearchCondition);
	/*
	 * 최근 진행중인 업무 5건
	 */
	//public List<AdminTodo> listCurrentTodo(AdminTodoSearchCondition adminTodoSearchCondition);
}
