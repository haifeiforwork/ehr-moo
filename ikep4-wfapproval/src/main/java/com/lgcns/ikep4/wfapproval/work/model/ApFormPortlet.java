/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 양식 포틀릿정보
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormPortlet.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApFormPortlet extends BaseObject {

	private static final long serialVersionUID = -3174555603808561504L;

	/**
	 * 양식ID
	 */
	@NotEmpty
	private String formId;

	/**
	 * 사용자ID
	 */
	@NotEmpty
	private String userId;

	/**
	 * 사용횟수
	 */
	private Integer useCount;

	/**
	 * 마지막 사용일자
	 */
	private Date useDate;
	
	/**
	 * Top Row Count
	 */
	private Integer rowCount = 5;

	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the useCount
	 */
	public Integer getUseCount() {
		return useCount;
	}

	/**
	 * @param useCount the useCount to set
	 */
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	/**
	 * @return the useDate
	 */
	public Date getUseDate() {
		return useDate;
	}

	/**
	 * @param useDate the useDate to set
	 */
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	
	/**
	 * @return the rowCount
	 */
	public Integer getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
}
