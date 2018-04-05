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
import com.lgcns.ikep4.lightpack.todo.model.TaskUser;
import com.lgcns.ikep4.lightpack.todo.model.TaskUserPk;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskUserDao.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface TaskUserDao extends GenericDao<TaskUser, TaskUserPk> {
	/**
	 * 할일 조회
	 * @param taskUserPk
	 * @return
	 */
	TaskUser getTaskUser(TaskUserPk taskUserPk);
	/**
	 * 과업별 반대 상태  갯수
	 * @param taskUser
	 * @return
	 */
	int countRestReverseStatus(TaskUser taskUser);
	/**
	 * 작업등록 수정에 의한 삭제 대상자 조회
	 * @param map
	 * @return
	 */
	List<TaskUser> listDeleteWorker(Map<String, Object> map);
	/**
	 * 태스크 아이디로 대상자 조회
	 * @param taskId
	 * @return
	 */
	List<TaskUser> listTaskUserByTaskId(String taskId);
	/**
	 * TaskId로 삭제
	 * @param taskId
	 */
	void removeByTaskId(String taskId);
}
