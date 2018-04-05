/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 아이템타입 코드 모델
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: ItemType.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class ItemType extends BaseObject {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -3812145309154420286L;

	/**
	 * 순번
	 */
	private String num;
	
	/**
	 * 아이템타입 코드
	 */
	@NotNull
	@Size(min=0,max=25)
	private String itemTypeCode;

	/**
	 * 아이템타입 이름
	 */
	@NotNull
	@Size(min=0,max=100)
	private String itemTypeName;

	/**
	 * 아이템타입 표시명
	 */
	@Size(min=0,max=65)
	private String displayName;
	
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
	private String updateDate;

	/**
	 * 코드 중복체크용 변수
	 */
	private String codeCheck;
	
	/**
	 * 썸네일 너비
	 */
	@Digits(integer=10, fraction=0)
	private String thumbnailWidthSize;
	
	/**
	 * 썸네일 높이
	 */
	@Digits(integer=10, fraction=0)
	private String thumbnailHeightSize;
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
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

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCodeCheck() {
		return codeCheck;
	}

	public void setCodeCheck(String codeCheck) {
		this.codeCheck = codeCheck;
	}

	public String getThumbnailWidthSize() {
		return thumbnailWidthSize;
	}

	public void setThumbnailWidthSize(String thumbnailWidthSize) {
		this.thumbnailWidthSize = thumbnailWidthSize;
	}

	public String getThumbnailHeightSize() {
		return thumbnailHeightSize;
	}

	public void setThumbnailHeightSize(String thumbnailHeightSize) {
		this.thumbnailHeightSize = thumbnailHeightSize;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
