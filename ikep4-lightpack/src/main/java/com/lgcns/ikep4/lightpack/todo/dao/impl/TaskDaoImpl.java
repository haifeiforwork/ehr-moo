/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.todo.dao.TaskDao;
import com.lgcns.ikep4.lightpack.todo.model.Task;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskDaoImpl.java 16297 2011-08-19 07:52:43Z giljae $
 */
@Repository
public class TaskDaoImpl extends GenericDaoSqlmap<Task, String> implements TaskDao {
	private static final String NAMESPACE = "lightpack.todo.dao.task.";
	
	public String create(Task task) {
		return (String) sqlInsert(NAMESPACE + "create", task);
	}

	public boolean exists(String taskId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", taskId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public Task get(String taskId) {
		return (Task) sqlSelectForObject(NAMESPACE + "get", taskId);
	}

	public void remove(String taskId) {
		sqlDelete(NAMESPACE + "remove", taskId);
	}

	public void update(Task task) {
		sqlUpdate(NAMESPACE + "update", task);
	}
	
	public Task getTask(String taskId) {
		return (Task) sqlSelectForObject(NAMESPACE + "getTask", taskId);
	}
	
	//작업완료 업데이트
	public void updateCompleteStatus(Task task) {
		sqlUpdate(NAMESPACE + "updateCompleteStatus", task);
	}
}

