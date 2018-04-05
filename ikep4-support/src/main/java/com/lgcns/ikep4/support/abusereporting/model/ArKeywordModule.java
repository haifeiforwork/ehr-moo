/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.abusereporting.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * Keyword VO
 *
 * @author 최성우
 * @version $Id: ArKeywordModule.java 16258 2011-08-18 05:37:22Z giljae $
 */

public class ArKeywordModule extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -4118511420124927305L;

	/**
	 * 모듈 코드
	 */
	private String moduleCode;

	/**
	 * 키워드
	 */
	private String keyword;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

}
