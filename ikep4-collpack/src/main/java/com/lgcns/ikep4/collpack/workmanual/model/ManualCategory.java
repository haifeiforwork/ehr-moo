/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualCategory.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class ManualCategory extends BaseObject {
	private static final long serialVersionUID = 7550128124382611174L;
	
	/**
	 * 카테고리 ID 
	 */
	private String categoryId;      
	
	/**
	 * 카테고리명 
	 */
	private String categoryName;    
	
	/**
	 * 부모 카테고리 ID 
	 */
	private String categoryParentId;
	
	/**
	 * 공개여부 
	 */
	private Integer readPermission = 0;
	
	/**
	 * 정렬순서 
	 */
	private Integer sortOrder = 1;       
	
	/**
	 * 포탈 ID 
	 */
	private String portalId;        
	
	/**
	 * 등록자 ID 
	 */
	private String registerId;      
	
	/**
	 * 등록자 이름 
	 */
	private String registerName;    
	
	/**
	 * 등록일 
	 */
	private Date   registDate;
	
	/**
	 * 하위 노드 갯수 
	 */
	private Integer childCount = 0;
	
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
	 * @return the categoryParentId
	 */
	public String getCategoryParentId() {
		return categoryParentId;
	}
	/**
	 * @param categoryParentId the categoryParentId to set
	 */
	public void setCategoryParentId(String categoryParentId) {
		this.categoryParentId = categoryParentId;
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
	 * @return the readPermission
	 */
	public Integer getReadPermission() {
		return readPermission;
	}
	/**
	 * @param readPermission the readPermission to set
	 */
	public void setReadPermission(Integer readPermission) {
		this.readPermission = readPermission;
	}
	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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
}
