/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.testreq.search;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 시험의뢰서 searchCondition
 * 
 * @author pjh
 * @version $Id$
 */
public class TestRequestSearchCondition extends SearchCondition{
	
	/** 관리 번호. */
	private String trMgntNo;
	
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
	
	/** 관리자 여부. */
	private Boolean isAdmin;
	
	/** empNo */
	private String sessionEmpNo;
	
	/** groupId */
	private String sessionGoupId;
	
	
	/** searchStartWriteDate */
	private String searchStartWriteDate;
	
	/** searchEndWriteDate */
	private String searchEndWriteDate;
	
	/** searchReqEmpNo */
	private String searchReqEmpNo;
	private String searchReqEmpNm;
	
	/** searchRcvEmpNo */
	private String searchRcvEmpNo;
	private String searchRcvEmpNm;
	
	/** searchProcessStatus */
	private String searchProcessStatus;
	
	/** searchItemKindCd */
	private String searchItemKindCd;
	
	/** searchProductName */
	private String searchTestReqTitle;
	private String searchCompanyChkVal;
	
	/** fileItemId */
	private String fileItemId;

	/**
	 * @return the trMgntNo
	 */
	public String getTrMgntNo() {
		return trMgntNo;
	}

	/**
	 * @param trMgntNo the trMgntNo to set
	 */
	public void setTrMgntNo(String trMgntNo) {
		this.trMgntNo = trMgntNo;
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
	 * @return the sessionEmpNo
	 */
	public String getSessionEmpNo() {
		return sessionEmpNo;
	}

	/**
	 * @param sessionEmpNo the sessionEmpNo to set
	 */
	public void setSessionEmpNo(String sessionEmpNo) {
		this.sessionEmpNo = sessionEmpNo;
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
	 * @return the searchStartWriteDate
	 */
	public String getSearchStartWriteDate() {
		return searchStartWriteDate;
	}

	/**
	 * @param searchStartWriteDate the searchStartWriteDate to set
	 */
	public void setSearchStartWriteDate(String searchStartWriteDate) {
		this.searchStartWriteDate = searchStartWriteDate;
	}

	/**
	 * @return the searchEndWriteDate
	 */
	public String getSearchEndWriteDate() {
		return searchEndWriteDate;
	}

	/**
	 * @param searchEndWriteDate the searchEndWriteDate to set
	 */
	public void setSearchEndWriteDate(String searchEndWriteDate) {
		this.searchEndWriteDate = searchEndWriteDate;
	}

	/**
	 * @return the searchReqEmpNo
	 */
	public String getSearchReqEmpNo() {
		return searchReqEmpNo;
	}

	/**
	 * @param searchReqEmpNo the searchReqEmpNo to set
	 */
	public void setSearchReqEmpNo(String searchReqEmpNo) {
		this.searchReqEmpNo = searchReqEmpNo;
	}

	/**
	 * @return the searchRcvEmpNo
	 */
	public String getSearchRcvEmpNo() {
		return searchRcvEmpNo;
	}

	/**
	 * @param searchRcvEmpNo the searchRcvEmpNo to set
	 */
	public void setSearchRcvEmpNo(String searchRcvEmpNo) {
		this.searchRcvEmpNo = searchRcvEmpNo;
	}

	/**
	 * @return the searchProcessStatus
	 */
	public String getSearchProcessStatus() {
		return searchProcessStatus;
	}

	/**
	 * @param searchProcessStatus the searchProcessStatus to set
	 */
	public void setSearchProcessStatus(String searchProcessStatus) {
		this.searchProcessStatus = searchProcessStatus;
	}

	/**
	 * @return the searchItemKindCd
	 */
	public String getSearchItemKindCd() {
		return searchItemKindCd;
	}

	/**
	 * @param searchItemKindCd the searchItemKindCd to set
	 */
	public void setSearchItemKindCd(String searchItemKindCd) {
		this.searchItemKindCd = searchItemKindCd;
	}

	/**
	 * @return the searchTestReqTitle
	 */
	public String getSearchTestReqTitle() {
		return searchTestReqTitle;
	}

	/**
	 * @param searchTestReqTitle the searchTestReqTitle to set
	 */
	public void setSearchTestReqTitle(String searchTestReqTitle) {
		this.searchTestReqTitle = searchTestReqTitle;
	}

	/**
	 * @return the searchReqEmpNm
	 */
	public String getSearchReqEmpNm() {
		return searchReqEmpNm;
	}

	/**
	 * @param searchReqEmpNm the searchReqEmpNm to set
	 */
	public void setSearchReqEmpNm(String searchReqEmpNm) {
		this.searchReqEmpNm = searchReqEmpNm;
	}

	/**
	 * @return the searchRcvEmpNm
	 */
	public String getSearchRcvEmpNm() {
		return searchRcvEmpNm;
	}

	/**
	 * @param searchRcvEmpNm the searchRcvEmpNm to set
	 */
	public void setSearchRcvEmpNm(String searchRcvEmpNm) {
		this.searchRcvEmpNm = searchRcvEmpNm;
	}

	/**
	 * @return the searchCompanyChkVal
	 */
	public String getSearchCompanyChkVal() {
		return searchCompanyChkVal;
	}

	/**
	 * @param searchCompanyChkVal the searchCompanyChkVal to set
	 */
	public void setSearchCompanyChkVal(String searchCompanyChkVal) {
		this.searchCompanyChkVal = searchCompanyChkVal;
	}

	/**
	 * @return the fileItemId
	 */
	public String getFileItemId() {
		return fileItemId;
	}

	/**
	 * @param fileItemId the fileItemId to set
	 */
	public void setFileItemId(String fileItemId) {
		this.fileItemId = fileItemId;
	}
}
