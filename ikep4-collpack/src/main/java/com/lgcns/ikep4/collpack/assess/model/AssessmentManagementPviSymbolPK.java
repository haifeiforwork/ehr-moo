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
 * Assessment Management AssessmentManagementPviSymbolPK model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: AssessmentManagementPviSymbolPK.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class AssessmentManagementPviSymbolPK extends BaseObject { 

	/**
	 *
	 */
	private static final long serialVersionUID = 1430130047552287383L;

	/**
	 * PVI 심볼 ID
	 */
	private String symbolId;

	/**
	 * @return the symbolId
	 */
	public String getSymbolId() {
		return symbolId;
	}

	/**
	 * @param symbolId the symbolId to set
	 */
	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}

}
