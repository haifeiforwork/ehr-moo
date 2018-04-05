/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.addressbook.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 게시글 모델 클래스.
 *
 * @author ${user}
 * @version $$Id: BoardItem.java 16240 2011-08-18 04:08:15Z giljae $$
 */
public class Category extends BaseObject {

	private static final long serialVersionUID = -4708892515108909532L;
	
	private String boardId;

	private String categoryId;
	private String categoryName;
	private String categorySeqId;
	private String categoryType;
	private String color;
	private String description;
	private String portalId;
	private String registerId;
	private String registerName;
	
	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	private Date registDate;
	/**
	 * CategoryId_LIST
	 */
	private String categoryIdList;
	
	/**
	 * CategoryId_LIST
	 */
	private String delIdList;
	
	/**
	 * CategoryId_LIST
	 */
	private String addNameList;
	
	/**
	 * CategoryId_LIST
	 */
	private String updateNameList;
	
	/**
	 * CategoryId_LIST
	 */
	private String updateIdList;
	
	private String alignList;
	
	
	public String getAlignList() {
		return alignList;
	}

	public void setAlignList(String alignList) {
		this.alignList = alignList;
	}

	public String getUpdateNameList() {
		return updateNameList;
	}

	public void setUpdateNameList(String updateNameList) {
		this.updateNameList = updateNameList;
	}

	public String getUpdateIdList() {
		return updateIdList;
	}

	public void setUpdateIdList(String updateIdList) {
		this.updateIdList = updateIdList;
	}

	public String getDelIdList() {
		return delIdList;
	}

	public void setDelIdList(String delIdList) {
		this.delIdList = delIdList;
	}

	public String getAddNameList() {
		return addNameList;
	}

	public void setAddNameList(String addNameList) {
		this.addNameList = addNameList;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(String categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategorySeqId() {
		return categorySeqId;
	}

	public void setCategorySeqId(String categorySeqId) {
		this.categorySeqId = categorySeqId;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
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