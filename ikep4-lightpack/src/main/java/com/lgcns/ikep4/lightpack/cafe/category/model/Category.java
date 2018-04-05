/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.category.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Cafe Category 객체
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: Category.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class Category extends BaseObject {

	private static final long serialVersionUID = -9008136270278390614L;

	/**
	 * 워크스페이스 카테고리 ID
	 */
	private String categoryId;

	/**
	 * 포탈Id
	 */
	private String portalId;

	/**
	 * 워크스페이스 카테고리 명
	 */
	// @NotEmpty
	// @Size(min = 0, max = 200)
	private String categoryName;

	// @NotEmpty
	// @Size(min = 0, max = 200)
	private String categoryEnglishName;

	/**
	 * 카테고리 별 순서
	 */
	private int sortOrder;

	/**
	 * 사용유무 (0: 사용 , 1: 삭제)
	 */
	private int isDelete;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 명
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * 부모 카테고리 아이디
	 */
	private String parentId;

	/**
	 * 카테고리 설명
	 */
	// @NotEmpty
	// @Size(min = 0, max = 200)
	private String description;

	/**
	 * 카테고리별 워크스페이스 수
	 */
	private int countCafe;

	/**
	 * 하위카테고리 개수
	 */
	private int countChild;

	/**
	 * 카테고리 위치
	 */
	private String categoryLocation;

	/**
	 * 카테고리 개수
	 */
	private int cafeCount;

	/**
	 * 카테고리 개수
	 */
	private int childCount;

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
	 * @return the isDelete
	 */
	public int getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
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
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
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
	 * @return the countCafe
	 */
	public int getCountCafe() {
		return countCafe;
	}

	/**
	 * @param countCafe the countCafe to set
	 */
	public void setCountCafe(int countCafe) {
		this.countCafe = countCafe;
	}

	/**
	 * @return the categoryEnglishName
	 */
	public String getCategoryEnglishName() {
		return categoryEnglishName;
	}

	/**
	 * @param categoryEnglishName the categoryEnglishName to set
	 */
	public void setCategoryEnglishName(String categoryEnglishName) {
		this.categoryEnglishName = categoryEnglishName;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	 * @return the categoryLocation
	 */
	public String getCategoryLocation() {
		return categoryLocation;
	}

	/**
	 * @param categoryLocation the categoryLocation to set
	 */
	public void setCategoryLocation(String categoryLocation) {
		this.categoryLocation = categoryLocation;
	}

	/**
	 * @return the cafeCount
	 */
	public int getCafeCount() {
		return cafeCount;
	}

	/**
	 * @param cafeCount the cafeCount to set
	 */
	public void setCafeCount(int cafeCount) {
		this.cafeCount = cafeCount;
	}

	/**
	 * @return the countChild
	 */
	public int getCountChild() {
		return countChild;
	}

	/**
	 * @param countChild the countChild to set
	 */
	public void setCountChild(int countChild) {
		this.countChild = countChild;
	}

	/**
	 * @return the childCount
	 */
	public int getChildCount() {
		return childCount;
	}

	/**
	 * @param childCount the childCount to set
	 */
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

}
