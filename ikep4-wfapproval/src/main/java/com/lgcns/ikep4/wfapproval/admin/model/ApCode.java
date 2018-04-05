/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.model;

import java.sql.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApCode.java 16234 2011-08-18 02:44:36Z giljae $
 */
public class ApCode extends BaseObject {
	
	private static final long serialVersionUID = -6193939972099623836L;
	
	/**
	 * 코드ID
	 */
	private String codeId;
	
	/**
	 * 부모 코드ID
	 */
	private String parentCodeId;
	
	/**
	 * 코드명
	 */
	private String codeName;
	
	/**
	 * 코드 한글명
	 */
	@NotEmpty
	private String krName;
	
	/**
	 * 코드 영문명
	 * @Pattern(regexp="[0-9a-zA-Z]")
	 */	
	private String enName;
	
	/**
	 * 코드구분값
	 */
	private String codeValue;
	
	/**
	 * 코드 설명
	 */
	@Size(min=0, max=30)
	private String codeDesc;
	
	/**
	 * 코드타입
	 */
	private String codeType;
	
	/**
	 * 코드정렬값
	 */
	private String codeOrder;
	
	/**
	 * 코드 사용유무
	 */
	private String codeUse;
	
	/**
	 * 등록일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date registDate;
	
	/**
	 * 수정일자
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date modifyDate;
	
	/**
	 * 하위 개수
	 */
	private String childCount; 
	
	/**
	 * @return the codeName
	 */
	public String getCodeName() {
		return codeName;
	}

	/**
	 * @param codeName the codeName to set
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	/**
	 * @return the childCount
	 */
	public String getChildCount() {
		return childCount;
	}

	/**
	 * @param childCount the childCount to set
	 */
	public void setChildCount(String childCount) {
		this.childCount = childCount;
	}

	/**
	 * @return the codeId
	 */
	public String getCodeId() {
		return codeId;
	}

	/**
	 * @param codeId the codeId to set
	 */
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	/**
	 * @return the parentCodeId
	 */
	public String getParentCodeId() {
		return parentCodeId;
	}

	/**
	 * @param parentCodeId the parentCodeId to set
	 */
	public void setParentCodeId(String parentCodeId) {
		this.parentCodeId = parentCodeId;
	}

	/**
	 * @return the krName
	 */
	public String getKrName() {
		return krName;
	}

	/**
	 * @param krName the krName to set
	 */
	public void setKrName(String krName) {
		this.krName = krName;
	}

	/**
	 * @return the enName
	 */
	public String getEnName() {
		return enName;
	}

	/**
	 * @param enName the enName to set
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}

	/**
	 * @return the codeValue
	 */
	public String getCodeValue() {
		return codeValue;
	}

	/**
	 * @param codeValue the codeValue to set
	 */
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	/**
	 * @return the codeDesc
	 */
	public String getCodeDesc() {
		return codeDesc;
	}

	/**
	 * @param codeDesc the codeDesc to set
	 */
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	/**
	 * @return the codeType
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * @param codeType the codeType to set
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * @return the codeOrder
	 */
	public String getCodeOrder() {
		return codeOrder;
	}

	/**
	 * @param codeOrder the codeOrder to set
	 */
	public void setCodeOrder(String codeOrder) {
		this.codeOrder = codeOrder;
	}

	/**
	 * @return the codeUse
	 */
	public String getCodeUse() {
		return codeUse;
	}

	/**
	 * @param codeUse the codeUse to set
	 */
	public void setCodeUse(String codeUse) {
		this.codeUse = codeUse;
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
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
