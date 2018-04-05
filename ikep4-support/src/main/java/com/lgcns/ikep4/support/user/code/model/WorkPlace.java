/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.code.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 사업장코드 모델
 * 
 * @author 최재영
 * @version $Id: WorkPlace.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class WorkPlace extends BaseObject {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -6768828515701490831L;

	/**
	 * 순번
	 */
	private String num;
	
	/**
	 * 사업장 코드
	 */
	@NotNull
	@Size(min=0,max=20)
	private String workPlaceCode;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 사업장 이름
	 */
	@NotNull
	@Size(min=0,max=20)
	private String workPlaceName;

	/**
	 * 사업장 영어 이름
	 */
	@NotNull
	@Size(min=0,max=100)
	private String workPlaceEnglishName;
	
	/**
	 * 정렬 순서
	 */
	private String sortOrder;

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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}

	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getWorkPlaceName() {
		return workPlaceName;
	}

	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}
	
	public String getWorkPlaceEnglishName() {
		return workPlaceEnglishName;
	}

	public void setWorkPlaceEnglishName(String workPlaceEnglishName) {
		this.workPlaceEnglishName = workPlaceEnglishName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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

}
