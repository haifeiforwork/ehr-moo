/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 포틀릿_배치_정보 VO
 *
 * @author 이형운
 * @version $Id: SocialBlogPortletLayout.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBlogPortletLayout extends BaseObject {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -8961321802872801572L;

	/**
	 * 포틀릿_배치_ID
	 */
	private String portletLayoutId;
	
	/**
	 * 블로그 ID
	 */
	private String blogId;
	
	/**
	 * 포틀릿_ID
	 */
	private String portletId;
	
	/**
	 * 컬럼 인덱스
	 */
	private Integer colIndex;
	
	/**
	 * 행 인덱스(정렬 순서)
	 */
	private Integer rowIndex;
	
	/**
	 * 해당 포틀릿 레이아웃의 포틀릿 정보
	 */
	private SocialBlogPortlet socialBlogPortlet;

	/**
	 * @return the portletLayoutId
	 */
	public String getPortletLayoutId() {
		return portletLayoutId;
	}

	/**
	 * @param portletLayoutId the portletLayoutId to set
	 */
	public void setPortletLayoutId(String portletLayoutId) {
		this.portletLayoutId = portletLayoutId;
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

	/**
	 * @return the portletId
	 */
	public String getPortletId() {
		return portletId;
	}

	/**
	 * @param portletId the portletId to set
	 */
	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	/**
	 * @return the colIndex
	 */
	public Integer getColIndex() {
		return colIndex;
	}

	/**
	 * @param colIndex the colIndex to set
	 */
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	/**
	 * @return the rowIndex
	 */
	public Integer getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @return the socialBlogPortlet
	 */
	public SocialBlogPortlet getSocialBlogPortlet() {
		return socialBlogPortlet;
	}

	/**
	 * @param socialBlogPortlet the socialBlogPortlet to set
	 */
	public void setSocialBlogPortlet(SocialBlogPortlet socialBlogPortlet) {
		this.socialBlogPortlet = socialBlogPortlet;
	}
	
}
