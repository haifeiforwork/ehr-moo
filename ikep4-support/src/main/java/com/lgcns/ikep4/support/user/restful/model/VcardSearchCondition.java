package com.lgcns.ikep4.support.user.restful.model;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 
 * 명함 SearchCondition
 *
 * @version $Id$
 */
public class VcardSearchCondition extends SearchCondition {

	private static final long serialVersionUID = -8318422231225692545L;
	
	private String searchColumn;
	
	private String searchWord;
	
	private String userId;
	
	private String folderId;
	
	private String myVcardFlag;
	
	private String indexSearchText;
	
	private String localeCode;
	
	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getIndexSearchText() {
		return indexSearchText;
	}

	public void setIndexSearchText(String indexSearchText) {
		this.indexSearchText = indexSearchText;
	}

	public String getMyVcardFlag() {
		return myVcardFlag;
	}

	public void setMyVcardFlag(String myVcardFlag) {
		this.myVcardFlag = myVcardFlag;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
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
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}