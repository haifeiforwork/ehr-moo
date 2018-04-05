/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.todo.dao.TodoDao;
import com.lgcns.ikep4.lightpack.todo.model.TaskUser;
import com.lgcns.ikep4.lightpack.todo.model.Todo;
import com.lgcns.ikep4.lightpack.todo.model.TodoConstants;
import com.lgcns.ikep4.lightpack.todo.model.TodoPk;
import com.lgcns.ikep4.lightpack.todo.search.TodoSearchCondition;
import com.lgcns.ikep4.lightpack.todo.service.TaskUserService;
import com.lgcns.ikep4.lightpack.todo.service.TodoService;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TodoServiceImpl.java 17260 2012-01-26 02:13:23Z yu_hs $
 */
@Service 
@Transactional
public class TodoServiceImpl extends GenericServiceImpl<Todo, TodoPk> implements TodoService {
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private TodoDao todoDao;
	
    @Autowired
    private TimeZoneSupportService timeZoneSupportService;

	@Autowired
	private TaskUserService taskUserService;	

	public TodoPk create(Todo todo) {
		return todoDao.create(todo);
	}

	public boolean exists(TodoPk todoPk) {
		return todoDao.exists(todoPk);
	}

	public Todo read(TodoPk todoPk) {
		return todoDao.get(todoPk);
	}

	public void delete(TodoPk todoPk) {
		todoDao.remove(todoPk);
	}

	public void update(Todo todo) {
		todoDao.update(todo);
	}
	/////////////////////////////////////////////////////////
	private boolean isNotEmptyTodoKey(String systemId, String subworkId, String taskKey, String workerId) {
		if(StringUtil.isEmpty(systemId)) {
			return false;
		}
		if(StringUtil.isEmpty(subworkId)) {
			return false;
		}
		if(StringUtil.isEmpty(taskKey)) {
			return false;
		}
		if(StringUtil.isEmpty(workerId)) {
			return false;
		}
		return true;
	}
	private boolean isInSystem(TodoPk todoPk) {
		if(!todoDao.existsLegacy(todoPk)) {
			return false;
		}
		if(!todoDao.existsSubwork(todoPk)) {
			return false;
		}
		return true;
	}
	
	
	//INTERFACE 업무생성
	public String todoAssign(String systemId, String subworkId, String taskKey, String workerId,
			String title, String directorId, Date dueDate, String statusName, String url) {
		//1.유효성 검사
		if(!isNotEmptyTodoKey(systemId, subworkId, taskKey, workerId)) {
			return TodoConstants.TODO_ERROR_DATA_EMPTY + "TODO PRIMARY KEY VALUES";
		}
		if(StringUtil.isEmpty(title)) {
			return TodoConstants.TODO_ERROR_DATA_EMPTY + "TITLE VALUE";
		}
		if(StringUtil.isEmpty(directorId)) {
			return TodoConstants.TODO_ERROR_DATA_EMPTY + "DIRECTOR_ID VALUE";
		}
		if(StringUtil.isEmpty(statusName)) {
			return TodoConstants.TODO_ERROR_DATA_EMPTY + "STATUS_NAME VALUE";
		}
		if(StringUtil.isEmpty(url)) {
			return TodoConstants.TODO_ERROR_DATA_EMPTY + "URL VALUE";
		}

		Todo todo = new Todo();
		todo.setSystemCode(systemId);
		todo.setSubworkCode(subworkId);
		todo.setTaskKey(taskKey);
		todo.setWorkerId(workerId);
		todo.setTitle(title);
		todo.setDirectorId(directorId);
		todo.setDueDate(dueDate);
		todo.setStatusName(statusName);
		todo.setUrl(url);
		
		if(!isInSystem((TodoPk)todo)) {
			return TodoConstants.TODO_ERROR_KEY_NOT_FOUND;
		}
		if(todoDao.exists((TodoPk)todo)) {
			return TodoConstants.TODO_ERROR_KEY_NOT_FOUND;
		}
		//2.업무처리
		todoDao.create(todo);
		
		return "";
	}
	//INTERFACE 업무완료
	public String todoComplete(String systemId, String subworkId, String taskKey, String workerId) {
		//1.유효성 검사
		if(!isNotEmptyTodoKey(systemId, subworkId, taskKey, workerId)) {
			return TodoConstants.TODO_ERROR_DATA_EMPTY + "TODO PRIMARY KEY VALUES";
		}

		TodoPk todoPk = new TodoPk();
		todoPk.setSystemCode(systemId);
		todoPk.setSubworkCode(subworkId);
		todoPk.setTaskKey(taskKey);
		todoPk.setWorkerId(workerId);

		if(!isInSystem(todoPk)) {
			return TodoConstants.TODO_ERROR_KEY_NOT_FOUND;
		}
		if(!todoDao.exists(todoPk)) {
			return TodoConstants.TODO_ERROR_KEY_NOT_FOUND;
		}
		
		//2.업무처리
		todoDao.updateStatusComplete(todoPk);
		
		return "";
	}
	
