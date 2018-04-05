package com.lgcns.ikep4.support.user.code.model;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class AdminSearchCondition extends SearchCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -1937798736945536950L;
	
	/**
	 * Code(ID) 혹은 이름 등의 검색어를 검색할 컬럼의 종류
	 */
	private String searchColumn;

	/**
	 * 검색어
	 */
	private String searchWord;

	/**
	 * 목록 조회시에 정렬할 기준이 되는 컬럼명
	 */
	private String sortColumn;

	/**
	 * 오름차순/내림차순의 정렬 타입
	 */
	private String sortType;
	
	private String viewMode;

	private String layoutType;

	
	/**
	 * 다국어 메시지가 적용될 필드명
	 */
	private String fieldName;
	
	/**
	 * 현재 로그인한 사용자의 세션에서 가져오는 로케일 코드
	 */
	private String userLocaleCode;
	
	/**
	 * 다국어 메시지에 사용되는 Item type code
	 */
	private String itemTypeCode;
	
	/**
	 * 상세정보 조회시 사용할 code 혹은 id 값
	 */
	private String code;
	
	/**
	 * 도시 조회를 위한 나라 코드
	 */
	private String nationCode;
	/**
	 * 포탈 아이디
	 */
	private String portalId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationCode() {
		return nationCode;
	}

}
