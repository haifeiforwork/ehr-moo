package com.lgcns.ikep4.socialpack.socialanalyzer.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * Model Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialAnalyzerSearchCondition.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialAnalyzerSearchCondition extends SearchCondition {
	private static final long serialVersionUID = 9184349337427595638L;
	
	/**
	 * 검색어
	 */
	private String searchText = "";

	/**
	 * 소트종류
	 */
	private String sortType = "A";
	
	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}

	/**
	 * @param sortType the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
}
