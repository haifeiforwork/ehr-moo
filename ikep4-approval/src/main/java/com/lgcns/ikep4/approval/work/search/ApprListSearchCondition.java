/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.search;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * 기안함 목록
 * 
 * @author
 * @version $Id$
 */
public class ApprListSearchCondition extends SearchCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 문서상태 (0:임시저장, 1:진행중, 2:완료, 3:반려, 4:회수, 5:오류..)
	 */
	private String searchApprDocStatus;

	private String searchApprDocType;

	private String apprId;

	/**
	 * 기안일
	 */
	private String searchStartDate;

	/**
	 * 기안일
	 */
	private String searchEndDate;

	/**
	 * 결재문서 제목
	 */
	private String searchApprTitle;

	/**
	 * 양식명
	 */
	private String searchFormName;

	/**
	 * 기안자
	 */
	private String userId;

	/**
	 * 부서ID
	 */
	private String groupId;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 위임자
	 */
	private String entrustId;

	/**
	 * 기안자
	 */
	private String searchUserId;

	private String searchUserName;

	private String searchReqUserName;

	private String searchEntrustUserName;

	private String searchGroupId;

	private String searchReqUserId;

	private String searchEntrustUserId;

	private String searchSignUserId;

	private String searchIsHidden;

	private String searchListType;

	/**
	 * 목록타입
	 */
	private String listType;

	private String linkType;

	private String entrustUserId;

	private String searchColumn;

	private String searchWord;

	/**
	 * 제목
	 */
	private String title;

	/**
	 * 수신
	 */
	private String receiver;

	private String searchApprDocNo;

	private String searchlistSortType;

	private String entrustType;

	/**
	 * 접수자 지정 여부
	 */
	private boolean receptionUser;

	private String searchStartYear;

	private String searchStartMonth;

	private String searchEndYear;

	private String searchEndMonth;

	private String searchStatisType;

	private String ym1;

	private String ym2;

	private String ym3;

	private String ym4;

	private String ym5;

	private String ym6;

	private String ym7;

	private String ym8;

	private String ym9;

	private String ym10;

	private String ym11;

	private String ym12;

	private int ymSize;

	private String formParentId;

	private String topFormParentId;

	private String formParentName;

	private String isExcel;
	
	private String folderId;
	
	private String folderName;
	
	private String folderParentId;

	@Override
	public Integer getDefaultPagePerRecord() {
		return 10;
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
	 * 목록 조회시에 정렬할 기준이 되는 컬럼명
	 */
	private String sortColumn;

	/**
	 * 오름차순/내림차순의 정렬 타입
	 */
	private String sortType;

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSearchApprDocStatus() {
		return searchApprDocStatus;
	}

	public void setSearchApprDocStatus(String searchApprDocStatus) {
		this.searchApprDocStatus = searchApprDocStatus;
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

	public String getSearchApprTitle() {
		return searchApprTitle;
	}

	public void setSearchApprTitle(String searchApprTitle) {
		this.searchApprTitle = searchApprTitle;
	}

	public String getSearchFormName() {
		return searchFormName;
	}

	public void setSearchFormName(String searchFormName) {
		this.searchFormName = searchFormName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getSearchUserId() {
		return searchUserId;
	}

	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
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

	public String getSearchGroupId() {
		return searchGroupId;
	}

	public void setSearchGroupId(String searchGroupId) {
		this.searchGroupId = searchGroupId;
	}

	public String getSearchReqUserId() {
		return searchReqUserId;
	}

	public void setSearchReqUserId(String searchReqUserId) {
		this.searchReqUserId = searchReqUserId;
	}

	public String getSearchApprDocType() {
		return searchApprDocType;
	}

	public void setSearchApprDocType(String searchApprDocType) {
		this.searchApprDocType = searchApprDocType;
	}

	public String getSearchEntrustUserId() {
		return searchEntrustUserId;
	}

	public void setSearchEntrustUserId(String searchEntrustUserId) {
		this.searchEntrustUserId = searchEntrustUserId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSearchApprDocNo() {
		return searchApprDocNo;
	}

	public void setSearchApprDocNo(String searchApprDocNo) {
		this.searchApprDocNo = searchApprDocNo;
	}

	public String getSearchIsHidden() {
		return searchIsHidden;
	}

	public void setSearchIsHidden(String searchIsHidden) {
		this.searchIsHidden = searchIsHidden;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public String getSearchReqUserName() {
		return searchReqUserName;
	}

	public void setSearchReqUserName(String searchReqUserName) {
		this.searchReqUserName = searchReqUserName;
	}

	public String getSearchEntrustUserName() {
		return searchEntrustUserName;
	}

	public void setSearchEntrustUserName(String searchEntrustUserName) {
		this.searchEntrustUserName = searchEntrustUserName;
	}

	public String getSearchListType() {
		return searchListType;
	}

	public void setSearchListType(String searchListType) {
		this.searchListType = searchListType;
	}

	public String getSearchSignUserId() {
		return searchSignUserId;
	}

	public void setSearchSignUserId(String searchSignUserId) {
		this.searchSignUserId = searchSignUserId;
	}

	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}

	public String getApprId() {
		return apprId;
	}

	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	public String getEntrustUserId() {
		return entrustUserId;
	}

	public void setEntrustUserId(String entrustUserId) {
		this.entrustUserId = entrustUserId;
	}

	public String getSearchlistSortType() {
		return searchlistSortType;
	}

	public void setSearchlistSortType(String searchlistSortType) {
		this.searchlistSortType = searchlistSortType;
	}

	public String getEntrustType() {
		return entrustType;
	}

	public void setEntrustType(String entrustType) {
		this.entrustType = entrustType;
	}

	public boolean isReceptionUser() {
		return receptionUser;
	}

	public void setReceptionUser(boolean receptionUser) {
		this.receptionUser = receptionUser;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getYm1() {
		return ym1;
	}

	public void setYm1(String ym1) {
		this.ym1 = ym1;
	}

	public String getYm2() {
		return ym2;
	}

	public void setYm2(String ym2) {
		this.ym2 = ym2;
	}

	public String getYm3() {
		return ym3;
	}

	public void setYm3(String ym3) {
		this.ym3 = ym3;
	}

	public String getYm4() {
		return ym4;
	}

	public void setYm4(String ym4) {
		this.ym4 = ym4;
	}

	public String getYm5() {
		return ym5;
	}

	public void setYm5(String ym5) {
		this.ym5 = ym5;
	}

	public String getYm6() {
		return ym6;
	}

	public void setYm6(String ym6) {
		this.ym6 = ym6;
	}

	public String getYm7() {
		return ym7;
	}

	public void setYm7(String ym7) {
		this.ym7 = ym7;
	}

	public String getYm8() {
		return ym8;
	}

	public void setYm8(String ym8) {
		this.ym8 = ym8;
	}

	public String getYm9() {
		return ym9;
	}

	public void setYm9(String ym9) {
		this.ym9 = ym9;
	}

	public String getYm10() {
		return ym10;
	}

	public void setYm10(String ym10) {
		this.ym10 = ym10;
	}

	public String getYm11() {
		return ym11;
	}

	public void setYm11(String ym11) {
		this.ym11 = ym11;
	}

	public String getYm12() {
		return ym12;
	}

	public void setYm12(String ym12) {
		this.ym12 = ym12;
	}

	public String getSearchStartYear() {
		return searchStartYear;
	}

	public void setSearchStartYear(String searchStartYear) {
		this.searchStartYear = searchStartYear;
	}

	public String getSearchStartMonth() {
		return searchStartMonth;
	}

	public void setSearchStartMonth(String searchStartMonth) {
		this.searchStartMonth = searchStartMonth;
	}

	public String getSearchEndYear() {
		return searchEndYear;
	}

	public void setSearchEndYear(String searchEndYear) {
		this.searchEndYear = searchEndYear;
	}

	public String getSearchEndMonth() {
		return searchEndMonth;
	}

	public void setSearchEndMonth(String searchEndMonth) {
		this.searchEndMonth = searchEndMonth;
	}

	public String getSearchStatisType() {
		return searchStatisType;
	}

	public void setSearchStatisType(String searchStatisType) {
		this.searchStatisType = searchStatisType;
	}

	public String getFormParentId() {
		return formParentId;
	}

	public void setFormParentId(String formParentId) {
		this.formParentId = formParentId;
	}

	public String getTopFormParentId() {
		return topFormParentId;
	}

	public void setTopFormParentId(String topFormParentId) {
		this.topFormParentId = topFormParentId;
	}

	public String getFormParentName() {
		return formParentName;
	}

	public void setFormParentName(String formParentName) {
		this.formParentName = formParentName;
	}

	public String getIsExcel() {
		return isExcel;
	}

	public void setIsExcel(String isExcel) {
		this.isExcel = isExcel;
	}

	public int getYmSize() {
		return ymSize;
	}

	public void setYmSize(int ymSize) {
		this.ymSize = ymSize;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFolderParentId() {
		return folderParentId;
	}

	public void setFolderParentId(String folderParentId) {
		this.folderParentId = folderParentId;
	}

}
