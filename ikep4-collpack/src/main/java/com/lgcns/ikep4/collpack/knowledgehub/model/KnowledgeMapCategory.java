/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.model;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Knowledge Map KnowledgeCategory model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapCategory.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapCategory extends KnowledgeMapCategoryBody {

	/**
	 *
	 */
	private static final long serialVersionUID = 6257681010198739771L;

	/**
	 * Child Category 개수
	 */
	private int childCount;

	/**
	 * Tag (,로 구분된 문자열)
	 * tagging table에 사용되는 항목
	 */
	@NotEmpty
	private String tags;

	/**
	 * @return the childCount
	 */
	public int getChildCount() {
		return childCount;
	}

	/**
	 * @param childCount the childCount to set
	 */
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

}
