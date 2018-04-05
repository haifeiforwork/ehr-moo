/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.model;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Expert Network ExpertPolicyBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPolicyBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkPolicyBody extends ExpertNetworkPolicyPK {

	/**
	 *
	 */
	private static final long serialVersionUID = -1922965803048668536L;

	/**
	 * 사용자별 방명록 게시글수에 대한 가중치(사용자의 방명록에 남겨진 방명록 글수에 대한 가중치)
	 */
	@NotNull
	@Digits(fraction = 0, integer = 1000)
	@Min(value = 0)
	@Max(value = 1000)
	private int guestbookWeight;

	/**
	 * 사용자별 Follower수에 대한 가중치(사용자를 Following 하는 인원수에 대한 가중치)
	 */
	@NotNull
	@Digits(fraction = 0, integer = 1000)
	@Min(value = 0)
	@Max(value = 1000)
	private int followWeight;

	/**
	 * 사용자별 즐겨찾기 수에 대한 가중치(사용자를 즐겨찾기한 인원수에 대한 가중치)
	 */
	@NotNull
	@Digits(fraction = 0, integer = 1000)
	@Min(value = 0)
	@Max(value = 1000)
	private int favoriteWeight;

	/**
	 * 포탈ID
	 */
	private String portalId;

	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자명
	 */
	private String registerName;
	
	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * @return the guestbookWeight
	 */
	public int getGuestbookWeight() {
		return guestbookWeight;
	}

	/**
	 * @param guestbookWeight the guestbookWeight to set
	 */
	public void setGuestbookWeight(int guestbookWeight) {
		this.guestbookWeight = guestbookWeight;
	}

	/**
	 * @return the followWeight
	 */
	public int getFollowWeight() {
		return followWeight;
	}

	/**
	 * @param followWeight the followWeight to set
	 */
	public void setFollowWeight(int followWeight) {
		this.followWeight = followWeight;
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
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
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
