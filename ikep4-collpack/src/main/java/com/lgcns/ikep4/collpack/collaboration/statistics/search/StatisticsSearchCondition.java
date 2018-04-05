package com.lgcns.ikep4.collpack.collaboration.statistics.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class StatisticsSearchCondition extends SearchCondition {
	private static final long serialVersionUID = 1L;
	// WS ID
	private String workspaceId;
	
	private String groupValue;
	
	private String memberFlag;
	
	private String startPeriodFlag;
	
	private String endPeriodFlag;
	// 시작년
	private String startYear;
	// 시작월
	private String startMonth;
	// 종료년
	private String endYear;
	// 종료월
	private String endMonth;
	// 검색 컬럼
	private String searchColumn;
	// 검색어
	private String searchWord;
	// 사용자 ID
	private String userId;
	// 그룹ID
	private String groupId;
	
	private boolean init = Boolean.TRUE; 
	
 
	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
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


	/**
	 * @return the workspaceId
	 */
	public String getWorkspaceId() {
		return workspaceId;
	}

	/**
	 * @param workspaceId the workspaceId to set
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
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
	 * @return the groupValue
	 */
	public String getGroupValue() {
		return groupValue;
	}

	/**
	 * @param groupValue the groupValue to set
	 */
	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}

	/**
	 * @return the memberFlag
	 */
	public String getMemberFlag() {
		return memberFlag;
	}

	/**
	 * @param memberFlag the memberFlag to set
	 */
	public void setMemberFlag(String memberFlag) {
		this.memberFlag = memberFlag;
	}

	/**
	 * @return the startPeriodFlag
	 */
	public String getStartPeriodFlag() {
		return startPeriodFlag;
	}

	/**
	 * @param startPeriodFlag the startPeriodFlag to set
	 */
	public void setStartPeriodFlag(String startPeriodFlag) {
		this.startPeriodFlag = startPeriodFlag;
	}

	/**
	 * @return the endPeriodFlag
	 */
	public String getEndPeriodFlag() {
		return endPeriodFlag;
	}

	/**
	 * @param endPeriodFlag the endPeriodFlag to set
	 */
	public void setEndPeriodFlag(String endPeriodFlag) {
		this.endPeriodFlag = endPeriodFlag;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the startYear
	 */
	public String getStartYear() {
		return startYear;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	/**
	 * @return the startMonth
	 */
	public String getStartMonth() {
		return startMonth;
	}

	/**
	 * @param startMonth the startMonth to set
	 */
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	/**
	 * @return the endYear
	 */
	public String getEndYear() {
		return endYear;
	}

	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	/**
	 * @return the endMonth
	 */
	public String getEndMonth() {
		return endMonth;
	}

	/**
	 * @param endMonth the endMonth to set
	 */
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	
	
}