/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.search;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * Form SearchCondition 확장
 * 
 * @author wonchu
 * @version $Id: FormSearchCondition.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ApprFormSearchCondition extends SearchCondition {

	static final long serialVersionUID = 9000241038587197782L;
	
	
	/**
	 * 양식목록 or 기안문목록
	 */
	private String mode;
	
	/**
	 * 검색 컬럼
	 */
	private String searchColumn;

	/**
	 * 검색어
	 */
	private String searchWord;

	/**
	 * 사용자 아이디
	 */
	private String userId;

	/**
	 * locale
	 */
	private String localeCode;

	/**
	 * 포탈 아이디
	 */
	private String portalId;

	/**
	 * 폼아이디
	 */
	private String formId;

	/**
	 * 부모 아이디
	 */
	private String formParentId;
	
	/**
	 * 부모 명
	 */
	private String formParentName;

	/**
	 * 폼 루트 아이디
	 */
	private String topFormParentId;

	/**
	 * usage
	 */
	private int usage;

	/**
	 * templateType
	 */
	private String templateType;
	
	/**
	 * linkType
	 */
	private String linkType;
	
	/**
	 * 수신처 사용 여부
	 */
	private String receive;


	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getFormParentId() {
		return formParentId;
	}

	public void setFormParentId(String formParentId) {
		this.formParentId = formParentId;
	}

	public String getTopFormParentId() {
		return topFormParentId;
	}

	public void setTopFormParentId(String topFormParentId) {
		this.topFormParentId = topFormParentId;
	}

	public int getUsage() {
		return usage;
	}

	public void setUsage(int usage) {
		this.usage = usage;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getFormParentName() {
		return formParentName;
	}

	public void setFormParentName(String formParentName) {
		this.formParentName = formParentName;
	}
	
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	
	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder().append("searchColumn:").append(searchColumn).append(",searchWord:")
				.append(searchWord);
		return str.toString();
	}

}