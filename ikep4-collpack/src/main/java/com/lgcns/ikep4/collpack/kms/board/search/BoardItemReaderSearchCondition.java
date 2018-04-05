/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

// TODO: Auto-generated Javadoc

public class BoardItemReaderSearchCondition extends SearchCondition {



	static final long serialVersionUID = -8694590878522938222L;

	/** 게시글 ID. */
	private String boardItemId;

	/** 검색 컬럼. */
	private String searchColumn;

	/** 검색어. */
	private String searchWord;


	/** 관리자 여부. */
	private Boolean admin;

	/** 로그인 한 사용자 ID. */
	private String userId;




	public String getBoardItemId() {
		return boardItemId;
	}

	public void setBoardItemId(String boardItemId) {
		this.boardItemId = boardItemId;
	}

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
	 * Gets the admin.
	 *
	 * @return the admin
	 */
	public Boolean getAdmin() {
		return this.admin;
	}

	/**
	 * Sets the admin.
	 *
	 * @param admin the new admin
	 */
	public void setAdmin(Boolean admin) {
		this.admin = admin;
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
}