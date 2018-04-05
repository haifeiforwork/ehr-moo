package com.lgcns.ikep4.portal.admin.screen.model;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 
 * 포탈 페이지 SearchCondition
 *
 * @author 임종상
 * @version $Id: PortalPageSearchCondition.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalPageSearchCondition extends SearchCondition {

	private static final long serialVersionUID = 3581799961963752751L;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 검색 컬럼
	 */
	private String searchColumn;

	/**
	 * 검색어
	 */
	private String searchWord;
	
	/**
	 * 필드 이름
	 */
	private String fieldName;
	
	/**
	 * 유저 로케일
	 */
	private String userLocaleCode;
	
	/**
	 * 페이지 ID
	 */
	private String pageId;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getUserLocaleCode() {
		return userLocaleCode;
	}

	public void setUserLocaleCode(String userLocaleCode) {
		this.userLocaleCode = userLocaleCode;
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
	
	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
}