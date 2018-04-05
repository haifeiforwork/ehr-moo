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
 * Assessment Management AssessmentManagementPolicyPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPolicyPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementPolicyPK extends BaseObject { 

	/**
	 *
	 */
	private static final long serialVersionUID = 7774877105297964082L;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 생성자
	 */
	public AssessmentManagementPolicyPK() {
	}

	/**
	 * 생성자
	 * @param portalId
	 */
	public AssessmentManagementPolicyPK(String portalId) {
		this.portalId = portalId;
	}

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

}
