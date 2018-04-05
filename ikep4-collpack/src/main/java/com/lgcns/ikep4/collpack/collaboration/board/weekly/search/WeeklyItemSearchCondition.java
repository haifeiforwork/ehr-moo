package com.lgcns.ikep4.collpack.collaboration.board.weekly.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class WeeklyItemSearchCondition extends SearchCondition {
	private static final long serialVersionUID = 1L;
	// WS ID
	private String workspaceId;
	// 주간보고 기간
	private String weeklyTerm;
	// 팀 ID
	private String teamId;
	// 포탈ID
	private String portalId;
	// 검색 컬럼
	private String searchColumn;
	// 검색어
	private String searchWord;
	// 조회 모드
	private String viewMode;
	// 레이아웃 타입
	private String layoutType; 
	
	private boolean init = Boolean.TRUE; 
	// 게시물 ID
	private String itemId;
	// 취한 갯수
	private int summaryCount;

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
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

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the weeklyTerm
	 */
	public String getWeeklyTerm() {
		return weeklyTerm;
	}

	/**
	 * @param weeklyTerm the weeklyTerm to set
	 */
	public void setWeeklyTerm(String weeklyTerm) {
		this.weeklyTerm = weeklyTerm;
	}

	/**
	 * @return the summaryCount
	 */
	public int getSummaryCount() {
		return summaryCount;
	}

	/**
	 * @param summaryCount the summaryCount to set
	 */
	public void setSummaryCount(int summaryCount) {
		this.summaryCount = summaryCount;
	}
}