/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.model;


public class History extends SpBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -891348293434769781L;

	/**
	 * SEARCH_HISTORY_ID[사용자별 검색 이력 ID]
	 */
	private String searchHistoryId;

	/**
	 * SEARCH_KEYWORD_ID[검색 키워드 ID]
	 */
	private String searchKeywordId;

	/**
	 * SEARCH_KEYWORD[검색 키워드 이름(검색키워드는 단어별 구분이 아닌 사용자가 입력한 전체 검색구문 기준)]
	 */
	private String searchKeyword;
	
	private Integer initFrequencyCount;
	private Integer frequencyCount;
	private Integer prevFrequencyCount;
	
	private Integer totalCount;
	
	private String prefixSearchKeywordId;

	public String getSearchHistoryId() {
		return searchHistoryId;
	}

	public void setSearchHistoryId(String searchHistoryId) {
		this.searchHistoryId = searchHistoryId == null ? null : searchHistoryId.trim();
	}

	public String getSearchKeywordId() {
		return searchKeywordId;
	}

	public void setSearchKeywordId(String searchKeywordId) {
		this.searchKeywordId = searchKeywordId == null ? null : searchKeywordId.trim();
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword == null ? null : searchKeyword.trim();
	}

	public Integer getInitFrequencyCount() {
		return initFrequencyCount;
	}

	public Integer getFrequencyCount() {
		return frequencyCount;
	}

	public void setInitFrequencyCount(Integer initFrequencyCount) {
		this.initFrequencyCount = initFrequencyCount;
	}

	public void setFrequencyCount(Integer frequencyCount) {
		this.frequencyCount = frequencyCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getPrefixSearchKeywordId() {
		return prefixSearchKeywordId;
	}

	public void setPrefixSearchKeywordId(String prefixSearchKeywordId) {
		this.prefixSearchKeywordId = prefixSearchKeywordId;
	}

	public Integer getPrevFrequencyCount() {
		return prevFrequencyCount;
	}

	public void setPrevFrequencyCount(Integer prevFrequencyCount) {
		this.prevFrequencyCount = prevFrequencyCount;
	}
}