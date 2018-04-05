package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PlannerParam")
public class PlannerParam  {

	// common param
	private String portalId;
	private String userId;

	// planCnt param
	private String searchDate;
	
	// planList param
	private String searchUserId;
	private String searchStartDate;
	private String searchEndDate;
	private String searchValue;
	private String searchPageNumber = "1";
	private String searchPagePerRecord = "20";
	private String searchArea;
	
	// delete param
	private String itemId;
	
	// teamPlanList param
	private String targetType;
	private String targetId;
	private String userIds;
	private String facilityIds;
	
	// agreementSchedule param
	private String searchStartTime;
	private String searchEndTime;
	private String leadTime;
	private String leadMinute;
	private String shareList;
	private String facilitiesList;
	
	private String itemStartDateTime;
	private String itemEndDateTime;
	private String isAgree;
	
	private String plannerType;
	private String workspaceId;
	
	private String sendMail;
	
	private String schedulePrivate = "0";
	
	private String scheduleId;
	
	private String cartooletcId;
	private String meetingRoomId;
	
	/*
	 * GET
	 */

	/**
	 * @return portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return searchDate
	 */
	public String getSearchDate() {
		return searchDate;
	}
	
	/**
	 * @return searchUserId
	 */
	public String getSearchUserId() {
		return searchUserId;
	}

	/**
	 * @return searchStartDate
	 */
	public String getSearchStartDate() {
		return searchStartDate;
	}

	/**
	 * @return searchEndDate
	 */
	public String getSearchEndDate() {
		return searchEndDate;
	}

	/**
	 * @return searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}
	
	/**
	 * @return searchPageNumber
	 */
	public String getSearchPageNumber() {
		return searchPageNumber;
	}
	
	/**
	 * @return searchPagePerRecord
	 */
	public String getSearchPagePerRecord() {
		return searchPagePerRecord;
	}

	/**
	 * @return itemId
	 */
	public String getItemId() {
		return itemId;
	}
	
	/**
	 * @return targetType
	 */
	public String getTargetType() {
		return targetType;
	}
	
	/**
	 * @return targetId
	 */
	public String getTargetId() {
		return targetId;
	}
	
	/**
	 * @return userIds
	 */
	public String getUserIds() {
		return userIds;
	}
	
	/**
	 * @return facilityIds
	 */
	public String getFacilityIds() {
		return facilityIds;
	}
	
	/**
	 * 
	 * @return searchStartTime
	 */
	public String getSearchStartTime() {
		return searchStartTime;
	}

	/**
	 * 
	 * @return searchEndTime
	 */
	public String getSearchEndTime() {
		return searchEndTime;
	}

	/**
	 * 
	 * @return leadTime
	 */
	public String getLeadTime() {
		return leadTime;
	}

	/**
	 * 
	 * @return leadMinute
	 */
	public String getLeadMinute() {
		return leadMinute;
	}

	/**
	 * 
	 * @return shareList
	 */
	public String getShareList() {
		return shareList;
	}

	/**
	 * 
	 * @return facilitiesList
	 */
	public String getFacilitiesList() {
		return facilitiesList;
	}
	
	/**
	 * 
	 * @return itemStartDateTime
	 */
	public String getItemStartDateTime() {
		return itemStartDateTime;
	}
	
	/**
	 * 
	 * @return itemEndDateTime
	 */
	public String getItemEndDateTime() {
		return itemEndDateTime;
	}
	
	/**
	 * 
	 * @return isAgree
	 */
	public String getIsAgree() {
		return isAgree;
	}
	

	
	/* 
	 * SET
	 */
	
	/**
	 * @param portalId
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	
	/**
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param searchDate
	 */
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	
	/**
	 * @param searchUserId
	 */
	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}

	/**
	 * @param searchStartDate
	 */
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	/**
	 * @param searchEndDate
	 */
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	/**
	 * @param searchValue
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	/**
	 * @param searchPageNumber
	 */
	public void setSearchPageNumber(String searchPageNumber) {
		this.searchPageNumber = searchPageNumber;
	}
	
	/**
	 * @param searchPagePerRecord
	 */
	public void setSearchPagePerRecord(String searchPagePerRecord) {
		this.searchPagePerRecord = searchPagePerRecord;
	}

	public String getSearchArea() {
		return searchArea;
	}

	public void setSearchArea(String searchArea) {
		this.searchArea = searchArea;
	}

	/**
	 * @param itemId
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	/**
	 * @param targetType
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
	/**
	 * @param targetId
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	/**
	 * @param userIds
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	
	/**
	 * @param facilityIds
	 */
	public void setFacilityIds(String facilityIds) {
		this.facilityIds = facilityIds;
	}
	
	/**
	 * 
	 * @param searchStartTime
	 */
	public void setSearchStartTime(String searchStartTime) {
		this.searchStartTime = searchStartTime;
	}

	/**
	 * 
	 * @param searchEndTime
	 */
	public void setSearchEndTime(String searchEndTime) {
		this.searchEndTime = searchEndTime;
	}

	/**
	 * 
	 * @param leadTime
	 */
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}

	/**
	 * 
	 * @param leadMinute
	 */
	public void setLeadMinute(String leadMinute) {
		this.leadMinute = leadMinute;
	}

	/**
	 * 
	 * @param shareList
	 */
	public void setShareList(String shareList) {
		this.shareList = shareList;
	}

	/**
	 * 
	 * @param facilitiesList
	 */
	public void setFacilitiesList(String facilitiesList) {
		this.facilitiesList = facilitiesList;
	}
	
	/**
	 * 
	 * @param itemStartDateTime
	 */
	public void setItemStartDateTime(String itemStartDateTime) {
		this.itemStartDateTime = itemStartDateTime;
	}
	
	/**
	 * 
	 * @param itemEndDateTime
	 */
	public void setItemEndDateTime(String itemEndDateTime) {
		this.itemEndDateTime = itemEndDateTime;
	}
	
	/**
	 * 
	 * @param isAgree
	 */
	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getPlannerType() {
		return plannerType;
	}

	public void setPlannerType(String plannerType) {
		this.plannerType = plannerType;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getSendMail() {
		return sendMail;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public String getSchedulePrivate() {
		return schedulePrivate;
	}

	public void setSchedulePrivate(String schedulePrivate) {
		this.schedulePrivate = schedulePrivate;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getCartooletcId() {
		return cartooletcId;
	}

	public void setCartooletcId(String cartooletcId) {
		this.cartooletcId = cartooletcId;
	}

	public String getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}
	
}
