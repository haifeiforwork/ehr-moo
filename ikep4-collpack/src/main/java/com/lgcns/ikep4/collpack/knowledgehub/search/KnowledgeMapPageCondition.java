/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.search;

import com.lgcns.ikep4.support.base.pagecondition.MorePageCondition;

/**
 * Knowledge Map 페이징
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPageCondition.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMapPageCondition extends MorePageCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -5207501037490379953L;

	/**
	 * 포털 ID
	 */
	private String portalId;

	/**
	 * 사용자 ID
	 */
	private String userId;

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

}
