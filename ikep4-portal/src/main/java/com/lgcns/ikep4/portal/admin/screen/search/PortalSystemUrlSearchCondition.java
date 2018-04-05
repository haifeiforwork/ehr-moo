package com.lgcns.ikep4.portal.admin.screen.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class PortalSystemUrlSearchCondition extends SearchCondition {
	
	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 5444241004820284061L;

	private String systemUrlId;
	
	private String systemUrlName;
	
	private String url;
	
	private String searchColumn;
	
	private String searchWord;
	
	private String fieldName;
	
	private String userLocaleCode;
	
	private String portalId;

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getSystemUrlId() {
		return systemUrlId;
	}

	public void setSystemUrlId(String systemUrlId) {
		this.systemUrlId = systemUrlId;
	}

	public String getSystemUrlName() {
		return systemUrlName;
	}

	public void setSystemUrlName(String systemUrlName) {
		this.systemUrlName = systemUrlName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
}