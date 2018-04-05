/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.search;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * 게시글 검색조건 모델 클래스
 */
public class BoardItemSearchCondition extends SearchCondition {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String workspaceId;
	
	// 게시판 구분
	private String isKnowhow;

	private String userId;

	/** 게시판 ID */
	private String boardId;

	/** 검색 컬럼 */
	private String searchColumn;

	/** 검색어 */
	private String searchWord;

	/** 보기 모드 (리스트, 요약, 간단, 갤러리) */
	private String viewMode;

	/** 레이아웃 (디폴트, 2Frame) */
	private String layoutType = "0";

	/** 관리자 여부. */
	private Boolean admin;
	
	private String isAll;
	
	// 팀 아이디
	private String teamId;

	// 포탈아이디
	private String portalId;

	private boolean init = Boolean.TRUE;

	// 게시물 아이디
	private String itemId;
	
	// 정보등급
	private String infoGrage;
	
	//사업장이름
	private String searchWorkPlaceName;
	
	//팀코드
	private String searchGroupId;
	
	//날짜입력구분
	private String searchRegType;
	
	//날짜시작
	private String searchStartRegDate;
	private String searchStartRegDate1;
	
	//날짜종료
	private String searchEndRegDate;
	private String searchEndRegDate1;
	
	//등록자
	private String searchRegisterName;
	
	//글제목
	private String searchTitle;
	
	//등록자ID
	private String registerId;
	
	//전문가ID
	private String assessorId;
	
	private String gubun;
	
	/**
	 * 사용자의 직군 코드
	 */
	private String jobClassCode;
	
	
	private String searchYear;
	private String searchMonth;
	private String workPlaceName;
	private String teamCode;
	private String teamName;
	private String startDate;
	private String endDate;
	private String monthCnt;
	private String expertName;
	private String searchIsknowhow;
	private String isPerson = "Y";	
	private String fromLeft;
	
	private String registerName;
	private String userName;
	
	public String getJobClassCode() {
		return jobClassCode;
	}

	public void setJobClassCode(String jobClassCode) {
		this.jobClassCode = jobClassCode;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getInfoGrage() {
		return infoGrage;
	}

	public void setInfoGrage(String infoGrage) {
		this.infoGrage = infoGrage;
	}

	/**
	 * Gets the board id.
	 * 
	 * @return the board id
	 */
	public String getBoardId() {
		return this.boardId;
	}

	/**
	 * Sets the board id.
	 * 
	 * @param boardId the new board id
	 */
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	/**
	 * Gets the search column.
	 * 
	 * @return the search column
	 */
	public String getSearchColumn() {
		return this.searchColumn;
	}

	/**
	 * Sets the search column.
	 * 
	 * @param searchColumn the new search column
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * Gets the search word.
	 * 
	 * @return the search word
	 */
	public String getSearchWord() {
		return this.searchWord;
	}

	/**
	 * Sets the search word.
	 * 
	 * @param searchWord the new search word
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * Gets the view mode.
	 * 
	 * @return the view mode
	 */
	public String getViewMode() {
		return this.viewMode;
	}

	/**
	 * Sets the view mode.
	 * 
	 * @param viewMode the new view mode
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	/**
	 * Gets the layout type.
	 * 
	 * @return the layout type
	 */
	public String getLayoutType() {
		return this.layoutType;
	}

	/**
	 * Sets the layout type.
	 * 
	 * @param layoutType the new layout type
	 */
	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
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

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getIsKnowhow() {
		return isKnowhow;
	}

	public void setIsKnowhow(String isKnowhow) {
		this.isKnowhow = isKnowhow;
	}

	public String getSearchWorkPlaceName() {
		return searchWorkPlaceName;
	}

	public void setSearchWorkPlaceName(String searchWorkPlaceName) {
		this.searchWorkPlaceName = searchWorkPlaceName;
	}

	public String getSearchGroupId() {
		return searchGroupId;
	}

	public void setSearchGroupId(String searchGroupId) {
		this.searchGroupId = searchGroupId;
	}

	public String getSearchRegType() {
		return searchRegType;
	}

	public void setSearchRegType(String searchRegType) {
		this.searchRegType = searchRegType;
	}

	public String getSearchStartRegDate() {
		return searchStartRegDate;
	}

	public void setSearchStartRegDate(String searchStartRegDate) {
		this.searchStartRegDate = searchStartRegDate;
	}

	public String getSearchEndRegDate() {
		return searchEndRegDate;
	}

	public void setSearchEndRegDate(String searchEndRegDate) {
		this.searchEndRegDate = searchEndRegDate;
	}

	public String getSearchStartRegDate1() {
		return searchStartRegDate1;
	}

	public void setSearchStartRegDate1(String searchStartRegDate1) {
		this.searchStartRegDate1 = searchStartRegDate1;
	}

	public String getSearchEndRegDate1() {
		return searchEndRegDate1;
	}

	public void setSearchEndRegDate1(String searchEndRegDate1) {
		this.searchEndRegDate1 = searchEndRegDate1;
	}

	public String getSearchRegisterName() {
		return searchRegisterName;
	}

	public void setSearchRegisterName(String searchRegisterName) {
		this.searchRegisterName = searchRegisterName;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getAssessorId() {
		return assessorId;
	}

	public void setAssessorId(String assessorId) {
		this.assessorId = assessorId;
	}

	public String getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear;
	}

	public String getSearchMonth() {
		return searchMonth;
	}

	public void setSearchMonth(String searchMonth) {
		this.searchMonth = searchMonth;
	}

	public String getWorkPlaceName() {
		return workPlaceName;
	}

	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMonthCnt() {
		return monthCnt;
	}

	public void setMonthCnt(String monthCnt) {
		this.monthCnt = monthCnt;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getSearchIsknowhow() {
		return searchIsknowhow;
	}

	public void setSearchIsknowhow(String searchIsknowhow) {
		this.searchIsknowhow = searchIsknowhow;
	}

	public String getIsPerson() {
		return isPerson;
	}

	public void setIsPerson(String isPerson) {
		this.isPerson = isPerson;
	}

	public String getFromLeft() {
		return fromLeft;
	}

	public void setFromLeft(String fromLeft) {
		this.fromLeft = fromLeft;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}