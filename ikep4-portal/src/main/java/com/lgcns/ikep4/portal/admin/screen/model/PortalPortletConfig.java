package com.lgcns.ikep4.portal.admin.screen.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 포틀릿 config 모델
 *
 * @author 임종상
 * @version $Id: PortalPortletConfig.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalPortletConfig extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -8245888112646388372L;

	/**
	 * 포틀릿 config ID
	 */
	private String portletConfigId;
	
	/**
	 * 페이지 레이아웃 ID
	 */
	private String pageLayoutId;
	
	/**
	 * 포틀릿 ID
	 */
	private String portletId;
	
	/**
	 * 열 인덱스
	 */
	private int colIndex;
	
	/**
	 * 행 인덱스
	 */
	private int rowIndex;
	
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
	 * 포틀릿 최소,일반,최대 화면 플래그 값(최소 : MIN, 일반 : NORMAL, 최대 : MAX)
	 */
	private String viewMode;

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getPortletConfigId() {
		return portletConfigId;
	}

	public void setPortletConfigId(String portletConfigId) {
		this.portletConfigId = portletConfigId;
	}

	public String getPageLayoutId() {
		return pageLayoutId;
	}

	public void setPageLayoutId(String pageLayoutId) {
		this.pageLayoutId = pageLayoutId;
	}

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}
