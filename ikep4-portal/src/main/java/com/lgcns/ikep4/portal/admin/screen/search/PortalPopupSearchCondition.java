package com.lgcns.ikep4.portal.admin.screen.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 
 * 포탈 팝업 SearchCondition
 *
 * @author 임종상
 * @version $Id: PortalPopupSearchCondition.java 170 2011-06-08 08:32:16Z dev07 $
 */
public class PortalPopupSearchCondition extends SearchCondition{

	private static final long serialVersionUID = 5063633514967065924L;

	/**
	 * 검색 컬럼
	 */
	private String searchColumn;

	/**
	 * 검색어
	 */
	private String searchWord;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;

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
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
}