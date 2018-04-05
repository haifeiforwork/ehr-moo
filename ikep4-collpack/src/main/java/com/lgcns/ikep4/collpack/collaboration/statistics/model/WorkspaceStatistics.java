/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.statistics.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
/**
 * 
 * WorkspaceMap
 *
 * @author
 * @version $Id: WorkspaceStatistics.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class WorkspaceStatistics extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 3757693700984733446L;
	// 연도
	private String yearString;
	// 멤버ID
	private String memberId;
	// 멤버 레벨
	private String memberLevel;
	// 멤버 레벨명
	private String levelName;
	// 멤버 레벨 영문명
	private String levelEnglishName;
	// 멤버명
	private String userName;
	// 멤버 영문명
	private String userEnglishName;
	// 팀명
	private String teamName;
	// 팀영문명
	private String teamEnglishName;
	// 직급명
	private String jobTitleName;
	// 직급 영문명
	private String jobTitleEnglishName;
	// 게시물 수
	private String docCount;
	// 주간보고 수
	private String weeklyCount;
	// 조회수
	private String hitCount;
	// 추천수
	private String recommendCount;
	// 댓글수
	private String lineReplyCount;
	// 즐겨찾기 수
	private String favoriteCount;
	// PVI
	private String pvi;
	// CVI
	private String cvi;
	// 년월
	private String yearMonth;
	// 멤버수
	private String memCnt;
	// 방문 비율
	private String visitRatio;
	// 게시글 등록 비율
	private String writeRatio;
	// 주간보고 등록 비율
	private String weeklyRatio;
	// 댓글 비율
	private String lineReplyRatio;
	// 팀평균 PVI
	private String pviAvg;
	// 팀원 평균 CVI
	private String cviAvg;
	

	/**
	 * @return the yearString
	 */
	public String getYearString() {
		return yearString;
	}

	/**
	 * @param yearString the yearString to set
	 */
	public void setYearString(String yearString) {
		this.yearString = yearString;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}
	
	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	/**
	 * @return the memberLevel
	 */
	public String getMemberLevel() {
		return memberLevel;
	}
	
	/**
	 * @param memberLevel the memberLevel to set
	 */
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
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
	 * @return the docCount
	 */
	public String getDocCount() {
		return docCount;
	}
	
	/**
	 * @param docCount the docCount to set
	 */
	public void setDocCount(String docCount) {
		this.docCount = docCount;
	}
	
	/**
	 * @return the weeklyCount
	 */
	public String getWeeklyCount() {
		return weeklyCount;
	}
	
	/**
	 * @param weeklyCount the weeklyCount to set
	 */
	public void setWeeklyCount(String weeklyCount) {
		this.weeklyCount = weeklyCount;
	}
	
	/**
	 * @return the hitCount
	 */
	public String getHitCount() {
		return hitCount;
	}
	
	/**
	 * @param hitCount the hitCount to set
	 */
	public void setHitCount(String hitCount) {
		this.hitCount = hitCount;
	}
	
	/**
	 * @return the recommendCount
	 */
	public String getRecommendCount() {
		return recommendCount;
	}
	
	/**
	 * @param recommendCount the recommendCount to set
	 */
	public void setRecommendCount(String recommendCount) {
		this.recommendCount = recommendCount;
	}
	
	/**
	 * @return the linereplyCount
	 */
	public String getLineReplyCount() {
		return lineReplyCount;
	}
	
	/**
	 * @param linereplyCount the linereplyCount to set
	 */
	public void setLineReplyCount(String lineReplyCount) {
		this.lineReplyCount = lineReplyCount;
	}
	
	/**
	 * @return the favoriteCount
	 */
	public String getFavoriteCount() {
		return favoriteCount;
	}
	
	/**
	 * @param favoriteCount the favoriteCount to set
	 */
	public void setFavoriteCount(String favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
	/**
	 * @return the pvi
	 */
	public String getPvi() {
		return pvi;
	}
	
	/**
	 * @param pvi the pvi to set
	 */
	public void setPvi(String pvi) {
		this.pvi = pvi;
	}
	
	/**
	 * @return the cvi
	 */
	public String getCvi() {
		return cvi;
	}
	
	/**
	 * @param cvi the cvi to set
	 */
	public void setCvi(String cvi) {
		this.cvi = cvi;
	}

	/**
	 * @return the yearMonth
	 */
	public String getYearMonth() {
		return yearMonth;
	}

	/**
	 * @param yearMonth the yearMonth to set
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * @return the memCnt
	 */
	public String getMemCnt() {
		return memCnt;
	}

	/**
	 * @param memCnt the memCnt to set
	 */
	public void setMemCnt(String memCnt) {
		this.memCnt = memCnt;
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
	 * @return the visitRatio
	 */
	public String getVisitRatio() {
		return visitRatio;
	}

	/**
	 * @param visitRatio the visitRatio to set
	 */
	public void setVisitRatio(String visitRatio) {
		this.visitRatio = visitRatio;
	}

	/**
	 * @return the writeRatio
	 */
	public String getWriteRatio() {
		return writeRatio;
	}

	/**
	 * @param writeRatio the writeRatio to set
	 */
	public void setWriteRatio(String writeRatio) {
		this.writeRatio = writeRatio;
	}

	/**
	 * @return the weeklyRatio
	 */
	public String getWeeklyRatio() {
		return weeklyRatio;
	}

	/**
	 * @param weeklyRatio the weeklyRatio to set
	 */
	public void setWeeklyRatio(String weeklyRatio) {
		this.weeklyRatio = weeklyRatio;
	}

	/**
	 * @return the lineReplyRatio
	 */
	public String getLineReplyRatio() {
		return lineReplyRatio;
	}

	/**
	 * @param lineReplyRatio the lineReplyRatio to set
	 */
	public void setLineReplyRatio(String lineReplyRatio) {
		this.lineReplyRatio = lineReplyRatio;
	}

	/**
	 * @return the pviAvg
	 */
	public String getPviAvg() {
		return pviAvg;
	}

	/**
	 * @param pviAvg the pviAvg to set
	 */
	public void setPviAvg(String pviAvg) {
		this.pviAvg = pviAvg;
	}

	/**
	 * @return the cviAvg
	 */
	public String getCviAvg() {
		return cviAvg;
	}

	/**
	 * @param cviAvg the cviAvg to set
	 */
	public void setCviAvg(String cviAvg) {
		this.cviAvg = cviAvg;
	}

	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * @return the levelEnglishName
	 */
	public String getLevelEnglishName() {
		return levelEnglishName;
	}

	/**
	 * @param levelEnglishName the levelEnglishName to set
	 */
	public void setLevelEnglishName(String levelEnglishName) {
		this.levelEnglishName = levelEnglishName;
	}

}
