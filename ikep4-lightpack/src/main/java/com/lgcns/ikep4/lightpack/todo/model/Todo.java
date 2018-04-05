/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: Todo.java 16240 2011-08-18 04:08:15Z giljae $
 */

public class Todo extends TodoPk {

	private static final long serialVersionUID = 8302198034835149162L;

	/**
	 * 제목
	 */
	private String title;

	/**
	 * 지시자 ID
	 */
	private String directorId;

	/**
	 * 작업 상태
	 */
	private String statusName;

	/**
	 * 마감일
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private Date dueDate;
	
	/**
	 * 완료여부( 0 : 미완료, 1 : 완료)
	 */
	private int isComplete = 0;

	/**
	 * URL
	 */
	private String url;

	/**
	 * 완료일
	 */
	private Date completeDate;

	/**
	 * 연계 데이터 입력 일시
	 */
	private Date syncInsertDate;

	/**
	 * 연계 데이터 수정 일시
	 */
	private Date syncUpdateDate;

	///////////////////////////////////////////////////////////
	/**
	 * 시스템 이름
	 */
	private String systemName;

	/**
	 * 업무종류 이름
	 */
	private String subworkName;

	/**
	 * 상태명
	 */
	private String todoStatusName;

	/**
	 * 상태코드
	 */
	private String todoStatus;

	/**
	 * 기타 이름
	 */
	private String etcName;
	
	/**
	 * 기타 이름(영어)
	 */
	private String etcEnglishName;

	/**
	 * 업무 화면 링크 타겟 정보(INNER:창내부, WINDOW:새창)
	 */
	private String target;
	
	/* 팀명 */
	private String  teamName;
	/* 팀명(영어) */
	private String  teamEnglishName;
	/* 직책명 */
	private String  jobTitleName;
	/* 직책명(영어) */
	private String  jobTitleEnglishName;	
	
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
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
	 * @return the directorId
	 */
	public String getDirectorId() {
		return directorId;
	}

	/**
	 * @param directorId the directorId to set
	 */
	public void setDirectorId(String directorId) {
		this.directorId = directorId;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	 * @return the isComplete
	 */
	public int getIsComplete() {
		return isComplete;
	}

	/**
	 * @param isComplete the isComplete to set
	 */
	public void setIsComplete(int isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the completeDate
	 */
	public Date getCompleteDate() {
		return completeDate;
	}

	/**
	 * @param completeDate the completeDate to set
	 */
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	/**
	 * @return the syncInsertDate
	 */
	public Date getSyncInsertDate() {
		return syncInsertDate;
	}

	/**
	 * @param syncInsertDate the syncInsertDate to set
	 */
	public void setSyncInsertDate(Date syncInsertDate) {
		this.syncInsertDate = syncInsertDate;
	}

	/**
	 * @return the syncUpdateDate
	 */
	public Date getSyncUpdateDate() {
		return syncUpdateDate;
	}

	/**
	 * @param syncUpdateDate the syncUpdateDate to set
	 */
	public void setSyncUpdateDate(Date syncUpdateDate) {
		this.syncUpdateDate = syncUpdateDate;
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
	 * @return the todoStatusName
	 */
	public String getTodoStatusName() {
		return todoStatusName;
	}

	/**
	 * @param todoStatusName the todoStatusName to set
	 */
	public void setTodoStatusName(String todoStatusName) {
		this.todoStatusName = todoStatusName;
	}

	/**
	 * @return the etcName
	 */
	public String getEtcName() {
		return etcName;
	}

	/**
	 * @param etcName the etcName to set
	 */
	public void setEtcName(String etcName) {
		this.etcName = etcName;
	}

	/**
	 * @return the etcEnglishName
	 */
	public String getEtcEnglishName() {
		return etcEnglishName;
	}

	/**
	 * @param etcEnglishName the etcEnglishName to set
	 */
	public void setEtcEnglishName(String etcEnglishName) {
		this.etcEnglishName = etcEnglishName;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the teamEnglishName
	 */
	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	/**
	 * @param teamEnglishName the teamEnglishName to set
	 */
	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	/**
	 * @return the jobTitleName
	 */
	public String getJobTitleName() {
		return jobTitleName;
	}

	/**
	 * @param jobTitleName the jobTitleName to set
	 */
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	/**
	 * @return the jobTitleEnglishName
	 */
	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	/**
	 * @param jobTitleEnglishName the jobTitleEnglishName to set
	 */
	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	public String getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(String todoStatus) {
		this.todoStatus = todoStatus;
	}
}
