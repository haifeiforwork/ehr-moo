package com.lgcns.ikep4.support.memo.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class MemoSearchCondition extends SearchCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -3531036098782197779L;
		

	/** 검색어. */
	private String searchWord;
	private String registerId;

	public String getSearchWord() {
		return searchWord;
	}


	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}


	public String getRegisterId() {
		return registerId;
	}


	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	
}
