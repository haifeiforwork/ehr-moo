package com.lgcns.ikep4.collpack.kms.main.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class KmsPortlet extends BaseObject {
	
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
	/**
	 * 게시판 ID
	 */
	private String boardId;
	/**
	 * 포틀릿 영문명
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
