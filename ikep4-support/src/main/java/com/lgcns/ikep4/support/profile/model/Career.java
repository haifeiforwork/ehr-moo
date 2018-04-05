/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.profile.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 경력 정보  Model Object
 *
 * @author 이형운 (dewey94@wooin.info)
 * @version $Id: Career.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Career extends BaseObject {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1199013294032165932L;
	
	/**
	 * 경력 정보 ID
	 */
	public String careerId;
	/**
	 * 경력 회사명 : 최대 12자
	 */
	@Size(min = 0, max = 12)
	public String companyName;
	/**
	 * 근무기간 시작일  : (yyyy.MM.dd)
	 */
	@NotNull
	@DateTimeFormat(pattern="yyyy.MM.dd")
	public Date workStartDate;
	/**
	 * 근무기간 종료일 : (yyyy.mm.dd)
	 */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	public Date workEndDate;
	/**
	 * 역할 : 최대 18자
	 */
	@Size(min = 0, max = 18)
	public String roleName;
	/**
	 * 담당업무 : 최대 200자
	 */
	@Size(min = 0, max = 200)
	public String workChange;
	/**
	 * 등록자 ID
	 */
	public String registerId;
	/**
	 * 등록자 명
	 */
	public String registerName;
	/**
	 * 등록일시
	 */
	public Date registDate;
	/**
	 * 수정일시
	 */
	public Date updateDate;
	
	/**
	 * 엑셀 다운로드용 Start Date
	 */
	public String strWorkStartDate;

	/**
	 * 엑셀 다운로드용 End Date
	 */
	public String strWorkEndDate;
	
	
	/**
	 * @return the careerId
	 */
	public String getCareerId() {
		return careerId;
	}
	/**
	 * @param careerId the careerId to set
	 */
	public void setCareerId(String careerId) {
		this.careerId = careerId;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the workStartDate
	 */
	public Date getWorkStartDate() {
		return workStartDate;
	}
	/**
	 * @param workStartDate the workStartDate to set
	 */
	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}
	/**
	 * @return the workEndDate
	 */
	public Date getWorkEndDate() {
		return workEndDate;
	}
	/**
	 * @param workEndDate the workEndDate to set
	 */
	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = workEndDate;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the workChange
	 */
	public String getWorkChange() {
		return workChange;
	}
	/**
	 * @param workChange the workChange to set
	 */
	public void setWorkChange(String workChange) {
		this.workChange = workChange;
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
	 * @return the strWorkStartDate
	 */
	public String getStrWorkStartDate() {
		return strWorkStartDate;
	}
	/**
	 * @param strWorkStartDate the strWorkStartDate to set
	 */
	public void setStrWorkStartDate(String strWorkStartDate) {
		this.strWorkStartDate = strWorkStartDate;
	}
	/**
	 * @return the strWorkEndDate
	 */
	public String getStrWorkEndDate() {
		return strWorkEndDate;
	}
	/**
	 * @param strWorkEndDate the strWorkEndDate to set
	 */
	public void setStrWorkEndDate(String strWorkEndDate) {
		this.strWorkEndDate = strWorkEndDate;
	}
	
	

}
