package com.lgcns.ikep4.support.searchpreprocessor.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 
 * 검색어 서치 컨디션
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: SpSearchCondition.java 16276 2011-08-18 07:09:07Z giljae $
 */
public class SpSearchCondition extends SearchCondition {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String searchColumn;
	
	private String searchWord;

	private String viewMode;
	
	private String layoutType; 
	
	private String portalId;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	} 
	
}