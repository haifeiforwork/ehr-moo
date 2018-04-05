package com.lgcns.ikep4.portal.main.model;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 
 * 포탈 SearchCondition
 *
 * @author 임종상
 * @version $Id: PortalSearchCondition.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalSearchCondition extends SearchCondition {

	private static final long serialVersionUID = -6914170801419979868L;

	/**
	 * 검색컬럼
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
