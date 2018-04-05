/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: LineReply.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class LineReply extends BaseObject {
	private static final long serialVersionUID = 7359480235265710963L;

	/**
	 * 댓글 ID
	 */
	private String linereplyId;

	/**
	 * 매뉴얼ID
	 */
	private String manualId;

	/**
	 * 댓글그룹ID
	 */
	private String linereplyGroupId;

	/**
	 * 댓글부모ID
	 */
	private String linereplyParentId;

	/**
	 * 같은 댓글 그룹 Thread에 속해있는 댓글들 간의 순서. 같은 Thread에서 단순 정렬 순서
	 */
	private Integer step = 0;

	/**
	 * 댓글 Thread의 LEVEL. Thread 표시할때 들여쓰기의 숫자
	 */
	private Integer indentation = 0;

	/**
	 * 댓글 내용
	 */
	private String linereplyContents;

	/**
	 * 댓글 삭제 여부
	 */
	private Integer isDelete = 0;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일시
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
	 * 수정일시
	 */
	private Date updateDate;

	/**
	 * 부서명
	 */
	private String teamName;

	/**
	 * 직급명
	 */
	private String jobTitleName;

	/**
	 * 등록자 이름(영어)
	 */
	private String registerEnglishName;

	/**
	 * 부서명(영어)
	 */
	private String teamEnglishName;

	/**
	 * 직급명(영어)
	 */
	private String jobTitleEnglishName;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;

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
	 * @return the linereplyId
	 */
	public String getLinereplyId() {
		return linereplyId;
	}

	/**
	 * @param linereplyId the linereplyId to set
	 */
	public void setLinereplyId(String linereplyId) {
		this.linereplyId = linereplyId;
	}

	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}

	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	/**
	 * @return the linereplyGroupId
	 */
	public String getLinereplyGroupId() {
		return linereplyGroupId;
	}

	/**
	 * @param linereplyGroupId the linereplyGroupId to set
	 */
	public void setLinereplyGroupId(String linereplyGroupId) {
		this.linereplyGroupId = linereplyGroupId;
	}

	/**
	 * @return the linereplyParentId
	 */
	public String getLinereplyParentId() {
		return linereplyParentId;
	}

	/**
	 * @param linereplyParentId the linereplyParentId to set
	 */
	public void setLinereplyParentId(String linereplyParentId) {
		this.linereplyParentId = linereplyParentId;
	}

	/**
	 * @return the step
	 */
	public Integer getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(Integer step) {
		this.step = step;
	}

	/**
	 * @return the indentation
	 */
	public Integer getIndentation() {
		return indentation;
	}

	/**
	 * @param indentation the indentation to set
	 */
	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}

	/**
	 * @return the linereplyContents
	 */
	public String getLinereplyContents() {
		return linereplyContents;
	}

	/**
	 * @param linereplyContents the linereplyContents to set
	 */
	public void setLinereplyContents(String linereplyContents) {
		this.linereplyContents = linereplyContents;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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
	 * @return the registerEnglishName
	 */
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	/**
	 * @param registerEnglishName the registerEnglishName to set
	 */
	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
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

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getProfilePicturePath() {
		return profilePicturePath;
	}

	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}

}
