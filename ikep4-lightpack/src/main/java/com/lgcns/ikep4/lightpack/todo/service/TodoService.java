/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.service;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoPk;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TodoService.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface TodoService extends GenericService<Todo, TodoPk> {
	/**
	 *INTERFACE 업무생성
	 * @param systemId
	 * @param subworkId
	 * @param taskKey
	 * @param workerId
	 * @param title
	 * @param directorId
	 * @param dueDate
	 * @param statusName
	 * @param url
	 * @return
	 */
	String todoAssign(String systemId, String subworkId, String taskKey, String workerId, String title, String directorId, Date dueDate, String statusName, String url);
	/**
	 *INTERFACE 업무완료
	 * @param systemId
	 * @param subworkId
	 * @param taskKey
	 * @param workerId
	 * @return
	 */
	String todoComplete(String systemId, String subworkId, String taskKey, String workerId);
	/**
	 *INTERFACE 업무삭제	
	 * @param systemId
	 * @param subworkId
	 * @param taskKey
	 * @param workerId
	 * @return
	 */
	String todoDelete(String systemId, String subworkId, String taskKey, String workerId);
	/**
	 *INTERFACE 오늘의 우선 업무(오늘 마감 + 지연)
	 * @param workerId
	 * @return
	 */
	List<Todo> listMyTodoDueToday(String workerId);
	/**
	 *INTERFACE 내일의 우선 업무(내일 마감 + 지연)
	 * @param workerId
	 * @return
	 */
	List<Todo> listMyTodoDueTomorrow(String workerId);
	/**
	 *INTERFACE 나의 지시 목록(최근 7일)
	 * @param directorId
	 * @return
	 */
	List<Todo> listMyOrderDueWeek(String directorId);
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
	 *todo 검색
	 * @param todoSearchCondition
	 * @return
	 */
	SearchResult<Todo> listMyTodoSearch(TodoSearchCondition todoSearchCondition);	
	/**
	 *todo 검색
	 * @param todoSearchCondition
	 * @return
	 */
	SearchResult<Todo> listMyTodoSearchDueDate(TodoSearchCondition todoSearchCondition);	
	/**
	 *나의 할 일
	 * @param id
	 * @param rowNum
	 * @return
	 */
	List<Todo> listMyTodo(String id, int rowNum);
	/**
	 *todo 자동완료
	 * @param todoPkList
	 * @param user
	 */
	void processTodoAutoComplete(List<TodoPk> todoPkList, User user);
	/**
	 *Task에서 삭제 요청
	 * @param systemId
	 * @param subworkId
	 * @param taskKey
	 */
	void deleteFromTask(String systemId, String subworkId, String taskKey);
}
