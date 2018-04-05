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
 * 
 * MbgroupMember VO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: MbgroupMember.java 16489 2011-09-06 01:41:09Z giljae $
 */

public class MbgroupMember extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -803113213427139116L;

	/**
	 * 멤버의 유저 아이디
	 */
	private String memberId;
	
	/**
	 * 마이크로블로깅그룹 아이디
	 */
	private String mbgroupId;

	/**
	 * STATUS
	 */
	private String status;

	/**
	 * 등록자 아이디
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록자 이름(영문)
	 */
	private String registerEnglishName;
	
	/**
	 * 등록일
	 */
	private Date registDate;
	
	/**
	 * 사용자의 프로필 사진 경로 (큰이미지)
	 */
	private String picturePath;
	
	/**
	 * 사용자의 프로필 사진 경로(작은이미지)
	 */
	private String profilePicturePath;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMbgroupId() {
		return mbgroupId;
	}

	public void setMbgroupId(String mbgroupId) {
		this.mbgroupId = mbgroupId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
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
