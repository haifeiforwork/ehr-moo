/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 결재 시스템 모델
 * 
 * @author  
 * @version $Id: ApprSystem.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class ApprSystem extends BaseObject {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 시스템 ID
	 */
	private String systemId;
	
	/**
	 * 시스템 명
	 */
	@NotNull
	@Size(min=0,max=50)
	private String systemName;
	
	/**
	 * 시스템 영문명
	 */
	@NotNull
	@Size(min=0,max=50)
	private String systemEnName;
	
	/**
	 * 시스템 타입 (0:문서결재, 1:통합뷰방식연계, 2:결재 Action 연계, 3:결재 Action과 결재선 연계, 4:결재 Action, 결재선, 결재내용)
	 */
	private int systemType;

	/**
	 * 정렬 순서
	 */
	private int systemOrder;

	
	/**
	 * 포탈 ID
	 */
	private String portalId;


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
	 * 수정자 ID
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
	
	/**
	 * 코드 중복체크용 변수
	 */
	private String systemIdCheck;

	private	String	description;
	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @return the systemEnName
	 */
	public String getSystemEnName() {
		return systemEnName;
	}

	/**
	 * @param systemEnName the systemEnName to set
	 */
	public void setSystemEnName(String systemEnName) {
		this.systemEnName = systemEnName;
	}

	/**
	 * @return the systemType
	 */
	public int getSystemType() {
		return systemType;
	}

	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}

	/**
	 * @return the systemOrder
	 */
	public int getSystemOrder() {
		return systemOrder;
	}

	/**
	 * @param systemOrder the systemOrder to set
	 */
	public void setSystemOrder(int systemOrder) {
		this.systemOrder = systemOrder;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
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

	/**
	 * @return the systemIdCheck
	 */
	public String getSystemIdCheck() {
		return systemIdCheck;
	}

	/**
	 * @param systemIdCheck the systemIdCheck to set
	 */
	public void setSystemIdCheck(String systemIdCheck) {
		this.systemIdCheck = systemIdCheck;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



}
