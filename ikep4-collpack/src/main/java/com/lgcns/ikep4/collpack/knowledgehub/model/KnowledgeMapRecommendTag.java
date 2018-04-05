/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.model;


/**
 * Knowledge Map KnowledgeRecommendTagBody model
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendTag.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapRecommendTag extends KnowledgeMapRecommendTagBody {

	/**
	 *
	 */
	private static final long serialVersionUID = -7838765125894920829L;

	/**
	 * 태그 이름
	 */
	private String tagName;

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
