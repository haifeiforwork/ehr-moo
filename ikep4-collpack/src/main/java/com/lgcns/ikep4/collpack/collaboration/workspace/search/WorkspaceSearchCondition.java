package com.lgcns.ikep4.collpack.collaboration.workspace.search;

import com.lgcns.ikep4.framework.web.SearchCondition;


public class WorkspaceSearchCondition extends SearchCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6031368888140196449L;

	/**
	 * 포탈ID
	 */
	private String portalId;
	
	/**
	 * 보기방식(List/Summary)
	 */
	private String viewMode;	
	
	/**
	 * 타입별 목록 ( 조직여부 : 1 )
	 */
	private int isOrganization;
	
	/**
	 * 워크스페이스 ID
	 */
	private String workspaceId;

	/**
	 * 워크스페이스 상태(WO:개설대기, O:개설, WC:폐쇄대기, C:폐쇄)
	 */
	private String workspaceStatus;	

	/**
	 * 워크스페이스 타입
	 */
	private String typeId;
	
	
	/**
	 * 목록 URL
	 */
	private String listUrl;
	/**
	 * 목록 타입
	 */
	private String listType;



	/**
	 * 멤버ID
	 */
	private String memberId;
	/**
	 * 카테고리 ID
	 */
	private String categoryId;
	/**
	 * 카테고리 명
	 */
	private String categoryName;

	/**
	 * 그룹ID
	 */
	private String groupId;
	/**
	 * 그룹명
	 */
	private String groupName;
	/**
	 * 등록자
	 */
	private String registerId;
	/**
	 * WS 전체 명
	 */
	private String fullPath;
	/**
	 * 리더 ID
	 */
	private String groupLeaderId;
	/**
	 * 리더명
	 */
	private String groupLeaderName;
	
	/**
	 * Search 컬럼
	 */
	private String searchColumn;

	/**
	 * Search Word
	 */
	private String searchWord;

	private boolean init = Boolean.TRUE;

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
	 * @return the listType
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * @param listType the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
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
	 * @return the workspaceStatus
	 */
	public String getWorkspaceStatus() {
		return workspaceStatus;
	}

	/**
	 * @param workspaceStatus the workspaceStatus to set
	 */
	public void setWorkspaceStatus(String workspaceStatus) {
		this.workspaceStatus = workspaceStatus;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
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
	 * @return the init
	 */
	public boolean isInit() {
		return init;
	}

	/**
	 * @param init the init to set
	 */
	public void setInit(boolean init) {
		this.init = init;
	}

	/**
	 * @return the groupLeaderName
	 */
	public String getGroupLeaderName() {
		return groupLeaderName;
	}

	/**
	 * @param groupLeaderName the groupLeaderName to set
	 */
	public void setGroupLeaderName(String groupLeaderName) {
		this.groupLeaderName = groupLeaderName;
	}

	/**
	 * @return the groupLeaderId
	 */
	public String getGroupLeaderId() {
		return groupLeaderId;
	}

	/**
	 * @param groupLeaderId the groupLeaderId to set
	 */
	public void setGroupLeaderId(String groupLeaderId) {
		this.groupLeaderId = groupLeaderId;
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
	 * @return the isOrganization
	 */
	public int getIsOrganization() {
		return isOrganization;
	}

	/**
	 * @param isOrganization the isOrganization to set
	 */
	public void setIsOrganization(int isOrganization) {
		this.isOrganization = isOrganization;
	}

	/**
	 * @return the listUrl
	 */
	public String getListUrl() {
		return listUrl;
	}

	/**
	 * @param listUrl the listUrl to set
	 */
	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

}