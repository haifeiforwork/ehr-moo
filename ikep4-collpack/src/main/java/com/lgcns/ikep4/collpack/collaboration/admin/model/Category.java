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
 * Workspace Category 객체
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: Category.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class Category extends BaseObject {

	private static final long serialVersionUID = -9008136270278390614L;

	/**
	 * 워크스페이스 카테고리 ID
	 */
	private String categoryId;

	/**
	 * 타입아이디
	 */
	private String typeId;

	/**
	 * 포탈Id
	 */
	private String portalId;

	/**
	 * 워크스페이스 카테고리 명
	 */
	@NotEmpty
	@Size(min = 0, max = 200)
	private String categoryName;

	@NotEmpty
	@Size(min = 0, max = 200)
	private String categoryEnglishName;

	/**
	 * 카테고리 별 순서
	 */
	private int sortOrder;

	/**
	 * 사용유무 (0: 사용 , 1: 삭제)
	 */
	private int isDelete;

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
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 명
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/****************
	 * 추가사항
	 */

	/**
	 * 유형(타입) 이름
	 */
	private String typeName;

	/**
	 * 유형(타입)  영문 이름
	 */
	private String typeEnglishName;

	/**
	 * 타입별 워크스페이스 수
	 */
	private int countWorkspace;

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

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
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	 * @return the countWorkspace
	 */
	public int getCountWorkspace() {
		return countWorkspace;
	}

	/**
	 * @param countWorkspace the countWorkspace to set
	 */
	public void setCountWorkspace(int countWorkspace) {
		this.countWorkspace = countWorkspace;
	}

	/**
	 * @return the categoryEnglishName
	 */
	public String getCategoryEnglishName() {
		return categoryEnglishName;
	}

	/**
	 * @param categoryEnglishName the categoryEnglishName to set
	 */
	public void setCategoryEnglishName(String categoryEnglishName) {
		this.categoryEnglishName = categoryEnglishName;
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

}
