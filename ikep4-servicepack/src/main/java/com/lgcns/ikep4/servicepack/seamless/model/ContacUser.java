package com.lgcns.ikep4.servicepack.seamless.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


public class ContacUser extends BaseObject {

	private static final long serialVersionUID = -1232392795474649318L;

	/**
	 * 사용자 ID
	 */
	private String userId;

	/**
	 * 사용자 이름
	 */
	private String userName;

	/**
	 * 사용자_영문_이름
	 */
	private String userEnglishName;

	/**
	 * 사용자 메일
	 */
	private String mail;

	/**
	 * 팀명
	 */
	private String teamName;

	/**
	 * 영문_팀명
	 */
	private String teamEnglishName;

	/**
	 * 사용자 휴대번호
	 */
	private String mobile;

	/**
	 * 사용자 사무실 번호
	 */
	private String officePhoneNo;

	/**
	 * 사용자 직급명
	 */
	private String jobTitleName;

	/**
	 * 사용자 영문_직급명
	 */
	private String jobTitleEnglishName;

	/**
	 * 즐겨찾기등록여부
	 */
	private Integer favoriteYn;

	/**
	 * fllow 여부
	 */
	private Integer followYn;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficePhoneNo() {
		return officePhoneNo;
	}

	public void setOfficePhoneNo(String officePhoneNo) {
		this.officePhoneNo = officePhoneNo;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public Integer getFavoriteYn() {
		return favoriteYn;
	}

	public void setFavoriteYn(Integer favoriteYn) {
		this.favoriteYn = favoriteYn;
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

	public Integer getFollowYn() {
		return followYn;
	}

	public void setFollowYn(Integer followYn) {
		this.followYn = followYn;
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
