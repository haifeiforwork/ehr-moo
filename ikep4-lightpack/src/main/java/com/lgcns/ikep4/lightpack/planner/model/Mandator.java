/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 일정관리 위임
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: Mandator.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class Mandator extends BaseObject {

	private static final long serialVersionUID = 3128009922656269324L;

	/**
	 * 일정 위임자
	 */
	private String mandatorId;
	
	/**
	 * 일정 피위임자
	 */
	private String trusteeId;
	
	private String trusteeName;
	
	private String trusteeEnglishName;
	
	/**
	 * 위임 시작일자
	 */
	private Date startDate;
	
	/**
	 * 위임 종료일작
	 */
	private Date endDate;

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
	 * 등록일자
	 */
	private Date registDate;

	///////////////////////////////////////////////////////////
	
	/**
	 * @return the mandatorId
	 */
	public String getMandatorId() {
		return mandatorId;
	}

	/**
	 * @param mandatorId the mandatorId to set
	 */
	public void setMandatorId(String mandatorId) {
		this.mandatorId = mandatorId;
	}

	/**
	 * @return the trusteeId
	 */
	public String getTrusteeId() {
		return trusteeId;
	}

	/**
	 * @param trusteeId the trusteeId to set
	 */
	public void setTrusteeId(String trusteeId) {
		this.trusteeId = trusteeId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getTrusteeName() {
		return trusteeName;
	}

	public void setTrusteeName(String trusteeName) {
		this.trusteeName = trusteeName;
	}

	public String getTrusteeEnglishName() {
		return trusteeEnglishName;
	}

	public void setTrusteeEnglishName(String trusteeEnglishName) {
		this.trusteeEnglishName = trusteeEnglishName;
	}
	
	
}
