/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.base.model;

/**
 * KnowledgeCategoryParameterMap model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: CategorySortOrderMap.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class CategorySortOrderMap {

	/**
	 *
	 */
	private String categoryId;

	/**
	 *
	 */
	private String categoryParentId;
	
	/**
	 *
	 */
	private int sortOrder;
	
	/**
	 *
	 */
	private int sortOrderFrom;
	
	/**
	 *
	 */
	private int sortOrderTo;

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
	 * @return the sortOrderFrom
	 */
	public int getSortOrderFrom() {
		return sortOrderFrom;
	}

	/**
	 * @param sortOrderFrom the sortOrderFrom to set
	 */
	public void setSortOrderFrom(int sortOrderFrom) {
		this.sortOrderFrom = sortOrderFrom;
	}

	/**
	 * @return the sortOrderTo
	 */
	public int getSortOrderTo() {
		return sortOrderTo;
	}

	/**
	 * @param sortOrderTo the sortOrderTo to set
	 */
	public void setSortOrderTo(int sortOrderTo) {
		this.sortOrderTo = sortOrderTo;
	}

	/**
	 * Constructor
	 */
	public CategorySortOrderMap() {
		sortOrderFrom = 0;
		sortOrderTo = 0;
	}

	/**
	 * Constructor
	 * @param categoryId
	 * @param categoryParentId
	 * @param sortOrder
	 * @param sortOrderFrom
	 * @param sortOrderTo
	 */
	public CategorySortOrderMap(String categoryId, String categoryParentId, int sortOrder, int sortOrderFrom, int sortOrderTo) {
		this.categoryId = categoryId;
		this.categoryParentId = categoryParentId;
		this.sortOrder = sortOrder;
		this.sortOrderFrom = sortOrderFrom;
		this.sortOrderTo = sortOrderTo;
	}

}
