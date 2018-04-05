/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.model;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * Assessment Management AssessmentManagementPolicyBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPolicyBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementPolicyBody extends AssessmentManagementPolicyPK {

	/**
	 *
	 */
	private static final long serialVersionUID = -9174126364339125164L;

	/**
	 * 기여점수 만점기준점수
	 */
	@Min(value = 1)
	@Max(value = 999999999)
	private int contributionMax;

	/**
	 * 기여점수 산정 가중치
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int contributionWeight;

	/**
	 * 참여점수 만점기준점수
	 */
	@Min(value = 1)
	@Max(value = 999999999)
	private int participationMax;

	/**
	 * 참여점수 산정 가중치
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int participationWeight;

	/**
	 * 활용점수 만점기준점수
	 */
	@Min(value = 1)
	@Max(value = 999999999)
	private int applicationMax;

	/**
	 * 활용점수 산정 가중치
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int applicationWeight;

	/**
	 * 친화점수 만점기준점수
	 */
	@Min(value = 1)
	@Max(value = 999999999)
	private int friendshipMax;

	/**
	 * 친화점수 산정 가중치
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int friendshipWeight;

	/**
	 * 주도점수 만점기준점수
	 */
	@Min(value = 1)
	@Max(value = 999999999)
	private int leadershipMax;

	/**
	 * 주도점수 산정 가중치
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int leadershipWeight;

	/**
	 * PVI 평가점수 산정 시작일
	 */
	private Date evaluationStartDate;

	/**
	 * 자료 등록 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int itemRegistScore;

	/**
	 * 우수 자료 등록건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int bestItemScore;

	/**
	 * 답변 등록 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int answerScore;

	/**
	 * 우수 답변 등록 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int bestAnswerScore;

	/**
	 * 댓글(Comment) 등록 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int linereplyScore;

	/**
	 * 추천 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int recommendScore;

	/**
	 * 조회 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int hitScore;

	/**
	 * 검색 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int searchScore;

	/**
	 * 마이크로블로깅 Follow 수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int followScore;

	/**
	 * 마이크로블로깅 Following 수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int followingScore;

	/**
	 * 마이크로블로깅 교차 Follow 수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int crossFollowingScore;

	/**
	 * 마이크로블로깅 그룹 가입수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int mblogGroupScore;

	/**
	 * 방명록 등록글 수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int guestbookItemScore;

	/**
	 * Tweet 등록건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int tweetScore;

	/**
	 * Retweet 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int retweetScore;

	/**
	 * 프로파일 방문 건수에 대한 평가점수
	 */
	@Min(value = 0)
	@Max(value = 1000)
	private int profileVisitScore;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록일시
	 */
	private Date registDate;

	/**
	 * 생성자
	 */
	public AssessmentManagementPolicyBody() {
		super();
	}

	/**
	 * 생성자
	 * @param portalId
	 */
	public AssessmentManagementPolicyBody(String portalId) {
		super(portalId);
	}

	/**
	 * @return the contributionMax
	 */
	public int getContributionMax() {
		return contributionMax;
	}

	/**
	 * @param contributionMax the contributionMax to set
	 */
	public void setContributionMax(int contributionMax) {
		this.contributionMax = contributionMax;
	}

	/**
	 * @return the contributionWeight
	 */
	public int getContributionWeight() {
		return contributionWeight;
	}

	/**
	 * @param contributionWeight the contributionWeight to set
	 */
	public void setContributionWeight(int contributionWeight) {
		this.contributionWeight = contributionWeight;
	}

	/**
	 * @return the participationMax
	 */
	public int getParticipationMax() {
		return participationMax;
	}

	/**
	 * @param participationMax the participationMax to set
	 */
	public void setParticipationMax(int participationMax) {
		this.participationMax = participationMax;
	}

	/**
	 * @return the participationWeight
	 */
	public int getParticipationWeight() {
		return participationWeight;
	}

	/**
	 * @param participationWeight the participationWeight to set
	 */
	public void setParticipationWeight(int participationWeight) {
		this.participationWeight = participationWeight;
	}

	/**
	 * @return the applicationMax
	 */
	public int getApplicationMax() {
		return applicationMax;
	}

	/**
	 * @param applicationMax the applicationMax to set
	 */
	public void setApplicationMax(int applicationMax) {
		this.applicationMax = applicationMax;
	}

	/**
	 * @return the applicationWeight
	 */
	public int getApplicationWeight() {
		return applicationWeight;
	}

	/**
	 * @param applicationWeight the applicationWeight to set
	 */
	public void setApplicationWeight(int applicationWeight) {
		this.applicationWeight = applicationWeight;
	}

	/**
	 * @return the friendshipMax
	 */
	public int getFriendshipMax() {
		return friendshipMax;
	}

	/**
	 * @param friendshipMax the friendshipMax to set
	 */
	public void setFriendshipMax(int friendshipMax) {
		this.friendshipMax = friendshipMax;
	}

	/**
	 * @return the friendshipWeight
	 */
	public int getFriendshipWeight() {
		return friendshipWeight;
	}

	/**
	 * @param friendshipWeight the friendshipWeight to set
	 */
	public void setFriendshipWeight(int friendshipWeight) {
		this.friendshipWeight = friendshipWeight;
	}

	/**
	 * @return the leadershipMax
	 */
	public int getLeadershipMax() {
		return leadershipMax;
	}

	/**
	 * @param leadershipMax the leadershipMax to set
	 */
	public void setLeadershipMax(int leadershipMax) {
		this.leadershipMax = leadershipMax;
	}

	/**
	 * @return the leadershipWeight
	 */
	public int getLeadershipWeight() {
		return leadershipWeight;
	}

	/**
	 * @param leadershipWeight the leadershipWeight to set
	 */
	public void setLeadershipWeight(int leadershipWeight) {
		this.leadershipWeight = leadershipWeight;
	}

	/**
	 * @return the evaluationStartDate
	 */
	public Date getEvaluationStartDate() {
		return evaluationStartDate;
	}

	/**
	 * @param evaluationStartDate the evaluationStartDate to set
	 */
	public void setEvaluationStartDate(Date evaluationStartDate) {
		this.evaluationStartDate = evaluationStartDate;
	}

	/**
	 * @return the itemRegistScore
	 */
	public int getItemRegistScore() {
		return itemRegistScore;
	}

	/**
	 * @param itemRegistScore the itemRegistScore to set
	 */
	public void setItemRegistScore(int itemRegistScore) {
		this.itemRegistScore = itemRegistScore;
	}

	/**
	 * @return the bestItemScore
	 */
	public int getBestItemScore() {
		return bestItemScore;
	}

	/**
	 * @param bestItemScore the bestItemScore to set
	 */
	public void setBestItemScore(int bestItemScore) {
		this.bestItemScore = bestItemScore;
	}

	/**
	 * @return the answerScore
	 */
	public int getAnswerScore() {
		return answerScore;
	}

	/**
	 * @param answerScore the answerScore to set
	 */
	public void setAnswerScore(int answerScore) {
		this.answerScore = answerScore;
	}

	/**
	 * @return the bestAnswerScore
	 */
	public int getBestAnswerScore() {
		return bestAnswerScore;
	}

	/**
	 * @param bestAnswerScore the bestAnswerScore to set
	 */
	public void setBestAnswerScore(int bestAnswerScore) {
		this.bestAnswerScore = bestAnswerScore;
	}

	/**
	 * @return the linereplyScore
	 */
	public int getLinereplyScore() {
		return linereplyScore;
	}

	/**
	 * @param linereplyScore the linereplyScore to set
	 */
	public void setLinereplyScore(int linereplyScore) {
		this.linereplyScore = linereplyScore;
	}

	/**
	 * @return the recommendScore
	 */
	public int getRecommendScore() {
		return recommendScore;
	}

	/**
	 * @param recommendScore the recommendScore to set
	 */
	public void setRecommendScore(int recommendScore) {
		this.recommendScore = recommendScore;
	}

	/**
	 * @return the hitScore
	 */
	public int getHitScore() {
		return hitScore;
	}

	/**
	 * @param hitScore the hitScore to set
	 */
	public void setHitScore(int hitScore) {
		this.hitScore = hitScore;
	}

	/**
	 * @return the searchScore
	 */
	public int getSearchScore() {
		return searchScore;
	}

	/**
	 * @param searchScore the searchScore to set
	 */
	public void setSearchScore(int searchScore) {
		this.searchScore = searchScore;
	}

	/**
	 * @return the followScore
	 */
	public int getFollowScore() {
		return followScore;
	}

	/**
	 * @param followScore the followScore to set
	 */
	public void setFollowScore(int followScore) {
		this.followScore = followScore;
	}

	/**
	 * @return the followingScore
	 */
	public int getFollowingScore() {
		return followingScore;
	}

	/**
	 * @param followingScore the followingScore to set
	 */
	public void setFollowingScore(int followingScore) {
		this.followingScore = followingScore;
	}

	/**
	 * @return the crossFollowingScore
	 */
	public int getCrossFollowingScore() {
		return crossFollowingScore;
	}

	/**
	 * @param crossFollowingScore the crossFollowingScore to set
	 */
	public void setCrossFollowingScore(int crossFollowingScore) {
		this.crossFollowingScore = crossFollowingScore;
	}

	/**
	 * @return the mblogGroupScore
	 */
	public int getMblogGroupScore() {
		return mblogGroupScore;
	}

	/**
	 * @param mblogGroupScore the mblogGroupScore to set
	 */
	public void setMblogGroupScore(int mblogGroupScore) {
		this.mblogGroupScore = mblogGroupScore;
	}

	/**
	 * @return the guestbookItemScore
	 */
	public int getGuestbookItemScore() {
		return guestbookItemScore;
	}

	/**
	 * @param guestbookItemScore the guestbookItemScore to set
	 */
	public void setGuestbookItemScore(int guestbookItemScore) {
		this.guestbookItemScore = guestbookItemScore;
	}

	/**
	 * @return the tweetScore
	 */
	public int getTweetScore() {
		return tweetScore;
	}

	/**
	 * @param tweetScore the tweetScore to set
	 */
	public void setTweetScore(int tweetScore) {
		this.tweetScore = tweetScore;
	}

	/**
	 * @return the retweetScore
	 */
	public int getRetweetScore() {
		return retweetScore;
	}

	/**
	 * @param retweetScore the retweetScore to set
	 */
	public void setRetweetScore(int retweetScore) {
		this.retweetScore = retweetScore;
	}

	/**
	 * @return the profileVisitScore
	 */
	public int getProfileVisitScore() {
		return profileVisitScore;
	}

	/**
	 * @param profileVisitScore the profileVisitScore to set
	 */
	public void setProfileVisitScore(int profileVisitScore) {
		this.profileVisitScore = profileVisitScore;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

}
