package com.lgcns.ikep4.lightpack.facility.model;

import java.util.Date;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class FacilitySearchCondition extends SearchCondition {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2783805823099398850L;

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
	 * 상세정보 조회시 사용할 사용자 id
	 */
	private String userId;
	
	private String searchType;
	
	/**
	 * 포탈 아이디
	 */
	private String portalId;
	
	private Date startDate;
	
	private Date endDate;

	
	/** 관리자 여부. */
	private Boolean admin;
	
	private String floorId;
	
	private String buildingId;
	
	
	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
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
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the userLocaleCode
	 */
	public String getUserLocaleCode() {
		return userLocaleCode;
	}

	/**
	 * @param userLocaleCode the userLocaleCode to set
	 */
	public void setUserLocaleCode(String userLocaleCode) {
		this.userLocaleCode = userLocaleCode;
	}

	/**
	 * @return the itemTypeCode
	 */
	public String getItemTypeCode() {
		return itemTypeCode;
	}

	/**
	 * @param itemTypeCode the itemTypeCode to set
	 */
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

}