/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.todo.model.TaskUser;
import com.lgcns.ikep4.lightpack.todo.model.TaskUserPk;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskUserService.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface TaskUserService extends GenericService<TaskUser, TaskUserPk> {
	/**
	 * 할일 조회
	 * @param taskUserPk
	 * @return
	 */
	TaskUser getTaskUser(TaskUserPk taskUserPk);
	/**
	 * 작업담당자 정보 조회
	 * @param taskId
	 * @return
	 */
	List<TaskUser> listTaskUserByTaskId(String taskId);
	/**
	 * 할일 저장(임시저장, 완료저장)
	 * @param taskUser
	 * @param user
	 */
	void updateTaskUser(TaskUser taskUser, User user);
	void updateMyTaskUser(TaskUser taskUser, User user);
}
