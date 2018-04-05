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
 * Assessment Management AssessmentManagementTargetPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementTargetPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementTargetPK extends BaseObject { 

	/**
	 *
	 */
	private static final long serialVersionUID = -7158677307887616839L;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 사용자 PVI 산정 대상 모듈 ID
	 */
	private String moduleCode;

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
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

}
