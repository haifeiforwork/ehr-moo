package com.lgcns.ikep4.support.recent.model;

import java.util.List;

import com.lgcns.ikep4.framework.web.SearchCondition;


public class RecentSearchCondition extends SearchCondition {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 검색컬럼
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
	 * 필드명
	 */
	private String fieldName;
	
	/**
	 * 모듈필드명
	 */
	private String moduleFieldName;
	
	/**
	 * 사용자 로케일 코드
	 */
	private String userLocaleCode;
	
	/**
	 * 엑티비디 필드 명
	 */
	private String activityFieldName;
	
	/**
	 * 모듈코드 리스트
	 */
	private List<String> moduleCodeList;
	
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

	public List<String> getModuleCodeList() {
		return moduleCodeList;
	}

	public void setModuleCodeList(List<String> moduleCodeList) {
		this.moduleCodeList = moduleCodeList;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}