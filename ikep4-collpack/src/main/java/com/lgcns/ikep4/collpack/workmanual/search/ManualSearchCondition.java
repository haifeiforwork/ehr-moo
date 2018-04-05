package com.lgcns.ikep4.collpack.workmanual.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class ManualSearchCondition extends SearchCondition {
	private static final long serialVersionUID = -6848398914316768885L;

	/**
	 * 보기 모드
	 */
	private String viewMode = "A";
	/**
	 * 검색 타입
	 */
	private String searchType = "A";	
	/**
	 * 검색어
	 */
	private String searchText;
	/**
	 * 등록자아이디
	 */
	private String registerId;
	/**
	 * 카테고리아이디
	 */
	private String categoryId;
	/**
	 * 매뉴얼아이디
	 */
	private String manualId;
	/**
	 * 포탈아이디
	 */
	private String portalId;
	/**
	 * 매뉴얼 버젼 아이디
	 */
	private String versionId;
	/**
	 * 1단계 메뉴 경로
	 */
	private String pathStep = "A"; //A:메인페이지  B:카테고리매뉴얼   C:개인업무매뉴얼
	/**
	 * 2단계 메뉴 경로
	 */
	private String pathStep2 = "A"; //A:업무 매뉴얼 조회    B:상세 업무 매뉴얼 조회
	
	/**
	 * @return the pathStep2
	 */
	public String getPathStep2() {
		return pathStep2;
	}
	/**
	 * @param pathStep2 the pathStep2 to set
	 */
	public void setPathStep2(String pathStep2) {
		this.pathStep2 = pathStep2;
	}
	/**
	 * @return the pathStep
	 */
	public String getPathStep() {
		return pathStep;
	}
	/**
	 * @param pathStep the pathStep to set
	 */
	public void setPathStep(String pathStep) {
		this.pathStep = pathStep;
	}
	/**
	 * @return the versionId
	 */
	public String getVersionId() {
		return versionId;
	}
	/**
	 * @param versionId the versionId to set
	 */
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}
	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
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
}
