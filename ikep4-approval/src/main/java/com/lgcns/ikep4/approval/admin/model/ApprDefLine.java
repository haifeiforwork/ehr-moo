/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Default 결재선 정보
 *
 * @author 
 * @version $Id$
 */
public class ApprDefLine extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Default 결재선 ID
	 */
	private	String	defLineId;
	
	/**
	 * Default 결재선 이름
	 */
	@NotEmpty
	@Size(max = 100)
	private String	defLineName;
	
	/**
	 * Default 결재선 설명
	 */
	private	String	defLineDesc;
	
	/**
	 * 결재유형(0:내부결재,1:협조공문)
	 */
	private	int		defLineType;
	
	/**
	 * 결재합의방법(0:순차합의,1:병렬합의)
	 */
	private	int		defLineWay;
	
	
	/**
	 * 등록자Id
	 */
	private	String registerId;
	
	/**
	 * 등록자명
	 */
	private	String registerName;
	
	/**
	 * 등록일
	 */
	private	Date registDate;

	private List<ApprDefLineInfo> apprDefLineInfo;
	
	
	private String portalId;

	private String systemId;
	
	private String systemName;
	
	private	String	systemEnName;
	
	/**
	 * @return the defLineId
	 */
	public String getDefLineId() {
		return defLineId;
	}

	/**
	 * @param defLineId the defLineId to set
	 */
	public void setDefLineId(String defLineId) {
		this.defLineId = defLineId;
	}

	/**
	 * @return the defLineName
	 */
	public String getDefLineName() {
		return defLineName;
	}

	/**
	 * @param defLineName the defLineName to set
	 */
	public void setDefLineName(String defLineName) {
		this.defLineName = defLineName;
	}

	/**
	 * @return the defLineDesc
	 */
	public String getDefLineDesc() {
		return defLineDesc;
	}

	/**
	 * @param defLineDesc the defLineDesc to set
	 */
	public void setDefLineDesc(String defLineDesc) {
		this.defLineDesc = defLineDesc;
	}

	/**
	 * @return the defLineType
	 */
	public int getDefLineType() {
		return defLineType;
	}

	/**
	 * @param defLineType the defLineType to set
	 */
	public void setDefLineType(int defLineType) {
		this.defLineType = defLineType;
	}

	/**
	 * @return the defLineWay
	 */
	public int getDefLineWay() {
		return defLineWay;
	}

	/**
	 * @param defLineWay the defLineWay to set
	 */
	public void setDefLineWay(int defLineWay) {
		this.defLineWay = defLineWay;
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

	public List<ApprDefLineInfo> getApprDefLineInfo() {
		return apprDefLineInfo;
	}

	public void setApprDefLineInfo(List<ApprDefLineInfo> apprDefLineInfo) {
		this.apprDefLineInfo = apprDefLineInfo;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getSystemId() {
		return systemId;
	}

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

}
