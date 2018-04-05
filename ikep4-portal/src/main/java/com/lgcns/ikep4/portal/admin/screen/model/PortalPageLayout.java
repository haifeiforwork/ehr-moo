/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 포탈 페이지 레이아웃 model
 *
 * @author 임종상
 * @version $Id: PortalPageLayout.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalPageLayout extends BaseObject{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 2385371015437912528L;

	/**
	 * 페이지 레이아웃 ID 
	 */
	private String pageLayoutId; 
	
	/**
	 * 페이지 ID
	 */
	private String pageId;
	
	/**
	 * 레이아웃 ID
	 */
	private String layoutId;
	
	/**
	 * 소유자 ID
	 */
	private String ownerId;
	
	/**
	 * 열 인덱스 (1단, 2단, 3단 표시)
	 */
	private int colIndex;
	
	/**
	 * 단 너비
	 */
	private double width;
	
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
	private String registDate;
	
	/**
	 * 수정일 ID
	 */
	private String updaterId;
	
	/**
	 * 수정자 이름
	 */
	private String updaterName;
	
	/**
	 * 수정일
	 */
	private String updateDate;
	
	/**
	 * 레이아웃 공용여부(0 : 개인, 1 : 공용)
	 */
	private int common; 
	
	public int getCommon() {
		return common;
	}

	public void setCommon(int common) {
		this.common = common;
	}

	/**
	 * @return the pageLayoutId
	 */
	public String getPageLayoutId() {
		return pageLayoutId;
	}

	/**
	 * @param pageLayoutId the pageLayoutId to set
	 */
	public void setPageLayoutId(String pageLayoutId) {
		this.pageLayoutId = pageLayoutId;
	}

	/**
	 * @return the pageId
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * @return the layoutId
	 */
	public String getLayoutId() {
		return layoutId;
	}

	/**
	 * @param layoutId the layoutId to set
	 */
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the colIndex
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * @param colIndex the colIndex to set
	 */
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
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
