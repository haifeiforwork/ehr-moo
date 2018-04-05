/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoPk;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TodoDao.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public interface TodoDao extends GenericDao<Todo, TodoPk> {
	/**
	 * 연계시스템 존재 여부
	 * @param todoPk
	 * @return
	 */
	boolean existsLegacy(TodoPk todoPk);
	/**
	 * 업무종류 존재 여부
	 * @param todoPk
	 * @return
	 */
	boolean existsSubwork(TodoPk todoPk);
	/**
	 * 할일 완료 업데이트
	 * @param todoPk
	 */
	void updateStatusComplete(TodoPk todoPk);
	/**
	 * 나의 할 일만 카운트
	 * @param id
	 * @return
	 */
	Integer countMyTodo(String id);
	/**
	 * 나의 업무만 카운트
	 * @param id
	 * @return
	 */
	Integer countMyTask(String id);
	/**
	 * 나의 할 일만 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Todo> listMyTodo(Map map);
	/**
	 * 나의 할 일 조회-검색
	 * @param todoSearchCondition
	 */
	Integer countMyTodoSearch(TodoSearchCondition todoSearchCondition);
	/**
	 * 나의 할 일 조회-검색
	 * @param todoSearchCondition
	 */
	Integer countMyTodoSearchDueDate(TodoSearchCondition todoSearchCondition);
	/**
	 * 나의 할 일 조회
	 * @param todoSearchCondition
	 * @return
	 */
	List<Todo> listMyTodoSearch(TodoSearchCondition todoSearchCondition);
	/**
	 * 나의 할 일 조회
	 * @param todoSearchCondition
	 * @return
	 */
	List<Todo> listMyTodoSearchDueDate(TodoSearchCondition todoSearchCondition);
	/**
	 * Task에서 삭제 요청
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	void removeFromTask(Map map);
	/**
	 * 오늘의 우선 업무(오늘 마감 + 지연)
	 * @param workerId
	 * @return
	 */
	List<Todo> listMyTodoDueToday(String workerId);
	/**
	 * 내일의 우선 업무(내일 마감 + 지연)
	 * @param workerId
	 * @return
	 */
	List<Todo> listMyTodoDueTomorrow(String workerId);
	/**
	 * 나의 지시 목록(최근 7일)
	 * @param directorId
	 * @return
	 */
	List<Todo> listMyOrderDueWeek(String directorId);
}
