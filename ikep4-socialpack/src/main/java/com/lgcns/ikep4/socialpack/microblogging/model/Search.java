/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * Search VO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Search.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Search extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -264717660076659854L;

	/**
	 * 검색 ID
	 */
	private String searchId;

	/**
	 * 검색어
	 */
	private String searchWord;

	/**
	 * 검색 유저 id
	 */
	private String searchUserId;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 검색어 저장 여부(0: 저장안됨,1: 저장됨)
	 */
	private String isSave;

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSearchUserId() {
		return searchUserId;
	}

	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getIsSave() {
		return isSave;
	}

	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}

}
