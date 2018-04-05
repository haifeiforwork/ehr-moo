/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.validation;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 접근 가능 여부
 * 
 * @author 주길재(giljae@gmail.com)
 * @version $Id: AccessingResult.java 16276 2011-08-18 07:09:07Z giljae $
 */
public class AccessingResult extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private boolean result;

	/**
	 * 접근 가능 여부
	 * @return - 승인(true), 불가(false) 
	 */
	public boolean isAccess() {
		return this.result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
