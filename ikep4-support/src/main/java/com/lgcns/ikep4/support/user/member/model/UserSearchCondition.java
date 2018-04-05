package com.lgcns.ikep4.support.user.member.model;

import com.lgcns.ikep4.framework.web.SearchCondition;


public class UserSearchCondition extends SearchCondition {
	
	private static final long serialVersionUID = 1L;

	private String searchColumn;

	private String searchWord;

	private String viewMode;

	private String layoutType;
	
	private String groupTypeId;
	
	private String isAllUser;
	
	private String userId;
	
	private String portalId;
	
	private String groupId;
	
	private String childGroupIds;

	private boolean init = Boolean.TRUE;
	
	private String fieldName;
	
	private String userLocaleCode;
	
	private String jobDutyCode;
		
	private String jobTitleCode;
	
	private String userFlag;
	
	private String SearchUserId;
	
	private String favoriteId;
	
	private String mssAuthCode;
	
	private String workPlaceName;
	
	private String period;
	
	private String teamCode;
	
	private String teamName;
	
	private String passwordChangeYn;


	@Override
	protected Integer getDefaultPagePerRecord() {
		return 10;
	}

	public String getChildGroupIds() {
		return childGroupIds;
	}

	public void setChildGroupIds(String childGroupIds) {
		this.childGroupIds = childGroupIds;
	}

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

	public String getGroupTypeId() {
		return groupTypeId;
	}

	public void setGroupTypeId(String groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	public String getIsAllUser() {
		return isAllUser;
	}

	public void setIsAllUser(String isAllUser) {
		this.isAllUser = isAllUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getUserLocaleCode() {
		return userLocaleCode;
	}

	public void setUserLocaleCode(String userLocaleCode) {
		this.userLocaleCode = userLocaleCode;
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
	
	public String getJobDutyCode() {
		return jobDutyCode;
	}

	public void setJobDutyCode(String jobDutyCode) {
		this.jobDutyCode = jobDutyCode;
	}

	public String getJobTitleCode() {
		return jobTitleCode;
	}

	public void setJobTitleCode(String jobTitleCode) {
		this.jobTitleCode = jobTitleCode;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public String getSearchUserId() {
		return SearchUserId;
	}

	public void setSearchUserId(String searchUserId) {
		SearchUserId = searchUserId;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getMssAuthCode() {
		return mssAuthCode;
	}

	public void setMssAuthCode(String mssAuthCode) {
		this.mssAuthCode = mssAuthCode;
	}

	public String getWorkPlaceName() {
		return workPlaceName;
	}

	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getPasswordChangeYn() {
		return passwordChangeYn;
	}

	public void setPasswordChangeYn(String passwordChangeYn) {
		this.passwordChangeYn = passwordChangeYn;
	}
	
	
}