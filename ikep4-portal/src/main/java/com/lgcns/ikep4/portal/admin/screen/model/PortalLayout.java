/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 레이아웃 모델
 *
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalLayout.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalLayout extends BaseObject{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -8796683995842185208L;

	/**
	 * 순번
	 */
	private String num;
	
	/**
	 * 레이아웃 ID
	 */
	private String layoutId;
	
	/**
	 * 레이아웃 이름
	 */
	@NotEmpty
	@Size(max = 100)
	private String layoutName;
	
	/**
	 * 레이아웃 공용여부(0 : 개인, 1 : 공용)
	 */
	private int common; 
	
	/**
	 * 설명
	 */
	@Size(max = 250)
	private String description;
	
	/**
	 * 레이아웃 내의 단수(1단,2단,3단 등)
	 */
	@Digits(integer=99999, fraction=0)
	@Range(min=1, max=99999)
	private String layoutNum;
	
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
	private String registDate;
	
	/**
	 * 수정일 ID
	 */
	private String updaterId;
	
	/**
	 * 수정자 이름
	 */
	private String updaterName;
	
	/**
	 * 수정일
	 */
	private String updateDate;
	
	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
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
	
	/**
	 * @return the layoutId
	 */
	public String getLayoutId() {
		return layoutId;
	}

	/**
	 * @param layoutId the layoutId to set
	 */
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	/**
	 * @return the layoutName
	 */
	public String getLayoutName() {
		return layoutName;
	}

	/**
	 * @param layoutName the layoutName to set
	 */
	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	/**
	 * @return the common
	 */
	public int getCommon() {
		return common;
	}

	/**
	 * @param common the common to set
	 */
	public void setCommon(int common) {
		this.common = common;
	}

	/**
	 * @return the layoutNum
	 */
	public String getLayoutNum() {
		return layoutNum;
	}

	/**
	 * @param layoutNum the layoutNum to set
	 */
	public void setLayoutNum(String layoutNum) {
		this.layoutNum = layoutNum;
	}

	/**
	 * @return the registDate
	 */
	public String getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
