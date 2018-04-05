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
 * 포틀릿_정보 VO
 *
 * @author 이형운
 * @version $Id: SocialBlogPortlet.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBlogPortlet extends BaseObject {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -59878589157541845L;

	/**
	 * 포틀릿_ID
	 */
	private String portletId;
	
	/**
	 * 포틀릿_이름
	 */
	private String portletName;
	
	/**
	 * 조회 페이지 URL
	 */
	private String viewUrl;
	
	/**
	 * DEFAULT 포틀릿 여부(1: DEFAULT)
	 */
	private Integer isDefault;
	
	/**
	 * 정렬 순서
	 */
	private Integer sortOrder;
	
	/**
	 * 포틀릿 영어 이름
	 */
	private String portletEnglishName;

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
	 * @return the portletName
	 */
	public String getPortletName() {
		return portletName;
	}

	/**
	 * @param portletName the portletName to set
	 */
	public void setPortletName(String portletName) {
		this.portletName = portletName;
	}

	/**
	 * @return the viewUrl
	 */
	public String getViewUrl() {
		return viewUrl;
	}

	/**
	 * @param viewUrl the viewUrl to set
	 */
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	/**
	 * @return the isDefault
	 */
	public Integer getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
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

	public String getPortletEnglishName() {
		return portletEnglishName;
	}

	public void setPortletEnglishName(String portletEnglishName) {
		this.portletEnglishName = portletEnglishName;
	}


}
