package com.lgcns.ikep4.portal.admin.screen.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 디폴트 포틀릿 config 모델
 *
 * @author 임종상
 * @version $Id: PortalDefPortletConfig.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalDefPortletConfig  extends BaseObject {

	private static final long serialVersionUID = -9024467296374056268L;
	
	/**
	 * 디폴트 PortletConfigId
	 */
	private String defaultPortletConfigId;
	
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
	 * 포틀릿 최소,일반,최대 화면 플래그
	 */
	private String viewMode;
	
	/**
	 * 헤더 사용여부
	 */
	private int headerMode;
	
	/**
	 * 디폴트 레이아웃 ID
	 */
	private String defaultPageLayoutId;
	
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
	private Date registDate;
	
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
	private Date updateDate;
	
	public int getHeaderMode() {
		return headerMode;
	}

	public void setHeaderMode(int headerMode) {
		this.headerMode = headerMode;
	}
	
	public String getDefaultPortletConfigId() {
		return defaultPortletConfigId;
	}

	public void setDefaultPortletConfigId(String defaultPortletConfigId) {
		this.defaultPortletConfigId = defaultPortletConfigId;
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

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getDefaultPageLayoutId() {
		return defaultPageLayoutId;
	}

	public void setDefaultPageLayoutId(String defaultPageLayoutId) {
		this.defaultPageLayoutId = defaultPageLayoutId;
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

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
