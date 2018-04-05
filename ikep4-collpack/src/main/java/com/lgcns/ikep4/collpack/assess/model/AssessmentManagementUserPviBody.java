/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.model;

import java.util.Date;


/**
 * Assessment Management AssessmentManagementUserPviBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementUserPviBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementUserPviBody extends AssessmentManagementUserPviPK { 

	/**
	 *
	 */
	private static final long serialVersionUID = 896418965304006474L;

	/**
	 * 사용자별 PVI(Personal Value Index) 점수
	 */
	private int pvi;

	/**
	 * 사용자별 PVI 전체 순위
	 */
	private int rank;

	/**
	 * 기여 점수(사용자의 지식활동이 전체에 기여한 정도를 나타내는 점수)
	 */
	private int contributionScore;

	/**
	 * 참여 점수(사용자의 지식활동 참여정도를 나타내는 점수)
	 */
	private int participationScore;

	/**
	 * 활용 점수(사용자의 지식활용정도를 나타내는 점수)
	 */
	private int applicationScore;

	/**
	 * 활용 점수(사용자의 소셜활동을 통한 사용자간 친화도를 나타내는 점수)
	 */
	private int friendshipScore;

	/**
	 * 활용 점수(사용자의 소셜활동과 지식활동을 통해 타 사용자에게 미치는 영향력을 나타내는 점수)
	 */
	private int influenceScore;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록일시
	 */
	private Date registDate;

	/**
	 * @return the pvi
	 */
	public int getPvi() {
		return pvi;
	}

	/**
	 * @param pvi the pvi to set
	 */
	public void setPvi(int pvi) {
		this.pvi = pvi;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the contributionScore
	 */
	public int getContributionScore() {
		return contributionScore;
	}

	/**
	 * @param contributionScore the contributionScore to set
	 */
	public void setContributionScore(int contributionScore) {
		this.contributionScore = contributionScore;
	}

	/**
	 * @return the participationScore
	 */
	public int getParticipationScore() {
		return participationScore;
	}

	/**
	 * @param participationScore the participationScore to set
	 */
	public void setParticipationScore(int participationScore) {
		this.participationScore = participationScore;
	}

	/**
	 * @return the applicationScore
	 */
	public int getApplicationScore() {
		return applicationScore;
	}

	/**
	 * @param applicationScore the applicationScore to set
	 */
	public void setApplicationScore(int applicationScore) {
		this.applicationScore = applicationScore;
	}

	/**
	 * @return the friendshipScore
	 */
	public int getFriendshipScore() {
		return friendshipScore;
	}

	/**
	 * @param friendshipScore the friendshipScore to set
	 */
	public void setFriendshipScore(int friendshipScore) {
		this.friendshipScore = friendshipScore;
	}

	/**
	 * @return the influenceScore
	 */
	public int getInfluenceScore() {
		return influenceScore;
	}

	/**
	 * @param influenceScore the influenceScore to set
	 */
	public void setInfluenceScore(int influenceScore) {
		this.influenceScore = influenceScore;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
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
