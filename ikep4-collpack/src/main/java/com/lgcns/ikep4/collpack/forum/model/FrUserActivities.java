/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 토론활동정보 정보
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrUserActivities.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class FrUserActivities extends BaseObject {
	/**
	 *
	 */
	private static final long serialVersionUID = -983516426970165968L;

	/**
	 * 토론활동자 ID
	 */
	private String userId;

	/**
	 * 토론활동정책에 의해 산출되는 토론 순위
	 */
	private int discussionRank = -1;

	/**
	 * 토론활동점수정책에 의해 관리되는 토론점수
	 */
	private int discussionScore = -1;

	/**
	 * 토론 주제 등록수
	 */
	private int discussionCount = -1;

	/**
	 * 토론글 등록수
	 */
	private int itemCount = -1;

	/**
	 * 토론의견(댓글) 등록수
	 */
	private int linereplyCount = -1;

	/**
	 * 베스트 토론글 등록수
	 */
	private int bestItemCount = -1;

	/**
	 * 베스트 토론의견(댓글) 등록수
	 */
	private int bestLinereplyCount = -1;

	/**
	 * 다른사용자에 의해 즐겨찾기된 토론글수
	 */
	private int favoriteCount = -1;

	/**
	 * 다른 사용자에 의해 찬성된 토론글수
	 */
	private int agreementCount = -1;

	/**
	 * 다른 사용자에 의해 반대된 토론글수
	 */
	private int oppositionCount = -1;

	/**
	 * 다른 사용자에 의해 추천된 토론의견수
	 */
	private int recommendCount = -1;

	/**
	 * 사용자 팀이름
	 */
	private String teamName;

	/**
	 * 직책
	 */
	private String jobTitleName;

	/**
	 * 사용자
	 */
	private String userName;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getDiscussionRank() {
		return discussionRank;
	}

	public void setDiscussionRank(int discussionRank) {
		this.discussionRank = discussionRank;
	}

	public int getDiscussionScore() {
		return discussionScore;
	}

	public void setDiscussionScore(int discussionScore) {
		this.discussionScore = discussionScore;
	}

	public int getDiscussionCount() {
		return discussionCount;
	}

	public void setDiscussionCount(int discussionCount) {
		this.discussionCount = discussionCount;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(int linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	public int getBestItemCount() {
		return bestItemCount;
	}

	public void setBestItemCount(int bestItemCount) {
		this.bestItemCount = bestItemCount;
	}

	public int getBestLinereplyCount() {
		return bestLinereplyCount;
	}

	public void setBestLinereplyCount(int bestLinereplyCount) {
		this.bestLinereplyCount = bestLinereplyCount;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public int getAgreementCount() {
		return agreementCount;
	}

	public void setAgreementCount(int agreementCount) {
		this.agreementCount = agreementCount;
	}

	public int getOppositionCount() {
		return oppositionCount;
	}

	public void setOppositionCount(int oppositionCount) {
		this.oppositionCount = oppositionCount;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
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