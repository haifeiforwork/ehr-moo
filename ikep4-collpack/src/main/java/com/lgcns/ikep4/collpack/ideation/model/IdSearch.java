/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import java.util.Date;
import java.util.List;

/**
 * 토론글 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdSearch.java 15406 2011-06-22 07:22:25Z loverfairy $
 */
public class IdSearch extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 2289890139646564801L;


	/**
	 * 토론글 아이템 ID
	 */
	private String itemId;


    /**
     * 토론글 제목
     */
    private String title;


    /**
     * 등록자 이름
     */
    private String userId;

    
    /**
	 * 게시판 가져올 끝 수
	 */
	private Integer endRowIndex;
	
	/**
	 * 게시판 가져올 처음 수
	 */
	private Integer startRowIndex;
	
	/**
	 * 현재 페이지 번호
	 */
	private int pageIndex = 1;
	
	/**
	 * 정렬 타입
	 */
	private String orderType;
	
	/**
	 * 한계 날짜
	 */
	private String limitDate;
	
	/**
	 * 동의계수 한계
	 */
	private String limitAgreementCount;
	
	/**
	 * 포털 ID
	 */
	private String portalId;
	
	
	/**
	 * 삭제 플래그
	 */
	private String itemDelete;
	
	/**
	 * 토론글 리스트
	 */
	private List<String> itemIdList;
	
	/**
	 * 리스트 타입
	 */
	private String listType;
	
	/**
	 * 검색 컬럼
	 */
	private String searchColumn;
	
	/**
	 * 검색 단어
	 */
	private String searchWord;
	
	/**
	 * 자식 토론글이 있는지
	 */
	private String isItem;
	
	/**
	 * 내가 등록한 자료인지
	 */
	private String isMy;
	
	/**
	 * best 인지
	 */
	private String isBest;
	
	/**
	 * tab 타입
	 */
	private String tabType;
	
	/**
	 * 사업화 인지
	 */
	private String isBusiness;
	
	/**
	 * 사용자가 채택한 idea 인지
	 */
	private String isUserAdopt;
	
	/**
	 * 제안이면
	 */
	private String isSuggestion;
	
	/**
	 * 참여이면
	 */
	private String isContribution;
	
	/**
	 * 검색 From 날짜
	 */
	private Date startDate;

	/**
	 * 검색 To 날짜
	 */
	private Date endDate;
	
	/**
	 * 채택됬는지
	 */
	private String isAdopt;
	
	/**
	 * 시작날짜 스트링
	 */
	private String startDateToString;
	
	/**
	 * 끝날짜 스트링
	 * @return
	 */
	private String endDateToString;
	
	/**
	 * 페이지 row 개수
	 */
	private int pagePer = 0;
	
	 /**
     * iframe 모드 인지
     */
    private String docIframe;
	
    private String categoryId;
    
    private String isNullCategory;
    
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public Integer getEndRowIndex() {
		return endRowIndex;
	}

	public void setEndRowIndex(Integer endRowIndex) {
		this.endRowIndex = endRowIndex;
	}

	public Integer getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(Integer startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getLimitAgreementCount() {
		return limitAgreementCount;
	}

	public void setLimitAgreementCount(String limitAgreementCount) {
		this.limitAgreementCount = limitAgreementCount;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getIsBest() {
		return isBest;
	}

	public void setIsBest(String best) {
		this.isBest = best;
	}


	public List<String> getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(List<String> itemIdList) {
		this.itemIdList = itemIdList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemDelete() {
		return itemDelete;
	}

	public void setItemDelete(String itemDelete) {
		this.itemDelete = itemDelete;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
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


	public String getIsItem() {
		return isItem;
	}

	public void setIsItem(String isItem) {
		this.isItem = isItem;
	}

	public String getIsMy() {
		return isMy;
	}

	public void setIsMy(String isMy) {
		this.isMy = isMy;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public String getIsBusiness() {
		return isBusiness;
	}

	public void setIsBusiness(String isBusiness) {
		this.isBusiness = isBusiness;
	}

	public String getIsUserAdopt() {
		return isUserAdopt;
	}

	public void setIsUserAdopt(String isUserAdopt) {
		this.isUserAdopt = isUserAdopt;
	}

	public String getIsSuggestion() {
		return isSuggestion;
	}

	public void setIsSuggestion(String isSuggestion) {
		this.isSuggestion = isSuggestion;
	}

	public String getIsContribution() {
		return isContribution;
	}

	public void setIsContribution(String isContribution) {
		this.isContribution = isContribution;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIsAdopt() {
		return isAdopt;
	}

	public void setIsAdopt(String isAdopt) {
		this.isAdopt = isAdopt;
	}

	public String getStartDateToString() {
		return startDateToString;
	}

	public void setStartDateToString(String startDateToString) {
		this.startDateToString = startDateToString;
	}

	public String getEndDateToString() {
		return endDateToString;
	}

	public void setEndDateToString(String endDateToString) {
		this.endDateToString = endDateToString;
	}

	public int getPagePer() {
		return pagePer;
	}

	public void setPagePer(int pagePer) {
		this.pagePer = pagePer;
	}

	public String getDocIframe() {
		return docIframe;
	}

	public void setDocIframe(String docIframe) {
		this.docIframe = docIframe;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getIsNullCategory() {
		return isNullCategory;
	}

	public void setIsNullCategory(String isNullCategory) {
		this.isNullCategory = isNullCategory;
	}
}