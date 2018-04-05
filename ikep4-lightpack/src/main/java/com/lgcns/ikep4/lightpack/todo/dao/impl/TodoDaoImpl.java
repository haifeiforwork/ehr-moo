/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.todo.dao.TodoDao;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoPk;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TodoDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository
public class TodoDaoImpl extends GenericDaoSqlmap<Todo, TodoPk> implements TodoDao {
	private static final String NAMESPACE = "lightpack.todo.dao.todo.";
	
	public TodoPk create(Todo todo) {
		sqlInsert(NAMESPACE + "create", todo);
		return (TodoPk) todo;
	}

	public boolean exists(TodoPk todoPk) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", todoPk);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public Todo get(TodoPk todoPk) {
		return (Todo) sqlSelectForObject(NAMESPACE + "get", todoPk);
	}

	public void remove(TodoPk todoPk) {
		sqlDelete(NAMESPACE + "remove", todoPk);
	}

	public void update(Todo todo) {
		sqlUpdate(NAMESPACE + "update", todo);
	}

	public boolean existsLegacy(TodoPk todoPk) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "countLegacy", todoPk);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public boolean existsSubwork(TodoPk todoPk) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "countSubwork", todoPk);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	public void updateStatusComplete(TodoPk todoPk) {
		sqlUpdate(NAMESPACE + "updateStatusComplete", todoPk);
	}

	//나의 할 일만 카운트
	public Integer countMyTodo(String id) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countMyTodo", id);
	}
	
	//나의 업무만 카운트
	public Integer countMyTask(String id) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countMyTask", id);
	}
	
	//나의 할 일만 조회
	@SuppressWarnings("rawtypes")
	public List<Todo> listMyTodo(Map map) {
		return sqlSelectForList(NAMESPACE + "listMyTodo", map);
	}
	
	public Integer countMyTodoSearch(TodoSearchCondition todoSearchCondition) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countMyTodoSearch", todoSearchCondition);
	}	
	
	public Integer countMyTodoSearchDueDate(TodoSearchCondition todoSearchCondition) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countMyTodoSearchDueDate", todoSearchCondition);
	}	
	
	//나의 할 일 조회
	public List<Todo> listMyTodoSearch(TodoSearchCondition todoSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listMyTodoSearch", todoSearchCondition);
	}

	//나의 할 일 조회
	public List<Todo> listMyTodoSearchDueDate(TodoSearchCondition todoSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listMyTodoSearchDueDate", todoSearchCondition);
	}

	//Task에서 삭제 요청
	@SuppressWarnings("rawtypes")
	public void removeFromTask(Map map) {
		sqlDelete(NAMESPACE + "removeFromTask", map);
	}
	
	//오늘의 우선 업무(오늘 마감 + 지연)
	public List<Todo> listMyTodoDueToday(String workerId) {
		return sqlSelectForList(NAMESPACE + "listMyTodoDueToday", workerId);
	}
	//내일의 우선 업무(내일 마감 + 지연)
	public List<Todo> listMyTodoDueTomorrow(String workerId) {
		return sqlSelectForList(NAMESPACE + "listMyTodoDueTomorrow", workerId);
	}
	//나의 지시 목록(최근 7일)
	public List<Todo> listMyOrderDueWeek(String directorId) {
		return sqlSelectForList(NAMESPACE + "listMyOrderDueWeek", directorId);
	}
}

