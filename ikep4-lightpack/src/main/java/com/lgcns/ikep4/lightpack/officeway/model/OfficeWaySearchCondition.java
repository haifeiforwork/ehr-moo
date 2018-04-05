package com.lgcns.ikep4.lightpack.officeway.model;

import java.util.Date;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class OfficeWaySearchCondition extends SearchCondition {
	
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
	
	 private String teamId;
	 
	 private String status1;

	 private String status2;
	 
	 private String searchStatus;
	 
	 private String startYear;
	 
	 private String startMonth;
	 
	 private String endYear;
	 
	 private String endMonth;
	 
	 private String searchStartDate;
	 
	 private String searchEndDate;
	 
	 private String searchTeam;
	 
	 private String auth;
	
	/** 관리자 여부. */
	private Boolean admin;
	
	
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

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
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

	public String getSearchTeam() {
		return searchTeam;
	}

	public void setSearchTeam(String searchTeam) {
		this.searchTeam = searchTeam;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}


}