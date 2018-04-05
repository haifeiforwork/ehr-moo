/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Expert Network ExpertListPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkListPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkListPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6564534986617616414L;

	/**
	 * 전문가 ID
	 */
	@NotEmpty
	private String userId;

	/**
	 * 전문가 카테고리 ID
	 */
	@NotEmpty
	private String categoryId;


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

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

}
