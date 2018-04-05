package com.lgcns.ikep4.support.activitystream.model;

import java.util.List;

import com.lgcns.ikep4.framework.web.SearchCondition;


public class ActivityStreamSearchCondition extends SearchCondition {

	private static final long serialVersionUID = 1L;

	/**
	 * 검색 컬럼
	 */
	private String searchColumn;

	/**
	 * 검색어
	 */
	private String searchWord;

	/**
	 * 뷰모드
	 */
	private String viewMode;

	/**
	 * 레이아웃
	 */
	private String layoutType;

	/**
	 * 모듈명
	 */
	private String moduleFieldName;

	/**
	 * 사용자 세션코드
	 */
	private String userLocaleCode;

	/**
	 * Activity 필드명
	 */
	private String activityFieldName;

	/**
	 * 부서 리스트
	 */
	private List<String> groupList;

	/**
	 * 전체여부
	 */
	private String isAll;

	/**
	 * following 여부
	 */
	private String isFollowing;

	/**
	 * favorite 여부
	 */
	private String isFavorite;

	/**
	 * my 여부
	 */
	private String isMy;

	/**
	 * user code 등록 여부
	 */
	private String isUserCode;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 초기화 여부
	 */
	private boolean init = Boolean.TRUE;

	@Override
	protected Integer getDefaultPagePerRecord() {
		return 10;
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

	public String getModuleFieldName() {
		return moduleFieldName;
	}

	public void setModuleFieldName(String moduleFieldName) {
		this.moduleFieldName = moduleFieldName;
	}

	public String getUserLocaleCode() {
		return userLocaleCode;
	}

	public void setUserLocaleCode(String userLocaleCode) {
		this.userLocaleCode = userLocaleCode;
	}

	public String getActivityFieldName() {
		return activityFieldName;
	}

	public void setActivityFieldName(String activityFieldName) {
		this.activityFieldName = activityFieldName;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public List<String> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<String> groupList) {
		this.groupList = groupList;
	}

	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	public String getIsFollowing() {
		return isFollowing;
	}

	public void setIsFollowing(String isFollowing) {
		this.isFollowing = isFollowing;
	}

	public String getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}

	public String getIsMy() {
		return isMy;
	}

	public void setIsMy(String isMy) {
		this.isMy = isMy;
	}

	public String getIsUserCode() {
		return isUserCode;
	}

	public void setIsUserCode(String isUserCode) {
		this.isUserCode = isUserCode;
	}

}