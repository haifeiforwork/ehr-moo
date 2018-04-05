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

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TaskUser.java 16240 2011-08-18 04:08:15Z giljae $
 */

public class TaskUser extends TaskUserPk {

	private static final long serialVersionUID = -3784415174675516164L;
	
	/**
	 * 작업내용
	 */
	private String userContents;

	/**
	 * 첨부 파일 수
	 */
	private int userAttachCount = 0;

	/**
	 * 사용자 완료일
	 */
	private Date userCompleteDate;

	/**
	 * 사용자 상태
	 */
	private String userStatus;
	
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
	 * 작업자 이름
	 */
	private String workerName;

	/**
	 * 작업자 직급명
	 */
	private String workerTeamName;

	/**
	 * 작업자 팀명
	 */
	private String workerJobRankName;
	
	/**
	 * 작업자 이름(영어)
	 */
	private String workerEnglishName;

	/**
	 * 작업자 직급명(영어)
	 */
	private String workerTeamEnglishName;

	/**
	 * 작업자 팀명(영어)
	 */
	private String workerJobRankEnglishName;	

	/**
	 * 파일 업로드
	 */
	private List<FileLink> fileLinkList;

	/**
	 * 파일 다운로드
	 */
	private List<FileData> fileDataList;		

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
	 * @return the workerTeamName
	 */
	public String getWorkerTeamName() {
		return workerTeamName;
	}

	/**
	 * @param workerTeamName the workerTeamName to set
	 */
	public void setWorkerTeamName(String workerTeamName) {
		this.workerTeamName = workerTeamName;
	}

	/**
	 * @return the workerJobRankName
	 */
	public String getWorkerJobRankName() {
		return workerJobRankName;
	}

	/**
	 * @param workerJobRankName the workerJobRankName to set
	 */
	public void setWorkerJobRankName(String workerJobRankName) {
		this.workerJobRankName = workerJobRankName;
	}

	/**
	 * @return the userContents
	 */
	public String getUserContents() {
		return userContents;
	}

	/**
	 * @param userContents the userContents to set
	 */
	public void setUserContents(String userContents) {
		this.userContents = userContents;
	}

	/**
	 * @return the userAttachCount
	 */
	public int getUserAttachCount() {
		return userAttachCount;
	}

	/**
	 * @param userAttachCount the userAttachCount to set
	 */
	public void setUserAttachCount(int userAttachCount) {
		this.userAttachCount = userAttachCount;
	}

	/**
	 * @return the userCompleteDate
	 */
	public Date getUserCompleteDate() {
		return userCompleteDate;
	}

	/**
	 * @param userCompleteDate the userCompleteDate to set
	 */
	public void setUserCompleteDate(Date userCompleteDate) {
		this.userCompleteDate = userCompleteDate;
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

	/**
	 * @return the workerTeamEnglishName
	 */
	public String getWorkerTeamEnglishName() {
		return workerTeamEnglishName;
	}

	/**
	 * @param workerTeamEnglishName the workerTeamEnglishName to set
	 */
	public void setWorkerTeamEnglishName(String workerTeamEnglishName) {
		this.workerTeamEnglishName = workerTeamEnglishName;
	}

	/**
	 * @return the workerJobRankEnglishName
	 */
	public String getWorkerJobRankEnglishName() {
		return workerJobRankEnglishName;
	}

	/**
	 * @param workerJobRankEnglishName the workerJobRankEnglishName to set
	 */
	public void setWorkerJobRankEnglishName(String workerJobRankEnglishName) {
		this.workerJobRankEnglishName = workerJobRankEnglishName;
	}
}