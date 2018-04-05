/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Mblog VO
 * 
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Mblog.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Mblog extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 8577854112382198246L;

	/**
	 * 마이크로블로깅 게시글 ID
	 */
	private String mblogId;

	/**
	 * 마이크로블로깅 타입(0 : 원글, 1: 답글, 2:RETWEET 글)
	 */
	@NotNull
	private String mblogType;

	/**
	 * 마이크로블로깅 게시글 부모 ID
	 */
	private String parentMblogId;

	/**
	 * THREAD ID
	 */
	private String threadId;

	/**
	 * 마이크로블로깅 그룹 ID
	 */
	private String mbgroupId;

	/**
	 * RETWEET MBLOG ID
	 */
	private String retweetMblogId;

	/**
	 * 마이크로블로깅 내용
	 */
	@NotEmpty
	@Size(min = 1, max = 500)
	private String contents;

	/**
	 * 리트윗 허용여부
	 */
	private String isRetweetAllowed;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
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
	 * DISPLAY용 마이크로 블로깅 내용(축약된 ADDON URL포함)
	 */
	private String contentsDisplay;

	/*
	 * ******************************************************************
	 * 외부 sns 연동필드
	 * ******************************************************************
	 */

	/**
	 * twitter 연동시
	 */
	private String twitter;

	/**
	 * facebook 연동시
	 */
	private String facebook;

	/*
	 * ******************************************************************
	 * 검색결과 리턴용 필드
	 * ******************************************************************
	 */

	/**
	 * ADDON_CODE 랜덤스트링(4자)+Sequence(1000부터)
	 */
	private String addonCode;

	/**
	 * ADDON_TYPE에 따른 DISPLAY
	 */
	private String displayCode;

	/**
	 * 원본 링크 URL
	 */
	private String sourceLink;

	/**
	 * 실제파일명 - IKEP4_DM_FILE table에 있는 필드
	 */
	private String fileRealName;

	/**
	 * 로그인 사용자가 favorite 등록한 글인지 여부
	 */
	private String isFavorite;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;

	public String getMblogId() {
		return mblogId;
	}

	public void setMblogId(String mblogId) {
		this.mblogId = mblogId;
	}

	public String getMblogType() {
		return mblogType;
	}

	public void setMblogType(String mblogType) {
		this.mblogType = mblogType;
	}

	public String getParentMblogId() {
		return parentMblogId;
	}

	public void setParentMblogId(String parentMblogId) {
		this.parentMblogId = parentMblogId;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getMbgroupId() {
		return mbgroupId;
	}

	public void setMbgroupId(String mbgroupId) {
		this.mbgroupId = mbgroupId;
	}

	public String getRetweetMblogId() {
		return retweetMblogId;
	}

	public void setRetweetMblogId(String retweetMblogId) {
		this.retweetMblogId = retweetMblogId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getIsRetweetAllowed() {
		return isRetweetAllowed;
	}

	public void setIsRetweetAllowed(String isRetweetAllowed) {
		this.isRetweetAllowed = isRetweetAllowed;
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

	public String getContentsDisplay() {
		return contentsDisplay;
	}

	public void setContentsDisplay(String contentsDisplay) {
		this.contentsDisplay = contentsDisplay;
	}

	public String getAddonCode() {
		return addonCode;
	}

	public void setAddonCode(String addonCode) {
		this.addonCode = addonCode;
	}

	public String getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}

	public String getFileRealName() {
		return fileRealName;
	}

	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	public String getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}

	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder().append("[mblogId:").append(mblogId).append(",mblogType:")
				.append(mblogType).append(",parentMblogId:").append(parentMblogId).append(",threadId:")
				.append(threadId).append(",mbgroupId:").append(mbgroupId).append(",retweetMblogId:")
				.append(retweetMblogId).append(",contents:").append(contents).append(",isRetweetAllowed:")
				.append(isRetweetAllowed).append(",registerId:").append(registerId).append(",registerName:")
				.append(registerName).append(",registDate:").append(registDate).append(",contentsDisplay:")
				.append(contentsDisplay).append(",addonCode:").append(addonCode).append(",sourceLink:")
				.append(sourceLink).append(",fileRealName:").append(fileRealName).append("]");
		return str.toString();
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
