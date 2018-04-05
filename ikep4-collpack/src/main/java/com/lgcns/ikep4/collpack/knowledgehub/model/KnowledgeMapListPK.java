/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Knowledge Map KnowledgeListPK model
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapListPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapListPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 7588355031431697379L;

	/**
	 * 카테고리 ID
	 */
	@NotEmpty
	private String categoryId;

	/**
	 * 게시물ID
	 */
	@NotEmpty
	private String itemId;

	/**
	 * 해당 게시물의 서비스 유닛 구분 (SB : BLOG, TC : TeamCOLL, QA : QNA, CV : DIC, WM : MANUAL, CF : CAFE, FR : FORUM, ID : IDEA, BD : BBS )
	 */
	@NotEmpty
	private String itemType;


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
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

}
