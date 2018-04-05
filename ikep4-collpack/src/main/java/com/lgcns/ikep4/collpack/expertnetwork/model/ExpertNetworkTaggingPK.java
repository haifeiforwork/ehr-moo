/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Expert Network ExpertTaggingPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkTaggingPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkTaggingPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6853583728888379190L;

	/**
	 * 태그명
	 */
	@NotEmpty
	@Size(max=200)
	private String tag;

	/**
	 * 전문가 카테고리 ID
	 */
	@NotEmpty
	private String categoryId;

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
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
