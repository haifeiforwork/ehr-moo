package com.lgcns.ikep4.collpack.workmanual.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class ApprovalSearchCondition extends SearchCondition {
	private static final long serialVersionUID = -6599119504142468798L;
	
	/**
	 * 사용자아이디
	 */
	private String userId;
	/**
	 * 상신 타입
	 */
	private String approvalType = "B";
	/**
	 * 검색 조건 타입
	 */
	private String searchType = "A";	
	/**
	 * 검색어
	 */
	private String searchText;
	
	/**
	 * @return the approvalType
	 */
	public String getApprovalType() {
		return approvalType;
	}
	/**
	 * @param approvalType the approvalType to set
	 */
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}
	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	
}
