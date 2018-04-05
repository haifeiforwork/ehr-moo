/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Cafe의 공용 포틀릿 정보
 * 
 * @author 김종철
 * @version $Id: CafePortlet.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class CafePortlet extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -417943091503391986L;

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
	 * 정렬 순서
	 */
	private Integer sortOrder;

	/**
	 * DEFAULT 포틀릿 여부(1: DEFAULT)
	 */
	private Integer isDefault;

	private String boardId;

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
	 * @return the boardId
	 */
	public String getBoardId() {
		return boardId;
	}

	/**
	 * @param boardId the boardId to set
	 */
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	/**
	 * @return the portletEnglishName
	 */
	public String getPortletEnglishName() {
		return portletEnglishName;
	}

	/**
	 * @param portletEnglishName the portletEnglishName to set
	 */
	public void setPortletEnglishName(String portletEnglishName) {
		this.portletEnglishName = portletEnglishName;
	}

}