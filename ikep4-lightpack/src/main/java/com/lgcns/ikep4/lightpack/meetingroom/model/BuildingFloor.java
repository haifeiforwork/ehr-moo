/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 시스템 map VO
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystem.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class BuildingFloor extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5053554515408925907L;
	
	/**
	 * 순서
	 */
	private String num;

	/**
	 * 트리 레벨
	 */
	private int depthLevel;
	
	private String buildingFloorId;
	
	@NotNull
	@Size(min=0,max=20)
	private String buildingFloorName;
	
	@NotNull
	@Size(min=0,max=100)
	private String buildingFloorEnglishName;
	
	private String parentBuildingFloorId;
	
	private String useFlag;
	
	private String sortOrder;
	
	private String portalId;
	
	private String registerId;
	
	private String registerName;
	
	private String registDate;
	
	private String updaterId;
	
	private String updaterName;
	
	private String updateDate;
	
	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return the depthLevel
	 */
	public int getDepthLevel() {
		return depthLevel;
	}

	/**
	 * @param depthLevel the depthLevel to set
	 */
	public void setDepthLevel(int depthLevel) {
		this.depthLevel = depthLevel;
	}

	/**
	 * @return the buildingFloorId
	 */
	public String getBuildingFloorId() {
		return buildingFloorId;
	}

	/**
	 * @param buildingFloorId the buildingFloorId to set
	 */
	public void setBuildingFloorId(String buildingFloorId) {
		this.buildingFloorId = buildingFloorId;
	}

	/**
	 * @return the buildingFloorName
	 */
	public String getBuildingFloorName() {
		return buildingFloorName;
	}

	/**
	 * @param buildingFloorName the buildingFloorName to set
	 */
	public void setBuildingFloorName(String buildingFloorName) {
		this.buildingFloorName = buildingFloorName;
	}

	/**
	 * @return the buildingFloorEnglishName
	 */
	public String getBuildingFloorEnglishName() {
		return buildingFloorEnglishName;
	}

	/**
	 * @param buildingFloorEnglishName the buildingFloorEnglishName to set
	 */
	public void setBuildingFloorEnglishName(String buildingFloorEnglishName) {
		this.buildingFloorEnglishName = buildingFloorEnglishName;
	}

	/**
	 * @return the parentBuildingFloorId
	 */
	public String getParentBuildingFloorId() {
		return parentBuildingFloorId;
	}

	/**
	 * @param parentBuildingFloorId the parentBuildingFloorId to set
	 */
	public void setParentBuildingFloorId(String parentBuildingFloorId) {
		this.parentBuildingFloorId = parentBuildingFloorId;
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
	
}