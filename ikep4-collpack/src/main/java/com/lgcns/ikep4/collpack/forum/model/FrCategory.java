/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import java.util.Date;

/**
 * 질문 카테고리 관리 테이블
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrCategory.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class FrCategory extends BaseObject {
   
	/**
	 *
	 */
	private static final long serialVersionUID = -7703609912602261437L;

	/**
	 * 토론 카테고리 ID
	 */
	private String categoryId;

	/**
	 * 토론 카테고리 이름
	 */
    private String categoryName;

    /**
     * 카테고리 메뉴 표시 순서
     */
    private Integer categoryOrder;

    /**
     * 포탈 ID
     */
    private String portalId;

    /**
     * 등록자 ID
     */
    private String registerId;

    /**
     * 등록자 이름
     */
    private String registerName;

    /**
     * 등록일시
     */
    private Date registDate;

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

	public Integer getCategoryOrder() {
		return categoryOrder;
	}

	public void setCategoryOrder(Integer categoryOrder) {
		this.categoryOrder = categoryOrder;
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