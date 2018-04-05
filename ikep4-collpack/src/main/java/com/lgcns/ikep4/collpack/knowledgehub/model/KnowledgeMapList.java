/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.model;

import java.util.List;

import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * Knowledge Map KnowledgeListPK model
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapList.java 16604 2011-09-22 08:15:14Z giljae $
 */
public class KnowledgeMapList extends KnowledgeMapListBody {

	/**
	 *
	 */
	private static final long serialVersionUID = 908473865566457447L;

	/**
	 * 등록자 이름 (Locale 관련)
	 */
	private String userName;

	/**
	 * 등록자 이름 (Locale 관련)
	 */
	private String userEnglishName;

	/**
	 * 팀명 (Locale 관련)
	 */
	private String teamName;

	/**
	 * 팀명 (Locale 관련)
	 */
	private String teamEnglishName;

	/**
	 * 직급명 (Locale 관련)
	 */
	private String jobTitleName;

	/**
	 * 직급명 (Locale 관련)
	 */
	private String jobTitleEnglishName;

	/**
	 * itemType 명칭
	 */
	private String itemTypeDisplayName;

	/**
	 * Tag 정보
	 */
	private List<Tag> tagList;

	/**
	 * 사용자의 프로필 사진 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사용자의 프로필 사진 경로(작은이미지)
	 */
	private String profilePicturePath;

	/**
	 * @return the tagList
	 */
	public List<Tag> getTagList() {
		return tagList;
	}

	/**
	 * @param tagList
	 *            the tagList to set
	 */
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userEnglishName
	 */
	public String getUserEnglishName() {
		return userEnglishName;
	}

	/**
	 * @param userEnglishName
	 *            the userEnglishName to set
	 */
	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName
	 *            the teamName to set
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
	 * @param teamEnglishName
	 *            the teamEnglishName to set
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
	 * @param jobTitleName
	 *            the jobTitleName to set
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
	 * @param jobTitleEnglishName
	 *            the jobTitleEnglishName to set
	 */
	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	/**
	 * @return the itemTypeDisplayName
	 */
	public String getItemTypeDisplayName() {
		return itemTypeDisplayName;
	}

	/**
	 * @param itemTypeDisplayName
	 *            the itemTypeDisplayName to set
	 */
	public void setItemTypeDisplayName(String itemTypeDisplayName) {
		this.itemTypeDisplayName = itemTypeDisplayName;
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
