/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 소셜블로그 게시글 카테고리용 VO
 *
 * @author 이형운
 * @version $Id: SocialCategory.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialCategory extends BaseObject {


	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -4933957593273976654L;

	/**
	 * 소셜블로그 게시글 카테고리 ID
	 */
	private String categoryId;
	
	/**
	 * 소셜블로그 게시글 카테고리 명
	 */
    @NotNull
	@NotEmpty
	@Size(min=0, max=20)
	private String categoryName;
	
	/**
	 * 소셜블로그 게시글 정렬순서
	 */
	private Integer sortOrder;
	
	/**
	 * 해당 소셜 블로그의 ID
	 */
	private String blogId;

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
	 * @return the blogId
	 */
	public String getBlogId() {
		return blogId;
	}

	/**
	 * @param blogId the blogId to set
	 */
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

}
