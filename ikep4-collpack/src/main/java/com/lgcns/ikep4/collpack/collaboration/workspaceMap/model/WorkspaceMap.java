/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspaceMap.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
/**
 * 
 * WorkspaceMap
 *
 * @author 홍정관
 * @version $Id: WorkspaceMap.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class WorkspaceMap extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -5363744512275007422L;
	
	//부모리스트
	private List<WorkspaceMap> parentList;
	
	//자식리스트
	private List<WorkspaceMap> childList;
	
	//맵 아이디
	private String mapId;
	
	//워크스페이스 아이디
	private String workspaceId;
	
	//맵 부모아이디
	private String mapParentId;
	
	//맵 이름
	@NotEmpty
	@Size(min=0,max=200)
	private String mapName;
	
	//맵 설명
	@NotEmpty
	@Size(min=0,max=500)
	private String mapDescription;
	
	//맵 정렬순서
	private int sortOrder;
	
	//등록자 아이디
	private String registerId;
	
	//등록자 이름
	private String registerName;
	// 등록자 영문 이름
	private String registerEnglishName;
	
	//등록 일자
	private Date registDate;
	
	//수정자 아이디
	private String updaterId;
	
	//수정자 이름
	private String updaterName;
	
	//수정일자
	private Date updateDate;
	
	//WorkspaceTag
	private String tag;
	
	//WorkspaceTag
	@NotEmpty
	private String tags;
	
	
	//actionType
	private String actionType;
	// 맴 하위 갯수
	private Integer childCount;
	
	// 아이템 아이디
	private String itemId;
	// 태그 아이템 아이디
	private String tagItemId;
	// 태그 아이템 이름
	private String tagItemName;
	// 태그 아이템 본문 내용
	private String tagItemContents;
	// 태그 URL
	private String tagItemUrl;
	// 태그 레코그 갯수
	private String recordNumber;
	
	
	/**
	 * @return the mapId
	 */
	public String getMapId() {
		return mapId;
	}
	/**
	 * @param mapId the mapId to set
	 */
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	/**
	 * @return the worspaceId
	 */
	public String getWorkspaceId() {
		return workspaceId;
	}
	/**
	 * @param worspaceId the worspaceId to set
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	/**
	 * @return the mapParentId
	 */
	public String getMapParentId() {
		return mapParentId;
	}
	/**
	 * @param mapParentId the mapParentId to set
	 */
	public void setMapParentId(String mapParentId) {
		this.mapParentId = mapParentId;
	}
	/**
	 * @return the mapName
	 */
	public String getMapName() {
		return mapName;
	}
	/**
	 * @param mapName the mapName to set
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	/**
	 * @return the mapDescription
	 */
	public String getMapDescription() {
		return mapDescription;
	}
	/**
	 * @param mapDescription the mapDescription to set
	 */
	public void setMapDescription(String mapDescription) {
		this.mapDescription = mapDescription;
	}
	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	 * @return the registeDate
	 */
	public Date getRegistDate() {
		return registDate;
	}
	/**
	 * @param registeDate the registeDate to set
	 */
	public void setRegistDate(Date registeDate) {
		this.registDate = registeDate;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the parentList
	 */
	public List<WorkspaceMap> getParentList() {
		return parentList;
	}
	/**
	 * @param parentList the parentList to set
	 */
	public void setParentList(List<WorkspaceMap> parentList) {
		this.parentList = parentList;
	}
	/**
	 * @return the childList
	 */
	public List<WorkspaceMap> getChildList() {
		return childList;
	}
	/**
	 * @param childList the childList to set
	 */
	public void setChildList(List<WorkspaceMap> childList) {
		this.childList = childList;
	}
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	/**
	 * @return the childCount
	 */
	public Integer getChildCount() {
		return childCount;
	}
	/**
	 * @param childCount the childCount to set
	 */
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the tagItemName
	 */
	public String getTagItemName() {
		return tagItemName;
	}
	/**
	 * @param tagItemName the tagItemName to set
	 */
	public void setTagItemName(String tagItemName) {
		this.tagItemName = tagItemName;
	}
	/**
	 * @return the tagItemContents
	 */
	public String getTagItemContents() {
		return tagItemContents;
	}
	/**
	 * @param tagItemContents the tagItemContents to set
	 */
	public void setTagItemContents(String tagItemContents) {
		this.tagItemContents = tagItemContents;
	}
	/**
	 * @return the tagItemUrl
	 */
	public String getTagItemUrl() {
		return tagItemUrl;
	}
	/**
	 * @param tagItemUrl the tagItemUrl to set
	 */
	public void setTagItemUrl(String tagItemUrl) {
		this.tagItemUrl = tagItemUrl;
	}
	/**
	 * @return the recordNumber
	 */
	public String getRecordNumber() {
		return recordNumber;
	}
	/**
	 * @param recordNumber the recordNumber to set
	 */
	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}
	/**
	 * @return the tagItemId
	 */
	public String getTagItemId() {
		return tagItemId;
	}
	/**
	 * @param tagItemId the tagItemId to set
	 */
	public void setTagItemId(String tagItemId) {
		this.tagItemId = tagItemId;
	}
	/**
	 * @return the registerEnglishName
	 */
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}
	/**
	 * @param registerEnglishName the registerEnglishName to set
	 */
	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}
	
}
