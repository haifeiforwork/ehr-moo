/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


public class ApprOfficialConfig extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -19603840852644811L;

	private String configId;

	private String portalId;

	private String headerTitle;

	private String headerHeight;

	private String headerType;

	private String headerImgId;

	private String headerAlign;

	private String footerTitle;

	private String footerHeight;

	private String footerType;

	private String footerImgId;

	private String footerAlign;

	private String companyName;

	private String companyAddress;

	private String companyZipCode;

	private String companyTel;

	private String companyFax;

	private String sealId;

	private String registerId;

	private String registerName;

	private Date registDate;

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getHeaderTitle() {
		return headerTitle;
	}

	public void setHeaderTitle(String headerTitle) {
		this.headerTitle = headerTitle;
	}

	public String getHeaderHeight() {
		return headerHeight;
	}

	public void setHeaderHeight(String headerHeight) {
		this.headerHeight = headerHeight;
	}

	public String getHeaderType() {
		return headerType;
	}

	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}

	public String getHeaderImgId() {
		return headerImgId;
	}

	public void setHeaderImgId(String headerImgId) {
		this.headerImgId = headerImgId;
	}

	public String getHeaderAlign() {
		return headerAlign;
	}

	public void setHeaderAlign(String headerAlign) {
		this.headerAlign = headerAlign;
	}

	public String getFooterTitle() {
		return footerTitle;
	}

	public void setFooterTitle(String footerTitle) {
		this.footerTitle = footerTitle;
	}

	public String getFooterHeight() {
		return footerHeight;
	}

	public void setFooterHeight(String footerHeight) {
		this.footerHeight = footerHeight;
	}

	public String getFooterType() {
		return footerType;
	}

	public void setFooterType(String footerType) {
		this.footerType = footerType;
	}

	public String getFooterImgId() {
		return footerImgId;
	}

	public void setFooterImgId(String footerImgId) {
		this.footerImgId = footerImgId;
	}

	public String getFooterAlign() {
		return footerAlign;
	}

	public void setFooterAlign(String footerAlign) {
		this.footerAlign = footerAlign;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyZipCode() {
		return companyZipCode;
	}

	public void setCompanyZipCode(String companyZipCode) {
		this.companyZipCode = companyZipCode;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public String getSealId() {
		return sealId;
	}

	public void setSealId(String sealId) {
		this.sealId = sealId;
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

}
