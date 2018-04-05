/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: Task.java 16240 2011-08-18 04:08:15Z giljae $
 */

public class Task extends BaseObject {

	private static final long serialVersionUID = 16496683791919785L;

	/**
	 * 과업 ID
	 */
	private String taskId;

	/**
	 * 제목
	 */
	@NotEmpty
	@Size(min=1, max=100)
	private String title;

	/**
	 * 지시자 ID
	 */
	private String directorId;

	/**
	 * 시작일
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private Date startDate;

	/**
	 * 마감일
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private Date dueDate;

	/**
	 * 지시내용
	 */
	@NotEmpty
	@Size(min=1, max=1000)
	private String taskContents;

	/**
	 * 첨부파일수
	 */
	private int taskAttachCount = 0;

	/**
	 * 과업 상태
	 */
	private String taskStatus;
	
	private String subworkCode;

	/**
	 * 완료일
	 */
	private Date taskCompleteDate;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * 기타 이름
	 */
	private String etcName;

	/**
	 * 작업자들
	 */
	private List<String> workerList;

	/**
	 * 지시자 이름
	 */
	private String directorName;

	/**
	 * 지시자 직급명
	 */
	private String directorTeamName;

	/**
	 * 지시자 팀명
	 */
	private String directorJobRankName;
	/**
	 * 지시자 이름(영어)
	 */
	private String directorEnglishName;

	/**
	 * 지시자 직급명(영어)
	 */
	private String directorTeamEnglishName;

	/**
	 * 지시자 팀명(영어)
	 */
	private String directorJobRankEnglishName;	

	/**
	 * 파일 업로드
	 */
	private List<FileLink> fileLinkList;

	/**
	 * 파일 다운로드
	 */
	private List<FileData> fileDataList;	

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
	 * @return the taskContents
	 */
	public String getTaskContents() {
		return taskContents;
	}

	/**
	 * @param taskContents the taskContents to set
	 */
	public void setTaskContents(String taskContents) {
		this.taskContents = taskContents;
	}

	/**
	 * @return the taskAttachCount
	 */
	public int getTaskAttachCount() {
		return taskAttachCount;
	}

	/**
	 * @param taskAttachCount the taskAttachCount to set
	 */
	public void setTaskAttachCount(int taskAttachCount) {
		this.taskAttachCount = taskAttachCount;
	}

	/**
	 * @return the taskStatus
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * @return the taskCompleteDate
	 */
	public Date getTaskCompleteDate() {
		return taskCompleteDate;
	}

	/**
	 * @param taskCompleteDate the taskCompleteDate to set
	 */
	public void setTaskCompleteDate(Date taskCompleteDate) {
		this.taskCompleteDate = taskCompleteDate;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
	 * @return the workerList
	 */
	public List<String> getWorkerList() {
		return workerList;
	}

	/**
	 * @param workerList the workerList to set
	 */
	public void setWorkerList(List<String> workerList) {
		this.workerList = workerList;
	}

	/**
	 * @return the directorName
	 */
	public String getDirectorName() {
		return directorName;
	}

	/**
	 * @param directorName the directorName to set
	 */
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	/**
	 * @return the directorTeamName
	 */
	public String getDirectorTeamName() {
		return directorTeamName;
	}

	/**
	 * @param directorTeamName the directorTeamName to set
	 */
	public void setDirectorTeamName(String directorTeamName) {
		this.directorTeamName = directorTeamName;
	}

	/**
	 * @return the directorJobRankName
	 */
	public String getDirectorJobRankName() {
		return directorJobRankName;
	}

	/**
	 * @param directorJobRankName the directorJobRankName to set
	 */
	public void setDirectorJobRankName(String directorJobRankName) {
		this.directorJobRankName = directorJobRankName;
	}

	/**
	 * @return the fileLinkList
	 */
	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	/**
	 * @param fileLinkList the fileLinkList to set
	 */
	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	/**
	 * @return the fileDataList
	 */
	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	/**
	 * @param fileDataList the fileDataList to set
	 */
	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	/**
	 * @return the directorEnglishName
	 */
	public String getDirectorEnglishName() {
		return directorEnglishName;
	}

	/**
	 * @param directorEnglishName the directorEnglishName to set
	 */
	public void setDirectorEnglishName(String directorEnglishName) {
		this.directorEnglishName = directorEnglishName;
	}

	/**
	 * @return the directorTeamEnglishName
	 */
	public String getDirectorTeamEnglishName() {
		return directorTeamEnglishName;
	}

	/**
	 * @param directorTeamEnglishName the directorTeamEnglishName to set
	 */
	public void setDirectorTeamEnglishName(String directorTeamEnglishName) {
		this.directorTeamEnglishName = directorTeamEnglishName;
	}

	/**
	 * @return the directorJobRankEnglishName
	 */
	public String getDirectorJobRankEnglishName() {
		return directorJobRankEnglishName;
	}

	/**
	 * @param directorJobRankEnglishName the directorJobRankEnglishName to set
	 */
	public void setDirectorJobRankEnglishName(String directorJobRankEnglishName) {
		this.directorJobRankEnglishName = directorJobRankEnglishName;
	}

	public String getSubworkCode() {
		return subworkCode;
	}

	public void setSubworkCode(String subworkCode) {
		this.subworkCode = subworkCode;
	} 
}