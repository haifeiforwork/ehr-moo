/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.search;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.web.SearchCondition;


public class ArAbuseSearchCondition extends SearchCondition  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * 검색어
	 */
	private String keyword;

	/**
	 * 검색어
	 */
	private String searchWord;

	/**
	 * 모듈 코드
	 */
	
	private String moduleCode;

	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 검색 From 날짜
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date startDate;

	/**
	 * 검색 To 날짜
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date endDate;

	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	
	
}
