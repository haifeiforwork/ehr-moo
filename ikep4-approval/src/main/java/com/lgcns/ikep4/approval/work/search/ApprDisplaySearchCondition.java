/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 공람 설정 및 해제
 *
 * @author 
 * @version $Id$
 */
public class ApprDisplaySearchCondition extends SearchCondition {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 공람여부 (0:미확인, 1:확인)
	 */
	private String searchUserStatus;
	
	/**
	 * 결재문서 ID
	 */
	private String apprId;
	
	
	
	/**
	 * 기안자
	 */
	private String userId;
	
	/**
	 * 기안자
	 */
	private String groupId;
	/**
	 * 기안자
	 */
	private String portalId;
	
	/**
	 * 기안자
	 */
	private String searchUserId;
	
	private String	searchColumn;
	
	private String	searchWord;
	
	/**
	 * 공람일시
	 */
	private String searchStartDate;
	
	/**
	 * 공람일시
	 */
	private String searchEndDate;
	
	/**
	 * 결재문서 제목
	 */
	private String searchApprTitle;
	
	private String listType;
	
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
	 * 목록 조회시에 정렬할 기준이 되는 컬럼명
	 */
	private String sortColumn;

	/**
	 * 오름차순/내림차순의 정렬 타입
	 */
	private String sortType;
	
	private String searchUserName;
	

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSearchUserId() {
		return searchUserId;
	}

	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getSearchUserStatus() {
		return searchUserStatus;
	}

	public void setSearchUserStatus(String searchUserStatus) {
		this.searchUserStatus = searchUserStatus;
	}

	public String getApprId() {
		return apprId;
	}

	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getSearchApprTitle() {
		return searchApprTitle;
	}

	public void setSearchApprTitle(String searchApprTitle) {
		this.searchApprTitle = searchApprTitle;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}
	
}
