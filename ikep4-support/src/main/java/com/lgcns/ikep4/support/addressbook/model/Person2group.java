/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Person과 Group 매칭 테이블 VO
 *
 * @author 이형운
 * @version $Id: Person2group.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Person2group extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 6376004676144494703L;

	/**
	 * 개인 주소록 사용자 ID
	 */
	private String personId ;
	
	/**
	 * 개인 주소록 그룹 ID
	 */
	private String addrgroupId;
	
	/**
	 * 사용자 타입(I: 사내 사용자 정보, O: 사외 사용자 정보)
	 */
	private String userType;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자명
	 */
	private String registerName;
	
	/**
	 * 등록일
	 */
	private Date registDate;
	
	/**
	 * 수정자 ID
	 */
	private String updaterId;
	
	/**
	 * 수정자명
	 */
	private String updaterName;
	
	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * @return the addrgroupId
	 */
	public String getAddrgroupId() {
		return addrgroupId;
	}

	/**
	 * @param addrgroupId the addrgroupId to set
	 */
	public void setAddrgroupId(String addrgroupId) {
		this.addrgroupId = addrgroupId;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}

