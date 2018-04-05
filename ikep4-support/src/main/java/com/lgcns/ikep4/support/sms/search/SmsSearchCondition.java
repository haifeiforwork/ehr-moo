package com.lgcns.ikep4.support.sms.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class SmsSearchCondition extends SearchCondition{

	private static final long serialVersionUID = -7351588435452407613L;

	/**
	 * 검색 컬럼
	 */
	private String searchColumn;

	/**
	 * 검색어
	 */
	private String searchWord;
	
	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
}