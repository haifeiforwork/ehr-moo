/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * Assessment Management AssessmentManagementUserPviPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementUserPviPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementUserPviPK extends BaseObject { 

	/**
	 *
	 */
	private static final long serialVersionUID = 2443180324734680664L;

	/**
	 * 사용자 ID
	 */
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
