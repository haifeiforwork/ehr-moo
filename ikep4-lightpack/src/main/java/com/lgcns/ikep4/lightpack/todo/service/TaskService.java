/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.service;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.todo.model.Task;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskService.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface TaskService extends GenericService<Task, String> {
	/**
	 * 작업 등록 조회
	 * @param taskId
	 * @return
	 */
	Task getTask(String taskId);
	/**
	 * 작업등록
	 * @param task
	 * @param user
	 * @return
	 */
	String createTask(Task task, User user);
	/**
	 * 작업수정
	 * @param task
	 * @param user
	 */
	void updateTask(Task task, User user);
	/**
	 * 작업삭제
	 * @param taskId
	 */
	void deleteTask(String taskId);
}
