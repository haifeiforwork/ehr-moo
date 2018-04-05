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
 * @version $Id: KnowledgeMapRecommendTagBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapRecommendTagBody extends KnowledgeMapRecommendTagPK {

	/**
	 *
	 */
	private static final long serialVersionUID = -7769615727516535433L;

	/**
	 * 정렬순서(1,2,3,..)
	 */
	private int sortOrder;

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

}
