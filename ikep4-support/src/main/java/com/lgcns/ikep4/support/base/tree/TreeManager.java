/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.base.tree;

import org.codehaus.jackson.map.ObjectMapper;

import com.lgcns.ikep4.support.base.exception.JsonException;

/**
 * Tree Manager
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: TreeManager.java 16258 2011-08-18 05:37:22Z giljae $
 */
public final class TreeManager {

	/**
	 * 기본 생성자
	 */
	private TreeManager() {
	}

	/**
	 * Object를 JSON String 으로 변환
	 * @param obj - 변환할 Object
	 * @return String - 변환된 JSON 문자열
	 */
	public static String getJSON(Object obj) {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = objectMapper.writeValueAsString(obj);
		} catch(Exception ex) {
			throw new JsonException(ex);
		}

		return jsonString;
	}
}
