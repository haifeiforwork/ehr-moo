/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Knowledge Map KnowledgeCategoryBody model
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapCategoryBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapCategoryBody extends KnowledgeMapCategoryPK {

	/**
	 *
	 */
	private static final long serialVersionUID = 8630656694752507725L;

	/**
	 * 카테고리 부모ID (최상위 : Category_ID = Category_Parent_ID)
	 */
	private String categoryParentId;
	
	/**
	 * 카테고리 명
	 */
	@NotEmpty
	@Size(max=100)
	private String categoryName;
	
	/**
	 * 정렬순서 (1,2...,10,11)
	 */
	private int sortOrder;
	
	/**
	 * Blog 검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isBlog;

	/**
	 * Team Collaboration 검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isColl;

	/**
	 * 업무매뉴얼  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isManual;

	/**
	 * QNA  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isQna;

	/**
	 * 용어사전  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isDic;

	/**
	 * Cafe  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isCafe;

	/**
	 * Forum  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isForum;

	/**
	 * Idea  검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isIdea;

	/**
	 * BBS 검색대상 여부 ( 0: 비대상, 1: 대상 )
	 */
	private int isBbs;

	/**
	 * 포탈ID
	 */
	private String portalId;
	
	/**
	 * 등록자ID
	 */
	private String registerId;
	
	/**
	 * 등록자이름
	 */
	private String registerName;
	
	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * @return the categoryParentId
	 */
	public String getCategoryParentId() {
		return categoryParentId;
	}

	/**
	 * @param categoryParentId the categoryParentId to set
	 */
	public void setCategoryParentId(String categoryParentId) {
		this.categoryParentId = categoryParentId;
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
	 * @return the isBlog
	 */
	public int getIsBlog() {
		return isBlog;
	}

	/**
	 * @param isBlog the isBlog to set
	 */
	public void setIsBlog(int isBlog) {
		this.isBlog = isBlog;
	}

	/**
	 * @return the isColl
	 */
	public int getIsColl() {
		return isColl;
	}

	/**
	 * @param isColl the isColl to set
	 */
	public void setIsColl(int isColl) {
		this.isColl = isColl;
	}

	/**
	 * @return the isManual
	 */
	public int getIsManual() {
		return isManual;
	}

	/**
	 * @param isManual the isManual to set
	 */
	public void setIsManual(int isManual) {
		this.isManual = isManual;
	}

	/**
	 * @return the isQna
	 */
	public int getIsQna() {
		return isQna;
	}

	/**
	 * @param isQna the isQna to set
	 */
	public void setIsQna(int isQna) {
		this.isQna = isQna;
	}

	/**
	 * @return the isDic
	 */
	public int getIsDic() {
		return isDic;
	}

	/**
	 * @param isDic the isDic to set
	 */
	public void setIsDic(int isDic) {
		this.isDic = isDic;
	}

	/**
	 * @return the isCafe
	 */
	public int getIsCafe() {
		return isCafe;
	}

	/**
	 * @param isCafe the isCafe to set
	 */
	public void setIsCafe(int isCafe) {
		this.isCafe = isCafe;
	}

	/**
	 * @return the isForum
	 */
	public int getIsForum() {
		return isForum;
	}

	/**
	 * @param isForum the isForum to set
	 */
	public void setIsForum(int isForum) {
		this.isForum = isForum;
	}

	/**
	 * @return the isIdea
	 */
	public int getIsIdea() {
		return isIdea;
	}

	/**
	 * @param isIdea the isIdea to set
	 */
	public void setIsIdea(int isIdea) {
		this.isIdea = isIdea;
	}

	/**
	 * @return the isBbs
	 */
	public int getIsBbs() {
		return isBbs;
	}

	/**
	 * @param isBbs the isBbs to set
	 */
	public void setIsBbs(int isBbs) {
		this.isBbs = isBbs;
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

}
