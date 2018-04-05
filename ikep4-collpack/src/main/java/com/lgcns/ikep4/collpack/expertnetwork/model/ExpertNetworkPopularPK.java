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
 * Expert Network ExpertPopularPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkPopularPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkPopularPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 8741174293272576768L;

	/**
	 * 인기있는 전문가 ID (별도 정책에 의해 선정)
	 */
	@NotEmpty
	private String userId;

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
