/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TodoPk.java 16240 2011-08-18 04:08:15Z giljae $
 */

public class TodoPk extends BaseObject {

	private static final long serialVersionUID = 8302198034835149162L;

	/**
	 * 시스템 코드
	 */
	private String systemCode;

	/**
	 * 업무종류
	 */
	private String subworkCode;

	/**
	 * 과업키
	 */
	private String taskKey;

	/**
	 * 작업자 ID
	 */
	private String workerId;

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @return the subworkCode
	 */
	public String getSubworkCode() {
		return subworkCode;
	}

	/**
	 * @param subworkCode the subworkCode to set
	 */
	public void setSubworkCode(String subworkCode) {
		this.subworkCode = subworkCode;
	}

	/**
	 * @return the taskKey
	 */
	public String getTaskKey() {
		return taskKey;
	}

	/**
	 * @param taskKey the taskKey to set
	 */
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	/**
	 * @return the workerId
	 */
	public String getWorkerId() {
		return workerId;
	}

	/**
	 * @param workerId the workerId to set
	 */
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
}
