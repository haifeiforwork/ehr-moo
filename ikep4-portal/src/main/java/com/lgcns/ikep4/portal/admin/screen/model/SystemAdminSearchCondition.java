package com.lgcns.ikep4.portal.admin.screen.model;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 
 * 시스템 관리자 SearchCondition
 *
 * @author 임종상
 * @version $Id: SystemAdminSearchCondition.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
public class SystemAdminSearchCondition extends SearchCondition{

	/**
	 *
	 */
	private static final long serialVersionUID = 3370406696198944602L;

	/**
	 * 검색 컬럼
	 */
	private String searchColumn;

	/**
	 * 검색어
	 */
	private String searchWord;
	
	/**
	 * 리소스 이름
	 */
	private String resourceName;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * security 객체
	 */
	private PortalSecurity security;
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public PortalSecurity getSecurity() {
		return security;
	}

	public void setSecurity(PortalSecurity security) {
		this.security = security;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
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
}