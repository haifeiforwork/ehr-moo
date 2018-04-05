/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.admin.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Workspace Type 객체
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: WorkspaceType.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class WorkspaceType extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -7386797021650744508L;

	/**
	 * 타입 코드
	 */
	private String typeId;

	/**
	 * 포탈ID
	 */
	private String portalId;

	/**
	 * 타입 명(워크스페이스/Cop/TFT/Project/Informal)
	 */
	@NotEmpty
	@Size(min = 0, max = 200)
	private String typeName;

	/**
	 * 타입 영문명
	 */
	@NotEmpty
	@Size(min = 0, max = 200)
	private String typeEnglishName;

	/**
	 * 타입설명
	 */
	@NotEmpty
	@Size(min = 0, max = 500)
	private String typeDescription;

	/**
	 * 타입영문설명
	 */
	@NotEmpty
	@Size(min = 0, max = 500)
	private String typeEnglishDescription;

	/**
	 * 유형(타입별) 워크스페이스 수
	 */
	private int countWorkspaceByType;

	/**
	 * Type 순서
	 */
	private int sortOrder;

	/**
	 * 카테고리의 조직여부(0:기타타입,1:조직타입)
	 */
	private int isOrganization;

	/**
	 * 삭제여부 (0:사용,1:삭제처리)
	 */
	private int isDelete;

	/**
	 * 등록자 ID(개설 신청자)
	 */
	private String registerId;

	/**
	 * 등록자명(개설자)
	 */
	private String registerName;

	/**
	 * 등록일(개설일)
	 */
	private Date registDate;

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the isOrganization
	 */
	public int getIsOrganization() {
		return isOrganization;
	}

	/**
	 * @param isOrganization the isOrganization to set
	 */
	public void setIsOrganization(int isOrganization) {
		this.isOrganization = isOrganization;
	}

	/**
	 * @return the isDelete
	 */
	public int getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
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
	 * @return the typeDescription
	 */
	public String getTypeDescription() {
		return typeDescription;
	}

	/**
	 * @param typeDescription the typeDescription to set
	 */
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	/**
	 * @return the countWorkspaceByType
	 */
	public int getCountWorkspaceByType() {
		return countWorkspaceByType;
	}

	/**
	 * @param countWorkspaceByType the countWorkspaceByType to set
	 */
	public void setCountWorkspaceByType(int countWorkspaceByType) {
		this.countWorkspaceByType = countWorkspaceByType;
	}

	/**
	 * @return the typeEnglishName
	 */
	public String getTypeEnglishName() {
		return typeEnglishName;
	}

	/**
	 * @param typeEnglishName the typeEnglishName to set
	 */
	public void setTypeEnglishName(String typeEnglishName) {
		this.typeEnglishName = typeEnglishName;
	}

	public String getTypeEnglishDescription() {
		return typeEnglishDescription;
	}

	public void setTypeEnglishDescription(String typeEnglishDescription) {
		this.typeEnglishDescription = typeEnglishDescription;
	}

}
