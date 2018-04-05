/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.model;


public class Dictionary extends SpBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -7944444709982177667L;

	/**
	 * SEARCH_KEYWORD_ID[검색 키워드 ID]
	 */
	private String searchKeywordId;

	/**
	 * SEARCH_KEYWORD[검색 키워드 이름 (검색키워드는 단어별 구분이 아닌 사용자가 입력한 전체 검색구문 기준)]
	 */
	private String searchKeyword;

	/**
	 * FREQUENCY_COUNT[동일 검색 키워드 발생 빈도수]
	 */
	private Integer frequencyCount;

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

	public Integer getFrequencyCount() {
		return frequencyCount;
	}

	public void setFrequencyCount(Integer frequencyCount) {
		this.frequencyCount = frequencyCount;
	}

	
}