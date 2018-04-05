/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 게시판 관리 모델 클래스
 *
 * @author ${user}
 * @version $$Id: Award.java 13763 2011-05-30 05:56:02Z designtker $$
 */
public class AwardMark extends BaseObject {

	private static final long serialVersionUID = 698439488205835764L;

	/** 포틀릿 config ID. */
	private String portletConfigId;
	
	/** 게시판 ID. */
	private String awardId;

	/** 등록자 ID. */
	private String registerId;
	
	/** 등록자 ID. */
	private String viewCount;

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	/** 등록일. */
	private Date registDate;

	/** 수정자 Id. */
	private String updaterId;

	/** 수정자 ID. */
	private Date updateDate;

	public String getPortletConfigId() {
		return portletConfigId;
	}

	public void setPortletConfigId(String portletConfigId) {
		this.portletConfigId = portletConfigId;
	}

	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



	
}