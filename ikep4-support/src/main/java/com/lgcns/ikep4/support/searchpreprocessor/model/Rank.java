/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.model;

public class Rank extends SpBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -4940501384725171605L;

	/**
	 * SEARCH_KEYWORD_ID[검색 키워드 ID]
	 */
	private String searchKeywordId;

	/**
	 * SEARCH_RANK[전체 검색 빈도수에 따른 검색 순위]
	 */
	private Integer searchRank;

	/**
	 * PRE_SEARCH_RANK[전체 검색 빈도수에 따른 이전 검색 순위]
	 */
	private Integer preSearchRank;

	public String getSearchKeywordId() {
		return searchKeywordId;
	}

	public void setSearchKeywordId(String searchKeywordId) {
		this.searchKeywordId = searchKeywordId;
	}

	public Integer getSearchRank() {
		return searchRank;
	}

	public void setSearchRank(Integer searchRank) {
		this.searchRank = searchRank;
	}

	public Integer getPreSearchRank() {
		return preSearchRank;
	}

	public void setPreSearchRank(Integer preSearchRank) {
		this.preSearchRank = preSearchRank;
	}
}