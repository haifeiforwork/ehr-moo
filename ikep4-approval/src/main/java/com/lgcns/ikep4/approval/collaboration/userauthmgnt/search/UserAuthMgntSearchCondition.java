/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.userauthmgnt.search;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 사용자 권한 관리 searchCondition
 * 
 * @author pjh
 * @version $Id$
 */
public class UserAuthMgntSearchCondition extends SearchCondition{
	
	/** 검색 컬럼. */
	private String searchColumn;

	/** 검색어. */
	private String searchWord;

	/** 정렬컬럼. */
	private String sortColumn;
	
	/** 정렬 타입. */
	private String sortType;

	/** 보기 모드 (리스트, 요약, 간단, 갤러리). */
	private String viewMode;

	/** 레이아웃 (디폴트, 2Frame). */
	@JsonIgnore
	private String layoutType;

	/** 사용자 ID. */
	private String searchUserId;
	
	/** 사용자명. */
	private String searchUserName;

	/** 업무구분코드. */
	private String searchWorkGbnCd;
	
	/** 부서코드. */
	private String searchGroupId;
	
	/** 부서명. */
	private String searchGroupName;
	
	/** 부서Id. */
	private String searchDeptId;
	
	/** 사용자EMPNO. */
	private String searchEmpNo;
	
	/** 사용여부. */
	private String searchUseYn;
	
	/** 관리자 여부. */
	private Boolean isAdmin;
	
	private Boolean isEnableGroup;
	
	private String sessionGoupId;
	
	private Boolean isSystemAdmin;
	
	@Override
	public Integer getDefaultPagePerRecord() {
		return 10;
	}

	/**
	 * @return the searchColumn
	 */
	public String getSearchColumn() {
		return searchColumn;
	}

	/**
	 * @param searchColumn the searchColumn to set
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * @return the sortColumn
	 */
	public String getSortColumn() {
		return sortColumn;
	}

	/**
	 * @param sortColumn the sortColumn to set
	 */
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	/**
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}

	/**
	 * @param sortType the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	/**
	 * @return the viewMode
	 */
	public String getViewMode() {
		return viewMode;
	}

	/**
	 * @param viewMode the viewMode to set
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	/**
	 * @return the layoutType
	 */
	public String getLayoutType() {
		return layoutType;
	}

	/**
	 * @param layoutType the layoutType to set
	 */
	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	/**
	 * @return the searchUserId
	 */
	public String getSearchUserId() {
		return searchUserId;
	}

	/**
	 * @param searchUserId the searchUserId to set
	 */
	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}

	/**
	 * @return the searchUserName
	 */
	public String getSearchUserName() {
		return searchUserName;
	}

	/**
	 * @param searchUserName the searchUserName to set
	 */
	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	/**
	 * @return the searchWorkGbnCd
	 */
	public String getSearchWorkGbnCd() {
		return searchWorkGbnCd;
	}

	/**
	 * @param searchWorkGbnCd the searchWorkGbnCd to set
	 */
	public void setSearchWorkGbnCd(String searchWorkGbnCd) {
		this.searchWorkGbnCd = searchWorkGbnCd;
	}

	/**
	 * @return the searchGroupId
	 */
	public String getSearchGroupId() {
		return searchGroupId;
	}

	/**
	 * @param searchGroupId the searchGroupId to set
	 */
	public void setSearchGroupId(String searchGroupId) {
		this.searchGroupId = searchGroupId;
	}

	/**
	 * @return the searchGroupName
	 */
	public String getSearchGroupName() {
		return searchGroupName;
	}

	/**
	 * @param searchGroupName the searchGroupName to set
	 */
	public void setSearchGroupName(String searchGroupName) {
		this.searchGroupName = searchGroupName;
	}

	/**
	 * @return the searchDeptId
	 */
	public String getSearchDeptId() {
		return searchDeptId;
	}

	/**
	 * @param searchDeptId the searchDeptId to set
	 */
	public void setSearchDeptId(String searchDeptId) {
		this.searchDeptId = searchDeptId;
	}

	/**
	 * @return the searchEmpNo
	 */
	public String getSearchEmpNo() {
		return searchEmpNo;
	}

	/**
	 * @param searchEmpNo the searchEmpNo to set
	 */
	public void setSearchEmpNo(String searchEmpNo) {
		this.searchEmpNo = searchEmpNo;
	}

	/**
	 * @return the isAdmin
	 */
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the isEnableGroup
	 */
	public Boolean getIsEnableGroup() {
		return isEnableGroup;
	}

	/**
	 * @param isEnableGroup the isEnableGroup to set
	 */
	public void setIsEnableGroup(Boolean isEnableGroup) {
		this.isEnableGroup = isEnableGroup;
	}

	/**
	 * @return the searchUseYn
	 */
	public String getSearchUseYn() {
		return searchUseYn;
	}

	/**
	 * @param searchUseYn the searchUseYn to set
	 */
	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}

	/**
	 * @return the sessionGoupId
	 */
	public String getSessionGoupId() {
		return sessionGoupId;
	}

	/**
	 * @param sessionGoupId the sessionGoupId to set
	 */
	public void setSessionGoupId(String sessionGoupId) {
		this.sessionGoupId = sessionGoupId;
	}

	/**
	 * @return the isSystemAdmin
	 */
	public Boolean getIsSystemAdmin() {
		return isSystemAdmin;
	}

	/**
	 * @param isSystemAdmin the isSystemAdmin to set
	 */
	public void setIsSystemAdmin(Boolean isSystemAdmin) {
		this.isSystemAdmin = isSystemAdmin;
	}
}
