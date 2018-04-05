/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.model;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Knowledge Map KnowledgeMapPolicyBody model
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPolicyBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapPolicyBody extends KnowledgeMapPolicyPK {

	/**
	 *
	 */
	private static final long serialVersionUID = 7490565358128149274L;

	/**
	 * 지식 정책 타입( 0 : 인기지식정책, 1 : 추천지식정책)
	 */
	private String policyType;

	/**
	 * 조회 가중치
	 */
	@NotNull
	@Digits(fraction = 0, integer = 1000)
	@Min(value = 0)
	@Max(value = 1000)
	private int hitWeight;

	/**
	 * 추천 가중치
	 */
	@NotNull
	@Digits(fraction = 0, integer = 1000)
	@Min(value = 0)
	@Max(value = 1000)
	private int recommendWeight;

	/**
	 * 즐겨찾기 가중치
	 */
	@NotNull
	@Digits(fraction = 0, integer = 1000)
	@Min(value = 0)
	@Max(value = 1000)
	private int favoriteWeight;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 등록일시
	 */
	private Date registDate;

	/**
	 * @return the policyType
	 */
	public String getPolicyType() {
		return policyType;
	}

	/**
	 * @param policyType the policyType to set
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	/**
	 * @return the hitWeight
	 */
	public int getHitWeight() {
		return hitWeight;
	}

	/**
	 * @param hitWeight the hitWeight to set
	 */
	public void setHitWeight(int hitWeight) {
		this.hitWeight = hitWeight;
	}

	/**
	 * @return the recommendWeight
	 */
	public int getRecommendWeight() {
		return recommendWeight;
	}

	/**
	 * @param recommendWeight the recommendWeight to set
	 */
	public void setRecommendWeight(int recommendWeight) {
		this.recommendWeight = recommendWeight;
	}

	/**
	 * @return the favoriteWeight
	 */
	public int getFavoriteWeight() {
		return favoriteWeight;
	}

	/**
	 * @param favoriteWeight the favoriteWeight to set
	 */
	public void setFavoriteWeight(int favoriteWeight) {
		this.favoriteWeight = favoriteWeight;
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
