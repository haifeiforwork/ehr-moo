/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 주소록 그룹 VO 
 *
 * @author 이형운
 * @version $Id: Addrgroup.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class Addrgroup extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 2105676927788814999L;

	
	private String categoryId;
	
	private String categoryName;
	
	private String messengerUse;
	/**
	 * 개인 주소록 그룹 ID
	 */
	private String addrgroupId;
	
	private String addrgroupSeqId;
	
	public String getAddrgroupSeqId() {
		return addrgroupSeqId;
	}

	public void setAddrgroupSeqId(String addrgroupSeqId) {
		this.addrgroupSeqId = addrgroupSeqId;
	}

	/**
	 * 그룹명
	 */
	@NotNull
	@NotEmpty
	private String addrgroupName;
	
	/**
	 * 메모
	 */
	private String addrgroupMemo;
	
	/**
	 * P : 개인그룹,  O : 공용그룹
	 */
	private String groupType;
	
	/**
	 * 포탈 ID(for Multi Portal) - 공용그룹일 경우 only
	 */
	private String portalId;
	
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
	 * 수정일
	 */
	private Date updateDate;
	
	/**
	 * 소속 그룹의 사용자 수
	 */
	private Integer addrgroupUserCnt;
	
	/**
	 * 소속 그룹 사용자의 ID
	 */
	private List<String> addrpersonList;

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
	 * @return the addrgroupName
	 */
	public String getAddrgroupName() {
		return addrgroupName;
	}

	/**
	 * @param addrgroupName the addrgroupName to set
	 */
	public void setAddrgroupName(String addrgroupName) {
		this.addrgroupName = addrgroupName;
	}

	/**
	 * @return the addrgroupMemo
	 */
	public String getAddrgroupMemo() {
		return addrgroupMemo;
	}

	/**
	 * @param addrgroupMemo the addrgroupMemo to set
	 */
	public void setAddrgroupMemo(String addrgroupMemo) {
		this.addrgroupMemo = addrgroupMemo;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
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
	 * @return the addrgroupUserCnt
	 */
	public Integer getAddrgroupUserCnt() {
		return addrgroupUserCnt;
	}

	/**
	 * @param addrgroupUserCnt the addrgroupUserCnt to set
	 */
	public void setAddrgroupUserCnt(Integer addrgroupUserCnt) {
		this.addrgroupUserCnt = addrgroupUserCnt;
	}

	/**
	 * @return the addrpersonList
	 */
	public List<String> getAddrpersonList() {
		return addrpersonList;
	}

	/**
	 * @param addrpersonList the addrpersonList to set
	 */
	public void setAddrpersonList(List<String> addrpersonList) {
		this.addrpersonList = addrpersonList;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMessengerUse() {
		return messengerUse;
	}

	public void setMessengerUse(String messengerUse) {
		this.messengerUse = messengerUse;
	}
	
}
