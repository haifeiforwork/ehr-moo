/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.search;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.lgcns.ikep4.framework.web.SearchCondition;

// TODO: Auto-generated Javadoc
/**
 * 게시판 검색조건 모델 클래스.
 */
public class AwardSearchCondition extends SearchCondition {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 게시판 ID. */
	private String awardId;

	/** 검색 컬럼. */
	private String searchColumn;

	/** 검색어. */
	private String searchWord;

	/** 보기 모드 (리스트, 요약, 간단, 갤러리). */
	private String viewMode;

	/** 레이아웃 (디폴트, 2Frame). */
	@JsonIgnore
	private String layoutType;

	/** 사용자 ID. */
	private String userId;

	/** 관리자 여부. */
	private Boolean isAdmin;

	/**
	 * Gets the search column.
	 *
	 * @return the search column
	 */
	public String getSearchColumn() {
		return this.searchColumn;
	}

	/**
	 * Sets the search column.
	 *
	 * @param searchColumn the new search column
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * Gets the search word.
	 *
	 * @return the search word
	 */
	public String getSearchWord() {
		return this.searchWord;
	}

	/**
	 * Sets the search word.
	 *
	 * @param searchWord the new search word
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * Gets the view mode.
	 *
	 * @return the view mode
	 */
	public String getViewMode() {
		return this.viewMode;
	}

	/**
	 * Sets the view mode.
	 *
	 * @param viewMode the new view mode
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	/**
	 * Gets the layout type.
	 *
	 * @return the layout type
	 */
	public String getLayoutType() {
		return this.layoutType;
	}

	/**
	 * Sets the layout type.
	 *
	 * @param layoutType the new layout type
	 */
	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	/**
	 * Gets the award id.
	 *
	 * @return the award id
	 */
	public String getAwardId() {
		return this.awardId;
	}

	/**
	 * Sets the award id.
	 *
	 * @param awardId the new award id
	 */
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the checks if is admin.
	 *
	 * @return the checks if is admin
	 */
	public Boolean getIsAdmin() {
		return this.isAdmin;
	}

	/**
	 * Sets the checks if is admin.
	 *
	 * @param isAdmin the new checks if is admin
	 */
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
