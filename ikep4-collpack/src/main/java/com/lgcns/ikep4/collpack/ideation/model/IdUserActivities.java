/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 사용자별 아이디어 활동현황정보
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdUserActivities.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class IdUserActivities extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 5338184531852514658L;

	/**
	 * 사용자 ID
	 */
	private String userId;

	/**
	 * 사용자별 아이디어 제안활동 순위
	 */
	private int suggestionRank = -1;

	/**
	 * 사용자별 아이디어 제안활동 점수
	 */
	private int suggestionScore = -1;

	/**
	 * 사용자별 아이디어 기여활동 순위
	 */
	private int contributionRank = -1;

	/**
	 * 사용자별 아이디어 기여활동 점수
	 */
	private int contributionScore = -1;

	/**
	 * 아이디어 등록수
	 */
	private int itemCount = -1;

	/**
	 * 사용자별 추천된 아이디어 건수
	 */
	private int recommendItemCount = -1;

	/**
	 * 사용자별 채택된 아이디어 건수
	 */
	private int adoptItemCount = -1;

	/**
	 * 베스트 아이디어 등록수
	 */
	private int bestItemCount = -1;

	/**
	 * 사용자별 사업자선정 아이디어 건수
	 */
	private int businessItemCount = -1;

	/**
	 * 다른 사용자에 의해 추천된 토론의견수
	 */
	private int recommendCount = -1;

	/**
	 * 사용자별 아이디어 채택한 총 건수
	 */
	private int adoptCount = -1;

	/**
	 * 다른사용자에 의해 즐겨찾기된 아이디어수
	 */
	private int favoriteCount = -1;

	/**
	 * 토론의견(댓글) 등록수
	 */
	private int linereplyCount = -1;

	/**
	 * 사용자 팀이름
	 */
	private String teamName;

	/**
	 * 직책
	 */
	private String jobTitleName;

	/**
	 * 사용자이
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

	public int getSuggestionRank() {
		return suggestionRank;
	}

	public void setSuggestionRank(int suggestionRank) {
		this.suggestionRank = suggestionRank;
	}

	public int getSuggestionScore() {
		return suggestionScore;
	}

	public void setSuggestionScore(int suggestionScore) {
		this.suggestionScore = suggestionScore;
	}

	public int getContributionRank() {
		return contributionRank;
	}

	public void setContributionRank(int contributionRank) {
		this.contributionRank = contributionRank;
	}

	public int getContributionScore() {
		return contributionScore;
	}

	public void setContributionScore(int contributionScore) {
		this.contributionScore = contributionScore;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getRecommendItemCount() {
		return recommendItemCount;
	}

	public void setRecommendItemCount(int recommendItemCount) {
		this.recommendItemCount = recommendItemCount;
	}

	public int getAdoptItemCount() {
		return adoptItemCount;
	}

	public void setAdoptItemCount(int adoptItemCount) {
		this.adoptItemCount = adoptItemCount;
	}

	public int getBestItemCount() {
		return bestItemCount;
	}

	public void setBestItemCount(int bestItemCount) {
		this.bestItemCount = bestItemCount;
	}

	public int getBusinessItemCount() {
		return businessItemCount;
	}

	public void setBusinessItemCount(int businessItemCount) {
		this.businessItemCount = businessItemCount;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	public int getAdoptCount() {
		return adoptCount;
	}

	public void setAdoptCount(int adoptCount) {
		this.adoptCount = adoptCount;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public int getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(int linereplyCount) {
		this.linereplyCount = linereplyCount;
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