package com.lgcns.ikep4.lightpack.cafe.cafe.search;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.web.SearchCondition;


/**
 * CafeSearchCondition
 * 
 * @author 유승목
 * @version $Id: CafeSearchCondition.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class CafeSearchCondition extends SearchCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6031368888140196449L;

	/**
	 * 포탈ID
	 */
	private String portalId;

	/**
	 * 보기방식(List/Summary)
	 */
	private String viewMode;

	/**
	 * 타입별 목록 ( 조직여부 : 1 )
	 */
	private int isOrganization;

	/**
	 * 워크스페이스 ID
	 */
	private String cafeId;

	/**
	 * 워크스페이스 상태(WO:개설대기, O:개설, WC:폐쇄대기, C:폐쇄)
	 */
	private String cafeStatus;

	/**
	 * 워크스페이스 타입
	 */
	private String typeId;

	private String listUrl;

	private String listType;

	private String logPage;

	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date startDate;

	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date endDate;

	private String cafeName;

	private String memberId;

	private String categoryId;

	private String categoryName;

	private String groupId;

	private String groupName;

	private String registerId;

	private String fullPath;

	private String fullEnglishPath;

	private String groupLeaderId;

	private String groupLeaderName;

	/**
	 * Search 컬럼
	 */
	private String searchColumn;

	/**
	 * Search Word
	 */
	private String searchWord;

	private boolean init = Boolean.TRUE;

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
	 * @return the listType
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * @param listType the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}

	/**
	 * @return the cafeId
	 */
	public String getCafeId() {
		return cafeId;
	}

	/**
	 * @param cafeId the cafeId to set
	 */
	public void setCafeId(String cafeId) {
		this.cafeId = cafeId;
	}

	/**
	 * @return the cafeStatus
	 */
	public String getCafeStatus() {
		return cafeStatus;
	}

	/**
	 * @param cafeStatus the cafeStatus to set
	 */
	public void setCafeStatus(String cafeStatus) {
		this.cafeStatus = cafeStatus;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	 * @return the init
	 */
	public boolean isInit() {
		return init;
	}

	/**
	 * @param init the init to set
	 */
	public void setInit(boolean init) {
		this.init = init;
	}

	/**
	 * @return the groupLeaderName
	 */
	public String getGroupLeaderName() {
		return groupLeaderName;
	}

	/**
	 * @param groupLeaderName the groupLeaderName to set
	 */
	public void setGroupLeaderName(String groupLeaderName) {
		this.groupLeaderName = groupLeaderName;
	}

	/**
	 * @return the groupLeaderId
	 */
	public String getGroupLeaderId() {
		return groupLeaderId;
	}

	/**
	 * @param groupLeaderId the groupLeaderId to set
	 */
	public void setGroupLeaderId(String groupLeaderId) {
		this.groupLeaderId = groupLeaderId;
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
	 * @return the isOrganization
	 */
	public int getIsOrganization() {
		return isOrganization;
	}

	/**
	 * @param isOrganization the isOrganization to set
	 */
	public void setIsOrganization(int isOrganization) {
		this.isOrganization = isOrganization;
	}

	/**
	 * @return the listUrl
	 */
	public String getListUrl() {
		return listUrl;
	}

	/**
	 * @param listUrl the listUrl to set
	 */
	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the fullEnglishPath
	 */
	public String getFullEnglishPath() {
		return fullEnglishPath;
	}

	/**
	 * @param fullEnglishPath the fullEnglishPath to set
	 */
	public void setFullEnglishPath(String fullEnglishPath) {
		this.fullEnglishPath = fullEnglishPath;
	}

	/**
	 * @return the logPage
	 */
	public String getLogPage() {
		return logPage;
	}

	/**
	 * @param logPage the logPage to set
	 */
	public void setLogPage(String logPage) {
		this.logPage = logPage;
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

	public String getCafeName() {
		return cafeName;
	}

	public void setCafeName(String cafeName) {
		this.cafeName = cafeName;
	}

}