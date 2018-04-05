/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.search;

import com.lgcns.ikep4.socialpack.microblogging.base.Constant;


public class MblogSearchCondition  {
	private static final long serialVersionUID = 1L;

	/*
	 * 로그인 사용자 id
	 */
	private String loginId;

	/*
	 * 트윗터 홈 주인 id
	 */
	private String ownerId;
	
	/*
	 * WORKSPACE 의 ID
	 */
	private String workspaceId;
	
	/*
	 * MblogId
	 */
	private String mblogId;

	/*
	 * 트윗터 홈 주인이 속한 부서(그룹) ID
	 */
	private String ownerGroupId;

	/*
	 * 등록자 것만 보는지 여부
	 */
	private String registerOnly = "N";

	/*
	 * 검색 기준 
	 */
	private String standardType;

	/*
	 * 검색 기준 MblogId
	 */
	private String standardMblogId;

	/*
	 * 조회 갯수
	 */
	private int fetchSize = Constant.FETCH_SIZE_10;

	/*
	 * 검색어 (Tag 포함 mbtagType + searchWord)
	 */
	private String searchString;

	/*
	 * MBTAG TYPE (0: MENTION 1: 해쉬태그 )
	 */
	private String mbtagType;

	/*
	 * 검색어 (Tag 제외)
	 */
	private String searchWord;

	/*
	 * 검색 기준 UserId
	 */
	private String standardUserId;

	/**
	 * 마이크로블로깅 링크 타입 (0 : URL, 1 : 투표, 2: 이미지, 3 :  파일)
	 */
	private String addonType;

	/**
	 * 검색 기준 ADDON_CODE
	 */
	private String standardAddonCode;
	
	/**
	 * 함께 follow인 경우 Y.
	 */
	private String bothFollow;


	/**
	 * mbgroupId
	 */
	private String mbgroupId;

	/**
	 * memberId
	 */
	private String memberId;

	/**
	 * searchType
	 */
	private String searchType;
	
	private int nowSize;

	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getRegisterOnly() {
		return registerOnly;
	}

	public void setRegisterOnly(String registerOnly) {
		this.registerOnly = registerOnly;
	}

	public String getStandardType() {
		return standardType;
	}

	public void setStandardType(String standardType) {
		this.standardType = standardType;
	}

	public String getStandardMblogId() {
		return standardMblogId;
	}

	public void setStandardMblogId(String standardMblogId) {
		this.standardMblogId = standardMblogId;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getMbtagType() {
		return mbtagType;
	}

	public void setMbtagType(String mbtagType) {
		this.mbtagType = mbtagType;
	}
	
	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getStandardUserId() {
		return standardUserId;
	}

	public void setStandardUserId(String standardUserId) {
		this.standardUserId = standardUserId;
	}

	public String getAddonType() {
		return addonType;
	}

	public void setAddonType(String addonType) {
		this.addonType = addonType;
	}

	public String getStandardAddonCode() {
		return standardAddonCode;
	}

	public void setStandardAddonCode(String standardAddonCode) {
		this.standardAddonCode = standardAddonCode;
	}

	public String getBothFollow() {
		return bothFollow;
	}

	public void setBothFollow(String bothFollow) {
		this.bothFollow = bothFollow;
	}

	public String getMbgroupId() {
		return mbgroupId;
	}

	public void setMbgroupId(String mbgroupId) {
		this.mbgroupId = mbgroupId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder()
			.append("[loginId:").append(loginId)
			.append(",ownerId:").append(ownerId)
			.append(",workspaceId:").append(workspaceId)
			.append(",registerOnly:").append(registerOnly)
			.append(",standardType:").append(standardType)
			.append(",standardMblogId:").append(standardMblogId)
			.append(",fetchSize:").append(fetchSize)
			.append(",searchString:").append(searchString)
			.append(",mbtagType:").append(mbtagType)
			.append(",searchWord:").append(searchWord)
			.append(",standardUserId:").append(standardUserId)
			.append(",addonType:").append(addonType)
			.append(",standardAddonCode:").append(standardAddonCode).append("]");
		return str.toString();
	}

	public String getOwnerGroupId() {
		return ownerGroupId;
	}

	public void setOwnerGroupId(String ownerGroupId) {
		this.ownerGroupId = ownerGroupId;
	}

	public String getMblogId() {
		return mblogId;
	}

	public void setMblogId(String mblogId) {
		this.mblogId = mblogId;
	}

	public int getNowSize() {
		return nowSize;
	}

	public void setNowSize(int nowSize) {
		this.nowSize = nowSize;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	
}
