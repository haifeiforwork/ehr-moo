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
import com.lgcns.ikep4.lightpack.todo.dao.TaskUserDao;
import com.lgcns.ikep4.lightpack.todo.model.TaskUser;
import com.lgcns.ikep4.lightpack.todo.model.TaskUserPk;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskUserDaoImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Repository
public class TaskUserDaoImpl extends GenericDaoSqlmap<TaskUser, TaskUserPk> implements TaskUserDao {
	private static final String NAMESPACE = "lightpack.todo.dao.taskUser.";
	
	public TaskUserPk create(TaskUser taskUser) {
		sqlInsert(NAMESPACE + "create", taskUser);
		return (TaskUserPk) taskUser; 
	}

	public boolean exists(TaskUserPk taskUserPk) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", taskUserPk);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public TaskUser get(TaskUserPk taskUserPk) {
		return (TaskUser) sqlSelectForObject(NAMESPACE + "get", taskUserPk);
	}

	public void remove(TaskUserPk taskUserPk) {
		sqlDelete(NAMESPACE + "remove", taskUserPk);
	}

	public void update(TaskUser taskUser) {
		sqlUpdate(NAMESPACE + "update", taskUser);
	}

	public TaskUser getTaskUser(TaskUserPk taskUserPk) {
		return (TaskUser) sqlSelectForObject(NAMESPACE + "getTaskUser", taskUserPk);
	}

	//과업별 반대 상태  갯수
	public int countRestReverseStatus(TaskUser taskUser) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countRestReverseStatus", taskUser);
	}

	//작업등록 수정에 의한 삭제 대상자 조회
	@SuppressWarnings("rawtypes")
	public List<TaskUser> listDeleteWorker(Map map) {
		return sqlSelectForList(NAMESPACE + "listDeleteWorker", map);
	}
	
	public List<TaskUser> listTaskUserByTaskId(String taskId) {
		return sqlSelectForList(NAMESPACE + "listTaskUserByTaskId", taskId);
	}
	
	//TaskId로 삭제
	public void removeByTaskId(String taskId) {
		sqlDelete(NAMESPACE + "removeByTaskId", taskId);
	}
}

