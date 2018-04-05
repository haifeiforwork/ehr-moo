package com.lgcns.ikep4.portal.admin.screen.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 디폴트 페이지 레이아웃 모델
 *
 * @author 임종상
 * @version $Id: PortalDefPageLayout.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalDefPageLayout extends BaseObject {

	private static final long serialVersionUID = -1950540416523068906L;
	
	/**
	 * 디폴트 페이지 레이아웃 ID
	 */
	private String defaultPageLayoutId;
	
	/**
	 * 페이지 ID
	 */
	private String pageId;
	
	/**
	 * 레이아웃 ID
	 */
	private String layoutId;
	
	/**
	 * 열 인덱스
	 */
	private int colIndex;
	
	/**
	 * 열 인덱스
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
	
	public String getDefaultPageLayoutId() {
		return defaultPageLayoutId;
	}

	public void setDefaultPageLayoutId(String defaultPageLayoutId) {
		this.defaultPageLayoutId = defaultPageLayoutId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
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
