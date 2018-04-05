/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Follow VO
 * 
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Follow.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Follow extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 6819221763187598143L;

	/**
	 * follow 하는 사용자 ID
	 */
	private String userId;

	/**
	 * follow 되는 사용자 ID
	 */
	private String followingUserId;

	/**
	 * 등록일
	 */
	private Date registDate;

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

	public String getFollowingUserId() {
		return followingUserId;
	}

	public void setFollowingUserId(String followingUserId) {
		this.followingUserId = followingUserId;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
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
