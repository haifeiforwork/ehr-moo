/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.model;


public class Relation extends RelationKey {
    /**
	 *
	 */
	private static final long serialVersionUID = -7885929523465436691L;

	/**
	 * ID[ID]
	 */
	private String id;

	/**
	 * SEARCH_KEYWORD[검색 키워드 이름]
	 */
	private String searchKeyword;

	/**
	 * RELATION_KEYWORD[연관 검색 키워드 이름]
	 */
	private String relationKeyword;

	/**
	 * FREQUENCY_COUNT[검색 키워드와 연관 검색 키워드 쌍의 발생 빈도수]
	 */
	private Integer frequencyCount;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id =id;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword == null ? null : searchKeyword.trim();
	}

	public String getRelationKeyword() {
		return relationKeyword;
	}

	public void setRelationKeyword(String relationKeyword) {
		this.relationKeyword = relationKeyword == null ? null : relationKeyword.trim();
	}

	public Integer getFrequencyCount() {
		return frequencyCount;
	}

	public void setFrequencyCount(Integer frequencyCount) {
		this.frequencyCount = frequencyCount;
	}
}