/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.assess.model;

import java.util.Date;

/**
 * Assessment Management AssessmentManagementInitializationLogBody model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementInitializationLogBody.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementInitializationLogBody extends AssessmentManagementInitializationLogPK { 

	/**
	 *
	 */
	private static final long serialVersionUID = -1561522934462459823L;

	/**
	 * 사용자 평가점수 초기화 일시
	 */
	private Date initialDate;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * @return the initialDate
	 */
	public Date getInitialDate() {
		return initialDate;
	}

	/**
	 * @param initialDate the initialDate to set
	 */
	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
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

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

}
