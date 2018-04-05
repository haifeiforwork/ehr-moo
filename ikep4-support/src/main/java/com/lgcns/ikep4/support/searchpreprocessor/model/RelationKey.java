/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.model;


public class RelationKey extends SpBaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -1495969494465281997L;

	/**
	 * SEARCH_KEYWORD_ID[검색 키워드 ID]
	 */
	private String searchKeywordId;

	private String relationKeywordId;
	
	
	public RelationKey() {
		super();
	}

	public RelationKey(String searchKeywordId, String relationKeywordId) {
		super();
		this.searchKeywordId = searchKeywordId;
		this.relationKeywordId = relationKeywordId;
	}

	public String getSearchKeywordId() {
		return searchKeywordId;
	}

	public String getRelationKeywordId() {
		return relationKeywordId;
	}

	public void setSearchKeywordId(String searchKeywordId) {
		this.searchKeywordId = searchKeywordId;
	}

	public void setRelationKeywordId(String relationKeywordId) {
		this.relationKeywordId = relationKeywordId;
	}
	
	

}