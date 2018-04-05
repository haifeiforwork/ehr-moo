/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: MyOrder.java 16240 2011-08-18 04:08:15Z giljae $
 */

public class MyOrder extends BaseObject {

	private static final long serialVersionUID = -677509730946510038L;
	
	/**
	 * 시스템 코드명
	 */
	private String systemName;

	/**
	 * 업무종류명
	 */
	private String subworkName;

	/**
	 * 업무코드명
	 */
	private String subworkCode;
	
	/**
	 * 과업키
	 */
	private String taskId;

	/**
	 * 작업자명
	 */
	private String workerName;
	
	/**
	 * 제목
	 */
	private String title;

	/**
	 * 시작일
	 */
	private Date startDate;

	/**
	 * 마감일
	 */
	private Date dueDate;
	
	/**
	 * 과업 상태
	 */
	private String userStatus;
	
	/**
	 * 지연 일수
	 */
	private String overDayCount;
	
	/**
	 * 작업자수
	 */
	private String workersCount;

	/**
	 * 작업자명(영어)
	 */
	private String workerEnglishName;

	/**
	 * @return the workersCount
	 */
	public String getWorkersCount() {
		return workersCount;
	}

	/**
	 * @param workersCount the workersCount to set
	 */
	public void setWorkersCount(String workersCount) {
		this.workersCount = workersCount;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @return the subworkName
	 */
	public String getSubworkName() {
		return subworkName;
	}

	/**
	 * @param subworkName the subworkName to set
	 */
	public void setSubworkName(String subworkName) {
		this.subworkName = subworkName;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the workerName
	 */
	public String getWorkerName() {
		return workerName;
	}

	/**
	 * @param workerName the workerName to set
	 */
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return the overDayCount
	 */
	public String getOverDayCount() {
		return overDayCount;
	}

	/**
	 * @param overDayCount the overDayCount to set
	 */
	public void setOverDayCount(String overDayCount) {
		this.overDayCount = overDayCount;
	}

	/**
	 * @return the workerEnglishName
	 */
	public String getWorkerEnglishName() {
		return workerEnglishName;
	}

	/**
	 * @param workerEnglishName the workerEnglishName to set
	 */
	public void setWorkerEnglishName(String workerEnglishName) {
		this.workerEnglishName = workerEnglishName;
	}

	public String getSubworkCode() {
		return subworkCode;
	}

	public void setSubworkCode(String subworkCode) {
		this.subworkCode = subworkCode;
	}
}