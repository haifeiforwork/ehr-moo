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
 * @version $Id: UserSetting.java 16240 2011-08-18 04:08:15Z giljae $
 */

public class UserSetting extends BaseObject {
	private static final long serialVersionUID = 16496683791919785L;

	/**
	 * 사용자ID
	 */
	private String userId;

	/**
	 * 한번에 보이는 개수
	 */
	private int pageViewNum = 10;

	/**
	 * 지시작업종료 알림 설정
	 */
	private String taskEndNotiType = "N";

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the pageViewNum
	 */
	public int getPageViewNum() {
		return pageViewNum;
	}

	/**
	 * @param pageViewNum the pageViewNum to set
	 */
	public void setPageViewNum(int pageViewNum) {
		this.pageViewNum = pageViewNum;
	}

	/**
	 * @return the taskEndNotiType
	 */
	public String getTaskEndNotiType() {
		return taskEndNotiType;
	}

	/**
	 * @param taskEndNotiType the taskEndNotiType to set
	 */
	public void setTaskEndNotiType(String taskEndNotiType) {
		this.taskEndNotiType = taskEndNotiType;
	}
}