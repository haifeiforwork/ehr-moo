/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.proposal.search;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 개발제안서 searchCondition
 * 
 * @author pjh
 * @version $Id$
 */
public class ProposalSearchCondition extends SearchCondition{
	
	/** 관리 번호. */
	private String proposalNo;
	
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
	
	/** fileItemId */
	private String fileItemId;
	
	/** searchStartReqDate */
	private String searchStartReqDate;
	
	/** searchEndReqDate */
	private String searchEndReqDate;
	
	/** searchReqEmpNo */
	private String searchReqEmpNo;
	private String searchReqEmpNm;
	
	/** searchTcsRcvEmpNo */
	private String searchTcsRcvEmpNo;
	private String searchTcsRcvEmpNm;
	
	/** searchProductName */
	private String searchProductName;

	/**
	 * @return the proposalNo
	 */
	public String getProposalNo() {
		return proposalNo;
	}

	/**
	 * @param proposalNo the proposalNo to set
	 */
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
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

	/**
	 * @return the searchStartReqDate
	 */
	public String getSearchStartReqDate() {
		return searchStartReqDate;
	}

	/**
	 * @param searchStartReqDate the searchStartReqDate to set
	 */
	public void setSearchStartReqDate(String searchStartReqDate) {
		this.searchStartReqDate = searchStartReqDate;
	}

	/**
	 * @return the searchEndReqDate
	 */
	public String getSearchEndReqDate() {
		return searchEndReqDate;
	}

	/**
	 * @param searchEndReqDate the searchEndReqDate to set
	 */
	public void setSearchEndReqDate(String searchEndReqDate) {
		this.searchEndReqDate = searchEndReqDate;
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
	 * @return the searchTcsRcvEmpNo
	 */
	public String getSearchTcsRcvEmpNo() {
		return searchTcsRcvEmpNo;
	}

	/**
	 * @param searchTcsRcvEmpNo the searchTcsRcvEmpNo to set
	 */
	public void setSearchTcsRcvEmpNo(String searchTcsRcvEmpNo) {
		this.searchTcsRcvEmpNo = searchTcsRcvEmpNo;
	}

	/**
	 * @return the searchProductName
	 */
	public String getSearchProductName() {
		return searchProductName;
	}

	/**
	 * @param searchProductName the searchProductName to set
	 */
	public void setSearchProductName(String searchProductName) {
		this.searchProductName = searchProductName;
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
	 * @return the searchTcsRcvEmpNm
	 */
	public String getSearchTcsRcvEmpNm() {
		return searchTcsRcvEmpNm;
	}

	/**
	 * @param searchTcsRcvEmpNm the searchTcsRcvEmpNm to set
	 */
	public void setSearchTcsRcvEmpNm(String searchTcsRcvEmpNm) {
		this.searchTcsRcvEmpNm = searchTcsRcvEmpNm;
	}
	
}
