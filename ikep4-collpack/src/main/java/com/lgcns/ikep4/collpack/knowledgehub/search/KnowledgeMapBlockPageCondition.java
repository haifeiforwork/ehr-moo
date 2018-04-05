/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.search;

import com.lgcns.ikep4.support.base.pagecondition.BlockPageCondition;

/**
 * Knowledge Map 페이징
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapBlockPageCondition.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapBlockPageCondition extends BlockPageCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -2998737676929258558L;

	/**
	 * 카테고리 ID
	 */
	private String categoryId;

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
