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
 * Knowledge Map KnowledgeRecommendTagPK model
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendTagPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapRecommendTagPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -3353307664574276818L;

	/**
	 * 사용자ID (추천받는 사람)
	 */
	@NotEmpty
	private String userId;

	/**
	 * 추천받은 태그 ID
	 */
	@NotEmpty
	private String recommendTagId;

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
	 * @return the recommendTagId
	 */
	public String getRecommendTagId() {
		return recommendTagId;
	}

	/**
	 * @param recommendTagId the recommendTagId to set
	 */
	public void setRecommendTagId(String recommendTagId) {
		this.recommendTagId = recommendTagId;
	}

}
