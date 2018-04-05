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
public class MeetingRoom extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6166180181636524352L;

	private String meetingRoomId;

	private String meetingRoomName;
	
	private String meetingRoomEnglishName;

	private String buildingId;

	private String buildingName;

	private String managerId;
	
	private String subManagerId;
	 
	private String lastManagerId;

	private String managerName;

	private String equipment;

	private String[] equipmentList;

	private String equipmentName;

	private String capacity;

	private String area;

	private String floorId;

	private String floorName;

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
	
	private String buildingFloorId;
	
	private String buildingFloorName;
	
	private int level;
	
	private String categoryId;
	
	private String toolId;
	
	private String carTools;
	
	private String carTool;
	

	/**
	 * @return the meetingRoomId
	 */
	public String getMeetingRoomId() {
		return meetingRoomId;
	}

	/**
	 * @param meetingRoomId the meetingRoomId to set
	 */
	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	/**
	 * @return the meetingRoomName
	 */
	public String getMeetingRoomName() {
		return meetingRoomName;
	}

	/**
	 * @param meetingRoomName the meetingRoomName to set
	 */
	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}

	/**
	 * @return the meetingRoomEnglishName
	 */
	public String getMeetingRoomEnglishName() {
		return meetingRoomEnglishName;
	}

	/**
	 * @param meetingRoomEnglishName the meetingRoomEnglishName to set
	 */
	public void setMeetingRoomEnglishName(String meetingRoomEnglishName) {
		this.meetingRoomEnglishName = meetingRoomEnglishName;
	}

	/**
	 * @return the buildingId
	 */
	public String getBuildingId() {
		return buildingId;
	}

	/**
	 * @param buildingId the buildingId to set
	 */
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	/**
	 * @return the buildingName
	 */
	public String getBuildingName() {
		return buildingName;
	}

	/**
	 * @param buildingName the buildingName to set
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

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
	 * @return the equipment
	 */
	public String getEquipment() {
		return equipment;
	}

	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	/**
	 * @return the equipmentList
	 */
	public String[] getEquipmentList() {
		return equipmentList;
	}

	/**
	 * @param equipmentList the equipmentList to set
	 */
	public void setEquipmentList(String[] equipmentList) {
		this.equipmentList = equipmentList;
	}

	/**
	 * @return the equipmentName
	 */
	public String getEquipmentName() {
		return equipmentName;
	}

	/**
	 * @param equipmentName the equipmentName to set
	 */
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	/**
	 * @return the capacity
	 */
	public String getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the floorId
	 */
	public String getFloorId() {
		return floorId;
	}

	/**
	 * @param floorId the floorId to set
	 */
	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	/**
	 * @return the floorName
	 */
	public String getFloorName() {
		return floorName;
	}

	/**
	 * @param floorName the floorName to set
	 */
	public void setFloorName(String floorName) {
		this.floorName = floorName;
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

	public String getBuildingFloorId() {
		return buildingFloorId;
	}

	public void setBuildingFloorId(String buildingFloorId) {
		this.buildingFloorId = buildingFloorId;
	}

	public String getBuildingFloorName() {
		return buildingFloorName;
	}

	public void setBuildingFloorName(String buildingFloorName) {
		this.buildingFloorName = buildingFloorName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	public String getCarTools() {
		return carTools;
	}

	public void setCarTools(String carTools) {
		this.carTools = carTools;
	}

	public String getCarTool() {
		return carTool;
	}

	public void setCarTool(String carTool) {
		this.carTool = carTool;
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
