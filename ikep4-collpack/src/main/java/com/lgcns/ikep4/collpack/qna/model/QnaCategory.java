package com.lgcns.ikep4.collpack.qna.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class QnaCategory extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 4103624428454893382L;

	/**
	 * 질문 카테고리 ID
	 */
	private String categoryId;
	
	/**
	 * 카테고리 이름
	 */
	@NotEmpty
	private String categoryName;
	
	/**
	 * 카테고리 메뉴 표시 순서
	 */
	private int categoryOrder;
	
	/**
	 * 포탈 ID
	 */
	private String registerId;
	
	/**
	 * 등록자 ID
	 */
	private String registerName;
	
	/**
	 */
	private Date registDate;
	
	/**
	 * 등록자 이름
	 */
	private String portalId;
	
	/**
	 */
	private String updaterId;
	
	/**
	 */
	private String updaterName;
	
	/**
	 */
	private Date updateDate;
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
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
	public int getCategoryOrder() {
		return categoryOrder;
	}
	public void setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	
}