/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 댓글 정보
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaLinereply.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class QnaLinereply extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 3422851501328767233L;

	/**
	 * qna Id
	 */
	private String qnaId;

	/**
	 * Id
	 */
	private String linereplyId;

	/**
	 * Id
	 */
	private String linereplyParentId;

	/**
	 * 한줄 reply 내용
	 */
	@NotEmpty
	@Size(min = 1, max = 300)
	private String contents;

	/**
	 * 등록자 Id
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록날짜
	 */
	private Date registDate;

	/**
	 * 수정자 Id
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 
	 */
	private Date updateDate;

	/**
	 * 
	 */
	private String linereplyGroupId;

	/**
	 * 
	 */
	private int step = 0;

	/**
	 * 
	 */
	private int indentation = 0;

	/**
	 * 
	 */
	private int linereplyDelete;

	/**
	 * 사용자 팀이름
	 */
	private String teamName;

	/**
	 * 직책
	 */
	private String jobTitleName;

	/**
	 * 영문 이름
	 */
	private String userEnglishName;

	/**
	 * 영문 팀명
	 */
	private String teamEnglishName;

	/**
	 * 영문직책
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

	public String getQnaId() {
		return qnaId;
	}

	public void setQnaId(String qnaId) {
		this.qnaId = qnaId;
	}

	public String getLinereplyId() {
		return linereplyId;
	}

	public void setLinereplyId(String linereplyId) {
		this.linereplyId = linereplyId;
	}

	public String getLinereplyParentId() {
		return linereplyParentId;
	}

	public void setLinereplyParentId(String linereplyParentId) {
		this.linereplyParentId = linereplyParentId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getLinereplyGroupId() {
		return linereplyGroupId;
	}

	public void setLinereplyGroupId(String linereplyGroupId) {
		this.linereplyGroupId = linereplyGroupId;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getIndentation() {
		return indentation;
	}

	public void setIndentation(int indentation) {
		this.indentation = indentation;
	}

	public int getLinereplyDelete() {
		return linereplyDelete;
	}

	public void setLinereplyDelete(int linereplyDelete) {
		this.linereplyDelete = linereplyDelete;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

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