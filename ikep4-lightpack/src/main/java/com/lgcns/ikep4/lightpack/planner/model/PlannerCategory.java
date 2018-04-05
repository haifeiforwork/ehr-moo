/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 범주 model
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: PlannerCategory.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class PlannerCategory extends BaseObject {

	private static final long serialVersionUID = 2386148839283868375L;
	
	public static final String DEFAULT_CATEGORY_ID = "1";

	private String portalId;
	
	private String categoryId; 		// 범주 id
	
	private String seqId;	// 표시 순서
	
	private String categoryName;	// 카테고리 명 (없음, 중요, 업무, 개인, 휴가, 필수참석, 출장, 생일, 기념일 등)
	
	private String categoryType;	// 카테고리 타입  ( 0 : 공통, 1 : 일정, 2 : 이벤트)
	
	private String description; // 카테고리 설명
	
	private String color;		// 카테고리 구분 색상
	
	private String registerId;	// 등록자  ID
	
	private String registerName; // 등록자 이름
	
	private Date registDate;	// 등록일
	
	private String icon;
	
	// 카테고리 로케일 목록
	private List<PlannerCategoryLocale> plannerCategoryLocaleList;
	
	private int sortOrder;

	/**
	 * @return the id
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @return the seqId
	 */
	public String getSeqId() {
		return seqId;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
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
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
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
	 * @return the categoryType
	 */
	public String getCategoryType() {
		return categoryType;
	}

	/**
	 * @param categoryType the categoryType to set
	 */
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<PlannerCategoryLocale> getPlannerCategoryLocaleList() {
		return plannerCategoryLocaleList;
	}

	public void setPlannerCategoryLocaleList(List<PlannerCategoryLocale> plannerCategoryLocaleList) {
		this.plannerCategoryLocaleList = plannerCategoryLocaleList;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
