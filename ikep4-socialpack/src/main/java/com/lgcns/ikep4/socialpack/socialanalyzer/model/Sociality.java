/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: Sociality.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Sociality extends BaseObject {
	private static final long serialVersionUID = -3150513995167156573L;

	/**
	 * 사용자ID
	 */
	private String userId;

	/**
	 * SOCIALITY INDEX(계산된 점수)
	 */
	private Float indexSociality = (float) 0;

	/**
	 * INFLUENCE INDEX(계산된 점수)
	 */
	private Float indexInfluence = (float) 0;

	/**
	 * FELLOWSHIP INDEX(계산된 점수)
	 */
	private Float indexFellowship = (float) 0;

	/**
	 * SOCIALITY INDEX 기준 Ranking (순위)
	 */
	private Integer rankSociality = 0;

	/**
	 * INFLUENCE INDEX 기준 Ranking (순위)
	 */
	private Integer rankInfluence = 0;

	/**
	 * FELLOWSHIP INDEX 기준 Ranking (순위)
	 */
	private Integer rankFellowship = 0;

	/**
	 * FOLLOWER 수
	 */
	private Integer followerCount = 0;

	/**
	 * 자신이 Following하고 있는 사용자수
	 */
	private Integer followingCount = 0;

	/**
	 * 상호 FOLLOWING 하는 수
	 */
	private Integer bothFollowingCount = 0;

	/**
	 * 마이크로블로깅 작성 수
	 */
	private Integer tweetCount = 0;

	/**
	 * 자신의 글이 RETWEET된 수
	 */
	private Integer retweetCount = 0;

	/**
	 * 자신의 블로그에 방문한 유저 수
	 */
	private Integer blogVisitCount = 0;

	/**
	 * 자신이 속한 마이크로블로깅 그룹 수
	 */
	private Integer mblogGroupCount = 0;

	/**
	 * 자신의 프로파일에 남겨진 방명록 수
	 */
	private Integer guestbookCount = 0;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * 사용자명
	 */
	private String userName;

	/**
	 * 사용자명(영어)
	 */
	private String userEnglishName;

	/**
	 * 팀명
	 */
	private String teamName;

	/**
	 * 팀명(영어)
	 */
	private String teamEnglishName;

	/**
	 * 직책명
	 */
	private String jobTitleName;

	/**
	 * 직책명(영어)
	 */
	private String jobTitleEnglishName;

	/**
	 * index
	 */
	private Integer rNum = 0;

	/**
	 * TOP_PERCENT
	 */
	private String topPercent;

	/**
	 * 자신의 프로파일에 방문한 유저 수
	 */
	private Integer profileVisitCount = 0;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the indexSociality
	 */
	public Float getIndexSociality() {
		return indexSociality;
	}

	/**
	 * @param indexSociality the indexSociality to set
	 */
	public void setIndexSociality(Float indexSociality) {
		this.indexSociality = indexSociality;
	}

	/**
	 * @return the indexInfluence
	 */
	public Float getIndexInfluence() {
		return indexInfluence;
	}

	/**
	 * @param indexInfluence the indexInfluence to set
	 */
	public void setIndexInfluence(Float indexInfluence) {
		this.indexInfluence = indexInfluence;
	}

	/**
	 * @return the indexFellowship
	 */
	public Float getIndexFellowship() {
		return indexFellowship;
	}

	/**
	 * @param indexFellowship the indexFellowship to set
	 */
	public void setIndexFellowship(Float indexFellowship) {
		this.indexFellowship = indexFellowship;
	}

	/**
	 * @return the rankSociality
	 */
	public Integer getRankSociality() {
		return rankSociality;
	}

	/**
	 * @param rankSociality the rankSociality to set
	 */
	public void setRankSociality(Integer rankSociality) {
		this.rankSociality = rankSociality;
	}

	/**
	 * @return the rankInfluence
	 */
	public Integer getRankInfluence() {
		return rankInfluence;
	}

	/**
	 * @param rankInfluence the rankInfluence to set
	 */
	public void setRankInfluence(Integer rankInfluence) {
		this.rankInfluence = rankInfluence;
	}

	/**
	 * @return the rankFellowship
	 */
	public Integer getRankFellowship() {
		return rankFellowship;
	}

	/**
	 * @param rankFellowship the rankFellowship to set
	 */
	public void setRankFellowship(Integer rankFellowship) {
		this.rankFellowship = rankFellowship;
	}

	/**
	 * @return the followerCount
	 */
	public Integer getFollowerCount() {
		return followerCount;
	}

	/**
	 * @param followerCount the followerCount to set
	 */
	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}

	/**
	 * @return the followingCount
	 */
	public Integer getFollowingCount() {
		return followingCount;
	}

	/**
	 * @param followingCount the followingCount to set
	 */
	public void setFollowingCount(Integer followingCount) {
		this.followingCount = followingCount;
	}

	/**
	 * @return the bothFollowingCount
	 */
	public Integer getBothFollowingCount() {
		return bothFollowingCount;
	}

	/**
	 * @param bothFollowingCount the bothFollowingCount to set
	 */
	public void setBothFollowingCount(Integer bothFollowingCount) {
		this.bothFollowingCount = bothFollowingCount;
	}

	/**
	 * @return the tweetCount
	 */
	public Integer getTweetCount() {
		return tweetCount;
	}

	/**
	 * @param tweetCount the tweetCount to set
	 */
	public void setTweetCount(Integer tweetCount) {
		this.tweetCount = tweetCount;
	}

	/**
	 * @return the retweetCount
	 */
	public Integer getRetweetCount() {
		return retweetCount;
	}

	/**
	 * @param retweetCount the retweetCount to set
	 */
	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	/**
	 * @return the blogVisitCount
	 */
	public Integer getBlogVisitCount() {
		return blogVisitCount;
	}

	/**
	 * @param blogVisitCount the blogVisitCount to set
	 */
	public void setBlogVisitCount(Integer blogVisitCount) {
		this.blogVisitCount = blogVisitCount;
	}

	/**
	 * @return the mblogGroupCount
	 */
	public Integer getMblogGroupCount() {
		return mblogGroupCount;
	}

	/**
	 * @param mblogGroupCount the mblogGroupCount to set
	 */
	public void setMblogGroupCount(Integer mblogGroupCount) {
		this.mblogGroupCount = mblogGroupCount;
	}

	/**
	 * @return the guestbookCount
	 */
	public Integer getGuestbookCount() {
		return guestbookCount;
	}

	/**
	 * @param guestbookCount the guestbookCount to set
	 */
	public void setGuestbookCount(Integer guestbookCount) {
		this.guestbookCount = guestbookCount;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
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
	 * @param userEnglishName the userEnglishName to set
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
	 * @param teamName the teamName to set
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
	 * @param teamEnglishName the teamEnglishName to set
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
	 * @param jobTitleName the jobTitleName to set
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
	 * @param jobTitleEnglishName the jobTitleEnglishName to set
	 */
	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	/**
	 * @return the rNum
	 */
	public Integer getrNum() {
		return rNum;
	}

	/**
	 * @param rNum the rNum to set
	 */
	public void setrNum(Integer rNum) {
		this.rNum = rNum;
	}

	/**
	 * @return the topPercent
	 */
	public String getTopPercent() {
		return topPercent;
	}

	/**
	 * @param topPercent the topPercent to set
	 */
	public void setTopPercent(String topPercent) {
		this.topPercent = topPercent;
	}

	/**
	 * @return the profileVisitCount
	 */
	public Integer getProfileVisitCount() {
		return profileVisitCount;
	}

	/**
	 * @param profileVisitCount the profileVisitCount to set
	 */
	public void setProfileVisitCount(Integer profileVisitCount) {
		this.profileVisitCount = profileVisitCount;
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
