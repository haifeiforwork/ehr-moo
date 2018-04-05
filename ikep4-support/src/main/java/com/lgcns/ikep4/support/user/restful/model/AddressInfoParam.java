package com.lgcns.ikep4.support.user.restful.model;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement(name="AddressInfoParam")
public class AddressInfoParam  {

	private String portalId;
	
	private String userId;

	private String deptId;
	
	private String addrUserId;
	
	private String isFavorite;
	
	private String folderId;

	private String date;
	
	private String searchUserId;

	private String searchWord;
	
	private String userFlag;
	
	private String listSize;
	
	private String page;
	
	
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
	 * @return deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @return addrUserId
	 */
	public String getAddrUserId() {
		return addrUserId;
	}
	
	/**
	 * @return isFavorite
	 */
	public String getIsFavorite() {
		return isFavorite;
	}
	
	/**
	 * @return folderId
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * @return searchUserId
	 */
	public String getSearchUserId() {
		return searchUserId;
	}

	/**
	 * @return searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @return userFlag
	 */
	public String getUserFlag() {
		return userFlag;
	}

	/**
	 * @return listSize
	 */
	public String getListSize() {
		return listSize;
	}

	/**
	 * @return page
	 */
	public String getPage() {
		return page;
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
	 * @param deptId
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @param addrUserId
	 */
	public void setAddrUserId(String addrUserId) {
		this.addrUserId = addrUserId;
	}
	
	/**
	 * @param isFavorite
	 */
	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}
		
	
	/**
	 * @param vcardGroupId
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * @param searchUserId
	 */
	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}

	/**
	 * @param searchWord
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * @param userFlag
	 */
	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	/**
	 * @param listSize
	 */
	public void setListSize(String listSize) {
		this.listSize = listSize;
	}

	/**
	 * @param page
	 */
	public void setPage(String page) {
		this.page = page;
	}	
}
