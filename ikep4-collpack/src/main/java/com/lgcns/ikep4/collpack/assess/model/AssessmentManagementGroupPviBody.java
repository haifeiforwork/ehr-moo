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
 * Assessment Management AssessmentManagementGroupPviBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementGroupPviBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementGroupPviBody extends AssessmentManagementGroupPviPK { 

	/**
	 *
	 */
	private static final long serialVersionUID = -3351795469701104614L;

	/**
	 * 상위 부모 조직 그룹 ID
	 */
	private String parentGroupId;

	/**
	 * 조직 그룹 PVI 점수
	 */
	private int pvi;

	/**
	 * 조직 그룹 PVI 순위
	 */
	private int rank;

	/**
	 * 기여 점수(조직내 구성원들의 지식기여점수의 평균값)
	 */
	private int contributionScore;

	/**
	 * 참여 점수(조직내 구성원들의 지식활동참여점수의 평균값)
	 */
	private int participationScore;

	/**
	 * 활용 점수(조직내 구성원들의 지식활용점수의 평균값)
	 */
	private int applicationScore;

	/**
	 * 친화 점수(조직내 구성원들의 친화점수의 평균값)
	 */
	private int friendshipScore;

	/**
	 * 영향력 점수(조직내 구성원들의 영향력점수의 평균값)
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
	 * 생성자
	 */
	public AssessmentManagementGroupPviBody() {
		super();
	}

	/**
	 * 생성자
	 * @param groupId - 그룹ID
	 */
	public AssessmentManagementGroupPviBody(String groupId) {
		super(groupId);
	}

	/**
	 * @return the parentGroupId
	 */
	public String getParentGroupId() {
		return parentGroupId;
	}

	/**
	 * @param parentGroupId the parentGroupId to set
	 */
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

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
