/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.common.exception;

import com.lgcns.ikep4.framework.common.exception.BaseRuntimeException;

/**
 * JSON 변환 Exception (더이상 사용하지 않음) support-common.base 로 이전됨
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: JsonException.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class JsonException extends BaseRuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 5773808435029292653L;

	public JsonException(Throwable cause) {
		super("JSON Convert Error", cause);
	}

	public JsonException() {
		super("JSON Convert Error");
	}

}
