/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import java.util.Date;

/**
 * 토론정책관리 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrPolicy.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class FrPolicy extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 3555296352460966554L;

	/**
	 * 토론 정책 관리 ID
	 */
	private String policyId;

	/**
	 * 토론정책타입
		( 0 : 토론활동 정책
		1 : 인기토론주제 정책
		2 : 인기토론글 정책
		3 : 우수토론 정책
		4 : 토론분석 정책)
	 */
    private String policyType;

    /**
     * 토론활동정책타입인 경우  토론활동참여도 가중치(단위 %)
     */
    private int participationWeight;

    /**
     * 토론활동정책타입인 경우  토론활동 피드백 가중치(단위 %)
     */
    private int feedbackWeight;

    /**
     * 1. 토론활동정책타입인 경우  토론주제 등록수에 대한 가중치(단위 %)
     */
    private int discussionWeight;

    /**
     * 1. 토론활동정책타입인 경우  토론글 등록수에 대한 가중치(단위 %)
	   2. 인기토론주제 정책타입인 경우 토론글 등록수에 대한 가중치(단위%)
     */
    private int itemWeight;

    /**
     * 1. 토론활동정책타입인 경우  토론의견(댓글) 등록수에 대한 가중치(단위 %)
        2. 인기토론주제 및 인기토론글 정책타입인 경우 토론의견 등록수에 대한 가중치(단위%)
     */
    private int linereplyWeight;

    /**
     * 1. 인기토론주제 정책타입인 경우 인기토론주제 최대 선정 갯수
		2. 인기토론글 정책타입인 경우 인기토론글 최대 선정 갯수
     */
    private Integer maxCount;

    /**
     * 1. 토론활동정책타입인 경우  베스트 토론글 등록수에 대한 가중치(단위 %)
     */
    private int bestItemWeight;

    /**
     * 1. 토론활동정책타입인 경우 베스트 토론의견(댓글) 등록수에 대한 가중치(단위 %)
     */
    private int bestLinereplyWeight;

    /**
     * 1. 토론활동정책타입인 경우  다른 사용자에 의해 즐겨찾기된 토론글수에 대한 가중치(단위 %)
		2. 인기토론주제 및 인기토론글 정책타입인 경우 즐겨찾기수에 대한 가중치(단위%)
     */
    private int favoriteWeight;

    /**
     * 1. 토론활동정책타입인 경우  다른 사용자에 의해 찬성된 토론글수에 대한 가중치(단위 %)
		2. 인기토론주제 및 인기토론글 정책타입인 경우 (찬성수+반대수)에 대한 가중치(단위 %)
		3. 우수토론 정책타입인 경우 찬성수
     */
    private int agreementWeight;

    /**
     * 1. 토론활동정책타입인 경우  다른 사용자에 의해 추천된 토론의견(댓글)수에 대한 가중치(단위 %)
		2. 인기토론주제정책타입인 경우 추천수에 대한 가중치(단위%)
		3. 우수토론 정책타입인 경우 추천수
     */
    private int recommendWeight;

    /**
     * 1. 토론분석 정책타입인 경우 분석대상기간
     */
    private Integer targetTerm;

    /**
     * 포탈 ID
     */
    private String portalId;

    /**
     * 등록자 ID
     */
    private String registerId;

    /**
     * 등록자 이름
     */
    private String registerName;

    /**
     * 등록일시
     */
    private Date registDate;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public int getParticipationWeight() {
		return participationWeight;
	}

	public void setParticipationWeight(int participationWeight) {
		this.participationWeight = participationWeight;
	}

	public int getFeedbackWeight() {
		return feedbackWeight;
	}

	public void setFeedbackWeight(int feedbackWeight) {
		this.feedbackWeight = feedbackWeight;
	}

	public int getDiscussionWeight() {
		return discussionWeight;
	}

	public void setDiscussionWeight(int discussionWeight) {
		this.discussionWeight = discussionWeight;
	}

	public int getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(int itemWeight) {
		this.itemWeight = itemWeight;
	}

	public int getLinereplyWeight() {
		return linereplyWeight;
	}

	public void setLinereplyWeight(int linereplyWeight) {
		this.linereplyWeight = linereplyWeight;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public int getBestItemWeight() {
		return bestItemWeight;
	}

	public void setBestItemWeight(int bestItemWeight) {
		this.bestItemWeight = bestItemWeight;
	}

	public int getBestLinereplyWeight() {
		return bestLinereplyWeight;
	}

	public void setBestLinereplyWeight(int bestLinereplyWeight) {
		this.bestLinereplyWeight = bestLinereplyWeight;
	}

	public int getFavoriteWeight() {
		return favoriteWeight;
	}

	public void setFavoriteWeight(int favoriteWeight) {
		this.favoriteWeight = favoriteWeight;
	}

	public int getAgreementWeight() {
		return agreementWeight;
	}

	public void setAgreementWeight(int agreementWeight) {
		this.agreementWeight = agreementWeight;
	}

	public int getRecommendWeight() {
		return recommendWeight;
	}

	public void setRecommendWeight(int recommendWeight) {
		this.recommendWeight = recommendWeight;
	}

	public Integer getTargetTerm() {
		return targetTerm;
	}

	public void setTargetTerm(Integer targetTerm) {
		this.targetTerm = targetTerm;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
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

}