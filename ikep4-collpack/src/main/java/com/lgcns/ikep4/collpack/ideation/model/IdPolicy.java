/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import java.util.Date;

/**
 * 아이디어 정책 관리
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdPolicy.java 7008 2011-04-21 02:07:44Z loverfairy $
 */
public class IdPolicy extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -2368821478780401535L;

	/**
	 * 아이디어 정책 관리 ID
	 */
	private String policyId;

	/**
	 * 정책 타입( 0 : 우수 아이디어 정책 1 : 아이디어 제안활동 정책 2 : 아이디어 기여활동 정책)
	 */
    private String policyType;


    /**
     * 정책타입이 아이디어 기여활동 정책(2) 인경우 아이디어 댓글등록한 건수에 대한 가중치(단위 %)
     */
    private int linereplyWeight;

     /**
     * 정책타입이 아이디어 제안활동 정책(1) 인 경우 우수 아이디어 건수에 대한 가중치(단위 %)
     */
    private int bestWeight;

    /**
     * 정책타입이 아이디어 기여활동 정책(2) 인경우 아이디어 즐겨찾기한 건수에 대한 가중치(단위 %)
     */
    private int favoriteWeight;

    /**
     * 정책타입이 우수 아이디어 정책(0) 인 경우 추천수
     */
    private int recommendCount;

    /**
     * 1. 정책타입이 아이디어 제안활동 정책(1) 인 경우 추천된 아이디어 건수에 대한 가중치(단위 %)
		2. 정책타입이 아이디어 기여활동 정책(2) 인경우 아이디어 추천한 건수에 대한 가중치(단위 %)
     */
    private int recommendWeight;


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
    
    /**
     * 정책타입이 아이디어 제안활동 정책(1) 인 경우 아이디어 제안건수에 대한 가중치(단위 %)
     */
    private int suggestionWeight;
    
    /**
     * 1. 정책타입이 아이디어 제안활동 정책(1) 인 경우 채택된 아이디어 건수에 대한 가중치(단위 %)
		2. 정책타입이 아이디어 기여활동 정책(2) 인경우 아이디어 채택한 건수에 대한 가중치(단위 %)
     */
    private int adoptWeight;
    
    /**
     * 정책타입이 아이디어 제안활동 정책(1) 인 경우 사업화 선정 아이디어 건수에 대한 가중치(단위 %)
     */
    private int businessWeight;

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

	public int getLinereplyWeight() {
		return linereplyWeight;
	}

	public void setLinereplyWeight(int linereplyWeight) {
		this.linereplyWeight = linereplyWeight;
	}


	public int getFavoriteWeight() {
		return favoriteWeight;
	}

	public void setFavoriteWeight(int favoriteWeight) {
		this.favoriteWeight = favoriteWeight;
	}


	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	public int getRecommendWeight() {
		return recommendWeight;
	}

	public void setRecommendWeight(int recommendWeight) {
		this.recommendWeight = recommendWeight;
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

	public int getSuggestionWeight() {
		return suggestionWeight;
	}

	public void setSuggestionWeight(int suggestionWeight) {
		this.suggestionWeight = suggestionWeight;
	}

	public int getAdoptWeight() {
		return adoptWeight;
	}

	public void setAdoptWeight(int adoptWeight) {
		this.adoptWeight = adoptWeight;
	}

	public int getBusinessWeight() {
		return businessWeight;
	}

	public void setBusinessWeight(int businessWeight) {
		this.businessWeight = businessWeight;
	}

	public int getBestWeight() {
		return bestWeight;
	}

	public void setBestWeight(int bestWeight) {
		this.bestWeight = bestWeight;
	}
}