	//INTERFACE 업무삭제	
	public String todoDelete(String systemId, String subworkId, String taskKey, String workerId) {
		//1.유효성 검사
		if(!isNotEmptyTodoKey(systemId, subworkId, taskKey, workerId)) {
			return TodoConstants.TODO_ERROR_DATA_EMPTY + "TODO PRIMARY KEY VALUES";
		}

		TodoPk todoPk = new TodoPk();
		todoPk.setSystemCode(systemId);
		todoPk.setSubworkCode(subworkId);
		todoPk.setTaskKey(taskKey);
		todoPk.setWorkerId(workerId);

		if(!isInSystem(todoPk)) {
			return TodoConstants.TODO_ERROR_KEY_NOT_FOUND;
		}
		if(!todoDao.exists(todoPk)) {
			return TodoConstants.TODO_ERROR_KEY_NOT_FOUND;
		}
		
		//2.업무처리
		todoDao.remove(todoPk);
		
		return "";
	}
	//INTERFACE 오늘의 우선 업무(오늘 마감 + 지연)
	public List<Todo> listMyTodoDueToday(String workerId) {
		return todoDao.listMyTodoDueToday(workerId); 
	}
	//INTERFACE 내일의 우선 업무(내일 마감 + 지연)
	public List<Todo> listMyTodoDueTomorrow(String workerId) {
		return todoDao.listMyTodoDueTomorrow(workerId); 
	}
	//INTERFACE 나의 지시 목록(최근 7일)
	public List<Todo> listMyOrderDueWeek(String directorId) {
		return todoDao.listMyOrderDueWeek(directorId); 
	}	
	
	//나의 할 일만 카운트
	public Integer countMyTodo(String id) {
		return todoDao.countMyTodo(id);
	}
	
	//나의 업무만 카운트
	public Integer countMyTask(String id) {
		return todoDao.countMyTask(id);
	}
	//todo 검색
	public SearchResult<Todo> listMyTodoSearch(TodoSearchCondition todoSearchCondition) {
		//timeZone 적용 
		if(todoSearchCondition.getFromDay() != null) {
	        todoSearchCondition.setFromDay(timeZoneSupportService.convertServerTimeZone(todoSearchCondition.getFromDay()));
		}
		if(todoSearchCondition.getToDay() != null) {
	        todoSearchCondition.setToDay(timeZoneSupportService.convertServerTimeZone(todoSearchCondition.getToDay()));
		}
		
		Integer count = todoDao.countMyTodoSearch(todoSearchCondition);
		todoSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<Todo> searchResult = null; 
		if(todoSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Todo>(todoSearchCondition);
		} else {
			List<Todo> myTodoList = todoDao.listMyTodoSearch(todoSearchCondition); 
			searchResult = new SearchResult<Todo>(myTodoList, todoSearchCondition);  			
		} 

		//timeZone 해지
		if(todoSearchCondition.getFromDay() != null) {
	        todoSearchCondition.setFromDay(timeZoneSupportService.convertTimeZone(todoSearchCondition.getFromDay()));
		}
		if(todoSearchCondition.getToDay() != null) {
	        todoSearchCondition.setToDay(timeZoneSupportService.convertTimeZone(todoSearchCondition.getToDay()));
		}		
		
		return searchResult;
	}
	
	//todo 검색
	public SearchResult<Todo> listMyTodoSearchDueDate(TodoSearchCondition todoSearchCondition) {
		//timeZone 적용 
		if(todoSearchCondition.getFromDay() != null) {
	        todoSearchCondition.setFromDay(timeZoneSupportService.convertServerTimeZone(todoSearchCondition.getFromDay()));
		}
		if(todoSearchCondition.getToDay() != null) {
	        todoSearchCondition.setToDay(timeZoneSupportService.convertServerTimeZone(todoSearchCondition.getToDay()));
		}
		
		Integer count = todoDao.countMyTodoSearchDueDate(todoSearchCondition);
		todoSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<Todo> searchResult = null; 
		if(todoSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Todo>(todoSearchCondition);
		} else {
			List<Todo> myTodoList = todoDao.listMyTodoSearchDueDate(todoSearchCondition); 
			searchResult = new SearchResult<Todo>(myTodoList, todoSearchCondition);  			
		} 

		//timeZone 해지
		if(todoSearchCondition.getFromDay() != null) {
	        todoSearchCondition.setFromDay(timeZoneSupportService.convertTimeZone(todoSearchCondition.getFromDay()));
		}
		if(todoSearchCondition.getToDay() != null) {
	        todoSearchCondition.setToDay(timeZoneSupportService.convertTimeZone(todoSearchCondition.getToDay()));
		}		
		
		return searchResult;
	}
	//나의 할 일
	public List<Todo> listMyTodo(String id, int rowNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workerId", id);
		map.put("rowNum", rowNum);
		return todoDao.listMyTodo(map);
	}

	//todo 자동완료
	public void processTodoAutoComplete(List<TodoPk> todoPkList, User user){
		for(int i=0; i<todoPkList.size(); i++) {
			//작업등록 관련이면 
			if(todoPkList.get(i).getSystemCode().equals(TodoConstants.TODO_SYSTEM_CODE) && todoPkList.get(i).getSubworkCode().equals(TodoConstants.TODO_SUBWORK_CODE)) { 
				TaskUser taskUser = new TaskUser();
				taskUser.setWorkerId(todoPkList.get(i).getWorkerId());
				taskUser.setTaskId(todoPkList.get(i).getTaskKey());
				taskUser.setUserContents("");
				taskUser.setUserAttachCount(0);
				taskUser.setUserStatus("B");
				taskUser.setUpdaterId(user.getUserId());
				taskUser.setUpdaterName(user.getUserName());
				taskUserService.updateTaskUser(taskUser, user);
			} else {
				todoComplete(todoPkList.get(i).getSystemCode(), todoPkList.get(i).getSubworkCode(), todoPkList.get(i).getTaskKey(), todoPkList.get(i).getWorkerId());
			}
		}		
	}
	
	//Task에서 삭제 요청
	public void deleteFromTask(String systemId, String subworkId, String taskKey) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("systemCode", systemId);
		map.put("subworkCode", subworkId);
		map.put("taskKey", taskKey);
		todoDao.removeFromTask(map);
	}
}