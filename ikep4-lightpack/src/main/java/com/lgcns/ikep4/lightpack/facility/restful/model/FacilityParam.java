package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MeetingRoomParam")
public class FacilityParam  {

	// common param
	private String portalId;
	private String userId;
	
	// retrieveFacility param
	private String categoryId;
	
	private String searchWord;
	
	private String facilityId;
	private String date;
	
	private String isWholeDay = "0";
	private String searchStartDate;
	private String searchStartTime;
	private String searchEndDate;
	private String searchEndTime;
	private String meetingRoomId;
	private String cartooletcId;
	
	
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
	 * @return categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	
	/**
	 * @return searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}
	
	/**
	 * @return facilityId
	 */
	public String getFacilityId() {
		return facilityId;
	}
	
	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * @return isWholeDay
	 */
	public String getIsWholeDay() {
		return isWholeDay;
	}
	
	/**
	 * @return searchStartDate
	 */
	public String getSearchStartDate() {
		return searchStartDate;
	}
	
	/**
	 * @return searchStartTime
	 */
	public String getSearchStartTime() {
		return searchStartTime;
	}
	
	/**
	 * @return searchEndDate
	 */
	public String getSearchEndDate() {
		return searchEndDate;
	}
	
	/**
	 * @return searchEndTime
	 */
	public String getSearchEndTime() {
		return searchEndTime;
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
	 * @param facilityCategoryId
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	/**
	 * @param searchWord
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
	/**
	 * @param facilityId
	 */
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	
	/**
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * @param isWholeDay
	 */
	public void setIsWholeDay(String isWholeDay) {
		this.isWholeDay = isWholeDay;
	}
	
	/**
	 * @param searchStartDate
	 */
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	
	/**
	 * @param searchStartTime
	 */
	public void setSearchStartTime(String searchStartTime) {
		this.searchStartTime = searchStartTime;
	}
	
	/**
	 * @param searchEndDate
	 */
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	
	/**
	 * @param searchEndTime
	 */
	public void setSearchEndTime(String searchEndTime) {
		this.searchEndTime = searchEndTime;
	}
	/**
	 * @param meetingRoomId
	 */
	public String getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}
	/**
	 * @param cartooletcId
	 */
	public String getCartooletcId() {
		return cartooletcId;
	}

	public void setCartooletcId(String cartooletcId) {
		this.cartooletcId = cartooletcId;
	}
	
}
