/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: Pro.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class Pro extends BaseObject {
	private static final long serialVersionUID = 4182056323460781014L;

	/**
	 * 태그 ID
	 */
	private String tagId;

	/**
	 * 태그 아이템 ID
	 */
	private String tagItemId;

	/**
	 * 태그명
	 */
	private String tagName;

	int count;

	/**
	 * 사용자 ID
	 */
	private String userId;

	/**
	 * 사용자의 이메일 주소
	 */
	private String mail;

	/**
	 * 사용자의 이름
	 */
	private String userName;

	/**
	 * 사용자의 영어이름
	 */
	private String userEnglishName;

	/**
	 * 이 사용자가 해당된 부서(그룹) 이름
	 */
	private String teamName;

	/**
	 * 이 사용자가 해당된 부서(그룹) 영어이름
	 */
	private String teamEnglishName;

	/**
	 * 사용자의 모바일폰 번호
	 */
	private String mobile;

	/**
	 * 사용자의 호칭코드
	 */
	private String jobTitleCode;

	/**
	 * 해당 호칭코드의 이름
	 */
	private String jobTitleName;

	/**
	 * 해당 호칭코드의 영어이름
	 */
	private String jobTitleEnglishName;

	/**
	 * 사용자의 프로필 사진 ID
	 */
	private String profilePictureId;

	/**
	 * 페이지 인덱스
	 */
	private int pageIndex;

	/**
	 * 페이지 전체 갯수
	 */
	private int pageCount;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagItemId() {
		return tagItemId;
	}

	public void setTagItemId(String tagItemId) {
		this.tagItemId = tagItemId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJobTitleCode() {
		return jobTitleCode;
	}

	public void setJobTitleCode(String jobTitleCode) {
		this.jobTitleCode = jobTitleCode;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	public String getProfilePictureId() {
		return profilePictureId;
	}

	public void setProfilePictureId(String profilePictureId) {
		this.profilePictureId = profilePictureId;
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
