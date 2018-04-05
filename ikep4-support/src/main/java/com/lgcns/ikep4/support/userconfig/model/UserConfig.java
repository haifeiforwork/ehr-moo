/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.userconfig.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 개인화 설정 모델 클래스.
 *
 * @author 최현식
 * @version $Id: UserConfig.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class UserConfig extends BaseObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 개인화 설정 ID. */
	private String id;

	/** 사용자 ID. */
	private String userId;

	/** 묘듈 ID. */
	private String moduleId;

	/** 페이지 카운트. */
	private Integer pageCount;

	/** 페이지 레이아웃. */
	private String layout;

	/** 포털 ID. */
	private String portalId;

	/** 등록일. */
	private Date registDate;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the module id.
	 *
	 * @return the module id
	 */
	public String getModuleId() {
		return this.moduleId;
	}

	/**
	 * Sets the module id.
	 *
	 * @param moduleId the new module id
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * Gets the page count.
	 *
	 * @return the page count
	 */
	public Integer getPageCount() {
		return this.pageCount;
	}

	/**
	 * Sets the page count.
	 *
	 * @param pageCount the new page count
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * Gets the layout.
	 *
	 * @return the layout
	 */
	public String getLayout() {
		return this.layout;
	}

	/**
	 * Sets the layout.
	 *
	 * @param layout the new layout
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

	/**
	 * Gets the portal id.
	 *
	 * @return the portal id
	 */
	public String getPortalId() {
		return this.portalId;
	}

	/**
	 * Sets the portal id.
	 *
	 * @param portalId the new portal id
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * Gets the regist date.
	 *
	 * @return the regist date
	 */
	public Date getRegistDate() {
		return this.registDate;
	}

	/**
	 * Sets the regist date.
	 *
	 * @param registDate the new regist date
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

}
