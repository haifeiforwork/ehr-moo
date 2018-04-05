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
 * Expert Network ExpertFellowPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkFellowPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class ExpertNetworkFellowPK extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 5370721062420332079L;

	/**
	 * 사용자ID(기준사용자)
	 */
	@NotEmpty
	private String userId;

	/**
	 * 전문가ID(나의 전문분야 정보와 동일분야)
	 */
	@NotEmpty
	private String expertUserId;


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
	 * @return the expertUserId
	 */
	public String getExpertUserId() {
		return expertUserId;
	}

	/**
	 * @param expertUserId the expertUserId to set
	 */
	public void setExpertUserId(String expertUserId) {
		this.expertUserId = expertUserId;
	}

}
