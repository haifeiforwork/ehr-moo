/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.model;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public class Cartooletc extends BaseObject {

	private static final long serialVersionUID = 6166180181636524352L;

	/**
	 *
	 */

	private String cartooletcId;

	private String cartooletcName;
	
	private String cartooletcEnglishName;

	private String categoryId;

	private String categoryName;
	
	
	private String regionId;

	private String regionName;

	private String managerId;
	
	private String subManagerId;
	
	private String lastManagerId;

	private String managerName;

	private String phone;

	private String description;

	private String fileId;

	private String useFlag;

	private String sortOrder;
	
	private String portalId;

	private String registerId;

	private String registerName;

	private String registDate;
	
	private String updaterId;
	
	private String updaterName;
	
	private String updateDate;

	private String isRecent;
	
	private String isFavorite;
	
	private String schedulePublic;
	
	private String autoApprove;
	
	private List<Map<String, Object>> scheduleList;
	
	private String toolUseFlag;
	
	private String viewYn;
	
	private String mid;


	/**
	 * @return the managerId
	 */
	public String getManagerId() {
		return managerId;
	}

	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	/**
	 * @return the managerName
	 */
	public String getManagerName() {
		return managerName;
	}

	/**
	 * @param managerName the managerName to set
	 */
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the useFlag
	 */
	public String getUseFlag() {
		return useFlag;
	}

	/**
	 * @param useFlag the useFlag to set
	 */
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */
	public String getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the isRecent
	 */
	public String getIsRecent() {
		return isRecent;
	}

	/**
	 * @param isRecent the isRecent to set
	 */
	public void setIsRecent(String isRecent) {
		this.isRecent = isRecent;
	}

	/**
	 * @return the isFavorite
	 */
	public String getIsFavorite() {
		return isFavorite;
	}

	/**
	 * @param isFavorite the isFavorite to set
	 */
	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}

	/**
	 * @return the schedulePublic
	 */
	public String getSchedulePublic() {
		return schedulePublic;
	}

	/**
	 * @param schedulePublic the schedulePublic to set
	 */
	public void setSchedulePublic(String schedulePublic) {
		this.schedulePublic = schedulePublic;
	}

	/**
	 * @return the autoApprove
	 */
	public String getAutoApprove() {
		return autoApprove;
	}

	/**
	 * @param autoApprove the autoApprove to set
	 */
	public void setAutoApprove(String autoApprove) {
		this.autoApprove = autoApprove;
	}

	public List<Map<String, Object>> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<Map<String, Object>> scheduleList) {
		this.scheduleList = scheduleList;
	}

	public String getCartooletcId() {
		return cartooletcId;
	}

	public void setCartooletcId(String cartooletcId) {
		this.cartooletcId = cartooletcId;
	}

	public String getCartooletcName() {
		return cartooletcName;
	}

	public void setCartooletcName(String cartooletcName) {
		this.cartooletcName = cartooletcName;
	}

	public String getCartooletcEnglishName() {
		return cartooletcEnglishName;
	}

	public void setCartooletcEnglishName(String cartooletcEnglishName) {
		this.cartooletcEnglishName = cartooletcEnglishName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getToolUseFlag() {
		return toolUseFlag;
	}

	public void setToolUseFlag(String toolUseFlag) {
		this.toolUseFlag = toolUseFlag;
	}

	public String getViewYn() {
		return viewYn;
	}

	public void setViewYn(String viewYn) {
		this.viewYn = viewYn;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSubManagerId() {
		return subManagerId;
	}

	public void setSubManagerId(String subManagerId) {
		this.subManagerId = subManagerId;
	}

	public String getLastManagerId() {
		return lastManagerId;
	}

	public void setLastManagerId(String lastManagerId) {
		this.lastManagerId = lastManagerId;
	}

}
